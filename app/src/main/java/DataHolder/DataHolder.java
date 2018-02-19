package DataHolder;

import org.json.JSONObject;

import FireBase.FireBaseAdmin;


/**
 * Created by guille on 17/2/18.
 */

public class DataHolder {
    public static  DataHolder instance = new DataHolder();
    public static JSONObject jsonObjectTwitter;
    public FireBaseAdmin fireBaseAdmin;
    public DataHolder(){
        fireBaseAdmin = new FireBaseAdmin();
    }
}
