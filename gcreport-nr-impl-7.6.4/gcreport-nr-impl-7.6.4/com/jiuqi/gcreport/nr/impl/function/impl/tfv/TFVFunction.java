/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.nr.impl.function.GcBaseExecutorContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.xlib.utils.StringUtil
 *  org.apache.commons.lang3.text.StrSubstitutor
 */
package com.jiuqi.gcreport.nr.impl.function.impl.tfv;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.nr.impl.function.GcBaseExecutorContext;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.gcreport.nr.impl.function.impl.tfv.TFVFetchDataService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.xlib.utils.StringUtil;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TFVFunction
extends NrFunction
implements IGcFunction {
    private final String FUNCTION_NAME = "TFV";
    private static Logger logger = LoggerFactory.getLogger(TFVFunction.class);

    public TFVFunction() {
        this.parameters().add(new Parameter("SelectSQl", 6, "\u67e5\u8be2\u8bed\u53e5(\u5982\u9700\u4ece\u4e3b\u6570\u636e\u6216\u7ef4\u5ea6\u4e2d\u83b7\u53d6\u6570\u636e\uff0c\u5219\u9700\u5c06\u5b57\u6bb5\u540d\u7528##\u5305\u88f9\u8d77\u6765\uff0c\u5982#AMT#\uff0c\u82e5\u662f\u5b57\u7b26\u578b\u5219\u9700\u7528'##'\u5305\u88f9\u8d77\u6765\uff0c\u5982'#OPPUNITID#')"));
        this.parameters().add(new Parameter("RType", 6, "\u8fd4\u56de\u7c7b\u578b (Str \uff1a\u8fd4\u56de\u5b57\u7b26\u4e32Num\uff1a\u8fd4\u56de\u5b9e\u6570)"));
        this.parameters().add(new Parameter("flied1", 6, "\u517c\u5bb9\u5386\u53f2\u8bed\u6cd5\u7528\uff0c\u6700\u65b0\u8bed\u6cd5\u4e0d\u7528\u4f20", true));
        this.parameters().add(new Parameter("flied2", 6, "\u517c\u5bb9\u5386\u53f2\u8bed\u6cd5\u7528\uff0c\u6700\u65b0\u8bed\u6cd5\u4e0d\u7528\u4f20", true));
        this.parameters().add(new Parameter("flied3", 6, "\u517c\u5bb9\u5386\u53f2\u8bed\u6cd5\u7528\uff0c\u6700\u65b0\u8bed\u6cd5\u4e0d\u7528\u4f20", true));
    }

    public String name() {
        return "TFV";
    }

    public String title() {
        return "TFV\u516c\u5f0f";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        String finalSql;
        String rType;
        Map<Object, Object> fetchDataS = new HashMap(16);
        QueryContext queryContext = (QueryContext)iContext;
        if (queryContext.getExeContext() instanceof GcBaseExecutorContext) {
            TFVFetchDataService fetchDataService = null;
            try {
                fetchDataService = (TFVFetchDataService)SpringBeanUtils.getBean(TFVFetchDataService.class);
            }
            catch (Exception e) {
                return null;
            }
            fetchDataS = fetchDataService.fetchData(queryContext.getExeContext());
        } else {
            DimensionValueSet masterKeys = ((QueryContext)iContext).getCurrentMasterKey();
            fetchDataS = new HashMap(16);
            for (int i = 0; i < masterKeys.size(); ++i) {
                fetchDataS.put(masterKeys.getName(i), masterKeys.getValue(i));
            }
        }
        if (parameters.size() == 5) {
            String compValue;
            String sql = "select {0} from {1} where {2} ";
            String tableName = (String)parameters.get(0).evaluate(iContext);
            String filedName = (String)parameters.get(1).evaluate(iContext);
            String vType = (String)parameters.get(2).evaluate(iContext);
            String conDiStr = (String)parameters.get(3).evaluate(iContext);
            rType = (String)parameters.get(4).evaluate(iContext);
            if (StringUtil.equals((String)"ORG", (String)vType)) {
                compValue = filedName;
            } else if (StringUtil.equals((String)"SUM", (String)vType) || StringUtil.equals((String)"AVG", (String)vType)) {
                compValue = vType + "(" + filedName + ")";
            } else if (StringUtil.equals((String)"COUNT", (String)vType)) {
                compValue = "COUNT(*)";
            } else {
                throw new BusinessRuntimeException("\u6682\u4e0d\u652f\u6301\u6b64\u7c7b\u578b\uff1a" + vType);
            }
            conDiStr = StrSubstitutor.replace((Object)conDiStr, fetchDataS, (String)"#", (String)"#");
            finalSql = MessageFormat.format(sql, compValue, tableName, conDiStr);
        } else if (parameters.size() == 2) {
            finalSql = (String)parameters.get(0).evaluate(iContext);
            if (!finalSql.trim().toLowerCase().startsWith("select")) {
                throw new BusinessRuntimeException("TFV\u5fc5\u987b\u662f\u67e5\u8be2\u8bed\u53e5");
            }
            rType = (String)parameters.get(1).evaluate(iContext);
            finalSql = StrSubstitutor.replace((Object)finalSql, fetchDataS, (String)"#", (String)"#");
        } else {
            throw new BusinessRuntimeException("TFV\u7684\u53c2\u6570\u53ea\u80fd\u662f\u4e8c\u4e2a\u6216\u4e94\u4e2a");
        }
        logger.info("TFV\u516c\u5f0f\u7684\u67e5\u8be2\u8bed\u53e5\u4e3a\uff1a" + finalSql);
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap(finalSql, new Object[0]);
        logger.info(finalSql + "\u7684\u67e5\u8be2\u7ed3\u679c\u4e3a\uff1a" + maps.toString());
        if (maps.isEmpty()) {
            return null;
        }
        if (maps.size() > 1) {
            throw new BusinessRuntimeException("\u8ba1\u7b97\u7ed3\u679c\u4e3a\u591a\u4e2a");
        }
        Object o = ((Map)maps.iterator().next()).entrySet().iterator().next().getValue();
        if (o == null) {
            return null;
        }
        String value = String.valueOf(o);
        if (StringUtil.equalsIgnoreCase((String)"Num", (String)rType)) {
            return Double.valueOf(value);
        }
        if (StringUtil.equalsIgnoreCase((String)"Str", (String)rType)) {
            return value;
        }
        throw new BusinessRuntimeException("\u8fd4\u56de\u7c7b\u578b\u53ea\u80fd\u4e3aStr\u6216Num");
    }

    public String toDescription() {
        String description = super.toDescription();
        StringBuffer buffer = new StringBuffer(description);
        buffer.append("\u516c\u5f0f\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("TFV(\"SELECT SUM(KM_JF) FROM ZW_FZHSZD where kmcode='122101' and DATATIME = '#DATATIME#' and F4='#AMT#'\"").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\u542b\u4e49\u662f\u4ece\u8868ZW_FZHSZD\u4e2d\u53d6\u51fa\u6ee1\u8db3\u6761\u4ef6\uff1akmcode='122101' and DATATIME= '#DATATIME#' and F4'=#AMT#'\u7684\u5b57\u6bb5KM_JF\u7684\u5408\u8ba1").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u5907\u6ce8\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd0\u7b97\u548c\u7075\u6d3b\u89c4\u5219\u4e2d\u652f\u6301TFV\u516c\u5f0f\uff0c\u7075\u6d3b\u89c4\u5219\u53ea\u6709\u53d6\u6570\u65b9\u5f0f\u662f\u660e\u7ec6\u65f6\u652f\u6301\u3002");
        return buffer.toString();
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() != 5 && parameters.size() != 2) {
            throw new BusinessRuntimeException("TFV\u53c2\u6570\u53ea\u80fd\u662f\u4e8c\u4e2a\u6216\u4e94\u4e2a");
        }
        return super.validate(context, parameters);
    }
}

