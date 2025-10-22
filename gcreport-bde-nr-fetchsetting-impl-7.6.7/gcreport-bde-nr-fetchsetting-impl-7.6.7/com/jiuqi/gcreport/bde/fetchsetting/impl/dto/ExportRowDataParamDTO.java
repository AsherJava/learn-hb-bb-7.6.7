/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExportRowDataParamDTO {
    private List<DataLinkDefine> dataLinks;
    private Map<String, List<FetchSettingVO>> fixedFetchSettingDesGroupByDataLinkId;
    private Map<String, DataField> fieldDefinedGroupByKey;
    private Map<String, Integer> titleKeysGroupByKeyAndIndex;
    private Map<String, String> dimCodeToDimTitleMap;
    private boolean floatRegion;
    private Map<String, String> floatFieldCodeToTitleMap;

    public ExportRowDataParamDTO() {
    }

    public ExportRowDataParamDTO(List<DataLinkDefine> dataLinks, Map<String, List<FetchSettingVO>> fixedFetchSettingDesGroupByDataLinkId, Map<String, DataField> fieldDefinedGroupByKey, Map<String, Integer> titleKeysGroupByKeyAndIndex, Map<String, String> dimCodeToDimTitleMap, boolean floatRegion) {
        this.dataLinks = dataLinks;
        this.fixedFetchSettingDesGroupByDataLinkId = fixedFetchSettingDesGroupByDataLinkId;
        this.fieldDefinedGroupByKey = fieldDefinedGroupByKey;
        this.titleKeysGroupByKeyAndIndex = titleKeysGroupByKeyAndIndex;
        this.dimCodeToDimTitleMap = dimCodeToDimTitleMap;
        this.floatRegion = floatRegion;
    }

    public List<DataLinkDefine> getDataLinks() {
        return this.dataLinks;
    }

    public void setDataLinks(List<DataLinkDefine> dataLinks) {
        this.dataLinks = dataLinks;
    }

    public Map<String, List<FetchSettingVO>> getFixedFetchSettingDesGroupByDataLinkId() {
        return this.fixedFetchSettingDesGroupByDataLinkId;
    }

    public void setFixedFetchSettingDesGroupByDataLinkId(Map<String, List<FetchSettingVO>> fixedFetchSettingDesGroupByDataLinkId) {
        this.fixedFetchSettingDesGroupByDataLinkId = fixedFetchSettingDesGroupByDataLinkId;
    }

    public Map<String, DataField> getFieldDefinedGroupByKey() {
        return this.fieldDefinedGroupByKey;
    }

    public void setFieldDefinedGroupByKey(Map<String, DataField> fieldDefinedGroupByKey) {
        this.fieldDefinedGroupByKey = fieldDefinedGroupByKey;
    }

    public Map<String, Integer> getTitleKeysGroupByKeyAndIndex() {
        return this.titleKeysGroupByKeyAndIndex;
    }

    public void setTitleKeysGroupByKeyAndIndex(Map<String, Integer> titleKeysGroupByKeyAndIndex) {
        this.titleKeysGroupByKeyAndIndex = titleKeysGroupByKeyAndIndex;
    }

    public Map<String, String> getDimCodeToDimTitleMap() {
        return this.dimCodeToDimTitleMap;
    }

    public void setDimCodeToDimTitleMap(Map<String, String> dimCodeToDimTitleMap) {
        this.dimCodeToDimTitleMap = dimCodeToDimTitleMap;
    }

    public boolean isFloatRegion() {
        return this.floatRegion;
    }

    public void setFloatRegion(boolean floatRegion) {
        this.floatRegion = floatRegion;
    }

    public Map<String, String> getFloatFieldCodeToTitleMap() {
        return this.floatFieldCodeToTitleMap;
    }

    public void setFloatFieldCodeToTitleMap(FloatRegionConfigVO fetchFloatSettingVO) {
        List queryFields = fetchFloatSettingVO.getQueryConfigInfo().getQueryFields();
        if (CollectionUtils.isEmpty((Collection)queryFields)) {
            this.floatFieldCodeToTitleMap = new HashMap<String, String>();
            return;
        }
        this.floatFieldCodeToTitleMap = queryFields.stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
    }
}

