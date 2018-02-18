package sqllite;

/**
 * Created by guille on 17/2/18.
 */



public class Contact {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    double lat,lon;
   // Marker perfilMarker;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _phone_number){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this.lat=lat;
        this.lon=lon;
    }


    // constructor
    public Contact(String name, String _phone_number,double lat,double lon){
        this._name = name;
        this._phone_number = _phone_number;
        this.lat=lat;
        this.lon=lon;

    }


  //  public void setMarkerEnsalada(Marker markerEnsalada){
//        this.perfilMarker = markerEnsalada;
  //  }
    //public Marker getMarkerEnsalada(){
      //  return perfilMarker;
    //}

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


}