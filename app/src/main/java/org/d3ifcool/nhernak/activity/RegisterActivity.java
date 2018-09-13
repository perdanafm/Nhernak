package org.d3ifcool.nhernak.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.d3ifcool.nhernak.R;

public class RegisterActivity extends FirebaseGoogleSignIn {

    //DECLARE VIEW
    private EditText userEmailEditTextReg, userPasswordEditTextReg;
    private Button registerButton;
    private SignInButton googleButton;
    private TextView loginText;

    //PROGRESS DIALOG
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        isNetworkAvailable();
        //Configure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //ASSIGN ID VIEW
        googleButton = (SignInButton) findViewById(R.id.signin_google);
        TextView textView = (TextView) googleButton.getChildAt(0);
        textView.setText(R.string.register_with_google);
        userEmailEditTextReg = (EditText) findViewById(R.id.userEmailRegister);
        userPasswordEditTextReg = (EditText) findViewById(R.id.userPasswordRegister);
        registerButton = (Button) findViewById(R.id.btn_register);
        loginText = (TextView) findViewById(R.id.loginText);

        mProgressDialog = new ProgressDialog(this);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity( new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    mProgressDialog.setTitle("Register");
                    mProgressDialog.setMessage("Please wait ...");
                    mProgressDialog.show();
                    createUser();
                }else Toast.makeText(RegisterActivity.this,"Tidak ada koneksi internet",Toast.LENGTH_SHORT).show();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    mProgressDialog.setTitle("Login with google");
                    mProgressDialog.setMessage("Please wait ...");
                    mProgressDialog.show();
                    signIn();
                }else Toast.makeText(RegisterActivity.this,"Tidak ada koneksi internet",Toast.LENGTH_SHORT).show();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton facebookButton = findViewById(R.id.signin_facebook);
        facebookButton.setReadPermissions("email", "public_profile");
        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookRegister", "facebook:onSuccess:" + loginResult);
                if (isNetworkAvailable()) {
                    mProgressDialog.setTitle("Login");
                    mProgressDialog.setMessage("Please wait ...");
                    mProgressDialog.show();
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }else Toast.makeText(RegisterActivity.this,"Tidak ada koneksi internet",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.d("FacebookRegister", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookRegister", "facebook:onError", error);
                // ...
            }
        });
    }

    private void createUser() {
        String emailUser,passwordUser;
        emailUser = userEmailEditTextReg.getText().toString().trim();
        passwordUser = userPasswordEditTextReg.getText().toString().trim();
        Log.d("TAG",passwordUser);
        Log.d("TAG",emailUser);

        if (!TextUtils.isEmpty(emailUser) && !TextUtils.isEmpty(passwordUser)){
            if (passwordUser.length()<8){
                userPasswordEditTextReg.setError("Password harus lebih dari 8 karakter");
            }else{
                mProgressDialog.setTitle("Creating Account");
                mProgressDialog.setMessage("Please wait ... ");
                mProgressDialog.show();
                mAuth.createUserWithEmailAndPassword(emailUser,passwordUser)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,
                                            "Akun telah berhasil diregistrasi",Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                    Intent moveToHome = new Intent(RegisterActivity.this,MainActivity.class);
                                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(moveToHome);

                                }else {
                                    task.getException().printStackTrace();
                                    Toast.makeText(RegisterActivity.this,
                                            "Failed to registered",Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                }
                            }
                        });
            }
        }else {
            if (TextUtils.isEmpty(emailUser)){
                userEmailEditTextReg.setError("Field tidak boleh kosong");
            }else if (TextUtils.isEmpty(passwordUser)){
                userPasswordEditTextReg.setError("Field tidak boleh kosong");
            }
        }
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
