class Wall
{
  //private float elasticity;
  private color c;
  private float x1, y1, wide, high;
  private float fx,fy;
  private int movable;
  private int count;
  private boolean isLit;
  
  public Wall(float x1, float y1, float w, float h, color cr, int m){
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
