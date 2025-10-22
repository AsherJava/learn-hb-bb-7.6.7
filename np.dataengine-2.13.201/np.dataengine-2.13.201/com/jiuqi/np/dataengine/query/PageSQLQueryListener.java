/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageSQLQueryListener
implements ISQLQueryListener {
    private Map<String, ISQLQueryListener.ParamInfo> paraValueMap;

    public PageSQLQueryListener(List<Object> argValues, List<Integer> argDataTypes) {
        if (argValues != null) {
            this.paraValueMap = new HashMap<String, ISQLQueryListener.ParamInfo>();
            for (int i = 0; i < argValues.size(); ++i) {
                String paramName = PageSQLQueryListener.createParamName(i);
                int dataType = argDataTypes.get(i);
                if (dataType == 4) {
                    dataType = 3;
                }
                ISQLQueryListener.ParamInfo paramInfo = new ISQLQueryListener.ParamInfo(paramName, dataType, argValues.get(i));
                this.paraValueMap.put(paramName, paramInfo);
            }
        }
    }

    public ISQLQueryListener.ParamInfo findParam(String paramName) throws SyntaxException {
        if (this.paraValueMap != null) {
            return this.paraValueMap.get(paramName);
        }
        return null;
    }

    public static String createParamName(int index) {
        return "PARA" + index;
    }
}

