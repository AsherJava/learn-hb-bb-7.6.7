/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.consts.ZBDataType
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  nr.single.map.configurations.file.ini.BufferIni
 *  nr.single.map.configurations.file.ini.IniBuffer
 *  nr.single.map.configurations.file.ini.MemStream
 *  nr.single.map.configurations.file.ini.Stream
 *  nr.single.map.configurations.file.ini.StreamIniBuffer
 */
package nr.single.para.compare.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.map.configurations.file.ini.BufferIni;
import nr.single.map.configurations.file.ini.IniBuffer;
import nr.single.map.configurations.file.ini.MemStream;
import nr.single.map.configurations.file.ini.Stream;
import nr.single.map.configurations.file.ini.StreamIniBuffer;
import nr.single.para.basedata.IOrgDataDefineService;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.CompareDataFieldExtendDTO;
import nr.single.para.compare.definition.ISingleCompareDataFMDMFieldService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.common.FieldUseType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.system.SingleParaOptionsService;
import nr.single.para.compare.internal.util.CompareTypeMan;
import nr.single.para.compare.service.FMDMDefineCompareService;
import nr.single.para.parain.internal.cache.FieldInfoCache;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.service.IParaImportCommonService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FMDMDefineCompareServiceImpl
implements FMDMDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(FMDMDefineCompareServiceImpl.class);
    @Autowired
    private IOrgDataDefineService orgDataDefineSevice;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private ISingleCompareDataFMDMFieldService fmdmdFieldService;
    @Autowired
    private SingleParaOptionsService paraOptionService;

    @Override
    public boolean compareFMDMDefine(ParaCompareContext compareContext) throws Exception {
        String entityCode = null;
        String tableCode = null;
        String mdTableCode = null;
        boolean dataSchemeIsMulityEntity = false;
        CompareTypeMan compareFieldMan = new CompareTypeMan();
        HashMap<String, FieldInfoCache> findMDInfoFields = new HashMap<String, FieldInfoCache>();
        HashMap<String, FieldInfoCache> findEntityFields = new HashMap<String, FieldInfoCache>();
        HashMap<String, FieldInfoCache> findDataFields = new HashMap<String, FieldInfoCache>();
        DesignDataTable mdInfoTable = null;
        if (compareContext.getCache().getDataScheme() == null) {
            if (StringUtils.isNotEmpty((String)compareContext.getOption().getCorpEntityId())) {
                entityCode = this.loadEntityFieldsById(compareContext.getOption().getCorpEntityId(), findEntityFields, compareFieldMan);
            }
        } else {
            List dataTables = this.dataSchemeSevice.getAllDataTableBySchemeAndTypes(compareContext.getDataSchemeKey(), new DataTableType[]{DataTableType.MD_INFO});
            if (!CollectionUtils.isEmpty(dataTables)) {
                mdInfoTable = (DesignDataTable)dataTables.get(0);
                mdTableCode = this.loadMdInfoFields(compareContext, findMDInfoFields, compareFieldMan, mdInfoTable);
            }
            entityCode = compareContext.getOption() != null && StringUtils.isNotEmpty((String)compareContext.getOption().getCorpEntityId()) ? this.loadEntityFieldsById(compareContext.getOption().getCorpEntityId(), findEntityFields, compareFieldMan) : this.loadEntityFields(compareContext, findEntityFields, compareFieldMan);
            tableCode = this.loadDataFields(compareContext, findDataFields, compareFieldMan);
            List dims2 = this.dataSchemeSevice.getDataSchemeDimension(compareContext.getDataSchemeKey(), DimensionType.UNIT_SCOPE);
            if (dims2 != null && dims2.size() > 0) {
                dataSchemeIsMulityEntity = true;
            }
        }
        this.loadFormMapping(compareContext, compareFieldMan);
        CompareDataFMDMFieldDTO fmdmQueryParam = new CompareDataFMDMFieldDTO();
        fmdmQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        fmdmQueryParam.setDataType(CompareDataType.DATA_FMDMFIELD);
        List<CompareDataFMDMFieldDTO> oldFMDMFieldList = this.fmdmdFieldService.list(fmdmQueryParam);
        HashMap<String, CompareDataFMDMFieldDTO> oldFMDMFieldDic = new HashMap<String, CompareDataFMDMFieldDTO>();
        for (CompareDataFMDMFieldDTO oldData : oldFMDMFieldList) {
            oldFMDMFieldDic.put(oldData.getSingleCode(), oldData);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CompareDataFieldExtendDTO extData = new CompareDataFieldExtendDTO();
        ArrayList<CompareDataFMDMFieldDTO> addItems = new ArrayList<CompareDataFMDMFieldDTO>();
        ArrayList<CompareDataFMDMFieldDTO> updateItems = new ArrayList<CompareDataFMDMFieldDTO>();
        FMRepInfo repInfo = compareContext.getParaInfo().getFmRepInfo();
        for (ZBInfo zb : repInfo.getDefs().getZbsNoZDM()) {
            FieldInfoCache fieldInfo;
            String fieldName;
            String singleFieldName = fieldName = zb.getFieldName();
            String fieldTitle = zb.getZbTitle();
            if (this.paraOptionService.isFieldMatchOnlyMapping() && StringUtils.isNotEmpty((String)zb.getMappingCode())) {
                fieldName = zb.getMappingCode();
            }
            if ("PARENTCODE".equalsIgnoreCase(fieldName) || "CODE".equalsIgnoreCase(fieldName) || "ORGCODE".equalsIgnoreCase(fieldName)) continue;
            boolean isTitleField = false;
            if (StringUtils.isNotEmpty((String)repInfo.getDWMCFieldName()) && repInfo.getDWMCFieldName().equalsIgnoreCase(singleFieldName)) {
                fieldName = "NAME";
                fieldTitle = "\u540d\u79f0";
                isTitleField = true;
            }
            CompareDataFMDMFieldDTO fmdmDTO = null;
            boolean isNew = false;
            if (oldFMDMFieldDic.containsKey(singleFieldName)) {
                fmdmDTO = (CompareDataFMDMFieldDTO)oldFMDMFieldDic.get(singleFieldName);
                updateItems.add(fmdmDTO);
            } else {
                fmdmDTO = new CompareDataFMDMFieldDTO();
                fmdmDTO.setKey(UUID.randomUUID().toString());
                addItems.add(fmdmDTO);
                isNew = true;
            }
            fmdmDTO.setInfoKey(compareContext.getComapreResult().getCompareId());
            fmdmDTO.setDataType(CompareDataType.DATA_FMDMFIELD);
            fmdmDTO.setSingleUseType(this.getFieldUseType(repInfo, zb));
            fmdmDTO.setSingleCode(zb.getFieldName());
            fmdmDTO.setSingleTitle(zb.getZbTitle());
            fmdmDTO.setOrder(OrderGenerator.newOrder());
            extData.setFieldType(this.paraImportService.getFieldType(zb.getDataType()));
            fmdmDTO.setSingleData(objectMapper.writeValueAsString((Object)extData));
            boolean isFieldNew = false;
            if (this.paraOptionService.isFieldMatchOnlyMapping()) {
                if (findMDInfoFields.containsKey(fieldName)) {
                    fieldInfo = (FieldInfoCache)findMDInfoFields.get(fieldName);
                    this.setFmdmDTO(fmdmDTO, fieldInfo, zb, mdTableCode);
                } else if (findEntityFields.containsKey(fieldName)) {
                    fieldInfo = (FieldInfoCache)findEntityFields.get(fieldName);
                    this.setFmdmDTO(fmdmDTO, fieldInfo, zb, mdTableCode);
                } else if (findDataFields.containsKey(fieldName)) {
                    fieldInfo = (FieldInfoCache)findDataFields.get(fieldName);
                    this.setFmdmDTO(fmdmDTO, fieldInfo, zb, mdTableCode);
                } else {
                    isFieldNew = true;
                }
            } else if (compareFieldMan.getNetTitleItemDic().containsKey(fieldTitle)) {
                fmdmDTO.setUpdateType(CompareUpdateType.UPDATA_USENET);
                List<CompareDataDO> netTitleItemList = compareFieldMan.getNetTitleItemDic().get(fieldTitle);
                CompareDataDO fieldCData = null;
                CompareDataDO fieldCDataMDinfo = null;
                CompareDataDO fieldCDataEntity = null;
                CompareDataDO fieldCDataData = null;
                ArrayList<CompareDataDO> netTitleItemListMdInfo = new ArrayList<CompareDataDO>();
                ArrayList<CompareDataDO> netTitleItemListEntity = new ArrayList<CompareDataDO>();
                ArrayList<CompareDataDO> netTitleItemListData = new ArrayList<CompareDataDO>();
                for (CompareDataDO cData : netTitleItemList) {
                    DesignDataTable dataTable2;
                    FieldInfoCache fieldInfo2 = (FieldInfoCache)cData.getObjectValue("fieldInfoCache");
                    if (fieldName.equalsIgnoreCase(fieldInfo2.getFieldCode())) {
                        if (cData.getObjectValue("attribute") != null) {
                            fieldCDataEntity = cData;
                            continue;
                        }
                        if (cData.getObjectValue("dataTable") == null) continue;
                        dataTable2 = (DesignDataTable)cData.getObjectValue("dataTable");
                        if (dataTable2 != null && dataTable2.getDataTableType() == DataTableType.MD_INFO) {
                            fieldCDataMDinfo = cData;
                            continue;
                        }
                        fieldCDataData = cData;
                        continue;
                    }
                    if (cData.getObjectValue("attribute") != null) {
                        netTitleItemListEntity.add(cData);
                        continue;
                    }
                    if (cData.getObjectValue("dataTable") == null) continue;
                    dataTable2 = (DesignDataTable)cData.getObjectValue("dataTable");
                    if (dataTable2 != null && dataTable2.getDataTableType() == DataTableType.MD_INFO) {
                        netTitleItemListMdInfo.add(cData);
                        continue;
                    }
                    netTitleItemListData.add(cData);
                }
                if (fieldCDataMDinfo != null) {
                    fieldCData = fieldCDataMDinfo;
                } else if (fieldCDataEntity != null) {
                    fieldCData = fieldCDataEntity;
                } else if (fieldCDataData != null) {
                    fieldCData = fieldCDataData;
                }
                FieldInfoCache fieldInfo2 = null;
                DesignDataTable dataTable = null;
                if (fieldCData != null) {
                    fieldInfo2 = (FieldInfoCache)fieldCData.getObjectValue("fieldInfoCache");
                    dataTable = (DesignDataTable)fieldCData.getObjectValue("dataTable");
                    fmdmDTO.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                } else {
                    fieldCData = !netTitleItemListMdInfo.isEmpty() ? (CompareDataDO)netTitleItemListMdInfo.get(0) : (!netTitleItemListEntity.isEmpty() ? (CompareDataDO)netTitleItemListEntity.get(0) : (!netTitleItemListData.isEmpty() ? (CompareDataDO)netTitleItemListData.get(0) : netTitleItemList.get(0)));
                    fieldInfo2 = (FieldInfoCache)fieldCData.getObjectValue("fieldInfoCache");
                    dataTable = (DesignDataTable)fieldCData.getObjectValue("dataTable");
                    fmdmDTO.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                }
                if (fieldCData.getObjectValue("attribute") != null) {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_ORG);
                }
                if (fieldInfo2.getFieldDefine().getEntityAttr() != null) {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_ORG);
                } else if (dataTable != null && dataTable.getDataTableType() == DataTableType.MD_INFO) {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_MDINFO);
                } else {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_FIX);
                }
                fmdmDTO.setOwnerTableKey(fieldInfo2.getOwnerTableCode());
                fmdmDTO.setNetCode(fieldInfo2.getFieldCode());
                fmdmDTO.setNetKey(fieldInfo2.getFieldDefine().getKey());
                fmdmDTO.setNetTitle(fieldInfo2.getFieldTitle());
                fmdmDTO.setMatchKey(fmdmDTO.getNetKey());
            } else if (findMDInfoFields.containsKey(fieldName)) {
                fieldInfo = (FieldInfoCache)findMDInfoFields.get(fieldName);
                this.setFmdmDTO(fmdmDTO, fieldInfo, zb, mdTableCode);
            } else if (findEntityFields.containsKey(fieldName)) {
                fieldInfo = (FieldInfoCache)findEntityFields.get(fieldName);
                this.setFmdmDTO(fmdmDTO, fieldInfo, zb, mdTableCode);
            } else if (findDataFields.containsKey(fieldName)) {
                fieldInfo = (FieldInfoCache)findDataFields.get(fieldName);
                this.setFmdmDTO(fmdmDTO, fieldInfo, zb, mdTableCode);
            } else {
                isFieldNew = true;
            }
            if (!isFieldNew) continue;
            fmdmDTO.setNetCode(fieldName);
            fmdmDTO.setNetTitle(fieldTitle);
            fmdmDTO.setUpdateType(CompareUpdateType.UPDATE_NEW);
            fmdmDTO.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            if (dataSchemeIsMulityEntity) {
                if (fmdmDTO.getSingleUseType() == FieldUseType.USE_BBLX || fmdmDTO.getSingleUseType() == FieldUseType.USE_QYMC) {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_ORG);
                } else if (zb.getDataType() == ZBDataType.PICTURE || zb.getDataType() == ZBDataType.ATTATCHMENT) {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_FIX);
                } else {
                    fmdmDTO.setOwnerTableType(CompareTableType.TABLE_MDINFO);
                }
            } else if (fmdmDTO.getSingleUseType() == FieldUseType.USE_OTHER && (zb.getDataType() == ZBDataType.INTEGER || zb.getDataType() == ZBDataType.NUMERIC)) {
                fmdmDTO.setOwnerTableType(CompareTableType.TABLE_MDINFO);
            } else if (zb.getDataType() == ZBDataType.PICTURE || zb.getDataType() == ZBDataType.ATTATCHMENT) {
                fmdmDTO.setOwnerTableType(CompareTableType.TABLE_FIX);
            } else if (fmdmDTO.getSingleUseType() == FieldUseType.USE_BBLX || fmdmDTO.getSingleUseType() == FieldUseType.USE_QYMC) {
                fmdmDTO.setOwnerTableType(CompareTableType.TABLE_ORG);
            } else if (zb.getDataType() == ZBDataType.PICTURE || zb.getDataType() == ZBDataType.ATTATCHMENT) {
                fmdmDTO.setOwnerTableType(CompareTableType.TABLE_FIX);
            } else if (mdInfoTable != null) {
                fmdmDTO.setOwnerTableType(CompareTableType.TABLE_MDINFO);
            } else {
                fmdmDTO.setOwnerTableType(CompareTableType.TABLE_ORG);
            }
            if (!isTitleField) continue;
            fmdmDTO.setOwnerTableKey(entityCode);
        }
        if (addItems.size() > 0) {
            this.fmdmdFieldService.batchAdd(addItems);
        }
        if (updateItems.size() > 0) {
            this.fmdmdFieldService.batchUpdate(updateItems);
        }
        return true;
    }

    private void setFmdmDTO(CompareDataFMDMFieldDTO fmdmDTO, FieldInfoCache fieldInfo, ZBInfo zb, String mdTableCode) {
        fmdmDTO.setUpdateType(CompareUpdateType.UPDATA_USENET);
        if (fieldInfo.getFieldDefine().getTitle().equalsIgnoreCase(zb.getZbTitle())) {
            fmdmDTO.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
        } else {
            fmdmDTO.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
        }
        if (fieldInfo.getFieldDefine().getEntityAttr() != null) {
            fmdmDTO.setOwnerTableType(CompareTableType.TABLE_ORG);
        } else if (fieldInfo.getOwnerTableCode().equalsIgnoreCase(mdTableCode)) {
            fmdmDTO.setOwnerTableType(CompareTableType.TABLE_MDINFO);
        } else {
            fmdmDTO.setOwnerTableType(CompareTableType.TABLE_FIX);
        }
        fmdmDTO.setOwnerTableKey(fieldInfo.getOwnerTableCode());
        fmdmDTO.setNetCode(fieldInfo.getFieldCode());
        fmdmDTO.setNetKey(fieldInfo.getFieldDefine().getKey());
        fmdmDTO.setNetTitle(fieldInfo.getFieldTitle());
        fmdmDTO.setMatchKey(fmdmDTO.getNetKey());
    }

    private FieldUseType getFieldUseType(FMRepInfo repInfo, ZBInfo zb) {
        FieldUseType result = FieldUseType.USE_OTHER;
        if (zb.getFieldName().equalsIgnoreCase(repInfo.getDWMCFieldName())) {
            result = FieldUseType.USE_QYMC;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getDWDMFieldName())) {
            result = FieldUseType.USE_QYDM;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getBBLXField())) {
            result = FieldUseType.USE_BBLX;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getLevelField())) {
            result = FieldUseType.USE_TREELEVEL;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getPeriodField())) {
            result = FieldUseType.USE_SQ;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getSJDMField())) {
            result = FieldUseType.USE_SJQYDM;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getZBDMField())) {
            result = FieldUseType.USE_JTZB;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getSNDMField())) {
            result = FieldUseType.USE_SNDM;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getXBYSField())) {
            result = FieldUseType.USE_XBYS;
        } else if (zb.getFieldName().equalsIgnoreCase(repInfo.getXJHSField())) {
            result = FieldUseType.USE_ZSHS;
        }
        return result;
    }

    private boolean loadOrgFields(ParaCompareContext compareContext) {
        List dims = this.dataSchemeSevice.getDataSchemeDimension(compareContext.getDataSchemeKey(), DimensionType.UNIT);
        String entityId = ((DesignDataDimension)dims.get(0)).getDimKey();
        OrgCategoryDO table = this.orgDataDefineSevice.queryOrgDatadefine(EntityUtils.getId((String)entityId));
        if (table != null) {
            List zbs = table.getZbs();
            for (ZB zB : zbs) {
            }
        }
        return true;
    }

    private String loadEntityFields(ParaCompareContext compareContext, Map<String, FieldInfoCache> findFields, CompareTypeMan compareFieldMan) throws Exception {
        List dims2 = this.dataSchemeSevice.getDataSchemeDimension(compareContext.getDataSchemeKey(), DimensionType.UNIT_SCOPE);
        String entityId = null;
        if (dims2 != null && dims2.size() > 0) {
            entityId = ((DesignDataDimension)dims2.get(0)).getDimKey();
        } else {
            List dims = this.dataSchemeSevice.getDataSchemeDimension(compareContext.getDataSchemeKey(), DimensionType.UNIT);
            entityId = ((DesignDataDimension)dims.get(0)).getDimKey();
        }
        return this.loadEntityFieldsById(entityId, findFields, compareFieldMan);
    }

    private String loadEntityFieldsById(String entityId, Map<String, FieldInfoCache> findFields, CompareTypeMan compareFieldMan) throws Exception {
        IEntityModel entityMode = this.entityMetaService.getEntityModel(entityId);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        Iterator iterator = entityMode.getAttributes();
        while (iterator.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)iterator.next();
            String code = this.replaceSysChar(entityDefine.getCode(), attribute.getCode());
            FieldInfoCache fieldInfoCache = new FieldInfoCache(entityDefine.getCode(), new FieldInfoDefine(attribute));
            findFields.put(code, fieldInfoCache);
            CompareDataDO cData = compareFieldMan.addNetItem(code, attribute.getTitle(), attribute.getID());
            cData.setObjectValue("fieldInfoCache", fieldInfoCache);
            cData.setObjectValue("attribute", attribute);
        }
        String entityCode = null;
        if (entityDefine != null) {
            entityCode = entityDefine.getCode();
        }
        return entityCode;
    }

    public String loadDataFields(ParaCompareContext compareContext, Map<String, FieldInfoCache> findFields, CompareTypeMan compareFieldMan) {
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)compareContext.getCache().getDataScheme().getPrefix())) {
            fileFlag = compareContext.getCache().getDataScheme().getPrefix() + "_";
        }
        String tableFlag = String.format("%s%s_JCB", fileFlag, "FMDM");
        DesignDataTable dataTable = null;
        DesignDataTable findTableDefine = this.dataSchemeSevice.getDataTableByCode(tableFlag);
        if (findTableDefine != null) {
            if (StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey()) && compareContext.getDataSchemeKey().equalsIgnoreCase(findTableDefine.getDataSchemeKey())) {
                dataTable = findTableDefine;
            } else {
                int iCount = 1;
                String oldTableCode = tableFlag;
                while (findTableDefine != null) {
                    tableFlag = oldTableCode + "_" + String.valueOf(iCount);
                    findTableDefine = this.dataSchemeSevice.getDataTableByCode(tableFlag);
                    ++iCount;
                    if (findTableDefine == null) break;
                    if (!StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey()) || !compareContext.getDataSchemeKey().equalsIgnoreCase(findTableDefine.getDataSchemeKey())) continue;
                    dataTable = findTableDefine;
                    break;
                }
            }
        }
        this.loadFieldsFromTable(dataTable, findFields, compareFieldMan);
        return tableFlag;
    }

    public String loadMdInfoFields(ParaCompareContext compareContext, Map<String, FieldInfoCache> findFields, CompareTypeMan compareFieldMan, DesignDataTable mdInfoTable) {
        String tableFlag = null;
        if (StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey())) {
            this.loadFieldsFromTable(mdInfoTable, findFields, compareFieldMan);
            tableFlag = mdInfoTable.getCode();
        }
        return tableFlag;
    }

    private void loadFieldsFromTable(DesignDataTable dataTable, Map<String, FieldInfoCache> findFields, CompareTypeMan compareFieldMan) {
        if (dataTable != null) {
            List fields = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
            for (DesignDataField field : fields) {
                String code = this.replaceSysChar(dataTable.getCode(), field.getCode());
                FieldInfoCache fieldInfoCache = new FieldInfoCache(dataTable.getCode(), new FieldInfoDefine(field));
                findFields.put(code, fieldInfoCache);
                CompareDataDO cData = compareFieldMan.addNetItem(code, field.getTitle(), field.getKey());
                cData.setObjectValue("fieldInfoCache", fieldInfoCache);
                cData.setObjectValue("field", field);
                cData.setObjectValue("dataTable", dataTable);
            }
        }
    }

    private void loadFormMapping(ParaCompareContext compareContext, CompareTypeMan compareFieldMan) throws Exception {
        String filePath;
        ParaInfo paraInfo;
        if (this.paraOptionService.isFieldMatchOnlyMapping() && (paraInfo = compareContext.getParaInfo()) != null && StringUtils.isNotEmpty((String)paraInfo.getTaskDir()) && SinglePathUtil.getFileExists((String)(filePath = compareContext.getParaInfo().getTaskDir() + "PARA" + File.separatorChar + "MapNexus.Ini"))) {
            File file = new File(FilenameUtils.normalize(filePath));
            try (FileInputStream srcStream = new FileInputStream(file);){
                MemStream stream = new MemStream();
                stream.copyFrom((InputStream)srcStream, (long)((InputStream)srcStream).available());
                StreamIniBuffer aBuffer = new StreamIniBuffer((Stream)stream);
                BufferIni ini = new BufferIni((IniBuffer)aBuffer);
                ini.readInfo();
                FMRepInfo rep = paraInfo.getFmRepInfo();
                for (ZBInfo zb : rep.getDefs().getZbsNoZDM()) {
                    String mapping = ini.readString(rep.getCode(), zb.getFieldName(), "");
                    if (StringUtils.isEmpty((String)mapping)) continue;
                    String netFieldCode = mapping;
                    String netTableCode = null;
                    int id1 = netFieldCode.indexOf(91);
                    int id2 = netFieldCode.indexOf(93);
                    if (id1 >= 0 && id2 >= 0 && id1 < id2) {
                        netTableCode = mapping.substring(0, id1);
                        netFieldCode = mapping.substring(id1 + 1, id2);
                    }
                    zb.setMappingCode(netFieldCode);
                    zb.setMappingTalbe(netTableCode);
                    if (!StringUtils.isEmpty(netTableCode) || !compareFieldMan.getNetCodeItemDic().containsKey(netFieldCode)) continue;
                    List<CompareDataDO> netFields = compareFieldMan.getNetCodeItemDic().get(netFieldCode);
                    for (CompareDataDO oFieldData : netFields) {
                        oFieldData.setObjectValue("MappingFieldCode", zb.getFieldName());
                        oFieldData.setObjectValue("MappingTableCode", rep.getCode());
                    }
                }
            }
        }
    }

    private String replaceSysChar(String tableName, String fieldName) {
        String code = fieldName;
        if (fieldName.startsWith(tableName + "_")) {
            code = fieldName.substring((tableName + "_").length(), fieldName.length());
        }
        return code;
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        CompareDataFMDMFieldDTO compareDataDTO = new CompareDataFMDMFieldDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.fmdmdFieldService.delete(compareDataDTO);
    }
}

