package hofbo.tactical_material_game;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FIREBASEAUTH = "FIREBASEAUTH";

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btn_register:
                    register();
                    break;
                case R.id.btn_login:
                    login();
                    break;
                case R.id.btn_logout:
                    logout();
                    break;

            }
        }
    };


    // TODO: Rename and change types of parameters
    private FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(FirebaseAuth mAuth) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Button registerButton = (Button) view.findViewById(R.id.btn_register);
        Button loginButton = (Button) view.findViewById(R.id.btn_login);
        Button logoutButton = (Button) view.findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(mOnClickListener);
        loginButton.setOnClickListener(mOnClickListener);
        registerButton.setOnClickListener(mOnClickListener);

        return view;
    }


    // Tegistrieren
    public void register() {
        EditText login_email = (EditText) getActivity().findViewById(R.id.login_email);
        EditText login_password = (EditText) getActivity().findViewById(R.id.login_password);
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Snackbar snackbar = Snackbar
                                    .make(getActivity().findViewById(R.id.Fragment_Container), "Registriert", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }

                        // ...
                    }
                });
    }
    //Login
    public void login (){
        EditText login_email = (EditText) getActivity().findViewById(R.id.login_email);
        EditText login_password = (EditText) getActivity().findViewById(R.id.login_password);
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Snackbar snackbar = Snackbar
                                    .make(getActivity().findViewById(R.id.Fragment_Container), "Auth Fail", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }

                        // ...
                    }
                });


    }

    //Logout
    public void logout(){
        mAuth.signOut();
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
