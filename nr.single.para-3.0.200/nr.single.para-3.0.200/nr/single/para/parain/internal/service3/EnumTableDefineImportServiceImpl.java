/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.va.basedata.common.BaseDataConsts
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.StorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 *  nr.single.map.param.service.SingleParamFileService
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.va.basedata.common.BaseDataConsts;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.StorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.param.service.SingleParamFileService;
import nr.single.para.basedata.IBaseDataDefineService;
import nr.single.para.basedata.IBaseDataVerService;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.ISingleCompareDataEnumItemService;
import nr.single.para.compare.definition.ISingleCompareDataEnumService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IEnumBBLXDefineImportService;
import nr.single.para.parain.service.IEnumTableDefineImportService;
import nr.single.para.parain.util.IEnumLevelCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnumTableDefineImportServiceImpl
implements IEnumTableDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(EnumTableDefineImportServiceImpl.class);
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private IBaseDataDefineService baseDefineService;
    @Autowired
    private IEnumLevelCodeUtil enumLevelCodeUtil;
    @Autowired
    private IEnumBBLXDefineImportService enumBBLXService;
    @Autowired
    private ISingleCompareDataEnumService enumDataService;
    @Autowired
    private ISingleCompareDataEnumItemService enumItemService;
    @Autowired
    private IFormTypeApplyService formTypeApplyService;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private SingleParamFileService singleParamService;
    @Autowired
    private IBaseDataVerService baseVerService;

    @Override
    public void importEnumTableDefines(TaskImportContext importContext) throws Exception {
        DesignTaskDefine task = importContext.getTaskDefine();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        if (null == task) {
            throw new Exception("\u8be5\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u5148\u5bfc\u5165!");
        }
        if (null == formScheme) {
            throw new Exception("\u8be5\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u5148\u5bfc\u5165!");
        }
        int historyType = 0;
        int year = 0;
        if (StringUtils.isNotEmpty((String)importContext.getParaInfo().getTaskYear())) {
            year = Integer.parseInt(importContext.getParaInfo().getTaskYear());
        }
        historyType = this.getHistoryTyte(task.getKey(), year);
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        BaseDataGroupDO groupr = this.baseDefineService.getAndInsertBaseDataGroup("NR_GROUP", "\u62a5\u8868", "");
        BaseDataGroupDO group0 = this.baseDefineService.getAndInsertBaseDataGroup("NR_DT_" + dataScheme.getCode(), dataScheme.getTitle(), groupr.getName());
        BaseDataGroupDO group = this.baseDefineService.getAndInsertBaseDataGroup("NR_ENUM_" + dataScheme.getCode(), "\u679a\u4e3e\u5b57\u5178", group0.getName());
        double startPos = importContext.getCurProgress();
        ParaImportInfoResult enumsLog = null;
        HashMap<String, CompareDataEnumDTO> oldEnumDic = new HashMap<String, CompareDataEnumDTO>();
        if (importContext.getCompareInfo() != null) {
            CompareDataEnumDTO enumQueryParam = new CompareDataEnumDTO();
            enumQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            enumQueryParam.setDataType(CompareDataType.DATA_ENUM);
            List<CompareDataEnumDTO> oldEnumList = this.enumDataService.list(enumQueryParam);
            for (CompareDataEnumDTO compareDataEnumDTO : oldEnumList) {
                oldEnumDic.put(compareDataEnumDTO.getSingleCode(), compareDataEnumDTO);
                importContext.getCompareEnumDic().put(compareDataEnumDTO.getSingleCode(), compareDataEnumDTO);
            }
            if (importContext.getImportResult() != null) {
                enumsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_ENUM, "enums", "\u679a\u4e3e");
            }
        }
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = "_" + dataScheme.getPrefix().toUpperCase();
        }
        importContext.getMapScheme().getEnumInfos().clear();
        Map enums = importContext.getParaInfo().getEnunList();
        for (Map.Entry entry : enums.entrySet()) {
            boolean enumIsBBLX;
            EnumsItemModel singleEnum = (EnumsItemModel)entry.getValue();
            if (StringUtils.isEmpty((String)singleEnum.getCode())) continue;
            String enumName = String.format("MD%s%s", fileFlag, "_" + singleEnum.getCode().toUpperCase());
            importContext.onProgress(startPos += 0.1 / (double)enums.size(), "\u5bfc\u5165\u679a\u4e3e\uff1a" + singleEnum.getCode());
            log.info("\u5bfc\u5165\u679a\u4e3e\uff1a" + singleEnum.getCode());
            CompareDataEnumDTO enumCompare = null;
            ParaImportInfoResult enumLog = null;
            if (oldEnumDic.containsKey(singleEnum.getCode())) {
                enumCompare = (CompareDataEnumDTO)oldEnumDic.get(singleEnum.getCode());
                enumName = enumCompare.getNetCode();
                if (enumsLog != null) {
                    enumLog = new ParaImportInfoResult();
                    enumLog.copyForm(enumCompare);
                    enumLog.setSuccess(true);
                    enumsLog.addItem(enumLog);
                }
            }
            if (enumIsBBLX = "BBLX".equalsIgnoreCase(singleEnum.getCode())) {
                enumName = String.format("MD%s%s", "_" + singleEnum.getCode().toUpperCase(), fileFlag);
                this.enumBBLXService.importEnumBBLXDefine(importContext, singleEnum, enumCompare);
                continue;
            }
            this.importNewEnum(importContext, singleEnum, enumName, fileFlag, tenantName, group.getName(), enumCompare, year, historyType, enumLog);
        }
    }

    private void importNewEnum(TaskImportContext importContext, EnumsItemModel singleEnum, String enumName, String fileFlag, String tenantName, String groupName, CompareDataEnumDTO enumCompare, int year, int historyType, ParaImportInfoResult enumLog) throws Exception {
        boolean enumIsBBLX = "BBLX".equalsIgnoreCase(singleEnum.getCode());
        String enumTile = singleEnum.getTitle();
        if (StringUtils.isEmpty((String)enumTile)) {
            log.info("\u5bfc\u5165\u679a\u4e3e\uff0c\u679a\u4e3e\u6807\u9898\u4e3a\u7a7a\uff1a", (Object)singleEnum.getCode());
            enumTile = singleEnum.getCode();
        }
        boolean enumDataChange = false;
        List<CompareDataEnumItemDTO> oldEnumItemList = null;
        if (enumCompare != null) {
            CompareDataEnumItemDTO enumItemQueryParam = new CompareDataEnumItemDTO();
            enumItemQueryParam.setInfoKey(enumCompare.getInfoKey());
            enumItemQueryParam.setEnumCompareKey(enumCompare.getKey());
            enumItemQueryParam.setDataType(CompareDataType.DATA_ENUMITEM);
            oldEnumItemList = this.enumItemService.list(enumItemQueryParam);
            for (CompareDataEnumItemDTO oldData : oldEnumItemList) {
                if (oldData.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                    enumDataChange = true;
                    break;
                }
                if (oldData.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                    enumDataChange = true;
                    break;
                }
                if (oldData.getUpdateType() != CompareUpdateType.UPDATA_USESINGLE) continue;
                if (StringUtils.isNotEmpty((String)oldData.getNetCode()) && !oldData.getNetCode().equalsIgnoreCase(oldData.getSingleCode())) {
                    enumDataChange = true;
                    break;
                }
                if (!StringUtils.isNotEmpty((String)oldData.getNetTitle()) || oldData.getNetTitle().equalsIgnoreCase(oldData.getSingleTitle())) continue;
                enumDataChange = true;
                break;
            }
        }
        if (!enumDataChange && oldEnumItemList != null) {
            PageVO queryRes;
            BaseDataDTO queryParam = new BaseDataDTO();
            queryParam.setTableName(enumName);
            queryParam.setStopflag(Integer.valueOf(-1));
            queryParam.setRecoveryflag(Integer.valueOf(0));
            queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            if (year > 1970 && year < 3000) {
                Date[] dates = this.baseVerService.getDateRegion(year);
                queryParam.setVersionDate(dates[0]);
            }
            if ((queryRes = this.baseDataClient.list(queryParam)).getRows().size() != oldEnumItemList.size()) {
                enumDataChange = true;
            }
        }
        String baseID = null;
        BaseDataDefineDO baseDefine = null;
        if (enumCompare != null) {
            if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                enumName = enumCompare.getNetCode();
                enumTile = enumCompare.getNetTitle();
                baseDefine = this.creatBaseDataDefine(singleEnum, tenantName, enumName, enumTile, enumIsBBLX, groupName, historyType, enumDataChange, enumLog);
                baseID = baseDefine.getId().toString();
            } else {
                if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                    if (StringUtils.isNotEmpty((String)enumCompare.getMatchKey())) {
                        enumName = enumCompare.getNetCode();
                        baseID = enumCompare.getMatchKey();
                        this.UpdateEnumMap(importContext, singleEnum, enumName, baseID);
                    }
                    return;
                }
                if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_OVER) {
                    enumName = enumCompare.getNetCode();
                    baseDefine = this.creatBaseDataDefine(singleEnum, tenantName, enumName, enumTile, enumIsBBLX, groupName, historyType, enumDataChange, enumLog);
                    baseID = baseDefine.getId().toString();
                } else {
                    if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) {
                        return;
                    }
                    if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                        enumName = enumCompare.getNetCode();
                        enumTile = enumCompare.getNetTitle();
                        baseID = enumCompare.getNetKey();
                        baseDefine = this.queryBaseDataDefine(tenantName, enumName, historyType, enumDataChange);
                    }
                }
            }
        } else {
            baseDefine = this.creatBaseDataDefine(singleEnum, tenantName, enumName, enumTile, enumIsBBLX, groupName, historyType, enumDataChange, enumLog);
            baseID = baseDefine.getId().toString();
        }
        if (enumIsBBLX) {
            DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
            DataModelDO origalDataModel = this.getCreateDataModel(tenantName, enumName, enumTile, enumIsBBLX);
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(enumName);
            dataModelDTO.setTenantName(tenantName);
            DataModelDO dataModelDO = client.get(dataModelDTO);
            origalDataModel.setColumns(StorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
            client.push(origalDataModel);
        }
        boolean needVersion = baseDefine != null && baseDefine.getVersionflag() != null && baseDefine.getVersionflag() == 1;
        boolean isVersion = false;
        Date verDate = null;
        if (historyType > 0 && year > 1970 && year < 3000 && needVersion) {
            BaseDataVersionDO ver = null;
            ver = enumDataChange ? this.baseVerService.insertYearVerion(enumName, year, historyType < 3) : this.baseVerService.queryYearVerion(enumName, year, historyType < 3);
            if (ver != null && ver.getValidtime().getTime() == BaseDataConsts.VERSION_MIN_DATE.getTime() && ver.getInvalidtime().getTime() == BaseDataConsts.VERSION_MAX_DATE.getTime()) {
                isVersion = false;
            } else if (ver != null) {
                Date[] dates = this.baseVerService.getDateRegion(year);
                if (dates != null && dates.length > 0) {
                    verDate = dates[0];
                }
                isVersion = true;
            }
        }
        log.info("\u5bfc\u5165\u679a\u4e3e\u5b9a\u4e49\u5b8c\u6210\uff1a" + enumName);
        SingleFileEnumInfo mapEnum = this.UpdateEnumMap(importContext, singleEnum, enumName, baseID);
        try {
            this.ImportEnumDatasBatch(importContext, singleEnum, enumName, enumCompare, mapEnum, isVersion, verDate, enumDataChange, oldEnumItemList, enumLog);
        }
        catch (Exception e) {
            log.error("\u5bfc\u5165\u679a\u4e3e\u6570\u636e\u5931\u8d25:" + enumName + "," + e.getMessage(), e);
        }
    }

    private DataModelDO getCreateDataModel(String tenantName, String tablename, String tabletitle, boolean enumIsBBLX) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setGroupcode("public");
        dataModelDO.setName(tablename);
        dataModelDO.setTitle(tabletitle);
        DataModelTemplateEntity template = StorageUtil.getDataModelTemplate((String)"basedata", (String)tablename);
        dataModelDO.setColumns(template.getTemplateFields());
        dataModelDO.addColumn("ICON").columnTitle("\u56fe\u6807").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.setIndexConsts(template.getTemplateIndexs());
        return dataModelDO;
    }

    private BaseDataDefineDO creatBaseDataDefine(EnumsItemModel singleEnum, String tenantName, String tablename, String tabletitle, boolean enumIsBBLX, String groupName, int historyType, boolean enumDataChange, ParaImportInfoResult enumLog) {
        BaseDataDefineDTO result = null;
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataQueryDTO = new BaseDataDefineDTO();
        baseDataQueryDTO.setTenantName(tenantName);
        baseDataQueryDTO.setName(tablename);
        BaseDataDefineDO oldDefine = client.get(baseDataQueryDTO);
        if (oldDefine == null) {
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setTenantName(tenantName);
            baseDataDefineDTO.setName(tablename);
            baseDataDefineDTO.setId(UUID.randomUUID());
            baseDataDefineDTO.setStructtype(Integer.valueOf(1));
            baseDataDefineDTO.setTitle(tabletitle);
            baseDataDefineDTO.setGroupname(groupName);
            baseDataDefineDTO.setSharetype(Integer.valueOf(0));
            baseDataDefineDTO.setVersionflag(Integer.valueOf(0));
            if (enumIsBBLX) {
                baseDataDefineDTO.setSolidifyflag(Integer.valueOf(0));
            } else {
                baseDataDefineDTO.setSolidifyflag(Integer.valueOf(0));
            }
            if (enumIsBBLX) {
                baseDataDefineDTO.setDefaultShowColumns(EnumTableDefineImportServiceImpl.getDefaultShowColumns(enumIsBBLX));
            }
            this.setBaseDataLevel(singleEnum, baseDataDefineDTO);
            R r = client.add(baseDataDefineDTO);
            if (r.getCode() != 0) {
                if (enumLog != null) {
                    enumLog.setSuccess(false);
                    enumLog.setMessage(tablename + "\u679a\u4e3e\u65b0\u589e\u5931\u8d25\uff1a" + r.getMsg());
                }
                log.info(tablename + "\u679a\u4e3e\u65b0\u589e\u5931\u8d25\uff1a" + r.getMsg());
            }
            result = baseDataDefineDTO;
        } else {
            BaseDataDefineDTO defineDTO = new BaseDataDefineDTO();
            defineDTO.setId(oldDefine.getId());
            defineDTO.setTenantName(tenantName);
            defineDTO.setName(tablename);
            defineDTO.setModifytime(new Date());
            defineDTO.setDefine(oldDefine.getDefine());
            defineDTO.setTitle(tabletitle);
            if (enumIsBBLX) {
                defineDTO.setDefaultShowColumns(EnumTableDefineImportServiceImpl.getDefaultShowColumns(enumIsBBLX));
            }
            if (historyType > 0 && (oldDefine.getVersionflag() == 0 || oldDefine.getVersionflag() == null) && enumDataChange) {
                defineDTO.setVersionflag(Integer.valueOf(1));
                defineDTO.setDimensionflag(Integer.valueOf(1));
            } else {
                defineDTO.setVersionflag(oldDefine.getVersionflag());
                defineDTO.setDimensionflag(oldDefine.getDimensionflag());
            }
            this.setBaseDataLevel(singleEnum, defineDTO);
            R r = client.update(defineDTO);
            if (r.getCode() != 0) {
                if (enumLog != null) {
                    enumLog.setSuccess(false);
                    enumLog.setMessage(tablename + "\u679a\u4e3e\u66f4\u65b0\u5931\u8d25\uff1a" + r.getMsg());
                }
                log.info(tablename + "\u679a\u4e3e\u66f4\u65b0\u5931\u8d25\uff1a" + r.getMsg());
            }
            result = defineDTO;
        }
        return result;
    }

    private BaseDataDefineDO queryBaseDataDefine(String tenantName, String tablename, int historyType, boolean enumDataChange) {
        BaseDataDefineDO oldDefine;
        BaseDataDefineDO result = null;
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataQueryDTO = new BaseDataDefineDTO();
        baseDataQueryDTO.setTenantName(tenantName);
        baseDataQueryDTO.setName(tablename);
        result = oldDefine = client.get(baseDataQueryDTO);
        if (historyType > 0 && oldDefine.getVersionflag() == 0 && enumDataChange) {
            BaseDataDefineDTO defineDTO = new BaseDataDefineDTO();
            defineDTO.setId(oldDefine.getId());
            defineDTO.setTenantName(tenantName);
            defineDTO.setName(tablename);
            defineDTO.setModifytime(new Date());
            defineDTO.setDefine(oldDefine.getDefine());
            defineDTO.setVersionflag(Integer.valueOf(1));
            defineDTO.setDimensionflag(Integer.valueOf(1));
            R r = client.update(defineDTO);
            if (r.getCode() != 0) {
                log.info(tablename + "\u679a\u4e3e\u66f4\u65b0\u5931\u8d25\uff1a" + r.getMsg());
            }
            result = defineDTO;
        }
        return result;
    }

    private void setBaseDataLevel(EnumsItemModel singleEnum, BaseDataDefineDTO enumDefine) {
        if (0 == singleEnum.getTreeTyep() && StringUtils.isNotEmpty((String)singleEnum.getCodeStruct())) {
            if (StringUtils.isNotEmpty((String)singleEnum.getCodeStruct())) {
                enumDefine.setStructtype(Integer.valueOf(3));
                String levelCode = singleEnum.getCodeStruct().replace(";", "");
                if (StringUtils.isEmpty((String)levelCode)) {
                    levelCode = String.valueOf(singleEnum.getCodeLen());
                }
                if (singleEnum.getFix()) {
                    levelCode = levelCode + "#" + String.valueOf(singleEnum.getCodeLen());
                }
                enumDefine.setLevelcode(levelCode);
            } else {
                enumDefine.setStructtype(Integer.valueOf(0));
            }
        } else if (1 == singleEnum.getTreeTyep()) {
            enumDefine.setStructtype(Integer.valueOf(2));
        } else if (StringUtils.isEmpty((String)singleEnum.getCodeStruct())) {
            enumDefine.setStructtype(Integer.valueOf(0));
        }
    }

    private static List<Map<String, Object>> getDefaultShowColumns(boolean enumIsBBLX) {
        ArrayList<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)"\u4ee3\u7801", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)"\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"SHORTNAME", (String)"\u7b80\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        if (enumIsBBLX) {
            fields.add(BaseDataStorageUtil.getColumnMap((String)"ICON", (String)"\u56fe\u6807", (String)DataModelType.ColumnType.NVARCHAR.name()));
        }
        return fields;
    }

    private SingleFileEnumInfo UpdateEnumMap(TaskImportContext importContext, EnumsItemModel singleEnum, String netEnumCode, String netEnumKey) {
        List enumList = importContext.getMapScheme().getEnumInfos();
        SingleFileEnumInfo mapEnum = importContext.getMapScheme().getNewEnumInfo();
        mapEnum.setEnumCode(singleEnum.getCode());
        mapEnum.setEnumTitle(singleEnum.getTitle());
        mapEnum.setNetTableCode(netEnumCode);
        mapEnum.setNetTableKey(netEnumKey);
        enumList.add(mapEnum);
        return mapEnum;
    }

    private void ImportEnumDatasBatch(TaskImportContext importContext, EnumsItemModel singleEnum, String tableCode, CompareDataEnumDTO enumCompare, SingleFileEnumInfo mapEnum, Boolean isVersion, Date verDate, boolean enumDataChange, List<CompareDataEnumItemDTO> oldEnumItemList, ParaImportInfoResult enumLog) throws Exception {
        BaseDataDTO data;
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        HashMap<String, Object> sinleEnumItems = new HashMap<String, Object>();
        for (Object item : singleEnum.getItemDataList()) {
            if (sinleEnumItems.containsKey(item.getCode())) continue;
            sinleEnumItems.put(item.getCode(), item);
        }
        HashMap<String, CompareDataEnumItemDTO> oldEnumItemDic = new HashMap<String, CompareDataEnumItemDTO>();
        if (enumCompare != null && oldEnumItemList != null) {
            for (CompareDataEnumItemDTO oldData : oldEnumItemList) {
                oldEnumItemDic.put(oldData.getSingleCode(), oldData);
            }
        }
        ParaImportInfoResult enumItemsLog = null;
        if (enumCompare != null && importContext.getImportResult() != null) {
            enumItemsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_ENUMITEM, "enumItems", "\u679a\u4e3e\u9879");
        }
        BaseDataDTO queryParam2 = new BaseDataDTO();
        queryParam2.setTableName(tableCode);
        queryParam2.setStopflag(Integer.valueOf(-1));
        queryParam2.setRecoveryflag(Integer.valueOf(-1));
        queryParam2.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        if (isVersion.booleanValue() && verDate != null) {
            queryParam2.setVersionDate(verDate);
        }
        PageVO queryRes = this.baseDataClient.list(queryParam2);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableCode);
        BaseDataBatchOptDTO basedataBatchOptDTO = new BaseDataBatchOptDTO();
        basedataBatchOptDTO.setHighTrustability(true);
        basedataBatchOptDTO.setTenantName(tenantName);
        basedataBatchOptDTO.setQueryParam(queryParam);
        ArrayList<Object> batchDataList = new ArrayList<Object>();
        basedataBatchOptDTO.setDataList(batchDataList);
        boolean isUseVersion = false;
        if (isVersion.booleanValue() && verDate != null) {
            queryParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
            queryParam.setVersionDate(verDate);
            basedataBatchOptDTO.setTableCover(false);
            isUseVersion = true;
        }
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        HashMap<String, BaseDataDTO> oldDataMap = new HashMap<String, BaseDataDTO>();
        HashMap<String, DataInfo> enumDic = new HashMap<String, DataInfo>();
        if (queryRes != null && queryRes.getRows() != null && queryRes.getRows().size() > 0) {
            for (BaseDataDO oldData : queryRes.getRows()) {
                String netCode = oldData.getCode();
                String netTitle = oldData.getName();
                String netKey = oldData.getId().toString();
                data = new BaseDataDTO();
                data.setId(oldData.getId());
                data.setCode(netCode);
                data.setName(netTitle);
                data.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
                data.setTenantName(tenantName);
                data.setTableName(tableCode);
                data.setStopflag(oldData.getStopflag());
                data.setRecoveryflag(oldData.getRecoveryflag());
                data.put("checkParentDisable", oldData.get((Object)"checkParentDisable"));
                data.setParentcode(oldData.getParentcode());
                oldDataMap.put(data.getCode(), data);
            }
        }
        for (DataInfo item : singleEnum.getItemDataList()) {
            if (enumDic.containsKey(item.getCode())) continue;
            String netItemCode = item.getCode();
            String netItemTitle = item.getName();
            CompareDataEnumItemDTO enumItemCompare = null;
            if (oldEnumItemDic.containsKey(item.getCode())) {
                enumItemCompare = (CompareDataEnumItemDTO)oldEnumItemDic.get(item.getCode());
                if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetTitle())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_KEEP) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetTitle())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetTitle())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATA_USENET) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetTitle())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() != CompareUpdateType.UPDATA_USESINGLE && enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetTitle())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                }
                if (enumItemsLog != null) {
                    ParaImportInfoResult enumItemLog = new ParaImportInfoResult();
                    enumItemLog.copyForm(enumItemCompare);
                    enumItemLog.setCode(singleEnum.getCode() + "_" + enumItemCompare.getNetCode());
                    enumItemLog.setSuccess(true);
                    enumItemLog.setParentCompareKey(enumCompare.getKey());
                    enumItemsLog.addItem(enumItemLog);
                }
            }
            data = null;
            if (oldDataMap.containsKey(netItemCode)) {
                BaseDataDO oldData = (BaseDataDO)oldDataMap.get(netItemCode);
                data = new BaseDataDTO();
                data.setId(UUID.randomUUID());
                data.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
            } else {
                data = new BaseDataDTO();
                data.setId(UUID.randomUUID());
                data.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
            }
            if (StringUtils.isEmpty((String)netItemTitle)) {
                netItemTitle = netItemCode;
            }
            data.setCode(netItemCode);
            data.setName(netItemTitle);
            data.setTenantName(tenantName);
            data.setTableName(tableCode);
            data.setStopflag(Integer.valueOf(0));
            data.setRecoveryflag(Integer.valueOf(0));
            data.put("checkParentDisable", (Object)true);
            String parentCode = null;
            if (StringUtils.isNotEmpty((String)singleEnum.getCodeStruct())) {
                parentCode = this.enumLevelCodeUtil.getLevelParentCode(netItemCode, singleEnum.getCodeStruct(), singleEnum.getFix());
                while (!sinleEnumItems.containsKey(parentCode) && StringUtils.isNotEmpty((String)parentCode)) {
                    String newParentCode = this.enumLevelCodeUtil.getLevelParentCode(parentCode, singleEnum.getCodeStruct(), singleEnum.getFix());
                    if (StringUtils.isNotEmpty((String)newParentCode) && newParentCode.equalsIgnoreCase(parentCode)) {
                        parentCode = newParentCode;
                        break;
                    }
                    parentCode = newParentCode;
                }
            } else {
                parentCode = item.getParent();
            }
            if (StringUtils.isEmpty((String)parentCode)) {
                parentCode = "-";
            }
            data.setParentcode(parentCode);
            if (!StringUtils.isNotEmpty((String)item.getParent()) || dataMap.containsKey(item.getParent())) {
                // empty if block
            }
            if (!dataMap.containsKey(netItemCode)) {
                dataMap.put(data.getCode(), data);
                batchDataList.add(data);
            }
            enumDic.put(item.getCode(), item);
            SingleFileEnumItem mapItem = mapEnum.getNewEnumItem();
            mapItem.setItemCode(item.getCode());
            mapItem.setItemTitle(item.getName());
            mapItem.setNetItemCode(netItemCode);
            mapItem.setNetItemTitle(netItemTitle);
            mapEnum.getEnumItems().add(mapItem);
        }
        if (oldDataMap.size() > 0) {
            for (String netCode : oldDataMap.keySet()) {
                if (dataMap.containsKey(netCode)) continue;
                BaseDataDO oldData = (BaseDataDO)oldDataMap.get(netCode);
                if (isUseVersion) {
                    oldData.setRecoveryflag(Integer.valueOf(1));
                }
                batchDataList.add(oldData);
                dataMap.put(netCode, oldData);
            }
        }
        if (batchDataList.size() > 0) {
            R r = this.baseDataClient.sync(basedataBatchOptDTO);
            log.info("\u679a\u4e3e\u9879\u6dfb\u52a0\uff1a" + r.getMsg());
            List list = (List)r.get((Object)"results");
            if (list != null) {
                for (R r2 : list) {
                    if (r2.getCode() == 0) continue;
                    log.info("\u679a\u4e3e\u9879\u66f4\u65b0\uff1a" + r2.getMsg() + r2.get((Object)"BASEDATACODE").toString());
                    String errItemCode = r2.get((Object)"BASEDATACODE").toString();
                    String errMsg = r2.getMsg();
                    if (enumItemsLog == null || !enumItemsLog.getCodeFinder().containsKey(singleEnum.getCode() + "_" + errItemCode)) continue;
                    ParaImportInfoResult enumItemLog = enumItemsLog.getCodeFinder().get(singleEnum.getCode() + "_" + errItemCode);
                    enumItemLog.setSuccess(false);
                    enumItemLog.setMessage(errMsg);
                }
            }
        }
    }

    private int getHistoryTyte(String taskKey, int year) throws Exception {
        int historyType = 0;
        List formSchemes = this.viewController.queryFormSchemeByTask(taskKey);
        if (formSchemes.size() <= 0) {
            historyType = 0;
        } else {
            HashSet<Integer> AllYears = new HashSet<Integer>();
            int maxYear = 0;
            int minYear = 3000;
            for (DesignFormSchemeDefine formScheme1 : formSchemes) {
                ParaInfo pInfo = this.singleParamService.getSingleTaskInfo(taskKey, formScheme1.getKey());
                if (pInfo == null || !StringUtils.isNotEmpty((String)pInfo.getTaskYear())) continue;
                Integer year1 = Integer.parseInt(pInfo.getTaskYear());
                AllYears.add(year1);
                if (maxYear < year1) {
                    maxYear = year1;
                }
                if (minYear <= year1) continue;
                minYear = year1;
            }
            if (AllYears.size() > 0) {
                if (year < minYear) {
                    historyType = 1;
                } else if (year >= minYear && year < maxYear) {
                    historyType = 2;
                } else if (year > minYear && year >= maxYear) {
                    historyType = 3;
                }
            }
        }
        return historyType;
    }
}

