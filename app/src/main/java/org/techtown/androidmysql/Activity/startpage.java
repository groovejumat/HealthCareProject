package org.techtown.androidmysql.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.androidmysql.HeightWeightDialog;
import org.techtown.androidmysql.NumberSelectDialog;
import org.techtown.androidmysql.R;

public class startpage extends AppCompatActivity {

    private org.techtown.androidmysql.HeightWeightDialog HeightWeightDialog;
    private org.techtown.androidmysql.NumberSelectDialog NumberSelectDialog;

    //저장 처리할 몸무게와 키 값.
    float Weight;
    float Height;


    //셰어드 프리퍼런스 처리를 할 int값
    int weightheight=0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        //셰어드 프리퍼런스로 값가지고 오기



        //셰어드 프리퍼런스 객체 생성. (몸무게와 키를 저장하는데에 사용)
        sharedPreferences = getSharedPreferences("WeightHeight", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //삭제처리하기 (상황에 따라 사용하기)
        editor.remove("weight");
        editor.remove("height");
        editor.commit();

        //파라미터에 리스너 등록
        Handler handler = new Handler();

        //엑티비티 생성 후 1.5초 뒤에 몸무게 키 입력 다이얼로그 생성.
        handler.postDelayed(new Runnable() {
            public void run() {
                float sharedweight = sharedPreferences.getFloat("weight", 0);
                float sharedhegiht = sharedPreferences.getFloat("height", 0);
                if(sharedweight!=0 && sharedhegiht!=0){
                    //로그인 화면으로 페이지 전환
                    Intent intent= new Intent(startpage.this , login.class);
                    startActivity(intent);
                }
                else {
                    HeightWeightDialog = new HeightWeightDialog(startpage.this,positiveListener,WeightListener,HeightListener);
                    HeightWeightDialog.show();
                }
            }
        }, 1500);  // 1500 milliseconds
    }


    //여기다가 셰어드 프리퍼런스 값이 있는지 없는지를 체크한후 전환을 시작한다.//
    //확인 리스너생성
    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {

            //셰어드 저장처리가 완료됩니다
            editor.putFloat("weight",Weight);
            Log.e("셰어드 프리퍼런스 처리 할 값 확인", "몸무게 : " + Weight);
            editor.putFloat("height",Height);
            Log.e("셰어드 프리퍼런스 처리 할 값 확인", "몸무게 : " + Height);
            editor.commit();


            //로그인 화면으로 페이지 전환
            Intent intent= new Intent(startpage.this , sign.class);
            startActivity(intent);


            //셰어드 프리퍼런스로 값 저장 처리.
            Toast.makeText(getApplicationContext(), "메인 다이얼로그 리스너 반응." ,Toast.LENGTH_SHORT).show();
            HeightWeightDialog.dismiss();
        }
    };
    //

    //몸무게리스너
    private View.OnClickListener WeightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "키 입력 버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            weightheight=0;
            NumberSelectDialog = new NumberSelectDialog(startpage.this,selectListener,1);
            NumberSelectDialog.show();
        }
    };

    //키리스너
    private View.OnClickListener HeightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "몸무게 입력 버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            weightheight=1;
            NumberSelectDialog = new NumberSelectDialog(startpage.this,selectListener,0);
            NumberSelectDialog.show();
        }
    };


    private View.OnClickListener selectListener = new View.OnClickListener() {
        public void onClick(View v) {

            int Number = NumberSelectDialog.number;
            int PointNumber = NumberSelectDialog.pointnumber;

            String Weightbefore = Number+"."+PointNumber;
            float Weightfloat = Float.parseFloat(Weightbefore);

            Toast.makeText(getApplicationContext(), Weightfloat+" 입니다." ,Toast.LENGTH_SHORT).show();

            if(weightheight == 0){
                HeightWeightDialog.HeightText.setText(Weightfloat+"");
                Weight=Weightfloat;
            }
            else if(weightheight == 1){
                HeightWeightDialog.WeightText.setText(Weightfloat+"");
                Height=Weightfloat;
            }


            NumberSelectDialog.dismiss();
        }
    };

}
