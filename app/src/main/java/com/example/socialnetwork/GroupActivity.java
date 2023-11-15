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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GroupActivity extends AppCompatActivity {

    int UserID;
    int GroupID;
    TextView GroupName;
    TextView AdminName;
    EditText GroupPostText;
    Button GroupPostB;
    ListView GroupPostsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent=getIntent();
        UserID=intent.getExtras().getInt("Userid");
        GroupID=intent.getExtras().getInt("Groupid");

        Group group=new Group();
        User Admin=new User();

        for(int i=0;i<Group.GroupList.size();i++)
        {
            if(Group.GroupList.get(i).getGroupID()==GroupID)
            {
                group=Group.GroupList.get(i);
            }
        }

        for(int i=0;i<User.UsersList.size();i++)
        {
            if(group.getAdminID()==User.UsersList.get(i).getID())
            {
                Admin=User.UsersList.get(i);
            }
        }

        GroupName=(TextView) findViewById(R.id.groupNameTV);
        AdminName=(TextView) findViewById(R.id.AdminNameTV);

        GroupName.setText(group.getGroupName());
        AdminName.setText("Managed by : "+Admin.getName());

        DataBase PostData=new DataBase(this);

        GroupPostText=(EditText) findViewById(R.id.GroupPostTF);
        GroupPostB=(Button) findViewById(R.id.GroupPostB);

        GroupPostsLV=findViewById(R.id.GroupPostList);

        Collections.reverse(Post.PostsList);
        GroupPostListADP a=new GroupPostListADP(Post.PostsList);
        GroupPostsLV.setAdapter(a);

        GroupPostB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(GroupPostText.getText().toString().matches(""))
                {
                    Toast.makeText(GroupActivity.this,"Empty Post",Toast.LENGTH_LONG).show();
                }
                else
                {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm");
                    Date date=new Date(System.currentTimeMillis());

                    Post post=new Post(Post.PostsList.size()+1,
                            GroupPostText.getText().toString(),
                            simpleDateFormat.format(date),
                            "Group Post",
                            UserID,GroupID);


                    Post.PostsList.add(post);
                    PostData.InsertPost(post);

                    Toast.makeText(GroupActivity.this,"Posted!",Toast.LENGTH_LONG).show();
                    GroupPostText.setText("");



                    Collections.reverse(Post.PostsList);
                    GroupPostListADP a=new GroupPostListADP(Post.PostsList);
                    GroupPostsLV.setAdapter(a);

                }
            }
        });



    }



    public class GroupPostListADP extends BaseAdapter
    {
        ArrayList<Post> Posts=new ArrayList<>();


        public GroupPostListADP(ArrayList<Post> posts)
        {
            for(int x=0;x<posts.size();x++)
            {
                if(posts.get(x).getDstGroupID()==GroupID)
                {
                    this.Posts.add(posts.get(x));
                }

            }

        }

        @Override
        public int getCount()
        {
            return this.Posts.size();
        }

        @Override
        public Post getItem(int i)
        {
            return Posts.get(i);
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
            View view1=layoutInflater.inflate(R.layout.post_list,null);

            TextView PostPubName=(TextView) view1.findViewById(R.id.PostPublisherNameTV);
            TextView PostDate=(TextView) view1.findViewById(R.id.PostDateTV);
            TextView PostText=(TextView) view1.findViewById(R.id.PostTextTV);
            TextView PostLikes=(TextView) view1.findViewById(R.id.PostLikesTV);
            TextView PostCommentsNum=(TextView) view1.findViewById(R.id.PostCommentNumTV);
            Button CommentB=(Button) view1.findViewById(R.id.PostCommentB);
            Button LikeB=(Button) view1.findViewById(R.id.LikeB);
            EditText CommentTF=(EditText) view1.findViewById(R.id.CommentTF);
            ListView CommentsLV=view1.findViewById(R.id.CommentsLV);


            GroupCommentListADP commentListADP=new GroupCommentListADP(Comment.CommentsList,this.Posts.get(i).getPostID());
            CommentsLV.setAdapter(commentListADP);
            PostCommentsNum.setText(commentListADP.getCount() + "Comments");



            int PublisherID=this.Posts.get(i).getUserID();
            int PostID=this.Posts.get(i).getPostID();
            int Likecount=0;
            for (int z=0;z<Like.LikesList.size();z++)
            {

                if(Like.LikesList.get(z).getPostID()==PostID)
                {
                    Likecount++;
                    if(Like.LikesList.get(z).getUserID()==UserID)
                    {
                        LikeB.setText("Unlike");
                    }
                }
            }

            PostLikes.setText(Likecount+"Likes");

            User Publisher=new User();

            for(int l=0;l<User.UsersList.size();l++)
            {
                if(User.UsersList.get(l).getID()==PublisherID)
                {
                    Publisher=User.UsersList.get(l);
                }
            }


                PostPubName.setText(String.valueOf(Publisher.getName()));
                PostDate.setText(String.valueOf(this.Posts.get(i).getCreatedAt()));
                PostText.setText(String.valueOf(this.Posts.get(i).getText()));





            CommentB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(CommentTF.getText().toString().matches(""))
                    {

                    }
                    else
                    {
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm");
                        Date date=new Date(System.currentTimeMillis());
                        Comment comment=new Comment(Comment.CommentsList.size()+1,
                                CommentTF.getText().toString(),
                                simpleDateFormat.format(date),
                                PostID,
                                UserID);
                        DataBase PostComment=new DataBase(GroupActivity.this);

                        Comment.CommentsList.add(comment);

                        PostComment.InsertComment(comment);
                        PostCommentsNum.setText(commentListADP.getCount() + "Comments");


                        GroupCommentListADP commentListADP=new GroupCommentListADP(Comment.CommentsList,PostID);
                        CommentsLV.setAdapter(commentListADP);

                        Toast.makeText(GroupActivity.this,"Commented!",Toast.LENGTH_LONG).show();
                    }




                }
            });

            LikeB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    DataBase PostLike=new DataBase(GroupActivity.this);
                    if(LikeB.getText().toString().matches("Like"))
                    {
                        LikeB.setText("Unlike");

                        Like like=new Like(Like.LikesList.size()+1,PostID,UserID);

                        PostLike.InsertLike(like);
                        Like.LikesList.add(like);

                        int Likecount2=0;
                        for (int z=0;z<Like.LikesList.size();z++)
                        {

                            if(Like.LikesList.get(z).getPostID()==PostID)
                            {
                                Likecount2++;
                            }
                        }

                        PostLikes.setText(Likecount2+"Likes");

                    }
                    else if(LikeB.getText().toString().matches("Unlike"))
                    {
                        LikeB.setText("Like");

                        for (int k=0;k<Like.LikesList.size();k++)
                        {
                            if(Like.LikesList.get(k).getPostID()==PostID && Like.LikesList.get(k).getUserID()==UserID)
                            {
                                String id=String.valueOf(Like.LikesList.get(k).getLikeID());
                                PostLike.DeleteLike(id);
                                Like.LikesList.remove(k);


                            }
                        }

                        int Likecount2=0;
                        for (int z=0;z<Like.LikesList.size();z++)
                        {

                            if(Like.LikesList.get(z).getPostID()==PostID)
                            {
                                Likecount2++;
                            }
                        }

                        PostLikes.setText(Likecount2+"Likes");
                    }
                }
            });





            return view1;
        }

    }


    public class GroupCommentListADP extends BaseAdapter
    {

        ArrayList<Comment> Comments=new ArrayList<>();

        public GroupCommentListADP(ArrayList<Comment> comments,int postID)
        {
            for(int x=0;x<comments.size();x++)
            {
                if(comments.get(x).getPostID()==postID)
                {
                    this.Comments.add(comments.get(x));
                }
            }
        }

        @Override
        public int getCount()
        {
            return this.Comments.size();
        }

        @Override
        public Object getItem(int i) {
            return this.Comments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.comment_list,null);

            TextView cName=(TextView) view1.findViewById(R.id.CommentNameTV);
            TextView cDate=(TextView) view1.findViewById(R.id.CommentDateTV);
            TextView cText=(TextView) view1.findViewById(R.id.CommentTV);

            for(int j=0;j<User.UsersList.size();j++)
            {
                if(this.Comments.get(i).getUserID()==User.UsersList.get(j).getID())
                {
                    cName.setText(String.valueOf(User.UsersList.get(j).getName()));
                    cDate.setText(String.valueOf(this.Comments.get(i).getTime()));
                    cText.setText(String.valueOf(this.Comments.get(i).getCommentText()));
                }
            }




            return view1;
        }
    }
}