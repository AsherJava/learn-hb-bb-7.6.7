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
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nvwa.framework.parameter.syntax;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeDataFormator;
import java.text.Format;
import java.util.ArrayList;
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
        Format f;
        IDataFormator formator;
        if (this.isArrayMode()) {
            return 11;
        }
        if (this.valueMode == 2 && (formator = this.getDataFormator(context)) != null && (f = formator.getFormator(context)) != null) {
            return 6;
        }
        if (this.paramModel.getDataType() == 5) {
            return 3;
        }
        return this.paramModel.getDataType();
    }

    private boolean isArrayMode() {
        return this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE || this.paramModel.isRangeParameter() && StringUtils.isEmpty((String)this.suffix);
    }

    public Object evaluate(IContext context) throws SyntaxException {
        if (this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE) {
            return this.getArrayValue();
        }
        if (this.paramModel.isRangeParameter()) {
            return this.getRangeValue();
        }
        return this.getSingleValue();
    }

    public Object getParamValue(IContext context) throws SyntaxException {
        try {
            AbstractParameterValue value = this.paramEnv.getOriginalValue(this.paramModel.getName());
            if (value != null) {
                if (value instanceof SmartSelectorParameterValue) {
                    return value.getValue();
                }
                IParameterValueFormat format = ParameterUtils.createValueFormat(this.paramModel.getDatasource());
                List<Object> listVal = value.toValueList(format);
                if (listVal.isEmpty()) {
                    return null;
                }
                if (this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE) {
                    return new ArrayData(this.paramModel.getDataType(), listVal);
                }
                if (this.paramModel.getSelectMode() == ParameterSelectMode.RANGE) {
                    if (StringUtils.isEmpty((String)this.suffix)) {
                        return new ArrayData(this.paramModel.getDataType(), listVal);
                    }
                    if (SUFFIX_MIN.equalsIgnoreCase(this.suffix)) {
                        return listVal.get(0);
                    }
                    if (SUFFIX_MAX.equalsIgnoreCase(this.suffix)) {
                        return listVal.size() <= 1 ? null : listVal.get(1);
                    }
                    throw new SyntaxException(this.getToken(), "\u8303\u56f4\u53c2\u6570\u6ca1\u6709\u652f\u6301\u540e\u7f00[" + this.suffix + "]\u3002");
                }
                return listVal.get(0);
            }
        }
        catch (ParameterException e) {
            throw new SyntaxException(this.getToken(), (Throwable)e);
        }
        return this.evaluate(context);
    }

    public Object getParamValueTitle(IContext context) throws SyntaxException {
        ParameterResultset rs;
        try {
            rs = this.paramEnv.getValue(this.paramModel.getName());
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        if (rs == null || rs.isEmpty()) {
            return null;
        }
        List<String> names = rs.getNameValueAsList();
        if (SUFFIX_MIN.equalsIgnoreCase(this.suffix)) {
            return names.get(0);
        }
        if (SUFFIX_MAX.equalsIgnoreCase(this.suffix)) {
            return names.size() < 2 ? null : names.get(1);
        }
        return this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE ? new ArrayData(6, names) : names.get(0);
    }

    private Object getRangeValue() throws SyntaxException {
        List<?> val = this.getValues();
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
        List<?> values = this.getValues();
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
        if (this.valueMode == 2) {
            return 6;
        }
        if (this.paramModel.getDataType() == 5) {
            return 3;
        }
        return this.paramModel.getDataType();
    }

    private Object getSingleValue() throws SyntaxException {
        List<?> values = this.getValues();
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    private List<?> getValues() throws SyntaxException {
        switch (this.valueMode) {
            case 0: 
            case 1: {
                try {
                    return this.paramEnv.getValueAsList(this.paramModel.getName());
                }
                catch (ParameterException e) {
                    throw new SyntaxException((Throwable)e);
                }
            }
            case 2: {
                ParameterResultset result;
                try {
                    result = this.paramEnv.getValue(this.paramModel.getName());
                }
                catch (ParameterException e) {
                    throw new SyntaxException((Throwable)e);
                }
                if (result == null || result.isEmpty()) {
                    return null;
                }
                ArrayList list = new ArrayList();
                IDataFormator df = this.getDataFormator(null);
                Format formator = df == null ? null : df.getFormator(null);
                result.forEach(c -> {
                    String title = c.getTitle();
                    if ((title == null || title.equals(c.getValue())) && formator != null) {
                        title = formator.format(c.getValue());
                    }
                    list.add(title);
                });
                return list;
            }
        }
        throw new SyntaxException("\u672a\u652f\u6301\u7684\u53c2\u6570\u53d6\u503c\u6a21\u5f0f\uff1a" + this.valueMode);
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

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        ParamNodeDataFormator formator = new ParamNodeDataFormator(this.paramModel);
        formator.setLanguage(this.paramEnv.getLanguage());
        return formator;
    }

    private DataNode toDataNode(IContext context) throws SyntaxException {
        int dataType = this.getType(context);
        Object dataValue = this.evaluate(context);
        return new DataNode(this.getToken(), dataType, dataValue);
    }
}

