/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.summary.service.impl;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.service.IDataAccessService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
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
import org.springframework.stereotype.Service;

@Service
public class DataAccessServiceImpl
implements IDataAccessService {
    private static final Logger logger = LoggerFactory.getLogger(DataAccessServiceImpl.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;

    @Override
    public int getDataCount(String tableCode) throws SummaryCommonException {
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
            List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            queryModel.getColumns().add(new NvwaQueryColumn((ColumnModelDefine)allColumnModels.get(0)));
            INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            return dataSet.size();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.DATA_QUERY_FAILED, tableCode, e.getMessage());
        }
    }
}

