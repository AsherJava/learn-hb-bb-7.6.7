/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 */
package com.jiuqi.nr.designer.excel.importexcel.cache;

import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nr.designer.excel.importexcel.util.FormHelper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormDataInsertDB {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private FormHelper formHelper;

    public void saveFormData(ExcelImportContext importContext) throws Exception {
        this.nrDesignTimeController.insertDataRegionDefines(importContext.getRegoinData().toArray(new DesignDataRegionDefine[0]));
        if (!this.formHelper.mapIsEmpty(importContext.getLinkData())) {
            for (Map<String, DesignDataLinkDefine> designDataLinkDefineMap : importContext.getLinkData().values()) {
                this.nrDesignTimeController.insertDataLinkDefines(designDataLinkDefineMap.values().toArray(new DesignDataLinkDefine[0]));
            }
        }
        if (!this.formHelper.listIsEmpty(importContext.getFormulaImportContext().getFormulaDefineList())) {
            this.nrDesignTimeController.insertFormulasNotAnalysis(importContext.getFormulaImportContext().getFormulaDefineList().toArray(new DesignFormulaDefine[0]));
        }
    }
}

