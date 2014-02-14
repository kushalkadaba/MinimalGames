import java.util.Vector;
import java.util.Set;
import java.util.HashSet;

int num = 4;
int a = 15;
int x,y;
int count = 0;
int status = -1; //1 if player1, 2 if player2 and 0 if drawn
Player[] player;
color white= color(255,255,255);
color RED = color(255,0,0);
color BLUE = color(0,0,255);
color GREEN = color(0,255,0);
boolean ex;
Square[] s, add;
void setup()
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
void draw()
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

void mousePressed()
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
     color cc = s[i].isCaptured(s);
     
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

void queue()
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

Player getPlayer(color playerColor)
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
int getPlayerNumber(Player pl)
{
  if(player[0] == pl)
    return 1;
  else
    return 2;
}
boolean noWhiteSpaces()
{
  for(int i=0; i<s.length; i++)
  {
    if( s[i].getColor() == white)
      return false;
  }
  return true;
}
int greenCaptured()
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
