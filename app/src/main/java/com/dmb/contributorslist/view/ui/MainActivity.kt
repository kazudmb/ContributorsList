package com.dmb.contributorslist.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmb.contributorslist.R
import com.dmb.contributorslist.service.model.Project

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment() //一覧のFragment
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, TAG_OF_PROJECT_LIST_FRAGMENT)
                .commit()
        }
    }

    fun show(project: Project) {
        val projectFragment = ProjectFragment.forProject(project.name) //詳細のFragment
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("project")
            .replace(R.id.fragment_container, projectFragment, null)
            .commit()
    }
}