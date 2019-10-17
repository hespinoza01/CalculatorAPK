package com.hespinoza.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Operation operator = new Operation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.operationType);
        Button btnResult = (Button) findViewById(R.id.resultBtn);
        final TextView numberA = (TextView) findViewById(R.id.numberA);
        final TextView numberB = (TextView) findViewById(R.id.numberB);
        final TextView result = (TextView) findViewById(R.id.resultText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operation_type,
                android.R.layout.simple_spinner_dropdown_item
        );

        // Spinner event's
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                operator.setValues(
                        parent.getSelectedItemPosition(),
                        parent.getSelectedItem().toString()
                );
                //String msg = parent.getSelectedItem() + "index: "  + index;
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO - Custom Code
            }
        });

        // btnResult Event
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float a = getNumberValue(numberA);
                float b = getNumberValue(numberB);
                String res = getResult(a, b, operator);

                result.setText(res);
                numberA.setText("");
                numberB.setText("");
            }
        });
    }

    public float getNumberValue(TextView field){
        if(field.getText().length() > 0){
            return Float.parseFloat(field.getText().toString());
        }

        return 0;
    }

    public String getStringValue(float number){
        float result = number - (int) number;

        return (result != 0) ? String.valueOf(number) : String.format("%.0f", number);
    }

    public String getResult(float a, float b, Operation op){
        float result = 0;

        switch (op.getIndex()){
            case 0:
                result = a + b;
                break;

            case 1:
                result = a - b;
                break;

            case 2:
                result = a * b;
                break;

            case 3:
                result = (a > b) ? (a / b) : (b / a);
                break;
        }

        return String.format("%s %s y %s : %s", op.getName(), getStringValue(a), getStringValue(b), getStringValue(result));
    }
}

class Operation{
    private int Index;
    private String Name;

    Operation(){
        this.Index = -1;
        this.Name = "None";
    }

    void setIndex(int value){
        this.Index = value;
    }

    int getIndex(){
        return this.Index;
    }

    void setValues(int index, String name){
        setIndex(index);
        setName(name);
    }

    void setName(String value){
        this.Name = value;
    }

    String getName(){
        return this.Name;
    }
}
