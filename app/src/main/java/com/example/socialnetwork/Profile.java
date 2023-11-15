package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Profile extends AppCompatActivity
{
    private int Userid;
    private TextView pName;
    private TextView pPhone;
    private Button ProfPostB;
    private EditText PostText;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent=getIntent();
        int UserID=intent.getExtras().getInt("id");
        Userid=UserID;

        DataBase PostData=new DataBase(this);



        pName=(TextView) findViewById(R.id.ProfNameTV);
        pName.setText(User.UsersList.get(UserID-1).getName());

        pPhone=(TextView) findViewById(R.id.ProfPhoneTV);
        pPhone.setText(User.UsersList.get(UserID-1).getPhone());


        ListView PostsLV=findViewById(R.id.ProfPostList);

        Collections.reverse(Post.PostsList);
        PostListADP a=new PostListADP(Post.PostsList);
        PostsLV.setAdapter(a);





        ProfPostB=(Button) findViewById(R.id.ProfPostB);
        PostText=(EditText) findViewById(R.id.ProfPostTF);

        ProfPostB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(PostText.getText().toString().matches(""))
                {
                    Toast.makeText(Profile.this,"Empty Post",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Post post=new Post(Post.PostsList.size()+1,PostText.getText().toString(),Userid);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm");
                    Date date=new Date(System.currentTimeMillis());
                    post.setCreatedAt(simpleDateFormat.format(date));
                    Post.PostsList.add(post);
                    PostData.InsertPost(post);

                    Toast.makeText(Profile.this,"Posted!",Toast.LENGTH_LONG).show();
                    PostText.setText("");
                    Collections.reverse(Post.PostsList);
                    PostListADP a=new PostListADP(Post.PostsList);
                    PostsLV.setAdapter(a);

                }
            }
        });











    }

    public class PostListADP extends BaseAdapter
    {
        ArrayList<Post> Posts=new ArrayList<>();


        public PostListADP(ArrayList<Post> posts)
        {
            for(int x=0;x<posts.size();x++)
            {
                if(posts.get(x).getUserID()==Userid)
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

            CommentListADP commentListADP=new CommentListADP(Comment.CommentsList,this.Posts.get(i).getPostID());
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
                    if(Like.LikesList.get(z).getUserID()==Userid)
                    {
                        LikeB.setText("Unlike");
                    }
                }
            }

            PostLikes.setText(Likecount+"Likes");

            if(Userid==PublisherID)
            {
                PostPubName.setText(String.valueOf(User.UsersList.get(Userid-1).getName()));
                PostDate.setText(String.valueOf(this.Posts.get(i).getCreatedAt()));
                PostText.setText(String.valueOf(this.Posts.get(i).getText()));
            }




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
                                Userid);
                        DataBase PostComment=new DataBase(Profile.this);

                        Comment.CommentsList.add(comment);

                        PostComment.InsertComment(comment);
                        PostCommentsNum.setText(commentListADP.getCount() + "Comments");


                        CommentListADP commentListADP=new CommentListADP(Comment.CommentsList,PostID);
                        CommentsLV.setAdapter(commentListADP);

                        Toast.makeText(Profile.this,"Commented!",Toast.LENGTH_LONG).show();
                    }




                }
            });

            LikeB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    DataBase PostLike=new DataBase(Profile.this);
                    if(LikeB.getText().toString().matches("Like"))
                    {
                        LikeB.setText("Unlike");

                        Like like=new Like(Like.LikesList.size()+1,PostID,Userid);

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
                            if(Like.LikesList.get(k).getPostID()==PostID && Like.LikesList.get(k).getUserID()==Userid)
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

    public class CommentListADP extends BaseAdapter
{

    ArrayList<Comment> Comments=new ArrayList<>();

    public CommentListADP(ArrayList<Comment> comments,int postID)
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