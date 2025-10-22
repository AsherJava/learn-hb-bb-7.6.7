/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasureUtils {
    private static final Logger logger = LoggerFactory.getLogger(MeasureUtils.class);

    public static DataRow queryMeasureDataByCode(TableModelDefine tableModeDefine, String measureValue) {
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        INvwaDataAccessProvider iNvwaDataAccessProvider = (INvwaDataAccessProvider)BeanUtil.getBean(INvwaDataAccessProvider.class);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List allColumns = dataModelService.getColumnModelDefinesByTable(tableModeDefine.getID());
        allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(allColumns.get(1), measureValue);
        try {
            INvwaDataAccess dataAccess = iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            if (result.size() == 1) {
                DataRow dataRow = result.get(0);
                return dataRow;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static double getRate(DataRow row) {
        return row.getString(4) != null ? new BigDecimal(row.getString(4)).doubleValue() : new BigDecimal(0).doubleValue();
    }
}

