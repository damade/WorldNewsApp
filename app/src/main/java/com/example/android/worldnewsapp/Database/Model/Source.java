package com.example.android.worldnewsapp.Database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = NewsLocal.class,
                parentColumns = "sourceId",
                childColumns = "id"
        )})

public class Source {

    @PrimaryKey(autoGenerate = true)
    private int sourceId;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "source_name")
    private String name;

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
