import java.io.*;
import java.util.*;

public class Output{
	public static void set(){
	   	InputStreamReader isr = null;
		BufferedReader br = null;
		
		System.out.print("何かキーを押してください。");
		
		try {
			isr = new InputStreamReader(System.in);
			br = new BufferedReader(isr);
			br.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO トリップと通過リンクをgoogleEarthに表示
	 * @param linkMap
	 * @param nodeMap
	 * @param trip
	 */
	public static void googleEarth(Map<Integer, Link> linkMap, Map<Integer, Node> nodeMap,Trip trip){
		try{
    		BufferedWriter bw_kml = new BufferedWriter(new FileWriter("./output/linkFile/" + trip.getPersonalID() + "/" + trip.getTripID() + "MapMatchingResult.kml"));
    		StringBuilder sb_kml = new StringBuilder();
    		sb_kml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\r\n");
    		sb_kml.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">").append("\r\n");
    		sb_kml.append("<Folder><name>link</name><open>1</open>").append("\r\n");
    		sb_kml.append("<Style id=\"redline\"><LineStyle><color>ff0000ff</color><width>6</width></LineStyle></Style>").append("\r\n");
    		sb_kml.append("<Style id=\"s_ylw-pushpin\"><IconStyle><scale>1.1</scale><Icon><href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href></Icon><hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/></IconStyle></Style>").append("\r\n");
    		bw_kml.write(sb_kml.toString());
    		sb_kml.setLength(0);
    		//通過リンク表示
    		for (Integer linkID : trip.getPathList()){
    			Node upNode = nodeMap.get(linkMap.get(linkID).getUpNodeID());
    			Node dnNode = nodeMap.get(linkMap.get(linkID).getDnNodeID());
    			sb_kml.append("<Document><name>").append(linkID).append("</name><Placemark><name></name><styleUrl>#redline</styleUrl><LineString><coordinates>");
    			sb_kml.append(upNode.getLon()).append(",").append(upNode.getLat()).append("\r\n");
    			sb_kml.append(dnNode.getLon()).append(",").append(dnNode.getLat()).append("</coordinates></LineString></Placemark></Document>").append("\r\n");
    		}
    		//ノード表示
    		for (Location location : trip.getLocationList()){
    			sb_kml.append("<Document><name>").append(location.getLocationID()).append("</name><Placemark><name>");
    			sb_kml.append(location.getLocationID()).append("</name><open>1</open><styleUrl>#s_ylw-pushpin</styleUrl><LookAt><longitude>");
    			sb_kml.append(location.getLon()).append("</longitude><latitude>");
    			sb_kml.append(location.getLat()).append("</latitude><altitude>0</altitude></LookAt><styleUrl>#m_ylw-pushpin</styleUrl><Point><coordinates>");
    			sb_kml.append(location.getLon()).append(",");
    			sb_kml.append(location.getLat()).append("</coordinates></Point></Placemark></Document>").append("\r\n");
    		}
    		sb_kml.append("</Folder></kml>");
    		bw_kml.write(sb_kml.toString());
    		bw_kml.close();
    		System.out.println("kmlデータ終了");
		}
	    catch( IOException e ) {
	    	System.out.println( "例外(row) : " + e );
		    System.exit(1);
	    }
	}
	
    public static void err(){
    	InputStreamReader isr = null;
		BufferedReader br = null;
		
		System.out.print("計算に失敗しました。");
		System.out.print("何かキーを押してください。");
		
		try {
			isr = new InputStreamReader(System.in);
			br = new BufferedReader(isr);
			br.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}