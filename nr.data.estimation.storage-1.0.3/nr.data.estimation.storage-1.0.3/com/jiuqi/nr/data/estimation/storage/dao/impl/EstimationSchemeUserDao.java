/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.storage.dao.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationDimTableDao;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeTemplateDao;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeUserDao;
import com.jiuqi.nr.data.estimation.storage.entity.impl.DimTableRecord;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationScheme;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class EstimationSchemeUserDao
implements IEstimationSchemeUserDao {
    @Resource
    private DataModelService dataModelService;
    @Resource
    private IEstimationDimTableDao dimTableDao;
    @Resource
    private IEstimationSchemeTemplateDao templateDao;
    @Resource
    private INvwaDataAccessProvider iNvwaDataAccessProvider;

    @Override
    public int insertEstimationScheme(EstimationScheme scheme) {
        int successCount = this.templateDao.insertEstimationScheme(scheme);
        if (successCount > 0) {
            try {
                this.insertEstimationDims(scheme, scheme.getDimValueSet());
            }
            catch (Exception e) {
                this.templateDao.deleteEstimationScheme(scheme.getKey());
                successCount = 0;
            }
        }
        return successCount;
    }

    @Override
    public int updateEstimationScheme(EstimationScheme scheme) {
        return 0;
    }

    @Override
    public int removeEstimationScheme(String estimationSchemeKey) {
        EstimationSchemeBase schemeBase = this.templateDao.findEstimationScheme(estimationSchemeKey);
        if (schemeBase != null) {
            try {
                this.removeEstimationDims(schemeBase);
                this.templateDao.deleteEstimationScheme(estimationSchemeKey);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @Override
    public EstimationScheme findEstimationScheme(String estimationScheme, DimensionValueSet dimValueSet) {
        Map<String, DimensionValueSet> dimMap;
        EstimationSchemeBase schemeBase = this.templateDao.findEstimationScheme(estimationScheme);
        try {
            dimMap = this.queryEstimationDims(schemeBase.getFormSchemeId(), Collections.singletonList(schemeBase.getKey()));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (dimMap.containsKey(schemeBase.getKey())) {
            return new EstimationScheme(schemeBase, dimMap.get(schemeBase.getKey()));
        }
        return null;
    }

    @Override
    public List<EstimationScheme> findEstimationSchemes(String creator, String formSchemeId, DimensionValueSet dimValueSet) {
        ArrayList<EstimationScheme> schemes = new ArrayList<EstimationScheme>();
        List<EstimationSchemeBase> estimationSchemes = this.templateDao.findEstimationSchemes(creator, formSchemeId);
        if (estimationSchemes != null && !estimationSchemes.isEmpty()) {
            Map<String, DimensionValueSet> dimMap;
            try {
                dimMap = this.queryEstimationDims(formSchemeId, estimationSchemes.stream().map(EstimationSchemeBase::getKey).collect(Collectors.toList()));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            estimationSchemes.forEach(schemeBase -> schemes.add(new EstimationScheme((EstimationSchemeBase)schemeBase, (DimensionValueSet)dimMap.get(schemeBase.getKey()))));
        }
        return schemes;
    }

    private Map<String, DimensionValueSet> queryEstimationDims(String formSchemeId, List<String> estimationKeys) throws Exception {
        HashMap<String, DimensionValueSet> dimMap = new HashMap<String, DimensionValueSet>();
        NvwaQueryModel queryModel = this.newNvwaQueryModel(formSchemeId);
        NvwaQueryColumn nvwaColumn = queryModel.getColumns().stream().filter(nvwaQueryColumn -> "ESTIMATION_SCHEME".equals(nvwaQueryColumn.getColumnModel().getCode())).findFirst().get();
        queryModel.getColumnFilters().put(nvwaColumn.getColumnModel(), estimationKeys);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        MemoryDataSet dataSet = readOnlyDataAccess.executeQuery(context);
        Iterator iterator = dataSet.iterator();
        while (iterator.hasNext()) {
            String estimationSchemeKey = null;
            DimensionValueSet dimValueSet = new DimensionValueSet();
            DataRow dataRow = (DataRow)iterator.next();
            List columns = queryModel.getColumns();
            for (int idx = 0; idx < columns.size(); ++idx) {
                String fieldCode = ((NvwaQueryColumn)columns.get(idx)).getColumnModel().getCode();
                String value = dataRow.getString(idx);
                dimValueSet.setValue(fieldCode, (Object)value);
                if (!"ESTIMATION_SCHEME".equals(fieldCode)) continue;
                estimationSchemeKey = value;
            }
            dimMap.put(estimationSchemeKey, dimValueSet);
        }
        return dimMap;
    }

    private void insertEstimationDims(EstimationSchemeBase schemeBase, DimensionValueSet dimValueSet) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(schemeBase.getFormSchemeId());
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        dimValueSet.setValue("ESTIMATION_SCHEME", (Object)schemeBase.getKey());
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        INvwaDataRow dataRow = dataUpdator.addInsertRow();
        List columns = queryModel.getColumns();
        for (int idx = 0; idx < columns.size(); ++idx) {
            dataRow.setValue(idx, dimValueSet.getValue(((NvwaQueryColumn)columns.get(idx)).getColumnModel().getCode()));
        }
        dataUpdator.commitChanges(context);
    }

    private void removeEstimationDims(EstimationSchemeBase schemeBase) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(schemeBase.getFormSchemeId());
        NvwaQueryColumn nvwaColumn = queryModel.getColumns().stream().filter(nvwaQueryColumn -> "ESTIMATION_SCHEME".equals(nvwaQueryColumn.getColumnModel().getCode())).findFirst().get();
        queryModel.getColumnFilters().put(nvwaColumn.getColumnModel(), schemeBase.getKey());
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataSet iNvwaDataRows = dataAccess.executeQueryForUpdate(context);
        iNvwaDataRows.deleteRow(new ArrayKey(new Object[]{schemeBase.getKey()}));
        iNvwaDataRows.commitChanges(context);
    }

    private NvwaQueryModel newNvwaQueryModel(String formSchemeId) {
        DimTableRecord dimTableRecord = this.dimTableDao.findDimTableRecordByFormScheme(formSchemeId);
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(dimTableRecord.getTableName());
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        columnModelDefines.forEach(col -> queryModel.getColumns().add(new NvwaQueryColumn(col)));
        queryModel.setMainTableName(tableModelDefine.getName());
        return queryModel;
    }
}

