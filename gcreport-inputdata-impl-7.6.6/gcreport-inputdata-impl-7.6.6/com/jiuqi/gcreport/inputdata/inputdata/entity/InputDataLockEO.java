/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.inputdata.inputdata.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_INPUTDATALOCK", title="\u5185\u90e8\u8868\u9501", indexs={@DBIndex(name="IDX_GC_INPUTDATALOCK_LOCK", columnsFields={"LOCKID"}), @DBIndex(name="IDX_GC_INPUTDATALOCK_INPUT", columnsFields={"INPUTITEMID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE), @DBIndex(name="IDX_GC_INPUTDATALOCK_GPCUR", columnsFields={"OFFSETGROUPID", "CURRENCYCODE"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE), @DBIndex(name="IDX_GC_INPUTDATALOCK_CHECK", columnsFields={"CHECKGROUPID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class InputDataLockEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_INPUTDATALOCK";
    @DBColumn(nameInDB="lockId", title="\u9501ID", dbType=DBColumn.DBType.Varchar, length=50, isRequired=true)
    private String lockId;
    @DBColumn(nameInDB="inputItemId", title="\u5185\u90e8\u8868\u5206\u5f55ID", dbType=DBColumn.DBType.Varchar, length=50)
    private String inputItemId;
    @DBColumn(nameInDB="offsetGroupId", title="\u62b5\u9500\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar, length=50)
    private String offsetGroupId;
    @DBColumn(nameInDB="CHECKGROUPID", title="\u5bf9\u8d26\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar, length=50)
    private String checkGroupId;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar, length=50)
    private String currencyCode;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private Long createTime;
    @DBColumn(nameInDB="lockSrc", title="\u9501\u6765\u6e90", dbType=DBColumn.DBType.Varchar, length=100)
    private String lockSrc;
    @DBColumn(nameInDB="IPADDRESS", title="ip\u5730\u5740", dbType=DBColumn.DBType.Varchar, length=50)
    private String ipAddress;
    @DBColumn(nameInDB="USERNAME", title="\u7528\u6237\u540d", dbType=DBColumn.DBType.NVarchar, length=100)
    private String userName;

    public String getLockId() {
        return this.lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getInputItemId() {
        return this.inputItemId;
    }

    public void setInputItemId(String inputItemId) {
        this.inputItemId = inputItemId;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    public String getCheckGroupId() {
        return this.checkGroupId;
    }

    public void setCheckGroupId(String checkGroupId) {
        this.checkGroupId = checkGroupId;
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

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String toString() {
        return "InputDataLockEO{lockId='" + this.lockId + '\'' + ", inputItemId='" + this.inputItemId + '\'' + ", offsetGroupId='" + this.offsetGroupId + '\'' + ", checkGroupId='" + this.checkGroupId + '\'' + ", currencyCode='" + this.currencyCode + '\'' + ", createTime=" + this.createTime + ", lockSrc='" + this.lockSrc + '\'' + ", ipAddress='" + this.ipAddress + '\'' + ", userName='" + this.userName + '\'' + '}';
    }
}

