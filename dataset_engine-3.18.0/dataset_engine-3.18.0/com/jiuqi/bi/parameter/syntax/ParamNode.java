/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.syntax;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.ParameterSelectMode;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;

public final class ParamNode
extends ASTNode {
    private static final long serialVersionUID = 1L;
    private final IParameterEnv paramEnv;
    private final ParameterModel paramModel;
    private String suffix;
    private int valueMode;
    public static final String SUFFIX_MAX = "MAX";
    public static final String SUFFIX_MIN = "MIN";
    public static final int DATATYPE_DATASET = 5700;
    public static final int VALUE_DEFAULT = 0;
    public static final int VALUE_KEY = 1;
    public static final int VALUE_NAME = 2;

    public ParamNode(Token token, IParameterEnv paramEnv, ParameterModel paramModel) {
        super(token);
        this.paramEnv = paramEnv;
        this.paramModel = paramModel;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public ParameterModel getParam() {
        return this.paramModel;
    }

    public int getValueMode() {
        return this.valueMode;
    }

    public void setValueMode(int valueMode) {
        this.valueMode = valueMode;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DATA;
    }

    public int getType(IContext context) throws SyntaxException {
        if (this.isNormalHierarchy()) {
            return 5700;
        }
        if (this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE || this.paramModel.isRangeParameter() && StringUtils.isEmpty((String)this.suffix)) {
            return 11;
        }
        return DataType.translateToSyntaxType(this.paramModel.getDataType());
    }

    public Object evaluate(IContext context) throws SyntaxException {
        if (this.isNormalHierarchy()) {
            throw new SyntaxException(this.getToken(), "\u65e0\u6cd5\u76f4\u63a5\u5bf9\u5217\u95f4\u5c42\u7ea7\u7684\u53c2\u6570\u8fdb\u884c\u64cd\u4f5c\u3002");
        }
        if (this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE) {
            return this.getArrayValue();
        }
        if (this.paramModel.isRangeParameter()) {
            return this.getRangeValue();
        }
        return this.getSingleValue();
    }

    public Object getParamValue(IContext context) throws SyntaxException {
        if (this.paramModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR && this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE) {
            Object value;
            try {
                value = this.paramEnv.getValue(this.paramModel.getName());
            }
            catch (ParameterException e) {
                throw new SyntaxException(this.getToken(), (Throwable)e);
            }
            if (value != null && !(value instanceof SmartSelector)) {
                throw new SyntaxException(this.getToken(), "\u53c2\u6570\u8fd4\u56de\u503c\u7c7b\u578b\u9519\u8bef\u3002");
            }
            return value;
        }
        return this.evaluate(context);
    }

    public Object getParamValueTitle(IContext context) throws SyntaxException {
        try {
            List<String> retVal = this.paramEnv.getNameValueAsString(this.paramModel.getName());
            return retVal == null || retVal.isEmpty() ? null : retVal.get(0);
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    private Object getRangeValue() throws SyntaxException {
        List<?> val = this.getRawValue();
        if (val == null) {
            return null;
        }
        if (StringUtils.isEmpty((String)this.suffix)) {
            ArrayData arr = new ArrayData(this.getArrayDataType(), 2);
            if (val.size() >= 1) {
                arr.set(0, val.get(0));
            }
            if (val.size() >= 2) {
                arr.set(1, val.get(1));
            }
            return arr;
        }
        if (SUFFIX_MIN.equalsIgnoreCase(this.suffix)) {
            return val.isEmpty() ? null : val.get(0);
        }
        if (SUFFIX_MAX.equalsIgnoreCase(this.suffix)) {
            return val.size() <= 1 ? null : val.get(1);
        }
        throw new SyntaxException(this.getToken(), "\u8303\u56f4\u53c2\u6570\u6ca1\u6709\u652f\u6301\u540e\u7f00[" + this.suffix + "]\u3002");
    }

    private Object getArrayValue() throws SyntaxException {
        List<?> values = this.getRawValue();
        if (values == null) {
            return null;
        }
        ArrayData arr = new ArrayData(this.getArrayDataType(), values.size());
        for (int i = 0; i < values.size(); ++i) {
            arr.set(i, values.get(i));
        }
        return arr;
    }

    private int getArrayDataType() {
        return this.valueMode == 2 ? 6 : DataType.translateToSyntaxType(this.paramModel.getDataType());
    }

    private Object getSingleValue() throws SyntaxException {
        List<?> values = this.getRawValue();
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    private List<?> getRawValue() throws SyntaxException {
        try {
            switch (this.valueMode) {
                case 0: {
                    return this.paramEnv.getValueAsList(this.paramModel.getName());
                }
                case 1: {
                    return this.paramEnv.getKeyValueAsList(this.paramModel.getName());
                }
                case 2: {
                    return this.paramEnv.getNameValueAsString(this.paramModel.getName());
                }
            }
        }
        catch (ParameterException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        throw new SyntaxException("\u672a\u652f\u6301\u7684\u53c2\u6570\u53d6\u503c\u6a21\u5f0f\uff1a" + this.valueMode);
    }

    private boolean isNormalHierarchy() {
        return this.paramModel.getDataSourceModel() != null && this.paramModel.getDataSourceModel().getHireachyType() == ParameterHierarchyType.NORMAL;
    }

    public boolean isStatic(IContext context) {
        return true;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.paramModel.getName());
        if (!StringUtils.isEmpty((String)this.suffix)) {
            buffer.append('.').append(this.suffix);
        }
    }

    public boolean support(Language lang) {
        return true;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.toString(buffer);
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (StringUtils.isEmpty((String)this.paramModel.getTitle())) {
            buffer.append(this.paramModel.getName());
        } else {
            buffer.append(this.paramModel.getTitle());
        }
        if (SUFFIX_MAX.equalsIgnoreCase(this.suffix)) {
            buffer.append("\u4e0a\u9650\u503c");
        } else if (SUFFIX_MIN.equalsIgnoreCase(this.suffix)) {
            buffer.append("\u4e0b\u9650\u503c");
        }
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        DataNode node;
        try {
            node = this.toDataNode(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        node.interpret(context, buffer, Language.SQL, (Object)info);
    }

    protected void toExcel(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        DataNode node;
        try {
            node = this.toDataNode(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        node.interpret(context, buffer, Language.EXCEL, info);
    }

    private DataNode toDataNode(IContext context) throws SyntaxException {
        int dataType = this.getType(context);
        Object dataValue = this.evaluate(context);
        return new DataNode(this.getToken(), dataType, dataValue);
    }
}

