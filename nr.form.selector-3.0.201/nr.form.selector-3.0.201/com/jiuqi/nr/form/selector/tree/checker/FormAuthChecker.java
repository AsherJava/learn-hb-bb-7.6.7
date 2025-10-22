/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.FormQueryHelperImpl;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.FormAuthCheckExecutor;
import com.jiuqi.nr.form.selector.tree.checkExecutor.FormAuthCheckExecutorOfBatch;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FormAuthChecker
implements IReportFormChecker {
    public static final String CHECKER_ID = "form-auth-checker";
    @Resource
    private IRunTimeViewController runTimeView;
    @Resource
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u5168\u90e8";
    }

    @Override
    public boolean isDisplay() {
        return true;
    }

    @Override
    public int getOrdinary() {
        return 99999;
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        FormQueryHelperImpl formQueryHelper = new FormQueryHelperImpl(context, this.runTimeView);
        if (formQueryHelper.isBatchDimValueSet()) {
            return new FormAuthCheckExecutorOfBatch(formQueryHelper, this.authorityProvider);
        }
        return new FormAuthCheckExecutor(formQueryHelper, this.dataAccessServiceProvider);
    }
}

