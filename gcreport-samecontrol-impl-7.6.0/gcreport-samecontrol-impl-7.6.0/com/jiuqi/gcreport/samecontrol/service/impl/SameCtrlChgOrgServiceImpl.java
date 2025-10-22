/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient
 *  com.jiuqi.gcreport.org.api.intf.GcOrgClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgEditVO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractDataClient
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 *  com.jiuqi.gcreport.samecontrol.vo.SameChgOrgVersionVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.organization.service.OrgVersionService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient;
import com.jiuqi.gcreport.org.api.intf.GcOrgClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgEditVO;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractDataClient;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgLogDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgLogEO;
import com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.service.VirtualOrgService;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import com.jiuqi.gcreport.samecontrol.vo.SameChgOrgVersionVO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SameCtrlChgOrgServiceImpl
implements SameCtrlChgOrgService {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlChgOrgServiceImpl.class);
    @Autowired
    private VirtualOrgService virtualOrgService;
    @Autowired
    private OrgVersionService orgVersionService;
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    @Autowired
    private SameCtrlChgOrgLogDao sameCtrlChgOrgLogDao;
    @Autowired(required=false)
    private SameCtrlInvestBillClient sameCtrlInvestBillClient;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private SameCtrlExtractLogService sameCtrlExtractLogService;

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgs(String orgTypeName, String yyyyTmmmm) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        OrgVersionVO orgVersionVO = GcOrgPublicTool.getInstance().getTypeVerInstance().getOrgVersionByType(orgTypeName, yearPeriodUtil.getEndDate());
        if (null == orgVersionVO) {
            return Collections.EMPTY_LIST;
        }
        return this.sameCtrlChgOrgDao.listSameCtrlChgOrgs(orgVersionVO.getId());
    }

    @Override
    public Set<String> listOneYearVirtualCodeByVirtualParentCode(String virtualParentCode, String orgTypeName, String yyyyTmmmm) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        Date endDate = yearPeriodUtil.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(1, calendar.get(1) - 1);
        Date beginDate = calendar.getTime();
        OrgVersionVO orgVersionVO = GcOrgPublicTool.getInstance().getTypeVerInstance().getOrgVersionByType(orgTypeName, yearPeriodUtil.getEndDate());
        if (null == orgVersionVO) {
            return Collections.EMPTY_SET;
        }
        return this.sameCtrlChgOrgDao.listVirtualCodeByVirtualParentCode(virtualParentCode, beginDate, endDate);
    }

    @Override
    public Set<String> listOneYearChangedCodeByChangedParentCode(String virtualParentCode, String orgTypeName, String yyyyTmmmm) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        Date endDate = yearPeriodUtil.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(1, calendar.get(1) - 1);
        Date beginDate = calendar.getTime();
        OrgVersionVO orgVersionVO = GcOrgPublicTool.getInstance().getTypeVerInstance().getOrgVersionByType(orgTypeName, yearPeriodUtil.getEndDate());
        if (null == orgVersionVO) {
            return Collections.EMPTY_SET;
        }
        return this.sameCtrlChgOrgDao.listChangedCodeByChangedParentCode(virtualParentCode, beginDate, endDate);
    }

    @Override
    public Set<String> listOneYearVirtualCodeBySameParentCode(String sameParentCode, String orgTypeName, String yyyyTmmmm) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        Date endDate = yearPeriodUtil.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(1, calendar.get(1) - 1);
        Date beginDate = calendar.getTime();
        OrgVersionVO orgVersionVO = GcOrgPublicTool.getInstance().getTypeVerInstance().getOrgVersionByType(orgTypeName, yearPeriodUtil.getEndDate());
        if (null == orgVersionVO) {
            return Collections.EMPTY_SET;
        }
        return this.sameCtrlChgOrgDao.listVirtualCodeBySameParentCode(sameParentCode, beginDate, endDate);
    }

    @Override
    public void autoCreateSameCtrlChgOrg(String orgType, String yyyyTmmmm) {
        List<SameChgOrgVersionVO> sameChgOrgVersions = this.virtualOrgService.listChangeOrg(orgType, yyyyTmmmm);
        List<SameChgOrgVersionVO> noSameChangeDisposalOrg = this.virtualOrgService.listNoSameCtrlDisposalOrg(orgType, yyyyTmmmm);
        this.saveOrganizeInfo(sameChgOrgVersions, noSameChangeDisposalOrg, orgType, yyyyTmmmm);
    }

    @Override
    public List<SameCtrlChgOrgVO> listSameCtrlChgOrgs(ChangeOrgCondition condition) {
        String periodStr = condition.getPeriodStr();
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date toDate = yearPeriodUtil.getEndDate();
        List<SameCtrlChgOrgEO> eos = this.sameCtrlChgOrgDao.listSameCtrlChgOrgsByDisposalDate(condition, toDate);
        eos = eos.stream().filter(eo -> toDate.getTime() <= this.getNextYearLastTermDate(eo.getChangeDate()).getTime()).collect(Collectors.toList());
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCenterService readOrgCenterTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List<SameCtrlChgOrgVO> vos = eos.stream().map(eo -> this.convertEO2VO(condition, (SameCtrlChgOrgEO)((Object)eo), readOrgCenterTool, noAuthzOrgTool)).collect(Collectors.toList());
        vos.removeAll(Collections.singleton(null));
        return vos;
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByDisposeOrg(ChangeOrgCondition condition) {
        return CollectionUtils.newArrayList();
    }

    private Date getNextYearLastTermDate(Date changeDate) {
        int nextYear = DateUtils.getYearOfDate((Date)changeDate) + 1;
        return DateUtils.lastDateOf((int)nextYear, (int)12);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void addChangedOrg(ChangeOrgCondition changeOrgCondition) {
        String orgType = changeOrgCondition.getOrgType();
        String periodStr = changeOrgCondition.getPeriodStr();
        SameCtrlChgOrgVO sameCtrlChgOrgVO = changeOrgCondition.getSameCtrlChgOrgVO();
        SameChgOrgVersionVO sameChgOrgVersion = this.virtualOrgService.getChangeOrg(orgType, periodStr, sameCtrlChgOrgVO.getChangedCode());
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(orgType);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        orgVersionDTO.setVersionDate(yearPeriodUtil.getEndDate());
        OrgVersionDO orgVersion = this.orgVersionService.get(orgVersionDTO);
        if (orgVersion == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u7248\u672c\u4e0d\u5b58\u5728\uff0c\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + orgType + " \u65f6\u671f\uff1a" + periodStr);
        }
        String orgVersionId = UUIDUtils.toString36((UUID)orgVersion.getId());
        ContextUser user = NpContextHolder.getContext().getUser();
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService gcOrgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO changeBeforeOrg = sameChgOrgVersion.getChangeBeforeOrg() == null ? sameChgOrgVersion.getChangeCurrentOrg() : sameChgOrgVersion.getChangeBeforeOrg();
        OrgToJsonVO virtualOrg = this.virtualOrgService.generateVirtualOrg(changeBeforeOrg);
        List<SameCtrlChgOrgEO> chgOrgList = this.sameCtrlChgOrgDao.listSameCtrlChgOrgsByChangedAndDisposeDate(sameChgOrgVersion.getChangeCurrentOrg().getCode(), sameCtrlChgOrgVO.getChangeDate(), sameCtrlChgOrgVO.getDisposalDate());
        if (!CollectionUtils.isEmpty(chgOrgList)) {
            throw new BusinessRuntimeException("\u5f53\u524d\u65f6\u671f\u5185\u53d8\u52a8\u5355\u4f4d\u5df2\u5b58\u5728\uff0c\u4e0d\u5141\u8bb8\u751f\u6210\u865a\u62df\u5355\u4f4d\u3002");
        }
        long currentTime = System.currentTimeMillis();
        SameCtrlChgOrgEO sameCtrlChgOrg = this.getSameCtrlChgInfo(sameChgOrgVersion, virtualOrg.getCode(), yearPeriodUtil.getEndDate(), orgVersionId, null, user.getName(), currentTime);
        sameCtrlChgOrg.setId(UUIDOrderUtils.newUUIDStr());
        sameCtrlChgOrg.setDisposalDate(sameCtrlChgOrgVO.getDisposalDate());
        sameCtrlChgOrg.setChangeDate(sameCtrlChgOrgVO.getChangeDate());
        this.saveSameCtrlChgInfo(Arrays.asList(virtualOrg), Arrays.asList(sameCtrlChgOrg), new ArrayList<SameCtrlChgOrgLogEO>(), orgType, periodStr, new ArrayList<GcOrgCacheVO>());
        if (changeOrgCondition.getExtract().booleanValue()) {
            GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            SameCtrlChgOrgVO vo = this.convertEO2VO(changeOrgCondition, sameCtrlChgOrg, gcOrgCenterTool, noAuthzOrgTool);
            SameCtrlExtractDataVO condition = new SameCtrlExtractDataVO();
            BeanUtils.copyProperties(changeOrgCondition, condition);
            condition.setSameCtrlChgOrg(vo);
            condition.setSn(UUIDUtils.newUUIDStr());
            ((SameCtrlExtractDataClient)SpringContextUtils.getBean(SameCtrlExtractDataClient.class)).sameCtrlExtractData(condition);
        }
    }

    @Override
    public List<GcOrgCacheVO> getDisposeAndAcquisitionOrg(ChangeOrgCondition condition) {
        SameChgOrgVersionVO sameChgOrgVersion = this.virtualOrgService.getChangeOrg(condition.getOrgType(), condition.getPeriodStr(), condition.getOrgCode());
        if (sameChgOrgVersion == null) {
            return new ArrayList<GcOrgCacheVO>();
        }
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        ArrayList<GcOrgCacheVO> orgList = new ArrayList<GcOrgCacheVO>();
        GcOrgCacheVO beforeOrg = sameChgOrgVersion.getChangeBeforeOrg() == null ? null : noAuthzOrgTool.getOrgByCode(sameChgOrgVersion.getChangeBeforeOrg().getParentId());
        orgList.add(beforeOrg);
        orgList.add(noAuthzOrgTool.getOrgByCode(sameChgOrgVersion.getChangeCurrentOrg().getParentId()));
        return orgList;
    }

    private Map<String, Object> getInvestBill(String orgType, String changedCode, String parentCode, YearPeriodDO yearPeriodUtil) {
        YearPeriodObject yp = new YearPeriodObject(null, yearPeriodUtil.getYear(), yearPeriodUtil.getType(), yearPeriodUtil.getPeriod());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        String baseUnitId = orgCenterTool.getDeepestBaseUnitId(parentCode);
        if (StringUtils.isEmpty((String)baseUnitId) || this.sameCtrlInvestBillClient == null) {
            logger.error("\u5355\u4f4d\uff1a" + parentCode + " \u7684\u672c\u90e8\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u6216\u8005SameCtrlInvestBillClient\u4e0d\u5b58\u5728\u5b9e\u73b0\u7c7b\u3002");
            return null;
        }
        return this.sameCtrlInvestBillClient.getByUnitAndYear(baseUnitId, changedCode, yearPeriodUtil.getYear());
    }

    @Override
    public void changedCtrlOrgsState(List<String> deleteIds) {
        if (CollectionUtils.isEmpty(deleteIds)) {
            return;
        }
        Map<String, Integer> sameCtrlChgId2OffsetCountMap = this.sameCtrlOffSetItemDao.listOffsetCountBySameCtrlChgId(deleteIds);
        List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList = this.sameCtrlChgOrgDao.listSameCtrlChgOrgsByIds(deleteIds);
        for (SameCtrlChgOrgEO orgEO : sameCtrlChgOrgEOList) {
            if (Objects.nonNull(orgEO.getDeleteFlag()) && 0 == orgEO.getDeleteFlag() && sameCtrlChgId2OffsetCountMap.containsKey(orgEO.getId()) && sameCtrlChgId2OffsetCountMap.get(orgEO.getId()) > 0) {
                throw new BusinessRuntimeException("\u8bf7\u5148\u5220\u9664\u5904\u7f6e\u65b9\u3001\u6536\u8d2d\u65b9\u7684\u62b5\u9500\u5206\u5f55");
            }
            orgEO.setDeleteFlag(orgEO.getDeleteFlag());
        }
        this.sameCtrlChgOrgDao.updateAll(sameCtrlChgOrgEOList);
    }

    private SameCtrlChgOrgVO convertEO2VO(ChangeOrgCondition changeOrgCondition, SameCtrlChgOrgEO eo, GcOrgCenterService readOrgCenterTool, GcOrgCenterService noAuthzOrgTool) {
        GcBaseData changedOrg;
        SameCtrlChgOrgVO vo = new SameCtrlChgOrgVO();
        BeanUtils.copyProperties((Object)eo, vo);
        Integer changeOrgTypeCode = eo.getChangedOrgType();
        vo.setChangedOrgTypeCode(changeOrgTypeCode);
        String changeOrgTypeName = ChangedOrgTypeEnum.getChangeTypeNameByCode((Integer)eo.getChangedOrgType());
        vo.setChangedOrgTypeName(changeOrgTypeName);
        int[] showChgOrgFlag = new int[]{3};
        Boolean[] orgAuthFlag = new Boolean[]{true};
        String changedName = this.getOrgName(readOrgCenterTool, noAuthzOrgTool, vo.getChangedCode(), showChgOrgFlag, orgAuthFlag);
        if (StringUtils.isEmpty((String)changedName) && (changedOrg = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_ORG", vo.getChangedCode())) != null) {
            changedName = changedOrg.getTitle();
        }
        if (StringUtils.isEmpty((String)changedName)) {
            vo.setChangedName(vo.getChangedCode());
        } else {
            vo.setChangedName(changedName);
        }
        if (changeOrgTypeCode != ChangedOrgTypeEnum.NON_SAME_CTRL_DISPOSE.getCode()) {
            orgAuthFlag = new Boolean[]{true};
            String changedParentName = this.getOrgName(readOrgCenterTool, noAuthzOrgTool, vo.getChangedParentCode(), showChgOrgFlag, orgAuthFlag);
            vo.setChangedParentName(changedParentName);
            if (!orgAuthFlag[0].booleanValue()) {
                vo.setChangedParentAuth(Boolean.valueOf(false));
            }
        }
        if (changeOrgTypeCode != ChangedOrgTypeEnum.NON_SAME_CTRL_NEW.getCode()) {
            orgAuthFlag = new Boolean[]{true};
            String virtualParentName = this.getOrgName(readOrgCenterTool, noAuthzOrgTool, vo.getVirtualParentCode(), showChgOrgFlag, orgAuthFlag);
            vo.setVirtualParentName(virtualParentName);
            if (!orgAuthFlag[0].booleanValue()) {
                vo.setVirtualParentAuth(Boolean.valueOf(false));
            }
        }
        if (showChgOrgFlag[0] <= 0) {
            return null;
        }
        if (changeOrgTypeCode == ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode()) {
            orgAuthFlag = new Boolean[]{true};
            String sameParentName = this.getOrgName(readOrgCenterTool, noAuthzOrgTool, vo.getSameParentCode(), showChgOrgFlag, orgAuthFlag);
            vo.setSameParentName(sameParentName);
            if (!orgAuthFlag[0].booleanValue()) {
                vo.setSameParentAuth(Boolean.valueOf(false));
            }
        }
        SameCtrlExtractLogVO sameCtrlExtractLog = new SameCtrlExtractLogVO();
        BeanUtils.copyProperties(changeOrgCondition, sameCtrlExtractLog);
        sameCtrlExtractLog.setChangedCode(eo.getChangedCode());
        SameCtrlExtractLogVO sameCtrlExtractLogResult = this.sameCtrlExtractLogService.querySameCtrlExtractLog(sameCtrlExtractLog);
        if (sameCtrlExtractLogResult != null && sameCtrlExtractLogResult.getTaskState() != null) {
            vo.setLogState(sameCtrlExtractLogResult.getTaskState().getCode());
        } else {
            vo.setLogState(SameCtrlExtractTaskStateEnum.NONE.getCode());
        }
        if (!StringUtils.isEmpty((String)eo.getVirtualCodeType())) {
            GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_VIRTUALORGTYPE", eo.getVirtualCodeType());
            vo.setVirtualCodeType(eo.getVirtualCodeType());
            vo.setVirtualCodeTypeName(gcBaseData.getTitle());
        }
        if (!StringUtils.isEmpty((String)eo.getVirtualCode())) {
            GcOrgCacheVO gcOrgCacheVO = readOrgCenterTool.getOrgByCode(eo.getVirtualCode());
            if (gcOrgCacheVO == null) {
                vo.setVirtualName(eo.getVirtualCode());
            } else {
                vo.setVirtualName(gcOrgCacheVO.getTitle());
            }
        }
        if (!StringUtils.isEmpty((String)eo.getCorrespondVirtualCode())) {
            StringBuilder title = new StringBuilder();
            for (String code : eo.getCorrespondVirtualCode().split(";")) {
                GcOrgCacheVO gcOrgCacheVO = readOrgCenterTool.getOrgByCode(code);
                if (gcOrgCacheVO == null) {
                    title.append(code).append(";");
                    continue;
                }
                title.append(gcOrgCacheVO.getTitle()).append(";");
            }
            vo.setCorrespondVirtualName(title.toString());
        }
        vo.setDeleteFlag(Boolean.valueOf(Objects.equals(1, eo.getDeleteFlag())));
        vo.setDisableFlag(eo.getDisableFlag());
        return vo;
    }

    private String getOrgName(GcOrgCenterService readOrgCenterTool, GcOrgCenterService noAuthzOrgTool, String orgCode, int[] showChgOrgFlag, Boolean[] orgAuthFlag) {
        GcOrgCacheVO orgCacheVO = readOrgCenterTool.getOrgByCode(orgCode);
        if (orgCacheVO == null) {
            orgCacheVO = noAuthzOrgTool.getOrgByCode(orgCode);
            showChgOrgFlag[0] = showChgOrgFlag[0] - 1;
            orgAuthFlag[0] = false;
        }
        return orgCacheVO == null ? "" : orgCacheVO.getTitle();
    }

    private void saveOrganizeInfo(List<SameChgOrgVersionVO> sameChgOrgVersions, List<SameChgOrgVersionVO> noSameChangeDisposalOrg, String orgType, String yyyyTmmmm) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(orgType);
        orgVersionDTO.setVersionDate(yearPeriodUtil.getEndDate());
        OrgVersionDO orgVersion = this.orgVersionService.get(orgVersionDTO);
        if (orgVersion == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u7248\u672c\u4e0d\u5b58\u5728\uff0c\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + orgType + " \u65f6\u671f\uff1a" + yyyyTmmmm);
        }
        String orgVersionId = UUIDUtils.toString36((UUID)orgVersion.getId());
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        ArrayList<OrgToJsonVO> virtualOrgList = new ArrayList<OrgToJsonVO>();
        ArrayList<SameCtrlChgOrgEO> sameCtrlChgOrgList = new ArrayList<SameCtrlChgOrgEO>();
        ArrayList<SameCtrlChgOrgLogEO> sameCtrlChgOrgLogList = new ArrayList<SameCtrlChgOrgLogEO>();
        ArrayList<GcOrgCacheVO> changeBeforeOrgCaches = new ArrayList<GcOrgCacheVO>();
        long currentTime = System.currentTimeMillis();
        for (SameChgOrgVersionVO sameChgOrgVersion : sameChgOrgVersions) {
            if (null == sameChgOrgVersion.getChangeBeforeOrg()) {
                Map<String, Object> acquisitionInvestBill = this.getInvestBill(orgType, sameChgOrgVersion.getChangeCurrentOrg().getCode(), sameChgOrgVersion.getChangeCurrentOrg().getParentId(), yearPeriodUtil);
                if (!this.checkNoSameCtrlChgInfo(sameChgOrgVersion.getChangeCurrentOrg().getCode(), yearPeriodUtil.getBeginDate(), acquisitionInvestBill, yyyyTmmmm)) continue;
                SameCtrlChgOrgEO sameCtrlChgOrg = this.getSameCtrlChgInfo(sameChgOrgVersion, sameChgOrgVersion.getChangeCurrentOrg().getCode(), yearPeriodUtil.getBeginDate(), orgVersionId, acquisitionInvestBill, user.getName(), currentTime);
                SameCtrlChgOrgLogEO sameCtrlChgOrgLog = this.getSameCtrlChgOrgLogInfo(sameCtrlChgOrg, user.getId(), currentTime);
                sameCtrlChgOrgList.add(sameCtrlChgOrg);
                sameCtrlChgOrgLogList.add(sameCtrlChgOrgLog);
                continue;
            }
            Map<String, Object> investBill = this.getInvestBill(orgType, sameChgOrgVersion.getChangeCurrentOrg().getCode(), sameChgOrgVersion.getChangeBeforeOrg().getParentId(), yearPeriodUtil);
            OrgToJsonVO virtualOrg = this.virtualOrgService.generateVirtualOrg(sameChgOrgVersion.getChangeBeforeOrg());
            Map<String, Object> acquisitionInvestBill = this.getInvestBill(orgType, sameChgOrgVersion.getChangeCurrentOrg().getCode(), sameChgOrgVersion.getChangeCurrentOrg().getParentId(), yearPeriodUtil);
            if (!this.checkVirtualOrgInfo(sameChgOrgVersion.getChangeCurrentOrg().getCode(), yearPeriodUtil.getBeginDate(), investBill, acquisitionInvestBill, yyyyTmmmm)) continue;
            SameCtrlChgOrgEO sameCtrlChgOrg = this.getSameCtrlChgInfo(sameChgOrgVersion, virtualOrg.getCode(), yearPeriodUtil.getBeginDate(), orgVersionId, investBill, user.getName(), currentTime);
            SameCtrlChgOrgLogEO sameCtrlChgOrgLog = this.getSameCtrlChgOrgLogInfo(sameCtrlChgOrg, user.getId(), currentTime);
            virtualOrgList.add(virtualOrg);
            sameCtrlChgOrgList.add(sameCtrlChgOrg);
            sameCtrlChgOrgLogList.add(sameCtrlChgOrgLog);
        }
        for (SameChgOrgVersionVO sameChgOrgVersion : noSameChangeDisposalOrg) {
            if (null != sameChgOrgVersion.getChangeCurrentOrg()) continue;
            Map<String, Object> disposeInvestBill = this.getInvestBill(orgType, sameChgOrgVersion.getChangeBeforeOrg().getCode(), sameChgOrgVersion.getChangeBeforeOrg().getParentId(), yearPeriodUtil);
            GcOrgCacheVO changeBeforeOrg = sameChgOrgVersion.getChangeBeforeOrg();
            if (!this.checkNoSameCtrlChgInfo(sameChgOrgVersion.getChangeBeforeOrg().getCode(), yearPeriodUtil.getBeginDate(), disposeInvestBill, yyyyTmmmm)) continue;
            SameCtrlChgOrgEO sameCtrlChgOrg = this.getSameCtrlChgInfo(sameChgOrgVersion, changeBeforeOrg.getCode(), yearPeriodUtil.getBeginDate(), orgVersionId, disposeInvestBill, user.getName(), currentTime);
            SameCtrlChgOrgLogEO sameCtrlChgOrgLog = this.getSameCtrlChgOrgLogInfo(sameCtrlChgOrg, user.getId(), currentTime);
            changeBeforeOrgCaches.add(changeBeforeOrg);
            sameCtrlChgOrgList.add(sameCtrlChgOrg);
            sameCtrlChgOrgLogList.add(sameCtrlChgOrgLog);
        }
        ((SameCtrlChgOrgServiceImpl)SpringContextUtils.getBean(SameCtrlChgOrgServiceImpl.class)).saveSameCtrlChgInfo(virtualOrgList, sameCtrlChgOrgList, sameCtrlChgOrgLogList, orgType, yyyyTmmmm, changeBeforeOrgCaches);
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveSameCtrlChgInfo(List<OrgToJsonVO> virtualOrgList, List<SameCtrlChgOrgEO> sameCtrlChgOrgList, List<SameCtrlChgOrgLogEO> sameCtrlChgOrgLogList, String orgType, String yyyyTmmmm, List<GcOrgCacheVO> changeBeforeOrgCaches) {
        this.sameCtrlChgOrgDao.addBatch(sameCtrlChgOrgList);
        this.sameCtrlChgOrgLogDao.addBatch(sameCtrlChgOrgLogList);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        List<String> orgVersionPerodList = this.listPeriodStr(orgType, yearPeriodUtil);
        this.saveSameCtrlVirtualOrg(orgVersionPerodList, virtualOrgList, orgType);
        this.saveNoSameCtrlDisposalOrg(orgVersionPerodList, changeBeforeOrgCaches, orgType);
    }

    private void saveSameCtrlVirtualOrg(List<String> orgVersionPerods, List<OrgToJsonVO> virtualOrgs, String orgType) {
        GcOrgClient mangerCenterTool = (GcOrgClient)SpringBeanUtils.getBean(GcOrgClient.class);
        for (OrgToJsonVO orgToJsonVO : virtualOrgs) {
            for (String periodVersion : orgVersionPerods) {
                GcOrgEditVO newOrg = new GcOrgEditVO();
                newOrg.setOrgType(orgType);
                newOrg.setOrgVerCode(periodVersion);
                newOrg.setOrg(orgToJsonVO);
                newOrg.setOrgCode(orgToJsonVO.getCode());
                this.saveOrg(mangerCenterTool, newOrg);
            }
        }
    }

    private void saveNoSameCtrlDisposalOrg(List<String> orgVersionPerods, List<GcOrgCacheVO> changeBeforeOrgCaches, String orgType) {
        GcOrgBaseClient gcOrgBase = (GcOrgBaseClient)SpringBeanUtils.getBean(GcOrgBaseClient.class);
        GcOrgClient mangerCenterTool = (GcOrgClient)SpringBeanUtils.getBean(GcOrgClient.class);
        for (GcOrgCacheVO orgCache : changeBeforeOrgCaches) {
            GcOrgBaseApiParam gcOrgBaseApiParam = new GcOrgBaseApiParam();
            gcOrgBaseApiParam.setOrgCode(orgCache.getCode());
            OrgToJsonVO orgToJsonVO = (OrgToJsonVO)gcOrgBase.getUnitById(gcOrgBaseApiParam).getData();
            if (null == orgToJsonVO) continue;
            for (String periodVersion : orgVersionPerods) {
                OrgToJsonVO orgToJsonNew = new OrgToJsonVO();
                BeanUtils.copyProperties(orgToJsonVO, orgToJsonNew);
                orgToJsonNew.setTitle(orgToJsonVO.getTitle());
                orgToJsonNew.setCode(orgToJsonVO.getCode());
                orgToJsonNew.setParentid(orgCache.getParentId());
                orgToJsonNew.setFieldValue("ORGID", (Object)orgCache.getCode());
                orgToJsonNew.setCreateTime(new Date());
                orgToJsonNew.setRecoveryFlag(true);
                orgToJsonNew.setFieldValue("RECOVERYFLAG", (Object)1);
                orgToJsonNew.setFieldValue("ID", (Object)UUIDUtils.newUUIDStr());
                GcOrgEditVO newOrg = new GcOrgEditVO();
                newOrg.setOrgType(orgType);
                newOrg.setOrgVerCode(periodVersion);
                newOrg.setOrg(orgToJsonNew);
                newOrg.setOrgCode(orgToJsonNew.getCode());
                this.saveOrg(mangerCenterTool, newOrg);
            }
        }
    }

    private void saveOrg(GcOrgClient mangerCenterTool, GcOrgEditVO newOrg) {
        mangerCenterTool.createOrg(newOrg);
    }

    private boolean checkNoSameCtrlChgInfo(String changeOrgCode, Date changedDate, Map<String, Object> disposeInvestBill, String yyyyTmmmm) {
        if (disposeInvestBill == null || disposeInvestBill.get("DISPOSEFLAG") == null || !String.valueOf(disposeInvestBill.get("DISPOSEFLAG")).equals("1")) {
            logger.error("\u53d8\u52a8\u5355\u4f4d\uff1a[{}] \u65f6\u671f\uff1a[{}] \u5bf9\u5e94\u7684\u6295\u8d44\u53f0\u8d26\u4e3a\u672a\u5904\u7f6e\u3002", (Object)changeOrgCode, (Object)yyyyTmmmm);
            return false;
        }
        List<SameCtrlChgOrgEO> chgOrgList = this.sameCtrlChgOrgDao.listSameCtrlChgOrgsByChangedAndDisposeDate(changeOrgCode, changedDate, (Date)disposeInvestBill.get("DISPOSEDATE"));
        if (chgOrgList.isEmpty()) {
            return true;
        }
        logger.error("\u53d8\u52a8\u5355\u4f4d\uff1a[{}] \u65f6\u671f\uff1a[{}] \u5f53\u524d\u53d8\u52a8\u5355\u4f4d\u5b58\u5728\u4e8e\u540c\u63a7\u53d8\u52a8\u6570\u636e\u4e2d\u3002", (Object)changeOrgCode, (Object)yyyyTmmmm);
        return false;
    }

    private boolean checkVirtualOrgInfo(String changeOrgCode, Date changedDate, Map<String, Object> disposeInvestBill, Map<String, Object> acquisitionInvestBill, String yyyyTmmmm) {
        if (disposeInvestBill == null || disposeInvestBill.get("DISPOSEFLAG") == null || !String.valueOf(disposeInvestBill.get("DISPOSEFLAG")).equals("1")) {
            logger.error("\u53d8\u52a8\u5355\u4f4d\uff1a[{}] \u65f6\u671f\uff1a[{}] \u5bf9\u5e94\u7684\u6295\u8d44\u53f0\u8d26\u4e3a\u672a\u5904\u7f6e\u3002", (Object)changeOrgCode, (Object)yyyyTmmmm);
            return false;
        }
        if (acquisitionInvestBill == null) {
            logger.error("\u53d8\u52a8\u5355\u4f4d\uff1a[{}] \u65f6\u671f\uff1a[{}] \u4f5c\u4e3a\u6295\u8d44\u53f0\u8d26\u4e2d\u7684\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5b58\u5728\u3002", (Object)changeOrgCode, (Object)yyyyTmmmm);
            return false;
        }
        List<SameCtrlChgOrgEO> chgOrgList = this.sameCtrlChgOrgDao.listSameCtrlChgOrgsByChangedAndDisposeDate(changeOrgCode, changedDate, (Date)disposeInvestBill.get("DISPOSEDATE"));
        if (chgOrgList.isEmpty()) {
            return true;
        }
        logger.error("\u53d8\u52a8\u5355\u4f4d\uff1a[{}] \u65f6\u671f\uff1a[{}] \u5f53\u524d\u53d8\u52a8\u5355\u4f4d\u5b58\u5728\u4e8e\u540c\u63a7\u53d8\u52a8\u6570\u636e\u4e2d\u3002", (Object)changeOrgCode, (Object)yyyyTmmmm);
        return false;
    }

    private SameCtrlChgOrgEO getSameCtrlChgInfo(SameChgOrgVersionVO sameChgOrgVersion, String virtualCode, Date changeDate, String orgVersionId, Map<String, Object> investBill, String creator, long currentTime) {
        String sameParentStr;
        SameCtrlChgOrgEO sameCtrlChgOrg = new SameCtrlChgOrgEO();
        sameCtrlChgOrg.setVirtualCode(virtualCode);
        if (null != sameChgOrgVersion.getChangeCurrentOrg() && null != sameChgOrgVersion.getChangeBeforeOrg()) {
            sameCtrlChgOrg.setChangedCode(sameChgOrgVersion.getChangeCurrentOrg().getCode());
            sameCtrlChgOrg.setChangedParentCode(sameChgOrgVersion.getChangeCurrentOrg().getParentId());
            sameCtrlChgOrg.setChangedParents(sameChgOrgVersion.getChangeCurrentOrg().getParentStr());
            sameCtrlChgOrg.setVirtualParentCode(sameChgOrgVersion.getChangeBeforeOrg().getParentId());
            sameCtrlChgOrg.setVirtualParents(sameChgOrgVersion.getChangeBeforeOrg().getParentStr());
            sameParentStr = this.virtualOrgService.getCommonUnit(sameChgOrgVersion.getChangeBeforeOrg().getParents(), sameChgOrgVersion.getChangeCurrentOrg().getParents());
            sameCtrlChgOrg.setChangedOrgType(ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode());
        } else if (null == sameChgOrgVersion.getChangeCurrentOrg() && null != sameChgOrgVersion.getChangeBeforeOrg()) {
            sameCtrlChgOrg.setChangedCode(sameChgOrgVersion.getChangeBeforeOrg().getCode());
            sameCtrlChgOrg.setChangedParentCode(sameChgOrgVersion.getChangeBeforeOrg().getParentId());
            sameCtrlChgOrg.setChangedParents(sameChgOrgVersion.getChangeBeforeOrg().getParentStr());
            sameCtrlChgOrg.setVirtualParentCode(sameChgOrgVersion.getChangeBeforeOrg().getParentId());
            sameCtrlChgOrg.setVirtualParents(sameChgOrgVersion.getChangeBeforeOrg().getParentStr());
            sameParentStr = sameChgOrgVersion.getChangeBeforeOrg().getParentId();
            sameCtrlChgOrg.setChangedOrgType(ChangedOrgTypeEnum.NON_SAME_CTRL_DISPOSE.getCode());
        } else {
            sameCtrlChgOrg.setChangedCode(sameChgOrgVersion.getChangeCurrentOrg().getCode());
            sameCtrlChgOrg.setChangedParentCode(sameChgOrgVersion.getChangeCurrentOrg().getParentId());
            sameCtrlChgOrg.setChangedParents(sameChgOrgVersion.getChangeCurrentOrg().getParentStr());
            sameCtrlChgOrg.setVirtualParentCode(sameChgOrgVersion.getChangeCurrentOrg().getParentId());
            sameCtrlChgOrg.setVirtualParents(sameChgOrgVersion.getChangeCurrentOrg().getParentStr());
            sameParentStr = sameChgOrgVersion.getChangeCurrentOrg().getParentId();
            sameCtrlChgOrg.setChangedOrgType(ChangedOrgTypeEnum.NON_SAME_CTRL_NEW.getCode());
        }
        if (StringUtils.isEmpty((String)sameParentStr)) {
            logger.info("\u53d8\u52a8\u5355\u4f4d\u4e0e\u865a\u62df\u5355\u4f4d\u65e0\u5171\u540c\u4e0a\u7ea7\uff0c\u53d8\u52a8\u524d\u5355\u4f4d:{},\u53d8\u52a8\u540e\u5355\u4f4d:{}", (Object)(null == sameChgOrgVersion.getChangeBeforeOrg() ? "" : sameChgOrgVersion.getChangeBeforeOrg().getParents()), (Object)(null == sameChgOrgVersion.getChangeCurrentOrg() ? "" : sameChgOrgVersion.getChangeCurrentOrg().getParents()));
        }
        sameCtrlChgOrg.setId(UUIDOrderUtils.newUUIDStr());
        sameCtrlChgOrg.setChangeDate(changeDate);
        sameCtrlChgOrg.setSameParentCode(sameParentStr);
        sameCtrlChgOrg.setOrgVersionId(orgVersionId);
        sameCtrlChgOrg.setCreator(creator);
        sameCtrlChgOrg.setCreateTime(currentTime);
        sameCtrlChgOrg.setDeleteFlag(0);
        if (investBill != null) {
            sameCtrlChgOrg.setDisposalDate((Date)investBill.get("DISPOSEDATE"));
        }
        return sameCtrlChgOrg;
    }

    private SameCtrlChgOrgLogEO getSameCtrlChgOrgLogInfo(SameCtrlChgOrgEO sameCtrlChgOrg, String operatorId, long operateTime) {
        SameCtrlChgOrgLogEO sameCtrlChgOrgLog = new SameCtrlChgOrgLogEO();
        BeanUtils.copyProperties((Object)sameCtrlChgOrg, (Object)sameCtrlChgOrgLog);
        sameCtrlChgOrgLog.setOperatorId(operatorId);
        sameCtrlChgOrgLog.setOperateTime(operateTime);
        sameCtrlChgOrgLog.setOperating("\u81ea\u52a8\u521b\u5efa");
        sameCtrlChgOrgLog.setChangeDate(sameCtrlChgOrg.getChangeDate().getTime());
        return sameCtrlChgOrgLog;
    }

    @Override
    public List<SameCtrlChgOrgEO> listCurrYearChgOrgsByDisposOrg(String disposOrg, String periodStr) {
        this.checkQueryParams(disposOrg, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date currYearFormDate = this.getCurrYearFromDate(yearPeriodUtil);
        return this.sameCtrlChgOrgDao.listChgOrgsByDisposOrg(disposOrg, currYearFormDate, yearPeriodUtil.getEndDate());
    }

    @Override
    public List<SameCtrlChgOrgEO> listCurrYearChgOrgsByAcquisitionOrg(String acquisitionOrg, String periodStr) {
        this.checkQueryParams(acquisitionOrg, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date currYearFormDate = this.getCurrYearFromDate(yearPeriodUtil);
        return this.sameCtrlChgOrgDao.listChgOrgsByAcquisitionOrg(acquisitionOrg, currYearFormDate, yearPeriodUtil.getEndDate());
    }

    @Override
    public List<SameCtrlChgOrgEO> listCurrYearChgOrgsBySameParentOrg(String sameParentOrg, String periodStr) {
        this.checkQueryParams(sameParentOrg, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date currYearFormDate = this.getCurrYearFromDate(yearPeriodUtil);
        return this.sameCtrlChgOrgDao.listChgOrgsBySameParentOrg(sameParentOrg, currYearFormDate, yearPeriodUtil.getEndDate());
    }

    @Override
    public List<SameCtrlChgOrgEO> listAllYearChgOrgsByDisposOrg(String disposOrg, String periodStr) {
        this.checkQueryParams(disposOrg, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date allYearFormDate = this.getAllYearFromDate(periodStr);
        return this.sameCtrlChgOrgDao.listChgOrgsByDisposOrg(disposOrg, allYearFormDate, yearPeriodUtil.getEndDate());
    }

    @Override
    public List<SameCtrlChgOrgEO> listAllYearChgOrgsByAcquisitionOrg(String acquisitionOrg, String periodStr) {
        this.checkQueryParams(acquisitionOrg, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date allYearFormDate = this.getAllYearFromDate(periodStr);
        return this.sameCtrlChgOrgDao.listChgOrgsByAcquisitionOrg(acquisitionOrg, allYearFormDate, yearPeriodUtil.getEndDate());
    }

    @Override
    public List<SameCtrlChgOrgEO> listAllYearChgOrgsBySameParentOrg(String sameParentOrg, String periodStr) {
        this.checkQueryParams(sameParentOrg, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date allYearFormDate = this.getAllYearFromDate(periodStr);
        return this.sameCtrlChgOrgDao.listChgOrgsBySameParentOrg(sameParentOrg, allYearFormDate, yearPeriodUtil.getEndDate());
    }

    @Override
    public boolean contasinsChangeOrg(String virtualCode, String periodStr) {
        this.checkQueryParams(virtualCode, periodStr);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date currYearFormDate = this.getCurrYearFromDate(yearPeriodUtil);
        List<SameCtrlChgOrgEO> chgOrgs = this.sameCtrlChgOrgDao.listChgOrgsChangeOrg(virtualCode, currYearFormDate, yearPeriodUtil.getEndDate());
        return chgOrgs.size() > 0;
    }

    @Override
    public List<GcBaseData> listVirtualOrgTypeBaseData() {
        return GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_VIRTUALORGTYPE");
    }

    private Date getAllYearFromDate(String periodStr) {
        String currYear = periodStr.substring(0, 4);
        int preYear = Integer.valueOf(currYear) - 1;
        String preYearPeriodStr = periodStr.replace(currYear, String.valueOf(preYear));
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)preYearPeriodStr);
        Date fromDate = yearPeriodUtil.getEndDate();
        return fromDate;
    }

    private Date getCurrYearFromDate(YearPeriodDO yearPeriodUtil) {
        YearPeriodObject currYearPeriodUtil = new YearPeriodObject(null, yearPeriodUtil.getYear(), yearPeriodUtil.getType(), 1);
        return currYearPeriodUtil.formatYP().getBeginDate();
    }

    private void checkQueryParams(String disposOrg, String periodStr) {
        if (StringUtils.isEmpty((String)disposOrg) || StringUtils.isEmpty((String)periodStr)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private List<String> listPeriodStr(String orgType, YearPeriodDO yearPeriod) {
        Calendar calendar = this.getCalendar(yearPeriod.getBeginDate());
        calendar.set(1, calendar.get(1) + 1);
        calendar.set(2, calendar.get(2) - 1);
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(orgType);
        List orgVersionList = this.orgVersionService.list(orgVersionDTO).getRows();
        ArrayList<String> periodStr = new ArrayList<String>();
        for (OrgVersionDO orgVersion : orgVersionList) {
            if (orgVersion.getValidtime().getTime() < yearPeriod.getBeginDate().getTime() || orgVersion.getValidtime().getTime() > calendar.getTime().getTime()) continue;
            Calendar calendarVersion = this.getCalendar(orgVersion.getValidtime());
            YearPeriodDO yearPeriodVer = YearPeriodUtil.transform(null, (int)yearPeriod.getType(), (Calendar)calendarVersion);
            periodStr.add(yearPeriodVer.getFormatValue());
        }
        return periodStr;
    }

    @Override
    public void addManageChangedOrg(ChangeOrgCondition changeOrgCondition) {
        List<SameCtrlChgOrgEO> sameCtrlChgOrgEOListByMRecid;
        List sameCtrlChgOrgVO = changeOrgCondition.getSameCtrlChgOrgVOList();
        if (CollectionUtils.isEmpty((Collection)sameCtrlChgOrgVO)) {
            return;
        }
        ArrayList sameCtrlChgOrgEOList = new ArrayList();
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(changeOrgCondition.getOrgType());
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform((String)changeOrgCondition.getSchemeId(), (String)changeOrgCondition.getPeriodStr());
        orgVersionDTO.setVersionDate(yearPeriodUtil.getEndDate());
        OrgVersionDO orgVersion = this.orgVersionService.get(orgVersionDTO);
        if (orgVersion == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u7248\u672c\u4e0d\u5b58\u5728\uff0c\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + changeOrgCondition.getOrgType() + " \u65f6\u671f\uff1a" + changeOrgCondition.getPeriodStr());
        }
        String orgVersionId = UUIDUtils.toString36((UUID)orgVersion.getId());
        YearPeriodObject yp = new YearPeriodObject(changeOrgCondition.getSchemeId(), changeOrgCondition.getPeriodStr());
        GcOrgCenterService readOrgCenterTool = GcOrgPublicTool.getInstance((String)changeOrgCondition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        ContextUser user = NpContextHolder.getContext().getUser();
        String mRecid = UUIDOrderUtils.newUUIDStr();
        sameCtrlChgOrgVO.forEach(vo -> {
            SameCtrlChgOrgEO sameCtrlChgOrgEO = new SameCtrlChgOrgEO();
            BeanUtils.copyProperties(vo, (Object)sameCtrlChgOrgEO);
            sameCtrlChgOrgEO.setVirtualCodeType(vo.getVirtualCodeType());
            if (StringUtils.isEmpty((String)vo.getmRecid())) {
                sameCtrlChgOrgEO.setmRecid(mRecid);
                sameCtrlChgOrgEO.setSameParentCode("111");
            } else {
                GcOrgCacheVO orgCacheVO = readOrgCenterTool.getCommonUnit(readOrgCenterTool.getOrgByCode(sameCtrlChgOrgEO.getChangedCode()), readOrgCenterTool.getOrgByCode(sameCtrlChgOrgEO.getVirtualCode()));
                sameCtrlChgOrgEO.setSameParentCode(orgCacheVO.getCode());
            }
            long currentTime = System.currentTimeMillis();
            sameCtrlChgOrgEO.setChangedOrgType(vo.getChangedOrgTypeCode());
            sameCtrlChgOrgEO.setId(UUIDOrderUtils.newUUIDStr());
            if (vo.getChangeDate() == null) {
                sameCtrlChgOrgEO.setChangeDate(this.getChangeDate(changeOrgCondition));
            } else {
                sameCtrlChgOrgEO.setChangeDate(vo.getChangeDate());
            }
            sameCtrlChgOrgEO.setCreator(user.getName());
            sameCtrlChgOrgEO.setCreateTime(currentTime);
            sameCtrlChgOrgEO.setOrgVersionId(orgVersionId);
            sameCtrlChgOrgEO.setDeleteFlag(0);
            sameCtrlChgOrgEO.setDisableFlag(0);
            sameCtrlChgOrgEOList.add(sameCtrlChgOrgEO);
        });
        List mRecidList = sameCtrlChgOrgVO.stream().map(SameCtrlChgOrgVO::getmRecid).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(mRecidList) && (sameCtrlChgOrgEOListByMRecid = this.sameCtrlChgOrgDao.listSameCtrlChgOrgByMRecid((String)mRecidList.get(0))).size() == 1 && StringUtils.isEmpty((String)sameCtrlChgOrgEOListByMRecid.get(0).getVirtualCode())) {
            this.sameCtrlChgOrgDao.delete((BaseEntity)sameCtrlChgOrgEOListByMRecid.get(0));
        }
        this.sameCtrlChgOrgDao.addBatch(sameCtrlChgOrgEOList);
    }

    private Date getChangeDate(ChangeOrgCondition changeOrgCondition) {
        PeriodWrapper ps = new PeriodWrapper(changeOrgCondition.getPeriodStr());
        return new GregorianCalendar(ps.getYear(), ps.getPeriod() - 1, 1).getTime();
    }

    @Override
    public void updateManageChangedOrg(ChangeOrgCondition changeOrgCondition) {
        this.sameCtrlChgOrgDao.updateManageChangedOrg(changeOrgCondition);
    }

    @Override
    public void deleteManageChangedOrgByMRecid(List<String> mRecids) {
        mRecids.forEach(mRecid -> {
            List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList = this.sameCtrlChgOrgDao.listSameCtrlChgOrgByMRecid((String)mRecid);
            sameCtrlChgOrgEOList.forEach(sameCtrlChgOrgEO -> {
                int count = this.sameCtrlOffSetItemDao.countBySameCtrlChgId(sameCtrlChgOrgEO.getId());
                if (count > 0) {
                    throw new BusinessRuntimeException("\u53d8\u52a8\u5355\u4f4d\u5b58\u5728\u540c\u63a7\u62b5\u9500\u5206\u5f55\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5220\u9664\u3002");
                }
            });
        });
        this.sameCtrlChgOrgDao.deleteByMRecid(mRecids);
    }

    @Override
    public void deleteManageChangedOrg(String id) {
        int count = this.sameCtrlOffSetItemDao.countBySameCtrlChgId(id);
        if (count > 0) {
            throw new BusinessRuntimeException("\u865a\u62df\u5355\u4f4d\u5b58\u5728\u540c\u63a7\u62b5\u9500\u5206\u5f55\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5220\u9664\u3002");
        }
        this.sameCtrlChgOrgDao.deleteById(id);
    }

    @Override
    public List<SameCtrlChgOrgVO> listManageChangedOrg(ChangeOrgCondition condition) {
        YearPeriodObject yp = new YearPeriodObject(condition.getSchemeId(), condition.getPeriodStr());
        GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCenterService readOrgCenterTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)condition.getPeriodStr());
        condition.setPeriodDate(yearPeriodUtil.getEndDate());
        List<SameCtrlChgOrgEO> eos = this.sameCtrlChgOrgDao.listSameCtrlChgOrgByParents(condition);
        eos.addAll(this.sameCtrlChgOrgDao.listSameCtrlChangedCodeByParam(condition));
        Set<String> mRecidList = eos.stream().map(SameCtrlChgOrgEO::getmRecid).collect(Collectors.toSet());
        ArrayList sameCtrlChgOrgEOList = new ArrayList();
        mRecidList.forEach(mRecid -> sameCtrlChgOrgEOList.addAll(this.sameCtrlChgOrgDao.listSameCtrlChgOrgByMRecid((String)mRecid)));
        List readOrgList = sameCtrlChgOrgEOList.stream().filter(sameCtrlChgOrgEO -> {
            GcOrgCacheVO gcOrgCacheVO = readOrgCenterTool.getOrgByCode(sameCtrlChgOrgEO.getChangedCode());
            return gcOrgCacheVO != null;
        }).collect(Collectors.toList());
        List<SameCtrlChgOrgVO> vos = readOrgList.stream().map(eo -> this.convertEO2VO(condition, (SameCtrlChgOrgEO)((Object)eo), readOrgCenterTool, noAuthzOrgTool)).collect(Collectors.toList());
        vos.removeAll(Collections.singleton(null));
        return vos;
    }
}

