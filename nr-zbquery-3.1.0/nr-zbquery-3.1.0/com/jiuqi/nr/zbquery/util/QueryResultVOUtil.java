/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo
 *  com.jiuqi.bi.quickreport.engine.result.CellResultInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter
 *  com.jiuqi.nvwa.cellbook.converter.ICellBookGridDataConverterProvider
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.Row
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.engine.ZBQueryResult;
import com.jiuqi.nr.zbquery.engine.grid.QueryResultGridDataConverterProvider;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.rest.vo.DrillPierceParamVO;
import com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO;
import com.jiuqi.nr.zbquery.rest.vo.QueryResultVO;
import com.jiuqi.nr.zbquery.service.DrillPierceService;
import com.jiuqi.nr.zbquery.util.HyperLinkDataCover;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter;
import com.jiuqi.nvwa.cellbook.converter.ICellBookGridDataConverterProvider;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.Row;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class QueryResultVOUtil {
    private static final String ROW_DIMS = "rowDimsMap";
    private ZBQueryResult zbQueryResult;
    private QueryModelFinder finder;
    private QueryConfigVO queryConfigVO;
    private QueryResultVO queryResultVO = new QueryResultVO();
    private JSONObject hyperLinkData;

    public QueryResultVOUtil() {
    }

    public QueryResultVOUtil(ZBQueryResult zbQueryResult, QueryConfigVO config) {
        this.zbQueryResult = zbQueryResult;
        this.queryConfigVO = config;
        this.finder = new QueryModelFinder(config.getQueryModel());
    }

    public QueryResultVO getResultVO() {
        return this.queryResultVO;
    }

    private void setHeaderWrapline(CellBook cellBook) {
        List sheets = cellBook.getSheets();
        sheets.forEach(sheet -> {
            int headerRowCount = sheet.getHeaderRowCount();
            int columnCount = sheet.getColumnCount();
            for (int r = 0; r < headerRowCount; ++r) {
                Row row = sheet.getRow(r);
                row.setAuto(true);
                for (int c = 0; c < columnCount; ++c) {
                    Cell cell = sheet.getCell(r, c);
                    cell.setWrapLine(true);
                }
            }
        });
    }

    public QueryResultVO buildQueryResultVO() {
        GridData gridData = this.zbQueryResult.getData();
        this.hyperLinkData = HyperLinkDataCover.hyperlinkEnv2Json(this.zbQueryResult.getHyperlinkEnv());
        if (gridData != null) {
            JSONObject qlData = HyperLinkDataCover.hyperlinkCellMsg2Json(gridData);
            this.hyperLinkData.put("qlData", (Object)qlData);
            this.setZBPierceRestrictionsInfo(gridData);
            if (gridData.getRowCount() > 1) {
                CellBook cellBook = new CellBook();
                QueryResultGridDataConverterProvider converterProvider = new QueryResultGridDataConverterProvider();
                CellBookGriddataConverter.gridDataToCellBook((GridData)gridData, (CellBook)cellBook, (String)"result", (String)"result", (ICellBookGridDataConverterProvider)converterProvider);
                this.setHeaderWrapline(cellBook);
                this.queryResultVO.setCellBook(cellBook);
                this.queryResultVO.setTreeInfo(converterProvider.getTreeInfo().toString());
            }
        }
        this.queryResultVO.setPageInfo(this.zbQueryResult.getPageInfo());
        this.queryResultVO.setColNames(this.zbQueryResult.getColNames());
        this.queryResultVO.setHyperlinkEnv(this.hyperLinkData.toString());
        return this.queryResultVO;
    }

    public void buildDrillInfo(DrillPierceService drillPierceService) {
        boolean isMasterDimInRow;
        QueryDimension masterDimension = this.finder.getMasterDimension();
        if (masterDimension == null) {
            return;
        }
        String unitFullName = masterDimension.getFullName();
        boolean bl = isMasterDimInRow = this.finder.getLayoutIndexOnRow(unitFullName) > -1;
        if (!isMasterDimInRow) {
            return;
        }
        ConditionField conditionField = this.finder.getConditionField(unitFullName);
        if (conditionField == null || conditionField.getConditionType() == ConditionType.SINGLE) {
            return;
        }
        JSONObject rowDims = this.getRowDimInfoForDrill(this.finder, this.zbQueryResult);
        this.hyperLinkData.put(ROW_DIMS, (Object)rowDims);
        QueryObject unitQueryObject = this.finder.getQueryObject(unitFullName);
        if (unitQueryObject instanceof FieldGroup && ((FieldGroup)unitQueryObject).isEnablePierce()) {
            DrillPierceParamVO vo = new DrillPierceParamVO();
            vo.setColNames(this.zbQueryResult.getColNames());
            vo.setConditionValues(this.queryConfigVO.getConditionValues());
            vo.setQueryModel(this.queryConfigVO.getQueryModel());
            vo.setRowDims(this.hyperLinkData.get(ROW_DIMS).toString());
            String drillInfo = drillPierceService.getDrillInfo(vo);
            if (drillInfo != null) {
                this.hyperLinkData.put("positionCodeChildren", new JSONObject(drillInfo).get("positionCodeChildren"));
            }
        }
        this.queryResultVO.setHyperlinkEnv(this.hyperLinkData.toString());
    }

    private void setZBPierceRestrictionsInfo(GridData gridData) {
        LinkedHashMap<String, Integer> cellMapperMap = new LinkedHashMap<String, Integer>();
        int rowCount = gridData.getRowCount();
        int colCount = gridData.getColCount();
        int dimRestrictionSize = 0;
        for (int c = 1; c < colCount; ++c) {
            List curRestrictions;
            Object obj;
            for (int r = 1; !(r >= rowCount || gridData.getColVisible(c) && (obj = gridData.getObj(c, r)) instanceof CellResultInfo && (curRestrictions = ((CellResultInfo)obj).getRestrictions()).size() > dimRestrictionSize && (dimRestrictionSize = curRestrictions.size()) >= this.finder.getQueryModel().getDimensions().size()); ++r) {
            }
        }
        JSONArray restrictionIndex = new JSONArray();
        for (int c = 1; c < colCount; ++c) {
            JSONArray colJson = new JSONArray();
            for (int r = 1; r < rowCount; ++r) {
                List curRestrictions;
                if (!gridData.getColVisible(c)) continue;
                Object obj = gridData.getObj(c, r);
                if (obj instanceof CellResultInfo && (curRestrictions = ((CellResultInfo)obj).getRestrictions()).size() == dimRestrictionSize) {
                    JSONObject cellMapper = this.formatRestriction(curRestrictions);
                    Integer dimIndex = cellMapperMap.getOrDefault(cellMapper.toString(), -1);
                    if (dimIndex < 0) {
                        dimIndex = cellMapperMap.size();
                        cellMapperMap.put(cellMapper.toString(), dimIndex);
                    }
                    colJson.put((Object)dimIndex);
                    continue;
                }
                colJson.put((Object)"");
            }
            restrictionIndex.put((Object)colJson);
        }
        this.hyperLinkData.put("cellDimRestrictionIndex", (Object)restrictionIndex);
        JSONArray cellMappers = new JSONArray();
        for (Map.Entry entry : cellMapperMap.entrySet()) {
            cellMappers.put((Object)new JSONObject((String)entry.getKey()));
        }
        this.hyperLinkData.put("cellDimRestrictions", (Object)cellMappers);
    }

    public JSONObject formatRestriction(List<CellRestrictionInfo> curRestrictions) {
        JSONObject result = new JSONObject();
        Map<String, String> aliasFullNameMapper = this.zbQueryResult.getAliasFullNameMapper();
        for (CellRestrictionInfo cellRestrictionInfo : curRestrictions) {
            if (cellRestrictionInfo == null) continue;
            String value = cellRestrictionInfo.getValue().toString();
            String fieldName = cellRestrictionInfo.getFieldName();
            String fullName = aliasFullNameMapper.get(fieldName);
            if (StringUtils.isEmpty((String)fullName)) continue;
            QueryObject queryObject = this.finder.getQueryObject(fullName);
            if (queryObject != null) {
                QueryDimension queryDimension;
                boolean isPeriod;
                if (queryObject.getType() == QueryObjectType.DIMENSION) {
                    boolean isPeriod2;
                    QueryDimension queryDimension2 = this.finder.getQueryDimension(fullName);
                    boolean bl = isPeriod2 = queryDimension2.getDimensionType() == QueryDimensionType.PERIOD;
                    if (isPeriod2) {
                        value = PeriodUtil.toNrPeriod(value, queryDimension2.getPeriodType());
                    }
                    result.put(this.getKey(fullName, queryDimension2, queryObject), (Object)value);
                    continue;
                }
                if (queryObject.getType() != QueryObjectType.DIMENSIONATTRIBUTE) continue;
                QueryDimension parentDim = this.finder.getQueryDimension(queryObject.getParent());
                boolean bl = isPeriod = parentDim.getDimensionType() == QueryDimensionType.PERIOD;
                if (isPeriod && value.length() > 4) {
                    value = PeriodUtil.toNrPeriod(value, parentDim.getPeriodType());
                }
                if ((queryDimension = this.finder.getQueryDimension(queryObject.getParent())).isVirtualDimension()) {
                    result.put(fullName.split("\\.")[1], (Object)value);
                    continue;
                }
                if (StringUtils.isNotEmpty((String)queryObject.getMessageAlias())) {
                    result.put(queryObject.getMessageAlias(), (Object)value);
                    continue;
                }
                result.put(fullName, (Object)value);
                continue;
            }
            if (fullName.contains("BIZKEYORDER")) {
                result.put("RECORDKEY", (Object)value);
                continue;
            }
            if (!fullName.contains(".")) continue;
            String parentFullName = fullName.split("\\.")[0];
            QueryDimension queryDimension = this.finder.getQueryDimension(parentFullName);
            QueryObject qObject = this.finder.getQueryObject(parentFullName);
            result.put(this.getKey(fullName, queryDimension, qObject), (Object)value);
        }
        return result;
    }

    private String getKey(String fullName, QueryDimension queryDimension, QueryObject qObject) {
        String key = fullName;
        if (queryDimension != null && StringUtils.isNotEmpty((String)queryDimension.getMessageAlias())) {
            key = queryDimension.getMessageAlias();
        } else if (qObject != null && StringUtils.isNotEmpty((String)qObject.getMessageAlias())) {
            key = qObject.getMessageAlias();
        }
        return key;
    }

    private JSONObject getRowDimInfoForDrill(QueryModelFinder finder, ZBQueryResult queryResult) {
        HashMap<Integer, String> rowDims = new HashMap<Integer, String>();
        GridData gridData = queryResult.getData();
        Map<String, String> aliasFullNameMapper = queryResult.getAliasFullNameMapper();
        int rowCount = gridData.getRowCount();
        int colCount = gridData.getColCount();
        block0: for (int r = 1; r < rowCount; ++r) {
            for (int c = 1; c < colCount; ++c) {
                JSONObject dim;
                List curRestrictions;
                Object obj = gridData.getObj(c, r);
                if (!(obj instanceof CellResultInfo) || (curRestrictions = ((CellResultInfo)obj).getRestrictions()) == null) continue;
                if (rowDims.get(r - 1) != null && JSONObject.getNames((JSONObject)(dim = new JSONObject((String)rowDims.get(r - 1)))) != null && JSONObject.getNames((JSONObject)dim).length > curRestrictions.size()) continue block0;
                JSONObject jsonObject = this.handleRestrictionsInfo(finder, curRestrictions, aliasFullNameMapper);
                rowDims.put(r - 1, jsonObject.toString());
            }
        }
        JSONObject result = new JSONObject();
        for (Map.Entry entry : rowDims.entrySet()) {
            result.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        return result;
    }

    private JSONObject handleRestrictionsInfo(QueryModelFinder finder, List<CellRestrictionInfo> curRestrictions, Map<String, String> aliasFullNameMapper) {
        JSONObject result = new JSONObject();
        for (CellRestrictionInfo cellRestrictionInfo : curRestrictions) {
            if (cellRestrictionInfo == null) continue;
            String value = cellRestrictionInfo.getValue().toString();
            String fieldName = cellRestrictionInfo.getFieldName();
            String fullName = aliasFullNameMapper.get(fieldName);
            if (StringUtils.isEmpty((String)fullName)) continue;
            QueryObject queryObject = finder.getQueryObject(fullName);
            if (queryObject != null) {
                QueryDimension queryDimension;
                boolean isPeriod;
                if (queryObject.getType() == QueryObjectType.DIMENSION) {
                    QueryDimension queryDimension2 = finder.getQueryDimension(fullName);
                    boolean bl = isPeriod = queryDimension2.getDimensionType() == QueryDimensionType.PERIOD;
                    if (isPeriod) {
                        value = PeriodUtil.toNrPeriod(value, queryDimension2.getPeriodType());
                    }
                    result.put(fullName, (Object)value);
                    continue;
                }
                if (queryObject.getType() != QueryObjectType.DIMENSIONATTRIBUTE) continue;
                QueryDimension parentDim = finder.getQueryDimension(queryObject.getParent());
                boolean bl = isPeriod = parentDim.getDimensionType() == QueryDimensionType.PERIOD;
                if (isPeriod && value.length() > 4) {
                    value = PeriodUtil.toNrPeriod(value, parentDim.getPeriodType());
                }
                if ((queryDimension = finder.getQueryDimension(queryObject.getParent())).isVirtualDimension()) {
                    result.put(fullName.split("\\.")[1], (Object)value);
                    continue;
                }
                result.put(queryObject.getParent(), (Object)value);
                if (!isPeriod) continue;
                result.put(queryObject.getParent() + "_BIPeriod", (Object)cellRestrictionInfo.getValue().toString());
                continue;
            }
            if (!fullName.contains(".")) continue;
            String parentFullName = fullName.split("\\.")[0];
            result.put(parentFullName, (Object)value);
        }
        return result;
    }
}

