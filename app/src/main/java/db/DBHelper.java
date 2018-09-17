package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//참고자료:
//1. http://blog.naver.com/PostView.nhn?blogId=hee072794&logNo=220619425456
//2. http://cocomo.tistory.com/409
public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    //DB 생성
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQLite Database로 쿼리 실행
        db.execSQL(" CREATE TABLE BLOGLIST ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, BLOG_NAME TEXT, BLOG_URL TEXT, BLOG_ARTICLE_DB TEXT, BLOG_API_ADDRESS TEXT, BLOG_API_ID TEXT, API_TOKEN TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }
}
