package FireBase;

/**
 * Created by guille on 19/2/18.
 */


import com.google.firebase.database.DataSnapshot;

public interface FireBaseAdminListener {

    public  void firebaseAdmin_registerOk(Boolean blOk);
    public void firebaseAdmin_loginOk(Boolean blOk);
    public void firebaseAdmin_ramaDescargada(String rama, DataSnapshot dataSnapshot);
}
