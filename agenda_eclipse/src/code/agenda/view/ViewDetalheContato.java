package code.agenda.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import code.agenda.R;
import code.agenda.dao.DbAdapter;
import code.agenda.entidade.Contato;

/**
 * 
 * @author bruno
 *
 */
public class ViewDetalheContato extends Fragment {
	
	private Context context = null;
	
	private DbAdapter database = null;
	private Contato contato = null;
	private View view = null;
	public ViewDetalheContato() { }
	
	public static final String ARG_ITEM_ID = "item_id";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//habilitar menu de opções
		setHasOptionsMenu(true);
		
		context = getActivity();
		database = new DbAdapter(context);
		database.open();
		
		String idContato = getArguments().getString(ARG_ITEM_ID);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			//carrega o contato selecionado pelo argumento passado pelo fragment
			contato = database.getContato(Integer.parseInt(idContato));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_contato_detail, container, false);
		
		//Mostra o conteudo em modo texto
		if (contato != null) {
			((EditText) view.findViewById(R.id.edtEmailDetalhe)).setText(contato.getEmail());
			((EditText) view.findViewById(R.id.edtTelefoneDetalhe)).setText(contato.getTelefone());
		}
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.delete, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_delete:
			excluirContato(contato);
			return true;
		}
		return false;
	}
	
	private void excluirContato(Contato contato) {
		if(!database.excluir(context, contato)) {
			Toast.makeText(getActivity(), "Erro na exclusão do contato!", Toast.LENGTH_LONG).show();
		}
		getActivity().finish();
	}
}