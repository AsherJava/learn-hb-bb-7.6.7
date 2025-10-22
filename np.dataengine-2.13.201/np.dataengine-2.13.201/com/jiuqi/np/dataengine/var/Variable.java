/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.var;

import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.data.DataTypes;
import java.io.Serializable;

public class Variable
implements Serializable {
    private static final long serialVersionUID = 7264552734679838758L;
    private String varName;
    private String varTitle;
    private Serializable varValue;
    private transient Object unserializableVarValue;
    private int dataType;

    public Variable(String varName, int dataType) {
        this.varName = varName;
        this.varTitle = varName;
        this.dataType = dataType;
    }

    public Variable(String varName, String varTitle, int dataType) {
        this.varName = varName;
        this.varTitle = varTitle;
        this.dataType = dataType;
    }

    public Variable(String varName, String varTitle, int dataType, Object varValue) {
        this.varName = varName;
        this.varTitle = varTitle;
        this.dataType = dataType;
        this.setVarValue(varValue);
    }

    public String getVarName() {
        return this.varName;
    }

    public String getVarTitle() {
        return this.varTitle;
    }

    public void setVarTitle(String value) {
        this.varTitle = value;
    }

    public Object getVarValue(IContext context) throws Exception {
        if (this.varValue != null) {
            return this.varValue;
        }
        return this.unserializableVarValue;
    }

    public void setVarValue(Object value) {
        if (value instanceof Serializable) {
            this.varValue = (Serializable)value;
        } else {
            this.unserializableVarValue = value;
        }
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isStatic() {
        return this.varValue != null;
    }

    public String toString() {
        return "Variable [varName=" + this.varName + ", varTitle=" + this.varTitle + ", dataType=" + DataTypes.toString(this.dataType) + "]";
    }

    public void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) {
        info.setCurrentName("new _Var('" + this.varName + "','" + this.varTitle + "','" + this.dataType + "')");
    }
}

