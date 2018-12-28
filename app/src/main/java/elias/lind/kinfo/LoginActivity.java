package elias.lind.kinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.editText_kidsname);
        passWord = findViewById(R.id.editText_password);

        mAuth = FirebaseAuth.getInstance();


    }

    public void login(View view) {

        String username = userName.getText().toString();
        String password = passWord.getText().toString();

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("HEJ", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("HEJ", user.getUid());
                    ((LocalVars) getApplication()).setUID(user.getUid());

                    Intent intent = new Intent(getBaseContext(), LoginKidActivity.class);
                    finish();
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("HEJ", "createUserWithEmail:failure", task.getException());
                }
            }
        });


    }
}
