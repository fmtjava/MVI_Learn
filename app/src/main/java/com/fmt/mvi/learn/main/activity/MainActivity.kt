package com.fmt.mvi.learn.main.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.jzvd.Jzvd
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.fmt.mvi.learn.R
import com.fmt.mvi.learn.databinding.ActivityMainBinding
import com.fmt.mvi.learn.main.action.MainViewAction
import com.fmt.mvi.learn.main.factory.MainFragmentFactory
import com.fmt.mvi.learn.main.state.MainViewState
import com.fmt.mvi.learn.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_COUNT = 2
    }

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initWindow()
        initView()
        registerUIStateCallback()
        mViewModel.dispatch(MainViewAction.GetCurrentTabIndex)
    }

    private fun initWindow() {
        //https://blog.csdn.net/StjunF/article/details/121840122
        //通过WindowInsetsControllerCompat可以简化状态栏、导航栏、键盘控制
        WindowCompat.getInsetsController(window, findViewById(android.R.id.content)).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun initView() {
        mBinding.viewPager.isUserInputEnabled = false
        mBinding.viewPager.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount(): Int = FRAGMENT_COUNT

            override fun createFragment(position: Int): Fragment =
                MainFragmentFactory.create(position)
        }
        mBinding.bottomNavigationView.setActiveColor(R.color.selected_tint_color)
            .setInActiveColor(R.color.default_tint_color)
            .addItem(BottomNavigationItem(R.drawable.ic_travel, getString(R.string.travel)))
            .addItem(BottomNavigationItem(R.drawable.ic_video, getString(R.string.video)))
            .setTabSelectedListener(object : BottomNavigationBar.SimpleOnTabSelectedListener() {
                override fun onTabSelected(position: Int) {
                    Jzvd.releaseAllVideos()
                    mBinding.viewPager.currentItem = position
                    mViewModel.dispatch(MainViewAction.SaveCurrentTabIndex(position))
                }
            })
            .initialise()
    }

    private fun registerUIStateCallback() {
        lifecycleScope.launchWhenResumed {
            mViewModel.state.flowWithLifecycle(lifecycle).collect { viewState ->
                when (viewState) {
                    is MainViewState.InitialDefaultTab -> {
                        mBinding.bottomNavigationView.selectTab(viewState.index)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
        MainFragmentFactory.clear()
    }

}