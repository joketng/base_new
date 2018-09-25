package com.jointem.base;

import android.app.Activity;

import java.util.Stack;

public class ActivityHelper {
    private static ActivityHelper activityHelper;
    private static Stack<Activity> activities;

    private ActivityHelper() {
    }

    /**
     * @Description: 得到ActivityHelper实例
     * @Return_type: ActivityHelper
     * @author JW.Lee
     */
    public static ActivityHelper getInstance() {
        if (activityHelper == null) {
            activityHelper = new ActivityHelper();
        }
        return activityHelper;
    }

    /**
     * @Description: 退出当前Activity
     * @Return_type: void
     * @author JW.Lee
     */
    private void removeActivity(Activity activity) {
        if (activity != null && activities != null) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
            activities.remove(activity);
        }
    }

    /**
     * @Description: 获得当前栈顶Activity
     * @Return_type: Activity
     * @author JW.Lee
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activities != null && !activities.empty()) {
            activity = activities.lastElement();
        }
        return activity;
    }

    /**
     * @Description: 将当前Activity推入栈中
     * @Return_type: void
     * @author JW.Lee
     */
    public void pushActivity(Activity activity) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.push(activity);
    }

    /**
     * @Description: 退出栈顶元素
     * @Return_type: void
     * @author JW.Lee
     */
    public void popActivity() {
        if (activities != null) {
            Activity activity = activities.lastElement();
            if (!activity.isFinishing()) {
                activity.finish();
            }
            activities.pop();
        }
    }

    /**
     * @Description: 退出应用
     * @Return_type: void
     * @author JW.Lee
     */
    public void exitApplication() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            removeActivity(activity);
        }
        System.exit(0);
    }

    /**
     * @Description: 清掉其它activity
     * @Return_type: void
     * @author JW.Lee
     */
    public void clearOtherActivitys(Activity activity) {
        if (activity != null && activities != null) {
            for (Activity a : activities) {
                if (activity != a) {
                    a.finish();
                }
            }
        }
    }
}
