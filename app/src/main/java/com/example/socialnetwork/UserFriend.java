package com.example.socialnetwork;

import java.util.ArrayList;

public class UserFriend
{
    public static ArrayList<UserFriend> userFriendsList=new ArrayList<>();

    private int FriendshipID;
    private int SRCuserID;
    private int DSTuserID;

    public UserFriend(int friendshipID,int srcUser,int dstUser)
    {
        this.FriendshipID=friendshipID;
        this.SRCuserID=srcUser;
        this.DSTuserID=dstUser;
    }


    public int getFriendshipID() {
        return FriendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        FriendshipID = friendshipID;
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
}
