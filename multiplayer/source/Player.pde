class Player
{
  Square[] s;
  Square[] blocked;
  private color c;
  private int count;
  
  public Player(color cr)
  {
    c = cr;
    count = 0;
  }
  
  public color getColor()
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
  void enqueue(Square t)
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
  void dequeue()
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
  void dequeue(Square r)
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
  boolean putBlock(Square t)
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
