package elias.lind.kinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class AddKidActivity extends AppCompatActivity {

    public String PASSWORD;
    public String NAME;
    public String FOODALLER;
    public String ANIMALALLER;
    public String MESSAGE;

    public DatabaseReference mDatabaseReference;

    private boolean passwordOK = false;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kid);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mTitle = findViewById(R.id.Add_kid);

        mAddKidsName = findViewById(R.id.editText_addKidsName);
        mFoodAllergies = findViewById(R.id.editText_foodAllergies);
        mAnimalAllergies = findViewById(R.id.editText_animalAllergies);
        mMessage = findViewById(R.id.editText_message);
        mPassword = findViewById(R.id.editText_favoriteAnimal);
        mPasswordCheck = findViewById(R.id.editText_favoriteAnimalCheck);


        mAddPhoto = findViewById(R.id.add_photo);
        mCancle = findViewById(R.id.buttonCancel);
        mNext = findViewById(R.id.buttonNext);
        mProfilePic = findViewById(R.id.profilepic);

    }

    public void next(View view) {
        FOODALLER = mFoodAllergies.getText().toString();
        ANIMALALLER = mAnimalAllergies.getText().toString();
        MESSAGE = mMessage.getText().toString();
        NAME = mAddKidsName.getText().toString();
        PASSWORD = mPassword.getText().toString();

        isPasswordValid(PASSWORD);

        if (passwordOK){
            if (!NAME.isEmpty() && !FOODALLER.isEmpty() && !ANIMALALLER.isEmpty() && !MESSAGE.isEmpty()) {

                KidUser user = new KidUser(NAME, FOODALLER, ANIMALALLER, MESSAGE, PASSWORD);

                ((LocalVars) this.getApplication()).setNAME(NAME);
                ((LocalVars) this.getApplication()).setFOODALLER(FOODALLER);
                ((LocalVars) this.getApplication()).setANIMALALLER(ANIMALALLER);
                ((LocalVars) this.getApplication()).setMESSAGE(MESSAGE);
                ((LocalVars) this.getApplication()).setPASSWORD(PASSWORD);


                mDatabaseReference.child("User").child(NAME).setValue(user);
                Toast.makeText(getBaseContext(), "It works", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, AddGrownupActivity.class);
                startActivity(intent);
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
                        mProfilePic.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
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

}
