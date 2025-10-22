/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityDefineAssist
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.upgrade.currencyupdate;

import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class TaskFlowCurrencyProcessor {
    Logger logger = LoggerFactory.getLogger(TaskFlowCurrencyProcessor.class);
    @Autowired
    private IEntityDefineAssist iEntityDefineAssist;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private DesignBigDataTableDao designBigDataTableDao;
    @Autowired
    private RunTimeBigDataTableDao runTimeBigDataTableDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String ATTR_KEY = "key";
    private static final String ATTR_CODE = "code";

    @Transactional
    public void updateData() {
        try {
            this.updateDesignData();
            this.updateRuntimeData();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void updateDesignData() throws Exception {
        List allTasks = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_TASK_DES");
        for (Map designTaskDefine : allTasks) {
            Boolean reportDimension;
            Boolean aBoolean;
            boolean openCurrency = this.iEntityDefineAssist.existCurrencyAttributes(designTaskDefine.get("TK_DW").toString());
            if (!openCurrency || !(aBoolean = this.existCurrencyDimDesign(designTaskDefine)).booleanValue() || (reportDimension = this.isReportDimensionDesign(designTaskDefine.get("TK_DATASCHEME").toString(), "MD_CURRENCY@BASE")).booleanValue()) continue;
            List<DesignBigDataTable> bigDataTables = this.designBigDataTableDao.queryigDataDefines(designTaskDefine.get("TK_KEY").toString(), "FLOWSETTING");
            for (DesignBigDataTable bigDataTable : bigDataTables) {
                DesignTaskFlowsDefine designTaskFlowsDefine;
                if (bigDataTable == null || null == bigDataTable.getData() || null == (designTaskFlowsDefine = DesignTaskFlowsDefine.bytesToTaskFlowsData(bigDataTable.getData()))) continue;
                boolean isUpdate = false;
                if (designTaskFlowsDefine.isCheckBeforeReporting() && ReportAuditType.NONE.equals((Object)designTaskFlowsDefine.getCheckBeforeReportingType())) {
                    designTaskFlowsDefine.setCheckBeforeReportingType(ReportAuditType.CUSTOM);
                    designTaskFlowsDefine.setCheckBeforeReportingCustom("CNY");
                    isUpdate = true;
                }
                if (designTaskFlowsDefine.getReportBeforeAudit() && ReportAuditType.NONE.equals((Object)designTaskFlowsDefine.getReportBeforeAuditType())) {
                    designTaskFlowsDefine.setReportBeforeAuditType(ReportAuditType.CUSTOM);
                    designTaskFlowsDefine.setReportBeforeAuditCustom("CNY");
                    isUpdate = true;
                }
                if (!isUpdate) continue;
                byte[] data = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(designTaskFlowsDefine);
                bigDataTable.setData(data);
                this.designBigDataTableDao.updateData(bigDataTable);
            }
        }
    }

    private void updateRuntimeData() throws Exception {
        List allTasks = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_TASK");
        for (Map taskDefine : allTasks) {
            Boolean reportDimension;
            Boolean aBoolean;
            boolean openCurrency = this.iEntityDefineAssist.existCurrencyAttributes(taskDefine.get("TK_DW").toString());
            if (!openCurrency || !(aBoolean = this.existCurrencyDimRuntime(taskDefine)).booleanValue() || (reportDimension = this.isReportDimensionRuntime(taskDefine.get("TK_DATASCHEME").toString(), "MD_CURRENCY@BASE")).booleanValue()) continue;
            List bigDataTables = this.runTimeBigDataTableDao.list(new String[]{ATTR_KEY, ATTR_CODE}, new Object[]{taskDefine.get("TK_KEY").toString(), "FLOWSETTING"}, RunTimeBigDataTable.class);
            for (RunTimeBigDataTable bigDataTable : bigDataTables) {
                DesignTaskFlowsDefine taskFlowsDefine;
                if (bigDataTable == null || null == bigDataTable.getData() || null == (taskFlowsDefine = DesignTaskFlowsDefine.bytesToTaskFlowsData(bigDataTable.getData()))) continue;
                boolean isUpdate = false;
                if (ReportAuditType.NONE.equals((Object)taskFlowsDefine.getCheckBeforeReportingType())) {
                    taskFlowsDefine.setCheckBeforeReportingType(ReportAuditType.CUSTOM);
                    taskFlowsDefine.setCheckBeforeReportingCustom("CNY");
                    isUpdate = true;
                }
                if (ReportAuditType.NONE.equals((Object)taskFlowsDefine.getReportBeforeAuditType())) {
                    taskFlowsDefine.setReportBeforeAuditType(ReportAuditType.CUSTOM);
                    taskFlowsDefine.setReportBeforeAuditCustom("CNY");
                    isUpdate = true;
                }
                if (!isUpdate) continue;
                byte[] data = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(taskFlowsDefine);
                bigDataTable.setData(data);
                this.runTimeBigDataTableDao.updateData(bigDataTable);
            }
        }
    }

    private Boolean isReportDimensionDesign(String dataSchemeKey, String dimKey) {
        if (dataSchemeKey == null) {
            return Boolean.FALSE;
        }
        List dimensions = this.iDesignDataSchemeService.getReportDimension(dataSchemeKey);
        return dimensions.stream().anyMatch(dataDimension -> dimKey.equals(dataDimension.getDimKey()));
    }

    private Boolean existCurrencyDimDesign(Map<String, Object> taskDefine) {
        Object dims;
        if (Objects.nonNull(taskDefine) && null != (dims = taskDefine.get("TK_DIMS")) && StringUtils.hasLength(dims.toString())) {
            return dims.toString().contains("MD_CURRENCY@BASE");
        }
        return Boolean.FALSE;
    }

    private Boolean isReportDimensionRuntime(String dataSchemeKey, String dimKey) {
        if (dataSchemeKey == null) {
            return Boolean.FALSE;
        }
        List dimensions = this.runtimeDataSchemeService.getReportDimension(dataSchemeKey);
        return dimensions.stream().anyMatch(dataDimension -> dimKey.equals(dataDimension.getDimKey()));
    }

    private Boolean existCurrencyDimRuntime(Map<String, Object> taskDefine) {
        Object dims;
        if (Objects.nonNull(taskDefine) && null != (dims = taskDefine.get("TK_DIMS")) && StringUtils.hasLength(dims.toString())) {
            return dims.toString().contains("MD_CURRENCY@BASE");
        }
        return Boolean.FALSE;
    }
}

