/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.holiday.manager.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="sys_holiday_manager")
public class HolidayDefine {
    public static final String CLZ_FIELD_YEAR = "year";
    public static final String DB_FIELD_YEAR = "hm_year";
    @DBAnno.DBField(dbField="hm_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="hm_year")
    private String year;
    @DBAnno.DBField(dbField="hm_title")
    private String title;
    @DBAnno.DBField(dbField="hm_date")
    private String date;
    @DBAnno.DBField(dbField="hm_days")
    private int days;
    @DBAnno.DBField(dbField="hm_event")
    private int event;
    @DBAnno.DBField(dbField="hm_info", dbType=Clob.class)
    private String info;
    @DBAnno.DBField(dbField="hm_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="hm_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

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

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

