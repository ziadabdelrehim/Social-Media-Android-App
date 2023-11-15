package com.example.socialnetwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    public static final String DbName = "socialnetwork.db";

    public DataBase(Context context) {
        super(context, DbName, null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USERS(ID INTEGER PRIMARY KEY,Name TEXT,Email TEXT,Pass TEXT,Phone TEXT,UserName TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE UsersPosts(PostID INTEGER PRIMARY KEY,Post TEXT,CreatedAT DATE,PostType TEXT,UserID INTEGER,DstGroupID INTEGER,FOREIGN KEY(UserID) REFERENCES USERS(ID),FOREIGN KEY(DstGroupID) REFERENCES Groups(GroupID))");
        sqLiteDatabase.execSQL("CREATE TABLE Comments(CommentID INTEGER PRIMARY KEY,Comment TEXT,Time DATE,PostID INTEGER,UserID INTEGER,FOREIGN KEY(PostID) REFERENCES UsersPosts(PostID),FOREIGN KEY(UserID) REFERENCES USERS(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE Likes(LikeID INTEGER PRIMARY KEY,PostID INTEGER,UserID INTEGER,FOREIGN KEY(PostID) REFERENCES UsersPosts(PostID),FOREIGN KEY(UserID) REFERENCES USERS(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE FriendRequests(FriendReqID INTEGER PRIMARY KEY,Status TEXT,SrcID INTEGER,DstID INTEGER,FOREIGN KEY(SrcID) REFERENCES USERS(ID),FOREIGN KEY(DstID) REFERENCES USERS(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE UserFriends(FriendshipID INTEGER PRIMARY KEY,SrcID INTEGER,DstID INTEGER,FOREIGN KEY(SrcID) REFERENCES USERS(ID),FOREIGN KEY(DstID) REFERENCES USERS(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE Messages(MessageID INTEGER PRIMARY KEY,Message TEXT,SrcID INTEGER,DstID INTEGER,FOREIGN KEY(SrcID) REFERENCES USERS(ID),FOREIGN KEY(DstID) REFERENCES USERS(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE Groups(GroupID INTEGER PRIMARY KEY,GroupName TEXT,AdminID INTEGER,FOREIGN KEY(AdminID) REFERENCES USERS(ID))");
        sqLiteDatabase.execSQL("CREATE TABLE GroupMembers(MembershipID INTEGER PRIMARY KEY,MembershipType TEXT,GroupID INTEGER,MemberID INTEGER,FOREIGN KEY(GroupID) REFERENCES Groups(GroupID),FOREIGN KEY(MemberID) REFERENCES USERS(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USERS");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS UsersPosts");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Comments");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Likes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS FriendRequests");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS UserFriends");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Messages");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Groups");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS GroupMembers");
        onCreate(sqLiteDatabase);
    }

    public void InsertUser(User user) {
        SQLiteDatabase s = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("ID", user.getID());
        contentValues.put("Name", user.getName());
        contentValues.put("Email", user.getEmail());
        contentValues.put("Pass", user.getPassword());
        contentValues.put("Phone", user.getPhone());
        contentValues.put("UserName", user.getUserName());
        s.insert("USERS", null, contentValues);

    }


    public ArrayList<User> GetUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase s = this.getReadableDatabase();

        Cursor cursor = s.rawQuery("SELECT * FROM USERS", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            users.add(
                    new User(
                            cursor.getInt(0),  //ID
                            cursor.getString(1), //Name
                            cursor.getString(2), //Email
                            cursor.getString(3), //Pass
                            cursor.getString(4), //Phone
                            cursor.getString(5))); //UserName

            cursor.moveToNext();

        }


        return users;
    }


    public void InsertPost(Post post) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("PostID", post.getPostID());
        contentValues.put("Post", post.getText());
        contentValues.put("CreatedAT", post.getCreatedAt());
        contentValues.put("PostType",post.getPostType());
        contentValues.put("UserID", post.getUserID());
        contentValues.put("DstGroupID",post.getDstGroupID());
        sqLiteDatabase.insert("UsersPosts", null, contentValues);
    }

    public ArrayList<Post> GetPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        SQLiteDatabase s = this.getReadableDatabase();

        Cursor cursor = s.rawQuery("SELECT * FROM UsersPosts", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            posts.add(
                    new Post(cursor.getInt(0),  //PostID
                            cursor.getString(1), //Post
                            cursor.getString(2),//Date
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5)));//UserID

            cursor.moveToNext();

        }


        return posts;
    }


    public void InsertComment(Comment comment) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CommentID", comment.getCommentID());
        contentValues.put("Comment", comment.getCommentText());
        contentValues.put("Time", comment.getTime());
        contentValues.put("PostID", comment.getPostID());
        contentValues.put("UserID", comment.getUserID());
        sqLiteDatabase.insert("Comments", null, contentValues);
    }

    public ArrayList<Comment> GetComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Comments", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            comments.add(
                    new Comment(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getInt(4)));

            cursor.moveToNext();

        }


        return comments;
    }

    public void InsertLike(Like like) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("LikeID", like.getLikeID());
        contentValues.put("PostID", like.getPostID());
        contentValues.put("UserID", like.getUserID());
        sqLiteDatabase.insert("Likes", null, contentValues);
    }

    public ArrayList<Like> GetLikes() {
        ArrayList<Like> likes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Likes", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            likes.add(
                    new Like(cursor.getInt(0),  //LikeID
                            cursor.getInt(1), //PostID
                            cursor.getInt(2)));//UserID

            cursor.moveToNext();

        }


        return likes;
    }

    public void DeleteLike(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("Likes", "LikeID=?", new String[]{id});
    }

    public void InsertFreindReq(FriendRequest friendRequest) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("FriendReqID", friendRequest.getFriendshipID());
        contentValues.put("Status", friendRequest.getStatus());
        contentValues.put("SrcID", friendRequest.getSRCuserID());
        contentValues.put("DstID", friendRequest.getDSTuserID());

        sqLiteDatabase.insert("FriendRequests", null, contentValues);
    }

    public ArrayList<FriendRequest> GetFriendReq() {
        ArrayList<FriendRequest> friendRequests = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Likes", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            friendRequests.add(
                    new FriendRequest(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)));

            cursor.moveToNext();

        }


        return friendRequests;
    }

    public void DeleteFriendReq(FriendRequest friendRequest) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("FriendRequests", "FriendReqID=?", new String[]{String.valueOf(friendRequest.getFriendshipID())});
    }

    public void InsertNewFriend(UserFriend userFriend) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("FriendshipID", userFriend.getFriendshipID());
        contentValues.put("SrcID", userFriend.getSRCuserID());
        contentValues.put("DstID", userFriend.getDSTuserID());

        sqLiteDatabase.insert("UserFriends", null, contentValues);

    }

    public ArrayList<UserFriend> GetUserFriends() {
        ArrayList<UserFriend> userFriends = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM UserFriends", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            userFriends.add(
                    new UserFriend(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2)));

            cursor.moveToNext();

        }

        return userFriends;
    }

    public void InsertMessage(Message message) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("MessageID", message.getMessageID());
        contentValues.put("Message", message.getMessageText());
        contentValues.put("SrcID", message.getSrcID());
        contentValues.put("DstID", message.getDstID());

        sqLiteDatabase.insert("Messages", null, contentValues);
    }

    public ArrayList<Message> GetMessages() {
        ArrayList<Message> messagesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Messages", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            messagesList.add(
                    new Message(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)));

            cursor.moveToNext();

        }

        return messagesList;
    }


    public void InsertGroup(Group group) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("GroupID", group.getGroupID());
        contentValues.put("GroupName", group.getGroupName());
        contentValues.put("AdminID", group.getAdminID());
        sqLiteDatabase.insert("Groups", null, contentValues);
    }


    public ArrayList<Group> GetGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Groups", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            groups.add(
                    new Group(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2)));

            cursor.moveToNext();

        }

        return groups;
    }


    public void InsertGroupMember(GroupMember groupMember) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("MembershipID", groupMember.getMemberShipID());
        contentValues.put("MembershipType", groupMember.getType());
        contentValues.put("GroupID",groupMember.getGroupID());
        contentValues.put("MemberID",groupMember.getUserID());
        sqLiteDatabase.insert("GroupMembers", null, contentValues);
    }



    public ArrayList<GroupMember> GetGroupMembers() {
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GroupMembers", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {


            groupMembers.add(
                    new GroupMember(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)));

            cursor.moveToNext();

        }

        return groupMembers;
    }

}
