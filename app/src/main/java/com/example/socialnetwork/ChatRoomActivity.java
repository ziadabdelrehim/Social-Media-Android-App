package com.example.socialnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity
{
    int UserID;
    int FriendID;
    Button SendB;
    EditText MessageBox;
    TextView DstName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent=getIntent();
        UserID=intent.getExtras().getInt("UserID");
        FriendID=intent.getExtras().getInt("FriendID");
        int x=0;

        Toast.makeText(ChatRoomActivity.this, String.valueOf(FriendID), Toast.LENGTH_SHORT).show();

        DstName=(TextView) findViewById(R.id.DstNameTV);

        for (int o=0;o<User.UsersList.size();o++)
        {
            if(User.UsersList.get(o).getID()==FriendID)
            {
                DstName.setText(User.UsersList.get(o).getName());
            }
        }



        RecyclerView chatRV=findViewById(R.id.ChatRecyclerView);

        chatRV.setHasFixedSize(true);
        chatRV.setLayoutManager(new LinearLayoutManager(this));

        MessageADP messageADP=new MessageADP(Message.MessagesList,UserID,FriendID);


        chatRV.setAdapter(messageADP);



        SendB=(Button) findViewById(R.id.ChatSendB);
        MessageBox=(EditText) findViewById(R.id.MessageBoxTF);

        SendB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Message message=new Message(Message.MessagesList.size()+1,MessageBox.getText().toString(),UserID,FriendID);
                Message.MessagesList.add(message);
                DataBase dataBase=new DataBase(ChatRoomActivity.this);
                dataBase.InsertMessage(message);
                MessageBox.setText("");

                MessageADP messageADP1=new MessageADP(Message.MessagesList,UserID,FriendID);

                chatRV.setAdapter(messageADP1);


            }
        });




    }


    public class MessageADP extends RecyclerView.Adapter
    {
        ArrayList<Message> messages=new ArrayList<>();

        public MessageADP(ArrayList<Message> messages1,int srcID,int dstID)
        {
            for (int k=0;k<messages1.size();k++)
            {
                if((messages1.get(k).getSrcID()==UserID) & (messages1.get(k).getDstID()==FriendID))
                {

                    this.messages.add(messages1.get(k));
                }
                else if((messages1.get(k).getSrcID()==FriendID) & (messages1.get(k).getDstID()==UserID))
                {
                    this.messages.add(messages1.get(k));
                }
            }

        }

        @Override
        public int getItemViewType(int position)
        {
            if(this.messages.get(position).getSrcID()==UserID)
            {
                return 1;
            }
            else
            {
                return 2;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view1;
            if(viewType==1)
            {
                view1=layoutInflater.inflate(R.layout.sent,parent,false);
                return new SentMessagesViewHolder(view1);
            }
            else
            {
                view1=layoutInflater.inflate(R.layout.receive,parent,false);
                return new ReceiveMessagesViewHolder(view1);
            }


        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
        {
            if(getItemViewType(position)==1)
            {
                SentMessagesViewHolder viewHolder=(SentMessagesViewHolder) holder;
                viewHolder.SentMessage.setText(this.messages.get(position).getMessageText());
            }
            else
            {
                ReceiveMessagesViewHolder viewHolder=(ReceiveMessagesViewHolder) holder;
                viewHolder.ReceivedMessage.setText(this.messages.get(position).getMessageText());
            }
        }

        @Override
        public int getItemCount()
        {
            return this.messages.size();
        }


        class SentMessagesViewHolder extends RecyclerView.ViewHolder
        {

            TextView SentMessage;

            public SentMessagesViewHolder(@NonNull View itemView) {
                super(itemView);

                SentMessage=itemView.findViewById(R.id.SentMessageTV);
            }
        }

        class ReceiveMessagesViewHolder extends RecyclerView.ViewHolder
        {

            TextView ReceivedMessage;

            public ReceiveMessagesViewHolder(@NonNull View itemView)
            {
                super(itemView);

                ReceivedMessage=itemView.findViewById(R.id.ReceiveMessageTV);
            }
        }
    }
}