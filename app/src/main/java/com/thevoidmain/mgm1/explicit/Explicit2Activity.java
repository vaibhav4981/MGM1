package com.thevoidmain.mgm1.explicit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.thevoidmain.mgm1.R;

public class Explicit2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit2);
    }

    public void goFirst(View view){
        Intent intent = new Intent(Explicit2Activity.this, Explicit1Activity.class);
        startActivity(intent);
    }
}
