package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName, profilePhoneNumber;
    private CircleImageView imageViewCircleProfile;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName112);
        profilePhoneNumber = findViewById(R.id.profilePhone112);
        imageViewCircleProfile = findViewById(R.id.imageViewCircleProfile112);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseUser = auth.getCurrentUser();

        getUserDetails();
    }

    public void getUserDetails()
    {
        reference.child("User").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                String image = snapshot.child("image").getValue().toString();

                profileName.setText(name);
                profilePhoneNumber.setText(phone);


                if (image.equals("null"))
                {
                    imageViewCircleProfile.setImageResource(R.drawable.ic_account_black);
                }
                else
                {
                    Picasso.get().load(image).into(imageViewCircleProfile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}