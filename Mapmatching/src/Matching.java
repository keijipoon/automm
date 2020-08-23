import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Matching {

	/**
	 * TODO リンク尤度を算出する．
	 * @param nodeMap
	 * @param linkMap
	 * @param locationList
	 * @param limit
	 */
	public static void exeLinkCost(Map<Integer,Node> nodeMap, Map<Integer,Link> linkMap,  List<Location> locationList, int limit){
		for(Iterator<Integer> i = linkMap.keySet().iterator();i.hasNext(); ){
   			Integer o = i.next();
			Link link  = linkMap.get(o);
   			int upNodeID = link.getUpNodeID();
   			int dnNodeID = link.getDnNodeID();

   			Node upNode = nodeMap.get(upNodeID);
   			Node dnNode = nodeMap.get(dnNodeID);
   			link.setLinkCost(Integer.MAX_VALUE);

       		if(upNode.getMinLength() < limit && dnNode.getMinLength() < limit){
				double midX = (upNode.getX() + dnNode.getX()) / 2;
				double midY = (upNode.getY() + dnNode.getY()) / 2;
				double minLength = Double.MAX_VALUE;
				double ox  = locationList.get(0).getX();
				double oy = locationList.get(0).getY();


				for(int j = 1; j < locationList.size(); j++){
					Location location = locationList.get(j);
					double dx = location.getX();
					double dy = location.getY();
					double length=Length.minLength(ox,oy,dx,dy,midX,midY);
					if(length < minLength){
						minLength = length;
					}
					ox = dx;
					oy = dy;
				}
				double midLinkCost = minLength;
				link.setLinkCost((int)((upNode.getMinLength() + dnNode.getMinLength() + 2 * midLinkCost ) / 2 * link.getDistance() / 2 / link.getLaneNum() / 100));
      		}else{
      			link.setLinkCost(9999999);
			}
		}
	}
	/**
	 * TODO リンクからの最短距離算出
	 * @param linkMap
	 * @param x
	 * @param y
	 * @return
	 */
	public static double minLength( Map<Integer, Link> linkMap, double x,double y){
		double minLength = Integer.MAX_VALUE;
		for(Iterator<Integer> i = linkMap.keySet().iterator();i.hasNext(); ){
   			Link link = linkMap.get(i.next());
			double length = Length.minLength(link.getUpNodeX(),link.getUpNodeY(),link.getDnNodeX(),link.getDnNodeY(),x,y);
			if(length < minLength){
				minLength = length;
			}
		}
		return minLength;
	}

	/**
	 * TODO 近いリンクIDリストを検索
	 * @param linkMap
	 * @param locationList
	 * @param index
	 * @return
	 */
	public static List<Integer> getNearLinkList(Map<Integer, Link> linkMap, List<Location> locationList,int index){
   		ArrayList<Integer> ODLinkList = new  ArrayList<Integer>();

		for(Iterator<Integer> i = linkMap.keySet().iterator();i.hasNext(); ){
   			Link link = linkMap.get(i.next());
			Location location = locationList.get(index);
			double length = Length.minLength(link.getUpNodeX(),link.getUpNodeY(),link.getDnNodeX(),link.getDnNodeY(),location.getX(),location.getY());
			if(length < 30){//30m以内
				ODLinkList.add(link.getLinkID());
			}
		}
   		return ODLinkList;
	}

	/**
	 * TODO 通過時間を算出
	 * @param linkMap
	 * @param trip
	 * @param passNodeList
	 */
	public static void exePassNodeTime(Map<Integer, Link> linkMap, Trip trip,List<PassNode> passNodeList){
		long minLocationTime = 0;

		List<Location> locationList = trip.getLocationList();
		//開始の起点ノードのみ
		{
			int linkID = trip.getPathList().get(0);
			Link link = linkMap.get(linkID);
			double minLength = Double.MAX_VALUE;
			Location upLocation = locationList.get(0);

			for(int j = 1 ;j < locationList.size(); j++){
				Location dnLocation = locationList.get(j);
				if(minLocationTime < dnLocation.getTime().getTime()){
					long timeLength = (dnLocation.getTime().getTime() - upLocation.getTime().getTime()) / 1000;
					for(int t = 0 ; t < timeLength + 1 ; t++){//等分
						double timeX = upLocation.getX() + (dnLocation.getX() - upLocation.getX()) * t /timeLength;
						double timeY = upLocation.getY() + (dnLocation.getY() - upLocation.getY()) * t /timeLength;
						double length = Length.direct_distance(link.getUpNodeX(), timeX, link.getUpNodeY(), timeY);
						if(minLength>length){
							minLength=length;
							if(minLocationTime < upLocation.getTime().getTime() + t * 1000){
								minLocationTime = upLocation.getTime().getTime() + t * 1000;
							}
						}
					}
				}
				upLocation = dnLocation;
			}

			PassNode passNode = new PassNode();
			passNode.setNodeID(link.getUpNodeID());
			passNode.setLinkID(link.getLinkID());
			passNode.setPassTime(new Timestamp(minLocationTime));
			passNodeList.add(passNode);

		}
		//開始の起点ノード以外
		for(int k =0 ; k < trip.getPathList().size();k++){
			int linkID = trip.getPathList().get(k);
			Link link = linkMap.get(linkID);
			double minLength = Double.MAX_VALUE;
			Location upLocation = locationList.get(0);
			for(int j=1 ; j < locationList.size(); j++){
				Location dnLocation = locationList.get(j);
				if(minLocationTime < dnLocation.getTime().getTime()){
					long timeLength = (dnLocation.getTime().getTime() - upLocation.getTime().getTime()) / 1000;
					for(int t = 0 ; t < timeLength + 1 ; t++){//等分
						double timeX = upLocation.getX() + (dnLocation.getX() - upLocation.getX()) * t /timeLength;
						double timeY = upLocation.getY() + (dnLocation.getY() - upLocation.getY()) * t /timeLength;
//						double length = Length.direct_distance(link.getUpNodeX(), timeX, link.getUpNodeY(), timeY);
						double length = Length.direct_distance(link.getDnNodeX(), timeX, link.getDnNodeY(), timeY);
						if(minLength > length){
							minLength = length;
							if(minLocationTime < upLocation.getTime().getTime() + t * 1000){
								minLocationTime = upLocation.getTime().getTime() + t * 1000;
							}
						}
					}
				}
				upLocation = dnLocation;
			}
			PassNode passNode = new PassNode();
//			passNode.setNodeID(link.getUpNodeID());
			passNode.setNodeID(link.getDnNodeID());
			passNode.setLinkID(link.getLinkID());
			passNode.setPassTime(new Timestamp(minLocationTime));
			passNodeList.add(passNode);
		}
	}

	/**
	 * TODO ロケーションデータを分割する．
	 * @param personMap
	 * @param locationList
	 */
	public static void partLocation(Map<String,Person> personMap, List<Location> locationList){
		for(Location location : locationList){
			Person person = personMap.get(location.getPersonalID());
			if(person != null){
				List<Trip> tripList = person.getTripList();
				for(Trip trip : tripList){
					if(trip.getDepatureTime().before(location.getTime()) && trip.getArrivalTime().after(location.getTime())){
						trip.getLocationList().add(location);
						break;
					}
				}
			}
		}
    }
}
