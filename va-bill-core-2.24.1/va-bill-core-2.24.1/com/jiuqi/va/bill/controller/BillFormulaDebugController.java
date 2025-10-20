/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.debug.BillFormulaDebugContextVO;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugVO;
import com.jiuqi.va.bill.service.BillFormulaDebugService;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/formula"})
public class BillFormulaDebugController {
    @Autowired
    private BillFormulaDebugService billFormulaDebugService;

    @PostMapping(value={"/context/save"})
    R saveContext(@RequestBody BillFormulaDebugContextVO billFormulaDebugContextVO) {
        return this.billFormulaDebugService.saveContext(billFormulaDebugContextVO) ? R.ok() : R.error();
    }

    @GetMapping(value={"/context/query/{defineCode}"})
    R queryContext(@PathVariable(value="defineCode") String defineCode) {
        if (!StringUtils.hasText(defineCode)) {
            return R.error();
        }
        BillFormulaDebugContextVO billFormulaDebugContextVO = this.billFormulaDebugService.queryContext(defineCode);
        return R.ok().put("data", (Object)billFormulaDebugContextVO);
    }

    @PostMapping(value={"/relation/rules/query"})
    R queryRelationRules(@RequestBody BillFormulaDebugVO billFormulaDebugVO) {
        List<FormulaImpl> formulaList = this.billFormulaDebugService.queryRelationRules(billFormulaDebugVO);
        return R.ok().put("data", formulaList);
    }

    @PostMapping(value={"/debug/info"})
    R ruleDebugInfo(@RequestBody BillFormulaDebugVO billFormulaDebugVO) {
        List<BillFormulaDebugVO> result = this.billFormulaDebugService.getRuleDebugInfo(billFormulaDebugVO);
        return R.ok().put("data", result);
    }
}

