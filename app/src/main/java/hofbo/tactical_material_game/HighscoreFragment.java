package hofbo.tactical_material_game;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HighscoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HighscoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HighscoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HighscoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HighscoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HighscoreFragment newInstance(String param1, String param2) {
        HighscoreFragment fragment = new HighscoreFragment();
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
        View v = inflater.inflate(R.layout.fragment_highscore_, container, false);


        //Tab inialisation
        TabHost host = v.findViewById(R.id.highscoretabhost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.high_tab_1);
        spec.setIndicator(getString(R.string.highglobal));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.high_tab_2);
        spec.setIndicator(getString(R.string.highself));
        host.addTab(spec);

        //Global list
        final RecyclerView rv = v.findViewById(R.id.high_card_list);
        rv.setHasFixedSize(true);
        final Activity c = getActivity();
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        HighScoreItemAdapter mAdapter = new HighScoreItemAdapter(createList(2323));
        rv.setAdapter(mAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        //User list
        final RecyclerView rvu = v.findViewById(R.id.high_card_list_user);
        rvu.setHasFixedSize(true);
        final LinearLayoutManager llmu = new LinearLayoutManager(getActivity());
        llmu.setOrientation(LinearLayoutManager.VERTICAL);
        rvu.setLayoutManager(llmu);

        HighScoreItemAdapter uAdapter = new HighScoreItemAdapter(createList(30));
        rvu.setAdapter(uAdapter);
        rvu.setItemAnimator(new DefaultItemAnimator());

        return v;
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

    private HighScoreItems[] createList(int size) {

        HighScoreItems[] result = new HighScoreItems[size];
        for (int i=0;i < size; i++) {

            result[i] = new HighScoreItems("Player " + i,"" + (size - i)*13);


        }

        return result;
    }
}


