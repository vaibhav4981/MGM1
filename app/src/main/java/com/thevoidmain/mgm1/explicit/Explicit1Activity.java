package com.thevoidmain.mgm1.explicit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thevoidmain.mgm1.R;

public class Explicit1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit1);


    }

    public void goSecond(View view){

        Intent intent = new Intent(Explicit1Activity.this, Explicit2Activity.class);
        startActivity(intent);

    }

}
