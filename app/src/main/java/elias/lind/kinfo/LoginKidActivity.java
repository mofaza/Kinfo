package elias.lind.kinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginKidActivity extends AppCompatActivity {

    private TextView kidsname;
    private TextView foodallergies;
    private TextView animalallergies;
    private TextView message;
    private TextView parentnames;
    private TextView relationship1;
    private TextView grownupname1;

    private TextView relationship2;
    private TextView grownupname2;
    private LinearLayout mLinearLayoutParent2;
    private LinearLayout mLinearLayoutMore;
    private FrameLayout touchBox;


    private ImageView kidPicture;
    private ImageView growupPicture1;
    private ImageView growupPicture2;


    private String phone1;
    private String address1;
    private String phone2;
    private String address2;

    public DatabaseReference mDatabaseReference;
    private FirebaseStorage mStorage;
    private String userId;
    private StorageReference mPathRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kid);

        kidPicture = findViewById(R.id.kidPicture);
        growupPicture1 = findViewById(R.id.grownup_picture1);
        growupPicture2 = findViewById(R.id.grownup_picture2);


        kidsname = findViewById(R.id.kid_name);
        foodallergies = findViewById(R.id.foodAllergies_SET);
        animalallergies = findViewById(R.id.animalAllergies_SET);

        message = findViewById(R.id.info_text);
        parentnames = findViewById(R.id.parents_names);

        relationship1 = findViewById(R.id.relationship1);
        grownupname1 = findViewById(R.id.grownup_name1);

        relationship2= findViewById(R.id.relationship2);
        grownupname2 = findViewById(R.id.grownup_name2);
        mLinearLayoutParent2 = findViewById(R.id.linearlayout_parent2);
        touchBox = findViewById(R.id.touchBox);
        touchBox.setVisibility(View.INVISIBLE);

        mLinearLayoutMore = findViewById(R.id.editBox);
        mLinearLayoutMore.setVisibility(View.INVISIBLE);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        mPathRef = mStorage.getReference();
        userId = ((LocalVars) this.getApplication()).getUID();

        mDatabaseReference.child("Users").child("User").child(userId).child("Grownup1").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if (user.getGrownupUsers() == 2){
                            mLinearLayoutParent2.setVisibility(View.VISIBLE);
                            setGrownupPic(2);


                            mDatabaseReference.child("Users").child("User").child(userId).child("Grownup2").addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Get user value
                                            User user = dataSnapshot.getValue(User.class);

                                            if (user.getGrownupUsers() == 2){
                                                phone2 = user.getPhonenumber();
                                                address2 = user.getAddress();
                                                relationship2.setText(user.getRelationship());
                                                grownupname2.setText(user.getGrownup());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.d("HEJE", "getUser:onCancelled", databaseError.toException());
                                        }
                                    });
                        } else {
                            mLinearLayoutParent2.setVisibility(View.INVISIBLE);
                            setGrownupPic(1);

                        }

                        setKidPic();

                        phone1 = user.getPhonenumber();
                        address1 = user.getAddress();
                        kidsname.setText(user.getKidsname());
                        foodallergies.setText(user.getFoodallergies());
                        animalallergies.setText(user.getAnimalallergies());
                        message.setText(user.getMessage());
                        parentnames.setText(user.getGrownup());
                        relationship1.setText(user.getRelationship());
                        grownupname1.setText(user.getGrownup());

                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HEJE", "getUser:onCancelled", databaseError.toException());
                    }
                });

    }

    private void setGrownupPic(Integer numberOfGrownups) {
        if (numberOfGrownups == 2){
            String picnr2 = "grownuppic2.jpg";
            String picnr1 = "grownuppic1.jpg";

            loadGrownupPicture(picnr2, growupPicture2);
            loadGrownupPicture(picnr1, growupPicture1);

        } else {
            String picnr1 = "grownuppic1.jpg";
            loadGrownupPicture(picnr1, growupPicture1);

        }
    }

    private void loadGrownupPicture(String pic, final ImageView growupPicture) {
        StorageReference pathReference2 = mPathRef.child(userId + "/" + pic);
        pathReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

    public void call1(View view) {

        Uri call = Uri.parse("tel:" + phone1);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }

    public void sendText1(View view) {
        Uri sms_uri = Uri.parse("smsto:"+phone1);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        startActivity(smsIntent);
    }

    public void openMaps1(View view) {
        String uri = "geo:0,0?q="+address1;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }

    public void call2(View view) {

        Uri call = Uri.parse("tel:" + phone2);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }

    public void sendText2(View view) {
        Uri sms_uri = Uri.parse("smsto:"+phone2);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        startActivity(smsIntent);
    }

    public void openMaps2(View view) {
        String uri = "geo:0,0?q="+address2;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }

    public void showSettingBox(View view) {
        mLinearLayoutMore.setVisibility(View.VISIBLE);
        touchBox.setVisibility(View.VISIBLE);
    }

    public void hideSettingBox(View view) {
        mLinearLayoutMore.setVisibility(View.INVISIBLE);
        touchBox.setVisibility(View.INVISIBLE);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, AddKidActivity.class);
        intent.putExtra("edit", 2);
        finish();
        startActivity(intent);
    }


    public void signOut(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        finish();
        startActivity(intent);
    }
}
