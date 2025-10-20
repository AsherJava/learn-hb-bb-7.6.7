/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$EventType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.event.OrgEvent
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.event.OrgEvent;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.dao.VaOrgDataDao;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.domain.OrgDataUniqueDO;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.impl.help.OrgContextService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import com.jiuqi.va.organization.service.impl.help.OrgDataModifyService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
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
import java.util.Set;
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

@Component
public class OrgDataSyncService {
    private static Logger logger = LoggerFactory.getLogger(OrgDataSyncService.class);
    private static String rsKey = "results";
    private static String rsKeyDataId = "ORGID";
    private static String rsKeyDataCode = "ORGCODE";
    private static String rsKeyStatus = "STATUS";
    private static String rsStatus1 = "DOING";
    private static String rsStatus2 = "DONE";
    private static String rsStatus3 = "FAILURE";
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    private OrgDataParamService orgDataParamService;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private OrgDataModifyService orgDataModifyService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private VaOrgDataDao orgDao;
    @Autowired
    private OrgContextService contextService;

    public R sync(OrgBatchOptDTO param) {
        OrgDTO rqParam = param.getQueryParam();
        if (rqParam == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        List dataList = param.getDataList();
        if (dataList == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.data.empty", new Object[0]));
        }
        DataModelDO dataModel = this.getDataModel(param.getTenantName(), rqParam.getCategoryname());
        if (dataModel == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.check.datamodel.not.exist", new Object[0]));
        }
        rqParam.remove((Object)"stopflag");
        rqParam.remove((Object)"recoveryflag");
        this.orgDataParamService.setVersionDate(rqParam);
        R checkRs = this.orgDataParamService.checkModifyVersionData(rqParam);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        if (param.isTableCover()) {
            this.cleanTable(dataModel);
            this.updateCache(rqParam, true);
        }
        if (dataList.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        if (!param.isHighTrustability() && !param.isFullFieldOverride()) {
            HashMap<String, String> errorResult = new HashMap<String, String>();
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setName(rqParam.getCategoryname());
            orgCategoryDO.setDeepClone(false);
            OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
            orgBatchOptDTO.setQueryParam(rqParam);
            ArrayList<OrgDO> checkList = new ArrayList<OrgDO>();
            orgBatchOptDTO.setDataList(checkList);
            for (OrgDO orgDO : dataList) {
                if (!StringUtils.hasText(orgDO.getOrgcode())) continue;
                checkList.add(orgDO);
            }
            this.orgDataParamService.checkOrgCode(orgBatchOptDTO, errorResult, this.orgCategoryService.get(orgCategoryDO), OrgDataAction.Sync);
            if (!errorResult.isEmpty()) {
                return R.error((String)(OrgCoreI18nUtil.getMessage("org.error.orgdata.add.code.duplicate", new Object[0]) + "\uff1a" + JSONUtil.toJSONString(errorResult.keySet())));
            }
        }
        ArrayList<OrgDO> addList = new ArrayList<OrgDO>();
        ArrayList<OrgDO> updateList = new ArrayList<OrgDO>();
        try {
            if (param.isFullFieldOverride()) {
                this.pickData(param, addList, updateList);
            } else {
                this.convertData(param, addList, updateList);
            }
        }
        catch (Exception e) {
            logger.error("\u540c\u6b65\u5f02\u5e38\uff1a" + rqParam.getCategoryname(), e);
            return R.error((String)e.getMessage());
        }
        ArrayList results = new ArrayList();
        R rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])).put(rsKey, results);
        if (param.isHighTrustability() || param.isFullFieldOverride()) {
            this.syncWithNoCheck(rqParam, addList, updateList, rs, dataModel);
        } else {
            this.syncWithCheck(rqParam, addList, updateList, rs, dataModel);
        }
        return rs;
    }

    private void cleanTable(DataModelDO dataDodel) {
        SqlDTO sqlDTO = new SqlDTO(dataDodel.getTenantName(), null);
        sqlDTO.setSql("delete from " + dataDodel.getName());
        this.commonDao.executeBySql(sqlDTO);
        sqlDTO.setSql("delete from " + dataDodel.getName() + "_SUBLIST");
        try {
            this.commonDao.executeBySql(sqlDTO);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void updateCache(OrgDTO rqParam, boolean forceUpdate) {
        OrgDataSyncCacheDTO odsc = new OrgDataSyncCacheDTO();
        odsc.setTenantName(rqParam.getTenantName());
        odsc.setOrgDTO(rqParam);
        odsc.setForceUpdate(forceUpdate);
        this.orgDataCacheService.pushSyncMsg(odsc);
    }

    private void pickData(OrgBatchOptDTO param, List<OrgDO> addList, List<OrgDO> updateList) {
        String key;
        BigDecimal bd1 = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BigDecimal bd2 = new BigDecimal(1.0E-4, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        param.getQueryParam().setQueryStartVer(bd1);
        List paramDataList = param.getDataList();
        boolean tableCover = param.isTableCover();
        if (tableCover) {
            for (OrgDO orgDO : paramDataList) {
                bd1 = bd1.add(bd2);
                orgDO.setVer(bd1);
                addList.add(orgDO);
            }
            return;
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setTenantName(param.getTenantName());
        queryParam.setCategoryname(param.getQueryParam().getCategoryname());
        queryParam.setVersionDate(param.getQueryParam().getVersionDate());
        if (paramDataList.size() == 1) {
            queryParam.setCode(((OrgDO)paramDataList.get(0)).getCode());
        } else if (paramDataList.size() <= 2000) {
            ArrayList<String> codes = new ArrayList<String>();
            for (OrgDO orgDO : paramDataList) {
                codes.add(orgDO.getCode());
            }
            queryParam.setOrgCodes(codes);
        }
        List<OrgDataUniqueDO> dataList = this.orgDao.selectUniqueInfo(queryParam);
        if (dataList.isEmpty()) {
            for (OrgDO orgDO : param.getDataList()) {
                bd1 = bd1.add(bd2);
                orgDO.setVer(bd1);
                addList.add(orgDO);
            }
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        HashMap<String, UUID> keyMap = new HashMap<String, UUID>();
        for (OrgDataUniqueDO orgDO : dataList) {
            key = orgDO.getCode() + "@" + sdf.format(orgDO.getValidtime());
            keyMap.put(key, orgDO.getId());
        }
        for (OrgDataUniqueDO orgDO : param.getDataList()) {
            key = orgDO.getCode() + "@" + sdf.format(orgDO.getValidtime());
            bd1 = bd1.add(bd2);
            orgDO.setVer(bd1);
            if (keyMap.containsKey(key)) {
                orgDO.setId((UUID)keyMap.get(key));
                updateList.add((OrgDO)orgDO);
                continue;
            }
            addList.add((OrgDO)orgDO);
        }
    }

    private void convertData(OrgBatchOptDTO param, List<OrgDO> addList, List<OrgDO> updateList) {
        OrgDO oldData;
        boolean tableCover = param.isTableCover();
        boolean highTrustability = param.isHighTrustability();
        List dataList = param.getDataList();
        OrgDTO rqParam = param.getQueryParam();
        String code = null;
        String parentcode = null;
        HashMap<String, Object> newCodeRefDataMap = new HashMap<String, Object>();
        HashMap<String, String> newCodeRefParentcodeMap = new HashMap<String, String>();
        for (Object orgDO : dataList) {
            code = orgDO.getCode();
            if (newCodeRefDataMap.containsKey(code)) {
                throw new RuntimeException("\u5b58\u5728\u91cd\u590dcode\u7684\u6570\u636e\uff1a" + code);
            }
            newCodeRefDataMap.put(code, orgDO);
            parentcode = orgDO.getParentcode();
            if ("".equals(parentcode)) {
                parentcode = "-";
            }
            if (parentcode == null) continue;
            newCodeRefParentcodeMap.put(code, parentcode);
        }
        if (tableCover && !newCodeRefParentcodeMap.isEmpty()) {
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
        HashMap<String, String> endCodeRefParentcodeMap = new HashMap<String, String>();
        endCodeRefParentcodeMap.putAll(newCodeRefParentcodeMap);
        HashMap<String, OrgDO> oldCodeRefDataMap = null;
        if (!tableCover) {
            oldCodeRefDataMap = new HashMap<String, OrgDO>();
            HashMap<String, String> oldCodeRefParentcodeMap = new HashMap<String, String>();
            OrgDTO convertParam = new OrgDTO();
            convertParam.setTenantName(rqParam.getTenantName());
            convertParam.setCategoryname(rqParam.getCategoryname());
            convertParam.setVersionDate(rqParam.getVersionDate());
            convertParam.setRecoveryflag(Integer.valueOf(-1));
            convertParam.setStopflag(Integer.valueOf(-1));
            convertParam.setAuthType(OrgDataOption.AuthType.NONE);
            ArrayList<Object> codeList = new ArrayList<Object>();
            codeList.addAll(newCodeRefDataMap.keySet());
            convertParam.setOrgCodes(codeList);
            List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(convertParam);
            if (list != null && !list.isEmpty()) {
                for (OrgDO orgDO : list) {
                    code = orgDO.getCode();
                    oldCodeRefDataMap.put(code, orgDO);
                    parentcode = orgDO.getParentcode();
                    oldCodeRefParentcodeMap.put(code, parentcode);
                }
            }
            if (!newCodeRefParentcodeMap.isEmpty()) {
                upChangeParentMap = new HashMap<String, String>();
                HashSet<String> suppParentSet = new HashSet<String>();
                String tmpCode = null;
                for (Map.Entry entry : newCodeRefParentcodeMap.entrySet()) {
                    tmpCode = (String)entry.getKey();
                    parentcode = (String)entry.getValue();
                    if (!oldCodeRefParentcodeMap.containsKey(tmpCode)) {
                        suppParentSet.add(parentcode);
                        continue;
                    }
                    if (parentcode.equals(oldCodeRefParentcodeMap.get(tmpCode))) continue;
                    upChangeParentMap.put(tmpCode, parentcode);
                    suppParentSet.add(parentcode);
                }
                if (!suppParentSet.isEmpty()) {
                    codeList.clear();
                    codeList.addAll(suppParentSet);
                    list = this.orgDataCacheService.listBasicCacheData(convertParam);
                    if (list != null && !list.isEmpty()) {
                        String[] parents = null;
                        for (OrgDO orgDO : list) {
                            code = orgDO.getCode();
                            parentcode = orgDO.getParentcode();
                            if ("-".equals(parentcode)) {
                                if (endCodeRefParentcodeMap.containsKey(code)) continue;
                                endCodeRefParentcodeMap.put(code, "-");
                                continue;
                            }
                            parents = orgDO.getParents().split("\\/");
                            for (int i = parents.length - 1; i > 0; --i) {
                                if (endCodeRefParentcodeMap.containsKey(parents[i])) continue;
                                endCodeRefParentcodeMap.put(parents[i], parents[i - 1]);
                            }
                        }
                    }
                }
                if (!upChangeParentMap.isEmpty()) {
                    codeList.clear();
                    codeList.addAll(upChangeParentMap.keySet());
                    convertParam.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
                    list = this.orgDataCacheService.listBasicCacheData(convertParam);
                    if (list != null && !list.isEmpty()) {
                        for (OrgDO orgDO : list) {
                            code = orgDO.getCode();
                            oldCodeRefDataMap.put(code, orgDO);
                            parentcode = orgDO.getParentcode();
                            if (!endCodeRefParentcodeMap.containsKey(code)) {
                                endCodeRefParentcodeMap.put(code, parentcode);
                            }
                            if (newCodeRefDataMap.containsKey(code)) continue;
                            oldData = new OrgDO();
                            oldData.putAll((Map)orgDO);
                            oldData.setParents(null);
                            dataList.add(oldData);
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
        OrgDTO tempOrgDTO = new OrgDTO();
        OrgDTO defaultDTO = new OrgDTO();
        defaultDTO.setCategoryname(rqParam.getCategoryname());
        this.orgDataParamService.setDefaultVal(defaultDTO);
        for (OrgDO orgDO : dataList) {
            code = orgDO.getCode();
            bd1 = bd1.add(bd2);
            orgDO.put("ORG_FIXED_VER", (Object)bd1);
            if (tableCover || !oldCodeRefDataMap.containsKey(code)) {
                addList.add(orgDO);
                if (orgDO.getCreateuser() == null) {
                    orgDO.setCreateuser(createUserId);
                }
                this.initField(orgDO, rqParam, defaultDTO, tempOrgDTO);
                continue;
            }
            oldData = (OrgDO)oldCodeRefDataMap.get(code);
            orgDO.setId(oldData.getId());
            updateList.add(orgDO);
            this.updateField(orgDO, rqParam, tempOrgDTO);
            if (!highTrustability) continue;
            tempOrgDTO.clear();
            tempOrgDTO.putAll((Map)orgDO);
            this.orgDataModifyService.checkAndAddVersionData(tempOrgDTO, oldData, addList);
        }
        this.initParents(addList, endCodeRefParentcodeMap);
        if (upChangeParentMap != null && !upChangeParentMap.isEmpty()) {
            this.initParents(updateList, endCodeRefParentcodeMap);
        }
    }

    private void initField(OrgDO data, OrgDTO rqParam, OrgDTO defaultDTO, OrgDTO tempOrgDTO) {
        tempOrgDTO.clear();
        tempOrgDTO.putAll((Map)rqParam);
        tempOrgDTO.putAll((Map)data);
        this.orgDataModifyService.initFieldVal(tempOrgDTO);
        data.putAll((Map)tempOrgDTO);
        for (Map.Entry entry : defaultDTO.entrySet()) {
            data.putIfAbsent(entry.getKey(), entry.getValue());
        }
    }

    private void updateField(OrgDO data, OrgDTO rqParam, OrgDTO tempOrgDTO) {
        tempOrgDTO.clear();
        tempOrgDTO.putAll((Map)rqParam);
        tempOrgDTO.putAll((Map)data);
        this.orgDataModifyService.updateField(tempOrgDTO);
        data.putAll((Map)tempOrgDTO);
    }

    private void initParents(List<OrgDO> dataList, Map<String, String> parentMap) {
        String parentcode = null;
        for (OrgDO data : dataList) {
            parentcode = data.getParentcode();
            if (parentcode == null || data.getParents() != null) continue;
            data.setParents(data.getCode());
            this.setParents(data, parentcode, parentMap);
        }
    }

    private void setParents(OrgDO data, String parentcode, Map<String, String> parentMap) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncWithCheck(OrgDTO rqParam, List<OrgDO> addList, List<OrgDO> updateList, R rts, DataModelDO dataModel) {
        List results = (List)rts.get((Object)rsKey);
        String i18nTrue = OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]);
        String i18nFalse = OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]);
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        VaOrgDataDao orgDataDao = (VaOrgDataDao)sqlSession.getMapper(VaOrgDataDao.class);
        int batchSize = this.contextService.getBatchSubmitSize();
        OrgDTO tempData = new OrgDTO();
        OrgDO orgDO = null;
        R rs = null;
        try {
            int i;
            int addSize = addList.size();
            int updateSize = updateList.size();
            int cnt = 0;
            int total = addSize + updateSize;
            for (i = 0; i < addSize; ++i) {
                orgDO = addList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)orgDO);
                tempData.setCacheSyncDisable(Boolean.valueOf(true));
                tempData.put("hasDataModel", (Object)dataModel);
                tempData.put("ORG_BATCH_DAO", (Object)orgDataDao);
                rs = this.orgDataModifyService.add(tempData) > 0 ? R.ok((String)i18nTrue) : R.error((String)i18nFalse);
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
                orgDO = updateList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)orgDO);
                tempData.setCacheSyncDisable(Boolean.valueOf(true));
                tempData.put("hasDataModel", (Object)dataModel);
                tempData.put("ORG_BATCH_DAO", (Object)orgDataDao);
                rs = this.orgDataModifyService.update(tempData) > 0 ? R.ok((String)i18nTrue) : R.error((String)i18nFalse);
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
            logger.error("\u540c\u6b65\u5f02\u5e38\uff1a" + rqParam.getCategoryname(), e);
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
            this.updateCache(rqParam, false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncWithNoCheck(OrgDTO rqParam, List<OrgDO> addList, List<OrgDO> updateList, R rts, DataModelDO dataModel) {
        List results = (List)rts.get((Object)rsKey);
        String i18nTrue = OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]);
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        VaOrgDataDao vaOrgDataDao = (VaOrgDataDao)sqlSession.getMapper(VaOrgDataDao.class);
        CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
        SqlDTO sqlDTO = new SqlDTO(rqParam.getTenantName(), null);
        sqlDTO.addParam("categoryName", (Object)rqParam.getCategoryname());
        int batchSize = this.contextService.getBatchSubmitSize();
        try {
            R rs;
            int i;
            OrgCategoryDO cgParam = new OrgCategoryDO();
            cgParam.setName(rqParam.getCategoryname());
            cgParam.setDeepClone(false);
            OrgCategoryDO categoryDO = this.orgCategoryService.get(cgParam);
            OrgDTO tempData = new OrgDTO();
            OrgDO orgDO = null;
            int addSize = addList.size();
            int updateSize = updateList.size();
            int cnt = 0;
            int total = addSize + updateSize;
            ArrayList<OrgDO> subBizTemp = new ArrayList<OrgDO>();
            for (i = 0; i < addSize; ++i) {
                orgDO = addList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)orgDO);
                tempData.put("hasDataModel", (Object)dataModel);
                tempData.put("ORG_BATCH_DAO", (Object)vaOrgDataDao);
                rs = R.ok((String)i18nTrue);
                rs.put(rsKeyStatus, (Object)rsStatus3);
                rs.put(rsKeyDataId, (Object)tempData.getId());
                rs.put(rsKeyDataCode, (Object)tempData.getCode());
                results.add(rs);
                vaOrgDataDao.add(tempData);
                if (tempData.containsKey((Object)"hasMultiValues")) {
                    subBizTemp.add(orgDO);
                }
                this.sendEvent(categoryDO, tempData, OrgDataOption.EventType.ADD);
                if (++cnt % batchSize == 0 || cnt == total) {
                    this.updateSubData(subBizTemp, commonDao, sqlDTO, OrgDataOption.EventType.ADD);
                    sqlSession.commit();
                    sqlSession.clearCache();
                    rs.put(rsKeyStatus, (Object)rsStatus1);
                    continue;
                }
                rs.put(rsKeyStatus, (Object)rsStatus2);
            }
            for (i = 0; i < updateSize; ++i) {
                orgDO = updateList.get(i);
                tempData.clear();
                tempData.putAll((Map)rqParam);
                tempData.putAll((Map)orgDO);
                tempData.put("hasDataModel", (Object)dataModel);
                tempData.put("ORG_BATCH_DAO", (Object)vaOrgDataDao);
                rs = R.ok((String)i18nTrue);
                rs.put(rsKeyStatus, (Object)rsStatus3);
                rs.put(rsKeyDataId, (Object)tempData.getId());
                rs.put(rsKeyDataCode, (Object)tempData.getCode());
                results.add(rs);
                vaOrgDataDao.update(tempData);
                if (tempData.containsKey((Object)"hasMultiValues")) {
                    subBizTemp.add(orgDO);
                }
                this.sendEvent(categoryDO, tempData, OrgDataOption.EventType.UPDATE);
                if (++cnt % batchSize == 0 || cnt == total) {
                    this.updateSubData(subBizTemp, commonDao, sqlDTO, OrgDataOption.EventType.UPDATE);
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
            logger.error("\u540c\u6b65\u5f02\u5e38\uff1a" + rqParam.getCategoryname(), e);
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
            this.updateCache(rqParam, false);
        }
    }

    private void updateSubData(List<OrgDO> subBizData, CommonDao commonDao, SqlDTO sqlDTO, OrgDataOption.EventType eventType) {
        if (subBizData == null || subBizData.isEmpty()) {
            return;
        }
        String categoryName = (String)sqlDTO.getParam("categoryName");
        ArrayList<String> zbNameList = (ArrayList<String>)sqlDTO.getParam("zbList");
        String addSubSql = (String)sqlDTO.getParam("addSubSql");
        String deleteSubSql = (String)sqlDTO.getParam("deleteSubSql");
        if (zbNameList == null) {
            zbNameList = new ArrayList<String>();
            OrgCategoryDO categoryDO = new OrgCategoryDO();
            categoryDO.setName(categoryName);
            List<ZBDTO> zbList = this.orgCategoryService.listZB(categoryDO);
            for (ZBDTO zb : zbList) {
                if (zb.getMultiple() == null || zb.getMultiple() != 1 || zb.getReltablename() == null || zb.getRelatetype() == null || 1 != zb.getRelatetype()) continue;
                zbNameList.add(zb.getName());
            }
            sqlDTO.addParam("zbList", zbNameList);
            addSubSql = this.orgDataModifyService.getAddSubSql();
            sqlDTO.addParam("addSubSql", (Object)addSubSql);
            deleteSubSql = this.orgDataModifyService.getDeleteSubSql();
            sqlDTO.addParam("deleteSubSql", (Object)deleteSubSql);
        }
        LinkedHashMap<UUID, Map> valueMap = new LinkedHashMap<UUID, Map>();
        String lowerName = null;
        Object value = null;
        for (String string : zbNameList) {
            lowerName = string.toLowerCase();
            for (OrgDO orgDO : subBizData) {
                valueMap.computeIfAbsent(orgDO.getId(), key -> new LinkedHashMap());
                value = orgDO.get((Object)lowerName);
                if (!(value instanceof ArrayList)) continue;
                ((Map)valueMap.get(orgDO.getId())).put(string, (List)value);
            }
        }
        if (valueMap.isEmpty()) {
            return;
        }
        if (eventType == OrgDataOption.EventType.UPDATE) {
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

    private void sendEvent(OrgCategoryDO categoryDO, OrgDTO param, OrgDataOption.EventType eventType) {
        OrgDTO oldData = null;
        if (eventType == OrgDataOption.EventType.UPDATE) {
            param.setAuthType(OrgDataOption.AuthType.NONE);
            List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(param);
            if (list != null && !list.isEmpty()) {
                oldData = new OrgDTO();
                oldData.putAll((Map)list.get(0));
            }
        }
        OrgEvent event = new OrgEvent((Object)this.getClass().getName());
        event.setEventType(eventType);
        event.setOrgOldDTO(oldData);
        event.setOrgDTO(param);
        event.setOrgCategoryDO(categoryDO);
        try {
            ApplicationContextRegister.publishEvent((ApplicationEvent)event);
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u6570\u636e\u9879\u53d8\u66f4\u540e\u7f6e\u4e8b\u4ef6\u5f02\u5e38", e);
        }
    }

    public R batchRemove(OrgBatchOptDTO orgBatchOptDTO) {
        DataModelDO dataModel;
        List list = orgBatchOptDTO.getDataList();
        if (list == null || list.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.data.empty", new Object[0]));
        }
        OrgDTO queryParam = orgBatchOptDTO.getQueryParam();
        if (queryParam.getVersionDate() == null) {
            queryParam.setVersionDate(new Date());
        }
        if ((dataModel = this.getDataModel(orgBatchOptDTO.getTenantName(), queryParam.getCategoryname())) == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.check.datamodel.not.exist", new Object[0]));
        }
        this.orgDataParamService.getOrgVersion(queryParam);
        R checkRs = this.orgDataParamService.checkModifyVersionData(queryParam);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        Object forceDelete = orgBatchOptDTO.getExtInfo("forceDelete");
        boolean isForceDelete = forceDelete != null && (Boolean)forceDelete != false;
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        ArrayList<UUID> removeIds = new ArrayList<UUID>();
        OrgDTO tempData = new OrgDTO();
        OrgDTO currData = new OrgDTO();
        int rsCnt = 0;
        ArrayList<R> results = new ArrayList<R>();
        int flag = 0;
        R result = null;
        for (OrgDO data : list) {
            if (data.getId() == null || !StringUtils.hasText(data.getCode())) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
            }
            currData.clear();
            currData.putAll((Map)queryParam);
            currData.putAll((Map)data);
            if (!isForceDelete && (result = this.orgDataParamService.checkRemove(currData)).getCode() != 0) {
                result.put("key", (Object)data.getCode());
                results.add(result);
                ++rsCnt;
                continue;
            }
            tempData.clear();
            tempData.putAll((Map)queryParam);
            tempData.setId(data.getId());
            tempData.setRecoveryflag(Integer.valueOf(1));
            tempData.remove((Object)"stopflag");
            tempData.setCacheSyncDisable(Boolean.valueOf(true));
            tempData.put("hasDataModel", (Object)dataModel);
            if (isForceDelete) {
                flag = this.orgDataModifyService.remove(tempData);
                removeIds.add(data.getId());
            } else {
                flag = this.orgDataModifyService.update(tempData);
            }
            if (flag > 0) {
                result = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            } else {
                result = R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
                ++rsCnt;
            }
            result.put("key", (Object)data.getCode());
            results.add(result);
        }
        queryParam.setQueryStartVer(startVer);
        if (isForceDelete) {
            if (this.orgDataCacheService.countCache((OrgDO)queryParam) < 30000) {
                this.updateCache(queryParam, true);
            } else {
                int page;
                int total = removeIds.size();
                if (total > (page = 500)) {
                    HashSet<UUID> tmpList = new HashSet<UUID>();
                    for (int i = 0; i < total; ++i) {
                        tmpList.add((UUID)removeIds.get(i));
                        if (i == 0 || (i + 1) % page != 0 && i != total - 1) continue;
                        this.removeCache(queryParam, tmpList);
                        tmpList.clear();
                    }
                } else {
                    this.removeCache(queryParam, new HashSet<UUID>(removeIds));
                }
                this.updateCache(queryParam, false);
            }
        } else {
            this.updateCache(queryParam, false);
        }
        R r = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        r.put("results", results);
        if (rsCnt > 0) {
            r.put("msg", (Object)OrgCoreI18nUtil.getMessage("org.error.orgdata.delete.check.relate", rsCnt));
        }
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)(isForceDelete ? "\u7269\u7406\u5220\u9664" : "\u903b\u8f91\u5220\u9664"), (String)queryParam.getCategoryname(), (String)"", (String)("\u6570\u636e" + JSONUtil.toJSONString((Object)list)));
        return r;
    }

    private void removeCache(OrgDTO queryParam, Set<UUID> removeIds) {
        OrgDataSyncCacheDTO odsc = new OrgDataSyncCacheDTO();
        odsc.setTenantName(queryParam.getTenantName());
        odsc.setOrgDTO(queryParam);
        odsc.setRemove(true);
        odsc.setRemoveIds(removeIds);
        this.orgDataCacheService.pushSyncMsg(odsc);
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

