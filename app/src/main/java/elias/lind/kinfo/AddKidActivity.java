package elias.lind.kinfo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class AddKidActivity extends AppCompatActivity {

    private TextView mTitle;
    private EditText mAddKidsName;
    private EditText mFoodAllergies;
    private EditText mAnimalAllergies;
    private EditText mMessage;
    private EditText mPassword;
    private EditText mPasswordCheck;

    private ImageButton mAddPhoto;
    private Button mCancle;
    private Button mNext;
    private CircularImageView mProfilePic;

    public static final int GALLERY_REQUEST =1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kid);

        mTitle = findViewById(R.id.Add_kid);

        mAddKidsName = findViewById(R.id.editText_addKidsName);
        mFoodAllergies = mAddKidsName = findViewById(R.id.editText_foodAllergies);
        mAnimalAllergies = findViewById(R.id.editText_animalAllergies);
        mMessage = findViewById(R.id.editText_message);
        mPassword = findViewById(R.id.editText_favoriteAnimal);
        mPasswordCheck = findViewById(R.id.editText_favoriteAnimalCheck);


        mAddPhoto = findViewById(R.id.add_photo);
        mCancle = findViewById(R.id.buttonCancel);
        mNext = findViewById(R.id.buttonNext);
        mProfilePic = findViewById(R.id.profilepic);

        mAuth = FirebaseAuth.getInstance();
    }

    public void next(View view) {
        Intent intent = new Intent(this, AddGrownupActivity.class);
        startActivity(intent);
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
                        mProfilePic.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    private boolean isEmailValid (String email){
        return email.contains("@");
    }

    private boolean isPasswordValid (String password) {
        String confirmPassword = mPasswordCheck.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }

    private void createFirebaseUser(){

        String name = mAddKidsName.getText().toString();
        String foodAller = mFoodAllergies.getText().toString();
        String animalAller = mAnimalAllergies.getText().toString();
        String message = mMessage.getText().toString();
        String password = mPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(name, password);



    }
}
