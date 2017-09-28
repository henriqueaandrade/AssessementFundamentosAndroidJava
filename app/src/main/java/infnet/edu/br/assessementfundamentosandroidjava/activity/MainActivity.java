package infnet.edu.br.assessementfundamentosandroidjava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import infnet.edu.br.assessementfundamentosandroidjava.InfnetAPI;
import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.adapter.RecyclerAdapter;
import infnet.edu.br.assessementfundamentosandroidjava.model.Tarefa;
import infnet.edu.br.assessementfundamentosandroidjava.util.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<Tarefa> tarefas;
    private InfnetAPI infnetAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        Retrofit retrofit = new Retrofit.Builder()
//                 .baseUrl(InfnetService.BASE_URL)
//                 .addConverterFactory(GsonConverterFactory.create())
//                 .build();

        recyclerView = (RecyclerView) findViewById(R.id.lista);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        infnetAPI = ApiClient.getInfnetAPI().create(InfnetAPI.class);
        Call<List<Tarefa>> call = infnetAPI.getTarefas();

        call.enqueue(new Callback<List<Tarefa>>() {
            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {

                if (!response.isSuccessful()) {
                    Log.v("joao: ", "ocorreu um erro: " + response.code());
                } else {

                    tarefas = response.body();
                    adapter = new RecyclerAdapter(tarefas);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                Log.d("joao: ", "Erro: " + t.getMessage());
            }
        });

//        ApiClient apiClient = new ApiClient();
//        apiClient.getInfnetAPI();
//
//        InfnetAPI infnet = apiClient.retrofit.create(InfnetAPI.class);
//        Call<List<Tarefa>> apiInfnet = infnet.getTarefas();
//
//        apiInfnet.enqueue(new Callback<List<Tarefa>>() {
//            @Override
//            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
//                if (!response.isSuccessful()) {
//                    Log.v("joao: ", "ocorreu um erro: " + response.code());
//                } else {
//
//                    List<Tarefa> tarefas =  response.body();
//
//                    for (Tarefa t : tarefas){
//                        Log.i("joao: ", t.getDescricao());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Tarefa>> call, Throwable t) {
//                Log.d("joao: ", "Erro: " + t.getMessage());
//            }
//        });

//        InfnetService service = retrofit.create(InfnetService.class);
//        Call<InfnetService> dadosInfnet = service.listRepos();




//        dadosInfnet.enqueue(new Callback<InfnetService>() {
//            @Override
//            public void onResponse(Call<InfnetService> call, Response<InfnetService> response) {
//                if (!response.isSuccessful()) {
//                    // se retornou com falha
//                    Log.i("Erro", "Ocorreu um outro erro " + response.code());
//                } else {
//                    // se retornou com sucesso
//                    InfnetCatalog catalog = (InfnetCatalog) response.body();
//
//                    for (Ativity ativity : catalog.getAtivities() ) {
//                        Log.i("Sucess", String.format("%s: %s", ativity.getDescription()));
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<InfnetService> call, Throwable t) {
//                Log.i("Erro", "Ocorreu um erro: " + t.getMessage());
//            }
//        });
    }
}
