package com.android.example.environmentracker;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EnvironmentCheckerActivity extends Activity implements OnClickListener, SensorEventListener{

    private Button ambientBtn, lightBtn, pressureBtn, humidityBtn;
    private TextView ambientValue, lightValue, pressureValue, humidityValue;
    private TextView[] valueFields = new TextView[4];
    private final int AMBIENT=0;
    private final int LIGHT=1;
    private final int PRESSURE=2;
    private final int HUMIDITY=3;
    private SensorManager senseManage;
    private Sensor envSense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ambientBtn = (Button)findViewById(R.id.ambient_btn);
        lightBtn = (Button)findViewById(R.id.light_btn);
        pressureBtn = (Button)findViewById(R.id.pressure_btn);
        humidityBtn = (Button)findViewById(R.id.humidity_btn);

        senseManage = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ambientValue =(TextView)

                findViewById(R.id.ambient_text);

        valueFields[AMBIENT]=ambientValue;
        lightValue =(TextView)

                findViewById(R.id.light_text);

        valueFields[LIGHT]=lightValue;
        pressureValue =(TextView)

                findViewById(R.id.pressure_text);

        valueFields[PRESSURE]=pressureValue;
        humidityValue =(TextView)

                findViewById(R.id.humidity_text);

        valueFields[HUMIDITY]=humidityValue;

    }

    public void onClick(View v)
    {

        if (v.getId()==R.id.ambient_btn)
        {
            envSense = senseManage.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            if(envSense==null)
                Toast.makeText(this.getApplicationContext(),
                        "Sorry - your device doesn't have an ambient temperature sensor!",
                        Toast.LENGTH_SHORT).show();
            else
                senseManage.registerListener(this, envSense, SensorManager.SENSOR_DELAY_NORMAL);
//ambient temperature

        }
        else if(v.getId()==R.id.light_btn)
        {
            envSense = senseManage.getDefaultSensor(Sensor.TYPE_LIGHT);
            if(envSense==null)
                Toast.makeText(this.getApplicationContext(),
                        "Sorry - your device doesn't have a light sensor!",
                        Toast.LENGTH_SHORT).show();
            else
                senseManage.registerListener(this, envSense, SensorManager.SENSOR_DELAY_NORMAL);
//light

        }
        else if(v.getId()==R.id.pressure_btn)
        {
            envSense = senseManage.getDefaultSensor(Sensor.TYPE_PRESSURE);
            if(envSense==null)
                Toast.makeText(this.getApplicationContext(),
                        "Sorry - your device doesn't have a pressure sensor!",
                        Toast.LENGTH_SHORT).show();
            else
                senseManage.registerListener(this, envSense, SensorManager.SENSOR_DELAY_NORMAL);
//pressure

        }
        else if(v.getId()==R.id.humidity_btn)
        {
            envSense = senseManage.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            if(envSense==null)
                Toast.makeText(this.getApplicationContext(),
                        "Sorry - your device doesn't have a humidity sensor!",
                        Toast.LENGTH_SHORT).show();
            else
                senseManage.registerListener(this, envSense, SensorManager.SENSOR_DELAY_NORMAL);
//humidity

        }
    }


    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

        String accuracyMsg = "";
        switch(accuracy){
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                accuracyMsg="Sensor has high accuracy";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                accuracyMsg="Sensor has medium accuracy";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                accuracyMsg="Sensor has low accuracy";
                break;
            case SensorManager.SENSOR_STATUS_UNRELIABLE:
                accuracyMsg="Sensor has unreliable accuracy";
                break;
            default:
                break;
        }

    }
    @Override
    public final void onSensorChanged(SensorEvent event) {


        float sensorValue = event.values[0];
        TextView currValue = ambientValue;


        String envInfo = "";
        int currType = event.sensor.getType();


        switch (currType) {
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                envInfo = sensorValue + " degrees Celsius";
                currValue = valueFields[AMBIENT];
                break;
            case Sensor.TYPE_LIGHT:
                envInfo = sensorValue + " SI lux units";
                currValue = valueFields[LIGHT];
                break;
            case Sensor.TYPE_PRESSURE:
                envInfo = sensorValue + " hPa (millibars)";
                currValue = valueFields[PRESSURE];
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                envInfo = sensorValue + " percent humidity";
                currValue = valueFields[HUMIDITY];
                break;
            default:
                break;

        }
        currValue.setText(envInfo);
        envSense = null;
        senseManage.unregisterListener(this);

    }
    @Override
    protected void onPause(){
        super.onPause();
        senseManage.unregisterListener(this);
    }

}
