package com.stoffe.gym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.stoffe.gym.Helpers.UtilsNew


class MainActivity : AppCompatActivity() {
    @OptIn(NavigationUiSaveStateControl::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UtilsNew.incrementAppOpenCount(this)
        val manager = ReviewManagerFactory.create(this)

        if(UtilsNew.isAppOpenedMoreThanThreeTimes(this) && !UtilsNew.getHasSeenCard(this)) {
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo = task.result

                    val flow = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener { _ ->
                        UtilsNew.setHasSeenCard(this)
                    }
                } else {
                    // There was some problem, log or handle the error code.
                    (task.exception as ReviewException).errorCode
                }
            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_host) as NavHostFragment?
        setupWithNavController(
            findViewById<BottomNavigationView>(R.id.bottom_navigation) ,
            navHostFragment!!.navController, false
        )


    }

}