//main
import java.io.*;
import java.util.*;
import java.sql.Timestamp;
/**
 * 
 * @author bin
 *
 */
public class Main{

	private static final int DISTANCE_TO_NODE = 200;

	public static void main(String args[]){		
		//宣言
		List<PassNode> passNodeList = new ArrayList<PassNode>();

		//読み込みファイルの指定
		String file1 = "./input/YOKOHAMAnode.csv";      //nodeデータの場所を指定
		String file2 = "./input/YOKOHAMAlink.csv";      //linkデータの場所を指定
		String file3 = "./input/UnlinkedTrip.csv";     //tripデータの場所を指定
		String file4 = "./input/LocData.csv"; //locationデータの場所を指定

		//データの読み込み
		System.out.println("ネットワークデータを読込");
		ReadFile.setNode(file1);
		ReadFile.setLink(file2);		
		System.out.println("PPデータを読込");
		ReadFile.setTrip(file3);
		ReadFile.setLocation(file4);

		//tirp単位でlocationデータの分割をする．
		System.out.println("locデータを貼り付け");
		Matching.partLocation(ReadFile.getPersonMap(), ReadFile.getLocationList());
		
		//各ノードを始点とするリンクの検索(ダイクストラの準備)
		Dijkstra.set(ReadFile.getLinkMap(), ReadFile.getNodeMap());
		System.out.println("set完了");

		//マップマッチング計算
		try {
			PrintWriter line1 = new PrintWriter(new FileWriter("./output/link.csv"));//全体の経路データ
			line1.println("モニターID,ダイアリーID,交通手段,リンクID,リンク出発時刻,所要時間(s),速度(km/h),リンクコスト,リンク長(m)");
			PrintWriter line2 = new PrintWriter(new FileWriter("./output/path.csv"));//全体の経路データ
			line2.println("モニターID,ダイアリーID,交通手段,経路出発nodeID,経路到着nodeID,経路長（m）,所要時間(s),速度(km/h)");

			List <Trip> tripList = ReadFile.getTripList();
			for(Trip trip : tripList){
				//モニターごとにフォルダーを作成しその中に管理
				File file00 = new File("./output/linkFile/" + trip.getPersonalID() + "/");
				//もしそのモニター名のフォルダがないなら作成する
				if(file00.exists() == false && trip.getLocationList().size() > 1){
					file00.mkdirs();
				}
				if(trip.getLocationList().size()> 1){
					//リンクの中点から取得点を繋げたリンクまでの最短距離を算出し，起終点および中点と取得点をつなげた経路の面積をリンクコストとして算出
					//初期値
					int minCost = Integer.MAX_VALUE;
					int limit = DISTANCE_TO_NODE; //ノードとの距離
					while(true){
						//ネットワーク内の対象リンクを抽出しコストを算出する
						Matching.exeLinkCost(ReadFile.getNodeMap(), ReadFile.getLinkMap(), trip.getLocationList(), limit);

						//ネットワーク内の対象リンクの中から起点リンク候補を決定する 
						List<Integer> startLinkList = null;
						for(int j = 0; j < trip.getLocationList().size();j++){
							if(startLinkList==null || startLinkList.isEmpty()==true){
								startLinkList = Matching.getNearLinkList(ReadFile.getLinkMap(), trip.getLocationList(), j);							}
						}

						//ネットワーク内の対象リンクの中から終点リンク候補を決定する 
						List<Integer> endLinkList=null;
						for(int j = trip.getLocationList().size() ;j > 0; j--){
							if(endLinkList==null || endLinkList.isEmpty()==true){
								endLinkList=Matching.getNearLinkList(ReadFile.getLinkMap(), trip.getLocationList(), j-1);
							}
						}

						//起点，終点のリンク候補同士から経路列挙
						for(int j = 0; j < startLinkList.size();j++){
							int sLinkID = startLinkList.get(j);
							int sNodeID = ReadFile.getLinkMap().get(sLinkID).getUpNodeID();
							for(int k = 0; k < endLinkList.size(); k++){    	        				
								int eLinkID = endLinkList.get(k);
								int eNodeID = ReadFile.getLinkMap().get(eLinkID).getDnNodeID();
								if(sNodeID != eNodeID){
									System.out.println(sLinkID+" "+eLinkID+" "+sNodeID+" "+eNodeID);
									Dijkstra.exeDijkstra(ReadFile.getLinkMap(), ReadFile.getNodeMap(), sNodeID,eNodeID);
									int cost = Dijkstra.getCost(ReadFile.getNodeMap(), eNodeID);   
									if(minCost > cost){
										minCost = cost;
										trip.setStartNodeID(sNodeID);
										trip.setEndNodeID(eNodeID);
									}									
								}
							}
						} 
						if(minCost<9999999 || limit > 100){    		
							if(minCost < 999999){
								Dijkstra.exeDijkstra(ReadFile.getLinkMap(), ReadFile.getNodeMap(), trip.getStartNodeID(), trip.getEndNodeID());
								Dijkstra.clearPath();
								Dijkstra.exePath(ReadFile.getLinkMap(), ReadFile.getNodeMap(), trip.getStartNodeID(), trip.getEndNodeID());
								trip.setPathList(Dijkstra.getPath());
								trip.setPathLength(Dijkstra.getPathLength());   	        				
							}else{
								trip.setPathLength(-1) ;
							}
							break;
						}
						limit += 100;
					}
				}
				if(trip.getPathLength()==-1){
					System.out.println(trip.getTripID() + ":出発地と到着地がつながっていない.");
					line2.println(trip.getTripID() + ",出発地と到着地がつながっていない.");
				}else if(trip.getPathLength()==0){
					System.out.println(trip.getTripID() + ":出発ノードと到着ノードが同じ.");
					line2.println(trip.getTripID() + ",出発ノードと到着ノードが同じ.");
				}else{
					Matching.exePassNodeTime(ReadFile.getLinkMap(), trip, passNodeList);
					//交差点の通過時刻出力
					Timestamp startTime = trip.getDepatureTime();
					Timestamp endTime = trip.getArrivalTime();
					if(passNodeList.size() != 0){
						Timestamp upTime = passNodeList.get(0).getPassTime();
						for(int j = 1 ; j < passNodeList.size(); j++){   	        		
							PassNode passNode = passNodeList.get(j);
							Timestamp dnTime = passNode.getPassTime();
							int linkID = passNode.getLinkID();
							Link link1 = ReadFile.getLinkMap().get(linkID);
							
							double linkTime = (int)(( dnTime.getTime() - upTime.getTime()) / 100);
							double velocity = (int)(link1.getDistance() / linkTime * 3600 / 10);
							line1.println(trip.getPersonalID() + "," +  trip.getTripID() + "," +  trip.getMode() + ","+ link1.getLinkID() + "," + upTime + "," + linkTime/10 + "," + velocity/10+ "," +link1.getLinkCost()+ "," +link1.getDistance());
							
							upTime = dnTime;
							if(j==1){
								startTime = upTime;
							}
							if(j == passNodeList.size()-1){
								endTime = dnTime;
							}
						}
					}
					Output.googleEarth(ReadFile.getLinkMap(), ReadFile.getNodeMap(), trip);//マッチングできたものだけ結果を出力
					double linkTime = (int)((endTime.getTime() - startTime.getTime()) / 100);
					double velocity = (int)(trip.getPathLength() / 10 / linkTime * 3600 );
					System.out.println(trip.getTripID() + ":マッチング成功.");
					line2.println(trip.getPersonalID() + "," +  trip.getTripID() + "," +  trip.getMode() + "," + passNodeList.get(0).getNodeID() + "," + passNodeList.get(passNodeList.size() - 1).getNodeID() + "," + trip.getPathLength() + ","+linkTime / 10 + "," + velocity / 10);
				}
				passNodeList.clear();
			}
			line2.close();
			line1.close();
		}
		catch( IOException e ) {
			System.out.println( "例外(row) : " + e );
			System.exit(1);
		}
	}
}
