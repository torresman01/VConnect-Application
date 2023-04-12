package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText profileName, profileEmail, profilePhone;
    private Button updateProfile;
    private CircleImageView imageViewCircleProfile;
    private ImageView imageViewHome;

    boolean imageControl = false;
    Uri imageUri;

    String image;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        imageViewCircleProfile = findViewById(R.id.imageViewCircleProfilePage);
        profileName = findViewById(R.id.profileUserName);
        profileEmail = findViewById(R.id.profileUserEmail);
        profilePhone = findViewById(R.id.profileUserPhoneNumber);
        updateProfile = findViewById(R.id.updateProfileAccountBtn);
        imageViewHome = findViewById(R.id.imageViewBackHome);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseUser = auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getUserDetails();

        imageViewCircleProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageChooser();

            }
        });


        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });




        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateUserProfile();

            }
        });


    }

    public void updateUserProfile()
    {
        String accountName = profileName.getText().toString();
        String accountPhone = profilePhone.getText().toString();

        reference.child("User").child(firebaseUser.getUid()).child("name").setValue(accountName);
        reference.child("User").child(firebaseUser.getUid()).child("phone").setValue(accountPhone);

        if (imageControl){

            UUID randomID = UUID.randomUUID();
            String imageName = "images/"+randomID+".jpg";
            storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference myStorageRef = firebaseStorage.getReference(imageName);
                    myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //Converting to string
                            String filePath = uri.toString();
                            reference.child("User").child(auth.getUid()).child("image").setValue(filePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProfileActivity.this,"Profile Updated Successful!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileActivity.this,"Profile Update Failed. Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }
            });

        }else {
            reference.child("User").child(auth.getUid()).child("image").setValue(image);
        }

        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();


    }

    public void getUserDetails()
    {
        reference.child("User").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String name = snapshot.child("name").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                image = snapshot.child("image").getValue().toString();

                profileName.setText(name);
                profileEmail.setText(email);
                profilePhone.setText(phone);


                

                if (image.equals("null"))
                {
                    imageViewCircleProfile.setImageResource(R.drawable.ic_account);
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

    public void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageViewCircleProfile);
            imageControl = true;
        }
        else
        {
            imageControl = false;
        }
    }
}