/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.etl.automation;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataTableAutomationWriteContext
implements IContext {
    private List<DimensionCombination> authorityList = new ArrayList<DimensionCombination>();
    private List<DimensionValueSet> detailList = new ArrayList<DimensionValueSet>();
    private InputStream inputStream;
    private Map<String, Integer> bizkeyIndexMap;
    private Map<String, Integer> dimKeyIndexMap;
    private int allNums = 0;
    private int successNums = 0;
    private List<String> bizKeyList;
    private List<DataField> dataFields;
    private DataTable dataTable;
    private DataTableType dataTableType;
    private IDataTable iDataTable;
    private String dataSchemeKey;
    private List<DataDimension> dataSchemeDimension;
    private List<String> zbCodeList = new ArrayList<String>();
    private List<List<Object>> zbDataList = new ArrayList<List<Object>>();
    private IDataQuery iDataQuery;
    private List<DataRowImpl> allDataRows;
    private List<String> zbKeyList = new ArrayList<String>();
    private IDataAccessService iDataAccessService;
    private ITempTable tmpTable;
    private List<Object[]> batchValues = new ArrayList<Object[]>();
    private String datatime;
    private String fullOrAdd;
    private List<Integer> fieldIndexList = new ArrayList<Integer>();

    public List<Integer> getFieldIndexList() {
        return this.fieldIndexList;
    }

    public void setFieldIndexList(List<Integer> fieldIndexList) {
        this.fieldIndexList = fieldIndexList;
    }

    public List<DataDimension> getDataSchemeDimension() {
        return this.dataSchemeDimension;
    }

    public void setDataSchemeDimension(List<DataDimension> dataSchemeDimension) {
        this.dataSchemeDimension = dataSchemeDimension;
    }

    public ITempTable getTmpTable() {
        return this.tmpTable;
    }

    public void setTmpTable(ITempTable tmpTable) {
        this.tmpTable = tmpTable;
    }

    public IDataAccessService getiDataAccessService() {
        return this.iDataAccessService;
    }

    public void setiDataAccessService(IDataAccessService iDataAccessService) {
        this.iDataAccessService = iDataAccessService;
    }

    public List<String> getZbKeyList() {
        return this.zbKeyList;
    }

    public void setZbKeyList(List<String> zbKeyList) {
        this.zbKeyList = zbKeyList;
    }

    public String getDatatime() {
        return this.datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getFullOrAdd() {
        return this.fullOrAdd;
    }

    public void setFullOrAdd(String fullOrAdd) {
        this.fullOrAdd = fullOrAdd;
    }

    public IDataQuery getiDataQuery() {
        return this.iDataQuery;
    }

    public void setiDataQuery(IDataQuery iDataQuery) {
        this.iDataQuery = iDataQuery;
    }

    public List<String> getZbCodeList() {
        return this.zbCodeList;
    }

    public void setZbCodeList(List<String> zbCodeList) {
        this.zbCodeList = zbCodeList;
    }

    public List<List<Object>> getZbDataList() {
        return this.zbDataList;
    }

    public void setZbDataList(List<List<Object>> zbDataList) {
        this.zbDataList = zbDataList;
    }

    public List<Object[]> getBatchValues() {
        return this.batchValues;
    }

    public void setBatchValues(List<Object[]> batchValues) {
        this.batchValues = batchValues;
    }

    public List<DataRowImpl> getAllDataRows() {
        return this.allDataRows;
    }

    public void setAllDataRows(List<DataRowImpl> allDataRows) {
        this.allDataRows = allDataRows;
    }

    public List<DimensionCombination> getAuthorityList() {
        return this.authorityList;
    }

    public void setAuthorityList(List<DimensionCombination> authorityList) {
        this.authorityList = authorityList;
    }

    public List<DimensionValueSet> getDetailList() {
        return this.detailList;
    }

    public void setDetailList(List<DimensionValueSet> detailList) {
        this.detailList = detailList;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Map<String, Integer> getBizkeyIndexMap() {
        return this.bizkeyIndexMap;
    }

    public void setBizkeyIndexMap(Map<String, Integer> bizkeyIndexMap) {
        this.bizkeyIndexMap = bizkeyIndexMap;
    }

    public Map<String, Integer> getDimKeyIndexMap() {
        return this.dimKeyIndexMap;
    }

    public void setDimKeyIndexMap(Map<String, Integer> dimKeyIndexMap) {
        this.dimKeyIndexMap = dimKeyIndexMap;
    }

    public int getAllNums() {
        return this.allNums;
    }

    public void setAllNums(int allNums) {
        this.allNums = allNums;
    }

    public int getSuccessNums() {
        return this.successNums;
    }

    public void setSuccessNums(int successNums) {
        this.successNums = successNums;
    }

    public List<String> getBizKeyList() {
        return this.bizKeyList;
    }

    public void setBizKeyList(List<String> bizKeyList) {
        this.bizKeyList = bizKeyList;
    }

    public List<DataField> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(List<DataField> dataFields) {
        this.dataFields = dataFields;
    }

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public IDataTable getiDataTable() {
        return this.iDataTable;
    }

    public void setiDataTable(IDataTable iDataTable) {
        this.iDataTable = iDataTable;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DataTableAutomationWriteContext(InputStream inputStream, Map<String, Integer> bizkeyIndexMap, List<String> bizKeyList, List<DataField> dataFields, DataTable dataTable, DataTableType dataTableType, IDataTable iDataTable) {
        this.inputStream = inputStream;
        this.bizkeyIndexMap = bizkeyIndexMap;
        this.bizKeyList = bizKeyList;
        this.dataFields = dataFields;
        this.dataTable = dataTable;
        this.dataTableType = dataTableType;
        this.iDataTable = iDataTable;
    }
}

