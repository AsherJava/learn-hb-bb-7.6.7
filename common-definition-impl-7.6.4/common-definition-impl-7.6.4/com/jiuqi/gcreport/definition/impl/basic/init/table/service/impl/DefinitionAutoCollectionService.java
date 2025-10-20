/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.env.GcEnvVarProperties
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.env.GcEnvVarProperties;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.event.EntTableDefineInitAfterEvent;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.TableDefinitionService;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DefinitionAutoCollectionService {
    @Value(value="classpath*:/config/definition/table/*.td.json")
    private Resource[] tableDefineResources;
    @Value(value="classpath*:/config/definition/initdata/*.data.json")
    private Resource[] initDataResources;
    private final TableDefinitionService tableDefinitionService;
    private ApplicationContext applicationContext;
    private final GcEnvVarProperties envPro;
    private Logger logger = LoggerFactory.getLogger(DefinitionAutoCollectionService.class);

    public DefinitionAutoCollectionService(TableDefinitionService tableDefinitionService, GcEnvVarProperties envPro, ApplicationContext applicationContext) {
        this.tableDefinitionService = tableDefinitionService;
        this.envPro = envPro;
        this.applicationContext = applicationContext;
    }

    public void execute() {
        if (!this.envPro.isInittabledefine()) {
            return;
        }
        ArrayList<DefinitionValueV> initDatas = new ArrayList<DefinitionValueV>();
        ObjectMapper om = new ObjectMapper();
        for (Resource res : this.tableDefineResources) {
            DefinitionTableV[] node;
            try {
                node = (DefinitionTableV[])om.readValue(res.getInputStream(), DefinitionTableV[].class);
            }
            catch (IOException e2) {
                this.logger.error("\u89e3\u6790" + res.getFilename() + "\u6587\u4ef6\u4e3a\u8868\u5b9a\u4e49\u5931\u8d25", e2);
                continue;
            }
            try {
                this.tableDefinitionService.initTableDefines(node);
            }
            catch (Exception e3) {
                this.logger.error("\u540c\u6b65" + res.getFilename() + "\u4e2d\u8868\u5b9a\u4e49\u5efa\u6a21\u5931\u8d25", e3);
                continue;
            }
            for (DefinitionTableV tab : node) {
                if (tab.getInitDatas() == null) continue;
                initDatas.addAll(tab.getInitDatas());
            }
        }
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        Map<String, BaseEntity> entityMap = entityTableCollector.getEntitys().stream().collect(Collectors.toMap(BaseEntity::getTableName, e -> e, (k1, k2) -> k1));
        entityTableCollector.getEntitys().forEach(entity -> {
            try {
                if (ShardingBaseEntity.class.isAssignableFrom(entity.getClass())) {
                    List<String> shardingList = ((ShardingBaseEntity)entity).getShardingList();
                    if (!CollectionUtils.isEmpty(shardingList)) {
                        for (String sharding : shardingList) {
                            this.setInitTableAndDatas((List<DefinitionValueV>)initDatas, entityTableCollector, (BaseEntity)entity, (entity.getTableName() + "_" + sharding).toUpperCase(), entityMap);
                        }
                    }
                    return;
                }
                this.setInitTableAndDatas((List<DefinitionValueV>)initDatas, entityTableCollector, (BaseEntity)entity, null, entityMap);
            }
            catch (Exception e) {
                this.logger.error(entity.getClass() + "\u521d\u59cb\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        });
        for (Resource res : this.initDataResources) {
            DefinitionValueV[] node;
            try {
                node = (DefinitionValueV[])om.readValue(res.getInputStream(), DefinitionValueV[].class);
            }
            catch (IOException e4) {
                this.logger.error("\u89e3\u6790" + res.getFilename() + "\u6587\u4ef6\u4e2d\u521d\u59cb\u5316\u6570\u636e\u5931\u8d25", e4);
                continue;
            }
            for (DefinitionValueV tab : node) {
                if (tab == null) continue;
                initDatas.add(tab);
            }
        }
        initDatas.addAll(entityTableCollector.getInitDatas());
        this.tableDefinitionService.initData(initDatas);
    }

    private void setInitTableAndDatas(List<DefinitionValueV> initDatas, EntityTableCollector entityTableCollector, BaseEntity entity, String tableName, Map<String, BaseEntity> entityMap) {
        BaseEntity baseEntity;
        DBTable dbTable = entityTableCollector.getDbTableByType(entity.getClass());
        TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance(entity, dbTable);
        DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
        if (!StringUtils.isEmpty((String)tableName)) {
            tableDefine.setCode(tableName);
            tableDefine.setTableName(tableName);
            if (ShardingBaseEntity.class.isAssignableFrom(entity.getClass())) {
                String sharding = tableName.replace(entity.getTableName(), "");
                List<DefinitionTableIndexV> tableDefineIndexs = tableDefine.getIndexs();
                if (!CollectionUtils.isEmpty(tableDefineIndexs)) {
                    tableDefineIndexs.forEach(e -> e.setTitle(e.getTitle() + sharding));
                }
            }
        }
        if (entity instanceof ITableExtend) {
            tableDefine.getFields().addAll(((ITableExtend)((Object)entity)).getExtendFieldList(tableName));
        }
        if (!StringUtils.isEmpty((String)dbTable.sourceTable()) && Objects.nonNull(baseEntity = entityMap.get(dbTable.sourceTable()))) {
            DBTable dbTableByType = entityTableCollector.getDbTableByType(baseEntity.getClass());
            if (!StringUtils.isEmpty((String)dbTableByType.dataSource())) {
                tableDefine.setDataSource(OuterDataSourceUtils.getOuterDataSourceCode((String)dbTableByType.dataSource()));
            }
            if (baseEntity instanceof ITableExtend) {
                tableDefine.getFields().addAll(((ITableExtend)((Object)baseEntity)).getExtendFieldList(null));
            }
        }
        try {
            this.tableDefinitionService.initTableDefine(tableDefine);
            this.publishEvent(tableDefine);
        }
        catch (Exception e2) {
            this.logger.error("\u540c\u6b65" + tableDefine.getTableName() + "\u8868\u5b9a\u4e49\u5efa\u6a21\u5931\u8d25", e2);
            return;
        }
        if (tableDefine.getInitDatas() != null) {
            initDatas.addAll(tableDefine.getInitDatas());
        }
    }

    public void initTableDefineByTableName(String tableName) {
        if (StringUtils.isEmpty((String)tableName)) {
            this.logger.info("\u6839\u636e\u8868\u540d\u540c\u6b65\u5355\u4e2a\u6570\u636e\u5efa\u6a21\uff1a\u8868\u540d\u4e3a\u7a7a\u8df3\u8fc7\u5904\u7406");
            return;
        }
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity entity = entityTableCollector.getEntityByName(tableName.toUpperCase());
        if (entity == null) {
            this.logger.info("\u6839\u636e\u8868\u540d\u540c\u6b65\u5355\u4e2a\u6570\u636e\u5efa\u6a21\uff1a\u6839\u636e\u8868\u540d" + tableName + "\u672a\u80fd\u627e\u5230\u5bf9\u5e94\u7684EO\u5b9e\u4f53\uff0c\u8df3\u8fc7\u5904\u7406");
            return;
        }
        TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance(entity, entityTableCollector.getDbTableByType(entity.getClass()));
        DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
        try {
            this.tableDefinitionService.initTableDefine(tableDefine);
        }
        catch (Exception e) {
            this.logger.error("\u6839\u636e\u8868\u540d\u540c\u6b65\u5355\u4e2a\u6570\u636e\u5efa\u6a21\uff1a" + tableDefine.getTableName() + "\u8868\u5b9a\u4e49\u5efa\u6a21\u5931\u8d25", e);
            return;
        }
    }

    private void publishEvent(DefinitionTableV tableDefine) {
        this.applicationContext.publishEvent(new EntTableDefineInitAfterEvent(new EntTableDefineInitAfterEvent.EntTableDefineInitAfterInfo(tableDefine.getTableName()), NpContextHolder.getContext()));
    }
}

