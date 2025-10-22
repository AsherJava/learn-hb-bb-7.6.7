/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.period.internal.simpleTitle;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
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
public class PeriodAddSimpleTitleProcessor {
    Logger logger = LoggerFactory.getLogger(PeriodAddSimpleTitleProcessor.class);
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    public static final String QUERY_PERIOD = "select * from nr_period";

    public void createData() {
        try {
            List periodList = this.jdbcTemplate.queryForList(QUERY_PERIOD);
            for (Map periodDefine : periodList) {
                String code = (String)periodDefine.get("P_CODE");
                PeriodType type = PeriodType.fromType((int)Integer.parseInt(periodDefine.get("P_TYPE").toString()));
                DesignTableModelDefine table = this.designDataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
                List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(table.getID());
                DesignColumnModelDefine simpleTitle = this.periodDataDao.createSimpleTitle(table.getID());
                boolean hasColumn = false;
                for (DesignColumnModelDefine columnModelDefine : columnModelDefines) {
                    if (!columnModelDefine.getCode().equals(simpleTitle.getCode())) continue;
                    hasColumn = true;
                }
                if (!hasColumn) {
                    this.designDataModelService.insertColumnModelDefine(simpleTitle);
                    this.dataModelDeployService.deployTable(table.getID());
                }
                if (!type.equals((Object)PeriodType.MONTH) && !type.equals((Object)PeriodType.HALFYEAR)) continue;
                List<PeriodDataDefineImpl> list = this.periodDataDao.getDataListSqls(code);
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                NvwaQueryModel queryModel = new NvwaQueryModel();
                List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
                columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
                for (ColumnModelDefine column : columns) {
                    queryModel.getColumns().add(new NvwaQueryColumn(column));
                }
                INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                INvwaDataUpdator updaor = dataAccess.openForUpdate(context);
                for (PeriodDataDefineImpl define : list) {
                    INvwaDataRow newRow = updaor.addUpdateRow(ArrayKey.copyOf((Object[])new Object[]{define.getCode()}));
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
                    if (PeriodUtils.isPeriod13(code, type)) {
                        newRow.setValue(16, (Object)PeriodUtils.autoMonthSimpleTitle(type, define.getMonth()));
                        continue;
                    }
                    if (!type.equals((Object)PeriodType.HALFYEAR) || define.getTitle().equals(PeriodUtils.getDateStrFromPeriod(define.getCode()))) continue;
                    newRow.setValue(16, (Object)define.getTitle());
                }
                updaor.commitChanges(context);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

