/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.impl.BaseDataResourceDao;
import com.jiuqi.nr.dataresource.dao.impl.TransUtil;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.i18n.ResourceTreeI18NService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DataResourceDaoImpl
extends BaseDataResourceDao<DataResourceDO>
implements IDataResourceDao {
    @Autowired
    private ResourceTreeI18NService i18NService;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    @Override
    public List<DataResourceDO> getByParent(String resourceTreeKey, String parentKey) {
        List list = super.list(new String[]{"DR_PARENT_KEY", "DD_KEY"}, (Object[])new String[]{parentKey, resourceTreeKey}, this.getClz());
        this.buildI18N(list);
        return list;
    }

    @Override
    public List<DataResourceDO> getByParentNoI18N(String resourceTreeKey, String parentKey) {
        return super.list(new String[]{"DR_PARENT_KEY", "DD_KEY"}, (Object[])new String[]{parentKey, resourceTreeKey}, this.getClz());
    }

    private void buildI18N(List<DataResourceDO> list) {
        if (this.i18NService.isOpenLanaguage()) {
            for (DataResourceDO aDo : list) {
                if (aDo.getResourceKind() == DataResourceKind.DIM_GROUP) {
                    aDo.setTitle(this.i18NService.getI18NDimTitle(aDo.getDimKey(), aDo.getTitle()));
                    continue;
                }
                String prefix = NodeType.valueOf(aDo.getResourceKind().getValue()).name();
                aDo.setTitle(this.i18NService.getI18NTitle(prefix + aDo.getKey(), aDo.getTitle()));
            }
        }
    }

    @Override
    public List<DataResourceDO> getByParent(String parentKey) {
        List list = super.list(new String[]{"DR_PARENT_KEY"}, (Object[])new String[]{parentKey}, this.getClz());
        this.buildI18N(list);
        return list;
    }

    @Override
    public List<DataResourceDO> getByParentNoI18N(String parentKey) {
        return super.list(new String[]{"DR_PARENT_KEY"}, (Object[])new String[]{parentKey}, this.getClz());
    }

    @Override
    public List<DataResourceDO> getByDefineKey(String resourceTreeKey) {
        return super.list(new String[]{"DD_KEY"}, (Object[])new String[]{resourceTreeKey}, this.getClz());
    }

    @Override
    public void deleteByDefineKey(String resourceTreeKey) {
        Assert.notNull((Object)resourceTreeKey, "resourceTreeKey must not be null.");
        super.deleteBy(new String[]{"DD_KEY"}, (Object[])new String[]{resourceTreeKey});
    }

    @Override
    public List<DataResourceDO> getByConditions(String resourceTreeKey, String parentKey, String title) {
        Assert.notNull((Object)resourceTreeKey, "resourceTreeKey must not be null.");
        return super.list(new String[]{"DD_KEY", "DR_PARENT_KEY", "DR_TITLE"}, (Object[])new String[]{resourceTreeKey, parentKey, title}, this.getClz());
    }

    @Override
    public List<DataResourceDO> getByConditions(String resourceTreeKey, String dimKey) {
        Assert.notNull((Object)resourceTreeKey, "resourceTreeKey must not be null.");
        return super.list(new String[]{"DD_KEY", "DIM_KEY"}, (Object[])new String[]{resourceTreeKey, dimKey}, this.getClz());
    }

    @Override
    public List<DataResourceDO> getByConditions(String defineKey, int resourceKind, List<String> dimKey) {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        DataResourceKind[] dataResourceKinds = DataResourceKind.interestType(resourceKind);
        if (dataResourceKinds.length == 0) {
            return Collections.emptyList();
        }
        List kinds = Arrays.stream(dataResourceKinds).map(DataResourceKind::getValue).collect(Collectors.toList());
        String sql = this.selectSQL.toString() + " where " + "DD_KEY" + " = :dd_key and " + "DR_KIND" + " in (:kinds)";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("dd_key", (Object)defineKey);
        sqlParameterSource.addValue("kinds", kinds);
        if (dimKey != null) {
            sql = sql + " and " + "DIM_KEY" + " in (:dimKey)";
            sqlParameterSource.addValue("dimKey", dimKey);
        }
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, this.getRowMapper(this.getClz()));
    }

    @Override
    public List<DataResourceDO> searchBy(String defineKey, String keyword) {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        Assert.notNull((Object)keyword, "keyword must not be null.");
        String sql = this.selectSQL.toString() + " where " + "DD_KEY" + " = :dd_key and " + "DR_TITLE" + " like :keyword";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        keyword = "%" + keyword + "%";
        sqlParameterSource.addValue("dd_key", (Object)defineKey);
        sqlParameterSource.addValue("keyword", (Object)keyword);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, this.getRowMapper(this.getClz()));
    }

    @Override
    public Class<DataResourceDO> getClz() {
        return DataResourceDO.class;
    }
}

