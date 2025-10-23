/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.fmdm.internal.formula;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import org.springframework.util.StringUtils;

public class VarFMDMCode
extends Variable {
    private static final long serialVersionUID = -3183910474905711044L;
    private String bizFieldCode;

    public VarFMDMCode(String bizFieldCode) {
        super("DWDM", "\u5c01\u9762\u4ee3\u7801\u6807\u8bc6", 6);
        this.bizFieldCode = bizFieldCode;
    }

    public Object getVarValue(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        if (!StringUtils.hasText(this.bizFieldCode)) {
            this.bizFieldCode = "CODE";
        }
        return qContext.getCache().get(this.bizFieldCode);
    }
}

