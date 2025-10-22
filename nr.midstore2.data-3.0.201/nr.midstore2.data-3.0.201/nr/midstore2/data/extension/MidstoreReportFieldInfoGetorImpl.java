/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreDataType
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreTableType
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreDataFieldDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreDataTableDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreDataTableGroupDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreMappingDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  com.jiuqi.nvwa.midstore.extension.IMidstoreFieldInfoGetor
 */
package nr.midstore2.data.extension;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreDataType;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreTableType;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreDataFieldDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreDataTableDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreDataTableGroupDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreMappingDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import com.jiuqi.nvwa.midstore.extension.IMidstoreFieldInfoGetor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MidstoreReportFieldInfoGetorImpl
implements IMidstoreFieldInfoGetor {
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;

    public MidstoreDataFieldDTO getDataField(String dataFieldKey) {
        DataField datafield = this.dataSchemeSevice.getDataField(dataFieldKey);
        return this.getMidstoreFieldDtoByField(datafield);
    }

    public List<MidstoreDataFieldDTO> queryDataFieldsByKey(List<String> fieldKeys) {
        ArrayList<MidstoreDataFieldDTO> list = new ArrayList<MidstoreDataFieldDTO>();
        for (String fieldKey : fieldKeys) {
            DataField datafield = this.dataSchemeSevice.getDataField(fieldKey);
            list.add(this.getMidstoreFieldDtoByField(datafield));
        }
        return list;
    }

    public List<MidstoreDataFieldDTO> queryDataFieldsByTable(String dataTableKey) {
        ArrayList<MidstoreDataFieldDTO> list = new ArrayList<MidstoreDataFieldDTO>();
        List list2 = this.dataSchemeSevice.getDataFieldByTable(dataTableKey);
        for (DataField dataField : list2) {
            list.add(this.getMidstoreFieldDtoByField(dataField));
        }
        return list;
    }

    public MidstoreDataTableDTO getDataTable(String dataTableKey) {
        DataTable dataTable = this.dataSchemeSevice.getDataTable(dataTableKey);
        return this.getMidstoreTableDtoByTable(dataTable);
    }

    public List<MidstoreDataTableDTO> queryDataTablesByKey(List<String> dataTableKeys) {
        ArrayList<MidstoreDataTableDTO> list = new ArrayList<MidstoreDataTableDTO>();
        for (String tableKey : dataTableKeys) {
            DataTable dataTable = this.dataSchemeSevice.getDataTable(tableKey);
            list.add(this.getMidstoreTableDtoByTable(dataTable));
        }
        return list;
    }

    public MidstoreMappingDTO queryMapping(String configKey) {
        MidstoreMappingDTO mapping = new MidstoreMappingDTO();
        mapping.setKey(UUID.randomUUID().toString());
        mapping.setConfigKey(configKey);
        mapping.setConfigTitle("\u6620\u5c04\u65b9\u6848\u6d4b\u8bd5");
        return mapping;
    }

    public List<MidstoreMappingDTO> listMapings() {
        ArrayList<MidstoreMappingDTO> list = new ArrayList<MidstoreMappingDTO>();
        MidstoreMappingDTO mapping = new MidstoreMappingDTO();
        mapping.setKey(UUID.randomUUID().toString());
        mapping.setConfigKey(UUID.randomUUID().toString());
        mapping.setConfigTitle("\u6620\u5c04\u65b9\u6848\u6d4b\u8bd51");
        list.add(mapping);
        MidstoreMappingDTO mapping2 = new MidstoreMappingDTO();
        mapping2.setKey(UUID.randomUUID().toString());
        mapping2.setConfigKey(UUID.randomUUID().toString());
        mapping2.setConfigTitle("\u6620\u5c04\u65b9\u6848\u6d4b\u8bd52");
        list.add(mapping2);
        return list;
    }

    public List<MidstoreDataTableDTO> getAllDataTables() {
        return new ArrayList<MidstoreDataTableDTO>();
    }

    public List<MidstoreDataTableDTO> getDataTablesBySource(MidstoreSourceDTO source) {
        ArrayList<MidstoreDataTableDTO> list = new ArrayList<MidstoreDataTableDTO>();
        ReportDataSourceDTO sourceDto = new ReportDataSourceDTO(source);
        if (StringUtils.isNotEmpty((String)sourceDto.getTaskKey())) {
            TaskDefine task = this.viewController.queryTaskDefine(sourceDto.getTaskKey());
            String dataSchemeKey = task.getDataScheme();
            List list2 = this.dataSchemeSevice.getAllDataTable(dataSchemeKey);
            for (DataTable dataTable : list2) {
                list.add(this.getMidstoreTableDtoByTable(dataTable));
            }
        }
        return list;
    }

    private MidstoreDataFieldDTO getMidstoreFieldDtoByField(DataField dataField) {
        if (dataField == null) {
            return null;
        }
        MidstoreDataFieldDTO midstoreField = new MidstoreDataFieldDTO();
        midstoreField.setDataFieldCode(dataField.getCode());
        midstoreField.setDataFieldKey(dataField.getKey());
        midstoreField.setDataFieldTitle(dataField.getTitle());
        midstoreField.setDataFieldType(this.getMidstoreDataType(dataField.getDataFieldType()));
        midstoreField.setDataTableKey(dataField.getDataTableKey());
        midstoreField.setDecimal(dataField.getDecimal());
        midstoreField.setPrecision(dataField.getPrecision());
        if (StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) {
            midstoreField.setLinkType(1);
            String baseDataCode = EntityUtils.getId((String)dataField.getRefDataEntityKey());
            midstoreField.setLinkKey(baseDataCode);
        }
        return midstoreField;
    }

    private MidstoreDataTableDTO getMidstoreTableDtoByTable(DataTable dataTable) {
        if (dataTable == null) {
            return null;
        }
        MidstoreDataTableDTO midstoreTable = new MidstoreDataTableDTO();
        midstoreTable.setBizKeys(dataTable.getBizKeyFieldsID());
        midstoreTable.setDataTableCode(dataTable.getCode());
        midstoreTable.setDataTableKey(dataTable.getKey());
        midstoreTable.setDataTableTitle(dataTable.getTitle());
        midstoreTable.setDataTableType(this.getMidstoreTableType(dataTable.getDataTableType()));
        midstoreTable.setGroupKey(dataTable.getDataGroupKey());
        if (dataTable.getRepeatCode() == null) {
            midstoreTable.setRepeatCode(true);
        } else {
            midstoreTable.setRepeatCode(dataTable.getRepeatCode().booleanValue());
        }
        return midstoreTable;
    }

    private MidstoreTableType getMidstoreTableType(DataTableType tableType) {
        MidstoreTableType type = MidstoreTableType.TABLE;
        if (tableType == DataTableType.TABLE) {
            type = MidstoreTableType.TABLE;
        } else if (tableType == DataTableType.DETAIL) {
            type = MidstoreTableType.DETAIL;
        } else if (tableType == DataTableType.DETAIL) {
            type = MidstoreTableType.DETAIL;
        } else if (tableType == DataTableType.ACCOUNT) {
            type = MidstoreTableType.ACCOUNT;
        } else if (tableType == DataTableType.MULTI_DIM) {
            type = MidstoreTableType.MULTI_DIM;
        } else if (tableType == DataTableType.SUB_TABLE) {
            type = MidstoreTableType.SUB_TABLE;
        }
        return type;
    }

    private MidstoreDataType getMidstoreDataType(DataFieldType fieldType) {
        MidstoreDataType type = MidstoreDataType.STRING;
        if (fieldType == DataFieldType.STRING) {
            type = MidstoreDataType.STRING;
        } else if (fieldType == DataFieldType.BIGDECIMAL) {
            type = MidstoreDataType.BIGDECIMAL;
        } else if (fieldType == DataFieldType.BOOLEAN) {
            type = MidstoreDataType.BOOLEAN;
        } else if (fieldType == DataFieldType.CLOB) {
            type = MidstoreDataType.CLOB;
        } else if (fieldType == DataFieldType.DATE) {
            type = MidstoreDataType.DATE;
        } else if (fieldType == DataFieldType.DATE_TIME) {
            type = MidstoreDataType.DATE_TIME;
        } else if (fieldType == DataFieldType.FILE) {
            type = MidstoreDataType.FILE;
        } else if (fieldType == DataFieldType.INTEGER) {
            type = MidstoreDataType.INTEGER;
        } else if (fieldType == DataFieldType.PICTURE) {
            type = MidstoreDataType.PICTURE;
        } else if (fieldType == DataFieldType.UUID) {
            type = MidstoreDataType.UUID;
        }
        return type;
    }

    public List<MidstoreDataTableGroupDTO> getDataTableGroups(MidstoreSourceDTO source) {
        ArrayList<MidstoreDataTableGroupDTO> list = new ArrayList<MidstoreDataTableGroupDTO>();
        ReportDataSourceDTO sourceDto = new ReportDataSourceDTO(source);
        if (StringUtils.isNotEmpty((String)sourceDto.getTaskKey())) {
            TaskDefine task = this.viewController.queryTaskDefine(sourceDto.getTaskKey());
            String dataSchemeKey = task.getDataScheme();
            List list2 = this.dataSchemeSevice.getAllDataGroup(dataSchemeKey);
            for (DataGroup dataGroup : list2) {
                MidstoreDataTableGroupDTO newGroup = new MidstoreDataTableGroupDTO();
                newGroup.setGroupKey(dataGroup.getKey());
                newGroup.setGroupTitle(dataGroup.getTitle());
                newGroup.setParentGroupKey(dataGroup.getParentKey());
                list.add(newGroup);
            }
        }
        return list;
    }

    public MidstoreDataTableGroupDTO getDataTableGroup(String dataTableGroupKey) {
        MidstoreDataTableGroupDTO newGroup = null;
        if (StringUtils.isNotEmpty((String)dataTableGroupKey)) {
            DataGroup dataGroup = this.dataSchemeSevice.getDataGroup(dataTableGroupKey);
            newGroup = new MidstoreDataTableGroupDTO();
            newGroup.setGroupKey(dataGroup.getKey());
            newGroup.setGroupTitle(dataGroup.getTitle());
            newGroup.setParentGroupKey(dataGroup.getParentKey());
        }
        return newGroup;
    }

    public List<MidstoreDataTableGroupDTO> queryDataTableGroupsByKey(List<String> dataTableGroupKeys) {
        ArrayList<MidstoreDataTableGroupDTO> list = new ArrayList<MidstoreDataTableGroupDTO>();
        if (dataTableGroupKeys != null && !dataTableGroupKeys.isEmpty()) {
            List list2 = this.dataSchemeSevice.getDataGroups(dataTableGroupKeys);
            for (DataGroup dataGroup : list2) {
                MidstoreDataTableGroupDTO newGroup = new MidstoreDataTableGroupDTO();
                newGroup.setGroupKey(dataGroup.getKey());
                newGroup.setGroupTitle(dataGroup.getTitle());
                newGroup.setParentGroupKey(dataGroup.getParentKey());
                list.add(newGroup);
            }
        }
        return list;
    }
}

