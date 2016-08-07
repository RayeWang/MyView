package wang.raye.myview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;
import wang.raye.preioc.annotation.OnClick;
import widget.HeartLayout;

public class AnimActivity extends AppCompatActivity {

    @BindById(R.id.iv_destination)
    ImageView ivDestination;
    @BindById(R.id.iv_source)
    ImageView ivSource;
    @BindById(R.id.btn_begin)
    Button btnBegin;
    @BindById(R.id.heart_layout)
    HeartLayout heartLayout;

    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        PreIOC.binder(this);
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addImage(R.mipmap.ic_flower);
                    }
                });
            }
        }, 500, 200);
    }

    @OnClick(R.id.btn_begin)
    public void onClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.flower);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, (ivDestination.getX() - ivSource.getX()), 0, (ivDestination.getY() - ivSource.getY()));
        translateAnimation.setStartOffset(3000);
        translateAnimation.setDuration(2000);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.f, 1f, 0.f);
        scaleAnimation.setStartOffset(3000);
        scaleAnimation.setDuration(2000);


        AnimationSet set = new AnimationSet(false);
        set.addAnimation(animation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimation);

        Log.i("AnimActivity", "x:" + (ivDestination.getX() - ivSource.getX()) + "   y:" + (ivDestination.getY() - ivSource.getY()));
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ivSource.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivSource.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivSource.startAnimation(set);

    }
}
