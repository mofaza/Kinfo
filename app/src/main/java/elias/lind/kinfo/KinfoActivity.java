package elias.lind.kinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class KinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.KinfoTheme);
        this.setContentView(R.layout.activity_kinfo);
    }
}