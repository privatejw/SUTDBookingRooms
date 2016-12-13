package lol.wepekchek.istd.sutdbookingrooms.Login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lol.wepekchek.istd.sutdbookingrooms.BaseActivity;
import lol.wepekchek.istd.sutdbookingrooms.R;

public class LoginActivity extends AppCompatActivity {
    private DatabaseOperations dbo;
    private TelephonyManager mngr;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbo = new DatabaseOperations(this, "", null, 1);
        mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Checking for user...", true);
        if (dbo.displayStudents().equals("")) {
            Intent i = new Intent(LoginActivity.this, Register.class);
            startActivity(i);
            finish();
            progressDialog.dismiss();
        } else {
            String email = dbo.displayStudents() + "@mymail.sutd.edu.sg";
            String password = mngr.getDeviceId().toString();
            //String password = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            (mAuth.signInWithEmailAndPassword(email, password))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                boolean verification = checkVerification();
                                if (verification == false) {
                                    Intent i = new Intent(LoginActivity.this, ManualLogIn.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent i = new Intent(LoginActivity.this, BaseActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            } else if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password.")) {
                                dbo.deleteAll();
                                Intent i = new Intent(LoginActivity.this, Register.class);
                                startActivity(i);
                                finish();
                            } else if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.")) {
                                dbo.deleteAll();
                                Intent i = new Intent(LoginActivity.this, Register.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean checkVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()) {
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(LoginActivity.this, "Please verify your Student ID by checking your school email.", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
