package com.example.socialnetwork;

import java.util.ArrayList;

public class Group
{
    public static ArrayList<Group> GroupList=new ArrayList<>();

    private int GroupID;
    private String GroupName;
    private int AdminID;

    public Group()
    {

    }

    public Group(int groupID, String groupName, int adminID)
    {
        GroupID = groupID;
        GroupName = groupName;
        AdminID = adminID;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public int getAdminID() {
        return AdminID;
    }

    public void setAdminID(int adminID) {
        AdminID = adminID;
    }
}
