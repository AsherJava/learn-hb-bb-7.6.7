/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.workingpaper.querytask.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperSubjectQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperSubjectQmsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkingPaperSubjectQueryTaskImpl
extends AbstractWorkingPaperSubjectQueryTask {
    @Override
    public List<WorkingPaperTableHeaderVO> getHeaderVO(WorkingPaperQueryCondition queryCondition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        ArrayList<WorkingPaperTableHeaderVO> titles = new ArrayList<WorkingPaperTableHeaderVO>();
        titles.add(new WorkingPaperTableHeaderVO("kmcode", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combiningsubjectcode"), "left", (Object)"left", 250));
        titles.add(new WorkingPaperTableHeaderVO("kmname", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combiningsubjectname"), "left", (Object)"left", 200));
        titles.add(new WorkingPaperTableHeaderVO("kmorient", GcI18nUtil.getMessage((String)"gc.workingpaper.header.subjectdirection"), "center", (Object)"left", 90));
        Map<String, GcOrgCacheVO> directChildrens = this.getDirectOrgCode2DataMap(queryCondition);
        Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrg = this.getDirectOrgCode2DataContainMergeOrgAndDiffOrgMap(queryCondition);
        switch (qmsTypeEnum) {
            case CONTAIN_DXS: {
                WorkingPaperTableHeaderVO qmsVO = new WorkingPaperTableHeaderVO("QMS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closing"), "center");
                orgDirectChildsContainMergeOrgAndDiffOrg.forEach((orgCode, org) -> qmsVO.addChildren(new WorkingPaperTableHeaderVO("QMS_CONTAIN_DXS_SHOWVALUE_" + org.getCode(), org.getTitle(), "right")));
                queryCondition.setOrgchild(orgDirectChildsContainMergeOrgAndDiffOrg.values().stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
                qmsVO.addChildren(new WorkingPaperTableHeaderVO("QMS_CONTAIN_DXS_TOTAL_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closingsum"), "right"));
                titles.add(qmsVO);
                break;
            }
            case NOT_CONTAIN_DXS: {
                WorkingPaperTableHeaderVO qmsVO2 = new WorkingPaperTableHeaderVO("QMS", GcI18nUtil.getMessage((String)"gc.workingpaper.header.closing"), "center");
                directChildrens.forEach((orgCode, org) -> qmsVO2.addChildren(new WorkingPaperTableHeaderVO("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + org.getCode(), org.getTitle(), "right")));
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
                orgDirectChildsContainMergeOrgAndDiffOrg.forEach((orgCode, org) -> dxsVO.addChildren(new WorkingPaperTableHeaderVO("DXS_UNIT_SHOWVALUE_" + org.getCode(), org.getTitle(), "right")));
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
        Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrgMap = this.getDirectOrgCode2DataContainMergeOrgAndDiffOrgMap(condition);
        Map<String, String> ywlxCode2TitleMap = this.getLeafYwlxCode2TitleMap();
        Map<String, String> offSetElmModeCode2TitleMap = this.getOffSetElmModeCode2TitleMap();
        List<Object> workingPaperTableDataVOS = this.buildBlankWorkingPaperTableDataBySubjects(condition, subjectCode2DataMap, subjectCode2ChildSubjectCodesContainsSelfMap);
        List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsDTOs = this.getWorkingPaperQmsDTOs(condition, workingPaperTableDataVOS, orgDirectChildsContainMergeOrgAndDiffOrgMap, subjectCode2DataMap);
        Map<String, WorkingPaperSubjectQmsItemDTO> mergeQmsItemDatasBySubjectAndOrgMap = this.mergeWorkingPaperQmsDTOsBySubjectAndOrg(condition, workingPaperQmsDTOs, subjectCode2DataMap, orgDirectChildsContainMergeOrgAndDiffOrgMap, subjectCode2ChildSubjectCodesContainsSelfMap);
        Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2OffsetItemDatasMap = this.getSubjectWorkingPaperOffsetItemDTOs(condition, subjectCode2ChildSubjectCodesContainsSelfMap, workingPaperTableDataVOS);
        Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectMap = this.mergeOffsetItemDatasBySubject(subjectCode2DataMap, subjectCode2OffsetItemDatasMap);
        Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndOrgMap = this.mergeOffsetItemDatasBySubjectAndOrg(condition, subjectCode2DataMap, subjectCode2OffsetItemDatasMap, orgDirectChildsContainMergeOrgAndDiffOrgMap);
        Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndYwlxMap = this.mergeOffsetItemDatasBySubjectAndYwlx(subjectCode2DataMap, subjectCode2OffsetItemDatasMap, ywlxCode2TitleMap);
        Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndElmMode = this.mergeOffsetItemDatasBySubjectAndElmMode(subjectCode2DataMap, subjectCode2OffsetItemDatasMap, offSetElmModeCode2TitleMap);
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            dataVO.getZbvalueStr().put("kmcode", dataVO.getKmcode());
            dataVO.getZbvalueStr().put("kmname", dataVO.getKmname());
            dataVO.getZbvalueStr().put("kmorient", dataVO.getKmorient());
            WorkingPaperDxsItemDTO jdMergeOffSetVchrItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectMap.get(dataVO.getKmcode());
            if (jdMergeOffSetVchrItemDTO == null) {
                jdMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
            }
            BigDecimal jdOffSetCredit = jdMergeOffSetVchrItemDTO.getOffSetCredit();
            BigDecimal jdOffSetDebit = jdMergeOffSetVchrItemDTO.getOffSetDebit();
            BigDecimal jdDxTotal = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)jdOffSetDebit, (BigDecimal)jdOffSetCredit) : NumberUtils.sub((BigDecimal)jdOffSetCredit, (BigDecimal)jdOffSetDebit);
            dataVO.getZbvalue().put("DXS_JD_DEBIT", jdOffSetDebit);
            dataVO.getZbvalueStr().put("DXS_JD_DEBIT_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(jdOffSetDebit));
            dataVO.getZbvalue().put("DXS_JD_CREBIT", jdOffSetCredit);
            dataVO.getZbvalueStr().put("DXS_JD_CREBIT_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(jdOffSetCredit));
            dataVO.getZbvalue().put("DXS_JD_TOTAL", jdDxTotal);
            dataVO.getZbvalueStr().put("DXS_JD_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(jdDxTotal));
            WorkingPaperDxsItemDTO jzdfMergeOffSetVchrItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectMap.get(dataVO.getKmcode());
            if (jzdfMergeOffSetVchrItemDTO == null) {
                jzdfMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
            }
            BigDecimal jzdfOffSetCredit = jzdfMergeOffSetVchrItemDTO.getOffSetCredit();
            BigDecimal jzdfOffSetDebit = jzdfMergeOffSetVchrItemDTO.getOffSetDebit();
            BigDecimal jzdfDxTotal = NumberUtils.sub((BigDecimal)jzdfOffSetDebit, (BigDecimal)jzdfOffSetCredit);
            dataVO.getZbvalue().put("DXS_JZDF_TOTAL", jzdfDxTotal);
            dataVO.getZbvalueStr().put("DXS_JZDF_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(jzdfDxTotal));
            ArrayList ywlxTotalItems = new ArrayList();
            ywlxCode2TitleMap.forEach((ywlxCode, ywlxData) -> {
                WorkingPaperDxsItemDTO ywlxMergeOffSetVchrItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectAndYwlxMap.get(dataVO.getKmcode() + "_" + ywlxCode);
                if (ywlxMergeOffSetVchrItemDTO == null) {
                    ywlxMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
                }
                BigDecimal currYwlxDxs = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetDebit(), (BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetCredit()) : NumberUtils.sub((BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetCredit(), (BigDecimal)ywlxMergeOffSetVchrItemDTO.getOffSetDebit());
                dataVO.getZbvalue().put("DXS_YWLX_" + ywlxCode, currYwlxDxs);
                dataVO.getZbvalueStr().put("DXS_YWLX_SHOWVALUE_" + ywlxCode, WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(currYwlxDxs));
                ywlxTotalItems.add(currYwlxDxs);
            });
            BigDecimal ywlxDxsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])ywlxTotalItems.toArray(new BigDecimal[ywlxTotalItems.size()]));
            dataVO.getZbvalue().put("DXS_YWLX_TOTAL", ywlxDxsTotal);
            dataVO.getZbvalueStr().put("DXS_YWLX_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(ywlxDxsTotal));
            ArrayList elmModeTotalItems = new ArrayList();
            offSetElmModeCode2TitleMap.keySet().forEach(elmModeCode -> {
                WorkingPaperDxsItemDTO elmModeMergeOffSetVchrItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectAndElmMode.get(dataVO.getKmcode() + "_" + elmModeCode);
                if (elmModeMergeOffSetVchrItemDTO == null) {
                    elmModeMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
                }
                BigDecimal currElmModeDxs = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetDebit(), (BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetCredit()) : NumberUtils.sub((BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetCredit(), (BigDecimal)elmModeMergeOffSetVchrItemDTO.getOffSetDebit());
                dataVO.getZbvalue().put("DXS_ELMMODE_" + elmModeCode, currElmModeDxs);
                dataVO.getZbvalueStr().put("DXS_ELMMODE_SHOWVALUE_" + elmModeCode, WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(currElmModeDxs));
                elmModeTotalItems.add(currElmModeDxs);
            });
            BigDecimal elmModeDxsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])elmModeTotalItems.toArray(new BigDecimal[elmModeTotalItems.size()]));
            dataVO.getZbvalue().put("DXS_ELMMODE_TOTAL", elmModeDxsTotal);
            dataVO.getZbvalueStr().put("DXS_ELMMODE_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(elmModeDxsTotal));
            ArrayList unitTotalItems = new ArrayList();
            orgDirectChildsContainMergeOrgAndDiffOrgMap.forEach((orgCode, orgData) -> {
                WorkingPaperDxsItemDTO unitMergeOffSetVchrItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectAndOrgMap.get(dataVO.getKmcode() + "_" + orgCode);
                if (unitMergeOffSetVchrItemDTO == null) {
                    unitMergeOffSetVchrItemDTO = WorkingPaperDxsItemDTO.empty();
                }
                BigDecimal currUnitDxs = OrientEnum.D.getValue().equals(dataVO.getOrient()) ? NumberUtils.sub((BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetDebit(), (BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetCredit()) : NumberUtils.sub((BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetCredit(), (BigDecimal)unitMergeOffSetVchrItemDTO.getOffSetDebit());
                dataVO.getZbvalue().put("DXS_UNIT_" + orgCode, currUnitDxs);
                dataVO.getZbvalueStr().put("DXS_UNIT_SHOWVALUE_" + orgCode, WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(currUnitDxs));
                unitTotalItems.add(currUnitDxs);
            });
            BigDecimal unitDxsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])unitTotalItems.toArray(new BigDecimal[unitTotalItems.size()]));
            dataVO.getZbvalue().put("DXS_UNIT_TOTAL", unitDxsTotal);
            dataVO.getZbvalueStr().put("DXS_UNIT_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(unitDxsTotal));
        });
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            ArrayList unitTotalItems = new ArrayList();
            directChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                WorkingPaperSubjectQmsItemDTO workingPaperQmsItemDTO = (WorkingPaperSubjectQmsItemDTO)mergeQmsItemDatasBySubjectAndOrgMap.get(dataVO.getKmcode() + "_" + orgCode);
                if (workingPaperQmsItemDTO == null) {
                    workingPaperQmsItemDTO = WorkingPaperSubjectQmsItemDTO.empty();
                }
                dataVO.getZbvalue().put("QMS_NOT_CONTAIN_DXS_" + orgCode, workingPaperQmsItemDTO.getZbValue());
                dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + orgCode, WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(workingPaperQmsItemDTO.getZbValue()));
                unitTotalItems.add(workingPaperQmsItemDTO.getZbValue());
            });
            BigDecimal unitQmsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])unitTotalItems.toArray(new BigDecimal[unitTotalItems.size()]));
            dataVO.getZbvalue().put("QMS_NOT_CONTAIN_DXS_TOTAL", unitQmsTotal);
            dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(unitQmsTotal));
            ArrayList containDxsUnitTotalItems = new ArrayList();
            orgDirectChildsContainMergeOrgAndDiffOrgMap.forEach((orgCode, orgData) -> {
                WorkingPaperSubjectQmsItemDTO workingPaperQmsItemDTO = (WorkingPaperSubjectQmsItemDTO)mergeQmsItemDatasBySubjectAndOrgMap.get(dataVO.getKmcode() + "_" + orgCode);
                if (workingPaperQmsItemDTO == null) {
                    workingPaperQmsItemDTO = WorkingPaperSubjectQmsItemDTO.empty();
                }
                BigDecimal containDxsUnitQms = NumberUtils.add((BigDecimal)((BigDecimal)dataVO.getZbvalue().get("QMS_NOT_CONTAIN_DXS_" + orgCode)), (BigDecimal[])new BigDecimal[]{(BigDecimal)dataVO.getZbvalue().get("DXS_UNIT_" + orgCode)});
                dataVO.getZbvalue().put("QMS_CONTAIN_DXS_" + orgCode, containDxsUnitQms);
                dataVO.getZbvalueStr().put("QMS_CONTAIN_DXS_SHOWVALUE_" + orgCode, WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(containDxsUnitQms));
                containDxsUnitTotalItems.add(containDxsUnitQms);
            });
            BigDecimal containDxsUnitQmsTotal = NumberUtils.add((BigDecimal)BigDecimal.ZERO, (BigDecimal[])containDxsUnitTotalItems.toArray(new BigDecimal[containDxsUnitTotalItems.size()]));
            dataVO.getZbvalue().put("QMS_CONTAIN_DXS_TOTAL", containDxsUnitQmsTotal);
            dataVO.getZbvalueStr().put("QMS_CONTAIN_DXS_TOTAL_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(containDxsUnitQmsTotal));
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
            dataVO.getZbvalueStr().put("HBS_SHOWVALUE", WorkingPaperSubjectQueryTaskImpl.formatBigDecimal(hbsTotal));
        });
        List conditionSubjectCodes = condition.getBaseSubjectCodes().stream().filter(Objects::nonNull).collect(Collectors.toList());
        workingPaperTableDataVOS = workingPaperTableDataVOS.stream().filter(dataVO -> CollectionUtils.isEmpty((Collection)conditionSubjectCodes) || conditionSubjectCodes.contains(dataVO.getKmcode())).collect(Collectors.toList());
        List<WorkingPaperTableDataVO> rebuildWorkPaperVos = this.rebuildWorkPaperResultVos(condition, qmsTypeEnum, dxsTypeEnum, directChildOrgCode2DataMap, workingPaperTableDataVOS);
        return rebuildWorkPaperVos;
    }

    public Pagination<Map<String, Object>> getWorkPaperPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion pentrationQueryCondtion, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        String pentraColumnName = pentrationQueryCondtion.getPentraColumnName();
        String workPaperVoJson = pentrationQueryCondtion.getWorkPaperVoJson();
        WorkingPaperTableDataVO workingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)workPaperVoJson, WorkingPaperTableDataVO.class);
        String subjectCode = workingPaperTableDataVO.getKmcode();
        String systemID = WorkingPaperQueryUtils.getSystemID((WorkingPaperQueryCondition)pentrationQueryCondtion);
        Set<String> subjectCodes = WorkingPaperQueryUtils.listAllChildCodesContainsSelf(systemID, subjectCode);
        List<String> orgCodes = null;
        String ywlxCode = null;
        String elmMode = null;
        switch (dxsTypeEnum) {
            case JD: {
                break;
            }
            case JZDF: {
                break;
            }
            case YWLX: {
                if ("DXS_YWLX_TOTAL_SHOWVALUE".equals(pentraColumnName)) {
                    ywlxCode = null;
                    break;
                }
                ywlxCode = pentraColumnName.replace("DXS_YWLX_SHOWVALUE_", "");
                break;
            }
            case ELMMODE: {
                if ("DXS_ELMMODE_TOTAL_SHOWVALUE".equals(pentraColumnName)) {
                    elmMode = null;
                    break;
                }
                elmMode = pentraColumnName.replace("DXS_ELMMODE_SHOWVALUE_", "");
                break;
            }
            case UNIT: {
                if ("DXS_UNIT_TOTAL_SHOWVALUE".equals(pentraColumnName)) {
                    orgCodes = null;
                    break;
                }
                String orgCode = pentraColumnName.replace("DXS_UNIT_SHOWVALUE_", "");
                orgCodes = this.listAllOrgByParentIdContainsSelf((WorkingPaperQueryCondition)pentrationQueryCondtion, orgCode);
                break;
            }
            case NO_SHOW: {
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + dxsTypeEnum.getTitle());
            }
        }
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(pentrationQueryCondtion, true);
        if (!CollectionUtils.isEmpty(subjectCodes)) {
            queryParamsVO.setSubjectCodes(new ArrayList<String>(subjectCodes));
        }
        if (!CollectionUtils.isEmpty(orgCodes)) {
            queryParamsVO.setUnitIdList(orgCodes);
        }
        if (!StringUtils.isEmpty((String)elmMode)) {
            if ("OTHER".equals(elmMode)) {
                queryParamsVO.setOnlyQueryNullElmMode(true);
            } else {
                queryParamsVO.setElmModes(Arrays.asList(Integer.valueOf(elmMode)));
            }
        }
        if (!StringUtils.isEmpty((String)ywlxCode)) {
            if ("OTHER".equals(ywlxCode)) {
                queryParamsVO.setOnlyQueryNullGcBusinessTypeCode(true);
            } else {
                queryParamsVO.setGcBusinessTypeCodes(Arrays.asList(ywlxCode));
            }
        }
        GcOffSetAppOffsetService offSetItemAdjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
        Pagination offsetPage = offSetItemAdjustService.listOffsetEntrys(queryParamsVO, false);
        List<Map<String, Object>> offsetDatas = offsetPage.getContent();
        Set mRecids = offsetDatas.stream().map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet());
        offsetPage.setTotalElements(offsetPage.getTotalElements());
        offsetDatas = offsetDatas.stream().filter(offset -> mRecids.contains(offset.get("MRECID"))).collect(Collectors.toList());
        offsetDatas = WorkingPaperQueryUtils.setRowSpanAndSort(offsetDatas);
        offsetPage.setContent(offsetDatas);
        return offsetPage;
    }
}

