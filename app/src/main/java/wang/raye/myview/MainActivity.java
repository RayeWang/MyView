package wang.raye.myview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;
import wang.raye.preioc.annotation.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @BindById(R.id.list)
    ListView list;

    private ArrayList<ActivityInfo> mActivities = null;

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreIOC.binder(this);

        try {

            PackageInfo pi = getPackageManager().getPackageInfo("wang.raye.myview",
                    PackageManager.GET_ACTIVITIES);
            // 获取所有Activity信息
            mActivities = new ArrayList<ActivityInfo>(
                    Arrays.asList(pi.activities));
            //获取本类的名字
            String ourName = getClass().getName();
            //获取Activity的名字
            ArrayList<String> names = new ArrayList<String>();

            for(int i = mActivities.size() - 1; i > -1;i--){
                if(mActivities.get(i).name.equals(ourName)){
                    mActivities.remove(i);
                }else{
                    if(mActivities.get(i).nonLocalizedLabel != null){
                        names.add(0, mActivities.get(i).nonLocalizedLabel.toString());
                    }else{
                        mActivities.remove(i);
                    }
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            adapter.addAll(names);

            list.setAdapter(adapter);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnItemClick({R.id.list})
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Intent intent = new Intent();
        intent.setClassName(this, mActivities.get(position).name);
        startActivity(intent);
    }

}
