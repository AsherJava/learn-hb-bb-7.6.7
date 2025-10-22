/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formulaschemeconfig.vo;

import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BillFormulaSchemeConfigQueryResultDTO
implements Serializable {
    private static final long serialVersionUID = -1799766447999748429L;
    private List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs;
    private Integer total;

    public BillFormulaSchemeConfigQueryResultDTO() {
        this.formulaSchemeConfigTableVOs = new ArrayList<BillFormulaSchemeConfigTableVO>();
        this.total = 0;
    }

    public BillFormulaSchemeConfigQueryResultDTO(List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOS, Integer total) {
        this.formulaSchemeConfigTableVOs = formulaSchemeConfigTableVOS;
        this.total = total;
    }

    public List<BillFormulaSchemeConfigTableVO> getFormulaSchemeConfigTableVOs() {
        return this.formulaSchemeConfigTableVOs;
    }

    public void setFormulaSchemeConfigTableVOs(List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        this.formulaSchemeConfigTableVOs = formulaSchemeConfigTableVOs;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

