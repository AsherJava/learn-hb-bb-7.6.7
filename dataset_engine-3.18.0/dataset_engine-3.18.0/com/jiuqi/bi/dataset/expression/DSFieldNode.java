/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.text.CalendarFormatEx
 *  com.jiuqi.bi.text.DateFormatTransfer
 *  com.jiuqi.bi.text.DecimalFormat
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.format.BooleanFormat;
import com.jiuqi.bi.dataset.format.TimeDimFormat;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.text.CalendarFormatEx;
import com.jiuqi.bi.text.DateFormatTransfer;
import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.util.StringUtils;
import java.text.Format;
import java.util.Locale;

public class DSFieldNode
extends DynamicNode {
    private static final long serialVersionUID = 1L;
    private BIDataSetFieldInfo info;
    private int valueMode;
    public static final int VALUE_DEFAULT = 0;
    public static final int VALUE_KEY = 1;
    public static final int VALUE_NAME = 2;

    public DSFieldNode(Token token, BIDataSetFieldInfo fieldInfo) {
        super(token);
        this.info = fieldInfo;
    }

    public BIDataSetFieldInfo getFieldInfo() {
        return this.info;
    }

    public String getName() {
        return this.info.getName();
    }

    public int getValueMode() {
        return this.valueMode;
    }

    public void setValueMode(int valueMode) {
        this.valueMode = valueMode;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        DSFormulaContext fc = (DSFormulaContext)context;
        BIDataSetImpl dataset = fc.getDataSet();
        String name = this.info.getName();
        Metadata<BIDataSetFieldInfo> metadata = dataset.getMetadata();
        Column col = metadata.find(name);
        if (col == null) {
            throw new SyntaxException("\u5b57\u6bb5\u3010" + name + "\u3011\u4e0d\u5b58\u5728");
        }
        BIDataRow curDataRow = fc.getCurRow();
        if (curDataRow != null) {
            return this.formatValue(context, curDataRow.getValue(col.getIndex()));
        }
        int count = dataset.getRecordCount();
        if (count == 0) {
            return null;
        }
        if (count == 1) {
            return this.formatValue(context, dataset.get(0).getValue(col.getIndex()));
        }
        FieldType fType = ((BIDataSetFieldInfo)col.getInfo()).getFieldType();
        if (fType.isMeasureField()) {
            try {
                return dataset.sum(name);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        return dataset.get(0).getValue(col.getIndex());
    }

    private Object formatValue(IContext context, Object value) throws SyntaxException {
        if (value == null) {
            return null;
        }
        switch (this.valueMode) {
            case 0: 
            case 1: {
                return value;
            }
            case 2: {
                IDataFormator formator = this.getDataFormator(context);
                if (formator == null) {
                    return value;
                }
                Format f = formator.getFormator(context);
                return f == null ? value : f.format(value);
            }
        }
        return value;
    }

    public int getType(IContext context) throws SyntaxException {
        Format f;
        IDataFormator formator;
        if (this.valueMode == 2 && (formator = this.getDataFormator(context)) != null && (f = formator.getFormator(context)) != null) {
            return DataType.STRING.value();
        }
        DataType type = DataType.valueOf(this.info.getValType());
        if (type == DataType.UNKNOWN) {
            return 0;
        }
        return DataType.translateToSyntaxType(type);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.info.getName());
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(StringUtils.isEmpty((String)this.info.getTitle()) ? this.info.getName() : this.info.getTitle());
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.info.getName());
    }

    public IDataFormator getDataFormator(IContext context) {
        if (StringUtils.isEmpty((String)this.info.getShowPattern())) {
            return null;
        }
        return new IDataFormator(){

            public Format getFormator(IContext context) {
                if (DSFieldNode.this.info.getFieldType() == FieldType.TIME_DIM) {
                    return new TimeDimFormat(DSFieldNode.this.info, DateFormatTransfer.getLocale4Date((Locale)DSFieldNode.this.getLocale(context)));
                }
                if (DataTypes.isNumber((int)DSFieldNode.this.info.getValType()) && !StringUtils.isEmpty((String)DSFieldNode.this.info.getShowPattern())) {
                    return new DecimalFormat(DSFieldNode.this.info.getShowPattern());
                }
                if (DSFieldNode.this.info.getValType() == 2) {
                    return new CalendarFormatEx(DSFieldNode.this.info.getShowPattern(), DateFormatTransfer.getLocale4Date((Locale)DSFieldNode.this.getLocale(context)));
                }
                if (DSFieldNode.this.info.getValType() == 1) {
                    return new BooleanFormat(DSFieldNode.this.info);
                }
                return null;
            }
        };
    }

    private Locale getLocale(IContext context) {
        Locale locale = Locale.getDefault();
        String lang = null;
        if (context instanceof DSContext) {
            lang = ((DSContext)context).getI18nLang();
        } else if (context instanceof DSFormulaContext) {
            lang = ((DSFormulaContext)context).getLanguage();
        } else if (context instanceof TextFormulaContext) {
            lang = ((TextFormulaContext)context).getI18nLang();
        }
        if (StringUtils.isEmpty((String)lang)) {
            return locale;
        }
        return Locale.forLanguageTag(lang);
    }
}

