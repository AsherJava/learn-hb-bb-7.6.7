/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.dc.mappingscheme.impl.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GcHyperDimensionPublishEventListener
implements ApplicationListener<HyperDimensionPublishedEvent> {
    private static Logger logger = LoggerFactory.getLogger(GcHyperDimensionPublishEventListener.class);
    public static final String REF_TABLE_TEMPLATE_NAME = "REF";
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;

    @Override
    public void onApplicationEvent(HyperDimensionPublishedEvent event) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        List baseEntities = entityTableCollector.getEntitys();
        HashSet ybzTable = CollectionUtils.newHashSet();
        for (BaseEntity baseEntity : baseEntities) {
            DBTable dbTable = entityTableCollector.getDbTableByType(baseEntity.getClass());
            if (!dbTable.convertToBudModel() || !StringUtils.isEmpty((String)dbTable.sourceTable()) || !dbTable.name().startsWith("DC_")) continue;
            ybzTable.add(dbTable.name());
        }
        if (!ybzTable.contains(event.getModel().getCode())) {
            return;
        }
        for (ModelShowDimensionDTO showDimensionDTO : event.getModel().getDimensionDOs()) {
            BaseEntity entity = entityTableCollector.getEntityByName(REF_TABLE_TEMPLATE_NAME);
            TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
            DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
            if (StringUtils.isEmpty((String)showDimensionDTO.getBaseDataCode())) continue;
            this.checkAndAddSourceField(showDimensionDTO);
            String tableName = DataRefUtil.getRefTableName((String)showDimensionDTO.getBaseDataCode());
            tableDefine.setCode(tableName);
            tableDefine.setTableName(tableName);
            for (DefinitionTableIndexV index : tableDefine.getIndexs()) {
                String newTitle = index.getTitle();
                if (!newTitle.endsWith("_")) {
                    newTitle = newTitle + "_";
                }
                index.setTitle(newTitle + showDimensionDTO.getCode());
            }
            try {
                DeployTableProcessor.newInstance((DefinitionTableV)tableDefine).deploy();
                logger.info(tableName + "\u6a21\u578b\u53d1\u5e03\u4efb\u52a1\u76d1\u63a7\u5904\u7406\u5b8c\u6210");
            }
            catch (Exception e) {
                logger.error(DataRefUtil.getRefTableName((String)tableName) + "\u8868\u53d1\u5e03\u5931\u8d25!", e);
            }
        }
    }

    private void checkAndAddSourceField(ModelShowDimensionDTO showDimensionDTO) {
        ArrayList codeList;
        Object defaultShowFields;
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setName(showDimensionDTO.getBaseDataCode());
        baseDataDefineDTO.setDeepClone(Boolean.valueOf(true));
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(baseDataDefineDTO);
        Map params = (Map)JsonUtils.readValue((String)baseDataDefineDO.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        ArrayList keyList = CollectionUtils.newArrayList((Object[])new String[]{"DATASCHEMECODE", "ISOLATIONCODE"});
        HashSet keySet = CollectionUtils.newHashSet();
        List showFields = params.getOrDefault("showFields", CollectionUtils.newArrayList());
        if (Objects.nonNull(showFields)) {
            List columnList = showFields.stream().map(column -> MapUtils.getString((Map)column, (Object)"columnName")).collect(Collectors.toList());
            keySet.addAll(columnList);
        }
        if (Objects.nonNull(defaultShowFields = params.get("defaultShowFields"))) {
            List columnList = ((List)defaultShowFields).stream().map(column -> MapUtils.getString((Map)column, (Object)"columnName")).collect(Collectors.toList());
            keySet.addAll(columnList);
        }
        if (!CollectionUtils.isEmpty(codeList = new ArrayList(CollectionUtils.diff((Collection)keyList, (Collection)keySet)))) {
            ArrayList fieldProps = params.getOrDefault("fieldProps", CollectionUtils.newArrayList());
            params.put("fieldProps", fieldProps);
            for (String columnCode : codeList) {
                HashMap columnMap = CollectionUtils.newHashMap();
                columnMap.put("columnName", columnCode);
                columnMap.put("columnTitle", columnCode);
                columnMap.put("columnType", "NVARCHAR");
                columnMap.put("required", false);
                columnMap.put("readonly", false);
                columnMap.put("isQueryColumn", false);
                columnMap.put("unique", false);
                showFields.add(columnMap);
                HashMap propMap = CollectionUtils.newHashMap();
                propMap.put("multiple", false);
                propMap.put("unique", false);
                propMap.put("columnName", columnCode);
                ((List)fieldProps).add(propMap);
            }
            try {
                BeanUtils.copyProperties(baseDataDefineDO, baseDataDefineDTO);
                List columns = params.getOrDefault("columns", BaseDataStorageUtil.getTemplateFields());
                Set columnSet = BaseDataStorageUtil.getTemplateFields().stream().map(DataModelColumn::getColumnName).collect(Collectors.toSet());
                for (Map showFieldDefine : showFields) {
                    if (columnSet.contains(MapUtils.getString((Map)showFieldDefine, (Object)"columnName"))) continue;
                    columnSet.add(MapUtils.getString((Map)showFieldDefine, (Object)"columnName"));
                    columns.add(showFieldDefine);
                    if (showFieldDefine.containsKey("lengths")) continue;
                    showFieldDefine.put("lengths", new Object[]{60});
                }
                params.put("columns", columns);
                baseDataDefineDTO.setDefine(JsonUtils.writeValueAsString((Object)params));
                baseDataDefineDTO.setModifytime(new Date());
                this.baseDataDefineClient.update(baseDataDefineDTO);
            }
            catch (Exception e) {
                logger.error("\u3010{}\u3011\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u65b0\u589e\u5b57\u6bb5\u5931\u8d25", (Object)e, (Object)showDimensionDTO.getBaseDataCode());
            }
        }
    }
}

