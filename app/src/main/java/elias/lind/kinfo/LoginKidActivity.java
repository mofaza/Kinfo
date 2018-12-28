package elias.lind.kinfo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginKidActivity extends AppCompatActivity {

    private TextView kidsname;
    private TextView foodallergies;
    private TextView animalallergies;

    private TextView message;
    private TextView parentnames;

    private TextView relationship;
    private TextView grownupname;

    private Integer phone;
    private String address;

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    public DatabaseReference mDatabaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kid);

        kidsname = findViewById(R.id.kid_name);
        foodallergies = findViewById(R.id.foodAllergies_SET);
        animalallergies = findViewById(R.id.animalAllergies_SET);

        message = findViewById(R.id.info_text);
        parentnames = findViewById(R.id.parents_names);
        relationship = findViewById(R.id.relationship);
        grownupname = findViewById(R.id.grownup_name);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final String userId = ((LocalVars) this.getApplication()).getUID();
        Log.d("HEJE", userId);

        mDatabaseReference.child("User").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        kidsname.setText(user.getKidsname());
                        foodallergies.setText(user.getFoodallergies());
                        animalallergies.setText(user.getAnimalallergies());
                        message.setText(user.getMessage());
                        parentnames.setText(user.getGrownup());
                        relationship.setText(user.getRelationship());
                        grownupname.setText(user.getGrownup());

                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HEJE", "getUser:onCancelled", databaseError.toException());
                    }
                });

    }

}
