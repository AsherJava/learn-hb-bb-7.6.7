/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.service.IFormLockService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.LockStateCheckExecutor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockStateChecker
implements IReportFormChecker {
    public static final String CHECKER_ID = "lock-state-checker";
    public static final String LOCKED_CHECKER_ID = "locked-checker";
    public static final String UNLOCK_CHECKER_ID = "unlock-checker";
    @Autowired
    private IFormLockService formLockService;
    @Autowired
    private IRunTimeViewController runTimeView;

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u9501\u5b9a\u72b6\u6001";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        ArrayList<IReportFormChecker> reportFormCheckers = new ArrayList<IReportFormChecker>();
        IReportFormChecker locked = new IReportFormChecker(){

            @Override
            public String getCheckerId() {
                return LockStateChecker.LOCKED_CHECKER_ID;
            }

            @Override
            public String getShowText() {
                return "\u5df2\u9501\u5b9a";
            }

            @Override
            public boolean isDisplay() {
                return true;
            }

            @Override
            public List<IReportFormChecker> getGroupChecker() {
                return null;
            }

            @Override
            public IFormCheckExecutor getExecutor(IFormQueryContext context) {
                return new LockStateCheckExecutor(context, LockStateChecker.this.runTimeView, LockStateChecker.this.formLockService, LockStateChecker.LOCKED_CHECKER_ID);
            }
        };
        reportFormCheckers.add(locked);
        IReportFormChecker unlock = new IReportFormChecker(){

            @Override
            public String getCheckerId() {
                return LockStateChecker.UNLOCK_CHECKER_ID;
            }

            @Override
            public String getShowText() {
                return "\u672a\u9501\u5b9a";
            }

            @Override
            public boolean isDisplay() {
                return true;
            }

            @Override
            public List<IReportFormChecker> getGroupChecker() {
                return null;
            }

            @Override
            public IFormCheckExecutor getExecutor(IFormQueryContext context) {
                return new LockStateCheckExecutor(context, LockStateChecker.this.runTimeView, LockStateChecker.this.formLockService, LockStateChecker.UNLOCK_CHECKER_ID);
            }
        };
        reportFormCheckers.add(unlock);
        return reportFormCheckers;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new LockStateCheckExecutor(context, this.runTimeView, this.formLockService, CHECKER_ID);
    }
}

