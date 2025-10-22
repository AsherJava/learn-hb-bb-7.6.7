/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.designer.service.IPrintAttributeService;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.nr.designer.web.rest.vo.PrintWordVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintAttributeServiceImpl
implements IPrintAttributeService {
    @Autowired
    private IPrintDesignTimeController printController;

    @Override
    public PrintAttributeVo queryPrintAttribute(String printSchemeKey) throws Exception {
        DesignPrintTemplateSchemeDefine printScheme = this.printController.queryPrintTemplateSchemeDefine(printSchemeKey);
        if (printScheme != null) {
            return PrintAttributeVo.toPrintSchemeAttributeDefine(printSchemeKey, this.printController.getPrintSchemeAttribute(printScheme));
        }
        return null;
    }

    @Override
    public void updatePrintAttribute(PrintAttributeVo vo) throws Exception {
        DesignPrintTemplateSchemeDefine printScheme = this.printController.queryPrintTemplateSchemeDefine(vo.getPrintSchemeKey());
        PrintSchemeAttributeDefine attributeDefine = PrintAttributeVo.toAttributeDefine(vo);
        this.printController.setPrintSchemeAttribute(printScheme, attributeDefine);
        this.printController.updatePrintTemplateSchemeDefine(printScheme);
    }

    @Override
    public boolean printAttributeISChange(PrintAttributeVo vo) throws Exception {
        PrintAttributeVo printVo = this.queryPrintAttribute(vo.getPrintSchemeKey());
        PrintSchemeAttributeDefine attributeDefine = PrintAttributeVo.toAttributeDefine(vo);
        if (PrintElementUtils.getPaperByName((String)printVo.getPaperName()).getSize() != attributeDefine.getPaper().getPaperType()) {
            return false;
        }
        if (printVo.getDirection() != attributeDefine.getPaper().getDirection()) {
            return false;
        }
        if (printVo.getMarginTop() != attributeDefine.getPaper().getMarginTop()) {
            return false;
        }
        if (printVo.getMarginBottom() != attributeDefine.getPaper().getMarginBottom()) {
            return false;
        }
        if (printVo.getMarginLeft() != attributeDefine.getPaper().getMarginLeft()) {
            return false;
        }
        if (printVo.getMarginRight() != attributeDefine.getPaper().getMarginRight()) {
            return false;
        }
        if (!printVo.getNullResult().equals(attributeDefine.getFormat().getFormatNullResult())) {
            return false;
        }
        if (!printVo.getRoundingZeroResult().equals(attributeDefine.getFormat().getFormatRoundingZeroResult())) {
            return false;
        }
        if (!printVo.getZeroResult().equals(attributeDefine.getFormat().getFormatZeroResult())) {
            return false;
        }
        if (!printVo.getDecimal().equals(attributeDefine.getFormat().getDecimal())) {
            return false;
        }
        if (!(printVo.getSeparator() ? "," : "").equals(attributeDefine.getFormat().getDecimal())) {
            return false;
        }
        if (printVo.getWordLabels().size() != attributeDefine.getWordLabels().size()) {
            return false;
        }
        List<PrintWordVo> voLabelsList = printVo.getWordLabels();
        List attributeLabelsList = attributeDefine.getWordLabels();
        for (int i = 0; i < voLabelsList.size(); ++i) {
            WordLabelDefine wordLabelDefine_Vo = PrintWordVo.toWordLabelDefine(voLabelsList.get(i));
            if (((WordLabelDefine)attributeLabelsList.get(i)).equals(wordLabelDefine_Vo)) continue;
            return false;
        }
        return vo.getMarkConfig() == printVo.getMarkConfig() || null != vo.getMarkConfig() && vo.getMarkConfig().equals(printVo.getMarkConfig());
    }
}

