/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.offsetitem.upgrade;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.offsetitem.entity.GcUnOffsetSelectOption;
import com.jiuqi.gcreport.offsetitem.entity.RangeContent;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UpNvWaRouteParam
implements CustomClassExecutor {
    private static final String QUERY_CUSTOM_CONFIG = "select TITLE, ID, ORDINAL, RECVER, CODE, CONTENT, EFFECTRANGE, DATASOURCE, DATASOURCESTATE, SORTTYPE from GC_UNOFFSETSELECTOPTION where DATASOURCESTATE = 1";
    private static final String QUERY_RANGE_SQL = "select ROUTE_ID,CONFIG_JSON from NVWA_ROUTE join NVWA_ROUTE_PARAM on ID = ROUTE_ID where APP_NAME = 'offset' and PRODUCT_LINE ='@gc'";
    private static final String UPDATE_SQL = "update NVWA_ROUTE_PARAM set CONFIG_JSON = ? where ROUTE_ID = ? ";
    private JdbcTemplate jdbcTemplate;

    public void execute(DataSource da) throws Exception {
        this.jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        List<GcOffsetItemShowType> list = SpringContextUtils.getBeans(GcOffsetItemShowType.class).stream().collect(Collectors.toList());
        Map<String, List<Map<String, Object>>> collect = this.queryConfig(list);
        List<Map<String, Object>> maps = this.queryRangeContent();
        for (Map<String, Object> map : maps) {
            Boolean allDelAll;
            TreeMap<String, Object> jsonStr = new TreeMap<String, Object>();
            String dataSource = (String)map.get("dataSource");
            Boolean bl = map.get("allowDelAll") == null ? null : (allDelAll = Boolean.valueOf((Integer)map.get("allowDelAll") == 1));
            Boolean allowDisableOffset = map.get("allowDisableOffset") == null ? null : Boolean.valueOf((Integer)map.get("allowDisableOffset") == 1);
            Integer showRow = UpNvWaRouteParam.toInteger(map.get("showRow"));
            String routeId = (String)map.get("ROUTE_ID");
            if (dataSource != null) {
                jsonStr.put("dataSource", dataSource);
            }
            if (allDelAll != null) {
                jsonStr.put("allDelAll", allDelAll);
            }
            if (allowDisableOffset != null) {
                jsonStr.put("allowDisableOffset", allowDisableOffset);
            }
            if (showRow != null) {
                jsonStr.put("showRow", showRow);
            }
            HashMap<String, List<Map<String, Object>>> jsonObject = new HashMap<String, List<Map<String, Object>>>();
            for (String key : collect.keySet()) {
                jsonObject.put(key, collect.get(key));
            }
            jsonStr.put("showTypeOption", jsonObject);
            HashMap<String, TreeMap<String, Object>> configMap = new HashMap<String, TreeMap<String, Object>>();
            configMap.put("customConfig", jsonStr);
            String dbStr = JsonUtils.writeValueAsString(configMap);
            this.updateDB(routeId, dbStr);
        }
    }

    private void updateDB(String routeId, String customConfig) {
        this.jdbcTemplate.update(UPDATE_SQL, ps -> {
            ps.setBytes(1, customConfig.getBytes(StandardCharsets.UTF_8));
            ps.setString(2, routeId);
        });
    }

    private Map<String, List<Map<String, Object>>> queryConfig(List<GcOffsetItemShowType> gcOffsetItemShowTypeList) {
        List options = this.jdbcTemplate.query(QUERY_CUSTOM_CONFIG, (RowMapper)new BeanPropertyRowMapper(GcUnOffsetSelectOption.class));
        Map<String, List<Map<String, Object>>> result = options.stream().collect(Collectors.groupingBy(item -> item.DATASOURCE + "_" + item.EFFECTRANGE, Collectors.mapping(option -> {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("isSupportUnitTreeSort", this.getSortValByCode(option.getCODE(), gcOffsetItemShowTypeList));
            map.put("isUnitTreeSort", String.valueOf(0));
            map.put("showType", option.getTITLE());
            map.put("value", option.getCODE());
            map.put("isShow", String.valueOf(1));
            map.put("ordinal", option.getORDINAL());
            return map;
        }, Collectors.toList())));
        return result;
    }

    private boolean getSortValByCode(String code, List<GcOffsetItemShowType> gcOffsetItemShowTypeList) {
        for (GcOffsetItemShowType g : gcOffsetItemShowTypeList) {
            if (!g.getCode().equals(code)) continue;
            return g.isEnableMemorySort();
        }
        return false;
    }

    private List<Map<String, Object>> queryRangeContent() throws SQLException, IOException {
        ArrayList<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        List result = this.jdbcTemplate.query(QUERY_RANGE_SQL, (rs, row) -> {
            RangeContent rangeContent = new RangeContent();
            rangeContent.setId(rs.getString(1));
            rangeContent.setContent(rs.getBlob(2));
            return rangeContent;
        });
        for (RangeContent item : result) {
            String str = UpNvWaRouteParam.blobToString(item.getContent());
            Map map = (Map)JsonUtils.readValue((String)str, Map.class);
            map.put("ROUTE_ID", item.getId());
            if (str.equals("{}")) continue;
            res.add(map);
        }
        return res;
    }

    /*
     * Exception decompiling
     */
    public static String blobToString(Blob blob) throws SQLException, IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static Integer toInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String str;
            if (obj instanceof Integer) {
                return (Integer)obj;
            }
            if (obj instanceof String && !(str = (String)obj).trim().isEmpty()) {
                return Integer.parseInt(str.trim());
            }
            if (obj instanceof Number) {
                return ((Number)obj).intValue();
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot convert to Integer: " + obj);
        }
        return null;
    }
}

