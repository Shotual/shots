package com.example.guille.appexamentest;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.JsonParser;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Asyntasks.HttpJsonAsyncTask;
import Asyntasks.HttpJsonAsyncTaskListener;
import DataHolder.DataHolder;
import FireBase.FireBaseAdmin;
import FireBase.FireBaseAdminListener;
import sqllite.Contact;
import sqllite.DataBaseHandler;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Map<String,Contact> contactos;
    TextView name,email;
    ImageView imgPerfil;
    SupportMapFragment mapFragment;
    String s;
    public DataBaseHandler databaseHandler;

    public void setS(String s) {
        this.s = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder.instance.fireBaseAdmin= new FireBaseAdmin();
        MainActivityEvents events = new MainActivityEvents(this);

        DataHolder.instance.fireBaseAdmin.setListener(events);
        DataHolder.instance.fireBaseAdmin.descargarYObservarRama("Contactos");


        HttpJsonAsyncTask httpJsonAsyncTask1=new HttpJsonAsyncTask(this);
        httpJsonAsyncTask1.setListener(events);
        String url1 = String.format("http://10.0.2.2/pruebasJSON/leeJugadores.php");
        httpJsonAsyncTask1.execute(url1);

         databaseHandler =  new DataBaseHandler(this);
        //
        Contact contacttemp=databaseHandler.getContact(1);

        Log.v("id","NOMBREE ---->"+contacttemp.getName());

      List<Contact> contacts = databaseHandler.getAllContacts();
        Log.v("TUTORIALSQLLITE","CONCTACTOS---------->>"+contacts.size());
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,LAT: " + cn.getLat() + " ,LONG: " + cn.getLon();
            // Writing Contacts to log
            Log.v("TUTORIALSQLLITE ", log);
        }
        Log.v("PRUEBA1","PRUUUEBA"+s);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


}

class MainActivityEvents implements FireBaseAdminListener, HttpJsonAsyncTaskListener{
    MainActivity mainActivity;

    public MainActivityEvents(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }

    @Override
    public void firebaseAdmin_registerOk(Boolean blOk) {

    }

    @Override
    public void firebaseAdmin_loginOk(Boolean blOk) {

    }

    @Override
    public void firebaseAdmin_ramaDescargada(String rama, DataSnapshot dataSnapshot) {

        Log.v("RAMA",rama+"--------RAMA DESCARGADA............"+dataSnapshot);

        if(rama.equals("Noticias")){

            GenericTypeIndicator<ArrayList<Contact>> indicator=new GenericTypeIndicator<ArrayList<Contact>>(){};
            ArrayList<Contact> noticias=dataSnapshot.getValue(indicator);
            //VALUES NO ES UN ARRAY LIST ES UN COLLECTIONS
            Log.v("noticias","noticias CONTIENE: "+noticias);

            //PARA TRANSFORMAR UN COLLECTION A UN ARRAY LIST HAY QUE HACER es new ArrayList<Mensaje>(msg.values())

        }
        else{
            Log.v("noticias","ERROR");
        }

    }


    @Override
    public void JsonOk(String x) {
        Log.v("PRUEBA2",""+x);
        try {

            JSONObject object = new JSONObject(x); //Creamos un objeto JSON a partir de la cadena

            JSONArray json_array = object.optJSONArray("Contactos");
            for (int i = 0; i < json_array.length(); i++) {
               this.mainActivity.databaseHandler.addContact(new Contact(json_array.getJSONObject(i).getString("nombre"),5.2,2.4));
                Log.v("PRUEBA1","ENTRA EN EL FOR"+json_array.getJSONObject(i).getString("nombre"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}