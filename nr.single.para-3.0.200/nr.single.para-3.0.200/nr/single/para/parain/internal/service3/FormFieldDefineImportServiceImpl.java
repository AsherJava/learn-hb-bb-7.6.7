/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.facade.DesignFieldGroupDefine
 *  com.jiuqi.np.util.zip.ZipHelper
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.util.AttachmentObj
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportTableType
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  nr.single.map.common.ImportConsts
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.parain.internal.service3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.facade.DesignFieldGroupDefine;
import com.jiuqi.np.util.zip.ZipHelper;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.common.ImportConsts;
import nr.single.map.data.PathUtil;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.para.basedata.IBaseDataDefineService;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.ISingleCompareDataFMDMFieldService;
import nr.single.para.compare.definition.ISingleCompareDataFieldService;
import nr.single.para.compare.definition.ISingleCompareMapFieldService;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.defintion.CompareDataFMDMFieldDO;
import nr.single.para.parain.internal.cache.FieldInfoCache;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.RegionTableCache;
import nr.single.para.parain.internal.cache.RegionTableList;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.internal.service3.FormDefineImportServiceImpl;
import nr.single.para.parain.service.IAttachmentFileImportService;
import nr.single.para.parain.service.IFormFieldDefineImportService;
import nr.single.para.parain.service.IFormRegionDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class FormFieldDefineImportServiceImpl
implements IFormFieldDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(FormDefineImportServiceImpl.class);
    public static final boolean FIELDNEEDSPLIT = false;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private IAttachmentFileImportService attachMenentService;
    @Value(value="${jiuqi.nr.jio.splitcount:950}")
    private String splitcount;
    @Value(value="${jiuqi.nr.jio.splittablesize:6553500}")
    private int splitTableSize;
    @Value(value="${jiuqi.nr.jio.fieldprefixtype:0}")
    private int fieldPrefixType;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IBaseDataDefineService baseDefineService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private ISingleCompareDataFieldService fieldCompareService;
    @Autowired
    private ISingleCompareDataFMDMFieldService fmdmdFieldCompareService;
    @Autowired
    private ISingleCompareMapFieldService mapFieldService;
    @Autowired
    private IFormRegionDefineImportService formRegionService;
    @Autowired
    private DesignFormDefineService formService;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void importRegionFields(TaskImportContext importContext, FieldDefs def, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, RepInfo repInfo, boolean isFixed, boolean isFMDM, boolean isRegionNew, SingleFileRegionInfo singleRegion, RegionTableList cacheList, List<ZBInfo> singleZbs, CompareDataFormDTO formCompare, String dataGroupKey) throws Exception {
        TableInfoDefine findTable;
        boolean fmdmHasMdFeild;
        boolean fmdmIsData;
        String entityTableCode;
        HashMap<String, IEntityAttribute> fmdmFieldMap;
        Map<String, FieldInfoDefine> fieldMap;
        Map<String, DesignDataLinkDefine> formLinksMap;
        List<DesignDataLinkDefine> updateLinks;
        List<DesignDataLinkDefine> insertLinks;
        List<DesignDataField> updateFields;
        List<DesignDataField> insertFields;
        DesignDataTable tableDefine;
        DesignFieldGroupDefine fieldGroup;
        RegionTableCache dataTableCache;
        RegionTableCache mdTableCache;
        RegionTableCache curTableCache;
        ParaImportInfoResult fmdmLog;
        ParaImportInfoResult fieldsLog;
        HashMap<String, CompareDataFMDMFieldDTO> oldFMDMFieldCompareDic;
        HashMap<String, CompareDataFieldDTO> oldFieldCompareDic;
        block123: {
            CompareDataDO fieldDataCompare;
            ZBInfo zb;
            Iterator<ZBInfo> fieldCache2;
            block131: {
                RegionTableCache tableCache;
                DesignDataField fieldDefine;
                block130: {
                    IEntityAttribute entityAttribute;
                    block128: {
                        block129: {
                            block127: {
                                oldFieldCompareDic = new HashMap<String, CompareDataFieldDTO>();
                                oldFMDMFieldCompareDic = new HashMap<String, CompareDataFMDMFieldDTO>();
                                fieldsLog = null;
                                fmdmLog = null;
                                if (importContext.getCompareInfo() != null) {
                                    if (isFMDM) {
                                        oldFMDMFieldCompareDic.putAll(this.getCompareFMDMFields(importContext.getCompareInfo().getKey()));
                                    } else if (formCompare != null) {
                                        oldFieldCompareDic.putAll(this.getCompareDataFields(importContext.getCompareInfo().getKey(), formCompare.getKey()));
                                        if (!this.getIsNeedInsertOrUdpateTable(singleZbs, oldFieldCompareDic)) {
                                            log.info("\u65e0\u6307\u6807\u65b0\u589e\u6216\u66f4\u65b0\uff0c\u4e0d\u505a\u5bfc\u5165");
                                            return;
                                        }
                                    }
                                    if (importContext.getImportResult() != null) {
                                        fieldsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_FIELD, "fields", "\u6307\u6807");
                                        fmdmLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_FMDMFIELD, "FMDM", "\u5c01\u9762\u4ee3\u7801");
                                        fmdmLog.setCompareInfoKey(importContext.getCompareInfo().getKey());
                                        fieldsLog.setCompareInfoKey(importContext.getCompareInfo().getKey());
                                    }
                                }
                                curTableCache = cacheList.getCurTableCache();
                                mdTableCache = null;
                                dataTableCache = null;
                                fieldGroup = null;
                                tableDefine = null;
                                insertFields = importContext.getFormInfoCahche().getInsertDataFields();
                                updateFields = importContext.getFormInfoCahche().getUpdateDataFields();
                                insertLinks = importContext.getFormInfoCahche().getInsertLinks();
                                updateLinks = importContext.getFormInfoCahche().getUpdateLinks();
                                formLinksMap = importContext.getFormInfoCahche().getFormLinksMap();
                                fieldMap = cacheList.getFieldMap();
                                fmdmFieldMap = new HashMap<String, IEntityAttribute>();
                                entityTableCode = "";
                                fmdmIsData = false;
                                fmdmHasMdFeild = false;
                                if (isFMDM) {
                                    fmdmIsData = true;
                                    IEntityModel entityModel = this.entityMetaService.getEntityModel(importContext.getEntityId());
                                    if (entityModel == null) {
                                        throw new FMDMQueryException(String.format("\u627e\u4e0d\u5230'%s'\u7684\u5b9e\u4f53\u6a21\u578b", importContext.getEntityId()));
                                    }
                                    entityTableCode = EntityUtils.getId((String)importContext.getEntityId());
                                    Iterator attributes = entityModel.getAttributes();
                                    while (attributes.hasNext()) {
                                        entityAttribute = (IEntityAttribute)attributes.next();
                                        fmdmFieldMap.put(entityAttribute.getCode(), entityAttribute);
                                    }
                                }
                                findTable = null;
                                if (!importContext.getImportOption().isHistoryPara() || isFixed) break block127;
                                fieldCache2 = new FieldInfoCache();
                                entityAttribute = singleZbs.iterator();
                                break block128;
                            }
                            if (isFixed || formCompare == null) break block129;
                            fieldCache2 = singleZbs.iterator();
                            break block130;
                        }
                        if (!isFMDM || importContext.getCompareInfo() == null) break block123;
                        fieldCache2 = singleZbs.iterator();
                        break block131;
                    }
                    while (entityAttribute.hasNext()) {
                        ZBInfo zb2 = entityAttribute.next();
                        if ("SYS_ORDER".equalsIgnoreCase(zb2.getFieldName())) continue;
                        fieldDefine = null;
                        ((FieldInfoCache)((Object)fieldCache2)).setFieldNew(false);
                        ((FieldInfoCache)((Object)fieldCache2)).setFieldTitle(zb2.getZbTitle());
                        ((FieldInfoCache)((Object)fieldCache2)).setFieldCode(zb2.getFieldName());
                        fieldDefine = this.FindFieldBySingleZb(importContext, zb2, dataRegion.getTitle(), (FieldInfoCache)((Object)fieldCache2), null);
                        if (fieldDefine == null) continue;
                        tableDefine = this.dataSchemeService.getDataTable(fieldDefine.getDataTableKey());
                        tableCache = this.getMapTableCache(importContext, dataRegion, tableDefine, cacheList);
                        cacheList.setCurTableCache(tableCache);
                        curTableCache = cacheList.getCurTableCache();
                        fieldGroup = curTableCache.getFieldGroup();
                        tableDefine = curTableCache.getTableDefine().getDataTable();
                        findTable = curTableCache.getTableDefine();
                        break block123;
                    }
                    break block123;
                }
                while (fieldCache2.hasNext()) {
                    zb = fieldCache2.next();
                    if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName()) || !oldFieldCompareDic.containsKey(zb.getFieldName()) || (fieldDataCompare = (CompareDataFieldDTO)oldFieldCompareDic.get(zb.getFieldName())).getUpdateType() == CompareUpdateType.UPDATE_NEW || fieldDataCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE || !StringUtils.isNotEmpty((CharSequence)fieldDataCompare.getNetKey()) || (fieldDefine = this.dataSchemeService.getDataField(fieldDataCompare.getNetKey())) == null) continue;
                    tableDefine = this.dataSchemeService.getDataTable(fieldDefine.getDataTableKey());
                    tableCache = this.getMapTableCache(importContext, dataRegion, tableDefine, cacheList);
                    cacheList.setCurTableCache(tableCache);
                    curTableCache = cacheList.getCurTableCache();
                    fieldGroup = curTableCache.getFieldGroup();
                    tableDefine = curTableCache.getTableDefine().getDataTable();
                    findTable = curTableCache.getTableDefine();
                    break;
                }
                if (findTable == null) {
                    curTableCache = this.formRegionService.getRegonTableCache(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion, cacheList, dataGroupKey, true);
                    fieldGroup = curTableCache.getFieldGroup();
                    tableDefine = curTableCache.getTableDefine().getDataTable();
                    fieldMap = cacheList.getFieldMap();
                    singleRegion.getFields().clear();
                }
                break block123;
            }
            while (fieldCache2.hasNext()) {
                zb = fieldCache2.next();
                if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName()) || !oldFMDMFieldCompareDic.containsKey(zb.getFieldName()) || ((CompareDataFMDMFieldDO)(fieldDataCompare = (CompareDataFMDMFieldDTO)oldFMDMFieldCompareDic.get(zb.getFieldName()))).getOwnerTableType() != CompareTableType.TABLE_MDINFO) continue;
                fmdmHasMdFeild = true;
                break;
            }
        }
        if (curTableCache == null) {
            curTableCache = this.formRegionService.getRegonTableCache(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion, cacheList, dataGroupKey, false);
            fieldGroup = curTableCache.getFieldGroup();
            tableDefine = curTableCache.getTableDefine().getDataTable();
            fieldMap = cacheList.getFieldMap();
            singleRegion.getFields().clear();
            if (fmdmHasMdFeild) {
                mdTableCache = this.formRegionService.getRegonTableCache(importContext, def, dataRegion, formDefine, repInfo, isFixed, isFMDM, isRegionNew, singleRegion, cacheList, dataGroupKey, false, CompareTableType.TABLE_MDINFO);
            }
        }
        dataTableCache = curTableCache;
        if (!isFMDM || fmdmIsData) {
            if (tableDefine != null) {
                tableDefine.setDataTableGatherType(this.getTableGatherType(def, isFixed));
                tableDefine.setUpdateTime(Instant.now());
                this.dataSchemeService.updateDataTable(tableDefine);
            }
            if (fmdmHasMdFeild && mdTableCache != null) {
                DesignDataTable mdTableDefine = mdTableCache.getTableDefine().getDataTable();
                mdTableDefine.setDataTableGatherType(this.getTableGatherType(def, isFixed));
                mdTableDefine.setUpdateTime(Instant.now());
            }
        }
        ArrayList<CompareMapFieldDTO> addMapFields = new ArrayList<CompareMapFieldDTO>();
        Grid2Data rGrid = this.getFormGrid(importContext, formDefine);
        Map<String, TableInfoDefine> taskTableCache = importContext.getSchemeInfoCache().getTaskTableCache();
        Map<String, DesignDataLinkDefine> oldLinkCache = importContext.getFormInfoCahche().getOldLinkCache();
        FieldInfoCache fieldCache = new FieldInfoCache();
        fieldCache.setNeedPrefix(isFixed && !isFMDM);
        if (importContext.isUniqueField() && !isFMDM && isFixed && this.fieldPrefixType == 1 && repInfo.getTableType() != ReportTableType.RTT_BLOBTABLE && repInfo.getTableType() != ReportTableType.RTT_WORDTABLE) {
            fieldCache.setNeedPrefix(false);
        }
        boolean reportIsChange = false;
        int aIndex = 0;
        for (ZBInfo zb : singleZbs) {
            GridCellData cellData;
            boolean isLinkNew;
            boolean isFieldNew;
            DesignDataLinkDefine linkDefine;
            block143: {
                DesignDataField fieldDefine;
                block142: {
                    String linkExpression;
                    SingleFileFieldInfo singleField;
                    String fieldName;
                    boolean fmdmFieldIsMdInfo;
                    block132: {
                        String aCode;
                        String floatCode;
                        DesignDataTable mapTableDefine;
                        ParaImportInfoResult fieldLog;
                        block134: {
                            boolean needFieldUpdate;
                            block137: {
                                block138: {
                                    block141: {
                                        DesignDataField oldField;
                                        block140: {
                                            block139: {
                                                List fields;
                                                List<FieldInfoDefine> fieldInfos;
                                                HashMap<String, DesignDataField> MapFields;
                                                boolean isFieldHasData;
                                                CompareDataFieldDTO fieldDataCompare;
                                                block133: {
                                                    CompareMapFieldDTO mapFieldData;
                                                    block136: {
                                                        block135: {
                                                            DesignDataTable table;
                                                            DataTable runtimeDataTable;
                                                            CompareDataFMDMFieldDTO fieldFMDMCompare;
                                                            CompareDataDO fieldCompare;
                                                            boolean fmdmFieldIsData;
                                                            block125: {
                                                                if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName())) continue;
                                                                fmdmFieldIsData = false;
                                                                fmdmFieldIsMdInfo = false;
                                                                fieldName = zb.getFieldName();
                                                                fieldCompare = null;
                                                                fieldFMDMCompare = null;
                                                                fieldDataCompare = null;
                                                                fieldLog = null;
                                                                if (isFMDM) {
                                                                    if (oldFMDMFieldCompareDic.containsKey(fieldName)) {
                                                                        block124: {
                                                                            fieldFMDMCompare = (CompareDataFMDMFieldDTO)oldFMDMFieldCompareDic.get(fieldName);
                                                                            fieldCompare = fieldFMDMCompare;
                                                                            if (fmdmLog != null) {
                                                                                fieldLog = new ParaImportInfoResult();
                                                                                fieldLog.copyForm(fieldFMDMCompare);
                                                                                if (formCompare != null) {
                                                                                    fieldLog.setCode(formCompare.getSingleCode() + "_" + fieldLog.getNetCode());
                                                                                }
                                                                                fieldLog.setSuccess(true);
                                                                                fmdmLog.addItem(fieldLog);
                                                                            }
                                                                            boolean bl = fmdmFieldIsData = fieldFMDMCompare.getOwnerTableType() == CompareTableType.TABLE_FIX || fieldFMDMCompare.getOwnerTableType() == CompareTableType.TABLE_MDINFO;
                                                                            if (fieldFMDMCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                                                                                log.info("\u6307\u6807\uff1a" + fieldName + ",\u5ffd\u7565\u5bfc\u5165");
                                                                                continue;
                                                                            }
                                                                            if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) {
                                                                                log.info("\u6307\u6807\uff1a" + fieldName + ",\u4e0d\u8986\u76d6\u5bfc\u5165");
                                                                                continue;
                                                                            }
                                                                            if (fieldCompare.getUpdateType() != CompareUpdateType.UPDATE_NEW) {
                                                                                if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                                                                                    if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                                                                                        fieldName = fieldCompare.getNetCode();
                                                                                        break block124;
                                                                                    } else {
                                                                                        log.info("\u6307\u6807\uff1a" + fieldName + ",\u91cd\u65b0\u7f16\u7801\u7684\u6307\u6807\u4ee3\u7801\u4e3a\u7a7a\uff1a" + fieldCompare.getNetCode());
                                                                                        continue;
                                                                                    }
                                                                                }
                                                                                if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATA_USENET) {
                                                                                    if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                                                                                        fieldName = fieldCompare.getNetCode();
                                                                                        break block124;
                                                                                    } else {
                                                                                        log.info("\u6307\u6807\uff1a" + fieldName + ",\u4ee5\u7f51\u62a5\u4e3a\u51c6\u7684\u6307\u6807\u4ee3\u7801\u4e3a\u7a7a\uff1a" + fieldCompare.getNetCode());
                                                                                        continue;
                                                                                    }
                                                                                }
                                                                                if (fieldCompare.getUpdateType() != CompareUpdateType.UPDATE_OVER && fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                                                                                    if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                                                                                        fieldName = fieldCompare.getNetCode();
                                                                                    } else {
                                                                                        log.info("\u6307\u6807\uff1a" + fieldName + ",\u6307\u5b9a\u7684\u6307\u6807\u4e0d\u5b58\u5728\uff1a" + fieldCompare.getNetCode());
                                                                                        continue;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        if (fieldFMDMCompare.getOwnerTableType() == CompareTableType.TABLE_MDINFO && mdTableCache != null) {
                                                                            curTableCache = mdTableCache;
                                                                            fieldGroup = curTableCache.getFieldGroup();
                                                                            tableDefine = curTableCache.getTableDefine().getDataTable();
                                                                            fieldMap = cacheList.getFieldMap();
                                                                        } else {
                                                                            curTableCache = dataTableCache;
                                                                            fieldGroup = curTableCache.getFieldGroup();
                                                                            tableDefine = curTableCache.getTableDefine().getDataTable();
                                                                            fieldMap = cacheList.getFieldMap();
                                                                        }
                                                                        cacheList.setCurTableCache(curTableCache);
                                                                    }
                                                                } else if (oldFieldCompareDic.containsKey(fieldName)) {
                                                                    fieldDataCompare = (CompareDataFieldDTO)oldFieldCompareDic.get(fieldName);
                                                                    fieldCompare = fieldDataCompare;
                                                                    if (fieldsLog != null) {
                                                                        fieldLog = new ParaImportInfoResult();
                                                                        fieldLog.copyForm(fieldCompare);
                                                                        if (formCompare != null) {
                                                                            fieldLog.setCode(formCompare.getSingleCode() + "_" + fieldLog.getNetCode());
                                                                        }
                                                                        fieldLog.setSuccess(true);
                                                                        fieldLog.setParentCompareKey(formCompare.getKey());
                                                                        fieldsLog.addItem(fieldLog);
                                                                    }
                                                                    if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                                                                        log.info("\u6307\u6807\uff1a" + fieldName + ",\u5ffd\u7565\u5bfc\u5165");
                                                                        continue;
                                                                    }
                                                                    if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) {
                                                                        log.info("\u6307\u6807\uff1a" + fieldName + ",\u4e0d\u8986\u76d6\u5bfc\u5165");
                                                                        continue;
                                                                    }
                                                                    if (fieldCompare.getUpdateType() != CompareUpdateType.UPDATE_NEW) {
                                                                        if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                                                                            if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                                                                                fieldName = fieldCompare.getNetCode();
                                                                                break block125;
                                                                            } else {
                                                                                log.info("\u6307\u6807\uff1a" + fieldName + ",\u91cd\u65b0\u7f16\u7801\u7684\u6307\u6807\u4ee3\u7801\u4e3a\u7a7a\uff1a" + fieldCompare.getNetCode());
                                                                                continue;
                                                                            }
                                                                        }
                                                                        if (fieldCompare.getUpdateType() != CompareUpdateType.UPDATE_OVER && fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                                                                            if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                                                                                fieldName = fieldCompare.getNetCode();
                                                                            } else {
                                                                                log.info("\u6307\u6807\uff1a" + fieldName + ",\u6307\u5b9a\u7684\u6307\u6807\u4ee3\u7801\u7a7a\uff1a" + fieldCompare.getNetCode());
                                                                                continue;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            singleField = singleRegion.getNewField();
                                                            if (!"PARENTCODE".equalsIgnoreCase(fieldName) && !"ORGCODE".equalsIgnoreCase(fieldName)) {
                                                                singleRegion.getFields().add(singleField);
                                                            }
                                                            fieldDefine = null;
                                                            fieldCache.setFieldNew(false);
                                                            linkDefine = null;
                                                            linkExpression = null;
                                                            ++aIndex;
                                                            if (isFMDM && !fmdmFieldIsData) {
                                                                linkExpression = fieldName;
                                                                if (StringUtils.isNotEmpty((CharSequence)zb.getFieldName()) && zb.getFieldName().equalsIgnoreCase(importContext.getParaInfo().getFmRepInfo().getDWMCFieldName())) {
                                                                    linkExpression = "NAME";
                                                                }
                                                                if (fieldFMDMCompare != null && (fieldFMDMCompare.getUpdateType() == CompareUpdateType.UPDATA_USENET || fieldFMDMCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW || fieldFMDMCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT || fieldFMDMCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE) && StringUtils.isNotEmpty((CharSequence)fieldFMDMCompare.getNetCode())) {
                                                                    linkExpression = fieldFMDMCompare.getNetCode();
                                                                }
                                                                if (oldLinkCache.containsKey(linkExpression = this.replaceSysChar(entityTableCode, linkExpression))) {
                                                                    linkDefine = oldLinkCache.get(linkExpression);
                                                                }
                                                            } else if (importContext.getImportOption().isHistoryPara()) {
                                                                fieldCache.setFieldTitle(zb.getZbTitle());
                                                                fieldCache.setFieldCode(zb.getFieldName());
                                                                fieldDefine = this.FindFieldBySingleZb(importContext, zb, dataRegion.getTitle(), fieldCache, findTable);
                                                                if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName())) {
                                                                    fieldDefine = null;
                                                                    fieldCache.setFieldDefine(null);
                                                                    fieldCache.getMapFields().clear();
                                                                }
                                                                if (fieldDefine == null && fieldCache.getMapFields().size() <= 0) {
                                                                    fieldDefine = this.createNewField(importContext, dataRegion, formDefine, zb, cacheList, fieldCache, fieldCompare, formCompare);
                                                                } else if (fieldDefine != null) {
                                                                    // empty if block
                                                                }
                                                            } else {
                                                                fieldDefine = this.createNewField(importContext, dataRegion, formDefine, zb, cacheList, fieldCache, fieldCompare, formCompare);
                                                            }
                                                            isFieldNew = fieldCache.isFieldNew();
                                                            if (fieldDefine == null) break block132;
                                                            curTableCache.getSingleFields().add(zb.getFieldName());
                                                            mapTableDefine = tableDefine;
                                                            if (taskTableCache.containsKey(fieldDefine.getDataTableKey())) {
                                                                mapTableDefine = taskTableCache.get(fieldDefine.getDataTableKey()).getDataTable();
                                                            } else if (cacheList.getTableIDMap().containsKey(fieldDefine.getDataTableKey())) {
                                                                mapTableDefine = cacheList.getTableIDMap().get(fieldDefine.getDataTableKey()).getTableDefine().getDataTable();
                                                                if (mapTableDefine == null) {
                                                                    mapTableDefine = tableDefine;
                                                                }
                                                            } else if (!isFieldNew) {
                                                                mapTableDefine = this.dataSchemeService.getDataTable(fieldDefine.getDataTableKey());
                                                                if (mapTableDefine == null) {
                                                                    mapTableDefine = tableDefine;
                                                                } else {
                                                                    taskTableCache.put(mapTableDefine.getKey(), new TableInfoDefine(mapTableDefine));
                                                                }
                                                            }
                                                            isFieldHasData = false;
                                                            MapFields = new HashMap<String, DesignDataField>();
                                                            if (!isFieldNew && mapTableDefine != null && (runtimeDataTable = this.runtimeDataSchemeService.getDataTable(mapTableDefine.getKey())) != null) {
                                                                TableInfoDefine tableInfo = null;
                                                                if (importContext.getSchemeInfoCache().getTaskTableCache().containsKey(runtimeDataTable.getKey())) {
                                                                    tableInfo = importContext.getSchemeInfoCache().getTaskTableCache().get(runtimeDataTable.getKey());
                                                                } else {
                                                                    tableInfo = new TableInfoDefine(mapTableDefine);
                                                                    importContext.getSchemeInfoCache().getTaskTableCache().put(mapTableDefine.getKey(), tableInfo);
                                                                }
                                                                if (tableInfo.getHasData() == null) {
                                                                    isFieldHasData = this.runtimeDataSchemeService.dataTableCheckData(new String[]{runtimeDataTable.getKey()});
                                                                    tableInfo.setHasData(isFieldHasData);
                                                                } else {
                                                                    isFieldHasData = tableInfo.getHasData();
                                                                }
                                                                if (isFieldHasData && !MapFields.containsKey(fieldDefine.getKey())) {
                                                                    if (importContext.getSchemeInfoCache().getTableFieldCache().containsKey(mapTableDefine.getKey())) {
                                                                        fieldInfos = importContext.getSchemeInfoCache().getTableFieldCache().get(mapTableDefine.getKey());
                                                                        for (FieldInfoDefine fieldInfo : fieldInfos) {
                                                                            MapFields.put(fieldInfo.getKey(), fieldInfo.getDataField());
                                                                        }
                                                                    } else {
                                                                        fields = this.dataSchemeService.getDataFieldByTable(mapTableDefine.getKey());
                                                                        for (DesignDataField field : fields) {
                                                                            MapFields.put(field.getKey(), field);
                                                                        }
                                                                        importContext.getSchemeInfoCache().getTableFieldCache().put(mapTableDefine.getKey(), FieldInfoDefine.getFieldInfos2(fields));
                                                                    }
                                                                }
                                                            }
                                                            this.setFieldDefineAttr(importContext, def, fieldDefine, zb, mapTableDefine, isFMDM, singleField, isFieldNew, fmdmFieldIsData, fieldDataCompare, isFieldHasData);
                                                            String fieldJEDW = zb.getOtherJedw();
                                                            if (isFMDM && zb.getJEDW()) {
                                                                fieldJEDW = "\u5143";
                                                            }
                                                            if (isFMDM && mapTableDefine != null && mapTableDefine.getDataTableType() == DataTableType.MD_INFO) {
                                                                fmdmFieldIsMdInfo = true;
                                                            }
                                                            fieldDefine.setMeasureUnit(this.getfieldMeasureUnit(importContext, formDefine.getMeasureUnit(), repInfo.getMoneyUnit(), fieldJEDW, zb.getJEDW()));
                                                            if (!isFieldNew) break block133;
                                                            fieldDefine.setLevel(importContext.getCurServerCode());
                                                            if (fieldDefine.getAllowMultipleSelect() == null) {
                                                                fieldDefine.setAllowMultipleSelect(Boolean.valueOf(false));
                                                            }
                                                            insertFields.add(fieldDefine);
                                                            if (fieldDataCompare == null) break block134;
                                                            if (StringUtils.isNotEmpty((CharSequence)fieldDataCompare.getNetTableKey()) && (table = this.dataSchemeService.getDataTable(fieldDataCompare.getNetTableKey())) != null) {
                                                                fieldDefine.setDataTableKey(table.getKey());
                                                                singleField.setNetTableCode(table.getCode());
                                                                log.info("\u6307\u6807\uff1a" + fieldName + ",\u6307\u5b9a\u65b0\u589e\u5230\u6307\u6807\u8868\u4e0b\uff1a" + table.getCode() + "," + table.getKey());
                                                            }
                                                            mapFieldData = new CompareMapFieldDTO();
                                                            mapFieldData.setKey(UUID.randomUUID().toString());
                                                            mapFieldData.setDataSchemeKey(importContext.getDataSchemeKey());
                                                            mapFieldData.setFieldKey(fieldDefine.getKey());
                                                            if (!StringUtils.isNotEmpty((CharSequence)fieldDataCompare.getSingleMatchTitle())) break block135;
                                                            mapFieldData.setMatchTitle(fieldDataCompare.getSingleMatchTitle());
                                                            if (StringUtils.isEmpty((CharSequence)fieldDefine.getTitle()) || fieldDefine.getTitle().equalsIgnoreCase(fieldDefine.getAlias())) {
                                                                String fieldTitle = this.getFieldNewTitle(repInfo.getTitle(), fieldDataCompare.getSingleMatchTitle());
                                                                fieldDefine.setTitle(fieldTitle);
                                                                if (StringUtils.isEmpty((CharSequence)fieldDefine.getTitle())) {
                                                                    fieldDefine.setTitle(zb.getFieldName());
                                                                }
                                                            }
                                                            break block136;
                                                        }
                                                        log.info("\u65e0\u5339\u914d\u6807\u9898");
                                                    }
                                                    addMapFields.add(mapFieldData);
                                                    break block134;
                                                }
                                                boolean bl = needFieldUpdate = !importContext.getImportOption().isHistoryPara();
                                                if (needFieldUpdate) {
                                                    boolean bl2 = needFieldUpdate = fieldDataCompare == null || fieldDataCompare.getUpdateType() != CompareUpdateType.UPDATE_APPOINT && fieldDataCompare.getUpdateType() != CompareUpdateType.UPDATA_USENET && fieldDataCompare.getUpdateType() != CompareUpdateType.UPDATE_IGNORE;
                                                }
                                                if (!needFieldUpdate || !isFieldHasData) break block137;
                                                if (!MapFields.containsKey(fieldDefine.getKey())) {
                                                    if (importContext.getSchemeInfoCache().getTableFieldCache().containsKey(mapTableDefine.getKey())) {
                                                        fieldInfos = importContext.getSchemeInfoCache().getTableFieldCache().get(mapTableDefine.getKey());
                                                        for (FieldInfoDefine fieldInfo : fieldInfos) {
                                                            MapFields.put(fieldInfo.getKey(), fieldInfo.getDataField());
                                                        }
                                                    } else {
                                                        fields = this.dataSchemeService.getDataFieldByTable(mapTableDefine.getKey());
                                                        for (DesignDataField field : fields) {
                                                            MapFields.put(field.getKey(), field);
                                                        }
                                                        importContext.getSchemeInfoCache().getTableFieldCache().put(mapTableDefine.getKey(), FieldInfoDefine.getFieldInfos2(fields));
                                                    }
                                                }
                                                if (!MapFields.containsKey(fieldDefine.getKey())) break block137;
                                                oldField = (DesignDataField)MapFields.get(fieldDefine.getKey());
                                                if (oldField.getDataFieldType() != fieldDefine.getDataFieldType()) break block138;
                                                if (oldField.getDataFieldType() != DataFieldType.BIGDECIMAL) break block139;
                                                if (oldField.getDecimal() != null && fieldDefine.getDecimal() != null) {
                                                    if (fieldDefine.getDecimal() < oldField.getDecimal()) {
                                                        fieldDefine.setDecimal(oldField.getDecimal());
                                                    }
                                                    if (fieldDefine.getPrecision() < oldField.getPrecision() + fieldDefine.getDecimal() - oldField.getDecimal()) {
                                                        fieldDefine.setPrecision(Integer.valueOf(oldField.getPrecision() + fieldDefine.getDecimal() - oldField.getDecimal()));
                                                    }
                                                    if (fieldDefine.getPrecision() < oldField.getPrecision()) {
                                                        fieldDefine.setPrecision(oldField.getPrecision());
                                                    }
                                                    break block137;
                                                } else {
                                                    needFieldUpdate = false;
                                                }
                                                break block137;
                                            }
                                            if (oldField.getPrecision() == null || fieldDefine.getPrecision() == null || oldField.getPrecision() >= fieldDefine.getPrecision()) break block140;
                                            if (oldField.getDataFieldType() == DataFieldType.BIGDECIMAL && oldField.getDecimal() != null && fieldDefine.getDecimal() != null && oldField.getDecimal() > fieldDefine.getDecimal()) {
                                                fieldDefine.setDecimal(oldField.getDecimal());
                                            }
                                            break block137;
                                        }
                                        if (oldField.getPrecision() == null || fieldDefine.getPrecision() == null || !oldField.getPrecision().equals(fieldDefine.getPrecision())) break block141;
                                        if (oldField.getDataFieldType() == DataFieldType.BIGDECIMAL && oldField.getDecimal() != null && fieldDefine.getDecimal() != null && oldField.getDecimal() > fieldDefine.getDecimal()) {
                                            fieldDefine.setDecimal(oldField.getDecimal());
                                            needFieldUpdate = false;
                                            break block137;
                                        } else if (oldField.getDataFieldType() != DataFieldType.BIGDECIMAL || oldField.getDecimal() == null || fieldDefine.getDecimal() == null || oldField.getDecimal() >= fieldDefine.getDecimal()) {
                                            needFieldUpdate = false;
                                        }
                                        break block137;
                                    }
                                    needFieldUpdate = false;
                                    break block137;
                                }
                                needFieldUpdate = false;
                            }
                            if (needFieldUpdate) {
                                String findKey = fieldDefine.getKey();
                                List findFields = updateFields.stream().filter(s -> s.getKey().equalsIgnoreCase(findKey)).collect(Collectors.toList());
                                if (findFields == null || findFields.size() == 0) {
                                    updateFields.add(fieldDefine);
                                }
                            }
                        }
                        if (def.getRegionInfo() != null && StringUtils.isNotEmpty((CharSequence)def.getRegionInfo().getKeyField()) && !isFMDM && !isFixed && (floatCode = def.getRegionInfo().getKeyField() + ";").indexOf(zb.getFieldName() + ";") >= 0) {
                            fieldDefine.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                            mapTableDefine.setRepeatCode(Boolean.valueOf(!def.getRegionInfo().getKeyIsUnique()));
                            mapTableDefine.setUpdateTime(Instant.now());
                            this.dataSchemeService.updateDataTable(mapTableDefine);
                        }
                        if (!isRegionNew && oldLinkCache.containsKey(aCode = fieldDefine.getKey().toString())) {
                            linkDefine = oldLinkCache.get(aCode);
                        }
                        if (fieldLog != null) {
                            fieldLog.setParentNetKey(fieldDefine.getDataTableKey());
                        }
                    }
                    boolean bl = isLinkNew = null == linkDefine;
                    if (isLinkNew) {
                        linkDefine = this.viewController.createDataLinkDefine();
                    }
                    if (StringUtils.isNotEmpty((CharSequence)linkExpression) && fieldDefine == null) {
                        if (isFMDM) {
                            linkDefine.setType(DataLinkType.DATA_LINK_TYPE_FMDM);
                        } else {
                            linkDefine.setType(DataLinkType.DATA_LINK_TYPE_FIELD);
                        }
                        linkDefine.setLinkExpression(linkExpression);
                    }
                    this.setDataLinkAttr(importContext, linkDefine, fieldDefine, zb, dataRegion, isLinkNew, isFieldNew, isFixed, aIndex);
                    if (isFMDM && fmdmFieldIsMdInfo) {
                        linkDefine.setType(DataLinkType.DATA_LINK_TYPE_INFO);
                    }
                    formLinksMap.put(linkDefine.getKey(), linkDefine);
                    singleField.setNetDataLinkKey(linkDefine.getKey());
                    singleField.setRegionKey(linkDefine.getRegionKey());
                    singleField.setRegionCode(dataRegion.getCode());
                    singleField.setNetFormCode(formDefine.getFormCode());
                    singleField.setFormCode(repInfo.getCode());
                    singleField.setTableCode(repInfo.getCode());
                    if (fieldDefine != null) break block142;
                    singleField.setEnumCode(zb.getEnumId());
                    singleField.setFieldCode(zb.getFieldName());
                    singleField.setFieldSize(zb.getLength());
                    singleField.setFieldDecimal((int)zb.getDecimal());
                    singleField.setFieldType(this.paraImportService.getFieldType(zb.getDataType()));
                    singleField.setDefaultValue(zb.getDefaultValue());
                    singleField.setNetFieldCode(fieldName);
                    singleField.setNetTableCode(importContext.getEntityTable().getCode());
                    singleField.setEnumCode(zb.getEnumId());
                    if (StringUtils.isNotEmpty((CharSequence)linkExpression)) {
                        singleField.setNetFieldCode(linkExpression);
                        if (fmdmFieldMap.containsKey(linkExpression)) {
                            singleField.setNetFieldKey(((IEntityAttribute)fmdmFieldMap.get(linkExpression)).getID());
                        }
                    }
                    break block143;
                }
                if (repInfo.getTableType() == ReportTableType.RTT_BLOBTABLE && ("FJFIELD".equalsIgnoreCase(fieldDefine.getCode()) || fieldDefine.getDataFieldType() == DataFieldType.FILE)) {
                    this.uploadAttachmentFile(importContext, fieldDefine, repInfo, linkDefine);
                }
            }
            if (null != rGrid && null != (cellData = rGrid.getGridCellData(linkDefine.getPosX(), linkDefine.getPosY()))) {
                if ("\u2014\u2014".equalsIgnoreCase(cellData.getEditText()) || "\u4e00".equalsIgnoreCase(cellData.getEditText())) {
                    linkDefine.setPosX(0);
                    linkDefine.setPosY(0);
                } else if (StringUtils.isNotEmpty((CharSequence)cellData.getEditText())) {
                    cellData.setEditText("");
                    cellData.setShowText("");
                    reportIsChange = true;
                }
            }
            if (isLinkNew) {
                linkDefine.setOwnerLevelAndId(importContext.getCurServerCode());
                insertLinks.add(linkDefine);
            } else {
                updateLinks.add(linkDefine);
            }
            if (!isFieldNew) continue;
        }
        if (reportIsChange) {
            log.info("\u6307\u6807\u5355\u5143\u683c\u5b58\u5728\u8868\u6837\u4fe1\u606f\uff0c\u9700\u8981\u6e05\u9664");
            byte[] bytes = Grid2Data.gridToBytes((Grid2Data)rGrid);
            formDefine.setBinaryData(bytes);
        }
        curTableCache.setFieldGroup(fieldGroup);
        if (addMapFields.size() > 0) {
            this.mapFieldService.batchAdd(addMapFields);
        }
    }

    private boolean getIsNeedInsertOrUdpateTable(List<ZBInfo> singleZbs, Map<String, CompareDataFieldDTO> oldFieldCompareDic) {
        boolean needUpdateOrInsert = false;
        for (ZBInfo zb : singleZbs) {
            CompareDataFieldDTO fieldDataCompare;
            if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName()) || !oldFieldCompareDic.containsKey(zb.getFieldName()) || (fieldDataCompare = oldFieldCompareDic.get(zb.getFieldName())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE) continue;
            needUpdateOrInsert = true;
            break;
        }
        return needUpdateOrInsert;
    }

    private boolean getIsNewTable(RegionTableCache tableCache) {
        if (!tableCache.isTableNew()) {
            return false;
        }
        int minFieldCount = 0;
        if (tableCache.getTableDefine() != null) {
            minFieldCount = tableCache.getTableDefine().getBizKeysCount();
        }
        return tableCache.getFieldIDMap() != null && tableCache.getFieldIDMap().size() <= minFieldCount && tableCache.getTableDefine() != null;
    }

    private RegionTableCache getMapTableCache(TaskImportContext importContext, DesignDataRegionDefine dataRegion, DesignDataTable tableDefine, RegionTableList cacheList) throws Exception {
        cacheList.addTableDefine(new TableInfoDefine(tableDefine), dataRegion);
        RegionTableCache newCache = cacheList.getRegionTableCacheById(tableDefine.getKey());
        newCache.setFieldGroup(null);
        List fieldList2 = this.dataSchemeService.getDataFieldByTable(tableDefine.getKey());
        if (null != fieldList2) {
            for (DesignDataField field : fieldList2) {
                cacheList.addFieldDefine(new FieldInfoDefine(field));
            }
        }
        return newCache;
    }

    private DesignDataField createNewField(TaskImportContext importContext, DesignDataRegionDefine dataRegion, DesignFormDefine formDefine, ZBInfo zb, RegionTableList cacheList, FieldInfoCache fieldCache, ICompareData fieldCompare, CompareDataFormDTO formCompare) throws Exception {
        DesignDataField fieldDefine = null;
        Map<String, FieldInfoDefine> fieldMap = cacheList.getFieldMap();
        RegionTableCache curTableCache = cacheList.getCurTableCache();
        DesignDataTable tableDefine = curTableCache.getTableDefine().getDataTable();
        String curTableGroupKey = tableDefine.getDataGroupKey();
        DesignFieldGroupDefine fieldGroup = curTableCache.getFieldGroup();
        String curTableTitle = dataRegion.getTitle();
        boolean isTableNew = cacheList.isTableNew();
        boolean isFieldNew = false;
        FieldInfoDefine findFieldInfo = null;
        String newFieldCode = zb.getFieldName();
        String oldFieldCode = zb.getFieldName();
        boolean needPreix = fieldCache.isNeedPrefix();
        if (fieldCompare != null) {
            if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                newFieldCode = fieldCompare.getNetCode();
            }
            boolean bl = isFieldNew = fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW || fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE;
            if (!isFieldNew) {
                findFieldInfo = cacheList.getFieldDefine(fieldCompare.getNetKey());
                if (findFieldInfo == null && importContext.getSchemeInfoCache().getIdFieldCache().containsKey(fieldCompare.getNetKey())) {
                    findFieldInfo = importContext.getSchemeInfoCache().getIdFieldCache().get(fieldCompare.getNetKey());
                }
                isFieldNew = findFieldInfo == null;
            } else if (dataRegion.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE && formCompare != null && formCompare.getCompareType() == CompareContextType.CONTEXT_GLOBALCODE) {
                needPreix = false;
                boolean bl2 = isFieldNew = !fieldMap.containsKey(newFieldCode);
                if (isFieldNew) {
                    if (importContext.getSchemeInfoCache().getCodeFieldCache().containsKey(newFieldCode)) {
                        List<FieldInfoDefine> codeFields = importContext.getSchemeInfoCache().getCodeFieldCache().get(newFieldCode);
                        findFieldInfo = codeFields.get(0);
                        isFieldNew = false;
                    } else {
                        findFieldInfo = cacheList.getFieldByCode(newFieldCode);
                        isFieldNew = findFieldInfo == null;
                    }
                } else {
                    findFieldInfo = fieldMap.get(newFieldCode);
                }
            }
        } else {
            boolean bl = isFieldNew = isTableNew || !fieldMap.containsKey(newFieldCode);
            if (importContext.getImportOption().isHistoryPara() && isFieldNew) {
                boolean bl3 = isFieldNew = !fieldMap.containsKey(newFieldCode);
            }
        }
        if (isFieldNew) {
            boolean needSplictOld = false;
            if (needSplictOld && (curTableCache.getFieldCount() >= this.getMaxFieldCountInTable() || curTableCache.getTableSize() + 3 * zb.getLength() > this.getMaxTableFieldSize())) {
                this.updateFormInfoCacheToServer(importContext, true, false, false, false);
                tableDefine.setUpdateTime(Instant.now());
                this.dataSchemeService.updateDataTable(tableDefine);
                RegionTableCache newCache = cacheList.getNewCache(curTableCache);
                DesignDataTable newTableDefine = this.dataSchemeService.getDataTableByCode(newCache.getTableCode());
                if (null == newTableDefine) {
                    newTableDefine = this.dataSchemeService.initDataTable();
                    newTableDefine.setCode(newCache.getTableCode());
                    String tableTitle = importContext.getFormInfoCahche().getTableTitleByGroupKey(curTableGroupKey, curTableTitle + "\u5b50\u8868");
                    newTableDefine.setTitle(tableTitle);
                    newTableDefine.setLevel(importContext.getCurServerCode());
                    newTableDefine.setDataSchemeKey(importContext.getDataSchemeKey());
                    newTableDefine.setDataGroupKey(curTableGroupKey);
                    newCache.setTableDefine(new TableInfoDefine(newTableDefine));
                    newCache.setTableNew(true);
                    cacheList.addTableCache(newCache);
                    newTableDefine.setDataTableGatherType(tableDefine.getDataTableGatherType());
                    newTableDefine.setDataTableType(tableDefine.getDataTableType());
                    this.dataSchemeService.insertDataTable(newTableDefine);
                    importContext.getFormInfoCahche().addTableToGroup(curTableGroupKey, new TableInfoDefine(newTableDefine));
                    importContext.getSchemeInfoCache().getTaskTableCache().put(newTableDefine.getKey(), new TableInfoDefine(newTableDefine));
                } else {
                    newCache.setTableDefine(new TableInfoDefine(newTableDefine));
                    cacheList.addTableCache(newCache);
                }
                List fieldList = this.dataSchemeService.getDataFieldByTable(newTableDefine.getKey());
                if (null != fieldList) {
                    for (DesignDataField field : fieldList) {
                        FieldInfoDefine fieldInfo = new FieldInfoDefine(field);
                        cacheList.addFieldDefine(fieldInfo);
                    }
                }
                tableDefine = newTableDefine;
                curTableCache = newCache;
                boolean bl = isFieldNew = isTableNew || !fieldMap.containsKey(newFieldCode);
                if (importContext.getImportOption().isHistoryPara() && isFieldNew) {
                    isFieldNew = !fieldMap.containsKey(newFieldCode);
                }
                fieldGroup = null;
                curTableCache.setFieldGroup(fieldGroup);
                cacheList.setCurTableCache(curTableCache);
            } else if (curTableCache.getTableSize() > 65535) {
                log.info("\u5b58\u50a8\u8868:" + curTableCache.getTableCode() + ",\u6307\u6807\u6570\u91cf:" + curTableCache.getFieldCount() + ",\u5b57\u8282\u4f30\u8ba1\u503c:" + curTableCache.getTableSize());
            }
            if (isFieldNew) {
                fieldDefine = this.createNewFieldDefine(newFieldCode, oldFieldCode, tableDefine, needPreix, formDefine.getFormCode());
                fieldDefine.setNullable(Boolean.valueOf(true));
                fieldDefine.setDataFieldType(this.paraImportService.getDataFieldType(zb.getDataType()));
                fieldDefine.setPrecision(Integer.valueOf(zb.getLength()));
                if (fieldDefine.getDataFieldType() == DataFieldType.DATE && fieldDefine.getPrecision() < 20) {
                    fieldDefine.setPrecision(Integer.valueOf(20));
                }
                if (fieldCompare != null) {
                    CompareDataFieldDTO fieldCompare2 = null;
                    if (fieldCompare instanceof CompareDataFieldDTO) {
                        fieldCompare2 = (CompareDataFieldDTO)fieldCompare;
                    }
                    if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetCode())) {
                        fieldDefine.setCode(this.replaceSysChar(tableDefine.getCode(), fieldCompare.getNetCode()));
                    }
                    if (StringUtils.isNotEmpty((CharSequence)fieldCompare.getNetTitle())) {
                        fieldDefine.setTitle(this.getFieldNewTitle(formDefine.getTitle(), fieldCompare.getNetTitle()));
                    } else if (fieldCompare2 != null && StringUtils.isEmpty((CharSequence)zb.getZbTitle())) {
                        fieldDefine.setTitle(this.getFieldNewTitle(formDefine.getTitle(), fieldCompare2.getSingleMatchTitle()));
                    }
                    Map<String, List<FieldInfoDefine>> codeFieldCache = importContext.getSchemeInfoCache().getCodeFieldCache();
                    List<Object> codeFields = null;
                    if (codeFieldCache.containsKey(fieldDefine.getCode())) {
                        codeFields = codeFieldCache.get(fieldDefine.getCode());
                    } else {
                        codeFields = new ArrayList();
                        codeFieldCache.put(fieldDefine.getCode(), codeFields);
                    }
                    codeFields.add(new FieldInfoDefine(fieldDefine));
                }
                cacheList.addFieldDefine(new FieldInfoDefine(fieldDefine));
                fieldCache.setFieldNew(true);
            } else {
                fieldDefine = fieldMap.get(newFieldCode).getDataField();
            }
        } else {
            fieldDefine = fieldCompare != null ? findFieldInfo.getDataField() : fieldMap.get(newFieldCode).getDataField();
        }
        return fieldDefine;
    }

    @Override
    public void updateFormInfoCacheToServer(TaskImportContext importContext, boolean fieldMustUpdate, boolean linkMustUpdate, boolean regionMustUpdate, boolean TableMustUpdate) throws Exception {
        long currentTimeMillis;
        List<DesignDataField> insertFields = importContext.getFormInfoCahche().getInsertDataFields();
        List<DesignDataField> updateFields = importContext.getFormInfoCahche().getUpdateDataFields();
        List<DesignDataLinkDefine> insertLinks = importContext.getFormInfoCahche().getInsertLinks();
        List<DesignDataLinkDefine> updateLinks = importContext.getFormInfoCahche().getUpdateLinks();
        List<String> deleteLinks = importContext.getFormInfoCahche().getDeleteLinks();
        List<String> deleteTableKeys = importContext.getFormInfoCahche().getDeleteTableKeys();
        List<String> deleteTableGroupKeys = importContext.getFormInfoCahche().getDeleteTableGroupKeys();
        List<DesignDataRegionDefine> insertRegions = importContext.getFormInfoCahche().getInsertRegions();
        List<DesignDataRegionDefine> updateRegions = importContext.getFormInfoCahche().getUpdateRegions();
        List<String> deleteRegions = importContext.getFormInfoCahche().getDeleteRegions();
        if ((regionMustUpdate || linkMustUpdate) && insertRegions.size() > 0 || insertRegions.size() > 2000) {
            this.viewController.insertDataRegionDefines(insertRegions.toArray(new DesignDataRegionDefine[insertRegions.size()]));
            insertRegions.clear();
        }
        if (regionMustUpdate && updateRegions.size() > 0 || updateRegions.size() > 2000) {
            this.viewController.updateDataRegionDefines(updateRegions.toArray(new DesignDataRegionDefine[updateRegions.size()]));
            updateRegions.clear();
        }
        if (regionMustUpdate && deleteRegions.size() > 0 || deleteRegions.size() > 2000) {
            this.viewController.deleteDataRegionDefines(deleteRegions.toArray(new String[deleteRegions.size()]), true);
            deleteRegions.clear();
        }
        if (fieldMustUpdate && insertFields.size() > 0 || insertFields.size() > 2000) {
            currentTimeMillis = System.currentTimeMillis();
            for (DesignDataField field : insertFields) {
                if (!StringUtils.isNotEmpty((CharSequence)field.getTitle()) || field.getTitle().length() <= 200) continue;
                field.setTitle(field.getTitle().substring(0, 200));
            }
            this.dataSchemeService.insertDataFields(insertFields);
            log.info("\u63d2\u5165\u603b\u65701\uff1a{}", (Object)(insertFields.size() + ",\u8017\u65f6\uff1a" + (System.currentTimeMillis() - currentTimeMillis)));
            ArrayList<String> fieldkeys = new ArrayList<String>();
            String tableKey = null;
            for (DesignDataField field : insertFields) {
                if (null == tableKey) {
                    tableKey = field.getDataTableKey();
                }
                if (tableKey == field.getDataTableKey()) {
                    fieldkeys.add(field.getKey());
                    continue;
                }
                fieldkeys.clear();
                tableKey = field.getDataTableKey();
                fieldkeys.add(field.getKey());
            }
            if (fieldkeys.size() > 0) {
                fieldkeys.clear();
            }
            insertFields.clear();
        }
        if (fieldMustUpdate && updateFields.size() > 0 || updateFields.size() > 2000) {
            currentTimeMillis = System.currentTimeMillis();
            for (DesignDataField field : updateFields) {
                field.setUpdateTime(Instant.now());
                if (!StringUtils.isNotEmpty((CharSequence)field.getTitle()) || field.getTitle().length() <= 200) continue;
                field.setTitle(field.getTitle().substring(0, 200));
            }
            this.dataSchemeService.updateDataFields(updateFields);
            log.info("\u66f4\u65b0\u603b\u65702\uff1a{}", (Object)(updateFields.size() + ",\u8017\u65f6\uff1a" + (System.currentTimeMillis() - currentTimeMillis)));
            updateFields.clear();
        }
        if (linkMustUpdate && insertLinks.size() > 0 || insertLinks.size() > 2000) {
            this.viewController.insertDataLinkDefines(insertLinks.toArray(new DesignDataLinkDefine[insertLinks.size()]));
            insertLinks.clear();
        }
        if (linkMustUpdate && updateLinks.size() > 0 || updateLinks.size() > 2000) {
            this.viewController.updateDataLinkDefines(updateLinks.toArray(new DesignDataLinkDefine[updateLinks.size()]));
            updateLinks.clear();
        }
        if (linkMustUpdate && deleteLinks.size() > 0 || deleteLinks.size() > 2000) {
            this.viewController.deleteDataLinkDefines(deleteLinks.toArray(new String[deleteLinks.size()]));
            deleteLinks.clear();
        }
        if (TableMustUpdate) {
            LinkedHashSet<String> tableGroupsByDeleteTable = new LinkedHashSet<String>();
            if (deleteTableKeys.size() > 0) {
                ArrayList<String> newDeleteTableKeys = new ArrayList<String>();
                for (String tableKey : deleteTableKeys) {
                    List dataFields;
                    DesignDataTable table = this.dataSchemeService.getDataTable(tableKey);
                    if (table == null) continue;
                    int minBizCount = 0;
                    if (table.getBizKeys() != null) {
                        minBizCount = table.getBizKeys().length;
                    }
                    if ((dataFields = this.dataSchemeService.getDataFieldByTable(tableKey)) != null && dataFields.size() <= minBizCount) {
                        log.info("\u5220\u9664\u7a7a\u8868\uff1a" + table.getCode() + "," + table.getKey() + "," + table.getTitle());
                        newDeleteTableKeys.add(tableKey);
                        tableGroupsByDeleteTable.add(table.getDataGroupKey());
                        continue;
                    }
                    if (dataFields != null) {
                        log.info("\u4e0d\u662f\u7a7a\u8868\uff1a" + table.getCode() + "," + table.getKey() + "," + table.getTitle() + ",\u6307\u6807\u6570" + dataFields.size());
                        continue;
                    }
                    log.info("\u4e0d\u662f\u7a7a\u8868\uff1a" + table.getCode() + "," + table.getKey() + "," + table.getTitle());
                }
                if (!newDeleteTableKeys.isEmpty()) {
                    this.dataSchemeService.deleteDataTables(newDeleteTableKeys);
                    deleteTableKeys.clear();
                }
            }
            if (!deleteTableGroupKeys.isEmpty()) {
                tableGroupsByDeleteTable.addAll(deleteTableGroupKeys);
            }
            ArrayList<String> newDeleteTableGroupKeys = new ArrayList<String>();
            for (String groupKey : tableGroupsByDeleteTable) {
                DesignDataGroup dataGroup = this.dataSchemeService.getDataGroup(groupKey);
                if (dataGroup == null) continue;
                List dataTables = this.dataSchemeService.getDataTableByGroup(groupKey);
                if (dataTables == null || dataTables.isEmpty()) {
                    List subGroups = this.dataSchemeService.getDataGroupByParent(groupKey);
                    if (subGroups != null && !subGroups.isEmpty()) {
                        boolean isAllEmpty = true;
                        for (DesignDataGroup subGrop : subGroups) {
                            if (this.judgeDataGroupIsEmpty(subGrop.getKey())) {
                                if (tableGroupsByDeleteTable.contains(subGrop.getKey())) continue;
                                newDeleteTableGroupKeys.add(subGrop.getKey());
                                log.info("\u5220\u9664\u7a7a\u6307\u6807\u5206\u7ec4\uff1a" + subGrop.getCode() + "," + subGrop.getTitle() + "," + subGrop.getKey());
                                continue;
                            }
                            isAllEmpty = false;
                        }
                        if (isAllEmpty) {
                            newDeleteTableGroupKeys.add(groupKey);
                            log.info("\u5220\u9664\u7a7a\u6307\u6807\u5206\u7ec4\uff1a" + dataGroup.getCode() + "," + dataGroup.getTitle() + "," + dataGroup.getKey());
                            continue;
                        }
                        log.info("\u4e0d\u662f\u7a7a\u6307\u6807\u5206\u7ec4,\u5b58\u5728\u5b50\u5206\u7ec4\uff1a" + dataGroup.getCode() + "," + dataGroup.getTitle() + "," + dataGroup.getKey());
                        continue;
                    }
                    newDeleteTableGroupKeys.add(groupKey);
                    log.info("\u5220\u9664\u7a7a\u6307\u6807\u5206\u7ec4\uff1a" + dataGroup.getCode() + "," + dataGroup.getTitle() + "," + dataGroup.getKey());
                    continue;
                }
                log.info("\u4e0d\u662f\u7a7a\u6307\u6807\u5206\u7ec4\uff1a" + dataGroup.getCode() + "," + dataGroup.getTitle() + "," + dataGroup.getKey());
            }
            if (!newDeleteTableGroupKeys.isEmpty()) {
                this.dataSchemeService.deleteDataGroups(newDeleteTableGroupKeys);
                deleteTableGroupKeys.clear();
            }
        }
    }

    private boolean judgeDataGroupIsEmpty(String groupKey) {
        List subGroups;
        boolean result = false;
        List dataTables = this.dataSchemeService.getDataTableByGroup(groupKey);
        if ((dataTables == null || dataTables.isEmpty()) && ((subGroups = this.dataSchemeService.getDataGroupByParent(groupKey)) == null || subGroups.isEmpty())) {
            result = true;
        }
        return result;
    }

    private DesignDataField FindFieldBySingleZb(TaskImportContext importContext, ZBInfo zb, String curTableTitle, FieldInfoCache fieldCache, TableInfoDefine findTable) {
        Map<String, List<FieldInfoDefine>> titleFieldCache = importContext.getSchemeInfoCache().getTitleFieldCache();
        Map<String, List<FieldInfoDefine>> AliasFieldCache = importContext.getSchemeInfoCache().getAliasFieldCache();
        Map<String, TableInfoDefine> taskTableCache = importContext.getSchemeInfoCache().getTaskTableCache();
        FieldInfoDefine fieldDefine = null;
        ArrayList<FieldInfoDefine> newMaps = new ArrayList<FieldInfoDefine>();
        fieldCache.setMapFields(newMaps);
        if (importContext.getImportOption().isHistoryPara()) {
            List<FieldInfoDefine> mapFields = titleFieldCache.get(zb.getZbTitle());
            if (importContext.getImportOption().getHistoryMatchType() == 1) {
                mapFields = AliasFieldCache.get(zb.getFieldName());
            }
            if (mapFields != null) {
                if (mapFields.size() == 1) {
                    fieldDefine = mapFields.get(0);
                    newMaps.addAll(mapFields);
                } else if (mapFields.size() > 1) {
                    HashMap<String, FieldInfoDefine> mapSameTableFields = new HashMap<String, FieldInfoDefine>();
                    for (FieldInfoDefine mpField : mapFields) {
                        DesignDataTable mpTable = taskTableCache.get(mpField.getOwnerTableKey()).getDataTable();
                        boolean hasFind = false;
                        if (curTableTitle.equalsIgnoreCase(mpTable.getTitle()) || (curTableTitle + "\u5b50\u8868").equalsIgnoreCase(mpTable.getTitle())) {
                            hasFind = true;
                        } else if (findTable != null) {
                            if (StringUtils.isNotEmpty((CharSequence)mpTable.getKey()) && mpTable.getKey().equalsIgnoreCase(findTable.getKey())) {
                                hasFind = true;
                            } else if (StringUtils.isNotEmpty((CharSequence)mpTable.getTitle()) && mpTable.getTitle().equalsIgnoreCase(findTable.getTitle())) {
                                hasFind = true;
                            }
                        }
                        if (!hasFind) continue;
                        if (importContext.getImportOption().getHistoryMatchType() == 1) {
                            mapSameTableFields.put(mpField.getTitle(), mpField);
                            continue;
                        }
                        mapSameTableFields.put(mpField.getAlis(), mpField);
                    }
                    if (mapSameTableFields.size() > 1) {
                        if (importContext.getImportOption().getHistoryMatchType() == 1) {
                            if (mapSameTableFields.containsKey(zb.getZbTitle())) {
                                fieldDefine = (FieldInfoDefine)mapSameTableFields.get(zb.getZbTitle());
                                newMaps.add(fieldDefine);
                            } else {
                                newMaps.addAll(mapSameTableFields.values());
                            }
                        } else if (mapSameTableFields.containsKey(zb.getFieldName())) {
                            fieldDefine = (FieldInfoDefine)mapSameTableFields.get(zb.getFieldName());
                            newMaps.add(fieldDefine);
                        } else {
                            newMaps.addAll(mapSameTableFields.values());
                        }
                    } else if (mapSameTableFields.size() == 1) {
                        newMaps.addAll(mapSameTableFields.values());
                        fieldDefine = (FieldInfoDefine)newMaps.get(0);
                    }
                }
            }
        }
        fieldCache.setFieldDefine(fieldDefine);
        DesignDataField result = null;
        if (fieldDefine != null) {
            result = fieldDefine.getDataField();
        }
        return result;
    }

    private void setFieldDefineAttr(TaskImportContext importContext, FieldDefs def, DesignDataField fieldDefine, ZBInfo zb, DesignDataTable tableDefine, boolean isFMDM, SingleFileFieldInfo singleField, boolean isFieldNew, boolean fmdmFieldIsData, CompareDataFieldDTO fieldDataCompare, boolean isFieldHasData) {
        ParaInfo para = importContext.getParaInfo();
        if (fieldDataCompare == null || fieldDataCompare.getUpdateType() != CompareUpdateType.UPDATE_APPOINT) {
            fieldDefine.setTitle(this.getFieldNewTitle("", zb.getZbTitle()));
            if (StringUtils.isEmpty((CharSequence)fieldDefine.getTitle())) {
                fieldDefine.setTitle(zb.getFieldName());
            }
            if (!isFMDM || StringUtils.isEmpty((CharSequence)fieldDefine.getOrder())) {
                fieldDefine.setOrder(OrderGenerator.newOrder());
            }
        }
        if (isFieldNew) {
            fieldDefine.setDesc(importContext.getParaInfo().getTaskYear() + "_" + fieldDefine.getTitle());
            fieldDefine.setDataTableKey(tableDefine.getKey());
        }
        if (isFieldNew || !isFieldHasData) {
            fieldDefine.setDataFieldType(this.paraImportService.getDataFieldType(zb.getDataType()));
            fieldDefine.setPrecision(Integer.valueOf(zb.getLength()));
            fieldDefine.setDecimal(new Integer(zb.getDecimal()));
            if (fieldDefine.getDataFieldType() == DataFieldType.STRING) {
                if (fieldDefine.getPrecision() == 0) {
                    fieldDefine.setPrecision(Integer.valueOf(20));
                }
            } else if (fieldDefine.getDataFieldType() == DataFieldType.BIGDECIMAL) {
                if (fieldDefine.getDecimal() == 0 && fieldDefine.getPrecision() < 20) {
                    fieldDefine.setPrecision(Integer.valueOf(20));
                }
            } else if (fieldDefine.getDataFieldType() == DataFieldType.INTEGER && fieldDefine.getDecimal() != 0) {
                fieldDefine.setDecimal(Integer.valueOf(0));
            }
            if (!"FJFIELD".equalsIgnoreCase(fieldDefine.getCode())) {
                fieldDefine.setDefaultValue(zb.getDefaultValue());
            }
            if (null == ImportConsts.ENTITY_PARENT_FIELD || ImportConsts.ENTITY_PARENT_FIELD.equals(fieldDefine.getCode())) {
                // empty if block
            }
            if (zb.getZbSumMode()) {
                fieldDefine.setDataFieldGatherType(DataFieldGatherType.SUM);
            } else {
                fieldDefine.setDataFieldGatherType(DataFieldGatherType.NONE);
            }
            if (fieldDefine.getDataFieldType() != DataFieldType.INTEGER && fieldDefine.getDataFieldType() == DataFieldType.BIGDECIMAL) {
                // empty if block
            }
        } else if (isFieldHasData) {
            DataFieldType singleDataType = this.paraImportService.getDataFieldType(zb.getDataType());
            if (fieldDefine.getDataFieldType() == DataFieldType.STRING && fieldDefine.getDataFieldType() == singleDataType) {
                if (fieldDefine.getPrecision() < zb.getLength()) {
                    fieldDefine.setPrecision(Integer.valueOf(zb.getLength()));
                }
            } else if (fieldDefine.getDataFieldType() == DataFieldType.BIGDECIMAL && fieldDefine.getDataFieldType() == singleDataType) {
                Integer singleDecimal = new Integer(zb.getDecimal());
                if (fieldDefine.getDecimal() < singleDecimal) {
                    fieldDefine.setDecimal(singleDecimal);
                }
            } else if (fieldDefine.getDataFieldType() == DataFieldType.INTEGER) {
                // empty if block
            }
        }
        if (isFieldNew) {
            fieldDefine.setDataFieldApplyType(DataFieldApplyType.NONE);
            fieldDefine.setNullable(Boolean.valueOf(true));
            if (isFMDM && fmdmFieldIsData) {
                if (null == ImportConsts.ENTITY_PARENT_FIELD || !ImportConsts.ENTITY_PARENT_FIELD.equals(fieldDefine.getCode())) {
                    if (zb.getFieldName().equals(para.getFmRepInfo().getBBLXField())) {
                        if (importContext.getIsNewEntity().booleanValue()) {
                            fieldDefine.setNullable(Boolean.valueOf(false));
                        }
                    } else if (zb.getFieldName().equals(para.getFmRepInfo().getDWDMFieldName())) {
                        if (importContext.getIsNewEntity().booleanValue()) {
                            fieldDefine.setNullable(Boolean.valueOf(false));
                        }
                    } else if (zb.getFieldName().equals(para.getFmRepInfo().getDWMCFieldName()) && importContext.getIsNewEntity().booleanValue()) {
                        fieldDefine.setNullable(Boolean.valueOf(false));
                    }
                }
            } else if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName())) {
                // empty if block
            }
        }
        singleField.setEnumCode(zb.getEnumId());
        singleField.setFieldCode(zb.getFieldName());
        singleField.setFieldSize(zb.getLength());
        singleField.setFieldDecimal((int)zb.getDecimal());
        singleField.setFieldType(this.paraImportService.getFieldType(zb.getDataType()));
        singleField.setDefaultValue(zb.getDefaultValue());
        singleField.setNetFieldCode(fieldDefine.getCode());
        singleField.setNetFieldKey(fieldDefine.getKey());
        singleField.setNetTableCode(tableDefine.getCode());
    }

    private String getFieldNewTitle(String singleFormTitle, String singleFieldTitle) {
        String formStartTilte = singleFormTitle + "_";
        String fieldTitle = singleFieldTitle;
        if (StringUtils.isNotEmpty((CharSequence)fieldTitle)) {
            if (fieldTitle.length() > 200 && fieldTitle.startsWith(formStartTilte)) {
                fieldTitle = fieldTitle.substring(formStartTilte.length(), fieldTitle.length());
            }
            if (fieldTitle.length() > 200) {
                fieldTitle.substring(0, 200);
            }
        }
        return fieldTitle;
    }

    private String getfieldMeasureUnit(TaskImportContext importContext, String formMeasureUnit, String repMoneyUnit, String fieldMoneyUnit, boolean isJezh) {
        String fieldMeasureUnit = null;
        if (!isJezh) {
            fieldMeasureUnit = importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";NotDimession";
        } else if (StringUtils.isEmpty((CharSequence)fieldMoneyUnit)) {
            fieldMeasureUnit = StringUtils.isNotEmpty((CharSequence)formMeasureUnit) ? formMeasureUnit : importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";YUAN";
        } else if (importContext.getSchemeInfoCache().getMeasureCahce().containsKey(fieldMoneyUnit)) {
            String measureUnitCode = importContext.getSchemeInfoCache().getMeasureCahce().get(fieldMoneyUnit).getCode();
            fieldMeasureUnit = importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";" + measureUnitCode;
        } else {
            fieldMeasureUnit = importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";NotDimession";
        }
        return fieldMeasureUnit;
    }

    private void setDataLinkAttr(TaskImportContext importContext, DesignDataLinkDefine linkDefine, DesignDataField fieldDefine, ZBInfo zb, DesignDataRegionDefine dataRegion, boolean isLinkNew, boolean isFieldNew, boolean isFixed, int aIndex) throws Exception {
        linkDefine.setRegionKey(dataRegion.getKey());
        linkDefine.setTitle(zb.getZbTitle());
        linkDefine.setPosX(zb.getGridPos()[0]);
        linkDefine.setPosY(zb.getGridPos()[1]);
        linkDefine.setColNum(zb.getNumPos()[0]);
        linkDefine.setRowNum(zb.getNumPos()[1]);
        if (!isFixed && zb.getHLNumEmpty()) {
            linkDefine.setColNum(0);
            linkDefine.setRowNum(0);
        }
        if (linkDefine.getColNum() == 0 && linkDefine.getRowNum() == 0) {
            if (isFixed) {
                if (linkDefine.getPosY() == 0 && linkDefine.getPosX() == 0) {
                    linkDefine.setColNum(30000 + aIndex);
                    linkDefine.setRowNum(30000 + aIndex);
                } else if (linkDefine.getPosY() == 0) {
                    linkDefine.setColNum(20000 + linkDefine.getPosX());
                    linkDefine.setRowNum(20000 + linkDefine.getPosY() + 1);
                } else if (linkDefine.getPosX() == 0) {
                    linkDefine.setColNum(20000 + linkDefine.getPosX() + 1);
                    linkDefine.setRowNum(20000 + linkDefine.getPosY());
                } else {
                    linkDefine.setColNum(10000 + linkDefine.getPosX());
                    linkDefine.setRowNum(10000 + linkDefine.getPosY());
                }
            } else if (dataRegion.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                linkDefine.setColNum(dataRegion.getRegionLeft());
                if (linkDefine.getPosY() == 0 && linkDefine.getPosX() == 0) {
                    linkDefine.setRowNum(30000 + aIndex);
                } else if (linkDefine.getPosY() == 0) {
                    linkDefine.setRowNum(20000 + linkDefine.getPosX());
                } else {
                    linkDefine.setRowNum(10000 + linkDefine.getPosY());
                }
            } else if (dataRegion.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                linkDefine.setRowNum(dataRegion.getRegionTop());
                if (linkDefine.getPosY() == 0 && linkDefine.getPosX() == 0) {
                    linkDefine.setColNum(30000 + aIndex);
                } else if (linkDefine.getPosX() == 0) {
                    linkDefine.setColNum(20000 + linkDefine.getPosY());
                } else {
                    linkDefine.setColNum(10000 + linkDefine.getPosX());
                }
            }
        }
        linkDefine.setUniqueCode(OrderGenerator.newOrder());
        linkDefine.setOrder(OrderGenerator.newOrder());
        if (null != fieldDefine) {
            linkDefine.setType(DataLinkType.DATA_LINK_TYPE_FIELD);
            linkDefine.setLinkExpression(fieldDefine.getKey());
            if (fieldDefine.getDataFieldType() == DataFieldType.INTEGER || fieldDefine.getDataFieldType() == DataFieldType.BIGDECIMAL) {
                // empty if block
            }
        }
        this.setDataLinkEnumAttr(importContext, linkDefine, fieldDefine, zb, dataRegion, isLinkNew, isFieldNew);
    }

    private void setDataLinkEnumAttr(TaskImportContext importContext, DesignDataLinkDefine linkDefine, DesignDataField fieldDefine, ZBInfo zb, DesignDataRegionDefine dataRegion, boolean isLinkNew, boolean isFieldNew) throws Exception {
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        EnumInfo enumInfo = zb.getEnumInfo();
        if (null != enumInfo) {
            IEntityDefine entityDefine = null;
            IEntityModel entityMode = null;
            TableModelDefine tableModel = null;
            BaseDataDefineDO baseDataDefine = null;
            boolean isParentField = false;
            String enumTableCode = "";
            if (null != ImportConsts.ENTITY_PARENT_FIELD) {
                if (fieldDefine != null && ImportConsts.ENTITY_PARENT_FIELD.equalsIgnoreCase(fieldDefine.getCode())) {
                    isParentField = true;
                } else if (ImportConsts.ENTITY_PARENT_FIELD.equalsIgnoreCase(linkDefine.getLinkExpression())) {
                    isParentField = true;
                }
            }
            if (!importContext.getImportOption().isHistoryPara() || isFieldNew || fieldDefine == null || !StringUtils.isNotEmpty((CharSequence)fieldDefine.getRefDataFieldKey())) {
                String fileFlag = "";
                if (StringUtils.isNotEmpty((CharSequence)dataScheme.getPrefix())) {
                    fileFlag = "_" + dataScheme.getPrefix();
                }
                enumTableCode = String.format("MD%s_%s", fileFlag, enumInfo.getEnumIdenty().toUpperCase());
                boolean enumIsBBLX = "BBLX".equalsIgnoreCase(enumInfo.getEnumIdenty());
                if (enumIsBBLX) {
                    enumTableCode = String.format("MD_%s%s", enumInfo.getEnumIdenty().toUpperCase(), fileFlag);
                }
                if (importContext.getCompareInfo() != null && importContext.getCompareEnumDic() != null) {
                    CompareDataEnumDTO enumCompare;
                    if (importContext.getCompareEnumDic().containsKey(enumInfo.getEnumIdenty())) {
                        enumCompare = importContext.getCompareEnumDic().get(enumInfo.getEnumIdenty());
                        if (enumCompare != null) {
                            enumTableCode = enumCompare.getNetCode();
                        }
                    } else if (importContext.getCompareEnumDic().containsKey(enumInfo.getEnumIdenty().toUpperCase()) && (enumCompare = importContext.getCompareEnumDic().get(enumInfo.getEnumIdenty().toUpperCase())) != null) {
                        enumTableCode = enumCompare.getNetCode();
                    }
                }
                if ((baseDataDefine = importContext.getSchemeInfoCache().queryBaseDataDefineByCode(enumTableCode)) == null) {
                    baseDataDefine = this.baseDefineService.queryBaseDatadefine(enumTableCode);
                    if (null != baseDataDefine) {
                        importContext.getSchemeInfoCache().getBaseDataCache().put(enumTableCode, baseDataDefine);
                    } else if (baseDataDefine == null) {
                        boolean isAnalTask;
                        boolean bl = isAnalTask = importContext.getBaseImportContext() != null && importContext.getBaseImportContext().getSchemeInfoCache() != null && importContext.getBaseImportContext().getSchemeInfoCache().getFormScheme() != null;
                        if (isAnalTask) {
                            CompareDataEnumDTO enumCompare;
                            DesignDataScheme analDataScheme = importContext.getBaseImportContext().getDataScheme();
                            fileFlag = "";
                            if (StringUtils.isNotEmpty((CharSequence)analDataScheme.getPrefix())) {
                                fileFlag = "_" + analDataScheme.getPrefix().toUpperCase();
                            }
                            enumTableCode = String.format("MD%s_%s", fileFlag, enumInfo.getEnumIdenty().toUpperCase());
                            enumIsBBLX = "BBLX".equalsIgnoreCase(enumInfo.getEnumIdenty());
                            if (enumIsBBLX) {
                                enumTableCode = String.format("MD_%s%s", enumInfo.getEnumIdenty().toUpperCase(), fileFlag);
                            }
                            if (importContext.getCompareInfo() != null && importContext.getCompareEnumDic() != null && (enumCompare = importContext.getCompareEnumDic().get(enumInfo.getEnumIdenty().toUpperCase())) != null) {
                                enumTableCode = enumCompare.getNetCode();
                            }
                            if ((baseDataDefine = importContext.getBaseImportContext().getSchemeInfoCache().queryBaseDataDefineByCode(enumTableCode)) == null) {
                                baseDataDefine = this.baseDefineService.queryBaseDatadefine(enumTableCode);
                            }
                        }
                    }
                }
            }
            String entityId = null;
            if (baseDataDefine == null) {
                if (isParentField && StringUtils.isNotEmpty((CharSequence)importContext.getEntityTableKey())) {
                    entityId = importContext.getEntityTableKey();
                    entityDefine = this.entityMetaService.queryEntity(entityId);
                    entityMode = this.entityMetaService.getEntityModel(entityId);
                    if (entityDefine != null) {
                        tableModel = this.entityMetaService.getTableModel(entityDefine.getId());
                    }
                } else if (StringUtils.isNotEmpty((CharSequence)enumTableCode)) {
                    entityId = EntityUtils.getEntityId((String)enumTableCode, (String)"BASE");
                }
            } else {
                entityId = EntityUtils.getEntityId((String)baseDataDefine.getName(), (String)"BASE");
                entityDefine = this.entityMetaService.queryEntity(entityId);
                entityMode = this.entityMetaService.getEntityModel(entityId);
                if (entityDefine != null) {
                    tableModel = this.entityMetaService.getTableModel(entityDefine.getId());
                }
            }
            boolean hasEnumLink = false;
            if (tableModel != null && entityDefine != null && entityMode != null) {
                if (fieldDefine != null) {
                    if (tableModel != null) {
                        fieldDefine.setRefDataFieldKey(tableModel.getBizKeys());
                    }
                    if (entityDefine != null) {
                        fieldDefine.setRefDataEntityKey(entityDefine.getId());
                    }
                }
                if (StringUtils.isNotEmpty((CharSequence)entityId)) {
                    hasEnumLink = true;
                    linkDefine.setDisplayMode(EnumDisplayMode.DISPLAY_MODE_DEFAULT);
                    linkDefine.setEditMode(DataLinkEditMode.DATA_LINK_DROP_DOWN);
                    String titleKeys = "";
                    String codeKeys = "";
                    String codeTitleKeys = "";
                    if (entityMode != null && entityMode.getCodeField() != null) {
                        codeTitleKeys = codeKeys = entityMode.getCodeField().getName();
                    }
                    if (entityMode != null && entityMode.getNameField() != null) {
                        titleKeys = entityMode.getNameField().getName();
                        codeTitleKeys = codeKeys + ";" + titleKeys;
                    } else {
                        titleKeys = codeKeys;
                        codeTitleKeys = codeKeys;
                    }
                    if (StringUtils.isEmpty((CharSequence)titleKeys)) {
                        titleKeys = codeKeys;
                        log.info("\u679a\u4e3e" + enumTableCode + "\u6807\u9898\u5217\u4e0d\u6b63\u786e\u8bf7\u68c0\u67e5\uff01");
                    }
                    if (zb.getEnumInfo().getShowValueType() == 0) {
                        linkDefine.setCaptionFieldsString(codeKeys);
                    } else if (zb.getEnumInfo().getShowValueType() == 1) {
                        linkDefine.setCaptionFieldsString(titleKeys);
                    } else if (zb.getEnumInfo().getShowValueType() == 2) {
                        linkDefine.setCaptionFieldsString(codeTitleKeys);
                    } else {
                        linkDefine.setCaptionFieldsString(codeTitleKeys);
                    }
                    linkDefine.setDropDownFieldsString(codeTitleKeys);
                    String enumHLPos = "";
                    if (zb.getEnumInfo().gethYColNum() > 0 && zb.getEnumInfo().gethYRowNum() > 0) {
                        enumHLPos = this.getEnumTitleField(zb.getEnumInfo().gethYColNum(), zb.getEnumInfo().gethYRowNum());
                    } else if (zb.getEnumInfo().gethYColNum() > 0 || zb.getEnumInfo().gethYRowNum() > 0) {
                        int hyCol = zb.getEnumInfo().gethYColNum();
                        int hyRow = zb.getEnumInfo().gethYRowNum();
                        if (hyCol <= 0) {
                            hyCol = linkDefine.getPosX();
                        } else if (hyRow <= 0) {
                            hyRow = linkDefine.getPosY();
                        }
                        enumHLPos = this.getEnumTitleField(hyCol, hyRow);
                    }
                    if (StringUtils.isNotEmpty((CharSequence)enumHLPos) && StringUtils.isNotEmpty((CharSequence)titleKeys)) {
                        HashMap<String, String> enumTiltles = new HashMap<String, String>();
                        enumTiltles.put(titleKeys, enumHLPos);
                        ObjectMapper obj = new ObjectMapper();
                        String enumPos = obj.writeValueAsString(enumTiltles);
                        linkDefine.setEnumPos(enumPos);
                    }
                    if (isParentField) {
                        if (isLinkNew) {
                            linkDefine.setAllowNotLeafNodeRefer(Boolean.TRUE.booleanValue());
                            linkDefine.setAllowUndefinedCode(Boolean.FALSE);
                            if (fieldDefine != null) {
                                // empty if block
                            }
                        }
                    } else {
                        EnumsItemModel enumModel = (EnumsItemModel)importContext.getParaInfo().getEnunList().get(enumInfo.getEnumIdenty());
                        if (null != enumModel) {
                            linkDefine.setAllowNotLeafNodeRefer(!enumModel.getLeafOnly());
                            Boolean allowUndefinedCode = !enumModel.getForceInList();
                            linkDefine.setAllowUndefinedCode(allowUndefinedCode);
                            if (importContext.getParaInfo().isValueEmptyByEnumCtrl()) {
                                linkDefine.setAllowNullAble(Boolean.valueOf(enumModel.getValueEmpty()));
                            } else if (linkDefine.getAllowUndefinedCode().booleanValue()) {
                                Boolean allowNullAble = enumModel.getValueEmpty();
                                linkDefine.setAllowNullAble(allowNullAble);
                            } else {
                                linkDefine.setAllowNullAble(Boolean.FALSE);
                            }
                            if (fieldDefine != null) {
                                Boolean allowMultipleSelect = enumModel.getMultiSelect();
                                fieldDefine.setAllowMultipleSelect(allowMultipleSelect);
                                fieldDefine.setAllowUndefinedCode(allowUndefinedCode);
                            }
                        }
                    }
                }
            } else if (isParentField && isLinkNew) {
                linkDefine.setAllowNotLeafNodeRefer(Boolean.TRUE.booleanValue());
                linkDefine.setAllowUndefinedCode(Boolean.FALSE);
                linkDefine.setAllowNullAble(null);
                if (fieldDefine != null) {
                    // empty if block
                }
            }
            if (!hasEnumLink) {
                linkDefine.setEditMode(DataLinkEditMode.DATA_LINK_DEFAULT);
                linkDefine.setCaptionFieldsString("");
                linkDefine.setDropDownFieldsString("");
                linkDefine.setAllowNullAble(null);
            }
        } else {
            linkDefine.setEditMode(DataLinkEditMode.DATA_LINK_DEFAULT);
            linkDefine.setCaptionFieldsString("");
            linkDefine.setDropDownFieldsString("");
            linkDefine.setAllowNullAble(null);
        }
    }

    private String getEnumTitleField(int hYCol, int hYRow) {
        String result = "";
        if (hYCol > 0) {
            String[] codes = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            int newCol = hYCol;
            int id = newCol % 26;
            if (id == 0) {
                id = 26;
            }
            result = codes[id - 1];
            while (newCol > 26) {
                newCol = hYCol / 26;
                id = newCol % 26;
                if (id == 0) {
                    id = 26;
                }
                result = codes[id - 1] + result;
            }
        }
        if (hYRow > 0) {
            result = result + hYRow;
        }
        return result;
    }

    private void uploadAttachmentFile(TaskImportContext importContext, DesignDataField fieldDefine, RepInfo repInfo, DesignDataLinkDefine linkDefine) {
        block63: {
            try {
                String zipFile = importContext.getParaInfo().getParaDir() + repInfo.getCode() + ".ZIP";
                if (!PathUtil.getFileExists((String)zipFile)) break block63;
                String tempPath = PathUtil.createNewPath((String)importContext.getParaInfo().getTaskDir(), (String)"Temp");
                String tempFilePath = PathUtil.createNewPath((String)tempPath, (String)OrderGenerator.newOrder());
                try (FileInputStream inStream = new FileInputStream(zipFile);){
                    ZipHelper.unzipFile((String)tempFilePath, (InputStream)inStream);
                }
                List files = PathUtil.getFileList((String)tempFilePath, (boolean)false, (String)"");
                String linkId = linkDefine.getKey();
                AttachmentObj linkobj = this.getDataLinkAttachment(linkId);
                String fileGroup = null;
                String partition = "NR_LINK_TEMP";
                if (linkobj == null) {
                    linkobj = new AttachmentObj();
                    fileGroup = UUID.randomUUID().toString();
                } else {
                    fileGroup = linkobj.getGroupKey();
                    if (StringUtils.isEmpty((CharSequence)fileGroup)) {
                        fileGroup = UUID.randomUUID().toString();
                    } else {
                        int id = fileGroup.indexOf("|");
                        if (id > 0) {
                            partition = fileGroup.substring(fileGroup.indexOf("|") + 1);
                            fileGroup = fileGroup.substring(0, id);
                        }
                    }
                }
                if (linkobj.getDocument() == null) {
                    linkobj.setDocument(new ArrayList());
                }
                if (linkobj.getImg() == null) {
                    linkobj.setImg(new ArrayList());
                }
                if (linkobj.getStadio() == null) {
                    linkobj.setStadio(new ArrayList());
                }
                if (linkobj.getVedio() == null) {
                    linkobj.setVedio(new ArrayList());
                }
                if (linkobj.getZip() == null) {
                    linkobj.setZip(new ArrayList());
                }
                if (linkobj.getMinSize() == null) {
                    linkobj.setMinSize("");
                }
                if (linkobj.getMaxNumber() == null) {
                    linkobj.setMaxNumber("");
                }
                if (linkobj.getMaxSize() == null) {
                    linkobj.setMaxSize("");
                }
                if (StringUtils.isEmpty((CharSequence)fileGroup)) {
                    fileGroup = UUID.randomUUID().toString();
                }
                List<FileInfo> oldFileInfos = this.attachMenentService.getFileInGroup(fileGroup, partition);
                HashMap<String, FileInfo> oldFileInfoDic = new HashMap<String, FileInfo>();
                if (oldFileInfos != null && !oldFileInfos.isEmpty()) {
                    for (FileInfo info : oldFileInfos) {
                        oldFileInfoDic.put(info.getName(), info);
                    }
                }
                String value = "";
                for (String fileName : files) {
                    File file1 = new File(fileName);
                    if (oldFileInfoDic.containsKey(file1.getName())) {
                        this.attachMenentService.deleteFile((FileInfo)oldFileInfoDic.get(file1.getName()), true);
                    }
                    byte[] buffer = null;
                    try (FileInputStream fis = new FileInputStream(file1);
                         ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);){
                        int n;
                        byte[] b = new byte[1000];
                        while ((n = fis.read(b)) != -1) {
                            bos.write(b, 0, n);
                        }
                        buffer = bos.toByteArray();
                    }
                    catch (FileNotFoundException e) {
                        log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
                    }
                    catch (IOException e) {
                        log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
                    }
                    byte[] data = buffer;
                    value = this.attachMenentService.uploadFile(file1.getName(), fileGroup, data, partition);
                }
                if (StringUtils.isNotEmpty((CharSequence)value)) {
                    linkobj.setGroupKey(value);
                } else {
                    linkobj.setGroupKey(fileGroup + "|" + partition);
                }
                String attrCode = JacksonUtils.objectToJson((Object)linkobj);
                this.formService.updateBigDataDefine(linkId, "ATTACHMENT", DesignFormDefineBigDataUtil.StringToBytes((String)attrCode));
                if (StringUtils.isNotEmpty((CharSequence)fieldDefine.getDefaultValue())) {
                    fieldDefine.setDefaultValue(null);
                }
            }
            catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private AttachmentObj getDataLinkAttachment(String linkKey) {
        AttachmentObj attachmentObj = null;
        try {
            byte[] bigData = this.formService.getBigData(linkKey, "ATTACHMENT");
            if (null == bigData) {
                return null;
            }
            String attachment = DesignFormDefineBigDataUtil.bytesToString((byte[])bigData);
            if (!"".equals(attachment)) {
                attachmentObj = (AttachmentObj)JacksonUtils.jsonToObject((String)attachment, AttachmentObj.class);
            }
            return attachmentObj;
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    private DesignDataField createNewFieldDefine(String fieldCode, String oldFieldCode, DesignDataTable table, boolean needPrefix, String formCode) throws JQException {
        DesignDataField fieldDefine = this.dataSchemeService.initDataField();
        if (table.getDataTableType() == DataTableType.DETAIL) {
            fieldDefine.setCode(this.replaceSysChar(table.getCode(), fieldCode));
        } else if (needPrefix) {
            if (StringUtils.isNotEmpty((CharSequence)formCode)) {
                fieldDefine.setCode(formCode + "_" + oldFieldCode);
            } else {
                fieldDefine.setCode(table.getCode() + "_" + oldFieldCode);
            }
        } else {
            fieldDefine.setCode(this.replaceSysChar(formCode, fieldCode));
        }
        fieldDefine.setKey(UUID.randomUUID().toString());
        if (fieldDefine.getCode().length() > 50) {
            fieldDefine.setCode(OrderGenerator.newOrder() + "_" + fieldCode);
        }
        fieldDefine.setUseAuthority(Boolean.valueOf(false));
        fieldDefine.setAlias(oldFieldCode);
        fieldDefine.setDataSchemeKey(table.getDataSchemeKey());
        fieldDefine.setDataFieldKind(DataFieldKind.FIELD_ZB);
        fieldDefine.setAllowMultipleSelect(Boolean.valueOf(false));
        if (table != null) {
            if (table.getDataTableType() == DataTableType.DETAIL) {
                fieldDefine.setDataFieldKind(DataFieldKind.FIELD);
            }
            fieldDefine.setDataTableKey(table.getKey());
        }
        return fieldDefine;
    }

    @Override
    public Grid2Data getFormGrid(TaskImportContext importContext, DesignFormDefine formDefine) {
        Grid2Data rGrid = null;
        if (null != importContext.getFormInfoCahche()) {
            if (importContext.getFormInfoCahche().getFormGrid() != null) {
                rGrid = (Grid2Data)importContext.getFormInfoCahche().getFormGrid();
            } else if (null != formDefine && null != formDefine.getBinaryData()) {
                rGrid = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
                importContext.getFormInfoCahche().setFormGrid(rGrid);
            }
        }
        return rGrid;
    }

    @Override
    public DataTableGatherType getTableGatherType(FieldDefs def, boolean isFix) {
        DataTableGatherType gatherType = DataTableGatherType.CLASSIFY;
        gatherType = !isFix ? ("0".equals(def.getRegionInfo().getSummaryWidth()) ? DataTableGatherType.CLASSIFY : ("00".equals(def.getRegionInfo().getSummaryWidth()) ? DataTableGatherType.LIST : ("000".equals(def.getRegionInfo().getSummaryWidth()) || "0000".equals(def.getRegionInfo().getSummaryWidth()) ? DataTableGatherType.NONE : DataTableGatherType.CLASSIFY))) : (def.getRegionInfo() != null && StringUtils.isNotEmpty((CharSequence)def.getRegionInfo().getSumField()) ? DataTableGatherType.LIST : DataTableGatherType.CLASSIFY);
        return gatherType;
    }

    @Override
    public int getMaxFieldCountInTable() {
        int count = 950;
        if (StringUtils.isNotEmpty((CharSequence)this.splitcount)) {
            try {
                int aCount = Integer.parseInt(this.splitcount);
                if (aCount > 1 && aCount < count) {
                    count = aCount;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return count;
    }

    @Override
    public int getMaxTableFieldSize() {
        int size = 6553500;
        if (this.splitTableSize > 1000 && this.splitTableSize < Integer.MAX_VALUE) {
            size = this.splitTableSize;
        }
        return size;
    }

    private Map<String, CompareDataFMDMFieldDTO> getCompareFMDMFields(String comopareInfoKey) {
        HashMap<String, CompareDataFMDMFieldDTO> oldFMDMFieldDic = new HashMap<String, CompareDataFMDMFieldDTO>();
        if (StringUtils.isNotEmpty((CharSequence)comopareInfoKey)) {
            CompareDataFMDMFieldDTO fmdmQueryParam = new CompareDataFMDMFieldDTO();
            fmdmQueryParam.setInfoKey(comopareInfoKey);
            fmdmQueryParam.setDataType(CompareDataType.DATA_FMDMFIELD);
            List<CompareDataFMDMFieldDTO> oldFMDMFieldList = this.fmdmdFieldCompareService.list(fmdmQueryParam);
            for (CompareDataFMDMFieldDTO oldData : oldFMDMFieldList) {
                oldFMDMFieldDic.put(oldData.getSingleCode(), oldData);
            }
        }
        return oldFMDMFieldDic;
    }

    private Map<String, CompareDataFieldDTO> getCompareDataFields(String comopareInfoKey, String formCompareKey) {
        HashMap<String, CompareDataFieldDTO> oldFieldDic = new HashMap<String, CompareDataFieldDTO>();
        if (StringUtils.isNotEmpty((CharSequence)comopareInfoKey)) {
            CompareDataFieldDTO fieldQueryParam = new CompareDataFieldDTO();
            fieldQueryParam.setInfoKey(comopareInfoKey);
            fieldQueryParam.setDataType(CompareDataType.DATA_FIELD);
            fieldQueryParam.setFormCompareKey(formCompareKey);
            List<CompareDataFieldDTO> oldFieldList = this.fieldCompareService.list(fieldQueryParam);
            oldFieldDic = new HashMap();
            for (CompareDataFieldDTO oldField : oldFieldList) {
                oldFieldDic.put(oldField.getSingleCode(), oldField);
            }
        }
        return oldFieldDic;
    }

    private String replaceSysChar(String tableName, String fieldName) {
        String code = fieldName;
        if ("LEVEL".equalsIgnoreCase(fieldName) && StringUtils.isNotEmpty((CharSequence)tableName)) {
            code = tableName + "_" + fieldName;
        }
        return code;
    }
}

