class Length {
    static double a0;
    static double b0;
    static double c0;
    
    public static double intersectionX(double a1x,double a1y,double a2x,double a2y,double b1x,double b1y){
    	if((a1x-a2x)==0){
    		a1x=a1x/100000000.0;
    	}
		double a = a1y - a2y;
		double b = a2x - a1x;
		double c = a2x * a1y - a2y * a1x;
		double m = (a1y - a2y)/(a1x - a2x);
		double d = 1;
		double e = m;
		double f = b1x + b1y*m;
		
		double x = (b * f - c * e) / (b * d - a * e);
		return x;
	}
    public static double intersectionY(double a1x,double a1y,double a2x,double a2y,double b1x,double b1y){
    	if((a1x-a2x)==0){
    		a1x=a1x/100000000.0;
    	}
		double a = a1y - a2y;
		double b = a2x - a1x;
		double c = a2x * a1y - a2y * a1x;
		double m = (a1y - a2y)/(a1x - a2x);
		double d = 1;
		double e = m;
		double f = b1x + b1y*m;
		double y = (c * d - a * f) / (b * d - a * e);
		return y;
	}
    public static boolean determination(double a1,double a2,double b1,double b2,double p1,double p2){
		double X=(a2-p2)*(a2-b2)+(a1-p1)*(a1-b1);
		double Y=(b2-p2)*(a2-b2)+(b1-p1)*(a1-b1);
		double Z=X*Y;
		boolean D=true;
		if(Z>0){
			D=false;
		}
		return D;
	}
    public static void set_abc(double x1,double y1,double x2,double y2){
		a0=y1-y2;
		b0=x2-x1;
		c0=x1*y2-x2*y1;
	}
    public static double get_a(){
		return a0;
	}
    public static double get_b(){
		return b0;
	}
    public static double get_c(){
		return c0;
	}
    public static double distance(double a,double b,double c,double x,double y){
		double r;
		r = Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b);
		return r;
	}
    public static double direct_distance(double x1,double x2,double y1,double y2){
		double r;
		r = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		return r;
	}
	
    public static double minLength(double ox,double oy,double dx,double dy,double fx,double fy){
		double length=0;
		double vx=0;
		double vy=0;
		boolean D =determination(ox,oy,dx,dy,fx,fy);
		if(D==true){
			set_abc(ox,oy,dx,dy);
			double a=get_a();
			double b=get_b();
			double c=get_c();
			length=distance(a,b,c,fx,fy);
			vx=intersectionX(ox,oy,dx,dy,fx,fy);
			vy=intersectionY(ox,oy,dx,dy,fx,fy);
		}
		else{
			double len_o=Length.direct_distance(fx,ox,fy,oy);
			double len_d=Length.direct_distance(fx,dx,fy,dy);
			if(len_o<len_d){
				length=len_o;
				vx=ox;
				vy=oy;
			}
			else{
				length=len_d;
				vx=dx;
				vy=dy;
			}
		}
		return length;
	}
    public static double minLengthX(double ox,double oy,double dx,double dy,double fx,double fy){
		double length=0;
		double vx=0;
		double vy=0;
		boolean D =determination(ox,oy,dx,dy,fx,fy);
		if(D==true){
			set_abc(ox,oy,dx,dy);
			double a=get_a();
			double b=get_b();
			double c=get_c();
			length=distance(a,b,c,fx,fy);
			vx=intersectionX(ox,oy,dx,dy,fx,fy);
			vy=intersectionY(ox,oy,dx,dy,fx,fy);
		}
		else{
			double len_o=Length.direct_distance(fx,ox,fy,oy);
			double len_d=Length.direct_distance(fx,dx,fy,dy);
			if(len_o<len_d){
				length=len_o;
				vx=ox;
				vy=oy;
			}
			else{
				length=len_d;
				vx=dx;
				vy=dy;
			}
		}
		return vx;
	}
    public static double minLengthY(double ox,double oy,double dx,double dy,double fx,double fy){
		double length=0;
		double vx=0;
		double vy=0;
		boolean D =determination(ox,oy,dx,dy,fx,fy);
		if(D==true){
			set_abc(ox,oy,dx,dy);
			double a=get_a();
			double b=get_b();
			double c=get_c();
			length=distance(a,b,c,fx,fy);
			vx=intersectionX(ox,oy,dx,dy,fx,fy);
			vy=intersectionY(ox,oy,dx,dy,fx,fy);
		}
		else{
			double len_o=Length.direct_distance(fx,ox,fy,oy);
			double len_d=Length.direct_distance(fx,dx,fy,dy);
			if(len_o<len_d){
				length=len_o;
				vx=ox;
				vy=oy;
			}
			else{
				length=len_d;
				vx=dx;
				vy=dy;
			}
		}
		return vy;
	}
}