/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.service.IFormDataStatusService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.dataentry.service.IFormDataStatusService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.checkExecutor.FillStateCheckExecutor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FillStateChecker
implements IReportFormChecker {
    public static final String CHECKER_ID = "fill-state-checker";
    public static final String FILLED_CHECKER_ID = "filled-state-checker";
    public static final String UNFILLED_CHECKER_ID = "unfill-state-checker";
    @Autowired
    private IFormDataStatusService formDataStatusService;
    @Autowired
    private IRunTimeViewController runTimeView;

    @Override
    public String getCheckerId() {
        return CHECKER_ID;
    }

    @Override
    public String getShowText() {
        return "\u586b\u62a5\u72b6\u6001";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        ArrayList<IReportFormChecker> reportFormCheckers = new ArrayList<IReportFormChecker>();
        IReportFormChecker filled = new IReportFormChecker(){

            @Override
            public String getCheckerId() {
                return FillStateChecker.FILLED_CHECKER_ID;
            }

            @Override
            public String getShowText() {
                return "\u5df2\u586b\u62a5";
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
                return new FillStateCheckExecutor(context, FillStateChecker.this.runTimeView, FillStateChecker.this.formDataStatusService, FillStateChecker.FILLED_CHECKER_ID);
            }
        };
        reportFormCheckers.add(filled);
        IReportFormChecker unFill = new IReportFormChecker(){

            @Override
            public String getCheckerId() {
                return FillStateChecker.UNFILLED_CHECKER_ID;
            }

            @Override
            public String getShowText() {
                return "\u672a\u586b\u62a5";
            }

            @Override
            public boolean isDisplay() {
                return true;
            }

            @Override
            public int getOrdinary() {
                return 1;
            }

            @Override
            public List<IReportFormChecker> getGroupChecker() {
                return null;
            }

            @Override
            public IFormCheckExecutor getExecutor(IFormQueryContext context) {
                return new FillStateCheckExecutor(context, FillStateChecker.this.runTimeView, FillStateChecker.this.formDataStatusService, FillStateChecker.UNFILLED_CHECKER_ID);
            }
        };
        reportFormCheckers.add(unFill);
        return reportFormCheckers;
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return new FillStateCheckExecutor(context, this.runTimeView, this.formDataStatusService, CHECKER_ID);
    }
}

