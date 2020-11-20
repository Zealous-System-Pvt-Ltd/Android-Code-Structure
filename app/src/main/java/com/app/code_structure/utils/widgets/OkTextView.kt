package com.app.code_structure.utils.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.app.code_structure.utils.AppSettings


class OkTextView : AppCompatTextView {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }
    init {
        val newsize =  AppSettings.getFontSizeByValue(this.textSize)
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, newsize)
    }


}