package hofbo.tactical_material_game.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import hofbo.tactical_material_game.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FIREBASEAUTH = "FIREBASEAUTH";
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

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
                case R.id.btn_login_with_google:
                    signIn();
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

        if (mAuth.getCurrentUser() != null) {
            updateUI(true, getView(), mAuth);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        view.findViewById(R.id.btn_register).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_login).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_logout).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_login_with_google).setOnClickListener(mOnClickListener);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Snackbar.make(getActivity().findViewById(R.id.Fragment_Container), "Google Authentication failed.",
                                    Snackbar.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    // Registrieren
    public void register() {
        EditText login_email = (EditText) getActivity().findViewById(R.id.login_email);
        EditText login_password = (EditText) getActivity().findViewById(R.id.login_password);
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                               Snackbar.make(getActivity().findViewById(R.id.Fragment_Container), "Successfully registered", Snackbar.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user .
                            Snackbar.make(getActivity().findViewById(R.id.Fragment_Container), "Registering failed.",
                                    Snackbar.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

    //Login
    public void login() {
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
                        if (task.isSuccessful()) {
                            Snackbar.make(getActivity().findViewById(R.id.Fragment_Container), "Authentication successful", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(getActivity().findViewById(R.id.Fragment_Container), "Authentication failed.",
                                    Snackbar.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });


    }

    //Logout
    public void logout() {
        mAuth.signOut();
    }

    //Update UI
    public static void updateUI(final boolean signedIn, final View v, final FirebaseAuth mAut) {
        if (v.findViewById(R.id.cardAccount) != null) {
            if (signedIn) {
                TextView tmp;
                tmp = v.findViewById(R.id.lbl_email_user);
                tmp.setText(mAut.getCurrentUser().getEmail());

                tmp = v.findViewById(R.id.lbl_name_user);
                tmp.setText(mAut.getCurrentUser().getDisplayName());

                tmp = v.findViewById(R.id.lbl_uid_user);
                tmp.setText(mAut.getCurrentUser().getUid());

                new Thread(new Runnable() {
                    public void run() {
                        // a potentially  time consuming task
                        try {
                            final Bitmap uProfilePic = BitmapFactory.decodeStream((InputStream) new URL(mAut.getCurrentUser().getPhotoUrl().toString()).getContent());
                            ImageView tmpPic = v.findViewById(R.id.imgv_profile_pic);
                            tmpPic.post(new Runnable() {
                                public void run() {
                                    ImageView tmpPic = v.findViewById(R.id.imgv_profile_pic);
                                    tmpPic.setImageBitmap(uProfilePic);
                                }
                            });
                        } catch (MalformedURLException e) {


                        } catch (IOException e) {

                        } catch (Exception e){

                        }

                    }
                }).start();


            } else {
                TextView tmp;
                tmp = v.findViewById(R.id.lbl_email_user);
                tmp.setText("");

                tmp = v.findViewById(R.id.lbl_name_user);
                tmp.setText("");

                tmp = v.findViewById(R.id.lbl_uid_user);
                tmp.setText("");

                ImageView tmpPic = v.findViewById(R.id.imgv_profile_pic);
                tmpPic.setImageResource(R.drawable.ic_watch_later_black_24dp);

            }
        }

    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {

        }
        return bm;
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
