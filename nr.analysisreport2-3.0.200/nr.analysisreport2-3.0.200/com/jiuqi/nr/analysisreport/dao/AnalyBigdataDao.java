/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.apache.shiro.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.nr.analysisreport.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefine;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefineImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class AnalyBigdataDao
extends BaseDao {
    private Class<AnalyBigdataDefineImpl> implClass = AnalyBigdataDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public int inseartBigdata(AnalyBigdataDefine analyBigdataDefine) throws Exception {
        return this.insert(analyBigdataDefine);
    }

    public int[] inseartBigdatas(AnalyBigdataDefine[] analyBigdataDefines) throws Exception {
        return this.insert(analyBigdataDefines);
    }

    public int updateBigdata(AnalyBigdataDefine analyBigdataDefine) throws Exception {
        if (StringUtils.hasText(analyBigdataDefine.getArcKey())) {
            return this.update(analyBigdataDefine, new String[]{"key", "arcKey"}, new String[]{analyBigdataDefine.getKey(), analyBigdataDefine.getArcKey()});
        }
        analyBigdataDefine.setArcKey("default");
        return this.update(analyBigdataDefine);
    }

    public int deleteBigDataByKey(String key) throws Exception {
        return this.delete(key);
    }

    public AnalyBigdataDefine getBykey(String key) {
        return (AnalyBigdataDefine)this.getByKey(key, this.implClass);
    }

    public int[] batchDeleteByKeys(String[] keys) throws DBParaException {
        return this.delete(keys);
    }

    public List<AnalyBigdataDefine> list(String key) {
        return this.list(new String[]{"key"}, new Object[]{key}, this.implClass);
    }

    public AnalyBigdataDefine getArcBigData(String bigDataKey, String arcKey) {
        List list = super.list(new String[]{"key", "arcKey"}, new Object[]{bigDataKey, arcKey}, AnalyBigdataDefineImpl.class);
        if (!CollectionUtils.isEmpty(list)) {
            return (AnalyBigdataDefine)list.get(0);
        }
        return null;
    }

    public boolean checkGenCatalogCompleted(String templateKey) {
        String sql = "SELECT COUNT(1) FROM SYS_ANALYVERSION_BIGDATA WHERE AV_KEY=?  AND AV_UPDATETIME != AV_CATALOG_UPDATETIME";
        return (Integer)super.queryForObject(sql, new Object[]{templateKey}, Integer.TYPE) == 0;
    }

    public Map<String, String> getCatalogMap(String key) {
        String sql = "select %s,%s  from %s where %s = ? ";
        sql = String.format(sql, "av_arc_key", "av_catalog", "sys_analyversion_bigdata", "AV_KEY");
        return (Map)this.jdbcTemplate.query(sql, ps -> ps.setString(1, key), (ResultSetExtractor)new Mapper());
    }

    class Mapper
    implements ResultSetExtractor<Map<String, String>> {
        Mapper() {
        }

        public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<String, String> map = new HashMap<String, String>();
            while (rs.next()) {
                map.put(rs.getString("av_arc_key"), rs.getString("av_catalog"));
            }
            return map;
        }
    }
}

