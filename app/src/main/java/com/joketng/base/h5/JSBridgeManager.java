package com.joketng.base.h5;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import com.joketng.base.h5.ant.JCallBack;
import com.joketng.base.h5.ant.JContext;
import com.joketng.base.h5.ant.JView;
import com.joketng.base.h5.ant.JWebView;
import com.joketng.base.h5.ant.Param;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */
public class JSBridgeManager {
    private static Map<String, HashMap<String, Method>> exposedClasses = new HashMap<>();
    public static String METHOD_NAME, CLASS_NAME;
    public static void register(String className, Class clazz) {
        if (!exposedClasses.containsKey(className)) {
            try {
                exposedClasses.put(className, getAllMethod(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static HashMap<String, Method> getAllMethod(Class injectClazz) {
        HashMap<String, Method> mapMethods = new HashMap<>();
        Method[] methods = injectClazz.getMethods();
        String name;
        for (Method method : methods) {
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (name = method.getName()) == null) {
                continue;
            }
            mapMethods.put(name, method);
        }
        return mapMethods;
    }

    static boolean doNative(Context activity, View contentView, WebView webView, JSONObject jsonObj) {
        CLASS_NAME = jsonObj.optString("className");
        METHOD_NAME = jsonObj.optString("methodName");
        if (exposedClasses.containsKey(CLASS_NAME)) {
            HashMap<String, Method> methodHashMap = exposedClasses.get(CLASS_NAME);
            if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(METHOD_NAME)) {
                Method method = methodHashMap.get(METHOD_NAME);
                if (method != null) {
                    try {
                        List<Object> objects = new ArrayList<>();
                        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                        Object object = null;
                        for (Annotation[] annotations : parameterAnnotations) {
                            Annotation annotation = annotations[0];
                            if (annotation instanceof Param) {
                                Param annotationParam = (Param) annotation;
                                String key = annotationParam.value();
                                JSONObject jsonParam = jsonObj.getJSONObject("params");
                                object = jsonParam.opt(key);
                            } else if (annotation instanceof JCallBack) {
                                object = new CallBack4H5(jsonObj.optString("success"), jsonObj.optString("failed"), webView);
                            } else if (annotation instanceof JContext) {
                                object = activity;
                            } else if (annotation instanceof JWebView) {
                                object = webView;
                            } else if (annotation instanceof JView) {
                                object = contentView;
                            }
                            objects.add(object);
                        }
                        method.invoke(null, objects.toArray());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

}
