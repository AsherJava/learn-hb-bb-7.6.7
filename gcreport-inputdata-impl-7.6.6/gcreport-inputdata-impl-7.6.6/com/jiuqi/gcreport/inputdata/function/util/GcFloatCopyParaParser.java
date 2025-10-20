/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser
 */
package com.jiuqi.gcreport.inputdata.function.util;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;
import java.util.List;

public class GcFloatCopyParaParser
extends FloatCopyParaParser {
    public GcFloatCopyParaParser(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        super(qContext, parameters);
        this.parserCopyColumnWhenAdd((String)parameters.get(9).evaluate((IContext)qContext));
    }

    private void parserCopyColumnWhenAdd(String column) {
        String[] fields;
        if (!StringUtils.isEmpty((String)column) && (fields = column.split(",")) != null && fields.length > 0) {
            for (String field : fields) {
                String[] sd = field.split("=");
                if (sd == null || sd.length <= 0) continue;
                this.getQueryCondition().getRow().addQueryColumns(sd[0]);
                if (sd.length == 1) {
                    this.getCopyCondition().getRow().addQueryColumns(sd[0]);
                    continue;
                }
                this.getCopyCondition().getRow().addQueryColumns(sd[1]);
            }
        }
    }
}

