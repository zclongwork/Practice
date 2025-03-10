package com.zcl.practice.database.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zcl.practice.R
import com.zcl.practice.database.utils.DbManager
import com.zcl.practice.database.dao.UserDao
import com.zcl.practice.database.entity.User
import com.zcl.practice.database.utils.ToastUtil
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class DatabaseActivity : AppCompatActivity() {
    private val TAG = DatabaseActivity::class.java.simpleName
    private lateinit var net: Button
    private lateinit var queryBtn: Button
    private lateinit var insertBtn: Button
    private lateinit var updateBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var resultRecycle: RecyclerView
    private var list = mutableListOf<User>()
    var userDao: UserDao = DbManager.db.userDao()

    private val userAdapter by lazy {
        UserAdapter(R.layout.user_item_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        findVbId()
    }

    private fun findVbId() {
        net = findViewById(R.id.net)
        queryBtn = findViewById(R.id.query)
        insertBtn = findViewById(R.id.insert)
        updateBtn = findViewById(R.id.update)
        deleteBtn = findViewById(R.id.delete)
        resultRecycle = findViewById(R.id.result_recycle)
        resultRecycle.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        resultRecycle.adapter = userAdapter
        userAdapter.addChildClickViewIds(R.id.item_delete, R.id.item_modify)
        net.setOnClickListener { startActivity(Intent(this, NetFrameActivity::class.java)) }
        queryBtn.setOnClickListener { query() }
        insertBtn.setOnClickListener { insertSingle() }
        deleteBtn.setOnClickListener { delete() }
        updateBtn.setOnClickListener { update() }
        userAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.item_delete -> singleDel(list[position])
                R.id.item_modify -> singleModify(list[position])
            }
        }
        insertAll()
    }

    /**
     * 查询
     */
    private fun query() {
        list = userDao.queryAllUser()
        Log.e(TAG, list.toString())
        userAdapter.setList(list)
    }

    /**
     * 默认插入批量数据
     */
    private fun insertAll() {
        runBlocking {
            if (userDao.queryAllUser().size == 0) {
                val mutableList: MutableList<User> = mutableListOf()
                for (a in 1..3) {
                    val user = User("张三$a", 20 + a, "南京建邺$a", "")
                    mutableList.add(user)
                }
                userDao.addBatchUser(mutableList)
                ToastUtil.show("批量新增数据成功")
            }
            query()
        }
    }

    /**
     * 插入单条数据
     */
    private fun insertSingle() {
        val age = Random.nextInt(20, 40)
        val user = User("小二", age, "北京朝阳", "")
        userDao.addUser(user)
        ToastUtil.show("新增一条数据成功")
        query()
    }

    /**
     * 删除表里的所有数据
     */
    private fun delete() {
        userDao.deleteAllUser()
        ToastUtil.show("删除所有数据成功")
        query()
    }

    /**
     * 更新所有数据
     */
    private fun update() {
        userDao.updateAll()
        ToastUtil.show("更新表里所有年龄数据成功")
        query()
    }

    /**
     * 删除loginUser表里的指定删除某一个
     */
    private fun singleDel(singleUser: User) {
        userDao.deleteSingle(singleUser)
        ToastUtil.show("删除单条数据成功")
        query()
    }

    /**
     * 修改数据表里某一个对象的字段值
     */
    private fun singleModify(user: User) {
        user.aliasName = "修改的" + user.aliasName
        user.age = 100
        user.ads = "修改的地址"
        userDao.updateUser(user)
        ToastUtil.show("更新单条数据成功")
        list.clear()
        query()
    }
}
