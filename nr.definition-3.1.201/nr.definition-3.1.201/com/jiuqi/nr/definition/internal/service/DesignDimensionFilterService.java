/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.nvwa.authority.login.job.LoginAccessCountJobFactory
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.definition.event.DimensionFilterChangeEvent;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.DesignDimensionFilterDao;
import com.jiuqi.nvwa.authority.login.job.LoginAccessCountJobFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DesignDimensionFilterService
implements ApplicationListener<TaskChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(DesignDimensionFilterService.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DesignDimensionFilterDao designDimensionFilterDao;
    @Autowired
    private LoginAccessCountJobFactory loginAccessCountJobFactory;

    public DesignDimensionFilter getDimensionFilterByTaskKey(String taskKey, String entityId) {
        if (!StringUtils.hasLength(taskKey) || !StringUtils.hasLength(entityId)) {
            return null;
        }
        return this.designDimensionFilterDao.getByTaskKeyAndEntityId(taskKey, entityId);
    }

    public List<DesignDimensionFilter> getDimensionFilterByTaskKey(String taskKey) {
        if (!StringUtils.hasLength(taskKey)) {
            return Collections.emptyList();
        }
        return this.designDimensionFilterDao.getByTaskKey(taskKey);
    }

    public void updateDimensionFilter(DesignDimensionFilter designDimensionFilter) {
        if (designDimensionFilter != null) {
            this.designDimensionFilterDao.update(designDimensionFilter);
            this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.UPDATE, Collections.singletonList(designDimensionFilter.getTaskKey())));
        }
    }

    public void updateDimensionFilters(List<DesignDimensionFilter> designDimensionFilters) {
        if (designDimensionFilters != null && !designDimensionFilters.isEmpty()) {
            this.designDimensionFilterDao.batchUpdate(designDimensionFilters);
            Optional first = designDimensionFilters.stream().findFirst();
            first.ifPresent(designDimensionFilter -> this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.UPDATE, Collections.singletonList(designDimensionFilter.getTaskKey()))));
        }
    }

    public void saveDimensionFilter(DesignDimensionFilter dimensionFilter) {
        if (dimensionFilter == null) {
            return;
        }
        Assert.notNull((Object)dimensionFilter.getTaskKey(), (String)"taskKey can not be null");
        Assert.notNull((Object)dimensionFilter.getEntityId(), (String)"entityId can not be null");
        DesignDimensionFilter temp = this.designDimensionFilterDao.getByTaskKeyAndEntityId(dimensionFilter.getTaskKey(), dimensionFilter.getEntityId());
        if (temp == null) {
            this.designDimensionFilterDao.insert(dimensionFilter);
        } else {
            this.designDimensionFilterDao.update(dimensionFilter);
        }
        this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.UPDATE, Collections.singletonList(dimensionFilter.getTaskKey())));
    }

    public void deleteDimensionFilter(String taskKey) {
        if (StringUtils.hasLength(taskKey)) {
            this.designDimensionFilterDao.deleteByTaskKey(taskKey);
            this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(taskKey)));
        }
    }

    public void insertDimensionFilter(DesignDimensionFilter dimensionFilter) throws JQException {
        if (dimensionFilter != null) {
            DesignDimensionFilter designDimensionFilter = this.designDimensionFilterDao.getByTaskKeyAndEntityId(dimensionFilter.getTaskKey(), dimensionFilter.getEntityId());
            if (designDimensionFilter != null) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_070);
            }
            this.designDimensionFilterDao.insert(dimensionFilter);
            this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.ADD, Collections.singletonList(dimensionFilter.getTaskKey())));
        }
    }

    public void insertDimensionFilters(List<DesignDimensionFilter> dimensionFilters) throws JQException {
        if (dimensionFilters != null && !dimensionFilters.isEmpty()) {
            List<DesignDimensionFilter> dbList = this.designDimensionFilterDao.getByTaskKey(dimensionFilters.get(0).getTaskKey());
            Set collect = dbList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            DesignDimensionFilter filter = dimensionFilters.stream().filter(x -> collect.contains(x.getKey())).findFirst().orElse(null);
            if (filter != null) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_070, String.format(NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_070.getMessage(), filter.getTaskKey(), filter.getEntityId()));
            }
            this.designDimensionFilterDao.batchInsert(dimensionFilters);
            this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.ADD, Collections.singletonList(dimensionFilters.get(0).getTaskKey())));
        }
    }

    public void saveDimensionFiltersByTaskKey(String taskKey, List<DesignDimensionFilter> list) {
        Assert.notNull((Object)taskKey, (String)"taskKey can not be null");
        if (CollectionUtils.isEmpty(list)) {
            this.deleteDimensionFilter(taskKey);
            this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(taskKey)));
            return;
        }
        Set entityIdSet = this.designDimensionFilterDao.getByTaskKey(taskKey).stream().map(IDimensionFilter::getEntityId).collect(Collectors.toSet());
        ArrayList<DesignDimensionFilter> updateList = new ArrayList<DesignDimensionFilter>();
        ArrayList<DesignDimensionFilter> addList = new ArrayList<DesignDimensionFilter>();
        for (DesignDimensionFilter designDimensionFilter : list) {
            if (entityIdSet.contains(designDimensionFilter.getEntityId())) {
                updateList.add(designDimensionFilter);
                continue;
            }
            addList.add(designDimensionFilter);
        }
        this.designDimensionFilterDao.batchUpdate(updateList);
        this.designDimensionFilterDao.batchInsert(addList);
        this.applicationContext.publishEvent(new DimensionFilterChangeEvent(ParamChangeEvent.ChangeType.UPDATE, Collections.singletonList(taskKey)));
    }

    @Override
    public void onApplicationEvent(TaskChangeEvent event) {
        List<TaskDefine> tasks = event.getTasks();
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }
        log.debug("\u60c5\u666f\u8fc7\u6ee4\u6761\u4ef6\u76d1\u542c\u4e8b\u4ef6: {}", (Object)tasks);
        if (event.getType() == ParamChangeEvent.ChangeType.DELETE) {
            tasks.forEach(task -> this.deleteDimensionFilter(task.getKey()));
        }
    }
}

