package org.techtown.androidmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class numberpicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numberpicker);
        //기본적으로 0~5까지 넘버피커를 만들고

//        //5까지 올리면 더이상 올라가지 않게 하기
//
//        NumberPicker picker1 = (NumberPicker)findViewById(R.id.picker1);
//
//        picker1.setMinValue(0);
//
//        picker1.setMaxValue(5);
//
//        picker1.setWrapSelectorWheel(false);
//
//
//
//        //0~20까지만들고 길게 누르면 더 빨리 값이 바뀌게 하기
//
//        NumberPicker picker2 = (NumberPicker)findViewById(R.id.picker2);
//
//        picker2.setMinValue(0);
//
//        picker2.setMaxValue(20);
//
//        picker2.setOnLongPressUpdateInterval(100);
//
//
//
//        //0~6으로 하고 월~일요일로 하기
//
//        NumberPicker picker3 = (NumberPicker)findViewById(R.id.picker3);
//
//        picker3.setMinValue(0);
//
//        picker3.setMaxValue(6);
//
//        picker3.setDisplayedValues(new String[]{
//
//                "일요일", "월요일", "화요일", "수요일", "목요일", "금요일",
//
//                "토요일"});



        //0~3으로 하고 값별로 텍스트로 바꿔 출력하기

        NumberPicker.Formatter mFormatter = new NumberPicker.Formatter() {



            @Override

            public String format(int value) {

                // TODO Auto-generated method stub

                switch(value){

                    case 0:

                        return "zero";

                    case 1:

                        return "one";

                    case 2:

                        return "two";



                }



                return null;

            }

        };

//        //0~250으로 하고 값이 바뀔때마다 토스트 메시지 띄우기
//        NumberPicker picker2 = (NumberPicker)findViewById(R.id.picker2);
//        picker2.setMinValue(1);
//        picker2.setMaxValue(250);
//        picker2.setFormatter(mFormatter);
//
//
//        //0~250으로 하고 값이 바뀔때마다 토스트 메시지 띄우기
//        NumberPicker picker3 = (NumberPicker)findViewById(R.id.picker3);
//        picker3.setMinValue(1);
//        picker3.setMaxValue(250);
//        picker3.setFormatter(mFormatter);


        //100~200으로 하고 값이 바뀔때마다 토스트 메시지 띄우기
        NumberPicker picker4 = (NumberPicker)findViewById(R.id.picker4);
        picker4.setMinValue(1);
        picker4.setMaxValue(250);
        picker4.setFormatter(mFormatter);


        //0~2로하고 텍스트로 바꿔 출력하기
        NumberPicker picker5 = (NumberPicker)findViewById(R.id.picker5);
        picker5.setMinValue(0);
        picker5.setMaxValue(9);
        picker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub
                Toast.makeText(numberpicker.this, "Value : " + newVal, Toast.LENGTH_SHORT).show();

            }

        });


    }


}
