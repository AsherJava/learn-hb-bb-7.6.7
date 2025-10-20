/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DBUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.definition.impl.sqlutil;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DBUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao.IdTemporaryDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class IdTemporaryTableUtils {
    private static IdTemporaryTableUtils utils = new IdTemporaryTableUtils();
    private static IdTemporaryDao idTempDao;
    public static final String DATABSE_TYPE_MYSQL = "mysql";
    public static boolean IS_MYSQL;

    public static IdTemporaryTableUtils getInstance() {
        return utils;
    }

    public void ini(IdTemporaryDao idTempDao) {
        IdTemporaryTableUtils.idTempDao = idTempDao;
        IS_MYSQL = IdTemporaryTableUtils.isMySqlDataSourceType();
    }

    public static void insertTempDb(String groupId, Collection<String> ids) {
        Assert.isNotNull((Object)idTempDao, (String)"idTempDao\u672a\u521d\u59cb\u5316\uff0c\u65e0\u6cd5\u8fdb\u884cID\u4e34\u65f6\u8868\u7684\u6570\u636e\u5e93\u64cd\u4f5c\u3002", (Object[])new Object[0]);
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ArrayList<IdTemporary> idTemporaryEntities = new ArrayList<IdTemporary>();
        ids.forEach(id -> {
            IdTemporary idTempEntity = new IdTemporary();
            idTempEntity.setGroupId(groupId);
            idTempEntity.setTbId((String)id);
            idTemporaryEntities.add(idTempEntity);
        });
        idTempDao.saveAll(idTemporaryEntities);
    }

    public static void insertTempStr(String groupId, Collection<String> mulStr) {
        Assert.isNotNull((Object)idTempDao, (String)"idTempDao\u672a\u521d\u59cb\u5316\uff0c\u65e0\u6cd5\u8fdb\u884cID\u4e34\u65f6\u8868\u7684\u6570\u636e\u5e93\u64cd\u4f5c\u3002", (Object[])new Object[0]);
        if (CollectionUtils.isEmpty(mulStr)) {
            return;
        }
        ArrayList<IdTemporary> idTemporaryEntities = new ArrayList<IdTemporary>();
        mulStr.forEach(code -> {
            IdTemporary idTempEntity = new IdTemporary();
            idTempEntity.setGroupId(groupId);
            idTempEntity.setTbCode((String)code);
            idTemporaryEntities.add(idTempEntity);
        });
        idTempDao.saveAll(idTemporaryEntities);
    }

    static void insertTempDouble(String groupId, Collection<? extends Number> mulStr) {
        Assert.isNotNull((Object)idTempDao, (String)"idTempDao\u672a\u521d\u59cb\u5316\uff0c\u65e0\u6cd5\u8fdb\u884cID\u4e34\u65f6\u8868\u7684\u6570\u636e\u5e93\u64cd\u4f5c\u3002", (Object[])new Object[0]);
        if (CollectionUtils.isEmpty(mulStr)) {
            return;
        }
        ArrayList<IdTemporary> idTemporaryEntities = new ArrayList<IdTemporary>();
        mulStr.forEach(num -> {
            IdTemporary idTempEntity = new IdTemporary();
            idTempEntity.setGroupId(groupId);
            idTempEntity.setTbNum((Number)num);
            idTemporaryEntities.add(idTempEntity);
        });
        idTempDao.saveAll(idTemporaryEntities);
    }

    public static void deteteByGroupId(String groupId) {
        if (StringUtils.isEmpty(groupId)) {
            return;
        }
        if (IS_MYSQL) {
            List<IdTemporary> idTemporaries = idTempDao.listIdTemporaryByGroupId(groupId);
            idTempDao.deleteBatch(idTemporaries);
        } else {
            idTempDao.deleteByGroupId(groupId);
        }
    }

    public static void deteteByGroupIds(Collection<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        List<String> groupIdNotNull = groupIds.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(groupIdNotNull)) {
            return;
        }
        idTempDao.deleteByGroupIds(groupIdNotNull);
    }

    private static boolean isMySqlDataSourceType() {
        DBUtils dbUtils = (DBUtils)SpringContextUtils.getBean(DBUtils.class);
        String dbType = dbUtils.getDBType();
        return StringUtils.hasText(dbType) && DATABSE_TYPE_MYSQL.equalsIgnoreCase(dbType.trim());
    }
}

