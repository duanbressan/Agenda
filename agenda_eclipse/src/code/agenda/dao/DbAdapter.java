package code.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import code.agenda.entidade.Contato;

/**
 * 
 * @author bruno
 *
 */
public class DbAdapter {
	public static String Lock = "dblock";
	private SQLiteDatabase database = null;
	private DbHelper sqliteOpenHelper = null;
	private String[] columns = {ConstantesBanco.Banco.ID, ConstantesBanco.Banco.NOME, ConstantesBanco.Banco.EMAIL, ConstantesBanco.Banco.TELEFONE };
	
	public DbAdapter(Context context) {
		sqliteOpenHelper = new DbHelper(context);
	}
	
	//abrir conexao para ler os dados do banco
	public void open() throws SQLException {
		if(database == null) {
			database = sqliteOpenHelper.getReadableDatabase();
		} else if(!database.isOpen()) {
			database = sqliteOpenHelper.getReadableDatabase();
		}
	}
	
	public boolean isOpen() {
		return database.isOpen();
	}
	
	//fechar conexao
	public void close() {
		sqliteOpenHelper.close();
	}

    /**
	 * Metodo responsavel por inserir contato no banco de dados
	 * @param contato
	 * @return verdadeiro se for inserido com sucesso
	 */
    public boolean inserir(Context mContext, Contato contato){
        synchronized(Lock) {
        	database  = mContext.openOrCreateDatabase(ConstantesBanco.Banco.DATABASE_NAME, Context.MODE_PRIVATE, null);
            boolean retorno = false;
    		try {
    			ContentValues values = new ContentValues();
    			values.put(ConstantesBanco.Banco.NOME, contato.getNome());
    			values.put(ConstantesBanco.Banco.EMAIL, contato.getEmail());
    			values.put(ConstantesBanco.Banco.TELEFONE, contato.getTelefone());
    			
    			//recebe o id do banco
    			long id = database.insert(ConstantesBanco.Banco.TABLE_NAME, null, values);
    			
    			Cursor cursor = database.query(ConstantesBanco.Banco.TABLE_NAME, columns, ConstantesBanco.Banco.ID + " = " + id, null, null, null, null);
    			cursor.moveToFirst();
    			cursor.close();
    			retorno = true;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		return retorno;
        }
    }
	
	/**
	 * Metodo responsavel por excluir contato no banco de dados
	 * @param contato
	 * @return verdadeiro se for deletado com sucesso
	 */
	public boolean excluir(Context mContext, Contato contato) {
		boolean retorno = false;
		synchronized(Lock) {
			try {
				database  = mContext.openOrCreateDatabase(ConstantesBanco.Banco.DATABASE_NAME, Context.MODE_PRIVATE, null);
				database.delete(ConstantesBanco.Banco.TABLE_NAME, ConstantesBanco.Banco.ID + " = " + contato.getId(), null);
				database.close();
				retorno = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retorno;
        }
	}
	
	/**
	 * Obtem uma lista de todos os contatos cadastrado no banco de dados
	 * @return lista de contatos
	 */
	public List<Contato> getContatos() {
		List<Contato> lista = new ArrayList<Contato>();
		
		Cursor cursor = database.query(ConstantesBanco.Banco.TABLE_NAME, columns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			Contato contato = new Contato();
			contato.setId(cursor.getLong(0));
			contato.setNome(cursor.getString(1));
			contato.setEmail(cursor.getString(2));
			contato.setTelefone(cursor.getString(3));
			lista.add(contato);
			cursor.moveToNext();
		}
		cursor.close();
		return lista;
	}
	
	/**
	 * Obtem contato especifico
	 * @param id do contato
	 * @return contato
	 */
	public Contato getContato(int id) {
		Contato contato = null;
		
		database = sqliteOpenHelper.getReadableDatabase();
		
		Cursor cursor = database.query(ConstantesBanco.Banco.TABLE_NAME, columns, ConstantesBanco.Banco.ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
	 
		if(cursor != null) {
			cursor.moveToFirst();
			contato = new Contato();
			contato.setId(cursor.getLong(0));
			contato.setNome(cursor.getString(1));
			contato.setEmail(cursor.getString(2));
			contato.setTelefone(cursor.getString(3));
		}
		return contato;
	}
}
