/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.GroupLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLinks
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.facade.GroupLink;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupDefineImpl;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMGROUPLINK")
@DBAnno.DBLinks(value={@DBAnno.DBLink(linkWith=RunTimeFormGroupDefineImpl.class, linkField="key", field="groupKey"), @DBAnno.DBLink(linkWith=RunTimeFormDefineImpl.class, linkField="key", field="formKey")})
public class RunTimeFormGroupLink
implements GroupLink {
    private static final long serialVersionUID = -3138573596767144980L;
    @DBAnno.DBField(dbField="fgl_group_key")
    private String groupKey;
    @DBAnno.DBField(dbField="fgl_form_key")
    private String formKey;
    @DBAnno.DBField(dbField="fgl_level")
    private String level;
    @DBAnno.DBField(dbField="fgl_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fgl_form_order")
    private String formOrder;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFormOrder() {
        return this.formOrder;
    }

    public void setFormOrder(String formOrder) {
        this.formOrder = formOrder;
    }

    public String getObjectKey() {
        return this.getFormKey();
    }

    public void setObjectKey(String key) {
        this.setFormKey(key);
    }
}

