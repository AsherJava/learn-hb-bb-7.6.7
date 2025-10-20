/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.dao.internal;

import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.EntityTableDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.TableSqlDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.dao.IEntBaseOrmSqlFactory;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class EntDaoCacheManager {
    private Logger logger = LoggerFactory.getLogger(EntDaoCacheManager.class);
    @Autowired
    private List<IEntBaseOrmSqlFactory<? extends BaseEntity>> daos;
    @Autowired
    private IEntTableDefineProvider iEntTableDefineProvider;
    private Set<String> upperCaseTableNameSet;
    private volatile boolean daoInit = false;

    public void initTable() {
        if (this.daos != null) {
            this.daos.forEach(this::initGenericDAO);
            this.upperCaseTableNameSet = this.daos.stream().map(item -> item.getTableName().toUpperCase()).collect(Collectors.toSet());
        }
        this.daoInit = true;
    }

    public <Entity extends BaseEntity> void initGenericDAO(IEntBaseOrmSqlFactory<Entity> sqlFactory) {
        try {
            String tableName = ObjectUtils.isEmpty(sqlFactory.getTableName()) ? sqlFactory.getEntityType().getAnnotation(DBTable.class).name().toUpperCase() : sqlFactory.getTableName();
            EntityTableDeclarator<Entity> entityTableDeclarator = new EntityTableDeclarator<Entity>(sqlFactory.getEntityType(), tableName);
            sqlFactory.setTableDeclarator(entityTableDeclarator);
            TableSqlDeclarator<Entity> sqlDeclarator = new TableSqlDeclarator<Entity>(sqlFactory.getEntityType(), tableName);
            sqlFactory.setSqlDeclarator(sqlDeclarator);
        }
        catch (Exception e) {
            this.logger.error(sqlFactory + "\u5b9e\u4f53\u58f0\u660e\u5668\u6216SQL\u58f0\u540d\u5668\u52a0\u8f7d\u5f02\u5e38", e);
            e.printStackTrace();
        }
    }

    public void refreshEntTableDefines(Set<String> tableNames) {
        if (!this.daoInit) {
            return;
        }
        this.daos.stream().filter(dao -> tableNames.contains(dao.getTableName())).forEach(dao -> {
            if (dao.getSqlDeclarator() == null) {
                TableSqlDeclarator sqlDeclarator = new TableSqlDeclarator(dao.getEntityType(), dao.getTableName());
                dao.setSqlDeclarator(sqlDeclarator);
            } else {
                dao.getSqlDeclarator().setEntTableDefine(this.iEntTableDefineProvider.getTableDefine(dao.getTableName()));
            }
        });
    }

    public boolean hasGcTable(Set<String> tableNames) {
        if (!this.daoInit) {
            return false;
        }
        for (String tableName : tableNames) {
            if (tableName == null || !this.upperCaseTableNameSet.contains(tableName.toUpperCase())) continue;
            return true;
        }
        return false;
    }
}

