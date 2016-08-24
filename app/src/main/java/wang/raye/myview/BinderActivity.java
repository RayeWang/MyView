package wang.raye.myview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wang.raye.myview.services.BinderService;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;
import wang.raye.preioc.annotation.OnClick;

/**
 * 测试binder的activity
 * Create by Raye on 2016年8月24日16:32:00
 */
public class BinderActivity extends AppCompatActivity {

    @BindById(R.id.btn_start)
    Button btnStart;
    @BindById(R.id.tv_msg)
    TextView tvMsg;
    private ServiceConnection sc;
    private BinderService.MyBinder binder;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tvMsg.setText(""+System.currentTimeMillis());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        PreIOC.binder(this);
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (BinderService.MyBinder) service;
                binder.setHandler(handler);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

    }

    @OnClick({R.id.btn_start,R.id.btn_stop})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_start:
                Intent intent = new Intent(this, BinderService.class);
                bindService(intent,sc, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop:
                unbindService(sc);
                break;
        }

    }


    @Override
    protected void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }
}
