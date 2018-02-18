package com.example.milib;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public EditText etUsername;
    public EditText etPass;
    public Button btnLogin;
    public Button btnRegistro;
    public LoginFragmentsEvents events;
    public LoginFragmentListener listener;


    public LoginFragment() {
        // Required empty public constructor
    }

    public void setListener(LoginFragmentListener listener){
       this.listener=listener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // INFLATER DEVUELVE LA REPRESENTACION EN OBJETO DEL XML QUE SE ESTA USANDO
        // HAY QUE INTERCEPTARLO IGUALANDO A UNA VARIABLE VIEW

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername=v.findViewById(R.id.etusername);
        etPass =v.findViewById(R.id.etpass);
        btnLogin=v.findViewById(R.id.btnlogin);
        btnRegistro=v.findViewById(R.id.btnregistro);

        events = new LoginFragmentsEvents(this);
        //PASARLE EVENTS PARA QUE EL BOTON ESTE ESCUCHANDO SUS EVENTOS
        btnLogin.setOnClickListener(events);
        btnRegistro.setOnClickListener(events);

        //TODOS LOS EVENTOS QUE PASAN A LOS SEGMENTOS VISUALES HAY QUE CENTRALIZARLOS EN LA CLASE DE ABAJO
        Log.v("loco","REEEEEEEEGIIIIIIIIISSSSSSTROOOOOOOOO");
        return v;
    }


}

//CLASE PRIVADA QUE SOLO SERA ACCESIBLE DENTRO DE LOGINFRAGMENTS QUE IMPLEMENTARA ONCLICKLISTENER YA QUE TENDREMOS MAS DE UN BOTON

class LoginFragmentsEvents implements View.OnClickListener {

    private LoginFragment loginFragment;

    //HAY QUE PASARLE UNA REFERENCIA DEL FRAGMENT PRINCIPAL

    public LoginFragmentsEvents(LoginFragment fragment) {
        this.loginFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        //CUANDO LE DES A UN BOTON ME LO DIRA POR AQUI

        //CONDICION QUE CUANDO LE DES AL BOTON LOGIN O AL REGISTRO QUE TE LLEVE AL METODO DEL LISTENER Y DE ALLI AL MAINACTIVITY
        if (v.getId() == this.loginFragment.btnLogin.getId()) {
            Log.v("botonlogin","BOTON LOGIIN DADO");
            Log.v("botonlogin","BOTON LOGIIN DADO"+loginFragment.etUsername+loginFragment.etPass);
            this.loginFragment.listener.loginFragmentLoginButtonClicked(this.loginFragment.etUsername.getText().toString(), this.loginFragment.etPass.getText().toString());
        }else if (v.getId() == this.loginFragment.btnRegistro.getId()) {
            this.loginFragment.listener.loginFragmentRegisterButtonClicked();
            Log.v("registro","REEEEEEEEGIIIIIIIIISSSSSSTROOOOOOOOO");
        }
    }
}