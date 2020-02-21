package org.techtown.androidmysql;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

//키,몸무게 입력 다이얼로그 팝업생성 그리고 데이터 저장처리하기.
public class HeightWeightDialog extends Dialog {

    //몸무게와 아이디값을 받기위해 연결
    public EditText WeightText;
    public EditText HeightText;

    private Button mPositiveButton;
    private Button mNegativeButton;

    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mWeightListener;
    private View.OnClickListener mHeightListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.heightweightdialog);

        //EditText지정하기
        WeightText=findViewById(R.id.weight);
        WeightText.setClickable(false);
        WeightText.setFocusable(false);

        HeightText=findViewById(R.id.height);
        HeightText.setClickable(false);
        HeightText.setFocusable(false);

        //셋팅
        mPositiveButton=(Button)findViewById(R.id.pbutton);


        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(mPositiveListener);
        WeightText.setOnClickListener(mWeightListener);
        HeightText.setOnClickListener(mHeightListener);

    }

    //생성자 (클릭리스너를 객체로 받아야 됌.)
    public HeightWeightDialog(@NonNull Context context, View.OnClickListener positiveListener, View.OnClickListener WeightListener,View.OnClickListener HeightListener) {
        super(context);

        //입력완료 값
        this.mPositiveListener = positiveListener;

        //에디트 텍스트 클릭 리스너
        this.mHeightListener= WeightListener;
        this.mWeightListener= HeightListener;
    }

}
