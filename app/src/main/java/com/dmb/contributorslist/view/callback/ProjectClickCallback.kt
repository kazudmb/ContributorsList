package com.dmb.contributorslist.view.callback

import com.dmb.contributorslist.service.model.Project

/**
 * @link onClick(Project project) 詳細画面に移動
 */
interface ProjectClickCallback {
    fun onClick(project: Project)
}