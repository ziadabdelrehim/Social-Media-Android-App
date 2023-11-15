# Social Media Android App

## Overview

This Android application is a social media platform that allows users to create accounts, make friends, create and interact with posts, engage in one-on-one conversations, and participate in group communities.

## Demo Video

Check out the [Demo Video](https://github.com/ziadabdelrehim/Social-Media-Android-App/assets/80210355/743b9f4e-c942-4dbf-9ed5-7d935a35a36d) to see the app in action.


## Features

1. **Account Creation:**
   - Users can create accounts by providing their name and phone number.

2. **Friend Management:**
   - Users can send and accept friend requests.
   - View and manage their list of friends.

3. **Post Creation and Interaction:**
   - Users can create posts on their profile.
   - Friends can like and comment on posts.

4. **Chatting Room:**
   - Users can have one-on-one conversations with their friends.

5. **Group Interaction:**
   - Users can create and join group communities.
   - Group members can post, like, and comment within the group.

## Technologies Used

- Android Studio
- Java/Kotlin
- SQLite Database
- Firebase Authentication (if applicable)

## Database Schema

The application uses an SQLite database with the following tables:
- `USERS`: Stores user information.
- `UsersPosts`: Stores user posts with foreign key references to users and groups.
- `Comments`: Stores comments on posts with foreign key references to users and posts.
- `Likes`: Stores likes on posts with foreign key references to users and posts.
- `FriendRequests`: Stores friend requests with foreign key references to users.
- `UserFriends`: Stores user friendships with foreign key references to users.
- `Messages`: Stores one-on-one chat messages with foreign key references to users.
- `Groups`: Stores group information with foreign key references to users.
- `GroupMembers`: Stores group memberships with foreign key references to users and groups.


