package org.techtown.androidmysql.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.androidmysql.R;
import org.techtown.androidmysql.RecordData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class getdata extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdata);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
//        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
//        mArrayList = new ArrayList<>();
//
//        mAdapter = new UsersAdapter(this, mArrayList);
//        mRecyclerView.setAdapter(mAdapter);

        Button button_all = (Button) findViewById(R.id.button_main_all);
        button_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                mArrayList.clear();
//                mAdapter.notifyDataSetChanged();

                //정보를 json으로 가지고 오는 Asynk Task
                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/getrecordjson.php", "");
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getdata.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result); //이 부분에서 텍스트 결과 처리하기......
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



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
