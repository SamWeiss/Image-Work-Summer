
public class LPoint extends Point{

	public LPoint(int xval, int yval, int vval) {
		super(xval, yval, vval);
		if (dX>0 && dY >0){
			Quad = 1;
		}else if (dX>0 && dY <0){
			Quad = 3;
		}else if (dX<0 && dY >0){
			Quad = 2;
		}else if (dX<0 && dY <0){
			Quad = 4;
		}else{ Quad = 0;}
	}
	public LPoint(int xval, int yval) {
		super(xval, yval);
		if (dX>0 && dY >0){
			Quad = 1;
		}else if (dX>0 && dY <0){
			Quad = 3;
		}else if (dX<0 && dY >0){
			Quad = 2;
		}else if (dX<0 && dY <0){
			Quad = 4;
		}else{ Quad = 0;}
	}
	public LPoint(double xval, double yval) {
		super((int)xval, (int)yval);
		if (dX>0 && dY >0){
			Quad = 1;
		}else if (dX>0 && dY <0){
			Quad = 3;
		}else if (dX<0 && dY >0){
			Quad = 2;
		}else if (dX<0 && dY <0){
			Quad = 4;
		}else{ Quad = 0;}
		dX = xval;
		dY = yval;
	}
	double dX = 0;
	double dY = 0;
	int Quad;
	public void setQuad(){
		if (dX>0 && dY >0){
			Quad = 1;
		}else if (dX>0 && dY <0){
			Quad = 3;
		}else if (dX<0 && dY >0){
			Quad = 2;
		}else if (dX<0 && dY <0){
			Quad = 4;
		}else{ Quad = 0;}
	}
	public double getDX(){
		return dX;
	}
	public double getDY(){
		return dY;
	}
	public void setDX(double d){
		dX = d;
	}
	public void setDY(double d){
		dY = d;
	}public int getQuad(){
		return Quad;
	}
	public void approximate(){
		x = (int)dX;
		y = (int)dY;
	}
}
