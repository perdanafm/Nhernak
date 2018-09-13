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
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.d3ifcool.nhernak.R;

public class LoginActivity extends FirebaseGoogleSignIn {

    //View Declaration
    private EditText userEmailEditTextLog, userPasswordEditTextLog;
    private Button loginButton;
    private TextView registerText;

    private SignInButton googleButton;
    
    private ProgressDialog mProgressDialog;
    private static final String TAG = "LoginTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        isNetworkAvailable();
        //Assign View Variable
        googleButton = (SignInButton) findViewById(R.id.signin_google_layout);
        userEmailEditTextLog = (EditText) findViewById(R.id.userEmailLogin);
        userPasswordEditTextLog = (EditText) findViewById(R.id.userPasswordLogin);
        loginButton = (Button) findViewById(R.id.btn_login);
        registerText = (TextView) findViewById(R.id.registerText);
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton facebookButton = findViewById(R.id.signin_facebook_layout);
        facebookButton.setReadPermissions("email", "public_profile");
        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                if (isNetworkAvailable()) {
                    mProgressDialog.setTitle("Login");
                    mProgressDialog.setMessage("Please wait ...");
                    mProgressDialog.show();
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }else Toast.makeText(LoginActivity.this,"Tidak ada koneksi internet",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

// ...


        //PROGRESS DIALOG CONTEXT
        mProgressDialog = new ProgressDialog(this);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    mProgressDialog.setTitle("Login");
                    mProgressDialog.setMessage("Please wait ...");
                    mProgressDialog.show();
                    signIn();
                }else Toast.makeText(LoginActivity.this,"Tidak ada koneksi internet",Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()){
                    mProgressDialog.setTitle("Login");
                    mProgressDialog.setMessage("Please wait ...");
                    mProgressDialog.show();
                    loginUser();
                }else Toast.makeText(LoginActivity.this,"Tidak ada koneksi internet",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loginUser() {
        String userEmail, userPassword;

        userEmail = userEmailEditTextLog.getText().toString().trim();
        userPassword = userPasswordEditTextLog.getText().toString().trim();

        if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){
            mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        mProgressDialog.dismiss();

                        String currentUserId = mAuth.getCurrentUser().getUid();
                        String deviceToken = FirebaseInstanceId.getInstance().getToken();
                        Log.d("deviceToken",deviceToken);

                        mUserDatabase.child(currentUserId).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent moveToHome = new Intent(LoginActivity.this,MainActivity.class);
                                moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(moveToHome);
                            }
                        });

                    }else {
                        Toast.makeText(LoginActivity.this,"Email atau password salah",Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }else {
            Toast.makeText(LoginActivity.this,"Email dan Password tidak boleh kosong",Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
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
