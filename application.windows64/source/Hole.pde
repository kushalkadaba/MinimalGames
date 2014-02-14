class Hole
{
  final int radius = 10;
  color c;
  float x, y;
  boolean isGreen, isLit;
 
  public Hole(int gstart, int gend)
  {  
    int p = int(random(gstart, gend));
    
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
  public void setColor(color cl){
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
