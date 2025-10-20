/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.flexible.service;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.flexible.task.GcCalcInputDataForkJoinTask;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleMappingImplUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GcCalcPrepareDataService {
    private Logger logger = LoggerFactory.getLogger(GcCalcPrepareDataService.class);
    @Autowired
    private InputDataService inputDataService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    private static final String SQL_GETINPUTDATAEOS = " select %1$s \n from %6$s  e \n join %5$s  unit on e.MDCODE = unit.code \n  left join %5$s  oppunit on e.oppunitid = oppunit.code \n where substr(unit.gcparents, %2$s, %7$s) <> substr(oppunit.gcparents, %2$s, %7$s) \n and substr(unit.parents, 1, %3$s)  = '%4$s'\n and substr(oppunit.parents, 1, %3$s)  = '%4$s' \n and unit.validtime <= ? and unit.invalidtime > ?  \n and oppunit.validtime <= ? and oppunit.invalidtime > ?  \n and unit.ORGTYPEID = e.MD_GCORGTYPE \n and e.unionruleid is null \n and e.DATATIME = ? \n and e.REPORTSYSTEMID = ? \n and e.MD_CURRENCY = ? \n";

    public void prepareDatas(GcCalcEnvContext env, List<AbstractUnionRule> rules) {
        List<InputDataEO> inputDataEos = this.getInputDataEos(env);
        if (inputDataEos == null || inputDataEos.size() == 0) {
            return;
        }
        String reportSystemId = this.getSystemIdByTaskIdAndPeriodStr(env.getCalcArgments());
        List<AbstractUnionRule> flexibleRules = this.getFlexibleRuleListByReportSystemId(reportSystemId);
        List<String> inputDataNumberFields = this.getInputDataNumberFields(env.getCalcArgments().getTaskId());
        NpContext context = NpContextHolder.getContext();
        GcCalcInputDataForkJoinTask<InputDataEO> forkJoinTask = new GcCalcInputDataForkJoinTask<InputDataEO>(env, inputDataEos, inputData -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                RuleMappingImplUtils.mappingRule(reportSystemId, flexibleRules, inputData, inputDataNumberFields);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                NpContextHolder.clearContext();
                throw new BusinessRuntimeException("\u5185\u90e8\u5f55\u5165\u8868\u5206\u5f55[RECID:" + inputData.getId() + "]\u6570\u636e\u6e05\u6d17\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
            }
        });
        try {
            ForkJoinTask<Integer> submit = GcCalcRuleUtils.getInputDataForkJoinPool().submit(forkJoinTask);
            this.logger.debug("\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u5185\u90e8\u5f55\u5165\u8868\u6570\u636e\u6e05\u6d17\u6570\u91cf\uff1a" + submit.get().toString());
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u5185\u90e8\u5f55\u5165\u8868\u6570\u636e\u6e05\u6d17\u5f02\u5e38", e);
        }
        GcCalcPrepareDataService gcCalcPrepareDataService = (GcCalcPrepareDataService)SpringContextUtils.getBean(GcCalcPrepareDataService.class);
        gcCalcPrepareDataService.updateRuleInfo(inputDataEos);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void updateRuleInfo(Collection<InputDataEO> inputDataEos) {
        if (!CollectionUtils.isEmpty(inputDataEos)) {
            this.inputDataService.updateRuleInfo(inputDataEos);
        }
    }

    private List<InputDataEO> getInputDataEos(GcCalcEnvContext env) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(env.getCalcArgments().getTaskId());
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"e");
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        String orgType = calcArgments.getOrgType();
        YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Date orgvalidate = yp.formatYP().getEndDate();
        GcOrgCacheVO gcOrg = instance.getOrgByCode(calcArgments.getOrgId());
        if (gcOrg == null) {
            throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d\u3010ID:" + calcArgments.getOrgId() + "\u3011\u627e\u4e0d\u5230");
        }
        String gcOrgParents = gcOrg.getParentStr();
        int gcUnitParentsStartIndex = gcOrg.getGcParentStr().length() + 2;
        int gcUnitParentsEndIndex = gcOrgParents.length();
        int len = instance.getOrgCodeLength();
        String formatSQL = String.format(SQL_GETINPUTDATAEOS, columns, gcUnitParentsStartIndex, gcUnitParentsEndIndex, gcOrgParents, calcArgments.getOrgType(), tableName, len);
        EntNativeSqlDefaultDao<InputDataEO> inputDataDao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(calcArgments.getTaskId(), calcArgments.getPeriodStr());
        List inputDataEOs = inputDataDao.selectEntity(formatSQL, Arrays.asList(orgvalidate, orgvalidate, orgvalidate, orgvalidate, calcArgments.getPeriodStr(), systemId, calcArgments.getCurrency()));
        return inputDataEOs;
    }

    private String getSystemIdByTaskIdAndPeriodStr(GcCalcArgmentsDTO calcArgmentsDTO) {
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(calcArgmentsDTO.getTaskId(), calcArgmentsDTO.getPeriodStr());
        if (StringUtils.isEmpty(systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
        }
        return systemId;
    }

    private List<AbstractUnionRule> getFlexibleRuleListByReportSystemId(String reportSystemId) {
        ArrayList<String> ruleTypeCodes = new ArrayList<String>();
        ruleTypeCodes.add(RuleTypeEnum.FLEXIBLE.getCode());
        List rules = UnionRuleUtils.selectRuleListByReportSystemAndRuleTypes((String)reportSystemId, ruleTypeCodes);
        return rules;
    }

    private List<String> getInputDataNumberFields(String taskId) {
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
            String tableName = inputDataNameProvider.getTableNameByTaskId(taskId);
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableName);
            List<String> numberFieldCodes = dataModelService.getColumnModelDefinesByTable(tableDefine.getID()).stream().filter(columnModelDefine -> ColumnModelType.BIGDECIMAL.equals((Object)columnModelDefine.getColumnType()) || ColumnModelType.DOUBLE.equals((Object)columnModelDefine.getColumnType())).map(ColumnModelDefine::getName).collect(Collectors.toList());
            return numberFieldCodes;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u5185\u90e8\u8868\u91d1\u989d\u5b57\u6bb5\u5f02\u5e38\u3002", (Throwable)e);
        }
    }
}

