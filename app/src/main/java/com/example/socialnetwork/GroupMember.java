package com.example.socialnetwork;

import java.util.ArrayList;

public class GroupMember
{
    public static ArrayList<GroupMember> groupMembersList=new ArrayList<>();
    private int MemberShipID;
    private String Type;
    private int GroupID;
    private int UserID;

    public GroupMember(int memberShipID, String type, int groupID, int userID) {
        MemberShipID = memberShipID;
        Type = type;
        GroupID = groupID;
        UserID = userID;
    }

    public int getMemberShipID() {
        return MemberShipID;
    }

    public void setMemberShipID(int memberShipID) {
        MemberShipID = memberShipID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public static boolean IsGroupMember(int groupID,int userID)
    {
        boolean flag=false;

        for(int i=0;i<groupMembersList.size();i++)
        {
            if(groupID==groupMembersList.get(i).GroupID && userID==groupMembersList.get(i).UserID)
            {
                flag=true;
            }
        }


        return flag;
    }
}
