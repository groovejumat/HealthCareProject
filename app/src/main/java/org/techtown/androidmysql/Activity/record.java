package org.techtown.androidmysql.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.androidmysql.R;
import org.techtown.androidmysql.RecordData;
import org.techtown.androidmysql.dailyweight;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class record extends AppCompatActivity {

    private LineChart lineChart; //라인차트 뷰 객체 생성

    //x축표시
    ArrayList<String> xVal;

    //총 칼로리
    TextView Totalkcalview;
    int Totalkcal;
    //총 운동 횟수
    TextView Totalcountview;
    int Totalcount;
    //총 운동 시간
    TextView Totaltimeview;
    int Totaltime;

    //종합 통계를 만들기 위한 도표
    ArrayList<dailyweight> dailyweightArraylist = new ArrayList<dailyweight>();
    dailyweight dailyweightitem;

    //////////////////////
    private static String IP_ADDRESS = "15.164.218.247";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<RecordData> mArrayList;
    //    private UsersAdapter mAdapter;
//    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;
    //////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        //총 데이터를 담기 위한 뷰들에 대한 값 설정
        Totalcountview=findViewById(R.id.totalcount); //총 운동 횟수
        Totalkcalview=findViewById(R.id.totalkcal); //총 칼로리
        Totaltimeview=findViewById(R.id.totaltime); //총 운동 시간


         //결과값을 담는 textveiw지정//
//        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
//        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
//        mArrayList = new ArrayList<>();
//
//        mAdapter = new UsersAdapter(this, mArrayList);
//        mRecyclerView.setAdapter(mAdapter);


        //다음 메소드를 만들어서 실행하게 되면 값 정보를 가지고 온다.


//                mArrayList.clear();
//                mAdapter.notifyDataSetChanged();



        //정보를 json으로 가지고 오는 Asynk Task
        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getrecordjson.php", "");


        //라인차트 테스트//
        lineChart = (LineChart)findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1, 60));
        entries.add(new Entry(3, 65));
        entries.add(new Entry(4, 66));
        entries.add(new Entry(5, 66));
        entries.add(new Entry(6, 67));
        entries.add(new Entry(7, 65));

        LineDataSet lineDataSet = new LineDataSet(entries, "일주일 몸무게 변화량");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        //이 부분에서 포메터를 적용시킨다.
        //xAxis.setValueFormatter(new MyXAxisValueFormatter());

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawLabels(true);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);

        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

        MyMarkerView marker = new MyMarkerView(this,R.layout.custom_markerview);
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);


        //머테리얼 캘린더뷰 연결

        MaterialCalendarView materialCalendarView;
        materialCalendarView=findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2019, 0, 1))
                .setMaximumDate(CalendarDay.from(2021, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //calenderview 날짜 클릭 리스너.
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getApplicationContext(), "날짜를 눌렸습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        materialCalendarView.addDecorators(new OneDayDecorator());

    }


    //오늘 날짜에 지정색을 표시하는 데코레이터
    //++ 추가 이해 marterial Decorator는 아래와 같은 방식으로 자신의 취향에 맞게 커스텀이 가능하다.
    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }


    //찍힌 점에 대한 값을 입력하는 "MakerView"클래스 만들기 클래스
    public class MyMarkerView extends MarkerView {

        private TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);

            tvContent = (TextView)findViewById(R.id.tvContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            if (e instanceof CandleEntry) {

                CandleEntry ce = (CandleEntry) e;

                tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
            } else {

                tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
            }

            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }


    //X축의 값을 포멧 해주는 포메터//
    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            //axis.setLabelCount(5,true);
            return value + "!!!!";
        }

    }


    //데이터를 실제로 받아오는 Getdata//
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(record.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result); //이 부분에서 텍스트 결과 처리하기......
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }
    //데이터를 실제로 받아오는 Getdata//

    //서버에서 쿼리를 통해 데이터를 가지고 오는 showresult//
    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_EMAIL = "email";
        String TAG_TIME = "exercise_time";
        String TAG_KCAL ="kcal";
        String TAG_WEIGHT ="change_weight";
        String TAG_PROGRAMNUM ="program_num";
        String TAG_DATE ="exercise_date";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String email = item.getString(TAG_EMAIL);
                String time = item.getString(TAG_TIME);
                String kcal = item.getString(TAG_KCAL);
                String weight = item.getString(TAG_WEIGHT);
                String programnum = item.getString(TAG_PROGRAMNUM);
                String date = item.getString(TAG_DATE);

                //date를 float 형태로 만들기 ex)02.20
                int year = Integer.parseInt(date.substring(0,4));
                Log.e("년도:",year+"");
                String datebefore = date.replace('-','.');
                Log.e("날짜:",datebefore+"");
                String dateafter = datebefore.substring(5,10);
                Log.e("날짜:",dateafter+"");
                float weightafter=Float.parseFloat(weight);


                //weight를 flaot 형태로 만들기 string을 float으로



                //데일리 몸무게 정보를 담고 있는 클래스 객체를 생성
                dailyweightitem = new dailyweight(year,dateafter,weightafter);
                //dailyweightArraylist.add(new dailyweight(year,dateafter,weightafter));

                //총 운동 횟수
                Totalcount+=1;

                //다른날짜 값만을 담는 배열을 생성

                //총 운동 시간
                Totaltime+=Integer.parseInt(time);

                //운동 평균 횟수

                //운동 소모 칼로리
                Totalkcal+=Integer.parseInt(kcal);

                //entri 객체에 값을 넣어 준다.


                Log.e("태그 이메일:",email);
                Log.e("태그 시간:",time);
                Log.e("태그 칼로리:",kcal);
                Log.e("태그 몸무게:",weight);
                Log.e("태그 프로그램이름:",programnum);
                Log.e("태그 날짜:",date);


                //result부분 클래스를 담기위한 값들.....
                RecordData recorddata = new RecordData();

//                recorddata.setMember_id(id);
//                recorddata.setMember_name(name);
//                recorddata.setMember_country(country);

                //mArrayList.add(personalData);
                //mAdapter.notifyDataSetChanged();
            }
            //arraylist에 데일리 날짜와 몸무게 값을 추가.

            //x축에 라벨 값 표기
            //String[] values = new String[7];
            ArrayList<String> values = new ArrayList<String>();

            //values;
            XAxis xAxis = lineChart.getXAxis();

            for(int i=0; i<dailyweightArraylist.size(); i++){
                if(dailyweightArraylist.contains(dailyweightitem))
                dailyweightArraylist.set(i,dailyweightitem);

            }
            dailyweightArraylist.add(dailyweightitem);

            Log.e("불러온 데이터의 길이:",dailyweightArraylist.size()+"");

            Log.e("총 운동 횟수:",Totalcount+"");
            Log.e("총 시간:",Totaltime+"");
            Log.e("총 칼로리:",Totalkcal+"");

            Totalcountview.setText(Totalcount + " 회");
            Totaltimeview.setText(Totaltime + " 초");
            Totalkcalview.setText(Totalkcal + " kcal");


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    //서버에서 쿼리를 통해 데이터를 가지고 오는 showresult//
}
