/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.textprocessor.parse.node;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IAdjustable;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IDSNodeDescriptor;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TFieldNode
extends DynamicNode
implements IDSNodeDescriptor,
IAdjustable {
    private static final long serialVersionUID = 1L;
    private final DSModel dsModel;
    private final BIDataSetFieldInfo info;
    private int valueMode;
    public static final int VALUE_DEFAULT = 0;
    public static final int VALUE_KEY = 1;
    public static final int VALUE_NAME = 2;

    public TFieldNode(Token token, DSModel dsModel, BIDataSetFieldInfo fieldInfo) {
        super(token);
        this.info = fieldInfo;
        this.dsModel = dsModel;
    }

    @Override
    public boolean isAdjustable(String dsName) {
        if (dsName == null) {
            return true;
        }
        return this.dsModel.getName().equalsIgnoreCase(dsName);
    }

    @Override
    public IASTNode adjust() {
        return new DSFieldNode(this.token, this.info);
    }

    public BIDataSetFieldInfo getInfo() {
        return this.info;
    }

    public int getValueMode() {
        return this.valueMode;
    }

    public void setValueMode(int valueMode) {
        this.valueMode = valueMode;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        int count;
        BIDataSet dataset;
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        try {
            dataset = helper.openDataset(this.dsModel);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException("\u6253\u5f00\u6570\u636e\u96c6\u51fa\u9519\uff0c" + e.getMessage(), (Throwable)e);
        }
        List<FilterItem> globalFilter = tfc.getFormulaFilter(this.dsModel.getName());
        if (globalFilter != null && globalFilter.size() > 0) {
            try {
                dataset = (BIDataSetImpl)dataset.filter(globalFilter);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        String name = this.info.getName();
        Metadata<BIDataSetFieldInfo> metadata = dataset.getMetadata();
        Column col = metadata.find(name);
        if (col == null) {
            throw new SyntaxException("\u5b57\u6bb5\u3010" + this.dsModel.getName() + "." + name + "\u3011\u4e0d\u5b58\u5728");
        }
        if (this.info.isCalcField() && this.info.getCalcMode() == CalcMode.AGGR_THEN_CALC) {
            try {
                dataset = dataset.aggregate(new ArrayList<String>());
                col = dataset.getMetadata().find(name);
                ArrayList<Integer> colIdxList = new ArrayList<Integer>();
                colIdxList.add(col.getIndex());
                dataset.compute(colIdxList);
            }
            catch (BIDataSetException e1) {
                throw new SyntaxException((Throwable)e1);
            }
        }
        if ((count = dataset.getRecordCount()) == 0) {
            return null;
        }
        if (count == 1) {
            return this.formatValue(context, dataset.get(0).getValue(col.getIndex()));
        }
        FieldType fType = ((BIDataSetFieldInfo)col.getInfo()).getFieldType();
        if (fType.isMeasureField()) {
            try {
                return this.formatValue(context, dataset.sum(name));
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        return this.formatValue(context, dataset.get(0).getValue(col.getIndex()));
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

    public void toString(StringBuilder buffer) {
        buffer.append(this.dsModel.getName()).append(".").append(this.info.getName());
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.dsModel.getName()).append(".").append(this.info.getName());
    }

    public IDataFormator getDataFormator(IContext context) {
        if (StringUtils.isEmpty((String)this.info.getShowPattern())) {
            return null;
        }
        return new IDataFormator(){

            public Format getFormator(IContext context) {
                String lang = null;
                if (context instanceof DSContext) {
                    lang = ((DSContext)context).getI18nLang();
                } else if (context instanceof TextFormulaContext) {
                    lang = ((TextFormulaContext)context).getI18nLang();
                } else if (context instanceof DSFormulaContext) {
                    lang = ((DSFormulaContext)context).getLanguage();
                }
                Locale locale = StringUtils.isEmpty((String)lang) ? Locale.getDefault() : Locale.forLanguageTag(lang);
                DSField field = TFieldNode.this.dsModel.findField(TFieldNode.this.info.getName());
                if (field == null) {
                    return null;
                }
                return DSUtils.generateFormat(TFieldNode.this.dsModel, field, locale);
            }
        };
    }

    @Override
    public DSModel getDSModel() {
        return this.dsModel;
    }
}

