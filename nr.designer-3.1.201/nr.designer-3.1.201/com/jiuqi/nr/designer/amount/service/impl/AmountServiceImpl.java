/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.designer.amount.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.designer.amount.service.AmountService;
import com.jiuqi.nr.designer.web.treebean.AmountObject;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmountServiceImpl
implements AmountService {
    private static final Logger log = LoggerFactory.getLogger(AmountServiceImpl.class);
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;

    @Override
    public AmountObject queryById(String id) {
        AmountObject amount = null;
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine table = this.dataModelService.getTableModelDefineByCode("NR_MEASURE_UNIT");
            List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
            for (ColumnModelDefine column : allColumns) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            queryModel.getColumnFilters().put(allColumns.get(0), id);
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            if (result.size() == 1) {
                DataRow dataRow = result.get(0);
                amount = new AmountObject();
                amount.setId(dataRow.getString(0));
                amount.setCode(dataRow.getString(1));
                amount.setTitle(dataRow.getString(2));
                amount.setParent(dataRow.getString(3));
                amount.setRatio(dataRow.getString(4) != null ? new BigDecimal(dataRow.getString(4)) : new BigDecimal(0));
                amount.setBaseunit(dataRow.getInt(5));
                amount.setOrderl(dataRow.getString(6));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return amount;
    }

    @Override
    public AmountObject queryByCode(String code) {
        AmountObject amount = null;
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine table = this.dataModelService.getTableModelDefineByCode("NR_MEASURE_UNIT");
            List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
            for (ColumnModelDefine column : allColumns) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            queryModel.getColumnFilters().put(allColumns.get(1), code);
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            if (result.size() == 1) {
                DataRow dataRow = result.get(0);
                amount = new AmountObject();
                amount.setId(dataRow.getString(0));
                amount.setCode(dataRow.getString(1));
                amount.setTitle(dataRow.getString(2));
                amount.setParent(dataRow.getString(3));
                amount.setRatio(dataRow.getString(4) != null ? new BigDecimal(dataRow.getString(4)) : new BigDecimal(0));
                amount.setBaseunit(dataRow.getInt(5));
                amount.setOrderl(dataRow.getString(6));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return amount;
    }

    @Override
    public List<AmountObject> queryAllAmount() {
        ArrayList<AmountObject> list = new ArrayList<AmountObject>();
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine table = this.dataModelService.getTableModelDefineByCode("NR_MEASURE_UNIT");
            List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
            for (ColumnModelDefine column : columns) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            MemoryDataSet result = dataAccess.executeQuery(context);
            for (DataRow dataRow : result) {
                AmountObject amount = new AmountObject();
                amount.setId(dataRow.getString(0));
                amount.setCode(dataRow.getString(1));
                amount.setTitle(dataRow.getString(2));
                amount.setParent(dataRow.getString(3));
                amount.setRatio(dataRow.getString(4) != null ? new BigDecimal(dataRow.getString(4)) : new BigDecimal(0));
                amount.setBaseunit(dataRow.getInt(5));
                amount.setOrderl(dataRow.getString(6));
                list.add(amount);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return list.stream().sorted((a, b) -> a.getOrderl().compareTo(b.getOrderl())).collect(Collectors.toList());
    }

    @Override
    public List<AmountObject> queryAmountByParent(String parent) {
        ArrayList<AmountObject> amounts = new ArrayList<AmountObject>();
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine table = this.dataModelService.getTableModelDefineByCode("NR_MEASURE_UNIT");
            List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
            for (ColumnModelDefine column : allColumns) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            queryModel.getColumnFilters().put(allColumns.get(3), parent);
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            for (int i = 0; i < result.size(); ++i) {
                DataRow dataRow = result.get(i);
                AmountObject amount = new AmountObject();
                amount.setId(dataRow.getString(0));
                amount.setCode(dataRow.getString(1));
                amount.setTitle(dataRow.getString(2));
                amount.setParent(dataRow.getString(3));
                amount.setRatio(dataRow.getString(4) != null ? new BigDecimal(dataRow.getString(4)) : new BigDecimal(0));
                amount.setBaseunit(dataRow.getInt(5));
                amount.setOrderl(dataRow.getString(6));
                amounts.add(amount);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return amounts.stream().sorted((a, b) -> a.getOrderl().compareTo(b.getOrderl())).collect(Collectors.toList());
    }
}

