/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DSUtils
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.dataset.format.BooleanFormat
 *  com.jiuqi.bi.dataset.format.TimeDimFormat
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.CalcMode
 *  com.jiuqi.bi.dataset.model.field.DSCalcField
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.text.CalendarFormatEx
 *  com.jiuqi.bi.text.DateFormatTransfer
 *  com.jiuqi.bi.text.DecimalFormat
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.format.BooleanFormat;
import com.jiuqi.bi.dataset.format.TimeDimFormat;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.context.IEvalTracer;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.QParserHelper;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.parser.field.AvgReader;
import com.jiuqi.bi.quickreport.engine.parser.field.CountReader;
import com.jiuqi.bi.quickreport.engine.parser.field.FirstReader;
import com.jiuqi.bi.quickreport.engine.parser.field.IFieldReader;
import com.jiuqi.bi.quickreport.engine.parser.field.LatestReader;
import com.jiuqi.bi.quickreport.engine.parser.field.MaxReader;
import com.jiuqi.bi.quickreport.engine.parser.field.MinReader;
import com.jiuqi.bi.quickreport.engine.parser.field.SumReader;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFilterBuilder;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.text.CalendarFormatEx;
import com.jiuqi.bi.text.DateFormatTransfer;
import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DSFieldNode
extends DynamicNode {
    private static final long serialVersionUID = -9020581758554583366L;
    private final DSModel dataset;
    private final DSField field;
    private final boolean fullName;
    private List<IASTNode> restrictions;
    private IFieldReader reader;

    public DSFieldNode(Token token, DSModel dataset, DSField field, boolean fullName) {
        super(token);
        this.dataset = dataset;
        this.field = field;
        this.fullName = fullName;
        this.restrictions = new ArrayList<IASTNode>();
    }

    public DSModel getDataSet() {
        return this.dataset;
    }

    public DSField getField() {
        return this.field;
    }

    public List<IASTNode> getRestrictions() {
        return this.restrictions;
    }

    public int getType(IContext context) throws SyntaxException {
        return DataType.translateToSyntaxType((int)this.field.getValType());
    }

    public Object evaluate(IContext context) throws SyntaxException {
        IEvalTracer tracer;
        BIDataSet ds;
        ReportContext rptContext = (ReportContext)context;
        List<IFilterDescriptor> filters = this.buildFilters(rptContext);
        try {
            ds = this.field instanceof DSCalcField && ((DSCalcField)this.field).getCalcMode() == CalcMode.AGGR_THEN_CALC ? rptContext.aggregateDataSet(this.dataset.getName(), filters) : rptContext.openDataSet(this.dataset.getName(), filters);
        }
        catch (ReportContextException e) {
            throw new SyntaxException((Throwable)e);
        }
        Object result = this.evalField(ds);
        result = this.reviseResult(rptContext, result);
        IEvalTracer iEvalTracer = tracer = rptContext.getCurrentCell() == null ? null : rptContext.getCurrentCell().getTracer();
        if (tracer != null) {
            tracer.evalNode((IASTNode)this, filters, result);
        }
        return result;
    }

    private Object reviseResult(ReportContext context, Object value) {
        if (value == null) {
            if (context.isExcelMode()) {
                switch (this.field.getValType()) {
                    case 3: {
                        return 0;
                    }
                    case 10: {
                        return BigDecimal.ZERO;
                    }
                }
            }
            return null;
        }
        if (this.field.getValType() == 10 && !(value instanceof BigDecimal)) {
            return new BigDecimal(((Number)value).doubleValue(), MathContext.DECIMAL64);
        }
        return value;
    }

    private List<IFilterDescriptor> buildFilters(ReportContext context) throws SyntaxException {
        DSFilterBuilder builder = new DSFilterBuilder(this.dataset.getName());
        builder.setContext(context);
        builder.getRestrictions().addAll(this.restrictions);
        try {
            builder.build();
        }
        catch (ReportExpressionException e) {
            throw new SyntaxException((Throwable)e);
        }
        return builder.getFilters();
    }

    private Object evalField(BIDataSet ds) throws SyntaxException {
        if (this.reader == null) {
            this.reader = this.createReader(ds);
        }
        return this.reader.read(ds);
    }

    private IFieldReader createReader(BIDataSet ds) throws SyntaxException {
        Column column = ds.getMetadata().find(this.field.getName());
        if (column == null) {
            throw new SyntaxException("\u67e5\u627e\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + this.toString());
        }
        switch (((BIDataSetFieldInfo)column.getInfo()).getFieldType()) {
            case GENERAL_DIM: {
                return this.createDimReader(ds, (Column<BIDataSetFieldInfo>)column);
            }
            case MEASURE: {
                return this.createMeasureReader(ds, (Column<BIDataSetFieldInfo>)column);
            }
        }
        return new FirstReader(column.getIndex());
    }

    private IFieldReader createDimReader(BIDataSet ds, Column<BIDataSetFieldInfo> column) {
        BIDataSetFieldInfo fieldInfo = (BIDataSetFieldInfo)column.getInfo();
        if (StringUtils.isEmpty((String)fieldInfo.getKeyField()) || fieldInfo.getName().equalsIgnoreCase(fieldInfo.getKeyField())) {
            return new FirstReader(column.getIndex());
        }
        Column timekey = ds.getMetadata().find("SYS_TIMEKEY");
        if (timekey == null) {
            return new FirstReader(column.getIndex());
        }
        return new LatestReader(column.getIndex(), timekey.getIndex());
    }

    private IFieldReader createMeasureReader(BIDataSet ds, Column<BIDataSetFieldInfo> column) {
        BIDataSetFieldInfo fieldInfo = (BIDataSetFieldInfo)column.getInfo();
        switch (fieldInfo.getAggregation()) {
            case SUM: {
                return fieldInfo.isNumber() ? new SumReader(column.getIndex()) : new FirstReader(column.getIndex());
            }
            case COUNT: {
                if (fieldInfo.getAggregation() == AggregationType.COUNT) {
                    return new SumReader(column.getIndex());
                }
                return new CountReader(column.getIndex());
            }
            case AVG: {
                return fieldInfo.isNumber() ? new AvgReader(column.getIndex()) : new FirstReader(column.getIndex());
            }
            case MIN: {
                return new MinReader(column.getIndex());
            }
            case MAX: {
                return new MaxReader(column.getIndex());
            }
        }
        return new FirstReader(column.getIndex());
    }

    public void toString(StringBuilder buffer) {
        if (!this.restrictions.isEmpty()) {
            buffer.append('[');
        }
        if (this.fullName && this.isValidName(this.dataset.getName())) {
            buffer.append(this.dataset.getName()).append('.').append(this.field.getName());
        } else {
            buffer.append(this.field.getName());
        }
        if (!this.restrictions.isEmpty()) {
            for (IASTNode restriction : this.restrictions) {
                buffer.append(", ").append(restriction);
            }
            buffer.append(']');
        }
    }

    private boolean isValidName(String name) {
        return !StringUtils.isEmpty((String)name) && name.indexOf(64) == -1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        String fieldExpr = null;
        if (info instanceof DSFormulaInfo) {
            DSFormulaInfo fmlInfo = (DSFormulaInfo)info;
            if (!fmlInfo.getCurDataSet().equalsIgnoreCase(this.dataset.getName())) {
                ReportContext rptCntx = (ReportContext)context;
                if (rptCntx.getCurrentCell() == null) {
                    QParserHelper.evalInterpret(context, (IASTNode)this, buffer);
                } else {
                    IEvalTracer tracer = rptCntx.getCurrentCell().setTracer(null);
                    try {
                        QParserHelper.evalInterpret(context, (IASTNode)this, buffer);
                    }
                    finally {
                        rptCntx.getCurrentCell().setTracer(tracer);
                    }
                }
                return;
            }
            if (fmlInfo.isRawFields() && this.field.isDimention() && this.field instanceof DSCalcField) {
                fieldExpr = ((DSCalcField)this.field).getFormula();
            }
        }
        if (!this.restrictions.isEmpty()) {
            buffer.append('[');
        }
        if (fieldExpr != null) {
            buffer.append('(').append(fieldExpr).append(')');
        } else if (this.fullName && this.isValidName(this.dataset.getName())) {
            buffer.append(this.dataset.getName()).append('.').append(this.field.getName());
        } else {
            buffer.append(this.field.getName());
        }
        if (!this.restrictions.isEmpty()) {
            for (IASTNode restriction : this.restrictions) {
                buffer.append(", ");
                restriction.interpret(context, buffer, Language.FORMULA, null);
            }
            buffer.append(']');
        }
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (StringUtils.isEmpty((String)this.field.getTitle())) {
            this.toString(buffer);
        } else {
            if (!this.restrictions.isEmpty()) {
                buffer.append('[');
            }
            if (this.fullName && this.isValidName(this.dataset.getName())) {
                buffer.append(this.dataset.getName()).append('.').append(this.field.getTitle());
            } else {
                buffer.append(this.field.getTitle());
            }
            if (!this.restrictions.isEmpty()) {
                for (IASTNode restriction : this.restrictions) {
                    buffer.append(", ");
                    restriction.interpret(context, buffer, Language.EXPLAIN, info);
                }
                buffer.append(']');
            }
        }
    }

    public Object clone() {
        DSFieldNode newNode = (DSFieldNode)((Object)super.clone());
        newNode.reader = null;
        newNode.restrictions = new ArrayList<IASTNode>(this.restrictions.size());
        for (IASTNode restriction : this.restrictions) {
            newNode.restrictions.add((IASTNode)restriction.clone());
        }
        return newNode;
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        if (StringUtils.isEmpty((String)this.field.getShowPattern())) {
            return null;
        }
        return new IDataFormator(){

            public Format getFormator(IContext context) throws SyntaxException {
                try {
                    if (DSFieldNode.this.field.getFieldType() == FieldType.TIME_DIM) {
                        TimeDimFormat format = new TimeDimFormat(DSUtils.transform((DSField)DSFieldNode.this.field), DateFormatTransfer.getLocale4Date((Locale)DSFieldNode.this.getLocale(context)));
                        if (DSFieldNode.this.dataset.getMinFiscalMonth() >= 0 && DSFieldNode.this.dataset.getMaxFiscalMonth() >= 12) {
                            format.setFiscalMonth(DSFieldNode.this.dataset.getMinFiscalMonth(), DSFieldNode.this.dataset.getMaxFiscalMonth());
                        }
                        return format;
                    }
                    if (DataTypes.isNumber((int)DSFieldNode.this.field.getValType()) && !StringUtils.isEmpty((String)DSFieldNode.this.field.getShowPattern())) {
                        return new DecimalFormat(DSFieldNode.this.field.getShowPattern());
                    }
                    if (DSFieldNode.this.field.getValType() == 2) {
                        return new CalendarFormatEx(DSFieldNode.this.field.getShowPattern(), DateFormatTransfer.getLocale4Date((Locale)DSFieldNode.this.getLocale(context)));
                    }
                    if (DSFieldNode.this.field.getValType() == 1) {
                        return new BooleanFormat(DSUtils.transform((DSField)DSFieldNode.this.field));
                    }
                    return null;
                }
                catch (IllegalArgumentException e) {
                    throw new SyntaxException(DSFieldNode.this.dataset.getName() + "." + DSFieldNode.this.field.getName() + "\u5b57\u6bb5\u663e\u793a\u683c\u5f0f\u9519\u8bef\uff1a" + DSFieldNode.this.field.getShowPattern(), (Throwable)e);
                }
            }

            public String getPattern(IContext context) throws SyntaxException {
                return DSFieldNode.this.field.getShowPattern();
            }
        };
    }

    private Locale getLocale(IContext context) {
        Locale locale = Locale.getDefault();
        if (!(context instanceof ReportContext)) {
            return locale;
        }
        String lang = ((ReportContext)context).getLanguage();
        if (StringUtils.isEmpty((String)lang)) {
            return locale;
        }
        return Locale.forLanguageTag(lang);
    }
}

