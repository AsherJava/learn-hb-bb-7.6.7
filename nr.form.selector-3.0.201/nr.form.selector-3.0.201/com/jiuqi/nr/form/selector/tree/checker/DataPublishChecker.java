/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.service.IDataPublishService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.DataPublishExcutor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPublishChecker
implements IReportFormChecker {
    public static final String CHECKER_ID = "unLockChecker";
    @Autowired
    protected IDataPublishService iDataPublishService;
    @Autowired
    private IRunTimeViewController runTimeView;

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u6570\u636e\u53d1\u5e03\u8fc7\u6ee4\u5668";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new DataPublishExcutor(context, this.runTimeView, this.iDataPublishService);
    }
}

