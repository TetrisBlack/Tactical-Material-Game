
package hofbo.tactical_material_game;

        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * Created by Deniz on 14.10.2017.
 */


public class LoadoutItemAdapter extends RecyclerView.Adapter<LoadoutItemAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ShipStat[] mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView shipID;
        private final TextView shipName;
        private final ProgressBar shipLive;
        private final ProgressBar shipAgility;
        private final ProgressBar shipPower;
        private final ProgressBar shipRange;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    //TODO: einstellungen des Loadout an die datenbank übergeben


                    db.collection("users").document(mAuth.getUid()).collection("loadout").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot document = task.getResult();
                                if (document != null) {
                                    Log.d(TAG, "DocumentSnapshot data: " + task.getResult().size());
                                    if(task.getResult().size() < 3){

                                        Map<String, Object> ship = new HashMap<>();
                                        ship.put("shipID", getAdapterPosition());
                                        db.collection("users").document(mAuth.getUid()).collection("loadout").add(ship).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("LoadoutItemAdapter","DB write successful");

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, e.toString());
                                            }
                                        });



                                    }
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });




                    if(v.findViewById(R.id.fragment_loadout_selship1_button_img) != null) {
                        if(v.findViewById(R.id.fragment_loadout_selship2_button_img) != null){
                            if(v.findViewById(R.id.fragment_loadout_selship3_button_img) != null){




                            }
                        }
                    }






                }
            });
            //shipID = v.findViewById(R.id.high_player);
            shipName = v.findViewById(R.id.loadout_ship_name);
            shipLive = v.findViewById(R.id.loadout_prog_live);
            shipAgility = v.findViewById(R.id.loadout_prog_agility);
            shipPower = v.findViewById(R.id.loadout_prog_power);
            shipRange = v.findViewById(R.id.loadout_prog_range);
        }

        //public TextView getShipID() {
        //    return shipID;
        //}
        public TextView getShipName() {
            return shipName;
        }
        public ProgressBar getShipLive() {
            return shipLive;
        }
        public ProgressBar getShipAgility() {
            return shipAgility;
        }
        public ProgressBar getShipPower() {
            return shipPower;
        }
        public ProgressBar getShipRange() {
            return shipRange;
        }



    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public LoadoutItemAdapter(ShipStat[] dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.loadout_row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.getShipName().setText(mDataSet[position].shipName);
        //viewHolder.getShipID().setText(mDataSet[position].shipID);
        viewHolder.getShipLive().setProgress(mDataSet[position].shipLive);
        viewHolder.getShipPower().setProgress(mDataSet[position].shipPower);
        viewHolder.getShipAgility().setProgress(mDataSet[position].shipAgility);
        viewHolder.getShipRange().setProgress(mDataSet[position].shipRange);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}