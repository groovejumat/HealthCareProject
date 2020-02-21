package org.techtown.androidmysql;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class NumberSelectDialog extends Dialog {

    private Button mPositiveButton;
    private Button mNegativeButton;

    private TextView mUnit;

    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;

    public int number;
    public int pointnumber;

    public int valuechanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_numberpicker);

        //뷰 셋팅
        mPositiveButton=(Button)findViewById(R.id.pbutton);
        mUnit=(TextView)findViewById(R.id.unit);



        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(mPositiveListener);

        //넘버피커 리스너도 세팅해야 됌.
        //(여기 넘버피커 리스너를 세팅하기)

        //100~200으로 하고 값이 바뀔때마다 토스트 메시지 띄우기
        NumberPicker picker4 = (NumberPicker)findViewById(R.id.picker4);
        picker4.setValue(100);

        Log.d("값을 확인하세요", "값 확인 : " + valuechanger);

        picker4.setMinValue(1);
        picker4.setMaxValue(250);
        if(this.valuechanger==0){
            picker4.setValue(70);
            number=70;
            mUnit.setText("kg");
        }
        else if(this.valuechanger==1){
            picker4.setValue(170);
            number=170;
            mUnit.setText("cm");
        }
        picker4.setWrapSelectorWheel(false);
        picker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                number=newVal;
                Toast.makeText(getContext(), "Value : " + number, Toast.LENGTH_SHORT).show();
            }
        });


        //0~9로하고 텍스트로 바꿔 출력하기
        NumberPicker picker5 = (NumberPicker)findViewById(R.id.picker5);
        picker5.setMinValue(0);
        picker5.setMaxValue(9);
        picker5.setWrapSelectorWheel(false);
        picker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pointnumber=newVal;
                Toast.makeText(getContext(), "Value : " + pointnumber, Toast.LENGTH_SHORT).show();
            }
        });


    }

    //생성자 생성
    public NumberSelectDialog(@NonNull Context context, View.OnClickListener positiveListener,int changer) {
        super(context);
        this.mPositiveListener = positiveListener;
        this.valuechanger = changer;
    }

}
