package com.example.noteapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    public static final int DB_version = 2;
    public static String DB_NAME = "NotesDB.db";
    public static String DB_TABLE = "NotesTable";

    public String COLUMN_ID = "NOTESID";
    public String COLUMN_TITLE = "NOTESTITLE";
    public String COLUMN_DETAILS = "NOTESDETAILS";
    public String COLUMN_DATE = "NOTESDATE";
    public String COLUMN_TIME = "NOTESTIME";
    private SQLiteDatabase sqLiteDatabase;

    public NoteDatabase(@Nullable Context context) {
        super(context,DB_NAME, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

         String query = "CREATE TABLE " + DB_TABLE +
                 "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT , " +
                 COLUMN_TITLE + "TEXT," +
                 COLUMN_DETAILS + "TEXT, " +
                 COLUMN_DATE + "TEXT," +
                 COLUMN_TIME + "TEXT" + ")";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        if (i >= i1)
            return;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(sqLiteDatabase);
    }
    public long AddNote(NoteModel noteModel){
        long ID;
        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_TITLE, noteModel.getNoteTitle());
            contentValues.put(COLUMN_DETAILS, noteModel.getNoteDetails());
            contentValues.put(COLUMN_DATE, noteModel.getNoteDate());
            contentValues.put(COLUMN_TITLE, noteModel.getNoteTime());

            ID = db.insert(DB_TABLE, null, contentValues);

        Log.d("Insterted","id -->"+ID );
        return ID;

    }
    @SuppressLint("Recycle")
    public List<NoteModel> getNote(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<NoteModel> allNote = new ArrayList<>();
        Cursor cursor = null;
        String queryStatement = "SELECT * FROM " +  DB_TABLE;
       db.rawQuery(queryStatement,null);

        if(cursor!=null && cursor.moveToFirst()){
            do{
                NoteModel noteModel = new NoteModel();
                noteModel.setId(cursor.getInt(0));
                noteModel.setNoteTitle(cursor.getString(1));
                noteModel.setNoteDetails(cursor.getString(2));
                noteModel.setNoteDate(cursor.getString(3));
                noteModel.setNoteTitle(cursor.getString(4));

                allNote.add(noteModel);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return allNote;
    }
public  NoteModel getNotes(int id) {
    SQLiteDatabase db = this.getReadableDatabase();
    String[] query = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DETAILS, COLUMN_DATE, COLUMN_TIME};
    Cursor cursor = db.query(DB_TABLE, query, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();

    return new NoteModel(
            Integer.parseInt(cursor.getString(0)),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4));
}
  void deleteNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_TABLE,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
  }
}
