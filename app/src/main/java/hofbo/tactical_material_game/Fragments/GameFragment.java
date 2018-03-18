package hofbo.tactical_material_game.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import hofbo.tactical_material_game.Lobby;
import hofbo.tactical_material_game.Match;
import hofbo.tactical_material_game.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ValueEventListener lobbylist;
    ValueEventListener lobbyMatch;
    View view;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int id = view.getId();
            switch (id) {
                case R.id.FindGame:
                    final DatabaseReference mDatabase = db.getReference("lobby");

                    lobbylist = mDatabase.addValueEventListener(new ValueEventListener() {
                        public boolean stop = false;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (stop == false) {
                                for (DataSnapshot snapshotroot : dataSnapshot.getChildren()) {
                                    for (DataSnapshot snapshot : snapshotroot.getChildren()) {
                                        String tmp = "";
                                        if ("player1".equals(snapshot.getKey())) {
                                            tmp = snapshot.getValue().toString();


                                            Log.d("GameFragment", tmp);
                                            String tmpUID = mAuth.getUid();
                                            if (!tmp.equals(tmpUID)) {
                                                Map<String, String> p2 = new HashMap<>();
                                                p2.put("player1", tmp);
                                                p2.put("player2", mAuth.getUid());
                                                mDatabase.child(tmp).setValue(p2);

                                                EventDestroy(lobbylist, mDatabase);

                                                MatchListener(tmp, tmp);


                                                stop = true;
                                            }
                                        }
                                    }
                                }
                            }

                            if (dataSnapshot.hasChildren() == false) {
                                Lobby lobby = new Lobby();
                                lobby.createLobby(mAuth.getUid());
                                MatchListener(mAuth.getUid(), mAuth.getUid());
                                EventDestroy(lobbylist, mDatabase);
                            }


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    break;


            }
        }
    };

    public void EventDestroy(ValueEventListener lobbylist, DatabaseReference mDatabase) {
        mDatabase.removeEventListener(lobbylist);


    }


    public void MatchListener(String MatchID, final String Player1) {

        final DatabaseReference mDatabaseMatch = db.getReference("lobby/" + MatchID);
        final DatabaseReference mDatabaseGame = db.getReference("match/" + MatchID);

        lobbyMatch = mDatabaseMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String player1 = "";
                for (DataSnapshot snapshotroot : dataSnapshot.getChildren()) {
                    if (snapshotroot.getKey().equals("player1") == true){
                        player1 = snapshotroot.getValue().toString();
                    }
                    if (snapshotroot.getKey().equals("player2") && snapshotroot.getValue() != "") {
                        if (Player1.equals(mAuth.getUid())) {
                            Map<String, Object> p2 = new HashMap<>();
                            p2.put("player1", Player1);
                            p2.put("player2", snapshotroot.getValue());
                            p2.put("shipsPlayer1", "111");
                            p2.put("shipsPlayer2", "111");
                            p2.put("activePlayer", 1);
                            p2.put("round", "");
                            p2.put("playground", "");
                            mDatabaseMatch.removeValue();
                            mDatabaseGame.setValue(p2);
                            EventDestroy(lobbyMatch, mDatabaseGame);

                            Match match  = new Match(view, mAuth.getUid());
                            match.start();

                        } else {
                            Match match = new Match(view, player1);
                            match.start();
                            EventDestroy(lobbyMatch, mDatabaseGame);


                        }
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_, container, false);
        view.findViewById(R.id.FindGame).setOnClickListener(mOnClickListener);

        this.view = view;
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
