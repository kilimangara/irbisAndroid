package com.nikitazlain.swipeactivity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View


abstract class SlidingActivity : AppCompatActivity() {

    private lateinit var root : View

    private var startX = 0f

    private var startY = 0f

    private var isSliding = false

    private val SLIDE_THRESHOLD = 10

    private lateinit var size : Point

    private lateinit var windowScrim : ColorDrawable

    private var listener:SlidingListener? = null

    interface SlidingListener{

        fun onSlidingStarted()

        fun onSlidingFisinhed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        size = Point().apply { windowManager.defaultDisplay.getSize(this)}
        windowScrim = ColorDrawable(Color.argb(0xE0, 0, 0, 0))
        windowScrim.alpha = 255
        window.setBackgroundDrawable(windowScrim)
    }

    fun setSlidingListener(listener:SlidingListener){
        this.listener = listener
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        root = getRootView()
    }

    abstract fun getRootView() : View

    protected fun setRootView(rootView:View){
        root = rootView
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var handled = false

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                startX =ev.x
                startY = ev.y
            }

            MotionEvent.ACTION_MOVE -> {
                if(canSlideDown() && isSlidingDown(startX, startY, ev) || isSliding){
                    if(!isSliding){
                        isSliding = true
                        onSlidingStarted()
                        ev.action = MotionEvent.ACTION_CANCEL
                        super.dispatchTouchEvent(ev)
                    }
                    root.y = (ev.y - startY).coerceAtLeast(0f)
                    updateScrim()
                    handled = true
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if(isSliding){
                    isSliding =false
                    onSlidingFinished()
                    handled =true
                    if(shouldClose(ev.y -startY)){
                        closeAndDissmiss()
                    } else {
                        root.y = 0f
                    }
                }
                startX = 0f
                startY = 0f
            }
        }
        return if(handled) true else super.dispatchTouchEvent(ev)
    }

    private fun onSlidingFinished(){
        if(listener !=null){
            this.listener!!.onSlidingFisinhed()
        }
    }

    private fun onSlidingStarted(){
        if(listener!=null){
            this.listener!!.onSlidingStarted()
        }
    }

    private fun isSlidingDown(startX :Float, startY:Float, ev:MotionEvent):Boolean{
        val deltaX = Math.abs(startX - ev.x)
        if(deltaX > SLIDE_THRESHOLD) return false;
        val deltaY = ev.y - startY
        return deltaY >SLIDE_THRESHOLD
    }

    abstract fun canSlideDown(): Boolean

    private fun shouldClose(delta: Float): Boolean {
        return delta > size.y / 3
    }

    private fun closeAndDissmiss(){
        val start = root.y
        val finish = size.y.toFloat()
        val positionChanger = ObjectAnimator.ofFloat(root, "y", start, finish)
        positionChanger.duration = 1000
        positionChanger.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                root.y = size.y.toFloat()
                updateScrim()
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
                updateScrim()
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        positionChanger.addUpdateListener{
            updateScrim()
        }

        positionChanger.start()
    }

    private fun updateScrim(){
        val progress = root.y / size.y
        val alpha = (progress*255).toInt()
        windowScrim.alpha = 255 -alpha
    }
}