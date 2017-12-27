package net.smile.bantaengtour;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvCopy = (TextView) findViewById(R.id.tvCopyright);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Black.ttf");
        tvCopy.setTypeface(typeface);

        Button btnTest = (Button)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AboutActivity.this,TestScrollingActivity.class);
                startActivity(in);
            }
        });

    }
}
