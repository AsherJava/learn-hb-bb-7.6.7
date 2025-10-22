/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.holiday.manager.facade;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;

public class HolidayObj {
    private String key;
    private String title;
    private String date;
    private int days;
    private int event;
    private String info;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getEvent() {
        return this.event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static HolidayObj toHolidayObj(HolidayDefine source) {
        HolidayObj target = new HolidayObj();
        target.setKey(source.getKey());
        target.setDate(source.getDate());
        target.setTitle(source.getTitle());
        target.setEvent(source.getEvent());
        target.setDays(source.getDays());
        target.setInfo(source.getInfo());
        return target;
    }

    public static HolidayDefine toHolidayDefine(HolidayObj source, String year) {
        HolidayDefine target = new HolidayDefine();
        target.setKey(source.getKey());
        target.setYear(year);
        target.setDate(source.getDate());
        target.setTitle(source.getTitle());
        target.setEvent(source.getEvent());
        target.setDays(source.getDays());
        target.setInfo(source.getInfo());
        return target;
    }

    public static HolidayDefine toHolidayDefineForSave(HolidayObj source, String year) {
        HolidayDefine target = HolidayObj.toHolidayDefine(source, year);
        target.setKey(UUIDUtils.getKey());
        target.setOrder(OrderGenerator.newOrder());
        return target;
    }
}

