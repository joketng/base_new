package com.joketng.base.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/7/6
 */
object MonthObject {
    var sm = SimpleDateFormat("yyyy-MM-dd")

    fun getFirstDayOfMonth():String{
        val calstr = Calendar.getInstance(Locale.CHINA)
        //本月
        calstr.add(Calendar.MONTH, 0)
        //设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH, 1)
        val first = sm.format(calstr.time)
        return first
    }

    fun getLastDayOfMonth():String{
        val calstr = Calendar.getInstance()
        //本月
        calstr.add(Calendar.MONTH, 0)
        //设置当月为最后一天
        calstr.set(Calendar.DAY_OF_MONTH, calstr.getActualMaximum(Calendar.DAY_OF_MONTH))
        val last = sm.format(calstr.time)
        return last
    }

    fun getDayOfMonth(string:String): String{
        val calstr = Calendar.getInstance()
        calstr.time = sm.parse(string)
        val day = calstr.get(Calendar.DAY_OF_MONTH)
        return day.toString()
    }

    fun getLastMonth(date :Date):Date{
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, -1)
        return cal.time
    }

    fun getNextMonth(date: Date):Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, 1)
        return cal.time
    }

    fun getMinMonthDate(date: Date):Date{
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
        return cal.time
    }

    fun getMaxMonthDate(date:Date):Date{
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        return cal.time
    }

    fun getDayOfWeek(time:String):String{
        val calstr = Calendar.getInstance(Locale.CHINA)
        calstr.time = sm.parse(time)
        val index = calstr.get(Calendar.DAY_OF_WEEK)
        var week = ""
        when (index) {
            1 -> {
                week = "星期天"
            }
            2 -> {
                week = "星期一"

            }
            3 -> {
                week = "星期二"

            }
            4 -> {
                week = "星期三"

            }
            5 -> {
                week = "星期四"

            }
            6 -> {
                week = "星期五"

            }
            7 -> {
                week = "星期六"

            }
            else -> {
            }

        }
        return week

    }
}