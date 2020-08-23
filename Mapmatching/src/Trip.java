import java.util.*;
import java.sql.Timestamp;

class Trip{
	private int tripID;//ID
	private int startNodeID;//ID
	private int endNodeID;//ID
	private int order;//ID
	private double pathLength;//経路距離
	private String personalID;//ID
	private Timestamp depatureTime;//経路の開始点
	private Timestamp arrivalTime;//経路の開始点
	private int mode;//出発地に最も近いリンク
	private List<Location> locationList = new ArrayList<Location>();
	private List<Integer> pathList = new ArrayList<Integer>();

    public int getTripID() {
		return tripID;
	}
	public void setTripID(int tripID) {
		this.tripID = tripID;
	}
	public int getStartNodeID() {
		return startNodeID;
	}
	public void setStartNodeID(int startNodeID) {
		this.startNodeID = startNodeID;
	}
	public int getEndNodeID() {
		return endNodeID;
	}
	public void setEndNodeID(int endNodeID) {
		this.endNodeID = endNodeID;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public double getPathLength() {
		return pathLength;
	}
	public void setPathLength(double pathLength) {
		this.pathLength = pathLength;
	}
	public String getPersonalID() {
		return personalID;
	}
	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}
	public Timestamp getDepatureTime() {
		return depatureTime;
	}
	public void setDepatureTime(Timestamp depatureTime) {
		this.depatureTime = depatureTime;
	}
	public Timestamp getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	public List<Integer> getPathList() {
		return pathList;
	}
	public void setPathList(List<Integer> pathList) {
		this.pathList = pathList;
	}

}