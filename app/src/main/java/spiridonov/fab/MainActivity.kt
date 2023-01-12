package spiridonov.fab

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import spiridonov.fab.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val fromBottom by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom) }
    private val toBottom by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_to_bottom) }

    private var isAllFabsVisible = false

    private val fabsPairsList = mutableListOf<Pair<View, View>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initFabs()
        createFab()
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
                showOut(pair.first)
                showOut(pair.second)
            }
        }
        isAllFabsVisible = false
    }

    private fun createFab() = with(binding) {
        floatingActionButton.setOnClickListener {
            if (!isAllFabsVisible)
                fabsPairsList.forEach { pair ->
                    showIn(pair.first)
                    showIn(pair.second)
                }
            else
                fabsPairsList.forEach { pair ->
                    showOut(pair.first)
                    showOut(pair.second)
                }
            isAllFabsVisible = !isAllFabsVisible
        }
    }

    private fun showIn(v: View) {
        v.visibility = View.VISIBLE
        v.startAnimation(fromBottom)
    }

    private fun showOut(v: View) {
        v.startAnimation(toBottom)
        v.visibility = View.GONE
    }

    private fun setupSwipeListener(fab: View, textView: View) {
        fab.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                showOut(fab)
                showOut(textView)
                fabsPairsList.remove(Pair(fab, textView))
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                showOut(fab)
                showOut(textView)
                fabsPairsList.remove(Pair(fab, textView))
            }
        })
    }
}