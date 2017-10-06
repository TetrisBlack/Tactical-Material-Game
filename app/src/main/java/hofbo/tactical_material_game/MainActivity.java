package hofbo.tactical_material_game;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, LoadoutFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener{



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        FragmentManager fragmentManager = getFragmentManager();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction  = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_settings:
                    transaction.replace(R.id.Fragment_Container, new LoadoutFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_highscore:
                    transaction.add(R.id.Fragment_Container,new LoginFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.Fragment_Container,new AccountFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
