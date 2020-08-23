import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class ReadFile {
	private static Map<Integer,Node> nodeMap = new HashMap<Integer,Node>();
	private static Map<Integer, Link> linkMap = new HashMap<Integer,Link>();
	private static List<Location> locationList = new ArrayList<Location>();
	private static List<Trip> tripList = new ArrayList<Trip>();
	private static Map<String,Person> personMap = new HashMap<String,Person>();
	
	public static void setNode(String file1){
        try {
	    	BufferedReader line1 = new BufferedReader(new FileReader(file1));//ファイル入力準備FileReader line0;
		    line1.readLine();
		    String line10;
			while((line10 = line1.readLine()) != null){
   		    	
				StringTokenizer st = new StringTokenizer(line10,",");    
	         	
				Node node = new Node();				
				node.setNodeID(Integer.parseInt(st.nextToken()));
				double lat = Double.parseDouble(st.nextToken());
	        	double lon = Double.parseDouble(st.nextToken());
	        	node.setLat(lat);
	        	node.setLon(lon);
	        	node.setX(Henkan.x(node.getLat(), node.getLon()));
	        	node.setY(Henkan.y(node.getLat(), node.getLon()));
	        	
	        	nodeMap.put(node.getNodeID(), node);

		    }
		    line1.close();
		}
	    catch( IOException e ) {
//	    	System.out.println( "例外(row) : " + e );
	    	System.out.println("ノードファイルが読み込めません");
		    System.exit(1);
	    }
	}
	
    /**
     * TODO ロケーションデータ読み込み（生データ）
     * @param file1
     */
    public static void setLocation(String file1){
    	try {
	    	BufferedReader line = new BufferedReader(new FileReader(file1));//ファイル入力準備FileReader line0;
		    line.readLine();//ヘッダ
		    String line1;
			int a=1,b=1;
    		while((line1 = line.readLine()) != null){
    			if(a==10000){
    				a=0;
    				System.out.println(b);
    				b++;
    			}
    			StringTokenizer st = new StringTokenizer(line1);
    			int locationID = Integer.parseInt(st.nextToken(","));
    			String personalID = st.nextToken(",");
    			String time = st.nextToken(",");    			
    			float lat = Float.parseFloat(st.nextToken(","));//日本測地系
    			float lon = Float.parseFloat(st.nextToken(","));
    			int mode = Integer.parseInt(st.nextToken(",\""));    			
    			if(mode == 0){//GPSなら    				
        			Location location = new Location();
        			location.setLocationID(locationID);
        			location.setPersonalID(personalID);
        			time = time.replaceAll("/","-");
        			location.setTime(Timestamp.valueOf(time));        			
        			location.setLat(lat);
        			location.setLon(lon);
        			location.setX((float)Henkan.x(location.getLat(),location.getLon()));
        			location.setY((float)Henkan.y(location.getLat(),location.getLon()));
        			//location.setMode(mode);
        			locationList.add(location);
    			}
    		}	
    		line.close();
    	}
    	catch( IOException e ) {
    		//	    	System.out.println( "例外(row) : " + e );
    		System.out.println("Locationファイルが読み込めません");
    		System.exit(1);
    	}
	}
    
    /**
     * TODO トリップデータ読み込み
     * @param file
     */
    public static void setTrip(String file){
        try {
	    	BufferedReader line = new BufferedReader(new FileReader(file));
		    String line1;
		    line.readLine();
		    while((line1 = line.readLine()) != null){
    			StringTokenizer st = new StringTokenizer(line1);
    			int tripID = Integer.parseInt(st.nextToken(","));
    			String personalID = st.nextToken(",");
    			st.nextToken(",");
    			//int rawTripID = Integer.parseInt(st.nextToken(","));
	            String departureTime=st.nextToken(",");
	            if(departureTime.length() == 10){
	            	departureTime = departureTime + " 00:00:00";
	            }
	            departureTime = departureTime.replaceAll("/", "-");
	            String arrivalTime = st.nextToken(",");
	            if(arrivalTime.length() == 10){
	            	arrivalTime = arrivalTime+" 00:00:00";
	            }
	            arrivalTime = arrivalTime.replaceAll("/", "-");
	            int mode =Integer.parseInt(st.nextToken(","));
	            if(mode == 100 || mode == 500 ||mode == 600){//交通手段
	            	Person person;
	            	if((person = personMap.get(personalID)) == null){
			    		person = new Person();
			    		person.setPersonalID(personalID);
			    		personMap.put(personalID, person);
			    	}
	            	Trip trip = new Trip();
		   			trip.setTripID(tripID);
		   			trip.setPersonalID(personalID);
			    	trip.setDepatureTime(java.sql.Timestamp.valueOf(departureTime));
	    			trip.setArrivalTime(java.sql.Timestamp.valueOf(arrivalTime));
		            trip.setMode(mode);
		            tripList.add(trip);
		            person.getTripList().add(trip);
	            }
		    }
		    
		    line.close();
		}
	    catch( IOException e ) {
	    	System.out.println( "例外(row) : " + e );
		    System.exit(1);
	    }
	}
    
    public static void setLink(String file){
        try {
	    	BufferedReader line = new BufferedReader(new FileReader(file));//ファイル入力準備FileReader line0;
		    String line1;
		    line.readLine();
		    while((line1 = line.readLine()) != null){
		    	StringTokenizer st = new StringTokenizer(line1);    
  		        Link link = new Link();
		    	link.setLinkID(Integer.parseInt(st.nextToken(",")));
		    	link.setUpNodeID(Integer.parseInt(st.nextToken(",")));
		    	Node upNode = nodeMap.get(link.getUpNodeID());
		    	link.setUpNodeX(upNode.getX());
		    	link.setUpNodeY(upNode.getY());
		    	link.setDnNodeID(Integer.parseInt(st.nextToken(",")));
		    	Node dnNode = nodeMap.get(link.getDnNodeID());
		    	link.setDnNodeX(dnNode.getX());
		    	link.setDnNodeY(dnNode.getY());
		    	
		    	link.setDistance(Double.parseDouble(st.nextToken(",")));
		    	link.setLaneNum(Integer.parseInt(st.nextToken(",")));
		    	linkMap.put(link.getLinkID(), link);
	    	}
		    line.close();
		}
	    catch( IOException e ) {
//	    	System.out.println( "例外(row) : " + e );
	    	System.out.println("リンクデータ(ROD)ファイルが読み込めません");
		    System.exit(1);
	    }
	}

	public static Map<Integer, Node> getNodeMap() {
		return nodeMap;
	}

	public static void setNodeMap(Map<Integer, Node> nodeMap) {
		ReadFile.nodeMap = nodeMap;
	}

	public static Map<Integer, Link> getLinkMap() {
		return linkMap;
	}

	public static void setLinkMap(Map<Integer, Link> linkMap) {
		ReadFile.linkMap = linkMap;
	}

	public static List<Location> getLocationList() {
		return locationList;
	}

	public static void setLocationList(List<Location> locationList) {
		ReadFile.locationList = locationList;
	}

	public static List<Trip> getTripList() {
		return tripList;
	}

	public static void setTripList(List<Trip> tripList) {
		ReadFile.tripList = tripList;
	}

	public static Map<String, Person> getPersonMap() {
		return personMap;
	}

	public static void setPersonMap(Map<String, Person> personMap) {
		ReadFile.personMap = personMap;
	}


}
