/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.JustHaveGroupNodeExcutor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JustHaveGroupNodeChecker
implements IReportFormChecker {
    public static final String CHECKER_ID = "just-have-groupNode-checker";

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u53ea\u5c55\u793a\u5206\u7ec4\u8282\u70b9";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new JustHaveGroupNodeExcutor();
    }
}

