package com.fmt.mvi.learn.video.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.fmt.mvi.learn.R
import com.fmt.mvi.learn.commom.ui.BaseFragment
import com.fmt.mvi.learn.commom.utils.ShareUtils
import com.fmt.mvi.learn.databinding.FragmentVideoBinding
import com.fmt.mvi.learn.video.action.VideoListViewAction
import com.fmt.mvi.learn.video.adapter.VideoListAdapter
import com.fmt.mvi.learn.video.state.VideoListViewState
import com.fmt.mvi.learn.video.viewmodel.VideoViewModel
import com.zy.multistatepage.state.EmptyState
import com.zy.multistatepage.state.ErrorState
import com.zy.multistatepage.state.LoadingState
import com.zy.multistatepage.state.SuccessState
import kotlinx.coroutines.flow.distinctUntilChanged

class VideoFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
    OnItemChildClickListener {

    private lateinit var mBinding: FragmentVideoBinding
    private val mAdapter by lazy { VideoListAdapter() }
    private val mViewModel by viewModels<VideoViewModel>()
    private var mCurrentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentVideoBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerUIStateCallback()
    }

    private fun initView() {
        mCurrentPage = 0
        mBinding.swipeRefreshLayout.setColorSchemeResources(R.color.selected_tint_color)
        mBinding.swipeRefreshLayout.setOnRefreshListener(this)
        mBinding.rcvVideo.layoutManager = LinearLayoutManager(requireContext())
        mAdapter.addChildClickViewIds(R.id.iv_share)
        mAdapter.setOnItemChildClickListener(this)
        mAdapter.loadMoreModule.setOnLoadMoreListener(this)
        mBinding.rcvVideo.adapter = mAdapter
    }

    private fun registerUIStateCallback() {
        lifecycleScope.launchWhenResumed {
            mViewModel.state.flowWithLifecycle(lifecycle).distinctUntilChanged().collect { state ->
                mBinding.swipeRefreshLayout.isRefreshing = false
                when (state) {
                    is VideoListViewState.LoadingState -> mBinding.multiStateContainer.show<LoadingState>()
                    is VideoListViewState.RefreshSuccessState -> {
                        mAdapter.setList(state.videoList)
                        if (state.videoList.isEmpty())
                            mBinding.multiStateContainer.show<EmptyState>()
                        else
                            mBinding.multiStateContainer.show<SuccessState>()
                    }
                    is VideoListViewState.LoadMoreSuccessState -> {
                        mAdapter.addData(state.videoList)
                        if (state.videoList.isEmpty()) {
                            mAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            mAdapter.loadMoreModule.loadMoreComplete()
                        }
                    }
                    is VideoListViewState.LoadErrorState -> {
                        if (mAdapter.itemCount == 0) {
                            mBinding.multiStateContainer.show<ErrorState> {
                                it.retry {
                                    retry()
                                }
                            }
                        }
                        onLoadError(state.errorMsg)
                    }
                    is VideoListViewState.LoadMoreErrorState -> {
                        onLoadError(state.errorMsg)
                    }
                }
            }
        }
    }

    private fun onLoadError(errorMsg: String) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
        mAdapter.loadMoreModule.loadMoreFail()
    }

    override fun loadPageData() {
        mViewModel.dispatch(VideoListViewAction.Refresh)
    }

    override fun onRefresh() {
        mCurrentPage = 0
        mViewModel.dispatch(VideoListViewAction.Refresh)
    }

    override fun onLoadMore() {
        mViewModel.dispatch(VideoListViewAction.LoadMore(++mCurrentPage))
    }

    private fun retry() {
        mCurrentPage = 0
        mViewModel.dispatch(VideoListViewAction.Retry)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (view.id == R.id.iv_share) {
            ShareUtils.share(
                requireContext(),
                mAdapter.data[position].title,
                mAdapter.data[position].playUrl
            )
        }
    }

}