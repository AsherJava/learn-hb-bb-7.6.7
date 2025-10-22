/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.nr.data.logic.internal.util.ParamUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckHandle
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.provider.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckHandle;
import com.jiuqi.nr.system.check.common.DBUtils;
import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import com.jiuqi.nr.system.check.datachange.provider.DataChangeExecutor;
import com.jiuqi.nr.system.check.datachange.util.DwUpper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class EntityCheckExecutor
implements DataChangeExecutor {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityCheckHandle entityCheckHandle;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private SplitTableHelper splitTableHelper;
    private static final Logger logger = LoggerFactory.getLogger(EntityCheckExecutor.class);
    private static final String selectSql = "SELECT %s,%s FROM %s WHERE %s GROUP BY %s,%s";
    private static final String updateSql = "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?";

    @Override
    public float getOrder() {
        return 10.0f;
    }

    @Override
    public String getExecutorType() {
        return "entityCheckData";
    }

    @Override
    public List<DataChangeRecord> execute(RepairParam repairParam) {
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        List taskDefines = allTaskDefines.stream().filter(a -> a.getDataScheme().equals(repairParam.getDataSchemeKey())).collect(Collectors.toList());
        HashSet<DataChangeRecord> dataChangeRecords = new HashSet<DataChangeRecord>();
        int taskNum = taskDefines.size();
        logger.info("\u6237\u6570\u6838\u5bf9\u4fe1\u606f\u8868\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u5f00\u59cb\u6267\u884c-\u9884\u8ba1\u6267\u884c{}\u4e2a\u4efb\u52a1", (Object)taskNum);
        if (!CollectionUtils.isEmpty(taskDefines)) {
            DwUpper dwUpper = new DwUpper(this.jdbcTemplate);
            int index = 0;
            for (TaskDefine taskDefine : taskDefines) {
                long startTime = System.currentTimeMillis();
                String msg = "\u5171" + taskNum + "\u4e2a\u4efb\u52a1\uff0c\u6b63\u5728\u6267\u884c\u7b2c" + ++index + "\u4e2a";
                logger.info("{}-\u4efb\u52a1{}\u6237\u6570\u6838\u5bf9\u4fe1\u606f\u8868\u5f00\u59cb\u6267\u884c\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199", (Object)msg, (Object)taskDefine.getTitle());
                try {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                    if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                            ExecutorContext executorContext = this.paramUtil.getExecutorContext(formSchemeDefine.getKey());
                            String tableName = this.entityCheckHandle.getTableName(formSchemeDefine);
                            TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName = this.splitTableHelper.getCurrentSplitTableName(executorContext, tableName));
                            if (tableModel == null) {
                                logger.error("{}-{}\u8868\u6a21\u578b\u4e0d\u5b58\u5728", (Object)msg, (Object)tableName);
                                continue;
                            }
                            try {
                                String select = String.format(selectSql, "ECU_UNIT_KEY", "PERIOD", tableName, DBUtils.buildCondition("ECU_UNIT_KEY"), "ECU_UNIT_KEY", "PERIOD");
                                String update = String.format(updateSql, tableName, "ECU_UNIT_KEY", "ECU_UNIT_KEY", "PERIOD");
                                dataChangeRecords.addAll(dwUpper.doExec(select, update, null));
                                logger.info("{}-{}\u8868\u5355\u4f4d\u4ee3\u7801\u5c0f\u5199\u8f6c\u5927\u5199\u6267\u884c\u6210\u529f", (Object)msg, (Object)tableName);
                            }
                            catch (Exception e) {
                                logger.error(msg + "-" + tableName + "\u8868\u5355\u4f4d\u4ee3\u7801\u5c0f\u5199\u8f6c\u5927\u5199\u6267\u884c\u5f02\u5e38:" + e.getMessage(), e);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                long endTime = System.currentTimeMillis();
                logger.info("{}-\u4efb\u52a1{}\u6237\u6570\u6838\u5bf9\u4fe1\u606f\u8868\u6267\u884c\u5355\u4f4d\u8f6c\u5927\u5199\u5b8c\u6210\uff0c\u5171\u8017\u65f6{}\u79d2", msg, taskDefine.getTitle(), (endTime - startTime) / 1000L);
            }
        }
        return new ArrayList<DataChangeRecord>(dataChangeRecords);
    }
}

