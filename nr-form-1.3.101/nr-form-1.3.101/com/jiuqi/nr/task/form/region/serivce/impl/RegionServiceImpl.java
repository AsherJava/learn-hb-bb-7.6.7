/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl
 *  com.jiuqi.nr.definition.internal.impl.RegionEdgeStyleData
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.task.form.region.serivce.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl;
import com.jiuqi.nr.definition.internal.impl.RegionEdgeStyleData;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.AbstractState;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.ext.dto.RegionQuery;
import com.jiuqi.nr.task.form.region.dto.DataRegionDTO;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.region.dto.RegionEdgeStyleDTO;
import com.jiuqi.nr.task.form.region.dto.RegionExtensionDTO;
import com.jiuqi.nr.task.form.region.dto.RegionOrderDTO;
import com.jiuqi.nr.task.form.region.dto.RegionTabSettingDTO;
import com.jiuqi.nr.task.form.region.serivce.IRegionService;
import com.jiuqi.nr.task.form.util.RegionBeanUtils;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl
implements IRegionService {
    private static final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignBigDataTableDao bigDataTableDao;

    @Override
    public DataRegionSettingDTO getSetting(RegionQuery regionQuery) {
        return null;
    }

    @Override
    public DataRegionDTO getSimpleSetting(RegionQuery regionQuery) {
        return null;
    }

    @Override
    public List<DataRegionDTO> listDataRegion(String formKey) {
        List dataRegionDefines = this.designTimeViewController.listDataRegionByForm(formKey);
        ArrayList<DataRegionDTO> regions = new ArrayList<DataRegionDTO>(dataRegionDefines.size());
        for (DesignDataRegionDefine dataRegionDefine : dataRegionDefines) {
            DesignRegionSettingDefine setting = this.designTimeViewController.getRegionSettingByRegion(dataRegionDefine.getKey());
            DataRegionSettingDTO dto = RegionBeanUtils.toSettingDto(dataRegionDefine, setting, this.bigDataTableDao);
            regions.add(dto);
        }
        return regions;
    }

    @Override
    public List<DataRegionSettingDTO> listDataRegionSetting(String formKey) {
        List dataRegionDefines = this.designTimeViewController.listDataRegionByForm(formKey);
        return dataRegionDefines.stream().map(r -> RegionBeanUtils.toSettingDto(r, this.designTimeViewController.getRegionSettingByRegion(r.getKey()), this.bigDataTableDao)).collect(Collectors.toList());
    }

    @Override
    public DataRegionSettingDTO getRegionSetting(String regionKey) {
        DesignDataRegionDefine dataRegion = this.designTimeViewController.getDataRegion(regionKey);
        if (dataRegion == null) {
            return null;
        }
        DesignRegionSettingDefine setting = this.designTimeViewController.getRegionSettingByRegion(regionKey);
        return RegionBeanUtils.toSettingDto(dataRegion, setting, this.bigDataTableDao);
    }

    @Override
    public void saveDataRegionSetting(String formKey, List<DataRegionSettingDTO> dataRegions) {
        Map<Constants.DataStatus, List<DataRegionSettingDTO>> dataStatusListMap = dataRegions.stream().collect(Collectors.groupingBy(AbstractState::getStatus));
        this.insert(dataStatusListMap.getOrDefault(Constants.DataStatus.NEW, Collections.emptyList()));
        List<DataRegionSettingDTO> settingDTOS = dataStatusListMap.getOrDefault(Constants.DataStatus.MODIFY, Collections.emptyList());
        this.update(formKey, settingDTOS);
        List defines = this.designTimeViewController.listDataRegionByForm(formKey);
        Set keys = defines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        String[] deleteKeys = (String[])dataStatusListMap.getOrDefault(Constants.DataStatus.DELETE, Collections.emptyList()).stream().map(DataCore::getKey).filter(keys::contains).toArray(String[]::new);
        this.designTimeViewController.deleteDataRegion(deleteKeys);
    }

    private void update(String formKey, List<DataRegionSettingDTO> settingDTOS) {
        Map<String, DesignDataRegionDefine> dataRegionDefineMap = this.designTimeViewController.listDataRegionByForm(formKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        DesignDataRegionDefine[] updateDataRegionDefines = new DesignDataRegionDefine[settingDTOS.size()];
        for (int i = 0; i < settingDTOS.size(); ++i) {
            DesignDataRegionDefine regionDefine;
            DataRegionSettingDTO regionSettingDTO = settingDTOS.get(i);
            updateDataRegionDefines[i] = regionDefine = dataRegionDefineMap.get(regionSettingDTO.getKey());
            RegionBeanUtils.toDefine(regionSettingDTO, regionDefine);
            RegionExtensionDTO extensionDTO = regionSettingDTO.getRegionExtension();
            if (extensionDTO == null || extensionDTO.getKey() == null) continue;
            this.designTimeViewController.deleteRegionSetting(extensionDTO.getKey());
            DesignRegionSettingDefine designRegionSettingDefine = this.convertRegionExtension(extensionDTO);
            designRegionSettingDefine.setKey(extensionDTO.getKey());
            this.designTimeViewController.insertRegionSetting(designRegionSettingDefine);
        }
        if (updateDataRegionDefines.length != 0) {
            this.designTimeViewController.updateDataRegion(updateDataRegionDefines);
        }
    }

    private void insert(List<DataRegionSettingDTO> settingDTOS) {
        ArrayList<DesignDataRegionDefine> regions = new ArrayList<DesignDataRegionDefine>(settingDTOS.size());
        for (DataRegionSettingDTO settingDTO : settingDTOS) {
            DesignDataRegionDefine regionDefine = this.designTimeViewController.initDataRegion();
            regions.add(regionDefine);
            RegionBeanUtils.toDefine(settingDTO, regionDefine);
            RegionExtensionDTO regionExtension = settingDTO.getRegionExtension();
            if (regionExtension == null) continue;
            DesignRegionSettingDefine designRegionSettingDefine = this.convertRegionExtension(regionExtension);
            regionDefine.setRegionSettingKey(designRegionSettingDefine.getKey());
            this.designTimeViewController.insertRegionSetting(designRegionSettingDefine);
        }
        this.designTimeViewController.insertDataRegion(regions.toArray(new DesignDataRegionDefine[0]));
    }

    private DesignRegionSettingDefine convertRegionExtension(RegionExtensionDTO extensionDTO) {
        RegionTabSettingData define;
        if (extensionDTO == null) {
            return null;
        }
        DesignRegionSettingDefine designRegionSettingDefine = this.designTimeViewController.initRegionSetting();
        designRegionSettingDefine.setDictionaryFillLinks(extensionDTO.getDictionaryFillLinks());
        if (extensionDTO.getRegionTabSettings() != null) {
            ArrayList<RegionTabSettingData> tabSettingDefines = new ArrayList<RegionTabSettingData>();
            for (RegionTabSettingDTO regionTabSetting : extensionDTO.getRegionTabSettings()) {
                define = new RegionTabSettingData();
                define.setId(regionTabSetting.getId());
                define.setTitle(regionTabSetting.getTitle());
                define.setDisplayCondition(regionTabSetting.getDisplayCondition());
                define.setFilterCondition(regionTabSetting.getFilterCondition());
                define.setBindingExpression(regionTabSetting.getBindingExpression());
                define.setRowNum(regionTabSetting.getRowNum());
                if (regionTabSetting.getOrder() == null) {
                    define.setOrder(OrderGenerator.newOrder());
                } else {
                    define.setOrder(regionTabSetting.getOrder());
                }
                tabSettingDefines.add(define);
            }
            designRegionSettingDefine.setRegionTabSetting(tabSettingDefines);
        }
        if (extensionDTO.getRegionEdgeStyles() != null) {
            ArrayList<RegionTabSettingData> styleDefines = new ArrayList<RegionTabSettingData>();
            for (RegionEdgeStyleDTO regionEdgeStyle : extensionDTO.getRegionEdgeStyles()) {
                define = new RegionEdgeStyleData();
                define.setStartIndex(regionEdgeStyle.getStartIndex());
                define.setEndIndex(regionEdgeStyle.getEndIndex());
                define.setEdgeLineStyle(regionEdgeStyle.getEdgeStyle());
                define.setEdgeLineColor(regionEdgeStyle.getEdgeLineColorToInt());
                styleDefines.add(define);
            }
            designRegionSettingDefine.setLastRowStyle(styleDefines);
        }
        List<RegionOrderDTO> regionOrderDTOS = extensionDTO.getRowNumberSettings();
        ArrayList<DesignRowNumberSettingImpl> rowNumberSetting = new ArrayList<DesignRowNumberSettingImpl>();
        for (RegionOrderDTO regionOrderDTO : regionOrderDTOS) {
            DesignRowNumberSettingImpl rs = new DesignRowNumberSettingImpl();
            rs.setPosX(regionOrderDTO.getPosX());
            rs.setPosY(regionOrderDTO.getPosY());
            rs.setStartNumber(regionOrderDTO.getStartNumber());
            rs.setIncrement(regionOrderDTO.getIncrement());
            rowNumberSetting.add(rs);
        }
        designRegionSettingDefine.setRowNumberSetting(rowNumberSetting);
        designRegionSettingDefine.setCardRecord(extensionDTO.getCardRecord());
        designRegionSettingDefine.setEntityDefaultValue(JacksonUtils.objectToJson(extensionDTO.getEntityDefaultValues()));
        return designRegionSettingDefine;
    }

    @Override
    public void insertDefaultRegion(DataRegionSettingDTO regionSettingDTO) {
        DesignDataRegionDefine regionDefine = this.designTimeViewController.initDataRegion();
        regionDefine.setFormKey(regionSettingDTO.getFormKey());
        regionDefine.setTitle(regionSettingDTO.getTitle());
        regionDefine.setRegionLeft(regionSettingDTO.getRegionLeft().intValue());
        regionDefine.setRegionRight(regionSettingDTO.getRegionRight().intValue());
        regionDefine.setRegionTop(regionSettingDTO.getRegionTop().intValue());
        regionDefine.setRegionBottom(regionSettingDTO.getRegionBottom().intValue());
        regionDefine.setRegionKind(DataRegionKind.forValue((int)regionSettingDTO.getRegionKind()));
        regionDefine.setRowsInFloatRegion(regionSettingDTO.getRowsInFloatRegion().intValue());
        regionDefine.setRegionEnterNext(regionSettingDTO.getRegionEnterNext());
        this.designTimeViewController.insertDataRegion(new DesignDataRegionDefine[]{regionDefine});
    }
}

