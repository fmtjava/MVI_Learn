package com.fmt.mvi.learn.video.adapter

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.databinding.DataBindingUtil
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fmt.mvi.learn.R
import com.fmt.mvi.learn.databinding.ItemVideoBinding
import com.fmt.mvi.learn.gobal.ConfigKeys
import com.fmt.mvi.learn.gobal.Configurator
import com.fmt.mvi.learn.video.model.VideoModel
import com.just.agentweb.AgentWebUtils

class VideoListAdapter :
    BaseQuickAdapter<VideoModel, BaseViewHolder>(R.layout.item_video),
    LoadMoreModule {

    private val mOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                0, 0, view.width, view.height, AgentWebUtils.dp2px(
                    Configurator.getConfiguration<Context>(
                        ConfigKeys.APPLICATION_CONTEXT
                    ), 4f
                ).toFloat()
            )
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemVideoBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: VideoModel) {
        holder.getView<JzvdStd>(R.id.video_player).apply {
            setUp(item.playUrl, item.title, Jzvd.SCREEN_NORMAL)
            posterImageView.load(item.coverUrl)
            outlineProvider = mOutlineProvider
            clipToOutline = true
        }
        holder.getBinding<ItemVideoBinding>()?.item = item
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        val jzvd: Jzvd? = holder.getViewOrNull(R.id.video_player)
        if (jzvd != null && Jzvd.CURRENT_JZVD != null && jzvd.jzDataSource
                .containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
        ) {
            if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                Jzvd.releaseAllVideos()
            }
        }
    }
}
