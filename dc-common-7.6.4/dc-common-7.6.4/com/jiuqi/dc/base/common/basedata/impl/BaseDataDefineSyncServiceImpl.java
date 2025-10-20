/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.apache.commons.collections4.MapUtils
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.base.common.basedata.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.base.common.basedata.BaseDataDefineSyncService;
import com.jiuqi.dc.base.common.basedata.LowerColumnMapRowMapper;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.feign.client.BaseDataClient;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class BaseDataDefineSyncServiceImpl
implements BaseDataDefineSyncService {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public void baseDataDefineSync() {
        List baseDataDefineList;
        Boolean tablePresent = TableParseUtils.tableExist((String)"jiuqi.gcreport.mdd.datasource", (List)CollectionUtils.newArrayList((Object[])new String[]{"BASEDATA_GROUP", "BASEDATA_DEFINE", "GC_DIMENSION"}));
        if (Boolean.FALSE.equals(tablePresent)) {
            logger.info("\u4e1a\u52a1\u5e93\u4e2d\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u76f8\u5173\u8868({},{},{})\u5b58\u5728\u7f3a\u5931\uff01,\u8df3\u8fc7\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65", "BASEDATA_GROUP", "BASEDATA_DEFINE", "GC_DIMENSION");
            return;
        }
        this.syncBaseDataGroup();
        List baseCodeList = OuterDataSourceUtils.getJdbcTemplate().query("SELECT DISTINCT REFERFIELD FROM GC_DIMENSION WHERE REFERFIELD IS NOT NULL", (RowMapper)new StringRowMapper());
        ArrayList addDefineList = CollectionUtils.newArrayList();
        ArrayList updateDefineList = CollectionUtils.newArrayList();
        if (!CollectionUtils.isEmpty((Collection)baseCodeList) && !CollectionUtils.isEmpty((Collection)(baseDataDefineList = OuterDataSourceUtils.getJdbcTemplate().query("SELECT ID,NAME,TITLE,REMARK,GROUPNAME,CREATOR,MODIFYTIME,ORDERNUM,DUMMYFLAG,DESIGNNAME,STRUCTTYPE,GROUPFIELDNAME,SHARETYPE,SHAREFIELDNAME,LEVELCODE,SHOWTYPE,DIMENSIONFLAG,VERSIONFLAG,AUTHFLAG,ACTAUTHFLAG,INSPECTTITLE,READONLY,SOLIDIFYFLAG,CACHEDISABLED,DEFINE,EXTDATA FROM BASEDATA_DEFINE WHERE " + SqlBuildUtil.getStrInCondi((String)"NAME", (List)baseCodeList), (RowMapper)new BeanPropertyRowMapper(BaseDataDefineDO.class))))) {
            Map<String, BaseDataDefineDO> exisitDefineMap = this.getExisitBaseDataDefineMap(new HashSet<String>(baseCodeList));
            for (BaseDataDefineDO baseDataDefine : baseDataDefineList) {
                if (!exisitDefineMap.containsKey(baseDataDefine.getName())) {
                    BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
                    BeanUtils.copyProperties(baseDataDefine, baseDataDefineDTO);
                    Set columnSet = BaseDataStorageUtil.getTemplateFields().stream().map(DataModelColumn::getColumnName).collect(Collectors.toSet());
                    Map params = (Map)JsonUtils.readValue((String)baseDataDefine.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
                    Map<String, Map<String, Object>> baseDataColumnMap = this.getColumnMap(baseDataDefine);
                    if (!MapUtils.isEmpty(baseDataColumnMap)) {
                        baseDataColumnMap.forEach((columnName, columnDefine) -> {
                            if (!columnSet.contains(columnName)) {
                                ArrayList columns = params.getOrDefault("columns", CollectionUtils.newArrayList());
                                ((List)columns).add(columnDefine);
                                if (!columnDefine.containsKey("lengths")) {
                                    columnDefine.put("lengths", new Object[]{60});
                                }
                                params.put("columns", columns);
                            }
                        });
                    }
                    baseDataDefineDTO.setDefine(JsonUtils.writeValueAsString((Object)params));
                    addDefineList.add(baseDataDefineDTO);
                    continue;
                }
                BaseDataDefineDO exisitDefine = exisitDefineMap.get(baseDataDefine.getName());
                Map<String, Map<String, Object>> exisitColumnMap = this.getColumnMap(exisitDefine);
                Map<String, Map<String, Object>> baseDataColumnMap = this.getColumnMap(baseDataDefine);
                if (MapUtils.isEmpty(baseDataColumnMap)) continue;
                Map params = (Map)JsonUtils.readValue((String)exisitDefine.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
                AtomicBoolean updateFlag = new AtomicBoolean(false);
                baseDataColumnMap.forEach((columnName, columnDefine) -> {
                    if (!exisitColumnMap.containsKey(columnName)) {
                        updateFlag.set(true);
                        ArrayList showFields = params.getOrDefault("showFields", CollectionUtils.newArrayList());
                        columnDefine.put("required", false);
                        ((List)showFields).add(columnDefine);
                        params.put("showFields", showFields);
                        ArrayList fieldProps = params.getOrDefault("fieldProps", CollectionUtils.newArrayList());
                        HashMap propMap = CollectionUtils.newHashMap();
                        propMap.put("multiple", columnDefine.get("multiple"));
                        propMap.put("unique", columnDefine.get("unique"));
                        propMap.put("required", false);
                        propMap.put("columnName", columnName);
                        ((List)fieldProps).add(propMap);
                        params.put("fieldProps", fieldProps);
                    }
                });
                if (!Boolean.TRUE.equals(updateFlag.get())) continue;
                BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
                BeanUtils.copyProperties(exisitDefine, baseDataDefineDTO);
                List columns = params.getOrDefault("columns", BaseDataStorageUtil.getTemplateFields());
                Set columnSet = BaseDataStorageUtil.getTemplateFields().stream().map(DataModelColumn::getColumnName).collect(Collectors.toSet());
                ArrayList showFields = params.getOrDefault("showFields", CollectionUtils.newArrayList());
                for (Map showFieldDefine : (List)showFields) {
                    if (columnSet.contains(MapUtils.getString((Map)showFieldDefine, (Object)"columnName"))) continue;
                    columnSet.add(MapUtils.getString((Map)showFieldDefine, (Object)"columnName"));
                    columns.add(showFieldDefine);
                    if (showFieldDefine.containsKey("lengths")) continue;
                    showFieldDefine.put("lengths", new Object[]{60});
                }
                params.put("columns", columns);
                baseDataDefineDTO.setDefine(JsonUtils.writeValueAsString((Object)params));
                baseDataDefineDTO.setModifytime(new Date());
                updateDefineList.add(baseDataDefineDTO);
            }
        }
        this.saveBaseDataDefine(addDefineList, updateDefineList);
        this.saveBaseData(addDefineList, updateDefineList);
    }

    private void saveBaseDataDefine(List<BaseDataDefineDTO> addDefineList, List<BaseDataDefineDTO> updateDefineList) {
        logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u5f00\u59cb");
        if (!CollectionUtils.isEmpty(addDefineList)) {
            logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u540c\u6b65,\u5f85\u65b0\u589e\u5b9a\u4e49:{}", (Object)addDefineList.stream().map(BaseDataDefineDO::getName).collect(Collectors.joining(",")));
            for (BaseDataDefineDTO addDefine : addDefineList) {
                try {
                    this.baseDataDefineClient.add(addDefine);
                }
                catch (Exception e) {
                    logger.error("\u3010{}\u3011\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u65b0\u589e\u5931\u8d25", (Object)addDefine.getName(), (Object)e);
                }
            }
        }
        if (!CollectionUtils.isEmpty(updateDefineList)) {
            logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u540c\u6b65,\u5f85\u4fee\u6539\u5b9a\u4e49:{}", (Object)updateDefineList.stream().map(BaseDataDefineDO::getName).collect(Collectors.joining(",")));
            for (BaseDataDefineDTO updateDefine : updateDefineList) {
                try {
                    this.baseDataDefineClient.update(updateDefine);
                }
                catch (Exception e) {
                    logger.error("\u3010{}\u3011\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u65b0\u589e\u5b57\u6bb5\u5931\u8d25", (Object)updateDefine.getName(), (Object)e);
                }
            }
        }
        logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u7ed3\u675f");
    }

    private void saveBaseData(List<BaseDataDefineDTO> addDefineList, List<BaseDataDefineDTO> updateDefineList) {
        if (!CollectionUtils.isEmpty(addDefineList)) {
            for (BaseDataDefineDTO addDefine : addDefineList) {
                Map<String, Map<String, Object>> columnMap = this.getColumnMap((BaseDataDefineDO)addDefine);
                List<Map<String, Object>> odsUnConvertDataList = this.getBaseDataList(addDefine.getName(), columnMap.keySet());
                BaseDataBatchOptDTO batchAddDTO = this.getBaseTableInsertData(addDefine.getName(), odsUnConvertDataList);
                if (batchAddDTO.getDataList().size() <= 0) continue;
                try {
                    R r = this.baseDataClient.sync(batchAddDTO);
                    logger.info("{}\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u6570\u636e\u65b0\u589e\u7ed3\u679c:{}", (Object)addDefine.getName(), (Object)r.getMsg());
                }
                catch (Exception e) {
                    logger.error("{}\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u6570\u636e\u65b0\u589e\u5931\u8d25", (Object)addDefine.getName(), (Object)e);
                }
            }
        }
        if (!CollectionUtils.isEmpty(updateDefineList)) {
            for (BaseDataDefineDTO updateDefine : updateDefineList) {
                int count = this.queryExistBaseDataCount(updateDefine.getName());
                if (count > 0) {
                    logger.info("{}\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u6570\u636e\u65b0\u589e\u7ed3\u679c: \u5b58\u5728\u6570\u636e\u8df3\u8fc7\u65b0\u589e", (Object)updateDefine.getName());
                    continue;
                }
                Map<String, Map<String, Object>> columnMap = this.getColumnMap((BaseDataDefineDO)updateDefine);
                List<Map<String, Object>> odsUnConvertDataList = this.getBaseDataList(updateDefine.getName(), columnMap.keySet());
                BaseDataBatchOptDTO batchAddDTO = this.getBaseTableInsertData(updateDefine.getName(), odsUnConvertDataList);
                if (batchAddDTO.getDataList().size() <= 0) continue;
                try {
                    R r = this.baseDataClient.sync(batchAddDTO);
                    logger.info("{}\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u6570\u636e\u65b0\u589e\u7ed3\u679c: {}", (Object)updateDefine.getName(), (Object)r.getMsg());
                }
                catch (Exception e) {
                    logger.error("{}\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u540c\u6b65\u6570\u636e\u65b0\u589e\u5931\u8d25", (Object)updateDefine.getName(), (Object)e);
                }
            }
        }
    }

    private List<Map<String, Object>> getBaseDataList(String tableName, Set<String> keySet) {
        return OuterDataSourceUtils.getJdbcTemplate().query(String.format("SELECT %s FROM %s ", String.join((CharSequence)",", keySet), tableName), (RowMapper)new LowerColumnMapRowMapper(), new Object[0]);
    }

    public BaseDataBatchOptDTO getBaseTableInsertData(String tableName, List<Map<String, Object>> odsUnConvertDataList) {
        BaseDataBatchOptDTO batchAddDto = new BaseDataBatchOptDTO();
        batchAddDto.setTenantName("__default_tenant__");
        batchAddDto.setDataList(new ArrayList());
        for (Map<String, Object> odsUnConvertData : odsUnConvertDataList) {
            BaseDataDTO baseData = new BaseDataDTO();
            baseData.setTableName(tableName);
            baseData.setShortname(StringUtils.isEmpty((String)baseData.getShortname()) ? baseData.getCode() : baseData.getShortname());
            baseData.putAll(odsUnConvertData);
            batchAddDto.getDataList().add(baseData);
        }
        return batchAddDto;
    }

    @NotNull
    private Map<String, Map<String, Object>> getColumnMap(BaseDataDefineDO exisitDefine) {
        Object defaultShowFields;
        Map params = (Map)JsonUtils.readValue((String)exisitDefine.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        HashMap columnMap = CollectionUtils.newHashMap();
        ArrayList showFields = params.getOrDefault("showFields", CollectionUtils.newArrayList());
        if (Objects.nonNull(showFields)) {
            ((List)showFields).forEach(column -> columnMap.put(MapUtils.getString((Map)column, (Object)"columnName"), column));
        }
        if (Objects.nonNull(defaultShowFields = params.get("defaultShowFields"))) {
            ((List)defaultShowFields).forEach(column -> {
                if (!columnMap.containsKey(MapUtils.getString((Map)column, (Object)"columnName"))) {
                    columnMap.put(MapUtils.getString((Map)column, (Object)"columnName"), column);
                }
            });
        }
        return columnMap;
    }

    private void syncBaseDataGroup() {
        logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u540c\u6b65\u5f00\u59cb");
        List bizGroupList = OuterDataSourceUtils.getJdbcTemplate().query("SELECT NAME,TITLE,PARENTNAME FROM BASEDATA_GROUP", (RowMapper)new BeanPropertyRowMapper(BaseDataGroupDO.class));
        Map<String, BaseDataGroupDO> exisitGroupMap = this.getExisitGroupMap();
        HashMap addGroupMap = CollectionUtils.newHashMap();
        HashMap allGroupMap = CollectionUtils.newHashMap();
        if (!CollectionUtils.isEmpty((Collection)bizGroupList)) {
            for (BaseDataGroupDO baseDataGroupDO : bizGroupList) {
                allGroupMap.put(baseDataGroupDO.getName(), baseDataGroupDO);
                if (exisitGroupMap.containsKey(baseDataGroupDO.getName())) continue;
                addGroupMap.put(baseDataGroupDO.getName(), baseDataGroupDO);
            }
        }
        if (!MapUtils.isEmpty((Map)addGroupMap)) {
            logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u540c\u6b65,\u5f85\u65b0\u589e\u5206\u7ec4:{}", (Object)String.join((CharSequence)",", addGroupMap.keySet()));
            for (Map.Entry entry : addGroupMap.entrySet()) {
                if (exisitGroupMap.containsKey(entry.getKey())) continue;
                this.addBaseDataGroup((BaseDataGroupDO)entry.getValue(), addGroupMap);
                exisitGroupMap.put((String)entry.getKey(), (BaseDataGroupDO)entry.getValue());
                BaseDataGroupDO parentGroup = (BaseDataGroupDO)addGroupMap.get(((BaseDataGroupDO)entry.getValue()).getParentname());
                while (Objects.nonNull(parentGroup)) {
                    exisitGroupMap.put(parentGroup.getName(), parentGroup);
                    parentGroup = (BaseDataGroupDO)addGroupMap.get(parentGroup.getParentname());
                }
            }
        }
        logger.info("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4\u540c\u6b65\u7ed3\u675f");
    }

    private void addBaseDataGroup(BaseDataGroupDO baseDataGroup, Map<String, BaseDataGroupDO> addGroupMap) {
        if (addGroupMap.containsKey(baseDataGroup.getParentname())) {
            this.addBaseDataGroup(addGroupMap.get(baseDataGroup.getParentname()), addGroupMap);
        }
        BaseDataGroupDTO param = new BaseDataGroupDTO();
        param.setName(baseDataGroup.getName());
        param.setTitle(baseDataGroup.getTitle());
        param.setParentname(baseDataGroup.getParentname());
        this.baseDataDefineClient.add(param);
    }

    private Map<String, BaseDataGroupDO> getExisitGroupMap() {
        BaseDataGroupDTO condi = new BaseDataGroupDTO();
        condi.setPagination(false);
        List baseDatas = this.baseDataDefineClient.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return CollectionUtils.newHashMap();
        }
        return baseDatas.stream().collect(Collectors.toMap(BaseDataGroupDO::getName, item -> item, (k1, k2) -> k2));
    }

    private Map<String, BaseDataDefineDO> getExisitBaseDataDefineMap(Set<String> codeSet) {
        BaseDataDefineDTO condi = new BaseDataDefineDTO();
        condi.setPagination(false);
        condi.setDeepClone(Boolean.valueOf(true));
        List baseDatas = this.baseDataDefineClient.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return CollectionUtils.newHashMap();
        }
        return baseDatas.stream().filter(e -> codeSet.contains(e.getName())).collect(Collectors.toMap(BaseDataDefineDO::getName, item -> item, (k1, k2) -> k2));
    }

    protected int queryExistBaseDataCount(String tableName) {
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(tableName);
        condi.setPagination(Boolean.valueOf(true));
        condi.setLimit(Integer.valueOf(1));
        condi.setOffset(Integer.valueOf(0));
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO list = this.baseDataClient.list(condi);
        List baseDatas = list.getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return 0;
        }
        return list.getTotal();
    }
}

