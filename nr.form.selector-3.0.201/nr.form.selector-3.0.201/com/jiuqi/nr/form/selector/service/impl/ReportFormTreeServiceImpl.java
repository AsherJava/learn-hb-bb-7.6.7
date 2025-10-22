/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.form.selector.service.impl;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.FormQueryHelperImpl;
import com.jiuqi.nr.form.selector.context.FormTreeContextImpl;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.service.ReportFormTreeService;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormCheckerFactory;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProviderFactory;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReportFormTreeServiceImpl
implements ReportFormTreeService {
    @Resource
    private IRunTimeViewController runTimeView;
    @Resource
    private IReportFormCheckerFactory checkerFactory;
    @Resource
    private IReportFormTreeProviderFactory providerFactory;

    @Override
    public List<ITree<IReportTreeNode>> getReportTree(FormTreeContextImpl context) {
        IFormQueryHelper formQueryHelper = this.getIFormQueryHelper(context, context);
        List<IReportFormChecker> formCheckers = this.getFormCheckers(context.getCheckers());
        IReportFormTreeData treeDataProvider = this.providerFactory.getFormTreeDataProvider(formQueryHelper, formCheckers, context.getFormSourceId(), context.getCheckForms());
        return treeDataProvider.getTree(formQueryHelper.getFormSchemeKey());
    }

    private IFormQueryHelper getIFormQueryHelper(IFormQueryContext context, FormTreeContextImpl contextImpl) {
        return new FormQueryHelperImpl(context, this.runTimeView, contextImpl);
    }

    private List<IReportFormChecker> getFormCheckers(List<String> ids) {
        ArrayList<IReportFormChecker> checkers = null;
        if (ids != null && !ids.isEmpty()) {
            checkers = new ArrayList<IReportFormChecker>();
            for (String id : ids) {
                checkers.add(this.checkerFactory.getReportFormChecker(id));
            }
        }
        return checkers;
    }
}

