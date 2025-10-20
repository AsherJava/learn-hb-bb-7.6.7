/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.quickreport.engine.script.IScriptObj;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class ScriptParamEnv
implements IScriptObj {
    public static final String ID = "paramEnv";
    private IParameterEnv paramEnv;
    private ILogger log;

    public ScriptParamEnv(IParameterEnv paramEnv, ILogger log) {
        this.paramEnv = paramEnv;
        this.log = log;
    }

    public String getUserGuid() {
        return this.paramEnv.getUserId();
    }

    public Object getValue(String paramName) {
        ParameterResultset result;
        ParameterModel model = this.paramEnv.getParameterModelByName(paramName);
        if (model == null) {
            return null;
        }
        try {
            result = this.paramEnv.getValue(paramName);
        }
        catch (ParameterException e) {
            this.log.error("\u83b7\u53d6\u53c2\u6570\u503c\u5931\u8d25\uff1a" + paramName, (Throwable)e);
            return null;
        }
        if (result.isEmpty()) {
            return null;
        }
        switch (model.getSelectMode()) {
            case SINGLE: {
                return result.get(0).getValue();
            }
            case MUTIPLE: {
                Object[] arr = new Object[result.size()];
                for (int i = 0; i < result.size(); ++i) {
                    arr[i] = result.get(i).getValue();
                }
                return arr;
            }
            case RANGE: {
                return new Object[]{result.get(0).getValue(), result.get(1).getValue()};
            }
        }
        return null;
    }

    public void setValue(String paramName, Object value) {
        ParameterModel model = this.paramEnv.getParameterModelByName(paramName);
        if (model == null) {
            return;
        }
        if (value == null) {
            try {
                this.paramEnv.setValue(paramName, null);
            }
            catch (ParameterException e) {
                this.log.error("\u8bbe\u7f6e\u53c2\u6570\u7a7a\u503c\u51fa\u9519\uff1a" + paramName, (Throwable)e);
            }
            return;
        }
        List<Object> arr = value instanceof Object[] ? Arrays.asList((Object[])value) : (value instanceof Collection ? new ArrayList<Object>((Collection)value) : Arrays.asList(value));
        try {
            this.paramEnv.setValue(paramName, arr);
        }
        catch (ParameterException e) {
            this.log.error("\u8bbe\u7f6e\u53c2\u6570\u503c\u9519\u8bef\uff1a" + paramName + " = " + value, (Throwable)e);
        }
    }

    @Override
    public Object getObj() {
        return this.paramEnv;
    }
}

