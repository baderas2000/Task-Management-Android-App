package com.example.se2_team06.model.notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_contact")
public class UserContact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contact_type")
    private UserContactType contactType;

    @ColumnInfo(name = "contact_value")
    private String contactValue;

    public UserContact() {
    }

    public UserContact(UserContactType contactType, String contactValue) {
        this.contactType = contactType;
        this.contactValue = contactValue;
    }

    public UserContactType getContactType() {
        return contactType;
    }

    public void setContactType(UserContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserContact{" +
                "id=" + id +
                ", contactType=" + contactType +
                ", contactValue='" + contactValue + '\'' +
                '}';
    }
}
