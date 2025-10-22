/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.examine.service.Impl;

import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class DataSchemeDataClearServiceImpl
implements IDataSchemeDataClearService {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeDataClearServiceImpl.class);
    @Autowired
    private List<IDataSchemeDataClearExtendService> clearExtendService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void clearDataSchemeData(DataClearParamObj paramObj) {
        this.clearDsData(paramObj.getDataSchemeKey());
        for (IDataSchemeDataClearExtendService dataClearExtendService : this.clearExtendService) {
            dataClearExtendService.doClear(paramObj);
        }
    }

    private void clearDsData(String dataSchemeKey) throws DataAccessException {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey);
        List accountTables = this.runtimeDataSchemeService.getAllDataTableBySchemeAndTypes(dataSchemeKey, new DataTableType[]{DataTableType.ACCOUNT});
        Set<Object> accountTableKeys = new HashSet();
        if (!CollectionUtils.isEmpty(accountTables)) {
            accountTableKeys = accountTables.stream().map(Basic::getKey).collect(Collectors.toSet());
        }
        HashSet<String> tableNames = new HashSet<String>();
        for (DataFieldDeployInfo deployInfo : deployInfos) {
            tableNames.add(deployInfo.getTableName());
            if (CollectionUtils.isEmpty(accountTableKeys) || !accountTableKeys.contains(deployInfo.getDataTableKey()) || deployInfo.getTableName().endsWith("_RPT")) continue;
            tableNames.add(deployInfo.getTableName() + "_HIS");
            accountTableKeys.remove(deployInfo.getDataTableKey());
        }
        for (String tableName : tableNames) {
            this.clearTableData(tableName);
        }
    }

    private void clearTableData(String tableName) throws DataAccessException {
        StringBuffer sql = new StringBuffer("TRUNCATE");
        sql.append(" TABLE");
        sql.append(" " + tableName);
        try {
            this.jdbcTemplate.execute(sql.toString());
            logger.info("{}\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)tableName);
        }
        catch (Exception e) {
            logger.error(tableName + "\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
        }
    }
}

