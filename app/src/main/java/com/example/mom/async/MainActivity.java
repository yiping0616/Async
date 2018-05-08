package com.example.mom.async;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Job1Task 在畫面按下GO1 工作五秒鐘 結束後再TextView顯示DONE
    public void go1(View view){
        new Job1Task().execute();
    }
    //內部類別 繼承AsyncTask 必須實作 doInBackground()
    class Job1Task extends AsyncTask<Void , Void , Void >{   //若不需要任何傳入值 不需更新資料 不需回傳資料  使用Void類別(大寫V)

        @Override
        protected Void doInBackground(Void... voids) {  //必須以 try...catch處理  , 可只打 Thread.sleep(5000)後 按Alt+Enter快速處理
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        //五秒結束後 改變TextView文字 因此override onPostExecute()  doInBackground執行完會自動執行 onPostExecute()
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView info = findViewById(R.id.info);
            info.setText("DONE");
        }
    }

    //Job2Task 在畫面按下GO2 工作n秒 結束後再TextView顯示DONE , n秒為傳入值 整數
    public void go2(View view){
        new Job2Task().execute(3);  //工作3秒
    }
    class Job2Task extends AsyncTask<Integer , Void , Void>{

        @Override
        protected Void doInBackground(Integer... integers) { //integers資料型態為整數陣列 傳入的3放在integers[0]
            try {
                Thread.sleep(integers[0]*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView info = findViewById(R.id.info);
            info.setText("DONE");
        }
    }

    //Job3Task 在畫面按下GO3 與需求2一樣 但需要更新秒數到TextView
    public void go3(View view){
        new Job3Task().execute(6);
    }
    class Job3Task extends AsyncTask<Integer , Integer , Void>{

        private TextView info ;
        public Job3Task(){
            info = findViewById(R.id.info);
        }
        @Override
        protected Void doInBackground(Integer... integers) {
            for (int i=1 ; i <= integers[0] ; i++) {
                publishProgress(i);  //publishProgress() 會自動執行 onProgressUpdate()內的程式 ,後面Thread.sleep(1000) 所以每秒會更新TextView
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {   //更新TextView的字
            super.onProgressUpdate(values);
            info.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            info.setText("DONE");
        }

    }

}
