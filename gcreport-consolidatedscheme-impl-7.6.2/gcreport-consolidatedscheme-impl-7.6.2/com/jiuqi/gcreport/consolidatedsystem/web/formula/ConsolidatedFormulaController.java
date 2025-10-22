/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.consolidatedsystem.api.Formula.ConsolidatedFormulaClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web.formula;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.api.Formula.ConsolidatedFormulaClient;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ConsolidatedFormulaController
implements ConsolidatedFormulaClient {
    @Autowired
    private ConsolidatedFormulaService consFormulaService;

    public BusinessResponseEntity<List<ConsolidatedFormulaVO>> listConsFormulas(String systemId) {
        List<ConsolidatedFormulaVO> consolidatedFormulaVOS = this.consFormulaService.listConsFormulas(systemId, true);
        return BusinessResponseEntity.ok(consolidatedFormulaVOS);
    }

    public BusinessResponseEntity<String> addConsFormula(List<ConsolidatedFormulaVO> consolidatedFormulaVOS) {
        this.consFormulaService.saveConsFormula(consolidatedFormulaVOS);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> batchDeleteConsFormula(List<String> ids) {
        this.consFormulaService.batchDeleteConsFormula(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> exchangeSort(String opNodeId, int step) {
        this.consFormulaService.exchangeSort(opNodeId, step);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }
}

