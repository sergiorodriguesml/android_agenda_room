package br.ufc.quixada.dadm.variastelas;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContatoDao {
    @Insert
    void insert(Contato word);

    @Query("DELETE FROM contato")
    void deleteAll();

    @Delete
    public void delete(Contato contato);

    @Query("SELECT * from contato ORDER BY cid ASC")
    LiveData<List<Contato>> getAllContatos();

    @Update
    public void update(Contato contato);
}