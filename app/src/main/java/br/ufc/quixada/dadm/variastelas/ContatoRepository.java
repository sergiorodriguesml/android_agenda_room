package br.ufc.quixada.dadm.variastelas;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContatoRepository {
    private ContatoDao mContatoDao;
    private LiveData<List<Contato>> mAllContatos;

    public ContatoRepository(Application application){
        ContatoDatabase db = ContatoDatabase.getAppDatabase(application);
        mContatoDao = db.contatoDao();
        mAllContatos = mContatoDao.getAllContatos();
    }

    LiveData<List<Contato>> getAllContatos() {
        return mAllContatos;
    }


    public void insert (Contato contato) {
        new insertAsyncTask(mContatoDao).execute(contato);
    }
    public void delete (Contato contato) {
        new deleteAsyncTask(mContatoDao).execute(contato);
    }
    public void update (Contato contato) {
        new updateAsyncTask(mContatoDao).execute(contato);
    }

    private static class insertAsyncTask extends AsyncTask<Contato, Void, Void> {

        private ContatoDao mAsyncTaskDao;

        insertAsyncTask(ContatoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contato... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<Contato, Void, Void> {

        private ContatoDao mAsyncTaskDao;

        deleteAsyncTask(ContatoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contato... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }

    }

    private static class updateAsyncTask extends AsyncTask<Contato, Void, Void> {

        private ContatoDao mAsyncTaskDao;

        updateAsyncTask(ContatoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contato... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }

    }
}
