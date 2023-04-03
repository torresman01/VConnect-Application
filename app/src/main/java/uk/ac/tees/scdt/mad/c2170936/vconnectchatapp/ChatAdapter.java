package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    List<ModelClass> list;
    String name;
    boolean status;
    int send;
    int receive;


    public ChatAdapter(List<ModelClass> list, String name) {
        this.list = list;
        this.name = name;

        status = false;
        send = 1;
        receive = 2;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == send)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message_card,parent,false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message_card,parent,false);
        }

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        //Print message in screen
        holder.textView.setText(list.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ChatViewHolder(@NonNull View itemView){
            super(itemView);

            if (status)
            {
                textView = itemView.findViewById(R.id.textViewSend);
            }
            else
            {
                textView = itemView.findViewById(R.id.textViewReceived);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getFrom().equals(name))
        {
            status = true;
            return send;
        }
        else
        {
            status = false;
            return receive;
        }
    }
}
