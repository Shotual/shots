package com.example.milib;

/**
 * Created by guille on 16/12/17.
 */


//EN ESTA PLANTILLA VAMOS A DEFINIR NUESTROS METODOS QUE LOGINFRAGMENT VA A UTILIZAR PARA COMUNICARSE CON EL EXTERIOR
// SIEMPRE Y CUANDO SE IMPLEMENTE LOGINFRAGMENT LISTENER

public interface LoginFragmentListener {
    public void loginFragmentLoginButtonClicked(String sUser, String sPass);
    public void loginFragmentRegisterButtonClicked();
}
