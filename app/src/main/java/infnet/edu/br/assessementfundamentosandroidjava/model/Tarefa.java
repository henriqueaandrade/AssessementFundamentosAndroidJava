package infnet.edu.br.assessementfundamentosandroidjava.model;

import android.media.Image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class Tarefa {
    @SerializedName("id")
    private String id;

    @SerializedName("descricao")
    private String descricao;

//    public Tarefa() {
//        this.id = id;
//        this.descricao = descricao;
//    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

}
