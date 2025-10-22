/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.service.IFormDataStatusService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.nr.dataentry.service.IFormDataStatusService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.FormQueryHelperImpl;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FillStateCheckExecutor
implements IFormCheckExecutor {
    protected IFormQueryContext context;
    protected IFormDataStatusService formDataStatusService;
    protected IFormQueryHelper formQueryHelper;
    protected String type;

    public FillStateCheckExecutor(IFormQueryContext context, IRunTimeViewController runTimeView, IFormDataStatusService formDataStatusService, String type) {
        this.context = context;
        this.formQueryHelper = new FormQueryHelperImpl(context, runTimeView);
        this.formDataStatusService = formDataStatusService;
        this.type = type;
    }

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        if (forms != null && forms.size() > 0) {
            JtableContext jtableContext = this.formQueryHelper.translate2JTableContext(this.formQueryHelper);
            List filledFormkey = this.formDataStatusService.getFilledFormkey(jtableContext);
            if ("filled-state-checker".equals(this.type)) {
                return forms.stream().filter(e -> filledFormkey.contains(e.getKey())).collect(Collectors.toList());
            }
            if ("unfill-state-checker".equals(this.type)) {
                return forms.stream().filter(e -> !filledFormkey.contains(e.getKey())).collect(Collectors.toList());
            }
            return forms;
        }
        return new ArrayList<FormDefine>();
    }
}

