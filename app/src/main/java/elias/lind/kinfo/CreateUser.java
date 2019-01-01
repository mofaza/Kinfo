package elias.lind.kinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUser extends AppCompatActivity {

    public String EMAIL;
    public String EMAILCHECK;
    public String PASSWORD;
    public String PASSWORDCHECK;

    private boolean passwordOK = false;
    private Boolean emailOK = false;


    private EditText mEmail;
    private EditText mEmailCheck;
    private EditText mPassword;
    private EditText mPasswordCheck;

    private Button mCancle;
    private Button mNext;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mEmail = findViewById(R.id.editText_email);
        mEmailCheck = findViewById(R.id.editText_emailcheck);
        mPassword = findViewById(R.id.editText_password);
        mPasswordCheck = findViewById(R.id.editText_password_check);

        mCancle = findViewById(R.id.buttonCancel);
        mNext = findViewById(R.id.buttonNext);

        mAuth = FirebaseAuth.getInstance();


    }

    public void next(View view) {
        EMAIL = mEmail.getText().toString();
        EMAILCHECK = mEmailCheck.getText().toString();
        PASSWORDCHECK = mPasswordCheck.getText().toString();
        PASSWORD = mPassword.getText().toString();

        isPasswordValid(PASSWORD);
        isEmailValid(EMAIL, EMAILCHECK);

        if (passwordOK && emailOK){
            if (!EMAIL.isEmpty() && !EMAILCHECK.isEmpty() && !PASSWORDCHECK.isEmpty() && !PASSWORD.isEmpty()) {

                createFirebaseUser();

                ((LocalVars) this.getApplication()).setPASSWORD(PASSWORD);
                ((LocalVars) this.getApplication()).setEMAIL(EMAIL);

                Intent intent = new Intent(this, AddKidActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getBaseContext(), "Fill everything out", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void isPasswordValid (String password) {
        String confirmPassword = mPasswordCheck.getText().toString();
        if (confirmPassword.equals(password) && password.length() > 5){
            passwordOK = true;
        } else{
            Toast.makeText(getBaseContext(), "The password is incorrect", Toast.LENGTH_SHORT).show();
            passwordOK = false;
        }

    }

    private void isEmailValid (String email, String emailcheck) {
        if (emailOK = email.contains("@") && email.equals(emailcheck)){
            emailOK = true;
        } else{
            Toast.makeText(getBaseContext(), "The email is incorrect", Toast.LENGTH_SHORT).show();
            emailOK = false;
        }
    }

    private void createFirebaseUser(){

        mAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("HEJ", "createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("HEJ", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void cancel(View view) {
        finish();
    }
}
