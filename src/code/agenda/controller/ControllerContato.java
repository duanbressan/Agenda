package code.agenda.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import code.agenda.R;
import code.agenda.dao.DbAdapter;
import code.agenda.entidade.Contato;
import code.agenda.view.ItemListActivity;

/**
 * 
 * @author bruno
 *
 */
public class ControllerContato extends Activity {
	
	private DbAdapter database = null;
	
	private EditText edtNome = null;
	private EditText edtEmail = null;
	private EditText edtTelefone = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_insert);
		
		// Mostra o botao para voltar (Action Bar)
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		database = new DbAdapter(this);
		
		edtNome = (EditText) findViewById(R.id.edtNome);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtTelefone = (EditText) findViewById(R.id.edtTelefone);
	}
	
	/**
	 * Adicionar contato no banco de dados
	 */
	private void adicionar() {
		Contato contato = new Contato();
		if(edtNome.getText().toString().isEmpty() 
				|| edtEmail.getText().toString().isEmpty() 
				|| edtTelefone.getText().toString().isEmpty()) {
			Toast.makeText(getApplication(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
		} else {
			try {
				
				contato.setNome(edtNome.getText().toString());
				contato.setEmail(edtEmail.getText().toString());
				contato.setTelefone(edtTelefone.getText().toString());
				
				database.open();
				database.inserir(ControllerContato.this, contato);
				database.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finish();
		}
	}
	
	/**
	 * Ao clicar na ActionBar, volta para ItemListActivity novamente
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
			return true;
		case R.id.action_salve:
			adicionar();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.insert, menu);
		return true;
	}
	
}
