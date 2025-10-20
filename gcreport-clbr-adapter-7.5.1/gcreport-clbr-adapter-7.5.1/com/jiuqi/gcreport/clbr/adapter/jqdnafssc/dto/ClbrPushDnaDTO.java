/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 */
package com.jiuqi.gcreport.clbr.adapter.jqdnafssc.dto;

import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import java.util.List;

public class ClbrPushDnaDTO {
    private String userName;
    private String openType = "ZDYPAGE";
    private String pageName = "NvawaGcPushBillPage";
    private String sso = "cwag_oa";
    private List<ClbrBillDTO> pageParams;

    public String toString() {
        return "ClbrPushDnaDTO{userName='" + this.userName + '\'' + ", openType='" + this.openType + '\'' + ", pageName='" + this.pageName + '\'' + ", sso='" + this.sso + '\'' + ", pageParams=" + this.pageParams + '}';
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpenType() {
        return this.openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSso() {
        return this.sso;
    }

    public void setSso(String sso) {
        this.sso = sso;
    }

    public List<ClbrBillDTO> getPageParams() {
        return this.pageParams;
    }

    public void setPageParams(List<ClbrBillDTO> pageParams) {
        this.pageParams = pageParams;
    }
}

