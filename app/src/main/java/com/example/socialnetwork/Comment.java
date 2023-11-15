package com.example.socialnetwork;

import java.util.ArrayList;

public class Comment
{
    public static ArrayList<Comment> CommentsList=new ArrayList<>();

    private int CommentID;
    private String CommentText;
    private String Time;
    private int PostID;
    private int UserID;



    public Comment(int commentID,String commentText,String time,int postID,int userID)
    {
        this.CommentID=commentID;
        this.Time=time;
        this.PostID=postID;
        this.UserID=userID;
        this.CommentText=commentText;
    }


    public void setCommentID(int commentID)
    {
        CommentID = commentID;
    }

    public void setPostID(int postID)
    {
        PostID = postID;
    }

    public void setUserID(int userID)
    {
        UserID = userID;
    }

    public void setCommentText(String commentText)
    {
        CommentText = commentText;
    }

    public int getCommentID()
    {
        return CommentID;
    }

    public int getPostID()
    {
        return PostID;
    }

    public int getUserID()
    {
        return UserID;
    }

    public String getCommentText()
    {
        return CommentText;
    }

    public void setTime(String time)
    {
        Time = time;
    }

    public String getTime()
    {
        return Time;
    }
}
