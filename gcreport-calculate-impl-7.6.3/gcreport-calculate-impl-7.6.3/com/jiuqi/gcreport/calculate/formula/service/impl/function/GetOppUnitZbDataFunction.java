/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.nr.impl.function.GcFunctionUtil
 *  com.jiuqi.gcreport.nr.impl.function.NrUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.nr.impl.function.GcFunctionUtil;
import com.jiuqi.gcreport.nr.impl.function.NrUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetOppUnitZbDataFunction
extends Function
implements INrFunction {
    private static final transient Logger logger = LoggerFactory.getLogger(GetOppUnitZbDataFunction.class);
    public static final String FUNCTION_NAME = "GetOppUnitZbData";
    private static final String FETCH_TYPE_GRADE_CONVERT_UNION = "1";
    private static final long serialVersionUID = 1L;
    private transient IDataDefinitionRuntimeController runtimeController;

    public GetOppUnitZbDataFunction() {
        this.parameters().add(new Parameter("unitZbCode", 6, "\u5355\u4f4d\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("zbCode", 6, "\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("fetchType", 6, "\u53d6\u6570\u65b9\u5f0f"));
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) {
        QueryContext queryContext = (QueryContext)context;
        try {
            DimensionValueSet dvs;
            AbstractData abstractData;
            String unitId = (String)parameters.get(0).evaluate(context);
            Object zbCode = parameters.get(1).evaluate((IContext)queryContext);
            Object fetchType = parameters.get(2).evaluate((IContext)queryContext);
            String yp = "";
            if (FETCH_TYPE_GRADE_CONVERT_UNION.equals(fetchType)) {
                unitId = this.getInvestedUnit(unitId, queryContext, new YearPeriodObject(null, yp));
            }
            Object value = null == (abstractData = NrTool.getZbValue((DimensionValueSet)(dvs = this.calcDimensionValueSet(queryContext.getCurrentMasterKey(), parameters, unitId)), (String)((String)zbCode))) || null == abstractData.getAsObject() ? this.getDefaultVal((String)zbCode) : abstractData.getAsObject();
            return value;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0.0;
        }
    }

    private String getInvestedUnit(String investedUnit, QueryContext queryContext, YearPeriodObject yp) {
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)GcFunctionUtil.getOrgTableCode((QueryContext)queryContext), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgCenterService.getUnionUnitByGrade(investedUnit);
        if (null != hbOrg) {
            investedUnit = hbOrg.getId();
        }
        return investedUnit;
    }

    private Object getDefaultVal(String zbCode) throws Exception {
        TableDefine tableDefine;
        ArrayKey zbCodeArrayKey = NrUtils.parseZbCode((String)zbCode);
        if (null == this.runtimeController) {
            this.runtimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        }
        if (null == (tableDefine = this.runtimeController.queryTableDefineByCode((String)zbCodeArrayKey.get(0)))) {
            return 0.0;
        }
        FieldDefine define = this.runtimeController.queryFieldByCodeInTable((String)zbCodeArrayKey.get(1), tableDefine.getKey());
        if (null == define) {
            return 0.0;
        }
        if (define.getType() == FieldType.FIELD_TYPE_FLOAT || define.getType() == FieldType.FIELD_TYPE_DECIMAL || define.getType() == FieldType.FIELD_TYPE_INTEGER) {
            return 0.0;
        }
        if (define.getType() == FieldType.FIELD_TYPE_STRING) {
            return "";
        }
        return 0.0;
    }

    private DimensionValueSet calcDimensionValueSet(DimensionValueSet olDimensionValueSet, List<IASTNode> parameters, String unitId) throws Exception {
        DimensionValueSet ds = new DimensionValueSet(olDimensionValueSet);
        ds.setValue("MD_ORG", (Object)unitId);
        return ds;
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u6d6e\u52a8\u8868\u53d6\u5bf9\u65b9\u5355\u4f4d\u6307\u6807\u6570\u636e";
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b").append("\u6307\u6807\u503c").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u5167\u90e8\u8868\u5bf9\u65b9\u5355\u4f4d\u6307\u6807\u6570\u503c ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u7b2c\u4e09\u4e2a\u53c2\u6570\u4f20\"1\"\u65f6,\u5982\u679c\u5355\u4f4d\u5728\u5f53\u524d\u5355\u4f4d\u7248\u672c\u4e2d\u662f\u672c\u90e8\u5355\u4f4d\uff0c\u53d6\u5bf9\u5e94\u5408\u5e76\u6237\u5355\u4f4d\u7684\u6307\u6807\u6570\u636e").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetOppUnitZbData(GC_INPUTDATA[OPPUNITID],'ZCOX_YB01[A01]','')/GetOppUnitZbData(GC_INPUTDATA[OPPUNITID],'ZCOX_YB01[A01]','1')").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

