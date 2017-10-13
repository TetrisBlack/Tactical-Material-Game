package hofbo.tactical_material_game;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.HalfFloat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements
        LoadoutFragment.OnFragmentInteractionListener, Highscore_Fragment.OnFragmentInteractionListener,
        Game_Fragment.OnFragmentInteractionListener, NewsFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener{

    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        FragmentManager fragmentManager = getFragmentManager();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction  = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_settings:

                    transaction.replace(R.id.Fragment_Container, new AccountFragment().newInstance(mAuth));
                    transaction.commit();
                    return true;
                case R.id.navigation_highscore:

                    transaction.replace(R.id.Fragment_Container, new Highscore_Fragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_start_game:
                    transaction.replace(R.id.Fragment_Container,new Game_Fragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_loadout:
                    transaction.replace(R.id.Fragment_Container,new LoadoutFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_news:
                    transaction.replace(R.id.Fragment_Container,new NewsFragment());
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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.Fragment_Container), "Willkommen zurück " + user.getDisplayName(), Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {
                    // User is signed out
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.Fragment_Container), "Logged Out", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                // ...
            }
        };




        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
