package elias.lind.kinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchKinfoActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;

    public DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_kinfo);

        userName = findViewById(R.id.editText_kidsname);
        passWord = findViewById(R.id.editText_password);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void login(View view) {

        final String username = userName.getText().toString();
        final String password = passWord.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {

                mDatabaseReference.child("Users").child("User").child(username + password).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user value
                                KidUser kiduser = dataSnapshot.getValue(KidUser.class);
                                try {
                                    kiduser.getKidsname();

                                    if (kiduser.getKidsname().equals(username) && kiduser.getKidpassword().equals(password)) {

                                        ((LocalVars) getApplication()).setUID(kiduser.getUid());

                                        Intent intent = new Intent(getBaseContext(), KinfoActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                } catch (Exception nullPointer){
                                    Log.d("SeachException", "No kid with that name or password - problem: " + nullPointer);
                                    Toast.makeText(getBaseContext(), "Wrong kid name or favorite animal", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("HEJE", "getUser:onCancelled", databaseError.toException());
                                Toast.makeText(getApplicationContext(), "Wrong kids name or password", Toast.LENGTH_LONG).show();

                            }
                        });

        }
        else {
            Toast.makeText(getBaseContext(), "Missing kidsname or password", Toast.LENGTH_LONG).show();
        }
    }


}
