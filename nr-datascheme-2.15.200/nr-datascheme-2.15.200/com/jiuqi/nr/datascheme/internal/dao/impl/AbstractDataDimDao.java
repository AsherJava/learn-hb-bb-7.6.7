/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public abstract class AbstractDataDimDao<DO extends DataDimDO>
extends BaseDao {
    private final Logger logger = LoggerFactory.getLogger(AbstractDataDimDao.class);
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insert(DO dataDimDO) throws DataAccessException {
        Assert.notNull(dataDimDO, "dataDimDO must not be null.");
        this.checkParameters(((DataDimDO)dataDimDO).getDataSchemeKey(), ((DataDimDO)dataDimDO).getDimKey());
        ((DataDimDO)dataDimDO).setUpdateTime(Instant.now());
        super.insert(dataDimDO);
    }

    public void batchInsert(List<DO> dataDims) throws DataAccessException {
        Assert.notNull(dataDims, "dataDims must not be null.");
        for (DataDimDO dataDim : dataDims) {
            this.checkParameters(dataDim.getDataSchemeKey(), dataDim.getDimKey());
            dataDim.setUpdateTime(Instant.now());
        }
        super.insert(dataDims.toArray(new Object[0]));
    }

    public void insertNoUpdateTime(DO dataDimDO) throws DataAccessException {
        Assert.notNull(dataDimDO, "dataDimDO must not be null.");
        this.checkParameters(((DataDimDO)dataDimDO).getDataSchemeKey(), ((DataDimDO)dataDimDO).getDimKey());
        super.insert(dataDimDO);
    }

    public void delete(@NonNull String dataSchemeKey, @NonNull String dimKey) throws DataAccessException {
        this.checkParameters(dataSchemeKey, dimKey);
        super.deleteBy(new String[]{"DD_DS_KEY", "DD_DIM_KEY"}, new Object[]{dataSchemeKey, dimKey});
    }

    public DO get(@NonNull String dataSchemeKey, @NonNull String dimKey) throws DataAccessException {
        this.checkParameters(dataSchemeKey, dimKey);
        return (DO)((DataDimDO)super.list(new String[]{"DD_DS_KEY", "DD_DIM_KEY"}, new Object[]{dataSchemeKey, dimKey}, this.getClz()).stream().findFirst().orElse(null));
    }

    public void deleteByDataScheme(@NonNull String dataSchemeKey) throws DataAccessException {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        super.deleteBy(new String[]{"DD_DS_KEY"}, new Object[]{dataSchemeKey});
    }

    public List<DO> getByDataScheme(@NonNull String dataSchemeKey) throws DataAccessException {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        return super.list(new String[]{"DD_DS_KEY"}, new Object[]{dataSchemeKey}, this.getClz());
    }

    private void checkParameters(String dataSchemeKey, String dimKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
    }

    public List<DO> getByPeriodType(@NonNull PeriodType periodType) {
        Assert.notNull((Object)periodType, "periodType must not be null.");
        return super.list(new String[]{"DD_PERIOD_TYPE"}, new Object[]{periodType}, this.getClz());
    }

    public List<DO> getByDimKey(@NonNull String dimKey) {
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        return this.getByDimKey(new String[]{dimKey});
    }

    public List<DO> getByDimKey(String ... dimKey) {
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        String sql = this.selectSQL + " where " + "DD_DIM_KEY" + " in ( :dimKey ) and " + "DD_TYPE" + " in ( :types )";
        this.logger.debug(sql);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("dimKey", Arrays.asList(dimKey));
        sqlParameterSource.addValue("types", Arrays.asList(DimensionType.UNIT.getValue(), DimensionType.UNIT_SCOPE.getValue()));
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public void delete(String dataScheme, DimensionType type, String dimKey) {
        super.deleteBy(new String[]{"DD_DS_KEY", "DD_TYPE", "DD_DIM_KEY"}, new Object[]{dataScheme, type.getValue(), dimKey});
    }

    public void delete(String dataScheme, DimensionType type) {
        super.deleteBy(new String[]{"DD_DS_KEY", "DD_TYPE"}, new Object[]{dataScheme, type.getValue()});
    }

    public List<DO> getAll() {
        return super.list(this.getClz());
    }

    public abstract Class<DO> getClz();
}

