/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.offsetitem.init.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.sql.Timestamp;

@DBTable(name="GC_OFFSETINIT_LOGINFOS", title="\u62b5\u9500\u521d\u59cb\u65e5\u5fd7\u8868", inStorage=true)
public class GcOffsetInitLogInfoEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_OFFSETINIT_LOGINFOS";
    @DBColumn(nameInDB="ORGCODE", title="\u5355\u4f4dCode", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String orgCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    protected Integer acctYear;
    @DBColumn(nameInDB="LASTMODIFYTIME", title="\u7b2c0\u671f\u62b5\u9500\u5206\u5f55\u6700\u540e\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Timestamp lastModifyTime;
    @DBColumn(nameInDB="LOGINFO", title="\u6267\u884c\u65e5\u5fd7", dbType=DBColumn.DBType.Text)
    private String loginfo;

    public static long getSerialVersionUID() {
        return 1L;
    }

    public static String getTABLENAME() {
        return TABLENAME;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Timestamp getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getLoginfo() {
        return this.loginfo;
    }

    public void setLoginfo(String loginfo) {
        this.loginfo = loginfo;
    }
}

