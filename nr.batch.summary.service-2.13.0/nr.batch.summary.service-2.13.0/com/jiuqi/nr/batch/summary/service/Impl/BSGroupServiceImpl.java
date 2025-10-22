/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.SummaryGroupDao
 *  com.jiuqi.nr.batch.summary.storage.SummarySchemeDao
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.Impl;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState;
import com.jiuqi.nr.batch.summary.storage.SummaryGroupDao;
import com.jiuqi.nr.batch.summary.storage.SummarySchemeDao;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BSGroupServiceImpl
implements BSGroupService {
    @Resource
    private SummaryGroupDao groupDao;
    @Resource
    private SummarySchemeDao schemeDao;

    @Override
    public SummaryGroup findGroup(String groupKey) {
        return this.groupDao.findGroup(groupKey);
    }

    @Override
    public int moveGroups2Group(String groupKey, List<String> groupKeys) {
        if (StringUtils.isNotEmpty((String)groupKey) && groupKeys != null && !groupKeys.isEmpty()) {
            return this.groupDao.moveGroup(groupKey, groupKeys);
        }
        return 0;
    }

    @Override
    public List<SummaryGroup> findChildGroups(String task, String groupKey) {
        return this.groupDao.findChildGroups(task, groupKey);
    }

    @Override
    public List<SummaryGroup> findAllChildGroups(String task, String groupKey) {
        return this.groupDao.findAllChildGroups(task, groupKey);
    }

    @Override
    public GroupServiceState removeSchemeGroup(String task, String groupKey) {
        SummaryGroup groupDefine = this.groupDao.findGroup(groupKey);
        if (groupDefine != null) {
            List allChildGroups = this.groupDao.findAllChildGroups(task, groupKey);
            allChildGroups.add(0, groupDefine);
            List groupKeys = allChildGroups.stream().map(SummaryGroup::getKey).collect(Collectors.toList());
            List groupSchemes = this.schemeDao.findGroupSchemes(task, groupKeys);
            if (groupSchemes != null && !groupSchemes.isEmpty()) {
                return GroupServiceState.HAS_CHILD_SCHEME;
            }
            return this.groupDao.removeRow(groupKeys) > 0 ? GroupServiceState.SUCCESS : GroupServiceState.FAIL;
        }
        return GroupServiceState.FAIL;
    }

    @Override
    public GroupServiceState renameSchemeGroup(String groupKey, String groupName) {
        return this.groupDao.renameGroup(groupKey, groupName) == 1 ? GroupServiceState.SUCCESS : GroupServiceState.FAIL;
    }

    @Override
    public GroupServiceState saveSchemeGroup(SummaryGroupDefine impl) {
        if (StringUtils.isEmpty((String)impl.getTask())) {
            return GroupServiceState.INVALID_TASK;
        }
        if (StringUtils.isEmpty((String)impl.getTitle())) {
            return GroupServiceState.INVALID_GROUP_NAME;
        }
        impl.setKey(UUID.randomUUID().toString());
        impl.setCreator(BatchSummaryUtils.getCurrentUserID());
        impl.setUpdateTime(new Date());
        impl.setOrdinal(System.currentTimeMillis() + "");
        impl.setParent(StringUtils.isEmpty((String)impl.getParent()) ? "00000000-0000-0000-0000-000000000000" : impl.getParent());
        return this.groupDao.insertRow(impl) == 1 ? GroupServiceState.SUCCESS : GroupServiceState.FAIL;
    }
}

