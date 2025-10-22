/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.para.basedata.IBaseDataVerService;
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
import nr.single.para.compare.internal.service.EnumTableDefineCompareServiceImpl;
import nr.single.para.compare.internal.system.SingleParaOptionsService;
import nr.single.para.compare.internal.util.CompareTypeMan;
import nr.single.para.compare.service.EnumItemDefineCompareService;
import nr.single.para.parain.util.IEnumLevelCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnumItemDefineCompareServiceImpl
implements EnumItemDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(EnumTableDefineCompareServiceImpl.class);
    @Autowired
    private ISingleCompareDataEnumService enumDataService;
    @Autowired
    private ISingleCompareDataEnumItemService enumItemService;
    @Autowired
    private IEnumLevelCodeUtil enumLevelCodeUtil;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private IBaseDataVerService baseVerService;
    @Autowired
    private SingleParaOptionsService paraOptionService;

    @Override
    public boolean compareEnumItemsDefine(ParaCompareContext compareContext, String singleEnumCode, String enumName, boolean isEnumNew, CompareContextType enumCompareType, String enumCompareKey) throws Exception {
        EnumsItemModel singleEnum = (EnumsItemModel)compareContext.getParaInfo().getEnunList().get(singleEnumCode);
        String enumTile = singleEnum.getTitle();
        if (StringUtils.isEmpty((String)enumTile)) {
            log.info("\u6bd4\u8f83\u679a\u4e3e\uff0c\u679a\u4e3e\u6807\u9898\u4e3a\u7a7a\uff1a", (Object)singleEnum.getCode());
            enumTile = singleEnum.getCode();
        }
        try {
            CompareDataEnumDTO enumCompare = this.enumDataService.getByKey(enumCompareKey);
            if (enumCompare != null) {
                this.compareEnumDatasBatch(compareContext, singleEnum, enumCompare, enumName, enumCompareType, isEnumNew);
            }
        }
        catch (Exception e) {
            log.error("\u6bd4\u8f83\u679a\u4e3e\u6570\u636e\u5931\u8d25:" + enumName + "," + e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean compareEnumItemsDefine(ParaCompareContext compareContext, String singleEnumCode, String enumName, boolean isEnumNew, CompareContextType enumCompareType, CompareDataEnumDTO enumCompare) throws Exception {
        EnumsItemModel singleEnum = (EnumsItemModel)compareContext.getParaInfo().getEnunList().get(singleEnumCode);
        String enumTile = singleEnum.getTitle();
        if (StringUtils.isEmpty((String)enumTile)) {
            log.info("\u6bd4\u8f83\u679a\u4e3e\uff0c\u679a\u4e3e\u6807\u9898\u4e3a\u7a7a\uff1a", (Object)singleEnum.getCode());
            enumTile = singleEnum.getCode();
        }
        try {
            this.compareEnumDatasBatch(compareContext, singleEnum, enumCompare, enumName, enumCompareType, isEnumNew);
        }
        catch (Exception e) {
            log.error("\u6bd4\u8f83\u679a\u4e3e\u6570\u636e\u5931\u8d25:" + enumName + "," + e.getMessage(), e);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean compareEnumDatasBatch(ParaCompareContext compareContext, EnumsItemModel singleEnum, CompareDataEnumDTO enumCompare, String tableCode, CompareContextType compareType, boolean isNewEnum) throws Exception {
        String singleTitle;
        String singleCode;
        String enumCompareKey = enumCompare.getKey();
        int year = 0;
        if (StringUtils.isNotEmpty((String)compareContext.getParaInfo().getTaskYear())) {
            year = Integer.parseInt(compareContext.getParaInfo().getTaskYear());
        }
        HashMap<String, DataInfo> sinleCodeItems = new HashMap<String, DataInfo>();
        HashMap<String, DataInfo> sinleTitleItems = new HashMap<String, DataInfo>();
        for (DataInfo item : singleEnum.getItemDataList()) {
            sinleCodeItems.put(item.getCode(), item);
            sinleTitleItems.put(item.getName(), item);
        }
        HashMap<String, CompareDataEnumItemDTO> oldEnumItemDic = null;
        if (compareContext.getParams() != null && compareContext.getParams().containsKey("EnumAllItems")) {
            Map oldEnumItemDics = (Map)compareContext.getParams().get("EnumAllItems");
            if (oldEnumItemDics.containsKey(enumCompareKey)) {
                oldEnumItemDic = (HashMap<String, CompareDataEnumItemDTO>)oldEnumItemDics.get(enumCompareKey);
            } else if (oldEnumItemDics.size() == 0) {
                oldEnumItemDic = new HashMap<String, CompareDataEnumItemDTO>();
            }
        }
        if (oldEnumItemDic == null) {
            CompareDataEnumItemDTO enumItemQueryParam = new CompareDataEnumItemDTO();
            enumItemQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
            enumItemQueryParam.setEnumCompareKey(enumCompareKey);
            enumItemQueryParam.setDataType(CompareDataType.DATA_ENUMITEM);
            List<CompareDataEnumItemDTO> oldEnumItemList = this.enumItemService.list(enumItemQueryParam);
            oldEnumItemDic = new HashMap();
            for (CompareDataEnumItemDTO oldData : oldEnumItemList) {
                oldEnumItemDic.put(oldData.getSingleCode(), oldData);
            }
        }
        ArrayList<CompareDataEnumItemDTO> addItems = new ArrayList<CompareDataEnumItemDTO>();
        ArrayList<CompareDataEnumItemDTO> updateItems = new ArrayList<CompareDataEnumItemDTO>();
        CompareTypeMan compareEnumItemMan = new CompareTypeMan();
        HashMap<String, BaseDataDO> netCodeItems = new HashMap<String, BaseDataDO>();
        HashMap<String, BaseDataDO> netTitleItems = new HashMap<String, BaseDataDO>();
        if (!isNewEnum) {
            PageVO queryRes;
            BaseDataDTO queryParam = new BaseDataDTO();
            queryParam.setTableName(tableCode);
            queryParam.setStopflag(Integer.valueOf(-1));
            queryParam.setRecoveryflag(Integer.valueOf(0));
            queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            if (year > 1970 && year < 3000) {
                Date[] dates = this.baseVerService.getDateRegion(year);
                queryParam.setVersionDate(dates[0]);
            }
            if ((queryRes = this.baseDataClient.list(queryParam)) != null && queryRes.getRows() != null && queryRes.getRows().size() > 0) {
                for (BaseDataDO data : queryRes.getRows()) {
                    String netCode = data.getCode();
                    String netTitle = data.getName();
                    String netKey = data.getId().toString();
                    netCodeItems.put(netCode, data);
                    netTitleItems.put(netTitle, data);
                    CompareDataDO cdata = compareEnumItemMan.addNetItem(netCode, netTitle, netKey);
                    cdata.setObjectValue("baseData", data);
                }
            }
        }
        HashSet codeList = new HashSet(netCodeItems.keySet());
        boolean itemAllSame = true;
        if (!isNewEnum && netCodeItems.size() > 0) {
            for (Object item : singleEnum.getItemDataList()) {
                CompareDataEnumItemDTO parentData;
                singleCode = item.getCode();
                singleTitle = item.getName();
                String singleParent = item.getParent();
                CompareDataEnumItemDTO netItem = null;
                String oldNetTitle = null;
                String oldNetCode = null;
                CompareUpdateType oldUpdateType = null;
                if (oldEnumItemDic.containsKey(singleCode)) {
                    CompareDataEnumItemDTO oldItem = (CompareDataEnumItemDTO)oldEnumItemDic.get(singleCode);
                    netItem = this.createItemFrom(oldItem);
                    netItem.setNetKey(null);
                    netItem.setMatchKey(null);
                    oldNetTitle = netItem.getNetTitle();
                    oldNetCode = netItem.getNetCode();
                    oldUpdateType = netItem.getUpdateType();
                    updateItems.add(netItem);
                } else {
                    netItem = new CompareDataEnumItemDTO();
                    netItem.setKey(UUID.randomUUID().toString());
                    addItems.add(netItem);
                    oldEnumItemDic.put(singleCode, netItem);
                }
                netItem.setInfoKey(compareContext.getComapreResult().getCompareId());
                netItem.setSingleCode(singleCode);
                netItem.setSingleTitle(singleTitle);
                netItem.setEnumCompareKey(enumCompareKey);
                netItem.setOrder(OrderGenerator.newOrder());
                String parentCode = item.getParent();
                if (StringUtils.isNotEmpty((String)singleEnum.getCodeStruct())) {
                    parentCode = this.enumLevelCodeUtil.getLevelParentCode(singleCode, singleEnum.getCodeStruct());
                }
                this.getCompareUpdateAndChangeType(singleCode, singleTitle, parentCode, singleEnum.getCodeStruct(), netCodeItems, netTitleItems, netItem, compareEnumItemMan, compareType);
                if (StringUtils.isNotEmpty((String)parentCode) && oldEnumItemDic.containsKey(parentCode) && StringUtils.isNotEmpty((String)(parentData = (CompareDataEnumItemDTO)oldEnumItemDic.get(parentCode)).getNetCode()) && (netItem.getUpdateType() == CompareUpdateType.UPDATE_RECODE || netItem.getUpdateType() == CompareUpdateType.UPDATA_USENET || netItem.getUpdateType() == CompareUpdateType.UPDATA_USESINGLE || netItem.getUpdateType() == CompareUpdateType.UPDATE_KEEP) && StringUtils.isNotEmpty((String)parentCode) && !parentCode.equalsIgnoreCase(parentData.getNetCode())) {
                    parentCode = parentData.getNetCode();
                    netItem.setUpdateType(CompareUpdateType.UPDATE_RECODE);
                }
                if (netItem.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                    netItem.setNetCode(singleCode);
                    netItem.setNetTitle(singleTitle);
                    netItem.setNetParentCode(singleParent);
                    netItem.setNetKey(null);
                } else if (netItem.getUpdateType() != CompareUpdateType.UPDATA_USENET && netItem.getUpdateType() != CompareUpdateType.UPDATE_KEEP && netItem.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                    String newCode = singleCode;
                    try {
                        boolean needReCode = true;
                        if (StringUtils.isNotEmpty((String)singleEnum.getCodeStruct())) {
                            int curlevel = this.enumLevelCodeUtil.getLevelCodeLevel(singleCode, singleEnum.getCodeStruct(), true);
                            boolean bl = needReCode = curlevel > 0;
                        }
                        if (!needReCode) {
                            // empty if block
                        }
                    }
                    catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                    codeList.add(newCode);
                    netItem.setNetCode(null);
                    netItem.setNetTitle(singleTitle);
                    netItem.setNetKey(null);
                }
                if (oldUpdateType != null && oldUpdateType == netItem.getUpdateType() && StringUtils.isEmpty((String)netItem.getNetTitle()) && StringUtils.isNotEmpty((String)oldNetTitle)) {
                    netItem.setNetTitle(oldNetTitle);
                }
                if (netItem.getChangeType() == CompareChangeType.CHANGE_FLAGTITLESAME) continue;
                itemAllSame = false;
            }
        } else {
            itemAllSame = false;
            for (Object item : singleEnum.getItemDataList()) {
                singleCode = item.getCode();
                singleTitle = item.getName();
                CompareDataEnumItemDTO netItem = null;
                String oldNetTitle = null;
                String oldNetCode = null;
                CompareUpdateType oldUpdateType = null;
                if (oldEnumItemDic.containsKey(singleCode)) {
                    CompareDataEnumItemDTO oldItem = (CompareDataEnumItemDTO)oldEnumItemDic.get(singleCode);
                    netItem = this.createItemFrom(oldItem);
                    netItem.setNetKey(null);
                    netItem.setMatchKey(null);
                    oldNetTitle = netItem.getNetTitle();
                    oldNetCode = netItem.getNetCode();
                    oldUpdateType = netItem.getUpdateType();
                    updateItems.add(netItem);
                } else {
                    netItem = new CompareDataEnumItemDTO();
                    netItem.setKey(UUID.randomUUID().toString());
                    netItem.setDataType(CompareDataType.DATA_ENUMITEM);
                    addItems.add(netItem);
                    oldEnumItemDic.put(singleCode, netItem);
                }
                netItem.setInfoKey(compareContext.getComapreResult().getCompareId());
                netItem.setSingleCode(singleCode);
                netItem.setSingleTitle(singleTitle);
                netItem.setSingleParentCode(item.getParent());
                netItem.setNetKey(null);
                netItem.setMatchKey(null);
                netItem.setNetCode(singleCode);
                netItem.setNetTitle(singleTitle);
                netItem.setNetParentCode(item.getParent());
                netItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                netItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                netItem.setEnumCompareKey(enumCompareKey);
                if (oldUpdateType == null || oldUpdateType != netItem.getUpdateType() || !StringUtils.isEmpty((String)netItem.getNetTitle()) || !StringUtils.isNotEmpty((String)oldNetTitle)) continue;
                netItem.setNetTitle(oldNetTitle);
            }
        }
        if (addItems.size() > 0) {
            this.enumItemService.batchAdd(addItems);
        }
        if (!updateItems.isEmpty()) {
            ArrayList<CompareDataEnumItemDTO> newUpdateItems = new ArrayList<CompareDataEnumItemDTO>();
            for (CompareDataEnumItemDTO item : updateItems) {
                CompareDataEnumItemDTO oldItem;
                if (!oldEnumItemDic.containsKey(item.getSingleCode()) || this.compareItemSame(item, oldItem = (CompareDataEnumItemDTO)oldEnumItemDic.get(item.getSingleCode()))) continue;
                newUpdateItems.add(item);
            }
            if (!newUpdateItems.isEmpty()) {
                if (newUpdateItems.size() > 100 && addItems.isEmpty()) {
                    CompareDataEnumItemDTO compareDataDTO = new CompareDataEnumItemDTO();
                    compareDataDTO.setInfoKey(compareContext.getComapreResult().getCompareId());
                    compareDataDTO.setEnumCompareKey(enumCompareKey);
                    this.enumItemService.delete(compareDataDTO);
                    this.enumItemService.batchAdd(newUpdateItems);
                } else {
                    this.enumItemService.batchUpdate(updateItems);
                }
            }
        }
        if (itemAllSame) {
            enumCompare.setItemChangeType(CompareChangeType.CHANGE_SAME);
            return true;
        }
        enumCompare.setItemChangeType(CompareChangeType.CHANGE_NOSAME);
        return true;
    }

    private void getCompareUpdateAndChangeType(String singleCode, String singleTitle, String parentCode, String codeStruct, Map<String, BaseDataDO> netCodeItems, Map<String, BaseDataDO> netTitleItems, CompareDataEnumItemDTO dataItem, CompareTypeMan compareEnumItemMan, CompareContextType compareType) throws Exception {
        if (compareType == CompareContextType.CONTEXT_CODE) {
            if (netCodeItems.containsKey(singleCode)) {
                BaseDataDO data = netCodeItems.get(singleCode);
                if (StringUtils.isNotEmpty((String)data.getName()) && data.getName().equalsIgnoreCase(singleTitle)) {
                    dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                } else {
                    dataItem.setUpdateType(CompareUpdateType.UPDATA_USESINGLE);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                }
                dataItem.setNetCode(data.getCode());
                dataItem.setNetTitle(data.getName());
                dataItem.setNetKey(data.getId().toString());
                dataItem.setMatchKey(dataItem.getNetKey());
                dataItem.setNetParentCode(data.getParentcode());
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            }
        } else if (compareEnumItemMan.getNetTitleItemDic().containsKey(singleTitle)) {
            CompareDataDO matchData = null;
            List<CompareDataDO> cDataList = compareEnumItemMan.getNetTitleItemDic().get(singleTitle);
            if (cDataList.size() == 1) {
                matchData = cDataList.get(0);
            } else {
                for (CompareDataDO cData : cDataList) {
                    if (!cData.getNetCode().equalsIgnoreCase(singleCode)) continue;
                    matchData = cData;
                    break;
                }
                if (matchData == null) {
                    int curlevel = 0;
                    if (StringUtils.isNotEmpty((String)codeStruct)) {
                        curlevel = this.enumLevelCodeUtil.getLevelCodeLevel(singleCode, codeStruct, true);
                    }
                    for (CompareDataDO cData : cDataList) {
                        BaseDataDO data1 = (BaseDataDO)cData.getObjectValue("baseData");
                        String cParentCode = data1.getParentcode();
                        if (StringUtils.isNotEmpty((String)parentCode) && parentCode.equalsIgnoreCase(cParentCode)) {
                            matchData = cData;
                            break;
                        }
                        if ("-".equalsIgnoreCase(cParentCode) && (curlevel == 0 || curlevel == 1)) {
                            matchData = cData;
                            break;
                        }
                        if (!StringUtils.isEmpty((String)parentCode) || !StringUtils.isEmpty((String)cParentCode)) continue;
                        matchData = cData;
                        break;
                    }
                }
                if (matchData == null) {
                    matchData = cDataList.get(0);
                }
            }
            BaseDataDO data = (BaseDataDO)matchData.getObjectValue("baseData");
            dataItem.setNetCode(data.getCode());
            dataItem.setNetTitle(data.getName());
            dataItem.setNetKey(data.getId().toString());
            dataItem.setMatchKey(dataItem.getNetKey());
            dataItem.setNetParentCode(data.getParentcode());
            if (singleCode.equalsIgnoreCase(data.getCode())) {
                dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
                dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
            }
            dataItem.setNetKey(data.getId().toString());
            dataItem.setMatchKey(dataItem.getNetKey());
        } else if (netCodeItems.containsKey(singleCode)) {
            BaseDataDO data = netCodeItems.get(singleCode);
            dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
            dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
            dataItem.setNetCode(data.getCode());
            dataItem.setNetTitle(data.getName());
            dataItem.setNetKey(data.getId().toString());
            dataItem.setMatchKey(dataItem.getNetKey());
            dataItem.setNetParentCode(data.getParentcode());
        } else {
            dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
            dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
        }
    }

    private void getCompareUpdateAndChangeType2(String singleCode, String singleTitle, Map<String, BaseDataDO> netCodeItems, Map<String, BaseDataDO> netTitleItems, CompareContextType compareType, CompareDataDO dataItem, CompareTypeMan compareEnumItemMan) {
        if (compareType == CompareContextType.CONTEXT_TITLE) {
            if (compareEnumItemMan.getNetTitleItemDic().containsKey(singleTitle)) {
                CompareDataDO matchData = null;
                List<CompareDataDO> cDataList = compareEnumItemMan.getNetTitleItemDic().get(singleTitle);
                if (cDataList.size() == 1) {
                    matchData = cDataList.get(0);
                } else {
                    for (CompareDataDO cData : cDataList) {
                        if (!cData.getNetCode().equalsIgnoreCase(singleCode)) continue;
                        matchData = cData;
                    }
                    if (matchData == null) {
                        matchData = cDataList.get(0);
                    }
                }
                BaseDataDO data = (BaseDataDO)matchData.getObjectValue("baseData");
                dataItem.setNetCode(data.getCode());
                dataItem.setNetTitle(data.getName());
                dataItem.setNetKey(data.getId().toString());
                if (singleCode.equalsIgnoreCase(data.getCode())) {
                    dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                } else {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                }
                dataItem.setNetKey(data.getId().toString());
            } else if (netCodeItems.containsKey(singleCode)) {
                BaseDataDO data = netCodeItems.get(singleCode);
                dataItem.setUpdateType(CompareUpdateType.UPDATE_RECODE);
                dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                dataItem.setNetKey(data.getId().toString());
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            }
        } else if (compareType == CompareContextType.CONTEXT_CODE) {
            if (compareEnumItemMan.getNetCodeItemDic().containsKey(singleCode)) {
                CompareDataDO matchData = null;
                List<CompareDataDO> cDataList = compareEnumItemMan.getNetCodeItemDic().get(singleCode);
                if (cDataList.size() == 1) {
                    matchData = cDataList.get(0);
                } else {
                    for (CompareDataDO cData : cDataList) {
                        if (!cData.getNetTitle().equalsIgnoreCase(singleTitle)) continue;
                        matchData = cData;
                    }
                    if (matchData == null) {
                        matchData = cDataList.get(0);
                    }
                }
                BaseDataDO data = (BaseDataDO)matchData.getObjectValue("baseData");
                dataItem.setNetCode(data.getCode());
                dataItem.setNetTitle(data.getName());
                if (singleTitle.equalsIgnoreCase(data.getName())) {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                } else {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                }
                dataItem.setNetKey(data.getId().toString());
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            }
        }
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        CompareDataEnumItemDTO compareDataDTO = new CompareDataEnumItemDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.enumItemService.delete(compareDataDTO);
    }

    private CompareDataEnumItemDTO createItemFrom(CompareDataEnumItemDTO old) {
        CompareDataEnumItemDTO item = new CompareDataEnumItemDTO();
        item.setKey(old.getKey());
        item.setInfoKey(old.getInfoKey());
        item.setSingleCode(old.getSingleCode());
        item.setSingleTitle(old.getSingleTitle());
        item.setSingleParentCode(old.getSingleParentCode());
        item.setNetKey(old.getNetKey());
        item.setMatchKey(old.getMatchKey());
        item.setNetCode(old.getNetCode());
        item.setNetTitle(old.getNetTitle());
        item.setNetParentCode(old.getNetParentCode());
        item.setChangeType(old.getChangeType());
        item.setUpdateType(old.getUpdateType());
        item.setEnumCompareKey(old.getEnumCompareKey());
        return item;
    }

    private boolean compareItemSame(CompareDataEnumItemDTO item, CompareDataEnumItemDTO old) {
        boolean result = true;
        String code = item.toString();
        String code2 = old.toString();
        if (StringUtils.isNotEmpty((String)code) && !code.equalsIgnoreCase(code2)) {
            result = false;
        }
        return result;
    }
}

