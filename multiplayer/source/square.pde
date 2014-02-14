import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

class Square
{
  private final color GREEN = color(0,255,0);
  private final color WHITE = color(255,255,255);
  private final color RED   = color(255,0,0);
  private final color BLUE  = color(0,0,255);
  private int x,y;
  private int a;
  private color c;
  
  Square(int posx, int posy, int d, color cr)
  {
    x = posx;
    y = posy;
    a = d;
    c = cr;
  }
  
  void display()
  {
    fill(c);
    rect(x,y,a,a);
  }
  
  Square[] setColor(color cr, boolean extending, Square[] ar)
  {
    Square[] s = null;
    Square t;
    int count = 0;
    if(extending)
    {
      if(!hasLeft(ar))
      {
        t = new Square(x-a,y,a,color(255,255,255));
        s = enqueue(s,t);
      }
      if(!hasRight(ar))
      {
        t = new Square(x+a,y,a,color(255,255,255));
        s = enqueue(s,t);
      }
      if(!hasTop(ar))
      {
        t = new Square(x,y-a,a,color(255,255,255));
        s = enqueue(s,t);
      }
      if(!hasBottom(ar))
      {
        t = new Square(x,y+a,a,color(255,255,255));
        s = enqueue(s,t);
      } 
    }
    c = cr;
    return s;
  }
  
  private Square[] enqueue(Square[] s, Square t)
  {
    Square[] temp;
    if( s != null)
    {
      temp = new Square[s.length+1];
      for(int i=0; i<s.length;i++)
      {
        temp[i] = s[i];
      }
      temp[s.length] = t;
    }
    else
    {
      temp = new Square[1];
      temp[0] = t;
    }
    s = new Square[temp.length];
    
    for(int i=0; i<temp.length;i++)
    {
      s[i]=temp[i];
    }
    
    return s;
  }
  
  boolean hasRight(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x+a)&&(s[i].y == y))
        return true;
    }
    return false;
  }
  boolean hasLeft(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x-a)&&(s[i].y == y))
        return true;
    }
    return false;
  }
  boolean hasTop(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x)&&(s[i].y == y-a))
        return true;
    }
    return false;
  }
  boolean hasBottom(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x)&&(s[i].y == y+a))
        return true;
    }
    return false;
  }
  
  public Square getLeft(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x-a)&&(s[i].y == y))
        return s[i];
    }
    return null;
  }
  public Square getRight(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x+a)&&(s[i].y == y))
        return s[i];
    }
    return null;
  }
  public Square getTop(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x)&&(s[i].y == y-a))
        return s[i];
    }
    return null;
  }
  
  public Square getBottom(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x)&&(s[i].y == y+a))
        return s[i];
    }
    return null;
  }
  public int isCaptured(Square[] ar)
  {
     Vector<Square> v = new Vector<Square>();
     Set<Integer> set = new HashSet<Integer>();
     Map<String,Integer> map = new HashMap<String,Integer>();
     map.put("WHITE",new Integer(0));
     map.put("GREEN",new Integer(0));
     map.put("RED",new Integer(0));
     map.put("BLUE",new Integer(0));
     
     int target;
     
     if(getColor() == GREEN)
       target = 4;
     else
       target = 3;

  if(hasLeft(ar))
  {
    v.add(getLeft(ar));
  }
  if(hasRight(ar))
  {
    v.add(getRight(ar));
  }
  if(hasTop(ar))
  {
    v.add(getTop(ar));
  }
  if(hasBottom(ar))
  {
    v.add(getBottom(ar));
  }

//terminal cells shouldn't be captured, ideally
  if(v.size() == 1 || v.size() == 2)
    return WHITE;
  
  int remInd = -1;
  Integer kw = 0,kg = 0,kr = 0,kb = 0;
  for (int i=0; i<v.size();i++)
  {
    if(v.get(i).getColor() == WHITE)
    {
      kw =  (Integer)map.get("WHITE");
      map.put("WHITE",kw + 1);
    }
    else if (v.get(i).getColor() == GREEN)
    {
      remInd = i;
      kg = (Integer)map.get("GREEN");
      map.put("GREEN", kg + 1);
    }
    else if (v.get(i).getColor() == RED)
    {
      kr = (Integer)map.get("RED");
      map.put("RED", kr + 1);
    }
    else if (v.get(i).getColor() == BLUE)
    {
      kb = (Integer)map.get("BLUE");
      map.put("BLUE", kb + 1);
      //println((Integer)map.get("BLUE"));
    }
  }  
  
 /* for(int i=0; i<v.size();i++)
    System.out.println(v.get(i).getX()+" "+v.get(i).getY()+" "+v.get(i).getColor());
*/
// Remove Green
  map.remove("GREEN");
  if(remInd != -1)
    v.remove(remInd);

//terminal cells shouldn't be captured, ideally
  if(v.size() == 1)
    return WHITE;
  
  target = min(v.size(),target);
  
//Remove whites
  map.remove("WHITE");

  
  //println(target+","+ (Integer)map.get("BLUE")+", "+(Integer)map.get("RED"));
  
  if((Integer)map.get("RED")>=target)
    return RED;
  else if ((Integer)map.get("BLUE") >= target)
    return BLUE;
  else
    return WHITE;
  }
  
  public color getColor()
  {
    return c;
  }
  
  public int getX()
  {
    return x;
  }
  
  public int getY()
  {
    return y;
  }
}
