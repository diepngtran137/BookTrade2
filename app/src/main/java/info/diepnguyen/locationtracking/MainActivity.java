package info.diepnguyen.locationtracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    private final static int LOGIN_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.startBtn);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setProviders(
                                AuthUI.EMAIL_PROVIDER,
                                AuthUI.GOOGLE_PROVIDER
                        ).build(),LOGIN_PERMISSION
                );
            }
        });

        Log.i("Main Activity","on Create is running");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_PERMISSION){
            startNewActivity(resultCode,data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();

        }
        else {
            Toast.makeText(this,"Login failed",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Main Activity","on Resume is running");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Main Activity","on Pause is running");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Main Activity","on Start is running");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Main Activity","on Stop is running");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Main Activity","on Restart is running");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Main Activity","on Destroy is running");
    }
}
