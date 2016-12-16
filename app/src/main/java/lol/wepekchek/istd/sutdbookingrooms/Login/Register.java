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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lol.wepekchek.istd.sutdbookingrooms.R;

/**
 * Created by student on 30/11/2016.
 */
public class Register extends AppCompatActivity {
    private DatabaseOperations dbo;
    private Button register;
    private EditText studentID;
    private FirebaseAuth mAuth;
    private static final int REQUEST_READ_PERMISSION = 123;
    private TelephonyManager mngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        if (CheckPermission(Register.this, Manifest.permission.READ_PHONE_STATE)) {
            attemptRegister();
        } else {
            RequestPermission(Register.this, Manifest.permission.READ_PHONE_STATE, REQUEST_READ_PERMISSION);
        }
    }

    private void attemptRegister(){
        dbo = new DatabaseOperations(this, "", null, 1);
        register = (Button) findViewById(R.id.register);
        studentID = (EditText) findViewById(R.id.studentID);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (studentID.getText().toString().length() == 7 && studentID.getText().toString().matches("[0-9]*")) {
                    final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "Please wait...", "Processing...", true);
                    String email = studentID.getText().toString() + "@mymail.sutd.edu.sg";

                    mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String password = mngr.getDeviceId().toString();

                    (mAuth.createUserWithEmailAndPassword(email, password))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mAuth.signInWithEmailAndPassword(studentID.getText().toString() + "@mymail.sutd.edu.sg", mngr.getDeviceId().toString());
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification();
                                        dbo.insertStudent(studentID.getText().toString());
                                        progressDialog.dismiss();
                                        Toast.makeText(Register.this, "Please verify your Student ID by checking your school email.", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Register.this, ManualLogIn.class);
                                        startActivity(i);
                                        finish();
                                    } else if (task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.")) {
                                        progressDialog.dismiss();
                                        dbo.insertStudent(studentID.getText().toString());
                                        registerNewPhone(studentID.getText().toString() + "@mymail.sutd.edu.sg");
                                        Intent i = new Intent(Register.this, ManualLogIn.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Register.this, "Please enter a valid Student ID", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void registerNewPhone(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            for (int i = 0; i < 2; i++) {
                                Toast.makeText(Register.this, "This Student ID has been registered on another phone.\nAn email has been sent to register a new phone for this ID.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
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
                    attemptRegister();
                }
                return;
            }
        }
    }
}
