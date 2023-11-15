package com.example.socialnetwork;

import java.util.ArrayList;

public class Like
{
    public static ArrayList<Like> LikesList=new ArrayList<>();
    private int LikeID;
    private int PostID;
    private int UserID;


    public Like(int likeID,int postID,int userID)
    {
        this.LikeID=likeID;
        this.PostID=postID;
        this.UserID=userID;
    }


    public int getLikeID()
    {
        return LikeID;
    }

    public void setLikeID(int likeID)
    {
        LikeID = likeID;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID)
    {
        PostID = postID;
    }

    public int getUserID()
    {
        return UserID;
    }

    public void setUserID(int userID)
    {
        UserID = userID;
    }
}
