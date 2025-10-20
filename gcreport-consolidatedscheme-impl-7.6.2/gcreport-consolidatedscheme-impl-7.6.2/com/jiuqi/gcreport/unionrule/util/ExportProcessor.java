/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.unionrule.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.util.ExcelUtils;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public class ExportProcessor {
    private List<SelectOptionVO> dimensions;
    private List<String> selectDimensions;
    private List<UnionRuleVO> ruleNodeList;
    private String reportSystemId;
    protected int order;
    private IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
    private RuleManagerFactory factory = (RuleManagerFactory)SpringBeanUtils.getBean(RuleManagerFactory.class);

    public ExportProcessor(List<UnionRuleVO> ruleNodeList, List<SelectOptionVO> dimensions, List<String> selectdimensions, String reportSystemId) {
        this.ruleNodeList = ruleNodeList;
        this.dimensions = dimensions;
        this.selectDimensions = selectdimensions;
        this.reportSystemId = reportSystemId;
    }

    public List<List<ExportExcelVO>> getSheetDatas() {
        ArrayList<List<ExportExcelVO>> sheetRuleLists = new ArrayList<List<ExportExcelVO>>();
        this.ruleNodeList.forEach(ruleNode -> sheetRuleLists.add(this.getSheetData((UnionRuleVO)ruleNode)));
        return sheetRuleLists;
    }

    protected List<ExportExcelVO> getSheetData(UnionRuleVO ruleNode) {
        ArrayList<ExportExcelVO> allRows = new ArrayList<ExportExcelVO>();
        ArrayList<String> ruleTitle = new ArrayList<String>();
        this.order = 1;
        this.getSheetData(ruleNode.getChildren(), allRows, ruleTitle);
        return allRows;
    }

    protected List<ExportExcelVO> getSheetData(List<UnionRuleVO> ruleNode, List<ExportExcelVO> allRows, List<String> ruleTitle) {
        ruleNode.forEach(ruleData -> {
            if ("group".equals(ruleData.getRuleType())) {
                ruleTitle.add(ruleData.getTitle());
                this.getSheetData(ruleData.getChildren(), allRows, ruleTitle);
                ruleTitle.remove(ruleData.getTitle());
            } else {
                ruleData.getRuleType();
                UnionRuleManager unionRuleManager = this.factory.getUnionRuleManager(ruleData.getRuleType());
                if (Objects.isNull(unionRuleManager)) {
                    return;
                }
                unionRuleManager.getUnionRuleExportProcessor((UnionRuleVO)ruleData, this.reportSystemId, this.order).getRowDatas(allRows, ruleTitle);
                ++this.order;
            }
        });
        return allRows;
    }

    public Map<String, String> getSheets() {
        LinkedHashMap<String, String> sheetMap = new LinkedHashMap<String, String>();
        this.ruleNodeList.forEach(sheet -> {
            if ("group".equals(sheet.getRuleType())) {
                sheetMap.put(sheet.getTitle(), null);
            }
        });
        return sheetMap;
    }

    public List<List<ExcelUtils.ExportColumnVO>> getSheetTitles() {
        ArrayList<List<ExcelUtils.ExportColumnVO>> titleList = new ArrayList<List<ExcelUtils.ExportColumnVO>>();
        ArrayList<ExcelUtils.ExportColumnVO> headerRow = new ArrayList<ExcelUtils.ExportColumnVO>();
        titleList.add(headerRow);
        headerRow.add(new ExcelUtils.ExportColumnVO("index", "\u5e8f\u53f7", 5));
        headerRow.add(new ExcelUtils.ExportColumnVO("ruleTitle", "\u89c4\u5219", 40));
        headerRow.add(new ExcelUtils.ExportColumnVO("debitOrCredit", "\u501f/\u8d37", 8));
        headerRow.add(new ExcelUtils.ExportColumnVO("subjectCode", "\u79d1\u76ee\u4ee3\u7801", 12));
        headerRow.add(new ExcelUtils.ExportColumnVO("subjectTitle", "\u79d1\u76ee\u540d\u79f0", 40));
        headerRow.add(new ExcelUtils.ExportColumnVO("formula", "\u516c\u5f0f", 80));
        headerRow.add(new ExcelUtils.ExportColumnVO("applyGcUnits", "\u9002\u7528\u5408\u5e76\u5355\u4f4d", 80));
        headerRow.add(new ExcelUtils.ExportColumnVO("ruleCondition", "\u9002\u7528\u6761\u4ef6", 20));
        headerRow.add(new ExcelUtils.ExportColumnVO("ruleType", "\u89c4\u5219\u7c7b\u578b", 16));
        headerRow.add(new ExcelUtils.ExportColumnVO("businessType", "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b", 16));
        headerRow.add(new ExcelUtils.ExportColumnVO("startFlag", "\u662f\u5426\u505c\u7528", 16));
        headerRow.add(new ExcelUtils.ExportColumnVO("unit", "\u5355\u4f4d", 16));
        headerRow.add(new ExcelUtils.ExportColumnVO("options", "\u9009\u9879", 80));
        if (!CollectionUtils.isEmpty(this.dimensions)) {
            this.dimensions.forEach(dim -> {
                if (this.selectDimensions.contains(dim.getValue().toString())) {
                    headerRow.add(new ExcelUtils.ExportColumnVO(dim.getValue().toString(), dim.getLabel(), 13));
                }
            });
        }
        return titleList;
    }
}

