/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.invest.investworkpaper.task;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.gcreport.invest.investworkpaper.dao.InvestWorkPaperSettingDao;
import com.jiuqi.gcreport.invest.investworkpaper.entity.InvestWorkPaperSettingEO;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.LinkedHashMap;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class InvestWorkPaperSettingRepairSystemIdTask
implements CustomClassExecutor {
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) throws Exception {
        try {
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_INVESTWORKPAPERSETTING");
            this.repairSystemId();
        }
        catch (Exception e) {
            this.logger.error("\u4fee\u590d\u6295\u8d44\u5de5\u4f5c\u5e95\u7a3f\u8bbe\u7f6e\u4e2d\u4f53\u7cfbid\u9519\u8bef:" + e.getMessage(), e);
        }
    }

    private void repairSystemId() {
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        List allBoundTaskVOs = consolidatedTaskService.getAllBoundTaskVOs();
        if (CollectionUtils.isEmpty(allBoundTaskVOs)) {
            return;
        }
        LinkedHashMap<String, String> taskId2SystemIdMap = new LinkedHashMap<String, String>();
        for (ConsolidatedTaskVO consolidatedTaskVO : allBoundTaskVOs) {
            taskId2SystemIdMap.put(consolidatedTaskVO.getTaskKey(), consolidatedTaskVO.getSystemId());
            List manageTaskKeys = consolidatedTaskVO.getManageTaskKeys();
            if (CollectionUtils.isEmpty(manageTaskKeys)) continue;
            manageTaskKeys.forEach(manageTaskKey -> taskId2SystemIdMap.put((String)manageTaskKey, consolidatedTaskVO.getSystemId()));
        }
        InvestWorkPaperSettingDao investWorkPaperSettingDao = (InvestWorkPaperSettingDao)SpringContextUtils.getBean(InvestWorkPaperSettingDao.class);
        List investWorkPaperSettingEOS = investWorkPaperSettingDao.selectList((BaseEntity)new InvestWorkPaperSettingEO());
        if (CollectionUtils.isEmpty(investWorkPaperSettingEOS)) {
            return;
        }
        for (InvestWorkPaperSettingEO investWorkPaperSettingEO : investWorkPaperSettingEOS) {
            String taskId = investWorkPaperSettingEO.getTaskId();
            investWorkPaperSettingEO.setSystemId((String)taskId2SystemIdMap.get(taskId));
        }
        investWorkPaperSettingDao.updateAll(investWorkPaperSettingEOS);
    }
}

