/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.dao.impl.BaseDataResourceDao;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.i18n.ResourceTreeI18NService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataResourceDefineGroupDaoImpl
extends BaseDataResourceDao<ResourceTreeGroup>
implements IDataResourceDefineGroupDao {
    @Autowired
    private ResourceTreeI18NService i18NService;

    @Override
    public List<ResourceTreeGroup> getByParent(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List list = super.list(new String[]{"DG_PARENT_KEY"}, (Object[])new String[]{parentKey}, this.getClz());
        if (this.i18NService.isOpenLanaguage()) {
            for (ResourceTreeGroup group : list) {
                group.setTitle(this.i18NService.getI18NTitle(NodeType.TREE_GROUP.name() + group.getKey(), group.getTitle()));
            }
        }
        return list;
    }

    @Override
    public List<ResourceTreeGroup> getByParentNoI18N(String parentKey) {
        return super.list(new String[]{"DG_PARENT_KEY"}, (Object[])new String[]{parentKey}, this.getClz());
    }

    @Override
    public void deleteByParent(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        super.deleteBy(new String[]{"DG_PARENT_KEY"}, (Object[])new String[]{parentKey});
    }

    @Override
    public List<ResourceTreeGroup> getByConditions(String parentKey, String title) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        Assert.notNull((Object)title, "title must not be null.");
        return super.list(new String[]{"DG_PARENT_KEY", "DG_TITLE"}, (Object[])new String[]{parentKey, title}, this.getClz());
    }

    @Override
    public List<ResourceTreeGroup> fuzzySearch(String fuzzyKey) {
        if (!StringUtils.hasText(fuzzyKey)) {
            return Collections.emptyList();
        }
        String sqLWhere = " DG_TITLE LIKE ?";
        fuzzyKey = "%" + fuzzyKey + "%";
        List list = super.list(sqLWhere, (Object[])new String[]{fuzzyKey}, this.getClz());
        if (!CollectionUtils.isEmpty(list) && this.i18NService.isOpenLanaguage()) {
            for (ResourceTreeGroup group : list) {
                group.setTitle(this.i18NService.getI18NTitle(NodeType.TREE_GROUP.name() + group.getKey(), group.getTitle()));
            }
        }
        return list;
    }

    @Override
    public Class<ResourceTreeGroup> getClz() {
        return ResourceTreeGroup.class;
    }
}

