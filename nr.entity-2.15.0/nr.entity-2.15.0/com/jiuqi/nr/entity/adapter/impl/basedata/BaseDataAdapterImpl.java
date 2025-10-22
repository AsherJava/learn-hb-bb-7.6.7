/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO
 *  com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  org.json.JSONObject
 */
package com.jiuqi.nr.entity.adapter.impl.basedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.adapter.executor.IExtendDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.JsonUtils;
import com.jiuqi.nr.entity.adapter.impl.basedata.IExtBaseDataDefineImport;
import com.jiuqi.nr.entity.adapter.impl.basedata.auth.BaseDataAuthTypeExtendImpl;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.DefaultBaseDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.DefineQueryHelper;
import com.jiuqi.nr.entity.adapter.impl.basedata.exception.BaseDataException;
import com.jiuqi.nr.entity.adapter.provider.DefineDTO;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.internal.model.impl.EntityGroupImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityModelImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO;
import com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service(value="baseDataAdapterImpl")
public class BaseDataAdapterImpl
implements IEntityAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataAdapterImpl.class);
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private VaBaseDataAuthService vaBaseDataAuthService;
    @Autowired
    @Qualifier(value="defaultBaseDataExecutor")
    private DefaultBaseDataExecutor baseDataExecutor;
    @Autowired(required=false)
    private IExtBaseDataDefineImport extBaseDataDefineImport;
    @Autowired
    @Qualifier(value="entityBaseDataAuthProvider")
    private IEntityAuthProvider entityAuthProvider;
    private List<IExtendDataExecutor<BaseDataAdapterImpl>> extendDataExecutors;
    private final ObjectMapper mapper = new ObjectMapper();
    public static final String I18N_CODE = "localizedName";
    private static final String EXPORT_GROUP = "NR";
    private static final String EXPORT_GROUP_NAME = "\u62a5\u8868\u5206\u7ec4";

    @Autowired
    public void setExtendDataExecutors(List<IExtendDataExecutor<BaseDataAdapterImpl>> extendDataExecutors) {
        extendDataExecutors.sort(Comparator.comparing(IExtendDataExecutor::getOrder));
        this.extendDataExecutors = extendDataExecutors;
    }

    @Override
    public List<IEntityGroup> getRootEntityGroups() {
        return this.getChildrenEntityGroups("-");
    }

    private IEntityGroup convertBg2Eg(BaseDataGroupDO baseDataGroup) {
        if (baseDataGroup == null) {
            return null;
        }
        EntityGroupImpl entityGroup = new EntityGroupImpl();
        entityGroup.setId(baseDataGroup.getName());
        entityGroup.setTitle(baseDataGroup.getTitle());
        entityGroup.setParentId(baseDataGroup.getParentname());
        return entityGroup;
    }

    @Override
    public IEntityGroup queryEntityGroup(String groupName) {
        BaseDataGroupDTO filter = new BaseDataGroupDTO();
        filter.setName(groupName);
        return this.convertBg2Eg(this.baseDataDefineClient.get(filter));
    }

    @Override
    public List<IEntityGroup> getChildrenEntityGroups(String groupName) {
        BaseDataGroupDTO filter = new BaseDataGroupDTO();
        filter.setParentname(groupName);
        PageVO rootGroupPage = this.baseDataDefineClient.list(filter);
        if (rootGroupPage.getTotal() == 0) {
            return Collections.emptyList();
        }
        return rootGroupPage.getRows().stream().map(this::convertBg2Eg).collect(Collectors.toList());
    }

    @Override
    public List<IEntityDefine> getEntitiesInGroup(String groupName) {
        DefineDTO dto = new DefineDTO();
        dto.setGroup(groupName);
        return this.getDefineProvider().query(dto);
    }

    private Predicate<IEntityDefine> cumulativeFilter(Integer cumulative) {
        return baseDataDefine -> {
            if (Objects.isNull(baseDataDefine)) {
                return false;
            }
            if (cumulative == null) {
                return true;
            }
            int flag = baseDataDefine.getDimensionFlag() == null ? 0 : baseDataDefine.getDimensionFlag();
            return cumulative.equals(flag);
        };
    }

    @Override
    public List<IEntityDefine> getEntities(int dimensionFlag) {
        return this.getDefineProvider().query(null).stream().filter(this.cumulativeFilter(dimensionFlag)).collect(Collectors.toList());
    }

    @Override
    public IEntityDefine getEntity(String tableName) {
        return this.getDefineProvider().get(tableName);
    }

    @Override
    public IEntityDefine getEntityByCode(String subTreeEntityCode) {
        return this.getEntity(subTreeEntityCode);
    }

    @Override
    public IEntityModel getEntityModel(String tableName) {
        if (ObjectUtils.isEmpty(tableName)) {
            return null;
        }
        DataModelDO dataModel = this.getDefineProvider().getModel(tableName);
        if (dataModel == null) {
            return null;
        }
        Map<String, IEntityAttribute> attributesMap = this.getDefineProvider().getAttributes(tableName);
        ArrayList<IEntityAttribute> attributes = new ArrayList<IEntityAttribute>(attributesMap.values());
        EntityModelImpl entityModel = new EntityModelImpl();
        entityModel.setEntityId(tableName);
        entityModel.setEntityAttributes(attributes);
        entityModel.setBizKeyField(attributesMap.get("OBJECTCODE"));
        entityModel.setBblxField(attributesMap.get("BBLX"));
        entityModel.setRecKeyField(DefineQueryHelper.getKeyColumn(dataModel, attributesMap));
        entityModel.setShowFields(DefineQueryHelper.getShowFields(attributes));
        entityModel.setNameField(attributesMap.get("NAME"));
        entityModel.setCodeField(attributesMap.get("CODE"));
        entityModel.setParentField(attributesMap.get("PARENTCODE"));
        entityModel.setOrderField(attributesMap.get("ORDINAL"));
        entityModel.setIconField(attributesMap.get("ICON"));
        entityModel.setBeginDateField(attributesMap.get("VALIDTIME"));
        entityModel.setEndDateField(attributesMap.get("INVALIDTIME"));
        entityModel.setStoppedField(attributesMap.get("STOPFLAG"));
        entityModel.putI18nCode("NAME", I18N_CODE);
        return entityModel;
    }

    @Override
    public List<IEntityRefer> getEntityRefer(String tableName) {
        if (!StringUtils.hasText(tableName)) {
            return Collections.emptyList();
        }
        DataModelDO dataModel = this.getDefineProvider().getModel(tableName);
        if (dataModel == null || CollectionUtils.isEmpty(dataModel.getColumns())) {
            return Collections.emptyList();
        }
        return dataModel.getColumns().stream().filter(column -> {
            boolean referBaseDataOrOrg;
            boolean valid;
            boolean bl = valid = StringUtils.hasText(column.getMapping()) && column.getMapping().split("\\.").length == 2 && column.getMappingType() != null;
            if (!valid) {
                return false;
            }
            boolean bl2 = referBaseDataOrOrg = column.getMappingType() == 1 || column.getMappingType() == 4;
            if (!referBaseDataOrOrg) {
                return false;
            }
            return !column.getMapping().split("\\.")[1].equals(tableName);
        }).map(columnModelDefine -> this.getEntityReferByRelatedField((DataModelColumn)columnModelDefine, tableName)).collect(Collectors.toList());
    }

    private IEntityRefer getEntityReferByRelatedField(DataModelColumn dataModelColumn, String tableName) {
        EntityReferImpl entityRefer = new EntityReferImpl();
        entityRefer.setOwnField(dataModelColumn.getColumnName());
        entityRefer.setOwnEntityId(tableName);
        String[] mapping = dataModelColumn.getMapping().split("\\.");
        entityRefer.setReferEntityId(mapping[0]);
        entityRefer.setReferEntityField(mapping[1]);
        return entityRefer;
    }

    @Override
    public List<String> getPath(String tableName) {
        if (!StringUtils.hasText(tableName)) {
            return Collections.emptyList();
        }
        IEntityDefine entityDefine = this.getDefineProvider().get(tableName);
        if (entityDefine == null) {
            return Collections.emptyList();
        }
        ArrayList<String> path = new ArrayList<String>();
        path.add(entityDefine.getGroup());
        String groupName = entityDefine.getGroup();
        BaseDataGroupDTO groupFilter = new BaseDataGroupDTO();
        while (groupName != null) {
            groupFilter.setName(groupName);
            BaseDataGroupDO baseDataGroup = this.baseDataDefineClient.get(groupFilter);
            if (baseDataGroup == null || "-".equals(baseDataGroup.getParentname())) break;
            groupName = baseDataGroup.getParentname();
            path.add(groupName);
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public List<IEntityDefine> fuzzySearchEntityByKeyWords(EntitySearchBO bo) {
        DefineDTO dto = new DefineDTO();
        dto.setKeyWords(bo.getKeyWords());
        return this.getDefineProvider().query(dto).stream().filter(this.cumulativeFilter(bo.getDimensionFlag())).filter(e -> {
            if (bo.getCalibre() == null) {
                return true;
            }
            return bo.getCalibre() != 0;
        }).collect(Collectors.toList());
    }

    @Override
    public TableModelDefine getTableByEntityId(String tableName) {
        return this.getDefineProvider().getNvwaModel(tableName);
    }

    @Override
    public List<IEntityDefine> getSubTreeEntities(String tableName) {
        ArrayList<IEntityDefine> orgTypeEntitys = new ArrayList<IEntityDefine>();
        orgTypeEntitys.add(this.getDefineProvider().get(tableName));
        return orgTypeEntitys;
    }

    @Override
    public IEntityDefine getBaseEntityBySubTreeEntityId(String subTreeEntityId) {
        return this.getDefineProvider().get(subTreeEntityId);
    }

    private IDefineQueryProvider getDefineProvider() {
        return this.getExecutor(null, ProviderMethodEnum.DEFINE_QUERY).getDefineProvider();
    }

    private IExtendDataExecutor getExecutor(IEntityQueryParam query, ProviderMethodEnum providerEnum) {
        for (IExtendDataExecutor<BaseDataAdapterImpl> executor : this.extendDataExecutors) {
            if (!executor.isEnable(query, providerEnum)) continue;
            return executor;
        }
        return this.baseDataExecutor;
    }

    private IDataQueryProvider getDataQueryProvider(IEntityQueryParam query) {
        return this.getExecutor(query, ProviderMethodEnum.DATA_QUERY).getDataQueryProvider(query);
    }

    @Override
    public EntityResultSet getAllRows(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).getAllData();
    }

    @Override
    public EntityResultSet getRootRows(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).getRootData();
    }

    @Override
    public EntityResultSet getChildRows(IEntityQueryParam query, String ... entityKeyDatas) {
        return this.getDataQueryProvider(query).getChildData(QueryOptions.TreeType.DIRECT_CHILDREN, entityKeyDatas);
    }

    @Override
    public EntityResultSet getAllChildRows(IEntityQueryParam query, String entityKeyData) {
        return this.getDataQueryProvider(query).getChildData(QueryOptions.TreeType.ALL_CHILDREN, entityKeyData);
    }

    @Override
    public EntityResultSet findByEntityKeys(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).findByEntityKeys();
    }

    @Override
    public String getParent(IEntityQueryParam query, String queryCode) {
        return this.getDataQueryProvider(query).getParent(queryCode);
    }

    @Override
    public String getParent(IEntityQueryParam query, Map<String, Object> rowData) {
        return this.getDataQueryProvider(query).getParent(rowData);
    }

    @Override
    public int getMaxDepth(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).getMaxDepth();
    }

    @Override
    public int getMaxDepthByEntityKey(IEntityQueryParam query, String entityKeyData) {
        return this.getDataQueryProvider(query).getMaxDepthByEntityKey(entityKeyData);
    }

    @Override
    public EntityResultSet findByCode(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).getAllData();
    }

    @Override
    public int getDirectChildCount(IEntityQueryParam query, String entityKeyData) {
        return this.getDataQueryProvider(query).getChildCount(entityKeyData, QueryOptions.TreeType.DIRECT_CHILDREN);
    }

    @Override
    public int getAllChildCount(IEntityQueryParam query, String entityKeyData) {
        return this.getDataQueryProvider(query).getChildCount(entityKeyData, QueryOptions.TreeType.ALL_CHILDREN);
    }

    @Override
    public Map<String, Integer> getDirectChildCountByParent(IEntityQueryParam query, String parentKey) {
        return this.getDataQueryProvider(query).getChildCountByParent(parentKey, QueryOptions.TreeType.DIRECT_CHILDREN);
    }

    @Override
    public Map<String, Integer> getAllChildCountByParent(IEntityQueryParam query, String parentKey) {
        return this.getDataQueryProvider(query).getChildCountByParent(parentKey, QueryOptions.TreeType.ALL_CHILDREN);
    }

    @Override
    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, String entityKeyData) {
        return this.getDataQueryProvider(query).getParentsEntityKeyDataPath(entityKeyData);
    }

    @Override
    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, Map<String, Object> rowData) {
        return this.getDataQueryProvider(query).getParentsEntityKeyDataPath(rowData);
    }

    @Override
    public int getTotalCount(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).getTotalCount();
    }

    @Override
    public EntityUpdateResult insertRows(IEntityUpdateParam updateParam) throws EntityUpdateException {
        return this.getExecutor(updateParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().insertRows(updateParam);
    }

    @Override
    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws EntityUpdateException {
        return this.getExecutor(deleteParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().deleteRows(deleteParam);
    }

    @Override
    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws EntityUpdateException {
        return this.getExecutor(updateParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().updateRows(updateParam);
    }

    @Override
    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) {
        return this.getExecutor(updateParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().rowsCheck(updateParam, insert);
    }

    @Override
    public EntityResultSet simpleQuery(IEntityQueryParam query) {
        return this.getDataQueryProvider(query).simpleQueryByKeys();
    }

    @Override
    public void importEntityData(String entityId, InputStream inputStream) {
        if (inputStream == null || ObjectUtils.isEmpty(entityId)) {
            return;
        }
        List baseDataDO = new ArrayList();
        try {
            if (inputStream.available() == 0) {
                return;
            }
            baseDataDO = (List)this.mapper.readValue(inputStream, (TypeReference)new TypeReference<List<BaseDataDO>>(){});
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (!CollectionUtils.isEmpty(baseDataDO)) {
            BaseDataDTO param = new BaseDataDTO();
            param.setTableName(entityId);
            BaseDataBatchOptDTO batchDTO = new BaseDataBatchOptDTO();
            batchDTO.setDataList(baseDataDO);
            batchDTO.setQueryParam(param);
            batchDTO.setHighTrustability(true);
            batchDTO.setTableCover(true);
            this.baseDataClient.sync(batchDTO);
        }
    }

    @Override
    public void exportEntityData(String entityId, OutputStream outputStream) {
        if (ObjectUtils.isEmpty(entityId)) {
            return;
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(entityId);
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        param.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO list = this.baseDataClient.list(param);
        if (list.getTotal() > 0) {
            String serialValue;
            List rows = list.getRows();
            try {
                serialValue = this.mapper.writeValueAsString((Object)rows);
            }
            catch (JsonProcessingException e) {
                throw new BaseDataException(String.format("\u5e8f\u5217\u5316\u57fa\u7840\u6570\u636e%s\u65f6\u53d1\u751f\u9519\u8bef", entityId), e);
            }
            if (StringUtils.hasText(serialValue)) {
                try {
                    outputStream.write(serialValue.getBytes(StandardCharsets.UTF_8));
                }
                catch (IOException e) {
                    throw new BaseDataException(String.format("\u5199\u5165\u6d41\u6570\u636e\u65f6\u57fa\u7840\u6570\u636e%s\u53d1\u751f\u9519\u8bef", entityId), e);
                }
            }
        }
    }

    @Override
    public void exportEntityDefine(String tableName, OutputStream outputStream) {
        if (outputStream == null || ObjectUtils.isEmpty(tableName)) {
            return;
        }
        DataModelDTO dataModelFilter = new DataModelDTO();
        dataModelFilter.setName(tableName);
        DataModelDO dataModel = this.dataModelClient.get(dataModelFilter);
        if (dataModel == null) {
            throw new RuntimeException(String.format("\u672a\u627e\u5230\u6807\u8bc6\u4e3a%s\u7684\u57fa\u7840\u6570\u636e\u5b9a\u4e49", tableName));
        }
        BaseDataDefineDTO filter = new BaseDataDefineDTO();
        filter.setName(tableName);
        BaseDataDefineDO baseDataDefine = this.baseDataDefineClient.get(filter);
        if (baseDataDefine == null) {
            throw new RuntimeException(String.format("\u672a\u627e\u5230\u6807\u8bc6\u4e3a%s\u7684\u57fa\u7840\u6570\u636e\u5efa\u6a21", tableName));
        }
        String groupname = baseDataDefine.getGroupname();
        BaseDataGroupDTO groupDTO = new BaseDataGroupDTO();
        groupDTO.setName(groupname);
        BaseDataGroupDO baseDataGroupDO = this.baseDataDefineClient.get(groupDTO);
        EntityDefineExportData extityDefineExportData = new EntityDefineExportData();
        extityDefineExportData.dataModel = dataModel;
        extityDefineExportData.baseDataDefine = baseDataDefine;
        if (baseDataGroupDO != null) {
            extityDefineExportData.groupDO = baseDataGroupDO;
        }
        try {
            outputStream.write(JsonUtils.writeValueAsString(extityDefineExportData).getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            throw new RuntimeException("\u8868\u3010" + baseDataDefine.getName() + "\u3011\u5b9a\u4e49\u5bfc\u51fa\u5f02\u5e38", e);
        }
    }

    @Override
    public IEntityDefine importEntityDefine(InputStream inputStream) {
        Object baseDataDefineExt;
        R importResult;
        EntityDefineExportData baseAndModelDefine;
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int r;
            try {
                r = inputStream.read();
                if (r == -1) {
                    break;
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            byteArrayOutputStream.write(r);
        }
        String json = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        JacksonUtils.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            baseAndModelDefine = (EntityDefineExportData)JacksonUtils.mapper.readValue(json, EntityDefineExportData.class);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        this.insertBaseDataGroup(baseAndModelDefine.groupDO);
        BaseDataDefineDO baseDataDefine = baseAndModelDefine.baseDataDefine;
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        BeanUtils.copyProperties(baseDataDefine, baseDataDefineDTO);
        baseDataDefineDTO.setModifytime(new Date());
        BaseDataDefineDTO oldDefineFilter = new BaseDataDefineDTO();
        oldDefineFilter.setName(baseDataDefine.getName());
        BaseDataDefineDO oldDefine = this.baseDataDefineClient.get(oldDefineFilter);
        if (this.extBaseDataDefineImport != null) {
            baseDataDefineDTO = this.extBaseDataDefineImport.rebuild(baseDataDefineDTO);
        }
        if (oldDefine == null) {
            importResult = this.baseDataDefineClient.add(baseDataDefineDTO);
            if (importResult.getCode() != 0) {
                throw new RuntimeException("\u5bfc\u5165\u5931\u8d25." + importResult.getMsg());
            }
        } else {
            baseDataDefineDTO.setId(oldDefine.getId());
            importResult = this.baseDataDefineClient.upate(baseDataDefineDTO);
        }
        if ((baseDataDefineExt = baseAndModelDefine.dataModel.getExtInfo("baseDataDefine")) instanceof LinkedHashMap) {
            LinkedHashMap linkedHashMap = (LinkedHashMap)baseDataDefineExt;
            JSONObject object = new JSONObject();
            linkedHashMap.forEach((arg_0, arg_1) -> ((JSONObject)object).put(arg_0, arg_1));
            baseAndModelDefine.dataModel.getExtInfo().put("baseDataDefine", object);
        }
        this.dataModelClient.push(baseAndModelDefine.dataModel);
        if (importResult.getCode() != 0) {
            throw new RuntimeException("\u5bfc\u5165\u5931\u8d25." + importResult.getMsg());
        }
        return this.getEntity(baseDataDefineDTO.getName());
    }

    private void insertBaseDataGroup(BaseDataGroupDO groupDO) {
        if (groupDO != null) {
            BaseDataGroupDTO basedataGroupDTO = new BaseDataGroupDTO();
            basedataGroupDTO.setName(groupDO.getName());
            BaseDataGroupDO baseDataGroupDO = this.baseDataDefineClient.get(basedataGroupDTO);
            if (baseDataGroupDO != null) {
                return;
            }
            basedataGroupDTO.setName(EXPORT_GROUP);
            baseDataGroupDO = this.baseDataDefineClient.get(basedataGroupDTO);
            if (baseDataGroupDO == null) {
                basedataGroupDTO.setTitle(EXPORT_GROUP_NAME);
                this.baseDataDefineClient.add(basedataGroupDTO);
            }
            basedataGroupDTO.setName(groupDO.getName());
            basedataGroupDTO.setTitle(groupDO.getTitle());
            basedataGroupDTO.setParentname(EXPORT_GROUP);
            this.baseDataDefineClient.add(basedataGroupDTO);
        }
    }

    @Override
    public String getId() {
        return "BASE";
    }

    @Override
    public String getTitle() {
        return "\u57fa\u7840\u6570\u636e";
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean enableSelect() {
        return true;
    }

    @Override
    public boolean isEnableAuthority(String tableName) {
        IEntityDefine entityDefine = this.getDefineProvider().get(tableName);
        if (entityDefine == null) {
            return false;
        }
        return entityDefine.isAuthFlag();
    }

    @Override
    public boolean canReadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataOption.AuthType.ACCESS.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canEditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataOption.AuthType.MANAGE.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canReadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataOption.AuthType.ACCESS.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canEditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataOption.AuthType.MANAGE.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canAuditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.APPROVAL.name(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canSubmitEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.SUBMIT.name(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canUploadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.UPLOAD.name(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public boolean canWriteEntity(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataOption.AuthType.WRITE.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canWriteEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataOption.AuthType.WRITE.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.SUBMIT.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.SUBMIT.name(), entityId, identityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.UPLOAD.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.UPLOAD.name(), entityId, identityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.APPROVAL.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.APPROVAL.name(), entityId, identityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.PUBLISH.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canReadUnPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.canAuthorityEntityKey(BaseDataAuthTypeExtendImpl.AuthTypeExtend.READ_UN_PUBLISH.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanReadEntityKeys(String entityId, Date queryVersionDate) {
        return this.entityAuthProvider.getCanOperateEntityKeys(BaseDataOption.AuthType.ACCESS.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanEditEntityKeys(String entityId, Date queryVersionDate) {
        return this.entityAuthProvider.getCanOperateEntityKeys(BaseDataOption.AuthType.MANAGE.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanWriteEntityKeys(String entityId, Date queryVersionDate) {
        return this.entityAuthProvider.getCanOperateEntityKeys(BaseDataOption.AuthType.WRITE.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanAuditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.getCanOperateEntityKeys(BaseDataAuthTypeExtendImpl.AuthTypeExtend.APPROVAL.name(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanSubmitEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.getCanOperateEntityKeys(BaseDataAuthTypeExtendImpl.AuthTypeExtend.SUBMIT.name(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanUploadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.getCanOperateEntityKeys(BaseDataAuthTypeExtendImpl.AuthTypeExtend.UPLOAD.name(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanUploadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.getCanOperateIdentities(BaseDataAuthTypeExtendImpl.AuthTypeExtend.UPLOAD.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanAuditIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.getCanOperateIdentities(BaseDataAuthTypeExtendImpl.AuthTypeExtend.APPROVAL.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanSubmitIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) {
        return this.entityAuthProvider.getCanOperateIdentities(BaseDataAuthTypeExtendImpl.AuthTypeExtend.SUBMIT.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanReadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.entityAuthProvider.getCanOperateIdentities(BaseDataOption.AuthType.ACCESS.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public void grantAllPrivilegesToEntityTable(String entityId) {
        Set<String> allObjectCodes = this.getByAuth(entityId);
        this.grantAllPrivilegesToEntityData(entityId, allObjectCodes.toArray(new String[0]));
    }

    private Set<String> getByAuth(String tableName) {
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(tableName);
        baseDataFilter.setVersionDate(null);
        baseDataFilter.setAuthType(BaseDataOption.AuthType.ACCESS);
        PageVO page = this.baseDataClient.list(baseDataFilter);
        if (page.getTotal() == 0) {
            return Collections.emptySet();
        }
        return page.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toSet());
    }

    @Override
    public void grantAllPrivilegesToEntityData(String tableName, String ... entityKeyData) {
        if (entityKeyData == null || entityKeyData.length == 0) {
            return;
        }
        String userName = NpContextHolder.getContext().getUserName();
        if (ObjectUtils.isEmpty(userName)) {
            return;
        }
        List auths = Stream.of(entityKeyData).map(objectCode -> {
            BaseDataAuthDO baseDataAuth = new BaseDataAuthDO();
            baseDataAuth.setBiztype(Integer.valueOf(1));
            baseDataAuth.setBizname(userName);
            baseDataAuth.setAuthtype(Integer.valueOf(1));
            baseDataAuth.setDefinename(tableName);
            baseDataAuth.setObjectcode(objectCode);
            baseDataAuth.setAtmanage(Integer.valueOf(1));
            baseDataAuth.setAtaccess(Integer.valueOf(1));
            baseDataAuth.setAtwrite(Integer.valueOf(1));
            baseDataAuth.setAtedit(Integer.valueOf(1));
            return baseDataAuth;
        }).collect(Collectors.toList());
        this.vaBaseDataAuthService.updateDetail(auths);
    }

    @Override
    public String getDimensionName(String entityId) {
        return entityId;
    }

    @Override
    public String getDimensionNameByCode(String entityCode) {
        return entityCode;
    }

    @Override
    public String getEntityIdByCode(String entityCode) {
        return entityCode;
    }

    @Override
    public boolean isDBMode(String entityId, Date date) {
        return false;
    }

    @Override
    public String getEntityCode(String entityId) {
        return entityId;
    }

    static class EntityDefineExportData
    implements Serializable {
        DataModelDO dataModel;
        BaseDataDefineDO baseDataDefine;
        BaseDataGroupDO groupDO;

        EntityDefineExportData() {
        }

        public DataModelDO getDataModel() {
            return this.dataModel;
        }

        public void setDataModel(DataModelDO dataModel) {
            this.dataModel = dataModel;
        }

        public BaseDataDefineDO getBaseDataDefine() {
            return this.baseDataDefine;
        }

        public void setBaseDataDefine(BaseDataDefineDO baseDataDefine) {
            this.baseDataDefine = baseDataDefine;
        }

        public BaseDataGroupDO getGroupDO() {
            return this.groupDO;
        }

        public void setGroupDO(BaseDataGroupDO groupDO) {
            this.groupDO = groupDO;
        }
    }
}

