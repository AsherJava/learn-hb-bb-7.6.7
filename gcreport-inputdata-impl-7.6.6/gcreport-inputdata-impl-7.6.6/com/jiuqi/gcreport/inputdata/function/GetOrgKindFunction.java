/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.nr.impl.function.NrFunction
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetOrgKindFunction
extends NrFunction
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "GETORGKIND";
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());

    public String name() {
        return "GETORGKIND";
    }

    public String title() {
        return "\u83b7\u53d6\u5355\u4f4d\u79cd\u7c7b";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            FormSchemeDefine formSchemeDefine = env.getFormSchemeDefine();
            String entitieTable = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)formSchemeDefine.getTaskKey());
            DimensionValueSet ds = queryContext.getCurrentMasterKey();
            String orgId = (String)ds.getValue("MD_ORG");
            String period = String.valueOf(ds.getValue("DATATIME"));
            if (StringUtils.isEmpty((String)orgId) || StringUtils.isEmpty((String)period)) {
                return "";
            }
            YearPeriodObject yp = new YearPeriodObject(null, period);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)entitieTable, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            GcOrgCacheVO orgVO = tool.getOrgByCode(orgId);
            switch (orgVO.getOrgKind()) {
                case UNIONORG: {
                    return "HB";
                }
                case DIFFERENCE: {
                    return "CE";
                }
                case SINGLE: 
                case BASE: {
                    return "DH";
                }
            }
            return "";
        }
        catch (Exception e) {
            this.logger.error("GETORGKIND()\u516c\u5f0f\u6267\u884c\u5f02\u5e38", e);
            return "";
        }
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b").append("\u5355\u4f4d\u79cd\u7c7b\u7b80\u5199:\u5408\u5e76\u8fd4\u56deHB,\u5dee\u989d\u8fd4\u56deCE,\u672c\u90e8\u6216\u8005\u5355\u6237\u5355\u4f4d\u8fd4\u56deDH").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u5355\u4f4d\u79cd\u7c7b\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETORGKIND()").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    private String getEntitieTable(String entityId) {
        TableModelDefine tableDefine = ((IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class)).getTableModel(entityId);
        String tableName = tableDefine.getName();
        if (StringUtils.isEmpty((String)tableName)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u8868\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return tableName;
    }
}

