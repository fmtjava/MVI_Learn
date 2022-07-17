package com.fmt.mvi.learn.travel.adapter

import android.os.Bundle
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fmt.mvi.learn.gobal.gson
import com.fmt.mvi.learn.travel.fragment.TravelTabFragment
import com.fmt.mvi.learn.travel.model.TravelModel

class TravelPageAdapter(fragment: Fragment, private val travelTabModel: TravelModel) :
    FragmentStateAdapter(fragment) {

    private val fragments by lazy { ArrayMap<String, Fragment?>(travelTabModel.tabs.size) }

    override fun getItemCount(): Int {
        return travelTabModel.tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        val tab = travelTabModel.tabs[position]
        var fragment = fragments[tab.groupChannelCode]
        if (fragment == null) {
            val bundle = Bundle()
            with(bundle) {
                putString(TravelTabFragment.URL, travelTabModel.url)
                putString(TravelTabFragment.PARAMS, gson.toJson(travelTabModel.params))
                putString(TravelTabFragment.GROUP_CHANNEL_CODE, tab.groupChannelCode)
                putInt(TravelTabFragment.TYPE, tab.type)
            }
            fragment = TravelTabFragment.newInstance(bundle)
            fragments[tab.groupChannelCode] = fragment
        }
        return fragment
    }
}