/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVParser
 *  org.apache.commons.csv.CSVRecord
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.function.func.doc.impl;

import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.function.func.doc.FuncDoc;
import com.jiuqi.nr.function.func.doc.FuncParamDoc;
import com.jiuqi.nr.function.func.doc.IFuncDoc;
import com.jiuqi.nr.function.func.doc.service.IFuncDocService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FuncDocServiceImpl
implements IFuncDocService {
    private static final Logger logger = LogFactory.getLogger(FuncDocServiceImpl.class);
    private volatile List<IFuncDoc> mergeList = null;
    private List<IFuncDoc> docs;
    private static final String DEFAULT_TRUE_CN = "\u662f";
    private static final String DEFAULT_TRUE_US = "TRUE";
    private static final String path = "csv/funcDoc.csv";

    @Autowired(required=false)
    public void setDocs(List<IFuncDoc> docs) {
        this.docs = docs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<IFuncDoc> list() {
        if (this.mergeList == null) {
            FuncDocServiceImpl funcDocServiceImpl = this;
            synchronized (funcDocServiceImpl) {
                if (this.mergeList == null) {
                    List<IFuncDoc> iFuncDocs = this.listStorage();
                    List<IFuncDoc> mergeDocs = this.mergeDocs(iFuncDocs);
                    this.mergeList = Collections.unmodifiableList(mergeDocs);
                }
            }
        }
        return this.mergeList;
    }

    private List<IFuncDoc> listStorage() {
        List<String[]> rows = this.readCSV();
        if (CollectionUtils.isEmpty(rows)) {
            return Collections.emptyList();
        }
        Map<Integer, String> headerMap = this.parseHeader(rows.get(0));
        ArrayList<IFuncDoc> parsedDocs = new ArrayList<IFuncDoc>();
        FuncDoc currentDoc = null;
        HashSet<String> funcNames = new HashSet<String>();
        for (int i = 1; i < rows.size(); ++i) {
            String[] cols = rows.get(i);
            if (cols.length == 0 || !StringUtils.hasText(cols[0])) continue;
            String nameValue = this.getCellValue(cols, headerMap, Column.NAME);
            if (StringUtils.hasText(nameValue)) {
                nameValue = nameValue.toUpperCase(Locale.ROOT);
                if (currentDoc == null || !nameValue.equalsIgnoreCase(currentDoc.name())) {
                    if (funcNames.contains(nameValue)) continue;
                    funcNames.add(nameValue);
                    currentDoc = new FuncDoc();
                    parsedDocs.add(currentDoc);
                }
                currentDoc.setName(nameValue);
            }
            if (currentDoc == null) continue;
            this.processRow(currentDoc, cols, headerMap);
        }
        return parsedDocs;
    }

    private Map<Integer, String> parseHeader(String[] headerRow) {
        HashMap<Integer, String> headerMap = new HashMap<Integer, String>();
        for (int i = 0; i < headerRow.length; ++i) {
            headerMap.put(i, headerRow[i].replace("\ufeff", ""));
        }
        return headerMap;
    }

    private void processRow(FuncDoc doc, String[] cols, Map<Integer, String> headerMap) {
        FuncParamDoc param = null;
        block9: for (int i = 0; i < cols.length; ++i) {
            String colName = headerMap.get(i);
            String value = cols[i];
            if (!StringUtils.hasText(value) || colName == null) continue;
            switch (Column.fromValue(colName)) {
                case TITLE: {
                    doc.setSortName(value);
                    continue block9;
                }
                case CATEGORY: {
                    doc.setCategory(value);
                    continue block9;
                }
                case DESC: {
                    doc.setDesc(value);
                    continue block9;
                }
                case RESULT: {
                    doc.setResult(value);
                    continue block9;
                }
                case EXAMPLE: {
                    doc.setExample(value);
                    continue block9;
                }
                case DEPRECATED: {
                    doc.setDeprecated(DEFAULT_TRUE_CN.equals(value) || DEFAULT_TRUE_US.equalsIgnoreCase(value));
                    continue block9;
                }
                case PARAM_NAME: 
                case PARAM_TYPE: 
                case PARAM_TITLE: 
                case PARAM_DESC: {
                    param = this.setParam(doc, param, colName, value);
                    continue block9;
                }
            }
        }
    }

    @NotNull
    private FuncParamDoc setParam(FuncDoc doc, FuncParamDoc param, String colName, String value) {
        if (param == null) {
            param = new FuncParamDoc();
            doc.addParam(param);
        }
        this.setParamField(param, colName, value);
        return param;
    }

    private void setParamField(FuncParamDoc param, String field, String value) {
        switch (Column.fromValue(field)) {
            case PARAM_NAME: {
                param.setName(value);
                break;
            }
            case PARAM_TYPE: {
                param.setType(value);
                break;
            }
            case PARAM_TITLE: {
                param.setSortName(value);
                break;
            }
            case PARAM_DESC: {
                param.setDesc(value);
                break;
            }
        }
    }

    private String getCellValue(String[] row, Map<Integer, String> header, Column column) {
        for (int i = 0; i < row.length; ++i) {
            if (header.get(i) == null || !header.get(i).equals(column.value)) continue;
            return row[i];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<String[]> readCSV() {
        ClassLoader classLoader = FuncDocServiceImpl.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(path);){
            if (is == null) {
                logger.error("\u8bfb\u53d6\u4e0d\u5230CSV\u6587\u4ef6");
                List<String[]> list = null;
                return list;
            }
            InputStreamReader reader = new InputStreamReader(is);
            CSVParser parse = CSVFormat.DEFAULT.parse((Reader)reader);
            List records = parse.getRecords();
            ArrayList<String[]> rows = new ArrayList<String[]>();
            for (CSVRecord record : records) {
                String[] row = new String[11];
                for (int i = 0; i < 11; ++i) {
                    row[i] = record.get(i);
                }
                rows.add(row);
            }
            ArrayList<String[]> arrayList = rows;
            return arrayList;
        }
        catch (IOException e) {
            logger.error("\u8bfb\u53d6CSV\u6587\u4ef6\u5f02\u5e38");
            return null;
        }
    }

    private List<IFuncDoc> mergeDocs(List<IFuncDoc> storageDocs) {
        ArrayList<IFuncDoc> list = new ArrayList<IFuncDoc>();
        if (CollectionUtils.isEmpty(storageDocs)) {
            return list;
        }
        Map nameMap = CollectionUtils.isEmpty(this.docs) ? Collections.emptyMap() : this.docs.stream().collect(Collectors.toMap(IFuncDoc::name, Function.identity()));
        for (IFuncDoc doc : storageDocs) {
            list.add(nameMap.getOrDefault(doc.name(), doc));
        }
        return list;
    }

    private static enum Column {
        NAME("func"),
        TITLE("title"),
        CATEGORY("category"),
        DESC("desc"),
        PARAM_NAME("paramName"),
        PARAM_TYPE("paramType"),
        PARAM_TITLE("paramTitle"),
        PARAM_DESC("paramDesc"),
        RESULT("result"),
        EXAMPLE("example"),
        DEPRECATED("deprecated");

        private final String value;

        private Column(String value) {
            this.value = value;
        }

        public static Column fromValue(String value) {
            for (Column column : Column.values()) {
                if (!column.value.equals(value)) continue;
                return column;
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }
    }
}

