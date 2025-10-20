/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.web.treebean.PrintSchemeObject;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavaPrintSchemeHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    public void savePrintSchemeObject(String taskID, PrintSchemeObject pritnSchemeObj) throws Exception {
        String printSchemeID;
        DesignPrintTemplateSchemeDefine printSchemeDefine = null;
        if (pritnSchemeObj.isDeleted() && (printSchemeID = pritnSchemeObj.getId()) != null) {
            this.nrDesignTimeController.deletePrintTemplateSchemeDefine(printSchemeID);
        }
        if (pritnSchemeObj.isNew() && (printSchemeDefine = this.nrDesignTimeController.queryPrintTemplateSchemeDefine(printSchemeID = pritnSchemeObj.getId())) == null) {
            printSchemeDefine = this.nrDesignTimeController.createPrintTemplateSchemeDefine();
            printSchemeDefine.setKey(pritnSchemeObj.getId());
            this.savePrintSchemeObjectHelper(printSchemeDefine, pritnSchemeObj);
            printSchemeDefine.setTaskKey(taskID);
            this.nrDesignTimeController.insertPrintTemplateSchemeDefine(printSchemeDefine);
        }
        if (pritnSchemeObj.isDirty()) {
            printSchemeID = pritnSchemeObj.getId();
            printSchemeDefine = this.nrDesignTimeController.queryPrintTemplateSchemeDefine(printSchemeID);
            this.savePrintSchemeObjectHelper(printSchemeDefine, pritnSchemeObj);
            printSchemeDefine.setTaskKey(taskID);
            printSchemeDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updatePrintTemplateSchemeDefine(printSchemeDefine);
        }
    }

    private void savePrintSchemeObjectHelper(DesignPrintTemplateSchemeDefine printSchemeDefine, PrintSchemeObject pritnSchemeObj) {
        printSchemeDefine.setTitle(pritnSchemeObj.getTitle());
        String formSchemeKey = pritnSchemeObj.getFormSchemeKey();
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            printSchemeDefine.setFormSchemeKey(formSchemeKey);
        }
    }
}

