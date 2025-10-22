/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.form.selector.context.IFormQueryContext
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 *  com.jiuqi.nr.form.selector.tree.IReportFormChecker
 */
package com.jiuqi.nr.dataSnapshot.common;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UnCheckDSFormChecker
implements IReportFormChecker,
IFormCheckExecutor {
    public static final String CHECKERID = "un_check_dataSnapshot_form";

    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        if (forms != null && forms.size() > 0) {
            return forms.stream().filter(form -> !form.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) && !form.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && !form.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT)).collect(Collectors.toList());
        }
        return new ArrayList<FormDefine>();
    }

    public String getCheckerId() {
        return CHECKERID;
    }

    public String getShowText() {
        return "\u8fc7\u6ee4\u6389\u5206\u6790\u8868\u53f0\u8d26\u8868";
    }

    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return this;
    }
}

