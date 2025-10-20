/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.map.MapUtil
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableData
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableExtractField
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractField
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractParagraph
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.enums.PublishStateEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import cn.hutool.core.map.MapUtil;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableData;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableExtractField;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractField;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractParagraph;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.enums.PublishStateEnum;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingDesService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingDesService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillSettingBuildPublishStateService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillSettingBuildPublishStateServiceImpl
implements BillSettingBuildPublishStateService {
    @Autowired
    private BillFixedSettingDesService billSettingDesService;
    @Autowired
    private BillFixedSettingService billSettingService;
    @Autowired
    private BillFloatSettingDesService billFloatSettingDesService;
    @Autowired
    private BillFloatSettingService billFloatSettingService;
    private final String FORMULA = "=";

    @Override
    public void setFieldShowTextAndPublishState(BillFormDefine billFormDefine, String billUniqueCode, String fetchSchemeId) {
        this.buildMainTableShowTextAndPublishState(billUniqueCode, fetchSchemeId, billFormDefine.getMainTableName(), billFormDefine.getMainData());
        this.buildChildrenTableShowTextAndPublish(billUniqueCode, fetchSchemeId, billFormDefine.getChildrenTable());
    }

    private void buildMainTableShowTextAndPublishState(String billUniqueCode, String fetchSchemeId, String mainTableName, List<BillExtractParagraph> mainData) {
        BillSettingCondiDTO billSettingCondi = new BillSettingCondiDTO();
        billSettingCondi.setBillType(billUniqueCode);
        billSettingCondi.setSchemeId(fetchSchemeId);
        billSettingCondi.setBillTable(mainTableName);
        Map<String, String> desFieldIdToEqualsStringSetMap = this.billSettingDesService.getFiexedSettingEOsInTable(billSettingCondi).stream().collect(Collectors.toMap(FetchSettingDesEO::getDataLinkId, FetchSettingDesEO::getEqualsString));
        Map<String, String> fieldIdToEqualsStringSetMap = this.billSettingService.getFixedSettingEOS(billSettingCondi).stream().collect(Collectors.toMap(FetchSettingEO::getDataLinkId, FetchSettingEO::getEqualsString));
        for (BillExtractParagraph paragraph : mainData) {
            for (List row : paragraph.getParaRowData()) {
                for (BillExtractField field : row) {
                    this.setMainTableFieldTextAndState(field, desFieldIdToEqualsStringSetMap, fieldIdToEqualsStringSetMap);
                }
            }
        }
    }

    private void buildChildrenTableShowTextAndPublish(String billUniqueCode, String fetchSchemeId, List<BillChildTableData> childrenTable) {
        BillSettingCondiDTO billSettingCondi = new BillSettingCondiDTO();
        for (BillChildTableData childTableData : childrenTable) {
            billSettingCondi = new BillSettingCondiDTO();
            String tableName = childTableData.getTableName();
            billSettingCondi.setBillType(billUniqueCode);
            billSettingCondi.setSchemeId(fetchSchemeId);
            billSettingCondi.setBillTable(tableName);
            Map<String, String> desFieldIdToEqualsStringSetMap = this.billSettingDesService.getFiexedSettingEOsInTable(billSettingCondi).stream().collect(Collectors.toMap(FetchSettingDesEO::getDataLinkId, FetchSettingDesEO::getEqualsString));
            Map<String, String> fieldIdToEqualsStringSetMap = this.billSettingService.getFixedSettingEOS(billSettingCondi).stream().collect(Collectors.toMap(FetchSettingEO::getDataLinkId, FetchSettingEO::getEqualsString));
            Map<String, FieldValueAndIsCustom> desFieldIdQueryFieldMapping = this.getDataLinkIdQyertFieldMap(this.billFloatSettingDesService.getBillFloatSetting(billSettingCondi));
            Map<String, FieldValueAndIsCustom> fieldIdQueryFieldMapping = this.getDataLinkIdQyertFieldMap(this.billFloatSettingService.getFloatSetting(billSettingCondi));
            List columns = childTableData.getColumns();
            for (BillChildTableExtractField column : columns) {
                this.setFieldShowTextAndPublishState(column, desFieldIdQueryFieldMapping, fieldIdQueryFieldMapping, desFieldIdToEqualsStringSetMap, fieldIdToEqualsStringSetMap);
            }
        }
    }

    private void setFieldShowTextAndPublishState(BillChildTableExtractField field, Map<String, FieldValueAndIsCustom> desFieldIdQueryFieldMapping, Map<String, FieldValueAndIsCustom> fieldIdQueryFieldMapping, Map<String, String> desFieldIdToEqualsStringSetMap, Map<String, String> fieldIdToEqualsStringSetMap) {
        String fieldCode = field.getColumnName();
        String showText = "";
        if (MapUtil.isEmpty(desFieldIdQueryFieldMapping)) {
            if (desFieldIdToEqualsStringSetMap.containsKey(fieldCode)) {
                showText = "=";
            }
        } else {
            FieldValueAndIsCustom fieldValueAndIsCustom = desFieldIdQueryFieldMapping.get(fieldCode);
            if (Objects.nonNull(fieldValueAndIsCustom)) {
                showText = fieldValueAndIsCustom.getValue();
                if (fieldValueAndIsCustom.isCustomText()) {
                    field.setCustomTextflag(true);
                }
            } else {
                showText = "";
            }
        }
        field.setColumnText(showText);
        if (StringUtils.isEmpty((String)showText)) {
            field.setPublishState(PublishStateEnum.EMPTY.getCode());
        } else if (this.checkIsPublish(showText, fieldCode, fieldIdToEqualsStringSetMap, desFieldIdToEqualsStringSetMap, fieldIdQueryFieldMapping)) {
            field.setPublishState(PublishStateEnum.PUBLISHED.getCode());
        } else {
            field.setPublishState(PublishStateEnum.SAVED.getCode());
        }
    }

    private void setMainTableFieldTextAndState(BillExtractField field, Map<String, String> desFieldIdToEqualsStringSetMap, Map<String, String> fieldIdToEqualsStringSetMap) {
        String fieldCode = field.getColumnName();
        String showText = "";
        if (desFieldIdToEqualsStringSetMap.containsKey(fieldCode)) {
            showText = "=";
        }
        field.setColumnText(showText);
        if (StringUtils.isEmpty((String)showText)) {
            field.setPublishState(PublishStateEnum.EMPTY.getCode());
        } else if (this.checkIsPublish(showText, fieldCode, fieldIdToEqualsStringSetMap, desFieldIdToEqualsStringSetMap, new HashMap<String, FieldValueAndIsCustom>())) {
            field.setPublishState(PublishStateEnum.PUBLISHED.getCode());
        } else {
            field.setPublishState(PublishStateEnum.SAVED.getCode());
        }
    }

    private Map<String, FieldValueAndIsCustom> getDataLinkIdQyertFieldMap(BillFloatRegionConfigDTO fetchFloatSettingDes) {
        QueryConfigInfo queryConfigInfo;
        if (null != fetchFloatSettingDes && null != fetchFloatSettingDes.getQueryConfigInfo() && null != (queryConfigInfo = fetchFloatSettingDes.getQueryConfigInfo()) && !CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return this.getFloatSettingDataLinkIdQueryFieldTitleMapping(queryConfigInfo);
        }
        return new HashMap<String, FieldValueAndIsCustom>();
    }

    private Map<String, FieldValueAndIsCustom> getFloatSettingDataLinkIdQueryFieldTitleMapping(QueryConfigInfo queryConfigInfo) {
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return new HashMap<String, FieldValueAndIsCustom>();
        }
        Map<String, String> nameToTitleMap = queryConfigInfo.getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
        return queryConfigInfo.getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, zbMappingVO -> {
            FieldValueAndIsCustom field = new FieldValueAndIsCustom();
            if ("=".equals(zbMappingVO.getQueryName())) {
                field.setValue("=");
                field.setCustomText(false);
                return field;
            }
            String patternStr = "(?<=\\$\\{)[^}]*(?=})";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(zbMappingVO.getQueryName());
            if (matcher.find()) {
                String code = matcher.group(0);
                field.setValue(StringUtils.isEmpty((String)((String)nameToTitleMap.get(code))) ? code : (String)nameToTitleMap.get(code));
                field.setCustomText(false);
                return field;
            }
            field.setValue(zbMappingVO.getQueryName());
            field.setCustomText(true);
            return field;
        }, (last, next) -> next));
    }

    private boolean checkIsPublish(String showText, String fieldId, Map<String, String> fieldIdToEqualsStringSetMap, Map<String, String> desFieldIdToEqualsStringSetMap, Map<String, FieldValueAndIsCustom> fieldIdQueryFieldMapping) {
        if (!"=".equals(showText)) {
            if (MapUtil.isEmpty(fieldIdQueryFieldMapping) || Objects.isNull(fieldIdQueryFieldMapping.get(fieldId)) || StringUtils.isEmpty((String)fieldIdQueryFieldMapping.get(fieldId).getValue())) {
                return false;
            }
            return fieldIdQueryFieldMapping.get(fieldId).getValue().equals(showText);
        }
        String equalsString = fieldIdToEqualsStringSetMap.get(fieldId);
        String desEqualsString = desFieldIdToEqualsStringSetMap.get(fieldId);
        if (equalsString == null || desEqualsString == null) {
            return false;
        }
        return equalsString.contains(desEqualsString);
    }

    private class FieldValueAndIsCustom {
        private String value;
        private boolean customText;

        private FieldValueAndIsCustom() {
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isCustomText() {
            return this.customText;
        }

        public void setCustomText(boolean customText) {
            this.customText = customText;
        }
    }
}

