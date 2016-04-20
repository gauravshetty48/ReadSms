package abhi.kurla.bloodpressure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView systolicView, diastolicView, heartRateView;
    String systolic, diastolic, heartRate = "000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            systolic = extras.getString("systolic");
            diastolic = extras.getString("diastolic");
            heartRate = extras.getString("heart_rate");
        } else {
            systolic = (String) savedInstanceState.getSerializable("systolic");
            diastolic = (String) savedInstanceState.getSerializable("diastolic");
            heartRate = (String) savedInstanceState.getSerializable("heart_rate");
        }
        systolicView = (TextView) findViewById(R.id.systolic_view);
        diastolicView = (TextView) findViewById(R.id.diastolic_view);
        heartRateView = (TextView) findViewById(R.id.heart_rate_view);

        systolicView.setText(systolic);
        diastolicView.setText(diastolic);
        heartRateView.setText(heartRate);

    }
}