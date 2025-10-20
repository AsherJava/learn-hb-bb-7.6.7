/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.FetchDimensionClient
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.bde.bizmodel.define;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.FetchDimensionClient;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractFinComputationModel
implements IBizComputationModel {
    @Autowired
    private FetchDimensionClient dimensionClient;

    protected final ColumnDefineVO buildFetchTypeColumn() {
        ColumnDefineVO column = new ColumnDefineVO(FetchFixedFieldEnum.FETCHTYPE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.fetchType"), true, "SINGLE", "SELECT", null, null);
        column.setWidth(Integer.valueOf(120));
        return column;
    }

    protected final ColumnDefineVO buildSumTypeColumn() {
        ArrayList<SelectOptionVO> sumTypeSelectOptions = new ArrayList<SelectOptionVO>();
        sumTypeSelectOptions.add(new SelectOptionVO(SumTypeEnum.FMX.getCode(), SumTypeEnum.FMX.getName()));
        sumTypeSelectOptions.add(new SelectOptionVO(SumTypeEnum.MX.getCode(), SumTypeEnum.MX.getName()));
        ColumnDefineVO sumType = new ColumnDefineVO(FetchFixedFieldEnum.SUMTYPE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.sumType"), true, "SINGLE", "SELECT", SumTypeEnum.FMX.getCode(), sumTypeSelectOptions);
        sumType.setWidth(Integer.valueOf(100));
        return sumType;
    }

    protected final String buildDimMemo(FetchSettingVO fetchSetting) {
        if (StringUtils.isEmpty((String)fetchSetting.getDimensionSetting())) {
            return "";
        }
        StringBuilder dimMemo = new StringBuilder();
        List dimensionList = ((ArrayList)JsonUtils.readValue((String)fetchSetting.getDimensionSetting(), (TypeReference)new TypeReference<ArrayList<Dimension>>(){})).stream().sorted(Comparator.comparing(Dimension::getDimCode)).collect(Collectors.toList());
        Map<String, String> dimNameMap = this.listDimensionByDataModel(this.getBizDataCode()).stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getTitle, (k1, k2) -> k2));
        for (Dimension dimension : dimensionList) {
            if (StringUtils.isEmpty((String)dimension.getDimValue())) continue;
            dimMemo.append(dimNameMap.get(dimension.getDimCode()) != null ? dimNameMap.get(dimension.getDimCode()) : dimension.getDimCode()).append(MatchRuleEnum.getEnumByCode((String)dimension.getDimRule()).getName()).append(dimension.getDimValue()).append(",");
        }
        return dimMemo.toString();
    }

    protected final String buildSubjectMemo(FetchSettingVO fetchSetting) {
        if (StringUtils.isEmpty((String)fetchSetting.getSubjectCode())) {
            return "";
        }
        if (fetchSetting.getSubjectCode().contains(":") || fetchSetting.getSubjectCode().contains(",")) {
            return String.format("%1$s\"%2$s\",", FetchFixedFieldEnum.SUBJECTCODE.getName(), fetchSetting.getSubjectCode());
        }
        return this.buildGenericMemo(FetchFixedFieldEnum.SUBJECTCODE.getName(), fetchSetting.getSubjectCode());
    }

    protected final String buildExcludeSubjectMemo(FetchSettingVO fetchSetting) {
        return this.buildGenericMemo(FetchFixedFieldEnum.EXCLUDESUBJECTCODE.getName(), fetchSetting.getExcludeSubjectCode());
    }

    protected List<SelectOptionVO> listDimensionOptionByBizDataModelCode(String dataModelCode) {
        return this.listDimensionByDataModel(dataModelCode).stream().sorted(Comparator.comparing(DimensionVO::getCode)).map(item -> new SelectOptionVO(item.getCode(), item.getTitle())).collect(Collectors.toList());
    }

    protected List<DimensionVO> listDimensionByDataModel(String dataModelCode) {
        List assistDimDTOS = (List)this.dimensionClient.listAllDimensionByDataModel(dataModelCode).getData();
        if (CollectionUtils.isEmpty((Collection)assistDimDTOS)) {
            return Collections.emptyList();
        }
        return assistDimDTOS;
    }

    protected List<SelectOptionVO> getFetchTypesByFetchTypeEnums(List<FetchTypeEnum> fetchTypeEnums) {
        if (CollectionUtils.isEmpty(fetchTypeEnums)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<SelectOptionVO> fetchTypes = new ArrayList<SelectOptionVO>();
        fetchTypeEnums.forEach(fetchTypeEnum -> fetchTypes.add(new SelectOptionVO(fetchTypeEnum.getCode(), fetchTypeEnum.getName())));
        return fetchTypes;
    }

    protected List<SelectOptionVO> listAllDimension() {
        List assistDimDTOS = (List)this.dimensionClient.listDimension().getData();
        if (CollectionUtils.isEmpty((Collection)assistDimDTOS)) {
            return Collections.emptyList();
        }
        return assistDimDTOS.stream().map(item -> new SelectOptionVO(item.getCode(), item.getTitle())).collect(Collectors.toList());
    }
}

