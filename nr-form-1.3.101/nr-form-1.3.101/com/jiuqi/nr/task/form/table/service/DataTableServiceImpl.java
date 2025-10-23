/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.form.table.service;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.AbstractState;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.service.AbstractDataSchemeSupport;
import com.jiuqi.nr.task.form.table.IDataTableService;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nr.task.form.util.ReverseModeUtils;
import com.jiuqi.nr.task.form.util.TableBeanUtils;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class DataTableServiceImpl
extends AbstractDataSchemeSupport
implements IDataTableService {
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public DataTableServiceImpl(IDesignTimeViewController designTimeViewController, IDesignDataSchemeService designDataSchemeService) {
        super(designTimeViewController, designDataSchemeService);
    }

    @Override
    public List<DataTableDTO> listDataTablesByForm(String formKey) {
        DataScheme dataScheme = this.getDataScheme(formKey);
        if (dataScheme != null) {
            List dataTables = this.getDesignDataSchemeService().getDataTableByScheme(dataScheme.getKey());
            return dataTables.stream().map(TableBeanUtils::toDto).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<DataTableDTO> listDataTables(List<String> keys) {
        List dataTables = this.getDesignDataSchemeService().getDataTables(keys);
        ArrayList<DataTableDTO> dataTableDTOList = new ArrayList<DataTableDTO>(dataTables.size());
        for (DesignDataTable dataTable : dataTables) {
            DataTableDTO dataTableDTO = TableBeanUtils.toDto(dataTable);
            dataTableDTO.setHasData(this.runtimeDataSchemeService.dataTableCheckData(new String[]{dataTable.getKey()}));
            dataTableDTOList.add(dataTableDTO);
        }
        return dataTableDTOList;
    }

    @Override
    public DataTableDTO getTable(String key) {
        DesignDataTable dataTable = this.getDesignDataSchemeService().getDataTable(key);
        if (dataTable != null) {
            DataTableDTO dataTableDTO = TableBeanUtils.toDto(dataTable);
            dataTableDTO.setHasData(this.runtimeDataSchemeService.dataTableCheckData(new String[]{key}));
            return dataTableDTO;
        }
        return null;
    }

    @Override
    public void saveTableSetting(List<DataTableDTO> tableSetting) {
        if (tableSetting != null) {
            List<DataTableDTO> updates;
            Map<Constants.DataStatus, List<DataTableDTO>> tableMap = tableSetting.stream().collect(Collectors.groupingBy(AbstractState::getStatus));
            List<DataTableDTO> newTables = tableMap.get(Constants.DataStatus.NEW);
            if (!CollectionUtils.isEmpty(newTables)) {
                // empty if block
            }
            if (!CollectionUtils.isEmpty(updates = tableMap.get(Constants.DataStatus.MODIFY))) {
                this.updateTables(updates);
            }
        }
    }

    private void updateTables(List<DataTableDTO> updates) {
        IDesignDataSchemeService designDataSchemeService = this.getDesignDataSchemeService();
        Map<String, DataTableDTO> tableMap = updates.stream().collect(Collectors.toMap(DataCore::getKey, f -> f));
        List dataTables = designDataSchemeService.getDataTables(new ArrayList<String>(tableMap.keySet()));
        List<DesignDataField> dataFields = new ArrayList();
        for (DesignDataTable dataTable : dataTables) {
            DataTableDTO dataTableDTO = tableMap.get(dataTable.getKey());
            dataTable.setDataTableGatherType(DataTableGatherType.valueOf((int)dataTableDTO.getGatherType()));
            dataTable.setRepeatCode(dataTableDTO.getRepeatCode());
            String[] bizKeys = dataTableDTO.getBizKeys();
            if (bizKeys != null) {
                dataFields = designDataSchemeService.getDataFields(Arrays.asList(bizKeys));
            }
            List oldFields = designDataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
            Iterator iterator = dataFields.iterator();
            while (iterator.hasNext()) {
                DesignDataField next = (DesignDataField)iterator.next();
                next.setUpdateTime(null);
                oldFields.removeIf(x -> x.getKey().equals(next.getKey()));
                if (next.getDataFieldKind().equals((Object)DataFieldKind.FIELD)) {
                    next.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                    continue;
                }
                iterator.remove();
            }
            if (!oldFields.isEmpty()) {
                for (DesignDataField oldValue : oldFields) {
                    oldValue.setDataFieldKind(DataFieldKind.FIELD);
                    oldValue.setUpdateTime(null);
                    dataFields.add(oldValue);
                    this.fieldValidator.levelCheckField(oldValue);
                }
            }
            dataTable.setUpdateTime(Instant.now());
        }
        designDataSchemeService.updateDataTables(dataTables);
        designDataSchemeService.updateDataFields(dataFields);
    }

    private void insertTables(List<DataTableDTO> newTables) {
        IDesignDataSchemeService designDataSchemeService = this.getDesignDataSchemeService();
        designDataSchemeService.insertDataTables(newTables.stream().map(x -> {
            DesignDataTable designDataTable = designDataSchemeService.initDataTable();
            TableBeanUtils.copyProperty(x, designDataTable);
            designDataTable.setKey(x.getKey());
            return designDataTable;
        }).collect(Collectors.toList()));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void insertReverseModeTables(List<DataTableDTO> newTables) {
        if (CollectionUtils.isEmpty(newTables)) {
            return;
        }
        IDesignDataSchemeService designDataSchemeService = this.getDesignDataSchemeService();
        String dataSchemeKey = newTables.get(0).getDataSchemeKey();
        List groups = designDataSchemeService.getAllDataGroup(dataSchemeKey);
        DesignDataGroup group = null;
        ArrayList<DesignDataGroup> groupList = new ArrayList<DesignDataGroup>();
        ArrayList<DesignDataTable> tableList = new ArrayList<DesignDataTable>(newTables.size());
        int level = 0;
        for (DataTableDTO tableDTO : newTables) {
            DesignDataTable designDataTable = designDataSchemeService.initDataTable();
            TableBeanUtils.copyProperty(tableDTO, designDataTable);
            designDataTable.setKey(tableDTO.getKey());
            ReverseModeUtils.GroupBuilder groupBuilder = ReverseModeUtils.createGroupBuilder(tableDTO.getDataGroupKey());
            if (!groupBuilder.needCreateGroup()) continue;
            group = this.findGroup(dataSchemeKey, groups, groupBuilder, groupList, null, level);
            designDataTable.setDataGroupKey(group.getKey());
            tableList.add(designDataTable);
        }
        if (!tableList.isEmpty()) {
            designDataSchemeService.insertDataTables(tableList);
        }
        if (!groupList.isEmpty()) {
            designDataSchemeService.insertDataGroups(groupList);
        }
    }

    private DesignDataGroup findGroup(String dataSchemeKey, List<DesignDataGroup> groups, ReverseModeUtils.GroupBuilder groupBuilder, List<DesignDataGroup> groupList, String parent, int level) {
        DesignDataGroup group;
        if (level == 3) {
            Optional<DesignDataGroup> first = groups.stream().filter(g -> Objects.equals(g.getKey(), parent)).findFirst();
            return first.orElse(null);
        }
        String title = groupBuilder.getTitle(level);
        Optional<DesignDataGroup> first = groups.stream().filter(g -> Objects.equals(g.getParentKey(), parent) && Objects.equals(g.getTitle(), title)).findFirst();
        if (first.isPresent()) {
            group = first.get();
        } else {
            group = this.createGroup(dataSchemeKey, parent, title);
            groupList.add(group);
            groups.add(group);
        }
        return this.findGroup(dataSchemeKey, groups, groupBuilder, groupList, group.getKey(), ++level);
    }

    private DesignDataGroup createGroup(String dataSchemeKey, String parentKey, String title) {
        DesignDataGroup firstGroup = this.getDesignDataSchemeService().initDataGroup();
        firstGroup.setDataSchemeKey(dataSchemeKey);
        firstGroup.setDataGroupKind(DataGroupKind.TABLE_GROUP);
        firstGroup.setTitle(title);
        firstGroup.setOrder(OrderGenerator.newOrder());
        firstGroup.setUpdateTime(Instant.now());
        firstGroup.setParentKey(parentKey);
        return firstGroup;
    }
}

