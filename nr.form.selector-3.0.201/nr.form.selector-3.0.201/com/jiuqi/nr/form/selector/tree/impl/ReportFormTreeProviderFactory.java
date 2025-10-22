/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormCheckerFactory;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProviderFactory;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeSource;
import com.jiuqi.nr.form.selector.tree.impl.IReportFormTreeFactory;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeDataJustHaveGroup;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeDataWithFormulaAdjust;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeDataWithTask;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeProviderWithTask;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeWithCheckerRetainAll;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ReportFormTreeProviderFactory
implements IReportFormTreeProviderFactory {
    @Resource
    private IRunTimeViewController runTimeView;
    @Resource
    private IReportFormCheckerFactory checkerFactory;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;
    @Resource
    private IReportFormTreeFactory formTreeFactory;

    @Override
    public IReportFormTreeProvider getFormTreeProvider(IFormQueryHelper formQueryHelper, List<IReportFormChecker> formCheckers) {
        IReportFormChecker formAuthChecker = this.checkerFactory.getReportFormChecker("form-auth-checker");
        IReportFormTreeProvider provider = new ReportFormTreeProvider(formQueryHelper, formAuthChecker);
        if (formCheckers != null) {
            provider = new ReportFormTreeWithCheckerRetainAll(formQueryHelper, provider, formCheckers);
        }
        return provider;
    }

    @Override
    public IReportFormTreeData getFormTreeDataProvider(IFormQueryHelper formQueryHelper, List<IReportFormChecker> formCheckers, String formSourceID, List<String> checkList) {
        IReportFormTreeSource formTreeSource = this.formTreeFactory.getFormTreeSource(formSourceID);
        if (formTreeSource != null) {
            return formTreeSource.getTreeProvider(formQueryHelper);
        }
        if ("formula".equals(formSourceID)) {
            IReportFormTreeProvider formProvider = this.getFormTreeProvider(formQueryHelper, formCheckers);
            return new ReportFormTreeDataWithFormulaAdjust(formQueryHelper, formProvider, checkList);
        }
        if ("task".equals(formSourceID)) {
            ReportFormTreeProviderWithTask formDataProvider = new ReportFormTreeProviderWithTask(formQueryHelper, this.authorityProvider);
            return new ReportFormTreeDataWithTask(this.runTimeView, formQueryHelper, formDataProvider, checkList);
        }
        if ("just-have-group".equals(formSourceID)) {
            IReportFormTreeProvider formProvider = this.getFormTreeProvider(formQueryHelper, formCheckers);
            return new ReportFormTreeDataJustHaveGroup(formQueryHelper, formProvider, checkList);
        }
        IReportFormTreeProvider formProvider = this.getFormTreeProvider(formQueryHelper, formCheckers);
        return new ReportFormTreeData(formProvider, checkList);
    }
}

