package elias.lind.kinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddGrownupActivity extends AppCompatActivity {

    private TextView mTitle;


    private Button mAddPhoto;
    private Button mAddGrownup;
    private Button mCancle;
    private Button mFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grownup);

        mAddPhoto = findViewById(R.id.add_photo);
        mAddGrownup = findViewById(R.id.buttonAddExtraGrownup);
        mCancle = findViewById(R.id.buttonCancel);
        mFinish = findViewById(R.id.buttonFinish);
    }


}
