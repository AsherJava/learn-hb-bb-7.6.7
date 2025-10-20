/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.StorageFieldConsts
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.gcreport.basedata.impl.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.init.data.VaBaseDataInitData;
import com.jiuqi.gcreport.basedata.impl.init.data.VaBaseDataInitDefine;
import com.jiuqi.gcreport.basedata.impl.init.data.VaBaseDataInitGroup;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.StorageFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class VABaseDataInitService {
    private final Logger logger = LoggerFactory.getLogger(VABaseDataInitService.class);
    @Value(value="classpath*:/vabasedatainit/group/*.group.json")
    private Resource[] groupResources;
    @Value(value="classpath*:/vabasedatainit/define/*.define.json")
    private Resource[] defineResources;
    @Value(value="classpath*:/vabasedatainit/data/*.data.json")
    private Resource[] dataResources;

    public void init() {
        this.initGroups();
        this.initDefines();
        this.initDatas();
    }

    private void initGroups() {
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4...\u542f\u52a8");
        BaseDataDefineClient baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        for (Resource res : this.groupResources) {
            try {
                String value = IOUtils.toString((InputStream)res.getInputStream(), (Charset)StandardCharsets.UTF_8).trim();
                List initGroups = (List)JsonUtils.readValue((String)value, (TypeReference)new TypeReference<List<VaBaseDataInitGroup>>(){});
                if (initGroups == null || initGroups.size() == 0) continue;
                for (VaBaseDataInitGroup initGroup : initGroups) {
                    BaseDataGroupDTO baseDataGroupDTO = this.copyObj(BaseDataGroupDTO.class, initGroup);
                    R existResult = baseDataDefineClient.exist(baseDataGroupDTO);
                    if (existResult.getCode() != 0) {
                        this.logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u67e5\u8be2\u662f\u5426\u5b58\u5728{}\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)initGroup.getTitle(), (Object)existResult.getMsg());
                        continue;
                    }
                    if (existResult.get((Object)"exist") != null && Boolean.valueOf(existResult.get((Object)"exist").toString()).booleanValue()) continue;
                    R addResult = baseDataDefineClient.add(baseDataGroupDTO);
                    if (addResult.getCode() != 0) {
                        this.logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4{}\u65b0\u589e\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)initGroup.getTitle(), (Object)addResult.getMsg());
                        continue;
                    }
                    this.logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u3010{} {}\u3011\u65b0\u589e\u6210\u529f", (Object)initGroup.getName(), (Object)initGroup.getTitle());
                }
            }
            catch (IOException e) {
                this.logger.error(res.getFilename() + "\u8bfb\u53d6\u5f02\u5e38", e);
            }
            catch (Exception e) {
                this.logger.error(res.getFilename() + "\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u521d\u59cb\u5316\u5f02\u5e38", e);
            }
        }
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4...\u5b8c\u6bd5");
    }

    private void initDefines() {
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u5b9a\u4e49...\u542f\u52a8");
        BaseDataDefineClient baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        for (Resource res : this.defineResources) {
            try {
                String value = IOUtils.toString((InputStream)res.getInputStream(), (Charset)StandardCharsets.UTF_8).trim();
                VaBaseDataInitDefine initDefine = (VaBaseDataInitDefine)JsonUtils.readValue((String)value, (TypeReference)new TypeReference<VaBaseDataInitDefine>(){});
                BaseDataDefineDTO baseDataDefineDTO = this.copyObj(BaseDataDefineDTO.class, initDefine);
                baseDataDefineDTO.setModifytime(new Date());
                baseDataDefineDTO.setShowcolumns(initDefine.getColumns());
                BaseDataDefineDO oldDefine = this.getOldBaseDataDefine(baseDataDefineClient, initDefine);
                if (oldDefine == null) {
                    List<Map<String, Object>> defaultShowColumns = this.getDefaultShowColumns(baseDataDefineDTO);
                    if (!CollectionUtils.isEmpty(initDefine.getShowFields())) {
                        HashSet<String> columnSet = new HashSet<String>();
                        for (Map<String, Object> map : defaultShowColumns) {
                            columnSet.add(String.valueOf(map.get("columnName")));
                        }
                        for (Map<String, Object> showField : initDefine.getShowFields()) {
                            if (columnSet.contains(showField.get("columnName"))) continue;
                            defaultShowColumns.add(showField);
                        }
                    }
                    baseDataDefineDTO.setDefaultShowColumns(defaultShowColumns);
                    ObjectNode initDefineObj = (ObjectNode)JsonUtils.readTree((String)value);
                    ObjectNode defineObj = (ObjectNode)JsonUtils.readTree((String)baseDataDefineDTO.getDefine());
                    defineObj.set("fieldProps", initDefineObj.get("showFields"));
                    if (initDefine.getDummyflag() != null && initDefine.getDummyflag() == 1 && !StringUtils.isEmpty((String)initDefine.getSqldefine())) {
                        defineObj.put("dummyObj", 1);
                        defineObj.put("sqlDefine", initDefine.getSqldefine());
                        if (!defineObj.hasNonNull("columns")) {
                            ArrayList<Map> columns = new ArrayList<Map>();
                            columns.add(BaseDataStorageUtil.getColumnMap((String)"ID", (String)StorageFieldConsts.getFtID(), (String)DataModelType.ColumnType.NVARCHAR.name()));
                            columns.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)StorageFieldConsts.getFtCode(), (String)DataModelType.ColumnType.NVARCHAR.name()));
                            columns.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)StorageFieldConsts.getFtName(), (String)DataModelType.ColumnType.NVARCHAR.name()));
                            defineObj.set("columns", (JsonNode)((ArrayNode)JsonUtils.readTree((String)JsonUtils.writeValueAsString(columns))));
                        }
                    }
                    baseDataDefineDTO.setDefine(JsonUtils.writeValueAsString((Object)defineObj));
                    R r = baseDataDefineClient.add(baseDataDefineDTO);
                    if (r.getCode() != 0) {
                        this.logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u65b0\u589e\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", initDefine.getName(), initDefine.getTitle(), r.getMsg());
                    } else {
                        this.logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u65b0\u589e\u6210\u529f", (Object)initDefine.getName(), (Object)initDefine.getTitle());
                    }
                } else {
                    boolean updateFlag = false;
                    if (!CollectionUtils.isEmpty(initDefine.getShowFields())) {
                        ObjectNode defineObj = (ObjectNode)JsonUtils.readTree((String)oldDefine.getDefine());
                        JsonNode showFieldsNode = defineObj.get("showFields");
                        if (Objects.isNull(showFieldsNode)) {
                            showFieldsNode = defineObj.get("defaultShowFields");
                        }
                        ArrayNode showFields = showFieldsNode instanceof ArrayNode ? (ArrayNode)showFieldsNode : (ArrayNode)JsonUtils.readTree((String)showFieldsNode.textValue());
                        HashSet columnSet = new HashSet();
                        for (JsonNode node : showFields) {
                            columnSet.add(Optional.ofNullable(node.get("columnName")).map(JsonNode::textValue).orElse(null));
                        }
                        for (Map<String, Object> showField : initDefine.getShowFields()) {
                            if (columnSet.contains(showField.get("columnName"))) continue;
                            showFields.add(JsonUtils.readTree((String)JsonUtils.writeValueAsString(showField)));
                            updateFlag = true;
                        }
                        defineObj.set("showFields", (JsonNode)showFields);
                        defineObj.set("fieldProps", (JsonNode)showFields);
                        baseDataDefineDTO.setDefine(updateFlag ? JsonUtils.writeValueAsString((Object)defineObj) : oldDefine.getDefine());
                    } else {
                        baseDataDefineDTO.setDefine(oldDefine.getDefine());
                    }
                    if (this.compareFields(baseDataDefineDTO, oldDefine)) {
                        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
                        BaseDataDTO baseDataCondi = new BaseDataDTO();
                        baseDataCondi.setTableName(baseDataDefineDTO.getName());
                        baseDataCondi.setAuthType(BaseDataOption.AuthType.NONE);
                        baseDataCondi.setPagination(Boolean.valueOf(true));
                        baseDataCondi.setOffset(Integer.valueOf(0));
                        baseDataCondi.setLimit(Integer.valueOf(10));
                        PageVO pageVO = baseDataClient.list(baseDataCondi);
                        if (pageVO != null && pageVO.getTotal() > 0) {
                            this.logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u56e0\u5df2\u5b58\u5728\u6570\u636e\uff0c\u5c5e\u6027\u66f4\u65b0\u5931\u8d25", (Object)initDefine.getName(), (Object)initDefine.getTitle());
                            this.resetFields(baseDataDefineDTO, oldDefine);
                        } else {
                            updateFlag = true;
                        }
                    }
                    if (updateFlag) {
                        R r = baseDataDefineClient.update(baseDataDefineDTO);
                        if (r.getCode() != 0) {
                            this.logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u66f4\u65b0\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", initDefine.getName(), initDefine.getTitle(), r.getMsg());
                            continue;
                        }
                        this.logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u66f4\u65b0\u6210\u529f", (Object)initDefine.getName(), (Object)initDefine.getTitle());
                    }
                }
                DataModelDO convertDataModelDO = this.convertDataModelDO(dataModelClient, initDefine);
                if (convertDataModelDO == null || CollectionUtils.isEmpty((Collection)convertDataModelDO.getColumns())) continue;
                R pushResult = dataModelClient.pushIncrement(convertDataModelDO);
                if (pushResult.getCode() != 0) {
                    this.logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u6269\u5c55\u5b57\u6bb5\u66f4\u65b0\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", initDefine.getName(), initDefine.getTitle(), pushResult.getMsg());
                    continue;
                }
                this.logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{} {}\u3011\u6269\u5c55\u5b57\u6bb5\u66f4\u65b0\u6210\u529f", (Object)initDefine.getName(), (Object)initDefine.getTitle());
            }
            catch (IOException e) {
                this.logger.error(res.getFilename() + "\u8bfb\u53d6\u5f02\u5e38", e);
            }
            catch (Exception e) {
                this.logger.error(res.getFilename() + "\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u521d\u59cb\u5316\u5f02\u5e38", e);
            }
        }
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u5b9a\u4e49...\u5b8c\u6bd5");
    }

    private boolean compareFields(BaseDataDefineDTO baseDataDTO, BaseDataDefineDO oldDefine) {
        return !oldDefine.getTitle().equals(baseDataDTO.getTitle()) || baseDataDTO.getStructtype() != null && baseDataDTO.getStructtype() != oldDefine.getStructtype() || baseDataDTO.getSharetype() != null && baseDataDTO.getSharetype() != oldDefine.getSharetype() || !StringUtils.isEmpty((String)baseDataDTO.getLevelcode()) && !baseDataDTO.getLevelcode().equals(oldDefine.getLevelcode()) || !StringUtils.isEmpty((String)baseDataDTO.getGroupfieldname()) && !baseDataDTO.getGroupfieldname().equals(oldDefine.getGroupfieldname()) || !StringUtils.isEmpty((String)baseDataDTO.getSharefieldname()) && !baseDataDTO.getSharefieldname().equals(oldDefine.getSharefieldname());
    }

    private void resetFields(BaseDataDefineDTO baseDataDefineDTO, BaseDataDefineDO oldDefine) {
        baseDataDefineDTO.setTitle(oldDefine.getTitle());
        baseDataDefineDTO.setStructtype(oldDefine.getStructtype());
        baseDataDefineDTO.setSharetype(oldDefine.getSharetype());
        baseDataDefineDTO.setLevelcode(oldDefine.getLevelcode());
        baseDataDefineDTO.setGroupfieldname(oldDefine.getGroupfieldname());
        baseDataDefineDTO.setSharefieldname(oldDefine.getSharefieldname());
    }

    private BaseDataDefineDO getOldBaseDataDefine(BaseDataDefineClient baseDataDefineClient, VaBaseDataInitDefine initDefine) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setTenantName("__default_tenant__");
        param.setName(initDefine.getName());
        BaseDataDefineDO oldDefine = baseDataDefineClient.get(param);
        return oldDefine;
    }

    private List<Map<String, Object>> getDefaultShowColumns(BaseDataDefineDTO baseDataDefineDTO) {
        ArrayList<Map<String, Object>> defaultShowColumns = new ArrayList<Map<String, Object>>();
        defaultShowColumns.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)StorageFieldConsts.getFtCode(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)true));
        defaultShowColumns.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)StorageFieldConsts.getFtName(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        defaultShowColumns.add(BaseDataStorageUtil.getColumnMap((String)"SHORTNAME", (String)StorageFieldConsts.getFtShortName(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        if (baseDataDefineDTO.getStructtype() == 2 || baseDataDefineDTO.getStructtype() == 3) {
            defaultShowColumns.add(BaseDataStorageUtil.getColumnMap((String)"PARENTCODE", (String)StorageFieldConsts.getFtParentCode(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)false, (Boolean)false));
        }
        return defaultShowColumns;
    }

    private void initDatas() {
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u9884\u7f6e\u6570\u636e...\u542f\u52a8");
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        for (Resource res : this.dataResources) {
            try {
                this.logger.info("\u5f00\u59cb\u3010{}\u3011\u7684\u9884\u7f6e\u6570\u636e\u521d\u59cb\u5316", (Object)res.getFilename());
                String value = IOUtils.toString((InputStream)res.getInputStream(), (Charset)StandardCharsets.UTF_8).trim();
                VaBaseDataInitData initData = (VaBaseDataInitData)JsonUtils.readValue((String)value, (TypeReference)new TypeReference<VaBaseDataInitData>(){});
                String tableName = initData.getTableName();
                BaseDataDTO baseDataCondi = new BaseDataDTO();
                baseDataCondi.setTableName(tableName);
                baseDataCondi.setAuthType(BaseDataOption.AuthType.NONE);
                baseDataCondi.setPagination(Boolean.valueOf(false));
                PageVO pageVO = baseDataClient.list(baseDataCondi);
                if (pageVO.getRs().getCode() != 0) {
                    this.logger.error("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u67e5\u8be2\u6570\u636e\u5217\u8868\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)tableName, (Object)pageVO.getRs().getMsg());
                    continue;
                }
                if (!initData.isAllowDataExists() && pageVO.getTotal() > 0) {
                    this.logger.info("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u8868\u5df2\u5b58\u5728\u6570\u636e\uff0c\u4e0d\u6267\u884c\u9884\u7f6e\u6570\u636e\u521d\u59cb\u5316", (Object)tableName);
                    continue;
                }
                String rowKey = StringUtils.isEmpty((String)initData.getCheckFieldNames()) ? "CODE" : initData.getCheckFieldNames().trim();
                Map<String, BaseDataDO> baseDatasMap = pageVO.getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
                BaseDataDTO basedataDTO = null;
                R r = null;
                for (Map<String, Object> rowData : initData.getRowDatas()) {
                    if (baseDatasMap.containsKey(rowData.get(rowKey))) continue;
                    basedataDTO = new BaseDataDTO();
                    basedataDTO.setTableName(tableName);
                    for (Map.Entry<String, Object> entry : rowData.entrySet()) {
                        basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
                    }
                    basedataDTO.setCode(rowData.get(rowKey).toString());
                    r = baseDataClient.add(basedataDTO);
                    if (r.getCode() != 0) {
                        this.logger.error("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u3010{} {}\u3011\u521d\u59cb\u5316\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", tableName, basedataDTO.getCode(), basedataDTO.getName(), r.getMsg());
                        continue;
                    }
                    this.logger.info("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u3010{} {}\u3011\u521d\u59cb\u5316\u6210\u529f", tableName, basedataDTO.getCode(), basedataDTO.getName());
                }
            }
            catch (IOException e) {
                this.logger.error(res.getFilename() + "\u8bfb\u53d6\u5f02\u5e38", e);
            }
            catch (Exception e) {
                this.logger.error(res.getFilename() + "\u57fa\u7840\u6570\u636e\u9884\u7f6e\u6570\u636e\u521d\u59cb\u5316\u5f02\u5e38", e);
            }
        }
        this.logger.info("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u9884\u7f6e\u6570\u636e...\u5b8c\u6bd5");
    }

    private DataModelDO convertDataModelDO(DataModelClient dataModelClient, VaBaseDataInitDefine initDefine) {
        if (CollectionUtils.isEmpty(initDefine.getColumns())) {
            return null;
        }
        DataModelDTO dataModelDTOCondi = new DataModelDTO();
        dataModelDTOCondi.setName(initDefine.getName());
        dataModelDTOCondi.setDeepClone(Boolean.valueOf(false));
        DataModelDO oldDataModelDO = dataModelClient.get(dataModelDTOCondi);
        HashSet<String> oldColumnSet = new HashSet<String>();
        if (oldDataModelDO != null && !CollectionUtils.isEmpty((Collection)oldDataModelDO.getColumns())) {
            for (DataModelColumn column : oldDataModelDO.getColumns()) {
                oldColumnSet.add(column.getColumnName());
            }
        }
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        for (DataModelColumn column : initDefine.getColumns()) {
            if (oldColumnSet.contains(column.getColumnName())) continue;
            columns.add(column);
        }
        if (CollectionUtils.isEmpty(columns)) {
            return null;
        }
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setName(initDefine.getName());
        dataModelDO.setTitle(initDefine.getTitle());
        dataModelDO.setGroupcode(initDefine.getGroupname());
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setTenantName("__default_tenant__");
        dataModelDO.setColumns(columns);
        return dataModelDO;
    }

    private <S, T> T copyObj(Class<T> targetClazz, S source) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

