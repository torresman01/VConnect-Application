package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.scdt.mad.c2170936.vconnectchatapp.databinding.ActivityHomeDrawerBinding;
import uk.ac.tees.scdt.mad.c2170936.vconnectchatapp.databinding.ActivityMainBinding;

public class HomeDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeDrawerBinding binding;

    private BottomNavigationView bottomNavigationView;

    private FrameLayout main_frame;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityHomeDrawerBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomeDrawer.toolbar);

        bottomNavigationView = findViewById(R.id.bottom_Navigation_View);
        main_frame = findViewById(R.id.nav_host_fragment_content_home_drawer);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_chat:
                        setFragment(new MessagesFragment());
                        return true;

                    case R.id.nav_user:
                        setFragment(new UsersFragment());
                        return true;

                    case R.id.nav_map:
                        setFragment(new MapFragment());
                        return true;

                    case R.id.nav_video:
                        setFragment(new ShotsFragment());
                        return true;

                    case R.id.nav_settings:
                        setFragment(new SettingsFragment());
                        return true;

                }

                return false;
            }
        });

//        bottomNavigationView.setOnNavigationItemReselectedListener(onNavigationItemSelectedListener);
       // bottomNavigationView.setOnItemReselectedListener((NavigationBarView.OnItemReselectedListener) onItemSelectedListener);

//        binding.appBarHomeDrawer.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

       setFragment(new MessagesFragment());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home_drawer, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();
    }

}