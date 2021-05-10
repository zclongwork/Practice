package com.zcl.practice;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.library.util.adapter.SimpleBaseAdapter;
import com.zcl.library.util.adapter.ViewHolder;
import com.zcl.practice.animation.AnimationActivity;
import com.zcl.practice.constant.ItemId;
import com.zcl.practice.danmakuflame.DemoActivity;
import com.zcl.practice.fragment.FragmentDemoActivity;
import com.zcl.practice.fragment.PageGridActivity;
import com.zcl.practice.fragment.PageZoomActivity;
import com.zcl.practice.ioc.IocActivity;
import com.zcl.practice.plugin.PluginDemoActivity;
import com.zcl.practice.rx.RxJavaDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    
    
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    
    
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
    }
    
    
    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {
        
        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于Practice需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用Practice")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }
    
    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }
    
    
    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSetting();
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    
    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSetting() {
    
        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许Practice使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }
    
    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        
        startActivityForResult(intent, 123);
    }
    
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSetting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    
        ListView listView = (ListView) findViewById(R.id.list);
        
        final List<ListItem> data = getData();
        
        ListAdapter adapter = new ListAdapter(this, data);
        
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListItem item = data.get(position);
                int itemId = item.id;
                switch (itemId) {
                    case ItemId.FRAGMENT:
                        startActivity(new Intent(MainActivity.this, FragmentDemoActivity.class));
                        break;
                    case ItemId.GRID:
                        startActivity(new Intent(MainActivity.this, PageGridActivity.class));
                        break;
                    case ItemId.ZOOM:
                        startActivity(new Intent(MainActivity.this, PageZoomActivity.class));
                        break;
                    default:
                        break;
                }


            }
        });
    }
    
    private List<ListItem> getData() {
        List<ListItem> data = new ArrayList<>();
        data.add(new ListItem(ItemId.FRAGMENT,"Fragment"));
        data.add(new ListItem(ItemId.GRID,"横向分页的GridView效果"));
        data.add(new ListItem(ItemId.ZOOM,"滑动缩放效果"));
        return data;
    }
    
    static class ListAdapter extends SimpleBaseAdapter<ListItem> {
    
        protected ListAdapter(Context context,
                List<ListItem> data) {
            super(context, data);
        }
    
        @Override
        protected int getItemViewLayoutRes() {
            return R.layout.item_main;
        }
    
        @Override
        protected View getItemView(int position, View convertView, ViewHolder holder) {
            final ListItem item = getItem(position);
            if (item != null && holder != null) {
                TextView titleView = holder.getView(R.id.tv_main);
                titleView.setText(item.title);
            }
            return convertView;
        }
    }
    
    static class ListItem {
        int id;
        String title;
        
        ListItem(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }
    

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent i = new Intent(this, AnimationActivity.class);
            startActivity(i);
            
        } else if (id == R.id.nav_gallery) {
    
            Intent i = new Intent(this, DemoActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(this, RxJavaDemoActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, ToolsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(this, IocActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(this, PluginDemoActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
