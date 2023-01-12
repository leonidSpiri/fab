package spiridonov.fab

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import spiridonov.fab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isAllFabsVisible = false

    private val fabsPairsList = mutableListOf<Pair<View, View>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initFabs()
        fabsListener()
        btnClickListener()
    }

    private fun btnClickListener() =
        binding.btnResetMenu.setOnClickListener {
            initFabs()
        }

    private fun initFabs() {
        fabsPairsList.clear()
        with(binding) {
            fabsPairsList.add(Pair(historyFab, historyActionText))
            fabsPairsList.add(Pair(searchFab, searchActionText))
            fabsPairsList.add(Pair(homeFab, homeActionText))
            fabsPairsList.add(Pair(settingsFab, settingsActionText))
        }
        fabsPairsList.forEach { pair ->
            setupSwipeListener(pair.first, pair.second)
            if (isAllFabsVisible) {
                animShowOut(pair.first)
                animShowOut(pair.second)
            }
            pair.first.visibility = View.GONE
            pair.second.visibility = View.GONE
        }
        isAllFabsVisible = false
    }

    private fun fabsListener() = with(binding) {
        floatingActionButton.setOnClickListener {
            if (!isAllFabsVisible)
                fabsPairsList.forEach { pair ->
                    animShowIn(pair.first)
                    animShowIn(pair.second)
                }
            else
                fabsPairsList.forEach { pair ->
                    println(fabsPairsList.size)
                    print(((pair.second) as TextView).text)
                    animShowOut(pair.first)
                    animShowOut(pair.second)
                }
            isAllFabsVisible = !isAllFabsVisible
        }
    }

    private fun animShowIn(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 0f
        v.translationY = v.height.toFloat()
        v.animate()
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
            })
            .translationY(0F)
            .alpha(1f)
            .start()
    }

    private fun animShowOut(v: View) {
        v.alpha = 1f
        v.translationY = 0F
        v.animate()
            .setDuration(200)
            .translationY(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.GONE
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }

    private fun setupSwipeListener(fab: View, textView: View) =
        fab.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                animShowOut(fab)
                animShowOut(textView)
                fabsPairsList.remove(Pair(fab, textView))
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                animShowOut(fab)
                animShowOut(textView)
                fabsPairsList.remove(Pair(fab, textView))
            }
        })
}