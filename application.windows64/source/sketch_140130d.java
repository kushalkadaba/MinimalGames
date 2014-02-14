import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_140130d extends PApplet {

  int ballrad, time;
  int direction;
  Hole []h, h1, h2,h3,h4;
  Wall []w;
  int red = color(255,0,0);
  int gstart[] = {2,33,49,79},
        gend[] = {31,46,76,91};
  int m, num_of_walls;
  float xpos, ypos, xposd, yposd, wx, wy;
  float sp = 50;  //optimum speed
  
public void setup(){
  size(640,320);
  textSize(30);
  textAlign(CENTER);
  newStart();
}
public void newStart(){
  ballrad = 35;
  time = 0;
  direction = 1;
  num_of_walls = 10;
  xpos = width/2;
  ypos = height/2;
  xposd = xpos;
  yposd = ypos;
/*goalx = width/2;
  goaly = height/9;
*/
//Create 4 holes in 4 directions

  h = new Hole[4];
  
  for( int i=0; i<4; i++)
  {
    h[i] = new Hole(gstart[i],gend[i]);
  }

//draw random walls  
  w = new Wall[num_of_walls];
  w = drawWalls(num_of_walls,w);
  
//Determine which hole should be green
  int gr = PApplet.parseInt(random(4));
  h[gr].setColor(color(0,255,0));
  
  

  rectMode(CORNER);  
  ellipseMode(RADIUS);
}

public void draw(){

  background(0, 100);
  noStroke();
/*fill(c);
  ellipse(goalx, goaly, goalr, goalr);
*/ 
// draw the holes
  for(int i=0; i<4;i++){
    h[i].display();
  }
  
  
// draw the walls
  for(int i = 0; i<num_of_walls; i++){
    w[i].display();
    if (w[i].getCount() == 0)
    {
      if(w[i].inLight(xpos,ypos,ballrad))
        w[i].setIsLit(true);
      else
        w[i].setIsLit(false);
      
      if(w[i].isColliding(xpos, ypos, 15))
      {
        direction = w[i].collide(xpos, ypos, direction);
      }
    }
  }
  
  for(int i=1; i<=ballrad; i++){
    float grad = 100/i;
    fill(255,255,0,grad);
//    fill(255,255,0);
    ellipse(xpos, ypos,i,i);
  }
    xpos = lerp(xpos, xposd, 0.1f);
    ypos = lerp(ypos, yposd, 0.1f);
    
    if (xpos < 0)
      newStart();
    else if(xpos > 640)
      newStart();
   
    if(ypos<0)
      newStart();
    else if (ypos > 320)
      newStart();

//Check if the ball is close to the hole
    for(int i=0; i<4; i++)
    {
     if(h[i].inLight(xpos,ypos,ballrad)){
       h[i].setIsLit(true);
       if(h[i].isIn(xpos, ypos)){
         xposd = h[i].getX();
         yposd = h[i].getY();
         ballrad = 0;
         if(h[i].reachedHole()){
           fill(0, 102, 153);
           if ( time == 0)
            time = millis()/1000;
           text("You took "+time+" seconds to reach the goal", width/2, height/2);
         }
         else{
           newStart();
         }
       }
     }   
     else
       h[i].setIsLit(false);
    } 
/*    
    float dx = xpos - goalx;
    float dy = ypos - goaly;
    
    float dist = sqrt(dx*dx + dy*dy);
    if(dist < (ballrad + goalr)){
      c = color(0,255,0);
      // try later
        float dx1 = dist/2;
        float a1  = acos(dist/(2*ballrad));        //float a1 = atan2(dy1,dx1);
        pushMatrix(); 
          translate(xpos,ypos);
          rotate(a1);
          beginShape();
            fill( 255, 255, 255);
            noStroke();
            arc(xpos,ypos,ballrad,ballrad,0,PI);
          endShape();
        popMatrix();
      */  
  /*  }
    else 
      c = color (0,0,0);
    if (dist < goalr){
      xposd = goalx;
      yposd = goaly;
      ballrad = 0;
    }
*/
  
  }
/* too boring to click all the time
void mousePressed(){
  float dx = mouseX - xpos;
  float dy = mouseY - ypos;
  
  float distance = sqrt(dx*dx + dy*dy);
  
  if (distance < ballrad) {
    
    float a = atan2(dy,dx);
    
    xposd = xpos - (sp*cos(a));
    
    yposd = ypos - (sp*sin(a));
  
  }
}
 */
public void keyPressed(){
// Change direction
/*  int sec = int(millis()/1000);
  if(sec%5 == 0)
  {
    direction = direction * -1;
    while(millis()/1000 != sec+1){
    }
  }
*/  
  if(key == CODED){
    if(keyCode == UP){
      yposd = ypos - (sp * direction);  
    }
    if(keyCode == DOWN){
      yposd = ypos + (sp * direction);
    }
    if(keyCode == LEFT){
      xposd = xpos - (sp * direction);
    }
    if(keyCode == RIGHT){
      xposd = xpos + (sp * direction);
    }
  }
}

public Wall[] drawWalls(int num, Wall[] w){
  
  int lenx, leny, index, count = 0, wcount = 0;
  int j, vicinity = 2,swap;
  int[] mov = {1,0,-1};
  float x, y, wide, high;
  Wall []temp1, temp2;
  int[] randx = new int[300];
  int[] randy = new int[170];
  
  for( int t = 50; t<245; t++)
  {
   randx[count++] = t;
  }
  for( int t = 395; t<500; t++)
  {
   randx[count++] = t;
  }
  count = 0;
  for( int t = 30; t<120; t++)
  {
   randy[count++] = t;
  }
  for( int t = 200; t< 280; t++)
  {
   randy[count++] = t;
  }
  
  lenx = randx.length;
  leny = randy.length;
  
  for(int i=0; i<num/2; i++){
      index = PApplet.parseInt(random(0,lenx));
      x = randx[index];
      
/* doesn't work -> out of bounds exception     
      //to avoid walls very close to one another
      swap = 2;
      for(j=1; j<=vicinity;j++){
        randx[index - j] = randx[lenx - swap];
        swap++;
        randx[index + j] = randx[lenx - swap];
        swap++;
      }
*/  
      randx[index] = randx[lenx - 1];
      lenx = lenx - 1;
      
      index = PApplet.parseInt(random(0,leny));
      y = randy[index];
     
/*      //to avoid walls very close to one another
      swap = 2;
      for(j=1; j<=vicinity;j++){
        randy[index - j] = randy[leny - swap];
        swap++;
        randy[index + j] = randy[leny - swap];
        swap++;
      }
*/
      randy[index]= randy[leny-1];
      leny = leny - 1;
      
      wide = PApplet.parseInt(random(50,100));
      high = 5;
      
      index = PApplet.parseInt(random(3));
      m = mov[index];
      w[wcount] = new Wall(x, y, wide, high, red, 0);
      wcount += 1;
  }
  
  for(int i=0; i<num/2; i++){
      index = PApplet.parseInt(random(0,lenx));
      x = randx[index];
/*      //to avoid walls very close to one another
      swap = 2;
      for(j=1; j<=vicinity;j++){
        randx[index - j] = randx[lenx - swap];
        swap++;
        randx[index + j] = randx[lenx - swap];
        swap++;
      }
*/      
      randx[index] = randx[lenx - 1];
      lenx = lenx -  1;
      
      index = PApplet.parseInt(random(0,leny));
      y = randy[index];
/*      
      //to avoid walls very close to one another
      swap = 2;
      for(j=1; j<=vicinity;j++){
        randy[index - j] = randy[leny - swap];
        swap++;
        randy[index + j] = randy[leny - swap];
        swap++;
      }
*/
      randy[index] = randy[leny - 1];
      leny = leny - 1;
      
      high = PApplet.parseInt(random(50,100));
      wide = 5;
      
      index = PApplet.parseInt(random(3));
      m = mov[index];
      w[wcount] = new Wall(x, y, wide, high, red, 0);
      wcount ++;
  }
  
//1) spread out walls!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  
  for(int i = 0; i<num; i++)
  {
    x = w[i].getX();
//    System.out.println(x);
  }
  return w;
} 



class Hole
{
  final int radius = 10;
  int c;
  float x, y;
  boolean isGreen, isLit;
 
  public Hole(int gstart, int gend)
  {  
    int p = PApplet.parseInt(random(gstart, gend));
    
    if(p > 1 && p < 32){
      y = 20;
      x = (p - 1)*2*radius + radius + 20;
    }
    else if(p> 32 && p<47){
      x = 610;
      y = (p - 32)*2*radius + radius+ 20;
    }
    else if(p>48 && p<76){
      y = 290;
      x = 620 - ((p-48)*2*radius);
    }
    else if(p>77 && p<92){
      x = 30;
      y = 310 - ((p-77)*2*radius);
    }
    
    c = color(255,0,0);
    isGreen = false;
/*
// create holes in all four directions
    gstart = gend + 2;
    if (gend == 31)
      gend = 46;
    else if (gend == 46)
      gend = 77;
    else if (gend == 77)
      gend = 91;
*/
  }
  
  public float getX(){
    return x;
  }
  
  public float getY(){
    return y;
  }
  public boolean reachedHole(){
    return isGreen;
  }
  public boolean isIn(float xpos, float ypos){
    float dx = xpos - x;
    float dy = ypos - y;
    
    float dist = sqrt(dx*dx + dy*dy);
    if(dist < radius)
      return true;
    
    return false;
  }
  public void setColor(int cl){
    c = cl;
    if(c == color(0,255,0))
      isGreen = true;
  }
  public void display(){
    noStroke();
    
    if(isLit)
      fill(c);
    else
      fill(0);
      
    ellipse(x,y,radius,radius);
  }
  
  public void setIsLit(boolean l){
    isLit = l;
  }
  
  public boolean inLight(float xpos, float ypos, int r){
        
    float dx = xpos - x;
    float dy = ypos - y;
    
    float dist = sqrt(dx*dx + dy*dy);
    if(dist < (r + radius))
      return true;
    
    return false;
  }
}
class Wall
{
  //private float elasticity;
  private int c;
  private float x1, y1, wide, high;
  private float fx,fy;
  private int movable;
  private int count;
  private boolean isLit;
  
  public Wall(float x1, float y1, float w, float h, int cr, int m){
    this.x1 = x1;
    this.y1 = y1;
    wide = w;
    
    high = h;
    //elasticity = e;
    c = cr;
    movable = m;
    if(m == 1)
    {
      fx = x1;
      fy = y1;
    }
    else if(m == -1)
    {
        fx = x1 + wide;
        fy = y1 + high; 
    }
    count = 0;
  }
  
  public void display(){
    if(isLit)
      fill(c);
    else
      fill(0);
      
    rect(x1,y1,wide,high);
  }
  
  public int collide(float x, float y, int sp)
  {
    switch (movable)
    {
      case 0:
        sp = sp * -1;
        count = count + 1;
      break;
    /*  default:
        float dist = sqrt(((x-fx)*(x-fx))+((y-fy)*(y-fy)));
        float acc = map(dist,0,70,0,3);
        sp = sp * acc; */
    }
    
    return sp;
  }
  
  public void setIsLit(boolean l){
    isLit = l;
  }
  
  public boolean inLight(float xpos, float ypos, int r){
        
    if(isColliding(xpos,ypos,(float)r))
      return true;
    
    return false;
  }
  
  public boolean isColliding(float xpos, float ypos, float r)
  {  
    float dist;
    
    if (wide != 5){
      dist = abs(ypos - this.y1);
      if (dist <= r && (xpos > this.x1 && xpos < (this.x1+wide)))
        return true;
    }
    else {
      dist = abs(xpos - this.x1);
      if (dist <= r && (ypos > this.y1 && ypos < (this.y1+high)))
        return true;
    }
      
    return false;
  }
  public int getCount(){
    return count;
  }
  public float getX(){
    return x1;
  }
 public float getY(){
  return y1;
 }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_140130d" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
