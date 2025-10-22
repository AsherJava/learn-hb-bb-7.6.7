/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.item.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import java.util.UUID;

@DBTable(name="GC_RELATED_ITEM_UNCHECK_DESC", title="\u5173\u8054\u4ea4\u6613\u5206\u5f55\u672a\u5bf9\u8d26\u8bf4\u660e", indexs={@DBIndex(name="IDX_GC_RELATED_ITEM_UNCHECK_DESC_ITEMID", columnsFields={"ITEMID"})}, dataSource="jiuqi.gcreport.mdd.datasource")
public class GcRelatedItemUnCheckDescEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_RELATED_ITEM_UNCHECK_DESC";
    @DBColumn(nameInDB="ITEMID", title="\u5206\u5f55ID", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true)
    private String itemId;
    @DBColumn(nameInDB="UNCHECKTYPE", title="\u672a\u5bf9\u8d26\u8bf4\u660e\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true)
    private String unCheckType;
    @DBColumn(nameInDB="UNCHECKDESC", title="\u672a\u5bf9\u8d26\u8bf4\u660e\u63cf\u8ff0", dbType=DBColumn.DBType.NVarchar, length=2000)
    private String unCheckDesc;
    @DBColumn(nameInDB="UPDATEUSER", title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updateUser;
    @DBColumn(nameInDB="UPDATEDATE", title="\u4fee\u6539\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date updateDate;

    public GcRelatedItemUnCheckDescEO(String itemId, String unCheckType, String unCheckDesc, Date updateDate, String updateUser) {
        this.itemId = itemId;
        this.unCheckType = unCheckType;
        this.unCheckDesc = unCheckDesc;
        this.updateDate = updateDate;
        this.setId(UUID.randomUUID().toString());
        this.updateUser = updateUser;
    }

    public GcRelatedItemUnCheckDescEO() {
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUnCheckType() {
        return this.unCheckType;
    }

    public void setUnCheckType(String unCheckType) {
        this.unCheckType = unCheckType;
    }

    public String getUnCheckDesc() {
        return this.unCheckDesc;
    }

    public void setUnCheckDesc(String unCheckDesc) {
        this.unCheckDesc = unCheckDesc;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}

