/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.certification.bean.NvwaApp
 *  com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaCertifyAppExtendManager
 *  com.jiuqi.nvwa.certification.service.INvwaAppService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.workingpaper.querytask.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperCubesSubjectQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperCubesPenBalanceParamDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperSubjectQmsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.certification.bean.NvwaApp;
import com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO;
import com.jiuqi.nvwa.certification.manage.INvwaCertifyAppExtendManager;
import com.jiuqi.nvwa.certification.service.INvwaAppService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkingPaperCubesSubjectQueryTaskImpl
extends AbstractWorkingPaperCubesSubjectQueryTask {
    @Autowired
    private INvwaCertifyAppExtendManager nvwaCertifyExtendManager;
    @Autowired
    private INvwaAppService nvwaAppService;

    @Override
    public List<WorkingPaperTableHeaderVO> getHeaderVO(WorkingPaperQueryCondition queryCondition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        ArrayList<WorkingPaperTableHeaderVO> titles = new ArrayList<WorkingPaperTableHeaderVO>();
        titles.add(new WorkingPaperTableHeaderVO("kmcode", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combiningsubjectcode"), "left", (Object)"left", 130));
        titles.add(new WorkingPaperTableHeaderVO("kmname", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combiningsubjectname"), "left", (Object)"left", 200));
        titles.add(new WorkingPaperTableHeaderVO("kmorient", GcI18nUtil.getMessage((String)"gc.workingpaper.header.subjectdirection"), "center", (Object)"left", 90));
        Map assistValues = queryCondition.getAssistName2Labels();
        if (assistValues != null) {
            assistValues.forEach((assistName, assistTitle) -> titles.add(new WorkingPaperTableHeaderVO(assistName + "_SHOWVALUE", assistTitle, "center", (Object)"left", 90)));
        }
        Map<String, GcOrgCacheVO> directChildrens = this.getDirectOrgCode2DataMap(queryCondition);
        Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrg = this.getDirectOrgCode2DataContainMergeOrgAndDiffOrgMap(queryCondition);
        switch (qmsTypeEnum) {
            case CONTAIN_DXS: {
                WorkingPaperTableHeaderVO qmsVO = new WorkingPaperTableHeaderVO("QMS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closing"), "center");
                orgDirectChildsContainMergeOrgAndDiffOrg.forEach((orgCode, org) -> {
                    WorkingPaperTableHeaderVO headerVO = new WorkingPaperTableHeaderVO("QMS_CONTAIN_DXS_SHOWVALUE_" + org.getCode(), org.getTitle(), "right");
                    headerVO.getParams().put("orgId", org.getCode());
                    headerVO.getParams().put("orgKind", org.getOrgKind().getCode());
                    qmsVO.addChildren(headerVO);
                });
                queryCondition.setOrgchild(orgDirectChildsContainMergeOrgAndDiffOrg.values().stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
                qmsVO.addChildren(new WorkingPaperTableHeaderVO("QMS_CONTAIN_DXS_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closingsum"), "right"));
                titles.add(qmsVO);
                break;
            }
            case NOT_CONTAIN_DXS: {
                WorkingPaperTableHeaderVO qmsVO2 = new WorkingPaperTableHeaderVO("QMS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closing"), "center");
                directChildrens.forEach((orgCode, org) -> {
                    WorkingPaperTableHeaderVO headerVO = new WorkingPaperTableHeaderVO("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + org.getCode(), org.getTitle(), "right");
                    headerVO.getParams().put("orgId", org.getCode());
                    headerVO.getParams().put("orgKind", org.getOrgKind().getCode());
                    qmsVO2.addChildren(headerVO);
                });
                queryCondition.setOrgchild(directChildrens.values().stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
                qmsVO2.addChildren(new WorkingPaperTableHeaderVO("QMS_NOT_CONTAIN_DXS_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closingsum"), "right"));
                titles.add(qmsVO2);
                break;
            }
            case NO_SHOW: {
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + qmsTypeEnum.getTitle());
            }
        }
        switch (dxsTypeEnum) {
            case JD: {
                WorkingPaperTableHeaderVO dxsVO = new WorkingPaperTableHeaderVO("DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offset"), "center");
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JD_DEBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.borrower"), "right"));
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JD_CREBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.lender"), "right"));
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JD_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offsetsum"), "right", (Object)false, 200));
                titles.add(dxsVO);
                break;
            }
            case JZDF: {
                WorkingPaperTableHeaderVO dxsVO = new WorkingPaperTableHeaderVO("DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offset"), "center");
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_JZDF_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offsetsum"), "right", (Object)false, 200));
                titles.add(dxsVO);
                break;
            }
            case YWLX: {
                WorkingPaperTableHeaderVO dxsVO = new WorkingPaperTableHeaderVO("DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offset"), "center");
                Map<String, String> leafYwlxTitleMap = this.getLeafYwlxCode2TitleMap();
                leafYwlxTitleMap.forEach((ywlxCode, ywlxTitle) -> dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_YWLX_SHOWVALUE_" + ywlxCode, ywlxTitle, "right")));
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_YWLX_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offsetsum"), "right", (Object)false, 200));
                titles.add(dxsVO);
                break;
            }
            case ELMMODE: {
                WorkingPaperTableHeaderVO dxsVO = new WorkingPaperTableHeaderVO("DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offset"), "center");
                Map<String, String> offSetElmModeCode2TitleMap = this.getOffSetElmModeCode2TitleMap();
                offSetElmModeCode2TitleMap.forEach((code, title) -> dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_ELMMODE_SHOWVALUE_" + code, title, "right")));
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_ELMMODE_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offsetsum"), "right", (Object)false, 200));
                titles.add(dxsVO);
                break;
            }
            case UNIT: {
                WorkingPaperTableHeaderVO dxsVO = new WorkingPaperTableHeaderVO("DXS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offset"), "center");
                directChildrens.forEach((orgCode, org) -> {
                    WorkingPaperTableHeaderVO headerVO = new WorkingPaperTableHeaderVO("DXS_UNIT_SHOWVALUE_" + org.getCode(), org.getTitle(), "right");
                    headerVO.getParams().put("orgId", org.getCode());
                    headerVO.getParams().put("orgKind", org.getOrgKind().getCode());
                    dxsVO.addChildren(headerVO);
                });
                dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_UNIT_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.offsetsum"), "right", (Object)false, 200));
                titles.add(dxsVO);
                break;
            }
            case NO_SHOW: {
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + dxsTypeEnum.getTitle());
            }
        }
        titles.add(new WorkingPaperTableHeaderVO("HBS_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combining"), "right", (Object)false, 200));
        return titles;
    }

    @Override
    public List<WorkingPaperTableDataVO> getDataVO(WorkingPaperQueryCondition condition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        Map<String, ConsolidatedSubjectVO> subjectCode2DataMap = this.getSubjectCode2DataMap(condition);
        Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap = WorkingPaperQueryUtils.getSubjectCode2AllChildCodeSetMap(subjectCode2DataMap.keySet(), this.getSystemId(condition.getSchemeID(), condition.getPeriodStr()), true);
        Map<String, GcOrgCacheVO> directChildOrgCode2DataMap = this.getDirectOrgCode2DataMap(condition);
        Map<String, GcOrgCacheVO> orgDirectChildsContainSelfOrgMap = this.getDirectOrgCode2DataContainSelfMap(condition);
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        Date orgvalidate = yp.formatYP().getEndDate();
        List orgCode2ParentDatas = EntNativeSqlDefaultDao.getInstance().selectMap("select CODE, PARENTS from " + condition.getOrg_type() + " where validtime <= ? and invalidtime > ?", new Object[]{orgvalidate, orgvalidate});
        HashMap orgCode2ParentCodes = new HashMap();
        if (!CollectionUtils.isEmpty((Collection)orgCode2ParentDatas)) {
            orgCode2ParentDatas.stream().forEach(orgCode2ParentData -> orgCode2ParentCodes.put(ConverterUtils.getAsString(orgCode2ParentData.get("CODE"), (String)""), ConverterUtils.getAsString(orgCode2ParentData.get("PARENTS"), (String)"")));
        }
        Map<String, String> ywlxCode2TitleMap = this.getLeafYwlxCode2TitleMap();
        Map<String, String> offSetElmModeCode2TitleMap = this.getOffSetElmModeCode2TitleMap();
        List<Map<String, Object>> cubesItems = this.queryCubesOffSetItems(condition, orgDirectChildsContainSelfOrgMap, subjectCode2DataMap);
        List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsItemDTOs = this.getWorkingPaperQmsItemDTOs(condition, subjectCode2DataMap, cubesItems);
        Map<String, List<WorkingPaperSubjectQmsItemDTO>> subjectCode2QmsDatasMap = this.getSubjectWorkingPaperQmsDTOs(subjectCode2ChildSubjectCodesContainsSelfMap, workingPaperQmsItemDTOs);
        List<WorkingPaperDxsItemDTO> workingPaperDxsItemDTOs = this.getWorkingPaperDxsItemDTOs(condition, subjectCode2DataMap, cubesItems);
        Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2DxsDatasMap = this.getSubjectWorkingPaperDxsDTOs(subjectCode2ChildSubjectCodesContainsSelfMap, workingPaperDxsItemDTOs);
        List<Object> workingPaperTableDataVOS = this.buildBlankWorkingPaperTableDataBySubjects(condition, subjectCode2DataMap, subjectCode2ChildSubjectCodesContainsSelfMap, subjectCode2QmsDatasMap, subjectCode2DxsDatasMap);
        Set assistNames = condition.getAssistName2Labels().keySet();
        GcOrgCacheVO mergeOrg = orgDirectChildsContainSelfOrgMap.get(condition.getOrgid());
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            String kmcode = dataVO.getKmcode();
            dataVO.getZbvalueStr().put("kmcode", kmcode);
            dataVO.getZbvalueStr().put("kmname", dataVO.getKmname());
            dataVO.getZbvalueStr().put("kmorient", dataVO.getKmorient());
            HashMap<String, String> assistName2DataValueMap = new HashMap<String, String>();
            assistNames.stream().forEach(assistName -> {
                String dataAssistValue = (String)dataVO.getZbvalueStr().get(assistName);
                assistName2DataValueMap.put((String)assistName, dataAssistValue);
            });
            List workingPaperCubesItemDTOsBySubjectCode = (List)subjectCode2DxsDatasMap.get(kmcode);
            List<WorkingPaperDxsItemDTO> workingPaperDxsItemDTOsBySubjectCodeAndAssist = this.filterDxsBySubjectAndAssists((Map<String, String>)assistName2DataValueMap, (WorkingPaperTableDataVO)dataVO, workingPaperCubesItemDTOsBySubjectCode);
            ArrayList ywlxTotalItems = new ArrayList();
            ywlxCode2TitleMap.forEach((ywlxCode, ywlxData) -> {
                List<WorkingPaperDxsItemDTO> matchItems = workingPaperDxsItemDTOsBySubjectCodeAndAssist.stream().filter(workingPaperCubesItemDTO -> {
                    if (!StringUtils.isEmpty((String)mergeOrg.getDiffUnitId()) && !workingPaperCubesItemDTO.getOrgCode().equals(mergeOrg.getDiffUnitId())) {
                        return false;
                    }
                    return ywlxCode.equals(workingPaperCubesItemDTO.getYwlxCode());
                }).collect(Collectors.toList());
                WorkingPaperDxsItemDTO ywlxMergeOffSetVchrItemDTO = this.mergeDxsAmountItem((WorkingPaperTableDataVO)dataVO, matchItems);
                BigDecimal currYwlxDxs = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetDebit(), (BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetCredit()) : NumberUtils.sub((BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetCredit(), (BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetDebit());
                dataVO.getZbvalue().put("DXS_YWLX_" + ywlxCode, currYwlxDxs);
                dataVO.getZbvalueStr().put("DXS_YWLX_SHOWVALUE_" + ywlxCode, WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(currYwlxDxs));
                ywlxTotalItems.add(currYwlxDxs);
            });
            BigDecimal ywlxDxsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])ywlxTotalItems.toArray(new BigDecimal[ywlxTotalItems.size()]));
            dataVO.getZbvalue().put("DXS_YWLX_TOTAL", ywlxDxsTotal);
            dataVO.getZbvalueStr().put("DXS_YWLX_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(ywlxDxsTotal));
            ArrayList elmModeTotalItems = new ArrayList();
            offSetElmModeCode2TitleMap.keySet().forEach(elmModeCode -> {
                List<WorkingPaperDxsItemDTO> matchItems = workingPaperDxsItemDTOsBySubjectCodeAndAssist.stream().filter(workingPaperCubesItemDTO -> {
                    if (!StringUtils.isEmpty((String)mergeOrg.getDiffUnitId()) && !workingPaperCubesItemDTO.getOrgCode().equals(mergeOrg.getDiffUnitId())) {
                        return false;
                    }
                    return elmModeCode.equals(workingPaperCubesItemDTO.getElmMode());
                }).collect(Collectors.toList());
                WorkingPaperDxsItemDTO elmModeMergeOffSetVchrItemDTO = this.mergeDxsAmountItem((WorkingPaperTableDataVO)dataVO, matchItems);
                BigDecimal currElmModeDxs = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetDebit(), (BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetCredit()) : NumberUtils.sub((BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetCredit(), (BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetDebit());
                dataVO.getZbvalue().put("DXS_ELMMODE_" + elmModeCode, currElmModeDxs);
                dataVO.getZbvalueStr().put("DXS_ELMMODE_SHOWVALUE_" + elmModeCode, WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(currElmModeDxs));
                elmModeTotalItems.add(currElmModeDxs);
            });
            BigDecimal elmModeDxsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])elmModeTotalItems.toArray(new BigDecimal[elmModeTotalItems.size()]));
            dataVO.getZbvalue().put("DXS_ELMMODE_TOTAL", elmModeDxsTotal);
            dataVO.getZbvalueStr().put("DXS_ELMMODE_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(elmModeDxsTotal));
            ArrayList unitTotalItems = new ArrayList();
            directChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                List<WorkingPaperDxsItemDTO> matchItems = workingPaperDxsItemDTOsBySubjectCodeAndAssist.stream().filter(workingPaperCubesItemDTO -> {
                    if (StringUtils.isEmpty((String)orgData.getDiffUnitId())) {
                        return orgCode.equals(workingPaperCubesItemDTO.getOrgCode());
                    }
                    if (!workingPaperCubesItemDTO.getOrgCode().equals(mergeOrg.getDiffUnitId())) {
                        return false;
                    }
                    String unitCode = workingPaperCubesItemDTO.getUnitCode();
                    String parentCodes = ConverterUtils.getAsString(orgCode2ParentCodes.get(unitCode), (String)"");
                    return parentCodes.contains(orgCode + "/");
                }).collect(Collectors.toList());
                WorkingPaperDxsItemDTO unitMergeOffSetVchrItemDTO = this.mergeDxsAmountItem((WorkingPaperTableDataVO)dataVO, matchItems);
                BigDecimal currUnitDxs = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetDebit(), (BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetCredit()) : NumberUtils.sub((BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetCredit(), (BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetDebit());
                dataVO.getZbvalue().put("DXS_UNIT_" + orgCode, currUnitDxs);
                dataVO.getZbvalueStr().put("DXS_UNIT_SHOWVALUE_" + orgCode, WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(currUnitDxs));
                unitTotalItems.add(currUnitDxs);
            });
            BigDecimal unitDxsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])unitTotalItems.toArray(new BigDecimal[unitTotalItems.size()]));
            dataVO.getZbvalue().put("DXS_UNIT_TOTAL", unitDxsTotal);
            dataVO.getZbvalueStr().put("DXS_UNIT_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(unitDxsTotal));
            List<WorkingPaperDxsItemDTO> jdMatchItems = workingPaperDxsItemDTOsBySubjectCodeAndAssist.stream().filter(workingPaperCubesItemDTO -> StringUtils.isEmpty((String)mergeOrg.getDiffUnitId()) || workingPaperCubesItemDTO.getOrgCode().equals(mergeOrg.getDiffUnitId())).collect(Collectors.toList());
            WorkingPaperDxsItemDTO jdMergeOffSetVchrItemDTO = this.mergeDxsAmountItem((WorkingPaperTableDataVO)dataVO, jdMatchItems);
            if (jdMergeOffSetVchrItemDTO == null) {
                jdMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
            }
            BigDecimal jdOffSetCredit = jdMergeOffSetVchrItemDTO.getOffSetCredit();
            BigDecimal jdOffSetDebit = jdMergeOffSetVchrItemDTO.getOffSetDebit();
            BigDecimal jdDxTotal = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)jdOffSetDebit, (BigDecimal)jdOffSetCredit) : NumberUtils.sub((BigDecimal)jdOffSetCredit, (BigDecimal)jdOffSetDebit);
            dataVO.getZbvalue().put("DXS_JD_DEBIT", jdOffSetDebit);
            dataVO.getZbvalueStr().put("DXS_JD_DEBIT_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(jdOffSetDebit));
            dataVO.getZbvalue().put("DXS_JD_CREBIT", jdOffSetCredit);
            dataVO.getZbvalueStr().put("DXS_JD_CREBIT_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(jdOffSetCredit));
            dataVO.getZbvalue().put("DXS_JD_TOTAL", jdDxTotal);
            dataVO.getZbvalueStr().put("DXS_JD_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(jdDxTotal));
            List<WorkingPaperDxsItemDTO> jzdfMatchItems = workingPaperDxsItemDTOsBySubjectCodeAndAssist.stream().filter(workingPaperCubesItemDTO -> StringUtils.isEmpty((String)mergeOrg.getDiffUnitId()) || workingPaperCubesItemDTO.getOrgCode().equals(mergeOrg.getDiffUnitId())).collect(Collectors.toList());
            WorkingPaperDxsItemDTO jzdfMergeOffSetVchrItemDTO = this.mergeDxsAmountItem((WorkingPaperTableDataVO)dataVO, jzdfMatchItems);
            if (jzdfMergeOffSetVchrItemDTO == null) {
                jzdfMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
            }
            BigDecimal jzdfOffSetCredit = jzdfMergeOffSetVchrItemDTO.getOffSetCredit();
            BigDecimal jzdfOffSetDebit = jzdfMergeOffSetVchrItemDTO.getOffSetDebit();
            BigDecimal jzdfDxTotal = NumberUtils.sub((BigDecimal)jzdfOffSetDebit, (BigDecimal)jzdfOffSetCredit);
            dataVO.getZbvalue().put("DXS_JZDF_TOTAL", jzdfDxTotal);
            dataVO.getZbvalueStr().put("DXS_JZDF_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(jzdfDxTotal));
        });
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            HashMap<String, String> assistName2DataValueMap = new HashMap<String, String>();
            assistNames.stream().forEach(assistName -> {
                String dataAssistValue = (String)dataVO.getZbvalueStr().get(assistName);
                assistName2DataValueMap.put((String)assistName, dataAssistValue);
            });
            List workingPaperQmsItemsBySubjectCode = (List)subjectCode2QmsDatasMap.get(dataVO.getKmcode());
            List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsItemsBySubjectCodeAndAssist = this.filterQmsBySubjectAndAssists((Map<String, String>)assistName2DataValueMap, (WorkingPaperTableDataVO)dataVO, workingPaperQmsItemsBySubjectCode);
            ArrayList unitTotalItems = new ArrayList();
            directChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                List<WorkingPaperSubjectQmsItemDTO> matchItems = workingPaperQmsItemsBySubjectCodeAndAssist.stream().filter(WorkingPaperCubesItemDTO -> orgCode.equals(WorkingPaperCubesItemDTO.getOrgCode())).collect(Collectors.toList());
                WorkingPaperSubjectQmsItemDTO workingPaperQmsItemDTO = this.mergeQmsAmountItem((WorkingPaperTableDataVO)dataVO, matchItems);
                dataVO.getZbvalue().put("QMS_NOT_CONTAIN_DXS_" + orgCode, workingPaperQmsItemDTO.getZbValue());
                dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + orgCode, WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(workingPaperQmsItemDTO.getZbValue()));
                unitTotalItems.add(workingPaperQmsItemDTO.getZbValue());
            });
            BigDecimal unitQmsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])unitTotalItems.toArray(new BigDecimal[unitTotalItems.size()]));
            dataVO.getZbvalue().put("QMS_NOT_CONTAIN_DXS_TOTAL", unitQmsTotal);
            dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(unitQmsTotal));
            ArrayList containDxsUnitTotalItems = new ArrayList();
            orgDirectChildsContainSelfOrgMap.forEach((orgCode, orgData) -> {
                BigDecimal containDxsUnitQms = NumberUtils.add((BigDecimal)((BigDecimal)dataVO.getZbvalue().get("QMS_NOT_CONTAIN_DXS_" + orgCode)), (BigDecimal[])new BigDecimal[]{(BigDecimal)dataVO.getZbvalue().get("DXS_UNIT_" + orgCode)});
                dataVO.getZbvalue().put("QMS_CONTAIN_DXS_" + orgCode, containDxsUnitQms);
                dataVO.getZbvalueStr().put("QMS_CONTAIN_DXS_SHOWVALUE_" + orgCode, WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(containDxsUnitQms));
                containDxsUnitTotalItems.add(containDxsUnitQms);
            });
            BigDecimal containDxsUnitQmsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])containDxsUnitTotalItems.toArray(new BigDecimal[containDxsUnitTotalItems.size()]));
            dataVO.getZbvalue().put("QMS_CONTAIN_DXS_TOTAL", containDxsUnitQmsTotal);
            dataVO.getZbvalueStr().put("QMS_CONTAIN_DXS_TOTAL_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(containDxsUnitQmsTotal));
        });
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            BigDecimal dxsTotal = BigDecimal.ZERO;
            switch (dxsTypeEnum) {
                case JD: {
                    dxsTotal = (BigDecimal)dataVO.getZbvalue().get("DXS_JD_TOTAL");
                    break;
                }
                case JZDF: {
                    dxsTotal = (BigDecimal)dataVO.getZbvalue().get("DXS_JZDF_TOTAL");
                    break;
                }
                case YWLX: {
                    dxsTotal = (BigDecimal)dataVO.getZbvalue().get("DXS_YWLX_TOTAL");
                    break;
                }
                case ELMMODE: {
                    dxsTotal = (BigDecimal)dataVO.getZbvalue().get("DXS_ELMMODE_TOTAL");
                    break;
                }
                case UNIT: {
                    dxsTotal = (BigDecimal)dataVO.getZbvalue().get("DXS_UNIT_TOTAL");
                    break;
                }
                case NO_SHOW: {
                    break;
                }
                default: {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + dxsTypeEnum.getTitle());
                }
            }
            BigDecimal hbsTotal = BigDecimal.ZERO;
            switch (qmsTypeEnum) {
                case NOT_CONTAIN_DXS: {
                    BigDecimal qmsTotal = (BigDecimal)dataVO.getZbvalue().get("QMS_NOT_CONTAIN_DXS_TOTAL");
                    hbsTotal = NumberUtils.add((BigDecimal)qmsTotal, (BigDecimal[])new BigDecimal[]{dxsTotal});
                    break;
                }
                case CONTAIN_DXS: {
                    BigDecimal qmsTotal;
                    hbsTotal = qmsTotal = (BigDecimal)dataVO.getZbvalue().get("QMS_CONTAIN_DXS_TOTAL");
                    break;
                }
                case NO_SHOW: {
                    hbsTotal = dxsTotal;
                    break;
                }
                default: {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + qmsTypeEnum.getTitle());
                }
            }
            dataVO.getZbvalue().put("HBS", hbsTotal);
            dataVO.getZbvalueStr().put("HBS_SHOWVALUE", WorkingPaperCubesSubjectQueryTaskImpl.formatBigDecimal(hbsTotal));
        });
        List conditionSubjectCodes = condition.getBaseSubjectCodes().stream().filter(Objects::nonNull).collect(Collectors.toList());
        workingPaperTableDataVOS = workingPaperTableDataVOS.stream().filter(dataVO -> CollectionUtils.isEmpty((Collection)conditionSubjectCodes) || conditionSubjectCodes.contains(dataVO.getKmcode())).collect(Collectors.toList());
        List<WorkingPaperTableDataVO> rebuildWorkPaperVos = this.rebuildWorkPaperResultVos(condition, qmsTypeEnum, dxsTypeEnum, orgDirectChildsContainSelfOrgMap, workingPaperTableDataVOS);
        return rebuildWorkPaperVos;
    }

    @Override
    public Map<String, Object> getWorkPaperPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion pentrationQueryCondtion, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        NvwaApp bdeApp = this.nvwaAppService.selectByCode("BDE");
        if (bdeApp == null) {
            throw new BusinessRuntimeException("\u5f53\u524d\u7cfb\u7edf\u670d\u52a1\u8ba4\u8bc1\u672a\u914d\u7f6eBDE\u670d\u52a1\u4fe1\u606f\u3002");
        }
        String pentraColumnName = pentrationQueryCondtion.getPentraColumnName();
        String pentraOrgID = pentraColumnName.substring(pentraColumnName.lastIndexOf("_") + 1);
        String workPaperVoJson = pentrationQueryCondtion.getWorkPaperVoJson();
        WorkingPaperTableDataVO workingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)workPaperVoJson, WorkingPaperTableDataVO.class);
        String subjectCode = workingPaperTableDataVO.getKmcode();
        String systemID = WorkingPaperQueryUtils.getSystemID((WorkingPaperQueryCondition)pentrationQueryCondtion);
        NvwaSsoBuildDTO nvwaSsoBuildDTO = new NvwaSsoBuildDTO();
        nvwaSsoBuildDTO.setFrontAddress(bdeApp.getFrontendURL());
        nvwaSsoBuildDTO.setUserName(NpContextHolder.getContext().getUserName());
        nvwaSsoBuildDTO.setJumpType("app");
        nvwaSsoBuildDTO.setScope("@dc");
        nvwaSsoBuildDTO.setAppName("BalanceQuery");
        WorkingPaperCubesPenBalanceParamDTO paperCubesPenBalanceParamDTO = new WorkingPaperCubesPenBalanceParamDTO();
        paperCubesPenBalanceParamDTO.setAcctYear(pentrationQueryCondtion.getAcctYear());
        paperCubesPenBalanceParamDTO.setContainAdjustVchr(true);
        paperCubesPenBalanceParamDTO.setCurrencyCode(pentrationQueryCondtion.getCurrencyCode());
        paperCubesPenBalanceParamDTO.setStartPeriod(pentrationQueryCondtion.getAcctPeriod());
        paperCubesPenBalanceParamDTO.setEndPeriod(pentrationQueryCondtion.getAcctPeriod());
        paperCubesPenBalanceParamDTO.setStartSubjectCode(subjectCode);
        paperCubesPenBalanceParamDTO.setEndSubjectCode(subjectCode);
        paperCubesPenBalanceParamDTO.setFinCurr(pentrationQueryCondtion.getCurrencyCode());
        paperCubesPenBalanceParamDTO.setRepCurrCode(pentrationQueryCondtion.getCurrencyCode());
        paperCubesPenBalanceParamDTO.setPage(1);
        paperCubesPenBalanceParamDTO.setPageSize(50);
        paperCubesPenBalanceParamDTO.setReportFlag(false);
        paperCubesPenBalanceParamDTO.setUnitCodes(Arrays.asList(pentraOrgID));
        ArrayList<WorkingPaperCubesPenBalanceParamDTO.QueryDim> queryDimList = new ArrayList<WorkingPaperCubesPenBalanceParamDTO.QueryDim>();
        Map assistName2LabelsMap = pentrationQueryCondtion.getAssistName2Labels();
        if (assistName2LabelsMap != null && assistName2LabelsMap.size() > 0) {
            String financialCubesDimTableName = FinancialCubesCommonUtil.getFinancialCubesDimTableName((FinancialCubesPeriodTypeEnum)FinancialCubesPeriodTypeEnum.getByCode((String)pentrationQueryCondtion.getPeriodTypeChar()));
            DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
            Map<String, String> dimensionCode2TableNameMap = dimensionService.findDimFieldsVOByTableName(financialCubesDimTableName).stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getDictTableName));
            HashMap map = (HashMap)JsonUtils.readValue((String)pentrationQueryCondtion.getWorkPaperVoJson(), (TypeReference)new TypeReference<HashMap<String, Object>>(){});
            assistName2LabelsMap.forEach((assistName, assistLabel) -> {
                String dataAssistValue = ConverterUtils.getAsString(map.get(assistName));
                queryDimList.add(new WorkingPaperCubesPenBalanceParamDTO.QueryDim((String)assistName, (String)assistLabel, dataAssistValue, dataAssistValue, (String)dimensionCode2TableNameMap.get(assistName)));
            });
        }
        paperCubesPenBalanceParamDTO.setQueryDimList(queryDimList);
        BaseDataDTO subjectParam = new BaseDataDTO();
        subjectParam.setTableName("MD_GCSUBJECT");
        subjectParam.put("systemid", (Object)systemID);
        subjectParam.setCode(subjectCode);
        subjectParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
        BaseDataClient baseDataClient = (BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class);
        PageVO pageVO = baseDataClient.list(subjectParam);
        BaseDataDO baseDataDO = null;
        if (pageVO.getTotal() > 0) {
            baseDataDO = (BaseDataDO)pageVO.getRows().get(0);
        }
        if (baseDataDO != null) {
            HashMap<String, Object> selectEndSub = new HashMap<String, Object>();
            selectEndSub.put("name", baseDataDO.getName());
            selectEndSub.put("objectcode", baseDataDO.getObjectcode());
            selectEndSub.put("code", baseDataDO.getCode());
            selectEndSub.put("parents", baseDataDO.getParents());
            paperCubesPenBalanceParamDTO.setSelectEndSub(selectEndSub);
            paperCubesPenBalanceParamDTO.setSelectStartSub(selectEndSub);
        }
        OrgDataClient orgDataClient = (OrgDataClient)SpringContextUtils.getBean(OrgDataClient.class);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setCode(pentraOrgID);
        orgDTO.setStopflag(Integer.valueOf(0));
        orgDTO.setRecoveryflag(Integer.valueOf(0));
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        OrgDO orgDO = orgDataClient.get(orgDTO);
        if (orgDO != null) {
            HashMap<String, Object> selectUnit = new HashMap<String, Object>();
            selectUnit.put("title", orgDO.getName());
            selectUnit.put("parentcode", orgDO.getParentcode());
            selectUnit.put("code", orgDO.getCode());
            selectUnit.put("key", orgDO.getCode());
            selectUnit.put("parents", orgDO.getParents());
            paperCubesPenBalanceParamDTO.setSelectUnit(selectUnit);
        }
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("systemCode", "GCREPORT");
        paramsMap.put("queryCondition", paperCubesPenBalanceParamDTO);
        return paramsMap;
    }
}

