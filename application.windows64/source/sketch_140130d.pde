  int ballrad, time;
  int direction;
  Hole []h, h1, h2,h3,h4;
  Wall []w;
  color red = color(255,0,0);
  int gstart[] = {2,33,49,79},
        gend[] = {31,46,76,91};
  int m, num_of_walls;
  float xpos, ypos, xposd, yposd, wx, wy;
  float sp = 50;  //optimum speed
  
void setup(){
  size(640,320);
  textSize(30);
  textAlign(CENTER);
  newStart();
}
void newStart(){
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
  int gr = int(random(4));
  h[gr].setColor(color(0,255,0));
  
  

  rectMode(CORNER);  
  ellipseMode(RADIUS);
}

void draw(){

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
    xpos = lerp(xpos, xposd, 0.1);
    ypos = lerp(ypos, yposd, 0.1);
    
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
void keyPressed(){
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

Wall[] drawWalls(int num, Wall[] w){
  
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
      index = int(random(0,lenx));
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
      
      index = int(random(0,leny));
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
      
      wide = int(random(50,100));
      high = 5;
      
      index = int(random(3));
      m = mov[index];
      w[wcount] = new Wall(x, y, wide, high, red, 0);
      wcount += 1;
  }
  
  for(int i=0; i<num/2; i++){
      index = int(random(0,lenx));
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
      
      index = int(random(0,leny));
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
      
      high = int(random(50,100));
      wide = 5;
      
      index = int(random(3));
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



