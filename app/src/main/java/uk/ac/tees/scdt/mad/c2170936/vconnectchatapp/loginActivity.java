package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    Button callSignUp, loginBtn;
    TextInputLayout regPhone, regPassword;

    //Form validation
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

        if (val.isEmpty()){
            regPassword.setError("Password cannot be empty");
            return false;
        }
        else {
            regPassword.setError(null);
            return true;
        }
    }

    //Save data in Firebase on button click
    public void loginUser(View view) {
        if (!validatePhone() | !validatePassword()){
            return;
        }
        else{
            isUSer();
        }


    }

    private void isUSer() {
        String userEnteredPhone = regPhone.getEditText().getText().toString().trim();
        String userEnteredPassword = regPassword.getEditText().getText().toString().trim();

        //Firebase call
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    regPhone.setError(null);
                    regPhone.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){

                        regPhone.setError(null);
                        regPhone.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredPhone).child("name").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredPhone).child("email").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),PhoneAuthActivity.class);

                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phone",phoneFromDB);

                        startActivity(intent);
                    }

                    else {

                        regPassword.setError("Wrong Password");
                        regPassword.requestFocus();
                    }
                }
                else {
                    regPhone.setError("No such user exist");
                    regPhone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks to all xml elements in activity_login.xml
          callSignUp = findViewById(R.id.signup);
          loginBtn = findViewById(R.id.acctLogin);
        regPhone = findViewById(R.id.phone_number);
        regPassword = findViewById(R.id.userPassword);





        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}