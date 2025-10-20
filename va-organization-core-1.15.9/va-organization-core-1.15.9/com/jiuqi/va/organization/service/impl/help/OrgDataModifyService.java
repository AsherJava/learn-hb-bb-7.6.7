/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$EventType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.event.OrgEvent
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  org.apache.ibatis.jdbc.SQL
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.event.OrgEvent;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.organization.common.OrgConstants;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.dao.VaOrgAuthDao;
import com.jiuqi.va.organization.dao.VaOrgDataDao;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.ibatis.jdbc.SQL;
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
public class OrgDataModifyService {
    public static final String ORG_BATCH_DAO = "ORG_BATCH_DAO";
    public static final String INIT_FIELD_FINISHED = "initFieldValFinished";
    public static final String ORG_FIXED_VER = "ORG_FIXED_VER";
    private static Logger logger = LoggerFactory.getLogger(OrgDataModifyService.class);
    @Autowired
    private VaOrgDataDao orgDataDao;
    @Autowired
    private VaOrgAuthDao orgAuthDao;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private OrgDataParamService orgDataParamService;
    @Autowired
    private OrgDataQueryService orgDataQueryService;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int add(OrgDTO orgDTO) {
        int flag = this.addData(orgDTO);
        if (flag > 0) {
            this.updateDetailData(orgDTO);
            orgDTO.setQueryStartVer(orgDTO.getVer());
            this.updateCache(orgDTO, OrgDataOption.EventType.ADD, true);
        }
        return flag;
    }

    private void updateCache(OrgDTO param, OrgDataOption.EventType eventType, boolean needSendEvent) {
        if (needSendEvent) {
            OrgDTO oldData = null;
            if (eventType == OrgDataOption.EventType.UPDATE) {
                OrgDataOption.AuthType authType = param.getAuthType();
                param.setAuthType(OrgDataOption.AuthType.NONE);
                List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(param);
                if (list != null && !list.isEmpty()) {
                    oldData = new OrgDTO();
                    oldData.putAll((Map)list.get(0));
                }
                param.setAuthType(authType);
            }
            OrgCategoryDO cgParam = new OrgCategoryDO();
            cgParam.setName(param.getCategoryname());
            cgParam.setDeepClone(false);
            OrgCategoryDO categoryDO = this.orgCategoryService.get(cgParam);
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
        if (!param.isCacheSyncDisable()) {
            OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
            bdsc.setTenantName(param.getTenantName());
            bdsc.setOrgDTO(param);
            bdsc.setRemove(eventType == OrgDataOption.EventType.DELETE);
            this.orgDataCacheService.pushSyncMsg(bdsc);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateDetailData(OrgDTO orgDTO) {
        if (orgDTO.getId() == null) {
            return;
        }
        if (!orgDTO.containsKey((Object)"hasMultiValues")) {
            return;
        }
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
        try {
            String categoryName = orgDTO.getCategoryname();
            OrgCategoryDO categoryDO = new OrgCategoryDO();
            categoryDO.setName(categoryName);
            List<ZBDTO> zbList = this.orgCategoryService.listZB(categoryDO);
            LinkedHashMap<String, List> valueMap = new LinkedHashMap<String, List>();
            String colName = null;
            Object value = null;
            for (ZBDTO zb : zbList) {
                if (zb.getMultiple() == null || zb.getMultiple() != 1 || zb.getReltablename() == null || zb.getRelatetype() == null || 1 != zb.getRelatetype() || !orgDTO.containsKey((Object)(colName = zb.getName().toLowerCase())) && !orgDTO.containsKey((Object)(colName + "_show")) || !((value = orgDTO.get((Object)colName)) instanceof ArrayList)) continue;
                valueMap.put(colName.toUpperCase(), (List)value);
            }
            if (valueMap.isEmpty()) {
                return;
            }
            SqlDTO sqlDTO = new SqlDTO(orgDTO.getTenantName(), null);
            sqlDTO.addParam("categoryName", (Object)categoryName);
            sqlDTO.addParam("masterid", (Object)orgDTO.getId());
            sqlDTO.setSql(this.getDeleteSubSql());
            for (Map.Entry filed : valueMap.entrySet()) {
                sqlDTO.addParam("fieldname", filed.getKey());
                commonDao.executeBySql(sqlDTO);
            }
            sqlDTO.setSql(this.getAddSubSql());
            for (Map.Entry filed : valueMap.entrySet()) {
                sqlDTO.addParam("fieldname", filed.getKey());
                int order = 1;
                for (String str : (List)filed.getValue()) {
                    sqlDTO.addParam("id", (Object)UUID.randomUUID());
                    sqlDTO.addParam("fieldvalue", (Object)str);
                    sqlDTO.addParam("ordernum", (Object)order++);
                    commonDao.executeBySql(sqlDTO);
                }
            }
            sqlSession.commit();
            sqlSession.clearCache();
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u591a\u9009\u5f02\u5e38", e);
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    public String getAddSubSql() {
        SQL sql = new SQL();
        sql.INSERT_INTO("${param.categoryName}_SUBLIST");
        sql.INTO_COLUMNS(new String[]{"ID", "MASTERID", "FIELDNAME", "FIELDVALUE", "ORDERNUM"});
        sql.INTO_VALUES(new String[]{"#{param.id, jdbcType=VARCHAR}", "#{param.masterid, jdbcType=VARCHAR}", "#{param.fieldname, jdbcType=VARCHAR}", "#{param.fieldvalue, jdbcType=VARCHAR}", "#{param.ordernum, jdbcType=NUMERIC}"});
        return sql.toString();
    }

    public String getDeleteSubSql() {
        SQL sql = new SQL();
        sql.DELETE_FROM("${param.categoryName}_SUBLIST");
        sql.WHERE("MASTERID = #{param.masterid, jdbcType=VARCHAR}");
        sql.WHERE("FIELDNAME = #{param.fieldname, jdbcType=VARCHAR}");
        return sql.toString();
    }

    private int addData(OrgDTO orgDTO) {
        this.initFieldVal(orgDTO);
        if (!StringUtils.hasText(orgDTO.getParents())) {
            this.setParents(orgDTO);
        }
        int flag = 0;
        try {
            if (orgDTO.containsKey((Object)ORG_BATCH_DAO)) {
                ((VaOrgDataDao)orgDTO.get((Object)ORG_BATCH_DAO)).add(orgDTO);
                flag = 1;
            } else {
                flag = this.orgDataDao.add(orgDTO);
            }
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u5165\u5e93\u5931\u8d25: " + JSONUtil.toJSONString((Object)orgDTO), e);
        }
        return flag;
    }

    public void initFieldVal(OrgDTO orgDTO) {
        UserLoginDTO currLoginUser;
        Integer recoveryflag;
        Integer stopflag;
        if (orgDTO.containsKey((Object)INIT_FIELD_FINISHED)) {
            return;
        }
        if (orgDTO.getId() == null) {
            orgDTO.setId(UUID.randomUUID());
        }
        if (!StringUtils.hasText(orgDTO.getOrgcode())) {
            orgDTO.setOrgcode(orgDTO.getCode());
        }
        if ((stopflag = orgDTO.getStopflag()) == null || stopflag != 0 && stopflag != 1) {
            orgDTO.setStopflag(Integer.valueOf(0));
        }
        if ((recoveryflag = orgDTO.getRecoveryflag()) == null || recoveryflag != 0 && recoveryflag != 1) {
            orgDTO.setRecoveryflag(Integer.valueOf(0));
        }
        if (orgDTO.getCreateuser() == null && (currLoginUser = ShiroUtil.getUser()) != null) {
            orgDTO.setCreateuser(currLoginUser.getId());
        }
        if (orgDTO.getCreatetime() == null) {
            orgDTO.setCreatetime(new Date());
        }
        BigDecimal ver = null;
        ver = orgDTO.containsKey((Object)ORG_FIXED_VER) ? (BigDecimal)orgDTO.get((Object)ORG_FIXED_VER) : OrderNumUtil.getOrderNumByCurrentTimeMillis();
        if (orgDTO.getOrdinal() == null) {
            orgDTO.setOrdinal(ver);
        }
        if (!StringUtils.hasText(orgDTO.getParentcode())) {
            orgDTO.setParentcode("-");
        }
        if (orgDTO.getParentcode().equals("-")) {
            orgDTO.setParents(orgDTO.getCode());
        }
        this.setVersionInfo(orgDTO);
        orgDTO.setVer(ver);
        orgDTO.put(INIT_FIELD_FINISHED, (Object)true);
    }

    private void setVersionInfo(OrgDTO orgDTO) {
        OrgVersionDO verDO = this.orgDataParamService.getOrgVersion(orgDTO);
        if (verDO != null) {
            orgDTO.setValidtime(verDO.getValidtime());
            orgDTO.setInvalidtime(verDO.getInvalidtime());
        }
    }

    private void setParents(OrgDTO orgDTO) {
        String parentcode = orgDTO.getParentcode();
        if (!StringUtils.hasText(parentcode) || "-".equals(parentcode)) {
            orgDTO.setParentcode("-");
            orgDTO.setParents(orgDTO.getCode());
            return;
        }
        OrgDTO parent = new OrgDTO();
        parent.setCategoryname(orgDTO.getCategoryname());
        parent.setVersionDate(orgDTO.getVersionDate());
        parent.setCode(parentcode);
        parent.setStopflag(Integer.valueOf(-1));
        parent.setAuthType(OrgDataOption.AuthType.NONE);
        List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(parent);
        if (list == null || list.isEmpty()) {
            orgDTO.setParentcode("-");
            orgDTO.setParents(orgDTO.getCode());
            return;
        }
        orgDTO.setParents(list.get(0).getParents() + "/" + orgDTO.getCode());
    }

    public R resetParents(OrgDTO orgDTO) {
        OrgDTO allOrgParam = new OrgDTO();
        allOrgParam.setCategoryname(orgDTO.getCategoryname());
        allOrgParam.setVersionDate(orgDTO.getVersionDate());
        allOrgParam.setStopflag(Integer.valueOf(-1));
        allOrgParam.setAuthType(OrgDataOption.AuthType.NONE);
        PageVO<OrgDO> page = this.orgDataQueryService.list(allOrgParam);
        if (page.getTotal() == 0) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        HashMap<String, String> codeMap = new HashMap<String, String>();
        codeMap.put("-", "");
        ArrayList<OrgDO> list = new ArrayList<OrgDO>();
        for (Object orgDO : page.getRows()) {
            codeMap.put(orgDO.getCode(), orgDO.getParents());
            list.add(new OrgDO((Map)orgDO));
        }
        HashMap<String, List<OrgDO>> fatherSonMap = new HashMap<String, List<OrgDO>>();
        for (OrgDO orgDO : list) {
            ArrayList<OrgDO> children;
            String parentcode = orgDO.getParentcode();
            if (!StringUtils.hasText(parentcode) || !codeMap.containsKey(parentcode)) {
                parentcode = "-";
            }
            if ((children = (ArrayList<OrgDO>)fatherSonMap.get(parentcode)) == null) {
                children = new ArrayList<OrgDO>();
            }
            children.add(orgDO);
            fatherSonMap.put(parentcode, children);
        }
        List topNodes = (List)fatherSonMap.get("-");
        if (topNodes == null || topNodes.isEmpty()) {
            return R.error((String)"\u4e0d\u5b58\u5728\u6570\u636e");
        }
        for (OrgDO orgDO : topNodes) {
            orgDO.setParentcode("-");
            orgDO.setParents(orgDO.getCode());
            this.resetparents(orgDO, fatherSonMap);
        }
        OrgDTO orgDTO2 = new OrgDTO();
        orgDTO2.setCategoryname(orgDTO.getCategoryname());
        orgDTO2.setVersionDate(orgDTO.getVersionDate());
        orgDTO2.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        this.orgDataParamService.getOrgVersion(orgDTO2);
        BigDecimal startVer = null;
        OrgDTO updateParam = new OrgDTO();
        for (OrgDO orgDO : list) {
            if (((String)codeMap.get(orgDO.getCode())).equals(orgDO.getParents())) continue;
            updateParam.clear();
            updateParam.putAll((Map)orgDTO2);
            updateParam.setParents(orgDO.getParents());
            updateParam.setId(orgDO.getId());
            if (this.updateData(updateParam) <= 0) continue;
            updateParam.setCacheSyncDisable(Boolean.valueOf(true));
            this.updateCache(updateParam, OrgDataOption.EventType.UPDATE, true);
            if (startVer != null) continue;
            startVer = updateParam.getVer();
        }
        if (startVer != null) {
            updateParam.setCacheSyncDisable(Boolean.valueOf(false));
            updateParam.setQueryStartVer(startVer);
            this.updateCache(updateParam, OrgDataOption.EventType.UPDATE, false);
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    private void resetparents(OrgDO father, Map<String, List<OrgDO>> fatherSonMap) {
        if (father == null || fatherSonMap == null || fatherSonMap.isEmpty()) {
            return;
        }
        List<OrgDO> children = fatherSonMap.get(father.getCode());
        if (children == null || children.isEmpty()) {
            return;
        }
        for (OrgDO child : children) {
            String grandParents = father.getParents();
            grandParents = StringUtils.hasText(grandParents) ? grandParents + "/" : "";
            child.setParents(grandParents + child.getCode());
            this.resetparents(child, fatherSonMap);
        }
    }

    public int update(OrgDTO orgDTO) {
        int flag = this.updateData(orgDTO);
        if (flag > 0) {
            this.updateDetailData(orgDTO);
            orgDTO.setQueryStartVer(orgDTO.getVer());
            this.updateCache(orgDTO, OrgDataOption.EventType.UPDATE, true);
        }
        return flag;
    }

    public int updateData(OrgDTO orgDTO) {
        OrgVersionDO versionDO = this.orgDataParamService.getOrgVersion(orgDTO);
        if (versionDO == null) {
            return 0;
        }
        this.updateField(orgDTO);
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDTO.getCategoryname());
        param.setId(orgDTO.getId());
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setVersionDate(orgDTO.getVersionDate());
        param.put("orgVersionData", (Object)versionDO);
        List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(param);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        OrgDO oldOrg = list.get(0);
        this.checkAndAddVersionData(orgDTO, oldOrg, null);
        int flag = 0;
        try {
            if (orgDTO.containsKey((Object)ORG_BATCH_DAO)) {
                ((VaOrgDataDao)orgDTO.get((Object)ORG_BATCH_DAO)).update(orgDTO);
                flag = 1;
            } else {
                flag = this.orgDataDao.update(orgDTO);
            }
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u5165\u5e93\u5931\u8d25: " + JSONUtil.toJSONString((Object)orgDTO), e);
        }
        return flag;
    }

    public void updateField(OrgDTO orgDTO) {
        Integer recoveryflag;
        if (orgDTO.containsKey((Object)INIT_FIELD_FINISHED)) {
            return;
        }
        Integer stopflag = orgDTO.getStopflag();
        if (stopflag == null || stopflag == -1) {
            orgDTO.remove((Object)"stopflag");
        }
        if ((recoveryflag = orgDTO.getRecoveryflag()) == null || recoveryflag == -1) {
            orgDTO.remove((Object)"recoveryflag");
        }
        this.setVersionInfo(orgDTO);
        if (orgDTO.containsKey((Object)ORG_FIXED_VER)) {
            orgDTO.setVer((BigDecimal)orgDTO.get((Object)ORG_FIXED_VER));
        } else {
            orgDTO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        if (orgDTO.containsKey((Object)"ordinal") && orgDTO.getOrdinal() == null) {
            orgDTO.remove((Object)"ordinal");
        }
        orgDTO.put(INIT_FIELD_FINISHED, (Object)true);
    }

    public boolean checkAndAddVersionData(OrgDTO newOrg, OrgDO oldOrg, List<OrgDO> addList) {
        OrgCategoryDO categoryParam = new OrgCategoryDO();
        categoryParam.setName(oldOrg.getCategoryname());
        categoryParam.setDeepClone(false);
        OrgCategoryDO orgCategory = this.orgCategoryService.get(categoryParam);
        Integer versionflag = orgCategory.getVersionflag();
        if (versionflag == null || versionflag == 0) {
            return false;
        }
        Date newValidtime = newOrg.getValidtime();
        Date newInvalidtime = newOrg.getInvalidtime();
        Date oldValidtime = oldOrg.getValidtime();
        Date oldInvalidtime = oldOrg.getInvalidtime();
        boolean validFlag = false;
        boolean invalidFlag = false;
        if (newValidtime.compareTo(oldValidtime) == 0) {
            validFlag = true;
        }
        if (newInvalidtime.compareTo(oldInvalidtime) == 0) {
            invalidFlag = true;
        }
        if (validFlag && invalidFlag) {
            return false;
        }
        OrgDTO copyData = new OrgDTO();
        copyData.putAll((Map)oldOrg);
        copyData.setCategoryname(newOrg.getCategoryname());
        copyData.setVersionDate(newOrg.getVersionDate());
        if (!validFlag) {
            copyData.setId(UUID.randomUUID());
            copyData.setValidtime(oldValidtime);
            copyData.setInvalidtime(newValidtime);
            copyData.setCreatetime(new Date());
            copyData.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            if (addList != null) {
                addList.add((OrgDO)copyData);
            } else {
                if (newOrg.containsKey((Object)ORG_BATCH_DAO)) {
                    ((VaOrgDataDao)newOrg.get((Object)ORG_BATCH_DAO)).add(copyData);
                } else {
                    this.orgDataDao.add(copyData);
                }
                this.updateDetailData(copyData);
            }
        }
        if (!invalidFlag) {
            if (addList != null && !validFlag) {
                copyData = new OrgDTO();
                copyData.putAll((Map)oldOrg);
                copyData.setCategoryname(newOrg.getCategoryname());
                copyData.setVersionDate(newOrg.getVersionDate());
            }
            copyData.setId(UUID.randomUUID());
            copyData.setValidtime(newInvalidtime);
            copyData.setInvalidtime(oldInvalidtime);
            copyData.setCreatetime(new Date());
            copyData.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            if (addList != null) {
                addList.add((OrgDO)copyData);
            } else {
                if (newOrg.containsKey((Object)ORG_BATCH_DAO)) {
                    ((VaOrgDataDao)newOrg.get((Object)ORG_BATCH_DAO)).add(copyData);
                } else {
                    this.orgDataDao.add(copyData);
                }
                this.updateDetailData(copyData);
            }
        }
        return true;
    }

    public int remove(OrgDTO orgDTO) {
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDTO.getCategoryname());
        param.setId(orgDTO.getId());
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setVersionDate(orgDTO.getVersionDate());
        param.put("orgVersionData", orgDTO.get((Object)"orgVersionData"));
        List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(param);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        OrgDO oldOrg = list.get(0);
        param.setCacheSyncDisable(Boolean.valueOf(orgDTO.isCacheSyncDisable()));
        this.setVersionInfo(param);
        boolean isAddVersion = this.checkAndAddVersionData(param, oldOrg, null);
        int flag = this.orgDataDao.remove(param);
        if (flag == 0) {
            return flag;
        }
        this.updateCache(param, OrgDataOption.EventType.DELETE, true);
        if (isAddVersion) {
            this.updateCache(param, null, false);
        } else {
            try {
                this.orgDataDao.removeSub(orgDTO);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            param.setCode(oldOrg.getCode());
            this.removeAuth(param);
        }
        return flag;
    }

    private void removeAuth(OrgDTO orgDTO) {
        OrgAuthDO param = new OrgAuthDO();
        param.setOrgcategory(orgDTO.getCategoryname());
        param.setOrgname(orgDTO.getCode());
        param.setAuthtype(1);
        this.orgAuthDao.delete(param);
    }

    public int upOrDown(OrgDTO orgDTO, OrgConstants.UpOrDown upOrDown) {
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDTO.getCategoryname());
        param.setVersionDate(orgDTO.getVersionDate());
        param.setParentcode(orgDTO.getParentcode());
        param.setRecoveryflag(Integer.valueOf(0));
        param.setStopflag(Integer.valueOf(-1));
        param.setAuthType(OrgDataOption.AuthType.MANAGE);
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO<OrgDO> page = this.orgDataQueryService.list(param);
        if (page.getTotal() == 0) {
            return 0;
        }
        List list = page.getRows();
        if (upOrDown == OrgConstants.UpOrDown.UP && ((OrgDO)list.get(0)).getCode().equals(orgDTO.getCode())) {
            return -1;
        }
        if (upOrDown == OrgConstants.UpOrDown.DOWN && ((OrgDO)list.get(list.size() - 1)).getCode().equals(orgDTO.getCode())) {
            return -2;
        }
        int i = 0;
        for (i = 0; i < list.size() && !((OrgDO)list.get(i)).getCode().equals(orgDTO.getCode()); ++i) {
        }
        OrgDO orgDTO2 = null;
        orgDTO2 = upOrDown == OrgConstants.UpOrDown.UP ? (OrgDO)list.get(i - 1) : (OrgDO)list.get(i + 1);
        BigDecimal temp = ((OrgDO)list.get(i)).getOrdinal();
        OrgDTO baseParam = new OrgDTO();
        baseParam.setCategoryname(orgDTO.getCategoryname());
        baseParam.setVersionDate(orgDTO.getVersionDate());
        baseParam.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        this.orgDataParamService.getOrgVersion(baseParam);
        OrgDTO mParam = new OrgDTO();
        mParam.putAll((Map)baseParam);
        mParam.setId(orgDTO.getId());
        mParam.setOrdinal(orgDTO2.getOrdinal());
        mParam.setCacheSyncDisable(Boolean.valueOf(true));
        if (this.update(mParam) == 0) {
            return 0;
        }
        baseParam.setQueryStartVer(mParam.getVer());
        mParam.clear();
        mParam.putAll((Map)baseParam);
        mParam.setId(orgDTO2.getId());
        mParam.setOrdinal(temp);
        mParam.setCacheSyncDisable(Boolean.valueOf(true));
        if (this.update(mParam) == 0) {
            return 0;
        }
        this.updateCache(baseParam, null, false);
        return 1;
    }

    public int move(OrgDTO orgDTO) {
        List<OrgDO> list;
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDTO.getCategoryname());
        param.setVersionDate(orgDTO.getVersionDate());
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setStopflag(Integer.valueOf(-1));
        param.setRecoveryflag(Integer.valueOf(-1));
        param.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        String oldParents = orgDTO.getParents();
        String newParents = null;
        if (orgDTO.getParentcode().equals("-")) {
            newParents = orgDTO.getCode();
        } else {
            param.setCode(orgDTO.getParentcode());
            list = this.orgDataCacheService.listBasicCacheData(param);
            if (list == null || list.size() != 1) {
                logger.error("\u7236\u7ea7\u4e0d\u5b58\u5728\uff0c\u8f6c\u4f7f\u7528\u6839\u8282\u70b9\uff1a " + JSONUtil.toJSONString((Object)param));
                newParents = orgDTO.getCode();
            } else {
                newParents = list.get(0).getParents() + "/" + orgDTO.getCode();
            }
        }
        if (oldParents.equals(newParents)) {
            return 0;
        }
        param.setCode(orgDTO.getCode());
        param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        list = this.orgDataCacheService.listBasicCacheData(param);
        param.remove((Object)"code");
        param.remove((Object)"stopflag");
        param.remove((Object)"recoveryflag");
        param.setQueryChildrenType(null);
        this.orgDataParamService.getOrgVersion(param);
        BigDecimal startVer = null;
        OrgDTO tempData = new OrgDTO();
        int point = oldParents.length();
        for (OrgDO org : list) {
            tempData.clear();
            tempData.putAll((Map)param);
            tempData.setId(org.getId());
            tempData.setParents(newParents + org.getParents().substring(point));
            if (org.getParents().equals(oldParents)) {
                tempData.setParentcode(orgDTO.getParentcode());
            } else {
                tempData.remove((Object)"parentcode");
            }
            if (this.updateData(tempData) <= 0) continue;
            if (startVer == null) {
                startVer = tempData.getVer();
            }
            tempData.setCacheSyncDisable(Boolean.valueOf(true));
            this.updateCache(tempData, OrgDataOption.EventType.UPDATE, true);
        }
        param.setQueryStartVer(startVer);
        param.setCacheSyncDisable(Boolean.valueOf(false));
        this.updateCache(param, null, false);
        orgDTO.put("newParents", (Object)newParents);
        return 1;
    }
}

