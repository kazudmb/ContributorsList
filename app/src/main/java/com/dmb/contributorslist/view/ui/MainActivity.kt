package com.dmb.contributorslist.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmb.contributorslist.R
import com.dmb.contributorslist.service.model.Contributors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ContributorsListFragment() //一覧のFragment
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, TAG_OF_CONTRIBUTORS_LIST_FRAGMENT)
                .commit()
        }
    }

    fun show(contributors: Contributors) {
        val userFragment = UserFragment.forUser(contributors.login) //詳細のFragment
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("user")
            .replace(R.id.fragment_container, userFragment, null)
            .commit()
    }
}