package com.vignesh.a44.quizapp.Schema;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class UsersSchema {
    @Id
    @Field("_id")
    private String userId;
    @Field("username")
    private String username;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field("createdAt")
    private long createdAt;
    @Field("modifiedAt")
    private long modifiedAt;
    @Field("isVerified")
    private boolean isVerified;
    @Field("isAdmin")
    private boolean isAdmin;
    @Field("isAuthor")
    private boolean isAuthor;

    public UsersSchema() {
        this.userId = new ObjectId().toHexString();
    }

    public UsersSchema(String username, String email, String password, boolean isAuthor, boolean isAdmin) {
        this.userId = new ObjectId().toHexString();
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = System.currentTimeMillis();
        this.isAuthor = isAuthor;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setActive(boolean Admin) {
        isAdmin = Admin;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    @Override
    public String toString() {
        return "UsersSchema{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", isVerified=" + isVerified +
                ", isAdmin=" + isAdmin +
                ", isAuthor=" + isAuthor +
                '}';
    }
}
