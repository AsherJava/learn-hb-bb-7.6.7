/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AnalyseFormReadWriteAssessImpl
implements IReadWriteAccess {
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public String getName() {
        return "analyseForm";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        FormDefine formDefine = this.runTimeController.queryFormById(context.getFormKey());
        if (FormType.FORM_TYPE_ANALYSISREPORT == formDefine.getFormType() || FormType.FORM_TYPE_INSERTANALYSIS == formDefine.getFormType()) {
            String message = "\u5206\u6790\u62a5\u544a\u8868\u5355\u4e0d\u53ef\u7f16\u8f91";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("ANALYSIS_REPORT_NO_EDIT"))) {
                message = this.i18nHelper.getMessage("ANALYSIS_REPORT_NO_EDIT");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public boolean isServerAccess() {
        return true;
    }
}

