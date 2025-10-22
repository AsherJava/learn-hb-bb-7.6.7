/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportTableType
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  nr.single.map.common.ImportConsts
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 */
package nr.single.para.parain.internal.maping;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nr.single.map.common.ImportConsts;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.RegionTableList;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping.ITaskFileMapingFormService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileMapingFormService
implements ITaskFileMapingFormService {
    private static final Logger log = LoggerFactory.getLogger(TaskFileMapingFormService.class);
    public static final String BIZKEYORDER = "BIZKEYORDER";
    public static final String FIELDFLOATORDER = "FLOATORDER";
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private IDesignTimeFMDMAttributeService fmdmAttributeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public void UpdateMapSchemeDefineByForms(TaskImportContext importContext) throws Exception {
        this.UpdateMapSchemeDefineByForms(importContext, 0);
    }

    @Override
    public void UpdateMapSchemeDefineByForms(TaskImportContext importContext, int mapType) throws Exception {
        DesignDataScheme dataScheme;
        String formSchemeKey = importContext.getFormSchemeKey();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        Map<String, FieldInfoDefine> EntityFieldMaps = importContext.getEntityFieldMaps();
        Map<String, DesignFormDefine> formCache = importContext.getFormCache();
        ParaInfo para = importContext.getParaInfo();
        String taskKey = importContext.getTaskKey();
        importContext.getCurContext().setTaskKey(taskKey);
        DesignTaskDefine taskDefine = importContext.getTaskDefine();
        if (null == taskDefine) {
            taskDefine = this.viewController.queryTaskDefine(taskKey);
            importContext.setTaskDefine(taskDefine);
        }
        if (taskDefine != null && StringUtils.isNotEmpty((String)taskDefine.getDw())) {
            if (StringUtils.isEmpty((String)importContext.getEntityTableKey())) {
                importContext.setEntityTableKey(taskDefine.getDw());
            }
            if (StringUtils.isEmpty((String)importContext.getEntityId())) {
                importContext.setEntityId(taskDefine.getDw());
            }
        }
        if ((dataScheme = importContext.getDataScheme()) == null) {
            dataScheme = this.dataSchemeSevice.getDataScheme(taskDefine.getDataScheme());
            importContext.setDataScheme(dataScheme);
        }
        ArrayList<String> NewEntityList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)formScheme.getMasterEntitiesKey())) {
            String[] entityKeys = formScheme.getMasterEntitiesKey().split(";");
            for (String entityId : entityKeys) {
                if (!entityId.equalsIgnoreCase(formScheme.getDateTime())) {
                    IEntityModel entityMode = this.entityMetaService.getEntityModel(entityId);
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                    IEntityAttribute fiedDefine = entityMode.getBizKeyField();
                    String enityCode = entityMode.getEntityId();
                    IEntityModel parentEnity = this.getMasterEntity(entityMode);
                    if (parentEnity != entityMode) {
                        enityCode = parentEnity.getEntityId();
                        fiedDefine = parentEnity.getBizKeyField();
                    }
                    EntityFieldMaps.put(enityCode, new FieldInfoDefine(fiedDefine));
                    if (entityId.equalsIgnoreCase(formScheme.getDw())) {
                        if (StringUtils.isEmpty((String)importContext.getEntityTableKey())) {
                            importContext.setEntityTableKey(enityCode);
                        }
                        if (StringUtils.isEmpty((String)importContext.getEntityId())) {
                            importContext.setEntityId(entityId);
                        }
                        if (null == importContext.getEntityTable()) {
                            importContext.setEntityTable(new TableInfoDefine(entityDefine));
                        }
                    }
                    NewEntityList.add(entityId);
                    continue;
                }
                if (!entityId.equalsIgnoreCase(formScheme.getDateTime()) || !StringUtils.isEmpty((String)importContext.getPeriodEntityId())) continue;
                importContext.setPeriodEntityId(entityId);
            }
        } else {
            if (StringUtils.isNotEmpty((String)taskDefine.getDw())) {
                importContext.setEntityId(taskDefine.getDw());
            }
            if (StringUtils.isNotEmpty((String)taskDefine.getDateTime())) {
                importContext.setPeriodEntityId(taskDefine.getDateTime());
            }
        }
        importContext.setOnlyEntityCode(ImportConsts.getCodeStrings(NewEntityList));
        formCache.clear();
        List oldFormList = this.viewController.queryAllSoftFormDefinesByFormScheme(formSchemeKey);
        if (null != oldFormList && oldFormList.size() > 0) {
            for (DesignFormDefine form : oldFormList) {
                formCache.put(form.getFormCode(), form);
            }
        }
        FMRepInfo singleFmdm = para.getFmRepInfo();
        SingleFileFmdmInfo fmdmInfo = importContext.getMapScheme().getFmdmInfo();
        importContext.getMapScheme().getTableInfos().clear();
        this.getFormDefine(importContext, (RepInfo)singleFmdm, formSchemeKey, true, (SingleFileTableInfo)fmdmInfo, mapType);
        List repList = para.getRepInfos();
        for (RepInfo rep : repList) {
            if (singleFmdm == rep || rep.isFMDM() || "FMDM".equalsIgnoreCase(rep.getCode())) continue;
            if (rep.getTableType() == ReportTableType.RTT_BLOBTABLE || rep.getTableType() == ReportTableType.RTT_WORDTABLE) {
                para.getFJReportData(rep);
            }
            SingleFileTableInfo singleTable = importContext.getMapScheme().getNewTableInfo();
            importContext.getMapScheme().getTableInfos().add(singleTable);
            this.getFormDefine(importContext, rep, formSchemeKey, false, singleTable, mapType);
        }
    }

    private DesignFormDefine getFormDefine(TaskImportContext importContext, RepInfo repInfo, String formSchemeKey, boolean isFMDM, SingleFileTableInfo singleTable, int mapType) throws Exception {
        boolean isRegionNew;
        List forms;
        DesignFormDefine formDefine = null;
        if (isFMDM && (forms = this.viewController.queryFormsByTypeInScheme(formSchemeKey, FormType.FORM_TYPE_NEWFMDM)) != null && forms.size() > 0) {
            formDefine = (DesignFormDefine)forms.get(0);
        }
        if (null != formDefine || null == (formDefine = this.viewController.querySoftFormDefineByCodeInFormScheme(formSchemeKey, repInfo.getCode()))) {
            // empty if block
        }
        boolean isFormNew = null == formDefine;
        this.setFormDefineAttr(importContext, formDefine, repInfo, isFMDM, isFormNew, singleTable);
        log.info("\u751f\u6210\u8868\u5355\u6620\u5c04:" + repInfo.getCode() + " " + repInfo.getTitle() + " ,\u65f6\u95f4:" + new Date().toString());
        List<DesignDataLinkDefine> oldLinkList = importContext.getFormInfoCahche().getOldLinkList();
        Map<String, DesignDataLinkDefine> oldLinkCache = importContext.getFormInfoCahche().getOldLinkCache();
        Map<String, Map<String, DesignDataLinkDefine>> oldRegionLinkCache = importContext.getFormInfoCahche().getOldRegionLinkCache();
        Map<String, DesignDataLinkDefine> formLinksMap = importContext.getFormInfoCahche().getFormLinksMap();
        oldLinkCache.clear();
        oldRegionLinkCache.clear();
        oldLinkList.clear();
        formLinksMap.clear();
        DesignDataRegionDefine fixRegion = null;
        HashMap<Integer, DesignDataRegionDefine> floatRegions = new HashMap<Integer, DesignDataRegionDefine>();
        List<Object> dataRegions = null;
        if (!isFormNew) {
            dataRegions = this.viewController.getAllRegionsInForm(formDefine.getKey());
            for (DesignDataRegionDefine designDataRegionDefine : dataRegions) {
                if (designDataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                    fixRegion = designDataRegionDefine;
                } else if (designDataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                    floatRegions.put(designDataRegionDefine.getRegionTop(), designDataRegionDefine);
                } else if (designDataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                    floatRegions.put(designDataRegionDefine.getRegionLeft(), designDataRegionDefine);
                }
                List curRegionList = this.viewController.getAllLinksInRegion(designDataRegionDefine.getKey());
                oldLinkList.addAll(curRegionList);
                Map<Object, Object> oldRegionLinkList = null;
                if (oldRegionLinkCache.containsKey(designDataRegionDefine.getKey())) {
                    oldRegionLinkList = oldRegionLinkCache.get(designDataRegionDefine.getKey());
                } else {
                    oldRegionLinkList = new HashMap();
                    oldRegionLinkCache.put(designDataRegionDefine.getKey(), oldRegionLinkList);
                }
                for (DesignDataLinkDefine link : curRegionList) {
                    if (StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                        if (isFMDM && link.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                            DesignDataField dataField = this.dataSchemeSevice.getDataField(link.getLinkExpression());
                            if (dataField != null) {
                                oldLinkCache.put(dataField.getCode(), link);
                                oldRegionLinkList.put(dataField.getCode(), link);
                                continue;
                            }
                            oldLinkCache.put(link.getLinkExpression(), link);
                            oldRegionLinkList.put(link.getLinkExpression(), link);
                            continue;
                        }
                        oldLinkCache.put(link.getLinkExpression(), link);
                        oldRegionLinkList.put(link.getLinkExpression(), link);
                        continue;
                    }
                    log.info("\u5b58\u5728\u9519\u8bef\u94fe\u63a5\uff1a" + link.getKey());
                }
            }
            if (dataRegions == null) {
                dataRegions = new ArrayList();
            }
            if (dataRegions.size() == 0) {
                dataRegions.add(null);
            }
        } else {
            dataRegions = new ArrayList<Object>();
            dataRegions.add(null);
        }
        List<DesignDataRegionDefine> insertRegions = importContext.getFormInfoCahche().getInsertRegions();
        FieldDefs fieldDefs = repInfo.getDefs();
        DesignDataRegionDefine dataRegion = (DesignDataRegionDefine)dataRegions.get(0);
        boolean bl = isRegionNew = dataRegion == null;
        if (fixRegion != null) {
            dataRegion = fixRegion;
        }
        this.setDataRegion(importContext, fieldDefs, dataRegion, formDefine, repInfo, true, isFMDM, isRegionNew, singleTable.getRegion(), mapType);
        insertRegions.add(dataRegion);
        singleTable.getRegion().getSubRegions().clear();
        int idx = 1;
        for (FieldDefs subdef : fieldDefs.getSubMbs()) {
            DesignDataRegionDefine subRegion = null;
            subRegion = (DesignDataRegionDefine)floatRegions.get(subdef.getRegionInfo().getMapArea().getFloatRangeStartNo());
            if (subRegion == null && dataRegions.size() > idx) {
                subRegion = (DesignDataRegionDefine)dataRegions.get(idx);
            }
            ++idx;
            SingleFileRegionInfo aSubSingleRegion = singleTable.getRegion().getNewSubRegion();
            singleTable.getRegion().getSubRegions().add(aSubSingleRegion);
            isRegionNew = subRegion == null;
            this.setDataRegion(importContext, subdef, subRegion, formDefine, repInfo, false, isFMDM, isRegionNew, aSubSingleRegion, mapType);
            insertRegions.add(subRegion);
        }
        return formDefine;
    }

    private void setDataRegion(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion, int mapType) throws Exception {
        String tableCode = "";
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        Map<String, DesignDataLinkDefine> formLinksMap = importContext.getFormInfoCahche().getFormLinksMap();
        String fileFlag = "";
        DesignTaskDefine task = importContext.getTaskDefine();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = dataScheme.getPrefix();
        }
        this.setDataRegionAttr(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion);
        if (isFixed) {
            tableCode = String.format("%s%s", StringUtils.isEmpty((String)fileFlag) ? "" : fileFlag + "_", repInfo.getCode());
            if (isFMDM) {
                tableCode = null != importContext.getEntityTable() ? importContext.getEntityTable().getCode() : String.format("%s%s", StringUtils.isEmpty((String)fileFlag) ? "" : fileFlag + "_", "FMDM");
            }
        } else {
            tableCode = String.format("%s%s_F%s", StringUtils.isEmpty((String)fileFlag) ? "" : fileFlag + "_", repInfo.getCode(), def.getRegionInfo().getMapArea().getFloatRangeStartNo());
        }
        RegionTableList cacheList = new RegionTableList();
        HashMap<String, DesignFieldDefine> fieldMap = new HashMap<String, DesignFieldDefine>();
        HashMap<String, DesignFieldDefine> fieldMap2 = new HashMap<String, DesignFieldDefine>();
        HashMap<String, DesignFieldDefine> fieldMap3 = new HashMap<String, DesignFieldDefine>();
        HashMap<String, IFMDMAttribute> fmdmAttrMap = new HashMap<String, IFMDMAttribute>();
        DesignTableDefine tableDefine = this.dataController.queryTableDefinesByCode(tableCode);
        cacheList.setRegionTableCode(tableCode);
        cacheList.setFileFlag(fileFlag);
        if (isFMDM) {
            importContext.setDataSchemeKey(task.getDataScheme());
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            String dwEntityView = formScheme.getDw();
            if (StringUtils.isEmpty((String)dwEntityView)) {
                dwEntityView = task.getDw();
            }
            fmdmAttributeDTO.setEntityId(dwEntityView);
            fmdmAttributeDTO.setFormSchemeKey(importContext.getFormSchemeKey());
            List attrList = this.fmdmAttributeService.list(fmdmAttributeDTO);
            Iterator iterator = attrList.iterator();
            while (iterator.hasNext()) {
                IFMDMAttribute attr = (IFMDMAttribute)iterator.next();
                fmdmAttrMap.put(attr.getCode(), attr);
            }
        } else {
            if (null != dataRegion) {
                List tables;
                HashMap<String, DesignTableDefine> tableKeys = new HashMap<String, DesignTableDefine>();
                List fields = this.viewController.getAllFieldsByLinksInRegion(dataRegion.getKey());
                for (Object field : fields) {
                    fieldMap.put(field.getCode(), (DesignFieldDefine)field);
                    fieldMap2.put(field.getAlias(), (DesignFieldDefine)field);
                    DesignTableDefine table = null;
                    if (tableKeys.containsKey(field.getOwnerTableKey())) {
                        table = (DesignTableDefine)tableKeys.get(field.getOwnerTableKey());
                    } else {
                        table = this.dataController.queryTableDefine(field.getOwnerTableKey());
                        tableKeys.put(field.getOwnerTableKey(), table);
                        cacheList.addTableDefine(new TableInfoDefine(table), dataRegion);
                    }
                    cacheList.addFieldDefine(new FieldInfoDefine((DesignFieldDefine)field));
                    if (table == null) continue;
                    this.getOldFieldCode(table, fileFlag, (DesignFieldDefine)field, fieldMap3);
                    if (isFixed || tableDefine != null) continue;
                    tableDefine = table;
                }
                if (fieldMap.isEmpty() && (tables = this.viewController.getAllTableDefineInRegion(dataRegion.getKey())).size() > 0) {
                    for (DesignTableDefine table : tables) {
                        cacheList.addTableDefine(new TableInfoDefine(table), dataRegion);
                        this.getTableFields(cacheList, table, fileFlag, fieldMap, fieldMap2, fieldMap3);
                        if (isFixed || tableDefine != null) continue;
                        tableDefine = table;
                    }
                }
            }
            if (fieldMap.isEmpty() && null != tableDefine) {
                cacheList.addTableDefine(new TableInfoDefine(tableDefine), dataRegion);
                this.getTableFields(cacheList, tableDefine, fileFlag, fieldMap, fieldMap2, fieldMap3);
            }
        }
        Map<String, DesignDataLinkDefine> oldLinkCache = null;
        oldLinkCache = dataRegion != null && importContext.getFormInfoCahche().getOldRegionLinkCache().containsKey(dataRegion.getKey()) ? importContext.getFormInfoCahche().getOldRegionLinkCache().get(dataRegion.getKey()) : importContext.getFormInfoCahche().getOldLinkCache();
        HashMap<String, DesignDataLinkDefine> oldLinkPosCache = new HashMap<String, DesignDataLinkDefine>();
        if (oldLinkCache != null && mapType == 2) {
            for (DesignDataLinkDefine link : oldLinkCache.values()) {
                oldLinkPosCache.put(link.getPosX() + "_" + link.getPosY(), link);
            }
        }
        singleRegion.getFields().clear();
        for (ZBInfo zb : def.getZbsNoZDM()) {
            SingleFileFieldInfo singleField = singleRegion.getNewField();
            if (dataRegion != null) {
                singleField.setRegionKey(dataRegion.getKey());
                singleField.setRegionCode(dataRegion.getCode());
            }
            singleField.setFormCode(repInfo.getCode());
            singleField.setTableCode(repInfo.getCode());
            singleRegion.getFields().add(singleField);
            DesignFieldDefine fieldDefine = null;
            DesignDataLinkDefine linkDefine = null;
            if (isFMDM) {
                IFMDMAttribute attr;
                singleField.setEnumCode(zb.getEnumId());
                singleField.setFieldCode(zb.getFieldName());
                singleField.setFieldSize(zb.getLength());
                singleField.setFieldDecimal((int)zb.getDecimal());
                singleField.setDefaultValue(zb.getDefaultValue());
                singleField.setFieldType(this.paraImportService.getFieldType(zb.getDataType()));
                String linkExpression = zb.getFieldName();
                if (StringUtils.isNotEmpty((String)zb.getFieldName()) && zb.getFieldName().equalsIgnoreCase(importContext.getParaInfo().getFmRepInfo().getDWMCFieldName())) {
                    linkExpression = "NAME";
                }
                if (fmdmAttrMap.containsKey(linkExpression) && null != (attr = (IFMDMAttribute)fmdmAttrMap.get(linkExpression))) {
                    String aCode;
                    TableModelDefine tableModel;
                    singleField.setNetFieldCode(attr.getCode());
                    singleField.setNetFieldKey(attr.getZBKey());
                    singleField.setNetTableCode(attr.getCatagory());
                    if (StringUtils.isEmpty((String)attr.getEntityId()) && StringUtils.isNotEmpty((String)attr.getTableID()) && (tableModel = this.dataModelService.getTableModelDefineById(attr.getTableID())) != null) {
                        singleField.setNetTableCode(tableModel.getCode());
                    }
                    if (oldLinkCache.containsKey(aCode = attr.getCode())) {
                        linkDefine = oldLinkCache.get(aCode);
                    } else if (oldLinkCache.containsKey(attr.getID())) {
                        linkDefine = oldLinkCache.get(attr.getID());
                    }
                }
            } else {
                String aCode;
                if (mapType == 2) {
                    FieldInfoDefine fieldInfo;
                    String aCode2 = zb.getGridPos()[0] + "_" + zb.getGridPos()[1];
                    if (oldLinkPosCache.containsKey(aCode2) && (linkDefine = (DesignDataLinkDefine)oldLinkPosCache.get(aCode2)) != null && StringUtils.isNotEmpty((String)linkDefine.getLinkExpression()) && (fieldInfo = cacheList.getFieldDefine(linkDefine.getLinkExpression())) != null) {
                        fieldDefine = fieldInfo.getFieldDefine();
                    }
                    if (fieldDefine == null) {
                        log.info("\u6309\u5750\u6807\u5339\u914d\u6620\u5c04\u5931\u8d25\uff1a" + repInfo.getCode() + "," + aCode2 + "," + zb.getFieldName());
                    }
                } else if (mapType == 0) {
                    String mapZb = tableCode + "_" + zb.getFieldName();
                    if (fieldMap.containsKey(mapZb)) {
                        fieldDefine = (DesignFieldDefine)fieldMap.get(mapZb);
                    } else if (fieldMap.containsKey(repInfo.getCode() + "_" + zb.getFieldName())) {
                        fieldDefine = (DesignFieldDefine)fieldMap.get(repInfo.getCode() + "_" + zb.getFieldName());
                    } else if (fieldMap.containsKey(zb.getFieldName())) {
                        fieldDefine = (DesignFieldDefine)fieldMap.get(zb.getFieldName());
                    } else if (fieldMap2.containsKey(zb.getFieldName())) {
                        fieldDefine = (DesignFieldDefine)fieldMap2.get(zb.getFieldName());
                    } else if (fieldMap3.containsKey(zb.getFieldName())) {
                        fieldDefine = (DesignFieldDefine)fieldMap3.get(zb.getFieldName());
                    }
                }
                DesignTableDefine mapTableDefine = tableDefine;
                if (fieldDefine != null && cacheList.getTableIDMap().containsKey(fieldDefine.getOwnerTableKey())) {
                    mapTableDefine = cacheList.getTableIDMap().get(fieldDefine.getOwnerTableKey()).getTableDefine().getTableDefine();
                }
                if (fieldDefine != null && mapTableDefine == null) {
                    mapTableDefine = this.dataController.queryTableDefine(fieldDefine.getOwnerTableKey());
                }
                this.setFieldDefineAttr(importContext, def, fieldDefine, zb, mapTableDefine, isFMDM, singleField);
                if (!isRegionNew && fieldDefine != null && oldLinkCache.containsKey(aCode = fieldDefine.getKey())) {
                    linkDefine = oldLinkCache.get(aCode);
                }
            }
            if (linkDefine == null) continue;
            formLinksMap.put(linkDefine.getKey(), linkDefine);
            singleField.setNetDataLinkKey(linkDefine.getKey());
            singleField.setNetFormCode(formDefine.getFormCode());
        }
    }

    private void getTableFields(RegionTableList cacheList, DesignTableDefine tableDefine, String fileFlag, Map<String, DesignFieldDefine> fieldMap, Map<String, DesignFieldDefine> fieldMap2, Map<String, DesignFieldDefine> fieldMap3) throws JQException {
        List fieldList = this.dataController.getAllFieldsInTable(tableDefine.getKey());
        String tablePre = "";
        String tablePre2 = "";
        String tablePre3 = tableDefine.getCode() + "_";
        String fileFlag2 = fileFlag + "_";
        if (StringUtils.isNotEmpty((String)fileFlag) && tableDefine.getCode().startsWith(fileFlag2)) {
            tablePre = tableDefine.getCode().substring(fileFlag2.length());
            tablePre2 = tablePre + "_";
        }
        for (DesignFieldDefine field : fieldList) {
            String oldCode;
            fieldMap.put(field.getCode(), field);
            fieldMap2.put(field.getAlias(), field);
            cacheList.addFieldDefine(new FieldInfoDefine(field));
            if (StringUtils.isNotEmpty((String)tablePre) && field.getCode().startsWith(tablePre2)) {
                oldCode = field.getCode().substring(tablePre2.length());
                fieldMap3.put(oldCode, field);
                continue;
            }
            if (!StringUtils.isNotEmpty((String)tablePre3) || !field.getCode().startsWith(tablePre3)) continue;
            oldCode = field.getCode().substring(tablePre3.length());
            fieldMap3.put(oldCode, field);
        }
    }

    private void getOldFieldCode(DesignTableDefine tableDefine, String fileFlag, DesignFieldDefine field, Map<String, DesignFieldDefine> fieldMap3) {
        String tablePre = "";
        String tablePre2 = "";
        String tablePre3 = tableDefine.getCode() + "_";
        String fileFlag2 = fileFlag + "_";
        if (StringUtils.isNotEmpty((String)fileFlag) && tableDefine.getCode().startsWith(fileFlag2)) {
            tablePre = tableDefine.getCode().substring(fileFlag2.length());
            tablePre2 = tablePre + "_";
        }
        if (StringUtils.isNotEmpty((String)tablePre) && field.getCode().startsWith(tablePre2)) {
            String oldCode = field.getCode().substring(tablePre2.length());
            fieldMap3.put(oldCode, field);
        } else if (StringUtils.isNotEmpty((String)tablePre3) && field.getCode().startsWith(tablePre3)) {
            String oldCode = field.getCode().substring(tablePre3.length());
            fieldMap3.put(oldCode, field);
        }
    }

    private void setFormDefineAttr(TaskImportContext importContext, DesignFormDefine formDefine, RepInfo repInfo, boolean isFMDM, boolean isFormNew, SingleFileTableInfo singleTable) {
        singleTable.setSingleTableCode(repInfo.getCode());
        singleTable.setSingleTableTitle(repInfo.getTitle());
        singleTable.setSingleTableType(repInfo.getTableType().getValue());
        if (null != formDefine) {
            singleTable.setNetFormCode(formDefine.getFormCode());
            singleTable.setNetFormKey(formDefine.getKey());
        }
    }

    private void setDataRegionAttr(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion) {
        if (!isFixed) {
            singleRegion.setFloatingIndex(def.getRegionInfo().getMapArea().getFloatRangeStartNo());
            singleRegion.getFloatCodes().clear();
            if (!StringUtils.isEmpty((String)def.getRegionInfo().getKeyField())) {
                String[] floatCodes;
                for (String code : floatCodes = def.getRegionInfo().getKeyField().split(";")) {
                    singleRegion.getFloatCodes().add(code);
                }
            }
        }
        if (null != dataRegion) {
            singleRegion.setNetRegionKey(dataRegion.getKey());
        }
    }

    private void setFieldDefineAttr(TaskImportContext importContext, FieldDefs def, DesignFieldDefine fieldDefine, ZBInfo zb, DesignTableDefine tableDefine, boolean isFMDM, SingleFileFieldInfo singleField) {
        singleField.setEnumCode(zb.getEnumId());
        singleField.setFieldCode(zb.getFieldName());
        singleField.setFieldSize(zb.getLength());
        singleField.setFieldDecimal((int)zb.getDecimal());
        singleField.setDefaultValue(zb.getDefaultValue());
        singleField.setFieldType(this.paraImportService.getFieldType(zb.getDataType()));
        if (null != fieldDefine) {
            singleField.setNetFieldCode(fieldDefine.getCode());
            singleField.setNetFieldKey(fieldDefine.getKey());
            singleField.setNetTableCode(tableDefine.getCode());
        }
    }

    private IEntityModel getMasterEntity(IEntityModel entityMode) throws Exception {
        IEntityAttribute field = entityMode.getBizKeyField();
        if (StringUtils.isEmpty((String)field.getReferTableID())) {
            return entityMode;
        }
        IEntityModel parentEntityMode = this.entityMetaService.getEntityModel(field.getReferTableID());
        return this.getMasterEntity(parentEntityMode);
    }
}

