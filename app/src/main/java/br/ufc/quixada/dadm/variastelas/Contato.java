package br.ufc.quixada.dadm.variastelas;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contato")
public class Contato {

    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "endereco")
    private String endereco;

    @ColumnInfo(name = "telefone")
    private String telefone;

    @Ignore
    public Contato(String name, String endereco, String telefone) {
        this.name = name;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Contato() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "cid=" + cid +
                ", name='" + name  +
                ", endereco='" + endereco  +
                ", telefone='" + telefone  +
                '}';
    }
}

