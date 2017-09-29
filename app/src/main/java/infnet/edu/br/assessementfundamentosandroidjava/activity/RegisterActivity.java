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

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.config.FirebaseConfiguration;
import infnet.edu.br.assessementfundamentosandroidjava.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_password;
    private EditText edit_confirm_password;
    private EditText edit_cpf;
    private Button btn_register;
    private User user;

    private FirebaseAuth firebaseAuth = FirebaseConfiguration.getFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_name              = (EditText) findViewById(R.id.edit_name);
        edit_email             = (EditText) findViewById(R.id.edit_email);
        edit_password          = (EditText) findViewById(R.id.edit_password);
        edit_confirm_password  = (EditText) findViewById(R.id.edit_confirm_password);
        edit_cpf               = (EditText) findViewById(R.id.edit_cpf);
        btn_register           = (Button) findViewById(R.id.btn_register);

        SimpleMaskFormatter simpleMaskCpf    = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskTextWatcher      = new MaskTextWatcher(edit_cpf, simpleMaskCpf);
        edit_cpf.addTextChangedListener(maskTextWatcher);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edit_name.getText().toString();
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();
                String confirm_password = edit_confirm_password.getText().toString();
                String cpf = edit_cpf.getText().toString();

                // check is email is valid
                if (!isEmailValid(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Por favor insira um email válido",
                            Toast.LENGTH_LONG)
                            .show();
                } else {

                    // check if pass and confirm pass matches
                    if (!isPassMaches(password, confirm_password)) {
                        Toast.makeText(getApplicationContext(),
                                "Por favor insira senhas que se coicidem",
                                Toast.LENGTH_LONG)
                                .show();
                    } else {

                        String cpfWithoutFormater = cpf.replace(".", "");
                        cpfWithoutFormater = cpfWithoutFormater.replace("-", "");

                        if (name.isEmpty() || cpfWithoutFormater.isEmpty()) {
                            Toast.makeText(getApplicationContext(),
                                    "Todos os campos devem ser preenchidos",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {

                            user = new User();
                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setCPF(cpfWithoutFormater);

                            hideSoftKeyboard(RegisterActivity.this);

                            registerUser();

                            firebaseAuth.signOut();

                        } // End
                    }
                } // End else isEmailValid
            } // End onClick
        }); // End btn_register

    } // End onCreate

    private void registerUser() {

        firebaseAuth = FirebaseConfiguration.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            user.setId(task.getResult().getUser().getUid());

                            DatabaseReference databaseReference = FirebaseConfiguration
                                    .getFirebase()
                                    .child("users");

                            databaseReference.child(user.getId())
                                    .child("Email")
                                    .setValue(user.getEmail());

                            databaseReference.child(user.getId())
                                    .child("Name")
                                    .setValue(user.getName());

                            databaseReference.child(user.getId())
                                    .child("Password")
                                    .setValue(user.getPassword());

                            databaseReference.child(user.getId())
                                    .child("CPF")
                                    .setValue(user.getCPF());

                            Toast.makeText(getApplicationContext(),
                                    "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_LONG)
                                    .show();


                            goToLoginActivity(RegisterActivity.this);
                            finish();

                        } // End if isSuccessfull
                        else {
                            String erroExecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroExecao = "Digite uma senha mais forte de pelo menos 6 digitos, contendo mais caracteres com letras e números.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExecao = "E-mail ou senha inválidos.";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erroExecao = "E-mail já está cadastrado!";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), "Erro: " + erroExecao, Toast.LENGTH_SHORT).show();

                        } // End else if isSuccessfull
                    }
                }); // End addOnCompleteListener

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPassMaches(String pass, String confirm_pass) {
        if (pass.equals(confirm_pass)) {
            return true;
        } else {
            return false;
        }
    }

    public void goToLoginActivity(Activity activity) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
