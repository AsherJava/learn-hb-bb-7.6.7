/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 */
package com.jiuqi.gcreport.consolidatedsystem.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator;
import com.jiuqi.gcreport.consolidatedsystem.dao.formula.ConsolidatedFormulaDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.Formula.ConsolidatedFormulaEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ConsFormulaImportTask {
    @Autowired
    private ConsolidatedFormulaDao consolidatedFormulaDao;
    @Autowired
    private UnionRuleService unionRuleService;

    public StringBuffer consFormulaImport(ImportExcelSheet excelSheet, String systemId, StringBuffer resultMsg) {
        List excelSheetDatas = excelSheet.getExcelSheetDatas();
        if (CollectionUtils.isEmpty(excelSheetDatas)) {
            return resultMsg;
        }
        List<BaseRuleVO> baseRuleVOs = this.unionRuleService.findAllRuleTitles(systemId);
        Map<String, String> ruleTitle2IdMap = baseRuleVOs.stream().collect(Collectors.toMap(BaseRuleVO::getTitle, BaseRuleVO::getId, (t1, t2) -> t1));
        List<ConsolidatedFormulaEO> consFormulaEOS = this.consolidatedFormulaDao.listConsFormulas(systemId);
        Set<String> consFormulaSet = consFormulaEOS.stream().map(consFormula -> consFormula.getFormula()).collect(Collectors.toSet());
        String[] excelTitles = (String[])excelSheetDatas.get(0);
        for (int i = 1; i < excelSheetDatas.size(); ++i) {
            Object[] oneRowData = (Object[])excelSheetDatas.get(i);
            try {
                this.parseExcelContentRow(ruleTitle2IdMap, consFormulaSet, excelTitles, oneRowData, systemId);
                continue;
            }
            catch (Exception e) {
                resultMsg.append("\u5408\u5e76\u4f53\u7cfb\u516c\u5f0f\u5bfc\u5165\uff1a" + e.getMessage());
            }
        }
        return resultMsg;
    }

    private void parseExcelContentRow(Map<String, String> ruleTitle2IdMap, Set<String> consFormulaSet, String[] excelTitles, Object[] oneRowData, String systemId) {
        ConsolidatedFormulaEO consolidatedFormulaEO = new ConsolidatedFormulaEO();
        consolidatedFormulaEO.setSystemId(systemId);
        consolidatedFormulaEO.setSortOrder(OrderGenerator.newOrderShort());
        consolidatedFormulaEO.setInputFlag(0);
        consolidatedFormulaEO.setAntoFlag(0);
        consolidatedFormulaEO.setManualFlag(0);
        consolidatedFormulaEO.setCarryOver(0);
        block16: for (int k = 0; k < excelTitles.length; ++k) {
            String cellStr = (String)oneRowData[k];
            switch (excelTitles[k]) {
                case "\u516c\u5f0f": {
                    String consFormula = (String)oneRowData[k];
                    if (consFormulaSet.contains(consFormula)) {
                        throw new BusinessRuntimeException(String.format("\u516c\u5f0f[%s]\u5df2\u5b58\u5728", consFormula));
                    }
                    consolidatedFormulaEO.setFormula((String)oneRowData[k]);
                    continue block16;
                }
                case "\u8f93\u5165\u8c03\u6574": {
                    if (!"\u652f\u6301".equals(cellStr)) continue block16;
                    consolidatedFormulaEO.setInputFlag(1);
                    continue block16;
                }
                case "\u81ea\u52a8\u62b5\u9500": {
                    if (!"\u652f\u6301".equals(cellStr)) continue block16;
                    consolidatedFormulaEO.setAntoFlag(1);
                    continue block16;
                }
                case "\u624b\u52a8\u62b5\u9500": {
                    if (!"\u652f\u6301".equals(cellStr)) continue block16;
                    consolidatedFormulaEO.setManualFlag(1);
                    continue block16;
                }
                case "\u5e74\u7ed3": {
                    if (!"\u652f\u6301".equals(cellStr)) continue block16;
                    consolidatedFormulaEO.setCarryOver(1);
                    continue block16;
                }
                case "\u4f7f\u7528\u89c4\u5219": {
                    if (StringUtils.isEmpty((String)cellStr)) continue block16;
                    String[] ruleArr = cellStr.split(",");
                    Set ruleTitleSet = Arrays.stream(ruleArr).collect(Collectors.toSet());
                    List ruleIds = ruleTitleSet.stream().map(ruleTitle -> (String)ruleTitle2IdMap.get(ruleTitle)).collect(Collectors.toList());
                    HashMap jsonObject = new HashMap();
                    jsonObject.put("ruleIds", ruleIds);
                    consolidatedFormulaEO.setRuleIds(JsonUtils.writeValueAsString(jsonObject));
                    continue block16;
                }
            }
        }
        this.consolidatedFormulaDao.save(consolidatedFormulaEO);
    }
}

