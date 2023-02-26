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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.name);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        regPhone = findViewById(R.id.phone);
        regSignUpBtn = findViewById(R.id.regSignup);
        callLogin = findViewById(R.id.login);

        //save data in Firebase on button click
        regSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("User");

                //Get all the vales
                String name = regName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String phone = regPhone.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, email, phone, password);

                reference.child(phone).setValue(helperClass);
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
}