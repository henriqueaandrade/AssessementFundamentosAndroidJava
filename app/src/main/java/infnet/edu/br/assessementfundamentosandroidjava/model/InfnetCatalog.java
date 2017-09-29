package infnet.edu.br.assessementfundamentosandroidjava.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class InfnetCatalog {

    @SerializedName("tarefa")
    public List<Tarefa> tarefa;

    public List<Tarefa> getTarefas() {
        return tarefa;
    }

}
