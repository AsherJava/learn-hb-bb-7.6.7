/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
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

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
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

public class TRestrictNode
extends DynamicNode
implements IDSNodeDescriptor,
IAdjustable {
    private static final long serialVersionUID = 1L;
    private final DSModel dsModel;
    private final BIDataSetFieldInfo fieldInfo;
    private final List<IASTNode> restricts;

    public TRestrictNode(Token token, DSModel dsModel, BIDataSetFieldInfo fieldInfo, List<IASTNode> restrictItems) {
        super(token);
        this.dsModel = dsModel;
        this.fieldInfo = fieldInfo;
        this.restricts = restrictItems == null ? new ArrayList<IASTNode>() : new ArrayList<IASTNode>(restrictItems);
    }

    public Object evaluate(IContext context) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        DSNode dsNode = new DSNode(null, this.dsModel);
        String lang = tfc.getI18nLang();
        Locale locale = StringUtils.isEmpty((String)lang) ? Locale.getDefault() : Locale.forLanguageTag(lang);
        BIDataSetImpl dataset = helper.evaluate((IASTNode)dsNode, this.restricts, locale);
        if (dataset.getRecordCount() == 0) {
            return null;
        }
        FieldType fType = this.fieldInfo.getFieldType();
        if (fType.isDimField()) {
            Column column = dataset.getMetadata().find(this.fieldInfo.getName());
            return dataset.get(0).getValue(column.getIndex());
        }
        try {
            dataset = (BIDataSetImpl)dataset.aggregate(new ArrayList<String>());
            Column column = dataset.getMetadata().find(this.fieldInfo.getName());
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (info.isCalcField() && info.getCalcMode() == CalcMode.AGGR_THEN_CALC) {
                dataset.doCalcField(new int[]{column.getIndex()});
            }
            return dataset.get(0).getValue(column.getIndex());
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public int getType(IContext context) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        for (int i = 0; i < this.restricts.size(); ++i) {
            IASTNode param = this.restricts.get(i);
            this.checkField(param);
            param = helper.adjust(this.dsModel.getName(), param);
            this.restricts.set(i, param);
        }
        DataType type = DataType.valueOf(this.fieldInfo.getValType());
        if (type == DataType.UNKNOWN) {
            return 0;
        }
        return DataType.translateToSyntaxType(type);
    }

    public void toString(StringBuilder buffer) {
        buffer.append("[").append(this.dsModel.getName());
        buffer.append(".").append(this.fieldInfo.getName());
        if (this.restricts.size() > 0) {
            for (int i = 0; i < this.restricts.size(); ++i) {
                buffer.append(", ").append(this.restricts.get(i).toString());
            }
        }
        buffer.append("]");
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.toString(buffer);
    }

    public int validate(IContext context) throws SyntaxException {
        return super.validate(context);
    }

    private void checkField(IASTNode root) throws SyntaxException {
        for (IASTNode node : root) {
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode fieldNode = (DSFieldNode)node;
            DSField fd = this.find(this.dsModel, fieldNode.getName());
            if (fd == null) {
                throw new SyntaxException("\u6570\u636e\u96c6" + this.dsModel.getName() + "\u4e2d\u4e0d\u5b58\u5728\u5b57\u6bb5" + fieldNode.getName());
            }
            fieldNode.getFieldInfo().loadFromDSField(fd);
        }
    }

    private DSField find(DSModel dsModel, String fieldName) {
        List<DSField> fields = dsModel.getFields();
        for (DSField field : fields) {
            if (!field.getName().equalsIgnoreCase(fieldName)) continue;
            return field;
        }
        return null;
    }

    @Override
    public boolean isAdjustable(String dsName) {
        return false;
    }

    @Override
    public IASTNode adjust() {
        return null;
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        if (StringUtils.isEmpty((String)this.fieldInfo.getShowPattern())) {
            return null;
        }
        return new IDataFormator(){

            public Format getFormator(IContext context) {
                String lang = null;
                if (context instanceof DSContext) {
                    lang = ((DSContext)context).getI18nLang();
                } else if (context instanceof TextFormulaContext) {
                    lang = ((TextFormulaContext)context).getI18nLang();
                }
                Locale locale = StringUtils.isEmpty((String)lang) ? Locale.getDefault() : Locale.forLanguageTag(lang);
                DSField field = TRestrictNode.this.dsModel.findField(TRestrictNode.this.fieldInfo.getName());
                if (field == null) {
                    return null;
                }
                return DSUtils.generateFormat(TRestrictNode.this.dsModel, field, locale);
            }
        };
    }

    @Override
    public DSModel getDSModel() {
        return this.dsModel;
    }

    public List<IASTNode> getRestricts() {
        return this.restricts;
    }
}

