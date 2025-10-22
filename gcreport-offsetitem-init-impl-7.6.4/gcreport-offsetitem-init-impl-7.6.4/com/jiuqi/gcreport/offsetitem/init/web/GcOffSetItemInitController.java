/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.init.api.GcOffSetItemInitClient
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil
 *  com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil
 *  com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemIDVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemInitVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrPageVo
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO
 *  com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum
 *  com.jiuqi.np.log.LogHelper
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.init.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.api.GcOffSetItemInitClient;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitAssistService;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.service.impl.GcCalcOffsetInitLogServiceImpl;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemIDVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemInitVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrPageVo;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.np.log.LogHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Primary
public class GcOffSetItemInitController
implements GcOffSetItemInitClient {
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcOffSetInitService offSetInitService;
    @Autowired
    private GcCalcOffsetInitLogServiceImpl offsetInitLogService;
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired(required=false)
    private GcOffSetInitAssistService gcOffSetInitAssistService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> addAdjustOffSetItem(@Valid @RequestBody List<List<GcOffSetVchrItemVO>> batchlist, String investBillFlag, String periodStr) {
        Collections.reverse(batchlist);
        ArrayList<GcOffSetVchrDTO> dtoList = new ArrayList<GcOffSetVchrDTO>();
        for (int i = 0; i < batchlist.size(); ++i) {
            List<GcOffSetVchrItemVO> list = batchlist.get(i);
            GcOffSetVchrVO offSetItemVO = new GcOffSetVchrVO();
            if (list.get(0).getmRecid() != null) {
                offSetItemVO.setMrecid(((GcOffSetVchrItemVO)list.get(0)).getmRecid());
            }
            this.gcOffSetInitAssistService.changeInvestmentOffsetStatus(list, true, true, true);
            offSetItemVO.setItems((List)list);
            GcOffSetVchrDTO offSetItemDTO = OffsetConvertUtil.getInstance().convertVO2DTO(offSetItemVO);
            dtoList.add(offSetItemDTO);
        }
        this.offSetInitService.batchSave(dtoList);
        ArrayList<GcOffSetVchrItemIDVO> idList = new ArrayList<GcOffSetVchrItemIDVO>();
        for (GcOffSetVchrDTO dto : dtoList) {
            for (GcOffSetVchrItemDTO itemDTO : dto.getItems()) {
                idList.add(new GcOffSetVchrItemIDVO(itemDTO.getId(), itemDTO.getmRecid()));
            }
        }
        if (batchlist != null && batchlist.size() != 0 && batchlist.get(0) != null && batchlist.get(0).size() != 0) {
            GcOffSetVchrItemVO vo = batchlist.get(0).get(0);
            String srcTypeName = OffSetSrcTypeEnum.getEnumByValue((int)vo.getOffSetSrcType()).getSrcTypeName();
            if (GcCalcOffsetInitLogServiceImpl.initOffsetSrcTypeList.contains(new Integer(vo.getOffSetSrcType()))) {
                YearPeriodObject yp = new YearPeriodObject(null, StringUtils.isEmpty(periodStr) ? vo.getAcctYear() + "Y0012" : periodStr);
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)vo.getUnitVersion(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
                GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(vo.getUnitId()), tool.getOrgByCode(vo.getOppUnitId()));
                this.offsetInitLogService.insertOrUpdateCalcLogEO(commonUnit.getCode(), vo.getAcctYear().toString(), srcTypeName + "\u53d1\u751f\u4fee\u6539");
            }
        }
        return BusinessResponseEntity.ok(idList);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> initSubjectVO(@Valid @RequestBody List<GcOffSetVchrItemInitVO> voList) {
        GcBaseDataCenterTool instance = GcBaseDataCenterTool.getInstance();
        for (GcOffSetVchrItemInitVO vo : voList) {
            String enumSourceUnitCode;
            String subjCode = vo.getSubjectCode();
            ConsolidatedSubjectEO subjectEO = this.consolidatedSubjectService.getSubjectByCode(vo.getSystemId(), subjCode);
            BaseDataVO subjVo = new BaseDataVO();
            BeanUtils.copyProperties(subjectEO, subjVo);
            vo.setSubjectVo(subjVo);
            GcOrgCacheVO u = vo.getUnitVo() != null ? vo.getUnitVo() : vo.getOppUnitVo();
            GcOrgCacheVO unit = GcOrgPublicTool.getInstance(null, (GcAuthorityType)GcAuthorityType.NONE).getOrgByCode(u.getId());
            if (unit == null) {
                vo.addUnSysFieldValue("AREACODE", null);
                vo.addUnSysFieldValue("YWBKCODE", null);
                vo.setGcywlxVo(null);
                continue;
            }
            if (StringUtils.isEmpty(unit.getBaseFieldValue("AREACODE"))) {
                vo.addUnSysFieldValue("AREACODE", null);
            } else {
                enumSourceUnitCode = this.enumSourceUnitCode(vo, unit, "AREACODE");
                GcBaseData area = instance.queryBasedataByCode("MD_AREA", enumSourceUnitCode);
                vo.addUnSysFieldValue("AREACODE", (Object)instance.convertBaseDataVO(area));
            }
            if (StringUtils.isEmpty(unit.getBaseFieldValue("YWBKCODE"))) {
                vo.addUnSysFieldValue("YWBKCODE", null);
            } else {
                enumSourceUnitCode = this.enumSourceUnitCode(vo, unit, "YWBKCODE");
                GcBaseData ywbk = instance.queryBasedataByCode("MD_YWBK", enumSourceUnitCode);
                vo.addUnSysFieldValue("YWBKCODE", (Object)instance.convertBaseDataVO(ywbk));
            }
            if (StringUtils.isEmpty(unit.getBaseFieldValue("GCYWLXCODE"))) {
                vo.setGcywlxVo(null);
                continue;
            }
            enumSourceUnitCode = this.enumSourceUnitCode(vo, unit, "GCYWLXCODE");
            GcBaseData gcywlx = instance.queryBasedataByCode("MD_GCYWLX", enumSourceUnitCode);
            vo.addUnSysFieldValue("GCYWLXCODE", (Object)instance.convertBaseDataVO(gcywlx));
        }
        return BusinessResponseEntity.ok(voList);
    }

    private String enumSourceUnitCode(GcOffSetVchrItemInitVO vo, GcOrgCacheVO unit, String unsysCode) {
        Object unsysValueSource = vo.getRuleItem().get(unsysCode);
        GcOrgCacheVO destUnit = null;
        if (null == unsysValueSource) {
            destUnit = unit;
        } else if (InvestmentUnitEnum.INVESTMENT_UNIT.getCode().equals(unsysValueSource)) {
            destUnit = GcOrgPublicTool.getInstance().getOrgByCode(vo.getInvestUnitVo().getId());
        } else if (InvestmentUnitEnum.INVESTED_UNIT.getCode().equals(unsysValueSource)) {
            destUnit = GcOrgPublicTool.getInstance().getOrgByCode(vo.getInvestedUnitVo().getId());
        }
        Object enumSourceUnitCode = destUnit.getBaseFieldValue(unsysCode);
        if (null == enumSourceUnitCode) {
            return "";
        }
        return enumSourceUnitCode.toString();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> initGetOrgWDZ(@RequestBody GcOrgCacheInnerVO org) {
        if (StringUtils.isEmpty(org.getId())) {
            org.setId(org.getCode());
        }
        ArrayList<BaseDataVO> result = new ArrayList<BaseDataVO>();
        Object areaObj = org.getBaseFieldValue("AREACODE");
        Object ywbkObj = org.getBaseFieldValue("YWBKCODE");
        Object gcywlxObj = org.getBaseFieldValue("GCYWLXCODE");
        GcBaseDataCenterTool instance = GcBaseDataCenterTool.getInstance();
        if (StringUtils.isEmpty(areaObj)) {
            result.add(null);
        } else {
            GcBaseData area = instance.queryBasedataByCode("MD_AREA", areaObj.toString());
            result.add(instance.convertBaseDataVO(area));
        }
        if (StringUtils.isEmpty(ywbkObj)) {
            result.add(null);
        } else {
            GcBaseData ywbk = instance.queryBasedataByCode("MD_YWBK", ywbkObj.toString());
            result.add(instance.convertBaseDataVO(ywbk));
        }
        if (StringUtils.isEmpty(gcywlxObj)) {
            result.add(null);
        } else {
            GcBaseData gcywlx = instance.queryBasedataByCode("MD_GCYWLX", gcywlxObj.toString());
            result.add(instance.convertBaseDataVO(gcywlx));
        }
        return BusinessResponseEntity.ok(result);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> deleteInitAdjustSameSrcId(List<String> mrecids, String taskId, String orgType, String periodStr) {
        CalcLogUtil.getInstance().log(this.getClass(), "\u62b5\u9500\u521d\u59cb\u52fe\u9009\u7ea7\u8054\u5220\u9664mrecids", mrecids);
        GcOffSetVchrDTO offSetItemDTO = null;
        List<GcOffSetVchrItemVO> investmentOffsetList = null;
        if (mrecids != null && mrecids.size() != 0) {
            offSetItemDTO = this.offSetInitService.getOne(mrecids.get(0));
            investmentOffsetList = this.offSetInitService.getInvestmentOffsetItemByMrecids(mrecids);
        }
        this.deleteDoLog(mrecids, taskId);
        this.offSetInitService.deleteInitAdjustSameSrcId(mrecids, taskId);
        this.updateInitOffsetLog(offSetItemDTO, orgType, periodStr, "\u53d1\u751f\u5220\u9664\u64cd\u4f5c");
        this.deleteChangeInvestmentStatus(investmentOffsetList);
        return BusinessResponseEntity.ok();
    }

    private void deleteDoLog(List<String> mrecids, String taskId) {
        try {
            List<GcOffSetVchrItemInitEO> gcOffSetVchrItemInitEOS = this.offSetVchrItemInitDao.queryOffsetingEntryEO(mrecids);
            if (CollectionUtils.isEmpty(gcOffSetVchrItemInitEOS)) {
                return;
            }
            Integer acctYear = gcOffSetVchrItemInitEOS.get(0).getAcctYear();
            String systemId = gcOffSetVchrItemInitEOS.get(0).getSystemId();
            StringBuffer message = new StringBuffer("\u8be6\u7ec6\u4fe1\u606f\uff1a");
            HashSet unitOppunitRuleSet = new HashSet();
            gcOffSetVchrItemInitEOS.forEach(item -> {
                String unitOppUnitRuleStr = item.getUnitId() + "|" + item.getOppUnitId() + "|" + item.getRuleId();
                if (unitOppunitRuleSet.contains(unitOppUnitRuleStr)) {
                    return;
                }
                unitOppunitRuleSet.add(unitOppUnitRuleStr);
                message.append("\u672c\u65b9\u5355\u4f4d:").append(item.getUnitId()).append("\u5bf9\u65b9\u5355\u4f4d").append(item.getOppUnitId()).append("\u89c4\u5219").append(item.getRuleId()).append("\n");
            });
            String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId))).getSystemName();
            LogHelper.info((String)"\u5408\u5e76-\u5206\u5f55\u521d\u59cb\u5316", (String)("\u5220\u9664-\u4f53\u7cfb" + systemName + "\u65f6\u671f-" + acctYear), (String)message.toString());
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u5220\u9664\u65e5\u5fd7\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private void deleteChangeInvestmentStatus(List<GcOffSetVchrItemVO> investmentOffsetList) {
        if (!CollectionUtils.isEmpty(investmentOffsetList)) {
            Map<String, List<GcOffSetVchrItemVO>> mrecidMap = investmentOffsetList.stream().collect(Collectors.groupingBy(GcOffSetVchrItemVO::getmRecid));
            for (String mrecid : mrecidMap.keySet()) {
                if (!this.hasOffsetRecord(mrecidMap.get(mrecid), true)) {
                    this.gcOffSetInitAssistService.changeInvestmentOffsetStatus(mrecidMap.get(mrecid), false, true, false);
                }
                if (this.hasOffsetRecord(mrecidMap.get(mrecid), false)) continue;
                this.gcOffSetInitAssistService.changeInvestmentOffsetStatus(mrecidMap.get(mrecid), false, false, true);
            }
        }
    }

    private boolean hasOffsetRecord(List<GcOffSetVchrItemVO> gcOffSetVchrItemVOS, boolean isTz) {
        GcOffSetVchrItemVO itemVO = gcOffSetVchrItemVOS.get(0);
        return this.offSetInitService.hasOffsetRecordByUnitAndRuleType(itemVO.getSrcOffsetGroupId(), itemVO.getAcctYear(), isTz);
    }

    private void updateInitOffsetLog(GcOffSetVchrDTO offSetItemDTO, String orgType, String periodStr, String logInfo) {
        if (offSetItemDTO == null) {
            return;
        }
        if (offSetItemDTO != null && offSetItemDTO.getItems() != null && offSetItemDTO.getItems().size() != 0) {
            List initOffsetItem = offSetItemDTO.getItems();
            GcOffSetVchrItemDTO vo = (GcOffSetVchrItemDTO)initOffsetItem.get(0);
            String srcTypeName = vo.getOffSetSrcType().getSrcTypeName();
            if (GcCalcOffsetInitLogServiceImpl.initOffsetSrcTypeList.contains(new Integer(vo.getOffSetSrcType().getSrcTypeValue()))) {
                YearPeriodObject yp = new YearPeriodObject(null, StringUtils.isEmpty(periodStr) ? vo.getAcctYear() + "Y0012" : periodStr);
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
                GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(vo.getUnitId()), tool.getOrgByCode(vo.getOppUnitId()));
                this.offsetInitLogService.insertOrUpdateCalcLogEO(commonUnit.getCode(), vo.getAcctYear().toString(), srcTypeName + logInfo);
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteOffsetEntrys(@Valid @RequestBody OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        if (null == offsetItemInitQueryParamsVO.getAcctYear() || offsetItemInitQueryParamsVO.getSystemId() == null) {
            return BusinessResponseEntity.ok((Object)"\u975e\u6cd5\u5220\u9664");
        }
        if (null != offsetItemInitQueryParamsVO.getAcctPeriod() && offsetItemInitQueryParamsVO.getAcctPeriod() > 0) {
            return BusinessResponseEntity.ok((Object)"\u975e\u6cd5\u5220\u9664");
        }
        offsetItemInitQueryParamsVO.setOffSetSrcTypes((Collection)OffSetSrcTypeEnum.getAllInitOffSetSrcTypeValue());
        CalcLogUtil.getInstance().log(this.getClass(), "\u62b5\u9500\u521d\u59cb\u6279\u91cf\u5220\u9664", (Object)offsetItemInitQueryParamsVO);
        HashSet<String> mrecids = new HashSet<String>();
        this.offSetVchrItemInitDao.queryMrecids(offsetItemInitQueryParamsVO, mrecids);
        GcOffSetVchrDTO offSetItemDTO = null;
        List<GcOffSetVchrItemVO> investmentOffsetList = null;
        if (mrecids != null && mrecids.size() != 0) {
            offSetItemDTO = this.offSetInitService.getOne((String)mrecids.iterator().next());
            investmentOffsetList = this.offSetInitService.getInvestmentOffsetItemByMrecids(new ArrayList<String>(mrecids));
        }
        this.offSetInitService.deleteAllInitOffsetEntrys(offsetItemInitQueryParamsVO);
        this.deleteChangeInvestmentStatus(investmentOffsetList);
        this.updateInitOffsetLog(offSetItemDTO, offsetItemInitQueryParamsVO.getOrgType(), offsetItemInitQueryParamsVO.getPeriodStr(), "\u53d1\u751f\u5220\u9664\u64cd\u4f5c");
        return BusinessResponseEntity.ok((Object)"\u53d6\u6d88\u6210\u529f.");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<GcOffSetVchrVO> queryAdjust(String mrecid, String orgType, String periodStr) {
        GcOffSetVchrDTO offSetItemDTO = this.offSetInitService.getOne(mrecid);
        if (!CollectionUtils.isEmpty((Collection)offSetItemDTO.getItems())) {
            offSetItemDTO.getItems().forEach(item -> {
                item.setUnitVersion(orgType);
                item.setDefaultPeriod(periodStr);
            });
        }
        GcOffSetVchrVO offSetItemVO = OffsetConvertUtil.getInstance().convertDTO2VO(offSetItemDTO);
        return BusinessResponseEntity.ok((Object)offSetItemVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> queryAdjustByPageCondition(@RequestParam(value="pageSize") Integer pageSize, @RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="showQueryCount") boolean showQueryCount, @RequestParam(value="queryConditions") @RequestBody String queryConditions) {
        Map conditionMap = (Map)JsonUtils.readValue((String)queryConditions, (TypeReference)new TypeReference<Map<String, Object>>(){});
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = this.initOffsetItemInitQueryParamsVO(conditionMap);
        offsetItemInitQueryParamsVO.setPageNum(pageNum.intValue());
        offsetItemInitQueryParamsVO.setPageSize(pageSize.intValue());
        return this.queryOffsetEntry(offsetItemInitQueryParamsVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> queryOffsetEntry(@RequestBody OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        return BusinessResponseEntity.ok(this.offSetInitService.queryOffsetEntry(offsetItemInitQueryParamsVO));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<GcOffSetVchrPageVo> queryAdjustByCondition(@RequestParam(value="queryConditions") @RequestBody String queryConditions) {
        Map conditionMap = (Map)JsonUtils.readValue((String)queryConditions, (TypeReference)new TypeReference<HashMap<String, Object>>(){});
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = this.initOffsetItemInitQueryParamsVO(conditionMap);
        List<GcOffSetVchrItemDTO> gcOffSetVchrItemDTOs = this.offSetInitService.queryOffsetingEntryDTOSort(offsetItemInitQueryParamsVO);
        List gcOffSetVchrItemVOs = gcOffSetVchrItemDTOs.stream().map(gcOffSetVchrItemDTO -> this.offSetItemAdjustService.convertDTO2VO(gcOffSetVchrItemDTO)).collect(Collectors.toList());
        GcOffSetVchrPageVo gcOffSetVchrResult = new GcOffSetVchrPageVo();
        gcOffSetVchrResult.setItemVOs(gcOffSetVchrItemVOs);
        return BusinessResponseEntity.ok((Object)gcOffSetVchrResult);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Collection<List<GcOffSetVchrItemVO>>> queryByInvestment(@RequestBody GcOffsetItemQueryCondi condi) {
        condi.orgType = null;
        List<GcOffSetVchrItemDTO> offSetItemDTOs = this.offSetInitService.findByInvestment(condi);
        if (CollectionUtils.isEmpty(offSetItemDTOs)) {
            return BusinessResponseEntity.ok();
        }
        List gcOffSetVchrItemVOs = offSetItemDTOs.stream().map(gcOffSetVchrItemDTO -> this.offSetItemAdjustService.convertDTO2VO(gcOffSetVchrItemDTO)).collect(Collectors.toList());
        OffsetItemComparatorUtil.mapSortComparatorVO(gcOffSetVchrItemVOs);
        HashMap voMap = new HashMap();
        for (GcOffSetVchrItemVO vo : gcOffSetVchrItemVOs) {
            if (voMap.get(vo.getmRecid()) == null) {
                ArrayList<GcOffSetVchrItemVO> list = new ArrayList<GcOffSetVchrItemVO>();
                list.add(vo);
                voMap.put(vo.getmRecid(), list);
                continue;
            }
            ((List)voMap.get(vo.getmRecid())).add(vo);
        }
        Collection values = voMap.values();
        return BusinessResponseEntity.ok(values);
    }

    private OffsetItemInitQueryParamsVO initOffsetItemInitQueryParamsVO(Map<String, Object> queryConditions) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = new OffsetItemInitQueryParamsVO();
        offsetItemInitQueryParamsVO.setOrgType(MapUtils.getStr(queryConditions, (Object)"orgType"));
        offsetItemInitQueryParamsVO.setOrgId(MapUtils.getStr(queryConditions, (Object)"inputUnitIdCond"));
        offsetItemInitQueryParamsVO.setTaskId(MapUtils.getStr(queryConditions, (Object)"taskIdCond"));
        offsetItemInitQueryParamsVO.setAcctYear(MapUtils.getInteger(queryConditions, (Object)"acctYearCond"));
        offsetItemInitQueryParamsVO.setAcctPeriod(MapUtils.getInteger(queryConditions, (Object)"acctPeriodCond"));
        offsetItemInitQueryParamsVO.setPeriodStr(MapUtils.getStr(queryConditions, (Object)"defaultPeriod"));
        offsetItemInitQueryParamsVO.setCurrency(MapUtils.getStr(queryConditions, (Object)"offSetCurrCond"));
        return offsetItemInitQueryParamsVO;
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<GcBusinessTypeCountVO>> rootBusinessTypes(@RequestBody OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        return BusinessResponseEntity.ok(this.offSetInitService.rootBusinessTypes(offsetItemInitQueryParamsVO));
    }

    public void downloadErrorExcel(String sn, HttpServletResponse response) {
        Assert.notNull((Object)sn, "\u5bfc\u5165\u6279\u6b21\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
        this.offSetInitService.downloadErrorExcel(sn, response);
    }

    public BusinessResponseEntity<String> updateOffsetInitDisabledFlag(List<String> mrecids, String orgType, String periodStr, boolean isDisabled) {
        this.offSetInitService.updateOffsetInitDisabledFlag(mrecids, isDisabled);
        GcOffSetVchrDTO offSetItemDTO = null;
        if (mrecids != null && mrecids.size() != 0) {
            offSetItemDTO = this.offSetInitService.getOne(mrecids.get(0));
        }
        this.updateInitOffsetLog(offSetItemDTO, orgType, periodStr, "\u4fee\u6539\u4e86\u7981\u7528\u72b6\u6001");
        return BusinessResponseEntity.ok();
    }
}

