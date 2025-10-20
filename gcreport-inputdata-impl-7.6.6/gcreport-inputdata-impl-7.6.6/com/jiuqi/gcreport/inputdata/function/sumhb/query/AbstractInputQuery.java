/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.common.Utils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.query;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.dao.SumhbTempDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.InputQuery;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.common.Utils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractInputQuery
implements InputQuery {
    protected QueryContext queryContext;
    protected String inputTableName;
    protected Collection<String> calcFieldName;
    protected Collection<String> noCalcFieldNames;
    protected Collection<String> managementDimCodes;
    protected Map<String, String> dims;
    protected SumhbTempDao sumhbTempDao;
    protected ConsolidatedTaskService consolidatedTaskService;
    protected TaskDefine taskDefine;
    protected String orgCategory;
    protected String normalDate;
    protected GcOrgCacheVO org;
    protected int orgCodeLength;
    protected String systemId;
    protected List<Object> params;
    protected GcOrgCenterService orgCenterTool;
    protected static final Map<String, String> staticFieldMapping = new HashMap<String, String>();

    protected void init(QueryContext queryContext, String tableName, Collection<String> calcFieldName, Collection<String> noCalcFieldNames, Map<String, String> dims, SumhbTempDao sumhbTempDao) {
        this.queryContext = queryContext;
        this.inputTableName = tableName;
        this.calcFieldName = calcFieldName;
        this.noCalcFieldNames = noCalcFieldNames;
        this.dims = dims;
        this.sumhbTempDao = sumhbTempDao;
        this.consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            this.taskDefine = env.getTaskDefine();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.functioncontexterrormsg"), (Throwable)e);
        }
        this.orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)this.taskDefine.getKey());
        PeriodEngineService periodEngineService = (PeriodEngineService)SpringContextUtils.getBean(PeriodEngineService.class);
        Date date = Utils.getVersionDate((DimensionValueSet)queryContext.getCurrentMasterKey(), (PeriodEngineService)periodEngineService, (String)this.taskDefine.getDateTime());
        this.normalDate = DateUtils.format((Date)date, (String)"yyyy-MM-dd");
        YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)env.getFormSchemeKey(), (String)dims.get("DATATIME"));
        this.orgCenterTool = GcOrgPublicTool.getInstance((String)this.orgCategory, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
        this.org = this.orgCenterTool.getOrgByCode(dims.get("MDCODE"));
        this.orgCodeLength = this.orgCenterTool.getOrgCodeLength();
        this.systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(this.taskDefine.getKey(), dims.get("DATATIME"));
        if (ObjectUtils.isEmpty(this.systemId)) {
            this.systemId = "\u4e0d\u5b58\u5728";
        }
        this.managementDimCodes = ((ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class)).getManagementDimsBySystemId(this.systemId).stream().map(ManagementDim::getCode).collect(Collectors.toList());
    }

    protected Map<String, String> getInputAndOffsetFieldMapping(String alias) {
        HashMap<String, String> fieldNameMappings = new HashMap<String, String>();
        Set<String> inputFieldNames = this.getFieldNames(this.inputTableName);
        Set<String> offsetFieldNames = this.getFieldNames("GC_OFFSETVCHRITEM");
        inputFieldNames.forEach(inputField -> {
            String cfr_ignored_0 = fieldNameMappings.put((String)inputField, staticFieldMapping.containsKey(inputField) ? String.format(staticFieldMapping.get(inputField), alias) : (offsetFieldNames.contains(inputField) ? alias + "." + inputField : null));
        });
        return fieldNameMappings;
    }

    private Set<String> getFieldNames(String tableName) {
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName(tableName);
        return dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID()).stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
    }

    protected String concatSql(String detailSql, String sumSql) {
        String querySql = StringUtils.hasText(detailSql) && StringUtils.hasText(sumSql) ? detailSql + "\nunion all\n" + sumSql : (StringUtils.hasText(detailSql) ? detailSql : sumSql);
        return querySql;
    }

    static {
        staticFieldMapping.put("OFFSETGROUPID", "%1$s.SRCOFFSETGROUPID");
        staticFieldMapping.put("BIZKEYORDER", "%1$s.ID");
        staticFieldMapping.put("SUBJECTOBJ", "concat(concat(%1$s.SUBJECTCODE,'||'),%1$s.SYSTEMID)");
        staticFieldMapping.put("MDCODE", "%1$s.UNITID");
        staticFieldMapping.put("ORGCODE", "%1$s.UNITID");
        staticFieldMapping.put("OFFSETSTATE", "N'1'");
    }
}

