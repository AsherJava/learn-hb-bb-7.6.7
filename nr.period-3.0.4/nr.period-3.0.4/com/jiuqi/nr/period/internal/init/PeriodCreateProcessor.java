/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.period.internal.init;

import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodSqlUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PeriodCreateProcessor {
    Logger logger = LoggerFactory.getLogger(PeriodCreateProcessor.class);
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createData() {
        try {
            for (String t : NrPeriodConst.CREATE_PERIOD_DATA_TABLE) {
                List periods = this.jdbcTemplate.queryForList(String.format("SELECT * FROM NR_PERIOD WHERE P_CODE='%s'", t));
                if (null == periods || periods.size() != 1) continue;
                Map stringObjectMap = (Map)periods.get(0);
                PeriodDefineImpl queryPeriodByCode = new PeriodDefineImpl();
                queryPeriodByCode.setKey(stringObjectMap.get("P_KEY").toString());
                queryPeriodByCode.setCode(stringObjectMap.get("P_CODE").toString());
                queryPeriodByCode.setTitle(stringObjectMap.get("P_TITLE").toString());
                this.periodDataDao.createAndDeployTable(PeriodUtils.addPrefix(t), PeriodUtils.periodOfType(t).title(), "\u62a5\u8868" + PeriodUtils.periodOfType(t).title() + "\u62a5\u65f6\u671f\u8868", queryPeriodByCode.getCode());
                List<PeriodDataDefineImpl> list = PeriodSqlUtil.getInitData(t, NrPeriodConst.MAX_INIT_YEAR);
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                NvwaQueryModel queryModel = new NvwaQueryModel();
                TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(t));
                List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
                columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
                for (ColumnModelDefine column : columns) {
                    queryModel.getColumns().add(new NvwaQueryColumn(column));
                }
                INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                INvwaDataUpdator updaor = dataAccess.openForUpdate(context);
                for (PeriodDataDefineImpl define : list) {
                    INvwaDataRow newRow = updaor.addInsertRow();
                    newRow.setValue(0, (Object)define.getKey());
                    newRow.setValue(1, (Object)define.getCode());
                    newRow.setValue(2, (Object)define.getAlias());
                    newRow.setValue(3, (Object)define.getTitle());
                    newRow.setValue(4, (Object)define.getStartDate());
                    newRow.setValue(5, (Object)define.getEndDate());
                    newRow.setValue(6, (Object)define.getYear());
                    newRow.setValue(7, (Object)define.getQuarter());
                    newRow.setValue(8, (Object)define.getMonth());
                    newRow.setValue(9, (Object)define.getDay());
                    newRow.setValue(10, (Object)define.getTimeKey());
                    newRow.setValue(11, (Object)define.getDays());
                    newRow.setValue(12, (Object)define.getCreateTime());
                    newRow.setValue(13, (Object)define.getCreateUser());
                    newRow.setValue(14, (Object)define.getUpdateTime());
                    newRow.setValue(15, (Object)define.getUpdateUser());
                }
                updaor.commitChanges(context);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}

