package com.dannark.turistando.home

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dannark.turistando.R
import com.dannark.turistando.domain.Post
import com.dannark.turistando.util.getTimeArrayDiff

@BindingAdapter("postsLikesFormatted")
fun TextView.postsLikesFormatted(item: Post){
    item.let {
        if(item.likes > 0){
            visibility = View.VISIBLE
            text = "${item.likes}"
        }
        else{
            visibility = View.GONE
        }
    }
}

@BindingAdapter("timePastFormatted")
fun TextView.timePastFormatted(time: Long){
    time.let{
        val diff = getTimeArrayDiff(it)

        val postTime = when{
            diff.sec < 60 -> String.format(resources.getString(R.string.posted_time_s), diff.sec)
            diff.min < 60 -> String.format(resources.getString(R.string.posted_time_m), diff.min)
            diff.hours < 24 -> String.format(resources.getString(R.string.posted_time_h), diff.hours)
            diff.days < 31 -> String.format(resources.getString(R.string.posted_time_d), diff.days)
            else -> "-"
        }

        text = postTime
    }
}

@BindingAdapter("imageUrl", "imageBitmap", "glideCenterCrop","glideCircularCrop", "glideRoundingRadius", requireAll = false)
fun ImageView.bindImage(imgUrl: String?, bitmap: Bitmap?, centerCrop: Boolean = false, circularCrop: Boolean = false, roundingRadius : Int = 0){
    val imgUri = imgUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
    val imgToLoad = bitmap?:imgUri

    imgToLoad?.let {
        val req = Glide.with(context)
                .load(imgToLoad)
                .apply(
                        RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image)
                )
        if (centerCrop) req.centerCrop()
        if (circularCrop) req.circleCrop()
        if (roundingRadius != 0) req.apply(RequestOptions().transform(CenterCrop(), RoundedCorners(roundingRadius)))
        req.into(this)
    }
}

@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsPadding(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, padding, _, _ ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        view.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )
    }
}

fun View.doOnApplyWindowInsets(
    block: (View, WindowInsets, InitialPadding, InitialMargin, Int) -> Unit
) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    val initialHeight = recordInitialHeightForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    setOnApplyWindowInsetsListener { v, insets ->
        block(v, insets, initialPadding, initialMargin, initialHeight)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
        ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

private fun recordInitialHeightForView(view: View): Int {
    return view.layoutParams.height
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}