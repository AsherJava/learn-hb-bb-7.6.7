/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCustomFilter
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCustomFilters
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDynamicFilter
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilter
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilterColumn
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilters
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTop10
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.STDynamicFilterType$Enum
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.STFilterOperator$Enum
 */
package com.jiuqi.np.office.excel2.filter;

import com.jiuqi.np.office.excel2.filter.FilterOperator;
import java.util.List;
import org.apache.poi.ss.usermodel.AutoFilter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCustomFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCustomFilters;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDynamicFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilterColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilters;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSortCondition;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSortState;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTop10;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STDynamicFilterType;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STFilterOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSSFAutoFilterExtend
implements AutoFilter {
    private static final Logger logger = LoggerFactory.getLogger(XSSFAutoFilterExtend.class);
    private XSSFSheet _sheet;

    public XSSFAutoFilterExtend(Sheet sheet) {
        if (sheet instanceof XSSFSheet) {
            this._sheet = (XSSFSheet)sheet;
        } else if (sheet instanceof SXSSFSheet) {
            SXSSFWorkbook wb = (SXSSFWorkbook)sheet.getWorkbook();
            XSSFWorkbook xwb = wb.getXSSFWorkbook();
            this._sheet = xwb.getSheet(sheet.getSheetName());
        } else {
            logger.info("\u53ea\u652f\u630107\u7248\u672c\uff01\uff01");
        }
    }

    public void applyFilter(String column, FilterOperator operator, String criteria, Boolean isAnd) {
        CTAutoFilter af = this._sheet.getCTWorksheet().getAutoFilter();
        List<CTFilterColumn> filterColumnArray = af.getFilterColumnList();
        CTFilterColumn filterColumn = null;
        String ref = af.getRef();
        int sep = ref.indexOf(":");
        CellReference a = new CellReference(ref.substring(0, sep));
        int convertColStringToIndex = CellReference.convertColStringToIndex(column);
        convertColStringToIndex -= a.getCol();
        for (CTFilterColumn ctFilterColumn : filterColumnArray) {
            if (ctFilterColumn.getColId() != (long)convertColStringToIndex) continue;
            filterColumn = ctFilterColumn;
        }
        if (null == filterColumn) {
            filterColumn = af.addNewFilterColumn();
            filterColumn.setColId((long)convertColStringToIndex);
        }
        if (operator == FilterOperator.ABOVE_AVERAGE || operator == FilterOperator.BELOW_AVERAGE) {
            CTDynamicFilter addNewDynamicFilter = filterColumn.addNewDynamicFilter();
            if (operator == FilterOperator.ABOVE_AVERAGE) {
                addNewDynamicFilter.setType(STDynamicFilterType.Enum.forInt((int)2));
            } else {
                addNewDynamicFilter.setType(STDynamicFilterType.Enum.forInt((int)3));
            }
            addNewDynamicFilter.setVal(Double.valueOf(criteria).doubleValue());
        } else if (operator == FilterOperator.TOP10) {
            CTTop10 addNewTop10 = filterColumn.addNewTop10();
            addNewTop10.setVal(10.0);
            addNewTop10.setFilterVal(Double.valueOf(criteria).doubleValue());
        } else if (null == isAnd) {
            CTFilters filters = filterColumn.getFilters();
            if (null == filters) {
                filters = filterColumn.addNewFilters();
            }
            CTFilter addNewFilter = filters.addNewFilter();
            if (operator == FilterOperator.EQUAL) {
                addNewFilter.setVal(criteria);
            }
        } else {
            CTCustomFilters newCustomFilters = filterColumn.getCustomFilters();
            if (null == newCustomFilters) {
                newCustomFilters = filterColumn.addNewCustomFilters();
            }
            CTCustomFilter newCustomFilter = newCustomFilters.addNewCustomFilter();
            STFilterOperator.Enum enumOperator = null;
            if (operator == FilterOperator.CONTAIN) {
                enumOperator = STFilterOperator.Enum.forString((String)"equal");
                criteria = "*" + criteria + "*";
            } else if (operator == FilterOperator.NOT_CONTAIN) {
                enumOperator = STFilterOperator.Enum.forString((String)"notEqual");
                criteria = "*" + criteria + "*";
            } else if (operator == FilterOperator.GREATER_THAN_OR_EQUAL) {
                enumOperator = STFilterOperator.Enum.forString((String)"greaterThanOrEqual");
            } else if (operator == FilterOperator.GREATER_THAN) {
                enumOperator = STFilterOperator.Enum.forString((String)"greaterThan");
            } else if (operator == FilterOperator.EQUAL) {
                enumOperator = STFilterOperator.Enum.forString((String)"equal");
            } else if (operator == FilterOperator.LESS_THAN) {
                enumOperator = STFilterOperator.Enum.forString((String)"lessThan");
            } else if (operator == FilterOperator.LESS_THAN_OR_EQUAL) {
                enumOperator = STFilterOperator.Enum.forString((String)"lessThanOrEqual");
            } else if (operator == FilterOperator.NOT_EQUAL) {
                enumOperator = STFilterOperator.Enum.forString((String)"notEqual");
            }
            newCustomFilter.setOperator(enumOperator);
            newCustomFilter.setVal(criteria);
            newCustomFilters = filterColumn.getCustomFilters();
            newCustomFilters.setAnd(isAnd.booleanValue());
        }
    }

    public void applySort(String column, boolean isAsc) {
        CTAutoFilter af = this._sheet.getCTWorksheet().getAutoFilter();
        CTSortState newSortState = af.addNewSortState();
        String ref = af.getRef();
        int sep = ref.indexOf(":");
        CellReference a = new CellReference(ref.substring(0, sep));
        int row = a.getRow();
        short col = a.getCol();
        String convertNumToColString = CellReference.convertNumToColString(col);
        newSortState.setRef(convertNumToColString + (row + 1) + ":" + ref.substring(sep + 1));
        CTSortCondition newSortCondition = newSortState.addNewSortCondition();
        newSortCondition.setRef(column + row);
        newSortCondition.setDescending(!isAsc);
    }
}

