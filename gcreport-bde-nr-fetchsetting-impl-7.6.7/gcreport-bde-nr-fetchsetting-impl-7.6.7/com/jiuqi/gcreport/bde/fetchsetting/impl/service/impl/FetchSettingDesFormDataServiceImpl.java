/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bde.common.constant.DataRegionTypeEnum
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bde.common.constant.DataRegionTypeEnum;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.config.BdeFetchSettingGridCellDataSerialize;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesFormDataService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesStopService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchSettingDesFormDataServiceImpl
implements FetchSettingDesFormDataService {
    private static final String FORMULA = "=";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private FetchSettingDao fetchSettingDao;
    @Autowired
    private FetchSettingDesStopService stopFlagService;

    @Override
    public String queryFormData(FetchSettingCond fetchSettingCond) {
        BigDataDefine dataDefine = this.runTimeViewController.getReportDataFromForm(fetchSettingCond.getFormId());
        Grid2Data gridData = null;
        if (dataDefine != null) {
            if (dataDefine.getData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])dataDefine.getData());
            } else {
                throw new BdeRuntimeException("\u8868\u6837\u4fe1\u606f\u4e3a\u7a7a formId=" + fetchSettingCond.getFormId());
            }
        }
        if (gridData == null) {
            throw new BdeRuntimeException("\u8868\u6837\u4fe1\u606f\u4e3a\u7a7a formId=" + fetchSettingCond.getFormId());
        }
        this.fillGridCellData(fetchSettingCond, gridData);
        return this.serialize(gridData);
    }

    private String serialize(Grid2Data griddata) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new BdeFetchSettingGridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        try {
            return mapper.writeValueAsString((Object)griddata);
        }
        catch (JsonProcessingException e) {
            throw new BdeRuntimeException("grid\u8f6cString\u53d1\u751f\u5f02\u5e38" + e.getMessage());
        }
    }

    private void fillGridCellData(FetchSettingCond fetchSettingCond, Grid2Data gridData) {
        List regions = this.runTimeViewController.getAllRegionsInForm(fetchSettingCond.getFormId());
        for (DataRegionDefine region : regions) {
            List dataLinks = this.runTimeViewController.getAllLinksInRegion(region.getKey());
            Set<String> fieldKeys = dataLinks.stream().map(DataLinkDefine::getLinkExpression).filter(Objects::nonNull).collect(Collectors.toSet());
            Map<String, DataField> fieldDefinedGroupByKey = this.getFieldDefinedGroupByKey(fieldKeys);
            if (fieldDefinedGroupByKey.isEmpty()) continue;
            this.setCellData(fetchSettingCond, region, fieldDefinedGroupByKey, dataLinks, gridData);
        }
    }

    private Map<String, DataField> getFieldDefinedGroupByKey(Set<String> fieldKeys) {
        Map<String, Object> fieldDefinedByKey = new HashMap<String, DataField>(16);
        if (!CollectionUtils.isEmpty(fieldKeys)) {
            try {
                fieldDefinedByKey = this.runtimeDataSchemeService.getDataFields(new ArrayList<String>(fieldKeys)).stream().collect(Collectors.toMap(Basic::getKey, Function.identity()));
            }
            catch (Exception e) {
                throw new BdeRuntimeException("\u67e5\u8be2\u591a\u4e2a\u6307\u6807\u53d1\u751f\u5f02\u5e38" + e.getMessage());
            }
        }
        return fieldDefinedByKey;
    }

    private void setCellData(FetchSettingCond fetchSettingCond, DataRegionDefine region, Map<String, DataField> fieldDefinedGroupByKey, List<DataLinkDefine> dataLinks, Grid2Data gridData) {
        String regionType;
        if (Objects.isNull(fetchSettingCond) || StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId())) {
            this.emptyCellData(region, fieldDefinedGroupByKey, dataLinks, gridData);
            return;
        }
        Map<Object, Object> desDataLinkIdQueryFieldMapping = new HashMap(16);
        fetchSettingCond.setRegionId(region.getKey());
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingDesWithStopFlagByRegionId(fetchSettingCond);
        Map<String, String> desDataLinkIdEqualsStringMap = fetchSettingDesEOS.stream().collect(Collectors.toMap(FetchSettingDesEO::getDataLinkId, FetchSettingDesEO::getEqualsString));
        Map<String, Boolean> desDataLinkIdStopFlagMap = fetchSettingDesEOS.stream().collect(Collectors.toMap(FetchSettingDesEO::getDataLinkId, settingDes -> this.isStop(settingDes.getStopFlag())));
        List fetchSettingEOS = this.fetchSettingDao.listFetchSettingWithStopFlagByRegionId(fetchSettingCond);
        Map<String, String> dataLinkIdEqualsStringMap = fetchSettingEOS.stream().collect(Collectors.toMap(FetchSettingEO::getDataLinkId, FetchSettingEO::getEqualsString));
        Map<String, Boolean> dataLinkIdStopFlagMap = fetchSettingEOS.stream().collect(Collectors.toMap(FetchSettingEO::getDataLinkId, settingDes -> this.isStop(settingDes.getStopFlag())));
        Map<Object, Object> desFloatLinkidEqualStringMap = new HashMap();
        Map<Object, Object> floatLinkidEqualStringMap = new HashMap();
        Map<Object, Object> desFloatLinkidStopFlagMap = new HashMap();
        if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)region.getRegionKind())) {
            regionType = DataRegionTypeEnum.FIXED.getCode();
        } else {
            FloatRegionConfigVO floatRegionConfigVODesWithStop = this.stopFlagService.getFloatRegionConfigVODesWithStopState(fetchSettingCond);
            desDataLinkIdQueryFieldMapping = this.getFloatSettingDataLinkIdQueryFieldTitleMapping(floatRegionConfigVODesWithStop);
            desFloatLinkidEqualStringMap = this.getFloatLinkidEqualStringMap(floatRegionConfigVODesWithStop);
            desFloatLinkidStopFlagMap = this.getFloatLinkidStopFlagMap(floatRegionConfigVODesWithStop);
            FloatRegionConfigVO floatRegionConfigVOWithStop = this.stopFlagService.getFloatRegionConfigVO(fetchSettingCond);
            floatLinkidEqualStringMap = this.getFloatLinkidEqualStringMap(floatRegionConfigVOWithStop);
            regionType = DataRegionTypeEnum.FLOAT.getCode();
        }
        for (DataLinkDefine link : dataLinks) {
            DataField fieldDefine;
            GridCellData gridCellData = gridData.getGridCellData(link.getPosX(), link.getPosY());
            if (gridCellData == null || (fieldDefine = fieldDefinedGroupByKey.get(link.getLinkExpression())) == null) continue;
            HashMap<String, String> dataLinkInfo = new HashMap<String, String>(16);
            dataLinkInfo.put("dataLinkId", link.getKey());
            dataLinkInfo.put("regionId", region.getKey());
            String showText = "";
            if (desDataLinkIdQueryFieldMapping.isEmpty()) {
                if (desDataLinkIdEqualsStringMap.containsKey(link.getKey())) {
                    showText = FORMULA;
                }
            } else {
                showText = (String)desDataLinkIdQueryFieldMapping.get(link.getKey());
            }
            String linkKey = link.getKey();
            if (!StringUtils.isEmpty((String)showText)) {
                boolean isFix = this.isFixSetting(showText);
                boolean isPublish = isFix ? this.checkFixSettingIsPublish(dataLinkIdEqualsStringMap.get(linkKey), desDataLinkIdEqualsStringMap.get(linkKey)) : this.checkQueryFieldIsPublish((String)floatLinkidEqualStringMap.get(linkKey), (String)desFloatLinkidEqualStringMap.get(linkKey));
                boolean isStopped = isFix ? Boolean.TRUE.equals(desDataLinkIdStopFlagMap.get(linkKey)) : Boolean.TRUE.equals(desFloatLinkidStopFlagMap.get(linkKey));
                String text = this.getShowText(isPublish, isFix, isStopped, showText);
                int color = this.getForeGroundColor(isPublish, isStopped);
                gridCellData.setForeGroundColor(color);
                gridCellData.setShowText(text);
            }
            dataLinkInfo.put("regionType", regionType);
            dataLinkInfo.put("fieldDefineId", fieldDefine.getKey());
            dataLinkInfo.put("title", fieldDefine.getTitle());
            String dataLinkInfoStr = JSONUtil.toJSONString(dataLinkInfo);
            gridCellData.setDataExFromString(dataLinkInfoStr);
            gridCellData.setHorzAlign(3);
        }
    }

    private void emptyCellData(DataRegionDefine region, Map<String, DataField> fieldDefinedGroupByKey, List<DataLinkDefine> dataLinks, Grid2Data gridData) {
        String regionType = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)region.getRegionKind()) ? DataRegionTypeEnum.FIXED.getCode() : DataRegionTypeEnum.FLOAT.getCode();
        for (DataLinkDefine link : dataLinks) {
            DataField fieldDefine;
            GridCellData gridCellData = gridData.getGridCellData(link.getPosX(), link.getPosY());
            if (gridCellData == null || (fieldDefine = fieldDefinedGroupByKey.get(link.getLinkExpression())) == null) continue;
            HashMap<String, String> dataLinkInfo = new HashMap<String, String>(16);
            dataLinkInfo.put("dataLinkId", link.getKey());
            dataLinkInfo.put("regionId", region.getKey());
            dataLinkInfo.put("regionType", regionType);
            dataLinkInfo.put("fieldDefineId", fieldDefine.getKey());
            dataLinkInfo.put("title", fieldDefine.getTitle());
            String dataLinkInfoStr = JSONUtil.toJSONString(dataLinkInfo);
            gridCellData.setDataExFromString(dataLinkInfoStr);
            gridCellData.setHorzAlign(3);
        }
    }

    private String getShowText(boolean isPublish, boolean isFix, boolean isStopped, String showText) {
        if (!isFix) {
            return showText;
        }
        if (isPublish) {
            return isStopped ? "\u5df2\u505c\u7528" : "\u5df2\u53d1\u5e03";
        }
        return isStopped ? "\u5df2\u505c\u7528" : "\u5df2\u4fdd\u5b58";
    }

    private int getForeGroundColor(boolean isPublish, boolean isStopped) {
        if (isPublish) {
            return isStopped ? 253 : 254;
        }
        return 255;
    }

    private boolean checkFixSettingIsPublish(String settingEqualsString, String desSettingEqualsString) {
        return !StringUtils.isEmpty((String)settingEqualsString) && settingEqualsString.equals(desSettingEqualsString);
    }

    private boolean checkQueryFieldIsPublish(String queryFieldEqualsString, String desQueryFieldEqualsString) {
        return !StringUtils.isEmpty((String)queryFieldEqualsString) && queryFieldEqualsString.equals(desQueryFieldEqualsString);
    }

    private boolean isFixSetting(String showText) {
        return FORMULA.equals(showText);
    }

    private boolean isStop(Integer stopFlag) {
        return !Objects.isNull(stopFlag) && stopFlag == 1;
    }

    private Map<String, String> getFloatSettingDataLinkIdQueryFieldTitleMapping(FloatRegionConfigVO floatRegionConfigVO) {
        if (Objects.isNull(floatRegionConfigVO) || Objects.isNull(floatRegionConfigVO.getQueryConfigInfo())) {
            return new HashMap<String, String>();
        }
        QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return new HashMap<String, String>();
        }
        Map<String, String> nameToTitleMap = queryConfigInfo.getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
        return queryConfigInfo.getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, zbMappingVO -> {
            if (FORMULA.equals(zbMappingVO.getQueryName())) {
                return FORMULA;
            }
            String patternStr = "(?<=\\$\\{)[^}]*(?=})";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(zbMappingVO.getQueryName());
            if (matcher.find()) {
                String code = matcher.group(0);
                return StringUtils.isEmpty((String)((String)nameToTitleMap.get(code))) ? code : (String)nameToTitleMap.get(code);
            }
            return zbMappingVO.getQueryName();
        }, (last, next) -> next));
    }

    private Map<String, String> getFloatLinkidEqualStringMap(FloatRegionConfigVO floatRegionConfigVO) {
        if (Objects.isNull(floatRegionConfigVO) || Objects.isNull(floatRegionConfigVO.getQueryConfigInfo())) {
            return new HashMap<String, String>();
        }
        QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return new HashMap<String, String>();
        }
        Map<String, String> nameToTitleMap = queryConfigInfo.getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
        return queryConfigInfo.getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, zbMappingVO -> {
            String stopFlag = String.valueOf(Objects.nonNull(zbMappingVO.getStopFlag()) && zbMappingVO.getStopFlag() == 1);
            if (FORMULA.equals(zbMappingVO.getQueryName())) {
                return FORMULA + stopFlag;
            }
            String patternStr = "(?<=\\$\\{)[^}]*(?=})";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(zbMappingVO.getQueryName());
            if (matcher.find()) {
                String code = matcher.group(0);
                return StringUtils.isEmpty((String)((String)nameToTitleMap.get(code))) ? code + stopFlag : (String)nameToTitleMap.get(code) + stopFlag;
            }
            return zbMappingVO.getQueryName() + stopFlag;
        }, (last, next) -> next));
    }

    private Map<String, Boolean> getFloatLinkidStopFlagMap(FloatRegionConfigVO floatRegionConfigVO) {
        if (Objects.isNull(floatRegionConfigVO)) {
            return new HashMap<String, Boolean>();
        }
        QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
        if (Objects.isNull(queryConfigInfo)) {
            return new HashMap<String, Boolean>();
        }
        List zbMapping = queryConfigInfo.getZbMapping();
        if (CollectionUtils.isEmpty((Collection)zbMapping)) {
            return new HashMap<String, Boolean>();
        }
        HashMap<String, Boolean> floatLinkidStopFlagMap = new HashMap<String, Boolean>();
        for (FloatZbMappingVO FloatZbMappingVO2 : zbMapping) {
            floatLinkidStopFlagMap.put(FloatZbMappingVO2.getDataLinkId(), Objects.isNull(FloatZbMappingVO2.getStopFlag()) ? false : 1 == FloatZbMappingVO2.getStopFlag());
        }
        return floatLinkidStopFlagMap;
    }
}

