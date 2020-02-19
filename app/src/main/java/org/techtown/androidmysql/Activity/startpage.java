package org.techtown.androidmysql.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.techtown.androidmysql.HeightWeightDialog;
import org.techtown.androidmysql.R;

public class startpage extends AppCompatActivity {

    private HeightWeightDialog HeightWeightDialog;

    //이 부분에 에디트 텍스트 변수 지정.
    EditText HeightText;
    EditText WeightText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        //셰어드 프리퍼런스 객체 생성.
        SharedPreferences sharedPreferences = getSharedPreferences("WeightHeight", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //파라미터에 리스너 등록
        HeightWeightDialog = new HeightWeightDialog(this,positiveListener,negativeListener);
        HeightWeightDialog.show();

    }


    //확인 리스너생성
    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "확인버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();

            HeightWeightDialog.dismiss();
        }
    };

    //취소 리스너생성
    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            HeightWeightDialog.dismiss();
        }
    };
}
