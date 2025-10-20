/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formulaschemeconfig.vo;

import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NrFormulaSchemeConfigQueryResultDTO
implements Serializable {
    private static final long serialVersionUID = -1799766447999748429L;
    private List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs;
    private Integer total;

    public NrFormulaSchemeConfigQueryResultDTO() {
        this.formulaSchemeConfigTableVOs = new ArrayList<NrFormulaSchemeConfigTableVO>();
        this.total = 0;
    }

    public NrFormulaSchemeConfigQueryResultDTO(List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOS, Integer total) {
        this.formulaSchemeConfigTableVOs = formulaSchemeConfigTableVOS;
        this.total = total;
    }

    public List<NrFormulaSchemeConfigTableVO> getFormulaSchemeConfigTableVOs() {
        return this.formulaSchemeConfigTableVOs;
    }

    public void setFormulaSchemeConfigTableVOs(List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        this.formulaSchemeConfigTableVOs = formulaSchemeConfigTableVOs;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

