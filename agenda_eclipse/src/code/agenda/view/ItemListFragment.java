package code.agenda.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import code.agenda.dao.DbAdapter;
import code.agenda.entidade.Contato;

public class ItemListFragment extends ListFragment {

	private DbAdapter database = null;
	private List<Contato> contatos = null;
	
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks mCallbacks = sContatoCallbacks;

	private int mActivatedPosition = ListView.INVALID_POSITION;

	public interface Callbacks {
		public void onItemSelected(String id);
	}

	private static Callbacks sContatoCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	public ItemListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Context context = getActivity();
		database = new DbAdapter(context);
		database.open();
		contatos = database.getContatos();
		
		// TODO: replace with a real list adapter.
		setListAdapter(new ArrayAdapter<Contato>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, contatos));
		database.close();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sContatoCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected(String.valueOf(contatos.get(position).getId()));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}
		mActivatedPosition = position;
	}
	
	@Override
	public void onResume() {
		
		Context context = getActivity();
		database = new DbAdapter(context);
		database.open();
		
		contatos = database.getContatos();
		
		setListAdapter(new ArrayAdapter<Contato>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, contatos));
		database.close();
		
		super.onResume();
	}
}
