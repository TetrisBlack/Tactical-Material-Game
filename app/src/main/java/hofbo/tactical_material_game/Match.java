package hofbo.tactical_material_game;

import android.util.Log;
import android.view.View;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Bodi on 07.02.2018.
 */

public class Match {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private View view;
    private String lobby;
    private CardView[][] playground;
    private String TAG = "MATCH";
    private Integer spieler;
    private MatchClass match;
    private Integer ActiveSteps = 0;
    private DatabaseReference mDatabase;
    private ImageView tmpView;

    public Match(View view, String lobby) {
        this.view = view;
        this.lobby = lobby;
        this.playground = fillPlayground();
        mDatabase = db.getReference("match").child(this.lobby);
    }

    private CardView[][] fillPlayground() {

        CardView[][] playground = new CardView[6][6];
        ArrayList<CardView> cardViewList = new ArrayList<>();

        //region GameFields
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
        //endregion

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
        initMatchListener();
        Log.d(TAG, "start:" + lobby);
    }

    public void initMatchListener() {


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ValueEventListener lobbylist;

        lobbylist = mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    try {
                        match = dataSnapshot.getValue(MatchClass.class);
                        if (match.player1 == mAuth.getUid() && spieler == null) {
                            spieler = 1;
                        } else if (spieler == null) {
                            spieler = 2;
                        }

                        if (match.playground != null && match.activePlayer == spieler) {
                            rebuildEnemy(match.playground);
                        }

                    } catch (Exception e) {
                        Log.d(TAG, "onDataChange: " + e.toString());
                    }


                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private View tmpCard;
    private int x;
    private int y;


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View view_onclick) {

            ImageView p_1;
            ImageView p_2;
            ImageView p_3;


            if (match.player1.equals(mAuth.getUid()))  {
                p_1 = view.findViewById(R.id.p1_1);
                p_2 = view.findViewById(R.id.p1_2);
                p_3 = view.findViewById(R.id.p1_3);


            } else {
                p_1 = view.findViewById(R.id.p2_1);
                p_2 = view.findViewById(R.id.p2_2);
                p_3 = view.findViewById(R.id.p2_3);

            }


            //BruteForce x and y

            for (int i = 0; i < 6; i++) {
                for (int z = 0; z < 6; z++) {
                    if (playground[i][z] == view) {
                        x = i;
                        y = z;


                    }

                }
            }


            if (match.activePlayer == spieler) {
                int id = 0;
                int parentId_1 = 0;
                int parentId_2 = 0;
                int parentId_3 = 0;

                ViewGroup parent1 = null;
                ViewGroup parent2 = null;
                ViewGroup parent3 = null;


                try {
                    id = view_onclick.getId();
                    parentId_1 = ((View) p_1.getParent()).getId();
                    parentId_2 = ((View) p_2.getParent()).getId();
                    parentId_3 = ((View) p_3.getParent()).getId();

                    parent1 = (ViewGroup) p_1.getParent();
                    parent2 = (ViewGroup) p_2.getParent();
                    parent3 = (ViewGroup) p_3.getParent();


                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e.toString());
                }

                Integer flag = 0;
                if (tmpView != null) {
                    ViewGroup imageSetter = (ViewGroup) view_onclick;
                    imageSetter.addView(tmpView);
                    tmpView = null;
                    flag = 1;
                    ActiveSteps++;


                }


                if (parentId_1 == id && tmpView == null && flag == 0) {

                    CardView c = (CardView) view.findViewById(id);
                    c.setCardBackgroundColor(4);

                    tmpView = p_1;
                    parent1.removeView(p_1);


                } else if (parentId_2 == id && tmpView == null && flag == 0) {
                    CardView c = (CardView) view.findViewById(id);
                    c.setCardBackgroundColor(4);

                    tmpView = p_2;
                    parent2.removeView(p_2);


                } else if (parentId_3 == id && tmpView == null && flag == 0) {
                    CardView c = (CardView) view.findViewById(id);
                    c.setCardBackgroundColor(4);

                    tmpView = p_3;
                    parent3.removeView(p_3);


                }

                if (ActiveSteps > 2) {
                    if (match.activePlayer == 1) {
                        match.activePlayer = 2;
                    } else if (match.activePlayer == 2) {
                        match.activePlayer = 1;
                    }

                    try {

                        if (match.player1.equals(mAuth.getUid())) {
                            p_1 = view.findViewById(R.id.p1_1);
                            p_2 = view.findViewById(R.id.p1_2);
                            p_3 = view.findViewById(R.id.p1_3);


                        } else {
                            p_1 = view.findViewById(R.id.p2_1);
                            p_2 = view.findViewById(R.id.p2_2);
                            p_3 = view.findViewById(R.id.p2_3);

                        }

                        String p1S = bruteForceFields((View) p_1.getParent());
                        String p2S = bruteForceFields((View) p_2.getParent());
                        String p3S = bruteForceFields((View) p_3.getParent());

                        match.playground = p1S.toString() + p2S.toString() + p3S.toString();


                        mDatabase.setValue(match);
                        ActiveSteps = 0;

                    } catch (Exception e) {
                        Log.d(TAG, "onClick: " + e.toString());
                    }


                }
            }


        }
    };

    private void listenFields() {
        for (int i = 0; i < 6; i++) {
            for (int z = 0; z < 6; z++) {
                //Add the CardView objects in the two dimensional array automatically
                x = i;
                y = z;
                playground[i][z].setOnClickListener(mOnClickListener);
            }
        }
    }

    private String bruteForceFields(View viewObj) {
        try {
            for (int i = 0; i < 6; i++) {
                for (int z = 0; z < 6; z++) {
                    if (playground[i][z] == viewObj) {
                        String tmp = String.valueOf(i) + String.valueOf(z);
                        return tmp;


                    }

                }
            }
        } catch (Exception e) {
            Log.d(TAG, "bruteForceFields: " + e.toString());

        }
        return "";
    }

    private void rebuildEnemy(String fld) {
        int p1x = Integer.parseInt(fld.charAt(0) + "");
        int p2x = Integer.parseInt(fld.charAt(2) + "");
        int p3x = Integer.parseInt(fld.charAt(4) + "");
        int p1y = Integer.parseInt(fld.charAt(1) + "");
        int p2y = Integer.parseInt(fld.charAt(3) + "");
        int p3y = Integer.parseInt(fld.charAt(5) + "");

        ImageView p_1;
        ImageView p_2;
        ImageView p_3;

        String tmpID = mAuth.getUid();
        String tmpID2 = match.player1;
        if (tmpID2.equals(tmpID) == true) {
            p_1 = view.findViewById(R.id.p2_1);
            p_2 = view.findViewById(R.id.p2_2);
            p_3 = view.findViewById(R.id.p2_3);


        } else {
            p_1 = view.findViewById(R.id.p1_1);
            p_2 = view.findViewById(R.id.p1_2);
            p_3 = view.findViewById(R.id.p1_3);

        }

        ViewGroup parent1 = playground[Integer.parseInt(bruteForceFields((View) p_1.getParent()).charAt(0)+"")][Integer.parseInt(bruteForceFields((View) p_1.getParent()).charAt(1)+"")];
        ViewGroup parent2 = playground[Integer.parseInt(bruteForceFields((View) p_2.getParent()).charAt(0)+"")][Integer.parseInt(bruteForceFields((View) p_2.getParent()).charAt(1)+"")];
        ViewGroup parent3 = playground[Integer.parseInt(bruteForceFields((View) p_3.getParent()).charAt(0)+"")][Integer.parseInt(bruteForceFields((View) p_3.getParent()).charAt(1)+"")];


        parent1.removeView(p_1);
        parent2.removeView(p_2);
        parent3.removeView(p_3);

        playground[p1x][p1y].addView(p_1);
        playground[p2x][p2y].addView(p_2);
        playground[p3x][p3y].addView(p_3);


    }


}
