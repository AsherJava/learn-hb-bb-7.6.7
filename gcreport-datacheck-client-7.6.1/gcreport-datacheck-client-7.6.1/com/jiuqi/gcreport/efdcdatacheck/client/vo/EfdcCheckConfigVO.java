/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.tree.FormTree
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigFormInfo;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;

public class EfdcCheckConfigVO {
    private String schemeId;
    private String schemeTitle;
    private FormTree canSelectedFormsTree;
    private Integer orgMaxLength;
    private List<String> selectedFormKeys;
    private List<EfdcCheckConfigFormInfo> selectFormInfo;

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public FormTree getCanSelectedFormsTree() {
        return this.canSelectedFormsTree;
    }

    public void setCanSelectedFormsTree(FormTree canSelectedFormsTree) {
        this.canSelectedFormsTree = canSelectedFormsTree;
    }

    public List<String> getSelectedFormKeys() {
        return this.selectedFormKeys;
    }

    public void setSelectedFormKeys(List<String> selectedFormKeys) {
        this.selectedFormKeys = selectedFormKeys;
    }

    public Integer getOrgMaxLength() {
        return this.orgMaxLength;
    }

    public void setOrgMaxLength(Integer orgMaxLength) {
        this.orgMaxLength = orgMaxLength;
    }

    public List<EfdcCheckConfigFormInfo> getSelectFormInfo() {
        return this.selectFormInfo;
    }

    public void setSelectFormInfo(List<EfdcCheckConfigFormInfo> selectFormInfo) {
        this.selectFormInfo = selectFormInfo;
    }
}

