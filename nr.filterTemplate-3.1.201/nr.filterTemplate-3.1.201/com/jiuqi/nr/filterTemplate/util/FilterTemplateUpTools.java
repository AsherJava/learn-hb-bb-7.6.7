/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignDataLinkDefineDao
 *  com.jiuqi.nr.definition.internal.dao.DesignTaskDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao
 *  com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl
 *  com.jiuqi.nr.definition.internal.service.DesignTaskDefineService
 *  com.jiuqi.nr.definition.paramcheck.EntityViewUpgradeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.filterTemplate.util;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.dao.DesignDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignTaskDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.paramcheck.EntityViewUpgradeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FilterTemplateUpTools
implements EntityViewUpgradeService {
    private final Logger logger = LoggerFactory.getLogger(FilterTemplateUpTools.class);
    @Autowired
    private IFilterTemplateService filterTemplateService;
    @Autowired
    private DesignTaskDefineDao desTaskDao;
    @Autowired
    private RunTimeTaskDefineDao runTaskDao;
    @Autowired
    private DesignDataLinkDefineDao desLinkDao;
    @Autowired
    private RunTimeDataLinkDefineDao runLinkDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignTaskDefineService desTaskService;
    @Autowired
    private RunTimeTaskDefineDao runTimeTaskDefineDao;
    @Autowired
    private RunTimeDataLinkDefineDao runTimeDataLinkDefineDao;
    @Autowired(required=false)
    private IDataDefinitionDesignTimeController iDataDefinitionDesignTimeController;
    @Autowired(required=false)
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    protected ApplicationContext applicationContext;

    @Transactional(rollbackFor={Exception.class})
    public void upgrade() {
        this.logger.info("\u5f00\u59cb\u5347\u7ea7\u8bbe\u8ba1\u671f\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6761\u4ef6");
        this.upgradeDesTaskDw();
        this.logger.info("\u5f00\u59cb\u5347\u7ea7\u8fd0\u884c\u671f\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6761\u4ef6");
        this.upgradeRunTaskDw();
        this.logger.info("\u5f00\u59cb\u5347\u7ea7\u8bbe\u8ba1\u671f\u94fe\u63a5\u8fc7\u6ee4\u6761\u4ef6");
        this.upgradeDesLinkFilter();
        this.logger.info("\u5f00\u59cb\u5347\u7ea7\u8fd0\u884c\u671f\u94fe\u63a5\u8fc7\u6ee4\u6761\u4ef6");
        this.upgradeRunLinkFilter();
        RefreshCache refreshCache = new RefreshCache();
        refreshCache.setRefreshAll(true);
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshDefinitionCacheEvent());
    }

    private void upgradeDesTaskDw() {
        List hasFilterExpressionTasks = this.desTaskDao.listHasFilterExpressionTasks();
        this.logger.info("\u5171\u67e5\u51fa{}\u4e2a\u8bbe\u8ba1\u671f\u4e3b\u7ef4\u5ea6\u5e26\u8fc7\u6ee4\u6761\u4ef6\u7684\u4efb\u52a1\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)hasFilterExpressionTasks.size());
        if (hasFilterExpressionTasks != null && !CollectionUtils.isEmpty(hasFilterExpressionTasks)) {
            for (DesignTaskDefine hasFilterExpressionTask : hasFilterExpressionTasks) {
                try {
                    String filterExpression = hasFilterExpressionTask.getFilterExpression();
                    String entityViewKey = this.getEntityView(hasFilterExpressionTask.getDw(), filterExpression);
                    hasFilterExpressionTask.setFilterTemplate(entityViewKey);
                    hasFilterExpressionTask.setFilterExpression(null);
                    this.logger.info("\u8bbe\u8ba1\u671f\u66f4\u65b0\u4efb\u52a1{}", (Object)hasFilterExpressionTask.getTitle());
                    this.desTaskService.updateTaskDefine(hasFilterExpressionTask);
                }
                catch (Exception e) {
                    this.logger.error("\u8bbe\u8ba1\u671f\u4efb\u52a1{}\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6761\u4ef6\u5347\u7ea7\u5931\u8d25", (Object)hasFilterExpressionTask.getTitle());
                    throw new RuntimeException(String.format("\u8bbe\u8ba1\u671f\u4efb\u52a1{%s}\u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u53c2\u6570\u662f\u5426\u5b8c\u6574", hasFilterExpressionTask.getTitle()));
                }
            }
            this.logger.info("\u8bbe\u8ba1\u671f\u4efb\u52a1\u5347\u7ea7\u6210\u529f");
        }
    }

    private void upgradeRunTaskDw() {
        List hasFilterExpressionTasks = this.runTaskDao.listHasFilterExpressionTasks();
        this.logger.info("\u5171\u67e5\u51fa{}\u4e2a\u8fd0\u884c\u671f\u4e3b\u7ef4\u5ea6\u5e26\u8fc7\u6ee4\u6761\u4ef6\u7684\u4efb\u52a1\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)hasFilterExpressionTasks.size());
        if (hasFilterExpressionTasks != null && !CollectionUtils.isEmpty(hasFilterExpressionTasks)) {
            for (RunTimeTaskDefineImpl hasFilterExpressionTask : hasFilterExpressionTasks) {
                try {
                    String filterExpression = hasFilterExpressionTask.getFilterExpression();
                    String entityViewKey = this.getEntityView(hasFilterExpressionTask.getDw(), filterExpression);
                    hasFilterExpressionTask.setFilterTemplateID(entityViewKey);
                    hasFilterExpressionTask.setFilterExpression(null);
                    this.logger.info("\u8fd0\u884c\u671f\u66f4\u65b0\u4efb\u52a1{}", (Object)hasFilterExpressionTask.getTitle());
                    this.runTimeTaskDefineDao.update((Object)hasFilterExpressionTask);
                }
                catch (Exception e) {
                    this.logger.error("\u8fd0\u884c\u671f\u4efb\u52a1{}\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6761\u4ef6\u5347\u7ea7\u5931\u8d25", (Object)hasFilterExpressionTask.getTitle());
                    throw new RuntimeException(String.format("\u8fd0\u884c\u671f\u4efb\u52a1{%s}\u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u53c2\u6570\u662f\u5426\u5b8c\u6574", hasFilterExpressionTask.getTitle()));
                }
            }
            this.logger.info("\u8fd0\u884c\u671f\u4efb\u52a1\u5347\u7ea7\u6210\u529f");
        }
    }

    private void upgradeDesLinkFilter() {
        List hasFilterExpressionLinks = this.desLinkDao.listHasFilterExpressionLinks();
        this.logger.info("\u5171\u67e5\u51fa{}\u4e2a\u8bbe\u8ba1\u671f\u5e26\u8fc7\u6ee4\u6761\u4ef6\u7684\u94fe\u63a5\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)hasFilterExpressionLinks.size());
        ArrayList<DesignDataLinkDefine> needUpdateLinks = new ArrayList<DesignDataLinkDefine>();
        if (hasFilterExpressionLinks != null && !CollectionUtils.isEmpty(hasFilterExpressionLinks)) {
            for (DesignDataLinkDefine hasFilterExpressionLink : hasFilterExpressionLinks) {
                String filterExpression = hasFilterExpressionLink.getFilterExpression();
                try {
                    DesignFieldDefine fieldDefine;
                    if (hasFilterExpressionLink.getLinkExpression() == null || (fieldDefine = this.iDataDefinitionDesignTimeController.queryFieldDefine(hasFilterExpressionLink.getLinkExpression())) == null || fieldDefine.getEntityKey() == null) continue;
                    String entityViewKey = this.getEntityView(fieldDefine.getEntityKey(), filterExpression);
                    hasFilterExpressionLink.setFilterTemplate(entityViewKey);
                    hasFilterExpressionLink.setFilterExpression(null);
                    needUpdateLinks.add(hasFilterExpressionLink);
                }
                catch (JQException e) {
                    this.logger.error("\u8bbe\u8ba1\u671f\u94fe\u63a5{}\u8fc7\u6ee4\u6761\u4ef6\u5347\u7ea7\u5931\u8d25", (Object)hasFilterExpressionLink.getKey());
                    throw new RuntimeException(String.format("\u8bbe\u8ba1\u671f\u94fe\u63a5{%s}\u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u94fe\u63a5\u53c2\u6570\u662f\u5426\u6b63\u786e", hasFilterExpressionLink.getKey()));
                }
            }
        }
        if (!CollectionUtils.isEmpty(needUpdateLinks)) {
            this.logger.info("\u8bbe\u8ba1\u671f\u66f4\u65b0\u94fe\u63a5\u4e2d\uff0c\u6570\u91cf\u4e3a:{}\u4e2a,\u8bf7\u8010\u5fc3\u7b49\u5f85\uff01", (Object)needUpdateLinks.size());
            this.designTimeViewController.updateDataLinkDefines(needUpdateLinks.toArray(new DesignDataLinkDefine[0]));
            this.logger.info("\u8bbe\u8ba1\u671f\u94fe\u63a5\u5347\u7ea7\u5b8c\u6210\uff01");
        }
    }

    private void upgradeRunLinkFilter() {
        List hasFilterExpressionLinks = this.runLinkDao.listHasFilterExpressionLinks();
        this.logger.info("\u5171\u67e5\u51fa{}\u4e2a\u8fd0\u884c\u671f\u5e26\u8fc7\u6ee4\u6761\u4ef6\u7684\u94fe\u63a5\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)hasFilterExpressionLinks.size());
        ArrayList<RunTimeDataLinkDefineImpl> needUpdateLinks = new ArrayList<RunTimeDataLinkDefineImpl>();
        if (hasFilterExpressionLinks != null && !CollectionUtils.isEmpty(hasFilterExpressionLinks)) {
            for (RunTimeDataLinkDefineImpl hasFilterExpressionLink : hasFilterExpressionLinks) {
                String filterExpression = hasFilterExpressionLink.getFilterExpression();
                try {
                    FieldDefine fieldDefine;
                    if (hasFilterExpressionLink.getLinkExpression() == null || (fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(hasFilterExpressionLink.getLinkExpression())) == null || fieldDefine.getEntityKey() == null) continue;
                    String entityViewKey = this.getEntityView(fieldDefine.getEntityKey(), filterExpression);
                    hasFilterExpressionLink.setFilterTemplate(entityViewKey);
                    hasFilterExpressionLink.setFilterExpression(null);
                    needUpdateLinks.add(hasFilterExpressionLink);
                }
                catch (Exception e) {
                    this.logger.error("\u8fd0\u884c\u671f\u94fe\u63a5{}\u8fc7\u6ee4\u6761\u4ef6\u5347\u7ea7\u5931\u8d25", (Object)hasFilterExpressionLink.getKey());
                    throw new RuntimeException(String.format("\u8fd0\u884c\u671f\u94fe\u63a5{%s}\u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u94fe\u63a5\u53c2\u6570\u662f\u5426\u6b63\u786e", hasFilterExpressionLink.getKey()));
                }
            }
        }
        if (!CollectionUtils.isEmpty(needUpdateLinks)) {
            this.logger.info("\u8fd0\u884c\u671f\u66f4\u65b0\u94fe\u63a5\u4e2d\uff0c\u6570\u91cf\u4e3a:{}\u4e2a,\u8bf7\u8010\u5fc3\u7b49\u5f85\uff01", (Object)needUpdateLinks.size());
            try {
                this.runTimeDataLinkDefineDao.update((Object[])needUpdateLinks.toArray(new DataLinkDefine[0]));
            }
            catch (Exception e) {
                this.logger.error("\u8fd0\u884c\u671f\u94fe\u63a5\u8fc7\u6ee4\u6761\u4ef6\u5347\u7ea7\u5931\u8d25!");
                throw new RuntimeException(e);
            }
            this.logger.info("\u8fd0\u884c\u671f\u94fe\u63a5\u5347\u7ea7\u5b8c\u6210\uff01");
        }
    }

    private String getEntityView(String entityID, String filterExpression) {
        List<FilterTemplateDTO> entityViews = this.filterTemplateService.getByEntity(entityID);
        if (entityViews != null && !CollectionUtils.isEmpty(entityViews)) {
            for (FilterTemplateDTO entityView : entityViews) {
                if (!filterExpression.equals(entityView.getFilterContent())) continue;
                return entityView.getFilterTemplateID();
            }
        }
        return this.createNewTemplate(entityID, filterExpression);
    }

    private String createNewTemplate(String entityID, String filterExpression) {
        FilterTemplateDTO entityViewDefineData = this.filterTemplateService.init();
        entityViewDefineData.setEntityID(entityID);
        entityViewDefineData.setFilterTemplateTitle(this.getNewTemplateName(entityID));
        entityViewDefineData.setFilterContent(filterExpression);
        return this.filterTemplateService.insert(entityViewDefineData);
    }

    private String getNewTemplateName(String entityID) {
        int index = 0;
        String newBaseTitle = "";
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityID);
        if (entityDefine != null) {
            newBaseTitle = entityDefine.getTitle() + "_\u8fc7\u6ee4\u6a21\u677f";
        }
        String newTitle = newBaseTitle;
        List<FilterTemplateDTO> entityViewDefineDatas = this.filterTemplateService.getByEntity(entityID);
        if (entityViewDefineDatas != null && entityViewDefineDatas.size() > 0) {
            boolean flag = true;
            while (flag) {
                int oldIndex = index;
                for (FilterTemplateDTO viewDefineData : entityViewDefineDatas) {
                    String title = viewDefineData.getFilterTemplateTitle();
                    if (!newTitle.equals(title)) continue;
                    newTitle = newBaseTitle + ++index;
                    break;
                }
                if (oldIndex != index) continue;
                break;
            }
        }
        return newTitle;
    }
}

