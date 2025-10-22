/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 */
package com.jiuqi.nr.table.io.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.ColumnTypeDetector;
import com.jiuqi.nr.table.io.DataReader;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.ReaderRegistry;
import com.jiuqi.nr.table.io.Source;
import com.jiuqi.nr.table.io.json.DataFrameDeserializer;
import com.jiuqi.nr.table.io.json.DataFrameSerialize;
import com.jiuqi.nr.table.io.json.JsonReadOptions;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class JsonReader
implements DataReader<JsonReadOptions> {
    private static final JsonReader INSTANCE = new JsonReader();
    private final ObjectMapper mapper;

    public static void register(ReaderRegistry registry) {
        registry.registerExtension("json", INSTANCE);
        registry.registerMimeType("application/json", INSTANCE);
        registry.registerOptions(JsonReadOptions.class, INSTANCE);
    }

    public JsonReader() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new DataFrameSerialize());
        module.addDeserializer(DataFrame.class, new DataFrameDeserializer());
        this.mapper = new ObjectMapper();
        this.mapper.registerModule((Module)module);
    }

    @Override
    public DataFrame<?> read(Source source) throws IOException {
        return this.read(JsonReadOptions.builder(source).build());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataFrame<?> read(JsonReadOptions options) throws IOException {
        Reader createReader = null;
        try {
            createReader = options.source().createReader(null);
            DataFrame df = (DataFrame)this.mapper.readValue(createReader, DataFrame.class);
            if (null != df) {
                DataFrame dataFrame = df;
                return dataFrame;
            }
            JsonNode jsonObj = this.mapper.readTree(createReader);
            if (options.path() != null) {
                jsonObj = jsonObj.at(options.path());
            }
            if (!jsonObj.isArray()) {
                throw new IllegalStateException("Only reading a JSON array is currently supported. The array must hold an array or object for each row.");
            }
            if (jsonObj.size() == 0) {
                DataFrame<Object> dataFrame = DataFrame.create(options.tableName());
                return dataFrame;
            }
            JsonNode firstNode = jsonObj.get(0);
            if (firstNode.isArray()) {
                DataFrame<?> dataFrame = this.convertArrayOfArrays(jsonObj, options);
                return dataFrame;
            }
            DataFrame<?> dataFrame = this.convertArrayOfObjects(jsonObj, options);
            return dataFrame;
        }
        finally {
            if (null != createReader) {
                createReader.close();
            }
        }
    }

    private DataFrame<?> convertArrayOfArrays(JsonNode jsonObj, ReadOptions options) {
        int i;
        JsonNode firstNode = jsonObj.get(0);
        boolean firstRowAllStrings = true;
        ArrayList<String> columnNames = new ArrayList<String>();
        for (JsonNode n : firstNode) {
            if (n.isTextual()) continue;
            firstRowAllStrings = false;
        }
        boolean hasHeader = firstRowAllStrings;
        for (int i2 = 0; i2 < firstNode.size(); ++i2) {
            columnNames.add(hasHeader ? firstNode.get(i2).textValue() : "Column " + i2);
        }
        ArrayList<String[]> dataRows = new ArrayList<String[]>();
        int n = i = hasHeader ? 1 : 0;
        while (i < jsonObj.size()) {
            JsonNode arr = jsonObj.get(i);
            String[] row = new String[arr.size()];
            for (int j = 0; j < arr.size(); ++j) {
                row[j] = arr.get(j).asText();
            }
            dataRows.add(row);
            ++i;
        }
        return JsonReader.build(columnNames, dataRows, options);
    }

    private DataFrame<?> convertArrayOfObjects(JsonNode jsonObj, ReadOptions options) throws JsonProcessingException {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < jsonObj.size(); ++i) {
            JsonNode rowObj = jsonObj.get(i);
            String flattenedRow = null;
            if (i != 0) {
                result.append(",");
            }
            result.append(flattenedRow);
        }
        String flattenedJsonString = result.append("]").toString();
        JsonNode flattenedJsonObj = this.mapper.readTree(flattenedJsonString);
        HashSet colNames = new HashSet();
        for (JsonNode row : flattenedJsonObj) {
            Iterator fieldNames = row.fieldNames();
            while (fieldNames.hasNext()) {
                colNames.add(fieldNames.next());
            }
        }
        ArrayList<String> columnNames = new ArrayList<String>(colNames);
        ArrayList<String[]> dataRows = new ArrayList<String[]>();
        for (JsonNode node : flattenedJsonObj) {
            String[] arr = new String[columnNames.size()];
            for (int i = 0; i < columnNames.size(); ++i) {
                arr[i] = node.has((String)columnNames.get(i)) ? node.get((String)columnNames.get(i)).asText() : null;
            }
            dataRows.add(arr);
        }
        return JsonReader.build(columnNames, dataRows, options);
    }

    public static DataFrame<?> build(List<String> columnNames, List<String[]> dataRows, ReadOptions options) {
        DataFrame<Object> table = DataFrame.create(options.tableName());
        if (dataRows.isEmpty()) {
            return table;
        }
        ColumnTypeDetector detector = new ColumnTypeDetector(options.columnTypesToDetect());
        Iterator<String[]> iterator = dataRows.iterator();
        ColumnType[] types = detector.detectColumnTypes(iterator, options);
        for (int i = 0; i < types.length; ++i) {
            boolean hasColumnName = i < columnNames.size();
            Optional<ColumnType> configuredColumnType = options.columnTypeReadOptions().columnType(i, hasColumnName ? columnNames.get(i) : null);
            if (!configuredColumnType.isPresent()) continue;
            types[i] = configuredColumnType.get();
        }
        return table;
    }
}

