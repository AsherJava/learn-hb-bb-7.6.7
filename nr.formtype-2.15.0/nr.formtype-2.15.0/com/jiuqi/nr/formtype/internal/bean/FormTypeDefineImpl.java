/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.formtype.internal.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_FORMTYPE_DEFINE")
public class FormTypeDefineImpl
implements FormTypeDefine {
    private static final long serialVersionUID = -2624553900160535037L;
    public static final String CLZ_FIELD_GROUPID = "groupId";
    public static final String DB_FIELD_ID = "FT_ID";
    public static final String DB_FIELD_TITLE = "FT_TITLE";
    public static final String DB_FIELD_CODE = "FT_CODE";
    @DBAnno.DBField(dbField="FT_ID", isPk=true)
    private String id;
    @DBAnno.DBField(dbField="FT_CODE")
    private String code;
    @DBAnno.DBField(dbField="FT_TITLE")
    private String title;
    @DBAnno.DBField(dbField="FT_GROUP_ID")
    private String groupId;
    @DBAnno.DBField(dbField="FT_DESC")
    private String desc;
    @DBAnno.DBField(dbField="FT_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="FT_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = null == code ? code : code.toUpperCase();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

