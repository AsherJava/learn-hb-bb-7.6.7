/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.dao.WorkingPaperQueryWayDao;
import com.jiuqi.gcreport.workingpaper.entity.WorkingPaperQueryWayItemEO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.WorkingPaperQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractWorkingPaperQueryTask
implements WorkingPaperQueryTask {
    private static ThreadLocal<NumberFormat> decimalFormatThreadLocal = ThreadLocal.withInitial(() -> {
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf;
    });
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWorkingPaperQueryTask.class);
    protected static final String MERGE_STRING_CHAR = "_";
    protected static final String OTHER_CODE = "OTHER";
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;

    public static String formatBigDecimal(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) {
            return "0.00";
        }
        return decimalFormatThreadLocal.get().format(bigDecimal);
    }

    @Override
    public WorkingPaperTableVO queryAllData(WorkingPaperQueryCondition queryCondition) {
        String queryWayId = queryCondition.getQueryWayId();
        WorkingPaperQueryWayDao paperQueryWayDao = (WorkingPaperQueryWayDao)SpringContextUtils.getBean(WorkingPaperQueryWayDao.class);
        WorkingPaperQueryWayItemEO workPaperQueryWayItemEO = (WorkingPaperQueryWayItemEO)paperQueryWayDao.get((Serializable)((Object)queryWayId));
        if (workPaperQueryWayItemEO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposed") + queryWayId);
        }
        WorkingPaperDxsTypeEnum dxsTypeEnum = WorkingPaperDxsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getDxsType());
        if (dxsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + workPaperQueryWayItemEO.getDxsType());
        }
        WorkingPaperQmsTypeEnum qmsTypeEnum = WorkingPaperQmsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getQmsType());
        if (qmsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + workPaperQueryWayItemEO.getQmsType());
        }
        List<WorkingPaperTableHeaderVO> titles = this.getHeaderVO(queryCondition, qmsTypeEnum, dxsTypeEnum);
        List<WorkingPaperTableDataVO> datas = this.getDataVO(queryCondition, qmsTypeEnum, dxsTypeEnum);
        WorkingPaperTableVO workingPaperTableVO = new WorkingPaperTableVO();
        workingPaperTableVO.setTitles(titles);
        workingPaperTableVO.setDatas(datas);
        return workingPaperTableVO;
    }

    @Override
    public ExportExcelSheet getExcelVO(ExportContext context, Workbook workbook, WorkingPaperQueryCondition queryCondition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        WorkingPaperTableVO vo = this.queryAllData(queryCondition);
        LinkedHashMap<String, String> firstTitleMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> secondTitleMap = new LinkedHashMap<String, String>();
        List workingPaperTableHeaderVOs = vo.getTitles();
        for (int i = 0; i < workingPaperTableHeaderVOs.size(); ++i) {
            WorkingPaperTableHeaderVO workingPaperTableHeaderVO = (WorkingPaperTableHeaderVO)workingPaperTableHeaderVOs.get(i);
            List secondHeader = workingPaperTableHeaderVO.getChildren();
            if (CollectionUtils.isEmpty((Collection)secondHeader)) {
                secondTitleMap.put(workingPaperTableHeaderVO.getProp(), workingPaperTableHeaderVO.getLabel());
                firstTitleMap.put(workingPaperTableHeaderVO.getProp(), workingPaperTableHeaderVO.getLabel());
                continue;
            }
            secondHeader.stream().forEach(secondHeaderVO -> {
                secondTitleMap.put(secondHeaderVO.getProp(), secondHeaderVO.getLabel());
                firstTitleMap.put(secondHeaderVO.getProp(), workingPaperTableHeaderVO.getLabel());
            });
        }
        ArrayList columnKeys = new ArrayList(secondTitleMap.keySet());
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] headFirstRowData = new Object[columnKeys.size()];
        Object[] headSecondRowData = new Object[columnKeys.size()];
        for (int i = 0; i < columnKeys.size(); ++i) {
            String key = (String)columnKeys.get(i);
            headSecondRowData[i] = secondTitleMap.get(key);
            headFirstRowData[i] = firstTitleMap.get(key);
        }
        rowDatas.add(headFirstRowData);
        rowDatas.add(headSecondRowData);
        if (!context.isTemplateExportFlag()) {
            List exportDatas = vo.getDatas().stream().map(WorkingPaperTableDataVO::getZbvalueStr).collect(Collectors.toList());
            exportDatas.stream().forEach(exportData -> {
                Object[] rowData = new Object[columnKeys.size()];
                for (int i = 0; i < columnKeys.size(); ++i) {
                    String key = (String)columnKeys.get(i);
                    rowData[i] = exportData.get(key);
                }
                rowDatas.add(rowData);
            });
        }
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.workingpaper.sheet.workingpaper"), Integer.valueOf(2));
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        for (int i = 0; i < columnKeys.size(); ++i) {
            CellStyle cellStyle;
            CellType cellType;
            String key = (String)columnKeys.get(i);
            if (key.startsWith("QMS_") || key.startsWith("DXS_") || key.startsWith("RYTZS_") || key.startsWith("HBS_") || key.startsWith("UN_DXS_")) {
                cellType = CellType.NUMERIC;
                cellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            } else {
                cellType = CellType.STRING;
                cellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
            }
            exportExcelSheet.getContentCellStyleCache().put(i, cellStyle);
            exportExcelSheet.getContentCellTypeCache().put(i, cellType);
        }
        return exportExcelSheet;
    }

    protected String getSystemId(String schemeId, String periodStr) {
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = consolidatedTaskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
        return systemId;
    }

    protected Map<String, ConsolidatedSubjectVO> getSubjectCode2DataMap(WorkingPaperQueryCondition condtion) {
        List<ConsolidatedSubjectVO> subjects = WorkingPaperQueryUtils.getAllSubject(condtion);
        this.findAndMarkLeafNodes(subjects);
        List baseSubjectCodes = condtion.getBaseSubjectCodes();
        Map subjectCode2DataMap = subjects.stream().filter(subjectVO -> {
            if (CollectionUtils.isEmpty((Collection)baseSubjectCodes)) {
                return true;
            }
            boolean contains = baseSubjectCodes.contains(subjectVO.getCode());
            return contains;
        }).collect(Collectors.toMap(ConsolidatedSubjectVO::getCode, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        return subjectCode2DataMap;
    }

    public void findAndMarkLeafNodes(List<ConsolidatedSubjectVO> subjects) {
        HashMap<String, List> childrenMap = new HashMap<String, List>();
        for (ConsolidatedSubjectVO subject : subjects) {
            childrenMap.computeIfAbsent(subject.getParentCode(), k -> new ArrayList()).add(subject);
        }
        ArrayList<ConsolidatedSubjectVO> leafNodes = new ArrayList<ConsolidatedSubjectVO>();
        for (ConsolidatedSubjectVO subject : subjects) {
            if (!childrenMap.containsKey(subject.getCode())) {
                subject.setLeafFlag(Boolean.valueOf(true));
                leafNodes.add(subject);
                continue;
            }
            subject.setLeafFlag(Boolean.valueOf(false));
        }
    }

    protected List<String> listAllOrgByParentIdContainsSelf(WorkingPaperQueryCondition queryCondition, String parentOrgCode) {
        if (StringUtils.isEmpty((String)parentOrgCode)) {
            return Collections.emptyList();
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryCondition.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List list = tool.listAllOrgByParentIdContainsSelf(parentOrgCode);
        ArrayList<String> orgIds = new ArrayList<String>();
        for (GcOrgCacheVO org : list) {
            orgIds.add(org.getId());
        }
        return orgIds;
    }

    protected Map<String, GcOrgCacheVO> getDirectOrgCode2DataMap(WorkingPaperQueryCondition condtion) {
        List<GcOrgCacheVO> directChildOrgs = WorkingPaperQueryUtils.getDirectChilds(condtion);
        Map directChildOrgCode2DataMap = directChildOrgs.stream().collect(Collectors.toMap(GcOrgCacheVO::getCode, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        if (directChildOrgCode2DataMap == null) {
            return Collections.emptyMap();
        }
        return directChildOrgCode2DataMap;
    }

    protected Map<String, GcOrgCacheVO> getDirectOrgCode2DataContainMergeOrgAndDiffOrgMap(WorkingPaperQueryCondition condtion) {
        List<GcOrgCacheVO> directChildOrgs = WorkingPaperQueryUtils.getOrgDirectChildsContainMergeOrgAndDiffOrg(condtion);
        Map directChildOrgCode2DataMap = directChildOrgs.stream().filter(org -> !StringUtils.isEmpty((String)org.getCode())).collect(Collectors.toMap(GcOrgCacheVO::getCode, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        if (directChildOrgCode2DataMap == null) {
            return Collections.emptyMap();
        }
        return directChildOrgCode2DataMap;
    }

    protected Map<String, GcOrgCacheVO> getDirectOrgCode2DataContainSelfMap(WorkingPaperQueryCondition condtion) {
        List<GcOrgCacheVO> directChildOrgs = WorkingPaperQueryUtils.getOrgDirectChildsContainSelf(condtion);
        Map directChildOrgCode2DataMap = directChildOrgs.stream().filter(org -> !StringUtils.isEmpty((String)org.getCode())).collect(Collectors.toMap(GcOrgCacheVO::getCode, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        if (directChildOrgCode2DataMap == null) {
            return Collections.emptyMap();
        }
        return directChildOrgCode2DataMap;
    }

    protected Map<String, String> getLeafYwlxCode2TitleMap() {
        List gcBussinessTypes = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_GCBUSINESSTYPE");
        Set parentIds = gcBussinessTypes.stream().map(gcBussinessType -> {
            String parentid = gcBussinessType.getParentid();
            if (!StringUtils.isEmpty((String)parentid)) {
                return parentid;
            }
            return null;
        }).collect(Collectors.toSet());
        Map ywlxCode2TitleMap = gcBussinessTypes.stream().filter(GcBaseData2 -> !parentIds.contains(GcBaseData2.getCode())).collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle, (v1, v2) -> v1, LinkedHashMap::new));
        ywlxCode2TitleMap.put(OTHER_CODE, GcI18nUtil.getMessage((String)"gc.workingpaper.code.other"));
        return ywlxCode2TitleMap;
    }

    protected Map<String, String> getOffSetElmModeCode2TitleMap() {
        List<OffsetElmModeEnum> offsetElmModeEnums = Arrays.asList(OffsetElmModeEnum.values());
        LinkedHashMap<String, String> offsetElmModeCode2TitleMap = new LinkedHashMap<String, String>();
        offsetElmModeEnums.stream().forEach(offsetElmModeEnum -> offsetElmModeCode2TitleMap.put(String.valueOf(offsetElmModeEnum.getValue()), offsetElmModeEnum.getTitle()));
        return offsetElmModeCode2TitleMap;
    }

    protected Map<String, List<String>> getZbTableName2FieldNamesMap(List<WorkingPaperTableDataVO> workingPaperTableDataVOS) {
        HashMap<String, List<String>> zbTableName2FieldNames = new HashMap<String, List<String>>();
        workingPaperTableDataVOS.stream().forEach(vo -> {
            if (StringUtils.isEmpty((String)vo.getZbtable()) || StringUtils.isEmpty((String)vo.getZbfield())) {
                return;
            }
            List fields = zbTableName2FieldNames.computeIfAbsent(vo.getZbtable(), k -> new ArrayList());
            fields.add(vo.getZbfield());
        });
        return zbTableName2FieldNames;
    }

    protected List<WorkingPaperDxsItemDTO> getWorkingPaperOffsetItemDTOs(WorkingPaperQueryCondition condition) {
        long time1 = System.currentTimeMillis();
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.covertQueryParamsVO(condition);
        List<Map<String, Object>> offsetList = this.sumOffSetItemDTOBySubjectAndOrgAndYwlxAndElmmode(queryParamsVO);
        if (CollectionUtils.isEmpty(offsetList)) {
            return Collections.emptyList();
        }
        ArrayList<WorkingPaperDxsItemDTO> offSetVchrItemDTOS = new ArrayList<WorkingPaperDxsItemDTO>(offsetList.size());
        for (Map<String, Object> dataMap : offsetList) {
            WorkingPaperDxsItemDTO sumOffSetVchrItemDTO = this.buildWorkingPaperOffsetItemDTOByMap(condition, dataMap);
            String orgId = StringUtils.isEmpty((String)sumOffSetVchrItemDTO.getOrgCode()) ? condition.getOrgid() : sumOffSetVchrItemDTO.getOrgCode();
            sumOffSetVchrItemDTO.setOrgCode(orgId);
            offSetVchrItemDTOS.add(sumOffSetVchrItemDTO);
        }
        long time2 = System.currentTimeMillis();
        LOGGER.debug("\u5de5\u4f5c\u5e95\u7a3f\u83b7\u53d6\u62b5\u9500\u8bb0\u5f55\u8017\u65f6\uff1a{}", (Object)(time2 - time1));
        return offSetVchrItemDTOS;
    }

    protected WorkingPaperDxsItemDTO getMergeOffSetVchrItem(ConsolidatedSubjectVO consolidatedSubjectVO, List<WorkingPaperDxsItemDTO> workingPaperDxsItemDTOS) {
        WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = WorkingPaperDxsItemDTO.empty();
        mergeWorkingPaperOffsetItemDTO.setSubjectCode(consolidatedSubjectVO.getCode());
        workingPaperDxsItemDTOS.stream().forEach(offsetItemData -> {
            BigDecimal offSetDebit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetDebit()});
            BigDecimal offSetCredit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetCredit()});
            BigDecimal debit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDebit()});
            BigDecimal credit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getCredit()});
            BigDecimal diffd = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffd(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffd()});
            BigDecimal diffc = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffc(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffc()});
            mergeWorkingPaperOffsetItemDTO.setOffSetDebit(offSetDebit);
            mergeWorkingPaperOffsetItemDTO.setOffSetCredit(offSetCredit);
            mergeWorkingPaperOffsetItemDTO.setCredit(credit);
            mergeWorkingPaperOffsetItemDTO.setDebit(debit);
            mergeWorkingPaperOffsetItemDTO.setDiffd(diffd);
            mergeWorkingPaperOffsetItemDTO.setDiffc(diffc);
        });
        return mergeWorkingPaperOffsetItemDTO;
    }

    protected void mergeOffSetVchrItem(ConsolidatedSubjectVO consolidatedSubjectVO, WorkingPaperDxsItemDTO offsetItemData, WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO) {
        BigDecimal offSetDebit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetDebit()});
        BigDecimal offSetCredit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetCredit()});
        BigDecimal debit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDebit()});
        BigDecimal credit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getCredit()});
        BigDecimal diffd = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffd(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffd()});
        BigDecimal diffc = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffc(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffc()});
        mergeWorkingPaperOffsetItemDTO.setOffSetDebit(offSetDebit);
        mergeWorkingPaperOffsetItemDTO.setOffSetCredit(offSetCredit);
        mergeWorkingPaperOffsetItemDTO.setCredit(credit);
        mergeWorkingPaperOffsetItemDTO.setDebit(debit);
        mergeWorkingPaperOffsetItemDTO.setDiffd(diffd);
        mergeWorkingPaperOffsetItemDTO.setDiffc(diffc);
    }

    protected void mergeOffSetVchrItem(PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO, WorkingPaperDxsItemDTO offsetItemData, WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO) {
        BigDecimal offSetDebit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetDebit()});
        BigDecimal offSetCredit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetCredit()});
        BigDecimal debit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDebit()});
        BigDecimal credit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getCredit()});
        BigDecimal diffd = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffd(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffd()});
        BigDecimal diffc = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffc(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffc()});
        mergeWorkingPaperOffsetItemDTO.setOffSetDebit(offSetDebit);
        mergeWorkingPaperOffsetItemDTO.setOffSetCredit(offSetCredit);
        mergeWorkingPaperOffsetItemDTO.setCredit(credit);
        mergeWorkingPaperOffsetItemDTO.setDebit(debit);
        mergeWorkingPaperOffsetItemDTO.setDiffd(diffd);
        mergeWorkingPaperOffsetItemDTO.setDiffc(diffc);
    }

    protected List<WorkingPaperTableDataVO> rebuildWorkPaperResultVos(WorkingPaperQueryCondition condition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum, Map<String, GcOrgCacheVO> directChildOrgCode2DataMap, List<WorkingPaperTableDataVO> workingPaperTableDataVOS) {
        if (condition.getIsFilterZero().booleanValue()) {
            List<WorkingPaperTableHeaderVO> headerVOs = this.getHeaderVO(condition, qmsTypeEnum, dxsTypeEnum);
            HashSet amtProps = new HashSet();
            headerVOs.stream().forEach(headerVO -> {
                List childHeaderVOs = headerVO.getChildren();
                if (CollectionUtils.isEmpty((Collection)childHeaderVOs)) {
                    if (headerVO.getProp().startsWith("DXS_") || headerVO.getProp().startsWith("RYTZS_") || headerVO.getProp().startsWith("QMS_")) {
                        amtProps.add(headerVO.getProp());
                    }
                } else {
                    childHeaderVOs.stream().forEach(childHeaderVO -> {
                        if (childHeaderVO.getProp().startsWith("DXS_") || childHeaderVO.getProp().startsWith("RYTZS_") || childHeaderVO.getProp().startsWith("QMS_")) {
                            amtProps.add(childHeaderVO.getProp());
                        }
                    });
                }
            });
            workingPaperTableDataVOS = workingPaperTableDataVOS.stream().filter(dataVO -> {
                Map zbvalueStrMap = dataVO.getZbvalueStr();
                AtomicBoolean notAllZeroValue = new AtomicBoolean(false);
                amtProps.forEach(amtProp -> {
                    if (notAllZeroValue.get()) {
                        return;
                    }
                    BigDecimal amtValue = ConverterUtils.getAsBigDecimal(zbvalueStrMap.get(amtProp), (BigDecimal)BigDecimal.ZERO);
                    if (BigDecimal.ZERO.compareTo(amtValue) != 0) {
                        notAllZeroValue.set(true);
                    }
                });
                return notAllZeroValue.get();
            }).collect(Collectors.toList());
        }
        workingPaperTableDataVOS.stream().forEach(dataVO -> {
            if (StringUtils.isEmpty((String)dataVO.getZbfield())) {
                directChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                    dataVO.getZbvalueStr().put("QMS_NOT_CONTAIN_DXS_SHOWVALUE_" + orgCode, "--");
                    dataVO.getZbvalueStr().put("QMS_CONTAIN_DXS_SHOWVALUE_" + orgCode, "--");
                });
            }
        });
        return workingPaperTableDataVOS;
    }

    public List<Map<String, Object>> sumOffSetItemDTOBySubjectAndOrgAndYwlxAndElmmode(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        String selectFields = " record.UNITID,record.SUBJECTCODE,record.SUBJECTORIENT,record.OFFSETSRCTYPE,record.ELMMODE,record.GCBUSINESSTYPECODE,sum(record.OFFSET_DEBIT) as OFFSET_DEBIT_VALUE,sum(record.OFFSET_CREDIT) as OFFSET_CREDIT_VALUE, sum(record.DIFFD) as DIFFD_VALUE, sum(record.DIFFC) as DIFFC_VALUE, count(1) as OFFSETCOUNT ";
        String groupStr = " record.unitid,record.subjectCode,record.SUBJECTORIENT,record.OFFSETSRCTYPE,record.ELMMODE,record.GCBUSINESSTYPECODE \n";
        return this.offsetCoreService.sumOffsetValueGroupBy(queryParamsDTO, selectFields, groupStr);
    }
}

