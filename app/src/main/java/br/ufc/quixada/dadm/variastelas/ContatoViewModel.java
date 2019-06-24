package br.ufc.quixada.dadm.variastelas;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ContatoViewModel extends AndroidViewModel {
    private ContatoRepository mRepository;

    private LiveData<List<Contato>> mAllContatos;

    public ContatoViewModel (Application application) {
        super(application);
        mRepository = new ContatoRepository(application);
        mAllContatos = mRepository.getAllContatos();
    }

    LiveData<List<Contato>> getAllContatos() { return mAllContatos; }

    public void insert(Contato contato) { mRepository.insert(contato); }
    public void delete(Contato contato){ mRepository.delete(contato);}
    public void update(Contato contato){mRepository.update(contato);}


}
