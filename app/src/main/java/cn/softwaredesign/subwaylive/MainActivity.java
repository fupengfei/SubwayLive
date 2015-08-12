package cn.softwaredesign.subwaylive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{

    private Intent intent;
    private Button gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = (Button)findViewById(R.id.gps);
        intent = new Intent(this,PositionActivity.class);
        gps.setOnClickListener(this);
	// branch test

    }

    @Override
    public void onClick(View view) {
        startActivity(intent);
    }
}
