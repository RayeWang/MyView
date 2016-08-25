package wang.raye.myview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * 不用递归遍历所有view节点的activity
 * Create by Raye on 2016年8月25日10:56:33
 */
public class NonRecursiveActivity extends AppCompatActivity {

    @BindById(R.id.rl_main)
    LinearLayout rlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_recursive);
        PreIOC.binder(this);

        ArrayList<View> groups = new ArrayList<>();
        groups.add(rlMain);
        while (groups.size() > 0){
            View view = groups.get(0);
            groups.remove(0);
            for(int j = 0;j < ((ViewGroup) view).getChildCount();j++){
                View temp = ((ViewGroup) view).getChildAt(j);
                if(temp instanceof ViewGroup){
                    groups.add(temp);
                }else if(temp instanceof Button){
                    temp.setBackgroundColor(Color.RED);
                }
            }
        }
    }
}
