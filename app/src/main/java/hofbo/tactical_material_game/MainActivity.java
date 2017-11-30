package hofbo.tactical_material_game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import hofbo.tactical_material_game.Fragments.AccountFragment;
import hofbo.tactical_material_game.Fragments.GameFragment;
import hofbo.tactical_material_game.Fragments.HighscoreFragment;
import hofbo.tactical_material_game.Fragments.LoadoutFragment;
import hofbo.tactical_material_game.Fragments.NewsFragment;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity implements
        LoadoutFragment.OnFragmentInteractionListener, HighscoreFragment.OnFragmentInteractionListener,
        GameFragment.OnFragmentInteractionListener, NewsFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener {

    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAnalytics mFirebaseAnalytics;
    public Bitmap uProfilePic;

    //Fragment helper
    private int currentFragment = 0;
    private int lastFragment = 0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        FragmentManager fragmentManager = getFragmentManager();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();



            switch (item.getItemId()) {
                case R.id.navigation_settings:
                    setFragmentAnimation(0,transaction);

                    transaction.replace(R.id.Fragment_Container, new AccountFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_highscore:
                    setFragmentAnimation(1,transaction);

                    transaction.replace(R.id.Fragment_Container, new HighscoreFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_start_game:
                    setFragmentAnimation(2,transaction);

                    transaction.replace(R.id.Fragment_Container, new GameFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_loadout:
                    setFragmentAnimation(3,transaction);

                    transaction.replace(R.id.Fragment_Container, new LoadoutFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_news:
                    setFragmentAnimation(4,transaction);

                    transaction.replace(R.id.Fragment_Container, new NewsFragment());
                    transaction.commit();
                    return true;


            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    AccountFragment.updateUI(true,findViewById(R.id.Fragment_Container),mAuth);
                    Snackbar.make(findViewById(R.id.Fragment_Container), "Willkommen zurück " + user.getDisplayName(), Snackbar.LENGTH_SHORT).show();

                } else {
                    // User is signed out
                    AccountFragment.updateUI(false,findViewById(R.id.Fragment_Container),mAuth);
                    Snackbar.make(findViewById(R.id.Fragment_Container), "Logged Out", Snackbar.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                // ...
            }
        };


        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_start_game);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    public void setFragmentAnimation(int currentFragment, FragmentTransaction transaction){

        if(currentFragment == lastFragment){

        }else if(currentFragment > lastFragment){
            transaction.setCustomAnimations(R.animator.slide_in_right,R.animator.slide_out_right);

        }else if(currentFragment < lastFragment){
            transaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left);

        }
        lastFragment = currentFragment;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
