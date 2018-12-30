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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchKinfoActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;

    private FirebaseAuth mAuth;
    public DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_kinfo);

        userName = findViewById(R.id.editText_kidsname);
        passWord = findViewById(R.id.editText_password);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User");


    }

    public void login(View view) {

        String username = userName.getText().toString();
        String password = passWord.getText().toString();

//to store values in your credentials node just use this code

        Log.d("HEJE", mDatabaseReference.child("Kidsname").toString());
        //ref.child("usernames").child(username).child("password").setValue(password);
    }


}
