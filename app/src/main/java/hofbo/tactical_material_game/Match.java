package hofbo.tactical_material_game;

import android.util.Log;
import android.view.View;
import android.support.v7.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import hofbo.tactical_material_game.Fragments.GameFragment;

/**
 * Created by Bodi on 07.02.2018.
 */

public class Match {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private View view;
    private String lobby;
    private CardView[][] playground;

    public Match(View view, String lobby) {
        this.view = view;
        this.lobby = lobby;
        this.playground = fillPlayground();
    }

    private CardView[][] fillPlayground() {

        CardView[][] playground = new CardView[6][6];
        ArrayList<CardView> cardViewList = new ArrayList<>();

        cardViewList.add((CardView) view.findViewById(R.id.game_field_1));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_2));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_3));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_4));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_5));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_6));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_7));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_8));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_9));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_10));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_11));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_12));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_13));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_14));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_15));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_16));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_17));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_18));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_19));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_20));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_21));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_22));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_23));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_24));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_25));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_26));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_27));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_28));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_29));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_30));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_31));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_32));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_33));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_34));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_35));
        cardViewList.add((CardView) view.findViewById(R.id.game_field_36));

        for (int i = 0; i < 6; i++) {
            for (int z = 0; z < 6; z++) {
                //Add the CardView objects in the two dimensional array automatically
                playground[i][z] = cardViewList.get(0 + (i * 6) + z);
            }

        }
        return playground;
    }

    private void setStartPos() {

        //Player 1
        playground[0][0].setCardBackgroundColor(1);
        playground[0][1].setCardBackgroundColor(2);
        playground[0][2].setCardBackgroundColor(3);

        //Player 2
        playground[5][5].setCardBackgroundColor(1);
        playground[5][4].setCardBackgroundColor(2);
        playground[5][3].setCardBackgroundColor(3);

    }

    public void start() {
        setStartPos();
        listenFields();
        Log.d("Match!!!", "start:" + lobby);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int id = view.getId();

            CardView c = (CardView) view.findViewById(id);
            c.setCardBackgroundColor(4);


        }
    };

    private void listenFields() {
        for (int i = 0; i < 6; i++) {
            for (int z = 0; z < 6; z++) {
                //Add the CardView objects in the two dimensional array automatically
                playground[i][z].setOnClickListener(mOnClickListener);
            }
        }
    }

}
