/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.analysisreport.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AnalyVersionDefineDao
extends BaseDao {
    private static String COLUMN_AT_KEY = "key";
    private static String COLUMN_ANALYTEMPLATE_KEY = "analytemplateKey";
    private static String COLUMN_ENEITY_KEY = "entityKey";
    private static String COLUMN_DATE_KEY = "dateKey";
    private static String COLUMN_VERSION_NAME = "versionName";
    private Class<AnalyVersionDefineImpl> implClass = AnalyVersionDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<AnalyVersionDefine> getListByGroupKey(String entityKey, String dateKey) throws Exception {
        return this.list(new String[]{COLUMN_ENEITY_KEY, COLUMN_DATE_KEY}, new Object[]{entityKey, dateKey}, this.implClass);
    }

    public int insertVersion(AnalyVersionDefine analyVersionDefine) throws Exception {
        return this.insert(analyVersionDefine);
    }

    public int updateVersion(AnalyVersionDefine analyVersionDefine) throws Exception {
        return this.update(analyVersionDefine);
    }

    public int deleteBanben(AnalyVersionDefine analyVersionDefine) throws Exception {
        return this.delete(analyVersionDefine);
    }

    public int deleteVersionByKey(String key) throws Exception {
        return this.delete(key);
    }

    public AnalyVersionDefine getListByKey(String key) {
        return (AnalyVersionDefine)this.getByKey(key, this.implClass);
    }

    public List<AnalyVersionDefine> getVersionList(String analytemplateKey, String entitykey, String date) {
        return this.list(new String[]{COLUMN_ANALYTEMPLATE_KEY, COLUMN_ENEITY_KEY, COLUMN_DATE_KEY}, new Object[]{analytemplateKey, entitykey, date}, this.implClass);
    }

    public List<AnalyVersionDefine> getAllVersionList() {
        return this.list(this.implClass);
    }

    public List<AnalyVersionDefine> getVersionListByname(String analytemplateKey, String entitykey, String date, String name) {
        return this.list(new String[]{COLUMN_ANALYTEMPLATE_KEY, COLUMN_ENEITY_KEY, COLUMN_DATE_KEY, COLUMN_VERSION_NAME}, new Object[]{analytemplateKey, entitykey, date, name}, this.implClass);
    }

    public List<AnalyVersionDefine> getListByModelKey(String modelKey) {
        return this.list(new String[]{COLUMN_ANALYTEMPLATE_KEY}, new Object[]{modelKey}, this.implClass);
    }

    public int[] batchDeleteVersion(AnalyVersionDefine[] analyVersionDefines) throws DBParaException {
        return this.delete(analyVersionDefines);
    }

    public int deleteBymodelKey(String key) throws DBParaException {
        return this.deleteBy(new String[]{COLUMN_ANALYTEMPLATE_KEY}, new Object[]{key});
    }
}

