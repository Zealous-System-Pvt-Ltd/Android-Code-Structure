package com.app.code_structure.utils

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.app.code_structure.utils.preference.PreferenceKeys
import com.app.code_structure.utils.preference.SharedPreferenceManager

object AppSettings {

     var currentfontSizeType = ""

    fun getFontSizeByValue(currentsize:Float):Float{

        if(currentfontSizeType.equals(SharedPreferenceManager.getString(PreferenceKeys.fontsize, AppConstants.keyFontsizeNORMAL))) {
            setFontSizeTypeAsGlobal()
        }

        return if(currentfontSizeType.equals(AppConstants.keyFontsizeSMALL,true)){
            (currentsize * AppConstants.FontsizeSMALL)
        }else if(currentfontSizeType.equals(AppConstants.keyFontsizeNORMAL,true)){
            currentsize//(currentsize * (0.10f))
        }else if(currentfontSizeType.equals(AppConstants.keyFontsizeLARGE,true)){
            (currentsize * AppConstants.FontsizeLARGE)
        }else{
            currentsize
        }

    }

    fun getFontSizeByValue(oldkey:String, currentoldsize:Float):Float{

        if(currentfontSizeType.isEmpty()) {
            setFontSizeTypeAsGlobal()
        }
        var currentNewsize = currentoldsize


        if (!oldkey.equals(currentfontSizeType,true)) {
            if (oldkey.equals(AppConstants.keyFontsizeLARGE,true)) {
                currentNewsize = currentoldsize - (currentoldsize * AppConstants.fontdifference)
            } else if (oldkey.equals(AppConstants.keyFontsizeSMALL,true)) {
                currentNewsize = currentoldsize + (currentoldsize * AppConstants.fontdifference)
            }
        }

        return if(currentfontSizeType.equals(AppConstants.keyFontsizeSMALL,true)){
            (currentNewsize * AppConstants.FontsizeSMALL)
        }else if(currentfontSizeType.equals(AppConstants.keyFontsizeNORMAL,true)){
            currentNewsize//(currentsize * (0.10f))
        }else if(currentfontSizeType.equals(AppConstants.keyFontsizeLARGE,true)){
            (currentNewsize * AppConstants.FontsizeLARGE)
        }else{
            currentNewsize
        }

    }

    fun getFontSizeByValue(currentsize:Float, percentage:Float):Float{

        if(currentfontSizeType.isEmpty()) {
            setFontSizeTypeAsGlobal()
        }

        return if(currentfontSizeType.equals(AppConstants.keyFontsizeSMALL,true)){
            (currentsize * (percentage))
        }else if(currentfontSizeType.equals(AppConstants.keyFontsizeNORMAL,true)){
            currentsize//(currentsize * (0.10f))
        }else if(currentfontSizeType.equals(AppConstants.keyFontsizeLARGE,true)){
            (currentsize * (percentage))
        }else{
            currentsize
        }

    }


    fun setFontSizeTypeAsGlobal(){
        currentfontSizeType =   SharedPreferenceManager.getString(PreferenceKeys.fontsize, AppConstants.keyFontsizeNORMAL)
    }


    fun setFontSizeOfWholeView(oldfonsize:String, v: View){
        try {

            when (v) {
                is ViewGroup ->{
                    for (i in 0 until v.childCount) {
                        val child = v.getChildAt(i)
                        setFontSizeOfWholeView(oldfonsize, child)
                    }
                }is TextView ->{
                    v.setTextSize(TypedValue.COMPLEX_UNIT_PX,AppSettings.getFontSizeByValue(oldfonsize,v.textSize))

                }is EditText ->{
                    v.setTextSize(TypedValue.COMPLEX_UNIT_PX,AppSettings.getFontSizeByValue(oldfonsize,v.textSize))

                }is Button ->{
                    v.setTextSize(TypedValue.COMPLEX_UNIT_PX,AppSettings.getFontSizeByValue(oldfonsize,v.textSize))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



}