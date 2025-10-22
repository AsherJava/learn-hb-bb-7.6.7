/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.form.selector.context.IFormQueryContext
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 *  com.jiuqi.nr.form.selector.tree.IReportFormChecker
 */
package com.jiuqi.nr.integritycheck.tree.checker;

import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.integritycheck.tree.checkExecutor.FillTableIntegrityExcutor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FillTableIntegrityChecker
implements IReportFormChecker {
    public String getCheckerId() {
        return "fill-table-integrity";
    }

    public String getShowText() {
        return "\u8fc7\u6ee4\u65e0\u9700\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7684\u8868\u5355";
    }

    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new FillTableIntegrityExcutor();
    }
}

