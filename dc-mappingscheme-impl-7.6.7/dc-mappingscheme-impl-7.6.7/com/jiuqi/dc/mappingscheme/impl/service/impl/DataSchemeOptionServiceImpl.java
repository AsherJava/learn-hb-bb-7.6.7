/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionIntegerValue
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionStringValue
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue
 *  com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.mappingscheme.impl.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionIntegerValue;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionStringValue;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import com.jiuqi.dc.mappingscheme.impl.dao.DataSchemeOptionDao;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeOptionDO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataSchemeOptionServiceImpl
implements DataSchemeOptionService {
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataSchemeOptionDao dataSchemeOptionDao;
    @Autowired
    private DimensionService dimensionService;

    @Override
    public List<DataSchemeOptionVO> getListByPluginType(String pluginTypeSymbol) {
        IPluginType pluginType = this.pluginTypeGather.getPluginType(pluginTypeSymbol);
        Assert.isNotNull((Object)pluginType, (String)String.format("\u672a\u627e\u5230\u6807\u8bc6\u4e3a\u3010%1$s\u3011\u7684\u6838\u7b97\u8f6f\u4ef6\u7248\u672c", pluginTypeSymbol), (Object[])new Object[0]);
        if (pluginType.getOptionList() == null || pluginType.getOptionList().isEmpty()) {
            return CollectionUtil.newArrayList((Object[])new DataSchemeOptionVO[0]);
        }
        return pluginType.getOptionList().stream().sorted(Comparator.comparing(option -> option.getOptionType().getReadonlyFlag())).map(option -> {
            String cfSource;
            DataSchemeOptionVO vo = new DataSchemeOptionVO();
            vo.setCode(option.getCode());
            vo.setTitle(option.getTitle());
            vo.setParamType(option.getOptionType().getType());
            vo.setShowForm(option.getOptionType().getShowForm());
            vo.setMultipleFlag(option.getOptionType().getMultipleFlag());
            vo.setReadonlyFlag(option.getOptionType().getReadonlyFlag());
            vo.setSource(option.source());
            vo.setOptionValue(option.defaultValue());
            vo.setDescription(option.description());
            if ("CF_SOURCE".equals(option.getCode()) && !StringUtils.isEmpty((String)(cfSource = pluginType.getCfSource()))) {
                vo.setReadonlyFlag(Boolean.valueOf(true));
                vo.setOptionValue(cfSource);
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DataSchemeOptionVO> getListByDataScheme(DataSchemeDTO dataScheme) {
        List<DataSchemeOptionVO> optionVoList = this.getListByPluginType(dataScheme.getPluginType());
        Map<String, DataSchemeOptionDO> optionDoMap = this.dataSchemeOptionDao.queryByDataSchemeCode(dataScheme.getCode()).stream().collect(Collectors.toMap(DataSchemeOptionDO::getCode, o -> o, (k1, k2) -> k1));
        for (DataSchemeOptionVO optionVO : optionVoList) {
            if (!optionDoMap.containsKey(optionVO.getCode())) continue;
            optionVO.setId(optionDoMap.get(optionVO.getCode()).getId());
            String optionValue = optionDoMap.get(optionVO.getCode()).getOptionValue();
            if (!StringUtils.isEmpty((String)optionValue) && Objects.equals(OptionType.SELECT.getType(), optionVO.getParamType()) && Arrays.asList(OptionType.SELECT.getShowForm(), OptionType.CHECKBOX.getShowForm()).contains(optionVO.getShowForm()) && optionValue.startsWith("[") && optionValue.endsWith("]")) {
                String substring = optionValue.replaceFirst("\\[", "").substring(0, optionValue.length() - 2);
                optionVO.setOptionValue(substring.replace(" ", ""));
                continue;
            }
            optionVO.setOptionValue(optionValue);
        }
        return optionVoList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String dataSchemeCode, List<DataSchemeOptionVO> options) {
        this.dataSchemeOptionDao.deleteByDataSchemeCode(dataSchemeCode);
        for (DataSchemeOptionVO optionVO : options) {
            DataSchemeOptionDO optionDO = new DataSchemeOptionDO();
            optionDO.setDataSchemeCode(dataSchemeCode);
            optionDO.setCode(optionVO.getCode());
            if (Objects.equals(OptionType.SELECT.getType(), optionVO.getParamType()) && !StringUtils.isEmpty((String)optionVO.getOptionValue()) && Arrays.asList(OptionType.SELECT.getShowForm(), OptionType.CHECKBOX.getShowForm()).contains(optionVO.getShowForm())) {
                if (optionVO.getOptionValue().startsWith("[") && optionVO.getOptionValue().endsWith("]")) {
                    optionDO.setOptionValue(optionVO.getOptionValue());
                } else {
                    List collect = Arrays.stream(optionVO.getOptionValue().split(",")).map(String::trim).collect(Collectors.toList());
                    optionDO.setOptionValue(collect.toString());
                }
            } else {
                optionDO.setOptionValue(optionVO.getOptionValue());
            }
            if (StringUtils.isEmpty((String)optionVO.getId())) {
                optionDO.setId(UUIDUtils.newHalfGUIDStr());
            } else {
                optionDO.setId(optionVO.getId());
            }
            this.dataSchemeOptionDao.insert(optionDO);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(String dataSchemeCode) {
        this.dataSchemeOptionDao.deleteByDataSchemeCode(dataSchemeCode);
    }

    public void getValueByUnitCode(String unitCode, String optionCode) {
    }

    @Override
    public DataSchemeOptionDTO getValueByDataSchemeCode(String dataSchemeCode, String optionCode) {
        String dbValue;
        DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(dataSchemeCode);
        Assert.isNotNull((Object)dataScheme, (String)String.format("\u672a\u627e\u5230\u4ee3\u7801\u4e3a\u3010%1$s\u3011\u7684\u6570\u636e\u6620\u5c04\u65b9\u6848", dataSchemeCode), (Object[])new Object[0]);
        IPluginType pluginType = this.pluginTypeGather.getPluginType(dataScheme.getPluginType());
        Assert.isNotNull((Object)pluginType, (String)String.format("\u672a\u627e\u5230\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%1$s\u3011\u914d\u7f6e\u7684\u6838\u7b97\u8f6f\u4ef6\u7248\u672c", dataScheme.getName()), (Object[])new Object[0]);
        if (pluginType.getOptionList() == null || pluginType.getOptionList().isEmpty()) {
            throw new IllegalArgumentException(String.format("\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%1$s\u3011\u6ca1\u6709\u7ba1\u63a7\u9009\u9879\u5b9a\u4e49", dataScheme.getName()));
        }
        IDataSchemeOption optionDefine = null;
        try {
            optionDefine = pluginType.getOptionList().stream().filter(o -> o.getCode().equals(optionCode)).findFirst().get();
        }
        catch (NoSuchElementException e2) {
            throw new IllegalArgumentException(String.format("\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%1$s\u3011\u4e2d\u672a\u5305\u542b\u7ba1\u63a7\u9009\u9879%2$s", dataScheme.getName(), optionCode));
        }
        String optionValue = dbValue = dataScheme.getOptions().stream().filter(e -> Objects.equals(e.getCode(), optionCode)).findFirst().map(DataSchemeOptionVO::getOptionValue).orElse("");
        if (!Arrays.asList(OptionType.SELECT, OptionType.CHECKBOX).contains(optionDefine.getOptionType())) {
            optionValue = StringUtils.isEmpty((String)dbValue) ? optionDefine.defaultValue() : dbValue;
        }
        DataSchemeOptionDTO optionDTO = new DataSchemeOptionDTO(optionDefine.getCode(), optionDefine.getTitle());
        switch (optionDefine.getOptionType().getDataType()) {
            case STRING: {
                optionDTO.setOptionValue((DataSchemeOptionValue)new DataSchemeOptionStringValue(optionValue));
                break;
            }
            case INT: {
                optionDTO.setOptionValue((DataSchemeOptionValue)new DataSchemeOptionIntegerValue(optionValue));
                break;
            }
            default: {
                optionDTO.setOptionValue((DataSchemeOptionValue)new DataSchemeOptionStringValue(optionValue));
            }
        }
        return optionDTO;
    }

    @Override
    public List<DimensionVO> listOppositeDimensionByScheme(String dataSchemeCode) {
        DataSchemeOptionDTO option;
        ArrayList<DimensionVO> list = new ArrayList<DimensionVO>();
        try {
            option = this.getValueByDataSchemeCode(dataSchemeCode, "DC_OPPOSITE_DIMENSIONS");
        }
        catch (Exception e) {
            return list;
        }
        DataSchemeOptionValue optionValue = option.getOptionValue();
        if (!Objects.isNull(optionValue) && !StringUtils.isEmpty((String)optionValue.getStringValue())) {
            List<String> dimCodes = Arrays.asList(optionValue.getStringValue().split(","));
            List dimensionList = this.dimensionService.findDimFieldsVOByTableName("DC_VOUCHERITEMASS");
            for (DimensionVO dimensionVO : dimensionList) {
                if (!dimCodes.contains(dimensionVO.getCode())) continue;
                list.add(dimensionVO);
            }
        }
        return list;
    }

    @Override
    public Boolean isOppositeDimension(String dataSchemeCode, String dimCode) {
        DataSchemeOptionDTO option;
        try {
            option = this.getValueByDataSchemeCode(dataSchemeCode, "DC_OPPOSITE_DIMENSIONS");
        }
        catch (Exception e) {
            return false;
        }
        DataSchemeOptionValue optionValue = option.getOptionValue();
        if (!Objects.isNull(optionValue) && !StringUtils.isEmpty((String)optionValue.getStringValue())) {
            return Arrays.asList(optionValue.getStringValue().split(",")).contains(dimCode);
        }
        return false;
    }
}

