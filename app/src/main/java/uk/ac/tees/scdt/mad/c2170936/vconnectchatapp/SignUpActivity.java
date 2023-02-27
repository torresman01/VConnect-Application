package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //Declaring variables
    TextInputLayout regName, regEmail, regPhone, regPassword;
    Button callLogin, regSignUpBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

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
    public void registerUser(View view) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("User");

        if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()){
            return;
        }

        //Get all the values
        String name = regName.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        String phone = regPhone.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, email, phone, password);

        reference.child(phone).setValue(helperClass);
    }

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

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });



    }


}