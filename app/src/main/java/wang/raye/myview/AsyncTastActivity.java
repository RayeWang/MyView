package wang.raye.myview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;
import wang.raye.preioc.annotation.OnClick;

/**
 * 测试AsyncTask的activity
 * Create by Raye on 2016年8月24日23:51:15
 */
public class AsyncTastActivity extends AppCompatActivity {
    private static final String TAG = AsyncTastActivity.class.getName();
    @BindById(R.id.btn_close)
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_tast);
        PreIOC.binder(this);
        new TestTask().execute();
    }

    @OnClick(R.id.btn_close)
    public void onClick(View view){
        finish();
    }
    public class TestTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.i(TAG,"begin doInBackground");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"end doInBackground");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.i(TAG,"onPostExecute");
            Toast.makeText(AsyncTastActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();
            super.onPostExecute(aBoolean);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
