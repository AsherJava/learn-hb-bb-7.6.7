/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.enums.BalanceTable
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.utils.AsyncCallBackUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.base.impl.option.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.enums.BalanceTable;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.utils.AsyncCallBackUtil;
import com.jiuqi.dc.base.impl.acctperiod.service.AcctPeriodService;
import com.jiuqi.dc.base.impl.option.DcSysOptionIdEnum;
import com.jiuqi.dc.base.impl.option.service.DCSysOptionService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DCSysOptionServiceImpl
extends BaseDataCenterDaoImpl
implements DCSysOptionService {
    @Autowired
    private AcctPeriodService periodService;
    @Autowired
    private INvwaSystemOptionService sysOptionService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private TaskLogService taskLogService;
    private final Logger logger = LoggerFactory.getLogger(DCSysOptionServiceImpl.class);

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ResultObject saveOption(SystemOptionSave optionSave) {
        ArrayList yearList;
        boolean oldOrgn = "1".equals(this.sysOptionService.findValueById(DcSysOptionIdEnum.DC_BALANCE_ORGN_ENABLE.name()));
        ResultObject result = this.sysOptionService.save(optionSave);
        if (!result.isSuccess()) {
            return result;
        }
        boolean orgn = "1".equals(this.sysOptionService.findValueById(DcSysOptionIdEnum.DC_BALANCE_ORGN_ENABLE.name()));
        if (oldOrgn == orgn || !orgn) {
            return result;
        }
        try {
            yearList = this.periodService.listYear();
        }
        catch (Exception e) {
            yearList = CollectionUtils.newArrayList();
        }
        if (CollectionUtils.isEmpty((Collection)yearList)) {
            yearList.add(DateUtils.getYearOfDate((Date)new Date()));
        }
        yearList.add(DateUtils.getYearOfDate((Date)new Date()) + 1);
        ArrayList syncYearList = CollectionUtils.newArrayList();
        for (Integer year : yearList) {
            String tableColumnSql = this.getDbSqlHandler().getTableColumnSql("DC_PREASSBALANCE_" + year);
            ArrayList columnList = CollectionUtils.newArrayList();
            this.getJdbcTemplate().query(tableColumnSql, (rs, rownum) -> {
                columnList.add(rs.getString(1));
                return columnList;
            });
            if (columnList.contains("ORGNDSUM_1") || !orgn) continue;
            syncYearList.add(year);
        }
        if (!CollectionUtils.isEmpty((Collection)syncYearList)) {
            this.syncTable(syncYearList);
            List balanceTableTypes = Arrays.stream(BalanceTable.values()).map(Enum::name).collect(Collectors.toList());
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            HashMap<String, Object> vchrBalanceReBuildMsgMap = new HashMap<String, Object>();
            vchrBalanceReBuildMsgMap.put("startYear", syncYearList.get(0));
            vchrBalanceReBuildMsgMap.put("startPeriod", 0);
            vchrBalanceReBuildMsgMap.put("balanceTableTypes", balanceTableTypes);
            String instanceId = (String)taskHandlerClient.startTask("BalanceRebuild", JsonUtils.writeValueAsString(vchrBalanceReBuildMsgMap)).getData();
            AsyncCallBackUtil.asyncCall(() -> {
                taskHandlerClient.waitTaskFinished(instanceId);
                this.taskLogService.updateTaskResult(instanceId, null);
            });
        }
        return result;
    }

    private void syncTable(List<Integer> yearList) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        ArrayList tableDefineList = CollectionUtils.newArrayList();
        entityTableCollector.getEntitys().stream().filter(e -> e.getClass().getName().equals("com.jiuqi.dc.balance.impl.assist.entity.PreBalanceEO") || e.getClass().getName().equals("com.jiuqi.dc.balance.impl.cf.entity.CfBalanceEO")).forEach(entity -> {
            List shardingList;
            if (ShardingBaseEntity.class.isAssignableFrom(entity.getClass()) && !CollectionUtils.isEmpty((Collection)(shardingList = ((ShardingBaseEntity)entity).getShardingList()))) {
                for (String sharding : shardingList) {
                    TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
                    DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
                    if (entity instanceof ITableExtend) {
                        tableDefine.getFields().addAll(((ITableExtend)entity).getExtendFieldList(null));
                    }
                    String tableName = (((ShardingBaseEntity)entity).getTableNamePrefix() + "_" + sharding).toUpperCase();
                    tableDefine.setCode(tableName);
                    tableDefine.setTableName(tableName);
                    tableDefineList.add(tableDefine);
                }
            }
        });
        for (DefinitionTableV tableDefine : tableDefineList) {
            try {
                DeployTableProcessor.newInstance((DefinitionTableV)tableDefine).deploy();
            }
            catch (Exception e2) {
                this.logger.error("\u8868\u7ed3\u6784\u521d\u59cb\u5316" + tableDefine.getTableName() + "\u5931\u8d25");
            }
        }
    }
}

