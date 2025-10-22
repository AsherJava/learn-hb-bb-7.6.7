/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.data.util;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskFileDataCommonService {
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;

    public List<FieldData> getFieldDatasByRegion(String regionKey) throws Exception {
        ArrayList<FieldData> list = new ArrayList<FieldData>();
        List links = this.viewController.getAllLinksInRegion(regionKey);
        for (DataLinkDefine link : links) {
            FieldDefine field;
            if (!StringUtils.isNotEmpty((CharSequence)link.getLinkExpression()) || (field = this.dataRuntimeController.queryFieldDefine(link.getLinkExpression())) == null) continue;
            FieldData fieldData = new FieldData();
            fieldData.init(field);
            fieldData.setDataLinkKey(link.getKey());
            fieldData.setRegionKey(link.getRegionKey());
            list.add(fieldData);
        }
        return list;
    }

    public List<FieldData> getFieldDatasByRegion2(String regionKey) throws Exception {
        ArrayList<FieldData> list = new ArrayList<FieldData>();
        List links = this.viewController.getAllLinksInRegion(regionKey);
        HashMap<String, TableDefine> tableMaps = new HashMap<String, TableDefine>();
        for (DataLinkDefine link : links) {
            FieldDefine field;
            if (!StringUtils.isNotEmpty((CharSequence)link.getLinkExpression()) || (field = this.dataRuntimeController.queryFieldDefine(link.getLinkExpression())) == null) continue;
            FieldData fieldData = new FieldData();
            fieldData.init(field);
            fieldData.setDataLinkKey(link.getKey());
            fieldData.setRegionKey(link.getRegionKey());
            TableDefine table = null;
            if (tableMaps.containsKey(fieldData.getOwnerTableKey())) {
                table = (TableDefine)tableMaps.get(fieldData.getOwnerTableKey());
            } else {
                table = this.dataRuntimeController.queryTableDefine(fieldData.getOwnerTableKey());
                tableMaps.put(fieldData.getOwnerTableKey(), table);
            }
            if (table != null) {
                fieldData.setTableName(table.getCode());
            }
            list.add(fieldData);
        }
        return list;
    }

    public List<FieldData> copyFields(List<FieldData> soruces) throws Exception {
        ArrayList<FieldData> list = new ArrayList<FieldData>();
        HashMap<String, TableDefine> tableMaps = new HashMap<String, TableDefine>();
        HashMap<String, TableModelDefine> tableModes = new HashMap<String, TableModelDefine>();
        if (soruces != null) {
            for (FieldData fieldData : soruces) {
                FieldData field = new FieldData();
                this.copyFromFielData(field, fieldData);
                TableDefine table = null;
                if (StringUtils.isNotEmpty((CharSequence)fieldData.getOwnerTableKey())) {
                    if (tableMaps.containsKey(fieldData.getOwnerTableKey())) {
                        table = (TableDefine)tableMaps.get(fieldData.getOwnerTableKey());
                    } else {
                        table = this.dataRuntimeController.queryTableDefine(fieldData.getOwnerTableKey());
                        tableMaps.put(fieldData.getOwnerTableKey(), table);
                    }
                    if (table != null) {
                        field.setTableName(table.getCode());
                    } else {
                        TableModelDefine tableModel = null;
                        if (tableModes.containsKey(fieldData.getOwnerTableKey())) {
                            tableModel = (TableModelDefine)tableModes.get(fieldData.getOwnerTableKey());
                        } else {
                            tableModel = this.dataModelService.getTableModelDefineById(fieldData.getOwnerTableKey());
                            tableModes.put(fieldData.getOwnerTableKey(), tableModel);
                        }
                        if (tableModel != null) {
                            field.setTableName(tableModel.getCode());
                        }
                    }
                }
                list.add(field);
            }
        }
        return list;
    }

    public List<FieldData> copyFieldsFromLink(List<FieldData> soruces, List<LinkData> links) throws Exception {
        ArrayList<FieldData> list = new ArrayList<FieldData>();
        HashMap<String, TableDefine> tableMaps = new HashMap<String, TableDefine>();
        HashMap<String, TableModelDefine> tableModes = new HashMap<String, TableModelDefine>();
        if (soruces != null) {
            HashMap<String, FieldData> fieldIdsDic = new HashMap<String, FieldData>();
            HashMap<String, FieldData> fieldCodesDic = new HashMap<String, FieldData>();
            for (FieldData fieldData : soruces) {
                if (StringUtils.isNotEmpty((CharSequence)fieldData.getFieldKey())) {
                    fieldIdsDic.put(fieldData.getFieldKey(), fieldData);
                }
                fieldCodesDic.put(fieldData.getFieldCode(), fieldData);
            }
            for (LinkData linkData : links) {
                FieldData fieldData = null;
                if (StringUtils.isNotEmpty((CharSequence)linkData.getZbid()) && fieldIdsDic.containsKey(linkData.getZbid())) {
                    fieldData = (FieldData)fieldIdsDic.get(linkData.getZbid());
                } else if (StringUtils.isNotEmpty((CharSequence)linkData.getZbcode()) && fieldCodesDic.containsKey(linkData.getZbcode())) {
                    fieldData = (FieldData)fieldCodesDic.get(linkData.getZbcode());
                }
                FieldData field = new FieldData();
                if (fieldData != null) {
                    this.copyFromFielData(field, fieldData);
                    if (StringUtils.isEmpty((CharSequence)field.getDataLinkKey())) {
                        field.setDataLinkKey(linkData.getKey());
                        field.setRegionKey(linkData.getRegionKey());
                    }
                    if (StringUtils.isNotEmpty((CharSequence)linkData.getZbid())) {
                        DataField dataField = this.dataSchemeSevice.getDataField(linkData.getZbid());
                        if (dataField != null) {
                            field.setOwnerTableKey(dataField.getDataTableKey());
                        } else {
                            ColumnModelDefine columDefine = this.dataModelService.getColumnModelDefineByID(linkData.getZbid());
                            if (columDefine != null) {
                                field.setOwnerTableKey(columDefine.getTableID());
                            }
                        }
                    }
                    if (StringUtils.isNotEmpty((CharSequence)field.getOwnerTableKey())) {
                        TableDefine table = null;
                        if (tableMaps.containsKey(field.getOwnerTableKey())) {
                            table = (TableDefine)tableMaps.get(field.getOwnerTableKey());
                        } else {
                            table = this.dataRuntimeController.queryTableDefine(field.getOwnerTableKey());
                            tableMaps.put(field.getOwnerTableKey(), table);
                        }
                        if (table != null) {
                            field.setTableName(table.getCode());
                        } else {
                            TableModelDefine tableModel = null;
                            if (tableModes.containsKey(field.getOwnerTableKey())) {
                                tableModel = (TableModelDefine)tableModes.get(field.getOwnerTableKey());
                            } else {
                                tableModel = this.dataModelService.getTableModelDefineById(field.getOwnerTableKey());
                                tableModes.put(field.getOwnerTableKey(), tableModel);
                            }
                            if (tableModel != null) {
                                field.setTableName(tableModel.getCode());
                            }
                        }
                    }
                    if (StringUtils.isEmpty((CharSequence)field.getRegionKey())) {
                        field.setRegionKey(linkData.getRegionKey());
                    }
                } else {
                    field.setDataLinkKey(linkData.getKey());
                    field.setFieldDescription(linkData.getKey());
                    field.setRegionKey(linkData.getRegionKey());
                }
                list.add(field);
            }
        }
        return list;
    }

    private void copyFromFielData(FieldData field, FieldData fieldData) {
        field.setDataLinkKey(fieldData.getDataLinkKey());
        field.setDefaultValue(fieldData.getDefaultValue());
        field.setFieldCode(fieldData.getFieldCode());
        field.setFieldDescription(fieldData.getDataLinkKey());
        field.setFieldDescription(fieldData.getFieldDescription());
        field.setFieldGather(fieldData.getFieldGather());
        field.setFieldKey(fieldData.getFieldKey());
        field.setFieldName(fieldData.getFieldName());
        field.setFieldSize(fieldData.getFieldSize());
        field.setFieldTitle(fieldData.getFieldTitle());
        field.setFieldType(fieldData.getFieldType());
        field.setFieldValueType(fieldData.getFieldValueType());
        field.setFormKey(fieldData.getFormKey());
        field.setFormTitle(fieldData.getFormTitle());
        field.setFractionDigits(fieldData.getFractionDigits());
        field.setFullPath(fieldData.getFullPath());
        field.setMeasureUnit(fieldData.getMeasureUnit());
        field.setNullable(fieldData.getNullable());
        field.setOwnerTableKey(fieldData.getOwnerTableKey());
        field.setRegionKey(fieldData.getRegionKey());
        field.setTableName(fieldData.getTableName());
    }
}

