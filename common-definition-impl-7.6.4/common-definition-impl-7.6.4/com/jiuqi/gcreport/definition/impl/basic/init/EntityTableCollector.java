/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.init;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.intf.IDefaultValueEnum;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EntityTableCollector {
    private List<BaseEntity> entitys = new ArrayList<BaseEntity>();
    private Map<Class<? extends BaseEntity>, DBTable> classDbTableMapping = new HashMap<Class<? extends BaseEntity>, DBTable>();
    private List<DefinitionValueV> initDatas = new ArrayList<DefinitionValueV>();
    private Map<String, BaseEntity> tableNameEntityMapping = new HashMap<String, BaseEntity>(16);
    private Map<String, List<DefinitionTableV>> budModelTableMap = new HashMap<String, List<DefinitionTableV>>();
    private static EntityTableCollector entityTableCollector = new EntityTableCollector();

    public static EntityTableCollector getInstance() {
        return entityTableCollector;
    }

    private EntityTableCollector() {
    }

    void addEntityType(BaseEntity entity, DBTable dbTable) {
        if (this.tableNameEntityMapping.containsKey(entity.getTableName().toUpperCase())) {
            throw new BusinessRuntimeException("\u5b58\u5728\u591a\u4e2a\u8868\u540d\u4e3a\u201c" + entity.getTableName() + "\u201d\u7684\u5b9e\u4f53\u5b9a\u4e49\u3002");
        }
        this.entitys.add(entity);
        this.classDbTableMapping.put(entity.getClass(), dbTable);
        this.tableNameEntityMapping.put(entity.getTableName().toUpperCase(), entity);
    }

    void addInitDataType(Class initDataType) {
        if (initDataType.isEnum() && IDefaultValueEnum.class.isAssignableFrom(initDataType)) {
            IDefaultValueEnum[] enumConstants;
            for (IDefaultValueEnum dve : enumConstants = (IDefaultValueEnum[])initDataType.getEnumConstants()) {
                if (dve.getID() == null || StringUtils.isEmpty((String)dve.getTableName()) || dve.getFieldValues().size() < 1) {
                    return;
                }
                DefinitionValueV v = new DefinitionValueV(dve);
                this.initDatas.add(v);
            }
        }
    }

    public List<BaseEntity> getEntitys() {
        return new ArrayList<BaseEntity>(this.entitys);
    }

    public DBTable getDbTableByType(Class<? extends BaseEntity> type) {
        return this.classDbTableMapping.get(type);
    }

    public BaseEntity getEntityByName(String tableName) {
        if (this.tableNameEntityMapping.containsKey(tableName)) {
            return this.tableNameEntityMapping.get(tableName);
        }
        return null;
    }

    public List<DefinitionValueV> getInitDatas() {
        return new ArrayList<DefinitionValueV>(this.initDatas);
    }

    public Map<String, List<DefinitionTableV>> getModelTableMap() {
        return this.budModelTableMap;
    }

    public String getDataSourceByName(String tableName) {
        DBTable dbTableByType;
        BaseEntity entityByName = this.getEntityByName(tableName);
        if (entityByName != null && Objects.nonNull(dbTableByType = this.getDbTableByType(entityByName.getClass())) && !StringUtils.isEmpty((String)dbTableByType.dataSource())) {
            return OuterDataSourceUtils.getOuterDataSourceCode((String)dbTableByType.dataSource());
        }
        return null;
    }

    public void addToModelTableMap(String tableName, DefinitionTableV effectTable) {
        this.budModelTableMap.computeIfAbsent(tableName, k -> new ArrayList()).add(effectTable);
    }
}

