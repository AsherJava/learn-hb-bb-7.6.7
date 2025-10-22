/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.dao.IResourceLinkDao;
import com.jiuqi.nr.dataresource.dao.impl.TransUtil;
import com.jiuqi.nr.dataresource.entity.DataResourceLinkDO;
import com.jiuqi.nr.dataresource.entity.SearchDataFieldDO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DataResourceLinkDaoImpl
extends BaseDao
implements IResourceLinkDao {
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(DataResourceLinkDaoImpl.class);

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    @Override
    public void insert(List<DataResourceLinkDO> entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        for (DataResourceLinkDO treeDO : entity) {
            Assert.notNull((Object)treeDO, "Collection should not contain null.");
        }
        super.insert(entity.toArray());
    }

    @Override
    public void delete(String groupKey, List<String> keys) throws DataAccessException {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        Assert.notNull(keys, "keys must not be null.");
        if (keys.size() == 0) {
            return;
        }
        int number = 1000;
        int limit = (keys.size() + number - 1) / number;
        Stream.iterate(0, i -> i + 1).limit(limit).forEach(r -> {
            String sql = "delete from NR_DATARESOURCE_LINK where DR_KEY = :groupKey AND DF_KEY in (:keys) ";
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            List subDelIds = keys.stream().skip(r * number).limit(number).collect(Collectors.toList());
            sqlParameterSource.addValue("keys", subDelIds);
            sqlParameterSource.addValue("groupKey", (Object)groupKey);
            this.logger.debug(sql);
            this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
        });
    }

    @Override
    public void delete(String groupKey) throws DataAccessException {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        String sql = "delete from NR_DATARESOURCE_LINK where DR_KEY = :groupKey ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("groupKey", (Object)groupKey);
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    @Override
    public void delete(List<String> groupKey) throws DataAccessException {
        Assert.notNull(groupKey, "groupKey must not be null.");
        if (groupKey.isEmpty()) {
            return;
        }
        String sql = "delete from NR_DATARESOURCE_LINK where DR_KEY in (:groupKey) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("groupKey", groupKey);
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    @Override
    public void deleteByDefineKey(String defineKey) throws DataAccessException {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        String sql = "delete from NR_DATARESOURCE_LINK where DD_KEY = :defineKey ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("defineKey", (Object)defineKey);
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    @Override
    public void update(List<DataResourceLinkDO> entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        String sql = String.format("update %s set %s = :order where %s = :groupKey and %s = :dataFieldKey", "NR_DATARESOURCE_LINK", "DL_ORDER", "DR_KEY", "DF_KEY");
        ArrayList<MapSqlParameterSource> listParam = new ArrayList<MapSqlParameterSource>(entity.size());
        for (DataResourceLinkDO resourceGroupLinkDO : entity) {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            Assert.notNull((Object)resourceGroupLinkDO, "Collection should not contain null.");
            String groupKey = resourceGroupLinkDO.getGroupKey();
            Assert.notNull((Object)groupKey, "groupKey must not be null.");
            sqlParameterSource.addValue("groupKey", (Object)groupKey);
            String dataFieldKey = resourceGroupLinkDO.getDataFieldKey();
            Assert.notNull((Object)dataFieldKey, "dataFieldKey must not be null.");
            sqlParameterSource.addValue("dataFieldKey", (Object)dataFieldKey);
            String order = resourceGroupLinkDO.getOrder();
            Assert.notNull((Object)order, "order must not be null.");
            sqlParameterSource.addValue("order", (Object)order);
            listParam.add(sqlParameterSource);
        }
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.batchUpdate(sql, (SqlParameterSource[])listParam.toArray(new MapSqlParameterSource[0]));
    }

    @Override
    public List<DataField> getByGroup(String groupKey) throws DataAccessException {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        List list = super.list(new String[]{"DR_KEY"}, new Object[]{groupKey}, this.getClz());
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        HashMap<String, DataResourceLinkDO> map = new HashMap<String, DataResourceLinkDO>(list.size());
        for (DataResourceLinkDO dataResourceLinkDO : list) {
            map.put(dataResourceLinkDO.getDataFieldKey(), dataResourceLinkDO);
        }
        List dataFields = this.iRuntimeDataSchemeService.getDataFields(new ArrayList(map.keySet()));
        ArrayList<DataField> res = new ArrayList<DataField>();
        for (DataField dataField : dataFields) {
            if (!dataField.isVisible()) continue;
            DataFieldDTO dto = (DataFieldDTO)dataField;
            dto.setOrder(((DataResourceLinkDO)map.get(dto.getKey())).getOrder());
            res.add((DataField)dto);
        }
        res.sort(null);
        return res;
    }

    @Override
    public List<DataResourceLinkDO> getByDataFieldKey(String dataFieldKey) throws DataAccessException {
        Assert.notNull((Object)dataFieldKey, "dataFieldKey must not be null.");
        return super.list(new String[]{"DF_KEY"}, new Object[]{dataFieldKey}, this.getClz());
    }

    @Override
    public List<DataResourceLinkDO> getByDefineKey(String defineKey) throws DataAccessException {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        return super.list(new String[]{"DD_KEY"}, new Object[]{defineKey}, this.getClz());
    }

    @Override
    public List<SearchDataFieldDO> searchByDefineKey(String defineKey, String keyword) throws DataAccessException {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        Assert.notNull((Object)keyword, "keyword must not be null.");
        String format = "SELECT NDL.%s, NDFD.%s, NDFD.%s, NDFD.%s FROM NR_DATARESOURCE_LINK NDL INNER JOIN NR_DATASCHEME_FIELD NDFD ON NDL.%s = NDFD.%s WHERE NDL.%s = :defineKey   AND (UPPER(NDFD.%s) LIKE :keyword OR UPPER(NDFD.%s) LIKE :keyword)";
        String sql = String.format(format, "DR_KEY", "DF_KEY", "DF_CODE", "DF_TITLE", "DF_KEY", "DF_KEY", "DD_KEY", "DF_CODE", "DF_TITLE");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("defineKey", (Object)defineKey);
        sqlParameterSource.addValue("keyword", (Object)("%" + keyword.toUpperCase() + "%"));
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, (rs, rowNum) -> {
            SearchDataFieldDO fieldDO = new SearchDataFieldDO();
            String group = rs.getString("DR_KEY");
            String key = rs.getString("DF_KEY");
            String code = rs.getString("DF_CODE");
            String title = rs.getString("DF_TITLE");
            fieldDO.setGroupKey(group);
            fieldDO.setKey(key);
            fieldDO.setCode(code);
            fieldDO.setTitle(title);
            return fieldDO;
        });
    }

    public Class<DataResourceLinkDO> getClz() {
        return DataResourceLinkDO.class;
    }
}

