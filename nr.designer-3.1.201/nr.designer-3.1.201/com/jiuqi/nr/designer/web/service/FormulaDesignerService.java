/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.editor.EditorParam
 *  com.jiuqi.nr.definition.editor.LinkData
 *  com.jiuqi.nr.definition.editor.Service.EditorStyleCustom
 *  com.jiuqi.nr.definition.editor.StyleData
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.designer.web.service;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.editor.EditorParam;
import com.jiuqi.nr.definition.editor.LinkData;
import com.jiuqi.nr.definition.editor.Service.EditorStyleCustom;
import com.jiuqi.nr.definition.editor.StyleData;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.designer.bean.BaseDataImpl;
import com.jiuqi.nr.designer.bean.IBaseData;
import com.jiuqi.nr.designer.service.ExtentStyleService;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormTree;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormTreeItem;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignLinksData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.util.StringUtils;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FormulaDesignerService {
    private static final Logger log = LoggerFactory.getLogger(FormulaDesignerService.class);
    @Autowired
    private IRunTimeViewController nrRunTimeController;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private ExtentStyleService extentStyleService;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    @Qualifier(value="DataDefinitionDesignTimeController2")
    private DataDefinitionDesignTimeController2 npDesignTimeController;
    @Autowired
    @Qualifier(value="DataDefinitionRuntimeController2")
    private DataDefinitionRuntimeController2 dataDefinitionRuntimeController2;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired(required=false)
    private List<EditorStyleCustom> editorStyleCustom;
    public static int TEXT_BLUE = 0xFFCCFF;

    public FormTree getGroupAndFroms(String schemeKey) throws JQException {
        FormTree formTree = new FormTree();
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        Tree tree = new Tree((Object)rootItem);
        List groups = this.nrDesignTimeController.queryAllGroupsByFormScheme(schemeKey);
        for (DesignFormGroupDefine group : groups) {
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(group.getKey().toString());
            groupItem.setCode(group.getCode());
            groupItem.setTitle(group.getTitle());
            groupItem.setType("group");
            Tree child = new Tree((Object)groupItem);
            List forms = this.nrDesignTimeController.getAllFormsInGroup(group.getKey(), true);
            for (DesignFormDefine form : forms) {
                if (form.getFormType().getValue() == FormType.FORM_TYPE_ANALYSISREPORT.getValue()) continue;
                FormTreeItem item = new FormTreeItem();
                item.setKey(form.getKey().toString());
                item.setCode(form.getFormCode());
                item.setTitle(form.getTitle());
                item.setOrder(form.getOrder());
                item.setSerialNumber(form.getSerialNumber());
                item.setType(form.getFormType().getValue() + "");
                if (form.getFormType().getValue() == FormType.FORM_TYPE_FLOAT.getValue()) {
                    item.setIcons("#icon-16_SHU_A_NR_fudongbiao1");
                } else if (form.getFormType().getValue() == FormType.FORM_TYPE_SURVEY.getValue()) {
                    item.setIcons("#icon-16_SHU_A_NR_wenjuan");
                } else if (form.getFormType().getValue() == FormType.FORM_TYPE_ACCOUNT.getValue()) {
                    item.setIcons("#icon-16_SHU_A_NR_taizhangbiao");
                } else if (form.getFormType().getValue() == FormType.FORM_TYPE_NEWFMDM.getValue()) {
                    item.setIcons("#icon-16_SHU_A_NR_fengmiandaimabiao");
                } else {
                    item.setIcons("#icon-_GJZshuxingzhongji");
                }
                child.addChild((Object)item);
            }
            if (null == child.getChildren() || child.getChildren().size() == 0) continue;
            tree.addChild(child);
        }
        formTree.setTree((Tree<FormTreeItem>)tree);
        return formTree;
    }

    public FormTree getGroupAndFromsByRunTime(String schemeKey) throws Exception {
        FormTree formTree = new FormTree();
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        Tree tree = new Tree((Object)rootItem);
        List groups = this.nrRunTimeController.getAllFormGroupsInFormScheme(schemeKey);
        for (FormGroupDefine group : groups) {
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(group.getKey().toString());
            groupItem.setCode(group.getCode());
            groupItem.setTitle(group.getTitle());
            groupItem.setType("group");
            Tree child = new Tree((Object)groupItem);
            List forms = this.nrRunTimeController.getAllFormsInGroup(group.getKey());
            for (FormDefine form : forms) {
                if (form.getFormType().getValue() == FormType.FORM_TYPE_ANALYSISREPORT.getValue()) continue;
                FormTreeItem item = new FormTreeItem();
                item.setKey(form.getKey().toString());
                item.setCode(form.getFormCode());
                item.setTitle(form.getTitle());
                item.setOrder(form.getOrder());
                item.setSerialNumber(form.getSerialNumber());
                item.setType(form.getFormType().getValue() + "");
                if (form.getFormType().getValue() == FormType.FORM_TYPE_FLOAT.getValue()) {
                    item.setIcons("#icon-16_SHU_A_NR_fudongbiao1");
                } else if (form.getFormType().getValue() == FormType.FORM_TYPE_SURVEY.getValue()) {
                    item.setIcons("#icon-16_SHU_A_NR_wenjuan");
                } else {
                    item.setIcons("#icon-_GJZshuxingzhongji");
                }
                child.addChild((Object)item);
            }
            if (null == child.getChildren() || child.getChildren().size() == 0) continue;
            tree.addChild(child);
        }
        formTree.setTree((Tree<FormTreeItem>)tree);
        return formTree;
    }

    public FormulaDesignFormData getFormData(String formKey, int viewType, String regKey) {
        FormulaDesignFormData formData = new FormulaDesignFormData();
        try {
            DesignRegionSettingDefine designRegionSettingDefine;
            String linkExtprtion;
            formData.setFormKey(formKey);
            List regions = this.nrDesignTimeController.getAllRegionsInForm(formKey);
            ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
            HashSet<String> regionKeySet = new HashSet<String>();
            Grid2Data griddata = this.getGridData(formKey);
            DataRegionDefine localRegion = null;
            ArrayList<FormulaDesignLinksData> links = new ArrayList<FormulaDesignLinksData>();
            for (DataRegionDefine region : regions) {
                String regionKey = region.getKey();
                if (regionKey.equals(regKey)) {
                    localRegion = region;
                }
                if (!regionKeySet.add(regionKey)) continue;
                if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && region.getRegionBottom() - region.getRegionTop() == 0) {
                    floatRegions.add(region);
                    griddata.getGridCellData(0, region.getRegionTop()).setShowText("+" + region.getRegionTop());
                    griddata.getGridCellData(0, region.getRegionTop()).setBackGroundColor(TEXT_BLUE);
                    griddata.getGridCellData(0, region.getRegionTop()).setBottomBorderStyle(4);
                    griddata.getGridCellData(0, region.getRegionTop()).setRightBorderStyle(4);
                }
                List dataLinks = this.nrDesignTimeController.getAllLinksInRegion(regionKey);
                List<DesignTableDefine> desTableDefineList = this.getAllDesignTable(dataLinks, new HashMap<String, FieldDefine>());
                for (DataLinkDefine link : dataLinks) {
                    FormulaDesignLinksData linkData = new FormulaDesignLinksData();
                    linkData.setCol(link.getColNum());
                    linkData.setRow(link.getRowNum());
                    linkData.setX(link.getPosX());
                    linkData.setY(link.getPosY());
                    linkData.setLinkExpression(link.getLinkExpression());
                    linkData.setLinkType(link.getType().getValue());
                    DesignFieldDefine fieldDefine = null;
                    if (StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                        fieldDefine = this.nrDesignTimeController.queryFieldDefine(link.getLinkExpression());
                    }
                    if (fieldDefine != null) {
                        linkData.setTableCode(this.searchTableCodeByTableKeydesign(desTableDefineList, fieldDefine.getOwnerTableKey()));
                        linkData.setDataType(fieldDefine.getType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        linkData.setFieldTitle(fieldDefine.getTitle());
                        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                            linkData.setReferField(fieldDefine.getEntityKey());
                        }
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setFieldCode(link.getLinkExpression());
                        linkData.setFieldTitle("");
                        if (DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)link.getType())) {
                            DesignFormDefine designFormDefine = this.nrDesignTimeController.querySoftFormDefine(formKey);
                            DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(designFormDefine.getFormScheme());
                            DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
                            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
                            linkData.setTableCode(iEntityDefine.getCode());
                        } else {
                            linkData.setTableCode("");
                        }
                    }
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getX(), linkData.getY());
                    if (cellData == null) continue;
                    if (viewType == 2) {
                        cellData.setShowText("[" + linkData.getRow() + "," + linkData.getCol() + "]");
                    } else {
                        cellData.setShowText(linkData.getFieldCode());
                    }
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                }
            }
            if (localRegion != null && StringUtil.isNotEmpty((String)(linkExtprtion = (designRegionSettingDefine = this.nrDesignTimeController.getRegionSetting(localRegion.getRegionSettingKey())).getDictionaryFillLinks()))) {
                GridCellData topRow;
                Grid2Data openGrid;
                ExtentStyle extentStyle;
                FieldDefine queryFieldDefine = this.dataDefinitionRuntimeController2.queryFieldDefine(linkExtprtion);
                IEntityQuery newEntityQuery = this.iEntityDataService.newEntityQuery();
                EntityViewDefine buildEntityView = this.iEntityViewRunTimeController.buildEntityView(queryFieldDefine.getEntityKey());
                newEntityQuery.setEntityView(buildEntityView);
                IEntityTable executeReader = newEntityQuery.executeReader((IContext)new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController2));
                List queryItemData = executeReader.getAllRows();
                byte[] bigData = this.designFormDefineService.getBigData(designRegionSettingDefine.getKey(), "EXTENTSTYLE");
                if (bigData != null) {
                    extentStyle = ExtentStyle.bytesToTaskFlowsData((byte[])bigData);
                    openGrid = extentStyle.getGriddata();
                    griddata.insertRows(localRegion.getRegionTop(), openGrid.getRowCount() - 1);
                    for (int a = 0; a < openGrid.getRowCount() - 1; ++a) {
                        griddata.setRowHeight(localRegion.getRegionTop() + a, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + a, true);
                        griddata.getGridCellData(0, localRegion.getRegionTop() + a).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + openGrid.getRowCount() - 1);
                            GridCellData temp = openGrid.getGridCellData(c, a + 1);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            openGrid.getGridCellData(c, 1 + a).setShowText(showText);
                            openGrid.getGridCellData(c, 1 + a).setForeGroundColor(topRow.getForeGroundColor());
                            openGrid.getGridCellData(c, 1 + a).setHorzAlign(topRow.getHorzAlign());
                            openGrid.getGridCellData(c, 1 + a).setFontSize(topRow.getFontSize());
                            openGrid.getGridCellData(c, 1 + a).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.copyFrom(extentStyle.getGriddata(), 1, 1, openGrid.getColumnCount() - 1, openGrid.getRowCount() - 1, 1, localRegion.getRegionTop());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    griddata.deleteRows(localRegion.getRegionTop() + openGrid.getRowCount() - 1, 1);
                    formData.setPropList(extentStyle.getLineProps());
                } else {
                    extentStyle = this.extentStyleService.getFormStyle(formKey, queryItemData, localRegion.getKey(), linkExtprtion);
                    griddata.insertRows(localRegion.getRegionTop(), queryItemData.size());
                    openGrid = extentStyle.getGriddata();
                    griddata.copyFrom(openGrid, 1, localRegion.getRegionTop(), openGrid.getColumnCount() - 1, localRegion.getRegionTop() + queryItemData.size() - 1, 1, localRegion.getRegionTop());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    for (int i = 0; i < queryItemData.size(); ++i) {
                        griddata.setRowHeight(localRegion.getRegionTop() + i, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + i, true);
                        griddata.getGridCellData(0, localRegion.getRegionTop() + i).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + queryItemData.size());
                            GridCellData temp = griddata.getGridCellData(c, localRegion.getRegionTop() + i);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setShowText(showText);
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setForeGroundColor(topRow.getForeGroundColor());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setHorzAlign(topRow.getHorzAlign());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontSize(topRow.getFontSize());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.deleteRows(localRegion.getRegionTop() + queryItemData.size(), 1);
                    formData.setPropList(extentStyle.getLineProps());
                }
                formData.setEnums(this.copyIBaseData(queryItemData));
            }
            formData.setLinks(links);
            formData.setRegions(floatRegions);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return formData;
    }

    private List<DesignTableDefine> getAllDesignTable(List<DesignDataLinkDefine> dataLinks, Map<String, FieldDefine> fieldDefineMap) {
        ArrayList<String> tableIds = new ArrayList<String>();
        List<Object> defines = new ArrayList<DesignTableDefine>();
        try {
            ArrayList<String> expression = new ArrayList<String>();
            for (DesignDataLinkDefine link : dataLinks) {
                if (StringUtils.isEmpty((String)link.getLinkExpression())) continue;
                expression.add(link.getLinkExpression());
            }
            List fieldDefineList = this.nrDesignTimeController.queryFieldDefines((String[])expression.stream().toArray(String[]::new));
            if (null != fieldDefineList && !fieldDefineList.isEmpty()) {
                for (DesignFieldDefine fieldDefine : fieldDefineList) {
                    String id;
                    if (null == fieldDefineMap.get(fieldDefine.getKey())) {
                        fieldDefineMap.put(fieldDefine.getKey(), (FieldDefine)fieldDefine);
                    }
                    if (tableIds.contains(id = fieldDefine.getOwnerTableKey())) continue;
                    tableIds.add(id);
                }
            }
            if (tableIds.size() > 0) {
                defines = this.nrDesignTimeController.queryTableDefines(tableIds.toArray(new String[1]));
            }
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
        }
        return defines;
    }

    private List<IBaseData> copyIBaseData(List<IEntityRow> list) {
        ArrayList<IBaseData> baseData = new ArrayList<IBaseData>();
        for (IEntityRow row : list) {
            BaseDataImpl i = new BaseDataImpl(row.getEntityKeyData(), row.getEntityKeyData(), row.getTitle(), "", row.getParentEntityKey(), false);
            i.setKey(row.getEntityKeyData());
            baseData.add(i);
        }
        return baseData;
    }

    private String searchTableCodeByTableKeydesign(List<DesignTableDefine> list, String tableKey) {
        String rs = "";
        for (TableDefine tableDefine : list) {
            if (!tableDefine.getKey().equals(tableKey)) continue;
            rs = tableDefine.getCode();
        }
        return rs;
    }

    private String searchTableCodeByTableKey(List<TableDefine> list, String tableKey) {
        String rs = "";
        for (TableDefine t : list) {
            if (!t.getKey().equals(tableKey)) continue;
            rs = t.getCode();
        }
        return rs;
    }

    public FormulaDesignFormData getFormDataByRunTime(String formKey, int viewType, String regKey) {
        FormulaDesignFormData formData = new FormulaDesignFormData();
        try {
            RegionSettingDefine regionSettingDefine;
            String linkExtprtion;
            formData.setFormKey(formKey);
            List regions = this.nrRunTimeController.getAllRegionsInForm(formKey);
            ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
            HashSet<String> regionKeySet = new HashSet<String>();
            DataRegionDefine localRegion = null;
            Grid2Data griddata = this.getGridDataByRunTime(formKey);
            List fieldList = this.nrRunTimeController.getFieldKeysInForm(formKey);
            List tableDefineList = this.dataDefinitionRuntimeController2.queryTableDefinesByFields((Collection)fieldList);
            ArrayList<FormulaDesignLinksData> links = new ArrayList<FormulaDesignLinksData>();
            for (DataRegionDefine region : regions) {
                String regionKey = region.getKey();
                if (regionKey.equals(regKey)) {
                    localRegion = region;
                }
                if (!regionKeySet.add(regionKey)) continue;
                if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && region.getRegionBottom() - region.getRegionTop() == 0) {
                    floatRegions.add(region);
                    griddata.getGridCellData(0, region.getRegionTop()).setShowText("+" + region.getRegionTop());
                    griddata.getGridCellData(0, region.getRegionTop()).setBackGroundColor(TEXT_BLUE);
                    griddata.getGridCellData(0, region.getRegionTop()).setBottomBorderStyle(4);
                    griddata.getGridCellData(0, region.getRegionTop()).setRightBorderStyle(4);
                }
                List dataLinks = this.nrRunTimeController.getAllLinksInRegion(regionKey);
                for (DataLinkDefine link : dataLinks) {
                    FormulaDesignLinksData linkData = new FormulaDesignLinksData();
                    linkData.setCol(link.getColNum());
                    linkData.setRow(link.getRowNum());
                    linkData.setX(link.getPosX());
                    linkData.setY(link.getPosY());
                    linkData.setLinkExpression(link.getLinkExpression());
                    linkData.setLinkType(link.getType().getValue());
                    FieldDefine fieldDefine = null;
                    if (StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                        fieldDefine = this.dataDefinitionRuntimeController2.queryFieldDefine(link.getLinkExpression());
                    }
                    if (fieldDefine != null) {
                        linkData.setTableCode(this.searchTableCodeByTableKey(tableDefineList, fieldDefine.getOwnerTableKey()));
                        linkData.setDataType(fieldDefine.getType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        linkData.setFieldTitle(fieldDefine.getTitle());
                        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                            linkData.setReferField(fieldDefine.getEntityKey());
                        }
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setFieldCode(link.getLinkExpression());
                        linkData.setFieldTitle("");
                        if (DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)link.getType())) {
                            FormDefine designFormDefine = this.nrRunTimeController.queryFormById(formKey);
                            FormSchemeDefine formSchemeDefine = this.nrRunTimeController.getFormScheme(designFormDefine.getFormScheme());
                            TaskDefine designTaskDefine = this.nrRunTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
                            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
                            linkData.setTableCode(iEntityDefine.getCode());
                        } else {
                            linkData.setTableCode("");
                        }
                    }
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getX(), linkData.getY());
                    if (cellData == null) continue;
                    if (viewType == 2) {
                        cellData.setShowText("[" + linkData.getRow() + "," + linkData.getCol() + "]");
                    } else {
                        cellData.setShowText(linkData.getFieldCode());
                    }
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                }
            }
            formData.setEnums(new ArrayList<IBaseData>());
            if (localRegion != null && StringUtil.isNotEmpty((String)(linkExtprtion = (regionSettingDefine = this.nrRunTimeController.getRegionSetting(localRegion.getKey())).getDictionaryFillLinks()))) {
                GridCellData topRow;
                List<Object> queryItemData = new ArrayList();
                String fieldCode = "";
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController2.queryFieldDefine(linkExtprtion);
                if (fieldDefine != null) {
                    IDataAssist iDataAssist = this.dataAccessProvider.newDataAssist(new com.jiuqi.np.dataengine.executors.ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController2));
                    if (iDataAssist != null) {
                        fieldCode = iDataAssist.getDimensionName(fieldDefine);
                    }
                    String viewKey = this.extentStyleService.getEntityKey(localRegion.getKey(), formKey, fieldDefine.getKey());
                    queryItemData = this.extentStyleService.queryItemData(viewKey);
                }
                formData.setField(fieldCode);
                RunTimeBigDataTable runTimeBigDataTable = this.bigDataDao.queryigDataDefine(regionSettingDefine.getKey(), "EXTENTSTYLE");
                if (runTimeBigDataTable != null) {
                    byte[] bigData = runTimeBigDataTable.getData();
                    ExtentStyle extentStyle = ExtentStyle.bytesToTaskFlowsData((byte[])bigData);
                    Grid2Data openGrid = extentStyle.getGriddata();
                    griddata.insertRows(localRegion.getRegionTop(), openGrid.getRowCount() - 1);
                    for (int a = 0; a < openGrid.getRowCount() - 1; ++a) {
                        griddata.setRowHeight(localRegion.getRegionTop() + a, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + a, true);
                        griddata.getGridCellData(0, localRegion.getRegionTop() + a).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + openGrid.getRowCount() - 1);
                            GridCellData temp = openGrid.getGridCellData(c, a + 1);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            openGrid.getGridCellData(c, 1 + a).setShowText(showText);
                            openGrid.getGridCellData(c, 1 + a).setForeGroundColor(topRow.getForeGroundColor());
                            openGrid.getGridCellData(c, 1 + a).setHorzAlign(topRow.getHorzAlign());
                            openGrid.getGridCellData(c, 1 + a).setFontSize(topRow.getFontSize());
                            openGrid.getGridCellData(c, 1 + a).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.copyFrom(extentStyle.getGriddata(), 1, 1, openGrid.getColumnCount() - 1, openGrid.getRowCount() - 1, 1, localRegion.getRegionTop());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    griddata.deleteRows(localRegion.getRegionTop() + openGrid.getRowCount() - 1, 1);
                } else {
                    griddata.insertRows(localRegion.getRegionTop(), queryItemData.size());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    List linksInFormByField = this.nrDesignTimeController.getLinksInFormByField(formKey, linkExtprtion);
                    DesignDataLinkDefine designDataLinkDefine = (DesignDataLinkDefine)linksInFormByField.get(0);
                    for (int i = 0; i < queryItemData.size(); ++i) {
                        GridCellData gridCellData = griddata.getGridCellData(designDataLinkDefine.getPosX(), localRegion.getRegionTop() + i);
                        griddata.setRowHeight(localRegion.getRegionTop() + i, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + i, true);
                        gridCellData.setShowText(((IEntityRow)queryItemData.get(i)).getTitle());
                        gridCellData.setHorzAlign(3);
                        gridCellData.setEditText(((IEntityRow)queryItemData.get(i)).getTitle());
                        griddata.getGridCellData(0, localRegion.getRegionTop() + i).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + queryItemData.size());
                            GridCellData temp = griddata.getGridCellData(c, localRegion.getRegionTop() + i);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setShowText(showText);
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setForeGroundColor(topRow.getForeGroundColor());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setHorzAlign(topRow.getHorzAlign());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontSize(topRow.getFontSize());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.deleteRows(localRegion.getRegionTop() + queryItemData.size(), 1);
                }
                formData.setEnums(this.copyIBaseData(queryItemData));
            }
            formData.setLinks(links);
            formData.setRegions(floatRegions);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return formData;
    }

    public Grid2Data getGridData(String formKey) {
        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
        Grid2Data gridData = null;
        if (null != formDefine) {
            if (formDefine.getBinaryData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
            } else {
                gridData = new Grid2Data();
                gridData.setRowCount(10);
                gridData.setColumnCount(10);
            }
        }
        return gridData;
    }

    public Grid2Data getGridDataByRunTime(String formKey) {
        BigDataDefine dataDefine = this.nrRunTimeController.getReportDataFromForm(formKey);
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

    private List<FormulaDesignLinksData> toLinksData(List<LinkData> linkDatas) {
        ArrayList<FormulaDesignLinksData> datas = new ArrayList<FormulaDesignLinksData>();
        for (LinkData linkData : linkDatas) {
            FormulaDesignLinksData data = new FormulaDesignLinksData();
            data.setDataType(linkData.getDatatype());
            data.setLinkType(linkData.getLinkType());
            data.setLinkExpression(linkData.getLinkExpression());
            data.setX(linkData.getX());
            data.setY(linkData.getY());
            data.setRow(linkData.getRow());
            data.setCol(linkData.getCol());
            data.setFieldCode(linkData.getFieldcode());
            data.setFieldTitle(linkData.getFieldtitle());
            data.setReferField(linkData.getReferField());
            data.setTableCode(linkData.getTableCode());
            datas.add(data);
        }
        return datas;
    }

    public FormulaDesignFormData getFormData(EditorParam editorParam) {
        FormulaDesignFormData formData = new FormulaDesignFormData();
        try {
            DesignRegionSettingDefine designRegionSettingDefine;
            String linkExtprtion;
            if (editorParam.isCustomStyle() && null != editorParam.getEditorKey() && null != this.editorStyleCustom && this.editorStyleCustom.size() != 0) {
                for (EditorStyleCustom styleCustom : this.editorStyleCustom) {
                    if (!editorParam.getEditorKey().equals(styleCustom.getKey())) continue;
                    StyleData editData = styleCustom.getEditData(editorParam);
                    formData.setFormKey(editData.getFormKey());
                    formData.setGridData(editData.getGriddata());
                    formData.setLinks(this.toLinksData(editData.getLinks()));
                    formData.setRegions(new ArrayList<DataRegionDefine>());
                    return formData;
                }
            }
            formData.setFormKey(editorParam.getFormKey());
            List regions = this.nrDesignTimeController.getAllRegionsInForm(editorParam.getFormKey());
            ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
            HashSet<String> regionKeySet = new HashSet<String>();
            Grid2Data griddata = this.getGridData(editorParam.getFormKey());
            DataRegionDefine localRegion = null;
            ArrayList<FormulaDesignLinksData> links = new ArrayList<FormulaDesignLinksData>();
            for (DataRegionDefine region : regions) {
                String regionKey = region.getKey();
                if (regionKey.equals(editorParam.getRegionKey())) {
                    localRegion = region;
                }
                if (!regionKeySet.add(regionKey)) continue;
                if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && region.getRegionBottom() - region.getRegionTop() == 0) {
                    floatRegions.add(region);
                    griddata.getGridCellData(0, region.getRegionTop()).setShowText("+" + region.getRegionTop());
                    griddata.getGridCellData(0, region.getRegionTop()).setBackGroundColor(TEXT_BLUE);
                    griddata.getGridCellData(0, region.getRegionTop()).setBottomBorderStyle(4);
                    griddata.getGridCellData(0, region.getRegionTop()).setRightBorderStyle(4);
                }
                List dataLinks = this.nrDesignTimeController.getAllLinksInRegion(regionKey);
                HashMap<String, FieldDefine> fieldDefineMap = new HashMap<String, FieldDefine>();
                List<DesignTableDefine> desTableDefineList = this.getAllDesignTable(dataLinks, fieldDefineMap);
                for (DataLinkDefine link : dataLinks) {
                    FormulaDesignLinksData linkData = new FormulaDesignLinksData();
                    linkData.setCol(link.getColNum());
                    linkData.setRow(link.getRowNum());
                    linkData.setX(link.getPosX());
                    linkData.setY(link.getPosY());
                    linkData.setLinkExpression(link.getLinkExpression());
                    linkData.setLinkType(link.getType().getValue());
                    FieldDefine fieldDefine = null;
                    if (StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                        fieldDefine = (FieldDefine)fieldDefineMap.get(link.getLinkExpression());
                    }
                    if (fieldDefine != null) {
                        linkData.setTableCode(this.searchTableCodeByTableKeydesign(desTableDefineList, fieldDefine.getOwnerTableKey()));
                        linkData.setDataType(fieldDefine.getType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        linkData.setFieldTitle(fieldDefine.getTitle());
                        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                            linkData.setReferField(fieldDefine.getEntityKey());
                        }
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setFieldCode(link.getLinkExpression());
                        linkData.setFieldTitle("");
                        if (DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)link.getType())) {
                            DesignFormDefine designFormDefine = this.nrDesignTimeController.querySoftFormDefine(editorParam.getFormKey());
                            DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(designFormDefine.getFormScheme());
                            DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
                            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
                            linkData.setTableCode(iEntityDefine.getCode());
                        } else {
                            linkData.setTableCode("");
                        }
                    }
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getX(), linkData.getY());
                    if (cellData == null) continue;
                    if (editorParam.getViewType() == 2) {
                        cellData.setShowText("[" + linkData.getRow() + "," + linkData.getCol() + "]");
                    } else {
                        cellData.setShowText(linkData.getFieldCode());
                    }
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                }
            }
            if (localRegion != null && StringUtil.isNotEmpty((String)(linkExtprtion = (designRegionSettingDefine = this.nrDesignTimeController.getRegionSetting(localRegion.getRegionSettingKey())).getDictionaryFillLinks()))) {
                GridCellData temp;
                GridCellData topRow;
                Grid2Data openGrid;
                ExtentStyle extentStyle;
                FieldDefine queryFieldDefine = this.dataDefinitionRuntimeController2.queryFieldDefine(linkExtprtion);
                IEntityQuery newEntityQuery = this.iEntityDataService.newEntityQuery();
                EntityViewDefine buildEntityView = this.iEntityViewRunTimeController.buildEntityView(queryFieldDefine.getEntityKey());
                newEntityQuery.setEntityView(buildEntityView);
                IEntityTable executeReader = newEntityQuery.executeReader((IContext)new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController2));
                List queryItemData = executeReader.getAllRows();
                byte[] bigData = this.designFormDefineService.getBigData(designRegionSettingDefine.getKey(), "EXTENTSTYLE");
                if (bigData != null) {
                    extentStyle = ExtentStyle.bytesToTaskFlowsData((byte[])bigData);
                    openGrid = extentStyle.getGriddata();
                    griddata.insertRows(localRegion.getRegionTop(), openGrid.getRowCount() - 1);
                    for (int a = 0; a < openGrid.getRowCount() - 1; ++a) {
                        griddata.setRowHeight(localRegion.getRegionTop() + a, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + a, true);
                        griddata.getGridCellData(0, localRegion.getRegionTop() + a).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + openGrid.getRowCount() - 1);
                            temp = openGrid.getGridCellData(c, a + 1);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            openGrid.getGridCellData(c, 1 + a).setShowText(showText);
                            openGrid.getGridCellData(c, 1 + a).setForeGroundColor(topRow.getForeGroundColor());
                            openGrid.getGridCellData(c, 1 + a).setHorzAlign(topRow.getHorzAlign());
                            openGrid.getGridCellData(c, 1 + a).setFontSize(topRow.getFontSize());
                            openGrid.getGridCellData(c, 1 + a).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.copyFrom(extentStyle.getGriddata(), 1, 1, openGrid.getColumnCount() - 1, openGrid.getRowCount() - 1, 1, localRegion.getRegionTop());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    griddata.deleteRows(localRegion.getRegionTop() + openGrid.getRowCount() - 1, 1);
                    formData.setPropList(extentStyle.getLineProps());
                } else {
                    extentStyle = this.extentStyleService.getFormStyle(editorParam.getFormKey(), queryItemData, localRegion.getKey(), linkExtprtion);
                    griddata.insertRows(localRegion.getRegionTop(), queryItemData.size());
                    openGrid = extentStyle.getGriddata();
                    griddata.copyFrom(openGrid, 1, localRegion.getRegionTop(), openGrid.getColumnCount() - 1, localRegion.getRegionTop() + queryItemData.size() - 1, 1, localRegion.getRegionTop());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    for (int i = 0; i < queryItemData.size(); ++i) {
                        griddata.setRowHeight(localRegion.getRegionTop() + i, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + i, true);
                        griddata.getGridCellData(0, localRegion.getRegionTop() + i).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + queryItemData.size());
                            temp = griddata.getGridCellData(c, localRegion.getRegionTop() + i);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setShowText(showText);
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setForeGroundColor(topRow.getForeGroundColor());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setHorzAlign(topRow.getHorzAlign());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontSize(topRow.getFontSize());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.deleteRows(localRegion.getRegionTop() + queryItemData.size(), 1);
                    formData.setPropList(extentStyle.getLineProps());
                }
                formData.setEnums(this.copyIBaseData(queryItemData));
            }
            formData.setLinks(links);
            formData.setRegions(floatRegions);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return formData;
    }

    public FormulaDesignFormData getFormDataByRunTime(EditorParam editorParam) {
        FormulaDesignFormData formData = new FormulaDesignFormData();
        try {
            RegionSettingDefine regionSettingDefine;
            String linkExtprtion;
            if (editorParam.isCustomStyle() && null != editorParam.getEditorKey() && null != this.editorStyleCustom && this.editorStyleCustom.size() != 0) {
                for (EditorStyleCustom styleCustom : this.editorStyleCustom) {
                    if (!editorParam.getEditorKey().equals(styleCustom.getKey())) continue;
                    StyleData editData = styleCustom.getEditData(editorParam);
                    formData.setFormKey(editData.getFormKey());
                    formData.setGridData(editData.getGriddata());
                    formData.setLinks(this.toLinksData(editData.getLinks()));
                    formData.setRegions(new ArrayList<DataRegionDefine>());
                    return formData;
                }
            }
            formData.setFormKey(editorParam.getFormKey());
            List regions = this.nrRunTimeController.getAllRegionsInForm(editorParam.getFormKey());
            ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
            HashSet<String> regionKeySet = new HashSet<String>();
            DataRegionDefine localRegion = null;
            Grid2Data griddata = this.getGridDataByRunTime(editorParam.getFormKey());
            List fieldList = this.nrRunTimeController.getFieldKeysInForm(editorParam.getFormKey());
            List fieldDefines = this.dataDefinitionRuntimeController2.queryFieldDefines((Collection)fieldList);
            HashMap<String, FieldDefine> fieldDefineMap = new HashMap<String, FieldDefine>();
            if (null != fieldDefines && !fieldDefines.isEmpty()) {
                for (FieldDefine fieldDefine : fieldDefines) {
                    if (null != fieldDefineMap.get(fieldDefine.getKey())) continue;
                    fieldDefineMap.put(fieldDefine.getKey(), fieldDefine);
                }
            }
            List tableDefineList = this.dataDefinitionRuntimeController2.queryTableDefinesByFields((Collection)fieldList);
            ArrayList<FormulaDesignLinksData> links = new ArrayList<FormulaDesignLinksData>();
            for (DataRegionDefine region : regions) {
                String regionKey = region.getKey();
                if (regionKey.equals(editorParam.getFormKey())) {
                    localRegion = region;
                }
                if (!regionKeySet.add(regionKey)) continue;
                if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && region.getRegionBottom() - region.getRegionTop() == 0) {
                    floatRegions.add(region);
                    griddata.getGridCellData(0, region.getRegionTop()).setShowText("+" + region.getRegionTop());
                    griddata.getGridCellData(0, region.getRegionTop()).setBackGroundColor(TEXT_BLUE);
                    griddata.getGridCellData(0, region.getRegionTop()).setBottomBorderStyle(4);
                    griddata.getGridCellData(0, region.getRegionTop()).setRightBorderStyle(4);
                }
                List dataLinks = this.nrRunTimeController.getAllLinksInRegion(regionKey);
                for (DataLinkDefine link : dataLinks) {
                    FormulaDesignLinksData linkData = new FormulaDesignLinksData();
                    linkData.setCol(link.getColNum());
                    linkData.setRow(link.getRowNum());
                    linkData.setX(link.getPosX());
                    linkData.setY(link.getPosY());
                    linkData.setLinkExpression(link.getLinkExpression());
                    linkData.setLinkType(link.getType().getValue());
                    FieldDefine fieldDefine = null;
                    if (StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                        fieldDefine = (FieldDefine)fieldDefineMap.get(link.getLinkExpression());
                    }
                    if (fieldDefine != null) {
                        linkData.setTableCode(this.searchTableCodeByTableKey(tableDefineList, fieldDefine.getOwnerTableKey()));
                        linkData.setDataType(fieldDefine.getType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        linkData.setFieldTitle(fieldDefine.getTitle());
                        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                            linkData.setReferField(fieldDefine.getEntityKey());
                        }
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setFieldCode(link.getLinkExpression());
                        linkData.setFieldTitle("");
                        if (DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)link.getType())) {
                            FormDefine designFormDefine = this.nrRunTimeController.queryFormById(editorParam.getFormKey());
                            FormSchemeDefine formSchemeDefine = this.nrRunTimeController.getFormScheme(designFormDefine.getFormScheme());
                            TaskDefine designTaskDefine = this.nrRunTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
                            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
                            linkData.setTableCode(iEntityDefine.getCode());
                        } else {
                            linkData.setTableCode("");
                        }
                    }
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getX(), linkData.getY());
                    if (cellData == null) continue;
                    if (editorParam.getViewType() == 2) {
                        cellData.setShowText("[" + linkData.getRow() + "," + linkData.getCol() + "]");
                    } else {
                        cellData.setShowText(linkData.getFieldCode());
                    }
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                }
            }
            formData.setEnums(new ArrayList<IBaseData>());
            if (localRegion != null && StringUtil.isNotEmpty((String)(linkExtprtion = (regionSettingDefine = this.nrRunTimeController.getRegionSetting(localRegion.getKey())).getDictionaryFillLinks()))) {
                GridCellData topRow;
                List<Object> queryItemData = new ArrayList();
                String fieldCode = "";
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController2.queryFieldDefine(linkExtprtion);
                if (fieldDefine != null) {
                    IDataAssist iDataAssist = this.dataAccessProvider.newDataAssist(new com.jiuqi.np.dataengine.executors.ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController2));
                    if (iDataAssist != null) {
                        fieldCode = iDataAssist.getDimensionName(fieldDefine);
                    }
                    String viewKey = this.extentStyleService.getEntityKey(localRegion.getKey(), editorParam.getFormKey(), fieldDefine.getKey());
                    queryItemData = this.extentStyleService.queryItemData(viewKey);
                }
                formData.setField(fieldCode);
                RunTimeBigDataTable runTimeBigDataTable = this.bigDataDao.queryigDataDefine(regionSettingDefine.getKey(), "EXTENTSTYLE");
                if (runTimeBigDataTable != null) {
                    byte[] bigData = runTimeBigDataTable.getData();
                    ExtentStyle extentStyle = ExtentStyle.bytesToTaskFlowsData((byte[])bigData);
                    Grid2Data openGrid = extentStyle.getGriddata();
                    griddata.insertRows(localRegion.getRegionTop(), openGrid.getRowCount() - 1);
                    for (int a = 0; a < openGrid.getRowCount() - 1; ++a) {
                        griddata.setRowHeight(localRegion.getRegionTop() + a, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + a, true);
                        griddata.getGridCellData(0, localRegion.getRegionTop() + a).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + openGrid.getRowCount() - 1);
                            GridCellData temp = openGrid.getGridCellData(c, a + 1);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            openGrid.getGridCellData(c, 1 + a).setShowText(showText);
                            openGrid.getGridCellData(c, 1 + a).setForeGroundColor(topRow.getForeGroundColor());
                            openGrid.getGridCellData(c, 1 + a).setHorzAlign(topRow.getHorzAlign());
                            openGrid.getGridCellData(c, 1 + a).setFontSize(topRow.getFontSize());
                            openGrid.getGridCellData(c, 1 + a).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.copyFrom(extentStyle.getGriddata(), 1, 1, openGrid.getColumnCount() - 1, openGrid.getRowCount() - 1, 1, localRegion.getRegionTop());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    griddata.deleteRows(localRegion.getRegionTop() + openGrid.getRowCount() - 1, 1);
                } else {
                    griddata.insertRows(localRegion.getRegionTop(), queryItemData.size());
                    griddata.getGridCellData(0, localRegion.getRegionTop()).setShowText("+" + localRegion.getRegionTop());
                    List linksInFormByField = this.nrDesignTimeController.getLinksInFormByField(editorParam.getFormKey(), linkExtprtion);
                    DesignDataLinkDefine designDataLinkDefine = (DesignDataLinkDefine)linksInFormByField.get(0);
                    for (int i = 0; i < queryItemData.size(); ++i) {
                        GridCellData gridCellData = griddata.getGridCellData(designDataLinkDefine.getPosX(), localRegion.getRegionTop() + i);
                        griddata.setRowHeight(localRegion.getRegionTop() + i, 30);
                        griddata.setRowAutoHeight(localRegion.getRegionTop() + i, true);
                        gridCellData.setShowText(((IEntityRow)queryItemData.get(i)).getTitle());
                        gridCellData.setHorzAlign(3);
                        gridCellData.setEditText(((IEntityRow)queryItemData.get(i)).getTitle());
                        griddata.getGridCellData(0, localRegion.getRegionTop() + i).setBackGroundColor(TEXT_BLUE);
                        for (int c = 1; c < griddata.getColumnCount(); ++c) {
                            topRow = griddata.getGridCellData(c, localRegion.getRegionTop() + queryItemData.size());
                            GridCellData temp = griddata.getGridCellData(c, localRegion.getRegionTop() + i);
                            String showText = topRow.getShowText();
                            if (!StringUtil.isNotEmpty((String)showText) || temp == null || !StringUtil.isEmpty((String)temp.getEditText())) continue;
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setShowText(showText);
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setForeGroundColor(topRow.getForeGroundColor());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setHorzAlign(topRow.getHorzAlign());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontSize(topRow.getFontSize());
                            griddata.getGridCellData(c, localRegion.getRegionTop() + i).setFontName(topRow.getFontName());
                        }
                    }
                    griddata.deleteRows(localRegion.getRegionTop() + queryItemData.size(), 1);
                }
                formData.setEnums(this.copyIBaseData(queryItemData));
            }
            formData.setLinks(links);
            formData.setRegions(floatRegions);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return formData;
    }
}

