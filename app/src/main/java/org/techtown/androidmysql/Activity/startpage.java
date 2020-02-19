package org.techtown.androidmysql.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.techtown.androidmysql.HeightWeightDialog;
import org.techtown.androidmysql.R;

public class startpage extends AppCompatActivity {

    private HeightWeightDialog HeightWeightDialog;

    //이 부분에 에디트 텍스트 변수 지정.


    //셰어드 프리퍼런스를 통해서 키와 몸무게 값을 저장값에 따라 다이얼로그 생성처리 실행.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

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
