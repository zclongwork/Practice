package com.zcl.practice.ioc;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zcl.practice.BaseActivity;
import com.zcl.practice.R;

/**
 * Inversion of Control 控制反转
 *
 */
@ContentView(R.layout.activity_ioc)
public class IocActivity extends BaseActivity {


    @ViewInject(R.id.bt_1)
    private Button button1;

    @ViewInject(R.id.bt_2)
    private Button button2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button1.setText("我是button1");
        button2.setText("我是button2");
    }


    @OnClick({R.id.bt_1})
    public void click(View view) {
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick({R.id.bt_2})
    public boolean  longClick(View view) {
        Toast.makeText(this, "长按点击", Toast.LENGTH_SHORT).show();
        return false;
    }

}
