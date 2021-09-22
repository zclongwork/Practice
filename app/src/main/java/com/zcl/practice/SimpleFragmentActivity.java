package com.zcl.practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zcl.practice.ui.SeniorUIFragment;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * toolBar+fragment页面
 * @author zcl
 * @since 2021-09-01
 */
public class SimpleFragmentActivity extends BaseActivity {

    private static final String TAG = "SimpleFragmentActivity";
    private Toolbar toolbar;

    public static void startSeniorUI(Context context) {
        Intent intent = new Intent(context, SimpleFragmentActivity.class);
        intent.putExtra("type", 1);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_fragment);
        initView();
        initListener();

        if (savedInstanceState == null) {

            Intent intent = getIntent();
            int type = intent.getIntExtra("type", 10);
            Bundle bundle = null;

            Class<? extends Fragment> fragmentClass = SeniorUIFragment.class;
            int titleRes = R.string.senior_ui;

            if (type == 1) {
                fragmentClass = SeniorUIFragment.class;
                titleRes = R.string.senior_ui;
            }
            toolbar.setTitle(titleRes);

            setTitle(titleRes);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, fragmentClass, bundle)
                    .commit();
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initListener() {

    }

    public void add(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, fragmentClass, bundle)
                .commit();
    }

}