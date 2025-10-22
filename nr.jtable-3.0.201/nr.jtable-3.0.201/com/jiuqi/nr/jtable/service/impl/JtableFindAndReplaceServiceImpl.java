/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.ISaveInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.SaveDataBuilderFactory
 *  com.jiuqi.nr.datacrud.SaveReturnRes
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.CrudSaveException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.DecimalLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.IntgeterLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.FindPageQueryInfo;
import com.jiuqi.nr.jtable.params.input.FindReplaceDataCommitSet;
import com.jiuqi.nr.jtable.params.input.FindReplaceDataRegionSet;
import com.jiuqi.nr.jtable.params.input.FloatRegionSaveStructure;
import com.jiuqi.nr.jtable.params.input.PasteFormatDataInfo;
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.FindAndReplaceDataSet;
import com.jiuqi.nr.jtable.params.output.FindPageResult;
import com.jiuqi.nr.jtable.params.output.PasteFormatReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableFindAndReplaceService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableFindAndReplaceServiceImpl
implements IJtableFindAndReplaceService {
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private SaveDataBuilderFactory saveDataBuilderFactory;
    @Autowired
    private IDataService dataService;
    @Autowired
    private IJtableResourceService iJtableResourceService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;

    @Override
    public Map<String, Object> findPage(FindPageQueryInfo findPageQueryInfo) {
        int maxFindNum = 1000;
        LinkedHashSet<Object> rowIdSet = new LinkedHashSet<Object>();
        LinkedHashSet<String> linkeInfoSet = new LinkedHashSet<String>();
        HashMap<Object, Integer> rowIdMap = new HashMap<Object, Integer>();
        HashMap<String, Integer> linkKeyMap = new HashMap<String, Integer>();
        String maxFindNumStr = this.iNvwaSystemOptionService.get("nr-data-entry-group", "MAX_FIND_NUM");
        if (StringUtils.isNotEmpty((String)maxFindNumStr)) {
            maxFindNum = Integer.parseInt(maxFindNumStr);
        }
        List<String> regionKeys = findPageQueryInfo.getPageRegionKey();
        Map<String, PagerInfo> regionKeyPageMap = findPageQueryInfo.getRegionKeyPageMap();
        Map<String, RegionFilterInfo> regionFilterInfoMap = findPageQueryInfo.getRegionKeyFiltMap();
        List<String> selectOptions = findPageQueryInfo.getCheckbox();
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("linkKeys", linkeInfoSet);
        result.put("rowIds", rowIdSet);
        int rowIdIndex = -1;
        int linkKeyIndex = -1;
        int dataNum = 0;
        boolean breakFlag = false;
        String searchText = findPageQueryInfo.getSearchText();
        for (String regionKey : regionKeys) {
            if (breakFlag) break;
            HashedMap<String, List<String>> linkLevelValMap = new HashedMap<String, List<String>>();
            RegionData region = this.jtableParamService.getRegion(regionKey);
            Map<String, Integer> linkLevelMap = region.getLinkLevelMap();
            FindPageResult regionResult = new FindPageResult();
            ArrayList<Map<String, Object>> cellList = new ArrayList<Map<String, Object>>();
            regionResult.setCellList(cellList);
            regionResult.setAreaKey(regionKey);
            RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
            RegionFilterInfo regionFilterInfo = regionFilterInfoMap.get(regionKey);
            regionQueryInfo.setFilterInfo(regionFilterInfo);
            regionQueryInfo.setContext(findPageQueryInfo.getContext());
            regionQueryInfo.setRegionKey(regionKey);
            regionQueryInfo.getRestructureInfo().setFormatBaseDataValue(true);
            PagerInfo pagerInfo = regionKeyPageMap.get(regionKey);
            if (pagerInfo == null) {
                FindAndReplaceDataSet findAndReplaceDataSet = this.queryRegionDatas(regionQueryInfo);
                List<List<Object>> dataFormat = findAndReplaceDataSet.getDataFormat();
                Map<String, List<String>> cells = findAndReplaceDataSet.getCells();
                List<String> linsInfos = cells.get(regionKey);
                block1: for (int rowNum = 0; rowNum < dataFormat.size() && !breakFlag; ++rowNum) {
                    List<Object> rowData = dataFormat.get(rowNum);
                    for (int colNum = 2; colNum < rowData.size(); ++colNum) {
                        HashMap<String, Integer> cellMap = new HashMap<String, Integer>();
                        String data = rowData.get(colNum).toString();
                        LinkData link = this.jtableParamService.getLink(linsInfos.get(colNum));
                        if (link.isDataMask() || link.getType() == LinkType.LINK_TYPE_BOOLEAN.getValue() || link.getType() == LinkType.LINK_TYPE_PICTURE.getValue() || link.getType() == LinkType.LINK_TYPE_FILE.getValue()) continue;
                        if (!selectOptions.contains("2")) {
                            searchText = searchText.toLowerCase();
                            data = data.toLowerCase();
                        }
                        if ((!selectOptions.contains("1") || !data.equals(searchText)) && (!data.contains(searchText) || selectOptions.contains("1"))) continue;
                        if (dataNum < maxFindNum) {
                            if (rowIdSet.add(rowData.get(0))) {
                                rowIdMap.put(rowData.get(0), ++rowIdIndex);
                                cellMap.put("rowId", rowIdIndex);
                            } else {
                                cellMap.put("rowId", (Integer)rowIdMap.get(rowData.get(0)));
                            }
                            if (linkeInfoSet.add(linsInfos.get(colNum))) {
                                linkKeyMap.put(linsInfos.get(colNum), ++linkKeyIndex);
                                cellMap.put("linkKey", linkKeyIndex);
                            } else {
                                cellMap.put("linkKey", (Integer)linkKeyMap.get(linsInfos.get(colNum)));
                            }
                            cellList.add(cellMap);
                            ++dataNum;
                            continue;
                        }
                        regionResult.setExceedFlag(true);
                        breakFlag = true;
                        continue block1;
                    }
                }
                if (cellList.size() <= 0) continue;
                result.put(regionKey, regionResult);
                continue;
            }
            int limit = pagerInfo.getLimit();
            int pageIndex = 1;
            int currentNum = 0;
            pagerInfo.setLimit(5000);
            int pageSize = pagerInfo.getTotal() / pagerInfo.getLimit();
            if (pagerInfo.getTotal() % pagerInfo.getLimit() != 0) {
                ++pageSize;
            }
            block3: for (int i = 0; i < pageSize && !breakFlag; ++i) {
                pagerInfo.setOffset(i);
                regionQueryInfo.setPagerInfo(pagerInfo);
                FindAndReplaceDataSet findAndReplaceDataSet = this.queryRegionDatas(regionQueryInfo);
                List<List<Object>> dataFormat = findAndReplaceDataSet.getDataFormat();
                Map<String, List<String>> cells = findAndReplaceDataSet.getCells();
                List<String> linsInfos = cells.get(regionKey);
                block4: for (int rowNum = 0; rowNum < dataFormat.size(); ++rowNum) {
                    if (++currentNum > limit) {
                        ++pageIndex;
                        currentNum = 1;
                    }
                    if (breakFlag) continue block3;
                    List<Object> rowData = dataFormat.get(rowNum);
                    for (int colNum = 2; colNum < rowData.size(); ++colNum) {
                        HashMap<String, Integer> cellMap = new HashMap<String, Integer>();
                        String data = rowData.get(colNum).toString();
                        LinkData link = this.jtableParamService.getLink(linsInfos.get(colNum));
                        if (link.isDataMask() || link.getType() == LinkType.LINK_TYPE_BOOLEAN.getValue() || link.getType() == LinkType.LINK_TYPE_PICTURE.getValue() || link.getType() == LinkType.LINK_TYPE_FILE.getValue()) continue;
                        if (!selectOptions.contains("2")) {
                            searchText = searchText.toLowerCase();
                            data = data.toLowerCase();
                        }
                        if ((!selectOptions.contains("1") || !data.equals(searchText)) && (!data.contains(searchText) || selectOptions.contains("1"))) continue;
                        if (dataNum < maxFindNum) {
                            if (linkLevelMap != null && linkLevelMap.containsKey(link.getKey())) {
                                List<String> preValues;
                                List<String> preLevelLink = this.getPreLevelLink(link.getKey(), regionKey);
                                String preLevelLinkValue = this.getPreLevelLinkValue(cells.get(regionKey), rowData, preLevelLink);
                                if (linkLevelValMap.containsKey(data.toString())) {
                                    preValues = (List)linkLevelValMap.get(data.toString());
                                    boolean contain = preValues.contains(preLevelLinkValue);
                                    if (contain) continue;
                                    preValues.add(preLevelLinkValue);
                                } else {
                                    preValues = new ArrayList<String>();
                                    preValues.add(preLevelLinkValue);
                                    linkLevelValMap.put(data.toString(), preValues);
                                }
                            }
                            if (rowIdSet.add(rowData.get(0))) {
                                rowIdMap.put(rowData.get(0), ++rowIdIndex);
                                cellMap.put("rowId", rowIdIndex);
                            } else {
                                cellMap.put("rowId", (Integer)rowIdMap.get(rowData.get(0)));
                            }
                            if (linkeInfoSet.add(linsInfos.get(colNum))) {
                                linkKeyMap.put(linsInfos.get(colNum), ++linkKeyIndex);
                                cellMap.put("linkKey", linkKeyIndex);
                            } else {
                                cellMap.put("linkKey", (Integer)linkKeyMap.get(linsInfos.get(colNum)));
                            }
                            cellMap.put("pageIndex", pageIndex);
                            cellList.add(cellMap);
                            ++dataNum;
                            continue;
                        }
                        regionResult.setExceedFlag(true);
                        breakFlag = true;
                        continue block4;
                    }
                }
            }
            if (cellList.size() <= 0) continue;
            result.put(regionKey, regionResult);
        }
        return result;
    }

    private List<String> getPreLevelLink(String link, String regionKey) {
        ArrayList<String> levelLinks = new ArrayList<String>();
        RegionData region = this.jtableParamService.getRegion(regionKey);
        Map<String, Integer> linkLevelMap = region.getLinkLevelMap();
        if (linkLevelMap != null && linkLevelMap.size() > 0 && linkLevelMap.containsKey(link)) {
            Integer currLevel = linkLevelMap.get(link);
            if (currLevel == 1) {
                return null;
            }
            for (String key : linkLevelMap.keySet()) {
                Integer level = linkLevelMap.get(key);
                if (level >= currLevel) continue;
                levelLinks.add(key);
            }
        }
        return levelLinks;
    }

    private String getPreLevelLinkValue(List<String> cells, List<Object> rowDatas, List<String> preLinks) {
        StringBuilder preValue = new StringBuilder();
        if (preLinks == null || preLinks.size() == 0) {
            return null;
        }
        for (String linkKey : preLinks) {
            int index = cells.indexOf(linkKey);
            if (index == -1) continue;
            String value = rowDatas.get(index).toString();
            preValue.append(value);
        }
        return preValue.toString();
    }

    @Override
    public Map<String, Object> replaceAll(FindPageQueryInfo findPageQueryInfo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        FindReplaceDataCommitSet reportDataCommitSet = new FindReplaceDataCommitSet();
        reportDataCommitSet.setContext(findPageQueryInfo.getContext());
        List<String> regionKeys = findPageQueryInfo.getPageRegionKey();
        Map<String, RegionFilterInfo> regionFilterInfoMap = findPageQueryInfo.getRegionKeyFiltMap();
        for (String regionKey : regionKeys) {
            RegionData regionData = this.jtableParamService.getRegion(regionKey);
            if (regionData.isReadOnly()) continue;
            Map<String, PagerInfo> regionKeyPageMap = findPageQueryInfo.getRegionKeyPageMap();
            FindReplaceDataRegionSet findReplaceDataRegionSet = new FindReplaceDataRegionSet();
            findReplaceDataRegionSet.setRegionKey(regionKey);
            reportDataCommitSet.getCommitData().put(regionKey, findReplaceDataRegionSet);
            FindPageResult regionResult = new FindPageResult();
            ArrayList<Map<String, Object>> cellList = new ArrayList<Map<String, Object>>();
            regionResult.setCellList(cellList);
            regionResult.setAreaKey(regionKey);
            RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
            RegionFilterInfo regionFilterInfo = regionFilterInfoMap.get(regionKey);
            regionQueryInfo.setFilterInfo(regionFilterInfo);
            regionQueryInfo.setContext(findPageQueryInfo.getContext());
            regionQueryInfo.setRegionKey(regionKey);
            regionQueryInfo.getRestructureInfo().setFormatBaseDataValue(true);
            PagerInfo pagerInfo = regionKeyPageMap.get(regionKey);
            regionQueryInfo.setPagerInfo(pagerInfo);
            if (pagerInfo == null) {
                FindAndReplaceDataSet findAndReplaceDataSet = this.queryRegionDatasNoPage(regionQueryInfo);
                findReplaceDataRegionSet.setCells(findAndReplaceDataSet.getCells());
                this.resultDataSetReplace(findAndReplaceDataSet, findPageQueryInfo, regionKey, findReplaceDataRegionSet);
                continue;
            }
            pagerInfo.setLimit(5000);
            int pageSize = pagerInfo.getTotal() / pagerInfo.getLimit();
            if (pagerInfo.getTotal() % pagerInfo.getLimit() != 0) {
                ++pageSize;
            }
            for (int i = 0; i < pageSize; ++i) {
                pagerInfo.setOffset(i);
                FindAndReplaceDataSet findAndReplaceDataSet = this.queryRegionDatas(regionQueryInfo);
                findReplaceDataRegionSet.setCells(findAndReplaceDataSet.getCells());
                this.resultDataSetReplace(findAndReplaceDataSet, findPageQueryInfo, regionKey, findReplaceDataRegionSet);
            }
        }
        int replaceNum = 0;
        try {
            replaceNum = this.saveReportFormDatas(reportDataCommitSet);
        }
        catch (SaveDataException e) {
            e.printStackTrace();
        }
        map.put("replaceNum", replaceNum);
        return map;
    }

    private String dataUnFormat(LinkData linkData, String resultText) {
        switch (LinkType.forValue(linkData.getType())) {
            case LINK_TYPE_DATE: 
            case LINK_TYPE_DATETIME: {
                resultText = this.dateUnFormat(linkData, resultText);
                break;
            }
            case LINK_TYPE_FLOAT: 
            case LINK_TYPE_DECIMAL: {
                resultText = this.decimalUnFormat(linkData, resultText);
                break;
            }
            case LINK_TYPE_INTEGER: {
                resultText = this.integerUnFormat(linkData, resultText);
                break;
            }
        }
        return resultText;
    }

    private String integerUnFormat(LinkData linkData, String resultText) {
        IntgeterLinkData intgeterLinkData = (IntgeterLinkData)linkData;
        String tmpText = resultText;
        if (intgeterLinkData.isShowCurrencyMark()) {
            tmpText = tmpText.replace(intgeterLinkData.getCurrencyMark(), "");
        }
        if (intgeterLinkData.isShowThoundMark()) {
            tmpText = tmpText.replace(intgeterLinkData.getThoundsMark(), "");
        }
        if (intgeterLinkData.isBracketNegative()) {
            tmpText = tmpText.replace("(", "-");
            tmpText = tmpText.replace(")", "");
        }
        try {
            Integer num = null;
            if (intgeterLinkData.isPercent() && tmpText.contains("%")) {
                tmpText = tmpText.replace("%", "");
                num = Integer.valueOf(tmpText);
                num = num / 100;
            }
            if (intgeterLinkData.isThousandPer() && tmpText.contains("\u2030")) {
                tmpText = tmpText.replace("\u2030", "");
                num = Integer.valueOf(tmpText);
                num = num / 1000;
            }
            if (num != null) {
                tmpText = String.valueOf(num);
            }
        }
        catch (Exception e) {
            return resultText;
        }
        return tmpText;
    }

    private String decimalUnFormat(LinkData linkData, String resultText) {
        DecimalLinkData decimalLinkData = (DecimalLinkData)linkData;
        String tmpText = resultText;
        if (decimalLinkData.isShowCurrencyMark()) {
            tmpText = tmpText.replace(decimalLinkData.getCurrencyMark(), "");
        }
        if (decimalLinkData.isShowThoundMark()) {
            tmpText = tmpText.replace(decimalLinkData.getThoundsMark(), "");
        }
        if (tmpText.indexOf(46) == 0) {
            return "\u6821\u9a8c\u5931\u8d25";
        }
        if (decimalLinkData.isBracketNegative()) {
            if (tmpText.indexOf(46) == 1) {
                return "\u6821\u9a8c\u5931\u8d25";
            }
            tmpText = tmpText.replace("(", "-");
            tmpText = tmpText.replace(")", "");
        }
        try {
            Double num = null;
            if (decimalLinkData.isPercent() && tmpText.contains("%")) {
                tmpText = tmpText.replace("%", "");
                num = Double.valueOf(tmpText);
                num = num / 100.0;
            }
            if (decimalLinkData.isThousandPer() && tmpText.contains("\u2030")) {
                tmpText = tmpText.replace("\u2030", "");
                num = Double.valueOf(tmpText);
                num = num / 1000.0;
            }
            if (num == null) {
                num = Double.valueOf(tmpText);
            }
            tmpText = new BigDecimal(String.valueOf(num)).setScale(decimalLinkData.getFraction(), 4).toString();
        }
        catch (Exception e) {
            return resultText;
        }
        return tmpText;
    }

    private boolean preformatDateTime(List<String> list, String defaultValue, int formatCount) {
        list.stream().filter(item -> item != "").collect(Collectors.toList());
        if (list.size() == 0 || list.size() > formatCount) {
            return false;
        }
        int length = list.size();
        if (formatCount - length > 0) {
            for (int i = 0; i < formatCount - length; ++i) {
                list.add(defaultValue);
            }
        }
        return true;
    }

    private String parseDate(LinkData linkData, String resultText) {
        String result = "";
        String[] dateNum = resultText.split("[^0-9]");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(dateNum));
        if (!this.preformatDateTime(list, "1", 3)) {
            return result;
        }
        int[] dateArr = new int[3];
        for (int i = 0; i < list.size(); ++i) {
            dateArr[i] = Integer.parseInt((String)list.get(i));
        }
        if (dateArr[0] < 100 && dateArr[0] >= 0) {
            dateArr[0] = dateArr[0] > 70 ? dateArr[0] + 1900 : dateArr[0] + 2000;
        }
        if (dateArr[0] > 1500 && dateArr[0] <= 9999) {
            int[] days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (dateArr[0] % 4 == 0 && dateArr[0] % 100 != 0 || dateArr[0] % 100 == 0 && dateArr[0] % 400 == 0) {
                days[1] = 29;
            }
            if (dateArr[1] >= 1 && dateArr[1] <= 12 && dateArr[2] <= days[dateArr[1] - 1]) {
                result = "YYYYMM".equals(linkData.getStyle()) ? "" + dateArr[0] + "-" + dateArr[1] + "-" + '1' : "" + dateArr[0] + "-" + dateArr[1] + "-" + dateArr[2];
            }
        }
        return result;
    }

    private String parseDateTime(LinkData linkData, String resultText) {
        String result = "";
        String[] datearr = resultText.split("[^0-9]");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(datearr));
        if (!this.preformatDateTime(list, "00", 3)) {
            return result;
        }
        int[] intdatearr = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            intdatearr[i] = Integer.parseInt((String)list.get(i));
        }
        if (intdatearr[0] >= 0 && intdatearr[0] <= 23 && intdatearr[1] >= 0 && intdatearr[1] <= 59 && intdatearr[2] >= 0 && intdatearr[2] <= 59) {
            result = (result == "" ? "" : result + " ") + (intdatearr[0] / 10 < 1 ? "0" + intdatearr[0] : Integer.valueOf(intdatearr[0])) + ":" + (intdatearr[1] / 10 < 1 ? "0" + intdatearr[1] : Integer.valueOf(intdatearr[1])) + ":" + (intdatearr[2] / 10 < 1 ? "0" + intdatearr[2] : Integer.valueOf(intdatearr[2]));
        }
        return result;
    }

    private String dateUnFormat(LinkData linkData, String resultText) {
        String result = "";
        if (LinkType.LINK_TYPE_DATE.getValue() == linkData.getType()) {
            result = this.parseDate(linkData, resultText);
        } else {
            String[] dateTimearr = resultText.split(" ");
            if (dateTimearr.length == 2) {
                String datestr = "";
                String timestr = "";
                datestr = this.parseDate(linkData, dateTimearr[0]);
                if (datestr.length() > 0) {
                    datestr = datestr + ' ';
                    timestr = this.parseDateTime(linkData, dateTimearr[1]);
                    if (timestr.length() > 0) {
                        result = datestr + timestr;
                    }
                }
            }
        }
        if (result.equals("")) {
            return resultText;
        }
        return result;
    }

    private void resultDataSetReplace(FindAndReplaceDataSet findAndReplaceDataSet, FindPageQueryInfo findPageQueryInfo, String regionKey, FindReplaceDataRegionSet findReplaceDataRegionSet) {
        List<String> unableLinks = findPageQueryInfo.getRegionKeyUnEditableMap().get(regionKey);
        List<String> enumLinks = findPageQueryInfo.getRegionKeyEnumLinksMap().get(regionKey);
        List<String> bizKeys = findPageQueryInfo.getBizKeys();
        PasteFormatDataInfo pasteFormatDataInfo = new PasteFormatDataInfo();
        pasteFormatDataInfo.setContext(findPageQueryInfo.getContext());
        List<String> selectOptions = findPageQueryInfo.getCheckbox();
        String judgeText = findPageQueryInfo.getSearchText();
        String searchText = this.escapeRegex(findPageQueryInfo.getSearchText());
        String replaceText = findPageQueryInfo.getReplaceText();
        List<List<Object>> datas = findAndReplaceDataSet.getDataFormat();
        List<List<Object>> originData = findAndReplaceDataSet.getData();
        Map<String, List<String>> cells = findAndReplaceDataSet.getCells();
        List<String> linsInfos = cells.get(regionKey);
        ArrayList<String> modifyLinks = new ArrayList<String>();
        RegionData region = this.jtableParamService.getRegion(regionKey);
        if (region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            findReplaceDataRegionSet.getCells().put(regionKey, modifyLinks);
        }
        List<IRowData> iRowDatas = findAndReplaceDataSet.getiRowDatas();
        for (int rowNum = 0; rowNum < datas.size(); ++rowNum) {
            if (iRowDatas.get(rowNum).getGroupTreeDeep() >= 0) continue;
            int startcolNum = 0;
            boolean ismodifyFlag = false;
            ArrayList rowModifyData = new ArrayList();
            List<Object> rowData = datas.get(rowNum);
            if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                startcolNum = 2;
                ArrayList<Object> col1Data = new ArrayList<Object>();
                col1Data.add(rowData.get(0));
                col1Data.add(rowData.get(0));
                ArrayList<Object> col2Data = new ArrayList<Object>();
                col2Data.add(rowData.get(1));
                col2Data.add(rowData.get(1));
                rowModifyData.add(col1Data);
                rowModifyData.add(col2Data);
            }
            for (int colNum = startcolNum; colNum < rowData.size(); ++colNum) {
                ArrayList<Object> colData = new ArrayList<Object>();
                rowModifyData.add(colData);
                String data = rowData.get(colNum).toString();
                LinkData link = this.jtableParamService.getLink(linsInfos.get(colNum));
                String datajudgeText = data.toString();
                if (link.isDataMask() || link.getType() == LinkType.LINK_TYPE_BOOLEAN.getValue() || link.getType() == LinkType.LINK_TYPE_PICTURE.getValue() || link.getType() == LinkType.LINK_TYPE_FILE.getValue() || link.getType() == LinkType.LINK_TYPE_FORMULA.getValue() || bizKeys.contains(link.getZbid())) {
                    if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        colData.add(originData.get(rowNum).get(colNum));
                        colData.add(originData.get(rowNum).get(colNum));
                        continue;
                    }
                    rowModifyData.remove(colData);
                    continue;
                }
                if (unableLinks.size() > 0 && unableLinks.contains(linsInfos.get(colNum)) || enumLinks.size() > 0 && enumLinks.contains(linsInfos.get(colNum))) {
                    if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        colData.add(originData.get(rowNum).get(colNum));
                        colData.add(originData.get(rowNum).get(colNum));
                        continue;
                    }
                    rowModifyData.remove(colData);
                    continue;
                }
                if (!selectOptions.contains("2")) {
                    judgeText = judgeText.toLowerCase();
                    datajudgeText = datajudgeText.toLowerCase();
                }
                if (selectOptions.contains("1") && datajudgeText.equals(judgeText) || datajudgeText.contains(judgeText) && !selectOptions.contains("1")) {
                    String resultText = "";
                    resultText = selectOptions.contains("2") ? data.replaceAll(searchText, replaceText) : data.replaceAll("(?i)" + searchText, replaceText);
                    if ((link.getType() == LinkType.LINK_TYPE_INTEGER.getValue() || link.getType() == LinkType.LINK_TYPE_FLOAT.getValue() || link.getType() == LinkType.LINK_TYPE_DECIMAL.getValue()) && (resultText.contains("$") || resultText.contains("\u00a5") || resultText.contains("\u20ac"))) {
                        if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                            colData.add(originData.get(rowNum).get(colNum));
                            colData.add(originData.get(rowNum).get(colNum));
                            continue;
                        }
                        rowModifyData.remove(colData);
                        continue;
                    }
                    if (region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        modifyLinks.add(linsInfos.get(colNum));
                    }
                    if (link.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue()) {
                        if (resultText.equals("")) {
                            colData.add(originData.get(rowNum).get(colNum));
                            colData.add("");
                        } else {
                            HashMap<String, List<String>> dataLinkMaps = new HashMap<String, List<String>>();
                            ArrayList<String> datalinks = new ArrayList<String>();
                            datalinks.add(data);
                            datalinks.add(resultText);
                            dataLinkMaps.put(linsInfos.get(colNum), datalinks);
                            pasteFormatDataInfo.setDataLinkMaps(dataLinkMaps);
                            PasteFormatReturnInfo pasteFormatReturnInfo = this.iJtableResourceService.pasteFormatData(pasteFormatDataInfo);
                            List<String> entitys = pasteFormatReturnInfo.getDataLinkMaps().get(linsInfos.get(colNum));
                            if (entitys.size() == 1) {
                                colData.add(originData.get(rowNum).get(colNum));
                                colData.add(originData.get(rowNum).get(colNum));
                            } else if (entitys.size() == 2) {
                                colData.add(originData.get(rowNum).get(colNum));
                                if (entitys.get(1).equals("")) {
                                    colData.add(originData.get(rowNum).get(colNum));
                                } else {
                                    colData.add(entitys.get(1));
                                }
                            }
                        }
                    } else {
                        colData.add(originData.get(rowNum).get(colNum));
                        resultText = this.dataUnFormat(link, resultText);
                        colData.add(resultText);
                    }
                    ismodifyFlag = true;
                    continue;
                }
                if (link.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue()) {
                    HashMap<String, List<String>> dataLinkMaps = new HashMap<String, List<String>>();
                    ArrayList<String> datalinks = new ArrayList<String>();
                    datalinks.add(data);
                    dataLinkMaps.put(linsInfos.get(colNum), datalinks);
                    pasteFormatDataInfo.setDataLinkMaps(dataLinkMaps);
                    if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        colData.add(originData.get(rowNum).get(colNum));
                        colData.add(originData.get(rowNum).get(colNum));
                    }
                } else if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                    colData.add(originData.get(rowNum).get(colNum));
                    colData.add(originData.get(rowNum).get(colNum));
                }
                if (colData.size() != 0) continue;
                rowModifyData.remove(colData);
            }
            if (ismodifyFlag) {
                if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                    findReplaceDataRegionSet.getModifydata().add(rowModifyData);
                } else {
                    findReplaceDataRegionSet.getData().add(rowModifyData);
                    if (findReplaceDataRegionSet.getData().size() == 0) {
                        HashMap<String, List<String>> nmap = new HashMap<String, List<String>>();
                        findReplaceDataRegionSet.setCells(nmap);
                    }
                }
            }
            if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() || findReplaceDataRegionSet.getData().size() != 0) continue;
            HashMap<String, List<String>> nmap = new HashMap<String, List<String>>();
            findReplaceDataRegionSet.setCells(nmap);
        }
    }

    private String escapeRegex(String s) {
        s = s.replace("\\", "\\\\");
        s = s.replace("(", "\\(").replace(")", "\\)");
        s = s.replace("?", "\\?");
        s = s.replace("*", "\\*");
        s = s.replace("+", "\\+");
        s = s.replace("[", "\\[").replace("]", "\\]");
        s = s.replace("|", "\\|");
        s = s.replace("{", "\\{").replace("}", "\\}");
        s = s.replace(".", "\\.");
        s = s.replace("$", "\\$");
        s = s.replace("^", "\\^");
        s = s.replace("%", "\\%");
        return s;
    }

    public FindAndReplaceDataSet queryRegionDatas(RegionQueryInfo regionQueryInfo) {
        FindAndReplaceDataSet findAndReplaceDataSet = new FindAndReplaceDataSet();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionQueryInfo.getRegionKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            ArrayList cells = new ArrayList();
            findAndReplaceDataSet.getCells().put(regionQueryInfo.getRegionKey(), cells);
            return findAndReplaceDataSet;
        }
        regionQueryInfo.setDesensitized(true);
        DataCrudUtil.initRegionQueryInfo(regionRelation, regionQueryInfo);
        DataRegionDefine regionDefine = regionRelation.getRegionDefine();
        DataRegionKind regionKind = regionDefine.getRegionKind();
        findAndReplaceDataSet = regionKind != DataRegionKind.DATA_REGION_SIMPLE ? this.queryFloatRegionDatas(regionQueryInfo, regionRelation) : this.queryFixRegionDatas(regionQueryInfo, regionRelation);
        return findAndReplaceDataSet;
    }

    public FindAndReplaceDataSet queryRegionDatasNoPage(RegionQueryInfo regionQueryInfo) {
        FindAndReplaceDataSet findAndReplaceDataSet = new FindAndReplaceDataSet();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionQueryInfo.getRegionKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            ArrayList cells = new ArrayList();
            findAndReplaceDataSet.getCells().put(regionQueryInfo.getRegionKey(), cells);
            return findAndReplaceDataSet;
        }
        regionQueryInfo.setDesensitized(true);
        DataCrudUtil.initRegionQueryInfo(regionRelation, regionQueryInfo);
        regionQueryInfo.setPagerInfo(null);
        DataRegionDefine regionDefine = regionRelation.getRegionDefine();
        DataRegionKind regionKind = regionDefine.getRegionKind();
        findAndReplaceDataSet = regionKind != DataRegionKind.DATA_REGION_SIMPLE ? this.queryFloatRegionDatas(regionQueryInfo, regionRelation) : this.queryFixRegionDatas(regionQueryInfo, regionRelation);
        return findAndReplaceDataSet;
    }

    private FindAndReplaceDataSet queryFixRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        FindAndReplaceDataSet findAndReplaceDataSet = new FindAndReplaceDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        queryInfoBuilder.setDesensitized(regionQueryInfo.isDesensitized());
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        HashedMap<String, List<String>> cells = new HashedMap<String, List<String>>();
        List metaDatas = iRegionDataSet.getMetaData();
        List<Object> linkKeys = new ArrayList();
        if (metaDatas != null && metaDatas.size() > 0) {
            linkKeys = metaDatas.stream().map(IMetaData::getLinkKey).collect(Collectors.toList());
        }
        cells.put(regionKey, linkKeys);
        findAndReplaceDataSet.setCells(cells);
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        findAndReplaceDataSet.setiRowDatas(rowDatas);
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        for (IRowData rowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            DataCrudUtil.setDataAndFormatData(rowData, dataList, dataFormatList, formatter);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        findAndReplaceDataSet.setData(datas);
        findAndReplaceDataSet.setDataFormat(dataFormats);
        return findAndReplaceDataSet;
    }

    private FindAndReplaceDataSet queryFloatRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        FindAndReplaceDataSet findAndReplaceDataSet = new FindAndReplaceDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        queryInfoBuilder.setDesensitized(regionQueryInfo.isDesensitized());
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        HashedMap<String, List<String>> cells = new HashedMap<String, List<String>>();
        List metaDatas = iRegionDataSet.getMetaData();
        ArrayList<String> linkKeys = new ArrayList<String>();
        linkKeys.add(0, "ID");
        linkKeys.add(1, "FLOATORDER");
        int floatOrderIndex = 0;
        if (metaDatas != null && metaDatas.size() > 0) {
            List metaLinks = metaDatas.stream().map(IMetaData::getLinkKey).collect(Collectors.toList());
            floatOrderIndex = metaLinks.indexOf("FLOATORDER");
            metaLinks.remove(floatOrderIndex);
            linkKeys.addAll(metaLinks);
        }
        cells.put(regionKey, linkKeys);
        findAndReplaceDataSet.setCells(cells);
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, jtableContext);
        List<Object> bizKeyOrderFields = new ArrayList();
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        findAndReplaceDataSet.setiRowDatas(rowDatas);
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        List filledEnumLinks = regionRelation.getFilledEnumLinks();
        LinkedHashMap<String, FieldData> dimName2Field = new LinkedHashMap<String, FieldData>();
        for (FieldData fieldData : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
            dimName2Field.put(dimensionName, fieldData);
        }
        for (IRowData iRowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            String rowID = DataCrudUtil.buildFloatRowID(iRowData, dimName2Field, filledEnumLinks);
            DataCrudUtil.setDataAndFormatData(iRowData, dataList, dataFormatList, formatter);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        findAndReplaceDataSet.setData(datas);
        findAndReplaceDataSet.setDataFormat(dataFormats);
        return findAndReplaceDataSet;
    }

    public int saveReportFormDatas(FindReplaceDataCommitSet reportDataCommitSet) {
        JtableContext jtableContext = reportDataCommitSet.getContext();
        Map<String, FindReplaceDataRegionSet> commitData = reportDataCommitSet.getCommitData();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        int replaceNum = 0;
        for (RegionData region : regions) {
            if (!commitData.containsKey(region.getKey())) continue;
            FindReplaceDataRegionSet findReplaceDataRegionSet = commitData.get(region.getKey());
            findReplaceDataRegionSet.setContext(jtableContext);
            findReplaceDataRegionSet.setRegionKey(region.getKey());
            if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                replaceNum += this.saveFloatRegionDatas(findReplaceDataRegionSet, region);
                continue;
            }
            replaceNum += this.saveFixRegionDatas(findReplaceDataRegionSet, region);
        }
        return replaceNum;
    }

    private int saveFixRegionDatas(FindReplaceDataRegionSet findReplaceDataRegionSet, RegionData region) {
        int i;
        int replaceNum = 0;
        String regionKey = region.getKey();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            return 0;
        }
        Map<String, List<String>> cells = findReplaceDataRegionSet.getCells();
        List<String> linkKeys = cells.get(regionKey);
        if (linkKeys == null || linkKeys.isEmpty()) {
            return 0;
        }
        List<List<Object>> datas = findReplaceDataRegionSet.getData().get(0);
        if (findReplaceDataRegionSet.getData() == null || findReplaceDataRegionSet.getData().size() == 0) {
            return 0;
        }
        JtableContext context = findReplaceDataRegionSet.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createSaveDataBuilder(regionKey, dimensionCombinationBuilder.getCombination());
        String formulaSchemeKey = context.getFormulaSchemeKey();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
            saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        ArrayList<Integer> dataMaskLinkIndexs = new ArrayList<Integer>();
        for (i = 0; i < linkKeys.size(); ++i) {
            String linkKey = linkKeys.get(i);
            boolean isDataMask = false;
            MetaData metaData = regionRelation.getMetaDataByLink(linkKey);
            if (metaData != null) {
                isDataMask = metaData.isSensitive();
            }
            if (isDataMask) {
                dataMaskLinkIndexs.add(i);
                linkIndexs.add(-1);
                continue;
            }
            int index = saveDataBuilder.addLink(linkKey);
            linkIndexs.add(index);
        }
        saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 0);
        for (i = 0; i < datas.size(); ++i) {
            ReturnRes setReturnRes;
            if (dataMaskLinkIndexs.contains(i)) continue;
            Object data = datas.get(i).get(1);
            Object oldData = datas.get(i).get(0);
            String datastr = data.toString();
            if (data != null && data.toString().equals("")) {
                data = null;
            }
            if ((setReturnRes = saveDataBuilder.setData(((Integer)linkIndexs.get(i)).intValue(), data)).getCode() == 0) {
                if (oldData.toString().equals(datastr)) continue;
                ++replaceNum;
                continue;
            }
            saveDataBuilder.setData(((Integer)linkIndexs.get(i)).intValue(), oldData);
        }
        ISaveInfo saveInfo = saveDataBuilder.build();
        try {
            SaveReturnRes saveReturnRes = this.dataService.saveRegionData(saveInfo);
            if (saveReturnRes.getCode() != 0) {
                return 0;
            }
        }
        catch (CrudSaveException e) {
            return 0;
        }
        catch (CrudOperateException e) {
            return 0;
        }
        return replaceNum;
    }

    private int saveFloatRegionDatas(FindReplaceDataRegionSet findReplaceDataRegionSet, RegionData region) {
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(region.getKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            return 0;
        }
        Map<String, List<String>> cells = findReplaceDataRegionSet.getCells();
        List<String> linkKeys = cells.get(region.getKey());
        if (linkKeys == null || linkKeys.isEmpty()) {
            return 0;
        }
        List<List<List<Object>>> modifydata = findReplaceDataRegionSet.getModifydata();
        if (modifydata == null || modifydata.size() == 0) {
            return 0;
        }
        JtableContext context = findReplaceDataRegionSet.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
        RegionSettingUtil.rebuildMasterKeyByRegion(region, context, dimensionValueSet, regionRelation);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createSaveDataBuilder(region.getKey(), dimensionCombinationBuilder.getCombination());
        String formulaSchemeKey = context.getFormulaSchemeKey();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
            saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        HashMap<String, String> bizKeyToRowIdMap = new HashMap<String, String>();
        ArrayList<FieldData> bizKeyOrderFields = new ArrayList();
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(region.getKey(), context);
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        FloatRegionSaveStructure floatRegionSaveParam = new FloatRegionSaveStructure();
        floatRegionSaveParam.setRegion(region);
        floatRegionSaveParam.setBizKeyOrderFields(bizKeyOrderFields);
        floatRegionSaveParam.setBizKeyToRowIdMap(bizKeyToRowIdMap);
        floatRegionSaveParam.setDimensionValueSet(dimensionValueSet);
        floatRegionSaveParam.setRegionMetaDatas(regionMetaDatas);
        floatRegionSaveParam.setSaveDataBuilder(saveDataBuilder);
        floatRegionSaveParam.setRegionRelation(regionRelation);
        int replaceNum = this.modifyFloatRowData(findReplaceDataRegionSet, floatRegionSaveParam);
        this.deleteFloatRowData(findReplaceDataRegionSet, floatRegionSaveParam);
        ISaveInfo saveInfo = saveDataBuilder.build();
        try {
            this.dataService.saveRegionData(saveInfo);
        }
        catch (CrudOperateException e) {
            return 0;
        }
        return replaceNum;
    }

    private void deleteFloatRowData(FindReplaceDataRegionSet regionDataCommitSet, FloatRegionSaveStructure floatRegionSaveParam) {
        List<String> rowIDs = regionDataCommitSet.getDeletedata();
        if (rowIDs == null || rowIDs.size() == 0) {
            return;
        }
        SaveDataBuilder saveDataBuilder = floatRegionSaveParam.getSaveDataBuilder();
        DimensionValueSet dimensionValueSet = floatRegionSaveParam.getDimensionValueSet();
        List<FieldData> bizKeyOrderFields = floatRegionSaveParam.getBizKeyOrderFields();
        for (String rowID : rowIDs) {
            if (rowID.contains("FILL_ENTITY_EMPTY")) continue;
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DataCrudUtil.setBizKeyValueForDimension(dimensionCombinationBuilder, dimensionValueSet, rowID, bizKeyOrderFields);
            saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 3);
        }
    }

    private int inserFloatRowData(FindReplaceDataRegionSet regionDataCommitSet, FloatRegionSaveStructure floatRegionSaveParam) {
        int replaceNum = 0;
        List<List<List<Object>>> newdatas = regionDataCommitSet.getNewdata();
        if (newdatas == null || newdatas.size() == 0) {
            return 0;
        }
        RegionData region = floatRegionSaveParam.getRegion();
        SaveDataBuilder saveDataBuilder = floatRegionSaveParam.getSaveDataBuilder();
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> linkKeys = cells.get(region.getKey());
        int rowIDIndex = linkKeys.indexOf("ID");
        int sumIndex = linkKeys.indexOf("SUM");
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (int i = 0; i < linkKeys.size(); ++i) {
            if (i == rowIDIndex || sumIndex != -1 && i == sumIndex) continue;
            int index = saveDataBuilder.addLink(linkKeys.get(i));
            linkIndexs.add(index);
        }
        List<MetaData> regionMetaDatas = floatRegionSaveParam.getRegionMetaDatas();
        JtableContext context = regionDataCommitSet.getContext();
        List<Integer> validDataRowIndexs = DataCrudUtil.getValidDataRowIndexs(regionMetaDatas, linkKeys, region, context, newdatas);
        DimensionValueSet dimensionValueSet = floatRegionSaveParam.getDimensionValueSet();
        List<FieldData> bizKeyOrderFields = floatRegionSaveParam.getBizKeyOrderFields();
        block1: for (int i = 0; i < newdatas.size(); ++i) {
            List<List<Object>> rowDatas = newdatas.get(i);
            if (validDataRowIndexs.contains(i)) continue;
            List<Object> rowIDValueList = rowDatas.get(rowIDIndex);
            String rowID = rowIDValueList.get(1).toString();
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            StringBuffer floatRowID = new StringBuffer();
            if (region.getAllowDuplicateKey()) {
                if (StringUtils.isEmpty((String)rowID) || rowID.contains("FILL_ENTITY_EMPTY")) {
                    rowID = UUID.randomUUID().toString();
                }
                dimensionCombinationBuilder.setValue("RECORDKEY", (Object)rowID);
                if (floatRowID.length() > 0) {
                    floatRowID.append("#^$");
                }
                floatRowID.append(rowID);
            }
            for (FieldData bizKeyOrderField : bizKeyOrderFields) {
                String linkKey = bizKeyOrderField.getDataLinkKey();
                if (!StringUtils.isNotEmpty((String)linkKey)) continue;
                int linkKeyIndex = linkKeys.indexOf(linkKey);
                List<Object> linkKeyValueList = rowDatas.get(linkKeyIndex);
                String dimensionName = this.jtableDataEngineService.getDimensionName(bizKeyOrderField);
                dimensionCombinationBuilder.setValue(dimensionName, linkKeyValueList.get(1));
                if (floatRowID.length() > 0) {
                    floatRowID.append("#^$");
                }
                floatRowID.append(linkKeyValueList.get(1));
            }
            ReturnRes addRowReturnRes = saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 1);
            if (addRowReturnRes.getCode() != 0) {
                return 0;
            }
            int index = 0;
            for (int j = 0; j < rowDatas.size(); ++j) {
                ReturnRes setData;
                if (j == rowIDIndex || sumIndex != -1 && j == sumIndex) continue;
                Object data = rowDatas.get(j).get(1);
                if (data == null || StringUtils.isEmpty((String)data.toString())) {
                    data = null;
                }
                if ((setData = saveDataBuilder.setData(((Integer)linkIndexs.get(index)).intValue(), data)).getCode() != 0) continue block1;
                if (j == rowDatas.size() - 1) {
                    for (List<Object> modifyDatas : rowDatas) {
                        if (modifyDatas.get(0).toString().equals(modifyDatas.get(1).toString())) continue;
                        ++replaceNum;
                    }
                }
                ++index;
            }
        }
        return replaceNum;
    }

    private int modifyFloatRowData(FindReplaceDataRegionSet findReplaceDataRegionSet, FloatRegionSaveStructure floatRegionSaveParam) {
        List<List<List<Object>>> modifydata = findReplaceDataRegionSet.getModifydata();
        if (modifydata == null || modifydata.size() == 0) {
            return 0;
        }
        int replaceNum = 0;
        RegionData region = floatRegionSaveParam.getRegion();
        SaveDataBuilder saveDataBuilder = floatRegionSaveParam.getSaveDataBuilder();
        RegionRelation regionRelation = floatRegionSaveParam.getRegionRelation();
        Map<String, List<String>> cells = findReplaceDataRegionSet.getCells();
        List<String> linkKeys = cells.get(region.getKey());
        int rowIDIndex = linkKeys.indexOf("ID");
        int sumIndex = linkKeys.indexOf("SUM");
        ArrayList<Integer> dataMaskLinkIndexs = new ArrayList<Integer>();
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (int i = 0; i < linkKeys.size(); ++i) {
            if (i == rowIDIndex || sumIndex != -1 && i == sumIndex) continue;
            boolean isDataMask = false;
            MetaData metaData = regionRelation.getMetaDataByLink(linkKeys.get(i));
            if (metaData != null) {
                isDataMask = metaData.isSensitive();
            }
            if (isDataMask) {
                dataMaskLinkIndexs.add(i);
                linkIndexs.add(-1);
                continue;
            }
            int index = saveDataBuilder.addLink(linkKeys.get(i));
            linkIndexs.add(index);
        }
        DimensionValueSet dimensionValueSet = floatRegionSaveParam.getDimensionValueSet();
        List<FieldData> bizKeyOrderFields = floatRegionSaveParam.getBizKeyOrderFields();
        List<MetaData> regionMetaDatas = floatRegionSaveParam.getRegionMetaDatas();
        List<Integer> validDataRowIndexs = DataCrudUtil.getValidDataRowIndexs(regionMetaDatas, linkKeys, region, findReplaceDataRegionSet.getContext(), modifydata);
        for (int i = 0; i < modifydata.size(); ++i) {
            List<List<Object>> rowDatas = modifydata.get(i);
            List<Object> rowIDValueList = rowDatas.get(rowIDIndex);
            String rowID = rowIDValueList.get(1).toString();
            if (rowID.contains("FILL_ENTITY_EMPTY")) continue;
            if (validDataRowIndexs.contains(i)) {
                for (List<Object> modifyDatas : rowDatas) {
                    if (modifyDatas.get(0).toString().equals(modifyDatas.get(1).toString())) continue;
                    ++replaceNum;
                }
                findReplaceDataRegionSet.getDeletedata().add(rowID);
                continue;
            }
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DataCrudUtil.setBizKeyValueForDimension(dimensionCombinationBuilder, dimensionValueSet, rowID, bizKeyOrderFields);
            saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 2);
            int index = 0;
            for (int j = 0; j < rowDatas.size(); ++j) {
                ReturnRes setReturnRes;
                if (j == rowIDIndex || sumIndex != -1 && j == sumIndex || dataMaskLinkIndexs.contains(j)) continue;
                Object data = rowDatas.get(j).get(1);
                String datastr = data.toString();
                Object oldData = rowDatas.get(j).get(0);
                if (data == null || StringUtils.isEmpty((String)data.toString())) {
                    data = null;
                }
                if ((setReturnRes = saveDataBuilder.setData(((Integer)linkIndexs.get(index)).intValue(), data)).getCode() == 0) {
                    if (!oldData.toString().equals(datastr)) {
                        ++replaceNum;
                    }
                } else {
                    saveDataBuilder.setData(((Integer)linkIndexs.get(index)).intValue(), oldData);
                }
                ++index;
            }
        }
        return replaceNum;
    }
}

