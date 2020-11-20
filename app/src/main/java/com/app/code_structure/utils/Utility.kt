package com.app.code_structure.utils


import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.app.code_structure.BuildConfig
import com.app.code_structure.R
import com.app.code_structure.utils.widgets.MyProgressDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object Utility {

    fun log(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg)
        }
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun getStringFromObject(`object`: Any): String {

        val gson = Gson()
        return gson.toJson(`object`)
    }

    internal var pdlg: MyProgressDialog? = null

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun showLoading(c: Context) {
        if (pdlg == null) {
            pdlg = MyProgressDialog(c)
            pdlg!!.setCancelable(false)

            try {
                pdlg!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun preventStartingZero(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (editText.text.toString().trim().startsWith("0")) { // Not allowed
                    editText.setText("")
                }
            }

            override fun afterTextChanged(arg0: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        })
    }

    fun closeLoading() {
        if (pdlg != null && pdlg!!.isShowing) {
            pdlg!!.dismiss()
            pdlg = null
        }
    }

    fun openDatePicker(context: Context, editText: EditText) {
        var cal = Calendar.getInstance()
        val currentYear = 0
        val currentMonth = 0
        val currentDay = 0
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                var textDate = sdf.format(cal.time)
                editText.setText(textDate)

            }

        var datePicker = DatePickerDialog(
            context, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.maxDate = System.currentTimeMillis() - 1000
        datePicker.setTitle("")
        datePicker.show()

    }

    fun openDatePicker18Plus(context: Context, editText: EditText) {
        var cal = Calendar.getInstance()
        val currentYear = 0
        val currentMonth = 0
        val currentDay = 0
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                var textDate = sdf.format(cal.time)
                editText.setText(textDate)

            }


        var datePicker = DatePickerDialog(
            context, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),

            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate = System.currentTimeMillis() - 568025136000L
        datePicker.setTitle("")
        datePicker.show()

    }

    fun showErrorToast(ct: Context, message: String) {
        showToast(ct, message, true)
    }

    fun showSuccessToast(ct: Context, message: String) {
        showToast(ct, message, false)
    }

    fun parseDate(toDateFormat: String, dateString: String): String {
        return parseDate(null, toDateFormat, dateString)
    }

    fun parseFinalDate(toDateFormat: String, dateString: String): String {
        return parseFinalDate(null, toDateFormat, dateString)
    }

    fun parseDate(dateFormat: String?, toDateFormat: String, dateString: String): String {
        val formatter: SimpleDateFormat
        try {
            if (dateFormat == null) {
                formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            } else {
                formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            }
            val date = formatter.parse(dateString)
            return SimpleDateFormat(toDateFormat).format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun parseFinalDate(dateFormat: String?, toDateFormat: String, dateString: String): String {
        val formatter: SimpleDateFormat
        try {
            if (dateFormat == null) {
                formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            } else {
                formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            }
            val date = formatter.parse(dateString)
            return SimpleDateFormat(toDateFormat).format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getFormatedDateTime(strWriteFormat: String, dateStr: String): String {

        var formattedDate = dateStr

        val readFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val writeFormat = SimpleDateFormat(strWriteFormat, Locale.getDefault())

        var date: Date? = null

        try {
            date = readFormat.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            formattedDate = writeFormat.format(date)
        }

        return formattedDate
    }

    private fun showToast(ct: Context, message: String, isError: Boolean) {
        try {

            if (!message.equals("Under development", true)) {

                val inflater = LayoutInflater.from(ct)
                val layout = inflater.inflate(
                    R.layout.layout_toast_message,
                    null
                )
                val textV = layout.findViewById(R.id.lbl_toast) as TextView
                if (isError) {
                    textV.setTextColor(ct.resources.getColor(R.color.white))
                    textV.setBackgroundResource(R.drawable.toast_error_background)
                }
                textV.text = message
                val toast = Toast(ct)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private val EMAIL =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"


    fun isAValidEmail(input: CharSequence?): Boolean {
        if (input == null)
            return false
        val pattern = Pattern.compile(EMAIL)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    /**
     * Convert String into Object using @[Gson]
     *
     * @param mListStr to convert into string
     * @return object from string with gson
     */

    fun getObjectListFromString(mListStr: String): Any? {
        var obj: Any? = null
        val type = object : TypeToken<Any>() {

        }.type
        obj = Gson().fromJson<Any>(mListStr, type)
        return obj
    }

    fun hideSoftInput(activity: Activity) {
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getConfirmationDialog(
        context: Context,
        title: String,
        msg: String,
        btnPositiveTitle: String,
        btnNegativeTitle: String,
        onPositiveBtnClick: DialogInterface.OnClickListener,
        onNegativeBtnClick: DialogInterface.OnClickListener
    ): AlertDialog.Builder {

        return getStyledConfirmationDialog(
            context,
            0,
            title,
            msg,
            btnPositiveTitle,
            btnNegativeTitle,
            onPositiveBtnClick,
            onNegativeBtnClick
        ).setCancelable(false)
    }

    fun <T> getObjectFromString(stringJSON: String, classType: Class<T>): T {
        return Gson().fromJson(stringJSON, classType)
        /*  Object obj = null;
        Type type = new TypeToken<Object>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);
        return obj;*/
    }

    fun getStyledConfirmationDialog(
        context: Context,
        styleId: Int,
        title: String,
        msg: String,
        btnPositiveTitle: String,
        btnNegativeTitle: String,
        onPositiveBtnClick: DialogInterface.OnClickListener?,
        onNegativeBtnClick: DialogInterface.OnClickListener?
    ): AlertDialog.Builder {

        val builder: AlertDialog.Builder
        if (styleId == 0) {
            builder = AlertDialog.Builder(context)
        } else {
            builder = AlertDialog.Builder(context, styleId)
        }
        builder.setCancelable(true)
        builder.setTitle(title)
        if (!msg.isEmpty()) {
            builder.setMessage(msg)
        }
        if (!btnNegativeTitle.equals("", ignoreCase = true)) {
            builder.setNegativeButton(btnNegativeTitle) { dialog, which ->
                if (onNegativeBtnClick != null) {
                    onNegativeBtnClick.onClick(dialog, 0)
                } else {
                    dialog.dismiss()
                }
            }
        }
        if (!btnPositiveTitle.equals("", ignoreCase = true)) {
            builder.setPositiveButton(btnPositiveTitle) { dialog, which ->
                if (onPositiveBtnClick != null) {
                    onPositiveBtnClick.onClick(dialog, 0)
                } else {
                    dialog.dismiss()
                }
            }
        }
        return builder
    }


    fun getArrayAplhabet(): List<String> {

        return listOf(
            "", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )

    }


}