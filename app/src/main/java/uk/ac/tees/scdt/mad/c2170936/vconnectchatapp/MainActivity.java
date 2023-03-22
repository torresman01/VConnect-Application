package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout MainFrame;

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId())
                    {
                        case R.id.nav_chat:
                            setFragment(new MessageFragment());
                            return true;

                        case R.id.nav_user:
                            setFragment(new UsersFragment());
                            return true;

                        case R.id.nav_map:
                            setFragment(new MapFragment());
                            return true;

                        case R.id.nav_video:
                            setFragment(new VideoFragment());
                            return true;

                        case R.id.nav_settings:
                            setFragment(new SettingsFragment());
                            return true;
                    }

                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_Navigation_Bar);
        MainFrame = findViewById(R.id.frameMain);


        bottomNavigationView.setOnItemSelectedListener(onNavigationItemSelectedListener);

        setFragment(new MessageFragment());




    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(MainFrame.getId(),fragment);
        transaction.commit();
    }
}