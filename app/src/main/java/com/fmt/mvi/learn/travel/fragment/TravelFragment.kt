package com.fmt.mvi.learn.travel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.fmt.mvi.learn.commom.ui.BaseFragment
import com.fmt.mvi.learn.databinding.FragmentTravelBinding
import com.fmt.mvi.learn.travel.adapter.TravelPageAdapter
import com.fmt.mvi.learn.travel.action.TravelViewAction
import com.fmt.mvi.learn.travel.model.TravelModel
import com.fmt.mvi.learn.travel.state.TravelViewState
import com.fmt.mvi.learn.travel.viewmodel.TravelViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.zy.multistatepage.state.ErrorState
import com.zy.multistatepage.state.LoadingState
import com.zy.multistatepage.state.SuccessState
import kotlinx.coroutines.flow.distinctUntilChanged

class TravelFragment : BaseFragment() {

    private lateinit var mBinding: FragmentTravelBinding
    private val mViewModel by viewModels<TravelViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTravelBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUIStateCallback()
    }

    private fun registerUIStateCallback() {
        lifecycleScope.launchWhenResumed {
            mViewModel.state.flowWithLifecycle(lifecycle).distinctUntilChanged().collect { state ->
                when (state) {
                    is TravelViewState.LoadingState -> mBinding.multiStateContainer.show<LoadingState>()
                    is TravelViewState.LoadSuccess -> {
                        mBinding.multiStateContainer.show<SuccessState>()
                        initTravelTab(state.travelTabModel)
                    }
                    is TravelViewState.LoadFail -> {
                        mBinding.multiStateContainer.show<ErrorState> {
                            it.retry {
                                mViewModel.dispatch(TravelViewAction.Retry)
                            }
                        }
                        Toast.makeText(requireContext(), state.errorMsg, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initTravelTab(travelTabModel: TravelModel) {
        for (tab in travelTabModel.tabs) {
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab())
        }
        mBinding.viewPager.adapter = TravelPageAdapter(this, travelTabModel)
        mBinding.viewPager.offscreenPageLimit = travelTabModel.tabs.size
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = travelTabModel.tabs[position].labelName
        }.attach()
    }

    override fun loadPageData() {
        mViewModel.dispatch(TravelViewAction.GetTravelTabs)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}