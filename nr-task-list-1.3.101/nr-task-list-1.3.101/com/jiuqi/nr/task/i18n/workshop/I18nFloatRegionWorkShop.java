/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFloatRegionDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nLanguageType;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class I18nFloatRegionWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nFloatRegionWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.FLOAT_REGION_TAB_TITLE;
    private IDesignTimeViewController designTimeViewController;
    private DesignFormDefineService formDefineService;
    private final String I18N_FLOAT_REGION_FORM_COL_KEY = "formTitle";
    private final String I18N_FLOAT_REGION_TITLE_COL_KEY = "floatRegionTitle";
    private final String I18N_FLOAT_REGION_POS_COL_KEY = "floatRegionPos";
    private final String I18N_FLOAT_REGION_FORM_GROUP_COL_KEY = "formGroupTitle";

    public I18nFloatRegionWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.formDefineService = serviceProvider.getFormDefineService();
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        List formGroups = this.designTimeViewController.listFormGroupByFormScheme(queryVO.getFormSchemeKey());
        HashMap<String, DesignFormGroupDefine> groupMap = new HashMap<String, DesignFormGroupDefine>();
        HashMap<String, List<DesignFormDefine>> groupAndFormMap = new HashMap<String, List<DesignFormDefine>>();
        ArrayList floatForms = new ArrayList();
        HashMap<String, List<DesignDataRegionDefine>> formAndRegionMap = new HashMap<String, List<DesignDataRegionDefine>>();
        HashMap<String, byte[]> regionAndDefaultLanguageMap = new HashMap<String, byte[]>();
        HashMap<String, byte[]> regionAndOtherLanguageMap = new HashMap<String, byte[]>();
        ArrayList regions = new ArrayList();
        for (DesignFormGroupDefine formGroup : formGroups) {
            groupMap.put(formGroup.getKey(), formGroup);
            List formsUnderGroup = this.designTimeViewController.listFormByGroup(formGroup.getKey());
            if (CollectionUtils.isEmpty(formsUnderGroup)) continue;
            List floatFormDefines = formsUnderGroup.stream().filter(formDefine -> formDefine.getFormType() == FormType.FORM_TYPE_FLOAT).collect(Collectors.toList());
            floatForms.addAll(floatFormDefines);
            groupAndFormMap.put(formGroup.getKey(), floatFormDefines);
        }
        if (CollectionUtils.isEmpty(floatForms)) {
            return new ArrayList();
        }
        for (DesignFormDefine floatForm : floatForms) {
            List designDataRegionDefines = this.designTimeViewController.listDataRegionByForm(floatForm.getKey());
            List floatRegion = designDataRegionDefines.stream().filter(designDataRegionDefine -> designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE).collect(Collectors.toList());
            regions.addAll(floatRegion);
            formAndRegionMap.put(floatForm.getKey(), floatRegion);
        }
        try {
            for (DesignDataRegionDefine region : regions) {
                byte[] otherLanguageInfos = this.formDefineService.getBigDataByLanguageType(region.getRegionSettingKey(), "REGION_TAB", Integer.valueOf(queryVO.getLanguageType()).intValue());
                regionAndOtherLanguageMap.put(region.getKey(), otherLanguageInfos);
                byte[] defaultLanguageInfos = this.formDefineService.getBigData(region.getRegionSettingKey(), "REGION_TAB");
                regionAndDefaultLanguageMap.put(region.getKey(), defaultLanguageInfos);
            }
        }
        catch (Exception e) {
            throw new I18nException("\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u67e5\u8be2\u51fa\u9519");
        }
        return this.convert(groupMap, groupAndFormMap, formAndRegionMap, regionAndDefaultLanguageMap, regionAndOtherLanguageMap);
    }

    private List<I18nFloatRegionDTO> convert(Map<String, DesignFormGroupDefine> groupMap, Map<String, List<DesignFormDefine>> groupAndFormMap, Map<String, List<DesignDataRegionDefine>> formAndRegionMap, Map<String, byte[]> regionAndDefaultLanguageMap, Map<String, byte[]> regionAndOtherLanguageMap) {
        ArrayList<I18nFloatRegionDTO> result = new ArrayList<I18nFloatRegionDTO>();
        for (Map.Entry<String, DesignFormGroupDefine> entry : groupMap.entrySet()) {
            String groupKey = entry.getKey();
            DesignFormGroupDefine formGroup = entry.getValue();
            for (DesignFormDefine form : groupAndFormMap.get(groupKey)) {
                for (DesignDataRegionDefine dataRegion : formAndRegionMap.get(form.getKey())) {
                    byte[] defaultLanguageInfo = regionAndDefaultLanguageMap.get(dataRegion.getKey());
                    byte[] otherLanguageInfo = regionAndOtherLanguageMap.get(dataRegion.getKey());
                    List<I18nBaseDTO> tabTitles = this.getTabTitles(defaultLanguageInfo, otherLanguageInfo);
                    for (I18nBaseDTO tabTitle : tabTitles) {
                        I18nFloatRegionDTO floatRegionDTO = new I18nFloatRegionDTO(tabTitle);
                        floatRegionDTO.setFloatRegionKey(dataRegion.getKey());
                        floatRegionDTO.setFloatRegionTitle(dataRegion.getTitle());
                        floatRegionDTO.setFormKey(form.getKey());
                        floatRegionDTO.setFormTitle(form.getTitle());
                        floatRegionDTO.setFormGroupKey(formGroup.getKey());
                        floatRegionDTO.setFormGroupTitle(formGroup.getTitle());
                        result.add(floatRegionDTO);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO conditionQueryVO) {
        return this.buildCommonResultVO(conditionQueryVO, this.designTimeViewController);
    }

    @Override
    public List<I18nColsObj> buildCols() {
        List<I18nColsObj> cols = this.initCols();
        cols.add(this.buildColsObj("formGroupTitle", "\u62a5\u8868\u6240\u5c5e\u5206\u7ec4", false, false, 200));
        cols.add(this.buildColsObj("formTitle", "\u6240\u5c5e\u62a5\u8868", false, false, 200));
        cols.add(this.buildColsObj("floatRegionTitle", "\u6d6e\u52a8\u533a\u57df", false, false, 250));
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u4e2d\u6587\u6807\u9898", false, false, 300));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u6807\u9898", true, false, 280));
        return cols;
    }

    private List<I18nColsObj> buildIOCols() {
        List<I18nColsObj> cols = this.buildCols();
        cols.set(2, this.buildColsObj("floatRegionPos", "\u533a\u57df\u5750\u6807([\u5de6\u3001\u4e0a\u3001\u53f3\u3001\u4e0b])", false, false, null));
        return cols;
    }

    @Override
    public void save(I18nResultVO resultVO) throws I18nException {
        try {
            HashSet<String> regionKeys = new HashSet<String>();
            HashMap<String, String> titleMap = new HashMap<String, String>();
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nFloatRegionDTO)) continue;
                I18nFloatRegionDTO floatRegionTruly = (I18nFloatRegionDTO)i18nBaseDTO;
                titleMap.put(floatRegionTruly.getLanguageKey(), floatRegionTruly.getOtherLanguageInfo());
                if (regionKeys.contains(floatRegionTruly.getFloatRegionKey())) continue;
                regionKeys.add(floatRegionTruly.getFloatRegionKey());
            }
            for (String string : regionKeys) {
                DesignDataRegionDefine dataRegion = this.designTimeViewController.getDataRegion(string);
                List<DesignRegionTabSettingDefine> otherLanguageObjs = this.getTabsByDataRegion(dataRegion, resultVO.getLanguageType());
                for (DesignRegionTabSettingDefine languageObj : otherLanguageObjs) {
                    if (!titleMap.containsKey(languageObj.getId()) || "AllData".equals(languageObj.getTitle()) || "\u6240\u6709\u6570\u636e".equals(languageObj.getTitle())) continue;
                    languageObj.setTitle((String)titleMap.get(languageObj.getId()));
                }
                ArrayList<DesignRegionTabSettingDefine> tabObjs = new ArrayList<DesignRegionTabSettingDefine>(otherLanguageObjs);
                byte[] data = RegionTabSettingData.regionTabSettingDataToBytes(tabObjs);
                this.formDefineService.updateBigDataDefine(dataRegion.getRegionSettingKey(), "REGION_TAB", resultVO.getLanguageType().intValue(), data);
            }
        }
        catch (Exception e) {
            throw new I18nException("\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    private List<I18nBaseDTO> getTabTitles(byte[] defaultLanguageInfo, byte[] otherLanguageInfo) {
        ArrayList<I18nBaseDTO> baseDTOs = new ArrayList<I18nBaseDTO>();
        List<RegionTabSettingDefine> tabs = this.getOtherLanguageTabs(defaultLanguageInfo, otherLanguageInfo);
        Map<String, RegionTabSettingDefine> defaultTabTitleMap = this.getDefaultLanguageTabs(defaultLanguageInfo).stream().collect(Collectors.toMap(RegionTabSettingDefine::getId, v -> v));
        if (!CollectionUtils.isEmpty(tabs)) {
            for (RegionTabSettingDefine tab : tabs) {
                I18nBaseDTO baseDTO = new I18nBaseDTO();
                baseDTO.setDefaultLanguageInfo(defaultTabTitleMap.get(tab.getId()) == null ? tab.getTitle() : defaultTabTitleMap.get(tab.getId()).getTitle());
                baseDTO.setOtherLanguageInfo(tab.getTitle());
                baseDTO.setLanguageKey(tab.getId());
                baseDTOs.add(baseDTO);
            }
        }
        return baseDTOs;
    }

    private List<RegionTabSettingDefine> getOtherLanguageTabs(byte[] defaultLanguageInfo, byte[] otherLanguageInfo) {
        ArrayList<RegionTabSettingDefine> tabs = new ArrayList<RegionTabSettingDefine>();
        if (null == otherLanguageInfo) {
            if (defaultLanguageInfo != null) {
                List defaultDatas = RegionTabSettingData.bytesToRegionTabSettingData((byte[])defaultLanguageInfo);
                RegionTabSettingData.transAllData((List)defaultDatas);
                tabs.addAll(defaultDatas);
            }
        } else if (defaultLanguageInfo != null) {
            List defaultDatas = RegionTabSettingData.bytesToRegionTabSettingData((byte[])defaultLanguageInfo);
            List otherDatas = RegionTabSettingData.bytesToRegionTabSettingData((byte[])otherLanguageInfo);
            RegionTabSettingData.mergeTabSetting((List)defaultDatas, (List)otherDatas);
            RegionTabSettingData.transAllData((List)defaultDatas);
            tabs.addAll(defaultDatas);
        }
        return tabs;
    }

    private List<RegionTabSettingDefine> getDefaultLanguageTabs(byte[] defaultLanguageInfo) {
        if (defaultLanguageInfo == null) {
            return Collections.emptyList();
        }
        return new ArrayList<RegionTabSettingDefine>(RegionTabSettingData.bytesToRegionTabSettingData((byte[])defaultLanguageInfo));
    }

    private List<DesignRegionTabSettingDefine> getTabsByDataRegion(DesignDataRegionDefine dataRegion, Integer langeuageType) {
        List languageObjs = new ArrayList();
        try {
            if (langeuageType != null) {
                byte[] otherLanguageInfo = this.formDefineService.getBigDataByLanguageType(dataRegion.getRegionSettingKey(), "REGION_TAB", langeuageType.intValue());
                if (otherLanguageInfo == null || otherLanguageInfo.length == 0) {
                    otherLanguageInfo = this.formDefineService.getBigData(dataRegion.getRegionSettingKey(), "REGION_TAB");
                }
                languageObjs = RegionTabSettingData.bytesToRegionTabSettingData((byte[])otherLanguageInfo);
            } else {
                byte[] defauleLanguageInfo = this.formDefineService.getBigData(dataRegion.getRegionSettingKey(), "REGION_TAB");
                languageObjs = RegionTabSettingData.bytesToRegionTabSettingData((byte[])defauleLanguageInfo);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return languageObjs;
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
        Map<String, DesignFormSchemeDefine> schemeCodeMap = formSchemes.stream().collect(Collectors.toMap(FormSchemeDefine::getFormSchemeCode, V -> V));
        List<I18nColsObj> cols = this.buildIOCols();
        try {
            for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                if (!this.checkSheet(sheet)) {
                    logger.error("\u9875\u7b7e[{}]\u5185\u5bb9\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u6309\u7167\u5bfc\u51fa\u65f6\u5185\u5bb9\u5bfc\u5165", (Object)sheet.getSheetName());
                    continue;
                }
                String formSchemeCode = this.getResourceCodeFromSheet(sheet);
                DesignFormSchemeDefine formScheme = schemeCodeMap.get(formSchemeCode);
                if (formScheme == null) {
                    logger.error("{}\u9875\u7b7e\u4e0b\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230", (Object)sheet.getSheetName());
                    continue;
                }
                HashMap importTabMap = new HashMap();
                for (Row row : sheet) {
                    HashMap<String, String> tabTitleMap;
                    if (row.getRowNum() == 0) continue;
                    String formTitle = this.getCellValueWithImport(row, 1);
                    String regionPos = this.getCellValueWithImport(row, 2);
                    String tabTitle = this.getCellValueWithImport(row, 3);
                    String languageInfo = this.getCellValueWithImport(row, cols.size() - 1);
                    HashMap posAndTabMap = (HashMap)importTabMap.get(formTitle);
                    if (CollectionUtils.isEmpty(posAndTabMap)) {
                        posAndTabMap = new HashMap();
                        tabTitleMap = new HashMap<String, String>();
                        tabTitleMap.put(tabTitle, languageInfo);
                        posAndTabMap.put(regionPos, tabTitleMap);
                        importTabMap.put(formTitle, posAndTabMap);
                        continue;
                    }
                    tabTitleMap = (HashMap<String, String>)posAndTabMap.get(regionPos);
                    if (CollectionUtils.isEmpty(tabTitleMap)) {
                        tabTitleMap = new HashMap();
                        tabTitleMap.put(tabTitle, languageInfo);
                        posAndTabMap.put(regionPos, tabTitleMap);
                        continue;
                    }
                    tabTitleMap.put(tabTitle, languageInfo);
                }
                if (CollectionUtils.isEmpty(importTabMap)) continue;
                List floatForms = this.designTimeViewController.listFormByFormSchemeAndType(formScheme.getKey(), FormType.FORM_TYPE_FLOAT);
                Map<String, DesignFormDefine> formMap = floatForms.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, v -> v));
                for (String formTitle : importTabMap.keySet()) {
                    DesignFormDefine form = formMap.get(formTitle);
                    if (form == null) {
                        logger.error("\u672a\u627e\u5230\u62a5\u8868[{}]", (Object)formTitle);
                        continue;
                    }
                    List dataRegions = this.designTimeViewController.listDataRegionByForm(form.getKey());
                    Map<String, DesignDataRegionDefine> regionPosMap = dataRegions.stream().collect(Collectors.toMap(dataRegion -> this.buildRegionPos((DesignDataRegionDefine)dataRegion), v -> v));
                    Map regionPosAndTabMap = (Map)importTabMap.get(formTitle);
                    for (String regionPos : regionPosAndTabMap.keySet()) {
                        Map tabTitleMap = (Map)regionPosAndTabMap.get(regionPos);
                        DesignDataRegionDefine dataRegion2 = regionPosMap.get(regionPos);
                        if (dataRegion2 == null) {
                            logger.error("\u672a\u627e\u5230\u62a5\u8868[{}]\u4e0b\uff0c\u5750\u6807\u4e3a[{}]\u7684\u6d6e\u52a8\u533a\u57df", (Object)formTitle, (Object)regionPos);
                            continue;
                        }
                        List<DesignRegionTabSettingDefine> otherLanguageObjs = this.getTabsByDataRegion(dataRegion2, 2);
                        Map<String, DesignRegionTabSettingDefine> otherTabMap = otherLanguageObjs.stream().collect(Collectors.toMap(RegionTabSettingDefine::getId, v -> v));
                        List<DesignRegionTabSettingDefine> defaultTabs = this.getTabsByDataRegion(dataRegion2, null);
                        for (RegionTabSettingDefine regionTabSettingDefine : defaultTabs) {
                            if (!tabTitleMap.containsKey(regionTabSettingDefine.getTitle()) || "AllData".equals(regionTabSettingDefine.getTitle()) || "\u6240\u6709\u6570\u636e".equals(regionTabSettingDefine.getTitle())) continue;
                            DesignRegionTabSettingDefine otherTab = otherTabMap.get(regionTabSettingDefine.getId());
                            otherTab.setTitle((String)tabTitleMap.get(regionTabSettingDefine.getTitle()));
                        }
                        ArrayList<DesignRegionTabSettingDefine> tabObjs = new ArrayList<DesignRegionTabSettingDefine>(otherTabMap.values());
                        byte[] byArray = RegionTabSettingData.regionTabSettingDataToBytes(tabObjs);
                        this.formDefineService.updateBigDataDefine(dataRegion2.getRegionSettingKey(), "REGION_TAB", 2, byArray);
                        logger.info("\u8868\u5355[{}]\u4e0b\uff0c\u5750\u6807\u4e3a[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u66f4\u65b0\u6210\u529f", (Object)formTitle, (Object)regionPos);
                    }
                }
            }
            logger.info("\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u8bbe\u7f6e\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u533a\u57df\u9875\u7b7e\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u533a\u57df\u9875\u7b7e\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String formTitleCell = this.getCellValueWithImport(row, 1);
        String regionPosCell = this.getCellValueWithImport(row, 2);
        String tabTitleCell = this.getCellValueWithImport(row, 3);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildIOCols().size() - 1);
        return "\u6240\u5c5e\u62a5\u8868".equals(formTitleCell) && "\u533a\u57df\u5750\u6807([\u5de6\u3001\u4e0a\u3001\u53f3\u3001\u4e0b])".equals(regionPosCell) && "\u4e2d\u6587\u6807\u9898".equals(tabTitleCell) && "\u82f1\u6587\u6807\u9898".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            logger.info("\u5171\u6709{}\u4e2a\u62a5\u8868\u65b9\u6848", (Object)formSchemes.size());
            List<I18nColsObj> cols = this.buildIOCols();
            for (DesignFormSchemeDefine formScheme : formSchemes) {
                List groups = this.designTimeViewController.listFormGroupByFormScheme(formScheme.getKey());
                HashMap<String, DesignFormGroupDefine> formAndGroupMap = new HashMap<String, DesignFormGroupDefine>();
                HashMap<String, DesignFormDefine> regionAndFormMap = new HashMap<String, DesignFormDefine>();
                ArrayList<RegionTabSettingDefine> allTabs = new ArrayList<RegionTabSettingDefine>();
                HashMap<String, DesignDataRegionDefine> tabAndRegionMap = new HashMap<String, DesignDataRegionDefine>();
                HashMap<String, RegionTabSettingDefine> otherLanguageMap = new HashMap<String, RegionTabSettingDefine>();
                HashMap<String, RegionTabSettingDefine> defaultLanguageMap = new HashMap<String, RegionTabSettingDefine>();
                HashMap<String, String> regionPosMap = new HashMap<String, String>();
                for (DesignFormGroupDefine group : groups) {
                    List formDefines = this.designTimeViewController.listFormByGroup(group.getKey());
                    for (DesignFormDefine formDefine : formDefines) {
                        if (formDefine.getFormType() == FormType.FORM_TYPE_FIX) continue;
                        formAndGroupMap.put(formDefine.getKey(), group);
                        List dateRegions = this.designTimeViewController.listDataRegionByForm(formDefine.getKey());
                        for (DesignDataRegionDefine dataRegion : dateRegions) {
                            if (dataRegion.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) continue;
                            String regionPos = this.buildRegionPos(dataRegion);
                            regionPosMap.put(dataRegion.getKey(), regionPos);
                            byte[] otherLanguageInfo = this.formDefineService.getBigDataByLanguageType(dataRegion.getRegionSettingKey(), "REGION_TAB", I18nLanguageType.ENGLISH.getValue());
                            byte[] defaultLanguageInfo = this.formDefineService.getBigData(dataRegion.getRegionSettingKey(), "REGION_TAB");
                            List<RegionTabSettingDefine> otherLanguageTabs = this.getOtherLanguageTabs(defaultLanguageInfo, otherLanguageInfo);
                            List<RegionTabSettingDefine> defaultLanguageTabs = this.getDefaultLanguageTabs(defaultLanguageInfo);
                            allTabs.addAll(defaultLanguageTabs);
                            otherLanguageMap.putAll(otherLanguageTabs.stream().collect(Collectors.toMap(RegionTabSettingDefine::getId, v -> v)));
                            defaultLanguageMap.putAll(defaultLanguageTabs.stream().collect(Collectors.toMap(RegionTabSettingDefine::getId, v -> v)));
                            tabAndRegionMap.putAll(defaultLanguageTabs.stream().collect(Collectors.toMap(RegionTabSettingDefine::getId, v -> dataRegion)));
                            regionAndFormMap.put(dataRegion.getKey(), formDefine);
                        }
                    }
                }
                Sheet sheet = this.createSheet(workbook, this.getFormSchemeSheetName(formScheme), cols);
                int colNumber = cols.size();
                int rowNumber = allTabs.size() + 1;
                for (int row = 1; row < rowNumber; ++row) {
                    Row rowData = sheet.createRow(row);
                    rowData.setHeightInPoints(25.0f);
                    for (int col = 0; col < colNumber; ++col) {
                        Cell cell = rowData.createCell(col);
                        cell.setCellValue(this.getCellValue(row - 1, col, allTabs, tabAndRegionMap, regionAndFormMap, regionPosMap, formAndGroupMap, defaultLanguageMap, otherLanguageMap, cols));
                    }
                }
            }
            this.writeToZip(exportParam, this.getFileName(currentResourceType), workbook);
            logger.info("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u5b8c\u6210\u7b7e\u591a\u8bed\u8a00\u5931\u8d25", (Object)task.getTitle());
            throw new I18nException("\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u591a\u8bed\u8a00\u5bfc\u51fa\u5931\u8d25", e);
        }
    }

    private String buildRegionPos(DesignDataRegionDefine dataRegion) {
        StringBuffer regionPos = new StringBuffer();
        regionPos.append('[');
        regionPos.append(dataRegion.getRegionLeft()).append(',');
        regionPos.append(dataRegion.getRegionTop()).append(',');
        regionPos.append(dataRegion.getRegionRight()).append(',');
        regionPos.append(dataRegion.getRegionBottom());
        regionPos.append(']');
        return regionPos.toString();
    }

    private String getCellValue(int row, int col, List<RegionTabSettingDefine> tabs, Map<String, DesignDataRegionDefine> tabAndRegionMap, Map<String, DesignFormDefine> regionFormMap, Map<String, String> regionPosMap, Map<String, DesignFormGroupDefine> formAndGroupMap, Map<String, RegionTabSettingDefine> tabAndDefaultLanguageMap, Map<String, RegionTabSettingDefine> tabAndOtherLanguageMap, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nBaseObj colsObj = cols.get(col);
        RegionTabSettingDefine tab = tabs.get(row);
        DesignDataRegionDefine region = tabAndRegionMap.get(tab.getId());
        DesignFormDefine form = regionFormMap.get(region.getKey());
        DesignFormGroupDefine group = formAndGroupMap.get(form.getKey());
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = tabAndDefaultLanguageMap.get(tab.getId()) == null ? "" : tabAndDefaultLanguageMap.get(tab.getId()).getTitle();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = tabAndOtherLanguageMap.get(tab.getId()) == null ? "" : tabAndOtherLanguageMap.get(tab.getId()).getTitle();
                break;
            }
            case "formTitle": {
                cellValue = form.getTitle();
                break;
            }
            case "floatRegionPos": {
                cellValue = regionPosMap.get(region.getKey());
                break;
            }
            case "formGroupTitle": {
                cellValue = group.getTitle();
                break;
            }
        }
        return cellValue;
    }
}

