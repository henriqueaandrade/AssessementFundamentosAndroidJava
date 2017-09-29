package infnet.edu.br.assessementfundamentosandroidjava.config;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class FirebaseConfiguration {
    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;

    public static DatabaseReference getFirebase() {
        if ( databaseReference == null ) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if ( firebaseAuth == null ) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

}
