/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.item.entity;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_DB_LOCK", title="\u6570\u636e\u5e93\u9501", indexs={@DBIndex(name="IDX_GC_DB_LOCK_LOCK", columnsFields={"LOCKID"}), @DBIndex(name="IDX_GC_DB_ILOCK_ITEM", columnsFields={"ITEMID", "ISOLATIONFIELD"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)}, dataSource="jiuqi.gcreport.mdd.datasource")
public class GcDbLockEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DB_LOCK";
    @DBColumn(nameInDB="LOCKID", title="\u9501ID", dbType=DBColumn.DBType.Varchar, length=50, isRequired=true)
    private String lockId;
    @DBColumn(nameInDB="ITEMID", title="\u6570\u636e\u6807\u8bc6ID", dbType=DBColumn.DBType.Varchar, length=50)
    private String itemId;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private Long createTime;
    @DBColumn(nameInDB="LOCKSRC", title="\u9501\u6765\u6e90", dbType=DBColumn.DBType.Varchar, length=100)
    private String lockSrc;
    @DBColumn(nameInDB="IPADDRESS", title="ip\u5730\u5740", dbType=DBColumn.DBType.Varchar, length=50)
    private String ipAddress;
    @DBColumn(nameInDB="USERNAME", title="\u7528\u6237\u540d", dbType=DBColumn.DBType.NVarchar, length=100)
    private String userName;
    @DBColumn(nameInDB="ISOLATIONFIELD", title="\u9694\u79bb\u5b57\u6bb5", dbType=DBColumn.DBType.Varchar, length=50)
    private String isolationField;
    @DBColumn(nameInDB="THREADID", title="\u7ebf\u7a0bid", dbType=DBColumn.DBType.Varchar, length=50)
    private String threadId;
    @DBColumn(nameInDB="REENTRANTCOUNT", title="\u91cd\u5165\u6b21\u6570", dbType=DBColumn.DBType.Int)
    private Integer reentrantCount;

    public String getLockId() {
        return this.lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getLockSrc() {
        return this.lockSrc;
    }

    public void setLockSrc(String lockSrc) {
        this.lockSrc = lockSrc;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsolationField() {
        return this.isolationField;
    }

    public void setIsolationField(String isolationField) {
        this.isolationField = isolationField;
    }

    public String getThreadId() {
        return this.threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public int getReentrantCount() {
        return this.reentrantCount;
    }

    public void setReentrantCount(int reentrantCount) {
        this.reentrantCount = reentrantCount;
    }

    public GcDbLockEO() {
    }

    public GcDbLockEO(String lockId, String itemId, Long createTime, String lockSrc, String ipAddress, String userName, String isolationField, String threadId, Integer reentrantCount) {
        this.lockId = lockId;
        this.itemId = itemId;
        this.createTime = createTime;
        this.lockSrc = lockSrc;
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.isolationField = isolationField;
        this.setId(UUIDUtils.newUUIDStr());
        this.setThreadId(threadId);
        this.setReentrantCount(reentrantCount);
    }

    public String toString() {
        return "ILockEO{lockId='" + this.lockId + '\'' + ", itemId='" + this.itemId + '\'' + ", createTime=" + this.createTime + ", lockSrc='" + this.lockSrc + '\'' + ", ipAddress='" + this.ipAddress + '\'' + ", userName='" + this.userName + '\'' + ", isolationField='" + this.isolationField + '\'' + '}';
    }
}

