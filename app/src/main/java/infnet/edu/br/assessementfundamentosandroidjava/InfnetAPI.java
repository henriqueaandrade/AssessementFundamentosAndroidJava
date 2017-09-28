package infnet.edu.br.assessementfundamentosandroidjava;

import java.util.List;

import infnet.edu.br.assessementfundamentosandroidjava.model.Tarefa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public interface InfnetAPI {

    @POST("dadosAtividades.php")
    Call<List<Tarefa>> getTarefas();

}
