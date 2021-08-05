package com.dmb.contributorslist.view.callback

import com.dmb.contributorslist.service.model.Contributors

/**
 * @link onClick(Contributors contributors) 詳細画面に移動
 */
interface ContributorsClickCallback {
    fun onClick(contributors: Contributors)
}