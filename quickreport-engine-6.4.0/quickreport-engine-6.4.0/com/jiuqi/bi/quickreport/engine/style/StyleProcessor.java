/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.style;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.ConditionStyle;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class StyleProcessor<T extends ConditionStyle>
implements IStyleProcessor {
    private final IStyleProcessor next;
    private final List<StyleEntry<T>> entries;

    public StyleProcessor(IStyleProcessor next) {
        this.next = next;
        this.entries = new ArrayList<StyleEntry<T>>();
    }

    public final void add(IReportExpression filter, T style) {
        this.entries.add(new StyleEntry<T>(filter, style));
    }

    @Override
    public final void process(IContext context, IWorksheet worksheet, Position position) throws ReportStyleException {
        for (StyleEntry<T> entry : this.entries) {
            if (!this.testCondition(context, entry.filter)) continue;
            this.applyCellStyle((EngineWorksheet)worksheet, position, entry.style);
            this.applyCellComment((EngineWorksheet)worksheet, position, entry.style);
            break;
        }
    }

    @Override
    public final List<IReportExpression> getConditions() {
        return this.entries.stream().map(e -> e.filter).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Object getStyleInfo() {
        return null;
    }

    @Override
    public final IStyleProcessor next() {
        return this.next;
    }

    protected abstract void applyCellStyle(EngineWorksheet var1, Position var2, T var3) throws ReportStyleException;

    protected boolean testCondition(IContext context, IReportExpression expression) throws ReportStyleException {
        if (expression == null) {
            return true;
        }
        try {
            return expression.judge(context);
        }
        catch (ReportExpressionException e) {
            throw new ReportStyleException("\u5224\u65ad\u6761\u4ef6" + expression.toString() + "\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private void applyCellComment(EngineWorksheet worksheet, Position position, T style) {
        if (StringUtils.isEmpty((String)((ConditionStyle)style).getComment())) {
            return;
        }
        GridData grid = worksheet.getResultGrid().getGridData();
        CellValue cellValue = (CellValue)grid.getObj(position.col(), position.row());
        if (cellValue == null) {
            return;
        }
        cellValue.setComment(((ConditionStyle)style).getComment());
    }

    public static void applyStyles(IContext context, IWorksheet worksheet, Position position, CellBindingInfo bindingInfo) throws ReportStyleException {
        for (IStyleProcessor processor = bindingInfo.getStyleProcessor(); processor != null; processor = processor.next()) {
            processor.process(context, worksheet, position);
        }
    }

    private static class StyleEntry<T extends ConditionStyle> {
        public final IReportExpression filter;
        public final T style;

        public StyleEntry(IReportExpression filter, T style) {
            this.filter = filter;
            this.style = style;
        }
    }
}

