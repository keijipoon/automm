public class Link{
    private int linkID;   //Link名称
    private int upNodeID; //起点ノード名称
    private double upNodeX; //起点ノードX座標
    private double upNodeY; //起点ノードY座標
    private int dnNodeID; //終点ノード名称
    private double dnNodeX; //終点ノードX座標
    private double dnNodeY; //終点ノードY座標
    private double distance;     //距離(m) 
    private int linkCost;     //コスト（所要時間，距離など）
    private int laneNum;     //車線数
	
    public int getLinkID() {
		return linkID;
	}
	public void setLinkID(int linkID) {
		this.linkID = linkID;
	}
	public int getUpNodeID() {
		return upNodeID;
	}
	public void setUpNodeID(int upNodeID) {
		this.upNodeID = upNodeID;
	}
	public double getUpNodeX() {
		return upNodeX;
	}
	public void setUpNodeX(double upNodeX) {
		this.upNodeX = upNodeX;
	}
	public double getUpNodeY() {
		return upNodeY;
	}
	public void setUpNodeY(double upNodeY) {
		this.upNodeY = upNodeY;
	}
	public int getDnNodeID() {
		return dnNodeID;
	}
	public void setDnNodeID(int dnNodeID) {
		this.dnNodeID = dnNodeID;
	}
	public double getDnNodeX() {
		return dnNodeX;
	}
	public void setDnNodeX(double dnNodeX) {
		this.dnNodeX = dnNodeX;
	}
	public double getDnNodeY() {
		return dnNodeY;
	}
	public void setDnNodeY(double dnNodeY) {
		this.dnNodeY = dnNodeY;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getLinkCost() {
		return linkCost;
	}
	public void setLinkCost(int linkCost) {
		this.linkCost = linkCost;
	}
	public int getLaneNum() {
		return laneNum;
	}
	public void setLaneNum(int laneNum) {
		this.laneNum = laneNum;
	}
 
}