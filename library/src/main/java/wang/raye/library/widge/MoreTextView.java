package wang.raye.library.widge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import wang.raye.library.R;

/**
 * 有全文和收起的TextView
 * Created by Raye on 2016/6/24.
 */
public class MoreTextView extends LinearLayout {
    /**
     * TextView的实际高度
     */
    private int textViewHeight;
    /**
     * 默认全文的Text
     */
    private static final String EXPANDEDTEXT = "全文";
    /**
     * 默认收起的text
     */
    private static final String COLLAPSEDTEXT = "收起";
    /**
     * 全文的text
     */
    private String expandedText;
    /**
     * 收起的text
     */
    private String collapsedText;
    /**
     * 字体大小
     */
    private int textSize;
    /**
     * 字体颜色
     */
    private int textColor;
    /**
     * 超过多少行出现全文、收起按钮
     */
    private int trimLines;
    /**
     * 显示文本的TextView
     */
    private TextView showTextView;
    /**
     * 全文和收起的TextView
     */
    private TextView collapseTextView;
    /**
     * 是否是收起状态，默认收起
     */
    private boolean collapsed = true;


    public MoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MoreTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        showTextView = new TextView(context);
        setOrientation(VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreTextView);
        textColor = typedArray.getColor(R.styleable.MoreTextView_textColor, Color.GRAY);
        textSize = typedArray.getDimensionPixelSize(R.styleable.MoreTextView_textSize, 14);
        expandedText = typedArray.getString(R.styleable.MoreTextView_expandedText);
        if (TextUtils.isEmpty(expandedText)) {
            expandedText = EXPANDEDTEXT;
        }
        collapsedText = typedArray.getString(R.styleable.MoreTextView_collapsedText);
        if (TextUtils.isEmpty(collapsedText)) {
            collapsedText = COLLAPSEDTEXT;
        }
        trimLines = typedArray.getInt(R.styleable.MoreTextView_trimLines, 0);
        typedArray.recycle();
        showTextView.setTextSize(textSize);
        showTextView.setTextColor(textColor);
        addView(showTextView);


    }

    public void setText(CharSequence text) {
        globalLayout();
        showTextView.setText(text);
    }

    /**
     * 获取控件实际高度，并设置最大行数
     */
    private void globalLayout() {
        showTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = showTextView.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }

                int allLine = showTextView.getLineCount();

                textViewHeight = showTextView.getLineHeight() * allLine;

                if (trimLines > 0 && trimLines < allLine) {
                    //需要全文和收起
                    if (collapsed) {
                        showTextView.setHeight(showTextView.getLineHeight() * trimLines);
                    }

                    if (collapseTextView == null) {
                        //全文和收起的textView
                        collapseTextView = new TextView(getContext());
                        collapseTextView.setTextSize(textSize);
                        collapseTextView.setTextColor(Color.BLUE);
                        collapseTextView.setText(expandedText);
                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.BOTTOM);
                        collapseTextView.setLayoutParams(lp);
                        collapseTextView.setOnClickListener(collapseListener);
                        addView(collapseTextView);

                    }

                }
            }
        });
    }

    private OnClickListener collapseListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            v.setEnabled(false);
            final int startValue = showTextView.getHeight();
            final int deltaValue;

            if (collapsed) {
                //是放大
                deltaValue = textViewHeight - startValue;

            } else {
                deltaValue = showTextView.getLineHeight() * trimLines - startValue;
            }
            Animation animation = new Animation() {
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    showTextView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                }
            };
            animation.setDuration(500);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setEnabled(true);
                    collapsed = !collapsed;
                    collapseTextView.setText(collapsed ? expandedText : collapsedText);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            showTextView.startAnimation(animation);
        }
    };
}
