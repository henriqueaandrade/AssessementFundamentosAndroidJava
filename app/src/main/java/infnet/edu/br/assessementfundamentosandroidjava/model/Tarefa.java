package infnet.edu.br.assessementfundamentosandroidjava.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class Tarefa {
    @SerializedName("id")
    private int id;

    @SerializedName("descricao")
    private String descricao;

//    public Tarefa(int id, String descricao) {
//        this.id = id;
//        this.descricao = descricao;
//    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
