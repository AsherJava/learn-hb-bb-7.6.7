/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflowService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.calibre2.ICalibreDataRegionService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.domain.CalibreDataRegion
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.dataset.calibersum;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.calibersum.CaliberSumContext;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.calibersum.query.CaliberSumCKRQuerier;
import com.jiuqi.bi.dataset.calibersum.query.CaliberSumDataQuerier;
import com.jiuqi.bi.dataset.calibersum.query.CaliberSumUnitStateQuerier;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumResultSet;
import com.jiuqi.bi.dataset.remote.model.CaliberSumDSDefine;
import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflowService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.calibre2.ICalibreDataRegionService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaliberSumQueryExecutor {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private ICalibreDataRegionService calibreDataRegionService;
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private INvwaDataAccessProvider accessProvider;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private CaliberSumCKRQuerier CKRQuerier;
    @Autowired
    private CaliberSumUnitStateQuerier unitStateQuerier;
    @Autowired
    private IWorkflowService workflowService;

    public void runQuery(CaliberSumDSModel dsModel, IDSContext dsContext, int pageSize, int currentPage, MemoryDataSet<BIDataSetFieldInfo> result) throws Exception {
        CalibreDefineDTO calibreDefine;
        DimensionValueSet destMasterKeys;
        CaliberSumDSDefine caliberSumDSDefine = dsModel.getCaliberSumDSDefine();
        CaliberSumContext context = this.createExecutorContext(caliberSumDSDefine, destMasterKeys = this.buildMasterKeys(dsModel, calibreDefine = this.getCaliberDefine(caliberSumDSDefine), dsContext));
        if (context != null) {
            List<CalibreDataRegion> list = this.getCalibreDataRegions(context, calibreDefine, destMasterKeys);
            this.doPage(pageSize, currentPage, list);
            boolean onlyReportUnit = caliberSumDSDefine.getOnlyReportUnit() == null ? false : caliberSumDSDefine.getOnlyReportUnit();
            DimensionValueSet srcMasterKeys = this.createSrcMasterKeys(context, destMasterKeys, list, onlyReportUnit);
            CaliberSumDataQuerier querier = new CaliberSumDataQuerier(srcMasterKeys, context, dsModel, this.dataModelService, this.accessProvider);
            CaliberSumResultSet resultSet = querier.runQuery(list);
            this.CKRQuerier.load(context, resultSet);
            this.unitStateQuerier.load(context, resultSet);
            resultSet.toResult(result);
            context.getLogger().debug("\u6c47\u603b\u6570\u636e\u96c6\u67e5\u8be2\u7ed3\u679c\uff1a\n" + result);
        }
    }

    protected DimensionValueSet createSrcMasterKeys(CaliberSumContext context, DimensionValueSet destMasterKeys, List<CalibreDataRegion> list, boolean onlyReportUnit) {
        DimensionValueSet sourceMasterKeys = new DimensionValueSet(destMasterKeys);
        HashSet unitKeys = new HashSet();
        for (CalibreDataRegion calibreDataRegion : list) {
            unitKeys.addAll(calibreDataRegion.getEntityKeys());
        }
        String unitDim = context.getUnitDim();
        sourceMasterKeys.setValue(unitDim, new ArrayList(unitKeys));
        if (onlyReportUnit) {
            try {
                ArrayList<UploadState> uploadStates = new ArrayList<UploadState>(1);
                uploadStates.add(UploadState.UPLOADED);
                List stateRecords = this.workflowService.getDataByActionCode(context.getFormScheme(), sourceMasterKeys, uploadStates);
                ArrayList<String> uploadUnits = new ArrayList<String>(stateRecords.size());
                for (UploadStateNew state : stateRecords) {
                    DimensionValueSet dim = state.getEntities();
                    uploadUnits.add(dim.getValue(unitDim).toString());
                }
                sourceMasterKeys.setValue(unitDim, uploadUnits);
            }
            catch (Exception e) {
                context.getLogger().error(e.getMessage(), e);
            }
        }
        return sourceMasterKeys;
    }

    public int getRecordCount(CaliberSumDSModel dsModel, IDSContext dsContext) throws Exception {
        CaliberSumDSDefine caliberSumDSDefine = dsModel.getCaliberSumDSDefine();
        CalibreDefineDTO calibreDefine = this.getCaliberDefine(caliberSumDSDefine);
        DimensionValueSet destMasterKeys = this.buildMasterKeys(dsModel, calibreDefine, dsContext);
        CaliberSumContext context = this.createExecutorContext(caliberSumDSDefine, destMasterKeys);
        List<CalibreDataRegion> list = this.getCalibreDataRegions(context, calibreDefine, destMasterKeys);
        return list.size();
    }

    private CalibreDefineDTO getCaliberDefine(CaliberSumDSDefine caliberSumDSDefine) {
        CalibreDefineDTO defineDTO = new CalibreDefineDTO();
        defineDTO.setKey(caliberSumDSDefine.getCaliberId());
        CalibreDefineDTO calibreDefine = (CalibreDefineDTO)this.calibreDefineService.get(defineDTO).getData();
        return calibreDefine;
    }

    private List<CalibreDataRegion> getCalibreDataRegions(CaliberSumContext context, CalibreDefineDTO calibreDefine, DimensionValueSet masterKeys) throws Exception {
        List list = this.calibreDataRegionService.getList(context.getExecutorContext(), (CalibreDefineDO)calibreDefine, masterKeys);
        for (int i = list.size() - 1; i >= 0; --i) {
            CalibreDataRegion region = (CalibreDataRegion)list.get(i);
            if (!region.getEntityKeys().isEmpty()) continue;
            list.remove(i);
        }
        return list;
    }

    private CaliberSumContext createExecutorContext(CaliberSumDSDefine caliberSumDSDefine, DimensionValueSet masterKeys) throws Exception {
        String period = (String)masterKeys.getValue("DATATIME");
        String fromSchemeKey = null;
        SchemePeriodLinkDefine schemePeriodLink = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, caliberSumDSDefine.getTaskId());
        if (schemePeriodLink != null) {
            fromSchemeKey = schemePeriodLink.getSchemeKey();
        } else {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(caliberSumDSDefine.getTaskId());
            if (formSchemeDefines.size() > 0) {
                fromSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
            }
        }
        if (fromSchemeKey == null) {
            return null;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, fromSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)env);
        CaliberSumContext conetxt = new CaliberSumContext(executorContext);
        conetxt.setDestMasterKeys(masterKeys);
        conetxt.setPeriod(period);
        if (fromSchemeKey != null) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(fromSchemeKey);
            conetxt.setFormScheme(formScheme);
            FormulaSchemeDefine formulaScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(fromSchemeKey);
            conetxt.setFormulaSchemeKey(formulaScheme.getKey());
        }
        return conetxt;
    }

    private void doPage(int pageSize, int currentPage, List<CalibreDataRegion> list) {
        if (pageSize > 0) {
            int beginIndex = currentPage * pageSize;
            int endIndex = beginIndex + pageSize;
            if (endIndex > list.size()) {
                endIndex = list.size();
            }
            if (endIndex > beginIndex) {
                list = list.subList(beginIndex, endIndex);
            }
        }
    }

    private DimensionValueSet buildMasterKeys(CaliberSumDSModel dsModel, CalibreDefineDTO calibreDefine, IDSContext dsContext) throws Exception {
        List values;
        AbstractParameterDataSourceModel dataSourceModel;
        DimensionValueSet masterKeys = new DimensionValueSet();
        String period = null;
        for (ParameterModel parameterModel : dsModel.getParameterModels()) {
            dataSourceModel = parameterModel.getDatasource();
            if (!(dataSourceModel instanceof NrPeriodDataSourceModel) || (values = dsContext.getEnhancedParameterEnv().getValueAsList(parameterModel.getName().toUpperCase())) == null || values.size() <= 0) continue;
            period = (String)values.get(0);
            NrPeriodDataSourceModel nrPeriodDataSourceModel = (NrPeriodDataSourceModel)dataSourceModel;
            period = TimeDimUtils.timeKeyToPeriod((String)period, (PeriodType)PeriodType.fromType((int)nrPeriodDataSourceModel.getPeriodType()));
            masterKeys.setValue("DATATIME", (Object)period);
        }
        for (ParameterModel parameterModel : dsModel.getParameterModels()) {
            dataSourceModel = parameterModel.getDatasource();
            values = dsContext.getEnhancedParameterEnv().getValueAsList(parameterModel.getName());
            if (values == null || values.size() <= 0 || !(dataSourceModel instanceof NrEntityDataSourceModel)) continue;
            String entityId = ((NrEntityDataSourceModel)dataSourceModel).getEntityViewId();
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            String dimName = entityDefine.getDimensionName();
            if (entityId.equals(calibreDefine.getEntityId()) && values.size() == 1) {
                String rootKey = (String)values.get(0);
                masterKeys.setValue(dimName, this.getEntityKeys(entityDefine, period, rootKey));
                continue;
            }
            masterKeys.setValue(dimName, (Object)values);
        }
        return masterKeys;
    }

    private List<String> getEntityKeys(IEntityDefine entityDefine, String period, String rootKey) throws Exception {
        ArrayList<String> unitKeys = new ArrayList<String>();
        unitKeys.add(rootKey);
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)period);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(dim);
        iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityDefine.getId()));
        IEntityTable entityTable = iEntityQuery.executeReader(null);
        List allChildRows = entityTable.getAllChildRows(rootKey);
        for (IEntityRow row : allChildRows) {
            unitKeys.add(row.getEntityKeyData());
        }
        return unitKeys;
    }
}

