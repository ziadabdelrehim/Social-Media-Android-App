package com.example.socialnetwork;

import java.util.ArrayList;

public class FriendRequest
{
    public static ArrayList<FriendRequest> FriendReqList=new ArrayList<>();

    private int FriendReqID;
    private int SRCuserID;
    private int DSTuserID;
    private String Status;


    public FriendRequest()
    {

    }

    public FriendRequest(int friendReqID,String status,int srcUser,int dstUser)
    {
        this.FriendReqID=friendReqID;
        this.SRCuserID=srcUser;
        this.DSTuserID=dstUser;
        this.Status=status;
    }


    public int getFriendshipID() {
        return FriendReqID;
    }

    public void setFriendshipID(int friendReqID) {
        FriendReqID = friendReqID;
    }

    public int getSRCuserID() {
        return SRCuserID;
    }

    public void setSRCuserID(int SRCuserID) {
        this.SRCuserID = SRCuserID;
    }

    public int getDSTuserID() {
        return DSTuserID;
    }

    public void setDSTuserID(int DSTusedID) {
        this.DSTuserID = DSTusedID;
    }

    public String getStatus()
    {
        return Status;
    }

    public void setStatus(String status)
    {
        Status = status;
    }
}
