/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.form.selector.tree.checker;

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
public class UnCheckFMDMFormChecker
implements IReportFormChecker,
IFormCheckExecutor {
    public static final String CHECKER_ID = "un_check_fmdm_form";

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        if (forms != null && forms.size() > 0) {
            return forms.stream().filter(form -> !form.getFormType().equals((Object)FormType.FORM_TYPE_FMDM) && !form.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)).collect(Collectors.toList());
        }
        return new ArrayList<FormDefine>();
    }

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u8fc7\u6ee4\u6389\u5c01\u9762\u4ee3\u7801\u8868";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return this;
    }
}

