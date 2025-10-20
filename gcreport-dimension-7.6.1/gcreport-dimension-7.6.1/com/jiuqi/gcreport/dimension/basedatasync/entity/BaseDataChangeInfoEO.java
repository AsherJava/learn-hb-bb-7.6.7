/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 */
package com.jiuqi.gcreport.dimension.basedatasync.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@DBTable(name="DC_BASEDATACHANGEINFO", title="\u57fa\u7840\u6570\u636e\u53d8\u66f4\u8bb0\u5f55\u8868", kind=TableModelKind.SYSTEM_EXTEND, indexs={@DBIndex(name="IDX_BASEDATACHANGEINFO_COMB", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"BASEDATACODE", "HANDLESTATE"}), @DBIndex(name="IDX_BASEDATACHANGEINFO_HANDLE", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"HANDLESTATE"})}, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26\u56fa\u5316\u8868\u5206\u7ec4"))
public class BaseDataChangeInfoEO
extends BaseEntity {
    private static final long serialVersionUID = -4956038298562386244L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true, order=0)
    private String id;
    @DBColumn(nameInDB="CODE", title="\u57fa\u7840\u6570\u636e\u503c", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=1)
    private String code;
    @DBColumn(nameInDB="BASEDATACODE", title="\u57fa\u7840\u6570\u636eCODE", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=2)
    private String baseDataCode;
    @DBColumn(nameInDB="CHANGETYPE", title="\u53d8\u66f4\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60, order=3)
    private String changeType;
    @DBColumn(nameInDB="OPERATINGTIME", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=4)
    private Date operatingTime;
    @DBColumn(nameInDB="HANDLESTATE", title="\u5904\u7406\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, defaultValue="0", order=5)
    private String handleState;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBaseDataCode() {
        return this.baseDataCode;
    }

    public void setBaseDataCode(String baseDataCode) {
        this.baseDataCode = baseDataCode;
    }

    public String getChangeType() {
        return this.changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Date getOperatingTime() {
        return this.operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getHandleState() {
        return this.handleState;
    }

    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return "DC_BASEDATACHANGEINFO";
    }
}

