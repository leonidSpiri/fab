package spiridonov.fab

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import spiridonov.fab.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val fromBottom by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom) }
    private val toBottom by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_to_bottom) }

    private var isAllFabsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createFab()
    }

    private fun createFab() = with(binding) {
        floatingActionButton.setOnClickListener {

            if (!isAllFabsVisible) {
                showIn(historyFab)
                showIn(historyActionText)
                showIn(searchFab)
                showIn(searchActionText)
                showIn(homeFab)
                showIn(homeActionText)
                showIn(settingsFab)
                showIn(settingsActionText)
                floatingActionButton.extend()
            } else {
                showOut(historyFab)
                showOut(historyActionText)
                showOut(searchFab)
                showOut(searchActionText)
                showOut(homeFab)
                showOut(homeActionText)
                showOut(settingsFab)
                showOut(settingsActionText)
                floatingActionButton.shrink()
            }
            isAllFabsVisible = !isAllFabsVisible
        }
    }

    private fun showIn(v: View) {
        v.visibility = View.VISIBLE
        v.startAnimation(fromBottom)
    }

    private fun showOut(v: View) {
        v.visibility = View.GONE
        v.startAnimation(toBottom)
    }
}