/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  javax.annotation.Resource
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.gcreport.conversion.batch.audit.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.conversion.batch.audit.dao.ConversionBatchAuditDao;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditEnum;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditFileEntity;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditRunnerEntity;
import com.jiuqi.gcreport.conversion.batch.audit.service.ConversionBatchAuditService;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class ConversionBatchAuditServiceImpl
implements ConversionBatchAuditService {
    private static final Logger logger = LoggerFactory.getLogger(ConversionBatchAuditServiceImpl.class);
    @Resource
    private ConversionBatchAuditDao auditDao;
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IFuncExecuteService funcExecuteService;
    @Resource
    private IEntityMetaService entityMetaService;

    @Override
    public void addAudit(ConversionBatchAuditRunnerEntity entity) {
        try {
            this.auditDao.addBatchAudit(entity);
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u65b0\u589e\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u5931\u8d25");
        }
    }

    @Override
    public Map<String, Object> getAllBatchAudit(ConversionBatchAuditRunnerEntity entity) {
        HashMap<String, Object> type2Data = new HashMap<String, Object>();
        try {
            type2Data.put("count", this.auditDao.getAllBatchAuditCount(entity));
            type2Data.put("batchAuditData", this.auditDao.getAllBatchAudit(entity));
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u83b7\u53d6\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u5931\u8d25");
        }
        return type2Data;
    }

    @Override
    public List<ConversionBatchAuditFileEntity> getFileListForId(ConversionBatchAuditFileEntity entity) {
        return this.auditDao.getFileListForId(entity);
    }

    @Override
    public void deleteSelectBatchAudit(List<String> idList) {
        try {
            this.auditDao.deleteSelectBatchAudit(idList);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u5220\u9664\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u5931\u8d25");
        }
    }

    @Override
    public String getFileNameForEntity() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        String month = calendar.get(2) + 1 < 10 ? "0" + (calendar.get(2) + 1) : String.valueOf(calendar.get(2) + 1);
        String day = calendar.get(5) < 10 ? "0" + calendar.get(5) : String.valueOf(calendar.get(5));
        String hours = calendar.get(10) < 10 ? "0" + calendar.get(10) : String.valueOf(calendar.get(10));
        String minutes = calendar.get(12) < 10 ? "0" + calendar.get(12) : String.valueOf(calendar.get(12));
        String seconds = calendar.get(13) < 10 ? "0" + calendar.get(13) : String.valueOf(calendar.get(13));
        return year + month + day + hours + minutes + seconds;
    }

    @Override
    public PeriodWrapper getParamPeriod(ConversionBatchAuditRunnerEntity entity) {
        PeriodWrapper periodWrapper = this.getPeriodForScheme(entity.getSchemeId());
        if (ConversionBatchAuditEnum.PREVIOUSPERIOD.toString().equals(entity.getDataType())) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.priorPeriod(periodWrapper);
        } else if (ConversionBatchAuditEnum.LATERPERIOD.toString().equals(entity.getDataType())) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.nextPeriod(periodWrapper);
        }
        return periodWrapper;
    }

    private PeriodWrapper getPeriodForScheme(String schemeId) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        return TaskPeriodUtils.getCurrentPeriod((int)formScheme.getPeriodType().type());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Blob getFileBlob(ConversionBatchAuditRunnerEntity entity, PeriodWrapper periodWrapper) {
        JSONObject dataExportParam = this.getJsonStrForEntity(entity, periodWrapper);
        Blob blob = null;
        Connection connection = null;
        try {
            try (ByteArrayOutputStream os = new ByteArrayOutputStream();){
                SXSSFWorkbook sxWorkbook = ExpImpUtils.getWorkbook((String)"ConversionBatchExportExecutor", (String)"", (String)dataExportParam.toString());
                sxWorkbook.write((OutputStream)os);
                connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
                blob = connection.createBlob();
                blob.setBytes(1L, os.toByteArray());
                sxWorkbook.dispose();
            }
            if (this.jdbcTemplate == null) return blob;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u6587\u4ef6\u5b57\u8282\u6d41\u5931\u8d25", e);
            return blob;
        }
        finally {
            if (this.jdbcTemplate != null) {
                DataSourceUtils.releaseConnection(connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        return blob;
    }

    private JSONObject getJsonStrForEntity(ConversionBatchAuditRunnerEntity entity, PeriodWrapper periodWrapper) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isConversionWorkPaperPage", false);
        JSONObject dataJsonObject = new JSONObject();
        dataJsonObject.put("exportType", (Object)entity.getExportType());
        dataJsonObject.put("orgIds", entity.getOrgCodeList());
        dataJsonObject.put("orgVersionType", (Object)entity.getOrgType());
        dataJsonObject.put("reportZbDataMap", entity.getReportZbData());
        dataJsonObject.put("isAllorgChoose", false);
        dataJsonObject.put("formIds", this.getFormForEntity(entity));
        dataJsonObject.put("envContext", (Object)new JSONObject(JsonUtils.writeValueAsString((Object)this.getContext(entity, periodWrapper))));
        jsonObject.put("data", (Object)dataJsonObject);
        return jsonObject;
    }

    private List<String> getFormForEntity(ConversionBatchAuditRunnerEntity entity) {
        HashSet formList = new HashSet();
        entity.getFormKeyListMap().forEach(map -> formList.add(map.get("formKey")));
        entity.getReportZbData().forEach((formKey, zbValues) -> formList.add(formKey));
        return new ArrayList<String>(formList);
    }

    private DataEntryContext getContext(ConversionBatchAuditRunnerEntity entity, PeriodWrapper periodWrapper) {
        DataEntryContext context = new DataEntryContext();
        context.setTaskKey(entity.getTaskId());
        context.setFormSchemeKey(entity.getSchemeId());
        context.setDimensionSet(this.getDimensionSet(entity, periodWrapper));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskKey", (Object)entity.getTaskId());
        return context;
    }

    private Map<String, DimensionValue> getDimensionSet(ConversionBatchAuditRunnerEntity entity, PeriodWrapper periodWrapper) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue periodDimen = new DimensionValue();
        periodDimen.setName("DATATIME");
        periodDimen.setValue(periodWrapper.toString());
        dimensionSet.put("DATATIME", periodDimen);
        DimensionValue orgTypeDimen = new DimensionValue();
        orgTypeDimen.setName("MD_GCORGTYPE");
        orgTypeDimen.setValue(entity.getOrgType());
        dimensionSet.put("MD_GCORGTYPE", orgTypeDimen);
        return dimensionSet;
    }
}

