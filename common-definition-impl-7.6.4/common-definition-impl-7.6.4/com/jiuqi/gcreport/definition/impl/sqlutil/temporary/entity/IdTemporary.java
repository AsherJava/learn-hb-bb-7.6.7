/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_IDTEMPORARY", title="\u96c6\u5408\u53c2\u6570\u4e34\u65f6\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_IDTEMPORARY_GROUPID", columnsFields={"GROUP_ID"})})
public class IdTemporary
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_IDTEMPORARY";
    @DBColumn(nameInDB="GROUP_ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="TBID", dbType=DBColumn.DBType.Varchar, length=36)
    private String tbId;
    @DBColumn(nameInDB="TBCODE", dbType=DBColumn.DBType.NVarchar, length=100)
    private String tbCode;
    @DBColumn(nameInDB="TBNUM", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Number tbNum;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTbId() {
        return this.tbId;
    }

    public void setTbId(String tbId) {
        this.tbId = tbId;
    }

    public String getTbCode() {
        return this.tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public Number getTbNum() {
        return this.tbNum;
    }

    public void setTbNum(Number tbNum) {
        this.tbNum = tbNum;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

