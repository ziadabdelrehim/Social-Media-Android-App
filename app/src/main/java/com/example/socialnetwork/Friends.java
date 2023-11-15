package com.example.socialnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Friends extends AppCompatActivity
{
    private int Userid;
    private ListView UserLV;
    private ListView FriendReqLV;
    private ListView FriendsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Intent intent=getIntent();

        Userid= intent.getExtras().getInt("id");

        UserLV=findViewById(R.id.UsersListV);

        UserListADP userListADP=new UserListADP(User.UsersList);

        UserLV.setAdapter(userListADP);

        FriendReqLV=findViewById(R.id.FriendReqListV);

        FriendReqADP friendReqADP=new FriendReqADP(FriendRequest.FriendReqList);
        FriendReqLV.setAdapter(friendReqADP);


        FriendsLV=findViewById(R.id.FriendsListV);
        UserFriendsADP userFriendsADP=new UserFriendsADP(UserFriend.userFriendsList);
        FriendsLV.setAdapter(userFriendsADP);




    }


    public class UserListADP extends BaseAdapter
    {

        ArrayList<User> Users=new ArrayList<>();

        public UserListADP(ArrayList<User> users)
        {
            for(int x=0;x<users.size();x++)
            {
                if(users.get(x).getID()!=Userid && !User.HavingFriendship(users.get(x).getID(),Userid))
                {
                    this.Users.add(users.get(x));
                }
            }
        }

        @Override
        public int getCount() {
            return this.Users.size();
        }

        @Override
        public Object getItem(int i) {
            return this.Users.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.user_list,null);

            DataBase dataBase=new DataBase(Friends.this);

            TextView UserName= view1.findViewById(R.id.ULuserNameTV);
            Button AddB= view1.findViewById(R.id.AddB);

            int DstUserID=this.Users.get(i).getID();

            UserName.setText(this.Users.get(i).getName());

            for (int z=0;z<FriendRequest.FriendReqList.size();z++)
            {
                if(FriendRequest.FriendReqList.get(z).getDSTuserID()==DstUserID)
                {
                    AddB.setText(FriendRequest.FriendReqList.get(z).getStatus());
                }
            }

            AddB.setOnClickListener(view2 -> {
                if(AddB.getText().toString().matches("Add"))
                {
                    FriendRequest friendRequest=new FriendRequest(FriendRequest.FriendReqList.size()+1,"Pending",Userid,DstUserID);
                    FriendRequest.FriendReqList.add(friendRequest);
                    dataBase.InsertFreindReq(friendRequest);
                    AddB.setText("Pending");
                }
                else if(AddB.getText().toString().matches("Pending"))
                {
                    FriendRequest f=new FriendRequest();

                    for(int k=0;k<FriendRequest.FriendReqList.size();k++)
                    {
                        if(FriendRequest.FriendReqList.get(k).getDSTuserID()==DstUserID && FriendRequest.FriendReqList.get(k).getSRCuserID()==Userid)
                        {
                            f=FriendRequest.FriendReqList.get(k);
                        }
                    }
                    FriendRequest.FriendReqList.remove(f);
                    dataBase.DeleteFriendReq(f);
                    AddB.setText("Add");
                    Toast.makeText(Friends.this, "Request has been cancelled!", Toast.LENGTH_SHORT).show();
                }



            });


            return view1;
        }
    }

    public class FriendReqADP extends BaseAdapter
    {
        ArrayList<FriendRequest> FriendRequests=new ArrayList<>();
        User user=new User();

        public FriendReqADP(ArrayList<FriendRequest> friendRequests)
        {
            for(int x=0;x<friendRequests.size();x++)
            {
                if(friendRequests.get(x).getDSTuserID()==Userid)
                {
                    this.FriendRequests.add(friendRequests.get(x));
                }
            }
        }

        @Override
        public int getCount()
        {
            return this.FriendRequests.size();
        }

        @Override
        public Object getItem(int i)
        {
            return this.FriendRequests.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.friend_req_list,null);

            DataBase dataBase=new DataBase(Friends.this);

            TextView ReqSenderName= view1.findViewById(R.id.ReqSenderName);
            Button AcceptB= view1.findViewById(R.id.AcceptB);
            Button RejectB= view1.findViewById(R.id.RejectB);


            for (int h=0;h<User.UsersList.size();h++)
            {
                if(User.UsersList.get(h).getID()==this.FriendRequests.get(i).getSRCuserID())
                {
                    user=User.UsersList.get(h);
                }
            }
            ReqSenderName.setText(user.getName());

            AcceptB.setOnClickListener(view22 -> {
                UserFriend userFriend=new UserFriend(UserFriend.userFriendsList.size()+1,user.getID(),Userid);
                UserFriend.userFriendsList.add(userFriend);
                dataBase.InsertNewFriend(userFriend);


                FriendRequest.FriendReqList.remove(FriendRequests.get(i));
                dataBase.DeleteFriendReq(FriendRequests.get(i));



                UserListADP userListADP=new UserListADP(User.UsersList);

                UserLV.setAdapter(userListADP);


                FriendReqADP friendReqADP=new FriendReqADP(FriendRequest.FriendReqList);
                FriendReqLV.setAdapter(friendReqADP);


                UserFriendsADP userFriendsADP=new UserFriendsADP(UserFriend.userFriendsList);
                FriendsLV.setAdapter(userFriendsADP);

                Toast.makeText(Friends.this,"New Friend Accepted!",Toast.LENGTH_LONG).show();
            });

            RejectB.setOnClickListener(view2 -> {
                FriendRequest.FriendReqList.remove(FriendRequests.get(i));
                dataBase.DeleteFriendReq(FriendRequests.get(i));

                UserListADP userListADP=new UserListADP(User.UsersList);

                UserLV.setAdapter(userListADP);


                FriendReqADP friendReqADP=new FriendReqADP(FriendRequest.FriendReqList);
                FriendReqLV.setAdapter(friendReqADP);


                UserFriendsADP userFriendsADP=new UserFriendsADP(UserFriend.userFriendsList);
                FriendsLV.setAdapter(userFriendsADP);
                Toast.makeText(Friends.this,"Rejected!",Toast.LENGTH_LONG).show();
            });



            return view1;
        }
    }

    public class UserFriendsADP extends BaseAdapter
    {
        ArrayList<UserFriend> UserFriends=new ArrayList<>();


        public UserFriendsADP(ArrayList<UserFriend> userFriends)
        {
            for(int k=0;k<userFriends.size();k++)
            {
                if(userFriends.get(k).getDSTuserID()==Userid || userFriends.get(k).getSRCuserID()==Userid)
                {
                    this.UserFriends.add(userFriends.get(k));
                }
            }
        }

        @Override
        public int getCount()
        {
            return this.UserFriends.size();
        }

        @Override
        public Object getItem(int i)
        {
            return this.UserFriends.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.friends_list,null);



            TextView FriendName= view1.findViewById(R.id.FriendNameTV);
            Button MessageB= view1.findViewById(R.id.SendMessageB);







            for(int x=0;x<User.UsersList.size();x++)
            {
                if(User.UsersList.get(x).getID()!=Userid && User.UsersList.get(x).getID()==this.UserFriends.get(i).getSRCuserID())
                {
                    FriendName.setText(User.UsersList.get(x).getName());


                }
                else if(User.UsersList.get(x).getID()!=Userid && User.UsersList.get(x).getID()==this.UserFriends.get(i).getDSTuserID())
                {
                    FriendName.setText(User.UsersList.get(x).getName());


                }
            }

            UserFriend userFriend=this.UserFriends.get(i);

            MessageB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Userid!=userFriend.getSRCuserID())
                    {
                        openChattingRoom(userFriend.getSRCuserID());
                    }
                    else
                    {
                        openChattingRoom(userFriend.getDSTuserID());
                    }
                }
            });
            return view1;
        }
    }


    public void openChattingRoom(int index)
    {
        Intent intent=new Intent(this,ChatRoomActivity.class);
        intent.putExtra("FriendID",index);
        intent.putExtra("UserID",Userid);
        startActivity(intent);
    }

}