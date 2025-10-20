/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.format.IFormatable
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSTimeOffstBuilder;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionDescriptor;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.format.IFormatable;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class GrowthRateFunction
extends Function
implements IFormatable {
    private static final long serialVersionUID = 1L;

    public GrowthRateFunction() {
        this.parameters().add(new Parameter("expression", 3, "\u5ea6\u91cf\u5b57\u6bb5\u6216\u8868\u8fbe\u5f0f"));
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u589e\u957f\u7387\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        boolean found = false;
        for (IASTNode node : parameters.get(0)) {
            if (!(node instanceof DSFieldNode)) continue;
            found = true;
            break;
        }
        if (!found) {
            throw new SyntaxException(parameters.get(0).getToken(), this.name() + "()\u51fd\u6570\u4f20\u5165\u7684\u5ea6\u91cf\u672a\u5f15\u7528\u4efb\u4f55\u6570\u636e\u96c6\u5b57\u6bb5");
        }
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Number curValue = (Number)parameters.get(0).evaluate(context);
        if (curValue == null) {
            return null;
        }
        Number prevValue = this.evalPrev((ReportContext)context, parameters.get(0));
        if (prevValue == null || DataType.isZero((double)prevValue.doubleValue())) {
            return null;
        }
        double rate = (curValue.doubleValue() - prevValue.doubleValue()) / prevValue.doubleValue();
        return rate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Number evalPrev(ReportContext context, IASTNode measure) throws SyntaxException {
        List<TimeFilterInfo> timeFilters = this.createTimeOffsetFilters(context);
        this.resetTimeFilters(context, timeFilters);
        try {
            Number number = (Number)measure.evaluate((IContext)context);
            return number;
        }
        finally {
            this.restoreTimeFilters(context, timeFilters);
        }
    }

    private List<TimeFilterInfo> createTimeOffsetFilters(ReportContext rptContext) throws SyntaxException {
        ArrayList<TimeFilterInfo> timeFilters = new ArrayList<TimeFilterInfo>();
        ArrayList<DSTimeOffstBuilder.Locator> locators = new ArrayList<DSTimeOffstBuilder.Locator>();
        DSTimeOffstBuilder offsetBuilder = new DSTimeOffstBuilder(rptContext);
        for (int i = 0; i < rptContext.getCurrentFilters().size(); ++i) {
            IFilterDescriptor filter = rptContext.getCurrentFilters().get(i);
            if (filter.getField() == null || filter.getField().getFieldType() != FieldType.TIME_DIM) continue;
            timeFilters.add(new TimeFilterInfo(i, filter));
            DSTimeOffstBuilder.Locator locator = offsetBuilder.addFilter(filter);
            locators.add(locator);
        }
        RestrictionDescriptor restriction = this.createTimeOffsetter(offsetBuilder.getFilters().keySet());
        try {
            offsetBuilder.build(restriction);
        }
        catch (ReportExpressionException e) {
            throw new SyntaxException((Throwable)e);
        }
        for (int i = 0; i < timeFilters.size(); ++i) {
            TimeFilterInfo timeFilter = (TimeFilterInfo)timeFilters.get(i);
            timeFilter.offsetFilter = offsetBuilder.getFilter((DSTimeOffstBuilder.Locator)locators.get(i));
        }
        return timeFilters;
    }

    protected abstract RestrictionDescriptor createTimeOffsetter(Collection<DSFieldInfo> var1) throws SyntaxException;

    protected DSFieldInfo findYearField(Collection<DSFieldInfo> timeFields) {
        for (DSFieldInfo timeField : timeFields) {
            if (timeField.field.getTimegranularity() != TimeGranularity.YEAR) continue;
            return timeField;
        }
        return null;
    }

    protected DSFieldInfo findDetailField(Collection<DSFieldInfo> timeFields) {
        DSFieldInfo detailField = null;
        for (DSFieldInfo timeField : timeFields) {
            if (timeField.field.isTimekey()) {
                return timeField;
            }
            if (detailField != null && detailField.field.getTimegranularity().days() <= timeField.field.getTimegranularity().days()) continue;
            detailField = timeField;
        }
        return detailField;
    }

    private void resetTimeFilters(ReportContext rptContext, List<TimeFilterInfo> timeFilters) {
        for (TimeFilterInfo timeFilter : timeFilters) {
            rptContext.getCurrentFilters().set(timeFilter.index, timeFilter.offsetFilter);
        }
    }

    private void restoreTimeFilters(ReportContext rptContext, List<TimeFilterInfo> timeFilters) {
        for (TimeFilterInfo timeFilter : timeFilters) {
            rptContext.getCurrentFilters().set(timeFilter.index, timeFilter.rawFilter);
        }
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return new IDataFormator(){

            public Format getFormator(IContext context) throws SyntaxException {
                return new DecimalFormat("#,##0.00%");
            }
        };
    }

    private static final class TimeFilterInfo {
        public final int index;
        public final IFilterDescriptor rawFilter;
        public IFilterDescriptor offsetFilter;

        public TimeFilterInfo(int index, IFilterDescriptor rawFilter) {
            this.index = index;
            this.rawFilter = rawFilter;
        }

        public String toString() {
            return this.offsetFilter == null ? this.rawFilter.toString() : this.offsetFilter.toString();
        }
    }
}

