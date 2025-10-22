/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.PrintExecutor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PrintChecker
implements IReportFormChecker {
    public static final String CHECKER_ID = "printCheker";

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u6253\u5370\u529f\u80fd\u8868\u5355\u8fc7\u6ee4\u5668";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new PrintExecutor(context);
    }
}

