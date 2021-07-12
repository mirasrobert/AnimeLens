package com.example.imotaku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.imotaku.model.FavoriteAnime;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    private static final String TABLE_NAME = "favorites";
    // Table Columns
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SCORE = "score";
    private static final String EPISODES = "episodes";
    private static final String TYPE = "type";
    private static final String IMG = "image";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "imotaku", null, 1);
    }

    // This is called the first time a database is accessed.
    // There should be a code to generate a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableFavorites = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT, " + TYPE + " TEXT," + SCORE + " TEXT," + EPISODES + " TEXT," + IMG + " TEXT)";
        db.execSQL(createTableFavorites);
    }

    // This is called if the database version number changes.
    // It prevents previous users apps from breaking when you change database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String createTableFavorites = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT, " + TYPE + " TEXT," + SCORE + " TEXT," + EPISODES + " TEXT," + IMG + " TEXT)";
        db.execSQL(createTableFavorites);

        return true;
    }

    // Insert one favorite anime
    public boolean addOne(FavoriteAnime favoriteAnime) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ID, favoriteAnime.getId());
        cv.put(NAME, favoriteAnime.getName());
        cv.put(TYPE, favoriteAnime.getType());
        cv.put(SCORE, favoriteAnime.getScore());
        cv.put(EPISODES, favoriteAnime.getEpisodes());
        cv.put(IMG, favoriteAnime.getImage_url());

        long insert = db.insert(TABLE_NAME, null, cv);

        return insert > -1;
    }

    // Get All of the Favorite anime
    public List<FavoriteAnime> getAll() {

        List<FavoriteAnime> favoriteAnimeList = new ArrayList<>();

        // Get Data From Database
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL Statement
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            // Loop through cursor (result set) and create new favorite anime to view them.
            do {
                int animeID = cursor.getInt(0);
                String animeName = cursor.getString(1);
                String animeType = cursor.getString(2);
                String animeScore = cursor.getString(3);
                String animeEpisodes = cursor.getString(4);
                String animeImg = cursor.getString(5);

                FavoriteAnime favoriteAnime = new FavoriteAnime(animeID, animeName, animeType, animeScore, animeEpisodes, animeImg);
                favoriteAnimeList.add(favoriteAnime);

            } while (cursor.moveToNext());

        }  // If there are no results in DB


        cursor.close();
        db.close();

        return favoriteAnimeList;
    }

    // Get One
    public boolean getOne(int id) {
        // Find the favorite anime in the database. If it found, delete it and return true
        // Otherwise not found, return false

        List<FavoriteAnime> favoriteAnimeOne = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            // Loop through cursor (result set) and create new favorite anime to view them.
            do {
                int animeID = cursor.getInt(0);
                String animeName = cursor.getString(1);
                String animeType = cursor.getString(2);
                String animeScore = cursor.getString(3);
                String animeEpisodes = cursor.getString(4);
                String animeImg = cursor.getString(5);

                FavoriteAnime favoriteAnime = new FavoriteAnime(animeID, animeName, animeType, animeScore, animeEpisodes, animeImg);
                favoriteAnimeOne.add(favoriteAnime);

            } while (cursor.moveToNext());

        }  // If there are no results in DB


        cursor.close();
        db.close();

        // Now Check if anime exist
        return favoriteAnimeOne.size() != 0;
    }

    // Delete One of the favorite anime
    public boolean deleteOne(FavoriteAnime favoriteAnime) {
        // Find the favorite anime in the database. If it found, delete it and return true
        // Otherwise not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = " + favoriteAnime.getId();

        // Execute the query
         db.execSQL(sql);

        //Close the database
        db.close();

        return true;
    }

    // Delete All Data on Table
    public boolean deleteAll(FavoriteAnime favoriteAnime) {
        // Find the favorite anime in the database. If it found, delete it and return true
        // Otherwise not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        return cursor.moveToFirst();

    }

    // For Reset Purposes
    public boolean dropTable() {

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        return true;

    }
}
