package com.fmt.mvi.learn.travel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.fmt.mvi.learn.databinding.ActivityTravelDetailBinding
import com.just.agentweb.AgentWeb

class TravelDetailActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTravelDetailBinding
    private lateinit var mAgentWeb: AgentWeb

    companion object {
        const val KEY_TITLE = "key_title"
        const val KEY_URL = "key_url"
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, TravelDetailActivity::class.java)
            intent.putExtra(KEY_TITLE, title)
            intent.putExtra(KEY_URL, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTravelDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initWindow()
        initView()
    }

    private fun initWindow() {
        WindowCompat.getInsetsController(window, findViewById(android.R.id.content)).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun initView() {
        mBinding.toolbar.apply {
            title = intent.getStringExtra(KEY_TITLE)
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.llContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(intent.getStringExtra(KEY_URL))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}