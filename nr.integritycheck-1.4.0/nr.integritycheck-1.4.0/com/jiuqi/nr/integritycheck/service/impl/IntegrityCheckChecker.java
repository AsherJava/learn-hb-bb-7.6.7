/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.form.selector.context.IFormQueryContext
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 *  com.jiuqi.nr.form.selector.tree.IReportFormChecker
 */
package com.jiuqi.nr.integritycheck.service.impl;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.integritycheck.helper.FormOperationHelper;
import com.jiuqi.nr.integritycheck.service.impl.IntegrityCheckExecutor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegrityCheckChecker
implements IReportFormChecker {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FormOperationHelper fHelper;

    public String getCheckerId() {
        return "integrity-check-checker";
    }

    public String getShowText() {
        return "\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u6279\u91cf\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e\u62a5\u8868\u8fc7\u6ee4\u5668";
    }

    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new IntegrityCheckExecutor(context, this.runTimeViewController, this.fHelper);
    }
}

