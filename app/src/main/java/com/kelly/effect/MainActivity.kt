package com.kelly.effect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelly.effect.aidl.AIDLTestActivity
import com.kelly.effect.aop.AopMainActivity
import com.kelly.effect.architect.mvp.login.LoginActivity
import com.kelly.effect.javapoet.JPMainActivity
import com.kelly.effect.main.GridDividerItemDecoration
import com.kelly.effect.main.RecyclerAdapter
import com.kelly.effect.neteasy.musiclist.MusicListActivity
import com.kelly.effect.neteasy.playpage.MusicPlayActivity
import com.kelly.effect.widget.colorfilter.ColorFilterActivity
import com.kelly.effect.widget.pwdinput.PwdInputActivity
import com.kelly.effect.widget.svg.SvgChinaMapActivity
import com.kelly.effect.widget.treeAnimation.TreeAnimationActivity
import com.kelly.effect.widget.vLayout.VLayoutActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerAdapter<MainItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.rv_main)
        mRecyclerView?.layoutManager = GridLayoutManager(this, 3)
        mAdapter = object : RecyclerAdapter<MainItem>(createItem(), null) {
            override fun onCreateViewHolder(root: View, viewType: Int): ViewHolder<MainItem> {
                return object : RecyclerAdapter.ViewHolder<MainItem>(root) {
                    var mContent: TextView? = null

                    override fun onBind(mainItem: MainItem) {
                        mContent = root.findViewById(R.id.tv_content)
                        mContent?.text = mainItem.name
                    }

                }
            }

            override fun getItemLayout(mainItem: MainItem, position: Int): Int {
                return R.layout.main_recycle_item
            }
        }
        mRecyclerView?.adapter = mAdapter

        mRecyclerView?.addItemDecoration(GridDividerItemDecoration(this, 3))

        mAdapter?.setAdapterListener(object : RecyclerAdapter.AdapterListenerImpl<MainItem>() {
            override fun onItemClick(holder: RecyclerAdapter.ViewHolder<MainItem>, mainItem: MainItem) {
                super.onItemClick(holder, mainItem)
                when (mainItem.source) {
                    0 -> startActivity(Intent(this@MainActivity, AIDLTestActivity::class.java))
                    1 -> startActivity(Intent(this@MainActivity, SplashAnimActivity::class.java))
                    2 -> startActivity(Intent(this@MainActivity, IrregularActivity::class.java))
                    3 -> startActivity(Intent(this@MainActivity, ExplosionActivity::class.java))
                    4 -> startActivity(Intent(this@MainActivity, BezierActivity::class.java))
                    5 -> startActivity(Intent(this@MainActivity, PathMeasureActivity::class.java))
                    6 -> startActivity(Intent(this@MainActivity, SvgChinaMapActivity::class.java))
                    7 -> startActivity(Intent(this@MainActivity, VLayoutActivity::class.java))
                    8 -> startActivity(Intent(this@MainActivity, TreeAnimationActivity::class.java))
                    9 -> startActivity(Intent(this@MainActivity, MusicListActivity::class.java))
                    10 -> startActivity(Intent(this@MainActivity, MusicPlayActivity::class.java))
                    11 -> startActivity(Intent(this@MainActivity, ColorFilterActivity::class.java))
                    12 -> startActivity(Intent(this@MainActivity, PwdInputActivity::class.java))
                    13 -> startActivity(Intent(this@MainActivity, AopMainActivity::class.java))
                    14 -> startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    15 -> startActivity(Intent(this@MainActivity, JPMainActivity::class.java))
                }
            }
        })
    }

    private fun createItem(): List<MainItem> {
        val items = ArrayList<MainItem>()
        items.add(MainItem("AIDL测试", 0))
        items.add(MainItem("Canvas-splash动画", 1))
        items.add(MainItem("Canvas-不规则图形", 2))
        items.add(MainItem("Canvas-粒子爆炸效果", 3))
        items.add(MainItem("Canvas-贝塞尔曲线", 4))
        items.add(MainItem("PathMeasure-用法示例", 5))
        items.add(MainItem("Svg-中国地图", 6))
        items.add(MainItem("VLayout-淘宝首页", 7))
        items.add(MainItem("Bezier-树生长动画", 8))
        items.add(MainItem("网易云音乐歌单界面", 9))
        items.add(MainItem("网易云音乐播放界面", 10))
        items.add(MainItem("ColorFilter", 11))
        items.add(MainItem("自定义密码输入框", 12))
        items.add(MainItem("AspectJ-AOP示例", 13))
        items.add(MainItem("架构模式-MVP示例", 14))
        items.add(MainItem("JavaPoet示例", 15))
        return items
    }

    inner class MainItem(var name: String, var source: Int = -1)

}
