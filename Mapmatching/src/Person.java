import java.util.*;

class Person{
	private String personalID;//ID
	private List<Trip> tripList = new ArrayList<Trip>();
	private List<Location> locationList = new ArrayList<Location>();
	public String getPersonalID() {
		return personalID;
	}
	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}
	public List<Trip> getTripList() {
		return tripList;
	}
	public void setTripList(List<Trip> tripList) {
		this.tripList = tripList;
	}
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	
}