/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.vo.SameChgOrgVersionVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.service.VirtualOrgService;
import com.jiuqi.gcreport.samecontrol.vo.SameChgOrgVersionVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirtualOrgServiceImpl
implements VirtualOrgService {
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    private ThreadLocal<GcOrgCenterService> changeCurrentToolThreadLocal = new ThreadLocal();
    private ThreadLocal<GcOrgCenterService> changeBeforeToolThreadLocal = new ThreadLocal();
    private ThreadLocal<Boolean> isSameCtrlDisposalThreadLocal = new ThreadLocal();
    private static final Logger logger = LoggerFactory.getLogger(VirtualOrgServiceImpl.class);

    @Override
    public List<SameChgOrgVersionVO> listChangeOrg(String orgType, String yyyyTmmmm) {
        YearPeriodObject yp = new YearPeriodObject(null, yyyyTmmmm);
        GcOrgCenterService changeCurrentTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        this.changeCurrentToolThreadLocal.set(changeCurrentTool);
        List orgCacheTree = changeCurrentTool.getOrgTree();
        YearPeriodObject previousYearPeriod = this.getPreviousPeriodStr(yyyyTmmmm);
        GcOrgCenterService changeBeforeTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)previousYearPeriod);
        this.changeBeforeToolThreadLocal.set(changeBeforeTool);
        List orgAllCaches = changeBeforeTool.listAllOrgByParentIdContainsSelf(null);
        this.isSameCtrlDisposalThreadLocal.set(false);
        return this.listSameChgOrgVersion(orgCacheTree, orgAllCaches);
    }

    @Override
    public List<SameChgOrgVersionVO> listNoSameCtrlDisposalOrg(String orgType, String yyyyTmmmm) {
        YearPeriodObject yp = new YearPeriodObject(null, yyyyTmmmm);
        YearPeriodObject previousYearPeriod = this.getPreviousPeriodStr(yyyyTmmmm);
        GcOrgCenterService changePreviousTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)previousYearPeriod);
        this.changeCurrentToolThreadLocal.set(changePreviousTool);
        List orgCacheTree = changePreviousTool.getOrgTree();
        GcOrgCenterService changeCurrentTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        this.changeBeforeToolThreadLocal.set(changeCurrentTool);
        List orgAllCaches = changeCurrentTool.listAllOrgByParentIdContainsSelf(null);
        this.isSameCtrlDisposalThreadLocal.set(true);
        return this.listSameChgOrgVersion(orgCacheTree, orgAllCaches);
    }

    private List<SameChgOrgVersionVO> listSameChgOrgVersion(List<GcOrgCacheVO> orgCacheTree, List<GcOrgCacheVO> orgAllCaches) {
        ArrayList<SameChgOrgVersionVO> sameChgOrgVersions = new ArrayList<SameChgOrgVersionVO>();
        ArrayList<String> changeCommonOrgs = new ArrayList<String>();
        HashMap<String, GcOrgCacheVO> orgCacheCodeMapping = new HashMap<String, GcOrgCacheVO>();
        for (GcOrgCacheVO orgCache : orgAllCaches) {
            orgCacheCodeMapping.put(orgCache.getCode(), orgCache);
        }
        for (GcOrgCacheVO orgCache : orgCacheTree) {
            if (orgCache.isRecoveryFlag()) continue;
            GcOrgCacheVO orgCacheValue = (GcOrgCacheVO)orgCacheCodeMapping.get(orgCache.getCode());
            if (orgCacheValue == null) {
                SameChgOrgVersionVO sameChgOrgVersion = new SameChgOrgVersionVO();
                if (this.isSameCtrlDisposalThreadLocal.get().booleanValue()) {
                    sameChgOrgVersion.setChangeBeforeOrg(orgCache);
                } else {
                    sameChgOrgVersion.setChangeCurrentOrg(orgCache);
                }
                sameChgOrgVersions.add(sameChgOrgVersion);
                changeCommonOrgs.add(orgCache.getCode());
                continue;
            }
            this.addChangeOrgList(orgCacheValue, orgCache, changeCommonOrgs, sameChgOrgVersions, orgCacheCodeMapping);
        }
        return sameChgOrgVersions;
    }

    private void getChangeOrg(List<String> changeCommonOrgs, List<GcOrgCacheVO> childrenOrgTree, List<SameChgOrgVersionVO> sameChgOrgVersions, Map<String, GcOrgCacheVO> orgCacheCodeMapping) {
        for (GcOrgCacheVO orgCache : childrenOrgTree) {
            if (GcOrgKindEnum.DIFFERENCE.equals((Object)orgCache.getOrgKind()) || orgCache.isRecoveryFlag()) continue;
            GcOrgCacheVO orgCacheValue = orgCacheCodeMapping.get(orgCache.getCode());
            if (orgCacheValue == null) {
                SameChgOrgVersionVO sameChgOrgVersion = new SameChgOrgVersionVO();
                if (this.isSameCtrlDisposalThreadLocal.get().booleanValue()) {
                    sameChgOrgVersion.setChangeBeforeOrg(orgCache);
                } else {
                    sameChgOrgVersion.setChangeCurrentOrg(orgCache);
                }
                sameChgOrgVersions.add(sameChgOrgVersion);
                changeCommonOrgs.add(orgCache.getCode());
                continue;
            }
            this.addChangeOrgList(orgCacheValue, orgCache, changeCommonOrgs, sameChgOrgVersions, orgCacheCodeMapping);
        }
    }

    private void addChangeOrgList(GcOrgCacheVO orgCacheValue, GcOrgCacheVO orgCache, List<String> changeCommonOrgs, List<SameChgOrgVersionVO> sameChgOrgVersions, Map<String, GcOrgCacheVO> orgCacheCodeMapping) {
        if (!(orgCache.getParentStr().equals(orgCacheValue.getParentStr()) || StringUtils.isEmpty((String)this.getCommonUnit(orgCache.getParents(), orgCacheValue.getParents())) || orgCache.isRecoveryFlag())) {
            boolean parentsChangflag = false;
            for (String parentsCode : orgCache.getParents()) {
                if (!changeCommonOrgs.contains(parentsCode)) continue;
                parentsChangflag = true;
                break;
            }
            if (!parentsChangflag && this.checkOrgParentCode(orgCache, this.changeCurrentToolThreadLocal.get()) && this.checkOrgParentCode(orgCacheValue, this.changeBeforeToolThreadLocal.get())) {
                SameChgOrgVersionVO sameChgOrgVersion = new SameChgOrgVersionVO();
                sameChgOrgVersion.setChangeCurrentOrg(orgCache);
                sameChgOrgVersion.setChangeBeforeOrg(orgCacheValue);
                sameChgOrgVersions.add(sameChgOrgVersion);
                changeCommonOrgs.add(orgCache.getCode());
            }
        }
        if (orgCache.getChildren().size() > 0) {
            this.getChangeOrg(changeCommonOrgs, orgCache.getChildren(), sameChgOrgVersions, orgCacheCodeMapping);
        }
    }

    private boolean checkOrgParentCode(GcOrgCacheVO gcOrgCache, GcOrgCenterService gcOrgCenterTool) {
        for (String code : gcOrgCache.getParents()) {
            GcOrgCacheVO orgCache = gcOrgCenterTool.getOrgByCode(code);
            if (orgCache != null) continue;
            return false;
        }
        return true;
    }

    @Override
    public SameChgOrgVersionVO getChangeOrg(String orgType, String yyyyTmmmm, String changeCode) {
        YearPeriodObject yp = new YearPeriodObject(null, yyyyTmmmm);
        GcOrgCacheVO gcOrgCache = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp).getOrgByCode(changeCode);
        YearPeriodObject previousPeriodObject = this.getPreviousPeriodStr(yyyyTmmmm);
        GcOrgCacheVO gcPreviousOrgCache = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)previousPeriodObject).getOrgByCode(changeCode);
        SameChgOrgVersionVO sameChgOrgVersion = new SameChgOrgVersionVO();
        if (gcOrgCache != null) {
            if (gcPreviousOrgCache != null) {
                if (!StringUtils.isEmpty((String)this.getCommonUnit(gcOrgCache.getParents(), gcPreviousOrgCache.getParents()))) {
                    sameChgOrgVersion.setChangeBeforeOrg(gcPreviousOrgCache);
                    sameChgOrgVersion.setChangeCurrentOrg(gcOrgCache);
                    return sameChgOrgVersion;
                }
                throw new BusinessRuntimeException("\u53d8\u52a8\u5355\u4f4d" + changeCode + "\u5728\u5f53\u524d\u671f\u548c\u4e0a\u4e00\u671f\u5355\u4f4d\u7248\u672c\u4e0d\u5b58\u5728\u5171\u540c\u4e0a\u7ea7\u3002");
            }
            sameChgOrgVersion = new SameChgOrgVersionVO();
            sameChgOrgVersion.setChangeCurrentOrg(gcOrgCache);
            return sameChgOrgVersion;
        }
        throw new BusinessRuntimeException("\u53d8\u52a8\u5355\u4f4d" + changeCode + "\u4e0d\u5b58\u5728\uff0c\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + orgType + " \u65f6\u671f\uff1a" + yyyyTmmmm);
    }

    @Override
    public OrgToJsonVO generateVirtualOrg(GcOrgCacheVO changeOrg) {
        if (StringUtils.isEmpty((String)changeOrg.getCode())) {
            throw new BusinessRuntimeException("\u53d8\u52a8\u5355\u4f4d\u4e3a\u7a7a");
        }
        GcOrgBaseClient gcOrgBase = (GcOrgBaseClient)SpringBeanUtils.getBean(GcOrgBaseClient.class);
        GcOrgBaseApiParam gcOrgBaseApiParam = new GcOrgBaseApiParam();
        List<String> virtualCodeList = this.sameCtrlChgOrgDao.listVirtualCodesByChangeCode(changeOrg.getCode());
        String code = changeOrg.getCode().substring(0, changeOrg.getCode().length() - 1);
        String virtualCode = "";
        for (char endChar = 'A'; endChar <= 'Z'; endChar = (char)(endChar + '\u0001')) {
            virtualCode = code + endChar;
            gcOrgBaseApiParam.setOrgCode(virtualCode);
            OrgToJsonVO orgToJsonVO = (OrgToJsonVO)gcOrgBase.getUnitById(gcOrgBaseApiParam).getData();
            if ((CollectionUtils.isEmpty(virtualCodeList) || !virtualCodeList.contains(virtualCode)) && orgToJsonVO == null) break;
        }
        OrgToJsonVO orgToJson = new OrgToJsonVO();
        orgToJson.setTitle(changeOrg.getTitle() + "(\u540c\u63a7\u751f\u6210\u52ff\u5220)");
        orgToJson.setCode(virtualCode);
        orgToJson.setParentid(changeOrg.getParentId());
        orgToJson.setCreateTime(new Date());
        orgToJson.setRecoveryFlag(true);
        orgToJson.setFieldValue("RECOVERYFLAG", (Object)1);
        orgToJson.setFieldValue("PARENTS", (Object)(changeOrg.getParentStr().substring(0, changeOrg.getParentStr().length() - changeOrg.getCode().length()) + virtualCode));
        orgToJson.setFieldValue("BBLX", (Object)changeOrg.getBblx());
        orgToJson.setFieldValue("SHORTNAME", (Object)(changeOrg.getTitle() + "(\u540c\u63a7\u751f\u6210\u52ff\u5220)"));
        orgToJson.setFieldValue("ORGTYPEID", (Object)changeOrg.getOrgTypeId());
        orgToJson.setFieldValue("ADJTYPEIDS", changeOrg.getFields().get("ADJTYPEIDS"));
        orgToJson.setFieldValue("CURRENCYIDS", changeOrg.getFields().get("CURRENCYIDS"));
        orgToJson.setFieldValue("CURRENCYID", changeOrg.getFields().get("CURRENCYID"));
        orgToJson.setFieldValue("ORDINAL", (Object)changeOrg.getOrdinal());
        return orgToJson;
    }

    @Override
    public String getCommonUnit(String[] virtualParents, String[] changeParents) {
        if (CollectionUtils.isEmpty((Object[])virtualParents) || CollectionUtils.isEmpty((Object[])changeParents)) {
            return null;
        }
        if (Arrays.toString(virtualParents).equals(Arrays.toString(changeParents))) {
            if (changeParents.length >= 2) {
                return changeParents[changeParents.length - 2];
            }
            return changeParents[changeParents.length - 1];
        }
        int minLength = Math.min(virtualParents.length, changeParents.length);
        String firstSameParentId = null;
        for (int i = 0; i < minLength && virtualParents[i].equals(changeParents[i]); ++i) {
            firstSameParentId = virtualParents[i];
        }
        if (StringUtils.isEmpty(firstSameParentId)) {
            return null;
        }
        return firstSameParentId;
    }

    @Override
    public YearPeriodObject getPreviousPeriodStr(String yyyyTmmmm) {
        YearPeriodObject yearPeriodObject = new YearPeriodObject(null, yyyyTmmmm);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(yearPeriodObject.formatYP().getEndDate());
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)yearPeriodObject.getType(), (int)-1);
        YearPeriodObject yearPeriodCurrentObject = new YearPeriodObject(null, currentPeriod.toString());
        return yearPeriodCurrentObject;
    }
}

