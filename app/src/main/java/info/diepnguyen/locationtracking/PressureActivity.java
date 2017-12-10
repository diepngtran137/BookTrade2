package info.diepnguyen.locationtracking;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class PressureActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.resultPressure);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Log.i("Pressure Activity","on Create is running");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux= event.values[0];

        Float luxFloat= new Float(lux);
        textView.setText(luxFloat.toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);
        Log.i("Pressure Activity","on Resume is running");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Pressure Activity","on Pause is running");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Pressure Activity","on Start is running");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Pressure Activity","on Stop is running");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Pressure Activity","on Restart is running");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Pressure Activity","on Destroy is running");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                returnMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnMenu() {
        Intent intent = new Intent(PressureActivity.this,MenuActivity.class);
        startActivity(intent);
    }

}
