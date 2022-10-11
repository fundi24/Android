package com.example.projetaout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChooseType extends AppCompatActivity {

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioGroup radioGroup = findViewById(R.id.RadioGroup);
            int Selected = radioGroup.getCheckedRadioButtonId();
            if(Selected > 0)
            {
                String radioButt = ((RadioButton)findViewById(Selected)).getText().toString();
                Intent intent = new Intent();
                intent.putExtra("Type", radioButt);
                setResult(RESULT_OK,intent);
                finish();
            }
            else
            {
                Toast.makeText(ChooseType.this,getResources().getString(R.string.ErrorChoose),Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        Button But_Validation = findViewById(R.id.But_Validation);
        But_Validation.setOnClickListener(listener);
    }
}