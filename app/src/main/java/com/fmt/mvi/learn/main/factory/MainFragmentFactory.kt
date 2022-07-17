package com.fmt.mvi.learn.main.factory

import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.fmt.mvi.learn.travel.fragment.TravelFragment
import com.fmt.mvi.learn.video.fragment.VideoFragment

class MainFragmentFactory {

    companion object {
        private val fragments by lazy { SparseArray<Fragment?>() }

        @JvmStatic
        fun create(position: Int): Fragment {
            var fragment = fragments.get(position)
            if (fragment == null) {
                fragment = when (position) {
                    0 -> TravelFragment()
                    else -> VideoFragment()
                }
                fragments.put(position, fragment)
            }
            return fragment
        }

        @JvmStatic
        fun clear() {
            fragments.clear()
        }
    }

}