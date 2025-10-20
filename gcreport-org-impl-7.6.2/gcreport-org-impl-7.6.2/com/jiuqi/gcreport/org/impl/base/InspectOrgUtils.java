/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.definition.common.JDBCHelper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.gcreport.org.impl.base;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.cache.listener.GcOrgCacheListener;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.definition.common.JDBCHelper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InspectOrgUtils {
    private static final ConcurrentHashMap<String, Boolean> mergeUnitCache = new ConcurrentHashMap();
    private static final long CACHE_EXPIRATION_TIME = 60000L;

    public static String getDateFormat(Date date) {
        JDBCHelper jdbcHelper = (JDBCHelper)SpringContextUtils.getBean(JDBCHelper.class);
        String formatDate = "";
        Connection connection = null;
        try {
            connection = jdbcHelper.getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            formatDate = database.createSQLInterpretor(connection).formatSQLDate(date);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("SQL\u65e5\u671f\u683c\u5f0f\u8f6c\u6362\u5931\u8d25");
        }
        finally {
            jdbcHelper.closeConn(connection);
        }
        return formatDate;
    }

    public static boolean clearOrgCache(String orgType) {
        OrgDataClient orgDataClient = (OrgDataClient)SpringContextUtils.getBean(OrgDataClient.class);
        EntityResetCacheService nrCacheService = (EntityResetCacheService)SpringContextUtils.getBean(EntityResetCacheService.class);
        boolean flag = true;
        OrgCategoryDTO orgCatDTO = new OrgCategoryDTO();
        orgCatDTO.setName(orgType);
        orgDataClient.cleanCache(orgCatDTO);
        if (nrCacheService != null) {
            nrCacheService.resetCache(orgType);
        }
        InspectOrgUtils.clearGcOrgCache(orgType);
        return flag;
    }

    public static boolean syncOrgCache(String orgType) {
        OrgDataClient orgDataClient = (OrgDataClient)SpringContextUtils.getBean(OrgDataClient.class);
        EntityResetCacheService nrCacheService = (EntityResetCacheService)SpringContextUtils.getBean(EntityResetCacheService.class);
        boolean flag = true;
        OrgCategoryDTO orgCatDTO = new OrgCategoryDTO();
        orgCatDTO.setName(orgType);
        orgDataClient.syncCache(orgCatDTO);
        if (nrCacheService != null) {
            nrCacheService.resetCache(orgType);
        }
        InspectOrgUtils.clearGcOrgCache(orgType);
        return flag;
    }

    public static boolean clearGcOrgCache(String orgType) {
        GcOrgCacheListener messageService = (GcOrgCacheListener)SpringContextUtils.getBean(GcOrgCacheListener.class);
        messageService.publishRefreshMessage((GcOrgDataCacheBaseEvent)new GcOrgDataItemChangeEvent(orgType));
        return true;
    }

    public static String getVersionSql(String alias, String date) {
        String versionSql = " %1$s.VALIDTIME <= %2$s and %2$s < %1$s.INVALIDTIME ";
        return String.format(versionSql, alias, date);
    }

    public static List<String> getColumnsInTable(String table) {
        JDBCHelper jdbcHelper = (JDBCHelper)SpringContextUtils.getBean(JDBCHelper.class);
        ArrayList<String> res = new ArrayList<String>();
        ResultSet colRet = null;
        Connection connection = null;
        try {
            connection = jdbcHelper.getConnection();
            DatabaseMetaData metaData = jdbcHelper.getConnection().getMetaData();
            colRet = metaData.getColumns(null, "%", table, "%");
            while (colRet.next()) {
                colRet.getMetaData();
                String columnName = colRet.getString("COLUMN_NAME");
                res.add(columnName.toUpperCase());
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            jdbcHelper.close(connection, null, colRet);
        }
        return res;
    }

    public static String getGcParentsByOldParents(String parents, GcOrgCodeConfig gcOrgCodeConfig) {
        int len = gcOrgCodeConfig.getCodeLength();
        boolean variableLength = gcOrgCodeConfig.isVariableLength();
        StringBuffer gcParents = new StringBuffer();
        String[] parentPath = parents.split("/");
        if (variableLength) {
            for (int i = 0; i < parentPath.length; ++i) {
                String code;
                String newCode = code = parentPath[i];
                if (code.length() < len) {
                    StringBuffer s = new StringBuffer();
                    for (int j = 0; j < len - code.length(); ++j) {
                        s.append("@");
                    }
                    newCode = s.toString() + code;
                }
                if (i != parentPath.length - 1) {
                    gcParents.append(newCode).append("/");
                    continue;
                }
                gcParents.append(newCode);
            }
        } else {
            gcParents.append(parents);
        }
        return gcParents.toString();
    }

    public static String getDefaultOrgTypeId(String orgType) {
        Map<String, ZB> zbMap = GcOrgTypeTool.getInstance().getTypeExtFieldsByName(orgType);
        ZB zb = zbMap.get("ORGTYPEID");
        if (zb == null) {
            return "MD_ORG_CORPORATE";
        }
        if (!org.springframework.util.StringUtils.hasText(zb.getDefaultVal())) {
            return "MD_ORG_CORPORATE";
        }
        return zb.getDefaultVal().toUpperCase();
    }

    public static boolean checkEnableAutoCalc(String orgTypeName) {
        INvwaSystemOptionService systemOptionsService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String option = systemOptionsService.get("gc_option_org_fieldCalc", "NOT_AUTO_CALC_FIELD");
        if (!StringUtils.isEmpty((String)option)) {
            ArrayList notAutoCalctypes = CollectionUtils.newArrayList((Object[])option.split(";"));
            return !notAutoCalctypes.contains(orgTypeName);
        }
        return true;
    }

    public static boolean enableMergeUnit() {
        String cacheKey = "gc_option_org_fieldCalc";
        Boolean cachedValue = mergeUnitCache.get(cacheKey);
        if (cachedValue != null) {
            return cachedValue;
        }
        INvwaSystemOptionService systemOptionsService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String option = systemOptionsService.get("gc_option_org_fieldCalc", "DISABLE_GCREPORT_ORG");
        boolean result = option == null || !option.equals("1");
        mergeUnitCache.put(cacheKey, result);
        return result;
    }

    static {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(mergeUnitCache::clear, 60000L, 60000L, TimeUnit.MILLISECONDS);
    }
}

