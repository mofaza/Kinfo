package elias.lind.kinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddKidActivity extends AppCompatActivity {

    public String KIDPASSWORD;
    public String NAME;
    public String FOODALLER;
    public String ANIMALALLER;
    public String MESSAGE;


    public DatabaseReference mDatabaseReference;

    private boolean editBool = false;
    private boolean passwordOK = false;

    private TextView mTitle;
    private EditText mAddKidsName;
    private EditText mFoodAllergies;
    private EditText mAnimalAllergies;
    private EditText mMessage;
    private EditText mKidPassword;
    private EditText mKidPasswordCheck;

    private ImageView mAddPhoto;
    private Button mCancle;
    private Button mNext;

    private FirebaseStorage mStorage;
    private String userId;
    private StorageReference mPathRef;


    public static final int GALLERY_REQUEST =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kid);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        mPathRef = mStorage.getReference();
        userId = ((LocalVars) this.getApplication()).getUID();

        mTitle = findViewById(R.id.Add_kid);

        mAddKidsName = findViewById(R.id.editText_addKidsName);
        mFoodAllergies = findViewById(R.id.editText_foodAllergies);
        mAnimalAllergies = findViewById(R.id.editText_animalAllergies);
        mMessage = findViewById(R.id.editText_message);
        mKidPassword = findViewById(R.id.editText_favoriteAnimal);
        mKidPasswordCheck = findViewById(R.id.editText_favoriteAnimalCheck);

        mAddPhoto = findViewById(R.id.add_photo);
        mCancle = findViewById(R.id.buttonCancel);
        mNext = findViewById(R.id.buttonNext);

        Glide.with(getApplicationContext())
                .load(R.drawable.add_photo_button)
                .into(mAddPhoto);

        Intent intent = getIntent();
        Integer edit = intent.getIntExtra("edit", 1);
        if (edit == 2){
            editBool = true;
            populateViewsKid();
        }

    }

    private void populateViewsKid() {
        mDatabaseReference.child("Users").child("User").child(userId).child("Grownup1").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        mAddKidsName.setText(user.getKidsname());
                        mFoodAllergies.setText(user.getFoodallergies());
                        mAnimalAllergies.setText(user.getAnimalallergies());
                        mMessage.setText(user.getMessage());
                        mKidPassword.setText(user.getKidpassword());
                        mKidPasswordCheck.setText(user.getKidpassword());

                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HEJE", "getUser:onCancelled", databaseError.toException());
                    }
                });

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
                        .into(mAddPhoto);
                mAddPhoto.setRotation(90);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


    public void next(View view) {
        FOODALLER = mFoodAllergies.getText().toString();
        ANIMALALLER = mAnimalAllergies.getText().toString();
        MESSAGE = mMessage.getText().toString();
        NAME = mAddKidsName.getText().toString();
        KIDPASSWORD = mKidPassword.getText().toString();

        isPasswordValid(KIDPASSWORD);

        if (passwordOK){
            if (!NAME.isEmpty() && !FOODALLER.isEmpty() && !ANIMALALLER.isEmpty() && !MESSAGE.isEmpty()) {

                ((LocalVars) this.getApplication()).setNAME(NAME);
                ((LocalVars) this.getApplication()).setFOODALLER(FOODALLER);
                ((LocalVars) this.getApplication()).setANIMALALLER(ANIMALALLER);
                ((LocalVars) this.getApplication()).setMESSAGE(MESSAGE);
                ((LocalVars) this.getApplication()).setKIDPASSWORD(KIDPASSWORD);


                //mDatabaseReference.child("User").child(NAME).setValue(user);
                //Toast.makeText(getBaseContext(), "It works", Toast.LENGTH_SHORT).show();

                if (editBool){
                    Intent intent = new Intent(this, AddGrownupActivity.class);
                    intent.putExtra("edit", 2);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(this, AddGrownupActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(getBaseContext(), "Fill everything out", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void cancel(View view) {
        finish();
    }

    public void pic(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        mAddPhoto.setImageBitmap(bitmap);
                        Bitmap bitmaps = ((BitmapDrawable) mAddPhoto.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] datas = baos.toByteArray();
                        ((LocalVars) this.getApplication()).setData(datas);

                        Glide.with(getApplicationContext())
                                .load(bitmap)
                                .apply(RequestOptions.circleCropTransform())
                                .into(mAddPhoto);
                        mAddPhoto.setRotation(90);



                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }

                    break;
            }
    }
    private void isPasswordValid (String password) {
        String confirmPassword = mKidPasswordCheck.getText().toString();
        if (confirmPassword.equals(password) && password.length() > 2){
            passwordOK = true;
        } else{
            Toast.makeText(getBaseContext(), "The password is incorrect", Toast.LENGTH_SHORT).show();
            passwordOK = false;
        }

    }

}
