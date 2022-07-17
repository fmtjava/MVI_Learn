package com.fmt.mvi.learn.commom.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var mHasLoadedData = false

    override fun onResume() {
        super.onResume()
        if (!mHasLoadedData) {
            loadPageData()
            mHasLoadedData = true
        }
    }

    abstract fun loadPageData()

}