package infnet.edu.br.assessementfundamentosandroidjava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;

import infnet.edu.br.assessementfundamentosandroidjava.util.InfnetAPI;
import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.adapter.ItemAdapter;
import infnet.edu.br.assessementfundamentosandroidjava.model.InfnetCatalog;
import infnet.edu.br.assessementfundamentosandroidjava.model.Tarefa;
import infnet.edu.br.assessementfundamentosandroidjava.util.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private InfnetAPI infnetAPI;
    private ListView list;
    private ItemAdapter adapter;
    private ArrayList<Tarefa> tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.lista);
        tarefa = new ArrayList<>();

        infnetAPI = ApiClient.getInfnetAPI().create(InfnetAPI.class);
        Call<InfnetCatalog> call = infnetAPI.getTarefas();

        call.enqueue(new Callback<InfnetCatalog>() {
            @Override
            public void onResponse(Call<InfnetCatalog> call, Response<InfnetCatalog> response) {

                if (!response.isSuccessful()) {
                    Log.v("Joao: ", "ocorreu um erro: " + response.code());
                } else {

                    InfnetCatalog catalog = response.body();

                    if (catalog != null) {

                        tarefa = (ArrayList<Tarefa>) catalog.getTarefas();
                        adapter = new ItemAdapter(MainActivity.this, tarefa);
                        list.setAdapter(adapter);

                       // for (Tarefa t : catalog.getTarefas()) {
                           // Log.i("Joao", t.getDescricao());
                        // }

                    } else {
                        Toast.makeText(getApplicationContext(),
                                        "A lista está vazia!",
                                        Toast.LENGTH_SHORT)
                                        .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<InfnetCatalog> call, Throwable t) {
                Log.d("Joao: ", "Erro: " + t.getMessage());
            }
        }); // End call.enqueue
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
    }
}
