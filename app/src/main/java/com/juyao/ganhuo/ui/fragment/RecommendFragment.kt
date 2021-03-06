package com.juyao.ganhuo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.droidlover.xdroidmvp.base.RecyclerItemCallback
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.juyao.ganhuo.R
import com.juyao.ganhuo.adapter.CommonAdapter
import com.juyao.ganhuo.model.GanHuoBean
import com.juyao.ganhuo.ui.activity.CommonWebActivity
import kotlinx.android.synthetic.main.fragment_data.*


/**
 *
 *
 *Created by juyao on 2017/5/31 at 16:16.\n
 * 邮箱:juyao0909@gmail.com
 */

class RecommendFragment: DataFragment() {
    var mCommonAdapter: CommonAdapter?=null
    var pageNum=1
    override fun getType(): String {
        return "瞎推荐"
    }

    override fun initData(p0: Bundle?) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        data_list.setLayoutManager(layoutManager)
        mCommonAdapter = CommonAdapter(context)
        data_list.adapter= mCommonAdapter
        loadData(pageNum++)
        data_list!!.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                pageNum=1
                loadData(pageNum++)

            }

            override fun onLoadMore() {
                loadData(pageNum++)
            }
        })
        mCommonAdapter!!.recItemClick = object : RecyclerItemCallback<GanHuoBean, CommonAdapter.ViewHolder>() {
            override fun onItemClick(position: Int, model: GanHuoBean?, tag: Int, holder: CommonAdapter.ViewHolder?) {
                super.onItemClick(position, model, tag, holder)
                val intent = Intent(getContext(), CommonWebActivity::class.java)
                intent.putExtra("url",model!!.url)
                startActivity(intent)
            }
        }
    }
    fun loadData(page:Int){
        p.getGanHuoData(getType(),MAX_PAGESIZE,page)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_data
    }

    companion object{
        fun getInstance():RecommendFragment{
            return  RecommendFragment()
        }
    }

    override fun getDataResult(dataList: List<GanHuoBean>) {
        if(pageNum==2){
            mCommonAdapter!!.setData(dataList)
        }else{
            mCommonAdapter!!.addData(dataList)
        }

    }




}
