package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    //Declaring variables
    TextInputLayout regName, regEmail, regPhone, regPassword;
    Button callLogin, regSignUpBtn;
    CircleImageView imageViewCircle;
    boolean imageControl = false;
    Uri imageUri;
    public static final String READ_EXTERNAL_STORAGE = null;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    //For validation of form
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()){
            regName.setError("Name cannot be empty");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";

        if (val.isEmpty()){
            regEmail.setError("Email cannot be empty");
            return false;
        }
        else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhone(){
        String val = regPhone.getEditText().getText().toString();

        if (val.isEmpty()){
            regPhone.setError("Phone Number cannot be empty");
            return false;
        }else {
            regPhone.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //    "(?=.*[0-9])" +  //at least 1 digit
                //  "(?=.*[a-z])" +  //at least 1 lower case letter
                //   "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +           //no white space
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()){
            regPassword.setError("Password cannot be empty");
            return false;
        }
        else if (!val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;

        }
        else {
            regPassword.setError(null);
            return true;
        }
    }

    //Save data in Firebase on button click
//    public void registerUser(View view) {
////        rootNode = FirebaseDatabase.getInstance();
////        reference = rootNode.getReference("User");
//
////        if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()){
////            return;
////        }
//
////        //Get all the values
////        String name = regName.getEditText().getText().toString();
////        String email = regEmail.getEditText().getText().toString();
////        String password = regPassword.getEditText().getText().toString();
////        String phone = regPhone.getEditText().getText().toString();
////
////        Intent intent = new Intent(getApplicationContext(),PhoneAuthActivity.class);
////        intent.putExtra("phoneNo",phone);
////        startActivity(intent);
//
//        //Store data in Firebase
////        UserHelperClass helperClass = new UserHelperClass(name, email, phone, password);
////        reference.child(phone).setValue(helperClass);
//
//        //Show success message to user
////        Toast.makeText(this,"Account Created Successfully!", Toast.LENGTH_SHORT).show();
////
////        //Go to login activity after creating account
////        Intent intent = new Intent(getApplicationContext(),loginActivity.class);
////        startActivity(intent);
////        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.name);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.acctPassword);
        regPhone = findViewById(R.id.phone);
        regSignUpBtn = findViewById(R.id.regSignup);
        callLogin = findViewById(R.id.login);
        imageViewCircle = findViewById(R.id.imageViewCircle);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        //save data in Firebase on button click
//        regSignUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("User");
//
//                //Get all the vales
//                String name = regName.getEditText().getText().toString();
//                String email = regEmail.getEditText().getText().toString();
//                String password = regPassword.getEditText().getText().toString();
//                String phone = regPhone.getEditText().getText().toString();
//
//                UserHelperClass helperClass = new UserHelperClass(name, email, phone, password);
//
//                reference.child(phone).setValue(helperClass);
//            }
//        });


        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                WORK ON PERMISSION LATER
//                if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.class) != PackageManager.PERMISSION_GRANTED){
//
//                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission}, 1);
//
//                }

                imageChooser();
            }
        });


        regSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = regName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String phone = regPhone.getEditText().getText().toString();

                if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()){
                   return;
                } else {
                    signup(name,email,phone,password);
                }

            }
        });

         callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,loginActivity.class);
                startActivity(intent);
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
            Picasso.get().load(imageUri).into(imageViewCircle);
            imageControl = true;
        }
        else
        {
            imageControl = false;
        }
    }

    public void signup(String name, String email, String phone, String password){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    reference.child("User").child(auth.getUid()).child("phone").setValue(phone);

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
                                                Toast.makeText(SignUpActivity.this,"Account Created Successful!", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this,"Account Created Failed. Please try again!", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                });
                            }
                        });

                    }else {
                        reference.child("User").child(auth.getUid()).child("image").setValue("null");
                    }

                    Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(SignUpActivity.this,"ERROR! Unable to sign up!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}