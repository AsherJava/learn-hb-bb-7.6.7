/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.intf.data.DataFieldType
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataPluginCheck
implements PluginCheck {
    @Autowired
    private DataModelClient dataModelClient;

    public String getName() {
        return "data";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return DataDefineImpl.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultDTO pluginCheckResultDTO;
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName(this.getName());
        ArrayList<PluginCheckResultDTO> checkResults = new ArrayList<PluginCheckResultDTO>();
        DataDefineImpl dataDefine = (DataDefineImpl)pluginDefine;
        List tableList = dataDefine.getTableList();
        HashSet<String> tableTitles = new HashSet<String>();
        boolean hasMasterTable = false;
        ArrayList<DataModelDO> dataModelDOList = new ArrayList<DataModelDO>();
        for (int i = 0; i < tableList.size(); ++i) {
            String tableTitle;
            DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)tableList.get(i);
            UUID parentId = dataTableDefine.getParentId();
            if (parentId == null) {
                hasMasterTable = true;
            }
            String tableName = dataTableDefine.getTableName();
            DataTableType tableType = dataTableDefine.getTableType();
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setBiztype(DataModelType.BizType.BILL);
            dataModelDTO.setName(tableName);
            DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
            if (tableType == DataTableType.DATA || tableType == DataTableType.REFER) {
                if (dataModelDO == null) {
                    pluginCheckResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8868\u5728\u6570\u636e\u5efa\u6a21\u5fc5\u987b\u5b58\u5728\uff1a" + tableName, tableName);
                    checkResults.add(pluginCheckResultDTO);
                    continue;
                }
                if (Objects.nonNull(parentId)) {
                    dataModelDOList.add(dataModelDO);
                }
            }
            if (parentId == null && dataTableDefine.getFields().size() < 1) {
                pluginCheckResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u4e3b\u8868\u5b57\u6bb5\u5fc5\u9009\uff1a" + tableName, tableName);
                checkResults.add(pluginCheckResultDTO);
            }
            if (!StringUtils.hasText(tableTitle = dataTableDefine.getTitle())) {
                pluginCheckResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8868\u5b9a\u4e49\u7b2c[" + (i + 1) + "]\u884c\u8868\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff1a" + tableName, tableName);
                checkResults.add(pluginCheckResultDTO);
            } else if (!tableTitles.add(tableTitle)) {
                pluginCheckResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u8868\u5b9a\u4e49\u7b2c[" + (i + 1) + "]\u884c\u8868\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff1a" + tableTitle, tableName);
                checkResults.add(pluginCheckResultDTO);
            }
            HashSet fieldTitles = new HashSet();
            List fieldList = dataModelDO.getColumns().stream().map(DataModelColumn::getColumnName).collect(Collectors.toList());
            dataTableDefine.getFields().forEachIndex((integer, field) -> {
                DataFieldType fieldType = field.getFieldType();
                if (DataFieldType.CALC != fieldType && !fieldList.contains(field.getFieldName())) {
                    PluginCheckResultDTO pluginCheckDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, tableName + "\uff1a\u7b2c[" + (integer + 1) + "]\u884c\u5b57\u6bb5\u5728\u6570\u636e\u5efa\u6a21\u5fc5\u987b\u5b58\u5728\uff1a" + field.getName(), tableName + "-" + field.getName());
                    checkResults.add(pluginCheckDTO);
                    return;
                }
                String fieldTitle = field.getTitle();
                if (!StringUtils.hasText(fieldTitle)) {
                    PluginCheckResultDTO pluginCheckDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, tableName + "\uff1a\u7b2c[" + (integer + 1) + "]\u884c\u5b57\u6bb5\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff1a" + field.getName(), tableName + "-" + field.getName());
                    checkResults.add(pluginCheckDTO);
                } else if (!fieldTitles.add(fieldTitle)) {
                    PluginCheckResultDTO pluginCheckDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, tableName + "\uff1a\u7b2c[" + (integer + 1) + "]\u884c\u5b57\u6bb5\u540d\u79f0\u91cd\u590d\uff1a" + fieldTitle, tableName + "-" + field.getName());
                    checkResults.add(pluginCheckDTO);
                }
            });
        }
        if (!hasMasterTable) {
            pluginCheckResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5355\u636e\u4e3b\u8868\u5fc5\u586b", "");
            checkResults.add(pluginCheckResultDTO);
        }
        this.checkChildTableGroupId(tableList, dataModelDOList, checkResults);
        pluginCheckResultVO.setCheckResults(checkResults);
        return pluginCheckResultVO;
    }

    private void checkChildTableGroupId(List<DataTableDefineImpl> tableList, List<DataModelDO> dataModelDOList, List<PluginCheckResultDTO> checkResults) {
        if (CollectionUtils.isEmpty(tableList) || CollectionUtils.isEmpty(dataModelDOList)) {
            return;
        }
        String checkColumnName = "GROUPID";
        List childTables = tableList.stream().filter(x -> Objects.nonNull(x.getParentId())).collect(Collectors.toList());
        Set masterTableIds = tableList.stream().filter(x -> Objects.isNull(x.getParentId())).map(DataTableDefineImpl::getId).collect(Collectors.toSet());
        for (DataTableDefineImpl dataTableDefine : childTables) {
            UUID parentId = dataTableDefine.getParentId();
            String name = dataTableDefine.getTableName();
            if (masterTableIds.contains(parentId)) continue;
            boolean exist = true;
            for (DataModelDO dataModelDO : dataModelDOList) {
                if (!name.equalsIgnoreCase(dataModelDO.getName())) continue;
                List normalData = dataModelDO.getColumns().stream().filter(x -> "GROUPID".equalsIgnoreCase(x.getColumnName())).collect(Collectors.toList());
                exist = !CollectionUtils.isEmpty(normalData);
                break;
            }
            String tableName = null;
            for (DataTableDefineImpl childTable : childTables) {
                if (!childTable.getId().equals(parentId)) continue;
                tableName = childTable.getName();
                break;
            }
            if (exist || !Objects.nonNull(tableName)) continue;
            checkResults.add(this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5b50\u8868 " + tableName + " \u9009\u62e9\u7684\u5b50\u8868" + name + "\u6ca1\u6709 GROUPID \u5b57\u6bb5\uff01", null));
        }
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }
}

