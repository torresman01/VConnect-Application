package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout MainFrame;
    private AppBarLayout appBarLayout;

    FirebaseAuth auth;

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
        appBarLayout = findViewById(R.id.actionMenuBar);

        auth = FirebaseAuth.getInstance();

        bottomNavigationView.setOnItemSelectedListener(onNavigationItemSelectedListener);



        setFragment(new MessageFragment());

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(MainFrame.getId(),fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.ProfileAction)
        {
            setFragment(new SettingsFragment());
        }

        if (item.getItemId() == R.id.SignOutAction)
        {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, loginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}