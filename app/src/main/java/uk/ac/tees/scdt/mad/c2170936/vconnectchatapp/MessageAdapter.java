package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<String> messageList;
    String name;
    Context mContext;

    DatabaseReference reference;
    FirebaseDatabase database;

    public MessageAdapter(List<String> messageList, String name, Context mContext) {
        this.messageList = messageList;
        this.name = name;
        this.mContext = mContext;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        reference.child("User").child(messageList.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String otherName = snapshot.child("name").getValue().toString();
                String imageURL = snapshot.child("image").getValue().toString();

                holder.textViewMessage.setText(otherName);

                if (imageURL.equals("null"))
                {
                    holder.imageUsersView.setImageResource(R.drawable.ic_account_black);
                }
                else
                {
                    Picasso.get().load(imageURL).into(holder.imageUsersView);
                }

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,ChatActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("otherName",otherName);
                        mContext.startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textViewMessage;
        private CircleImageView imageUsersView;
        private CardView cardView;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

        textViewMessage = itemView.findViewById(R.id.textViewMessage);
        imageUsersView = itemView.findViewById(R.id.imageViewUsers);
        cardView = itemView.findViewById(R.id.cardView);


    }
    }

}
