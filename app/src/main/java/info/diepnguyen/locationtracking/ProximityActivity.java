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

public class ProximityActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mProximity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        Log.i("Proximity Actitvity","on Create");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux= event.values[0];

        Float luxFloat= new Float(lux);
        TextView textView = (TextView) findViewById(R.id.resultProximity);
        textView.setText(luxFloat.toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mProximity,SensorManager.SENSOR_DELAY_NORMAL);
        Log.i("Proximity Actitvity","on Resume");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Proximity Actitvity","on Start");
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.i("Proximity Actitvity","on Pause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Proximity Actitvity","on Restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Proximity Actitvity","on Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Proximity Actitvity","on Destroy");
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
        Intent intent = new Intent(ProximityActivity.this,MenuActivity.class);
        startActivity(intent);
    }
}
