package code.agenda.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import code.agenda.R;

public class ItemDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(ViewDetalheContato.ARG_ITEM_ID, getIntent().getStringExtra(ViewDetalheContato.ARG_ITEM_ID));
			ViewDetalheContato fragment = new ViewDetalheContato();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
