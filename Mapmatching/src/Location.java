import java.sql.Timestamp;

class Location {            //読み込みを行なうクラス
	private int locationID;//ロケーションID
	private String personalID;//モニターID
	private Timestamp time;//測位時刻
	private float lat;//緯度
	private float lon;//経度
	private float x;//直交座標系X座標
	private float y;//直交座標系Y座標
	private float fixX;//補正X座標
	private float fixY;//補正Y座標
//	private int mode;//出発地に最も近いリンク
	private boolean move;//移動滞在判別
	
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getPersonalID() {
		return personalID;
	}
	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public double getLat() {
		return lat;
	}
	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move = move;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getFixX() {
		return fixX;
	}
	public void setFixX(float fixX) {
		this.fixX = fixX;
	}
	public float getFixY() {
		return fixY;
	}
	public void setFixY(float fixY) {
		this.fixY = fixY;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}

}