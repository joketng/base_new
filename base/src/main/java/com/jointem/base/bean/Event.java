package com.jointem.base.bean;

/**
 * @author Kevin.Li
 * @Description: 组件件通讯载体
 * @date 2015-10-20 上午11:20:23
 */
public class Event {
    private String eventFlag;// 事件标识
    private Object data;// 携带的数据

    public Event() {
    }

    /**
     * @param eventFlag 事件标识<必填>
     * @param data      事件所携带数据<可为null>
     */
    public Event(String eventFlag, Object data) {
        this.eventFlag = eventFlag;
        this.data = data;
    }

    public String getEventFlag() {
        return eventFlag;
    }

    public void setEventFlag(String eventFlag) {
        this.eventFlag = eventFlag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
