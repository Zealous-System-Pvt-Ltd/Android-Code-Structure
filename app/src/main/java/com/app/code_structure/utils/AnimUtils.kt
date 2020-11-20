package com.app.code_structure.utils

import android.app.Activity
import com.app.code_structure.R


object AnimUtils {


    fun activityenterAnim(context: Activity) {
        context.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    fun activityexitAnim(context: Activity) {
        context.overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_right)
    }

    fun activityBottomToUpAnim_NoChange(context: Activity) {
        context.overridePendingTransition(R.anim.no_change, R.anim.activity_slide_up)
    }


}
