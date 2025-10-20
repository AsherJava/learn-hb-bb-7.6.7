/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.SelectOptionVO
 */
package com.jiuqi.gcreport.inputdata.check;

import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckColumnVO;
import java.util.List;

public class InputDataCheckInitVO {
    private String systemId;
    private String orgType;
    private List<InputDataCheckColumnVO> inputDataColumnSelect;
    private List<SelectOptionVO> inputDataCheckLevel;
    private List<InputDataCheckColumnVO> checkTableColumns;
    private List<InputDataCheckColumnVO> unCheckTableColumns;
    private List<InputDataCheckColumnVO> allTableColumns;

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<InputDataCheckColumnVO> getInputDataColumnSelect() {
        return this.inputDataColumnSelect;
    }

    public void setInputDataColumnSelect(List<InputDataCheckColumnVO> inputDataColumnSelect) {
        this.inputDataColumnSelect = inputDataColumnSelect;
    }

    public List<SelectOptionVO> getInputDataCheckLevel() {
        return this.inputDataCheckLevel;
    }

    public void setInputDataCheckLevel(List<SelectOptionVO> inputDataCheckLevel) {
        this.inputDataCheckLevel = inputDataCheckLevel;
    }

    public List<InputDataCheckColumnVO> getCheckTableColumns() {
        return this.checkTableColumns;
    }

    public void setCheckTableColumns(List<InputDataCheckColumnVO> checkTableColumns) {
        this.checkTableColumns = checkTableColumns;
    }

    public List<InputDataCheckColumnVO> getUnCheckTableColumns() {
        return this.unCheckTableColumns;
    }

    public void setUnCheckTableColumns(List<InputDataCheckColumnVO> unCheckTableColumns) {
        this.unCheckTableColumns = unCheckTableColumns;
    }

    public List<InputDataCheckColumnVO> getAllTableColumns() {
        return this.allTableColumns;
    }

    public void setAllTableColumns(List<InputDataCheckColumnVO> allTableColumns) {
        this.allTableColumns = allTableColumns;
    }
}

