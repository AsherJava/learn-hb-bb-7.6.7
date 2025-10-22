/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.formulamapping.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="SYS_FORMULA_MAPPING_SCHEME")
public class FormulaMappingSchemeDefine {
    @DBAnno.DBField(dbField="fms_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fms_order")
    private String order;
    @DBAnno.DBField(dbField="fms_target_fs_key")
    private String targetFSKey;
    @DBAnno.DBField(dbField="fms_source_fs_key")
    private String sourceFSKey;
    @DBAnno.DBField(dbField="fms_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

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

    public String getTargetFSKey() {
        return this.targetFSKey;
    }

    public void setTargetFSKey(String targetFSKey) {
        this.targetFSKey = targetFSKey;
    }

    public String getSourceFSKey() {
        return this.sourceFSKey;
    }

    public void setSourceFSKey(String sourceFSKey) {
        this.sourceFSKey = sourceFSKey;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

