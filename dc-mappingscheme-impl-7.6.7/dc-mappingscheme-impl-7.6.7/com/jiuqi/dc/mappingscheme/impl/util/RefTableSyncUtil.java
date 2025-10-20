/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefTableSyncUtil {
    public static final String REF_TABLE_TEMPLATE_NAME = "REF_";
    public static final Integer FN_DEFAULT_VALUELENGTH = 60;
    private static Logger logger = LoggerFactory.getLogger(RefTableSyncUtil.class);

    private RefTableSyncUtil() {
    }

    public static void syncTable(String tableName) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity entity = entityTableCollector.getEntityByName(REF_TABLE_TEMPLATE_NAME);
        TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
        DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
        tableDefine.setCode(DataRefUtil.getRefTableName((String)tableName));
        tableDefine.setTableName(DataRefUtil.getRefTableName((String)tableName));
        try {
            DeployTableProcessor.newInstance((DefinitionTableV)tableDefine).deploy();
        }
        catch (Exception e) {
            logger.error(DataRefUtil.getRefTableName((String)tableName) + "\u8868\u53d1\u5e03\u5931\u8d25!", e);
        }
    }

    public static List<String> getExtColumnNameListByRuleType(String ruleType, String tableName) {
        List isolationDim;
        ArrayList resultList = CollectionUtils.newArrayList();
        if (Objects.equals("MD_ORG", tableName)) {
            resultList.add(DataRefUtil.getOdsPrefixName((String)"ASSISTCODE"));
            resultList.add(DataRefUtil.getOdsPrefixName((String)"ASSISTNAME"));
        }
        if (!CollectionUtils.isEmpty((Collection)(isolationDim = (List)Optional.ofNullable(((IRuleTypeGather)ApplicationContextRegister.getBean(IRuleTypeGather.class)).getRuleTypeByCode(ruleType)).map(IRuleType::isolationDim).map(e -> e.stream().map(SelectOptionVO::getCode).collect(Collectors.toList())).orElse(null)))) {
            isolationDim.forEach(e -> resultList.add(DataRefUtil.getOdsPrefixName((String)e.toUpperCase())));
        }
        return resultList;
    }

    public static void syncSrcField() {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        ShardingBaseEntity entity = (ShardingBaseEntity)entityTableCollector.getEntityByName("DC_VOUCHERITEMASS");
        if (Objects.isNull(entity)) {
            return;
        }
        List yearList = entity.getShardingList();
        if (CollectionUtils.isEmpty((Collection)yearList)) {
            return;
        }
        for (String year : yearList) {
            TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
            DefinitionTableV define = tableDefineConvertHelper.convert();
            String tableName = entity.getTableNamePrefix() + "_" + year;
            define.setCode(tableName);
            define.setTableName(tableName);
            define.getFields().addAll(((ITableExtend)entity).getExtendFieldList(null));
            try {
                DeployTableProcessor.newInstance((DefinitionTableV)define).deploy();
            }
            catch (Exception e) {
                logger.error("\u8868\u7ed3\u6784\u521d\u59cb\u5316" + define.getTableName() + "\u5931\u8d25");
            }
        }
    }
}

