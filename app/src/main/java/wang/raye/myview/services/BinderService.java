package wang.raye.myview.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class BinderService extends Service {
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (isContinue) {
                    if (handler != null) {
                        handler.sendEmptyMessage(1);
                    }
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private boolean isContinue = true;
    private Handler handler ;

    public BinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        thread.start();
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isContinue = false;
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {

        public void stop() {
            isContinue = false;
        }

        public void setHandler(Handler handler) {
            BinderService.this.handler = handler;
        }

        public BinderService getServices() {
            return BinderService.this;
        }
    }

}
