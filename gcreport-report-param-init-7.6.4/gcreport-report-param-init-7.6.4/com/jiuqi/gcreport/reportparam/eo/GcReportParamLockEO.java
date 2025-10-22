/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.reportparam.eo;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_REPORTPARAMLOCK", inStorage=true, title="\u62a5\u8868\u53c2\u6570\u521d\u59cb\u5316\u9501\u8bb0\u5f55\u8868")
public class GcReportParamLockEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_REPORTPARAMLOCK";
    @DBColumn(length=60, nameInDB="LOCKUSER", dbType=DBColumn.DBType.NVarchar)
    private String lockUser;
    @DBColumn(length=1, nameInDB="LOCKED", dbType=DBColumn.DBType.Int)
    private Integer locked;
    @DBColumn(nameInDB="LOCKTIME", dbType=DBColumn.DBType.DateTime)
    private Date lockTime;

    public String getLockUser() {
        return this.lockUser;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public Integer getLocked() {
        return this.locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getLockTime() {
        return this.lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }
}

