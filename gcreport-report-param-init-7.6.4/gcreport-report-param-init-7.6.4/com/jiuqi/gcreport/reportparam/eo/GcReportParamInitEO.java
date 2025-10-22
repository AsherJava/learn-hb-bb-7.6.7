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

@DBTable(name="GC_REPORTPARAMINIT", inStorage=true, title="\u62a5\u8868\u53c2\u6570\u521d\u59cb\u5316\u8bb0\u5f55\u8868")
public class GcReportParamInitEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_REPORTPARAMINIT";
    @DBColumn(length=60, nameInDB="PARAM_NAME", dbType=DBColumn.DBType.NVarchar)
    private String name;
    @DBColumn(length=1, nameInDB="INITFLAG", dbType=DBColumn.DBType.Int)
    private Integer initFlag;
    @DBColumn(length=36, nameInDB="USERINFO", dbType=DBColumn.DBType.Varchar)
    private String userInfo;
    @DBColumn(length=36, nameInDB="CREATETIME", dbType=DBColumn.DBType.DateTime)
    private Date createTime;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInitFlag() {
        return this.initFlag;
    }

    public void setInitFlag(Integer initFlag) {
        this.initFlag = initFlag;
    }

    public String getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

