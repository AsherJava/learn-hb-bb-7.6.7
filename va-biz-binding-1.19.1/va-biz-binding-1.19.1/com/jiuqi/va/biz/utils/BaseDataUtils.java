/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.va.biz.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.va.biz.intf.ref.RefDataFilter;
import com.jiuqi.va.biz.ruler.common.consts.RefTableFieldProvider;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BaseDataUtils {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataUtils.class);
    private static DataModelClient dataModelClient;
    private static BaseDataDefineClient baseDataDefineClient;

    @Autowired
    private void setDataModelClient(DataModelClient dataModelClient) {
        BaseDataUtils.dataModelClient = dataModelClient;
    }

    @Autowired
    private void setBaseDataDefineClient(BaseDataDefineClient baseDataDefineClient) {
        BaseDataUtils.baseDataDefineClient = baseDataDefineClient;
    }

    public static DataModelDO findBaseDataDefine(String tableName) {
        DataModelDTO param = new DataModelDTO();
        param.setBiztype(DataModelType.BizType.BASEDATA);
        param.setName(tableName);
        param.setTenantName(Env.getTenantName());
        return dataModelClient.get(param);
    }

    public static DataModelColumn findRefObjectColumn(String tableName, String fieldName) throws DynamicNodeException {
        DataModelColumn modelColumn = null;
        try {
            Optional<RefTableFieldProvider.RefTableStructure> optional = RefTableFieldProvider.getRefTableByTableName(tableName).filter(o -> o.getFieldName().equals(fieldName)).findFirst();
            if (optional.equals(Optional.empty())) {
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntablefield") + String.format("%s[%s]", tableName, fieldName));
            }
            modelColumn = new DataModelColumn();
            modelColumn.setColumnName(optional.get().getFieldName());
            modelColumn.setColumnTitle(optional.get().getFieldTitle());
            modelColumn.setColumnType(optional.get().getColumnType());
        }
        catch (DynamicNodeException e) {
            throw e;
        }
        catch (Exception e) {
            DataModelDO modelDO = BaseDataUtils.findBaseDataDefine(tableName);
            if (modelDO == null) {
                BaseDataDefineDO baseDataDefineDO = BaseDataUtils.findVirtualBasedata(tableName);
                if (baseDataDefineDO != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    DataModelDTO convertValue = null;
                    try {
                        convertValue = (DataModelDTO)mapper.readValue(baseDataDefineDO.getDefine(), DataModelDTO.class);
                    }
                    catch (Exception e1) {
                        logger.error(String.format("\u89e3\u6790\u865a\u62df\u57fa\u7840\u6570\u636e[%s]\u5b9a\u4e49\u5f02\u5e38", tableName), e1);
                    }
                    if (convertValue == null) {
                        throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntabledefine") + tableName);
                    }
                    Optional<DataModelColumn> result = convertValue.getColumns().stream().filter(o -> o.getColumnName().equals(fieldName)).findFirst();
                    if (result.equals(Optional.empty())) {
                        throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntablefield") + String.format("%s[%s]", tableName, fieldName));
                    }
                    return result.get();
                }
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntabledefine") + tableName);
            }
            Optional<DataModelColumn> result = modelDO.getColumns().stream().filter(o -> o.getColumnName().equals(fieldName)).findFirst();
            if (result.equals(Optional.empty())) {
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntablefield") + String.format("%s[%s]", tableName, fieldName));
            }
            modelColumn = result.get();
        }
        return modelColumn;
    }

    private static BaseDataDefineDO findVirtualBasedata(String tableName) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(tableName);
        param.setTenantName(Env.getTenantName());
        return baseDataDefineClient.get(param);
    }

    public static List<Map<String, Object>> findRefObject(Map<String, Object> filterMap, Map<String, Map<String, Object>> map) {
        ArrayList<Map<String, Object>> filterResult = new ArrayList<Map<String, Object>>();
        HashMap waitFilterMap = new HashMap();
        filterMap.forEach((k, v) -> waitFilterMap.put(k.toLowerCase(), v));
        for (Map.Entry<String, Map<String, Object>> refTableEntry : map.entrySet()) {
            boolean flag = true;
            for (Map.Entry filterEntry : waitFilterMap.entrySet()) {
                if (filterEntry.getValue().equals(refTableEntry.getValue().get(filterEntry.getKey()))) continue;
                flag = false;
                break;
            }
            if (!flag) continue;
            filterResult.add(refTableEntry.getValue());
        }
        return filterResult;
    }

    public static List<Map<String, Object>> findRefObject(RefDataFilter filterCondition, Map<String, Map<String, Object>> map) {
        Objects.requireNonNull(filterCondition);
        ArrayList<Map<String, Object>> filterResult = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, Map<String, Object>> refTableEntry : map.entrySet()) {
            if (!filterCondition.filter(refTableEntry.getValue())) continue;
            filterResult.add(refTableEntry.getValue());
        }
        return filterResult;
    }

    public static void appendFilterNode(StringBuilder espression, String key, Object value) {
        espression.append("[").append(key.toUpperCase()).append("]");
        espression.append("=");
        if (value instanceof Number || value instanceof BigDecimal) {
            espression.append(value);
        } else if (value instanceof String || value instanceof UUID) {
            espression.append("\"").append(value).append("\"");
        }
    }
}

