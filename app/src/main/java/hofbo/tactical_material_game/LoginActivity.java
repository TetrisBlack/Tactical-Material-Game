package hofbo.tactical_material_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class LoginActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private int status = 0;



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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();

                        } else {
                            Snackbar.make(findViewById(R.id.Fragment_Container), "Google Authentication failed.",
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

        EditText login_email = (EditText) this.findViewById(R.id.input_email);
        EditText login_password = (EditText) this.findViewById(R.id.input_password);

        if (login_email.getText().length() > 0 && login_password.getText().length() > 5) {

            String email = login_email.getText().toString();
            String password = login_password.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();

                            } else {
                                // If sign in fails, display a message to the user .


                            }

                            // ...
                        }
                    });
        }


    }

    //Login
    public void login() {

        EditText login_email = (EditText) findViewById(R.id.input_email);
        EditText login_password = (EditText) this.findViewById(R.id.input_password);
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                Log.d("WHY?", "Logged in!");
                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                            } else {
                                Log.d("WHY?", "ERROR");
                            }

                            // ...

                        }
                    });
        }

    }
    public void startSignup(){
        if(status == 0){
            Button btn = findViewById(R.id.btn_login);
            btn.setText("Register");
            findViewById(R.id.btn_login_with_google).setVisibility(View.INVISIBLE);
            TextView txt = findViewById(R.id.link_signup);
            txt.setText("Back to the login");
            status = 1;


        }else if(status == 1){
            Button btn = findViewById(R.id.btn_login);
            btn.setText("Login");
            findViewById(R.id.btn_login_with_google).setVisibility(View.VISIBLE);
            TextView txt = findViewById(R.id.link_signup);
            txt.setText("No Account? :O");
            status = 0;



        }



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(btnListener);
        findViewById(R.id.btn_login_with_google).setOnClickListener(btnListener);
        findViewById(R.id.link_signup).setOnClickListener(btnListener);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            mGoogleApiClient.disconnect();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            // Yes we will handle click here but which button clicked??? We don't know

            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.btn_login:

                    if(status == 0){
                        login();
                    }if(status== 1){
                        register();
                }

                    // do something when the corky is clicked

                    break;


                case R.id.btn_login_with_google:
                    signIn();

                    break;


                case R.id.link_signup:
                    startSignup();
                    break;


                default:
                    break;
            }
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

        if (mAuth.getCurrentUser() != null) {


        }
    }


}
