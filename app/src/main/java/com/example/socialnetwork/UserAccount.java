package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserAccount extends AppCompatActivity {

    private Button Profile;
    private Button News;
    private Button Friends;
    private Button Messages;
    private Button Groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Intent intent=getIntent();
        int UserID=intent.getExtras().getInt("ID");
        Profile=(Button) findViewById(R.id.ProfileB);
        Profile.setText(User.UsersList.get(UserID-1).getName());

        Profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openProfile(UserID);
            }
        });

        Friends=(Button) findViewById(R.id.FriendsB);
        Friends.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openFriends(UserID);
            }
        });

        Groups=(Button) findViewById(R.id.GroupsB);
        Groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroups(UserID);
            }
        });


    }

    public void openProfile(int id)
    {
        Intent intent=new Intent(this,Profile.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void openFriends(int id)
    {
        Intent intent=new Intent(this,Friends.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }


    public void openGroups(int id)
    {
        Intent intent=new Intent(this,GroupsView.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}