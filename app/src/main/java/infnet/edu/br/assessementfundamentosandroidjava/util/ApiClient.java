package infnet.edu.br.assessementfundamentosandroidjava.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class ApiClient {
    public static final String BASE_URL = "http://infnet.educacao.ws/";
    public static Retrofit retrofit = null;

    public static Retrofit getInfnetAPI() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .build();
        }
        return retrofit;
    }
}
