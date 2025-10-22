/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.listedcompanyauthz.expimp.base;

import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

public class ListedCompanyExpImpVO {
    @ExcelColumn(index=0, title={"\u5355\u4f4d\u4ee3\u7801"})
    private String orgCode;
    @ExcelColumn(index=1, title={"\u5355\u4f4d\u540d\u79f0"})
    private String orgName;
    @ExcelColumn(index=2, title={"\u7528\u6237\u767b\u5f55\u540d"})
    private String userName;
    @ExcelColumn(index=3, title={"\u7528\u6237\u540d\u79f0"})
    private String userTitle;
    @ExcelColumn(index=4, title={"\u6240\u5c5e\u4e3b\u4f53"})
    private String userBelongOrg;
    @ExcelColumn(index=5, title={"\u6240\u5c5e\u89d2\u8272"})
    private String userRole;
    @ExcelColumn(index=6, title={"\u8d26\u52a1\u6570\u636e\u7a7f\u900f"})
    private String isPenetrate;
    private boolean success;
    private String result;

    public ListedCompanyExpImpVO() {
    }

    public ListedCompanyExpImpVO(String orgCode, String orgName, String userName, String userTitle) {
        this.setOrgCode(orgCode);
        this.setOrgName(orgName);
        this.setUserName(userName);
        this.setUserTitle(userTitle);
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserBelongOrg() {
        return this.userBelongOrg;
    }

    public void setUserBelongOrg(String userBelongOrg) {
        this.userBelongOrg = userBelongOrg;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getIsPenetrate() {
        return this.isPenetrate;
    }

    public void setIsPenetrate(String isPenetrate) {
        this.isPenetrate = isPenetrate;
    }
}

