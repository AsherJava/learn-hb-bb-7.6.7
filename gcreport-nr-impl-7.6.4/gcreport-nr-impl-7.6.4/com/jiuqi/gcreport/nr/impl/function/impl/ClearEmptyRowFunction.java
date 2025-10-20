/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.NrSqlUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.nr.impl.function.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.NrSqlUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClearEmptyRowFunction
extends AdvanceFunction
implements INrFunction {
    private static final long serialVersionUID = 899333003095435298L;
    static final String FUNCTION_NAME = "ClearEmptyRow";

    ClearEmptyRowFunction() {
        this.parameters().add(new Parameter("tableName", 6, "\u5b58\u50a8\u8868", true));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u6e05\u9664\u6d6e\u52a8\u8868\u7a7a\u884c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 0;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() > 0) {
            String tableCode = (String)parameters.get(0).evaluate(null);
            if (null == tableCode) {
                throw new SyntaxException("\u53c2\u6570\u975e\u6cd5");
            }
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByCode(tableCode.toUpperCase());
            if (null == tableDefine) {
                throw new SyntaxException("\u53c2\u6570\u975e\u6cd5\uff1a" + tableCode);
            }
        }
        return super.validate(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        String formSchemeKey = ((ReportFmlExecEnvironment)queryContext.getExeContext().getEnv()).getFormSchemeKey();
        String formCode = queryContext.getDefaultGroupName();
        Map<String, String> condition = this.getCondition(queryContext, formSchemeKey);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableDefine = null;
        if (parameters.size() > 0) {
            String tableCode = (String)parameters.get(0).evaluate(null);
            if (null != tableCode) {
                tableCode = tableCode.toUpperCase();
            }
            tableDefine = dataModelService.getTableModelDefineByCode(tableCode);
        }
        if (null == tableDefine) {
            Set tableDefineSet = NrTool.getTableDefineByFormCode((String)formSchemeKey, (String)formCode, (DataRegionKind)DataRegionKind.DATA_REGION_ROW_LIST);
            for (TableModelDefine oneTableDefine : tableDefineSet) {
                this.clearSumZbEmptyRow(condition, oneTableDefine);
            }
        } else {
            this.clearSumZbEmptyRow(condition, tableDefine);
        }
        return 0;
    }

    private Map<String, String> getCondition(QueryContext queryContext, String formSchemeKey) {
        DimensionValueSet dimensionValueSet = queryContext.getCurrentMasterKey();
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("orgId", (String)dimensionValueSet.getValue("MD_ORG"));
        condition.put("periodStr", (String)dimensionValueSet.getValue("DATATIME"));
        condition.put("schemeId", formSchemeKey);
        condition.put("currency", (String)dimensionValueSet.getValue("MD_CURRENCY"));
        condition.put("orgType", (String)dimensionValueSet.getValue("MD_GCORGTYPE"));
        return condition;
    }

    private void clearSumZbEmptyRow(Map<String, String> condition, TableModelDefine tableDefine) {
        List<String> numberAndGatherFiledCodeList = this.getNumberAndGatherFiledCodes(tableDefine);
        String sql = "delete from %1s  where MDCODE=? and DATATIME=? %2s";
        StringBuilder where = NrSqlUtils.buildEntityTableWhere(condition, (String)"");
        for (String filedCode : numberAndGatherFiledCodeList) {
            where.append(" and coalesce(").append(filedCode).append(",0)=0\n");
        }
        EntNativeSqlDefaultDao.getInstance().execute(String.format(sql, tableDefine.getName(), where), Arrays.asList(condition.get("orgId"), condition.get("periodStr")));
    }

    protected List<String> getNumberAndGatherFiledCodes(TableModelDefine tableDefine) {
        List<String> numberAndGatherFiledCodes = new ArrayList<String>();
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        try {
            if (tableDefine == null) {
                return numberAndGatherFiledCodes;
            }
            runtimeDataSchemeService.getDataTableByCode(tableDefine.getCode());
            List fieldDefines = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            if (CollectionUtils.isEmpty((Collection)fieldDefines)) {
                return numberAndGatherFiledCodes;
            }
            numberAndGatherFiledCodes = fieldDefines.stream().filter(field -> this.isNumberAndGatherType((ColumnModelDefine)field)).map(ColumnModelDefine::getName).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6570\u503c\u578b\u6c47\u603b\u6307\u6807\u5f02\u5e38:" + tableDefine.getName(), (Throwable)e);
        }
        return numberAndGatherFiledCodes;
    }

    protected boolean isNumberAndGatherType(ColumnModelDefine fieldDefine) {
        ColumnModelType fieldType = fieldDefine.getColumnType();
        boolean isNumber = fieldType == ColumnModelType.BIGDECIMAL || fieldType == ColumnModelType.INTEGER || fieldType == ColumnModelType.DOUBLE;
        AggrType gatherType = fieldDefine.getAggrType();
        boolean isGatherType = gatherType != null && !AggrType.NONE.equals((Object)gatherType);
        return isNumber && isGatherType;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }
}

