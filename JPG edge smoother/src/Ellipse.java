import java.util.ArrayList;
//import java.util.Collection;

//import cs1.Keyboard;
//import cs1.Keyboard;
public class Ellipse {
	//double a;
	ArrayList<LPoint> points = new ArrayList<LPoint>();
	ArrayList<Double> cAngle = new ArrayList<Double>();
	double delta;
	private int pGrace = Integer.MAX_VALUE;
	public Ellipse(){
	}
	public ArrayList<LPoint> getEllipse(int x, int y,double a, double b){
		//System.out.println("into rasterizer");
		//code goes here
		points = new ArrayList<LPoint>();
		cAngle = new ArrayList<Double>();
		cAngle.add(0.);
		//boolean added = false;
		int lX = -99;
		long nX;
		long nY;
		int lY = -99;
		double t = 0;
		double pi =  3.1415926535;
		while (t< 2* pi){
			nX = (Math.round((Math.cos(t)*a)*(x/2))) + (x/2);
			nY = (Math.round((Math.sin(t)*b)*(y/2))) + (y/2);
			if((nX != lX) || (nY != lY)){
				points.add(new LPoint((int)nX,(int)nY));
				lX = (int) nX;
				lY = (int) nY;
				cAngle.add(t);
				//added = true;
			}
			//if(added){
			//	t +=.2 * (cAngle.get(cAngle.size()-1) - cAngle.get(cAngle.size()-2));
			//	}else{
			//			t+= (double)( 1.0/(double)((double)x + (double)y));
			//			}
			t+= .002;
			//System.out.println("Graphing" + t);
		}
		//System.out.println(points.size() + " / 3142");
		return points;
	}
	public double getEnergy(ArrayList<LPoint> E, ArrayList<ArrayList<Point>> P, Point o, double g, double decay, double elasticity, double a, double b, int threshold){
		// variable rundown: E is the ArrayList of ellipse points, A is the ArrayList of area points, o is the origin, can be placed anywhere, g is the gravity
		// constant, as of right now, all points are attracted equally, regardless of distance, decay is the decay in strength of the area point repulsion
		// elasticity is the resistance to deformation of the shape
		//System.out.println("into E calc");
		double e = 0; int k;
		for(int i = 0; i < E.size(); i+= 4){
			//double gravity = g;//may not keep this value constant
			e+=g*o.getDistance(o,E.get(i));
			//for(int j = 0; j < A.size(); j ++){
			//e+= (1/(  Math.pow(o.getDistance(E.get(i), A.get(j)), decay)));
			//} //This was some old code that would do repulsion calculations on the entire area, which is really good, but really
			//really slow, the next edit should be below
			for(int j = 1; j <= 120; j++){
				k = j;
				if (j >60){k = j +1;}
				if (P.get((k%11)).get((k-((k%11)))/11).getv() == threshold ){
					e+= (1/(  Math.pow(o.getDistance(E.get(i), P.get((k%11)).get((k-((k%11))))), decay)));
				}
			}
		}
		//e+= (elasticity*(Math.sqrt((a*a - b*b)/(a*a))));
		//System.out.println("out of E calc");
		return e; // may want to rewrite this so it also does point gathering and restructure but it's a rough first pass so sue me
	}
	public double calcEnergy(ArrayList<LPoint> E, ArrayList<ArrayList<Point>> P, int threshold){
		for(int i = 0; i<E.size(); i++){E.get(i).setx(Math.round((float)E.get(i).getDX()));E.get(i).sety(Math.round((float)E.get(i).getDY()));}
		double energy = 0;
		for(int i = 1; i < E.size()-1; i ++){
			energy += 80.0*E.get(i).getDistance(E.get(i), E.get(i - 1));
			energy += Math.abs(.2*(E.get(i).getDistance(E.get(i), E.get(i - 1)) - E.get(i).getDistance(E.get(i + 1), E.get(i))));
			//System.out.println( E.get(i).getx() + " " + E.get(i).gety()  + " " + i + " "+ E.get(i).getDX()  + " " + E.get(i).getDY()  + " " );
			if((P.get(E.get(i).getx()).get( E.get(i).gety())).getv() == threshold){
				energy+= 20.;
			}
			if((E.get(i).getDistance(E.get(i), E.get(i - 1))) > 3){
				energy += ( 200*E.get(i).getDistance(E.get(i), E.get(i - 1)));
			}
		}
		energy += E.get(1).getDistance(E.get(0), E.get(E.size() - 1));
		return energy;
	}
	public ArrayList<Point> expandToFill(ArrayList<ArrayList<Point>> P, LPoint seed, int threshold){
		ArrayList<Point> area = new ArrayList<Point>();
		//ArrayList<Point> areaAdds = new ArrayList<Point>();
		//Collection<Point> c = null;
		boolean hit = false;
		area.add(seed);
		//stage one - expand until it hits something (just expanding in a rectangle or whatever
		// I need to restructure this section because it was spinning off object like crazy
		area.add(seed);
		int loopvar = 0;
		while(!hit){
			loopvar ++;
			if(P.get(seed.getx() +loopvar).get( seed.gety()).getv() == threshold){
				area.add(new Point(seed.getx()+loopvar, seed.gety()));
			}else{hit = true;}
			if(P.get(seed.getx() -loopvar).get( seed.gety()).getv() == threshold){
				area.add(new Point(seed.getx()-loopvar, seed.gety()));
			}else{hit = true;}
			if(P.get(seed.getx()).get( seed.gety()+loopvar).getv() == threshold){
				area.add(new Point(seed.getx(), seed.gety()+loopvar));
			}else{hit = true;}
			if(P.get(seed.getx()).get( seed.gety()-loopvar).getv() == threshold){
				area.add(new Point(seed.getx(), seed.gety()-loopvar));
			}else{hit = true;}
			System.out.println(area.size());

			//			for(int i = 0; i <area.size(); i++){
			//				if(P.get(area.get(i).getx() +1).get( area.get(i).gety()).getv() != threshold){
			//					area.add(new Point(area.get(i).getx()+1, area.get(i).gety()));
			//				}else{hit = true;}
			//				if(P.get(area.get(i).getx() -1).get( area.get(i).gety()).getv() != threshold){
			//					area.add(new Point(area.get(i).getx()-1, area.get(i).gety()));
			//				}else{hit = true;}
			//				if(P.get(area.get(i).getx()).get( area.get(i).gety() +1).getv() != threshold){
			//					area.add(new Point(area.get(i).getx(), area.get(i).gety() +1));
			//				}else{hit = true;}
			//				if(P.get(area.get(i).getx()).get( area.get(i).gety() -1).getv() != threshold){
			//					area.add(new Point(area.get(i).getx(), area.get(i).gety() -1));
			//				}else{hit = true;}
			//				removeDuplicates(area);
			//			}
			//			for(int i = 0; i< area.size(); i++){
			//				areaAdds.add(new LPoint(area.get(i).getx()-1, area.get(i).gety()));
			//				areaAdds.add(new LPoint(area.get(i).getx()+1, area.get(i).gety()));
			//				areaAdds.add(new LPoint(area.get(i).getx(), area.get(i).gety()-1));
			//				areaAdds.add(new LPoint(area.get(i).getx(), area.get(i).gety()+1));
			//			}
			//			for(int i = 0; i<areaAdds.size(); i++){
			//				System.out.println( " " + areaAdds.size() + " " + area.size());
			//				if((P.get(areaAdds.get(i).getx()).get( areaAdds.get(i).gety())).getv() != threshold){
			//					area.add(areaAdds.get(i));
			//				}else{hit = true;System.out.println("hit edge");}
			//			}
			//areaAdds.removeAll(c);
		}
		removeDuplicates(area);
		//return area;
		//stage two - start doing energy stuff,
		//so the question is what do I do here? I have a few ideas, but let's start off by identifying what we have:
		//we have the image itself, stored in P
		//we have a seed point and a rough rectangle around it that is in contact with the edge at at least one point
		//we have the threshold value
		// the plan is to not move points, only add points. so from here on out, we are going to be adding a point, then deciding if it helps or hinders the energy value
		//of the shape as a whole
		//so now the question is: what decides energy? one idea that I had that would be quick and dirty would be simply do area and border length calculations
		//but here's a question about that method, do we take the length in number of border point? or order them and then calculate the distance?
		//earlier border ordering methods had ... interesting results so at this time the border length will be in points
		//but this poses a problem: any border point that is added as a test will be kicked out because it increases the border without increasing the area
		//this ca be solved by taking a grid and then try all possible arrangements of points
		//either that or I can place some positive weight on being adjacent to a value below the threshold
		removeDuplicates(area);
		double e1 = 2;
		double e2 = 1;
		boolean added = true;
		while(e1>e2 && added){
			added = false;
			e1 = calcEnergyETF(area, P, threshold);
			added = addPointETF(area, P, threshold);
			e2 = calcEnergyETF(area, P, threshold);
		}
		return area;
	}
	public double calcEnergyETF(ArrayList<Point> area, ArrayList<ArrayList<Point>> p, int threshold){
		//this is a method that is used to calculate the "energy" of a shape that is in the expandToFill method. it had a weight for the ration of area to border length
		// and it also has a weight for the total size of the shape. The shape should expand until the penalty for border length overwhelms the bonus for area
		double energy = 0;
		//		int borderLength = 0;
		for(int i = 0; i< area.size(); i++){
			//double edgeVal = (double)p.get(area.get(i).getx()-1).get(area.get(i).gety()).getv() + (double)p.get(area.get(i).getx()+1).get(area.get(i).gety()).getv()
			//		+ (double)p.get(area.get(i).getx()).get(area.get(i).gety()-1).getv() + (double)p.get(area.get(i).getx()).get(area.get(i).gety()+1).getv();
			//this area is fun, in the Compute class I spent half a page doing what this summation does; essentially, if the four values around the point don't average
			//out to the threshold value,then you know that the given point is a border point. This only tells you that one of the points is on the border, not which one
			//which isn't ideal, but it's much faster than the other code I was using
			//			if(edgeVal != ((double)threshold*4.0)){
			//				borderLength++;System.out.println("location 2");
			//			}else{System.out.println("location 1");}
			//System.out.println(area.get(i).getx() + " " + area.get(i).gety());
			//			if(p.get(area.get(i).getx()-1).get(area.get(i).gety()).getv() != (threshold)
			//					|| p.get(area.get(i).getx()+1).get(area.get(i).gety()).getv() != (threshold) 
			//					|| p.get(area.get(i).getx()).get(area.get(i).gety()-1).getv() != (threshold) 
			//					|| p.get(area.get(i).getx()).get(area.get(i).gety()+1).getv() != (threshold)){
			//				borderLength++;//System.out.println("location 2");
			//			}else{/*System.out.println("location 1");*/}
			if(p.get(area.get(i).getx()).get(area.get(i).gety()).getv() != (threshold)){
				energy+= 1.;
			}
		}
		//System.out.print("location 1 duplicates: ");
		if(containsDuplicates(area)){
			energy += 10000000000000.;
		}
		energy+= (  ((double)borderID(area).size() / ((double)area.size()))) + (1000.0/(double)area.size());
		//System.out.println(energy + " " + borderLength + " " + area.size());
		return energy;
	}
	public boolean addPointETF(ArrayList<Point> area,  ArrayList<ArrayList<Point>> p, int threshold){
		//this is the workhorse of the second half of the expandToFill method, it tries to add points in various locations, and if the added point minimizes energy, then 
		//it is added and the energy is updated. It relies heavily on the above method, and this specific implementation only supports moving one point at a time.
		//at the bottom of this class there is a section of text that contains an out of date method that added all possible configurations of an 11 by 11 grid, which
		//is quite a few combinations and therefore took way way way too much code and computing time (we're talking for loops that needed to be at lease 22 layers deep)
		//but I might try and bring something like that back later
		boolean added = false;
		double OriginalE = calcEnergyETF(area, p, threshold);
		double NewE;
		ArrayList<Point> temp = new ArrayList<Point>();
		for (int i = 0; i < area.size(); i ++){
			temp.add(area.get(i));
			//I know this is slightly silly, to go through and copy every element of the ArrayList individually, but I blame java here, why can't I just duplicate the 
			//object outright?anyway, I think the second ArrayList still points to the same objects that are in the area ArrayList, but it's not that important, I
			//never do anything with those values, but I want to preserve the original list so I have to go and grab every single object
		}
		//ArrayList<Point> best = new ArrayList<Point>();
		//int areasize = 1;
		//int areasizeL = 0;
		for(int j = 0; j < area.size();j++){
			//areasizeL =areasize;
			//areasize = area.size();
			removeDuplicates(area);
			removeDuplicates(temp);
			//temp.add(new Point(-99,-99));
			for(int k = 0 ; k < 5; k ++){
				for(int l = 0; l < 5; l++){
					//I do this a few times so might as well explain it, the first for loop, the one with k as a variable is serving as the delta x, while the second for
					//loop, the one with l as a variable, is serving as the delta y variable, as they increase it works an 11*11 grid, which is centered at the point
					//in question by the code right below, which centers the values and takes the original point values
					temp.add( new Point(temp.get(j).getx() - 2 + k, temp.get(j).gety() + 2 - l));
					NewE = calcEnergyETF(temp, p, threshold);
					if(NewE < OriginalE){
						OriginalE = NewE;
						added = true;
						//for(int m = 0; m < temp.size();m++){
						area.add( temp.get(temp.size()-1));
						//temp.remove(temp.size()-1);
						//}
						//System.out.println("added a point");
						// so now it's really really hard to add points, lets go re-examine energy
					} else {temp.remove(temp.size()-1);}
				}
			}
			removeDuplicates(area);
			removeDuplicates(temp);
			System.out.println(area.size());
		}
		return added;
	}
	public ArrayList<Point> borderID(ArrayList<Point> a){
		//System.out.print("location 2 duplicates: ");
		containsDuplicates(a);
		//Keyboard.readInt();
		ArrayList<Point> border = new ArrayList<Point>();
		int borderI= 0;
		for(int i = 0; i < a.size(); i ++){
			for (int j = 0; j <a.size(); j++){
				//System.out.println(a.get(i).getx() +  " " + a.get(i).gety() + " " + a.get(j).getx() + " " + a.get(j).gety());
				if((a.get(i).getx() == a.get(j).getx()+1 && a.get(i).gety() == a.get(j).gety() )||
						(a.get(i).getx() == a.get(j).getx()-1 && a.get(i).gety() == a.get(j).gety() )||
						(a.get(i).gety() == a.get(j).gety()-1 && a.get(i).getx() == a.get(j).getx() )||
						(a.get(i).gety() == a.get(j).gety()+1 && a.get(i).getx() == a.get(j).getx() )){
					borderI++;
				}
			}
			if(borderI !=4){
				border.add(a.get(i));
			}
			//System.out.println(borderI);
			//Keyboard.readInt();
			borderI = 0;
		}
		//System.out.println("b: " +border.size() + "\na: " + a.size());
		return border;
	}
	public ArrayList<Point> gapFill(ArrayList<Point> a, boolean debug){
		//so in older versions of the LSet code, especially with the smoother, I ran into issues where pixels are more than 1 pixel apart, which makes the results much
		//harder to use, so this simple code writes pixels between points, it should bisect the segment between them perfectly, but as per usual there is the issue of
		//roundoff and java handling casting
		int lastSize = -99;
		boolean moved = true;
		while(moved && lastSize != a.size()){
			lastSize = a.size();
			moved = false;
			for(int i = 0; i < a.size()-1;i++){
				if(a.get(i).getDistance((Point)a.get(i), (Point)a.get(i+1)) > 1.5){
					//System.out.println(a.get(i).getDistance((Point)a.get(i), (Point)a.get(i+1)));
					a.add(i+1,new LPoint((((double)a.get(i+1).getx() + (double)a.get(i).getx())/2.0), (((double)a.get(i+1).gety() + (double)a.get(i).gety())/2.0)));
					i++;
					moved = true;
				}
				//System.out.println(a.size() +  " " + i + " "  +a.get(i+1).getDistance(a.get(i+1), a.get(i)) );
			}
			removeDuplicates(a);
			if(debug){System.out.println("the current, non duplicate ArrayList size is " + a.size());}
		}
		return a;
	}
	public ArrayList<LPoint> gapFillL(ArrayList<LPoint> a, boolean debug){
		//if you'll notice, this looks VERY similar to the code above it, in fact, it is the same code! well would you look at that, but wait! there's a twist, this one
		//has an 'L' at the end of it, meaning that this code works with LPoints. Why I need a separate method for this I do not know, any method that applies to Point 
		//objects should apply to LPoint objects, but the minute that you put them in an ArrayList, they seem to lose their association, so at first I thought I could 
		//just copy the first method without changing the name, but wrong again, Java considered them two different methods with the same name and parameters, with
		//only the generic ArrayList<E> as their parameter *grumbles discontentedly*


		//well this method is just a train wreck, it needs to be rebuilt from the ground up, and I'm not sure even that will work
		//

		//as of august 9th this should actually work, turns out math.sqrt isn't terribly exact as well as it was using doubles, when I wanted int values
		for(int i = 0 ; i < a.size(); i ++){
			a.get(i).approximate();
		}
		int lastSize = -99;
		boolean moved = true;
		while(moved && lastSize != a.size()){
			lastSize = a.size();
			moved = false;
			for(int i = 0; i < a.size()-1;i++){
				if(a.get(i).getDistance((Point)a.get(i), (Point)a.get(i+1)) > 1.5){
					//System.out.println(a.get(i).getDistance((Point)a.get(i), (Point)a.get(i+1)));
					a.add(i+1,new LPoint((((double)a.get(i+1).getx() + (double)a.get(i).getx())/2.0), (((double)a.get(i+1).gety() + (double)a.get(i).gety())/2.0)));
					i++;
					moved = true;
				}
				//System.out.println(a.size() +  " " + i + " "  +a.get(i+1).getDistance(a.get(i+1), a.get(i)) );
			}
			removeDuplicatesL(a);
			if(debug){System.out.println("the current, non duplicate ArrayList size is " + a.size());}
		}
		return a;
	}
	public ArrayList<LPoint> movePoints(ArrayList<LPoint> E, ArrayList<ArrayList<Point>> P, int threshold, int i, double resolution){
		LPoint original = E.get(i); LPoint c = original;
		LPoint d = new LPoint(c.getDX(), c.getDY());
		//double originalE = calcEnergy(E,P,threshold);
		ArrayList<LPoint> tempPoints = new ArrayList<LPoint>();
		for(double j = 0; j < 11; j ++){
			for (double k = 0; k < 11; k++){
				LPoint temp = new LPoint((d.getDX() -1.000000000000000*(resolution*5.0) + (1.0000000000000*resolution)*j),
						d.getDY() -1.00000000000000*(resolution*5.0) + 1.000000000000000*k*resolution);
				tempPoints.add(temp);
				//System.out.println(temp.getDX() +" " + temp.getDY() + " " + j + " " + k + "  ");
			} 
		}
		//Keyboard.readInt();
		double lowE = Double.MAX_VALUE;
		int lowEIndex = -1;
		for(int j = 0; j < tempPoints.size();j ++){
			E.set(i, tempPoints.get(j));
			double tempE = calcEnergy(E,P, threshold);
			if(tempE< lowE){
				lowE = tempE;
				lowEIndex = j;
			}
			//System.out.println(tempE + " " + j + " " + lowE + "  "  + E.get(i).getx() + " " + E.get(i).gety());

		}
		//System.out.println(lowEIndex + " " + tempPoints.size());
		E.set(i, tempPoints.get(lowEIndex));
		//Keyboard.readInt();

		//this was my old point moving method, it was faster and more efficient than the above but seemed not to work very well at all
		//		for(int j = 0; j < 10; j ++){
		//			c = original;c.sety(original.gety() + 1 );E.set(i, c);
		//			double e = calcEnergy(E,P,threshold);
		//			int index = 1;
		//			c = original;c.setx(original.getx() + 1 );E.set(i, c);
		//			double e2 = calcEnergy(E,P,threshold);
		//			if(e2<e){e = e2;index = 2;}
		//			c = original;c.sety(original.gety() - 1 );E.set(i, c);
		//			e2 = calcEnergy(E,P,threshold);
		//			if(e2<e){e = e2;index = 3;}
		//			c = original;c.setx(original.getx() - 1 );E.set(i, c);
		//			e2 = calcEnergy(E,P,threshold);
		//			if(e2<e){e = e2;index = 4;}
		//			if (e<originalE){
		//				if (index == 1){
		//					c = original;c.sety(original.gety() + 1 );E.set(i, c);
		//				} else if (index == 1){
		//					c = original;c.setx(original.getx() + 1 );E.set(i, c);
		//				} else if (index == 1){
		//					c = original;c.sety(original.gety() - 1 );E.set(i, c);
		//				} else if (index == 1){
		//					c = original;c.setx(original.getx() - 1 );E.set(i, c);
		//				}
		//				//System.out.println("moved Point, e went from " + originalE + " to " + e);
		//			}
		//}
		//System.out.println("did not move a point");
		return E;
	}
	public ArrayList<Point> removeDuplicates(ArrayList<Point> p){
		//System.out.println(p.size());
		for(int i = 0; i < p.size(); i ++){
			for(int j = 0; j < p.size(); j++){
				if(p.get(i).getx() == p.get(j).getx() && i != j &&p.get(i).gety() == p.get(j).gety()){
					p.remove(j);
					i=0;
					j = 0;
				}
			}
		}

		//System.out.println(p.size());
		return p;
	}
	public boolean containsDuplicates(ArrayList<Point> p){
		//int numD = 0;
		for(int i = 0; i < p.size(); i ++){
			for(int j = 0; j < p.size(); j++){
				if(p.get(i).getx() == p.get(j).getx() && i != j &&p.get(i).gety() == p.get(j).gety()){
					return true;
				}
			}
		}
		//System.out.println(numD);
		return false;

	}
	public ArrayList<LPoint> removeDuplicatesL(ArrayList<LPoint> p){
		//System.out.println(p.size());
		for(int i = 0; i < p.size(); i ++){
			for(int j = 0; j < p.size(); j++){
				if(p.get(i).getx() == p.get(j).getx() && i != j &&p.get(i).gety() == p.get(j).gety()){
					p.remove(j);
					i=0;
					j = 0;
				}
			}
		}
		//System.out.println(p.size());
		return p;
	}
	public double sumForces(ArrayList<LPoint> p){
		//System.out.println("The current net force acting on the shape is: " + delta);
		return delta;
	}
	public LPoint moveToFit(LPoint a, LPoint b){
		LPoint c = b;
		c.sety(b.gety() + 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.setx(b.getx() + 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.sety(b.gety() - 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.setx(b.getx() - 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.sety(b.gety() + 1 );c.setx(b.getx() + 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.sety(b.gety() + 1 );c.setx(b.getx() - 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.sety(b.gety() - 1 );c.setx(b.getx() - 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		c.sety(b.gety() - 1 );c.setx(b.getx() + 1 );
		if(c.getDistance(a, c) <= Math.sqrt(2)){
			return c;
		}
		System.out.println("could not solve with current moveToFit program");
		return b;
	}
	public ArrayList<LPoint> calcForces(ArrayList<LPoint> a, ArrayList<ArrayList<Point>> p, Point o, double g, int threshold, double decay, int h,
			int w, ArrayList<Point> Area, int grace){
		if(grace < pGrace){pGrace = grace;}
		//if(pGrace == 0) {return a;}
		//int k;
		//ArrayList<Integer> hitLocations = new ArrayList<Integer>();
		removeDuplicatesL(a);
		for(int i = 0; i < a.size(); i++){
			a.get(i).setDX((g*(((((double)o.getx()) - (((double)((a.get(i).getx())))))/ (o.getDistance(o,a.get(i)))))));
			a.get(i).setDY((g*(((((double)o.gety()) - (((double)((a.get(i).gety())))))/ (o.getDistance(o,a.get(i)))))));

			if(pGrace<0){
				if(a.get(i).getx() < 511 && a.get(i).gety() < 511 && a.get(i).getx() >0 && a.get(i).gety() >0){
					if((p.get(a.get(i).getx() - 1).get(a.get(i).gety())).getv() == threshold ||
							p.get(a.get(i).getx() + 1).get(a.get(i).gety()).getv() == threshold ||
							p.get(a.get(i).getx()).get(a.get(i).gety() - 1).getv() == threshold ||
							p.get(a.get(i).getx()).get(a.get(i).gety() + 1).getv() == threshold){
						a.get(i).setDX(0);a.get(i).setDY(0);
						//hitLocations.add(i);
					}
				}
			}
			//this needs explaining - sometime you want this to move a pixel or two without bothering itself with point hits, this lets
			//you do that
		}
		pGrace--;

		for(int i = 0; i <a.size(); i++){
			a.get(i).setDX(a.get(i).getx() + a.get(i).getDX());
			a.get(i).setDY(a.get(i).gety() + a.get(i).getDY());
			//sum of force vectors, serves to tell the program when it's done

			//point assignment
			a.get(i).setx(Math.round((float)a.get(i).getDX()));
			a.get(i).sety(Math.round((float)a.get(i).getDY()));


		}
		delta = 0;
		for(int l = 0; l < a.size(); l ++){
			delta += Math.abs((a.get(l).getDX() - (double)a.get(l).getx()));
			delta += Math.abs((a.get(l).getDY() - (double)a.get(l).gety()));
		}


		return a;
	}
	public ArrayList<LPoint> calcForces(ArrayList<LPoint> a, ArrayList<ArrayList<Point>> p, Point o, double g, int threshold, double decay, int h,
			int w, ArrayList<Point> Area){
		//int k;
		//ArrayList<Integer> hitLocations = new ArrayList<Integer>();
		removeDuplicatesL(a);
		for(int i = 0; i < a.size(); i++){
			a.get(i).setDX((g*(((((double)o.getx()) - (((double)((a.get(i).getx())))))/ (o.getDistance(o,a.get(i)))))));
			a.get(i).setDY((g*(((((double)o.gety()) - (((double)((a.get(i).gety())))))/ (o.getDistance(o,a.get(i)))))));
				if(a.get(i).getx() < 511 && a.get(i).gety() < 511 && a.get(i).getx() >0 && a.get(i).gety() >0){
					if((p.get(a.get(i).getx() - 1).get(a.get(i).gety())).getv() == threshold ||
							p.get(a.get(i).getx() + 1).get(a.get(i).gety()).getv() == threshold ||
							p.get(a.get(i).getx()).get(a.get(i).gety() - 1).getv() == threshold ||
							p.get(a.get(i).getx()).get(a.get(i).gety() + 1).getv() == threshold){
						a.get(i).setDX(0);a.get(i).setDY(0);
						//hitLocations.add(i);
					}
				}
			
			//this needs explaining - sometime you want this to move a pixel or two without bothering itself with point hits, this lets
			//you do that
		}
		for(int i = 0; i <a.size(); i++){
			a.get(i).setDX(a.get(i).getx() + a.get(i).getDX());
			a.get(i).setDY(a.get(i).gety() + a.get(i).getDY());
			//sum of force vectors, serves to tell the program when it's done

			//point assignment
			a.get(i).setx(Math.round((float)a.get(i).getDX()));
			a.get(i).sety(Math.round((float)a.get(i).getDY()));


		}
		delta = 0;
		for(int l = 0; l < a.size(); l ++){
			delta += Math.abs((a.get(l).getDX() - (double)a.get(l).getx()));
			delta += Math.abs((a.get(l).getDY() - (double)a.get(l).gety()));
		}


		return a;
	}
	public double computeAngle(ArrayList<LPoint> a, int i){
		double x1 = ((((((ArrayList<LPoint>)a)).get(i-10))).getx());
		double y1 = ((((((ArrayList<LPoint>)a)).get(i-10))).gety());
		double x2 = ((((((ArrayList<LPoint>)a)).get(i))).getx());
		double y2 = ((((((ArrayList<LPoint>)a)).get(i))).gety());
		double x3 = ((((((ArrayList<LPoint>)a)).get(i+10))).getx());
		double y3 = ((((((ArrayList<LPoint>)a)).get(i+10))).gety());
		double A = Math.sqrt( ((x1-x3)*(x1-x3)) + ((y1 - y3 )*(y1 - y3)) );
		double B = Math.sqrt( ((x2-x3)*(x2-x3)) + ((y2 - y3 )*(y2 - y3)) );
		double C = Math.sqrt( ((x1-x2)*(x1-x2)) + ((y1 - y2 )*(y1 - y2)));
		return  Math.acos((((B*B)+(C*C))-(A*A))/(2*B*C));
	}
}
//if(i >10 && i < a.size() - 10 && false){
//double angle = computeAngle(a,i);
//if(angle < 2.967059728){
//	a.get(i).setQuad();
//	int tx = a.get(i).getx();
//	int ty = a.get(i).gety();
//	if(a.get(i).getQuad() == 1){
//		int l = 0;
//		boolean solved = false;
//		while(l<10 && (computeAngle(a,i)< 3)){
//			a.get(i).setx(a.get(i).getx() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() - 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() - 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() + 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).setx(a.get(i).getx() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() + 10);
//		}
//		l =0;
//	} else if(a.get(i).getQuad() == 2){
//		int l = 0;
//		boolean solved = false;
//		while(l<10 && (computeAngle(a,i)< 2.967059728)){
//			a.get(i).setx(a.get(i).getx() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() + 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() - 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() - 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).setx(a.get(i).getx() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() + 10);
//		}
//		l =0;
//	} else if(a.get(i).getQuad() == 3){
//		int l = 0;
//		boolean solved = false;
//		while(l<10 && (computeAngle(a,i)< 2.967059728)){
//			a.get(i).setx(a.get(i).getx() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() - 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() + 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() + 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).setx(a.get(i).getx() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() - 10);
//		}
//		l =0;
//	} else if(a.get(i).getQuad() == 4){
//		int l = 0;
//		boolean solved = false;
//		while(l<10 && (computeAngle(a,i)< 2.967059728)){
//			a.get(i).setx(a.get(i).getx() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() + 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() - 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() + 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).sety(a.get(i).gety() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).sety(a.get(i).gety() - 10);
//		}
//		l =0;
//		while(l<10 && (computeAngle(a,i)< 2.967059728) && !solved){
//			a.get(i).setx(a.get(i).getx() + 1);
//			l++;
//		}
//		if((computeAngle(a,i)> 2.967059728)){
//			solved = true;
//		}else{
//			a.get(i).setx(a.get(i).getx() - 10);
//		}
//		l =0;
//	} else {
//		// well you must be on an axis then, so whatever, i can do this later - lol this is now a huge problem
//	}
//	if(tx == a.get(i).getx() && ty == a.get(i).gety()){
//		System.out.println("we are in the odd case of angle but no change, (x,y) " + a.get(i).getx() + " , " + a.get(i).gety() + " " + a.get(i).getQuad());
//	} else{
//		System.out.println("adjusted a point");
//	}
//} else {System.out.println("did not adjust point");}
//
//}


//hitLocations.add(i,hitLocations.get(i -1) + 3);
//hitLocations.add(i,hitLocations.get(i -1) + 2);
//hitLocations.add(i,hitLocations.get(i -1) + 1);
//a.get(hitLocations.get(i-1) + 1).setDX(0);a.get(hitLocations.get(i - 1 ) + 1).setDY(0);
//a.get(hitLocations.get(i-1) + 2).setDX(0);a.get(hitLocations.get(i - 1 ) + 2).setDY(0);
//a.get(hitLocations.get(i-1) + 3).setDX(0);a.get(hitLocations.get(i - 1 ) + 3).setDY(0);
//} else if(hitLocations.get(i) - hitLocations.get(i - 1) ==2){
////hitLocations.add(i,hitLocations.get(i -1) + 3);
//hitLocations.add(i,hitLocations.get(i -1) + 2);
//hitLocations.add(i,hitLocations.get(i -1) + 1);
//a.get(hitLocations.get(i-1) + 1).setDX(0);a.get(hitLocations.get(i - 1 ) + 1).setDY(0);
//a.get(hitLocations.get(i-1) + 2).setDX(0);a.get(hitLocations.get(i - 1 ) + 2).setDY(0);
////a.get(hitLocations.get(i-1) + 3).setDX(0);a.get(hitLocations.get(i - 1 ) + 3).setDY(0);
//} else if(hitLocations.get(i) - hitLocations.get(i - 1) ==1){
////hitLocations.add(i,hitLocations.get(i -1) + 3);
////hitLocations.add(i,hitLocations.get(i -1) + 2);
//hitLocations.add(i,hitLocations.get(i -1) + 1);
//a.get(hitLocations.get(i-1) + 1).setDX(0);a.get(hitLocations.get(i - 1 ) + 1).setDY(0);
////a.get(hitLocations.get(i-1) + 2).setDX(0);a.get(hitLocations.get(i - 1 ) + 2).setDY(0);
////a.get(hitLocations.get(i-1) + 3).setDX(0);a.get(hitLocations.get(i - 1 ) + 3).setDY(0);



//for(int j = 1; j <= 120; j++){
//				//System.out.println("location 2 " + j);
//				k = j;
//				if (j >60){k = j +1;}//System.out.println("location 3");}
//				if(a.get(i).getx() -5 +(k%11)>0 && a.get(i).getx() -5 +(k%11)<w && a.get(i).getx() - 5 +(k-((k%11)))/11>0 && a.get(i).getx() - 5 +(k-((k%11)))/11<h){
//					if (p.get(a.get(i).getx() -5 +(k%11)).get(a.get(i).getx() - 5 +(k-((k%11)))/11).getv() == threshold ){
//						System.out.println("location 1");
//						a.get(i).setDX( a.get(i).getDX() - (decay / ((double)a.get(i).getx() -
//								(double)p.get(a.get(i).getx() - 5 +(k%11)).get(a.get(i).getx() - 5 +((k-((k%11)))/11)).getx())));
//						a.get(i).setDY( a.get(i).getDY() - (decay / ((double)a.get(i).gety() - 
//								(double)p.get(a.get(i).getx() - 5 + (k%11)).get(a.get(i).getx() - 5 + ((k-((k%11)))/11)).gety())));
//					}
//				}
//			}
//here is an alternative method to the above because the above is being a jerk and refuses to listen to reason
//			for(int j = 0;j <Area.size(); j++){
//				if((double)((double)Area.get(j).getx() - (double)a.get(i).getx()) != 0 && ((double)Area.get(j).gety() - (double)a.get(i).gety() != 0)){
//					a.get(i).setDX(a.get(i).getDX() - (decay / (double)((double)Area.get(j).getx() - (double)a.get(i).getx())));
//					a.get(i).setDY(a.get(i).getDY() -  (decay / (double)((double)Area.get(j).gety() - (double)a.get(i).gety())));
//					//System.out.println("location 1");
//				}
//
//			}// well this one refuses to listen to me as well, I think I'll start a new one that instead of doing pixel repulsion just does a straight pixel cannot pass


//the following section of junk code is my first pass at adding pixels for the region growing method, it would have worked really well but I started it off
//too large and the sheer amount of time required to flesh out all of the options would have taken me all summer
//for(int i = 0; i < area.size();i ++){
//	temp.add(area.get(i));
//}
//for(int i = 0; i < 11; i++){
//	if( i == 0){
//		temp.add(new Point(-99,-99));
//		for(int k = 0 ; k < 11; k ++){
//			for(int l = 0; l < 11; l++){
//				temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//				NewE = calcEnergyETF(temp, p, threshold);
//				if(NewE < OriginalE){
//					OriginalE = NewE;
//					for(int m = 0; m < temp.size();m++){
//						best.set(m, temp.get(m));
//					}
//				}
//			}
//		}
//	}else if( i == 1){
//		temp.add(new Point(-99,-99));
//		temp.add(new Point(-99,-99));
//		for(int k = 0 ; k < 11; k ++){
//			for(int l = 0; l < 11; l++){
//				for(int n = k+1 ; n < 11 ; n ++){
//					for(int o = 0; o<11;o++){
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + n, temp.get(i).getx() - 5 + o));
//						NewE = calcEnergyETF(temp, p, threshold);
//						if(NewE < OriginalE){
//							OriginalE = NewE;
//							for(int m = 0; m < temp.size();m++){
//								best.set(m, temp.get(m));
//							}
//						}
//					}
//				}
//				for(int n = 0 ; n < 11 ; n ++){
//					for(int o = l+1; o<11;o++){
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + n, temp.get(i).getx() - 5 + o));
//						NewE = calcEnergyETF(temp, p, threshold);
//						if(NewE < OriginalE){
//							OriginalE = NewE;
//							for(int m = 0; m < temp.size();m++){
//								best.set(m, temp.get(m));
//							}
//						}
//					}
//				}
//				
//			}
//		}
//	}else if( i == 2){
//		for(int k = 0; k < 3; k++){temp.add(new Point(-99,-99));}
//		for(int k = 0 ; k < 11; k ++){
//			for(int l = 0; l < 11; l++){
//				for(int n = k+1 ; n < 11 ; n ++){
//					for(int o = 0; o<11;o++){
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + n, temp.get(i).getx() - 5 + o));
//						NewE = calcEnergyETF(temp, p, threshold);
//						if(NewE < OriginalE){
//							OriginalE = NewE;
//							for(int m = 0; m < temp.size();m++){
//								best.set(m, temp.get(m));
//							}
//						}
//					}
//				}
//				for(int n = 0 ; n < 11 ; n ++){
//					for(int o = l+1; o<11;o++){
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + n, temp.get(i).getx() - 5 + o));
//						NewE = calcEnergyETF(temp, p, threshold);
//						if(NewE < OriginalE){
//							OriginalE = NewE;
//							for(int m = 0; m < temp.size();m++){
//								best.set(m, temp.get(m));
//							}
//						}
//					}
//				}
//				
//			}
//		}
//	}else if( i == 3){
//		temp.add(new Point(-99,-99));
//		temp.add(new Point(-99,-99));
//		for(int k = 0 ; k < 11; k ++){
//			for(int l = 0; l < 11; l++){
//				for(int n = k+1 ; n < 11 ; n ++){
//					for(int o = 0; o<11;o++){
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + n, temp.get(i).getx() - 5 + o));
//						NewE = calcEnergyETF(temp, p, threshold);
//						if(NewE < OriginalE){
//							OriginalE = NewE;
//							for(int m = 0; m < temp.size();m++){
//								best.set(m, temp.get(m));
//							}
//						}
//					}
//				}
//				for(int n = 0 ; n < 11 ; n ++){
//					for(int o = l+1; o<11;o++){
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + k, temp.get(i).getx() - 5 + l));
//						temp.set(area.size(), new Point(temp.get(j).getx() - 5 + n, temp.get(i).getx() - 5 + o));
//						NewE = calcEnergyETF(temp, p, threshold);
//						if(NewE < OriginalE){
//							OriginalE = NewE;
//							for(int m = 0; m < temp.size();m++){
//								best.set(m, temp.get(m));
//							}
//						}
//					}
//				}
//				
//			}
//		}
//	}else if( i == 4){
//		
//	}else if( i == 5){
//		
//	}else if( i == 6){
//		
//	}else if( i == 7){
//		
//	}else if( i == 8){
//		
//	}else if( i == 9){
//		
//	}else if( i == 10){
//		
//	}else if( i == 11){
//		
//	}
//}
//		for(int i = 1; i< a.size() - 1; i ++){
//			a.get(i).setDX(a.get(i).getDX() + (decay*(a.get(i).getx() - a.get(i - 1).getx()) + decay*(a.get(i + 1).getx() - a.get(i).getx())));
//			a.get(i).setDY(a.get(i).getDY() + (decay*(a.get(i).gety() - a.get(i - 1).gety()) + decay*(a.get(i + 1).gety() - a.get(i).gety())));
//		}
//		for(int i = 1; i < hitLocations.size(); i ++){
//			if(hitLocations.get(i) - hitLocations.get(i - 1) <20 ){
////				for(int j = 1; j < (double)hitLocations.get(i) - hitLocations.get(i - 1) / 2.0; j ++){
////					LPoint b = a.get(hitLocations.get(i-1)); LPoint c = b;
////					c.sety(b.gety() + 1 );
////					double dist = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					int index = 1;
////					c.setx(b.getx() + 1 );
////					double dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 2;}
////					c.sety(b.gety() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 3;}
////					c.setx(b.getx() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 4;}
////					c.sety(b.gety() + 1 );c.setx(b.getx() + 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 5;}
////					c.sety(b.gety() + 1 );c.setx(b.getx() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 6;}
////					c.sety(b.gety() - 1 );c.setx(b.getx() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 7;}
////					c.sety(b.gety() - 1 );c.setx(b.getx() + 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i-1) + 1), c);
////					if(dist2>dist){dist = dist2;index = 8;}
////					if(index == 1){
////						c.sety(b.gety() + 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 2){
////						c.setx(b.getx() + 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 3){
////						c.sety(b.gety() - 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 4){
////						c.setx(b.getx() - 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 5){
////						c.sety(b.gety() + 1 );c.setx(b.getx() + 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 6){
////						c.sety(b.gety() + 1 );c.setx(b.getx() - 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 7){
////						c.sety(b.gety() - 1 );c.setx(b.getx() - 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}else if(index == 8){
////						c.sety(b.gety() - 1 );c.setx(b.getx() + 1 ); a.set(hitLocations.get(i-1) + 1 , c);
////					}
////					hitLocations.add(i,hitLocations.get(i-1));
////					
////					
////					b = a.get(hitLocations.get(i)); c = b;
////					c.sety(b.gety() + 1 );
////					dist = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					index = 1;
////					c.setx(b.getx() + 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 2;}
////					c.sety(b.gety() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 3;}
////					c.setx(b.getx() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 4;}
////					c.sety(b.gety() + 1 );c.setx(b.getx() + 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 5;}
////					c.sety(b.gety() + 1 );c.setx(b.getx() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 6;}
////					c.sety(b.gety() - 1 );c.setx(b.getx() - 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 7;}
////					c.sety(b.gety() - 1 );c.setx(b.getx() + 1 );
////					dist2 = c.getDistance(a.get(hitLocations.get(i) - 1), c);
////					if(dist2>dist){dist = dist2;index = 8;}
////					if(index == 1){
////						c.sety(b.gety() + 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 2){
////						c.setx(b.getx() + 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 3){
////						c.sety(b.gety() - 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 4){
////						c.setx(b.getx() - 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 5){
////						c.sety(b.gety() + 1 );c.setx(b.getx() + 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 6){
////						c.sety(b.gety() + 1 );c.setx(b.getx() - 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 7){
////						c.sety(b.gety() - 1 );c.setx(b.getx() - 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}else if(index == 8){
////						c.sety(b.gety() - 1 );c.setx(b.getx() + 1 ); a.set(hitLocations.get(i) - 1 , c);
////					}
////					hitLocations.add(i,hitLocations.get(i-1));
////				}
////			}
//		}
//		for(int i = 1; i < a.size(); i ++){
//			if ( o.getDistance(a.get(i), a.get(i-1)) > Math.sqrt(2)){
//				a.set(i-1, moveToFit(a.get(i), a.get(i-1)));
//			}
//		}
