/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.upgrade.face.ITaskVersionUpgrade
 *  com.jiuqi.nr.definition.upgrade.face.UpgradeType
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.task.api.task.SimpleAsyncTaskMonitor
 */
package com.jiuqi.nr.task.internal.upgrade;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.upgrade.face.ITaskVersionUpgrade;
import com.jiuqi.nr.definition.upgrade.face.UpgradeType;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.task.api.task.SimpleAsyncTaskMonitor;
import com.jiuqi.nr.task.internal.upgrade.dto.UpgradeResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TaskUpgradeService {
    private static final Logger logger = LoggerFactory.getLogger(TaskUpgradeService.class);
    @Autowired(required=false)
    private List<ITaskVersionUpgrade> taskVersionUpgrades;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFilterTemplateService filterTemplateService;

    public List<UpgradeResult> execute(String taskKey) {
        ArrayList<UpgradeResult> results = new ArrayList<UpgradeResult>();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        if (task == null) {
            UpgradeResult result = new UpgradeResult("\u4efb\u52a1\u5b9a\u4e49");
            result.setSuccess(false);
            result.setMessage("\u4efb\u52a1\u4e0d\u5b58\u5728");
            results.add(result);
            return results;
        }
        logger.info("\u5347\u7ea7\u4efb\u52a1\uff1a{}", (Object)task.getTitle());
        if (!CollectionUtils.isEmpty(this.taskVersionUpgrades)) {
            for (ITaskVersionUpgrade upgrade : this.taskVersionUpgrades) {
                if (!upgrade.apply(taskKey, UpgradeType.BEFORE)) continue;
                UpgradeResult moduleResult = new UpgradeResult(upgrade.getModuleName());
                SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor();
                try {
                    upgrade.doUpgrade(taskKey, UpgradeType.BEFORE, (AsyncTaskMonitor)monitor);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    moduleResult.setSuccess(false);
                    moduleResult.setMessage(e.getMessage());
                }
                results.add(moduleResult);
            }
        }
        List<UpgradeResult> result = this.upgradeTask(task);
        results.addAll(result);
        if (!CollectionUtils.isEmpty(this.taskVersionUpgrades)) {
            for (ITaskVersionUpgrade upgrade : this.taskVersionUpgrades) {
                if (!upgrade.apply(taskKey, UpgradeType.AFTER)) continue;
                UpgradeResult moduleResult = new UpgradeResult(upgrade.getModuleName());
                SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor();
                try {
                    upgrade.doUpgrade(taskKey, UpgradeType.AFTER, (AsyncTaskMonitor)monitor);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    moduleResult.setSuccess(false);
                    moduleResult.setMessage(e.getMessage());
                }
                results.add(moduleResult);
            }
        }
        return results;
    }

    private List<UpgradeResult> upgradeTask(DesignTaskDefine task) {
        ArrayList<UpgradeResult> results = new ArrayList<UpgradeResult>();
        UpgradeResult result = new UpgradeResult("\u4efb\u52a1\u5b9a\u4e49");
        results.add(result);
        try {
            task.setVersion("2.0");
            UpgradeResult filterTemplateUpgrade = this.upgradeTaskFilterTemplate(task);
            if (filterTemplateUpgrade != null) {
                results.add(filterTemplateUpgrade);
            }
            task.setEfdcSwitch(true);
            this.designTimeViewController.updateTask(task);
            List formScheme = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            HashSet fromKeys = new HashSet();
            for (DesignFormSchemeDefine formSchemeDefine : formScheme) {
                List designFormDefines = this.designTimeViewController.listFormByFormScheme(formSchemeDefine.getKey());
                fromKeys.addAll(designFormDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
            }
            this.designTimeViewController.batchUpdateFormTime(fromKeys.toArray(new String[0]));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setMessage("\u4efb\u52a1\u4fee\u6539\u7248\u672c\u5931\u8d25");
        }
        return results;
    }

    private UpgradeResult upgradeTaskFilterTemplate(DesignTaskDefine task) {
        UpgradeResult result = new UpgradeResult("\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6a21\u677f");
        String filterTemplateID = task.getFilterTemplate();
        if (!StringUtils.hasText(filterTemplateID)) {
            return null;
        }
        FilterTemplateDTO filterTemplate = this.filterTemplateService.getFilterTemplate(filterTemplateID);
        if (!StringUtils.hasText(filterTemplate.getFilterTemplateID())) {
            task.setFilterTemplate(null);
            result.setSuccess(false);
            result.setMessage("\u5f53\u524d\u4e3b\u7ef4\u5ea6\u7ed1\u5b9a\u7684\u8fc7\u6ee4\u6a21\u677f\u4e22\u5931");
            return result;
        }
        task.setFilterTemplate(null);
        task.setFilterExpression(filterTemplate.getFilterContent());
        return result;
    }
}

