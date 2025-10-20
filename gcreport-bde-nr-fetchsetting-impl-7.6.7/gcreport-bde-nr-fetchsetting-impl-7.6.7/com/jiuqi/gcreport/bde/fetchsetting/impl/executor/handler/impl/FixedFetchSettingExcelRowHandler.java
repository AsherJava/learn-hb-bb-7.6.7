/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.TreeBasedTable
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl;

import com.google.common.collect.TreeBasedTable;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FailedSettingLog;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.RegionFetchSetting;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.AbstractExportInnerRowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataLink;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FixedFetchSettingExcelRowHandler
extends AbstractExportInnerRowHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(FixedFetchSettingExcelRowHandler.class);

    @Override
    public RegionFetchSetting handleImportData(ExcelSettingDTO excelSettingDTO, FetchSettingExcelContext fetchSettingExcelContext) {
        Map<String, ImpExpDataLink> dataLinkDefineMap = this.getDataLinkDefineCodeMap(fetchSettingExcelContext.getBizType(), excelSettingDTO.getFormId(), excelSettingDTO.getRegionId());
        Map<String, ImpExpDataLink> dataLinkDefineNameMap = this.getDataLinkDefineNameMap(fetchSettingExcelContext.getBizType(), excelSettingDTO.getFormId(), excelSettingDTO.getRegionId());
        HashMap<String, List<FixedAdaptSettingExcelDTO>> fixedAdaptSettingMap = new HashMap<String, List<FixedAdaptSettingExcelDTO>>();
        HashMap<String, String> dataLinkKeyToFieldDefineCodeMap = new HashMap<String, String>();
        HashSet<String> failedDefineCodeSet = new HashSet<String>();
        TreeBasedTable adaptFormaulIndexTable = TreeBasedTable.create();
        TreeBasedTable logicFormulaStrTable = TreeBasedTable.create();
        for (ExcelRowFetchSettingVO excelRowFetchSettingVO : excelSettingDTO.getExportFetchSourceRowImpSettingVOS()) {
            ExcelRowFetchSettingVO fetchSourceRowSettingVO;
            FixedAdaptSettingExcelDTO fixedAdaptSettingDTO;
            ImpExpDataLink dataLinkDefine = this.impExpHandleAdaptorGather.getHandleAdaptor(fetchSettingExcelContext.getBizType().getCode()).findDataLinkByMap(dataLinkDefineMap, dataLinkDefineNameMap, excelRowFetchSettingVO.getFieldDefineCode());
            if (dataLinkDefine == null) {
                excelRowFetchSettingVO.setErrorLog("\u672a\u627e\u5230\u5bf9\u5e94\u6307\u6807");
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                continue;
            }
            if (failedDefineCodeSet.contains(dataLinkDefine.getKey())) continue;
            String logicFormual = (String)logicFormulaStrTable.get((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula());
            if (!StringUtils.isEmpty((String)logicFormual) && !logicFormual.equals(excelRowFetchSettingVO.getLogicFormula())) {
                failedDefineCodeSet.add(dataLinkDefine.getKey());
                excelRowFetchSettingVO.setErrorLog("\u5bfc\u5165\u6587\u4ef6\u4e2d\uff0c\u540c\u4e00\u6307\u6807\u7684\u540c\u4e00\u4e2a\u9002\u5e94\u6761\u4ef6\u4e0b\u6240\u6709\u89c4\u5219\u5747\u9700\u8bbe\u7f6e\u76f8\u540c\u7684\u903b\u8f91\u8868\u8fbe\u5f0f\uff0c\u8bf7\u68c0\u67e5\u3002");
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                continue;
            }
            List fixedAdaptSettingVOS = fixedAdaptSettingMap.computeIfAbsent(dataLinkDefine.getKey(), k -> new ArrayList());
            dataLinkKeyToFieldDefineCodeMap.put(dataLinkDefine.getKey(), StringUtils.isEmpty((String)excelRowFetchSettingVO.getFieldDefineCode()) ? excelRowFetchSettingVO.getFieldDefineTitle() : excelRowFetchSettingVO.getFieldDefineCode());
            Integer index = (Integer)adaptFormaulIndexTable.get((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula());
            if (index == null) {
                fixedAdaptSettingDTO = new FixedAdaptSettingExcelDTO(excelRowFetchSettingVO.getSheetName());
                fixedAdaptSettingDTO.setAdaptFormula(excelRowFetchSettingVO.getAdaptFormula());
                fixedAdaptSettingDTO.setLogicFormula(excelRowFetchSettingVO.getLogicFormula());
                fixedAdaptSettingDTO.setWildcardFormula(excelRowFetchSettingVO.getWildcardFormula());
                fixedAdaptSettingDTO.setDescription(excelRowFetchSettingVO.getDescription());
                fixedAdaptSettingDTO.setBizModelFormula(new LinkedHashMap<String, List<ExcelRowFetchSettingVO>>());
                fixedAdaptSettingVOS.add(fixedAdaptSettingDTO);
                adaptFormaulIndexTable.put((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula(), (Object)adaptFormaulIndexTable.row((Object)dataLinkDefine.getKey()).size());
                logicFormulaStrTable.put((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula(), (Object)(StringUtils.isEmpty((String)excelRowFetchSettingVO.getLogicFormula()) ? "" : excelRowFetchSettingVO.getLogicFormula()));
            } else {
                fixedAdaptSettingDTO = (FixedAdaptSettingExcelDTO)fixedAdaptSettingVOS.get(index);
                if (StringUtils.isEmpty((String)fixedAdaptSettingDTO.getDescription()) && !StringUtils.isEmpty((String)excelRowFetchSettingVO.getDescription())) {
                    fixedAdaptSettingDTO.setDescription(excelRowFetchSettingVO.getDescription());
                }
            }
            Map<String, List<ExcelRowFetchSettingVO>> fixedAdaptSettingVOBizModelFormula = fixedAdaptSettingDTO.getBizModelFormula();
            if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFetchSourceCode()) && !StringUtils.isEmpty((String)excelRowFetchSettingVO.getLogicFormula())) continue;
            try {
                fetchSourceRowSettingVO = this.convertToFetchSetting(excelRowFetchSettingVO, fetchSettingExcelContext);
            }
            catch (Exception e) {
                LOGGER.error("\u89e3\u6790\u53d6\u6570\u8bbe\u7f6e\u6570\u636e\u5f02\u5e38", e);
                excelRowFetchSettingVO.setErrorLog("\u89e3\u6790\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e");
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                failedDefineCodeSet.add(dataLinkDefine.getKey());
                continue;
            }
            fetchSourceRowSettingVO.setFetchSourceCode(excelRowFetchSettingVO.getFetchSourceCode());
            List fetchSourceRowSettingVOS = fixedAdaptSettingVOBizModelFormula.computeIfAbsent(fetchSourceRowSettingVO.getFetchSourceCode(), K -> new ArrayList());
            fetchSourceRowSettingVOS.add(fetchSourceRowSettingVO);
        }
        failedDefineCodeSet.forEach(fixedAdaptSettingMap::remove);
        RegionFetchSetting regionFetchSetting = new RegionFetchSetting();
        regionFetchSetting.setFetchSchemeId(fetchSettingExcelContext.getFetchSchemeId());
        regionFetchSetting.setFormSchemeId(fetchSettingExcelContext.getFormSchemeId());
        regionFetchSetting.setFormId(excelSettingDTO.getFormId());
        regionFetchSetting.setRegionId(excelSettingDTO.getRegionId());
        regionFetchSetting.setFixeAdaptSettingDTOMap(fixedAdaptSettingMap);
        regionFetchSetting.setDataLinkTitleMap(dataLinkKeyToFieldDefineCodeMap);
        return regionFetchSetting;
    }

    @Override
    public List<Object[]> handleExportData(FetchSettingExportContext fetchSettingExcelContext, FetchSettingCond fetchSettingCond) {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List<ExcelRowFetchSettingVO> rowImpSettingVOS = this.getExcelRowFetchSettingVOS(fetchSettingExcelContext, fetchSettingCond);
        if (CollectionUtils.isEmpty(rowImpSettingVOS)) {
            return null;
        }
        rowDatas.addAll(ImportInnerColumnUtil.getTitle(fetchSettingExcelContext.getFixColumns()));
        rowDatas.addAll(ImportInnerColumnUtil.getSettings(fetchSettingExcelContext.getFixColumns(), rowImpSettingVOS));
        rowDatas.add(new Object[0]);
        return rowDatas;
    }

    private List<ExcelRowFetchSettingVO> getExcelRowFetchSettingVOS(FetchSettingExportContext fetchSettingExcelContext, FetchSettingCond fetchSettingCond) {
        if (fetchSettingExcelContext.getTemplateExportFlag().booleanValue()) {
            return this.exportTemplateToVos(fetchSettingExcelContext.getBizType(), fetchSettingCond);
        }
        ArrayList<ExcelRowFetchSettingVO> rowImpSettingVOS = new ArrayList<ExcelRowFetchSettingVO>(128);
        Map<String, List<FetchSettingVO>> fetchSettingDesGroupByDataLinkId = this.fetchSettingDesService.getFetchSettingDesGroupByDataLinkId(fetchSettingCond);
        List<ImpExpDataLink> dataLinks = this.getDataLinkDefines(fetchSettingExcelContext.getBizType(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId());
        for (ImpExpDataLink dataLink : dataLinks) {
            List<FetchSettingVO> fetchSettingVOS = fetchSettingDesGroupByDataLinkId.get(dataLink.getKey());
            if (CollectionUtils.isEmpty(fetchSettingVOS)) continue;
            for (FetchSettingVO fetchSettingVO : fetchSettingVOS) {
                rowImpSettingVOS.addAll(this.convert2RowFetchSetting(fetchSettingVO, fetchSettingExcelContext, null));
            }
        }
        return rowImpSettingVOS;
    }
}

