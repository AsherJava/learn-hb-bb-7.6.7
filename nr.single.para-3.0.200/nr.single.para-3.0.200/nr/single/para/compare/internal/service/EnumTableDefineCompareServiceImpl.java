/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.ISingleCompareDataEnumItemService;
import nr.single.para.compare.definition.ISingleCompareDataEnumService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.system.SingleParaOptionsService;
import nr.single.para.compare.internal.util.CompareTypeMan;
import nr.single.para.compare.service.EnumItemDefineCompareService;
import nr.single.para.compare.service.EnumTableDefineCompareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnumTableDefineCompareServiceImpl
implements EnumTableDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(EnumTableDefineCompareServiceImpl.class);
    @Autowired
    private ISingleCompareDataEnumService enumDataService;
    @Autowired
    private EnumItemDefineCompareService enumItemCompareService;
    @Autowired
    private ISingleCompareDataEnumItemService enumItemService;
    @Autowired
    private SingleParaOptionsService paraOptionService;

    @Override
    public boolean compareEnumTableDefine(ParaCompareContext compareContext) throws Exception {
        double startPos = compareContext.getCurProgress();
        String fileFlag = this.getFileFlag(compareContext);
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        Map<String, CompareDataEnumDTO> oldEnumDic = this.getOldEnumDic(compareContext);
        CompareTypeMan compareMan = this.getCompareManCache(tenantName, fileFlag);
        ArrayList<CompareDataEnumDTO> addItems = new ArrayList<CompareDataEnumDTO>();
        ArrayList<CompareDataEnumDTO> updateItems = new ArrayList<CompareDataEnumDTO>();
        Map enums = compareContext.getParaInfo().getEnunList();
        double curProgress = startPos;
        double addProgrees = compareContext.getCurProgressLength() / (double)(enums.size() + 1);
        for (Map.Entry entry : enums.entrySet()) {
            boolean isEnumNew;
            EnumsItemModel singleEnum = (EnumsItemModel)entry.getValue();
            if (StringUtils.isEmpty((String)singleEnum.getCode())) continue;
            String enumName = this.getEnumName(fileFlag, singleEnum.getCode());
            String enumTitle = singleEnum.getTitle();
            compareContext.onProgress(Math.floor((curProgress += addProgrees) * 10000.0) / 10000.0, "\u6bd4\u8f83\u679a\u4e3e\uff1a" + enumName);
            log.info("\u6bd4\u8f83\u679a\u4e3e\uff1a" + singleEnum.getCode());
            CompareDataEnumDTO dataItem = null;
            if (oldEnumDic.containsKey(singleEnum.getCode())) {
                dataItem = oldEnumDic.get(singleEnum.getCode());
                updateItems.add(dataItem);
                dataItem.setNetKey(null);
                dataItem.setMatchKey(null);
                dataItem.setDataType(CompareDataType.DATA_ENUM);
            } else {
                dataItem = new CompareDataEnumDTO();
                dataItem.setKey(UUID.randomUUID().toString());
                if (this.paraOptionService.isEnumItemOnlyCode()) {
                    dataItem.setCompareType(CompareContextType.CONTEXT_CODE);
                } else {
                    dataItem.setCompareType(CompareContextType.CONTEXT_TITLE);
                }
                dataItem.setDataType(CompareDataType.DATA_ENUM);
                addItems.add(dataItem);
            }
            dataItem.setSingleCode(singleEnum.getCode());
            dataItem.setSingleTitle(singleEnum.getTitle());
            dataItem.setSingleCodeFix(singleEnum.getFix());
            dataItem.setSingleCodeLen(singleEnum.getCodeLen());
            dataItem.setSingleLevelCode(singleEnum.getCodeStruct());
            dataItem.setSingleStructType(this.getSingleStructType(singleEnum));
            dataItem.setOrder(OrderGenerator.newOrder());
            CompareDataDO findData = compareMan.getCompareUpdateAndChangeType(enumName, enumTitle, compareContext.getOption().getEnumCompareType(), dataItem);
            boolean bl = isEnumNew = dataItem.getUpdateType() == CompareUpdateType.UPDATE_NEW;
            if (findData != null) {
                this.setDataItemAttrByBaseData(dataItem, findData);
                enumName = dataItem.getNetCode();
            } else {
                dataItem.setNetCode(enumName);
                dataItem.setNetTitle(enumTitle);
                dataItem.setNetCodeFix(dataItem.getSingleCodeFix());
                dataItem.setNetCodeLen(dataItem.getSingleCodeLen());
                dataItem.setNetLevelCode(dataItem.getSingleLevelCode());
                dataItem.setNetStructType(dataItem.getSingleStructType());
            }
            dataItem.setInfoKey(compareContext.getComapreResult().getCompareId());
            this.compareOneEnum(compareContext, singleEnum, enumName, tenantName, isEnumNew, dataItem.getCompareType(), dataItem);
            if (dataItem.getChangeType() == CompareChangeType.CHANGE_FLAGTITLESAME && dataItem.getItemChangeType() == CompareChangeType.CHANGE_SAME) {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_IGNORE);
                continue;
            }
            if (dataItem.getUpdateType() != CompareUpdateType.UPDATE_IGNORE) continue;
            dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
        }
        if (addItems.size() > 0) {
            this.enumDataService.batchAdd(addItems);
        }
        if (updateItems.size() > 0) {
            this.enumDataService.batchUpdate(updateItems);
        }
        compareContext.getParams().put("EnumAllItems", null);
        compareContext.getParams().put("EnumAllDefines", null);
        return true;
    }

    @Override
    public boolean compareEnumTableDefinePrefix(ParaCompareContext compareContext, List<String> enumCompareKeys) throws Exception {
        double startPos = compareContext.getCurProgress();
        String fileFlag = this.getFileFlag(compareContext);
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        Map<String, CompareDataEnumDTO> oldEnumDic = this.getOldEnumDic(compareContext);
        CompareTypeMan compareMan = this.getCompareManCache(tenantName, fileFlag);
        HashMap<String, CompareDataEnumDTO> oldEnumKeyDic = new HashMap<String, CompareDataEnumDTO>();
        for (CompareDataEnumDTO oldEnumCompare : oldEnumDic.values()) {
            oldEnumKeyDic.put(oldEnumCompare.getKey(), oldEnumCompare);
        }
        ArrayList<CompareDataEnumDTO> addItems = new ArrayList<CompareDataEnumDTO>();
        ArrayList<CompareDataEnumDTO> updateItems = new ArrayList<CompareDataEnumDTO>();
        Map singleEnums = compareContext.getParaInfo().getEnunList();
        double curProgress = startPos;
        double addProgrees = compareContext.getCurProgressLength() / (double)(enumCompareKeys.size() + 1);
        for (String enumCompareKey : enumCompareKeys) {
            boolean isEnumNew;
            CompareDataEnumDTO dataItem = null;
            if (!oldEnumKeyDic.containsKey(enumCompareKey)) continue;
            dataItem = (CompareDataEnumDTO)oldEnumKeyDic.get(enumCompareKey);
            updateItems.add(dataItem);
            dataItem.setNetKey(null);
            dataItem.setMatchKey(null);
            dataItem.setDataType(CompareDataType.DATA_ENUM);
            EnumsItemModel singleEnum = (EnumsItemModel)singleEnums.get(dataItem.getSingleCode());
            if (singleEnum == null || StringUtils.isEmpty((String)singleEnum.getCode())) continue;
            String enumName = this.getEnumName(fileFlag, singleEnum.getCode());
            String enumTitle = singleEnum.getTitle();
            compareContext.onProgress(Math.floor((curProgress += addProgrees) * 10000.0) / 10000.0, "\u6bd4\u8f83\u679a\u4e3e\uff1a" + enumName);
            log.info("\u6bd4\u8f83\u679a\u4e3e\uff1a" + singleEnum.getCode());
            CompareDataDO findData = compareMan.getCompareUpdateAndChangeType(enumName, enumTitle, compareContext.getOption().getEnumCompareType(), dataItem);
            boolean bl = isEnumNew = dataItem.getUpdateType() == CompareUpdateType.UPDATE_NEW;
            if (findData != null) {
                this.setDataItemAttrByBaseData(dataItem, findData);
                enumName = dataItem.getNetCode();
            } else {
                dataItem.setNetCode(enumName);
                dataItem.setNetTitle(enumTitle);
                dataItem.setNetCodeFix(dataItem.getSingleCodeFix());
                dataItem.setNetCodeLen(dataItem.getSingleCodeLen());
                dataItem.setNetLevelCode(dataItem.getSingleLevelCode());
                dataItem.setNetStructType(dataItem.getSingleStructType());
            }
            dataItem.setInfoKey(compareContext.getComapreResult().getCompareId());
            this.compareOneEnum(compareContext, singleEnum, enumName, tenantName, isEnumNew, dataItem.getCompareType(), dataItem);
            if (dataItem.getChangeType() == CompareChangeType.CHANGE_FLAGTITLESAME && dataItem.getItemChangeType() == CompareChangeType.CHANGE_SAME) {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_IGNORE);
                continue;
            }
            if (dataItem.getUpdateType() != CompareUpdateType.UPDATE_IGNORE) continue;
            dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
        }
        if (addItems.size() > 0) {
            this.enumDataService.batchAdd(addItems);
        }
        if (updateItems.size() > 0) {
            this.enumDataService.batchUpdate(updateItems);
        }
        compareContext.getParams().put("EnumAllItems", null);
        compareContext.getParams().put("EnumAllDefines", null);
        return true;
    }

    @Override
    public boolean compareEnumItemDefine(ParaCompareContext compareContext, String enumCompareKey) throws Exception {
        boolean result = false;
        CompareDataEnumDTO enumQueryParam = new CompareDataEnumDTO();
        enumQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        enumQueryParam.setDataType(CompareDataType.DATA_ENUM);
        enumQueryParam.setKey(enumCompareKey);
        List<CompareDataEnumDTO> oldEnumList = this.enumDataService.list(enumQueryParam);
        if (oldEnumList.size() > 0) {
            CompareDataEnumDTO enumCompare = oldEnumList.get(0);
            if (compareContext.getOption().getEnumCompareType() != null) {
                enumCompare.setCompareType(compareContext.getOption().getEnumCompareType());
            } else if (compareContext.getOption().getItemCompareType() != null) {
                enumCompare.setCompareType(compareContext.getOption().getItemCompareType());
            }
            result = this.enumItemCompareService.compareEnumItemsDefine(compareContext, enumCompare.getSingleCode(), enumCompare.getNetCode(), StringUtils.isEmpty((String)enumCompare.getNetKey()), enumCompare.getCompareType(), enumCompare);
            if (compareContext.getOption().getUpdateType() != null) {
                enumCompare.setUpdateType(compareContext.getOption().getUpdateType());
            } else if (enumCompare.getChangeType() == CompareChangeType.CHANGE_FLAGTITLESAME && enumCompare.getItemChangeType() == CompareChangeType.CHANGE_SAME) {
                enumCompare.setUpdateType(CompareUpdateType.UPDATE_IGNORE);
            } else if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                enumCompare.setUpdateType(CompareUpdateType.UPDATE_OVER);
            }
            this.enumDataService.update(enumCompare);
        }
        return result;
    }

    private String getFileFlag(ParaCompareContext compareContext) {
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)compareContext.getOption().getEnumPrefix())) {
            fileFlag = compareContext.getOption().getEnumPrefix();
            if ("null".equalsIgnoreCase(fileFlag)) {
                fileFlag = "";
            }
        } else if (compareContext.getCache().getDataScheme() != null) {
            fileFlag = compareContext.getCache().getDataScheme().getPrefix();
        } else if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
            fileFlag = compareContext.getOption().getDataPrefix();
        }
        return fileFlag;
    }

    private CompareTypeMan getCompareManCache(String tenantName, String fileFlag) {
        CompareDataDO cData;
        String netKey;
        String netTitle;
        String netCode;
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName(tenantName);
        baseDataDefineDTO.setSearchKey("MD_");
        if (StringUtils.isNotEmpty((String)fileFlag)) {
            baseDataDefineDTO.setSearchKey("MD_" + fileFlag + "_");
        }
        CompareTypeMan compareMan = new CompareTypeMan();
        PageVO netList = client.list(baseDataDefineDTO);
        baseDataDefineDTO.setSearchKey("MD_BBLX");
        if (StringUtils.isNotEmpty((String)fileFlag)) {
            baseDataDefineDTO.setSearchKey("MD_BBLX_" + fileFlag);
        }
        PageVO netList2 = client.list(baseDataDefineDTO);
        if (netList != null) {
            for (BaseDataDefineDO data : netList.getRows()) {
                netCode = data.getName();
                netTitle = data.getTitle();
                netKey = data.getId().toString();
                cData = compareMan.addNetItem(netCode, netTitle, netKey);
                cData.setObjectValue("baseData", data);
            }
        }
        if (netList2 != null) {
            for (BaseDataDefineDO data : netList2.getRows()) {
                netCode = data.getName();
                netTitle = data.getTitle();
                netKey = data.getId().toString();
                cData = compareMan.addNetItem(netCode, netTitle, netKey);
                cData.setObjectValue("baseData", data);
            }
        }
        return compareMan;
    }

    private Map<String, CompareDataEnumDTO> getOldEnumDic(ParaCompareContext compareContext) {
        CompareDataEnumDTO enumQueryParam = new CompareDataEnumDTO();
        enumQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        enumQueryParam.setDataType(CompareDataType.DATA_ENUM);
        List<CompareDataEnumDTO> oldEnumList = this.enumDataService.list(enumQueryParam);
        HashMap<String, CompareDataEnumDTO> oldEnumDic = new HashMap<String, CompareDataEnumDTO>();
        for (CompareDataEnumDTO oldData : oldEnumList) {
            oldEnumDic.put(oldData.getSingleCode(), oldData);
        }
        compareContext.getParams().put("EnumAllDefines", oldEnumDic);
        CompareDataEnumItemDTO enumItemQueryParam = new CompareDataEnumItemDTO();
        enumItemQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        enumItemQueryParam.setDataType(CompareDataType.DATA_ENUMITEM);
        List<CompareDataEnumItemDTO> oldEnumItemList = this.enumItemService.list(enumItemQueryParam);
        HashMap oldEnumItemDics = new HashMap();
        for (CompareDataEnumItemDTO oldData1 : oldEnumItemList) {
            Map<String, CompareDataEnumItemDTO> oldEnumItemDic = null;
            if (oldEnumItemDics.containsKey(oldData1.getEnumCompareKey())) {
                oldEnumItemDic = (Map)oldEnumItemDics.get(oldData1.getEnumCompareKey());
            } else {
                oldEnumItemDic = new HashMap();
                oldEnumItemDics.put(oldData1.getEnumCompareKey(), oldEnumItemDic);
            }
            oldEnumItemDic.put(oldData1.getSingleCode(), oldData1);
        }
        compareContext.getParams().put("EnumAllItems", oldEnumItemDics);
        return oldEnumDic;
    }

    private void compareOneEnum(ParaCompareContext compareContext, EnumsItemModel singleEnum, String enumName, String tenantName, boolean isEnumNew, CompareContextType enumCompareType, CompareDataEnumDTO enumCompare) throws Exception {
        this.enumItemCompareService.compareEnumItemsDefine(compareContext, singleEnum.getCode(), enumName, isEnumNew, enumCompareType, enumCompare);
    }

    private void setDataItemAttrByBaseData(CompareDataEnumDTO dataItem, CompareDataDO findData) {
        dataItem.setNetKey(findData.getNetKey());
        dataItem.setNetCode(findData.getNetCode());
        dataItem.setNetTitle(findData.getNetTitle());
        dataItem.setMatchKey(dataItem.getNetKey());
        dataItem.setNetCodeFix(false);
        BaseDataDefineDO baseData = (BaseDataDefineDO)findData.getObjectValue("baseData");
        String levelCode = baseData.getLevelcode();
        if (StringUtils.isNotEmpty((String)levelCode) && baseData.getStructtype() == 3) {
            String levelCode1;
            int id = levelCode.indexOf("#");
            if (id >= 0) {
                String codeLen = levelCode.substring(id + 1, levelCode.length());
                levelCode = levelCode.substring(0, id);
                dataItem.setNetCodeLen(Integer.parseInt(codeLen));
                dataItem.setNetCodeFix(true);
            }
            if (StringUtils.isNotEmpty((String)(levelCode1 = levelCode))) {
                levelCode = "";
                for (int i = 0; i < levelCode1.length(); ++i) {
                    String c = String.valueOf(levelCode1.charAt(i));
                    levelCode = i == 0 ? c : levelCode + ";" + c;
                }
            }
            dataItem.setNetLevelCode(levelCode);
        }
        dataItem.setNetStructType(baseData.getStructtype());
    }

    private Integer getSingleStructType(EnumsItemModel singleEnum) {
        Integer result = null;
        if (0 == singleEnum.getTreeTyep() && StringUtils.isNotEmpty((String)singleEnum.getCodeStruct())) {
            result = StringUtils.isNotEmpty((String)singleEnum.getCodeStruct()) ? Integer.valueOf(3) : Integer.valueOf(0);
        } else if (1 == singleEnum.getTreeTyep()) {
            result = 2;
        } else if (StringUtils.isEmpty((String)singleEnum.getCodeStruct())) {
            result = 0;
        }
        return result;
    }

    private String getEnumName(String fileFlag, String singleEnumCode) {
        String enumName = "";
        boolean enumIsBBLX = "BBLX".equalsIgnoreCase(singleEnumCode);
        if (StringUtils.isNotEmpty((String)fileFlag)) {
            enumName = String.format("MD%s%s", "_" + fileFlag, "_" + singleEnumCode.toUpperCase());
            if (enumIsBBLX) {
                enumName = String.format("MD%s%s", "_" + singleEnumCode.toUpperCase(), "_" + fileFlag);
            }
        } else {
            enumName = String.format("MD%s", "_" + singleEnumCode.toUpperCase());
            if (enumIsBBLX) {
                enumName = String.format("MD%s", "_" + singleEnumCode.toUpperCase());
            }
        }
        return enumName;
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        this.enumItemCompareService.batchDelete(compareContext, compareKey);
        CompareDataEnumDTO compareDataDTO = new CompareDataEnumDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.enumDataService.delete(compareDataDTO);
    }
}

