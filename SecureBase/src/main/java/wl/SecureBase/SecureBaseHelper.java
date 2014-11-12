package wl.SecureBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huang and slavnic on 29/10/14.
 */

public class SecureBaseHelper extends SQLiteOpenHelper{
    private static final String TABLE = "secureT";
    private static final String COL_ID = "id";
    private static final String COL_KEY = "key";
    private static final String COL_DATA = "data";
    private static final String COL_IV = "IV";
    private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE + " ( "
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_KEY + " TEXT NOT NULL, "
            + COL_DATA + " TEXT NOT NULL, "+ COL_IV + " TEXT NOT NULL);";

    public SecureBaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);

    }

}
