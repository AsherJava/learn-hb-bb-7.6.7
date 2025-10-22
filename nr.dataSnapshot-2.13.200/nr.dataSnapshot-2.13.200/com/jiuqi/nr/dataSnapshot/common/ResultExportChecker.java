/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.form.selector.context.IFormQueryContext
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 *  com.jiuqi.nr.form.selector.tree.IReportFormChecker
 */
package com.jiuqi.nr.dataSnapshot.common;

import com.jiuqi.nr.dataSnapshot.common.ResultExportExecutor;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResultExportChecker
implements IReportFormChecker {
    public static final String CHECKERID = "DataSnapshotComparisonResultExportChecker";

    public String getCheckerId() {
        return CHECKERID;
    }

    public String getShowText() {
        return "\u5feb\u7167\u5bf9\u6bd4\u7ed3\u679c\u5bfc\u51fa\u529f\u80fd\u8868\u5355\u8fc7\u6ee4\u5668";
    }

    public List<IReportFormChecker> getGroupChecker() {
        return null;
    }

    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new ResultExportExecutor(context);
    }
}

