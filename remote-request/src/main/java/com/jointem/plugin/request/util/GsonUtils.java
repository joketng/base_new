package com.jointem.plugin.request.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by THC on 2016/1/12.
 */
public class GsonUtils {

	private static class SingleHolder {
		private static GsonUtils gsonUtils = null;
		private static Gson gson = null;

		public static GsonUtils getInstance() {
			if (null == gsonUtils) {
				gsonUtils = new GsonUtils();
			}
			return gsonUtils;
		}

		public static Gson getGSInstance() {
			if (null == gson) {
				GsonBuilder gb =new GsonBuilder();
				gb.disableHtmlEscaping();
				gson = gb.create();
			}
			return gson;
		}
	}

	private GsonUtils() {
	}

	public static GsonUtils getInstance() {
		return SingleHolder.getInstance();
	}

	public String toJsonString(Object object) {
		String gsonString = null;
		try {
			gsonString = SingleHolder.getGSInstance().toJson(object);
		} catch (Exception e) {
			Log.e("json", e.getMessage());
		}
		return gsonString;
	}

	public <T> T changeGsonToBean(String gsonString, Class<T> cls) {
		T t = null;
		try {
			t = SingleHolder.getGSInstance().fromJson(gsonString, cls);
		} catch (Exception e) {
			Log.e("json", e.getMessage());
		}
		return t;
	}

	private static class ListParameterizedType implements ParameterizedType {

		private Type type;

		private ListParameterizedType(Type type) {
			this.type = type;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return new Type[]{type};
		}

		@Override
		public Type getRawType() {
			return ArrayList.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}

	}

	public <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
		Type type = new ListParameterizedType(cls);
		List<T> list = SingleHolder.getGSInstance().fromJson(gsonString, type);
		return list;
	}

	public <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
		List<Map<String, T>> list = null;
		list = SingleHolder.getGSInstance().fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public <T> Map<String, T> changeGsonToMaps(String gsonString) {
		Map<String, T> map = null;
		map = SingleHolder.getGSInstance().fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}
}
