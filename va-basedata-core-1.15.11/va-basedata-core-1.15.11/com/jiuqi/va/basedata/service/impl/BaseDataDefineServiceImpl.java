/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$MoveType
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.common.StorageFieldConsts
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.event.BaseDataDefineEvent
 *  com.jiuqi.va.extend.BaseDataIsolationExtend
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.basedata.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.common.BaseDataAsyncTask;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.BaseDataDefineExtendUtil;
import com.jiuqi.va.basedata.common.BaseDataDefineTransUtil;
import com.jiuqi.va.basedata.common.DummyObjType;
import com.jiuqi.va.basedata.common.FormatValidationUtil;
import com.jiuqi.va.basedata.dao.VaBaseDataDefineDao;
import com.jiuqi.va.basedata.dao.VaBaseDataVersionDao;
import com.jiuqi.va.basedata.domain.BaseDataDefineBatchOperateDTO;
import com.jiuqi.va.basedata.domain.BaseDataDefineSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataDummyDTO;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataDefineCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataDummyService;
import com.jiuqi.va.basedata.storage.BaseDataDefineStorage;
import com.jiuqi.va.basedata.storage.BaseDataInfoStorage;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.common.StorageFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.event.BaseDataDefineEvent;
import com.jiuqi.va.extend.BaseDataIsolationExtend;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaBaseDataDefineServiceImpl")
public class BaseDataDefineServiceImpl
implements BaseDataDefineService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataDefineServiceImpl.class);
    @Autowired
    private BaseDataContextService baseDataContextService;
    @Autowired
    private VaBaseDataDefineDao baseDataDefineDao;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private VaBaseDataVersionDao baseDataVersionDao;
    @Autowired
    private BaseDataVersionService baseDataVersionService;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private BaseDataDefineCacheService defineCacheService;
    @Autowired(required=false)
    private List<BaseDataIsolationExtend> isolationExt;
    @Autowired
    private BaseDataAsyncTask baseDataAsyncTask;
    private BaseDataCacheService baseDataCacheService;
    private BaseDataDummyService baseDataDummyService;
    private BaseDataDefineTransUtil defineTransUtil;

    public BaseDataCacheService getBaseDataCacheService() {
        if (this.baseDataCacheService == null) {
            this.baseDataCacheService = (BaseDataCacheService)ApplicationContextRegister.getBean(BaseDataCacheService.class);
        }
        return this.baseDataCacheService;
    }

    private BaseDataDefineTransUtil getDefineTransUtil() {
        if (this.defineTransUtil == null) {
            this.defineTransUtil = (BaseDataDefineTransUtil)ApplicationContextRegister.getBean(BaseDataDefineTransUtil.class);
        }
        return this.defineTransUtil;
    }

    @Override
    public BaseDataDefineDO get(BaseDataDefineDTO param) {
        boolean languageTransFlag;
        Object languageTrans = param.getExtInfo("languageTransFlag");
        boolean bl = languageTransFlag = VaI18nParamUtils.getTranslationEnabled() != false && languageTrans != null && (Boolean)languageTrans != false;
        if (languageTransFlag) {
            param.setDeepClone(Boolean.valueOf(true));
        }
        BaseDataDefineDO data = null;
        if (StringUtils.hasText(param.getName())) {
            data = this.defineCacheService.getByName(param);
        } else {
            PageVO<BaseDataDefineDO> page = this.list(param);
            if (page != null && page.getTotal() == 1) {
                data = (BaseDataDefineDO)page.getRows().get(0);
            }
        }
        if (data != null && languageTransFlag) {
            this.getDefineTransUtil().transDefine(data);
        }
        return data;
    }

    @Override
    public PageVO<BaseDataDefineDO> list(BaseDataDefineDTO param) {
        boolean languageTransFlag;
        Object languageTrans = param.getExtInfo("languageTransFlag");
        boolean bl = languageTransFlag = VaI18nParamUtils.getTranslationEnabled() != false && languageTrans != null && (Boolean)languageTrans != false;
        if (languageTransFlag) {
            param.setDeepClone(Boolean.valueOf(true));
        }
        ArrayList<BaseDataDefineDO> rsList = new ArrayList<BaseDataDefineDO>();
        if (StringUtils.hasText(param.getName())) {
            BaseDataDefineDO define = this.defineCacheService.getByName(param);
            if (define != null) {
                rsList.add(define);
            }
        } else {
            List<BaseDataDefineDO> list = this.defineCacheService.list(param);
            for (BaseDataDefineDO baseDataDefineDO : list) {
                if (StringUtils.hasText(param.getGroupname()) && !"-".equals(param.getGroupname()) && !param.getGroupname().equals(baseDataDefineDO.getGroupname()) || StringUtils.hasText(param.getSearchKey()) && !baseDataDefineDO.getName().toUpperCase().contains(param.getSearchKey().trim().toUpperCase()) && !baseDataDefineDO.getTitle().contains(param.getSearchKey().trim()) || param.getAuthflag() != null && !param.getAuthflag().equals(baseDataDefineDO.getAuthflag()) || param.getActauthflag() != null && !param.getActauthflag().equals(baseDataDefineDO.getActauthflag()) || param.getDesignname() != null && !param.getDesignname().equals(baseDataDefineDO.getDesignname())) continue;
                rsList.add(baseDataDefineDO);
            }
        }
        if (rsList.size() > 1) {
            Collections.sort(rsList, (o1, o2) -> o1.getOrdernum().compareTo(o2.getOrdernum()));
        }
        PageVO page = new PageVO();
        page.setTotal(rsList.size());
        if (param.isPagination() && param.getLimit() > 0) {
            int endIndex = param.getOffset() + param.getLimit();
            endIndex = endIndex < rsList.size() ? endIndex : rsList.size();
            page.setRows(rsList.subList(param.getOffset(), endIndex));
        } else {
            page.setRows(rsList);
        }
        if (page.getTotal() > 0 && languageTransFlag) {
            this.getDefineTransUtil().transDefines(page.getRows());
        }
        page.setRs(R.ok());
        return page;
    }

    @Override
    public R exist(BaseDataDefineDTO param) {
        BaseDataDefineDO data = this.get(param);
        R r = R.ok();
        if (data != null) {
            r.put("exist", (Object)true);
            r.put("data", (Object)data);
        } else {
            r.put("exist", (Object)false);
        }
        return r;
    }

    @Override
    public R existData(BaseDataDefineDTO param) {
        BaseDataDefineDO define = this.get(param);
        if (define == null) {
            R r = R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.not.found", param.getName()));
            r.put("exist", (Object)false);
            return r;
        }
        int count = this.baseDataDefineDao.queryDataCount(param);
        R r = R.ok();
        r.put("exist", (Object)(count > 0 ? 1 : 0));
        return r;
    }

    @Override
    public R add(BaseDataDefineDTO baseDataDTO) {
        int cnt;
        UserLoginDTO currLoginUser;
        String name = baseDataDTO.getName();
        if (!this.baseDataContextService.isDefinePrefixUnfreeze() && !name.startsWith("MD_")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.name.check.startswith", new Object[0]));
        }
        if (name.equals("MD_ORG")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.built.in.system", new Object[0]));
        }
        if (name.startsWith("MD_ORG_")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.name.check.org.prefix", new Object[0]));
        }
        if (this.baseDataContextService.isCheckTableNameLength() && name.length() > 22 && baseDataDTO.getExtInfo("ignoreCheckTableNameLength") == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.name.check.length.exceeds", 22));
        }
        JDialectUtil jDialect = JDialectUtil.getInstance();
        JTableModel jtm = new JTableModel(baseDataDTO.getTenantName(), name);
        if (!this.baseDataContextService.isCreateDefineForce() && jDialect.hasTable(jtm)) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.check.physical.table.exist", new Object[0]));
        }
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setTenantName(baseDataDTO.getTenantName());
        param.setName(baseDataDTO.getName());
        BaseDataDefineDO old = this.get(param);
        if (old != null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.check.existed", new Object[0]));
        }
        boolean isCopy = false;
        if (baseDataDTO.getCopyName() != null) {
            this.copy(baseDataDTO.getCopyName(), baseDataDTO);
            isCopy = true;
        }
        R rs = null;
        if (!isCopy) {
            rs = this.checkIsolation(baseDataDTO);
            if (rs.getCode() != 0) {
                return rs;
            }
            rs = this.checkLevelCode(baseDataDTO);
            if (rs.getCode() != 0) {
                return rs;
            }
            this.setDefaultShowFields(baseDataDTO);
            if (baseDataDTO.getShowtype() == null) {
                baseDataDTO.setShowtype("NAME");
            }
        }
        baseDataDTO.setId(UUID.randomUUID());
        if (baseDataDTO.getCreator() == null && (currLoginUser = ShiroUtil.getUser()) != null) {
            baseDataDTO.setCreator(currLoginUser.getUsername());
        }
        if (baseDataDTO.getOrdernum() == null) {
            baseDataDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        if (baseDataDTO.getReadonly() == null) {
            baseDataDTO.setReadonly(Integer.valueOf(0));
        }
        if (baseDataDTO.getSolidifyflag() == null) {
            baseDataDTO.setSolidifyflag(Integer.valueOf(0));
        }
        if (baseDataDTO.getDummyflag() == null) {
            baseDataDTO.setDummyflag(Integer.valueOf(0));
        }
        if (baseDataDTO.getDummyflag() == 1 && baseDataDTO.getDefine() == null) {
            baseDataDTO.setDefine("{\"dummyObj\":0}");
        }
        baseDataDTO.setModifytime(new Date());
        if (!StringUtils.hasText(baseDataDTO.getGroupname())) {
            baseDataDTO.setGroupname("other");
        }
        if (baseDataDTO.getVersionflag() == null) {
            baseDataDTO.setVersionflag(Integer.valueOf(0));
        }
        if (baseDataDTO.getAuthflag() == null) {
            baseDataDTO.setAuthflag(Integer.valueOf(0));
        }
        if (baseDataDTO.getActauthflag() == null) {
            baseDataDTO.setActauthflag(Integer.valueOf(0));
        }
        if (baseDataDTO.getCachedisabled() == null) {
            baseDataDTO.setCachedisabled(Integer.valueOf(0));
        }
        List columns = null;
        if (baseDataDTO.getDummyflag() == 0) {
            if (isCopy) {
                DataModelDTO dataModelDTO = new DataModelDTO();
                dataModelDTO.setTenantName(baseDataDTO.getTenantName());
                dataModelDTO.setName(baseDataDTO.getCopyName());
                DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
                columns = dataModelDO.getColumns();
            } else {
                columns = this.getColumnsByDefine(baseDataDTO);
            }
            this.syncFieldProp(columns, (BaseDataDefineDO)baseDataDTO, null);
        }
        if ((cnt = this.baseDataDefineDao.insertSelective(baseDataDTO)) == 0) {
            return R.error();
        }
        if (baseDataDTO.getDummyflag() == 0) {
            R mdRs;
            boolean createWithColumns;
            boolean bl = createWithColumns = columns != null && !columns.isEmpty();
            if ((createWithColumns || baseDataDTO.getSolidifyflag() == 0) && (mdRs = BaseDataDefineStorage.init((BaseDataDefineDO)baseDataDTO, columns)).getCode() != 0 && !jDialect.hasTable(jtm)) {
                BaseDataDefineDTO paramTemp = new BaseDataDefineDTO();
                paramTemp.setId(baseDataDTO.getId());
                this.baseDataDefineDao.delete(paramTemp);
                return mdRs;
            }
            this.handleMultiSubList(baseDataDTO);
            if (baseDataDTO.getVersionflag() != null && baseDataDTO.getVersionflag() == 1) {
                this.initBaseDataVersion(baseDataDTO.getName());
            }
        }
        this.updateCache((BaseDataDefineDO)baseDataDTO, false);
        this.sendEvent(BaseDataOption.EventType.ADD, (BaseDataDefineDO)baseDataDTO, null);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49", (String)"\u65b0\u589e", (String)"", (String)param.getName(), (String)param.getTitle());
        return R.ok();
    }

    private void sendEvent(BaseDataOption.EventType eventType, BaseDataDefineDO newData, BaseDataDefineDO oldData) {
        BaseDataDefineEvent event = new BaseDataDefineEvent((Object)this.getClass().getName());
        event.setEventType(eventType);
        event.setOldDefineDTO(oldData);
        event.setNewDefineDTO(newData);
        try {
            ApplicationContextRegister.publishEvent((ApplicationEvent)event);
        }
        catch (Throwable e) {
            logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u53d8\u66f4\u540e\u7f6e\u4e8b\u4ef6\u5f02\u5e38", e);
        }
    }

    private void syncFieldProp(List<DataModelColumn> columns, BaseDataDefineDO newData, BaseDataDefineDO oldData) {
        Object newCol;
        if (columns == null || columns.isEmpty()) {
            return;
        }
        HashMap<String, DataModelColumn> columnMap = new HashMap<String, DataModelColumn>();
        for (DataModelColumn dataModelColumn : columns) {
            columnMap.put(dataModelColumn.getColumnName(), dataModelColumn);
        }
        HashMap<String, Boolean> newFieldMulti = new HashMap<String, Boolean>();
        HashMap<String, Boolean> newFieldUnique = new HashMap<String, Boolean>();
        this.getFieldProMap(newData, columns, newFieldMulti, newFieldUnique);
        if (oldData != null) {
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setTenantName(newData.getTenantName());
            dataModelDTO.setName(newData.getName());
            DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
            List oldColumns = dataModelDO.getColumns();
            HashMap<String, Boolean> oldFieldMulti = new HashMap<String, Boolean>();
            HashMap<String, Boolean> oldFieldUnique = new HashMap<String, Boolean>();
            this.getFieldProMap(oldData, oldColumns, oldFieldMulti, oldFieldUnique);
            String colName = null;
            newCol = null;
            String oldFieldMapping = "";
            String newFieldMapping = "";
            boolean oldMultiFlag = false;
            boolean newMultiFlag = false;
            int checkFlag = 0;
            Iterator iterator = oldColumns.iterator();
            while (iterator.hasNext()) {
                DataModelColumn oldCol = (DataModelColumn)iterator.next();
                if (oldCol.getColumnType() != DataModelType.ColumnType.NVARCHAR || (newCol = (DataModelColumn)columnMap.get(colName = oldCol.getColumnName())) == null) continue;
                checkFlag = 0;
                oldFieldMapping = "";
                newFieldMapping = "";
                oldMultiFlag = false;
                newMultiFlag = false;
                if (StringUtils.hasText(oldCol.getMapping())) {
                    oldFieldMapping = oldCol.getMapping();
                }
                if (StringUtils.hasText(newCol.getMapping())) {
                    newFieldMapping = newCol.getMapping();
                }
                if (!oldFieldMapping.equals(newFieldMapping)) {
                    ++checkFlag;
                }
                if (oldFieldMulti.containsKey(colName)) {
                    oldMultiFlag = (Boolean)oldFieldMulti.get(colName);
                }
                if (newFieldMulti.containsKey(colName)) {
                    newMultiFlag = (Boolean)newFieldMulti.get(colName);
                }
                if (oldMultiFlag != newMultiFlag) {
                    ++checkFlag;
                }
                if (checkFlag == 0) continue;
                if (oldMultiFlag) {
                    if (!this.checkSubTableExistsData(newData.getTenantName(), newData.getName(), colName)) continue;
                    newCol.setMappingType(oldCol.getMappingType());
                    newCol.setMapping(oldCol.getMapping());
                    newFieldMulti.put(colName, true);
                    newFieldUnique.put(colName, false);
                    logger.error(newData.getName() + "." + colName + "\u5b58\u5728\u591a\u9009\u6570\u636e\uff0c\u5df2\u8fd8\u539f\u914d\u7f6e\u3002");
                    continue;
                }
                if (!this.checkExistsData(newData.getTenantName(), newData.getName(), colName)) continue;
                newCol.setMappingType(oldCol.getMappingType());
                newCol.setMapping(oldCol.getMapping());
                newFieldMulti.put(colName, false);
                logger.error(newData.getName() + "." + colName + "\u5b58\u5728\u6570\u636e\uff0c\u5df2\u8fd8\u539f\u914d\u7f6e\u3002");
            }
        }
        String columnName = null;
        HashMap newFieldPropsMap = new HashMap();
        for (DataModelColumn endColumn : columns) {
            columnName = endColumn.getColumnName();
            if (!newFieldMulti.containsKey(columnName) || !newFieldUnique.containsKey(columnName)) continue;
            HashMap<String, String> prop = new HashMap<String, String>();
            prop.put("columnName", columnName);
            if (newFieldMulti.containsKey(columnName)) {
                prop.put("multiple", (String)newFieldMulti.get(columnName));
            }
            if (newFieldUnique.containsKey(columnName)) {
                prop.put("unique", (String)newFieldUnique.get(columnName));
            }
            newFieldPropsMap.put(columnName, prop);
        }
        ObjectNode objectNode = JSONUtil.parseObject((String)newData.getDefine());
        ArrayNode fieldProps = null;
        if (objectNode != null) {
            fieldProps = objectNode.withArray("fieldProps");
        }
        if (fieldProps != null && !fieldProps.isEmpty()) {
            boolean isTreeStyle = true;
            if (newData.getStructtype() == null || newData.getStructtype() < 2) {
                isTreeStyle = false;
            }
            HashSet<String> safeFilter = new HashSet<String>();
            safeFilter.add("objectcode".toUpperCase());
            safeFilter.add("code".toUpperCase());
            safeFilter.add("parentcode".toUpperCase());
            safeFilter.add("parents".toUpperCase());
            for (JsonNode field : fieldProps) {
                columnName = field.get("columnName").asText();
                if (isTreeStyle) {
                    if (safeFilter.contains(columnName)) {
                        ((ObjectNode)field).remove("fieldSensitive");
                    }
                } else if ("objectcode".equalsIgnoreCase(columnName)) {
                    ((ObjectNode)field).remove("fieldSensitive");
                }
                if (safeFilter.contains(columnName)) {
                    ((ObjectNode)field).remove("fieldSecurity");
                }
                if (newFieldMulti.containsKey(columnName)) {
                    ((ObjectNode)field).put("multiple", (Boolean)newFieldMulti.get(columnName));
                }
                if (newFieldUnique.containsKey(columnName)) {
                    ((ObjectNode)field).put("unique", (Boolean)newFieldUnique.get(columnName));
                }
                newFieldPropsMap.put(columnName, field);
            }
        }
        ArrayNode showFields = null;
        if (objectNode != null && ((showFields = objectNode.withArray("showFields")) == null || showFields.isEmpty())) {
            showFields = objectNode.withArray("defaultShowFields");
        }
        if (showFields != null && !showFields.isEmpty()) {
            JsonNode field = null;
            newCol = null;
            Iterator iter = showFields.iterator();
            while (iter.hasNext()) {
                field = (JsonNode)iter.next();
                columnName = field.get("columnName").asText();
                newCol = (DataModelColumn)columnMap.get(columnName);
                if (newCol == null) {
                    iter.remove();
                    continue;
                }
                ((ObjectNode)field).put("columnTitle", newCol.getColumnTitle());
                ((ObjectNode)field).put("columnType", newCol.getColumnType().toString());
                if (newCol.getMappingType() != null && newCol.getMappingType() >= 0) {
                    ((ObjectNode)field).put("mappingType", newCol.getMappingType());
                    if (newCol.getMappingType() > 0) {
                        ((ObjectNode)field).put("mapping", newCol.getMapping());
                    }
                } else {
                    ((ObjectNode)field).remove("mappingType");
                    ((ObjectNode)field).remove("mapping");
                }
                if (newFieldMulti.containsKey(columnName)) {
                    ((ObjectNode)field).put("multiple", (Boolean)newFieldMulti.get(columnName));
                }
                if (!newFieldUnique.containsKey(columnName)) continue;
                ((ObjectNode)field).put("unique", (Boolean)newFieldUnique.get(columnName));
            }
        }
        if (objectNode != null) {
            ArrayNode propNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString(newFieldPropsMap.values()));
            objectNode.set("fieldProps", (JsonNode)propNodes);
            objectNode.set("showFields", (JsonNode)showFields);
            newData.setDefine(JSONUtil.toJSONString((Object)objectNode));
        } else {
            HashMap<String, Collection<Object>> endMap = new HashMap<String, Collection<Object>>();
            endMap.put("fieldProps", newFieldPropsMap.values());
            endMap.put("showFields", (Collection<Object>)showFields);
            newData.setDefine(JSONUtil.toJSONString(endMap));
        }
    }

    private void getFieldProMap(BaseDataDefineDO defineDO, List<DataModelColumn> columns, Map<String, Boolean> fieldMulti, Map<String, Boolean> fieldUnique) {
        String define = defineDO.getDefine();
        ObjectNode objectNode = JSONUtil.parseObject((String)define);
        HashMap<String, DataModelColumn> columnMap = new HashMap<String, DataModelColumn>();
        for (DataModelColumn dataModelColumn : columns) {
            columnMap.put(dataModelColumn.getColumnName(), dataModelColumn);
        }
        String columnName = null;
        DataModelColumn column = null;
        boolean multi = false;
        ArrayNode fieldProps = null;
        if (objectNode != null) {
            fieldProps = objectNode.withArray("fieldProps");
        }
        if (fieldProps != null && !fieldProps.isEmpty()) {
            for (JsonNode field : fieldProps) {
                columnName = field.get("columnName").asText();
                multi = false;
                column = (DataModelColumn)columnMap.get(columnName);
                if (column == null) continue;
                if (column.getMappingType() != null && column.getMappingType() == 1 && field.has("multiple")) {
                    multi = field.get("multiple").asBoolean();
                    fieldMulti.put(columnName, multi);
                }
                if (multi) {
                    fieldUnique.put(columnName, false);
                    continue;
                }
                if (column.getColumnType() != DataModelType.ColumnType.NVARCHAR || !field.has("unique")) continue;
                fieldUnique.put(columnName, field.get("unique").asBoolean());
            }
        }
        ArrayNode showFields = null;
        if (objectNode != null && ((showFields = objectNode.withArray("showFields")) == null || showFields.isEmpty())) {
            showFields = objectNode.withArray("defaultShowFields");
        }
        if (showFields != null && !showFields.isEmpty()) {
            for (JsonNode field : showFields) {
                columnName = field.get("columnName").asText();
                multi = false;
                column = (DataModelColumn)columnMap.get(columnName);
                if (column == null) continue;
                if (!fieldMulti.containsKey(columnName) && field.has("multiple")) {
                    if (column.getMappingType() != null && column.getMappingType() == 1) {
                        fieldMulti.put(columnName, field.get("multiple").asBoolean());
                    } else {
                        fieldMulti.put(columnName, false);
                    }
                }
                if (fieldMulti.containsKey(columnName)) {
                    multi = fieldMulti.get(columnName);
                }
                if (multi) {
                    fieldUnique.put(columnName, false);
                    continue;
                }
                if (column.getColumnType() != DataModelType.ColumnType.NVARCHAR || fieldUnique.containsKey(columnName) || !field.has("unique")) continue;
                fieldUnique.put(columnName, field.get("unique").asBoolean());
            }
        }
        for (DataModelColumn dColumn : columns) {
            if (dColumn.getColumnType() != DataModelType.ColumnType.NVARCHAR) continue;
            columnName = dColumn.getColumnName();
            if (!fieldUnique.containsKey(columnName)) {
                fieldUnique.put(columnName, false);
            }
            if (dColumn.getMappingType() == null || dColumn.getMappingType() != 1 || fieldMulti.containsKey(columnName)) continue;
            fieldMulti.put(columnName, false);
        }
    }

    private void copy(String copyName, BaseDataDefineDTO newData) {
        String groupname = newData.getGroupname();
        String name = newData.getName();
        String title = newData.getTitle();
        String designname = newData.getDesignname();
        String remark = newData.getRemark();
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setTenantName(newData.getTenantName());
        param.setName(copyName);
        param.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO old = this.get(param);
        BaseDataDefineExtendUtil.clone((BaseDataDefineDO)newData, old);
        newData.setGroupname(groupname);
        newData.setName(name);
        newData.setTitle(title);
        newData.setDesignname(designname);
        newData.setRemark(remark);
        newData.setReadonly(Integer.valueOf(0));
        newData.setSolidifyflag(Integer.valueOf(0));
    }

    private R checkIsolation(BaseDataDefineDTO baseDataDTO) {
        Integer shareType = baseDataDTO.getSharetype();
        String shareFields = baseDataDTO.getSharefieldname();
        if (shareType == null || shareType == 0) {
            return R.ok();
        }
        if (!StringUtils.hasText(shareFields)) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.isolation.dimension.check.empty", new Object[0]));
        }
        if (shareType == 2 || shareType == 3) {
            String[] fields = shareFields.split("\\,");
            int unitcodeIndex = -1;
            for (int i = 0; i < fields.length; ++i) {
                if (!fields[i].equalsIgnoreCase("unitcode")) continue;
                unitcodeIndex = i;
                break;
            }
            if (unitcodeIndex == -1) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.check.org.isolation.dimension", new Object[0]));
            }
            if (fields.length > 1 && !fields[0].equalsIgnoreCase("unitcode")) {
                StringBuilder fieldsStr = new StringBuilder();
                fieldsStr.append(fields[unitcodeIndex]).append(",");
                for (int i = 0; i < fields.length; ++i) {
                    if (i == unitcodeIndex) continue;
                    fieldsStr.append(fields[i]).append(",");
                }
                baseDataDTO.setSharefieldname(fieldsStr.substring(0, fieldsStr.length() - 1));
            }
        }
        return R.ok();
    }

    private R checkLevelCode(BaseDataDefineDTO baseDataDTO) {
        if (baseDataDTO.getStructtype() != null && baseDataDTO.getStructtype() == 3) {
            String levelcode = baseDataDTO.getLevelcode();
            if (!StringUtils.hasText(levelcode)) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.levelcode.missing", new Object[0]));
            }
            int maxLength = 0;
            String[] objs = levelcode.split("\\#");
            String lvcode = objs[0];
            if (lvcode.length() > 10) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.levelcode.level.limit", new Object[0]));
            }
            for (int i = 0; i < lvcode.length(); ++i) {
                maxLength += Integer.parseInt(lvcode.substring(i, i + 1));
            }
            if (maxLength > 60) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.levelcode.length.limit", maxLength));
            }
            if (objs.length > 1 && maxLength > Integer.parseInt(objs[1])) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.levelcode.fixed.check", maxLength));
            }
        }
        return R.ok();
    }

    private void setDefaultShowFields(BaseDataDefineDTO baseDataDTO) {
        if (baseDataDTO.getDefaultShowColumns() != null) {
            return;
        }
        ArrayList<Map> fields = new ArrayList<Map>();
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)StorageFieldConsts.getFtCode(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)StorageFieldConsts.getFtName(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        if (baseDataDTO.getStructtype() == 2) {
            fields.add(BaseDataStorageUtil.getColumnMap((String)"PARENTCODE", (String)StorageFieldConsts.getFtParentCode(), (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)false, (Boolean)false));
        }
        baseDataDTO.setDefaultShowColumns(fields);
    }

    @Override
    public R update(BaseDataDefineDTO baseDataDTO) {
        boolean levelcodeChange;
        R rs;
        boolean onlyEditBasicInfo;
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setTenantName(baseDataDTO.getTenantName());
        param.setName(baseDataDTO.getName());
        BaseDataDefineDO oldDefine = this.get(param);
        if (oldDefine == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.not.found", baseDataDTO.getName()));
        }
        baseDataDTO.setName(oldDefine.getName());
        if (oldDefine.getModifytime() != null && baseDataDTO.getModifytime() != null && oldDefine.getModifytime().after(baseDataDTO.getModifytime())) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.info.bddefine.ver.changed", new Object[0]));
        }
        baseDataDTO.setModifytime(new Date());
        if (baseDataDTO.getExtInfo("ignoreExtendUpdateTime") == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ObjectNode extData = JSONUtil.parseObject((String)oldDefine.getExtdata());
            if (extData != null && extData.has("extendUpdateTime")) {
                extData.put("extendUpdateTime", sdf.format(new Date()));
                baseDataDTO.setExtdata(JSONUtil.toJSONString((Object)extData));
            }
        }
        baseDataDTO.setId(oldDefine.getId());
        baseDataDTO.setCreator(null);
        Integer oldDummyflag = oldDefine.getDummyflag();
        if (oldDummyflag != null && oldDummyflag == 1) {
            Integer newDummyflag = baseDataDTO.getDummyflag();
            if (newDummyflag != null && !oldDummyflag.equals(newDummyflag)) {
                return R.error();
            }
            this.baseDataDefineDao.updateByPrimaryKeySelective(baseDataDTO);
            this.updateCache((BaseDataDefineDO)baseDataDTO, false);
            this.sendEvent(BaseDataOption.EventType.UPDATE, (BaseDataDefineDO)baseDataDTO, oldDefine);
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49", (String)"\u66f4\u65b0", (String)"", (String)param.getName(), (String)param.getTitle());
            return R.ok();
        }
        boolean bl = onlyEditBasicInfo = baseDataDTO.getExtInfo("onlyEditBasicInfo") != null;
        if (onlyEditBasicInfo) {
            baseDataDTO.setStructtype(null);
            baseDataDTO.setSharetype(null);
            baseDataDTO.setSharefieldname(null);
            baseDataDTO.setGroupfieldname(null);
            baseDataDTO.setLevelcode(null);
            baseDataDTO.setVersionflag(null);
            baseDataDTO.setDefine(null);
        } else if (this.checkExistsData(baseDataDTO.getTenantName(), baseDataDTO.getName(), null)) {
            baseDataDTO.setStructtype(oldDefine.getStructtype());
            baseDataDTO.setSharetype(oldDefine.getSharetype());
            baseDataDTO.setSharefieldname(oldDefine.getSharefieldname());
            baseDataDTO.setGroupfieldname(oldDefine.getGroupfieldname());
            baseDataDTO.setLevelcode(oldDefine.getLevelcode());
        } else {
            rs = this.checkIsolation(baseDataDTO);
            if (rs.getCode() != 0) {
                return rs;
            }
        }
        if (baseDataDTO.getLevelcode() != null && (rs = this.checkLevelCode(baseDataDTO)).getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getVersionflag() != null) {
            if (baseDataDTO.getVersionflag() == 1) {
                this.initBaseDataVersion(baseDataDTO.getName());
            } else {
                BaseDataVersionDTO verParam = new BaseDataVersionDTO();
                verParam.setTablename(baseDataDTO.getName());
                List<BaseDataVersionDO> verList = this.baseDataVersionDao.list(verParam);
                if (verList != null && !verList.isEmpty()) {
                    if (verList.size() == 1 && verList.get(0).getValidtime().compareTo(BaseDataConsts.VERSION_MIN_DATE) == 0) {
                        verParam.setId(verList.get(0).getId());
                        this.baseDataVersionDao.delete(verParam);
                    } else {
                        return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.versionControl.closed", new Object[0]));
                    }
                }
            }
        }
        boolean hasPushed = false;
        if (baseDataDTO.getDefine() != null) {
            ObjectNode objectNode;
            ArrayNode showFields;
            R rs2 = this.handleBaseDataColumns(baseDataDTO, oldDefine);
            if (rs2.getCode() != 0) {
                return rs2;
            }
            hasPushed = rs2.containsKey((Object)"hasPushed");
            this.handleMultiSubList(baseDataDTO);
            List oldDefaultShowFields = oldDefine.getDefaultShowColumns();
            if (oldDefaultShowFields != null) {
                baseDataDTO.setDefaultShowColumns(oldDefaultShowFields);
            }
            if ((showFields = (objectNode = JSONUtil.parseObject((String)baseDataDTO.getDefine())).withArray("showFields")) != null && !showFields.isEmpty()) {
                String columnName = null;
                for (JsonNode node : showFields) {
                    columnName = node.get("columnName").asText();
                    if ("code".equalsIgnoreCase(columnName) || "name".equalsIgnoreCase(columnName)) {
                        ((ObjectNode)node).put("required", true);
                        continue;
                    }
                    if (!"parentcode".equalsIgnoreCase(columnName) && !"ver".equalsIgnoreCase(columnName) && !"objectcode".equalsIgnoreCase(columnName) && !"stopflag".equalsIgnoreCase(columnName) && !"ordinal".equalsIgnoreCase(columnName) && !"parents".equalsIgnoreCase(columnName)) continue;
                    ((ObjectNode)node).put("required", false);
                }
                objectNode.set("showFields", (JsonNode)showFields);
                baseDataDTO.setDefine(objectNode.toString());
            }
        }
        this.baseDataDefineDao.updateByPrimaryKeySelective(baseDataDTO);
        if (!hasPushed && baseDataDTO.getTitle() != null && !oldDefine.getTitle().equals(baseDataDTO.getTitle())) {
            DataModelDO dataModel = new DataModelDO();
            dataModel.setTenantName(baseDataDTO.getTenantName());
            dataModel.setName(baseDataDTO.getName());
            dataModel.setTitle(baseDataDTO.getTitle());
            this.dataModelClient.updateBaseInfo(dataModel);
        }
        this.updateCache((BaseDataDefineDO)baseDataDTO, false);
        this.sendEvent(BaseDataOption.EventType.UPDATE, (BaseDataDefineDO)baseDataDTO, oldDefine);
        boolean structTypeChange = baseDataDTO.getStructtype() != null && baseDataDTO.getStructtype().equals(oldDefine.getStructtype());
        boolean bl2 = levelcodeChange = StringUtils.hasText(baseDataDTO.getLevelcode()) && !baseDataDTO.getLevelcode().equals(oldDefine.getLevelcode());
        if (structTypeChange || levelcodeChange) {
            this.syncDummyBaseData(baseDataDTO);
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49", (String)"\u66f4\u65b0", (String)"", (String)param.getName(), (String)param.getTitle());
        return R.ok();
    }

    private void syncDummyBaseData(BaseDataDefineDTO baseDataDTO) {
        BaseDataDefineDTO defineDTO = new BaseDataDefineDTO();
        defineDTO.setTenantName(ShiroUtil.getTenantName());
        List<BaseDataDefineDO> list = this.defineCacheService.list(defineDTO);
        List dummyBaseDataList = list.stream().filter(baseDataDefineDO -> baseDataDefineDO.getDummyflag() != null && baseDataDefineDO.getDummyflag() == 1).collect(Collectors.toList());
        for (BaseDataDefineDO baseDataDefineDO2 : dummyBaseDataList) {
            String define = baseDataDefineDO2.getDefine();
            ObjectNode defineJson = JSONUtil.parseObject((String)define);
            if (!defineJson.has("basedataDefineCode")) continue;
            String baseDataDefineCode = defineJson.get("basedataDefineCode").asText();
            if (!defineJson.has("dummyObj") || defineJson.get("dummyObj").asInt(0) != DummyObjType.BASEDATA.getCode() || !baseDataDTO.getName().equals(baseDataDefineCode)) continue;
            BaseDataDefineDTO dummyBaseDataDefine = new BaseDataDefineDTO();
            dummyBaseDataDefine.setName(baseDataDefineDO2.getName());
            dummyBaseDataDefine.setTenantName(ShiroUtil.getTenantName());
            if (baseDataDTO.getStructtype() == 1) {
                dummyBaseDataDefine.setStructtype(Integer.valueOf(0));
            } else {
                dummyBaseDataDefine.setStructtype(baseDataDTO.getStructtype());
            }
            dummyBaseDataDefine.setDummyflag(Integer.valueOf(1));
            dummyBaseDataDefine.setModifytime(new Date());
            if (baseDataDTO.getStructtype() == 3) {
                dummyBaseDataDefine.setLevelcode(baseDataDTO.getLevelcode());
            }
            this.update(dummyBaseDataDefine);
        }
    }

    private void handleMultiSubList(BaseDataDefineDTO baseDataDTO) {
        boolean createTableFlag;
        block5: {
            if (baseDataDTO.getDefine() == null) {
                return;
            }
            createTableFlag = false;
            try {
                ObjectNode objectNode = JSONUtil.parseObject((String)baseDataDTO.getDefine());
                ArrayNode fieldProps = objectNode.withArray("fieldProps");
                if (fieldProps == null || fieldProps.isEmpty()) break block5;
                for (JsonNode field : fieldProps) {
                    if (!field.has("multiple") || !field.get("multiple").asBoolean()) continue;
                    createTableFlag = true;
                    break;
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (createTableFlag) {
            BaseDataInfoStorage.initDetailTable(baseDataDTO.getName(), baseDataDTO.getTenantName());
        }
    }

    private R handleBaseDataColumns(BaseDataDefineDTO baseDataDTO, BaseDataDefineDO oldDefine) {
        R r;
        List<DataModelColumn> columns = this.getColumnsByDefine(baseDataDTO);
        if (columns == null || columns.isEmpty()) {
            logger.warn("\u8b66\u544a\uff1a\u66f4\u65b0\u5b9a\u4e49\u672a\u643a\u5e26\u5b57\u6bb5\u4fe1\u606f\uff0c\u9700\u8981\u81ea\u8eab\u63a7\u5236\u5b57\u6bb5\u5c5e\u6027\u548c\u5c55\u793a\u914d\u7f6e\u7684\u51c6\u786e\u6027\u3002");
            return R.ok();
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(baseDataDTO.getTenantName());
        dataModelDTO.setName(baseDataDTO.getName());
        DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
        List oldColumns = dataModelDO.getColumns();
        JDialectUtil jDialectUtil = JDialectUtil.getInstance();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> fields = new ArrayList<String>();
        String fieldName = null;
        for (DataModelColumn column : columns) {
            fieldName = column.getColumnName();
            fields.add(fieldName);
            if (jDialectUtil.isReservedWord(fieldName)) {
                sb.append(", ").append(fieldName);
            }
            if (!fieldName.equalsIgnoreCase("pagination") && !fieldName.equalsIgnoreCase("expression") && !fieldName.equalsIgnoreCase("sharefields")) continue;
            sb.append(", ").append(fieldName);
        }
        if (sb.length() > 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.check.reserved.fields", sb.substring(1)));
        }
        HashMap<String, DataModelColumn> oldColumnMap = new HashMap<String, DataModelColumn>();
        ArrayList<String> deleteFields = new ArrayList<String>();
        if (oldColumns != null && !oldColumns.isEmpty()) {
            for (DataModelColumn column : oldColumns) {
                oldColumnMap.put(column.getColumnName(), column);
                if (fields.contains(column.getColumnName())) continue;
                deleteFields.add(column.getColumnName());
            }
        }
        ArrayList<String> exitDataFields = new ArrayList<String>();
        if (!deleteFields.isEmpty()) {
            for (String string : deleteFields) {
                if (!this.checkExistsData(baseDataDTO.getTenantName(), baseDataDTO.getName(), string)) continue;
                exitDataFields.add(string);
            }
        }
        if (!exitDataFields.isEmpty()) {
            StringBuilder sBuilder = new StringBuilder();
            for (String string : exitDataFields) {
                sBuilder.append("\u3010" + string + "\u3011\u3001");
            }
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.field.remove.data.existed", sBuilder.toString().substring(0, sBuilder.length() - 1)));
        }
        Integer shareType = baseDataDTO.getSharetype();
        if (shareType != null && shareType > 0) {
            HashMap<String, DataModelColumn> hashMap = new HashMap<String, DataModelColumn>();
            if (this.isolationExt != null && !this.isolationExt.isEmpty()) {
                String string = baseDataDTO.getName();
                String colName = null;
                for (BaseDataIsolationExtend ext : this.isolationExt) {
                    if (ext.getApplyTo() != null && !ext.getApplyTo().contains(string)) continue;
                    colName = ext.getIsolationColumn().getColumnName().toUpperCase();
                    hashMap.computeIfAbsent(colName, key -> ext.getIsolationColumn());
                }
            }
            for (String isofield : baseDataDTO.getSharefieldname().split("\\,")) {
                if (fields.contains(isofield.toUpperCase()) || !hashMap.containsKey(isofield.toUpperCase())) continue;
                columns.add((DataModelColumn)hashMap.get(isofield.toUpperCase()));
            }
        }
        this.syncFieldProp(columns, (BaseDataDefineDO)baseDataDTO, oldDefine);
        dataModelDO.setColumns(columns);
        dataModelDO.addExtInfo("baseDataDefine", (Object)baseDataDTO);
        if (baseDataDTO.getTitle() != null) {
            dataModelDO.setTitle(baseDataDTO.getTitle());
        }
        if ((r = this.dataModelClient.pushComplete(dataModelDO)).getCode() != 0) {
            return r;
        }
        r.put("hasPushed", (Object)true);
        for (DataModelColumn column : columns) {
            if (!StringUtils.hasText(column.getDefaultVal()) || oldColumnMap.containsKey(column.getColumnName()) && StringUtils.hasText(((DataModelColumn)oldColumnMap.get(column.getColumnName())).getDefaultVal())) continue;
            String tenantName = ShiroUtil.getTenantName();
            BaseDataDTO param = new BaseDataDTO();
            param.setTenantName(tenantName);
            param.setTableName(baseDataDTO.getName());
            BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
            bdsc.setTenantName(tenantName);
            bdsc.setBaseDataDTO(param);
            bdsc.setForceUpdate(true);
            this.baseDataAsyncTask.execute(() -> this.getBaseDataCacheService().pushSyncMsg(bdsc));
            break;
        }
        return r;
    }

    private List<DataModelColumn> getColumnsByDefine(BaseDataDefineDTO baseDataDTO) {
        String define = baseDataDTO.getDefine();
        if (!StringUtils.hasText(define)) {
            return null;
        }
        ObjectNode defineObj = JSONUtil.parseObject((String)define);
        if (defineObj == null) {
            return null;
        }
        if (!defineObj.has("columns")) {
            return null;
        }
        ArrayNode columns = null;
        try {
            columns = defineObj.withArray("columns");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        defineObj.remove("columns");
        baseDataDTO.setDefine(JSONUtil.toJSONString((Object)defineObj));
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        HashMap<String, DataModelColumn> colNames = new HashMap<String, DataModelColumn>();
        ArrayList<DataModelColumn> list = new ArrayList<DataModelColumn>();
        for (Object fieldData : columns) {
            DataModelColumn column = new DataModelColumn();
            column.setColumnName(fieldData.get("columnName").asText());
            column.setColumnTitle(fieldData.get("columnTitle").asText());
            column.setColumnType(DataModelType.ColumnType.valueOf((String)fieldData.get("columnType").asText()));
            if (fieldData.get("mappingType") != null) {
                column.setMappingType(Integer.valueOf(fieldData.get("mappingType").asInt()));
            }
            if (fieldData.get("mapping") != null) {
                column.setMapping(fieldData.get("mapping").asText());
            }
            if (fieldData.get("lengths") != null) {
                ArrayNode lengths = (ArrayNode)fieldData.withArray("lengths");
                Integer[] lengthsArray = new Integer[lengths.size()];
                for (int i = 0; i < lengths.size(); ++i) {
                    lengthsArray[i] = lengths.get(i).asInt();
                }
                column.setLengths(lengthsArray);
            }
            if (column.getColumnName().equals("ID")) {
                column.setPkey(Boolean.valueOf(true));
            } else if (fieldData.get("pkey") != null) {
                column.setPkey(Boolean.valueOf(fieldData.get("pkey").asBoolean()));
            }
            if (fieldData.get("columnAttr") != null) {
                column.setColumnAttr(DataModelType.ColumnAttr.valueOf((String)fieldData.get("columnAttr").asText()));
            }
            if (fieldData.get("defaultVal") != null) {
                column.setDefaultVal(fieldData.get("defaultVal").asText(null));
            }
            if (fieldData.get("nullable") != null) {
                column.setNullable(Boolean.valueOf(fieldData.get("nullable").asBoolean()));
            }
            if (column.getColumnName().equalsIgnoreCase("validtime") || column.getColumnName().equalsIgnoreCase("invalidtime")) {
                column.setColumnType(DataModelType.ColumnType.DATE);
            }
            list.add(column);
            colNames.put(column.getColumnName(), column);
        }
        List tempList = BaseDataStorageUtil.getTemplateFields();
        for (DataModelColumn dataModelColumn : tempList) {
            if (colNames.containsKey(dataModelColumn.getColumnName())) continue;
            list.add(dataModelColumn);
            colNames.put(dataModelColumn.getColumnName(), dataModelColumn);
        }
        Integer shareType = baseDataDTO.getSharetype();
        DataModelColumn codeCol = (DataModelColumn)colNames.get("CODE");
        DataModelColumn objectcodeCol = (DataModelColumn)colNames.get("OBJECTCODE");
        if (shareType != null && shareType == 0) {
            if (objectcodeCol.getLengths()[0] < codeCol.getLengths()[0]) {
                objectcodeCol.setLengths(codeCol.getLengths());
            }
        } else {
            objectcodeCol.setLengths(new Integer[]{200});
        }
        return list;
    }

    @Override
    public R remove(BaseDataDefineDTO baseDataDTO) {
        int count;
        BaseDataDefineDTO paramTemp = new BaseDataDefineDTO();
        paramTemp.setName(baseDataDTO.getName());
        BaseDataDefineDO old = (BaseDataDefineDO)this.baseDataDefineDao.selectOne(paramTemp);
        if (old == null) {
            this.updateCache((BaseDataDefineDO)baseDataDTO, true);
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.not.found", baseDataDTO.getName()));
        }
        JTableModel jtm = new JTableModel(baseDataDTO.getTenantName(), baseDataDTO.getName());
        JDialectUtil jDialect = JDialectUtil.getInstance();
        if (jDialect.hasTable(jtm) && (count = this.baseDataDefineDao.queryDataCount(baseDataDTO)) > 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.remove.check.data.existed", new Object[0]));
        }
        baseDataDTO.setId(old.getId());
        if (this.checkRelated(baseDataDTO)) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.remove.check.referenced", baseDataDTO.getExtInfo("relateInfoCheck")));
        }
        if (this.baseDataDefineDao.delete(paramTemp) > 0 && (old.getDummyflag() == null || old.getDummyflag() == 0)) {
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(baseDataDTO.getName());
            this.dataModelClient.remove((DataModelDO)dataModelDTO);
            BaseDataVersionDTO verParam = new BaseDataVersionDTO();
            verParam.setTablename(baseDataDTO.getName());
            this.baseDataVersionDao.delete(verParam);
        }
        this.updateCache((BaseDataDefineDO)baseDataDTO, true);
        this.sendEvent(BaseDataOption.EventType.DELETE, null, old);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49", (String)"\u5220\u9664", (String)"", (String)baseDataDTO.getName(), (String)"");
        return R.ok();
    }

    private void updateCache(BaseDataDefineDO baseDataDefineDO, boolean isRemove) {
        BaseDataDefineSyncCacheDTO bddscd = new BaseDataDefineSyncCacheDTO();
        bddscd.setTenantName(baseDataDefineDO.getTenantName());
        bddscd.setBaseDataDefine(baseDataDefineDO);
        bddscd.setRemove(isRemove);
        this.defineCacheService.pushSyncMsg(bddscd);
    }

    private boolean checkRelated(BaseDataDefineDTO baseDataDefineDTO) {
        String curTableName = baseDataDefineDTO.getName();
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        PageVO dataModels = this.dataModelClient.list(dataModelDTO);
        for (DataModelDO dataModel : dataModels.getRows()) {
            if (dataModel.getName().equalsIgnoreCase(curTableName)) continue;
            for (DataModelColumn column : dataModel.getColumns()) {
                if (!StringUtils.hasText(column.getMapping()) || !column.getMapping().split("\\.")[0].equalsIgnoreCase(curTableName)) continue;
                baseDataDefineDTO.addExtInfo("relateInfoCheck", (Object)("\u3010" + dataModel.getTitle() + " - " + dataModel.getName() + "[" + column.getColumnName() + "]\u3011"));
                return true;
            }
        }
        return false;
    }

    private void initBaseDataVersion(String tablename) {
        BaseDataVersionDO bdVerDO = new BaseDataVersionDO();
        bdVerDO.setTablename(tablename);
        bdVerDO.setName("-");
        bdVerDO.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
        this.baseDataVersionService.add(bdVerDO);
    }

    @Override
    public R updown(BaseDataDefineDTO baseDataDefineDTO) {
        if (baseDataDefineDTO.getMovetype() == null || baseDataDefineDTO.getId() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String moveType = baseDataDefineDTO.getMovetype();
        UUID currId = baseDataDefineDTO.getId();
        baseDataDefineDTO.setId(null);
        baseDataDefineDTO.setPagination(false);
        List defineList = this.list(baseDataDefineDTO).getRows();
        BaseDataDefineDO currDefine = null;
        BaseDataDefineDO targetDefine = null;
        int targetDefineIndex = -1;
        for (int i = 0; i < defineList.size(); ++i) {
            if (!currId.equals(((BaseDataDefineDO)defineList.get(i)).getId())) continue;
            currDefine = (BaseDataDefineDO)defineList.get(i);
            if (BaseDataOption.MoveType.UP.name().equals(moveType)) {
                targetDefineIndex = i - 1;
                break;
            }
            if (!BaseDataOption.MoveType.DOWN.name().equals(moveType)) break;
            targetDefineIndex = i + 1;
            break;
        }
        if (currDefine == null || targetDefineIndex < 0 || targetDefineIndex >= defineList.size()) {
            if (BaseDataOption.MoveType.UP.name().equals(moveType)) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.common.move.up", new Object[0]));
            }
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.common.move.down", new Object[0]));
        }
        targetDefine = (BaseDataDefineDO)defineList.get(targetDefineIndex);
        BaseDataDefineDO param = new BaseDataDefineDO();
        param.setId(currDefine.getId());
        param.setName(currDefine.getName());
        param.setOrdernum(targetDefine.getOrdernum());
        param.setModifytime(new Date());
        this.baseDataDefineDao.updateByPrimaryKeySelective(param);
        this.updateCache(param, false);
        this.sendEvent(BaseDataOption.EventType.UPDATE, param, currDefine);
        param.setId(targetDefine.getId());
        param.setName(targetDefine.getName());
        param.setOrdernum(currDefine.getOrdernum());
        param.setModifytime(new Date());
        this.baseDataDefineDao.updateByPrimaryKeySelective(param);
        this.updateCache(param, false);
        this.sendEvent(BaseDataOption.EventType.UPDATE, param, targetDefine);
        return R.ok();
    }

    @Override
    public R batchOperate(BaseDataDefineBatchOperateDTO updateParam) {
        List<UUID> addAuthDataIdList = updateParam.getAddAuthDataIdList();
        List<UUID> reduceAuthDataIdList = updateParam.getReduceAuthDataIdList();
        List<UUID> addDisableCacheIdList = updateParam.getAddDisableCacheIdList();
        List<UUID> reduceDisableCacheIdList = updateParam.getReduceDisableCacheIdList();
        if (addAuthDataIdList.isEmpty() && reduceAuthDataIdList.isEmpty() && addDisableCacheIdList.isEmpty() && reduceDisableCacheIdList.isEmpty()) {
            return R.ok();
        }
        BaseDataDefineDO findParam = new BaseDataDefineDO();
        BaseDataDefineDO old = null;
        BaseDataDefineDO param = new BaseDataDefineDO();
        if (!addAuthDataIdList.isEmpty()) {
            for (UUID id : addAuthDataIdList) {
                findParam.setId(id);
                old = (BaseDataDefineDO)this.baseDataDefineDao.selectOne(findParam);
                if (old == null) continue;
                param.setId(id);
                param.setName(old.getName());
                param.setAuthflag(Integer.valueOf(1));
                param.setModifytime(new Date());
                this.baseDataDefineDao.updateByPrimaryKeySelective(param);
                this.updateCache(param, false);
                this.sendEvent(BaseDataOption.EventType.UPDATE, param, old);
            }
        }
        if (!reduceAuthDataIdList.isEmpty()) {
            for (UUID id : reduceAuthDataIdList) {
                findParam.setId(id);
                old = (BaseDataDefineDO)this.baseDataDefineDao.selectOne(findParam);
                if (old == null) continue;
                param.setId(id);
                param.setName(old.getName());
                param.setAuthflag(Integer.valueOf(0));
                param.setModifytime(new Date());
                this.baseDataDefineDao.updateByPrimaryKeySelective(param);
                this.updateCache(param, false);
                this.sendEvent(BaseDataOption.EventType.UPDATE, param, old);
            }
        }
        if (!addDisableCacheIdList.isEmpty()) {
            for (UUID id : addDisableCacheIdList) {
                findParam.setId(id);
                old = (BaseDataDefineDO)this.baseDataDefineDao.selectOne(findParam);
                if (old == null) continue;
                param.setId(id);
                param.setName(old.getName());
                param.setCachedisabled(Integer.valueOf(1));
                param.setModifytime(new Date());
                this.baseDataDefineDao.updateByPrimaryKeySelective(param);
                this.updateCache(param, false);
                this.sendEvent(BaseDataOption.EventType.UPDATE, param, old);
            }
        }
        if (!reduceDisableCacheIdList.isEmpty()) {
            for (UUID id : reduceDisableCacheIdList) {
                findParam.setId(id);
                old = (BaseDataDefineDO)this.baseDataDefineDao.selectOne(findParam);
                if (old == null) continue;
                param.setId(id);
                param.setName(old.getName());
                param.setCachedisabled(Integer.valueOf(0));
                param.setModifytime(new Date());
                this.baseDataDefineDao.updateByPrimaryKeySelective(param);
                this.updateCache(param, false);
                this.sendEvent(BaseDataOption.EventType.UPDATE, param, old);
            }
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49", (String)"\u6279\u91cf\u64cd\u4f5c", (String)"", (String)"", (String)"");
        return R.ok();
    }

    @Override
    public List<DataModelColumn> isolationList(BaseDataDefineDTO param) {
        ArrayList<DataModelColumn> list = new ArrayList<DataModelColumn>();
        HashSet<String> colSet = new HashSet<String>();
        DataModelColumn unit = new DataModelColumn();
        unit.setColumnName("UNITCODE");
        unit.setColumnTitle("\u7ec4\u7ec7\u673a\u6784");
        list.add(unit);
        colSet.add("UNITCODE");
        if (this.isolationExt != null && !this.isolationExt.isEmpty()) {
            String name = param.getName();
            String colName = null;
            for (BaseDataIsolationExtend ext : this.isolationExt) {
                if (ext.getApplyTo() != null && !ext.getApplyTo().contains(name)) continue;
                colName = ext.getIsolationColumn().getColumnName().toUpperCase();
                if (colSet.contains(colName)) {
                    logger.info("#*#*#*#*#*#*#*# \u9694\u79bb\u7ef4\u5ea6\u91cd\u590d\u6ce8\u518c\uff1a" + ext.getClass().getName());
                    continue;
                }
                ext.getIsolationColumn().setColumnName(colName);
                list.add(ext.getIsolationColumn());
            }
        }
        return list;
    }

    @Override
    public R checkZbExistData(BaseDataDefineDTO param) {
        String curTableName = FormatValidationUtil.checkInjection(param.getName());
        List checkColumns = param.getShowcolumns();
        JDialectUtil jDialect = JDialectUtil.getInstance();
        JTableModel jtm = new JTableModel(param.getTenantName(), curTableName + "_SUBLIST");
        boolean hasSubTable = jDialect.hasTable(jtm);
        HashSet<String> existInfo = new HashSet<String>();
        String tenantName = param.getTenantName();
        String columnName = null;
        for (DataModelColumn column : checkColumns) {
            columnName = FormatValidationUtil.checkInjection(column.getColumnName());
            if (this.checkExistsData(tenantName, curTableName, columnName)) {
                existInfo.add(column.getColumnName());
                continue;
            }
            if (!hasSubTable || !this.checkSubTableExistsData(tenantName, curTableName, columnName)) continue;
            existInfo.add(column.getColumnName());
        }
        R res = R.ok();
        res.put("existInfo", existInfo);
        return res;
    }

    private boolean checkExistsData(String tenantName, String tbName, String colName) {
        SqlDTO sqlDTO = new SqlDTO(tenantName, null);
        String sqlTemp = null;
        if (colName == null) {
            sqlTemp = "select count(0) as cnt from %s";
            sqlDTO.setSql(String.format(sqlTemp, tbName));
        } else {
            sqlTemp = "select count(0) as cnt from %s where %s is not null";
            sqlDTO.setSql(String.format(sqlTemp, tbName, colName));
        }
        try {
            if (this.commonDao.countBySql(sqlDTO) > 0) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private boolean checkSubTableExistsData(String tenantName, String tbName, String colName) {
        String sqlTemp = "select count(0) as cnt from %s_SUBLIST where FIELDNAME = '%s'";
        SqlDTO sqlDTO = new SqlDTO(tenantName, sqlTemp);
        sqlDTO.setSql(String.format(sqlTemp, tbName, colName));
        try {
            if (this.commonDao.countBySql(sqlDTO) > 0) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public R checkSQLDefine(BaseDataDummyDTO basedata) {
        if (this.baseDataDummyService == null) {
            this.baseDataDummyService = (BaseDataDummyService)ApplicationContextRegister.getBean(BaseDataDummyService.class);
        }
        return this.baseDataDummyService.checkSQLDefine(basedata);
    }
}

