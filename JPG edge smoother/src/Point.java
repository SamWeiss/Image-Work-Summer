//import java.math.*;
public class Point {
	//this is a short class for storing and returning points
	//in their own objects, with the goal being simplification in
	//other classes that must access points
	int x;
	//x coordinate
	int y;
	//y coordinate
	int v;
	//rbg value (optional)
	int r;
	int g;
	int b;
	boolean chosen = false;
	//a multi use variable
	int edge = 0;
	//an occasionally used variable that is used to denote which edge is a border (ranges from 1 to 15, see Sam for what means what)
	int segment = -1;
	//this is a variable that is used in the edge knitting method to store where the point is coming from
	boolean start = false;
	//this variable is essentially the same as the one above it, but this one is used to record whether the point is at the start or
	//end of the given edge fragment
	public Point(int xval, int yval){
		x=xval;
		y=yval;
		chosen = false;
	}
	public Point(int xval, int yval, int vval){
		x=xval;
		y=yval;
		v=vval;
		r =((vval >> 16) & 0xff);
		g =((vval >> 8) & 0xff);
		b =((vval >> 0) & 0xff);
		chosen = false;
	}
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	public void setx(int a){
		x=a;
	}
	public void sety(int a){
		y=a;
	}
	public int getv(){
		return v;
	}
	public int setv(int value){
		v = value;
		return v;
	}
	
	public void setEdge(int e){
		edge = e;
	}
	public int getEdge(){
		return edge;
	}
	public void setChosen(boolean c){
		chosen = c;
	}
	public boolean getChosen(){
		return chosen;
	}
	public boolean getStart(){
		return start;
	}
	public int getSegment(){
		return segment;
	}
	public void setSegment(int  i){
		segment = 1;
	}
	public void setStart(boolean i){
		start = i;
	}
	public int getr(){
		return r;
	}
	public int getg(){
		return g;
	}
	public int getb(){
		return b;
	}
	public void setr(int a){
		r=a;
	}
	public void setg(int a){
		g=a;
	}
	public void setb(int a){
		b=a;
	}
	public void setrAll(){
		g=r;
		b=r;
	}
	public void setrgb(int a){
		r=a;
		g=a;
		b=a;	
	}
	public int rgbToInt(){
		v=0;
		v+=((r << 16));
		v+=((g <<  8));
		v+=((b <<  0));
		return v;
	}
	public double getDistance(LPoint a, LPoint b){
		//LPoint c = new LPoint(-99,-99);
		double x1 = b.getDX();
		double x2 = a.getDX();
		double y1 = b.getDY();
		double y2 = a.getDY();
		return Math.sqrt((  ((x1-x2)*(x1-x2)) + ((y2-y1)*(y2-y1))) );
	}
	public double getDistance(Point a, Point b){
		double x1 = b.getx();
		double x2 = a.getx();
		double y1 = b.gety();
		double y2 = a.gety();
		return Math.sqrt((  ((x1-x2)*(x1-x2)) + ((y2-y1)*(y2-y1))) );
	}
}
