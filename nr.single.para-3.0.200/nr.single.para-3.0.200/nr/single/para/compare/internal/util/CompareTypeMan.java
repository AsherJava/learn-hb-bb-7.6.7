/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.compare.definition.CompareDataDTO;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataDO;

public class CompareTypeMan {
    Map<String, CompareDataDO> netCodeItems;
    Map<String, CompareDataDO> netTitleItems;
    Map<String, List<CompareDataDO>> netTitleItemDic;
    Map<String, List<CompareDataDO>> netNewTitleItemDic;
    Map<String, List<CompareDataDO>> netCodeItemDic;
    Map<String, List<CompareDataDO>> netNewCodeItemDic;
    Map<String, CompareDataDO> netKeyItems;
    Map<String, List<CompareDataDO>> netAlisItemDic;
    Map<String, List<CompareDataDO>> netMatchItemDic;
    Map<String, List<CompareDataDO>> netFixCodeItemDic;
    Map<String, List<CompareDataDO>> netMappingItemDic;

    public CompareDataDO addNetItem(String netCode, String netTitle, String netKey) {
        CompareDataDTO data = new CompareDataDTO();
        data.setNetCode(netCode);
        data.setNetTitle(netTitle);
        data.setNetKey(netKey);
        this.addNetItem(data);
        return data;
    }

    public void addNetItem(CompareDataDO netItem) {
        this.getNetCodeItems().put(netItem.getNetCode(), netItem);
        this.getNetTitleItems().put(netItem.getNetTitle(), netItem);
        this.getNetKeyItems().put(netItem.getNetKey(), netItem);
        List<Object> titleList = null;
        if (this.getNetTitleItemDic().containsKey(netItem.getNetTitle())) {
            titleList = this.getNetTitleItemDic().get(netItem.getNetTitle());
        } else {
            titleList = new ArrayList();
            this.getNetTitleItemDic().put(netItem.getNetTitle(), titleList);
        }
        titleList.add(netItem);
        List<Object> codeList = null;
        if (this.getNetCodeItemDic().containsKey(netItem.getNetCode())) {
            codeList = this.getNetCodeItemDic().get(netItem.getNetCode());
        } else {
            codeList = new ArrayList();
            this.getNetCodeItemDic().put(netItem.getNetCode(), codeList);
        }
        codeList.add(netItem);
    }

    public void addAlisItem(String alisName, CompareDataDO netItem) {
        netItem.setObjectValue("alias", alisName);
        List<Object> codeList = null;
        if (this.getNetAlisItemDic().containsKey(alisName)) {
            codeList = this.getNetAlisItemDic().get(alisName);
        } else {
            codeList = new ArrayList();
            this.getNetAlisItemDic().put(alisName, codeList);
        }
        codeList.add(netItem);
    }

    public void addFixItem(String netCode, CompareDataDO netItem) {
        List<Object> codeList = null;
        if (this.getNetFixCodeItemDic().containsKey(netCode)) {
            codeList = this.getNetFixCodeItemDic().get(netCode);
        } else {
            codeList = new ArrayList();
            this.getNetFixCodeItemDic().put(netCode, codeList);
        }
        codeList.add(netItem);
    }

    public void addMappingItem(String mappingCode, CompareDataDO netItem) {
        List<Object> codeList = null;
        if (this.getNetMappingItemDic().containsKey(mappingCode)) {
            codeList = this.getNetMappingItemDic().get(mappingCode);
        } else {
            codeList = new ArrayList();
            this.getNetMappingItemDic().put(mappingCode, codeList);
        }
        codeList.add(netItem);
    }

    public void addMatchItem(String matchTitle, CompareDataDO netItem) {
        netItem.setObjectValue("matchTitle", matchTitle);
        List<Object> codeList = null;
        if (this.getNetMatchItemDic().containsKey(matchTitle)) {
            codeList = this.getNetMatchItemDic().get(matchTitle);
        } else {
            codeList = new ArrayList();
            this.getNetMatchItemDic().put(matchTitle, codeList);
        }
        codeList.add(netItem);
    }

    public void addNewItem(String newNetCode, CompareDataDO netItem) {
        List<Object> codeList = null;
        if (this.getNetNewCodeItemDic().containsKey(newNetCode)) {
            codeList = this.getNetNewCodeItemDic().get(newNetCode);
        } else {
            codeList = new ArrayList();
            this.getNetNewCodeItemDic().put(newNetCode, codeList);
        }
        codeList.add(netItem);
    }

    public void addNewTitleItem(String newNetTitle, CompareDataDO netItem) {
        List<Object> titmeList = null;
        if (this.getNetNewTitleItemDic().containsKey(newNetTitle)) {
            titmeList = this.getNetNewTitleItemDic().get(newNetTitle);
        } else {
            titmeList = new ArrayList();
            this.getNetNewTitleItemDic().put(newNetTitle, titmeList);
        }
        titmeList.add(netItem);
    }

    public Map<String, CompareDataDO> getNetCodeItems() {
        if (this.netCodeItems == null) {
            this.netCodeItems = new HashMap<String, CompareDataDO>();
        }
        return this.netCodeItems;
    }

    public void setNetCodeItems(Map<String, CompareDataDO> netCodeItems) {
        this.netCodeItems = netCodeItems;
    }

    public Map<String, CompareDataDO> getNetTitleItems() {
        if (this.netTitleItems == null) {
            this.netTitleItems = new HashMap<String, CompareDataDO>();
        }
        return this.netTitleItems;
    }

    public void setNetTitleItems(Map<String, CompareDataDO> netTitleItems) {
        this.netTitleItems = netTitleItems;
    }

    public CompareDataDO getCompareUpdateAndChangeType(String singleCode, String singleTitle, CompareContextType compareType, CompareDataDO dataItem) {
        CompareDataDO result = null;
        if (compareType == CompareContextType.CONTEXT_TITLE) {
            if (this.getNetTitleItemDic().containsKey(singleTitle)) {
                List<CompareDataDO> datas = this.getNetTitleItemDic().get(singleTitle);
                CompareDataDO data = null;
                if (datas.size() > 0) {
                    for (CompareDataDO data1 : datas) {
                        if (!singleCode.equalsIgnoreCase(data1.getNetCode())) continue;
                        data = data1;
                        break;
                    }
                    if (data == null) {
                        data = datas.get(0);
                    }
                }
                if (data != null && singleCode.equalsIgnoreCase(data.getNetCode())) {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                    dataItem.setNetKey(data.getNetCode());
                } else {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                }
                result = data;
            } else if (this.getNetCodeItems().containsKey(singleCode)) {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                result = this.getNetCodeItems().get(singleCode);
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            }
        } else if (compareType == CompareContextType.CONTEXT_CODE) {
            if (this.getNetCodeItems().containsKey(singleCode)) {
                CompareDataDO data = this.netCodeItems.get(singleCode);
                if (singleTitle.equalsIgnoreCase(data.getNetTitle())) {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                    dataItem.setNetKey(data.getNetKey());
                } else {
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                }
                result = data;
            } else if (this.getNetTitleItems().containsKey(singleTitle)) {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                result = this.getNetTitleItems().get(singleTitle);
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            }
        }
        return result;
    }

    public Map<String, CompareDataDO> getNetKeyItems() {
        if (this.netKeyItems == null) {
            this.netKeyItems = new HashMap<String, CompareDataDO>();
        }
        return this.netKeyItems;
    }

    public void setNetKeyItems(Map<String, CompareDataDO> netKeyItems) {
        this.netKeyItems = netKeyItems;
    }

    public Map<String, List<CompareDataDO>> getNetTitleItemDic() {
        if (this.netTitleItemDic == null) {
            this.netTitleItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netTitleItemDic;
    }

    public void setNetTitleItemDic(Map<String, List<CompareDataDO>> netTitleItemDic) {
        this.netTitleItemDic = netTitleItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetCodeItemDic() {
        if (this.netCodeItemDic == null) {
            this.netCodeItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netCodeItemDic;
    }

    public void setNetCodeItemDic(Map<String, List<CompareDataDO>> netCodeItemDic) {
        this.netCodeItemDic = netCodeItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetAlisItemDic() {
        if (this.netAlisItemDic == null) {
            this.netAlisItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netAlisItemDic;
    }

    public void setNetAlisItemDic(Map<String, List<CompareDataDO>> netAlisItemDic) {
        this.netAlisItemDic = netAlisItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetMatchItemDic() {
        if (this.netMatchItemDic == null) {
            this.netMatchItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netMatchItemDic;
    }

    public void setNetMatchItemDic(Map<String, List<CompareDataDO>> netMatchItemDic) {
        this.netMatchItemDic = netMatchItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetFixCodeItemDic() {
        if (this.netFixCodeItemDic == null) {
            this.netFixCodeItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netFixCodeItemDic;
    }

    public void setNetFixCodeItemDic(Map<String, List<CompareDataDO>> netFixCodeItemDic) {
        this.netFixCodeItemDic = netFixCodeItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetNewCodeItemDic() {
        if (this.netNewCodeItemDic == null) {
            this.netNewCodeItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netNewCodeItemDic;
    }

    public void setNetNewCodeItemDic(Map<String, List<CompareDataDO>> netNewCodeItemDic) {
        this.netNewCodeItemDic = netNewCodeItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetNewTitleItemDic() {
        if (this.netNewTitleItemDic == null) {
            this.netNewTitleItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netNewTitleItemDic;
    }

    public void setNetNewTitleItemDic(Map<String, List<CompareDataDO>> netNewTitleItemDic) {
        this.netNewTitleItemDic = netNewTitleItemDic;
    }

    public Map<String, List<CompareDataDO>> getNetMappingItemDic() {
        if (this.netMappingItemDic == null) {
            this.netMappingItemDic = new HashMap<String, List<CompareDataDO>>();
        }
        return this.netMappingItemDic;
    }

    public void setNetMappingItemDic(Map<String, List<CompareDataDO>> netMappingItemDic) {
        this.netMappingItemDic = netMappingItemDic;
    }
}

