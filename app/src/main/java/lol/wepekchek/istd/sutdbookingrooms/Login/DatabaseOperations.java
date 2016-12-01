package lol.wepekchek.istd.sutdbookingrooms.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOperations extends SQLiteOpenHelper {


    public DatabaseOperations(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "CURRENT_ID.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STUDENTS( STUDENTID TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENTS;");
        onCreate(db);
    }

    public void insertStudent(String studentID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("STUDENTID", studentID);
        this.getWritableDatabase().insertOrThrow("STUDENTS", "", contentValues);
    }

    public void deleteStudent(String studentID) {
        this.getWritableDatabase().delete("STUDENTS", "STUDENTID='" + studentID + "'", null);
    }

    public String displayStudents() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS", null);
        String storedID = "";
        while (cursor.moveToNext()) {
            storedID = cursor.getString(0);
        }
        return storedID;
    }

    public void deleteAll() {
        this.getWritableDatabase().delete("STUDENTS", null, null);
    }
}