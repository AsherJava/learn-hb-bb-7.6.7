/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.impl.DataAccessProviderImpl
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.util.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.de.dataflow.updatedata;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.impl.DataAccessProviderImpl;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.updatedata.IUpdateStateService;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UpdateStateServiceImpl
implements IUpdateStateService {
    private final Logger logger = LoggerFactory.getLogger(UpdateStateServiceImpl.class);
    private static final String FORM_KEY = "11111111-1111-1111-1111-111111111111";
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    ProcessStateHistoryDao processStateHistoryDao;
    @Autowired
    DataAccessProviderImpl dataAccessProvider;
    @Autowired
    IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DeEntityHelper deEntityHelper;
    @Autowired
    BusinessGenerator businessGenerator;
    @Autowired
    DimensionUtil dimensionUtil;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean updateState(String taskKey, String period) {
        try {
            boolean taskRepeate = this.isTaskRepeate(taskKey);
            if (taskRepeate) {
                this.logger.info("\u7cfb\u7edf\u4e2d\u5b58\u5728\u91cd\u590d\u7684\u4efb\u52a1,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                return false;
            }
            List<String> periodList = this.periodToList(period);
            if (periodList.size() == 0) {
                this.logger.info("\u672a\u6307\u5b9a\u5177\u4f53\u7684\u65f6\u671f,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                return false;
            }
            StringBuffer sb = new StringBuffer();
            boolean isPrimary = false;
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            sb.append("\n").append("----------------------------------------").append("\n");
            sb.append("\u4efb\u52a1\u540d\u79f0\uff1a" + taskDefine.getTitle()).append("\n");
            List formSchemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine formScheme : formSchemes) {
                sb.append("\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle()).append("\n");
                sb.append("\u65f6\u671f:" + periodList).append("\n");
                WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
                if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                    isPrimary = true;
                }
                List<DimensionValueSet> datas = this.getDatasByPeriod(formScheme, periodList);
                sb.append("\u4fee\u590d\u7684\u5355\u4f4d\u4e2a\u6570\uff1a" + datas.size()).append("\n");
                for (DimensionValueSet dimensionValueSet : datas) {
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), dimensionValueSet, FORM_KEY, FORM_KEY, isPrimary);
                    try {
                        List<UploadStateNew> uploadStates = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, null);
                        if (uploadStates == null || uploadStates.size() <= 0) continue;
                        UploadStateNew uploadState = (UploadStateNew)uploadStates.stream().findAny().get();
                        boolean forceUpload = uploadState.getActionStateBean() == null ? false : uploadState.getActionStateBean().isForceUpload();
                        this.processStateHistoryDao.updateState(businessKey, uploadState.getTaskId(), uploadState.getPreEvent(), forceUpload, null);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            sb.append("----------------------------------------");
            this.logger.info(sb.toString());
            return true;
        }
        catch (Exception e) {
            this.logger.info("\u72b6\u6001\u66f4\u65b0\u5f02\u5e38", (Object)e.getMessage());
            return false;
        }
    }

    private boolean isTaskRepeate(String taskKey) {
        ArrayList<TaskDefine> tasks = new ArrayList<TaskDefine>();
        try {
            List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            for (TaskDefine taskDefineTemp : allTaskDefines) {
                if (!taskDefineTemp.getTitle().equals(taskDefine.getTitle())) continue;
                tasks.add(taskDefineTemp);
            }
            if (tasks.size() > 1) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<String> periodToList(String period) {
        String[] priodStr;
        ArrayList<String> periods = new ArrayList<String>();
        if (StringUtils.isEmpty((String)period)) {
            return periods;
        }
        for (String string : priodStr = period.split(";")) {
            periods.add(string);
        }
        return periods;
    }

    private List<DimensionValueSet> getDatasByPeriod(FormSchemeDefine formScheme, List<String> periodList) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        DimensionValueSet dimensionValueSet = null;
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
        if (periodList.size() > 0) {
            for (String pStr : periodList) {
                List<IEntityRow> entityRow = this.deEntityHelper.getEntityRow(formScheme.getKey(), pStr);
                if (entityRow.size() <= 0) continue;
                for (IEntityRow iEntityRow : entityRow) {
                    dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)pStr);
                    String entityKeyData = iEntityRow.getEntityKeyData();
                    dimensionValueSet.setValue(dwMainDimName, (Object)entityKeyData);
                    dims.add(dimensionValueSet);
                }
            }
        }
        return dims;
    }

    @Override
    public boolean updateState(String taskKey, String period, String unitId) {
        try {
            boolean taskRepeate = this.isTaskRepeate(taskKey);
            if (taskRepeate) {
                this.logger.info("\u7cfb\u7edf\u4e2d\u5b58\u5728\u91cd\u590d\u7684\u4efb\u52a1,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                return false;
            }
            List<String> periodList = this.periodToList(period);
            if (periodList.size() == 0) {
                this.logger.info("\u672a\u6307\u5b9a\u5177\u4f53\u7684\u65f6\u671f,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                return false;
            }
            StringBuffer sb = new StringBuffer();
            boolean isPrimary = false;
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            sb.append("\n").append("----------------------------------------").append("\n");
            sb.append("\u4efb\u52a1\u540d\u79f0\uff1a" + taskDefine.getTitle()).append("\n");
            List formSchemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine formScheme : formSchemes) {
                sb.append("\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle()).append("\n");
                sb.append("\u65f6\u671f:" + periodList).append("\n");
                WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
                if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                    isPrimary = true;
                }
                String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
                DimensionValueSet dim = new DimensionValueSet();
                dim.setValue("DATATIME", (Object)period);
                dim.setValue(dwMainDimName, (Object)unitId);
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), dim, FORM_KEY, FORM_KEY, isPrimary);
                try {
                    List<UploadStateNew> uploadStates = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dim, null);
                    if (uploadStates == null || uploadStates.size() <= 0) continue;
                    UploadStateNew uploadState = (UploadStateNew)uploadStates.stream().findAny().get();
                    boolean forceUpload = uploadState.getActionStateBean() == null ? false : uploadState.getActionStateBean().isForceUpload();
                    this.processStateHistoryDao.updateState(businessKey, uploadState.getTaskId(), uploadState.getPreEvent(), forceUpload, null);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sb.append("----------------------------------------");
            this.logger.info(sb.toString());
            return true;
        }
        catch (Exception e) {
            this.logger.info("\u72b6\u6001\u66f4\u65b0\u5f02\u5e38", (Object)e.getMessage());
            return false;
        }
    }

    @Override
    public StringBuffer batchUpdateState(String taskKey, String period) {
        StringBuffer sb = new StringBuffer();
        try {
            boolean taskRepeate = this.isTaskRepeate(taskKey);
            if (taskRepeate) {
                sb.append("\u7cfb\u7edf\u4e2d\u5b58\u5728\u91cd\u590d\u7684\u4efb\u52a1,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                this.logger.info("\u7cfb\u7edf\u4e2d\u5b58\u5728\u91cd\u590d\u7684\u4efb\u52a1,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                return sb;
            }
            List<String> periodList = this.periodToList(period);
            if (periodList.size() == 0) {
                sb.append("\u672a\u6307\u5b9a\u5177\u4f53\u7684\u65f6\u671f,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                this.logger.info("\u672a\u6307\u5b9a\u5177\u4f53\u7684\u65f6\u671f,\u4e0d\u505a\u72b6\u6001\u66f4\u65b0\u7684\u64cd\u4f5c");
                return sb;
            }
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            if (taskDefine == null) {
                this.logger.info("\u4efb\u52a1\u4e0d\u5b58\u5728\uff0ckey=" + taskKey);
                sb.append("\u4efb\u52a1\u4e0d\u5b58\u5728\uff0ckey=" + taskKey);
                return sb;
            }
            FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(taskKey, period);
            if (formSchemeDefine == null) {
                this.logger.info("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\uff0ctaskKey=" + taskKey + ",period =" + period);
                sb.append("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\uff0ctaskKey=" + taskKey + ",period =" + period);
                return sb;
            }
            sb.append("\n").append("----------------------------------------").append("\n");
            sb.append("\u4efb\u52a1\u540d\u79f0\uff1a" + taskDefine.getTitle()).append("\n");
            List<String> unitIds = this.queryUnits();
            sb.append("\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeDefine.getTitle()).append("\n");
            sb.append("\u65f6\u671f:" + periodList).append("\n");
            sb.append("\u9700\u8981\u4fee\u590d\u7684\u5355\u4f4d\u6570\u91cf\uff1a" + unitIds.size()).append("\n");
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeDefine.getKey());
            DimensionValueSet dim = new DimensionValueSet();
            dim.setValue("DATATIME", (Object)period);
            dim.setValue(dwMainDimName, unitIds);
            try {
                List<UploadStateNew> uploadStates = this.batchQueryUploadStateService.queryUploadStateNew(formSchemeDefine, dim, null);
                if (uploadStates != null && uploadStates.size() > 0) {
                    UploadStateNew uploadState = (UploadStateNew)uploadStates.stream().findAny().get();
                    boolean forceUpload = uploadState.getActionStateBean() == null ? false : uploadState.getActionStateBean().isForceUpload();
                    this.processStateHistoryDao.updateUnitState(formSchemeDefine.getKey(), dim, taskKey, uploadState.getPreEvent(), forceUpload);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            sb.append("----------------------------------------");
            this.logger.info(sb.toString());
        }
        catch (Exception e) {
            sb.append(e.getMessage());
            this.logger.info("\u72b6\u6001\u66f4\u65b0\u5f02\u5e38", (Object)e.getMessage());
            return sb;
        }
        return sb;
    }

    private FormSchemeDefine getFormSchemeDefine(String taskKey, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
            if (schemePeriodLinkDefine != null) {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                return formScheme;
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return null;
    }

    private List<String> queryUnits() {
        ArrayList<String> units = new ArrayList<String>();
        String sql = "select unit_code from DT_TEMP_CQ_TO_GL_UNIT_MAP";
        try {
            this.jdbcTemplate.query(sql, rs -> {
                String unitCode = rs.getString("unit_code");
                units.add(unitCode);
            });
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return units;
    }
}

