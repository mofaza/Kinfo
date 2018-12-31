package elias.lind.kinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddGrownupActivity extends AddKidActivity {

    public String GROWNUP;
    public String RELATIONSHIP;
    public String PHONE;
    public String ADDRESS;


    private EditText mGrownup;
    private EditText mRelationship;
    private EditText mPhone;
    private EditText mAddress;

    private ImageView mAddPhoto;
    private Button mAddGrownup;
    private Button mCancle;
    private Button mFinish;

    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private User userdata;
    private FirebaseUser user;

    public static final int GALLERY_REQUEST = 2;
    private byte[] datas;
    private Integer grownupUsers = 0;
    private Boolean addedGrownupUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grownup);

        mAddPhoto = findViewById(R.id.add_photo_grownup);
        mAddGrownup = findViewById(R.id.buttonAddExtraGrownup);
        mCancle = findViewById(R.id.buttonCancel);
        mFinish = findViewById(R.id.buttonFinish);

        mGrownup = findViewById(R.id.editText_addGrownupsName);
        mRelationship = findViewById(R.id.editText_relationToKid);
        mPhone = findViewById(R.id.editText_phoneNumber);
        mAddress = findViewById(R.id.editText_address);

        mAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance();

        Glide.with(getApplicationContext())
                .load(R.drawable.add_photo_button)
                .into(mAddPhoto);

    }

    public void finish(View view) {

        setGrownupData();
        KidUser kiduserdata = new KidUser(
                ((LocalVars) this.getApplication()).getNAME(),
                ((LocalVars) this.getApplication()).getUID(),
                ((LocalVars) this.getApplication()).getKIDPASSWORD());

        if (grownupUsers == 2 && !GROWNUP.isEmpty() && !RELATIONSHIP.isEmpty() && !PHONE.isEmpty() && !ADDRESS.isEmpty()){
            mDatabaseReference.child("Users").child("User").child(user.getUid()).child("Grownup2").setValue(userdata);
            mDatabaseReference.child("Users").child("User").child(user.getUid()).child("Grownup1").child("grownupUsers").setValue(2);


        } else if (grownupUsers == 1){
            mDatabaseReference.child("Users").child("User").child(user.getUid()).child("Grownup1").setValue(userdata);
        }

        if (grownupUsers >0){
            mDatabaseReference.child("Users").child("User").child(((LocalVars) this.getApplication()).getNAME()+((LocalVars) this.getApplication()).getKIDPASSWORD()).setValue(kiduserdata);

            // Create a storage reference from our app
            StorageReference storageRef = mStorage.getReference();

            // Create a reference to "mountains.jpg"
            StorageReference childPicRef = storageRef.child(user.getUid() + "/kidpic.jpg");
            StorageReference grownupPicRef = storageRef.child(user.getUid() + "/grownuppic.jpg");

            UploadTask uploadTaskKid = childPicRef.putBytes(((LocalVars) this.getApplication()).getData());
            UploadTask uploadTaskGrownup = grownupPicRef.putBytes(datas);

            uploadTaskKid.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                }
            });

            uploadTaskGrownup.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                }
            });

            Intent intent = new Intent(this, StartActivity.class);
            Toast.makeText(getBaseContext(), "The profile is created. Log in to review/edit it", Toast.LENGTH_LONG).show();
            finish();
            startActivity(intent);
        }
    }



    private void setGrownupData() {
        user = mAuth.getCurrentUser();
        GROWNUP = mGrownup.getText().toString();
        RELATIONSHIP = mRelationship.getText().toString();
        PHONE = mPhone.getText().toString();
        ADDRESS = mAddress.getText().toString();

        if (!GROWNUP.isEmpty() && !RELATIONSHIP.isEmpty() && !PHONE.isEmpty() && !ADDRESS.isEmpty()){

            grownupUsers += 1;
            userdata = new User(
                    ((LocalVars) this.getApplication()).getNAME(),
                    ((LocalVars) this.getApplication()).getFOODALLER(),
                    ((LocalVars) this.getApplication()).getANIMALALLER(),
                    ((LocalVars) this.getApplication()).getMESSAGE(),
                    ((LocalVars) this.getApplication()).getPASSWORD(),
                    GROWNUP,
                    ((LocalVars) this.getApplication()).getEMAIL(),
                    RELATIONSHIP,
                    PHONE,
                    ADDRESS,
                    ((LocalVars) this.getApplication()).getKIDPASSWORD(),
                    grownupUsers);

            ((LocalVars) this.getApplication()).setUID(user.getUid());

        }
        else{
            Toast.makeText(getBaseContext(), "Fill out all the fields, please", Toast.LENGTH_LONG).show();
        }

}

    public void grownuppic(View view) {
        Intent photoPickerIntent2 = new Intent(Intent.ACTION_PICK);
        photoPickerIntent2.setType("image/*");
        startActivityForResult(photoPickerIntent2, GALLERY_REQUEST);

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
                        datas = baos.toByteArray();

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

    public void addAnotherGrownup(View view) {

        if (grownupUsers <1){
            setGrownupData();
            mDatabaseReference.child("Users").child("User").child(user.getUid()).child("Grownup1").setValue(userdata);

            mGrownup.getText().clear();
            mRelationship.getText().clear();
            mPhone.getText().clear();
            mAddress.getText().clear();
        } else {
            Toast.makeText(getBaseContext(), "You can't add more than 2 grown ups at the moment", Toast.LENGTH_LONG).show();
        }

    }
}
