/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.zbquery.authority.ZBQueryAuthorityService;
import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.dao.ZBQueryGroupDao;
import com.jiuqi.nr.zbquery.service.ZBQueryGroupService;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ZBQueryGroupServiceImpl
implements ZBQueryGroupService {
    @Autowired
    private ZBQueryGroupDao dao;
    @Autowired
    private ZBQueryInfoService zBQueryInfoService;
    @Autowired
    private ZBQueryAuthorityService zBQueryAuthorityService;

    @Override
    public String addQueryGroup(ZBQueryGroup group) throws JQException {
        Assert.notNull((Object)group, "group must not be null");
        if (!this.zBQueryAuthorityService.canWriteGroup(group.getParentId())) {
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_000);
        }
        if (StringHelper.isNull((String)group.getId())) {
            group.setId(UUID.randomUUID().toString());
        }
        group.setModifyTime(System.currentTimeMillis());
        return this.dao.addQueryGroup(group);
    }

    @Override
    public String modifyQueryGroup(ZBQueryGroup group) throws JQException {
        Assert.notNull((Object)group, "group must not be null");
        if (!this.zBQueryAuthorityService.canWriteGroup(group.getId())) {
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_000);
        }
        group.setModifyTime(System.currentTimeMillis());
        return this.dao.modifyQueryGroup(group);
    }

    @Override
    public String deleteQueryGroup(String groupId) throws JQException {
        Assert.notNull((Object)groupId, "groupId must not be null");
        if (!this.zBQueryAuthorityService.canWriteGroup(groupId)) {
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_000);
        }
        if (this.dao.getQueryGroupChildren(groupId).size() > 0) {
            return ZBQueryI18nUtils.getMessage("zbquery.exception.groupNotDelete001", new Object[0]);
        }
        if (this.zBQueryInfoService.getQueryInfoByGroup(groupId).size() > 0) {
            return ZBQueryI18nUtils.getMessage("zbquery.exception.groupNotDelete002", new Object[0]);
        }
        this.dao.deleteQueryGroupById(groupId);
        return "true";
    }

    @Override
    public Optional<ZBQueryGroup> getQueryGroupById(String groupId) {
        Assert.notNull((Object)groupId, "'groupId' must not be null");
        return Optional.ofNullable(this.dao.getQueryGroupById(groupId));
    }

    @Override
    public List<ZBQueryGroup> getQueryGroupByTitle(String title, boolean fuzzy) {
        Assert.hasText(title, "'title' must not be null");
        return this.dao.getQueryGroupByTitle(title, fuzzy).stream().filter(u -> this.zBQueryAuthorityService.canReadGroup(u.getId())).collect(Collectors.toList());
    }

    @Override
    public List<ZBQueryGroup> getQueryGroupChildren(String parentId) {
        if (StringHelper.isNull((String)parentId)) {
            parentId = "00000000-0000-0000-0000-000000000000";
        }
        return this.dao.getQueryGroupChildren(parentId).stream().filter(u -> this.zBQueryAuthorityService.canReadGroup(u.getId())).collect(Collectors.toList());
    }

    @Override
    public List<String> getQueryGroupAllChildrenId(String groupId) {
        Map<String, List<ZBQueryGroup>> parentIdMap = this.getParentIdMap(false);
        ArrayList<String> list = new ArrayList<String>();
        this.getChildrenId(parentIdMap, list, groupId);
        return list;
    }

    private void getChildrenId(Map<String, List<ZBQueryGroup>> parentIdMap, List<String> list, String groupId) {
        if (parentIdMap.containsKey(groupId)) {
            for (ZBQueryGroup zBQueryGroup : parentIdMap.get(groupId)) {
                this.getChildrenId(parentIdMap, list, zBQueryGroup.getId());
                list.add(zBQueryGroup.getId());
            }
        }
    }

    @Override
    public List<String> getAllQueryGroupParentId() {
        List<ZBQueryGroup> allQueryGroup = this.dao.getAllQueryGroup();
        if (allQueryGroup != null && allQueryGroup.size() > 0) {
            return allQueryGroup.stream().map(ZBQueryGroup::getParentId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<ZBQueryGroup>> getParentIdMap(boolean needAuthority) {
        List<ZBQueryGroup> groupList = this.dao.getAllQueryGroup();
        if (groupList != null && groupList.size() > 0) {
            return groupList.stream().collect(Collectors.toMap(ZBQueryGroup::getParentId, group -> {
                ArrayList<ZBQueryGroup> list = new ArrayList<ZBQueryGroup>();
                if (this.zBQueryAuthorityService.canReadGroup(group.getId())) {
                    list.add((ZBQueryGroup)group);
                }
                return list;
            }, (value1, value2) -> {
                value1.addAll(value2);
                return value1;
            }));
        }
        return Collections.emptyMap();
    }

    @Override
    public void getParentId(List<String> list, String groupId) {
        ZBQueryGroup zBQueryGroup = this.dao.getQueryGroupById(groupId);
        if (zBQueryGroup != null) {
            this.getParentId(list, zBQueryGroup.getParentId());
            list.add(zBQueryGroup.getParentId());
        }
    }

    @Override
    public List<ZBQueryGroup> getAllQueryGroup() {
        return this.dao.getAllQueryGroup().stream().filter(u -> this.zBQueryAuthorityService.canReadGroup(u.getId())).collect(Collectors.toList());
    }
}

