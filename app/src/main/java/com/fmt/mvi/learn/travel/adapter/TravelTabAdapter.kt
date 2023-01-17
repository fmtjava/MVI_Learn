package com.fmt.mvi.learn.travel.adapter

import android.content.Context
import android.widget.ImageView
import cn.jzvd.JZUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.fmt.mvi.learn.R
import com.fmt.mvi.learn.databinding.ItemTravelBinding
import com.fmt.mvi.learn.gobal.ConfigKeys
import com.fmt.mvi.learn.gobal.Configurator
import com.fmt.mvi.learn.travel.model.TravelItem
import com.just.agentweb.AgentWebUtils

class TravelTabAdapter :
    BaseQuickAdapter<TravelItem, BaseDataBindingHolder<ItemTravelBinding>>(R.layout.item_travel),
    LoadMoreModule {

    private val mMaxImageWidth =
        (JZUtils.getScreenWidth(Configurator.getConfiguration<Context>(ConfigKeys.APPLICATION_CONTEXT))
                - AgentWebUtils.dp2px(
            Configurator.getConfiguration<Context>(ConfigKeys.APPLICATION_CONTEXT),
            20f
        )) / 2

    override fun convert(holder: BaseDataBindingHolder<ItemTravelBinding>, item: TravelItem) {
        holder.dataBinding?.item = item
        val coverImageInfo = item.article.images[0]
        val layoutParams = holder.getView<ImageView>(R.id.iv_cover).layoutParams
        layoutParams.width = mMaxImageWidth
        layoutParams.height =
            (mMaxImageWidth * coverImageInfo.height / coverImageInfo.width).toInt()
    }

}