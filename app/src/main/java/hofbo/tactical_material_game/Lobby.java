package hofbo.tactical_material_game;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deniz on 01.02.2018.
 */



public class Lobby {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = db.getReference();
    public Lobby() {



    }



    public Lobby(String player1, String player2){



    }

    public void createLobby(String player1){
        Lobby lobby = new Lobby();



       DatabaseReference pushedPostRef = mDatabase.child("looby").push();

       String postId = pushedPostRef.getKey();

       Log.d("LOBBY:",postId);

       Map<String, String> lobbys = new HashMap<>();
       lobbys.put("player1", player1);
       mDatabase.child("lobby").child(player1).setValue(lobbys);

    }

}
