/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.io.Serializable;
import java.sql.Clob;

@DBAnno.DBTable(dbTable="sys_single_query")
public class QueryModel
implements Serializable {
    @DBAnno.DBField(dbField="sq_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="sq_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="sq_task_key")
    private String taskKey;
    @DBAnno.DBField(dbField="sq_formscheme_key")
    private String formschemeKey;
    @DBAnno.DBField(dbField="sq_item_title")
    private String itemTitle;
    @DBAnno.DBField(dbField="sq_group")
    private String group;
    @DBAnno.DBField(dbField="sq_item", tranWith="transBytes", dbType=Clob.class, appType=String.class)
    private String item;
    @DBAnno.DBField(dbField="sq_custom")
    private Integer custom;
    @DBAnno.DBField(dbField="sq_disuse")
    private Integer disUse;
    @DBAnno.DBField(dbField="sq_modal_warning", tranWith="transBytes", dbType=Clob.class, appType=String.class)
    private String forewarnCondition;
    @DBAnno.DBField(dbField="sq_level")
    private Integer level;

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDisUse() {
        return this.disUse;
    }

    public void setDisUse(Integer disUse) {
        this.disUse = disUse;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormschemeKey() {
        return this.formschemeKey;
    }

    public void setFormschemeKey(String formschemeKey) {
        this.formschemeKey = formschemeKey;
    }

    public String getItemTitle() {
        return this.itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getCustom() {
        return this.custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public String getForewarnCondition() {
        return this.forewarnCondition;
    }

    public void setForewarnCondition(String forewarnCondition) {
        this.forewarnCondition = forewarnCondition;
    }
}

