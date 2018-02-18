package FireBase;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by guille on 17/2/18.
 */

public interface FireBaseAdminListener {

    public  void firebaseAdmin_registerOk(Boolean blOk);
    public void firebaseAdmin_loginOk(Boolean blOk);
    public void firebaseAdmin_ramaDescargada(String rama, DataSnapshot dataSnapshot);
}
