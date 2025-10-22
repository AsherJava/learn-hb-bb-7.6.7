/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBField
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.query.dataset.defines;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.dataset.defines.DataSetConst;
import com.jiuqi.nr.query.dataset.deserializer.DataSetGroupDeserializer;
import com.jiuqi.nr.query.dataset.serializer.DataSetGroupSerializer;
import com.jiuqi.nvwa.definition.interval.anno.DBAnno;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="SYS_DATASETGROUP")
@JsonSerialize(using=DataSetGroupSerializer.class)
@JsonDeserialize(using=DataSetGroupDeserializer.class)
public class DataSetGroupDefine {
    public static final String DATASETGROUPDEFINE_ID = "id";
    public static final String DATASETGROUPDEFINE_TITLE = "title";
    public static final String DATASETGROUPDEFINE_PARENT = "parent";
    public static final String DATASETGROUPDEFINE_ORDER = "order";
    public static final String DATASETGROUPDEFINE_CREATOR = "creator";
    public static final String DATASETGROUPDEFINE_UPDATETIME = "updatetime";
    @DBAnno.DBField(dbField="DSG_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="DSG_TITLE", dbType=String.class, isPk=false)
    private String title;
    @DBAnno.DBField(dbField="DSG_PARENT", dbType=String.class, isPk=false)
    private String parent;
    @DBAnno.DBField(dbField="DSG_ORDER", dbType=String.class, isPk=false, isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updatetime;
    @DBAnno.DBField(dbField="DSG_CREATOR", dbType=String.class, isPk=false)
    private String creator = DataSetConst.getCreator();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCtreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}

