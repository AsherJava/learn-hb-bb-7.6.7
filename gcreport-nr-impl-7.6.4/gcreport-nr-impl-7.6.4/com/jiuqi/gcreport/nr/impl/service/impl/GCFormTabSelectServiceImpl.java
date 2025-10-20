/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.dataentry.tree.FormTreeItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCFormTabSelectServiceImpl
implements GCFormTabSelectService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;

    @Override
    public boolean isFormCondition(JtableContext jtableContext, String condition, DimensionValueSet dimensionValueSet) {
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)condition)) {
            return true;
        }
        boolean evaluat = true;
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)SpringContextUtils.getBean(IJtableDataEngineService.class);
        AbstractData expressionEvaluat = jtableDataEngineService.expressionEvaluat(condition, jtableContext, dimensionValueSet);
        if (expressionEvaluat != null && expressionEvaluat instanceof BoolData) {
            try {
                evaluat = expressionEvaluat.getAsBool();
            }
            catch (DataTypeException e) {
                e.printStackTrace();
            }
        }
        return evaluat;
    }

    @Override
    public List<FormTreeVo> queryFormTree(String formSchemeKey, String dataTime) throws Exception {
        ArrayList<FormTreeVo> formTreeVos = new ArrayList<FormTreeVo>();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
        if (dataTime != null) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName("DATATIME");
            dimensionValue.setValue(dataTime);
            dimensionSetMap.put("DATATIME", dimensionValue);
            dimensionValueSet.setValue("DATATIME", (Object)dataTime);
        }
        jtableContext.setDimensionSet(dimensionSetMap);
        List formGroupDefines = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        if (dataTime != null) {
            formGroupDefines = formGroupDefines.stream().filter(define -> this.isFormCondition(jtableContext, define.getCondition(), dimensionValueSet)).collect(Collectors.toList());
        }
        for (FormGroupDefine formGroupDefine : formGroupDefines) {
            List formDefines = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
            if (dataTime != null) {
                formDefines = formDefines.stream().filter(define -> this.isFormCondition(jtableContext, define.getFormCondition(), dimensionValueSet)).collect(Collectors.toList());
            }
            if (formDefines.size() == 0) continue;
            FormTreeVo formTreeVo = this.convertToFormTreeVo(formGroupDefine);
            List formDefineVos = formDefines.stream().map(define -> this.convertToFormTreeVo((FormDefine)define)).collect(Collectors.toList());
            formTreeVo.setChildren(formDefineVos);
            formTreeVos.add(formTreeVo);
        }
        return formTreeVos;
    }

    @Override
    public String queryFormData(String formKey, Consumer<GridCellData> consumer) {
        try {
            Grid2Data griddata = this.getGridDataByRunTime(formKey);
            this.fillZbCode(formKey, griddata, consumer);
            String result = this.serialize(griddata);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String serialize(Grid2Data griddata) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)griddata);
    }

    private void fillZbCode(String formKey, Grid2Data gridData, Consumer<GridCellData> consumer) throws Exception {
        this.fillZbCode(formKey, gridData, consumer, null, null);
    }

    private void fillZbCode(String formKey, Grid2Data gridData, Consumer<GridCellData> consumer, Map<String, String> mutiCriteridMap, Map<String, Object> zbAttrsMap) throws Exception {
        List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            String regionKey = region.getKey();
            List dataLinks = this.runTimeViewController.getAllLinksInRegion(regionKey);
            for (DataLinkDefine link : dataLinks) {
                GridCellData cellData;
                FieldDefine fieldDefine = this.runtimeController.queryFieldDefine(link.getLinkExpression());
                String tableCode = "";
                String fieldCode = "\u672a\u77e5";
                boolean isNumberField = true;
                if (fieldDefine != null) {
                    TableDefine tableDefine = this.runtimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                    if (tableDefine != null) {
                        tableCode = tableDefine.getCode();
                    }
                    fieldCode = fieldDefine.getCode();
                    FieldType fieldType = fieldDefine.getType();
                    if (fieldType != FieldType.FIELD_TYPE_FLOAT && fieldType != FieldType.FIELD_TYPE_INTEGER && fieldType != FieldType.FIELD_TYPE_DECIMAL) {
                        isNumberField = false;
                    }
                }
                if ((cellData = gridData.getGridCellData(link.getPosX(), link.getPosY())) == null) continue;
                cellData.setShowText(tableCode + "[" + fieldCode + "]");
                cellData.setHorzAlign(3);
                cellData.setForeGroundColor(255);
                if (null != consumer) {
                    consumer.accept(cellData);
                }
                cellData.setEditable(true);
                if (isNumberField) {
                    cellData.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                }
                if (mutiCriteridMap != null) {
                    cellData.setShowText(null);
                    cellData.setEditText(tableCode + "[" + fieldCode + "]");
                    if (mutiCriteridMap.get(fieldDefine.getKey()) == null) continue;
                    cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
                    cellData.setShowText(mutiCriteridMap.get(fieldDefine.getKey()));
                    continue;
                }
                if (zbAttrsMap == null) continue;
                cellData.setShowText(null);
                cellData.setEditText(tableCode + "[" + fieldCode + "];" + link.getKey());
                if (!zbAttrsMap.containsKey(link.getKey())) continue;
                cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
            }
        }
        if (null == gridData) {
            gridData = new Grid2Data();
            gridData.insertRows(0, 1, -1);
            gridData.insertColumns(0, 1);
            gridData.setRowHidden(0, true);
            gridData.setColumnHidden(0, true);
        }
    }

    private FormTreeVo convertToFormTreeVo(FormGroupDefine formGroupDefine) {
        FormTreeVo formTreeVo = new FormTreeVo();
        formTreeVo.setCode(formGroupDefine.getCode());
        formTreeVo.setId(formGroupDefine.getKey());
        formTreeVo.setTitle(formGroupDefine.getTitle());
        return formTreeVo;
    }

    private FormTreeVo convertToFormTreeVo(FormDefine formDefine) {
        FormTreeVo formTreeVo = new FormTreeVo();
        formTreeVo.setCode(formDefine.getFormCode());
        formTreeVo.setId(formDefine.getKey());
        formTreeVo.setTitle(formDefine.getTitle());
        formTreeVo.setSerialNumber(formDefine.getSerialNumber());
        return formTreeVo;
    }

    private Grid2Data getGridDataByRunTime(String formKey) {
        BigDataDefine dataDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        Grid2Data gridData = null;
        if (null != dataDefine) {
            if (dataDefine.getData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])dataDefine.getData());
            } else {
                gridData = new Grid2Data();
                gridData.setRowCount(10);
                gridData.setColumnCount(10);
            }
        }
        return gridData;
    }

    @Override
    public FormTree getFormTree(String formSchemeKey, String dataTime) throws Exception {
        List rootFormGroups = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        rootItem.setKey(UUIDUtils.emptyUUIDStr());
        rootItem.setTitle(GcI18nUtil.getMessage((String)"gc.nr.form.root"));
        Tree tree = new Tree((Object)rootItem);
        if (StringUtils.isEmpty((String)dataTime)) {
            this.addChildren((Tree<FormTreeItem>)tree, rootFormGroups);
        } else {
            this.addChildren((Tree<FormTreeItem>)tree, rootFormGroups, formSchemeKey, dataTime);
        }
        this.removeNoChildrenGroup(tree.getChildren());
        FormTree formTree = new FormTree();
        formTree.setTree(tree);
        return formTree;
    }

    private void removeNoChildrenGroup(List<Tree<FormTreeItem>> childrens) {
        for (int i = childrens.size() - 1; i >= 0; --i) {
            Tree<FormTreeItem> item = childrens.get(i);
            if (item == null || !"group".equals(((FormTreeItem)item.getData()).getType())) continue;
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                this.removeNoChildrenGroup(item.getChildren());
            }
            if (item.getChildren() != null && item.getChildren().size() != 0) continue;
            childrens.remove(i);
        }
    }

    private Tree<FormTreeItem> addChildren(Tree<FormTreeItem> node, List<FormGroupDefine> formGroupList) throws Exception {
        for (FormGroupDefine formGroup : formGroupList) {
            List formGroupDefines;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroup.getKey());
            groupItem.setCode(formGroup.getCode());
            groupItem.setTitle(formGroup.getCode() + '|' + formGroup.getTitle());
            groupItem.setType("group");
            Tree child = node.addChild((Object)groupItem);
            List formDefines = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            if (null != formDefines) {
                for (FormDefine form : formDefines) {
                    FormTreeItem reportItem = new FormTreeItem();
                    reportItem.setKey(form.getKey());
                    reportItem.setCode(form.getFormCode());
                    reportItem.setTitle(form.getFormCode() + '|' + form.getTitle());
                    reportItem.setSerialNumber(form.getSerialNumber());
                    reportItem.setType("form");
                    reportItem.setGroupKey(formGroup.getKey());
                    child.addChild((Object)reportItem);
                }
            }
            if (CollectionUtils.isEmpty((Collection)(formGroupDefines = this.runTimeViewController.getChildFormGroups(formGroup.getKey())))) continue;
            this.addChildren((Tree<FormTreeItem>)child, formGroupDefines);
        }
        return node;
    }

    private Tree<FormTreeItem> addChildren(Tree<FormTreeItem> node, List<FormGroupDefine> formGroupList, String formSchemeKey, String dataTime) throws Exception {
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(dataTime);
        dimensionSetMap.put("DATATIME", dimensionValue);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)dataTime);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setDimensionSet(dimensionSetMap);
        formGroupList = formGroupList.stream().filter(define -> this.isFormCondition(jtableContext, define.getCondition(), dimensionValueSet)).collect(Collectors.toList());
        for (FormGroupDefine formGroupDefine : formGroupList) {
            List formDefines = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
            if ((formDefines = formDefines.stream().filter(define -> this.isFormCondition(jtableContext, define.getFormCondition(), dimensionValueSet)).collect(Collectors.toList())).size() == 0) continue;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroupDefine.getKey());
            groupItem.setCode(formGroupDefine.getCode());
            groupItem.setTitle(formGroupDefine.getCode() + '|' + formGroupDefine.getTitle());
            groupItem.setType("group");
            Tree child = node.addChild((Object)groupItem);
            for (FormDefine form : formDefines) {
                FormTreeItem reportItem = new FormTreeItem();
                reportItem.setKey(form.getKey());
                reportItem.setCode(form.getFormCode());
                reportItem.setTitle(form.getFormCode() + '|' + form.getTitle());
                reportItem.setSerialNumber(form.getSerialNumber());
                reportItem.setType("form");
                reportItem.setGroupKey(formGroupDefine.getKey());
                child.addChild((Object)reportItem);
            }
            List formGroupDefines = this.runTimeViewController.getChildFormGroups(formGroupDefine.getKey());
            if (CollectionUtils.isEmpty((Collection)formGroupDefines)) continue;
            this.addChildren((Tree<FormTreeItem>)child, formGroupDefines, formSchemeKey, dataTime);
        }
        return node;
    }
}

