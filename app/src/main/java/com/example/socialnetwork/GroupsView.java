package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupsView extends AppCompatActivity {

    int Userid;
    EditText GroupName;
    Button CreateGroupB;
    ListView joinedGroupLV;
    ListView suggestedGroupLV;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_view);

        Intent intent=getIntent();

        Userid= intent.getExtras().getInt("id");

        GroupName=(EditText) findViewById(R.id.CreateGnameTF);
        CreateGroupB=(Button) findViewById(R.id.CreateGroupB);


        joinedGroupLV=findViewById(R.id.JoinedGroupLV);

        JoinedGroupADP joinedGroupADP=new JoinedGroupADP(Group.GroupList);

        joinedGroupLV.setAdapter(joinedGroupADP);


        suggestedGroupLV=findViewById(R.id.AllGroupsLV);

        AllGroupADP allGroupADP=new AllGroupADP(Group.GroupList);

        suggestedGroupLV.setAdapter(allGroupADP);

        CreateGroupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DataBase dataBase=new DataBase(GroupsView.this);


                Group group=new Group(Group.GroupList.size()+1,GroupName.getText().toString(),Userid);
                Group.GroupList.add(group);

                dataBase.InsertGroup(group);


                GroupMember groupMember=new GroupMember(GroupMember.groupMembersList.size()+1,
                        "Admin",
                        Group.GroupList.size(),
                        Userid);

                GroupMember.groupMembersList.add(groupMember);

                dataBase.InsertGroupMember(groupMember);


                JoinedGroupADP joinedGroupADP=new JoinedGroupADP(Group.GroupList);

                joinedGroupLV.setAdapter(joinedGroupADP);
            }
        });

    }

    public class JoinedGroupADP extends BaseAdapter
    {
        ArrayList<Group> groups=new ArrayList<>();

        public JoinedGroupADP(ArrayList<Group> groups1)
        {
            for(int x=0;x<groups1.size();x++)
            {
                if(GroupMember.IsGroupMember(groups1.get(x).getGroupID(),Userid))
                {
                    this.groups.add(groups1.get(x));
                }
            }
        }

        @Override
        public int getCount() {
            return this.groups.size();
        }

        @Override
        public Object getItem(int i) {
            return this.groups.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater=getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.joined_groups,null);



            TextView GroupName= view1.findViewById(R.id.jGroupNameTV);
            Button ViewGroupB= view1.findViewById(R.id.ViewGroupB);

            GroupName.setText(this.groups.get(i).getGroupName());

            Group group=this.groups.get(i);

            ViewGroupB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    openGroup(group.getGroupID(),Userid);
                }
            });

            return view1;
        }
    }


    public class AllGroupADP extends BaseAdapter
    {
        ArrayList<Group> groups=new ArrayList<>();

        public AllGroupADP(ArrayList<Group> groups1)
        {
            for(int x=0;x<groups1.size();x++)
            {
                if(!GroupMember.IsGroupMember(groups1.get(x).getGroupID(),Userid))
                {
                    this.groups.add(groups1.get(x));
                }
            }
        }

        @Override
        public int getCount() {
            return this.groups.size();
        }

        @Override
        public Object getItem(int i) {
            return this.groups.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater=getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.suggested_groups,null);



            TextView GroupName= view1.findViewById(R.id.sGroupNameTV);
            Button JoinGroupB= view1.findViewById(R.id.JoinGroupB);

            GroupName.setText(this.groups.get(i).getGroupName());

            Group group=this.groups.get(i);

            JoinGroupB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    DataBase dataBase=new DataBase(GroupsView.this);
                    GroupMember groupMember=new GroupMember(GroupMember.groupMembersList.size()+1,
                            "Admin",
                            Group.GroupList.size(),
                            Userid);

                    GroupMember.groupMembersList.add(groupMember);

                    dataBase.InsertGroupMember(groupMember);


                    JoinedGroupADP joinedGroupADP=new JoinedGroupADP(Group.GroupList);

                    joinedGroupLV.setAdapter(joinedGroupADP);

                    AllGroupADP allGroupADP=new AllGroupADP(Group.GroupList);

                    suggestedGroupLV.setAdapter(allGroupADP);
                }
            });

            return view1;
        }
    }


    public void openGroup(int Groupid,int userid)
    {
        Intent intent=new Intent(this,GroupActivity.class);
        intent.putExtra("Groupid",Groupid);
        intent.putExtra("Userid",userid);
        startActivity(intent);
    }
}