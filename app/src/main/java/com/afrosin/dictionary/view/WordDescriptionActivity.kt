package com.afrosin.dictionary.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityWordDescriptionBinding
import com.afrosin.utils.network.OnlineLiveData
import com.afrosin.utils.ui.AlertDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class WordDescriptionActivity : AppCompatActivity() {

    private val vb: ActivityWordDescriptionBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_description)

        setActionBarHomeButtonAsUp()

        vb.descriptionScreenSwipeRefreshLayout.setOnClickListener { startLoadingOrShowError() }

        setData()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(
            this@WordDescriptionActivity,
            {
                if (it) {
                    setData()
                } else {
                    AlertDialogFragment.newInstance(
                        getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline)
                    ).show(
                        supportFragmentManager,
                        DIALOG_FRAGMENT_TAG
                    )
                    stopRefreshAnimationIfNeeded()
                }
            }
        )

    }

    private fun stopRefreshAnimationIfNeeded() {
        if (vb.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            vb.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setData() {
        val bundle = intent.extras

        vb.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
        vb.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)

        val imageLink = bundle?.getString(URL_EXTRA)

        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(vb.descriptionImageview, imageLink)
//            userGlideToLoadImage(vb.descriptionImageview, imageLink)
        }
    }

    private fun usePicassoToLoadPhoto(imageview: ImageView, imageLink: String) {
        Picasso.with(applicationContext)
            .load("https:$imageLink")
            .placeholder(R.drawable.ic_no_image_vector)
            .fit()
            .centerCrop()
            .into(imageview, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageview.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }

    private fun setActionBarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG =
            "WORD_DESCRIPTION_ACTIVITY_DIALOG_FRAGMENT_TAG"

        private const val WORD_EXTRA = "WORD_DESCRIPTION_ACTIVITY_WORD_EXTRA"
        private const val DESCRIPTION_EXTRA = "WORD_DESCRIPTION_ACTIVITY_DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "WORD_DESCRIPTION_ACTIVITY_URL_EXTRA"

        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?
        ): Intent = Intent(context, WordDescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }
}