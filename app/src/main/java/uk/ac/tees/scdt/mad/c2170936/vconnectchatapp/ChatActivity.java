package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ImageView imageViewBack;
    private TextView textViewChat;
    private EditText editTextMessage;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    String name,otherName;

    FirebaseDatabase database;
    DatabaseReference reference;

    ChatAdapter adapter;
    List<ModelClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imageViewBack = findViewById(R.id.imageViewBack);
        textViewChat = findViewById(R.id.textViewChatting);
        editTextMessage = findViewById(R.id.editTextMessage);
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rViewChat);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        name = getIntent().getStringExtra("name");
        otherName = getIntent().getStringExtra("otherName");
        textViewChat.setText(otherName);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(in);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextMessage.getText().toString();
                
                if (!message.equals(""))
                {
                    sendMessage(message);
                    editTextMessage.setText("");
                }
            }
        });

        getChat();
    }

    private void getChat() {
        reference.child("Messages").child(name).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModelClass modelClass = snapshot.getValue(ModelClass.class);
                list.add(modelClass);
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(list.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new ChatAdapter(list,name);
        recyclerView.setAdapter(adapter);
    }




    private void sendMessage(String message)
    {
        final String key = reference.child("Messages").child(name).child(otherName).push().getKey();
        final Map<String, Object> messageMap = new HashMap<>();

        messageMap.put("message",message);
        messageMap.put("from",name);
        messageMap.put("to",otherName);

        reference.child("Messages").child(name).child(otherName).child(key).setValue(messageMap).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                reference.child("Messages").child(otherName).child(name).child(key).setValue(messageMap);
            }
        });


    }

}