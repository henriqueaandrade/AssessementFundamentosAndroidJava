package infnet.edu.br.assessementfundamentosandroidjava.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.config.FirebaseConfiguration;

public class InitialActivity extends AppCompatActivity {

    private Button btn_initial_login;
    private Button btn_initial_register;
    private LoginManager btn_facebook;
    private CallbackManager callbackManager;

    // Firebase
    private AuthCredential authCredential;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        btn_initial_login = (Button) findViewById(R.id.btn_initial_login);
        btn_initial_register = (Button) findViewById(R.id.btn_initial_register);
       // btn_facebook = (LoginManager) findViewById(R.id.btn_facebook_login);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        //acessFacebookLoginData(loginResult);
                        Toast.makeText(getApplicationContext(),
                                "Login efeituado com sucesso!",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),
                                        exception.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();

                    }
                }); // End LoginManager

        btn_initial_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }); // End btn_initial_login

        btn_initial_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }); // End btn_initial_register

    } // End onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void sendLoginFacebookData (View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    private void acessFacebookLoginData(AccessToken accessToken) {
        if (accessToken != null) {
            authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
            firebaseAuth = FirebaseConfiguration.getFirebaseAuth();
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(
                    InitialActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                                "Login efeituado com sucesso!",
                                                Toast.LENGTH_LONG).show();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao fazer login",
                                        Toast.LENGTH_LONG).show();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            }
                        }
                    });

        } 
    }
}
