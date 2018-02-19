package com.example.appexamen;import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import AsyncTasks.HttpJsonAsyncTask;
import AsyncTasks.HttpJsonAsyncTaskListener;
import DataHolder.DataHolder;
import FireBase.FireBaseAdmin;
import FireBase.FireBaseAdminListener;
import GPSAdmin.GPSTracker;
import sqllite.Contact;
import sqllite.DataBaseHandler;

public class MainActivity extends AppCompatActivity {
    public DataBaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataHolder.instance.fireBaseAdmin= new FireBaseAdmin();
        MainActivityEvents events = new MainActivityEvents(this);
        DataHolder.instance.fireBaseAdmin.setListener(events);
        databaseHandler =  new DataBaseHandler(this);


        ////_________DESCARGAR JSON DESDE PHP-MYSQL___________\\\\\\\\\\\
        HttpJsonAsyncTask httpJsonAsyncTask1=new HttpJsonAsyncTask(this);
        httpJsonAsyncTask1.setListener(events);
        String url1 = String.format("http://10.0.2.2/pruebasJSON/leeJugadores.php");
        httpJsonAsyncTask1.execute(url1);
        ////____________________________________________________\\\\\\\\\\

        ///_________GPS TRACKER SUBE CON TU NOMBRE DE TWITTER TU POSICION ACTUAL A FIREBASE___________\\\
        GPSTracker gpsTracker=new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            Log.v("SecondActivity",gpsTracker.getLatitude()+"  "+gpsTracker.getLongitude());
            // FBCoche fbcoche = new FBCoche(2017,"Cochecito","ferrari",gpsTracker.getLatitude(),gpsTracker.getLongitude(),"");


            Contact contact = null;
            try {
                contact = new Contact(DataHolder.jsonObjectTwitter.get("UserName").toString(),gpsTracker.getLatitude(),gpsTracker.getLongitude());
            } catch (JSONException e) {
                FirebaseCrash.report(new Exception("Error Contacto"));
            }
            DataHolder.instance.fireBaseAdmin.insertarenrama("/Contacts/1",contact.toMap());
        }
        else{
            gpsTracker.showSettingsAlert();
        }
        ///______________________________________________________________________________________________\\\


    }
    //____METODO PARA IR AL NAVDRAWER____\\
    public void iranav(View v){
        Intent intent = new Intent(getBaseContext(),Main2Activity.class);
        startActivity(intent);
        finish();
    }
    //___________________________________\\

}
class MainActivityEvents implements HttpJsonAsyncTaskListener, FireBaseAdminListener {
    MainActivity mainActivity;

    public MainActivityEvents(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void firebaseAdmin_registerOk(Boolean blOk) {

    }

    @Override
    public void firebaseAdmin_loginOk(Boolean blOk) {

    }

    @Override
    public void firebaseAdmin_ramaDescargada(String rama, DataSnapshot dataSnapshot) {

    }

    ///_____________AQUI SE GUARDAN LOS CONTACTOS DEL MYSQL CON EL JSON AL SQLITE______________\\\
    @Override
    public void JsonOk(String x) {
        Log.v("PRUEBA2",""+x);
        try {

            JSONObject object = new JSONObject(x); //Creamos un objeto JSON a partir de la cadena

            JSONArray json_array = object.optJSONArray("Contactos");
            for (int i = 0; i < json_array.length(); i++) {
                Log.v("PRUEBA1","ENTRA EN EL FOR"+json_array.getJSONObject(i).getString("nombre"));
                this.mainActivity.databaseHandler.addContact(new Contact(Integer.parseInt(json_array.getJSONObject(i).getString("id")),json_array.getJSONObject(i).getString("nombre"),Double.parseDouble(json_array.getJSONObject(i).getString("lat")),Double.parseDouble(json_array.getJSONObject(i).getString("lon"))));

            }

        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrash.report(new Exception("Error JSON"));
        }


        //__________SQL LITE PARA COMPROBAR QUE SE HAN SUBIDO CORRECTAMENTE___________\\
        DataBaseHandler databaseHandler =  new DataBaseHandler(mainActivity);
        List<Contact> contacts = databaseHandler.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,LAT: " + cn.getLat() + " ,LONG: " + cn.getLon();
            // Writing Contacts to log
            Log.v("TUTORIALSQLLITE ", log);
        }
        //______________________________________________________________________________\\
    }
    ///___________________________________________________________________________________________\\\
}