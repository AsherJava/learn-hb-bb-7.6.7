/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.event.BaseDataEvent
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataUniqueDO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataModifyService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.event.BaseDataEvent;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataSyncHelpService")
public class BaseDataSyncService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataSyncService.class);
    private static String rsKey = "results";
    private static String rsKeyDataId = "BASEDATAID";
    private static String rsKeyDataCode = "BASEDATACODE";
    private static String rsKeyStatus = "STATUS";
    private static String rsStatus1 = "DOING";
    private static String rsStatus2 = "DONE";
    private static String rsStatus3 = "FAILURE";
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    @Autowired
    private BaseDataParamService baseDataParamService;
    @Autowired
    private BaseDataModifyService baseDataModifyService;
    @Autowired
    private BaseDataQueryService baseDataQueryService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private BaseDataCacheCoordinationService coordinationService;
    @Autowired
    private BaseDataContextService baseDataContextService;

    public R sync(BaseDataBatchOptDTO param) {
        BaseDataDTO rqParam = param.getQueryParam();
        if (rqParam == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String tableName = rqParam.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.sync(param);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        List dataList = param.getDataList();
        if (dataList == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        rqParam.remove((Object)"stopflag");
        rqParam.remove((Object)"recoveryflag");
        R checkRs = this.baseDataParamService.modifyVersionCheck(rqParam);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        if (param.isTableCover()) {
            this.cleanTable(rqParam);
            this.updateCache(rqParam, true);
        }
        if (dataList.isEmpty()) {
            return R.ok();
        }
        ArrayList<BaseDataDO> addList = new ArrayList<BaseDataDO>();
        ArrayList<BaseDataDO> updateList = new ArrayList<BaseDataDO>();
        try {
            if (param.isFullFieldOverride()) {
                this.pickData(param, addList, updateList);
            } else {
                this.convertData(param, addList, updateList);
            }
        }
        catch (Exception e) {
            logger.error("\u540c\u6b65\u5f02\u5e38\uff1a" + rqParam.getTableName(), e);
            return R.error((String)e.getMessage());
        }
        this.initDataModel(rqParam);
        ArrayList results = new ArrayList();
        R rs = R.ok().put(rsKey, results);
        if (param.isHighTrustability() || param.isFullFieldOverride()) {
            this.syncWithNoCheck(rqParam, addList, updateList, rs);
        } else {
            this.syncWithCheck(rqParam, addList, updateList, rs);
        }
        return rs;
    }

    private void initDataModel(BaseDataDTO basedata) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(basedata.getTableName());
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
        basedata.put("hasDataModel", (Object)dataModel);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncWithNoCheck(BaseDataDTO rqParam, List<BaseDataDO> addList, List<BaseDataDO> updateList, R rts) {
        List results = (List)rts.get((Object)rsKey);
        String i18nTrue = BaseDataCoreI18nUtil.getOptSuccessMsg();
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        VaBaseDataDao baseDataDao = (VaBaseDataDao)sqlSession.getMapper(VaBaseDataDao.class);
        CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
        SqlDTO sqlDTO = new SqlDTO(rqParam.getTenantName(), null);
        sqlDTO.addParam("tableName", (Object)rqParam.getTableName());
        int batchSize = this.baseDataContextService.getBatchSubmitSize();
        try {
            R rs;
            int i;
            BaseDataDTO tempData = new BaseDataDTO();
            BaseDataDO baseDataDO = null;
            int addSize = addList.size();
            int updateSize = updateList.size();
            int cnt = 0;
            int total = addSize + updateSize;
            ArrayList<BaseDataDO> subBizTemp = new ArrayList<BaseDataDO>();
            this.baseDataParamService.encodeSecurityFields(addList, (BaseDataDO)rqParam);
            for (i = 0; i < addList.size(); ++i) {
                baseDataDO = addList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)baseDataDO);
                rs = R.ok((String)i18nTrue);
                rs.put(rsKeyStatus, (Object)rsStatus3);
                rs.put(rsKeyDataId, (Object)tempData.getId());
                rs.put(rsKeyDataCode, (Object)tempData.getCode());
                results.add(rs);
                baseDataDao.add(tempData);
                if (tempData.containsKey((Object)"hasMultiValues")) {
                    subBizTemp.add(baseDataDO);
                }
                this.sendEvent(tempData, BaseDataOption.EventType.ADD);
                if (++cnt % batchSize == 0 || cnt == total) {
                    this.updateSubData(subBizTemp, commonDao, sqlDTO, BaseDataOption.EventType.ADD);
                    sqlSession.commit();
                    sqlSession.clearCache();
                    rs.put(rsKeyStatus, (Object)rsStatus1);
                    continue;
                }
                rs.put(rsKeyStatus, (Object)rsStatus2);
            }
            this.baseDataParamService.encodeSecurityFields(updateList, (BaseDataDO)rqParam);
            for (i = 0; i < updateList.size(); ++i) {
                baseDataDO = updateList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)baseDataDO);
                rs = R.ok((String)i18nTrue);
                rs.put(rsKeyStatus, (Object)rsStatus3);
                rs.put(rsKeyDataId, (Object)tempData.getId());
                rs.put(rsKeyDataCode, (Object)tempData.getCode());
                results.add(rs);
                baseDataDao.update(tempData);
                if (tempData.containsKey((Object)"hasMultiValues")) {
                    subBizTemp.add(baseDataDO);
                }
                this.sendEvent(tempData, BaseDataOption.EventType.UPDATE);
                if (++cnt % batchSize == 0 || cnt == total) {
                    this.updateSubData(subBizTemp, commonDao, sqlDTO, BaseDataOption.EventType.UPDATE);
                    sqlSession.commit();
                    sqlSession.clearCache();
                    rs.put(rsKeyStatus, (Object)rsStatus1);
                    continue;
                }
                rs.put(rsKeyStatus, (Object)rsStatus2);
            }
        }
        catch (Exception e) {
            rts.setCode(500);
            rts.setMsg(e.getMessage());
            logger.error("\u540c\u6b65\u5f02\u5e38\uff1a" + rqParam.getTableName(), e);
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
            this.updateCache(rqParam, false);
        }
    }

    private void updateSubData(List<BaseDataDO> subBizData, CommonDao commonDao, SqlDTO sqlDTO, BaseDataOption.EventType eventType) {
        if (subBizData == null || subBizData.isEmpty()) {
            return;
        }
        String tableName = (String)sqlDTO.getParam("tableName");
        ArrayList<String> colList = (ArrayList<String>)sqlDTO.getParam("colList");
        String addSubSql = (String)sqlDTO.getParam("addSubSql");
        String deleteSubSql = (String)sqlDTO.getParam("deleteSubSql");
        if (colList == null) {
            colList = new ArrayList<String>();
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(tableName);
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
            List cols = dataModel.getColumns();
            for (DataModelColumn dataModelColumn : cols) {
                if (dataModelColumn.getMappingType() == null || dataModelColumn.getMappingType() != 1) continue;
                colList.add(dataModelColumn.getColumnName());
            }
            sqlDTO.addParam("colList", colList);
            addSubSql = this.baseDataModifyService.getAddSubSql();
            sqlDTO.addParam("addSubSql", (Object)addSubSql);
            deleteSubSql = this.baseDataModifyService.getDeleteSubSql();
            sqlDTO.addParam("deleteSubSql", (Object)deleteSubSql);
        }
        LinkedHashMap<UUID, Map> valueMap = new LinkedHashMap<UUID, Map>();
        String lowerName = null;
        Object value = null;
        for (String string : colList) {
            lowerName = string.toLowerCase();
            for (BaseDataDO baseDataDO : subBizData) {
                valueMap.computeIfAbsent(baseDataDO.getId(), key -> new LinkedHashMap());
                value = baseDataDO.get((Object)lowerName);
                if (!(value instanceof ArrayList)) continue;
                ((Map)valueMap.get(baseDataDO.getId())).put(string, (List)value);
            }
        }
        if (valueMap.isEmpty()) {
            return;
        }
        if (eventType == BaseDataOption.EventType.UPDATE) {
            sqlDTO.setSql(deleteSubSql);
            for (Map.Entry entry : valueMap.entrySet()) {
                sqlDTO.addParam("masterid", entry.getKey());
                for (Map.Entry entry2 : ((Map)entry.getValue()).entrySet()) {
                    sqlDTO.addParam("fieldname", entry2.getKey());
                    commonDao.executeBySql(sqlDTO);
                }
            }
        }
        sqlDTO.setSql(addSubSql);
        for (Map.Entry entry : valueMap.entrySet()) {
            sqlDTO.addParam("masterid", entry.getKey());
            for (Map.Entry entry3 : ((Map)entry.getValue()).entrySet()) {
                sqlDTO.addParam("fieldname", entry3.getKey());
                int order = 1;
                for (String str : (List)entry3.getValue()) {
                    sqlDTO.addParam("id", (Object)UUID.randomUUID());
                    sqlDTO.addParam("fieldvalue", (Object)str);
                    sqlDTO.addParam("ordernum", (Object)order++);
                    commonDao.executeBySql(sqlDTO);
                }
            }
        }
        subBizData.clear();
    }

    private void sendEvent(BaseDataDTO param, BaseDataOption.EventType eventType) {
        PageVO<BaseDataDO> page;
        BaseDataDTO oldData = null;
        if (eventType == BaseDataOption.EventType.UPDATE && (page = this.baseDataQueryService.listAnyWay(param)) != null && page.getTotal() > 0) {
            oldData = new BaseDataDTO();
            oldData.putAll((Map)page.getRows().get(0));
        }
        BaseDataEvent event = new BaseDataEvent((Object)this.getClass().getName());
        event.setEventType(eventType);
        event.setBaseDataOldDTO(oldData);
        event.setBaseDataDTO(param);
        try {
            ApplicationContextRegister.publishEvent((ApplicationEvent)event);
        }
        catch (Throwable e) {
            logger.error("\u57fa\u7840\u6570\u636e\u9879\u53d8\u66f4\u540e\u7f6e\u4e8b\u4ef6\u5f02\u5e38", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncWithCheck(BaseDataDTO rqParam, List<BaseDataDO> addList, List<BaseDataDO> updateList, R rts) {
        List results = (List)rts.get((Object)rsKey);
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        VaBaseDataDao baseDatadao = (VaBaseDataDao)sqlSession.getMapper(VaBaseDataDao.class);
        int batchSize = this.baseDataContextService.getBatchSubmitSize();
        BaseDataDTO tempData = new BaseDataDTO();
        BaseDataDO baseDataDO = null;
        R rs = null;
        try {
            int i;
            int addSize = addList.size();
            int updateSize = updateList.size();
            int cnt = 0;
            int total = addSize + updateSize;
            for (i = 0; i < addSize; ++i) {
                baseDataDO = addList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)baseDataDO);
                tempData.setCacheSyncDisable(Boolean.valueOf(true));
                tempData.put("BASEDATA_CODE_IGNORECASE", (Object)true);
                tempData.put("BASEDATA_BATCH_DAO", (Object)baseDatadao);
                rs = this.baseDataModifyService.add(tempData);
                rs.put(rsKeyDataId, (Object)tempData.getId());
                rs.put(rsKeyDataCode, (Object)tempData.getCode());
                results.add(rs);
                if (++cnt % batchSize == 0 || cnt == total) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                    rs.put(rsKeyStatus, (Object)(rs.getCode() == 0 ? rsStatus1 : rsStatus3));
                    continue;
                }
                rs.put(rsKeyStatus, (Object)(rs.getCode() == 0 ? rsStatus2 : rsStatus3));
            }
            for (i = 0; i < updateSize; ++i) {
                baseDataDO = updateList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)baseDataDO);
                tempData.setCacheSyncDisable(Boolean.valueOf(true));
                tempData.put("BASEDATA_BATCH_DAO", (Object)baseDatadao);
                rs = this.baseDataModifyService.update(tempData);
                rs.put(rsKeyDataId, (Object)tempData.getId());
                rs.put(rsKeyDataCode, (Object)tempData.getCode());
                results.add(rs);
                if (++cnt % batchSize == 0 || cnt == total) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                    rs.put(rsKeyStatus, (Object)(rs.getCode() == 0 ? rsStatus1 : rsStatus3));
                    continue;
                }
                rs.put(rsKeyStatus, (Object)(rs.getCode() == 0 ? rsStatus2 : rsStatus3));
            }
        }
        catch (Exception e) {
            rts.setCode(500);
            rts.setMsg(e.getMessage());
            logger.error("\u540c\u6b65\u5f02\u5e38\uff1a" + rqParam.getTableName(), e);
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
            this.updateCache(rqParam, false);
        }
    }

    private void pickData(BaseDataBatchOptDTO param, List<BaseDataDO> addList, List<BaseDataDO> updateList) {
        BigDecimal bd1 = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BigDecimal bd2 = new BigDecimal(1.0E-4, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        param.getQueryParam().setQueryStartVer(bd1);
        List paramDataList = param.getDataList();
        boolean tableCover = param.isTableCover();
        if (tableCover) {
            for (BaseDataDO baseDataDO : paramDataList) {
                bd1 = bd1.add(bd2);
                baseDataDO.setVer(bd1);
                addList.add(baseDataDO);
            }
            return;
        }
        BaseDataDTO optQueryParam = param.getQueryParam();
        this.baseDataParamService.initDefineInfo((BaseDataDO)optQueryParam);
        boolean versonMgrStarted = this.baseDataParamService.isVersionMgrStarted((BaseDataDO)optQueryParam);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTenantName(param.getTenantName());
        queryParam.setTableName(optQueryParam.getTableName());
        if (versonMgrStarted) {
            queryParam.setVersionDate(optQueryParam.getVersionDate());
        }
        if (paramDataList.size() == 1) {
            queryParam.setObjectcode(((BaseDataDO)paramDataList.get(0)).getObjectcode());
        } else if (paramDataList.size() <= 2000) {
            ArrayList<String> objectcodes = new ArrayList<String>();
            for (Object baseDataDO : paramDataList) {
                objectcodes.add(baseDataDO.getObjectcode());
            }
            queryParam.setBaseDataObjectcodes(objectcodes);
        } else {
            List sharefields = (List)optQueryParam.get((Object)"sharefields");
            if (sharefields != null) {
                queryParam.put("sharefields", (Object)sharefields);
                Object val = null;
                for (String field : sharefields) {
                    val = optQueryParam.get((Object)field);
                    if (val == null) continue;
                    queryParam.put(field, val);
                }
            }
        }
        List<BaseDataUniqueDO> dataList = this.baseDataDao.selectUniqueInfo(queryParam);
        if (dataList == null || dataList.isEmpty()) {
            for (Object baseDataDO : paramDataList) {
                bd1 = bd1.add(bd2);
                baseDataDO.setVer(bd1);
                addList.add((BaseDataDO)baseDataDO);
            }
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        HashMap<String, UUID> keyMap = new HashMap<String, UUID>();
        String key = null;
        for (BaseDataUniqueDO info : dataList) {
            key = versonMgrStarted ? info.getObjectcode() + "@" + sdf.format(info.getValidtime()) : info.getObjectcode();
            keyMap.put(key, info.getId());
        }
        for (BaseDataDO baseDataDO : paramDataList) {
            key = versonMgrStarted ? baseDataDO.getObjectcode() + "@" + sdf.format(baseDataDO.getValidtime()) : baseDataDO.getObjectcode();
            bd1 = bd1.add(bd2);
            baseDataDO.setVer(bd1);
            if (keyMap.containsKey(key)) {
                baseDataDO.setId((UUID)keyMap.get(key));
                updateList.add(baseDataDO);
                continue;
            }
            addList.add(baseDataDO);
        }
    }

    private void convertData(BaseDataBatchOptDTO param, List<BaseDataDO> addList, List<BaseDataDO> updateList) {
        BaseDataDO oldData;
        boolean tableCover = param.isTableCover();
        boolean highTrustability = param.isHighTrustability();
        List dataList = param.getDataList();
        BaseDataDTO rqParam = param.getQueryParam();
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)rqParam);
        int structType = defineDO.getStructtype();
        boolean versionMgrStarted = this.baseDataParamService.isVersionMgrStarted((BaseDataDO)rqParam);
        boolean cacheDisabled = this.baseDataParamService.isCacheDisabled((BaseDataDO)rqParam);
        boolean isTreeStyle = structType == 2 || structType == 3;
        String code = null;
        String parentcode = null;
        HashMap<String, BaseDataDO> newCodeRefDataMap = new HashMap<String, BaseDataDO>();
        HashMap<String, String> newCodeRefParentcodeMap = null;
        if (isTreeStyle) {
            newCodeRefParentcodeMap = new HashMap<String, String>();
        }
        for (Object dataDO : dataList) {
            code = dataDO.getCode();
            if (newCodeRefDataMap.containsKey(code)) {
                throw new RuntimeException("\u5b58\u5728\u91cd\u590dcode\u7684\u6570\u636e\uff1a" + code);
            }
            newCodeRefDataMap.put(code, (BaseDataDO)dataDO);
            if (!isTreeStyle) continue;
            parentcode = dataDO.getParentcode();
            if ("".equals(parentcode)) {
                parentcode = "-";
            }
            if (parentcode == null) continue;
            newCodeRefParentcodeMap.put(code, parentcode);
        }
        if (tableCover && newCodeRefParentcodeMap != null && !newCodeRefParentcodeMap.isEmpty()) {
            HashSet<String> parentcodeNotInSync = new HashSet<String>();
            for (String pcode : newCodeRefParentcodeMap.values()) {
                if ("-".equals(pcode) || newCodeRefDataMap.containsKey(pcode)) continue;
                parentcodeNotInSync.add(pcode);
            }
            if (!parentcodeNotInSync.isEmpty()) {
                throw new RuntimeException("\u540c\u6b65\u5f02\u5e38: \u6570\u636e\u4e2d\u4e22\u5931\u7236\u7ea7" + JSONUtil.toJSONString(parentcodeNotInSync));
            }
        }
        HashMap<String, String> upChangeParentMap = null;
        HashMap<String, String> endCodeRefParentcodeMap = null;
        if (isTreeStyle) {
            endCodeRefParentcodeMap = new HashMap<String, String>();
            endCodeRefParentcodeMap.putAll(newCodeRefParentcodeMap);
        }
        HashMap<String, Object> oldCodeRefDataMap = null;
        BaseDataDTO convertParam = null;
        if (!tableCover) {
            convertParam = new BaseDataDTO();
            convertParam.setTenantName(rqParam.getTenantName());
            convertParam.setTableName(rqParam.getTableName());
            convertParam.setVersionDate(rqParam.getVersionDate());
            convertParam.setRecoveryflag(Integer.valueOf(-1));
            convertParam.setStopflag(Integer.valueOf(-1));
            convertParam.setAuthType(BaseDataOption.AuthType.NONE);
            this.baseDataParamService.cloneDefineInfo((BaseDataDO)rqParam, (BaseDataDO)convertParam);
        }
        String objcodeSuffix = null;
        if (defineDO.getSharetype() > 0) {
            StringBuilder objcode = new StringBuilder();
            List sharefields = (List)rqParam.get((Object)"sharefields");
            if (sharefields != null) {
                String shareVal = null;
                for (String field : sharefields) {
                    shareVal = (String)rqParam.get((Object)field);
                    if (StringUtils.hasText(shareVal) && !"-".equals(shareVal)) {
                        objcode.append("||").append(shareVal);
                    }
                    if (convertParam == null) continue;
                    convertParam.put(field, (Object)shareVal);
                }
            }
            objcodeSuffix = objcode.toString();
        }
        if (!tableCover) {
            oldCodeRefDataMap = new HashMap<String, Object>();
            HashMap<String, String> oldCodeRefParentcodeMap = null;
            if (isTreeStyle) {
                oldCodeRefParentcodeMap = new HashMap<String, String>();
            }
            ArrayList<Object> codeList = new ArrayList<Object>();
            codeList.addAll(newCodeRefDataMap.keySet());
            convertParam.setBaseDataCodes(codeList);
            PageVO<BaseDataDO> page = this.baseDataQueryService.listAnyWay(convertParam);
            if (page != null && page.getTotal() > 0) {
                for (Object baseDataDO : page.getRows()) {
                    code = baseDataDO.getCode();
                    oldCodeRefDataMap.put(code, baseDataDO);
                    if (!isTreeStyle) continue;
                    oldCodeRefParentcodeMap.put(code, baseDataDO.getParentcode());
                }
            }
            if (isTreeStyle && !newCodeRefParentcodeMap.isEmpty()) {
                upChangeParentMap = new HashMap<String, String>();
                HashSet<String> suppParentSet = new HashSet<String>();
                for (Map.Entry entry : newCodeRefParentcodeMap.entrySet()) {
                    String tCode = (String)entry.getKey();
                    parentcode = (String)newCodeRefParentcodeMap.get(tCode);
                    if (!oldCodeRefParentcodeMap.containsKey(tCode)) {
                        suppParentSet.add(parentcode);
                        continue;
                    }
                    if (parentcode.equals(oldCodeRefParentcodeMap.get(tCode))) continue;
                    upChangeParentMap.put(tCode, parentcode);
                    suppParentSet.add(parentcode);
                }
                if (!suppParentSet.isEmpty()) {
                    codeList.clear();
                    codeList.addAll(suppParentSet);
                    page = this.baseDataQueryService.listAnyWay(convertParam);
                    if (page != null && page.getTotal() > 0) {
                        String[] parents = null;
                        for (Object baseDataDO : page.getRows()) {
                            code = baseDataDO.getCode();
                            parentcode = baseDataDO.getParentcode();
                            if ("-".equals(parentcode)) {
                                if (endCodeRefParentcodeMap.containsKey(code)) continue;
                                endCodeRefParentcodeMap.put(code, "-");
                                continue;
                            }
                            parents = baseDataDO.getParents().split("\\/");
                            for (int i = parents.length - 1; i > 0; --i) {
                                if (endCodeRefParentcodeMap.containsKey(parents[i])) continue;
                                endCodeRefParentcodeMap.put(parents[i], parents[i - 1]);
                            }
                        }
                    }
                }
                if (!upChangeParentMap.isEmpty()) {
                    convertParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
                    if (cacheDisabled) {
                        convertParam.setBaseDataCodes(null);
                        for (String string : upChangeParentMap.keySet()) {
                            convertParam.setCode(string);
                            page = this.baseDataQueryService.listAnyWay(convertParam);
                            if (page == null || page.getTotal() == 0) continue;
                            for (BaseDataDO baseDataDO : page.getRows()) {
                                code = baseDataDO.getCode();
                                oldCodeRefDataMap.put(code, baseDataDO);
                                parentcode = baseDataDO.getParentcode();
                                if (!endCodeRefParentcodeMap.containsKey(code)) {
                                    endCodeRefParentcodeMap.put(code, parentcode);
                                }
                                if (newCodeRefDataMap.containsKey(code)) continue;
                                BaseDataDO oldData2 = new BaseDataDO();
                                oldData2.putAll((Map)baseDataDO);
                                oldData2.setParents(null);
                                dataList.add(oldData2);
                            }
                        }
                    } else {
                        codeList.clear();
                        codeList.addAll(upChangeParentMap.keySet());
                        page = this.baseDataQueryService.listAnyWay(convertParam);
                        if (page != null && page.getTotal() > 0) {
                            for (BaseDataDO baseDataDO : page.getRows()) {
                                code = baseDataDO.getCode();
                                oldCodeRefDataMap.put(code, baseDataDO);
                                parentcode = baseDataDO.getParentcode();
                                if (!endCodeRefParentcodeMap.containsKey(code)) {
                                    endCodeRefParentcodeMap.put(code, parentcode);
                                }
                                if (newCodeRefDataMap.containsKey(code)) continue;
                                oldData = new BaseDataDO();
                                oldData.putAll((Map)baseDataDO);
                                oldData.setParents(null);
                                dataList.add(oldData);
                            }
                        }
                    }
                }
            }
        }
        String createUserId = null;
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            createUserId = user.getId();
        }
        BigDecimal bd1 = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BigDecimal bd2 = new BigDecimal(1.0E-4, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        rqParam.setQueryStartVer(bd1);
        for (BaseDataDO baseDataDO : dataList) {
            code = baseDataDO.getCode();
            bd1 = bd1.add(bd2);
            baseDataDO.put("BASEDATA_FIXED_VER", (Object)bd1);
            if (tableCover || !oldCodeRefDataMap.containsKey(code)) {
                addList.add(baseDataDO);
                if (objcodeSuffix != null) {
                    baseDataDO.put("objcodeSuffix", (Object)objcodeSuffix);
                }
                if (baseDataDO.getCreateuser() == null) {
                    baseDataDO.setCreateuser(createUserId);
                }
                this.initField(baseDataDO, rqParam, structType);
                continue;
            }
            oldData = (BaseDataDO)oldCodeRefDataMap.get(code);
            baseDataDO.setId(oldData.getId());
            updateList.add(baseDataDO);
            this.updateField(baseDataDO, rqParam);
            if (!highTrustability || !versionMgrStarted) continue;
            this.addVersionDataCheck(baseDataDO, oldData, addList, bd1, bd2);
        }
        if (isTreeStyle) {
            this.initParents(addList, endCodeRefParentcodeMap);
            if (upChangeParentMap != null && !upChangeParentMap.isEmpty()) {
                this.initParents(updateList, endCodeRefParentcodeMap);
            }
        }
    }

    private void initField(BaseDataDO data, BaseDataDTO rqParam, int structType) {
        data.putAll((Map)rqParam);
        if (structType == 3 && data.getParentcode() == null) {
            data.put("checkParentDisable", (Object)true);
            this.baseDataModifyService.setBasedataLevelCode(data);
        }
        this.baseDataModifyService.initFieldVal(data);
    }

    private void updateField(BaseDataDO data, BaseDataDTO rqParam) {
        data.put("defineStr", rqParam.get((Object)"defineStr"));
        data.put("baseDataDefineDO", rqParam.get((Object)"baseDataDefineDO"));
        data.put("versionData", rqParam.get((Object)"versionData"));
        this.baseDataModifyService.updateField(data);
    }

    private void initParents(List<BaseDataDO> dataList, Map<String, String> parentMap) {
        String parentcode = null;
        for (BaseDataDO data : dataList) {
            parentcode = data.getParentcode();
            if (parentcode == null || data.getParents() != null) continue;
            data.setParents(data.getCode());
            this.setParents(data, parentcode, parentMap);
            data.put("parentsInited", (Object)true);
        }
    }

    private void setParents(BaseDataDO data, String parentcode, Map<String, String> parentMap) {
        if ("-".equals(parentcode) || !StringUtils.hasText(parentcode)) {
            return;
        }
        for (String pcode : data.getParents().split("\\/")) {
            if (!parentcode.equals(pcode)) continue;
            throw new RuntimeException("\u540c\u6b65\u5f02\u5e38:\u3010" + data.getCode() + "\u3011\u6570\u636e\u51fa\u73b0\u73af\u94fe\u3002");
        }
        data.setParents(parentcode + "/" + data.getParents());
        this.setParents(data, parentMap.get(parentcode), parentMap);
    }

    private void addVersionDataCheck(BaseDataDO basedata, BaseDataDO oldData, List<BaseDataDO> addList, BigDecimal bd1, BigDecimal bd2) {
        if (oldData.getValidtime() == null) {
            oldData.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
        }
        if (oldData.getInvalidtime() == null) {
            oldData.setInvalidtime(BaseDataConsts.VERSION_MAX_DATE);
        }
        Date newValidtime = basedata.getValidtime();
        Date newInvalidtime = basedata.getInvalidtime();
        Date oldValidtime = oldData.getValidtime();
        Date oldInvalidtime = oldData.getInvalidtime();
        boolean validFlag = false;
        boolean invalidFlag = false;
        if (newValidtime.compareTo(oldValidtime) == 0) {
            validFlag = true;
        }
        if (newInvalidtime.compareTo(oldInvalidtime) == 0) {
            invalidFlag = true;
        }
        if (validFlag && invalidFlag) {
            return;
        }
        BaseDataDTO copyData = new BaseDataDTO();
        copyData.putAll((Map)basedata);
        copyData.putAll((Map)oldData);
        if (!validFlag) {
            copyData.setId(UUID.randomUUID());
            copyData.setValidtime(oldValidtime);
            copyData.setInvalidtime(newValidtime);
            bd1 = bd1.add(bd2);
            copyData.put("BASEDATA_FIXED_VER", (Object)bd1);
            copyData.setVer(bd1);
            addList.add((BaseDataDO)copyData);
        }
        if (!invalidFlag) {
            if (!validFlag) {
                copyData = new BaseDataDTO();
                copyData.putAll((Map)basedata);
                copyData.putAll((Map)oldData);
            }
            copyData.setId(UUID.randomUUID());
            copyData.setValidtime(newInvalidtime);
            copyData.setInvalidtime(oldInvalidtime);
            bd1 = bd1.add(bd2);
            copyData.put("BASEDATA_FIXED_VER", (Object)bd1);
            copyData.setVer(bd1);
            addList.add((BaseDataDO)copyData);
        }
    }

    private void cleanTable(BaseDataDTO rqParam) {
        SqlDTO sqlDTO = new SqlDTO(rqParam.getTenantName(), null);
        String tableName = rqParam.getTableName();
        StringBuilder sqlTemp = new StringBuilder();
        sqlTemp.append("delete from ").append(tableName);
        List sharefields = (List)rqParam.get((Object)"sharefields");
        if (sharefields != null && !sharefields.isEmpty()) {
            sqlTemp.append(" where 1 = 1 ");
            for (String field : sharefields) {
                if (!rqParam.containsKey((Object)field)) continue;
                sqlTemp.append(" and ").append(field.toUpperCase()).append(" = '").append(rqParam.get((Object)field).toString()).append("' ");
            }
        }
        sqlDTO.setSql(sqlTemp.toString());
        this.commonDao.executeBySql(sqlDTO);
        sqlTemp.setLength(0);
        sqlTemp.append("delete from ").append(tableName).append("_SUBLIST");
        if (sharefields != null && !sharefields.isEmpty()) {
            sqlTemp.append(" where masterid not in (select id from ").append(tableName).append(")");
        }
        sqlDTO.setSql(sqlTemp.toString());
        try {
            this.commonDao.executeBySql(sqlDTO);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void updateCache(BaseDataDTO rqParam, boolean isForcus) {
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(rqParam.getTenantName());
        bdsc.setBaseDataDTO(rqParam);
        bdsc.setForceUpdate(isForcus);
        this.baseDataCacheService.pushSyncMsg(bdsc);
    }

    public R batchAdd(BaseDataBatchOptDTO baseDataDTO) {
        return this.sync(baseDataDTO);
    }

    public R batchUpdate(BaseDataBatchOptDTO baseDataDTO) {
        return this.sync(baseDataDTO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R forceBatchRemove(BaseDataBatchOptDTO baseDataDTO) {
        String tableName = baseDataDTO.getQueryParam().getTableName();
        DataModelDO dataModel = this.getDataModel(baseDataDTO.getTenantName(), tableName);
        if (dataModel == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.modelDefinition.not.found", tableName));
        }
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
        try {
            String sqlTemp = "delete from " + dataModel.getName() + " where ID = '%s'";
            SqlDTO sqlDTO = new SqlDTO(baseDataDTO.getTenantName(), null);
            BaseDataDTO tempData = new BaseDataDTO();
            tempData.putAll((Map)baseDataDTO.getQueryParam());
            List list = baseDataDTO.getDataList();
            BaseDataDO dataDO = null;
            for (int i = 0; i < list.size(); ++i) {
                dataDO = (BaseDataDO)list.get(i);
                sqlDTO.setSql(String.format(sqlTemp, dataDO.getId()));
                commonDao.executeBySql(sqlDTO);
                if (i % 500 == 0 || i == list.size() - 1) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
                tempData.setId(dataDO.getId());
                tempData.setObjectcode(dataDO.getObjectcode());
                this.sendEvent(tempData, BaseDataOption.EventType.DELETE);
            }
            JDialectUtil jDialect = JDialectUtil.getInstance();
            String subTableName = dataModel.getName() + "_SUBLIST";
            JTableModel jtm = new JTableModel(baseDataDTO.getTenantName(), subTableName);
            if (jDialect.hasTable(jtm)) {
                sqlTemp = "delete from %s where MASTERID not in (select ID from %s)";
                sqlDTO.setSql(String.format(sqlTemp, subTableName, tableName));
                commonDao.executeBySql(sqlDTO);
            }
            baseDataDTO.getQueryParam().setExtInfo(baseDataDTO.getExtInfo());
            this.forceRemoveSubTable(baseDataDTO.getQueryParam(), list, commonDao);
            sqlSession.commit();
            sqlSession.clearCache();
            this.updateCache(baseDataDTO.getQueryParam(), true);
            R r = R.ok();
            return r;
        }
        catch (Exception e) {
            sqlSession.rollback();
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            sqlSession.close();
        }
    }

    private void forceRemoveSubTable(BaseDataDTO param, List<BaseDataDO> list, CommonDao commonDao) {
        String subTableName = (String)param.getExtInfo("subTableName");
        if (!StringUtils.hasText(subTableName)) {
            return;
        }
        DataModelDO dataModel = this.getDataModel(param.getTenantName(), subTableName);
        if (dataModel == null) {
            return;
        }
        Map subMianTableRefInfo = (Map)param.getExtInfo("subMianTableRefInfo");
        SqlDTO sqlDTO = new SqlDTO(param.getTenantName(), null);
        StringBuilder sqlTemp = null;
        boolean flag = true;
        for (BaseDataDO data : list) {
            flag = true;
            sqlTemp = new StringBuilder("delete from " + dataModel.getName() + " where 1=1 ");
            for (Map.Entry entry : subMianTableRefInfo.entrySet()) {
                sqlTemp.append(" and ").append(((String)entry.getKey()).toUpperCase()).append("='");
                if (data.get(entry.getValue()) != null) {
                    sqlTemp.append(data.get(entry.getValue()));
                } else if (param.get(entry.getValue()) != null) {
                    sqlTemp.append(param.get(entry.getValue()));
                } else {
                    flag = false;
                    break;
                }
                sqlTemp.append("'");
            }
            if (!flag) continue;
            sqlDTO.setSql(sqlTemp.toString());
            commonDao.executeBySql(sqlDTO);
        }
    }

    private DataModelDO getDataModel(String tenantName, String tableName) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(tenantName);
        dataModelDTO.setName(tableName);
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
        if (dataModel != null) {
            dataModel.setTenantName(tenantName);
        }
        return dataModel;
    }
}

