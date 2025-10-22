/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.List;

public class FinancialFetchInitTaskDTO
extends FetchInitTaskDTO {
    private EfdcInfo efdcInfo;
    private List<String> forms;
    private List<FormulaSchemeDefine> formulaSchemeList;
    private String gcTaskId;

    public EfdcInfo getEfdcInfo() {
        return this.efdcInfo;
    }

    public void setEfdcInfo(EfdcInfo efdcInfo) {
        this.efdcInfo = efdcInfo;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public List<FormulaSchemeDefine> getFormulaSchemeList() {
        return this.formulaSchemeList;
    }

    public void setFormulaSchemeList(List<FormulaSchemeDefine> formulaSchemeList) {
        this.formulaSchemeList = formulaSchemeList;
    }

    public String getGcTaskId() {
        return this.gcTaskId;
    }

    public void setGcTaskId(String gcTaskId) {
        this.gcTaskId = gcTaskId;
    }
}

