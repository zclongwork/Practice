package com.zcl.practice.database.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.drake.net.Get
import com.drake.net.utils.scopeNetLife
import com.zcl.practice.R
import com.zcl.practice.database.entity.ChannelEntity
import com.zcl.practice.database.utils.HmConverter

/**
 * Author: zcl
 * Date: 2022/06/02
 * Net 第三方网络框架测试类
 * github: https://github.com/liangjingkanji/Net
 */
class NetFrameActivity : AppCompatActivity() {

    lateinit var btn: Button
    lateinit var result: TextView
    private val TAG = NetFrameActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.net_frame_layout)
        initV()
    }

    private fun initV() {
        btn = findViewById(R.id.net_test_btn)
        result = findViewById(R.id.show_result)
        btn.setOnClickListener {
            testNet()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun testNet() {
        //网络框架 协程使用
        scopeNetLife {
            val res = Get<List<ChannelEntity>>("http://toutiao.itheima.net/v1_0/user/channels") {
                converter = HmConverter()
            }.await()
            Log.e(TAG, "response-- $res")
            runOnUiThread {
                result.text = "请求结果：>>$res"
            }
        }
    }
}