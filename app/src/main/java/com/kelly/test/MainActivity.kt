package com.kelly.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelly.test.aidl.AIDLTestActivity
import com.kelly.test.main.GridDividerItemDecoration
import com.kelly.test.main.RecyclerAdapter
import com.kelly.test.neteasy.musiclist.MusicListActivity
import com.kelly.test.neteasy.playpage.MusicPlayActivity
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
                    5 -> startActivity(Intent(this@MainActivity, MusicListActivity::class.java))
                    6 -> startActivity(Intent(this@MainActivity, MusicPlayActivity::class.java))
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
        items.add(MainItem("网易云音乐歌单界面", 5))
        items.add(MainItem("网易云音乐播放界面", 6))
        return items
    }

    inner class MainItem(var name: String, var source: Int = -1)

}
