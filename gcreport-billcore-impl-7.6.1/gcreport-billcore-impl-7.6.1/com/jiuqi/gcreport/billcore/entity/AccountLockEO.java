/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.billcore.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_ACCOUNTLOCK", title="\u53f0\u8d26\u9501\u5b9a\u8868")
public class AccountLockEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ACCOUNTLOCK";
    @DBColumn(nameInDB="ACCOUNTTYPE", dbType=DBColumn.DBType.Varchar, length=36)
    private String accountType;
    @DBColumn(nameInDB="STATUS", dbType=DBColumn.DBType.NVarchar, length=2)
    private String status;
    @DBColumn(nameInDB="MODIFIEDUSER", dbType=DBColumn.DBType.Varchar, length=36)
    private String modifiedUser;
    @DBColumn(nameInDB="MODIFIEDTIME", dbType=DBColumn.DBType.Date)
    private Date modifiedTime;
    @DBColumn(nameInDB="ACCTYEAR", dbType=DBColumn.DBType.Int)
    private Integer acctYear;

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedUser() {
        return this.modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }
}

