package com.example.socialnetwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Post
{
    public static ArrayList<Post> PostsList=new ArrayList<>();

    private int PostID;
    private int UserID;
    private String Text;
    private String CreatedAt;
    private String PostType;
    private int DstGroupID;

    public Post()
    {

    }


    public Post(int id, String text, String createdAt, int userID)
    {
        this.PostID=id;
        this.UserID=userID;
        this.Text=text;
        this.CreatedAt=createdAt;
        this.PostType="Regular Post";

    }

    public Post(int id,String text,String createdAt,String postType,int userID,int dstGroupID)
    {
        this.PostID=id;
        this.UserID=userID;
        this.Text=text;
        this.CreatedAt=createdAt;
        this.PostType=postType;
        this.DstGroupID=dstGroupID;
    }


    public Post(int id, String text, int userID)
    {
        this.PostID=id;
        this.UserID=userID;
        this.Text=text;

    }


    public void setPostID(int postID)
    {
        PostID = postID;
    }

    public void setUserID(int userID)
    {
        UserID = userID;
    }

    public void setText(String text)
    {
        Text = text;
    }

    public void setCreatedAt(String createdAt)
    {
        CreatedAt = createdAt;
    }

    public int getPostID()
    {
        return PostID;
    }

    public int getUserID()
    {
        return UserID;
    }

    public String getText()
    {
        return Text;
    }

    public String getCreatedAt()
    {
        return CreatedAt;
    }

    public String getPostType() {
        return PostType;
    }

    public void setPostType(String postType) {
        PostType = postType;
    }

    public int getDstGroupID() {
        return DstGroupID;
    }

    public void setDstGroupID(int dstGroupID) {
        DstGroupID = dstGroupID;
    }
}
