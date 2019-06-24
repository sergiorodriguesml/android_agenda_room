package br.ufc.quixada.dadm.variastelas;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Contato.class},version = 1, exportSchema = false)
public abstract class ContatoDatabase extends RoomDatabase {
    public abstract ContatoDao contatoDao();

    private static  ContatoDatabase INSTANCE;

    public  static  ContatoDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ContatoDatabase.class,"contato-database").build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }


}


