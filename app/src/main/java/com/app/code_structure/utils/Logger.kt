package com.app.code_structure.utils

import java.io.File
import java.io.RandomAccessFile

object Logger {

    /* Logging and Console */
    var DO_LOGGING = true
    var DO_SOP = true

    fun debug(title: String, mesg: String) {
        var logFile: File? = null
        var raf: RandomAccessFile? = null

        try {

            if (DO_LOGGING) {
                android.util.Log.d(title,mesg)
            }

        } catch (exception: Exception) {
            println("LogPrinter :: Debug :: " + exception.message)
            exception.printStackTrace()

        } finally {
            if (raf != null) {
                try {
                    raf.close()
                } catch (e: Exception) {
                }

            }
        }

        raf = null
        logFile = null
    }

    fun error(title: String, mesg: String) {
        var logFile: File? = null
        var raf: RandomAccessFile? = null

        try {
            if (DO_LOGGING) {
                error(title, mesg)
            }
        } catch (exception: Exception) {
        } finally {
            if (raf != null) {
                try {
                    raf.close()
                } catch (e: Exception) {
                }

            }
        }

        raf = null
        logFile = null
    }

    fun error(title: String, mException: Exception) {
        mException.printStackTrace()
        var logFile: File? = null
        var raf: RandomAccessFile? = null
        val mStackTraceElement: Array<StackTraceElement>
        var str: String? = String()
        try {
            if (DO_LOGGING) {

                error(title, mException.toString())

            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (raf != null) {
                try {
                    raf.close()
                } catch (e: Exception) {
                }

            }
        }

        raf = null
        logFile = null
        str = null

    }

    fun print(mesg: String) {
        if (DO_SOP) {
            println(mesg)
        }
    }

    fun print(title: String, mesg: String) {
        if (DO_SOP) {
            println("$title :: $mesg")
        }
    }

    fun print(title: String, e: Exception) {
        if (DO_SOP) {
            println(
                "=========================" + title + "========================="
            )
            e.printStackTrace()
        }
    }

    fun print(e: Exception) {
        if (DO_SOP) {
            e.printStackTrace()
        }
    }

    fun htmlEncode(str: String): String {
        return str.replace(">".toRegex(), "&lt;").replace("<".toRegex(), "&gt;")
            .replace("&".toRegex(), "&amp;").replace("\"".toRegex(), "&quot;")
            .replace("'".toRegex(), "&#039;")
    }
}