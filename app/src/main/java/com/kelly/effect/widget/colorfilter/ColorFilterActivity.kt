package com.kelly.effect.widget.colorfilter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelly.effect.R

import java.util.ArrayList

class ColorFilterActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mColorFilterAdapter: ColorFilterAdapter? = null
    private val filters = ArrayList<FloatArray>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_color_filter)

        inItFilters()
        mRecyclerView = findViewById(R.id.recyclerView)
        mColorFilterAdapter = ColorFilterAdapter(layoutInflater, filters)
        val gridLayoutManager = GridLayoutManager(this, 3)
        mRecyclerView?.layoutManager = gridLayoutManager
        mRecyclerView?.adapter = mColorFilterAdapter
    }

    private fun inItFilters() {
        filters.add(ColorFilter.colormatrix_heibai)
        filters.add(ColorFilter.colormatrix_fugu)
        filters.add(ColorFilter.colormatrix_gete)
        filters.add(ColorFilter.colormatrix_chuan_tong)
        filters.add(ColorFilter.colormatrix_danya)
        filters.add(ColorFilter.colormatrix_guangyun)
        filters.add(ColorFilter.colormatrix_fanse)
        filters.add(ColorFilter.colormatrix_hepian)
        filters.add(ColorFilter.colormatrix_huajiu)
        filters.add(ColorFilter.colormatrix_jiao_pian)
        filters.add(ColorFilter.colormatrix_landiao)
        filters.add(ColorFilter.colormatrix_langman)
        filters.add(ColorFilter.colormatrix_ruise)
        filters.add(ColorFilter.colormatrix_menghuan)
        filters.add(ColorFilter.colormatrix_qingning)
        filters.add(ColorFilter.colormatrix_yese)
    }

}
