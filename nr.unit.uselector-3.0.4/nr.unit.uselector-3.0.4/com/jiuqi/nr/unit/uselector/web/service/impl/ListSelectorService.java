/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.unit.uselector.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.dataio.ExcelRowData;
import com.jiuqi.nr.unit.uselector.dataio.IExcelWriteWorker;
import com.jiuqi.nr.unit.uselector.dataio.IRowData;
import com.jiuqi.nr.unit.uselector.filter.listselect.FConditionImportWorker;
import com.jiuqi.nr.unit.uselector.filter.listselect.FConditionResultExport;
import com.jiuqi.nr.unit.uselector.filter.listselect.FConditionTemplateExport;
import com.jiuqi.nr.unit.uselector.filter.listselect.FImportedConditions;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableDataSet;
import com.jiuqi.nr.unit.uselector.filter.listselect.FilterDataRuleImpl;
import com.jiuqi.nr.unit.uselector.filter.listselect.USelectorListSelectExecutorImpl;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.web.service.IListSelectorService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ListSelectorService
implements IListSelectorService {
    private static final int MAX_ROW_SIZE = 200;
    private static final int MAX_SHOW_SIZE = 100;
    @Resource
    private USelectorResultSet resultSet;
    @Resource
    private IUSelectorDataSourceHelper dataSourceHelper;

    @Override
    public FTableDataSet editConditions(FImportedConditions conditions) {
        List<ExcelRowData> excelRowData = conditions.getConditions();
        if (excelRowData == null || excelRowData.isEmpty()) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u7b5b\u9009\u5217\u8868\uff0c\u81f3\u5c11\u9700\u8981\u4e00\u4e2a\u6761\u4ef6\u624d\u80fd\u6267\u884c\u7b5b\u9009\uff01");
        }
        IUnitTreeContext context = this.resultSet.getRunContext(conditions.getSelector());
        IUSelectorDataSource dataSource = this.dataSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(context);
        List<IEntityRow> allRows = entityRowProvider.getAllRows();
        return this.getMatchResultWithConditions(new ArrayList<IRowData>(excelRowData), allRows, 100);
    }

    @Override
    public FTableDataSet importConditions(String selector, MultipartFile file) {
        List<IRowData> excelRowData = this.getFileInputStream(selector, file);
        if (excelRowData == null || excelRowData.isEmpty()) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u7b5b\u9009\u5217\u8868\uff0c\u81f3\u5c11\u9700\u8981\u4e00\u4e2a\u6761\u4ef6\u624d\u80fd\u6267\u884c\u7b5b\u9009\uff01");
        }
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        IUSelectorDataSource dataSource = this.dataSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(context);
        List<IEntityRow> allRows = entityRowProvider.getAllRows();
        return this.getMatchResultWithConditions(excelRowData, allRows, 100);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<IRowData> getFileInputStream(String selector, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();){
            FConditionImportWorker readWorker = new FConditionImportWorker(selector);
            List<IRowData> list = readWorker.readRows(inputStream);
            return list;
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    @Override
    public IExcelWriteWorker exportConditionTemplate(String selector) {
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        String entityName = context.getEntityDefine().getCode();
        return new FConditionTemplateExport(entityName);
    }

    @Override
    public IExcelWriteWorker exportResultSet(FImportedConditions conditions) {
        IUnitTreeContext context = this.resultSet.getRunContext(conditions.getSelector());
        IUSelectorDataSource dataSource = this.dataSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(context);
        List<IEntityRow> allRows = entityRowProvider.getAllRows();
        FTableDataSet tableDataSet = this.getMatchResultWithConditions(new ArrayList<IRowData>(conditions.getConditions()), allRows, allRows.size() + 1);
        String entityName = context.getEntityDefine().getCode();
        return new FConditionResultExport(entityName, tableDataSet);
    }

    private FTableDataSet getMatchResultWithConditions(List<IRowData> conditions, List<IEntityRow> allRows, int maxShowSize) {
        FilterDataRuleImpl rule = new FilterDataRuleImpl();
        USelectorListSelectExecutorImpl executor = new USelectorListSelectExecutorImpl(maxShowSize, rule, allRows, conditions);
        FTableDataSet rs = new FTableDataSet();
        rs.setPageSize(200);
        rs.setTableData(executor.executeListSelect());
        rs.setMatchCount(new HashMap<String, Integer>());
        rs.getMatchCount().put("unMatch", executor.getUnMatch());
        rs.getMatchCount().put("exactCount", executor.getExactCount());
        rs.getMatchCount().put("fuzzyCount", executor.getFuzzyCount());
        return rs;
    }
}

