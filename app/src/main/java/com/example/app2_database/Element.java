package com.example.app2_database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones_table")
public class Element {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "producent")
    private String producent;

    @NonNull
    @ColumnInfo(name = "model")
    private String model;

    @NonNull
    @ColumnInfo(name = "version")
    private String version;

    @NonNull
    @ColumnInfo(name = "www")
    private String www;
    public Element() {}

    public Element(@NonNull String p, @NonNull String m, @NonNull String v, @NonNull String w) {
        producent = p;
        model = m;
        version = v;
        www = w;
    }
    @Ignore
    public Element(long i, @NonNull String p, @NonNull String m, @NonNull String v, @NonNull String w) {
        id = i;
        producent = p;
        model = m;
        version = v;
        www = w;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getProducent() {
        return producent;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    @NonNull
    public String getVersion() {
        return version;
    }

    @NonNull
    public String getWww() {
        return www;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProducent(@NonNull String producent) {
        this.producent = producent;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public void setVersion(@NonNull String version) {
        this.version = version;
    }

    public void setWww(@NonNull String www) {
        this.www = www;
    }
}
