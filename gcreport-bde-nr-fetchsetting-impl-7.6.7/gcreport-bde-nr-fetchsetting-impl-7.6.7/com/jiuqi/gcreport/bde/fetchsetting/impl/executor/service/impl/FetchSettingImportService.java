/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO
 *  com.jiuqi.bde.bizmodel.client.vo.FetchSettingExtInfoVo
 *  com.jiuqi.bde.bizmodel.client.vo.QueryFieldVO
 *  com.jiuqi.bde.common.constant.DataRegionTypeEnum
 *  com.jiuqi.bde.common.constant.RegionTypeEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO;
import com.jiuqi.bde.bizmodel.client.vo.FetchSettingExtInfoVo;
import com.jiuqi.bde.bizmodel.client.vo.QueryFieldVO;
import com.jiuqi.bde.common.constant.DataRegionTypeEnum;
import com.jiuqi.bde.common.constant.RegionTypeEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelRegionInfo;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FailedSettingLog;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.RegionFetchSetting;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.AdaptSettingChecker;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.BdeLogicChecker;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.BizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.BizModelHandlerGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.FloatSettingTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.gather.FloatConfigExcelGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.FixedFetchSettingExcelRowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.FloatFetchSettingExcelRowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFormDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.IFetchSettingImportService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BdeLogicFormulaUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingLogHelperUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSettingImportService
implements IFetchSettingImportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchSettingImportService.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataRegionService iRuntimeDataRegionService;
    @Autowired
    private FetchFloatSettingDesService fetchFloatSettingDesService;
    @Autowired
    private FixedFetchSettingExcelRowHandler fixedFetchSettingExcelRowHandler;
    @Autowired
    private FloatFetchSettingExcelRowHandler floatFetchSettingExcelRowHandler;
    @Autowired
    private BdeLogicChecker bdeLogicChecker;
    @Autowired
    private BizModelHandlerGather bizModelHandlerGather;
    @Autowired
    private AdaptSettingChecker adaptSettingChecker;
    @Autowired
    private FetchSettingDesService fetchSettingDesService;
    @Autowired
    private FloatConfigExcelGather floatConfigExcelGather;
    @Autowired
    private BizModelClient bizModelClient;

    @Override
    public String getBizType() {
        return BizTypeEnum.NR.getCode();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String importSheet(FetchSettingExcelContext fetchSettingExcelContext, List<ImportExcelSheet> excelSheets) {
        Map formCodeTitleAndKeyMapping = fetchSettingExcelContext.getFormDefines().stream().collect(Collectors.toMap(FetchSettingNrUtil::getSheetTitleByForm, Function.identity(), (K1, K2) -> K1));
        StringBuilder errorLog = new StringBuilder();
        ArrayList<String> importFormIds = new ArrayList<String>();
        for (ImportExcelSheet excelSheet : excelSheets) {
            ArrayList<RegionFetchSetting> regionFetchSettingList = new ArrayList<RegionFetchSetting>();
            if ("\u586b\u62a5\u8bf4\u660e".equals(excelSheet.getSheetName())) continue;
            ImpExpFormDefine formDefine = (ImpExpFormDefine)formCodeTitleAndKeyMapping.get(excelSheet.getSheetName());
            if (formDefine == null) {
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelSheet.getSheetName(), String.format("\u6839\u636e\u3010%1$s\u3011\u672a\u627e\u5230\u5bf9\u5e94\u8868\u5355\n", excelSheet.getSheetName())));
                continue;
            }
            List<Integer> floatHeadIndexList = FetchSettingNrUtil.getFloatHeadIndexList(excelSheet.getExcelSheetDatas());
            List<ExcelRegionInfo> excelRegionInfos = this.buildRegionInfos(floatHeadIndexList);
            List dataRegionDefines = this.iRuntimeDataRegionService.getDataRegionsInForm(formDefine.getKey());
            for (ExcelRegionInfo excelRegionInfo : excelRegionInfos) {
                if (RegionTypeEnum.FIXED.equals((Object)excelRegionInfo.getRegionTypeEnum())) {
                    DataRegionDefine dataRegionDefine = dataRegionDefines.stream().filter(item -> DataRegionKind.DATA_REGION_SIMPLE.equals((Object)item.getRegionKind())).findFirst().get();
                    regionFetchSettingList.add(this.fixedImport(excelSheet, fetchSettingExcelContext, excelRegionInfo, dataRegionDefine));
                    continue;
                }
                try {
                    regionFetchSettingList.add(this.floatImport(excelSheet, fetchSettingExcelContext, excelRegionInfo, formDefine.getKey()));
                }
                catch (Exception e) {
                    errorLog.append(String.format("\u3010%3$s\u3011\u8868%1$s\u5bfc\u5165\u53d1\u751f\u5f02\u5e38\uff0c\u8df3\u8fc7\u5bfc\u5165\uff0c\u9519\u8bef\u539f\u56e0\uff1a%2$s\n", excelRegionInfo.getPositionStr(), e.getMessage(), excelSheet.getSheetName()));
                    LOGGER.error("\u533a\u57df{}\u5bfc\u5165\u8df3\u8fc7", (Object)JsonUtils.writeValueAsString((Object)excelRegionInfo), (Object)e);
                }
            }
            List<RegionFetchSetting> newRegionFetchSetting = this.doCheck(regionFetchSettingList, fetchSettingExcelContext);
            List<FetchSettingDesEO> fetchSettingDesEOS = this.buildFetchSettingDesEOBaseInfo(newRegionFetchSetting);
            this.fetchSettingDesService.saveBatchFetchFixedSettingDes(fetchSettingDesEOS);
            FetchSettingSaveDataVO fetchSettingSaveDataVO = this.buildFloatSaveData(newRegionFetchSetting, FetchSettingNrUtil.getFetchSettingCond(fetchSettingExcelContext, formDefine.getKey(), null));
            String floatMassageInfo = this.fetchFloatSettingDesService.saveFetchFloatSettingDataHandle(fetchSettingSaveDataVO);
            if (!StringUtils.isEmpty((String)floatMassageInfo)) {
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelSheet.getSheetName(), floatMassageInfo));
            }
            importFormIds.add(formDefine.getKey());
        }
        FetchSettingLogHelperUtil.logFetchSettingImport(fetchSettingExcelContext.getFormSchemeId(), fetchSettingExcelContext.getFetchSchemeId(), importFormIds);
        errorLog.append(fetchSettingExcelContext.getErrorLog());
        return errorLog.toString();
    }

    private List<ExcelRegionInfo> buildRegionInfos(List<Integer> floatHeadIndexList) {
        ArrayList<ExcelRegionInfo> excelRegionInfos = new ArrayList<ExcelRegionInfo>();
        if (floatHeadIndexList.get(0) > 0) {
            excelRegionInfos.add(new ExcelRegionInfo(RegionTypeEnum.FIXED, 0, floatHeadIndexList.get(0)));
        }
        if (floatHeadIndexList.size() < 2) {
            return excelRegionInfos;
        }
        for (int i = 1; i < floatHeadIndexList.size(); ++i) {
            excelRegionInfos.add(new ExcelRegionInfo(RegionTypeEnum.FLOAT, floatHeadIndexList.get(i - 1), floatHeadIndexList.get(i)));
        }
        return excelRegionInfos;
    }

    private List<FetchSettingDesEO> buildFetchSettingDesEOBaseInfo(List<RegionFetchSetting> regionFetchSettingList) {
        ArrayList<FetchSettingDesEO> fetchSettingDesData = new ArrayList<FetchSettingDesEO>();
        ArrayList<QueryFieldVO> queryFieldVOS = new ArrayList<QueryFieldVO>();
        ExtInfoParamVO extInfoParamDTO = new ExtInfoParamVO();
        LinkedHashMap fixedAdaptSettingVOS = new LinkedHashMap();
        LinkedHashMap<String, Map> regionIdLinkIdSettingMap = new LinkedHashMap<String, Map>();
        for (RegionFetchSetting regionFetchSetting : regionFetchSettingList) {
            Map fixedAdaptSetting = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString(regionFetchSetting.getFixeAdaptSettingDTOMap()), (TypeReference)new TypeReference<Map<String, List<FixedAdaptSettingVO>>>(){});
            fixedAdaptSettingVOS.putAll(fixedAdaptSetting);
            regionIdLinkIdSettingMap.put(regionFetchSetting.getRegionId(), fixedAdaptSetting);
            if (regionFetchSetting.getFloatRegionConfigVO() == null) continue;
            for (FloatQueryFieldVO floatQueryFieldVO : regionFetchSetting.getFloatRegionConfigVO().getQueryConfigInfo().getQueryFields()) {
                QueryFieldVO queryFieldVO = new QueryFieldVO();
                BeanUtils.copyProperties(floatQueryFieldVO, queryFieldVO);
                queryFieldVOS.add(queryFieldVO);
            }
            extInfoParamDTO.setDimList(queryFieldVOS);
        }
        extInfoParamDTO.setDataLinkFetchSetting(fixedAdaptSettingVOS);
        Map queryExtInfo = (Map)this.bizModelClient.queryExtInfo(extInfoParamDTO).getData();
        LinkedHashMap<String, FetchSettingExtInfoVo> fetchSettingExtInfoVoMap = new LinkedHashMap<String, FetchSettingExtInfoVo>();
        for (Map.Entry excelInfoResultEntry : queryExtInfo.entrySet()) {
            for (ExtInfoResultVO extInfoResultDTO : (List)excelInfoResultEntry.getValue()) {
                fetchSettingExtInfoVoMap.putAll(extInfoResultDTO.getBizModelSettingExtInfoMap());
            }
        }
        for (RegionFetchSetting regionFetchSetting : regionFetchSettingList) {
            int sortOrder = 0;
            Map fixedAdaptSettingMap = (Map)regionIdLinkIdSettingMap.get(regionFetchSetting.getRegionId());
            for (Map.Entry fixedAdaptEntry : fixedAdaptSettingMap.entrySet()) {
                DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine((String)fixedAdaptEntry.getKey());
                FetchSettingDesEO fetchSettingDes = new FetchSettingDesEO();
                if (regionFetchSetting.getFloatRegionConfigVO() == null) {
                    fetchSettingDes.setRegionType(DataRegionTypeEnum.FIXED.getCode());
                } else {
                    fetchSettingDes.setRegionType(DataRegionTypeEnum.FLOAT.getCode());
                }
                fetchSettingDes.setSortOrder(Integer.valueOf(++sortOrder));
                fetchSettingDes.setFetchSchemeId(regionFetchSetting.getFetchSchemeId());
                fetchSettingDes.setFormId(regionFetchSetting.getFormId());
                fetchSettingDes.setFormSchemeId(regionFetchSetting.getFormSchemeId());
                fetchSettingDes.setId(UUIDUtils.newHalfGUIDStr());
                fetchSettingDes.setDataLinkId(dataLinkDefine.getKey());
                fetchSettingDes.setFieldDefineId(dataLinkDefine.getLinkExpression());
                fetchSettingDes.setRegionId(dataLinkDefine.getRegionKey());
                List list = (List)fixedAdaptEntry.getValue();
                for (int i = 0; i < list.size(); ++i) {
                    FixedAdaptSettingVO fixedAdaptSetting = (FixedAdaptSettingVO)list.get(i);
                    String memo = this.getOptimizeRuleGroup(fixedAdaptSetting.getBizModelFormula(), fetchSettingExtInfoVoMap);
                    String logicFormulaMemo = ((ExtInfoResultVO)((List)queryExtInfo.get(fixedAdaptEntry.getKey())).get(i)).getLogicFormulaMemo();
                    if (!StringUtils.isEmpty((String)logicFormulaMemo) && !StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula())) {
                        fixedAdaptSetting.setMemo(JsonUtils.writeValueAsString((Object)logicFormulaMemo));
                        continue;
                    }
                    if (!StringUtils.isEmpty((String)logicFormulaMemo)) {
                        fixedAdaptSetting.setMemo(logicFormulaMemo);
                        continue;
                    }
                    if (!StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula())) {
                        LinkedHashMap<String, ArrayList<String>> adaptMemoMap = new LinkedHashMap<String, ArrayList<String>>();
                        adaptMemoMap.put(fixedAdaptSetting.getAdaptFormula(), new ArrayList<String>(Collections.singleton(memo)));
                        fixedAdaptSetting.setMemo(JsonUtils.writeValueAsString(adaptMemoMap));
                        continue;
                    }
                    fixedAdaptSetting.setMemo(memo);
                }
                fetchSettingDes.setFixedSettingData(JsonUtils.writeValueAsString((Object)list));
                fetchSettingDesData.add(fetchSettingDes);
            }
        }
        return fetchSettingDesData;
    }

    private String getOptimizeRuleGroup(Map<String, List<FixedFetchSourceRowSettingVO>> FixedFetchSourceRowSettingsMap, Map<String, FetchSettingExtInfoVo> fetchSettingExtInfoVoMap) {
        StringBuilder memo = new StringBuilder();
        for (Map.Entry<String, List<FixedFetchSourceRowSettingVO>> fixedFetchSourceEntry : FixedFetchSourceRowSettingsMap.entrySet()) {
            for (FixedFetchSourceRowSettingVO fixedFetchSourceRowSettingVO : fixedFetchSourceEntry.getValue()) {
                FetchSettingExtInfoVo settingExtInfoVo = fetchSettingExtInfoVoMap.get(fixedFetchSourceRowSettingVO.getId());
                fixedFetchSourceRowSettingVO.setOptimizeRuleGroup(settingExtInfoVo.getOptimizeRuleGroup());
                memo.append(settingExtInfoVo.getMemo()).append(";");
            }
        }
        return StringUtils.isEmpty((String)memo.toString()) ? memo.toString() : memo.substring(0, memo.length() - 1);
    }

    private List<RegionFetchSetting> doCheck(List<RegionFetchSetting> regionFetchSettingList, FetchSettingExcelContext fetchSettingExcelContext) {
        ArrayList<RegionFetchSetting> newRegionFetchSettings = new ArrayList<RegionFetchSetting>();
        for (RegionFetchSetting regionFetchSetting : regionFetchSettingList) {
            RegionFetchSetting newRegionFetchSetting = this.cloneObj(regionFetchSetting, RegionFetchSetting.class);
            LinkedHashMap<String, List<FixedAdaptSettingExcelDTO>> fixAdaptSettingDTOs = new LinkedHashMap<String, List<FixedAdaptSettingExcelDTO>>();
            newRegionFetchSetting.setFixeAdaptSettingDTOMap(fixAdaptSettingDTOs);
            for (Map.Entry<String, List<FixedAdaptSettingExcelDTO>> fixedAdaptEntry : regionFetchSetting.getFixeAdaptSettingDTOMap().entrySet()) {
                if (!this.adaptSettingChecker.doCheck(fixedAdaptEntry.getValue(), fetchSettingExcelContext)) {
                    fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(regionFetchSetting.getSheetName(), String.format("\u3010%1$s\u3011\u4e2d\u6307\u6807\u3010%2$s\u3011\u5b58\u5728\u914d\u7f6e\u9002\u5e94\u6761\u4ef6\u9519\u8bef\u7684\u89c4\u5219\uff0c\u82e5\u914d\u7f6e\u903b\u8f91\u8868\u8fbe\u5f0f\u5219\u8be5\u6307\u6807\u5bf9\u5e94\u7684\u5168\u90e8\u89c4\u5219\u5747\u9700\u914d\u7f6e\u9002\u5e94\u6761\u4ef6\u3002\u8bf7\u68c0\u67e5\uff01\n", regionFetchSetting.getSheetName(), regionFetchSetting.getDataLinkTitleMap().get(fixedAdaptEntry.getKey()))));
                    continue;
                }
                List<FixedAdaptSettingExcelDTO> fixedAdaptSettingDTOS = this.checkFixedAdaptSettingDTOs(regionFetchSetting.getDataLinkTitleMap().get(fixedAdaptEntry.getKey()), fixedAdaptEntry.getValue(), fetchSettingExcelContext, regionFetchSetting.getSheetName());
                if (CollectionUtils.isEmpty(fixedAdaptSettingDTOS)) continue;
                fixAdaptSettingDTOs.put(fixedAdaptEntry.getKey(), fixedAdaptSettingDTOS);
            }
            newRegionFetchSettings.add(newRegionFetchSetting);
        }
        return newRegionFetchSettings;
    }

    private List<FixedAdaptSettingExcelDTO> checkFixedAdaptSettingDTOs(String fieldDefinecCde, List<FixedAdaptSettingExcelDTO> fixedAdaptSettingDTOS, FetchSettingExcelContext fetchSettingExcelContext, String sheetName) {
        ArrayList<FixedAdaptSettingExcelDTO> newFixedAdaptSettingDTOS = new ArrayList<FixedAdaptSettingExcelDTO>();
        for (FixedAdaptSettingExcelDTO fixedAdaptSettingDTO : fixedAdaptSettingDTOS) {
            FixedAdaptSettingExcelDTO newFixedAdaptSettingDTO = this.cloneObj(fixedAdaptSettingDTO, FixedAdaptSettingExcelDTO.class);
            if (this.adaptSettingChecker.doCheck(fixedAdaptSettingDTO, fetchSettingExcelContext)) {
                if (!this.bdeLogicChecker.doCheck(fixedAdaptSettingDTO, fetchSettingExcelContext)) {
                    fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(sheetName, String.format("\u6307\u6807\u3010%1$s\u3011\u903b\u8f91\u8868\u8fbe\u5f0f\u914d\u7f6e\u4e0d\u5408\u7406\n", fieldDefinecCde)));
                    return null;
                }
                if (this.checkAdaptSetting(fetchSettingExcelContext, fixedAdaptSettingDTO, newFixedAdaptSettingDTOS, newFixedAdaptSettingDTO)) continue;
                return null;
            }
            fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(sheetName, String.format("\u6307\u6807\u3010%1$s\u3011\u7684\u9002\u5e94\u6761\u4ef6\u914d\u7f6e\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\uff01\n", fieldDefinecCde)));
            return null;
        }
        return newFixedAdaptSettingDTOS;
    }

    private boolean checkAdaptSetting(FetchSettingExcelContext fetchSettingExcelContext, FixedAdaptSettingExcelDTO fixedAdaptSettingDTO, List<FixedAdaptSettingExcelDTO> newFixedAdaptSettingDTOS, FixedAdaptSettingExcelDTO newFixedAdaptSettingDTO) {
        newFixedAdaptSettingDTOS.add(fixedAdaptSettingDTO);
        LinkedHashMap<String, List<ExcelRowFetchSettingVO>> excelFetchSourceRowImpSettingVOMap = new LinkedHashMap<String, List<ExcelRowFetchSettingVO>>();
        newFixedAdaptSettingDTO.setBizModelFormula(excelFetchSourceRowImpSettingVOMap);
        for (Map.Entry<String, List<ExcelRowFetchSettingVO>> excelRowFetchEntry : fixedAdaptSettingDTO.getBizModelFormula().entrySet()) {
            ArrayList<ExcelRowFetchSettingVO> excelRowFetchSettingVOS = new ArrayList<ExcelRowFetchSettingVO>();
            for (ExcelRowFetchSettingVO excelRowFetchSettingVO : excelRowFetchEntry.getValue()) {
                BizModelDTO bizModelDTO;
                BizModelHandler bizModelHandler = this.bizModelHandlerGather.getBizCheckerByBizModel(excelRowFetchSettingVO.getBizModelCode());
                if (bizModelHandler.doCheck(excelRowFetchSettingVO, bizModelDTO = fetchSettingExcelContext.getBizModelByCode(excelRowFetchEntry.getKey()))) {
                    excelRowFetchSettingVOS.add(excelRowFetchSettingVO);
                    continue;
                }
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                return false;
            }
            if (CollectionUtils.isEmpty(excelRowFetchSettingVOS)) continue;
            excelFetchSourceRowImpSettingVOMap.put(excelRowFetchEntry.getKey(), excelRowFetchSettingVOS);
        }
        return true;
    }

    protected <T> T cloneObj(Object source, Class<T> clazz) {
        return (T)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)source), clazz);
    }

    private RegionFetchSetting floatImport(ImportExcelSheet excelSheet, FetchSettingExcelContext fetchSettingExcelContext, ExcelRegionInfo excelRegionInfo, String formKey) {
        List<ImpExpInnerColumnHandler> floatColumnHandler = ImportInnerColumnUtil.getExportInnerColumnHandlersByRow(fetchSettingExcelContext.getFloatColumns(), (Object[])excelSheet.getExcelSheetDatas().get(excelRegionInfo.getStartIndex() + 2), (Object[])excelSheet.getExcelSheetDatas().get(excelRegionInfo.getStartIndex() + 3));
        FetchSettingCond fetchSettingCond = FetchSettingNrUtil.getFetchSettingCond(fetchSettingExcelContext, formKey, null);
        ArrayList<ExcelRowFetchSettingVO> floatExcelRowFetchSettingVOS = new ArrayList<ExcelRowFetchSettingVO>();
        HashSet<String> fieldDefineCodeSet = new HashSet<String>();
        for (int index = excelRegionInfo.getStartIndex() + FetchSettingNrUtil.FLOAT_HEAD_SIZE; index < excelRegionInfo.getEndIndex(); ++index) {
            ExcelRowFetchSettingVO excelRowFetchSettingVO = ImportInnerColumnUtil.getImportFetchSourceRowImpSetting(floatColumnHandler, (Object[])excelSheet.getExcelSheetDatas().get(index));
            excelRowFetchSettingVO.setRowNum(index + 1);
            excelRowFetchSettingVO.setSheetName(excelSheet.getSheetName());
            if (fieldDefineCodeSet.contains(excelRowFetchSettingVO.getFieldDefineCode())) continue;
            if (this.doBaseCheck(excelRowFetchSettingVO, fetchSettingExcelContext)) {
                floatExcelRowFetchSettingVOS.add(excelRowFetchSettingVO);
                continue;
            }
            if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getErrorLog())) continue;
            fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
            fieldDefineCodeSet.add(excelRowFetchSettingVO.getFieldDefineCode());
        }
        List<Object[]> floatConfigDatas = excelSheet.getExcelSheetDatas().subList(excelRegionInfo.getStartIndex(), excelRegionInfo.getStartIndex() + 4);
        FloatRegionConfigData floatRegionConfigData = this.getFloatConfigData(floatConfigDatas, fetchSettingCond);
        FloatRegionConfigVO newFloatRegionConfigVO = this.getQueryFieldByConfig(floatRegionConfigData, fetchSettingExcelContext);
        ExcelSettingDTO excelSettingDTO = new ExcelSettingDTO();
        excelSettingDTO.setFloatRegionConfigVO(newFloatRegionConfigVO);
        excelSettingDTO.setRegionId(floatRegionConfigData.getRegionId());
        excelSettingDTO.setFormId(formKey);
        excelSettingDTO.setExportFetchSourceRowImpSettingVOS(floatExcelRowFetchSettingVOS);
        RegionFetchSetting regionFetchSetting = this.floatFetchSettingExcelRowHandler.handleImportData(excelSettingDTO, fetchSettingExcelContext);
        regionFetchSetting.setSheetName(excelSheet.getSheetName());
        BdeLogicFormulaUtils.loadLogicFormula(regionFetchSetting, fetchSettingExcelContext);
        return regionFetchSetting;
    }

    private RegionFetchSetting fixedImport(ImportExcelSheet excelSheet, FetchSettingExcelContext fetchSettingExcelContext, ExcelRegionInfo excelRegionInfo, DataRegionDefine dataRegionDefine) {
        List<ImpExpInnerColumnHandler> fixColumnHandlers = ImportInnerColumnUtil.getExportInnerColumnHandlersByRow(fetchSettingExcelContext.getFixColumns(), (Object[])excelSheet.getExcelSheetDatas().get(0), (Object[])excelSheet.getExcelSheetDatas().get(1));
        ArrayList<ExcelRowFetchSettingVO> excelRowFetchSettingVOS = new ArrayList<ExcelRowFetchSettingVO>();
        HashSet<String> fieldDefineCode = new HashSet<String>();
        for (int i = excelRegionInfo.getStartIndex() + 2; i < excelRegionInfo.getEndIndex(); ++i) {
            ExcelRowFetchSettingVO excelRowFetchSettingVO = ImportInnerColumnUtil.getImportFetchSourceRowImpSetting(fixColumnHandlers, (Object[])excelSheet.getExcelSheetDatas().get(i));
            excelRowFetchSettingVO.setRowNum(i + 1);
            excelRowFetchSettingVO.setSheetName(excelSheet.getSheetName());
            if (fieldDefineCode.contains(excelRowFetchSettingVO.getFieldDefineCode())) continue;
            if (this.doBaseCheck(excelRowFetchSettingVO, fetchSettingExcelContext)) {
                excelRowFetchSettingVOS.add(excelRowFetchSettingVO);
                continue;
            }
            if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getErrorLog())) continue;
            fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
            fieldDefineCode.add(excelRowFetchSettingVO.getFieldDefineCode());
        }
        ExcelSettingDTO excelSettingDTO = new ExcelSettingDTO();
        excelSettingDTO.setRegionId(dataRegionDefine.getKey());
        excelSettingDTO.setFormId(dataRegionDefine.getFormKey());
        excelSettingDTO.setExportFetchSourceRowImpSettingVOS(excelRowFetchSettingVOS);
        RegionFetchSetting regionFetchSetting = this.fixedFetchSettingExcelRowHandler.handleImportData(excelSettingDTO, fetchSettingExcelContext);
        regionFetchSetting.setSheetName(excelSheet.getSheetName());
        BdeLogicFormulaUtils.loadLogicFormula(regionFetchSetting, fetchSettingExcelContext);
        return regionFetchSetting;
    }

    private FetchSettingSaveDataVO buildFloatSaveData(List<RegionFetchSetting> regionFetchSettingList, FetchSettingCond fetchSettingCond) {
        FetchSettingSaveDataVO fetchSettingSaveDataVO = new FetchSettingSaveDataVO();
        HashMap<String, FloatRegionConfigVO> fetchFloatSettingDatas = new HashMap<String, FloatRegionConfigVO>();
        for (RegionFetchSetting regionFetchSetting : regionFetchSettingList) {
            if (regionFetchSetting.getFloatRegionConfigVO() == null) continue;
            fetchFloatSettingDatas.put(regionFetchSetting.getRegionId(), regionFetchSetting.getFloatRegionConfigVO());
        }
        fetchSettingSaveDataVO.setFetchFloatSettingDatas(fetchFloatSettingDatas);
        fetchSettingSaveDataVO.setFixedSettingDatas(new LinkedHashMap());
        BeanUtils.copyProperties(fetchSettingCond, fetchSettingSaveDataVO);
        return fetchSettingSaveDataVO;
    }

    private FloatRegionConfigData getFloatConfigData(List<Object[]> floatConfigData, FetchSettingCond fetchSettingCond) {
        FloatRegionConfigData floatRegionConfigData = new FloatRegionConfigData(fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getFormSchemeId());
        floatRegionConfigData.setRegionId(FetchSettingNrUtil.nullConvertEmptyString(floatConfigData.get(0)[1]));
        floatRegionConfigData.setFloatType(FetchSettingNrUtil.nullConvertEmptyString(floatConfigData.get(1)[1]));
        floatRegionConfigData.setFloatConfig(FetchSettingNrUtil.nullConvertEmptyString(floatConfigData.get(1)[3]));
        if (FetchSettingImportService.isPos(floatRegionConfigData)) {
            List dataRegionDefines = this.iRuntimeDataRegionService.getDataRegionsInForm(fetchSettingCond.getFormId());
            Map dataRegionDefineMap = dataRegionDefines.stream().filter(item -> DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)item.getRegionKind())).collect(Collectors.toMap(item -> FetchSettingNrUtil.getRegionTopStr(item.getRegionTop()), Function.identity(), (K1, K2) -> K1));
            floatRegionConfigData.setRegionId(((DataRegionDefine)dataRegionDefineMap.get(floatRegionConfigData.getRegionId())).getKey());
        }
        return floatRegionConfigData;
    }

    private static boolean isPos(FloatRegionConfigData floatRegionConfigData) {
        return floatRegionConfigData.getRegionId().length() != 36;
    }

    private FloatRegionConfigVO getQueryFieldByConfig(FloatRegionConfigData floatRegionConfigData, FetchSettingExcelContext fetchSettingExcelContext) {
        FloatRegionHandlerVO floatRegionHandlerVO = fetchSettingExcelContext.getFloatRegionHandlerByName(floatRegionConfigData.getFloatType());
        if (floatRegionHandlerVO == null) {
            throw new RuntimeException("\u672a\u83b7\u53d6\u5230\u5bf9\u5e94\u6d6e\u52a8\u8bbe\u7f6e\u5904\u7406\u5668");
        }
        String floatCode = floatRegionHandlerVO.getCode();
        FloatConfigExcelHandle floatConfigExcelHandle = this.floatConfigExcelGather.getByFloatConfigCode(floatCode);
        FloatRegionConfigVO floatRegionConfigVO = floatConfigExcelHandle.getQueryFieldByConfig(floatRegionConfigData, fetchSettingExcelContext);
        BeanUtils.copyProperties(floatRegionConfigData, floatRegionConfigVO);
        return floatRegionConfigVO;
    }

    public boolean doBaseCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, FetchSettingExcelContext fetchSettingExportContext) {
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFieldDefineCode()) && StringUtils.isEmpty((String)excelRowFetchSettingVO.getFieldDefineTitle())) {
            return false;
        }
        if (FloatSettingTypeEnum.CUSTOM_TEXT.getName().equals(excelRowFetchSettingVO.getFieldType()) || FloatSettingTypeEnum.RESULT_COLUMN.getName().equals(excelRowFetchSettingVO.getFieldType())) {
            if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFieldValue())) {
                excelRowFetchSettingVO.setErrorLog("\u6d6e\u52a8\u5217\u8bbe\u7f6e\u5185\u5bb9\u4e0d\u80fd\u4e3a\u7a7a");
                return false;
            }
            return true;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFetchSourceName())) {
            if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getLogicFormula())) {
                return true;
            }
            excelRowFetchSettingVO.setErrorLog(String.format("\u4e1a\u52a1\u6a21\u578b\u4e0d\u80fd\u4e3a\u7a7a", excelRowFetchSettingVO.getFetchSourceName()));
            return false;
        }
        BizModelDTO bizModelDTO = fetchSettingExportContext.getBizModelByName(excelRowFetchSettingVO.getFetchSourceName());
        if (bizModelDTO == null) {
            if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getLogicFormula())) {
                return true;
            }
            excelRowFetchSettingVO.setErrorLog(String.format("\u6839\u636e\u3010%1$s\u3011\u672a\u83b7\u53d6\u5230\u4e1a\u52a1\u6a21\u578b", excelRowFetchSettingVO.getFetchSourceName()));
            return false;
        }
        excelRowFetchSettingVO.setFetchSourceCode(bizModelDTO.getCode());
        excelRowFetchSettingVO.setBizModelCode(bizModelDTO.getComputationModelCode());
        BizModelHandler bizModelHandler = this.bizModelHandlerGather.getBizCheckerByBizModel(bizModelDTO.getComputationModelCode());
        return bizModelHandler.basicCheck(excelRowFetchSettingVO, bizModelDTO);
    }
}

