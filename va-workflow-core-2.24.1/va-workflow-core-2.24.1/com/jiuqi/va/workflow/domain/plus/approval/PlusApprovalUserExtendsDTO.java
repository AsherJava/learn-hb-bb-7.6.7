/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDTO
 */
package com.jiuqi.va.workflow.domain.plus.approval;

import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDTO;

public class PlusApprovalUserExtendsDTO
extends PlusApprovalUserDTO {
    private String name;
    private String email;
    private String telephone;
    private String unitname;
    private String staffName;
    private String deptname;
    private String deptcode;
    private String staffobjectcode;
    private String positionCode;
    private String positionName;

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getStaffobjectcode() {
        return this.staffobjectcode;
    }

    public void setStaffobjectcode(String staffobjectcode) {
        this.staffobjectcode = staffobjectcode;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDeptname() {
        return this.deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDeptcode() {
        return this.deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUnitname() {
        return this.unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}

