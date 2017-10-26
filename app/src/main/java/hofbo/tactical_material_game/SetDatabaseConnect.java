package hofbo.tactical_material_game;


import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by Deniz on 15.10.2017.
 */


public class SetDatabaseConnect {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public SetDatabaseConnect(){

    }


    public void setUser(String username, String email,String uid, String profilePhotoURL){
       DatabaseReference mDataRef = mDatabase.getReference();
       FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            User newUser = new User(username,email,uid,profilePhotoURL);
            mDataRef.child("users").child(uid).setValue(newUser);
        }

    }

    public User getUser(String uid){
        User user = new User();
       final DatabaseReference mDataRef = mDatabase.getReference();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    Log.v(TAG,""+ childDataSnapshot.child("username").getValue());   //gives the value for given keyname
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

return null;


    }





}
@IgnoreExtraProperties
class User {

    public String username;
    public String email;
    public String profilePhotoURL;
    public String uid;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String uid, String profilePhotoURL ) {
        this.username = username;
        this.email = email;
        this.uid = uid;
        this.profilePhotoURL = profilePhotoURL;
    }

}
