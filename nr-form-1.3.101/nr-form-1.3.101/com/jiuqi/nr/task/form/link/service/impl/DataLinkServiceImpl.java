/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.task.form.link.service.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.controller.dto.LinkMappingDataDTO;
import com.jiuqi.nr.task.form.dto.AbstractState;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.exception.LinkRuntimeException;
import com.jiuqi.nr.task.form.ext.dto.LinkQuery;
import com.jiuqi.nr.task.form.link.dto.DataLinkDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.link.dto.FilterTemplateDTO;
import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import com.jiuqi.nr.task.form.link.service.IDataLinkService;
import com.jiuqi.nr.task.form.link.service.ILinkMappingService;
import com.jiuqi.nr.task.form.util.LinkBeanUtils;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataLinkServiceImpl
implements IDataLinkService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignBigDataTableDao bigDataTableDao;
    @Autowired
    private ILinkMappingService linkMappingService;

    @Override
    public DataLinkSettingDTO getSetting(LinkQuery linkQuery) {
        return null;
    }

    @Override
    public void saveLinks(String formKey, List<DataLinkSettingDTO> dataRegions) {
        Map<Constants.DataStatus, List<DataLinkSettingDTO>> dataLinkSettingMap = dataRegions.stream().collect(Collectors.groupingBy(AbstractState::getStatus));
        List<DataLinkSettingDTO> newLinkSettings = dataLinkSettingMap.getOrDefault(Constants.DataStatus.NEW, Collections.emptyList());
        this.insert(newLinkSettings);
        List<DataLinkSettingDTO> updateLinkSettings = dataLinkSettingMap.getOrDefault(Constants.DataStatus.MODIFY, Collections.emptyList());
        this.update(formKey, updateLinkSettings);
        this.delete(formKey, dataLinkSettingMap.getOrDefault(Constants.DataStatus.DELETE, Collections.emptyList()));
    }

    private void delete(String formKey, List<DataLinkSettingDTO> settingDTOS) {
        String[] strings = (String[])settingDTOS.stream().map(DataCore::getKey).toArray(String[]::new);
        this.deleteLinkMapping(formKey, strings);
        this.designTimeViewController.deleteDataLink(strings);
        ArrayList<DesignBigDataTable> bigDataTables = new ArrayList<DesignBigDataTable>(settingDTOS.size());
        for (DataLinkSettingDTO settingDTO : settingDTOS) {
            if (settingDTO.getAttachment() == null) continue;
            DesignBigDataTable bigDataTable = new DesignBigDataTable();
            bigDataTable.setKey(settingDTO.getKey());
            bigDataTable.setCode("ATTACHMENT");
            bigDataTable.setLang(1);
            bigDataTables.add(bigDataTable);
        }
        try {
            this.bigDataTableDao.delete(bigDataTables);
        }
        catch (Exception e) {
            throw new LinkRuntimeException(e);
        }
    }

    private void deleteLinkMapping(String formKey, String[] linkKeys) {
        LinkMappingDataDTO linkMappingDataDTO = this.linkMappingService.queryLinkMappingData(formKey, Collections.emptyList());
        List<LinkMappingDTO> linkMapping = linkMappingDataDTO.getLinkMapping();
        if (CollectionUtils.isEmpty(linkMapping)) {
            return;
        }
        HashSet<String> keys = new HashSet<String>(Arrays.asList(linkKeys));
        linkMapping.removeIf(linkMappingDTO -> keys.contains(linkMappingDTO.getLeftLinkKey()) || keys.contains(linkMappingDTO.getRightLinkKey()));
        this.linkMappingService.saveLinkMapping(formKey, linkMapping);
    }

    private void update(String formKey, List<DataLinkSettingDTO> linkSettingDTOS) {
        Map<String, DesignDataLinkDefine> dataLinkDefineMap = this.designTimeViewController.listDataLinkByForm(formKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        DesignDataLinkDefine[] updateDataLinkDefines = (DesignDataLinkDefine[])linkSettingDTOS.stream().map(setting -> {
            DesignDataLinkDefine designDataLinkDefine = (DesignDataLinkDefine)dataLinkDefineMap.get(setting.getKey());
            LinkBeanUtils.toDefine(setting, designDataLinkDefine);
            return designDataLinkDefine;
        }).toArray(DesignDataLinkDefine[]::new);
        if (updateDataLinkDefines.length > 0) {
            this.designTimeViewController.updateDataLink(updateDataLinkDefines);
        }
        ArrayList<DesignBigDataTable> bigDataTables = new ArrayList<DesignBigDataTable>(linkSettingDTOS.size());
        for (DataLinkSettingDTO settingDTO : linkSettingDTOS) {
            String attachment = settingDTO.getAttachment();
            if (attachment == null) continue;
            byte[] bytes = DesignFormDefineBigDataUtil.StringToBytes((String)attachment);
            DesignBigDataTable bigDataTable = new DesignBigDataTable();
            bigDataTable.setKey(settingDTO.getKey());
            bigDataTable.setData(bytes);
            bigDataTable.setCode("ATTACHMENT");
            bigDataTable.setLang(1);
            bigDataTables.add(bigDataTable);
        }
        try {
            this.bigDataTableDao.delete(bigDataTables);
            this.bigDataTableDao.insert((Object[])bigDataTables.toArray(new DesignBigDataTable[0]));
        }
        catch (DBParaException e) {
            throw new LinkRuntimeException(e);
        }
    }

    private void insert(List<DataLinkSettingDTO> newLinkSettings) {
        ArrayList<DesignDataLinkDefine> dataLinkDefines = new ArrayList<DesignDataLinkDefine>(newLinkSettings.size());
        ArrayList<DesignBigDataTable> bigDataTables = new ArrayList<DesignBigDataTable>(newLinkSettings.size());
        for (DataLinkSettingDTO link : newLinkSettings) {
            String attachment;
            DesignDataLinkDefine linkDefine = this.designTimeViewController.initDataLink();
            dataLinkDefines.add(linkDefine);
            LinkBeanUtils.toDefine(link, linkDefine);
            if (linkDefine.getUniqueCode() == null) {
                linkDefine.setUniqueCode(OrderGenerator.newOrder());
            }
            if ((attachment = link.getAttachment()) == null) continue;
            DesignBigDataTable bigDataTable = new DesignBigDataTable();
            bigDataTable.setKey(link.getKey());
            bigDataTable.setCode("ATTACHMENT");
            bigDataTable.setData(DesignFormDefineBigDataUtil.StringToBytes((String)attachment));
            bigDataTable.setLang(LanguageType.DEFAULT.getValue());
            bigDataTables.add(bigDataTable);
        }
        try {
            this.designTimeViewController.insertDataLink(dataLinkDefines.toArray(new DesignDataLinkDefine[0]));
            this.bigDataTableDao.insert((Object[])bigDataTables.toArray(new DesignBigDataTable[0]));
        }
        catch (DBParaException e) {
            throw new LinkRuntimeException(e);
        }
    }

    private com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO convert(FilterTemplateDTO template) {
        com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO filterTemplateDTO = new com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO();
        filterTemplateDTO.setFilterTemplateID(template.getFilterTemplateID());
        filterTemplateDTO.setEntityID(template.getEntityID());
        filterTemplateDTO.setFilterTemplateTitle(template.getFilterTemplateTitle());
        filterTemplateDTO.setFilterContent(template.getFilterCondition());
        filterTemplateDTO.setOrder(template.getOrder());
        if (filterTemplateDTO.getOrder() == null) {
            filterTemplateDTO.setOrder(OrderGenerator.newOrder());
        }
        filterTemplateDTO.setUpdateTime(Instant.now());
        filterTemplateDTO.setFieldKey(template.getFieldKey());
        return filterTemplateDTO;
    }

    @Override
    public List<DataLinkDTO> listDataLink(String formKey) {
        List linkDefines = this.designTimeViewController.listDataLinkByForm(formKey);
        return linkDefines.stream().map(LinkBeanUtils::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DataLinkSettingDTO> listDataLinkSettingByForm(String formKey) {
        List designDataLinkDefines = this.designTimeViewController.listDataLinkByForm(formKey);
        ArrayList<DataLinkSettingDTO> settingDTOS = new ArrayList<DataLinkSettingDTO>();
        for (DesignDataLinkDefine designDataLinkDefine : designDataLinkDefines) {
            DataLinkSettingDTO linkSettingDTO = LinkBeanUtils.toSettingDto(designDataLinkDefine, this.bigDataTableDao);
            settingDTOS.add(linkSettingDTO);
        }
        return settingDTOS;
    }

    @Override
    public DataLinkSettingDTO getLinkSetting(String linkKey) {
        DesignDataLinkDefine dataLink = this.designTimeViewController.getDataLink(linkKey);
        if (dataLink == null) {
            return null;
        }
        return LinkBeanUtils.toSettingDto(dataLink, this.bigDataTableDao);
    }

    @Override
    public List<DataLinkDTO> listLinksByFieldKeys(List<String> fields) {
        List linkDefines = this.designTimeViewController.getReferencedDataLinkByFields(fields);
        if (linkDefines != null) {
            return linkDefines.stream().map(LinkBeanUtils::toDto).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

