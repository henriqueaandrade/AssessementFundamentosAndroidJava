package infnet.edu.br.assessementfundamentosandroidjava.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;

import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.config.FirebaseConfiguration;
import infnet.edu.br.assessementfundamentosandroidjava.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_login_email;
    private EditText edit_login_password;
    private Button btn_login;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_login_email = (EditText) findViewById(R.id.edit_login_email);
        edit_login_password = (EditText) findViewById(R.id.edit_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_login_email.getText().toString();
                String password = edit_login_password.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Todos os campos devem ser preenchidos",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    user = new User();
                    user.setEmail(email);
                    user.setPassword(password);

                    login();

                    hideSoftKeyboard(LoginActivity.this);

                }
            }
        }); // End btn_login

    } // End onCreate

    private void login(){
        FirebaseAuth firebaseAuth = FirebaseConfiguration.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),
                                            "Login efetuado com sucesso!",
                                            Toast.LENGTH_SHORT)
                                            .show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();


                        } else {
                            String erroExcessao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                erroExcessao = "E-mail não cadastrado";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExcessao = "E-mail ou senha incorreta.";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), "Erro: " + erroExcessao, Toast.LENGTH_SHORT).show();
                        } // End if isSuccessful
                    }
                }); // End addOnCompleteListener

    } // End login()

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
