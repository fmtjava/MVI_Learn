package com.fmt.mvi.learn.travel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.fmt.mvi.learn.R
import com.fmt.mvi.learn.commom.ui.BaseFragment
import com.fmt.mvi.learn.databinding.FragmentCommonListBinding
import com.fmt.mvi.learn.gobal.fromJson
import com.fmt.mvi.learn.travel.adapter.TravelTabAdapter
import com.fmt.mvi.learn.travel.action.TravelTabViewAction
import com.fmt.mvi.learn.travel.activity.TravelDetailActivity
import com.fmt.mvi.learn.travel.model.Params
import com.fmt.mvi.learn.travel.state.TravelTabViewState
import com.fmt.mvi.learn.travel.viewmodel.TravelTabViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class TravelTabFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
    OnItemClickListener {

    private lateinit var mBinding: FragmentCommonListBinding
    private lateinit var mRequestParams: Params
    private val mViewModel: TravelTabViewModel by viewModels()
    private val mAdapter by lazy { TravelTabAdapter() }
    private var mRequestUrl = ""
    private var mGroupChannelCode = ""
    private var mCurrentPage = 0

    companion object {
        const val URL = "url"
        const val PARAMS = "params"
        const val GROUP_CHANNEL_CODE = "groupChannelCode"
        const val TYPE = "type"
        fun newInstance(arguments: Bundle): TravelTabFragment {
            val travelTabFragment = TravelTabFragment()
            travelTabFragment.arguments = arguments
            return travelTabFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentCommonListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRequestParams()
        registerUIStateCallback()
    }

    private fun initView() {
        mCurrentPage = 0
        mBinding.swipeRefreshLayout.setColorSchemeResources(R.color.selected_tint_color)
        mBinding.swipeRefreshLayout.setOnRefreshListener(this)
        mBinding.rcvVideo.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter.loadMoreModule.setOnLoadMoreListener(this)
        mAdapter.setOnItemClickListener(this)
        mBinding.rcvVideo.adapter = mAdapter
    }

    private fun initRequestParams() {
        arguments?.let { bundle ->
            mRequestUrl = bundle.getString(URL).toString()
            mRequestParams = fromJson(bundle.getString(PARAMS).toString())
            mGroupChannelCode = bundle.getString(GROUP_CHANNEL_CODE).toString()
            mRequestParams.groupChannelCode = bundle.getString(GROUP_CHANNEL_CODE).toString()
            mRequestParams.type = bundle.getInt(TYPE)
        }
    }

    private fun registerUIStateCallback() {
        lifecycleScope.launchWhenResumed {
            mViewModel.state.flowWithLifecycle(lifecycle).distinctUntilChanged()
                .collect { viewState ->
                    when (viewState) {
                        is TravelTabViewState.LoadingState -> {
                            mBinding.swipeRefreshLayout.isRefreshing = true
                        }
                        is TravelTabViewState.RefreshSuccess -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            mAdapter.setList(viewState.travelList)
                        }
                        is TravelTabViewState.LoadMoreSuccess -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            mAdapter.addData(viewState.travelList)
                            if (viewState.travelList.isEmpty()) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                            }
                        }
                        is TravelTabViewState.LoadError -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            Toast.makeText(requireContext(), viewState.errorMsg, Toast.LENGTH_LONG)
                                .show()
                            mAdapter.loadMoreModule.loadMoreFail()
                        }
                    }
                }
        }
    }

    override fun loadPageData() {
        mRequestParams.pagePara.pageIndex = mCurrentPage
        mViewModel.dispatch(TravelTabViewAction.Refresh(mRequestUrl, mRequestParams))
    }

    override fun onRefresh() {
        mCurrentPage = 0
        mRequestParams.pagePara.pageIndex = mCurrentPage
        mViewModel.dispatch(TravelTabViewAction.Refresh(mRequestUrl, mRequestParams))
    }

    override fun onLoadMore() {
        ++mCurrentPage
        mRequestParams.pagePara.pageIndex = mCurrentPage
        mViewModel.dispatch(TravelTabViewAction.LoadMore(mRequestUrl, mRequestParams))
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val title = mAdapter.data[position].article.articleTitle
        val h5Url = mAdapter.data[position].article.urls.find {
            it.h5Url != null
        }?.h5Url
        h5Url?.let {
            TravelDetailActivity.start(requireContext(), title, h5Url)
        }
    }
}