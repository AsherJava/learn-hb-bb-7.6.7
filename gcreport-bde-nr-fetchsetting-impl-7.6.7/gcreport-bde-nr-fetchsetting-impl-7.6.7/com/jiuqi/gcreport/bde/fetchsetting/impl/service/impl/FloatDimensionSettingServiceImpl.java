/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatDimensionSettingEO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FloatDimensionSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatDimensionSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FloatDimensionSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingLogHelperUtil;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FloatDimensionSettingServiceImpl
implements FloatDimensionSettingService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FloatDimensionSettingDao floatDimensionSettingDao;
    private final NedisCache bdeFloatDimensionSettingCache;

    public FloatDimensionSettingServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_FLOAT_DIMENSION_SETTING_MANAGE");
        this.bdeFloatDimensionSettingCache = cacheManager.getCache("BDE_FLOAT_DIMENSION_SETTING");
    }

    @Override
    public List<DimensionSetting> getDimensionSetByRegionId(String regionId) {
        Assert.isNotEmpty((String)regionId, (String)"\u6d6e\u52a8\u8868\u533a\u57dfID\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(regionId);
        if (fieldKeys == null || fieldKeys.isEmpty()) {
            return Collections.emptyList();
        }
        HashSet<String> fieldKeySet = new HashSet<String>(fieldKeys);
        Map<String, DataField> fieldDefinedGroup = this.getFieldDefinedGroupByKey(fieldKeySet);
        FloatDimensionSettingEO selectedFields = (FloatDimensionSettingEO)this.bdeFloatDimensionSettingCache.get(regionId, () -> this.floatDimensionSettingDao.getSelectedFields(regionId));
        if (selectedFields == null || selectedFields.getDimensionConfigInfo() == null || selectedFields.getDimensionConfigInfo().isEmpty()) {
            return fieldDefinedGroup.entrySet().stream().map(entry -> {
                DimensionSetting dto = new DimensionSetting();
                dto.setKey(((DataField)entry.getValue()).getKey());
                dto.setCode(((DataField)entry.getValue()).getCode());
                dto.setName(((DataField)entry.getValue()).getTitle());
                if (DataFieldKind.TABLE_FIELD_DIM == ((DataField)entry.getValue()).getDataFieldKind()) {
                    dto.setRequired(Boolean.valueOf(true));
                } else {
                    dto.setRequired(Boolean.valueOf(false));
                }
                return dto;
            }).collect(Collectors.toList());
        }
        List selectedFieldList = JSONUtil.parseArray((String)selectedFields.getDimensionConfigInfo(), String.class);
        return fieldDefinedGroup.entrySet().stream().map(entry -> {
            DimensionSetting dto = new DimensionSetting();
            DataField dataField = (DataField)entry.getValue();
            dto.setKey(((DataField)entry.getValue()).getKey());
            dto.setCode(dataField.getCode());
            dto.setName(dataField.getTitle());
            dto.setRequired(Boolean.valueOf(selectedFieldList.contains(dataField.getKey())));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveFloatDimensionSetting(FloatDimensionSettingDTO floatDimensionSettingDTO) {
        Assert.isNotEmpty((String)floatDimensionSettingDTO.getFormSchemeId(), (String)"\u62a5\u8868\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        Assert.isNotEmpty((String)floatDimensionSettingDTO.getFormId(), (String)"\u6d6e\u52a8\u8868ID\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        Assert.isNotEmpty((String)floatDimensionSettingDTO.getRegionId(), (String)"\u6d6e\u52a8\u8868\u533a\u57dfID\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        Assert.isNotEmpty((String)floatDimensionSettingDTO.getDimensionConfigInfo(), (String)"\u6d6e\u52a8\u8868\u533a\u57df\u7ef4\u5ea6\u8bbe\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        List floatDimensionSettingList = JSONUtil.parseArray((String)floatDimensionSettingDTO.getDimensionConfigInfo(), DimensionSetting.class);
        List requiredKeys = floatDimensionSettingList.stream().filter(DimensionSetting::getRequired).map(DimensionSetting::getKey).collect(Collectors.toList());
        String fetchSourceRowSettingStr = JSONUtil.toJSONString(requiredKeys);
        FetchSettingLogHelperUtil.logSaveFloatDimensionSetting(floatDimensionSettingDTO);
        FloatDimensionSettingEO selectedFields = this.floatDimensionSettingDao.getSelectedFields(floatDimensionSettingDTO.getRegionId());
        if (selectedFields != null) {
            this.floatDimensionSettingDao.delete(floatDimensionSettingDTO.getRegionId());
        }
        this.floatDimensionSettingDao.save(floatDimensionSettingDTO.getFormSchemeId(), floatDimensionSettingDTO.getFormId(), floatDimensionSettingDTO.getRegionId(), fetchSourceRowSettingStr);
        this.dimensionSettingCacheClear();
    }

    @Override
    public List<String> getDimensionSettingFiledList(String regionId) {
        FloatDimensionSettingEO selectedFields = (FloatDimensionSettingEO)this.bdeFloatDimensionSettingCache.get(regionId, () -> this.floatDimensionSettingDao.getSelectedFields(regionId));
        if (selectedFields == null || selectedFields.getDimensionConfigInfo() == null || selectedFields.getDimensionConfigInfo().isEmpty()) {
            return null;
        }
        return JSONUtil.parseArray((String)selectedFields.getDimensionConfigInfo(), String.class);
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

    public void dimensionSettingCacheClear() {
        this.bdeFloatDimensionSettingCache.clear();
    }
}

