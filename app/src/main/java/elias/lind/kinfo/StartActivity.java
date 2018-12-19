package elias.lind.kinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    private Button mSearch;
    private Button mLogIn;
    private Button mSignUp;
    private ImageView mKinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mKinfo = findViewById(R.id.kinfoLogo);
        mLogIn = findViewById(R.id.buttonLogIn);
        mSignUp = findViewById(R.id.buttonSignUp);
        mSearch = findViewById(R.id.buttonSearchKinfo);

    }
}
