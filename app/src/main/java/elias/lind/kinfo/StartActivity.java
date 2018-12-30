package elias.lind.kinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    private void signup() {
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }


    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void search(View view) {
        Intent intent = new Intent(this, SearchKinfoActivity.class);
        startActivity(intent);
    }
}
