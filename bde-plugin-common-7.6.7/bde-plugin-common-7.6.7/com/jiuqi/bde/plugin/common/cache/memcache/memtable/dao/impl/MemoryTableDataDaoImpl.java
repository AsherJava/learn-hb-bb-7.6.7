/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.template.IntegerResultSetExtractor
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.common.cache.memcache.memtable.dao.impl;

import com.jiuqi.bde.common.template.IntegerResultSetExtractor;
import com.jiuqi.bde.plugin.common.cache.memcache.gather.IMemoryBalanceType;
import com.jiuqi.bde.plugin.common.cache.memcache.gather.IMemoryBalanceTypeGather;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.dao.MemoryTableDataDao;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryTableDataDaoImpl
extends BaseDataCenterDaoImpl
implements MemoryTableDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Lazy
    private INvwaSystemOptionService optionService;
    @Autowired
    private IMemoryBalanceTypeGather memoryBalanceTypeGather;
    private static final String SELECT_COUNT_SQL = "SELECT 1 FROM BDE_MEMORY_TABLEDATA WHERE TABLENAME  = ? AND BIZCOMBID = ? \n";
    private static final String SELECT_INVALID_CACHE_SQL = "SELECT TABLENAME , BIZCOMBID FROM BDE_MEMORY_TABLEDATA WHERE CACHETIME < ? \n";
    private static final String INSERT_SQL = "INSERT INTO BDE_MEMORY_TABLEDATA (TABLENAME, BIZCOMBID, CACHETIME, REMARK) \nVALUES(? ,? ,? ,?) \n";
    private static final String LOCK_BY_VALIDTIME_SQL = "UPDATE BDE_MEMORY_TABLEDATA SET TABLENAME  = TABLENAME WHERE TABLENAME  = ? AND BIZCOMBID = ? AND (? - CACHETIME) >  ? \n";
    private static final String LOCK_SQL = "UPDATE BDE_MEMORY_TABLEDATA SET CACHETIME  = 0 WHERE TABLENAME  = ? AND BIZCOMBID = ? \n";
    private static final String UPDATE_SQL = "UPDATE BDE_MEMORY_TABLEDATA SET CACHETIME  = ? WHERE TABLENAME  = ? AND BIZCOMBID = ? \n";

    @Override
    public int insert(MemoryTableDataDO data) {
        int i;
        block3: {
            i = 0;
            try {
                Integer count = (Integer)this.jdbcTemplate.query(SELECT_COUNT_SQL, (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{data.getTableName(), data.getBizCombId()});
                if (count != null && count > 0) {
                    return 0;
                }
                i = this.jdbcTemplate.update(INSERT_SQL, new Object[]{data.getTableName(), data.getBizCombId(), 0, data.getRemark()});
            }
            catch (Exception e) {
                if (SqlUtil.isSQLUniqueException((String)VaMapperConfig.getDbType(), (Exception)e)) break block3;
                throw new BusinessRuntimeException((Throwable)e);
            }
        }
        return i;
    }

    @Override
    public int lockByValidTime(MemoryTableDataDO data) {
        String expirationStr = this.optionService.findValueById("BDE_TABLEDATA_EXPIRATION");
        Integer cacheValidTime = 1000 * Integer.valueOf(expirationStr);
        return this.jdbcTemplate.update(LOCK_BY_VALIDTIME_SQL, new Object[]{data.getTableName(), data.getBizCombId(), System.currentTimeMillis(), cacheValidTime});
    }

    @Override
    public int lockRow(MemoryTableDataDO data) {
        return this.jdbcTemplate.update(LOCK_SQL, new Object[]{data.getTableName(), data.getBizCombId()});
    }

    @Override
    public int updateCacheTime(MemoryTableDataDO data) {
        return this.jdbcTemplate.update(UPDATE_SQL, new Object[]{System.currentTimeMillis(), data.getTableName(), data.getBizCombId()});
    }

    @Override
    public List<MemoryTableDataDO> selectInvalidCaches() {
        String expirationStr = this.optionService.findValueById("BDE_TABLEDATA_EXPIRATION");
        Integer cacheValidTime = 1000 * Integer.valueOf(expirationStr);
        long invalidTime = System.currentTimeMillis() - (long)cacheValidTime.intValue();
        return this.jdbcTemplate.query(SELECT_INVALID_CACHE_SQL, (RowMapper)new BeanPropertyRowMapper(MemoryTableDataDO.class), new Object[]{invalidTime});
    }

    @Override
    public int deleteCacheData(MemoryTableDataDO data) {
        Assert.isNotNull((Object)data);
        Assert.isNotEmpty((String)data.getTableName());
        Assert.isNotEmpty((String)data.getBizCombId());
        String CLEAR_SQL = "DELETE FROM %1$s WHERE 1 = 1 AND BIZCOMBID = ? ";
        IMemoryBalanceType balanceType = this.memoryBalanceTypeGather.getMemoryBalanceType(data.getTableName());
        return this.jdbcTemplate.update(String.format("DELETE FROM %1$s WHERE 1 = 1 AND BIZCOMBID = ? ", balanceType.getCode()), new Object[]{data.getBizCombId()});
    }

    @Override
    public boolean existsCacheTableData(String memTableName) {
        Assert.isNotEmpty((String)memTableName);
        IMemoryBalanceType balanceType = this.memoryBalanceTypeGather.getMemoryBalanceType(memTableName);
        String COUNT_SQL = "SELECT COUNT(1) FROM %1$s WHERE 1 = 1 ";
        int count = (Integer)this.jdbcTemplate.query(this.getDbSqlHandler().getPageSql(String.format("SELECT COUNT(1) FROM %1$s WHERE 1 = 1 ", balanceType.getCode()), 1, 1), (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[0]);
        return count > 0;
    }

    @Override
    public void truncateCacheData(String memTableName) {
        Assert.isNotEmpty((String)memTableName);
        IMemoryBalanceType balanceType = this.memoryBalanceTypeGather.getMemoryBalanceType(memTableName);
        String TRUNCATE_SQL = "TRUNCATE TABLE %1$s";
        this.jdbcTemplate.execute(String.format("TRUNCATE TABLE %1$s", balanceType.getCode()));
    }

    @Override
    public int countDataByTableName(String tableName, Long timeStamp) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM ");
        sql.append("BDE_MEMORY_TABLEDATA");
        sql.append(" WHERE TABLENAME = ? AND CACHETIME > ?");
        return (Integer)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (rs != null && rs.next() && rs.getObject(1) != null) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[]{tableName, timeStamp});
    }
}

