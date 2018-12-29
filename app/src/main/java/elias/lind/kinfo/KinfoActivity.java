package elias.lind.kinfo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Locale;

public class KinfoActivity extends AppCompatActivity {

    private TextView kidsname;
    private TextView foodallergies;
    private TextView animalallergies;
    private TextView message;
    private TextView parentnames;
    private TextView relationship;
    private TextView grownupname;

    private ImageView kidPicture;
    private ImageView growupPicture;

    private String phone;
    private String address;

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    public DatabaseReference mDatabaseReference;
    private FirebaseStorage mStorage;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.KinfoTheme);
        this.setContentView(R.layout.activity_kinfo);

        kidPicture = findViewById(R.id.kidPicture);

        growupPicture = findViewById(R.id.gp);

        kidsname = findViewById(R.id.kid_name);
        foodallergies = findViewById(R.id.foodAllergies_SET);
        animalallergies = findViewById(R.id.animalAllergies_SET);

        message = findViewById(R.id.info_text);
        parentnames = findViewById(R.id.parents_names);
        relationship = findViewById(R.id.relationship);
        grownupname = findViewById(R.id.grownup_name);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        userId = ((LocalVars) this.getApplication()).getUID();

        setKidPic();
        setGrownupPic();


        mDatabaseReference.child("User").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        phone = user.getPhonenumber();
                        address = user.getAddress();
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

    private void setGrownupPic() {
        StorageReference pathRef = mStorage.getReference();
        StorageReference pathReference = pathRef.child(userId + "/grownuppic.jpg");
        Log.d("DATASTUFF", pathReference.toString());
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                Glide.with(getApplicationContext())
                        .load(imageURL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(growupPicture);
                growupPicture.setRotation(90);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


    private void setKidPic() {
        mStorage = FirebaseStorage.getInstance();

        StorageReference pathRefKid = mStorage.getReference();
        StorageReference pathReferenceKid = pathRefKid.child(userId + "/kidpic.jpg");
        Log.d("DATASTUFF", pathReferenceKid.toString());
        pathReferenceKid.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                Glide.with(getApplicationContext())
                        .load(imageURL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(kidPicture);
                kidPicture.setRotation(90);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void call(View view) {

        Uri call = Uri.parse("tel:" + phone);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }

    public void sendText(View view) {
        Uri sms_uri = Uri.parse("smsto:"+phone);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        startActivity(smsIntent);
    }

    public void openMaps(View view) {
        String uri = String.format(Locale.ENGLISH, "geo:0,0?q="+address);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }


}
