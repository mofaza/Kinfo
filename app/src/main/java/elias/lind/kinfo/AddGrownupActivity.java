package elias.lind.kinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class AddGrownupActivity extends AddKidActivity {

    private TextView mTitle;
    private Boolean emailOK = false;

    public String GROWNUP;
    public String EMAIL;
    public String RELATIONSHIP;
    public String PHONE;
    public String ADDRESS;


    private EditText mGrownup;
    private EditText mEmail;
    private EditText mEmailCheck;
    private EditText mRelationship;
    private EditText mPhone;
    private EditText mAddress;

    private Button mAddPhoto;
    private Button mAddGrownup;
    private Button mCancle;
    private Button mFinish;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grownup);

        mAddPhoto = findViewById(R.id.add_photo_grownup);
        mAddGrownup = findViewById(R.id.buttonAddExtraGrownup);
        mCancle = findViewById(R.id.buttonCancel);
        mFinish = findViewById(R.id.buttonFinish);

        mGrownup = findViewById(R.id.editText_addGrownupsName);
        mEmail = findViewById(R.id.editText_email);
        mEmailCheck = findViewById(R.id.editText_emailcheck);
        mRelationship = findViewById(R.id.editText_relationToKid);
        mPhone = findViewById(R.id.editText_phoneNumber);
        mAddress = findViewById(R.id.editText_address);

        mAuth = FirebaseAuth.getInstance();

    }


    private void createFirebaseUser(){

        mAuth.createUserWithEmailAndPassword(EMAIL, ((LocalVars) this.getApplication()).getPASSWORD())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("HEJ", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("HEJ", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void finish(View view){

        //createFirebaseUser();
        GROWNUP = mGrownup.getText().toString();
        EMAIL = mEmail.getText().toString();
        String emailcheck = mEmailCheck.getText().toString();
        RELATIONSHIP = mRelationship.getText().toString();
        PHONE = mPhone.getText().toString();
        ADDRESS = mAddress.getText().toString();

        createFirebaseUser();

        isEmailValid(EMAIL, emailcheck);

        if (emailOK && !GROWNUP.isEmpty() && !EMAIL.isEmpty() && !emailcheck.isEmpty() && !RELATIONSHIP.isEmpty() && !PHONE.isEmpty() && !ADDRESS.isEmpty()){

            GrownupUser grownuser = new GrownupUser(GROWNUP, EMAIL, RELATIONSHIP, PHONE, ADDRESS);
            mDatabaseReference.child("User").child(((LocalVars) this.getApplication()).getNAME()).child(RELATIONSHIP).setValue(grownuser);

            Intent intent = new Intent(this, LoginKidActivity.class);
            startActivity(intent);
        }
    }

    private void isEmailValid (String email, String emailcheck){
        emailOK = email.contains("@") && email.equals(emailcheck);


    }


}
