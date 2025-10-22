/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.internal.obj.EntityData
 *  com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.provider.impl;

import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import com.jiuqi.nr.system.check.datachange.provider.DataChangeExecutor;
import com.jiuqi.nr.system.check.datachange.util.CKDDWUpper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CKDDataChangeExecutor
implements DataChangeExecutor {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private SplitCheckTableHelper splitCheckTableHelper;
    private static final Logger logger = LoggerFactory.getLogger(CKDDataChangeExecutor.class);

    @Override
    public float getOrder() {
        return Float.MAX_VALUE;
    }

    @Override
    public String getExecutorType() {
        return "ckdData";
    }

    @Override
    public List<DataChangeRecord> execute(RepairParam repairParam) {
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        List taskDefines = allTaskDefines.stream().filter(a -> a.getDataScheme().equals(repairParam.getDataSchemeKey())).collect(Collectors.toList());
        ArrayList<DataChangeRecord> dataChangeRecords = new ArrayList<DataChangeRecord>();
        int taskNum = taskDefines.size();
        logger.info("\u51fa\u9519\u8bf4\u660e\u8868\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u5f00\u59cb\u6267\u884c-\u9884\u8ba1\u6267\u884c{}\u4e2a\u4efb\u52a1", (Object)taskNum);
        if (!CollectionUtils.isEmpty(taskDefines)) {
            CKDDWUpper ckddwUpper = new CKDDWUpper(this.jdbcTemplate);
            HashSet<String> recordDistinctKeys = new HashSet<String>();
            int index = 0;
            for (TaskDefine taskDefine : taskDefines) {
                long startTime = System.currentTimeMillis();
                String msg = "\u5171" + taskNum + "\u4e2a\u4efb\u52a1\uff0c\u6b63\u5728\u6267\u884c\u7b2c" + ++index + "\u4e2a";
                logger.info("{}-\u4efb\u52a1{}\u51fa\u9519\u8bf4\u660e\u8868\u5f00\u59cb\u6267\u884c\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199", (Object)msg, (Object)taskDefine.getTitle());
                try {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                    if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                            String ckdTableName = this.splitCheckTableHelper.getSplitCKDTableName(formSchemeDefine);
                            TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(ckdTableName);
                            if (tableModel == null) {
                                logger.error("{}-{}\u8868\u6a21\u578b\u4e0d\u5b58\u5728", (Object)msg, (Object)ckdTableName);
                                continue;
                            }
                            try {
                                List dimEntities = this.entityUtil.getDimEntities(formSchemeDefine);
                                EntityData dwEntity = this.entityUtil.getEntity(formSchemeDefine.getDw());
                                EntityData periodEntity = this.entityUtil.getPeriodEntity(formSchemeDefine.getDateTime());
                                List<DataChangeRecord> execResult = ckddwUpper.exec(ckdTableName, dwEntity, periodEntity, dimEntities);
                                this.addRecord(dataChangeRecords, execResult, recordDistinctKeys);
                                logger.info("{}-{}\u8868\u5355\u4f4d\u4ee3\u7801\u5c0f\u5199\u8f6c\u5927\u5199\u6267\u884c\u6210\u529f", (Object)msg, (Object)ckdTableName);
                            }
                            catch (Exception e) {
                                logger.error(msg + "-" + ckdTableName + "\u8868\u5355\u4f4d\u4ee3\u7801\u5c0f\u5199\u8f6c\u5927\u5199\u6267\u884c\u5f02\u5e38:" + e.getMessage(), e);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                long endTime = System.currentTimeMillis();
                logger.info("{}-\u4efb\u52a1{}\u51fa\u9519\u8bf4\u660e\u8868\u6267\u884c\u5355\u4f4d\u8f6c\u5927\u5199\u5b8c\u6210\uff0c\u5171\u8017\u65f6{}\u79d2", msg, taskDefine.getTitle(), (endTime - startTime) / 1000L);
            }
        }
        logger.info("\u51fa\u9519\u8bf4\u660e\u8868\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u6267\u884c\u5b8c\u6bd5-\u5171\u6267\u884c{}\u4e2a\u4efb\u52a1", (Object)taskNum);
        return dataChangeRecords;
    }

    private void addRecord(@NonNull List<DataChangeRecord> dataChangeRecords, List<DataChangeRecord> execResult, Set<String> distinctKeys) {
        if (execResult != null && !execResult.isEmpty()) {
            for (DataChangeRecord dataChangeRecord : execResult) {
                String oldUnitCode = dataChangeRecord.getOldUnitCode();
                String period = dataChangeRecord.getPeriod();
                String distinctKey = period + ";" + oldUnitCode;
                if (!distinctKeys.add(distinctKey)) continue;
                dataChangeRecords.add(dataChangeRecord);
            }
        }
    }
}

