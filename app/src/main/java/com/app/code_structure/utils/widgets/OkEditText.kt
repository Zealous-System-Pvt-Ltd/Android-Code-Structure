package com.app.code_structure.utils.widgets


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import com.app.code_structure.utils.AppSettings


class OkEditText : AppCompatEditText {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int)
            : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (this.tag != null) {
            Log.e("OkEditText", " == " + this.textSize)
        }
    }

    init {

        val newsize =  AppSettings.getFontSizeByValue(this.textSize)
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, newsize)
    }





}