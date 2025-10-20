/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 */
package com.jiuqi.gcreport.nr.impl.function.impl;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.nr.impl.service.JournalFunctionService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JournalPostFunction
extends AdvanceFunction
implements INrFunction {
    private static final long serialVersionUID = 1L;
    private final String FUNCTION_NAME = "JOURNALPOST";

    public JournalPostFunction() {
        this.parameters().add(new Parameter("tableName", 6, "\u8c03\u6574\u540e\u5b58\u50a8\u8868\u540d,\u4f8b\u5982\u57fa\u7840\u8868\u7684\u6307\u6807\u4e3aAA[bb],\u5219\u5b58\u50a8\u8868\u4e3aAA", true));
    }

    public String name() {
        return "JOURNALPOST";
    }

    public String title() {
        return "\u65e5\u8bb0\u8d26\u8fc7\u8d26";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 0;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!CollectionUtils.isEmpty(parameters)) {
            IASTNode node = parameters.get(0);
            String tableName = ((String)node.evaluate(null)).toUpperCase();
            try {
                DesignTableDefine tableDefine = ((IDataDefinitionDesignTimeController)SpringContextUtils.getBean(IDataDefinitionDesignTimeController.class)).queryTableDefinesByCode(tableName);
                if (null == tableDefine) {
                    throw new FunctionException("\u51fd\u6570journalPost\u53c2\u6570\u503c\u4e0d\u6b63\u786e,\u5b58\u50a8\u8868\u4e0d\u5b58\u5728:" + tableName);
                }
            }
            catch (JQException e) {
                throw new FunctionException("\u51fd\u6570journalPost\u53c2\u6570\u503c\u4e0d\u6b63\u786e,\u5b58\u50a8\u8868\u4e0d\u5b58\u5728:" + tableName);
            }
        }
        return super.validate(context, parameters);
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!CollectionUtils.isEmpty(parameters)) {
            IASTNode node = parameters.get(0);
            String afterZbTableName = ((String)node.evaluate(null)).toUpperCase();
            return ((JournalFunctionService)SpringContextUtils.getBean(JournalFunctionService.class)).callPostFunc((QueryContext)context, afterZbTableName);
        }
        return ((JournalFunctionService)SpringContextUtils.getBean(JournalFunctionService.class)).callPostFunc((QueryContext)context);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u8fc7\u8d26\u6267\u884c\u7ed3\u679c").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u89e6\u53d1\u65e5\u8bb0\u8d26\u8fc7\u8d26 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("JOURNALPOST()").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

