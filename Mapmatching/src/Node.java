import java.util.*;

class Node {            //読み込みを行なうクラス
	private int defNodeID;//読み込み時のノードID
	private int nodeID;//ノードID
	private double lat;//緯度
	private double lon;//経度
	private double y;//直角座標系Y座標
	private double x;//直角座標系X座標
	//ダイクストラ法計算用変数
	private int cost;//出発ノードからのコスト
	private int beforeLinkID;//開始点から当該への最短経路の直前のlinkID
	private  double minLength;//nodeIDとバス停間のつないだものとの最小距離
	private int nodeSet;//ノード集合　1:最短経路算出済みのノード集合,2:最短経路の計算中のノード集合,3:最短経路算出前のノード集合
	private  List<Link> nextLinkList = new ArrayList<Link>();//そのノードから出ているリンクの集合

	
	public int getDefNodeID() {
		return defNodeID;
	}
	public void setDefNodeID(int defNodeID) {
		this.defNodeID = defNodeID;
	}
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getBeforeLinkID() {
		return beforeLinkID;
	}
	public void setBeforeLinkID(int beforeLinkID) {
		this.beforeLinkID = beforeLinkID;
	}
	public double getMinLength() {
		return minLength;
	}
	public void setMinLength(double minLength) {
		this.minLength = minLength;
	}
	public int getNodeSet() {
		return nodeSet;
	}
	public void setNodeSet(int nodeSet) {
		this.nodeSet = nodeSet;
	}
	public List<Link> getNextLinkList() {
		return nextLinkList;
	}
	public void setNextLinkList(List<Link> nextLinkList) {
		this.nextLinkList = nextLinkList;
	}
}