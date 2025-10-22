/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package nr.midstore.core.internal.dataset.clear;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nr.midstore.core.dataset.clear.IMidstoreDataSetClearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MidstoreDataSetClearServiceImpl
implements IMidstoreDataSetClearService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreDataSetClearServiceImpl.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;

    @Override
    public void clearTableData(String formSchemeKey, String dataTableKey, DimensionValueSet dimesions) throws Exception {
        IDataQuery dataQuery = this.getDataQuery();
        ExecutorContext context = this.getExecutorContext(formSchemeKey, null, dimesions, null);
        dataQuery.setMasterKeys(dimesions);
        List allFieldsInTable = null;
        try {
            allFieldsInTable = this.dataDefinitionRuntimeController.getAllFieldsInTable(dataTableKey);
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (allFieldsInTable == null) {
            return;
        }
        DataTable dataTable = this.dataSchemeService.getDataTable(dataTableKey);
        for (FieldDefine fieldDefine : allFieldsInTable) {
            dataQuery.addColumn(fieldDefine);
        }
        if (dataTable.getDataTableType() != DataTableType.TABLE) {
            try {
                IDataUpdator openForUpdate = dataQuery.openForUpdate(context, true);
                openForUpdate.commitChanges();
            }
            catch (Exception e) {
                logger.error("\u6d6e\u52a8\u6570\u636e\u6e05\u9664\u6267\u884c\u51fa\u9519:" + e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void clearRegionData(String formSchemeKey, String dataRegionKey, DimensionValueSet dimesions) throws Exception {
        DataRegionDefine regionDefine = this.runTimeViewController.queryDataRegionDefine(dataRegionKey);
        IDataQuery dataQuery = this.getDataQuery(formSchemeKey, dataRegionKey);
        ExecutorContext context = this.getExecutorContext(formSchemeKey, regionDefine.getFormKey(), dimesions, null);
        dataQuery.setMasterKeys(dimesions);
        List allFieldsInTable = null;
        try {
            List fields = this.runTimeViewController.getFieldKeysInRegion(dataRegionKey);
            allFieldsInTable = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)fields);
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (allFieldsInTable == null) {
            return;
        }
        for (FieldDefine fieldDefine : allFieldsInTable) {
            dataQuery.addColumn(fieldDefine);
        }
        if (regionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            try {
                IDataUpdator openForUpdate = dataQuery.openForUpdate(context, true);
                openForUpdate.commitChanges();
            }
            catch (Exception e) {
                logger.error("\u6d6e\u52a8\u6570\u636e\u6e05\u9664\u6267\u884c\u51fa\u9519:" + e.getMessage(), e);
                throw e;
            }
        }
    }

    public IDataQuery getDataQuery() {
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery();
        return dataQuery;
    }

    public IDataQuery getDataQuery(String formSchemeKey, String dataRegionKey) {
        DataRegionDefine regionDefine = this.runTimeViewController.queryDataRegionDefine(dataRegionKey);
        FormDefine formDefine = this.runTimeViewController.queryFormById(regionDefine.getFormKey());
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        QueryEnvironment queryEnvironment = this.buildEnv(formSchemeKey, dataRegionKey, null, formDefine.getKey(), formDefine.getFormCode());
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setDefaultGroupName(formDefine.getFormCode());
        return dataQuery;
    }

    private QueryEnvironment buildEnv(String formSchemeKey, String regionKey, String formulaSchemeKey, String formKey, String formCode) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formSchemeKey);
        queryEnvironment.setRegionKey(regionKey);
        queryEnvironment.setFormulaSchemeKey(formulaSchemeKey);
        queryEnvironment.setFormKey(formKey);
        queryEnvironment.setFormCode(formCode);
        return queryEnvironment;
    }

    public ExecutorContext getExecutorContext(String formSchemeKey, String formKey, DimensionValueSet Dimesions, Map<String, Object> var) {
        VariableManager variableManager;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (StringUtils.isNotEmpty((String)formKey)) {
            FormDefine form = this.runTimeViewController.queryFormById(formKey);
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        executorContext.setVarDimensionValueSet(Dimesions);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        if (var != null && !CollectionUtils.isEmpty(var) && (variableManager = executorContext.getVariableManager()) != null) {
            for (Map.Entry<String, Object> variableEnt : var.entrySet()) {
                Variable variable = new Variable(variableEnt.getKey(), 6);
                variable.setVarValue(variableEnt.getValue());
                variableManager.add(variable);
            }
        }
        return executorContext;
    }
}

