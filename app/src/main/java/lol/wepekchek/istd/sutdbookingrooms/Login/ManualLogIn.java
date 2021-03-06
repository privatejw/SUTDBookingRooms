package lol.wepekchek.istd.sutdbookingrooms.Login;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
//import android.telephony.TelephonyManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lol.wepekchek.istd.sutdbookingrooms.BaseActivity;
import lol.wepekchek.istd.sutdbookingrooms.R;

/**
 * Created by student on 30/11/2016.
 */
public class ManualLogIn extends AppCompatActivity {
    private DatabaseOperations dbo;
    private Button login;
    private Button resend;
    private TelephonyManager mngr;
    private FirebaseAuth mAuth;
    private static final int REQUEST_READ_PERMISSION = 123;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_log_in);
        if (CheckPermission(ManualLogIn.this, Manifest.permission.READ_PHONE_STATE)) {
            doStuff();
        } else {
            RequestPermission(ManualLogIn.this, Manifest.permission.READ_PHONE_STATE, REQUEST_READ_PERMISSION);
        }
    }

    private void doStuff(){
        dbo = new DatabaseOperations(this, "", null, 1);
        login = (Button) findViewById(R.id.login);
        resend = (Button) findViewById(R.id.resend);
        mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(ManualLogIn.this, "Please wait...", "Logging in...", true);
                String email = dbo.displayStudents() + "@mymail.sutd.edu.sg";
                String password = mngr.getDeviceId().toString();


                (mAuth.signInWithEmailAndPassword(email, password))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    boolean verification = checkVerification();
                                    if (verification == true){
                                        Intent i = new Intent(ManualLogIn.this, BaseActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                } else if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password.")) {
                                    dbo.deleteAll();
                                    for (int i = 0; i < 2; i++) {
                                        Toast.makeText(ManualLogIn.this, "This Student ID has been registered to another phone. Please register a new ID.", Toast.LENGTH_LONG).show();
                                    }
                                    Intent i = new Intent(ManualLogIn.this, Register.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(ManualLogIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = dbo.displayStudents() + "@mymail.sutd.edu.sg";
                String password = mngr.getDeviceId().toString();

                (mAuth.signInWithEmailAndPassword(email, password))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user.isEmailVerified()){
                                        Toast.makeText(ManualLogIn.this, "Account has been verified. You can now log in.", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(ManualLogIn.this, "Email sent.", Toast.LENGTH_LONG).show();
                                        user.sendEmailVerification();
                                    }
                                } else if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password.")) {
                                    mAuth.sendPasswordResetEmail(dbo.displayStudents() + "@mymail.sutd.edu.sg");
                                    Toast.makeText(ManualLogIn.this, "Email sent.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ManualLogIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean checkVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()) {
            Toast.makeText(ManualLogIn.this, "Login successful", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(ManualLogIn.this, "Please verify your Student ID by checking your school email.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean CheckPermission(Context context, String Permission) {
        if (ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void RequestPermission(Activity thisActivity, String Permission, int Code) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Permission)) {
            } else {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Permission},
                        Code);
            }
        }
    }

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case REQUEST_READ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doStuff();
                }
                return;
            }
        }
    }
}
