/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.vo;

import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;

public class DataResourceDefineVO {
    private String key;
    private String dimKey;
    private String title;
    private String desc;
    private String groupKey;
    private String order;
    private boolean canWrite;
    private String period;

    public DataResourceDefineVO() {
    }

    public DataResourceDefineVO(DataResourceDefine dataResourceDefine) {
        this.key = dataResourceDefine.getKey();
        this.dimKey = dataResourceDefine.getDimKey();
        this.title = dataResourceDefine.getTitle();
        this.desc = dataResourceDefine.getDesc();
        this.groupKey = dataResourceDefine.getGroupKey();
        this.order = dataResourceDefine.getOrder();
        this.period = dataResourceDefine.getPeriod();
    }

    public DataResourceDefine toDd(IDataResourceDefineService defineService) {
        DataResourceDefine init = defineService.init();
        init.setKey(this.key);
        init.setDimKey(this.dimKey);
        init.setTitle(this.title);
        init.setDesc(this.desc);
        init.setGroupKey(this.groupKey);
        init.setOrder(this.order);
        init.setPeriod(this.period);
        return init;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isCanWrite() {
        return this.canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String toString() {
        return "DataResourceDefineVO{key='" + this.key + '\'' + ", dimKey='" + this.dimKey + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", groupKey='" + this.groupKey + '\'' + ", order='" + this.order + '\'' + '}';
    }
}

