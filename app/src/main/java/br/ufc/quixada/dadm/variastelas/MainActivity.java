package br.ufc.quixada.dadm.variastelas;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.dadm.variastelas.transactions.Constants;
import br.ufc.quixada.dadm.variastelas.Contato;

public class MainActivity extends AppCompatActivity {
    int selected;
    List<Contato> listaContatos;
    //ArrayAdapter adapter;
    ExpandableListAdapter adapter;
    //ListView listViewContatos;
    ExpandableListView listViewContatos;

    private ContatoViewModel contatoViewModel;

    private static final String CONTACTS_FILE = "br.ufc.quixada.dadm.variastelas.contacts_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContatos = new ArrayList<>();
        adapter = new ExpandableListAdapter( this, listaContatos );


        listViewContatos = ( ExpandableListView ) findViewById( R.id.expandableListView );
        listViewContatos.setAdapter( adapter );
        listViewContatos.setSelector( android.R.color.holo_blue_light );

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        contatoViewModel = new ViewModelProvider(this,factory).get(ContatoViewModel.class);


//        listViewContatos.setOnItemClickListener( new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
//            {
//                Toast.makeText(MainActivity.this, "" + listaContatos.get( position ).toString(), Toast.LENGTH_SHORT).show();
//                selected = position;
//            }
//        } );

        listViewContatos.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id)
            {
                selected = groupPosition;
                return false;
            }
        });

        contatoViewModel.getAllContatos().observe(this, new Observer<List<Contato>>() {
            @Override
            public void onChanged(@Nullable final List<Contato> contatos) {
                listaContatos.clear();
                for (Contato contato: contatos ) {
                    listaContatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_main_activity, menu );
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected( MenuItem item ) {

        switch(item.getItemId())
        {
            case R.id.add:
                clicarAdicionar();
                break;
            case R.id.edit:
                clicarEditar();
                break;
            case R.id.delete:
                apagarItemLista();
                break;
            case R.id.settings:
                break;
            case R.id.about:
                break;
        }
        return true;
    }

    private void apagarItemLista(){

        if( listaContatos.size() > 0 ){
            contatoViewModel.delete(listaContatos.get(selected));
            adapter.notifyDataSetChanged();
        } else {
            selected = -1;
        }

    }

    public void clicarAdicionar(){
        Intent intent = new Intent( this, ContactActivity.class );
        startActivityForResult( intent, Constants.REQUEST_ADD );
    }

    public void clicarEditar(){

        Intent intent = new Intent( this, ContactActivity.class );

        Contato contato = listaContatos.get( selected );

        intent.putExtra( "id", contato.getCid() );
        intent.putExtra( "nome", contato.getName() );
        intent.putExtra( "telefone", contato.getTelefone() );
        intent.putExtra( "endereco", contato.getEndereco() );

        startActivityForResult( intent, Constants.REQUEST_EDIT );
    }


    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD) {

            String nome = (String) data.getExtras().get("nome");
            String telefone = (String) data.getExtras().get("telefone");
            String endereco = (String) data.getExtras().get("endereco");

            Contato contato = new Contato(nome, telefone, endereco);
            listaContatos.add(contato);
            contatoViewModel.insert(contato);
            //adapter.notifyDataSetChanged();

        } else if (requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD) {

            String nome = (String) data.getExtras().get("nome");
            String telefone = (String) data.getExtras().get("telefone");
            String endereco = (String) data.getExtras().get("endereco");
            int idEditar = (int) data.getExtras().get("id");

            Contato editCt = new Contato(nome,endereco,telefone);
            editCt.setCid(idEditar);
            contatoViewModel.update(editCt);

//            for (Contato contato : listaContatos) {
//
//                if (contato.getCid() == idEditar) {
//                    contato.setName(nome);
//                    contato.setEndereco(endereco);
//                    contato.setTelefone(telefone);
//                }
//            }

            //adapter.notifyDataSetChanged();

        } //Retorno da tela de contatos com um conteudo para ser adicionado
        //Na segunda tela, o usuario clicou no botão ADD
        else if (resultCode == Constants.RESULT_CANCEL) {
            Toast.makeText(this, "Cancelado",
                    Toast.LENGTH_SHORT).show();
        }

    }
}

