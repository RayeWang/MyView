package wang.raye.myview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import wang.raye.library.widge.MoreTextView;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

public class MoreTextViewActivity extends AppCompatActivity {

    @BindById(R.id.mtv_text)
    MoreTextView mtvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        PreIOC.binder(this);
        mtvText.setText("因为公司项目需要全文收起的功能，一共有2种UI，所以需要写2个全文收起的控件，之前也是用过一个全文收起的控件，但是因为设计原因，在ListView刷新的时候会闪烁，我估计原因是因为控件本身的设计是需要先让TextView绘制完成，然后获取TextView一共有多少行，再判断是否需要全文收起按钮，如果需要，则吧TextView压缩回最大行数，添加全文按钮，这样就会造成ListView的Item先高后低，所以会发生闪烁，后面我也在网上找了几个，发现和之前的设计都差不多，虽然肯定是有解决了这个问题的控件，但是还是决定自己写了，毕竟找到控件后还需要测试，而现在的项目时间不充分啊（另外欢迎指教如何快速的找到自己需要的控件，有时候在Github上面搜索，都不知道具体该用什么关键字），而且自己写，也是一种锻炼。");
    }
}
