/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.io.common.ExtConstants
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaPageDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVParser
 *  org.apache.commons.csv.CSVPrinter
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.hshdcheck.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.hshdcheck.DescribeParam;
import com.jiuqi.nr.hshdcheck.DescribeQueryParam;
import com.jiuqi.nr.hshdcheck.HshdDescribe;
import com.jiuqi.nr.hshdcheck.service.HshdDescribeService;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaPageDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HshdDescribeServiceImpl
implements HshdDescribeService {
    private static final Logger log = LoggerFactory.getLogger(HshdDescribeServiceImpl.class);
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired(required=false)
    private SubDatabaseTableNamesProvider subTableNamesProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public Result<File> exportDescribe(DescribeQueryParam queryParam) {
        String formSchemeKey = queryParam.getFormSchemeKey();
        if (!StringUtils.hasLength(formSchemeKey)) {
            return Result.failed((String)("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + formSchemeKey));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return Result.failed((String)("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + formSchemeKey));
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String tableName = "SYS_ENTITYCHECKUP_" + formScheme.getFormSchemeCode();
        if (this.subTableNamesProvider != null) {
            tableName = this.subTableNamesProvider.getSubDatabaseTableName(formScheme.getTaskKey(), tableName);
        }
        HashSet<String> periods = new HashSet<String>();
        for (DimensionCombination combination : queryParam.getMasterKey().getDimensionCombinations()) {
            FixedDimensionValue periodDimensionValue = combination.getPeriodDimensionValue();
            periods.add(periodDimensionValue.getValue().toString());
        }
        ArrayList<HshdDescribe> list = new ArrayList<HshdDescribe>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(tableName);
        INvwaPageDataAccess pageDataAccess = this.nvwaDataAccessProvider.createPageDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            MemoryDataSet dataRows;
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(tableName);
            if (tableModelDefine == null) {
                return Result.failed((String)("\u672a\u627e\u5230\u62a5\u8868\u5b9e\u4f53\u8868,\u8bf7\u68c0\u67e5\u53c2\u6570" + formSchemeKey));
            }
            List fields = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
            for (ColumnModelDefine field : fields) {
                if (!"ECU_TK_KEY".equals(field.getCode()) && !"ECU_UNIT_KEY".equals(field.getCode()) && !"ECU_UNITNAME".equals(field.getCode()) && !"ECU_JSYS".equals(field.getCode()) && !"PERIOD".equals(field.getCode())) continue;
                columnIndexMap.put(field.getCode(), queryModel.getColumns().size());
                queryModel.getColumns().add(new NvwaQueryColumn(field));
            }
            int rowIndex = 0;
            int endRowIndex = 100000;
            while ((dataRows = pageDataAccess.executeQuery(context, rowIndex, endRowIndex)).size() > 0) {
                rowIndex = endRowIndex;
                endRowIndex += endRowIndex;
                for (DataRow dataRow : dataRows) {
                    String[] split;
                    String period = dataRow.getString(((Integer)columnIndexMap.get("PERIOD")).intValue());
                    if (period == null || (split = period.split("\\|")).length != 2 || !periods.contains(split[0])) continue;
                    HshdDescribe hshdDescribe = new HshdDescribe();
                    hshdDescribe.setFormSchemeKey(formSchemeKey);
                    hshdDescribe.setDestFormSchemeKey(dataRow.getString(((Integer)columnIndexMap.get("ECU_TK_KEY")).intValue()));
                    hshdDescribe.setMdCode(dataRow.getString(((Integer)columnIndexMap.get("ECU_UNIT_KEY")).intValue()));
                    hshdDescribe.setPeriod2period(period);
                    hshdDescribe.setJsys(dataRow.getString(((Integer)columnIndexMap.get("ECU_JSYS")).intValue()));
                    hshdDescribe.setMdName(dataRow.getString(((Integer)columnIndexMap.get("ECU_UNITNAME")).intValue()));
                    list.add(hshdDescribe);
                }
            }
        }
        catch (Exception e) {
            log.error("\u6267\u884c\u6570\u636e\u67e5\u8be2\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            return Result.failed((String)("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + formSchemeKey));
        }
        this.fillHshdDescribe(list);
        String rootpath = ExtConstants.EXPORTDIR + ExtConstants.FILE_SEPARATOR + LocalDate.now() + ExtConstants.FILE_SEPARATOR + UUID.randomUUID() + ExtConstants.FILE_SEPARATOR;
        try {
            String fileName = taskDefine.getTaskCode() + "-" + taskDefine.getTitle() + "-" + formScheme.getTitle() + ".csv";
            Path fileFullPath = Paths.get(rootpath, fileName);
            Files.createDirectories(fileFullPath.getParent(), new FileAttribute[0]);
            Files.createFile(fileFullPath, new FileAttribute[0]);
            HshdDescribeServiceImpl.exportToCsv(list, fileFullPath);
            return Result.succeed((Object)fileFullPath.toFile());
        }
        catch (Exception e) {
            log.error("\u751f\u6210\u6237\u6570\u6838\u5bf9\u6587\u4ef6\u5931\u8d25", e);
            return Result.failed((String)("\u751f\u6210\u6237\u6570\u6838\u5bf9\u6587\u4ef6\u5931\u8d25" + formSchemeKey));
        }
    }

    public void fillHshdDescribe(List<HshdDescribe> data) {
        HashMap<String, FormSchemeDefine> formSchemeDefineMap = new HashMap<String, FormSchemeDefine>();
        HashMap<String, TaskDefine> taskDefineHashMap = new HashMap<String, TaskDefine>();
        for (HshdDescribe datum : data) {
            TaskDefine destTaskDefine;
            FormSchemeDefine destFormSchemeDefine;
            TaskDefine taskDefine;
            String formSchemeKey = datum.getFormSchemeKey();
            String destFormSchemeKey = datum.getDestFormSchemeKey();
            FormSchemeDefine formSchemeDefine = formSchemeDefineMap.computeIfAbsent(formSchemeKey, key -> this.runTimeViewController.getFormScheme(key));
            if (formSchemeDefine == null || (taskDefine = taskDefineHashMap.computeIfAbsent(formSchemeDefine.getTaskKey(), key -> this.runTimeViewController.queryTaskDefine(key))) == null || (destFormSchemeDefine = formSchemeDefineMap.computeIfAbsent(destFormSchemeKey, key -> this.runTimeViewController.getFormScheme(key))) == null || (destTaskDefine = taskDefineHashMap.computeIfAbsent(destFormSchemeDefine.getTaskKey(), key -> this.runTimeViewController.queryTaskDefine(key))) == null) continue;
            datum.setTaskCode(taskDefine.getTaskCode());
            datum.setTaskTitle(taskDefine.getTitle());
            datum.setFormSchemeCode(formSchemeDefine.getFormSchemeCode());
            datum.setFormSchemeTitle(formSchemeDefine.getTitle());
            datum.setDestTaskCode(destTaskDefine.getTaskCode());
            datum.setTaskTitle(destTaskDefine.getTitle());
            datum.setFormSchemeCode(destFormSchemeDefine.getFormSchemeCode());
            datum.setFormSchemeTitle(destFormSchemeDefine.getTitle());
        }
    }

    public static void exportToCsv(List<HshdDescribe> data, Path filePath) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(new String[]{"taskKey", "taskCode", "taskTitle", "formSchemeKey", "formSchemeCode", "formSchemeTitle", "destTaskKey", "destTaskCode", "destTaskTitle", "destFormSchemeKey", "destFormSchemeCode", "destFormSchemeTitle", "mdCode", "mdName", "period2period", "jsys"}).build();
        try (FileWriter out = new FileWriter(filePath.toFile());
             CSVPrinter printer = new CSVPrinter((Appendable)out, format);){
            for (HshdDescribe item : data) {
                printer.printRecord(new Object[]{item.getTaskKey(), item.getTaskCode(), item.getTaskTitle(), item.getFormSchemeKey(), item.getFormSchemeCode(), item.getFormSchemeTitle(), item.getDestTaskKey(), item.getDestTaskCode(), item.getDestTaskTitle(), item.getDestFormSchemeKey(), item.getDestFormSchemeCode(), item.getDestFormSchemeTitle(), item.getMdCode(), item.getMdName(), item.getPeriod2period(), item.getJsys()});
            }
        }
    }

    @Override
    public Result<Void> importDescribe(DescribeParam queryParam, File csvFile) {
        Object formSchemeDefines;
        Object formSchemeTitle;
        String formSchemeKey = queryParam.getFormSchemeKey();
        FormSchemeDefine formScheme = null;
        if (StringUtils.hasLength(formSchemeKey)) {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        } else {
            String name = csvFile.getName();
            String[] codes = org.apache.commons.lang3.StringUtils.substringBeforeLast((String)name, (String)".").split("-");
            if (codes.length != 3) {
                return Result.failed((String)"\u6587\u4ef6\u540d\u79f0\u4e0d\u6b63\u786e\u65e0\u6cd5\u5bfc\u5165");
            }
            String taskCode = codes[0];
            formSchemeTitle = codes[2];
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefineByCode(taskCode);
            if (taskDefine == null) {
                return Result.failed((String)("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + taskCode));
            }
            try {
                formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            Iterator e = formSchemeDefines.iterator();
            while (e.hasNext()) {
                FormSchemeDefine formSchemeDefine = (FormSchemeDefine)e.next();
                if (!formSchemeDefine.getTitle().equals(formSchemeTitle)) continue;
                formScheme = formSchemeDefine;
                break;
            }
        }
        if (formScheme == null) {
            log.error("\u672a\u627e\u5230\u8981\u5bfc\u5165\u7684\u62a5\u8868\u65b9\u6848\uff0c\u62a5\u8868\u65b9\u6848key\uff1a{} {}", (Object)formSchemeKey, (Object)csvFile.getName());
            return Result.failed((String)"\u672a\u627e\u5230\u8981\u5bfc\u5165\u7684\u62a5\u8868\u65b9\u6848");
        }
        ArrayList<HshdDescribe> hshdDescribes = new ArrayList<HshdDescribe>();
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(new String[0]).setSkipHeaderRecord(true).build();
        try {
            FileReader reader = new FileReader(csvFile);
            formSchemeTitle = null;
            try {
                CSVParser csvParser = new CSVParser((Reader)reader, format);
                formSchemeDefines = null;
                try {
                    for (Object record : csvParser) {
                        HshdDescribe describe = new HshdDescribe();
                        describe.setTaskKey(record.get("taskKey"));
                        describe.setTaskCode(record.get("taskCode"));
                        describe.setTaskTitle(record.get("taskTitle"));
                        describe.setFormSchemeKey(record.get("formSchemeKey"));
                        describe.setFormSchemeCode(record.get("formSchemeCode"));
                        describe.setFormSchemeTitle(record.get("formSchemeTitle"));
                        describe.setDestTaskKey(record.get("destTaskKey"));
                        describe.setDestTaskCode(record.get("destTaskCode"));
                        describe.setDestTaskTitle(record.get("destTaskTitle"));
                        describe.setDestFormSchemeKey(record.get("destFormSchemeKey"));
                        describe.setDestFormSchemeCode(record.get("destFormSchemeCode"));
                        describe.setDestFormSchemeTitle(record.get("destFormSchemeTitle"));
                        describe.setMdCode(record.get("mdCode"));
                        describe.setMdName(record.get("mdName"));
                        describe.setPeriod2period(record.get("period2period"));
                        describe.setJsys(record.get("jsys"));
                        hshdDescribes.add(describe);
                    }
                }
                catch (Throwable e) {
                    formSchemeDefines = e;
                    throw e;
                }
                finally {
                    if (csvParser != null) {
                        if (formSchemeDefines != null) {
                            try {
                                csvParser.close();
                            }
                            catch (Throwable e) {
                                ((Throwable)formSchemeDefines).addSuppressed(e);
                            }
                        } else {
                            csvParser.close();
                        }
                    }
                }
            }
            catch (Throwable csvParser) {
                formSchemeTitle = csvParser;
                throw csvParser;
            }
            finally {
                if (reader != null) {
                    if (formSchemeTitle != null) {
                        try {
                            ((Reader)reader).close();
                        }
                        catch (Throwable csvParser) {
                            ((Throwable)formSchemeTitle).addSuppressed(csvParser);
                        }
                    } else {
                        ((Reader)reader).close();
                    }
                }
            }
        }
        catch (IOException e) {
            return Result.failed((String)("CSV \u6587\u4ef6\u5904\u7406\u5931\u8d25: " + e.getMessage()));
        }
        String tableName = "SYS_ENTITYCHECKUP_" + formScheme.getFormSchemeCode();
        if (this.subTableNamesProvider != null) {
            tableName = this.subTableNamesProvider.getSubDatabaseTableName(formScheme.getTaskKey(), tableName);
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(tableName);
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(tableName);
        if (tableModelDefine == null) {
            return Result.failed((String)("\u672a\u627e\u5230\u62a5\u8868\u5b9e\u4f53\u8868,\u8bf7\u68c0\u67e5\u53c2\u6570" + formScheme.getFormSchemeCode()));
        }
        HashSet<String> dwValues = new HashSet<String>();
        HashSet<String> periodValues = new HashSet<String>();
        this.papping(hshdDescribes, queryParam.getParamMapping());
        for (HshdDescribe hshdDescribe : hshdDescribes) {
            dwValues.add(hshdDescribe.getMdCode());
            periodValues.add(hshdDescribe.getPeriod2period());
        }
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
        ColumnModelDefine unitColumnModelDefine = null;
        ColumnModelDefine fcColumnDefine = null;
        ColumnModelDefine periodColumnDefine = null;
        ColumnModelDefine mdCodeColumnModelDefine = null;
        for (ColumnModelDefine field : fields) {
            if (field.getCode().equals("ECU_UNIT_KEY")) {
                unitColumnModelDefine = field;
            } else if (field.getCode().equals("ECU_FC_KEY")) {
                fcColumnDefine = field;
            } else if (field.getCode().equals("PERIOD")) {
                periodColumnDefine = field;
            } else if (field.getCode().equals("MDCODE")) {
                mdCodeColumnModelDefine = field;
            }
            columnIndexMap.put(field.getCode(), queryModel.getColumns().size());
            queryModel.getColumns().add(new NvwaQueryColumn(field));
        }
        queryModel.getColumnFilters().put(mdCodeColumnModelDefine, "00000000-0000-0000-0000-000000000000");
        if (!periodValues.isEmpty()) {
            if (periodValues.size() == 1) {
                queryModel.getColumnFilters().put(periodColumnDefine, periodValues.iterator().next());
            } else {
                queryModel.getColumnFilters().put(periodColumnDefine, new ArrayList(periodValues));
            }
        }
        queryModel.getColumnFilters().put(unitColumnModelDefine, new ArrayList(dwValues));
        queryModel.getColumnFilters().put(fcColumnDefine, formScheme.getKey());
        int updateCount = 0;
        int addCount = 0;
        try {
            INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataSet nvwaDataUpdatable = updatableDataAccess.executeQueryForUpdate(context);
            List rowKeyColumns = nvwaDataUpdatable.getRowKeyColumns();
            for (HshdDescribe hshdDescribe : hshdDescribes) {
                Object[] rowKey = new Object[rowKeyColumns.size()];
                for (int i = 0; i < rowKeyColumns.size(); ++i) {
                    ColumnModelDefine columnModelDefine = (ColumnModelDefine)rowKeyColumns.get(i);
                    rowKey[i] = "PERIOD".equals(columnModelDefine.getCode()) ? hshdDescribe.getPeriod2period() : ("ECU_FC_KEY".equals(columnModelDefine.getCode()) ? hshdDescribe.getFormSchemeKey() : ("ECU_UNIT_KEY".equals(columnModelDefine.getCode()) ? hshdDescribe.getMdCode() : "00000000-0000-0000-0000-000000000000"));
                }
                INvwaDataRow iNvwaDataRow = nvwaDataUpdatable.findRow(new ArrayKey(rowKey));
                if (iNvwaDataRow == null) {
                    iNvwaDataRow = nvwaDataUpdatable.appendRow();
                    ++addCount;
                } else {
                    ++updateCount;
                }
                block48: for (ColumnModelDefine field : fields) {
                    switch (field.getCode()) {
                        case "ECU_UNIT_KEY": {
                            String mdCode = hshdDescribe.getMdCode();
                            Integer i = (Integer)columnIndexMap.get("ECU_UNIT_KEY");
                            iNvwaDataRow.setValue(i.intValue(), (Object)mdCode);
                            continue block48;
                        }
                        case "ECU_UNITNAME": {
                            String mdName = hshdDescribe.getMdName();
                            Integer nameIndex = (Integer)columnIndexMap.get("ECU_UNITNAME");
                            iNvwaDataRow.setValue(nameIndex.intValue(), (Object)mdName);
                            continue block48;
                        }
                        case "ECU_JSYS": {
                            Integer jsysIndex = (Integer)columnIndexMap.get("ECU_JSYS");
                            iNvwaDataRow.setValue(jsysIndex.intValue(), (Object)hshdDescribe.getJsys());
                            continue block48;
                        }
                        case "ECU_TK_KEY": {
                            Integer tkKeyIndex = (Integer)columnIndexMap.get("ECU_TK_KEY");
                            iNvwaDataRow.setValue(tkKeyIndex.intValue(), (Object)hshdDescribe.getDestFormSchemeKey());
                            continue block48;
                        }
                        case "ECU_FC_KEY": {
                            Integer skKeyIndex = (Integer)columnIndexMap.get("ECU_FC_KEY");
                            iNvwaDataRow.setValue(skKeyIndex.intValue(), (Object)hshdDescribe.getFormSchemeKey());
                            continue block48;
                        }
                        case "PERIOD": {
                            Integer periodIndex = (Integer)columnIndexMap.get("PERIOD");
                            iNvwaDataRow.setValue(periodIndex.intValue(), (Object)hshdDescribe.getPeriod2period());
                            continue block48;
                        }
                        case "ECU_CHECK": {
                            Integer checkIndex = (Integer)columnIndexMap.get("ECU_CHECK");
                            iNvwaDataRow.setValue(checkIndex.intValue(), (Object)"");
                            continue block48;
                        }
                    }
                    Integer index = (Integer)columnIndexMap.get(field.getCode());
                    iNvwaDataRow.setValue(index.intValue(), (Object)"00000000-0000-0000-0000-000000000000");
                }
            }
            nvwaDataUpdatable.commitChanges(context);
        }
        catch (Exception e) {
            log.error("\u88c5\u5165\u6237\u6570\u6838\u5bf9\u51fa\u9519\u539f\u56e0\u6570\u636e\u5931\u8d25", e);
            return Result.failed((String)("\u88c5\u5165\u6237\u6570\u6838\u5bf9\u51fa\u9519\u539f\u56e0\u6570\u636e\u5931\u8d25" + e.getMessage()));
        }
        return Result.succeed((String)("\u6237\u6570\u6838\u5bf9\u5bfc\u5165\u5b8c\u6210\uff0c\u66f4\u65b0" + updateCount + "\u6761\u3001\u65b0\u589e" + addCount + "\u6761\u8bf4\u660e"));
    }

    private void papping(List<HshdDescribe> hshdDescribes, ParamsMapping paramsMapping) {
        Map originOrgCode = null;
        if (paramsMapping != null && paramsMapping.tryOrgCodeMap()) {
            List collect = hshdDescribes.stream().map(HshdDescribe::getMdCode).collect(Collectors.toList());
            originOrgCode = paramsMapping.getOriginOrgCode(collect);
        }
        HashMap<String, FormSchemeDefine> formSchemeDefineMap = new HashMap<String, FormSchemeDefine>();
        for (HshdDescribe hshdDescribe : hshdDescribes) {
            FormSchemeDefine formSchemeDefine;
            String mdCode = hshdDescribe.getMdCode();
            if (originOrgCode != null) {
                mdCode = originOrgCode.getOrDefault(mdCode, mdCode);
                hshdDescribe.setMdCode(mdCode);
            }
            if ((formSchemeDefine = formSchemeDefineMap.computeIfAbsent(hshdDescribe.getFormSchemeCode(), code -> this.runTimeViewController.getFormschemeByCode(code))) == null) {
                if (!log.isDebugEnabled()) continue;
                log.debug("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848{}", (Object)hshdDescribe.getFormSchemeCode());
                continue;
            }
            FormSchemeDefine destFormSchemeDefine = formSchemeDefineMap.computeIfAbsent(hshdDescribe.getDestFormSchemeCode(), code -> this.runTimeViewController.getFormschemeByCode(code));
            if (destFormSchemeDefine == null) {
                if (!log.isDebugEnabled()) continue;
                log.debug("\u672a\u627e\u5230\u76ee\u6807\u62a5\u8868\u65b9\u6848{}", (Object)hshdDescribe.getDestFormSchemeCode());
                continue;
            }
            hshdDescribe.setFormSchemeKey(formSchemeDefine.getKey());
            hshdDescribe.setDestFormSchemeKey(destFormSchemeDefine.getKey());
        }
    }
}

