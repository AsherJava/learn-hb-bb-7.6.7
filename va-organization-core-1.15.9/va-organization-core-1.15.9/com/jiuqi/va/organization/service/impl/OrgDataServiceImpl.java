/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.organization.service.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.organization.common.OrgConstants;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.domain.OrgDataRefAddDTO;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.help.OrgContextService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import com.jiuqi.va.organization.service.impl.help.OrgDataModifyService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import com.jiuqi.va.organization.service.impl.help.OrgDataSyncService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaOrgDataServiceImpl")
public class OrgDataServiceImpl
implements OrgDataService {
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    private OrgDataParamService orgDataParamService;
    @Autowired
    private OrgDataQueryService orgDataQueryService;
    @Autowired
    private OrgDataModifyService orgDataModifyService;
    @Autowired
    private OrgDataSyncService orgDataSyncService;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private OrgContextService orgContextService;
    private static final String updateBizType = "_updateBizType";
    private static final String changeParent = "changeParent";
    private static final String changeStopflag = "changeStopflag";
    private static final String changeRecoveryflag = "changeRecoveryflag";

    private boolean bindUser() {
        if (ThreadContext.get((Object)"LOGIN_USER_KEY") != null) {
            return false;
        }
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            ShiroUtil.bindUser((UserLoginDTO)user);
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public OrgDO get(OrgDTO orgDTO) {
        boolean bindFlag = this.bindUser();
        try {
            OrgDO orgDO = this.orgDataQueryService.get(orgDTO);
            return orgDO;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int count(OrgDTO orgDTO) {
        boolean bindFlag = this.bindUser();
        try {
            int n = this.orgDataQueryService.count(orgDTO);
            return n;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PageVO<OrgDO> list(OrgDTO orgDTO) {
        boolean bindFlag = this.bindUser();
        try {
            R rs = this.orgDataParamService.loadExtendParam(orgDTO, OrgDataAction.QUERY);
            if (rs.getCode() != 0) {
                PageVO page = new PageVO(true);
                page.setRs(rs);
                PageVO pageVO = page;
                return pageVO;
            }
            if (orgDTO.containsKey((Object)"_InterceptorDataList")) {
                PageVO pageVO = (PageVO)orgDTO.get((Object)"_InterceptorDataList");
                return pageVO;
            }
            PageVO<OrgDO> pageVO = this.orgDataQueryService.list(orgDTO);
            return pageVO;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    @Override
    public R checkIsLeaf(OrgDTO orgDTO) {
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDTO.getCategoryname());
        param.setVersionDate(orgDTO.getVersionDate());
        param.setStopflag(orgDTO.getStopflag());
        param.setAuthType(orgDTO.getAuthType());
        param.setParentcode(orgDTO.getCode());
        PageVO<OrgDO> orglist = this.orgDataQueryService.list(param);
        R res = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        res.put("isLeaf", (Object)(orglist.getTotal() == 0 ? 1 : 0));
        return res;
    }

    @Override
    public R checkUnique(OrgDTO param) {
        List zbs = param.getCheckUniqueZbs();
        if (zbs == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.check.unique.no.field", new Object[0]));
        }
        OrgDTO allParam = new OrgDTO();
        allParam.setAuthType(OrgDataOption.AuthType.NONE);
        allParam.setCategoryname(param.getCategoryname());
        allParam.setVersionDate(param.getVersionDate());
        allParam.setStopflag(Integer.valueOf(-1));
        allParam.setRecoveryflag(Integer.valueOf(-1));
        boolean unique = true;
        Object val = null;
        PageVO<OrgDO> all = null;
        block0: for (String zb : zbs) {
            val = param.getValueOf(zb);
            if (val == null) continue;
            allParam.put(zb, val);
            all = this.orgDataQueryService.list(allParam);
            if (all.getTotal() == 0) continue;
            if (param.getId() == null) {
                unique = false;
                break;
            }
            for (OrgDO orgDO : all.getRows()) {
                if (orgDO.getId().equals(param.getId())) continue;
                unique = false;
                continue block0;
            }
        }
        if (unique) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R add(OrgDTO orgDTO) {
        boolean ignoreCategoryAdd = orgDTO.containsKey((Object)"ignoreCategoryAdd") && (Boolean)orgDTO.get((Object)"ignoreCategoryAdd") != false;
        String categoryname = orgDTO.getCategoryname();
        if (!(ignoreCategoryAdd || this.orgContextService.isAddFromOtherAllow() || categoryname.equalsIgnoreCase("MD_ORG"))) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.add.from.other.category", categoryname));
        }
        String code = orgDTO.getCode();
        if (StringUtils.hasText(code) && (code.contains(";") || code.contains(",") || code.contains("/"))) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.add.code.contains.special.characters", new Object[0]));
        }
        if (code.replaceAll("\\s*", "").length() != code.length()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.add.code.contains.spaces", new Object[0]));
        }
        R rs = this.orgDataParamService.checkModify(orgDTO, false);
        if (rs.getCode() != 0) {
            return rs;
        }
        R extRs = this.orgDataParamService.loadExtendParam(orgDTO, OrgDataAction.Add);
        if (extRs.getCode() != 0) {
            return extRs;
        }
        OrgDO oldOrg = (OrgDO)rs.get((Object)"oldOrg");
        if (oldOrg != null && oldOrg.getRecoveryflag() == 0) {
            return R.error((int)3, (String)OrgCoreI18nUtil.getMessage("org.error.orgdata.add.code.duplicate", new Object[0]));
        }
        if (!StringUtils.hasText(orgDTO.getOrgcode())) {
            orgDTO.setOrgcode(code);
        }
        if ((rs = this.orgDataParamService.checkOrgCode(orgDTO, OrgDataAction.Add)).getCode() != 0) {
            return rs;
        }
        rs = this.orgDataParamService.checkFieldValueValid(orgDTO);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (oldOrg != null && oldOrg.getRecoveryflag() == 1) {
            orgDTO.setId(oldOrg.getId());
            orgDTO.setRecoveryflag(Integer.valueOf(0));
            this.setStopFlag(orgDTO);
            this.orgDataModifyService.update(orgDTO);
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        this.setStopFlag(orgDTO);
        if (!ignoreCategoryAdd && this.orgContextService.isAutoCreateAdminOrgFromOther() && !categoryname.equalsIgnoreCase("MD_ORG")) {
            OrgDTO mdOrg = new OrgDTO();
            mdOrg.putAll((Map)orgDTO);
            mdOrg.setCategoryname("MD_ORG");
            Calendar ca = Calendar.getInstance();
            ca.set(9998, 0, 1);
            Date versionDate = ca.getTime();
            mdOrg.setVersionDate(versionDate);
            R rs2 = this.add(mdOrg);
            if (rs2.getCode() == 3) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.add.admin.org.existed", new Object[0]));
            }
            if (rs2.getCode() != 0) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.add.admin.org.creating", rs2.getMsg()));
            }
        }
        int flag = this.orgDataModifyService.add(orgDTO);
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u65b0\u5efa", (String)orgDTO.getCategoryname(), (String)orgDTO.getCode(), (String)(flag > 0 ? "\u65b0\u5efa\u6210\u529f" : "\u65b0\u5efa\u5931\u8d25"));
        if (flag > 0) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    private void setStopFlag(OrgDTO orgDTO) {
        if (!StringUtils.hasText(orgDTO.getParentcode()) || "-".equals(orgDTO.getParentcode())) {
            orgDTO.setStopflag(Integer.valueOf(0));
            orgDTO.setParentcode("-");
            orgDTO.setParents(orgDTO.getCode());
        } else {
            OrgDTO param = new OrgDTO();
            param.setCategoryname(orgDTO.getCategoryname());
            param.setVersionDate(orgDTO.getVersionDate());
            param.setCode(orgDTO.getParentcode());
            param.setStopflag(Integer.valueOf(-1));
            param.setRecoveryflag(Integer.valueOf(-1));
            param.setAuthType(OrgDataOption.AuthType.NONE);
            OrgDO pOrg = this.orgDataQueryService.get(param);
            if (pOrg != null) {
                orgDTO.setStopflag(pOrg.getStopflag());
                orgDTO.setParents(pOrg.getParents() + "/" + orgDTO.getCode());
            } else {
                orgDTO.setStopflag(Integer.valueOf(0));
                orgDTO.setParentcode("-");
                orgDTO.setParents(orgDTO.getCode());
            }
        }
    }

    @Override
    @Deprecated
    public R relAdd(OrgDTO orgDTO) {
        OrgDTO target = new OrgDTO();
        target.setCategoryname(orgDTO.getCategoryname());
        target.setVersionDate(orgDTO.getVersionDate());
        target.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        target.setParentcode(orgDTO.getParentcode());
        OrgDTO source = new OrgDTO();
        source.setCategoryname("MD_ORG");
        Calendar ca = Calendar.getInstance();
        ca.set(9998, 0, 1);
        source.setVersionDate(ca.getTime());
        source.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        source.setOrgCodes(orgDTO.getOrgCodes());
        OrgDataRefAddDTO orgDateRefAddDTO = new OrgDataRefAddDTO();
        orgDateRefAddDTO.setTarget(target);
        orgDateRefAddDTO.setSource(source);
        orgDateRefAddDTO.setAutoMatchParent(true);
        orgDateRefAddDTO.setSyncExtField(false);
        return this.refAdd(orgDateRefAddDTO);
    }

    @Override
    public R refAdd(OrgDataRefAddDTO orgDateRefAddDTO) {
        String targetParentCode;
        OrgDTO target = orgDateRefAddDTO.getTarget();
        R rs = this.orgDataParamService.checkModifyVersionData(target);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDTO source = orgDateRefAddDTO.getSource();
        List orgCodes = source.getOrgCodes();
        if (orgCodes == null || orgCodes.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.reladd.not.selected", new Object[0]));
        }
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(source.getCategoryname());
        orgDataDTO.setVersionDate(source.getVersionDate());
        orgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDataDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDataDTO.setStopflag(Integer.valueOf(-1));
        orgDataDTO.setOrgCodes(orgCodes);
        PageVO<OrgDO> orgRefList = this.orgDataQueryService.list(orgDataDTO);
        if (orgRefList.getTotal() == 0) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.reladd.not.exist", new Object[0]));
        }
        OrgDTO baseDTO = new OrgDTO();
        baseDTO.setCategoryname(target.getCategoryname());
        baseDTO.setVersionDate(target.getVersionDate());
        baseDTO.setRecoveryflag(Integer.valueOf(0));
        baseDTO.setAuthType(OrgDataOption.AuthType.NONE);
        baseDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(target.isForceUpdateHistoryVersionData()));
        OrgDTO queryParam = new OrgDTO();
        queryParam.putAll((Map)baseDTO);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        ArrayList<OrgDTO> dataList = new ArrayList<OrgDTO>();
        HashSet<String> parentCodeSet = null;
        if (orgDateRefAddDTO.isAutoMatchParent()) {
            parentCodeSet = new HashSet<String>();
            parentCodeSet.add("-");
            parentCodeSet.add(target.getParentcode());
        }
        if (!StringUtils.hasText(targetParentCode = target.getParentcode())) {
            targetParentCode = "-";
        }
        String sourceParentCode = null;
        OrgDO oldOrgData = null;
        for (OrgDO refData : orgRefList.getRows()) {
            queryParam.setCode(refData.getCode());
            oldOrgData = this.orgDataQueryService.get(queryParam);
            if (oldOrgData != null && oldOrgData.getRecoveryflag() == 0) continue;
            OrgDTO addOrgDataDTO = new OrgDTO();
            if (orgDateRefAddDTO.isSyncExtField()) {
                addOrgDataDTO.putAll((Map)refData);
            }
            addOrgDataDTO.putAll((Map)baseDTO);
            addOrgDataDTO.setId(UUID.randomUUID());
            addOrgDataDTO.setCode(refData.getCode());
            addOrgDataDTO.setOrgcode(refData.getOrgcode());
            addOrgDataDTO.setName(refData.getName());
            addOrgDataDTO.setShortname(refData.getShortname());
            addOrgDataDTO.setStopflag(refData.getStopflag());
            addOrgDataDTO.setParents(null);
            if (parentCodeSet != null) {
                parentCodeSet.add(refData.getCode());
                sourceParentCode = refData.getParentcode();
                if (!parentCodeSet.contains(sourceParentCode)) {
                    queryParam.setCode(sourceParentCode);
                    oldOrgData = this.orgDataQueryService.get(queryParam);
                    if (oldOrgData != null) {
                        parentCodeSet.add(sourceParentCode);
                    }
                }
                if (parentCodeSet.contains(sourceParentCode)) {
                    addOrgDataDTO.setParentcode(sourceParentCode);
                } else {
                    addOrgDataDTO.setParentcode(targetParentCode);
                }
            } else {
                addOrgDataDTO.setParentcode(targetParentCode);
            }
            dataList.add(addOrgDataDTO);
        }
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setQueryParam(baseDTO);
        orgBatchOptDTO.setDataList(dataList);
        rs = this.orgDataParamService.loadBatchOptExtendParam(orgBatchOptDTO, OrgDataAction.Add);
        if (rs.getCode() != 0) {
            return rs;
        }
        rs = this.orgDataSyncService.sync(orgBatchOptDTO);
        if (rs.getCode() == 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u5173\u8054\u521b\u5efa", (String)target.getCategoryname(), (String)"", (String)("\u5173\u8054\u6570\u636e" + JSONUtil.toJSONString((Object)orgCodes)));
        }
        return rs;
    }

    @Override
    public R update(OrgDTO orgDTO) {
        String rsMsg;
        R rs = this.orgDataParamService.checkModify(orgDTO, true);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDO oldOrgDO = (OrgDO)rs.get((Object)"oldOrg");
        if (oldOrgDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.check.not.exist", new Object[0]));
        }
        if (orgDTO.getVer() != null && orgDTO.getVer().compareTo(oldOrgDO.getVer()) < 0) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.update.version.changed", new Object[0]));
        }
        rs = this.orgDataParamService.loadExtendParam(orgDTO, OrgDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (!StringUtils.hasText(orgDTO.getOrgcode())) {
            orgDTO.remove((Object)"orgcode");
        } else {
            R result = this.orgDataParamService.checkOrgCode(orgDTO, OrgDataAction.Update);
            if (result.getCode() != 0) {
                return result;
            }
        }
        rs = this.orgDataParamService.checkFieldValueValid(orgDTO);
        if (rs.getCode() != 0) {
            return rs;
        }
        orgDTO.setId(oldOrgDO.getId());
        orgDTO.remove((Object)"parentcode");
        orgDTO.remove((Object)"parents");
        int flag = this.orgDataModifyService.update(orgDTO);
        if (flag > 0 && orgDTO.isSyncOrgBaseInfo() && "MD_ORG".equalsIgnoreCase(orgDTO.getCategoryname())) {
            Calendar ca = Calendar.getInstance();
            ca.set(9998, 0, 1);
            Date versionDate = ca.getTime();
            OrgDTO subOrgQueryParam = new OrgDTO();
            subOrgQueryParam.setCode(orgDTO.getCode());
            subOrgQueryParam.setAuthType(OrgDataOption.AuthType.NONE);
            subOrgQueryParam.setStopflag(Integer.valueOf(-1));
            subOrgQueryParam.setVersionDate(versionDate);
            OrgDTO subOrgUpdateParam = new OrgDTO();
            for (OrgCategoryDO orgCat : this.orgCategoryService.list(new OrgCategoryDO()).getRows()) {
                if ("MD_ORG".equalsIgnoreCase(orgCat.getName())) continue;
                subOrgQueryParam.setCategoryname(orgCat.getName());
                oldOrgDO = this.orgDataQueryService.get(subOrgQueryParam);
                if (oldOrgDO == null) continue;
                subOrgUpdateParam.clear();
                subOrgUpdateParam.setCategoryname(orgCat.getName());
                subOrgUpdateParam.setId(oldOrgDO.getId());
                subOrgUpdateParam.setName(orgDTO.getName());
                subOrgUpdateParam.setShortname(orgDTO.getShortname());
                subOrgUpdateParam.setVersionDate(versionDate);
                this.orgDataModifyService.update(subOrgUpdateParam);
            }
        }
        String string = rsMsg = flag > 0 ? "\u64cd\u4f5c\u6210\u529f" : "\u64cd\u4f5c\u5931\u8d25";
        if (this.orgContextService.isRecordChangeLog() && orgDTO.containsKey((Object)"changeLogs")) {
            rsMsg = rsMsg + ":" + JSONUtil.toJSONString((Object)orgDTO.get((Object)"changeLogs"));
        }
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u66f4\u65b0", (String)orgDTO.getCategoryname(), (String)orgDTO.getName(), (String)rsMsg);
        return flag > 0 ? R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])) : R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R changeState(OrgDTO orgDTO) {
        R rs = this.orgDataParamService.checkModify(orgDTO, true);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDO oldOrgDO = (OrgDO)rs.get((Object)"oldOrg");
        if (oldOrgDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.check.not.exist", new Object[0]));
        }
        orgDTO.put(updateBizType, (Object)changeStopflag);
        R extRs = this.orgDataParamService.loadExtendParam(orgDTO, OrgDataAction.Update);
        if (extRs.getCode() != 0) {
            return extRs;
        }
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDTO.getCategoryname());
        param.setVersionDate(orgDTO.getVersionDate());
        param.setStopflag(Integer.valueOf(orgDTO.getStopflag() == 0 ? 1 : 0));
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setCode(oldOrgDO.getCode());
        if (orgDTO.get((Object)"includeSub") != null && ((Boolean)orgDTO.get((Object)"includeSub")).booleanValue()) {
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        }
        List<OrgDO> dataList = this.orgDataCacheService.listBasicCacheData(param);
        ArrayList<OrgDO> endList = new ArrayList<OrgDO>();
        for (OrgDO org : dataList) {
            OrgDO endData = new OrgDO();
            endData.setId(org.getId());
            endData.setCode(org.getCode());
            endData.setStopflag(orgDTO.getStopflag());
            endList.add(endData);
        }
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setQueryParam(param);
        orgBatchOptDTO.setDataList(endList);
        orgBatchOptDTO.setHighTrustability(true);
        return this.orgDataSyncService.sync(orgBatchOptDTO);
    }

    @Override
    public R remove(OrgDTO orgDataDTO) {
        R rs = this.orgDataParamService.loadExtendParam(orgDataDTO, OrgDataAction.Remove);
        if (rs.getCode() != 0) {
            return rs;
        }
        rs = this.orgDataParamService.checkRemove(orgDataDTO);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDO oldOrgDO = (OrgDO)rs.get((Object)"oldOrg");
        OrgDTO subOrgDTO = new OrgDTO();
        subOrgDTO.setCategoryname(orgDataDTO.getCategoryname());
        subOrgDTO.setVersionDate(orgDataDTO.getVersionDate());
        subOrgDTO.setCode(orgDataDTO.getCode());
        subOrgDTO.setId(oldOrgDO.getId());
        subOrgDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDataDTO.isForceUpdateHistoryVersionData()));
        subOrgDTO.setRecoveryflag(Integer.valueOf(1));
        int flag = this.orgDataModifyService.update(subOrgDTO);
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u903b\u8f91\u5220\u9664", (String)orgDataDTO.getCategoryname(), (String)orgDataDTO.getCode(), (String)(flag > 0 ? "\u5220\u9664\u6210\u529f" : "\u5220\u9664\u5931\u8d25"));
        return flag > 0 ? R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])) : R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R batchRemove(OrgBatchOptDTO orgBatchOptDTO) {
        R rs = this.orgDataParamService.loadBatchOptExtendParam(orgBatchOptDTO, OrgDataAction.Remove);
        if (rs.getCode() != 0) {
            return rs;
        }
        return this.orgDataSyncService.batchRemove(orgBatchOptDTO);
    }

    @Override
    public R recovery(OrgDTO orgDataDTO) {
        R rs = this.orgDataParamService.checkModifyVersionData(orgDataDTO);
        if (rs.getCode() != 0) {
            return rs;
        }
        List orgKeys = orgDataDTO.getOrgCodes();
        String parentcode = orgDataDTO.getParentcode();
        if (!StringUtils.hasText(parentcode)) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.recovery.parent.node.missing", new Object[0]));
        }
        if (orgKeys == null || orgKeys.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.recovery.codes.missing", new Object[0]));
        }
        orgDataDTO.put(updateBizType, (Object)changeRecoveryflag);
        rs = this.orgDataParamService.loadExtendParam(orgDataDTO, OrgDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDTO oldOrgDataDTO = new OrgDTO();
        oldOrgDataDTO.setCategoryname(orgDataDTO.getCategoryname());
        oldOrgDataDTO.setVersionDate(orgDataDTO.getVersionDate());
        oldOrgDataDTO.setStopflag(Integer.valueOf(-1));
        oldOrgDataDTO.setRecoveryflag(Integer.valueOf(-1));
        OrgDTO recoverParam = new OrgDTO();
        recoverParam.setCategoryname(orgDataDTO.getCategoryname());
        recoverParam.setVersionDate(orgDataDTO.getVersionDate());
        recoverParam.setRecoveryflag(Integer.valueOf(0));
        recoverParam.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDataDTO.isForceUpdateHistoryVersionData()));
        recoverParam.setParentcode(parentcode);
        this.orgDataParamService.getOrgVersion(recoverParam);
        StringBuilder errorMsg = new StringBuilder();
        int successCount = 0;
        OrgDTO tempData = new OrgDTO();
        for (String code : orgKeys) {
            oldOrgDataDTO.setCode(code);
            oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
            OrgDO oldOrgDO = this.orgDataQueryService.get(oldOrgDataDTO);
            if (oldOrgDO != null) {
                oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.MANAGE);
                if (!this.orgDataParamService.isAuthOrg(oldOrgDataDTO)) {
                    errorMsg.append("\u673a\u6784[" + code + "]\uff1a\u7f3a\u5c11\u7ba1\u7406\u6743\u9650<br/>");
                    continue;
                }
                if (oldOrgDO.getRecoveryflag() == 0) {
                    errorMsg.append("\u673a\u6784[" + code + "]\uff1a\u5df2\u7ecf\u662f\u6b63\u5e38\u72b6\u6001<br/>");
                    continue;
                }
                tempData.clear();
                tempData.putAll((Map)recoverParam);
                tempData.setId(oldOrgDO.getId());
                rs = this.orgDataParamService.checkOrgCode(tempData, OrgDataAction.Add);
                if (rs.getCode() != 0) {
                    errorMsg.append("\u673a\u6784[" + code + "]\uff1a\u663e\u793a\u4ee3\u7801\u91cd\u590d<br/>");
                    continue;
                }
                if (this.orgDataModifyService.update(tempData) > 0) {
                    this.resetParents(recoverParam);
                    ++successCount;
                    continue;
                }
                errorMsg.append("\u673a\u6784[" + code + "]\uff1a\u56de\u6536\u5931\u8d25<br/>");
                continue;
            }
            errorMsg.append("\u673a\u6784[" + code + "]\uff1a\u4e0d\u5b58\u5728<br/>");
        }
        R res = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.orgdata.recovery", orgKeys.size(), successCount));
        res.put("errorMsg", (Object)errorMsg);
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u8fd8\u539f", (String)orgDataDTO.getCategoryname(), (String)"", (String)("\u6210\u529f" + successCount + "\u6761"));
        return res;
    }

    @Override
    public R upOrDown(OrgDTO orgDTO, OrgConstants.UpOrDown upOrDown) {
        R rs = this.orgDataParamService.checkModify(orgDTO, true);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDO oldOrgDO = (OrgDO)rs.get((Object)"oldOrg");
        if (oldOrgDO != null) {
            orgDTO.setId(oldOrgDO.getId());
            int res = this.orgDataModifyService.upOrDown(orgDTO, upOrDown);
            if (res > 0) {
                LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u79fb\u52a8", (String)orgDTO.getCategoryname(), (String)orgDTO.getCode(), (String)"");
                return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            }
            if (res == -1) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.moveup.already.top", new Object[0]));
            }
            if (res == -2) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.movedown.already.bottom", new Object[0]));
            }
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.check.not.exist", new Object[0]));
    }

    @Override
    public R move(OrgDTO orgDTO) {
        if (orgDTO.getCode() != null) {
            return this.moveOne(orgDTO);
        }
        return this.moveBatch(orgDTO);
    }

    private R moveBatch(OrgDTO orgDTO) {
        List orgCodes = orgDTO.getOrgCodes();
        if (orgCodes == null || orgCodes.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        OrgDTO tempDTO = new OrgDTO();
        tempDTO.setCategoryname(orgDTO.getCategoryname());
        tempDTO.setVersionDate(orgDTO.getVersionDate());
        tempDTO.setParentcode(orgDTO.getParentcode());
        tempDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        String showType = orgDTO.getShowType() != null ? orgDTO.getShowType() : "CODE&NAME";
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (String code : orgCodes) {
            tempDTO.setCode(code);
            R rs = this.moveOne(tempDTO);
            if (rs.getCode() == 0) continue;
            sb.append("[");
            if (!showType.equals("NAME")) {
                sb.append(code).append(" ");
            }
            sb.append(tempDTO.getName()).append("] ").append(rs.getMsg()).append("<br>");
            ++cnt;
        }
        if (sb.length() > 0) {
            return R.error((int)(cnt == orgCodes.size() ? 1 : 2), (String)sb.toString());
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    private R moveOne(OrgDTO orgDTO) {
        R rs = this.orgDataParamService.checkModify(orgDTO, true);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDO oldOrgDO = (OrgDO)rs.get((Object)"oldOrg");
        if (oldOrgDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.move.not.exist", new Object[0]));
        }
        orgDTO.put(updateBizType, (Object)changeParent);
        rs = this.orgDataParamService.loadExtendParam(orgDTO, OrgDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        orgDTO.setName(oldOrgDO.getName());
        OrgDTO subOrgDTO = new OrgDTO();
        subOrgDTO.setCategoryname(orgDTO.getCategoryname());
        subOrgDTO.setVersionDate(orgDTO.getVersionDate());
        subOrgDTO.setCode(orgDTO.getCode());
        subOrgDTO.setId(oldOrgDO.getId());
        subOrgDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(orgDTO.isForceUpdateHistoryVersionData()));
        subOrgDTO.setParents(oldOrgDO.getParents());
        String targetParentCode = orgDTO.getParentcode();
        if (!StringUtils.hasText(targetParentCode)) {
            targetParentCode = "-";
        }
        subOrgDTO.setParentcode(targetParentCode);
        if (targetParentCode.equals(oldOrgDO.getParentcode())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.move.already.under", new Object[0]));
        }
        if (targetParentCode.equals(oldOrgDO.getCode())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.move.choose.itself", new Object[0]));
        }
        if (targetParentCode.equals("-")) {
            UserLoginDTO currLoginUser = ShiroUtil.getUser();
            if (currLoginUser == null || !"super".equalsIgnoreCase(currLoginUser.getMgrFlag())) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.move.parent.not.management.permission", new Object[0]));
            }
        } else {
            OrgDTO parentParam = new OrgDTO();
            parentParam.setCategoryname(orgDTO.getCategoryname());
            parentParam.setVersionDate(orgDTO.getVersionDate());
            parentParam.setStopflag(Integer.valueOf(-1));
            parentParam.setCode(targetParentCode);
            parentParam.setAuthType(OrgDataOption.AuthType.MANAGE);
            OrgDO targetParentDO = this.orgDataQueryService.get(parentParam);
            if (targetParentDO == null) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.move.parent.not.management.permission", new Object[0]));
            }
            List<String> pstrList = Arrays.asList(targetParentDO.getParents().split("\\/"));
            if (pstrList.contains(oldOrgDO.getCode())) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgdata.move.own.subordinates", new Object[0]));
            }
            subOrgDTO.setParentcode(targetParentCode);
        }
        if (this.orgDataModifyService.move(subOrgDTO) > 0) {
            R r = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            r.put("newParents", subOrgDTO.get((Object)"newParents"));
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u5f02\u52a8", (String)orgDTO.getCategoryname(), (String)orgDTO.getCode(), (String)"");
            return r;
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R batchUpdate(List<OrgDTO> dataList, OrgDTO param) {
        ArrayList<OrgDTO> list = new ArrayList<OrgDTO>();
        if (dataList != null && !dataList.isEmpty()) {
            for (OrgDTO orgDTO : dataList) {
                list.add(orgDTO);
            }
        }
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setQueryParam(param);
        orgBatchOptDTO.setDataList(list);
        return this.sync(orgBatchOptDTO);
    }

    @Override
    public R resetParents(OrgDTO orgDTO) {
        return this.orgDataModifyService.resetParents(orgDTO);
    }

    @Override
    public R sync(OrgBatchOptDTO orgBatchOptDTO) {
        R rs = this.orgDataParamService.loadBatchOptExtendParam(orgBatchOptDTO, OrgDataAction.Sync);
        if (rs.getCode() != 0) {
            return rs;
        }
        rs = this.orgDataSyncService.sync(orgBatchOptDTO);
        if (rs.getCode() == 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u63a5\u53e3\u540c\u6b65", (String)orgBatchOptDTO.getQueryParam().getCategoryname(), (String)"", (String)("\u540c\u6b65\u4e86" + orgBatchOptDTO.getDataList().size() + "\u6761\u6570\u636e"));
        }
        return rs;
    }

    @Override
    public R fastUpDown(OrgDTO orgDTO) {
        R rs = this.orgDataParamService.checkModify(orgDTO, true);
        if (rs.getCode() != 0) {
            return rs;
        }
        int j = 0;
        List orgCodes = orgDTO.getOrgCodes();
        String sortCode = null;
        ArrayList<OrgDO> sortList = new ArrayList<OrgDO>();
        OrgDTO oldOrgDataDTO = new OrgDTO();
        oldOrgDataDTO.setCategoryname(orgDTO.getCategoryname());
        oldOrgDataDTO.setStopflag(Integer.valueOf(-1));
        oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        oldOrgDataDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        oldOrgDataDTO.setParentcode(orgDTO.getParentcode());
        PageVO<OrgDO> page = this.orgDataQueryService.list(oldOrgDataDTO);
        List dataList = page.getRows();
        BigDecimal[] ordinals = new BigDecimal[dataList.size()];
        String targetCode = orgDTO.getCode();
        OrgDO targetObj = null;
        OrgDO tempObj = null;
        for (int i = 0; i < dataList.size(); ++i) {
            tempObj = (OrgDO)dataList.get(i);
            ordinals[i] = tempObj.getOrdinal();
            if (!tempObj.getCode().equals(targetCode)) continue;
            targetObj = tempObj;
        }
        Iterator it = dataList.iterator();
        while (it.hasNext() && j < orgCodes.size()) {
            tempObj = (OrgDO)it.next();
            sortCode = (String)orgCodes.get(j);
            if (!tempObj.getCode().equals(sortCode) || tempObj.getCode().equals(targetCode)) continue;
            sortList.add(tempObj);
            it.remove();
            ++j;
        }
        dataList.addAll(dataList.indexOf(targetObj), sortList);
        ArrayList<OrgDO> endList = new ArrayList<OrgDO>();
        for (int i = 0; i < dataList.size(); ++i) {
            tempObj = (OrgDO)dataList.get(i);
            if (ordinals[i].compareTo(tempObj.getOrdinal()) == 0) continue;
            OrgDO endData = new OrgDO();
            endData.setId(tempObj.getId());
            endData.setCode(tempObj.getCode());
            endData.setOrdinal(ordinals[i]);
            endList.add(endData);
        }
        if (endList.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.info.orgdata.movefast.not.moved", new Object[0]));
        }
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setQueryParam(oldOrgDataDTO);
        orgBatchOptDTO.setDataList(endList);
        orgBatchOptDTO.setHighTrustability(true);
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u5feb\u901f\u79fb\u52a8", (String)orgBatchOptDTO.getQueryParam().getCategoryname(), (String)"", (String)("\u5f71\u54cd\u4e86" + orgBatchOptDTO.getDataList().size() + "\u6761\u6570\u636e"));
        return this.orgDataSyncService.sync(orgBatchOptDTO);
    }

    @Override
    public List<OrgDO> verDiffList(OrgDTO orgDTO) {
        return this.orgDataQueryService.verDiffList(orgDTO);
    }

    @Override
    public R initCache(OrgCategoryDTO orgCatDTO) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setTenantName(orgCatDTO.getTenantName());
        orgDTO.setVersionDate(orgCatDTO.getVersionDate());
        if (orgCatDTO.getName() == null) {
            OrgCategoryDO param = new OrgCategoryDO();
            param.setTenantName(orgCatDTO.getTenantName());
            List catList = this.orgCategoryService.list(param).getRows();
            for (OrgCategoryDO orgCat : catList) {
                orgDTO.setCategoryname(orgCat.getName());
                this.orgDataCacheService.initCache(orgDTO);
            }
        } else {
            orgDTO.setCategoryname(orgCatDTO.getName());
            this.orgDataCacheService.initCache(orgDTO);
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    @Override
    public R syncCache(OrgCategoryDTO orgCatDTO) {
        String tenantName = orgCatDTO.getTenantName();
        OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
        bdsc.setTenantName(tenantName);
        bdsc.setForceUpdate(true);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setTenantName(tenantName);
        if (orgCatDTO.getVersionDate() != null) {
            orgDTO.setVersionDate(orgCatDTO.getVersionDate());
        }
        if (orgCatDTO.getName() == null) {
            OrgCategoryDO param = new OrgCategoryDO();
            param.setTenantName(tenantName);
            List catList = this.orgCategoryService.list(param).getRows();
            for (OrgCategoryDO orgCat : catList) {
                orgDTO.setCategoryname(orgCat.getName());
                bdsc.setOrgDTO(orgDTO);
                this.orgDataCacheService.pushSyncMsg(bdsc);
            }
        } else {
            orgDTO.setCategoryname(orgCatDTO.getName());
            bdsc.setOrgDTO(orgDTO);
            this.orgDataCacheService.pushSyncMsg(bdsc);
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    @Override
    public R cleanCache(OrgCategoryDTO orgCatDTO) {
        String tenantName = orgCatDTO.getTenantName();
        OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
        bdsc.setTenantName(tenantName);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setTenantName(tenantName);
        if (orgCatDTO.getVersionDate() != null) {
            orgDTO.setVersionDate(orgCatDTO.getVersionDate());
            bdsc.setRemove(true);
        } else {
            bdsc.setClean(true);
        }
        if (orgCatDTO.getName() == null) {
            OrgCategoryDO param = new OrgCategoryDO();
            param.setTenantName(tenantName);
            List catList = this.orgCategoryService.list(param).getRows();
            for (OrgCategoryDO orgCat : catList) {
                orgDTO.setCategoryname(orgCat.getName());
                bdsc.setOrgDTO(orgDTO);
                this.orgDataCacheService.pushSyncMsg(bdsc);
            }
        } else {
            orgDTO.setCategoryname(orgCatDTO.getName());
            bdsc.setOrgDTO(orgDTO);
            this.orgDataCacheService.pushSyncMsg(bdsc);
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }
}

