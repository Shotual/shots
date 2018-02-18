package com.example.guille.appexamentest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import DataHolder.DataHolder;
import FireBase.FireBaseAdmin;



public class TwitterLActivity extends AppCompatActivity {



    private TwitterLoginButton twitterLoginButton;
    Animation show_fab1;
    Button fab1;

    //JSON utilizado para almacenar los datos del usuario para posteriormente
    // poder pasarselos al DataHolder para usarlos en otra Activity
    private JSONObject datosTwitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataHolder.instance.fireBaseAdmin = new FireBaseAdmin();
        // Inicializamos Twitter despues de añadir el boton propio de Twitter
        // al XML y añadir las dependencias al build gradle
        Twitter.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fab1 =(Button)findViewById(R.id.btnanim);
        show_fab1= AnimationUtils.loadAnimation(this,R.anim.fab1_show);




        //Boton de Twitter.
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                datosTwitter = new JSONObject();
                try {
                    datosTwitter.put("UserName", result.data.getUserName().toString());
                    datosTwitter.put("id", String.valueOf(result.data.getUserId()));
                } catch (JSONException e) {
                    FirebaseCrash.report(new Exception("CREATE JSON WITH USER DATA FAIL"));
                    e.printStackTrace();
                }
                Log.v("USERNAME TWITTER", result.data.getUserName().toString());
                handleTwitterSession(result.data);

            }

            @Override
            public void failure(TwitterException exception) {
                FirebaseCrash.report(new Exception("USER FAIL AT LOG IN"));
            }
        });



    }

    public void buttonClicked(View v){
        fab1.startAnimation(show_fab1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d("Sesion Iniciada Twitter", "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        DataHolder.instance.fireBaseAdmin.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(TwitterLActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TwitterSuccess", "signInWithCredential:success");
                            DataHolder.instance.fireBaseAdmin.user = DataHolder.instance.fireBaseAdmin.mAuth.getCurrentUser();
                            //updateUI(user);
                            // getUserDatails();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TwitterFailure", "signInWithCredential:failure", task.getException());
                            Toast.makeText(TwitterLActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

        //Almacenamos los datos deL USUAIRO DE tWITTER
        DataHolder.instance.jsonObjectTwitter = datosTwitter;

        //CAMBIO DE ACTIVIDAD UNA VEZ OBTENIDO LOS RESULTADOS
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }





}
