package code.agenda.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import code.agenda.R;
import code.agenda.controller.ControllerContato;

/**
 * 
 * @author bruno
 * 
 */
public class ItemListActivity extends FragmentActivity implements ItemListFragment.Callbacks {

	/**
	 * Modo para carregar em dispositivo tablet
	 */
	private boolean isTabletDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		
		if (findViewById(R.id.item_detail_container) != null) {
			isTabletDevice = true;
			((ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list)).setActivateOnItemClick(true);
		}
	}

	/**
	 * Ao selecionar um contato para visualizar detalhes, o bundle recebe parametro de qual id do contato foi selecionado
	 * Activity ViewDetalheContato
	 */
	@Override
	public void onItemSelected(String id) {
		//se for verdadeiro, o dispositivo esta sendo usado em um tablet
		if (isTabletDevice) {			
			//passa por parametro qual foi o contato selecionado
			Bundle arguments = new Bundle();
			arguments.putString(ViewDetalheContato.ARG_ITEM_ID, id);
			ViewDetalheContato fragment = new ViewDetalheContato();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
			
		} else {
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ViewDetalheContato.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent i = new Intent(this, ControllerContato.class);
		startActivity(i);
		
		return super.onOptionsItemSelected(item);
	}
}
