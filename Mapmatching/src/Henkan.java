import jp.jasminesoft.gcat.scalc.*;

public class Henkan{
	static LatLong2XY xy = new LatLong2XY();
	static XY2LatLong xy2LatLong = new XY2LatLong(9);
	public static String dddmmss(double ddd){           //ddd.ddddddをddd/mm/ss.ssssに変換するプログラム
		double minutes = (ddd-(int)ddd)*60;
		double seconds = (minutes-(int)minutes)*60*1000;
		String dddmmssString = (int)ddd+"/"+(int)minutes+"/"+(int)seconds;
		return dddmmssString;
	}
	public static double keido(int mesh,int x){           //正規化座標を経度座標(日本測地系)に変換x
		int a = mesh/10000;
		int mesh_a = (mesh - a*10000)/100;
		int b = mesh/10;
		int mesh_b = mesh - b*10;
//		int mesh_x = mesh_a*8+mesh_b;
		double keido = 100.0000 + (double)mesh_a+((double)mesh_b)/8 + ((double)x)/10000*7.5000/60.0000;
		return keido;
	}
	public static double ido(int mesh,int y){           //正規化座標を緯度座標(日本測地系)に変換y
		int mesh_a = mesh/10000;
//		int mesh_a = mesh - a*10000;
		int b = mesh/100;
		int mesh_b = (mesh - b*100)/10;
//		System.out.println(mesh_a+" "+mesh_b);
//		int mesh_y = mesh_a*8+mesh_b;
		double ido = (double)mesh_a*40/60+(double)mesh_b*40/60/8+(double)y/10000*40/60/8;
		return ido;
	}
	public static double getLat(double x,double y){           //平面直角座標系yを緯度座標(日本測地系)に変換
		xy2LatLong.setY(y);
		xy2LatLong.setX(x);
		double lat = xy2LatLong.getLatitude();
		return lat;
	}
	public static double getLon(double x,double y){           //平面直角座標系yを緯度座標(日本測地系)に変換
		xy2LatLong.setY(y);
		xy2LatLong.setX(x);
		double lon = xy2LatLong.getLongitude();
		return lon;
	}
	
	public static double x(double lat0,double lon0){           //経度座標(日本測地系)を直角座標系x(単位m)に変換		
		double minutes = (lat0-(int)lat0)*60;
		double seconds = (minutes-(int)minutes)*60;
		double lat = (int)lat0+((double)((int)minutes))/100+seconds/10000;
		minutes = (lon0-(int)lon0)*60;
		seconds = (minutes-(int)minutes)*60;
		double lon = (int)lon0+((double)((int)minutes))/100+seconds/10000;
		xy.setLatitude(lat);
    	xy.setLongitude(lon);
		double x = xy.getY();
    	return x;
	}
	
	public static double y(double lat0,double lon0){           //緯度座標(日本測地系)を直角座標系y(単位m)に変換
		double minutes = (lat0-(int)lat0)*60;
		double seconds = (minutes-(int)minutes)*60;
		double lat = (int)lat0+((double)((int)minutes))/100+seconds/10000;
		minutes = (lon0-(int)lon0)*60;
		seconds = (minutes-(int)minutes)*60;
		double lon = (int)lon0+((double)((int)minutes))/100+seconds/10000;
		xy.setLatitude(lat);
    	xy.setLongitude(lon);
		double y = xy.getX();
     	return y;
	}
	
	public static double wgsToJgdLon(double lat0,double lon0){           //
		double lon = lon0 + 0.000046047*lat0 + 0.000083049*lon0 - 0.010041;
		return lon;
	}
	public static double wgsToJgdLat(double lat0,double lon0){           //
		double lat = lat0 + 0.00010696*lat0 - 0.000017467*lon0 - 0.0046020;
		return lat;
	}
	public static double jgdToWgsLon(double lat0,double lon0){           //
		double lon = lon0 - 0.000046038*lat0 - 0.000083043*lon0 + 0.010040;
		return lon;
	}
	public static double jgdToWgsLat(double lat0,double lon0){           //
		double lat = lat0 - 0.00010695*lat0 + 0.000017464*lon0 - 0.0046017;
		return lat;
	}
}