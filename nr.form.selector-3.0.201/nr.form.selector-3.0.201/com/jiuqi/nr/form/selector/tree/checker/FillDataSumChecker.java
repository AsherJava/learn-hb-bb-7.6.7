/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.FillDataSumExcutor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FillDataSumChecker
implements IReportFormChecker {
    @Override
    public String getCheckerId() {
        return "fill-Data-Sum";
    }

    @Override
    public String getShowText() {
        return "\u8fc7\u6ee4\u65e0\u6c47\u603b\u6743\u9650\u7684\u8868";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new FillDataSumExcutor();
    }
}

