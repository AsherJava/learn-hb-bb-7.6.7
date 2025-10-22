/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl
 *  com.jiuqi.nr.definition.internal.impl.RegionEdgeStyleData
 *  com.jiuqi.nr.definition.util.LevelSetting
 *  com.jiuqi.nr.single.core.para.consts.FloatRegionType
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl;
import com.jiuqi.nr.definition.internal.impl.RegionEdgeStyleData;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.single.core.para.consts.FloatRegionType;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.RegionTableCache;
import nr.single.para.parain.internal.cache.RegionTableList;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.internal.service3.FormDefineImportServiceImpl;
import nr.single.para.parain.service.IFormFieldDefineImportService;
import nr.single.para.parain.service.IFormRegionDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FormRegionDefineImportServiceImpl
implements IFormRegionDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(FormDefineImportServiceImpl.class);
    public static final boolean FIELDNEEDSPLIT = false;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private IFormFieldDefineImportService formFieldService;

    @Override
    public void importFormRegions(TaskImportContext importContext, RepInfo repInfo, DesignFormDefine formDefine, boolean isFormNew, boolean isFMDM, SingleFileTableInfo singleTable, DesignDataGroup dataGroup, CompareDataFormDTO formCompare) throws Exception {
        this.makeFormInfoCache(importContext, repInfo, formDefine.getKey(), isFormNew);
        this.makFormInfoCacheByGroup(importContext, dataGroup);
        this.formFieldService.getFormGrid(importContext, formDefine);
        Map<String, DesignDataLinkDefine> oldLinkCache = importContext.getFormInfoCahche().getOldLinkCache();
        Map<String, DesignDataLinkDefine> formLinksMap = importContext.getFormInfoCahche().getFormLinksMap();
        List<String> deleteLinks = importContext.getFormInfoCahche().getDeleteLinks();
        List<DesignDataRegionDefine> insertRegions = importContext.getFormInfoCahche().getInsertRegions();
        List<DesignDataRegionDefine> updateRegions = importContext.getFormInfoCahche().getUpdateRegions();
        if (isFormNew) {
            FieldDefs def = repInfo.getDefs();
            DesignDataRegionDefine dataRegion = this.viewController.createDataRegionDefine();
            dataRegion.setCode(OrderGenerator.newOrder());
            this.setDataRegion(importContext, def, dataRegion, formDefine, repInfo, true, isFMDM, true, singleTable.getRegion(), dataGroup, formCompare);
            insertRegions.add(dataRegion);
            singleTable.getRegion().getSubRegions().clear();
            if (repInfo.isFloatTable()) {
                for (FieldDefs subdef : def.getSubMbs()) {
                    DesignDataRegionDefine subRegion = this.viewController.createDataRegionDefine();
                    subRegion.setCode(OrderGenerator.newOrder());
                    subRegion.setOwnerLevelAndId(importContext.getCurServerCode());
                    SingleFileRegionInfo aSubSingleRegion = singleTable.getRegion().getNewSubRegion();
                    singleTable.getRegion().getSubRegions().add(aSubSingleRegion);
                    this.setDataRegion(importContext, subdef, subRegion, formDefine, repInfo, false, isFMDM, true, aSubSingleRegion, dataGroup, formCompare);
                    insertRegions.add(subRegion);
                }
            }
        } else {
            DesignDataRegionDefine dataRegion;
            List<DesignDataRegionDefine> dataRegions = importContext.getFormInfoCahche().getFormRegions();
            List<String> deleteRegions = importContext.getFormInfoCahche().getDeleteRegions();
            int curIdex = 0;
            boolean isRegionNew = dataRegions.size() <= curIdex;
            FieldDefs def = repInfo.getDefs();
            DesignDataRegionDefine designDataRegionDefine = dataRegion = isRegionNew ? this.viewController.createDataRegionDefine() : dataRegions.get(curIdex);
            if (isRegionNew) {
                dataRegion.setCode(OrderGenerator.newOrder());
            }
            this.setDataRegion(importContext, def, dataRegion, formDefine, repInfo, true, isFMDM, isRegionNew, singleTable.getRegion(), dataGroup, formCompare);
            if (isRegionNew) {
                dataRegion.setOwnerLevelAndId(importContext.getCurServerCode());
                insertRegions.add(dataRegion);
            } else {
                updateRegions.add(dataRegion);
            }
            singleTable.getRegion().setFloatingIndex(-1);
            singleTable.getRegion().setNetRegionKey(dataRegion.getKey());
            singleTable.getRegion().getSubRegions().clear();
            ++curIdex;
            if (repInfo.isFloatTable()) {
                for (FieldDefs subdef : def.getSubMbs()) {
                    DesignDataRegionDefine subRegion;
                    isRegionNew = dataRegions.size() <= curIdex;
                    DesignDataRegionDefine designDataRegionDefine2 = subRegion = isRegionNew ? this.viewController.createDataRegionDefine() : dataRegions.get(curIdex);
                    if (isRegionNew) {
                        subRegion.setCode(OrderGenerator.newOrder());
                    }
                    SingleFileRegionInfo aSubSingleRegion = singleTable.getRegion().getNewSubRegion();
                    singleTable.getRegion().getSubRegions().add(aSubSingleRegion);
                    this.setDataRegion(importContext, subdef, subRegion, formDefine, repInfo, false, isFMDM, isRegionNew, aSubSingleRegion, dataGroup, formCompare);
                    if (isRegionNew) {
                        subRegion.setOwnerLevelAndId(importContext.getCurServerCode());
                        insertRegions.add(subRegion);
                    } else {
                        updateRegions.add(subRegion);
                    }
                    ++curIdex;
                }
            }
            for (int i = curIdex; i < dataRegions.size(); ++i) {
                deleteRegions.add(dataRegions.get(i).getKey());
            }
        }
        for (DesignDataLinkDefine link : oldLinkCache.values()) {
            if (formLinksMap.containsKey(link.getKey())) continue;
            deleteLinks.add(link.getKey());
        }
        this.updateFormInfoCacheToServer(importContext, true, true, true, true);
    }

    @Override
    public void updateFormInfoCacheToServer(TaskImportContext importContext, boolean fieldMustUpdate, boolean linkMustUpdate, boolean regionMustUpdate, boolean TableMustUpdate) throws Exception {
        this.formFieldService.updateFormInfoCacheToServer(importContext, fieldMustUpdate, linkMustUpdate, regionMustUpdate, TableMustUpdate);
    }

    private void makFormInfoCacheByGroup(TaskImportContext importContext, DesignDataGroup dataGroup) {
        List tables = this.dataSchemeService.getDataTableByGroup(dataGroup.getKey());
        if (tables.size() > 0) {
            for (DesignDataTable table : tables) {
                importContext.getFormInfoCahche().addTableToGroup(dataGroup.getKey(), new TableInfoDefine(table));
            }
        }
    }

    private void makeFormInfoCache(TaskImportContext importContext, RepInfo repInfo, String netformKey, boolean isFormNew) throws JQException {
        importContext.getFormInfoCahche().ClearData();
        List<DesignDataLinkDefine> oldLinkList = importContext.getFormInfoCahche().getOldLinkList();
        Map<String, DesignDataLinkDefine> oldLinkCache = importContext.getFormInfoCahche().getOldLinkCache();
        Map<String, DesignDataLinkDefine> formLinksMap = importContext.getFormInfoCahche().getFormLinksMap();
        oldLinkCache.clear();
        oldLinkList.clear();
        formLinksMap.clear();
        List<String> deleteLinks = importContext.getFormInfoCahche().getDeleteLinks();
        if (!isFormNew) {
            List dataRegions = this.viewController.getAllRegionsInForm(netformKey);
            importContext.getFormInfoCahche().setFormRegions(dataRegions);
            for (DesignDataRegionDefine region : dataRegions) {
                importContext.getFormInfoCahche().getRegionsInformCache().put(region.getKey(), region);
                List regionLinks = this.viewController.getAllLinksInRegion(region.getKey());
                oldLinkList.addAll(regionLinks);
                ArrayList<DesignDataField> fields = new ArrayList<DesignDataField>();
                ArrayList<DesignDataTable> tables = new ArrayList<DesignDataTable>();
                HashMap<String, DesignDataTable> mapTables = new HashMap<String, DesignDataTable>();
                ArrayList<String> fieldKeys = new ArrayList<String>();
                for (Object link : regionLinks) {
                    if (!StringUtils.isNotEmpty((String)link.getLinkExpression())) continue;
                    fieldKeys.add(link.getLinkExpression());
                }
                if (!fieldKeys.isEmpty()) {
                    Object link;
                    List regionFields = this.dataSchemeService.getDataFields(fieldKeys);
                    link = regionFields.iterator();
                    while (link.hasNext()) {
                        DesignDataTable table;
                        DesignDataField field = (DesignDataField)link.next();
                        if (field == null) continue;
                        fields.add(field);
                        if (mapTables.containsKey(field.getDataTableKey()) || (table = this.dataSchemeService.getDataTable(field.getDataTableKey())) == null) continue;
                        mapTables.put(table.getKey(), table);
                        tables.add(table);
                    }
                }
                importContext.getFormInfoCahche().getRegiogFieldCache().put(region.getKey(), FieldInfoDefine.getFieldInfos2(fields));
                importContext.getFormInfoCahche().getRegionTableCache().put(region.getKey(), TableInfoDefine.getFieldInfos2(tables));
                for (DesignDataTable table : tables) {
                    importContext.getFormInfoCahche().getTablesInformCache().put(table.getKey(), new TableInfoDefine(table));
                }
                for (DesignDataField field : fields) {
                    importContext.getFormInfoCahche().getFieldsInFormCache().put(field.getKey(), new FieldInfoDefine(field));
                }
            }
            HashMap<String, DesignDataLinkDefine> GridPosLinks = new HashMap<String, DesignDataLinkDefine>();
            for (DesignDataLinkDefine link : oldLinkList) {
                String aCode = String.format("%s_%s", link.getPosX(), link.getPosY());
                if (repInfo.getLinkZBMap().containsKey(link.getLinkExpression())) {
                    GridPosLinks.put(aCode, link);
                    oldLinkCache.put(link.getLinkExpression(), link);
                    continue;
                }
                if (!GridPosLinks.containsKey(aCode)) {
                    GridPosLinks.put(aCode, link);
                    oldLinkCache.put(link.getLinkExpression(), link);
                    continue;
                }
                deleteLinks.add(link.getKey());
            }
        }
    }

    private void setDataRegion(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion, DesignDataGroup dataGroup, CompareDataFormDTO formCompare) throws Exception {
        boolean hasSingleField;
        this.setDataRegionAttr(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion);
        boolean bl = hasSingleField = def.getZbsNoZDM().size() > 0;
        if (hasSingleField && !isFixed && def.getZbsNoZDM().size() == 1 && "SYS_ORDER".equalsIgnoreCase(((ZBInfo)def.getZbsNoZDM().get(0)).getFieldName())) {
            hasSingleField = false;
        }
        RegionTableList cacheList = new RegionTableList();
        if (hasSingleField) {
            singleRegion.getFields().clear();
            this.formFieldService.importRegionFields(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion, cacheList, def.getZbsNoZDM(), formCompare, dataGroup.getKey());
            Map<String, FieldInfoDefine> fieldMap = cacheList.getFieldMap();
            this.setDataRegionOtherAttr(def, dataRegion, fieldMap, cacheList, isFMDM, isFixed);
        }
        this.updateFormInfoCacheToServer(importContext, true, false, false, false);
        if (!hasSingleField) {
            if (isFixed) {
                log.info(repInfo.getCode() + "\u56fa\u5b9a\u533a\u57df\u65e0\u6307\u6807\uff0c\u4e0d\u521b\u5efa\u5b58\u50a8\u8868");
            } else {
                log.info(repInfo.getCode() + "\u6d6e\u52a8\u533a\u57df\u65e0\u6307\u6807\uff0c\u4e0d\u521b\u5efa\u5b58\u50a8\u8868\uff1a" + String.valueOf(def.getRegionInfo().getMapArea().getFloatRangeStartNo()));
            }
        }
        if (cacheList.isTableNew() && cacheList.getTableIDMap() != null) {
            List<String> deleteTableKeys = importContext.getFormInfoCahche().getDeleteTableKeys();
            for (RegionTableCache tableCache : cacheList.getTableIDMap().values()) {
                if (!tableCache.isTableNew()) continue;
                int minFieldCount = 0;
                if (tableCache.getTableDefine() != null) {
                    minFieldCount = tableCache.getTableDefine().getBizKeysCount();
                }
                if (tableCache.getFieldIDMap() == null || tableCache.getFieldIDMap().size() > minFieldCount || tableCache.getTableDefine() == null) continue;
                deleteTableKeys.add(tableCache.getTableDefine().getKey());
                importContext.getFormInfoCahche().removeTableKeyFormGroup(null, tableCache.getTableDefine().getKey());
                log.info("\u5220\u9664\u7a7a\u8868\uff1a" + tableCache.getTableDefine().getCode());
            }
        }
    }

    @Override
    public RegionTableCache getRegonTableCache(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion, RegionTableList cacheList, String curTableGroupKey, boolean newSub) throws Exception {
        return this.getRegonTableCache(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion, cacheList, curTableGroupKey, newSub, CompareTableType.TABLE_FIX);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public RegionTableCache getRegonTableCache(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion, RegionTableList cacheList, String curTableGroupKey, boolean newSub, CompareTableType tableType) throws Exception {
        List<TableInfoDefine> tables;
        int iCount;
        DesignDataTable findTableDefine;
        String tableCode = "";
        Object fieldGroup = null;
        DesignDataScheme dataScheme = importContext.getDataScheme();
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = dataScheme.getPrefix() + "_";
        }
        boolean fmdmIsData = false;
        DesignDataTable mdInfoTableDefine = null;
        if (isFMDM) {
            fmdmIsData = true;
        }
        if (isFixed) {
            tableCode = String.format("%s%s", fileFlag, formDefine.getFormCode());
            if (isFMDM) {
                if (fmdmIsData) {
                    if (tableType == CompareTableType.TABLE_FIX) {
                        tableCode = String.format("%s%s_JCB", fileFlag, "FMDM");
                    } else if (tableType == CompareTableType.TABLE_MDINFO) {
                        String tableKey = this.dataSchemeService.insertDataTableForMdInfo(dataScheme.getKey());
                        mdInfoTableDefine = this.dataSchemeService.getDataTable(tableKey);
                        tableCode = mdInfoTableDefine.getCode();
                    }
                } else {
                    tableCode = String.format("%s%s", fileFlag, "FMDM");
                }
            }
        } else {
            tableCode = String.format("%s%s_F%s", fileFlag, formDefine.getFormCode(), def.getRegionInfo().getMapArea().getFloatRangeStartNo());
        }
        DesignDataTable tableDefine = null;
        if (newSub) {
            findTableDefine = this.dataSchemeService.getDataTableByCode(tableCode);
            iCount = 1;
            String string = tableCode;
            while (findTableDefine != null) {
                tableCode = string + "_" + String.valueOf(iCount);
                findTableDefine = this.dataSchemeService.getDataTableByCode(tableCode);
                ++iCount;
            }
        } else {
            findTableDefine = this.dataSchemeService.getDataTableByCode(tableCode);
            if (findTableDefine != null) {
                if (dataScheme.getKey().equalsIgnoreCase(findTableDefine.getDataSchemeKey())) {
                    tableDefine = findTableDefine;
                } else {
                    iCount = 1;
                    String string = tableCode;
                    while (findTableDefine != null) {
                        tableCode = string + "_" + String.valueOf(iCount);
                        findTableDefine = this.dataSchemeService.getDataTableByCode(tableCode);
                        ++iCount;
                        if (findTableDefine == null) break;
                        if (!dataScheme.getKey().equalsIgnoreCase(findTableDefine.getDataSchemeKey())) continue;
                        tableDefine = findTableDefine;
                        break;
                    }
                }
            }
        }
        cacheList.setRegionTableCode(tableCode);
        cacheList.setFileFlag(dataScheme.getPrefix());
        if (tableDefine == null && importContext.getFormInfoCahche().getRegionTableCache().containsKey(dataRegion.getKey()) && (tables = importContext.getFormInfoCahche().getRegionTableCache().get(dataRegion.getKey())).size() > 0) {
            for (TableInfoDefine tableInfoDefine : tables) {
                if (!tableCode.equalsIgnoreCase(tableInfoDefine.getCode())) continue;
                tableDefine = tableInfoDefine.getDataTable();
                break;
            }
        }
        if (tableDefine == null) {
            tableDefine = this.dataSchemeService.getDataTableByCode(tableCode);
        }
        RegionTableCache curTableCache = null;
        cacheList.setTableNew(false);
        if (null == tableDefine) {
            if (!isFMDM || mdInfoTableDefine == null) {
                tableDefine = this.dataSchemeService.initDataTable();
                tableDefine.setCode(tableCode);
                String tableTitle = importContext.getFormInfoCahche().getTableTitleByGroupKey(curTableGroupKey, dataRegion.getTitle());
                tableDefine.setTitle(tableTitle);
                tableDefine.setDataSchemeKey(importContext.getDataSchemeKey());
                if (isFixed) {
                    tableDefine.setDataTableType(DataTableType.TABLE);
                } else {
                    tableDefine.setDataTableType(DataTableType.DETAIL);
                    tableDefine.setRepeatCode(Boolean.valueOf(!def.getRegionInfo().getKeyIsUnique()));
                    tableDefine.setDataTableGatherType(this.formFieldService.getTableGatherType(def, isFixed));
                }
                if (!isFMDM || fmdmIsData) {
                    tableDefine.setLevel(importContext.getCurServerCode());
                }
                tableDefine.setDataGroupKey(curTableGroupKey);
                this.dataSchemeService.insertDataTable(tableDefine);
                importContext.getFormInfoCahche().addTableToGroup(curTableGroupKey, new TableInfoDefine(tableDefine));
                importContext.getSchemeInfoCache().getTaskTableCache().put(tableDefine.getKey(), new TableInfoDefine(tableDefine));
            } else if (mdInfoTableDefine != null) {
                String tableKey = this.dataSchemeService.insertDataTableForMdInfo(dataScheme.getKey());
                tableDefine = this.dataSchemeService.getDataTable(tableKey);
                if (!importContext.getSchemeInfoCache().getTaskTableCache().containsKey(tableDefine.getKey())) {
                    importContext.getSchemeInfoCache().getTaskTableCache().put(tableDefine.getKey(), new TableInfoDefine(tableDefine));
                }
            }
            importContext.getFormInfoCahche().addTableToGroup(curTableGroupKey, new TableInfoDefine(tableDefine));
            curTableCache = cacheList.getNewCache(dataRegion, new TableInfoDefine(tableDefine));
            cacheList.setTableNew(true);
            curTableCache.setTableNew(true);
            fieldGroup = null;
            List fieldList = this.dataSchemeService.getDataFieldByTable(tableDefine.getKey());
            if (null != fieldList) {
                for (DesignDataField field : fieldList) {
                    FieldInfoDefine fieldInfo = new FieldInfoDefine(field);
                    cacheList.addFieldDefine(fieldInfo);
                }
            }
        } else {
            void var23_39;
            curTableCache = cacheList.getNewCache(dataRegion, new TableInfoDefine(tableDefine));
            if (!isFMDM && !importContext.getImportOption().isHistoryPara() && StringUtils.isNotEmpty((String)dataRegion.getTitle()) && !dataRegion.getTitle().equalsIgnoreCase(tableDefine.getTitle())) {
                String tableTitle = importContext.getFormInfoCahche().getTableTitleByGroupKey(curTableGroupKey, dataRegion.getTitle());
                if (StringUtils.isEmpty((String)tableDefine.getTitle())) {
                    tableDefine.setTitle(tableTitle);
                }
            }
            RegionTableCache newCache = null;
            Object var23_35 = null;
            fieldGroup = null;
            Object newFieldGroup = null;
            List fieldList = this.dataSchemeService.getDataFieldByTable(tableDefine.getKey());
            boolean needSplictOld = false;
            if (needSplictOld && (fieldList.size() > this.formFieldService.getMaxFieldCountInTable() + 10 || curTableCache.getFieldsSize(FieldInfoDefine.getFieldInfos2(fieldList)) + 100 > this.formFieldService.getMaxTableFieldSize())) {
                newCache = cacheList.getNewCache(curTableCache);
                DesignDataTable designDataTable = this.dataSchemeService.getDataTableByCode(newCache.getTableCode());
                if (null == designDataTable) {
                    DesignDataTable designDataTable2 = this.dataSchemeService.initDataTable();
                    designDataTable2.setCode(newCache.getTableCode());
                    String tableTitle = importContext.getFormInfoCahche().getTableTitleByGroupKey(curTableGroupKey, dataRegion.getTitle() + "\u5b50\u8868");
                    designDataTable2.setTitle(tableTitle);
                    designDataTable2.setLevel(importContext.getCurServerCode());
                    designDataTable2.setDataGroupKey(curTableGroupKey);
                    newCache.setTableDefine(new TableInfoDefine(designDataTable2));
                    cacheList.addTableCache(newCache);
                    designDataTable2.setDataTableGatherType(tableDefine.getDataTableGatherType());
                    designDataTable2.setDataTableType(tableDefine.getDataTableType());
                    this.dataSchemeService.insertDataTable(designDataTable2);
                    importContext.getFormInfoCahche().addTableToGroup(curTableGroupKey, new TableInfoDefine(tableDefine));
                    importContext.getSchemeInfoCache().getTaskTableCache().put(designDataTable2.getKey(), new TableInfoDefine(designDataTable2));
                } else {
                    newCache.setTableDefine(new TableInfoDefine(designDataTable));
                    cacheList.addTableCache(newCache);
                    List fieldList2 = this.dataSchemeService.getDataFieldByTable(designDataTable.getKey());
                    if (null != fieldList2) {
                        for (DesignDataField field : fieldList2) {
                            FieldInfoDefine fieldInfo = new FieldInfoDefine(field);
                            cacheList.addFieldDefine(fieldInfo);
                        }
                    }
                }
                newFieldGroup = null;
            }
            int aCount = 1;
            int aSize = 0;
            int aNewSize = 0;
            for (DesignDataField field : fieldList) {
                if (needSplictOld && (++aCount >= this.formFieldService.getMaxFieldCountInTable() || (aSize += curTableCache.getFieldSize(field)) >= this.formFieldService.getMaxTableFieldSize()) && null != var23_39) {
                    field.setDataTableKey(var23_39.getKey());
                }
                FieldInfoDefine fieldInfo = new FieldInfoDefine(field);
                cacheList.addFieldDefine(fieldInfo);
                aNewSize += curTableCache.getFieldSize(field);
            }
            if (null != var23_39) {
                tableDefine = var23_39;
                curTableCache = newCache;
                fieldGroup = newFieldGroup;
            }
        }
        curTableCache.setFieldGroup(fieldGroup);
        cacheList.setCurTableCache(curTableCache);
        return curTableCache;
    }

    private void setDataRegionAttr(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion) {
        boolean isFloatAreaNull;
        dataRegion.setFormKey(formDefine.getKey());
        boolean bl = isFloatAreaNull = def == null || def.getRegionInfo() == null || def.getRegionInfo().getMapArea() == null;
        if (isFloatAreaNull) {
            dataRegion.setTitle(formDefine.getTitle() + "\u6d6e\u52a8\u6570\u636e\u533a\u57df");
        } else if (isFixed) {
            dataRegion.setTitle(formDefine.getTitle() + "\u56fa\u5b9a\u6570\u636e\u533a\u57df");
        } else {
            dataRegion.setTitle(formDefine.getTitle() + def.getRegionInfo().getMapArea().getFloatRangeStartNo() + "\u6570\u636e\u533a\u57df");
        }
        dataRegion.setOrder(OrderGenerator.newOrder());
        dataRegion.setCanDeleteRow(true);
        if (!isFixed && def != null) {
            dataRegion.setAllowDuplicateKey(!def.getRegionInfo().getKeyIsUnique());
        } else {
            dataRegion.setAllowDuplicateKey(false);
        }
        if (isFixed) {
            dataRegion.setRegionKind(DataRegionKind.DATA_REGION_SIMPLE);
            dataRegion.setRegionLeft(1);
            dataRegion.setRegionRight(repInfo.getColCount() - 1);
            dataRegion.setRegionTop(1);
            dataRegion.setRegionBottom(repInfo.getRowCount() - 1);
        } else {
            boolean newFlag = false;
            boolean isRegionSetingChange = true;
            DesignRegionSettingDefine regionSettingDefine = null;
            if (dataRegion.getRegionSettingKey() != null) {
                regionSettingDefine = this.viewController.getRegionSetting(dataRegion.getRegionSettingKey());
            }
            if (regionSettingDefine == null) {
                newFlag = true;
                regionSettingDefine = this.viewController.createRegionSetting();
                dataRegion.setRegionSettingKey(regionSettingDefine.getKey());
            }
            int posX = 0;
            int posY = 0;
            FloatRegionType regionType = null;
            if (def != null && def.getRegionInfo() != null) {
                if (!isFloatAreaNull) {
                    regionType = def.getRegionInfo().getMapArea().getFloatRegionType();
                }
                if (regionType == FloatRegionType.FLOAT_DIRECTION_ROW_FLOAT) {
                    dataRegion.setRegionKind(DataRegionKind.DATA_REGION_ROW_LIST);
                    dataRegion.setRegionLeft(1);
                    dataRegion.setRegionRight(repInfo.getColCount() - 1);
                    if (!isFloatAreaNull) {
                        dataRegion.setRegionTop(def.getRegionInfo().getMapArea().getFloatRangeStartNo());
                        dataRegion.setRegionBottom(def.getRegionInfo().getMapArea().getFloatRangeEndNo());
                    }
                    posX = def.getRegionInfo().getNumberingColNum();
                    posY = dataRegion.getRegionTop();
                } else if (regionType == FloatRegionType.FLOAT_DIRECTION_COL_FLOAT) {
                    dataRegion.setRegionKind(DataRegionKind.DATA_REGION_COLUMN_LIST);
                    if (!isFloatAreaNull) {
                        dataRegion.setRegionLeft(def.getRegionInfo().getMapArea().getFloatRangeStartNo());
                        dataRegion.setRegionRight(def.getRegionInfo().getMapArea().getFloatRangeEndNo());
                    }
                    dataRegion.setRegionTop(1);
                    dataRegion.setRegionBottom(repInfo.getRowCount() - 1);
                    posX = dataRegion.getRegionLeft();
                    posY = def.getRegionInfo().getNumberingColNum();
                }
            }
            this.setLastRowColumnLineSyte(dataRegion, formDefine, regionSettingDefine);
            if (def != null && def.getRegionInfo() != null) {
                ArrayList<DesignRowNumberSettingImpl> rowNumberSettingList;
                dataRegion.setRowsInFloatRegion(def.getRegionInfo().getMinKeepRecCount());
                dataRegion.setGatherSetting(def.getRegionInfo().getSumLevel());
                if (def.getRegionInfo().getNumberingColNum() > 0) {
                    isRegionSetingChange = true;
                    rowNumberSettingList = new ArrayList<DesignRowNumberSettingImpl>();
                    DesignRowNumberSettingImpl designRowNumberSettingImpl = new DesignRowNumberSettingImpl();
                    designRowNumberSettingImpl.setPosX(posX);
                    designRowNumberSettingImpl.setPosY(posY);
                    designRowNumberSettingImpl.setStartNumber(1);
                    designRowNumberSettingImpl.setIncrement(1);
                    rowNumberSettingList.add(designRowNumberSettingImpl);
                    regionSettingDefine.setRowNumberSetting(rowNumberSettingList);
                } else if (!newFlag && (rowNumberSettingList = regionSettingDefine.getRowNumberSetting()) != null && !rowNumberSettingList.isEmpty()) {
                    rowNumberSettingList = new ArrayList();
                    regionSettingDefine.setRowNumberSetting(rowNumberSettingList);
                    isRegionSetingChange = true;
                }
            }
            if (isRegionSetingChange) {
                if (newFlag) {
                    this.viewController.addRegionSetting(regionSettingDefine);
                } else {
                    this.viewController.updateRegionSetting(regionSettingDefine);
                }
            }
        }
        if (!isFixed) {
            if (!isFloatAreaNull) {
                singleRegion.setFloatingIndex(def.getRegionInfo().getMapArea().getFloatRangeStartNo());
            }
            singleRegion.getFloatCodes().clear();
            if (def != null && def.getRegionInfo() != null && !StringUtils.isEmpty((String)def.getRegionInfo().getKeyField())) {
                String[] floatCodes;
                for (String code : floatCodes = def.getRegionInfo().getKeyField().split(";")) {
                    singleRegion.getFloatCodes().add(code);
                }
            }
        }
        singleRegion.setNetRegionKey(dataRegion.getKey());
    }

    private void setDataRegionOtherAttr(FieldDefs def, DesignDataRegionDefine dataRegion, Map<String, FieldInfoDefine> fieldMap, RegionTableList cacheList, boolean isFMDM, boolean isFixed) {
        LevelSetting levelSetting = null;
        String tableSumFields = "";
        String tableSumLeves = "";
        String fieldPrecisions = "";
        if (def.getRegionInfo() != null && StringUtils.isNotEmpty((String)def.getRegionInfo().getSumField()) && StringUtils.isNotEmpty((String)def.getRegionInfo().getSumLevel()) && !isFMDM && !isFixed) {
            String[] floatSumCodes = def.getRegionInfo().getSumField().split(";");
            String[] floatSumLevels = def.getRegionInfo().getSumLevel().split(",");
            int tableSumLen = 0;
            for (String floatSumCode : floatSumCodes) {
                if (fieldMap == null || !fieldMap.containsKey(floatSumCode)) continue;
                DesignDataField fieldDefine = fieldMap.get(floatSumCode).getDataField();
                if (StringUtils.isNotEmpty((String)tableSumFields)) {
                    tableSumFields = tableSumFields + ";";
                }
                tableSumFields = tableSumFields + fieldDefine.getKey();
                int startLevel = 0;
                String fieldSumLevel = "";
                for (String floatSumLevel : floatSumLevels) {
                    int len = 0;
                    try {
                        len = Integer.parseInt(floatSumLevel);
                    }
                    catch (Exception e) {
                        len = 0;
                    }
                    if (tableSumLen >= len || len > tableSumLen + fieldDefine.getPrecision()) continue;
                    fieldSumLevel = ++startLevel <= 1 ? fieldSumLevel + startLevel : fieldSumLevel + "," + startLevel;
                }
                fieldPrecisions = StringUtils.isEmpty((String)fieldPrecisions) ? String.valueOf(fieldDefine.getPrecision()) : fieldPrecisions + ";" + String.valueOf(fieldDefine.getPrecision());
                if (StringUtils.isNotEmpty((String)tableSumLeves)) {
                    tableSumLeves = tableSumLeves + ";";
                }
                if (StringUtils.isEmpty((String)fieldSumLevel)) {
                    fieldSumLevel = "1";
                }
                tableSumLeves = tableSumLeves + fieldSumLevel;
                tableSumLen += fieldDefine.getPrecision().intValue();
            }
            levelSetting = new LevelSetting();
            levelSetting.setType(1);
            String levelCodes = "";
            for (String floatSumLevel : floatSumLevels) {
                if (StringUtils.isNotEmpty((String)levelCodes)) {
                    levelCodes = levelCodes + ";";
                }
                levelCodes = levelCodes + floatSumLevel;
            }
            levelSetting.setCode(levelCodes);
            if (floatSumCodes.length > 1) {
                levelSetting.setPrecision(fieldPrecisions);
            } else {
                levelSetting.setPrecision(null);
            }
        }
        dataRegion.setGatherFields(tableSumFields);
        dataRegion.setHideZeroGatherFields(tableSumFields);
        dataRegion.setLevelSetting(levelSetting);
        if (StringUtils.isNotEmpty((String)tableSumFields)) {
            dataRegion.setShowGatherDetailRows(true);
            dataRegion.setShowGatherSummaryRow(false);
        }
    }

    private void setLastRowColumnLineSyte(DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, DesignRegionSettingDefine regionSettingDefine) {
        if (null == formDefine.getBinaryData()) {
            return;
        }
        Grid2Data rGrid = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
        ArrayList<RegionEdgeStyleDefine> rowStyles = new ArrayList<RegionEdgeStyleDefine>();
        RegionEdgeStyleDefine regionEdge = null;
        if (dataRegion.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
            for (int i = dataRegion.getRegionLeft(); i <= dataRegion.getRegionRight(); ++i) {
                RegionEdgeStyleDefine regionEdgeNew;
                GridCellData cell = rGrid.getGridCellData(i, dataRegion.getRegionBottom());
                if (null == cell) continue;
                int lineStype = cell.getBottomBorderStyle();
                int lineColor = cell.getBottomBorderColor();
                regionEdge = regionEdgeNew = this.getRegionEdgeLine(rowStyles, regionEdge, cell, i, lineStype, lineColor);
                cell.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                cell.setBottomBorderColor(-1);
            }
            regionSettingDefine.setLastRowStyle(rowStyles);
        } else if (dataRegion.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
            for (int i = dataRegion.getRegionTop(); i <= dataRegion.getRegionBottom(); ++i) {
                RegionEdgeStyleDefine regionEdgeNew;
                GridCellData cell = rGrid.getGridCellData(dataRegion.getRegionRight(), i);
                if (null == cell) continue;
                int lineStype = cell.getRightBorderStyle();
                int lineColor = cell.getRightBorderColor();
                regionEdge = regionEdgeNew = this.getRegionEdgeLine(rowStyles, regionEdge, cell, i, lineStype, lineColor);
                cell.setRightBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                cell.setRightBorderColor(-1);
            }
            regionSettingDefine.setLastColumnStyle(rowStyles);
        }
        formDefine.setBinaryData(Grid2Data.gridToBytes((Grid2Data)rGrid));
    }

    private RegionEdgeStyleDefine getRegionEdgeLine(List<RegionEdgeStyleDefine> rowStyles, RegionEdgeStyleDefine regionEdge, GridCellData cell, int index, int lineStype, int lineColor) {
        if (regionEdge == null) {
            regionEdge = new RegionEdgeStyleData();
            regionEdge.setStartIndex(index);
            regionEdge.setEndIndex(index);
            regionEdge.setEdgeLineStyle(lineStype);
            regionEdge.setEdgeLineColor(lineColor);
            rowStyles.add(regionEdge);
        } else if (regionEdge.getEdgeLineStyle() == lineStype && cell.getBottomBorderColor() == lineColor) {
            regionEdge.setEndIndex(index);
        } else {
            regionEdge = new RegionEdgeStyleData();
            regionEdge.setStartIndex(index);
            regionEdge.setEndIndex(index);
            regionEdge.setEdgeLineStyle(lineStype);
            regionEdge.setEdgeLineColor(lineColor);
            rowStyles.add(regionEdge);
        }
        return regionEdge;
    }
}

