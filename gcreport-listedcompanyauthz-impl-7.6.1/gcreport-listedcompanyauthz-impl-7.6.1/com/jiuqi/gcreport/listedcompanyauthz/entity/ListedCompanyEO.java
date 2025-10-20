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
package com.jiuqi.gcreport.listedcompanyauthz.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_LISTEDCOMPANY", inStorage=true, title="\u4e0a\u5e02\u516c\u53f8\u8bbe\u7f6e\u8868", indexs={@DBIndex(type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"ORGCODE"}), @DBIndex(type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"USERNAME"}), @DBIndex(type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"ORGCODE", "USERNAME"})})
public class ListedCompanyEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    @DBColumn(length=60, title="\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar)
    private String orgCode;
    @DBColumn(length=100, title="\u5355\u4f4d\u540d\u79f0", dbType=DBColumn.DBType.NVarchar)
    private String orgTitle;
    @DBColumn(length=40, title="\u7528\u6237ID", dbType=DBColumn.DBType.NVarchar)
    private String userId;
    @DBColumn(length=60, title="\u7528\u6237\u767b\u5f55\u540d", dbType=DBColumn.DBType.NVarchar)
    private String userName;
    @DBColumn(length=200, title="\u7528\u6237\u540d\u79f0", dbType=DBColumn.DBType.NVarchar)
    private String userTitle;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(length=60, title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar)
    private String createUser;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date modifyTime;
    @DBColumn(length=60, title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.NVarchar)
    private String modifyUser;

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}

