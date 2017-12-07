package hofbo.tactical_material_game.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.firestore.CollectionReference;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Callable;


import hofbo.tactical_material_game.LoadoutItemAdapter;
import hofbo.tactical_material_game.R;

import hofbo.tactical_material_game.ShipStat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoadoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoadoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Map<String, Object>  ships =  new HashMap<>();
    ArrayList<Map<String,Object>> shipListe = new ArrayList<>();


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int id = view.getId();
            switch (id) {
                case R.id.fragment_loadout_selship1_button:

                    db.collection("users")
                            .document(mAuth.getUid()).collection("loadout").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots){
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                documentSnapshot.getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d("DELETE",task.toString());

                                        }else{
                                            Log.d("DELETE",task.toString());
                                        }
                                    }
                                });

                            }
                        }
                    });


                    break;

                case R.id.fragment_loadout_selship2_button:
                    db.collection("users").document("loadout").delete();
                    break;

                case R.id.fragment_loadout_selship3_button:
                    db.collection("users").document("loadout").delete();

                    break;




            }
        }
    };




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoadoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoadoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadoutFragment newInstance(String param1, String param2) {
        LoadoutFragment fragment = new LoadoutFragment();
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
        View view = inflater.inflate(R.layout.fragment_loadout, container, false);

        view.findViewById(R.id.fragment_loadout_selship1_button).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.fragment_loadout_selship2_button).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.fragment_loadout_selship3_button).setOnClickListener(mOnClickListener);

        final RecyclerView rv = view.findViewById(R.id.fragment_loadout_listview);
        rv.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        ShipStat[] data =  new ShipStat[3];

        db.collection("ships")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<ShipStat> newsItems = new ArrayList<ShipStat>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("Loadout:", document.getId() + " => " + document.getData());
                                newsItems.add(new ShipStat(document.getId(),document.getString("shipName"),document.getDouble("shipLive"),document.getDouble("shipAgility"),document.getDouble("shipPower"), document.getDouble("shipRange")));

                            }

                            ShipStat[] readyNewsItems = newsItems.toArray(new ShipStat[newsItems.size()]);



                            LoadoutItemAdapter mAdapter = new LoadoutItemAdapter(readyNewsItems);

                            rv.setAdapter(mAdapter);
                            rv.setItemAnimator(new DefaultItemAnimator());


                        } else {
                            Log.w("LOADOUT:", "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("users")
                .document(mAuth.getUid()).collection("loadout")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("test", "Listen failed.", e);
                            return;
                        }


                        for (DocumentSnapshot doc : value) {
                            if (doc.get("shipRef") != null) {
                                //ships.add(doc.getReference());

                                Log.d("test", doc.getReference().toString());
                                try {
                                    DocumentReference test = doc.getDocumentReference("shipRef");

                                    test.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                if(task.getResult().exists()) {
                                                    ships = task.getResult().getData();
                                                    Log.d("JA TEST", ships.toString());
                                                    shipListe.add(ships);
                                                }
                                            }else{

                                            }
                                            updateUI(shipListe);

                                        }
                                    });

                                }catch (Exception el){


                                }

                            }
                        }
                        shipListe.clear();
                        Log.d("test", "Current ShipID" );
                    }});







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

    public void updateUI(ArrayList<Map<String,Object>> ships){
        try {
            Button btn1 = getView().findViewById(R.id.fragment_loadout_selship1_button);
            Button btn2 = getView().findViewById(R.id.fragment_loadout_selship2_button);
            Button btn3 = getView().findViewById(R.id.fragment_loadout_selship3_button);


            int counter = 0;
            for(Map<String,Object> entry: ships){
                switch (counter){
                    case 0:
                        btn1.setText(entry.get("shipName").toString());
                        counter ++;

                        break;
                    case 1:
                        btn2.setText(entry.get("shipName").toString());
                        counter ++;

                        break;

                    case 2:
                        btn3.setText(entry.get("shipName").toString());
                        counter ++;

                        break;
                    default:

                }




            };
            shipListe.clear();

        }catch (Exception e){

        }




    };



}
