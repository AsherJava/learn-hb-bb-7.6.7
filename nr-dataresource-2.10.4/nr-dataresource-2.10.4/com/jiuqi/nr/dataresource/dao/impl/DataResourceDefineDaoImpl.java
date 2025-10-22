/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.impl.BaseDataResourceDao;
import com.jiuqi.nr.dataresource.dao.impl.TransUtil;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.i18n.ResourceTreeI18NService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataResourceDefineDaoImpl
extends BaseDataResourceDao<ResourceTreeDO>
implements IDataResourceDefineDao {
    @Autowired
    private ResourceTreeI18NService i18NService;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    @Override
    public List<ResourceTreeDO> getByResourceGroupKey(String key) throws DataAccessException {
        Assert.notNull((Object)key, "groupKey must not be null.");
        List list = super.list(new String[]{"DG_KEY"}, (Object[])new String[]{key}, this.getClz());
        if (this.i18NService.isOpenLanaguage()) {
            for (ResourceTreeDO tree : list) {
                tree.setTitle(this.i18NService.getI18NTitle(NodeType.TREE.name() + tree.getKey(), tree.getTitle()));
            }
        }
        return list;
    }

    @Override
    public List<ResourceTreeDO> getByResourceGroupKeyNoI18N(String key) throws DataAccessException {
        return super.list(new String[]{"DG_KEY"}, (Object[])new String[]{key}, this.getClz());
    }

    @Override
    public void deleteByResourceGroupKey(String key) throws DataAccessException {
        Assert.notNull((Object)key, "groupKey must not be null.");
        super.deleteBy(new String[]{"DG_KEY"}, (Object[])new String[]{key});
    }

    @Override
    public List<ResourceTreeDO> getByConditions(String groupKey, String title) {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        Assert.notNull((Object)title, "title must not be null.");
        return super.list(new String[]{"DG_KEY", "DD_TITLE"}, (Object[])new String[]{groupKey, title}, this.getClz());
    }

    @Override
    public List<ResourceTreeDO> fuzzySearch(String fuzzyKey) {
        if (!StringUtils.hasText(fuzzyKey)) {
            return Collections.emptyList();
        }
        String sqLWhere = " DD_TITLE LIKE ?";
        fuzzyKey = "%" + fuzzyKey + "%";
        List list = super.list(sqLWhere, (Object[])new String[]{fuzzyKey}, this.getClz());
        if (!CollectionUtils.isEmpty(list) && this.i18NService.isOpenLanaguage()) {
            for (ResourceTreeDO tree : list) {
                tree.setTitle(this.i18NService.getI18NTitle(NodeType.TREE.name() + tree.getKey(), tree.getTitle()));
            }
        }
        return list;
    }

    @Override
    public Class<ResourceTreeDO> getClz() {
        return ResourceTreeDO.class;
    }
}

