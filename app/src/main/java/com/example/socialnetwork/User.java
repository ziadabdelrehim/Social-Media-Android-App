package com.example.socialnetwork;

import java.util.ArrayList;

public class User
{
    public static ArrayList<User> UsersList=new ArrayList<>();


    int ID;
    String Name;
    String Email;
    String Password;
    String Phone;
    String UserName;

    public  User()
    {

    }

    public User(int id,String name,String email,String pass,String phone,String userName)
    {
        this.ID=id;
        this.Name=name;
        this.Email=email;
        this.Password=pass;
        this.Phone=phone;
        this.UserName=userName;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getID()
    {
        return ID;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getName()
    {
        return Name;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setPassword(String password)
    {
        Password = password;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPhone(String phone)
    {
        Phone = phone;
    }

    public String getPhone()
    {
        return Phone;
    }

    public void setUserName()
    {
        UserName = Name.concat((String) Integer.toString(this.ID)).replaceAll("\\s","");
    }

    public String getUserName()
    {
        return UserName;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Phone='" + Phone + '\'' +
                ", UserName='" + UserName + '\'' +
                '}';
    }

    public static boolean HavingFriendship(int SrcID,int DstID)
    {
        boolean flag=false;

        for(int i=0;i<UserFriend.userFriendsList.size();i++)
        {
            if(UserFriend.userFriendsList.get(i).getSRCuserID()==SrcID && UserFriend.userFriendsList.get(i).getDSTuserID()==DstID)
            {
                flag=true;
                return flag;
            }
            else if(UserFriend.userFriendsList.get(i).getSRCuserID()==DstID && UserFriend.userFriendsList.get(i).getDSTuserID()==SrcID)
            {
                flag=true;
                return flag;
            }
        }
        return flag;
    }
}
