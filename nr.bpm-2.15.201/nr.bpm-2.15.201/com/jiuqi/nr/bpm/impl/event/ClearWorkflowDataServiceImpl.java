/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ClearWorkflowDataServiceImpl
implements IDataSchemeDataClearExtendService {
    private static final Logger logger = LoggerFactory.getLogger(ClearWorkflowDataServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private JdbcTemplate jdbc;

    public void doClear(DataClearParamObj clearParam) {
        List taskKeys = clearParam.getTaskKey();
        if (taskKeys != null && taskKeys.size() > 0) {
            for (String taskKey : taskKeys) {
                try {
                    List formSchemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
                    for (FormSchemeDefine formSchemeDefine : formSchemes) {
                        this.deleteState(formSchemeDefine.getFormSchemeCode());
                        this.deleteHisState(formSchemeDefine.getFormSchemeCode());
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteState(String formSchemeCode) {
        Assert.notNull((Object)formSchemeCode, "formSchemeCode is must not be null!");
        String tableCode = "SYS_UP_ST_" + formSchemeCode;
        this.delete(tableCode);
    }

    private void deleteHisState(String formSchemeCode) {
        Assert.notNull((Object)formSchemeCode, "formSchemeCode is must not be null!");
        String tableCode = "SYS_UP_HI_" + formSchemeCode;
        this.delete(tableCode);
    }

    private void delete(String tableCode) {
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableCode);
        if (tableModel != null) {
            List fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
            DimensionValueSet masterKey = new DimensionValueSet();
            this.delete(masterKey, tableCode, fields);
        }
    }

    public int delete(DimensionValueSet dimensionValue, String tableName, List<ColumnModelDefine> allFields) {
        int deleteRow = 0;
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            for (ColumnModelDefine columnModelDefine : allFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            DimensionSet dimensionSet = dimensionValue.getDimensionSet();
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String name = dimensionSet.get(i);
                ColumnModelDefine column = dimensionChanger.getColumn(name);
                queryModel.getColumnFilters().put(column, dimensionValue.getValue(name));
            }
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator dataUpdate = updatableDataAccess.openForUpdate(context);
            dataUpdate.deleteAll();
            dataUpdate.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5f02\u5e38\uff01", e);
            return deleteRow;
        }
        return ++deleteRow;
    }
}

