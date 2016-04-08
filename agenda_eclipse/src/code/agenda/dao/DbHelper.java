package code.agenda.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author bruno
 *
 */
public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, ConstantesBanco.Banco.DATABASE_NAME, null, ConstantesBanco.Banco.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ConstantesBanco.Banco.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("TAG", "Upgrading database from " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + ConstantesBanco.Banco.TABLE_NAME);
		onCreate(db);
	}
	
	
}
