package code.agenda.dao;

/**
 * 
 * @author bruno
 *
 */
public class ConstantesBanco {
	public class Banco {
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "agenda.db";
		
		public static final String TABLE_NAME = "contatos";
		public static final String ID = "id";
		public static final String NOME = "nome";
		public static final String EMAIL = "email";
		public static final String TELEFONE = "telefone";
		
		public static final String CREATE_TABLE = 
				"CREATE TABLE " + TABLE_NAME + " ( " + ID + " integer primary key autoincrement, " + 
				NOME + " text not null, " + EMAIL + " text not null, " + 
				TELEFONE + " text not null " + ");";
	}
}
