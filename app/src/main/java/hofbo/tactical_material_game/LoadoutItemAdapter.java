
package hofbo.tactical_material_game;

        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;

/**
 * Created by Deniz on 14.10.2017.
 */


public class LoadoutItemAdapter extends RecyclerView.Adapter<LoadoutItemAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private LoadoutItemAdapter[] mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView shipID;
        private final TextView shipName;
        private final TextView shipLive;
        private final TextView shipAgility;
        private final TextView shipPower;
        private final TextView shipRange;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            shipID = v.findViewById(R.id.high_player);
            shipName = v.findViewById(R.id.loadout_ship_name);
            shipLive = v.findViewById(R.id.loadout_items_live);
            shipAgility = v.findViewById(R.id.loadout_items_agility);
            shipPower = v.findViewById(R.id.loadout_items_power);
            shipRange = v.findViewById(R.id.loadout_items_range);
        }

        public TextView getShipID() {
            return shipID;
        }
        public TextView getShipName() {
            return shipName;
        }
        public TextView getShipLive() {
            return shipLive;
        }
        public TextView getShipAgility() {
            return shipAgility;
        }
        public TextView getShipPower() {
            return shipPower;
        }
        public TextView getShipRange() {
            return shipRange;
        }



    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public LoadoutItemAdapter(LoadoutItemAdapter[] dataSet) {
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

        //viewHolder.getName().setText(mDataSet[position].name);
        //viewHolder.getScore().setText(mDataSet[position].score);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}