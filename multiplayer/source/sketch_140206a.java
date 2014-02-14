import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Vector; 
import java.util.Set; 
import java.util.HashSet; 
import java.util.Vector; 
import java.util.Set; 
import java.util.HashSet; 
import java.util.HashMap; 
import java.util.Map; 
import java.awt.Color; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_140206a extends PApplet {





int num = 4;
int a = 15;
int x,y;
int count = 0;
int status = -1; //1 if player1, 2 if player2 and 0 if drawn
Player[] player;
int white= color(255,255,255);
int RED = color(255,0,0);
int BLUE = color(0,0,255);
int GREEN = color(0,255,0);
boolean ex;
Square[] s, add;
public void setup()
{
  size(400,400);
  stroke(0);
  rectMode(CORNER);
  ex = true;
  player = new Player[2];
  player[0] = new Player(color(0,0,255));
  player[1] = new Player(color(255,0,0));
  s = new Square[1];
  s[0] = new Square(width/2,height/2, 15, color(255,255,255));
  add = s[0].setColor(color(0,255,0),ex,s);
  queue();
}
public void draw()
{
  for(int i=0; i<s.length; i++)
  {
    s[i].display();
  }
  
  if(status!=-1)
  {
    switch (status)
    {
      case 1:
        noLoop();
        fill(50,50,50,90);
        rect(0,0, width, height);
        fill(240, 230,10);
        text("Player1 has won the game", width/2-40, height/2);
     break;
     case 2:
     noLoop();
        fill(50,50,50,90);
        rect(0,0, width, height);
        fill(240, 230,10);
        text("Player2 has won the game", width/2-40, height/2);
     break;
     case 0:
     noLoop();
        fill(50,50,50,90);
        rect(0,0, width, height);
        fill(240, 230,10);
        text("The game is drawn", width/2-40, height/2);
     break;   
    }
  }
}

public void mousePressed()
{
  Player p = player[count%2];
  println("Move "+ (count+1));

  for(int i=0; i<s.length; i++)
  {
    if((s[i].getX()<mouseX && mouseX<s[i].getX()+a)
    && (s[i].getY()<mouseY && mouseY<s[i].getY()+a)
    && (s[i].getColor() == color(255,255,255)))
     {
       if(p.getCount() == 4)
        {
          ex = false;
          p.dequeue();
        }
       add = s[i].setColor(p.getColor(),ex,s);
      //if(p.getCount() < 4)
        // p.addCount();
       queue();
       p.enqueue(s[i]);
       count++;
       println(p.getColor()+" - "+p.getCount());
     } 
  }
   
 for(int i=0; i<s.length; i++)
 {
   if (s[i].getColor() != white)
   {
     int cc = s[i].isCaptured(s);
     
     if (cc == RED || cc == BLUE)
     {
       Player bl = getPlayer(cc);
       
       println(s[i].getX()+","+s[i].getY());
       if(s[i].getX() == width/2
       && s[i].getY() == height/2)
       {
         status = getPlayerNumber(bl);
       }
       else
       {
         if(bl == player[0])
           player[1].removeBlock(s[i]);
         else
           player[0].removeBlock(s[i]);
           
         if(bl.putBlock(s[i]))
         {
           if(s[i].getColor() == RED)
           {
          //player[1].decCount(); 
            player[1].dequeue(s[i]);
           }
           else
           {
             //player[0].decCount();
             player[0].dequeue(s[i]);
           }
         
           Square[] del = s[i].setColor(bl.getColor(),false, s);
         }
       }
     }
   }
 }
 int stemp;
 if(noWhiteSpaces())
   status = 0;
 else if((stemp = greenCaptured()) != -1)
   status = stemp;
}

public void queue()
{ 
  int len;
  if(add != null)
  {
    len = s.length + add.length;
    Square[] temp = new Square[len];
    for(int i=0; i<s.length; i++)
    {
      temp[i] = s[i];
    }
    for(int i=s.length; i<len; i++)
    {
      temp[i] = add[i-s.length];
    }
    s = new Square[len];
    for(int i=0; i<len; i++)
    {
       s[i] = temp[i];
    }
  }
}

public Player getPlayer(int playerColor)
{
  if(player[0].getColor() == playerColor)
     {
       return player[0];
     }
  else if(player[1].getColor() == playerColor)
     {
       return player[1];
     }
     return null;
}
public int getPlayerNumber(Player pl)
{
  if(player[0] == pl)
    return 1;
  else
    return 2;
}
public boolean noWhiteSpaces()
{
  for(int i=0; i<s.length; i++)
  {
    if( s[i].getColor() == white)
      return false;
  }
  return true;
}
public int greenCaptured()
{
  Player pl;
  for(int i=0; i<s.length;i++)
  {
    if(s[i].getColor() == GREEN)
    {
      if((pl = getPlayer(s[i].isCaptured(s))) != null)
        return getPlayerNumber(pl);
      else
        return -1;
    }
  }
  return -1;
}
class Player
{
  Square[] s;
  Square[] blocked;
  private int c;
  private int count;
  
  public Player(int cr)
  {
    c = cr;
    count = 0;
  }
  
  public int getColor()
  {
    return c;
  }
  
  public int getCount()
  {
    return count;
  }
  
/*public void addCount()
  {
    count++;
  }
  
  public void decCount()
  {
    count--;
  }*/
  public void enqueue(Square t)
  {
    Square[] temp;
    count++;
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
    
  }
  public void dequeue()
  {
    count--;
    Square[] temp = new Square[s.length-1];
    for(int i=1; i<s.length;i++)
    {
      temp[i-1] = s[i];
    }
    
    s = s[0].setColor(color(255,255,255),false,s); 
    
    s = new Square[temp.length];
    for(int i=0; i<temp.length;i++)
    {
      s[i]=temp[i];
    }
  }
  public void dequeue(Square r)
  {
    if(s == null)
    {
      return;
    }
    int n = 0;
    int l = s.length - 1;
    boolean isReduced = false;
    Square[] temp = new Square[l];
    println(getColor()+" removing ");
    
    for(int i=0; i<s.length; i++)
    {
      if(s[i].getX() == r.getX()
      && s[i].getY() == r.getY())
        isReduced = true;
    }
    
      try
      {
        if(isReduced)
        {
           for(int i=0; i<s.length; i++)
           {
            if(s[i].getX() == r.getX()
            && s[i].getY() == r.getY())
              continue;
            temp[n]=s[i];
            n++;
           }
     
          count--;
          println(getColor()+" has only "+count); 
          s = new Square[l];
          arrayCopy(temp, 0, s, 0, l);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
 
/*public boolean isBlocked(Square t)
  {
    if(blocked == null)
      return false;
    else
    { 
      for(int i=0; i<blocked.length; i++)
      {
        if(blocked[i] == t)
          return true;
      }
    }
    return false;
  }*/
  public boolean putBlock(Square t)
  {
    if(blocked != null)
    { 
      int l = blocked.length; 
      for(int i=0;i<l;i++)
      {
        if(blocked[i].getX() == t.getX()
        && blocked[i].getY() == t.getY())
        {
          println("Already Blocked");
          return false;
        }
        println("Player "+getColor()+" "+blocked[i].getX()+","+blocked[i].getY());
      }
      
      println("In putBlock ");
      Square[] temp = blocked;//new Square[l+1];
      //arrayCopy(blocked, 0, temp, 0, l);
      //temp[l] = t;
           
      blocked = new Square[l+1];
      arrayCopy(temp,0, blocked,0,l);
      blocked[l] = t;
      
     return true;
    }
    else
    {
      blocked = new Square[1];
      blocked[0] = t;
      return true;
    }
  }
  
  public void removeBlock(Square t)
  {
    //println("In remove block"+t.getX()+","+t.getY());
    if(blocked == null)
      return;
    else
    {
      boolean isFound = false;
      int n = blocked.length-1;
      for(int i=0;i<blocked.length;i++)
      {
        if(blocked[i].getX() == t.getX()
        && blocked[i].getY() == t.getY())
        {
          blocked[i] = blocked[n];
          isFound = true;
        }
      }
      if(isFound)
      {
        println("removing block"+ getColor()+"for "+t.getX()+" "+t.getY());
        try{
          Square[] temp = new Square[n];
          arrayCopy(blocked, 0, temp, 0, n);
          blocked = new Square[n];
          arrayCopy(temp, 0, blocked, 0, n);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
}







class Square
{
  private final int GREEN = color(0,255,0);
  private final int WHITE = color(255,255,255);
  private final int RED   = color(255,0,0);
  private final int BLUE  = color(0,0,255);
  private int x,y;
  private int a;
  private int c;
  
  Square(int posx, int posy, int d, int cr)
  {
    x = posx;
    y = posy;
    a = d;
    c = cr;
  }
  
  public void display()
  {
    fill(c);
    rect(x,y,a,a);
  }
  
  public Square[] setColor(int cr, boolean extending, Square[] ar)
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
  
  public boolean hasRight(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x+a)&&(s[i].y == y))
        return true;
    }
    return false;
  }
  public boolean hasLeft(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x-a)&&(s[i].y == y))
        return true;
    }
    return false;
  }
  public boolean hasTop(Square[] s)
  {
    for(int i=0; i<s.length;i++)
    {
      if((s[i].x == x)&&(s[i].y == y-a))
        return true;
    }
    return false;
  }
  public boolean hasBottom(Square[] s)
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
  
  public int getColor()
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_140206a" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
