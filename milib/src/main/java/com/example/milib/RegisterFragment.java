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
public class RegisterFragment extends Fragment {

    public EditText etuserreg;
    public EditText etpassreg;
    public Button btnaceptar;
    public Button btncancelar;
    public RegisterFragmentListener listener ;
    public RegisterFragmentsEvents events;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public void setListener(RegisterFragmentListener listener){
        this.listener=listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("oncreateregistro","REEEEEEEEGIIIIIIIIISSSSSSTROOOOOOOOO");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        etuserreg=v.findViewById(R.id.etuserreg);
        etpassreg=v.findViewById(R.id.etpassreg);
        btnaceptar=v.findViewById(R.id.btnaceptar);
        btncancelar=v.findViewById(R.id.btncancelar);

        events = new RegisterFragmentsEvents(this);
        btncancelar.setOnClickListener(events);
        btnaceptar.setOnClickListener(events);

        return v;
    }

}
class RegisterFragmentsEvents implements  View.OnClickListener{
    RegisterFragment registerFragment;

    public RegisterFragmentsEvents(RegisterFragment registerFragment){
        this.registerFragment=registerFragment;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==this.registerFragment.btnaceptar.getId()){
            Log.v("btnAceptar","BOTON REGISTRO ACEPTADOOOOO"+registerFragment.etuserreg);
            this.registerFragment.listener.registerFragmentRegisterButtonAceptarClicked(registerFragment.etuserreg.getText().toString(),registerFragment.etpassreg.getText().toString());

        }
             else if(v.getId()==this.registerFragment.btncancelar.getId()){
            Log.v("cancelar","BOnooooooO");
                this.registerFragment.listener.registerFragmentRegisterButtonCancelarClicked();

        }else{
            Log.v("rechazo","BOnooooooO");
        }
    }
}