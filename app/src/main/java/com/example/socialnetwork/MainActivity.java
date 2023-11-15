package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private Button Create;
    private Button Login;
    private EditText Email;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBase Data=new DataBase(this);  // Import from database to program
        User.UsersList=Data.GetUsers();
        Post.PostsList=Data.GetPosts();
        Comment.CommentsList=Data.GetComments();
        Like.LikesList=Data.GetLikes();
        FriendRequest.FriendReqList=Data.GetFriendReq();
        UserFriend.userFriendsList=Data.GetUserFriends();
        Message.MessagesList=Data.GetMessages();
        Group.GroupList=Data.GetGroups();
        GroupMember.groupMembersList=Data.GetGroupMembers();


        Create=(Button) findViewById(R.id.SignB);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openSignUp();
            }
        });

        Login=(Button) findViewById(R.id.LoginB);
        Email=(EditText) findViewById(R.id.EmailTF);
        Password=(EditText) findViewById(R.id.PasswordTF);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int flag=0;
                int index=-1;
                for(int i=0;i<User.UsersList.size();i++)
                {
                    if(Email.getText().toString().matches(User.UsersList.get(i).Email)
                            & Password.getText().toString().matches(User.UsersList.get(i).Password))
                    {
                        flag=1;
                        index=i;

                    }


                }
                if(flag==1)
                {
                    openUserAccount(index);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Wrong Entry",Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    public void openSignUp()
    {
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }

    public void openUserAccount(int index)
    {
        Intent intent=new Intent(this,UserAccount.class);
        intent.putExtra("ID",User.UsersList.get(index).getID());
        startActivity(intent);
    }

}