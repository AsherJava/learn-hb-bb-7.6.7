/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.chart;

import com.jiuqi.nr.query.chart.EngineType;
import java.util.ArrayList;

public enum ChartType {
    MLINEBOX("MLineBox", "\u6298\u7ebf\u76f4\u65b9\u56fe", "LineBarChart", EngineType.ECHARTS),
    ESTACK("EStack", "\u5806\u79ef\u56fe", "LineBarChart", EngineType.ECHARTS),
    WARNING("Warning", "\u9884\u8b66\u533a\u95f4\u56fe", "LineBarChart", EngineType.ANYCHART),
    DOUBLEBOX("DoubleBox", "\u53cc\u5411\u76f4\u65b9\u56fe", "LineBarChart", EngineType.ECHARTS),
    PROGRESS("Progress", "\u8fdb\u5ea6\u56fe", "LineBarChart", EngineType.ECHARTS),
    WATERFALL("Waterfall", "\u7011\u5e03\u56fe", "LineBarChart", EngineType.ANYCHART),
    EPIE("EPie", "\u997c\u56fe", "PieChart", EngineType.ECHARTS),
    EROSE("ERose", "\u5357\u4e01\u683c\u5c14\u73ab\u7470\u56fe", "PieChart", EngineType.ECHARTS),
    EMULTILEVELPIE("EMultiLevelPie", "\u591a\u7ea7\u997c\u56fe", "PieChart", EngineType.ECHARTS),
    MULTIMETER("MultiMeter", "\u4eea\u8868\u76d8", "MeterChart", EngineType.ECHARTS),
    MEASURE("Measure", "\u8ba1\u91cf\u56fe", "MeterChart", EngineType.ECHARTS),
    ZBCARD("ZbCard", "\u6307\u6807\u5361", "MeterChart", EngineType.OTHER),
    MSCATTER("MScatter", "\u6563\u70b9\u56fe", "ScatterChart", EngineType.ECHARTS),
    MQUADRANT("MQuadrant", "\u8c61\u9650\u56fe", "ScatterChart", EngineType.ECHARTS),
    DUPONT("Dupont", "\u675c\u90a6\u56fe", "RelationChart", EngineType.OTHER),
    COMPLEXRELATION("ComplexRelation", "\u548c\u5f26\u56fe", "RelationChart", EngineType.ECHARTS),
    RECTANGLETREE("RectangleTree", "\u77e9\u5f62\u6811\u56fe", "RelationChart", EngineType.ECHARTS),
    FUNNEL("Funnel", "\u6f0f\u6597\u56fe", "RelationChart", EngineType.ECHARTS),
    STRUCTTREE("StructTree", "\u7ed3\u6784\u5173\u7cfb\u56fe", "RelationChart", EngineType.D3),
    RADAR("Radar", "\u96f7\u8fbe\u56fe", "RadarChart", EngineType.ECHARTS),
    RANKING("Ranking", "\u6392\u540d\u56fe", "RankingChart", EngineType.D3),
    WORDCLOUD("WordCloud", "\u5b57\u7b26\u4e91\u56fe", "RankingChart", EngineType.ECHARTS),
    BUBBLESCLOUD("BubblesCloud", "\u6c14\u6ce1\u4e91\u56fe", "RankingChart", EngineType.D3),
    DOUBLECONTRAST("DoubleContrast", "\u4e24\u5206\u5bf9\u6bd4\u56fe", "RankingChart", EngineType.D3),
    EWARNINGMAP("EWarningMap", "\u9884\u8b66\u5730\u56fe", "MapChart", EngineType.ECHARTS),
    EMIGRATEMAP("EMigrateMap", "\u8fc1\u5f99\u5730\u56fe", "MapChart", EngineType.ECHARTS),
    ETRADEMAP("ETradeMap", "\u7701\u8d38\u6613\u5173\u7cfb\u56fe", "MapChart", EngineType.ECHARTS),
    ENATIONALTRADEMAP("ENationalTradeMap", "\u5168\u56fd\u8d38\u6613\u5173\u7cfb\u56fe", "MapChart", EngineType.ECHARTS),
    ECITYMAP("ECityMap", "\u57ce\u5e02\u5730\u56fe", "MapChart", EngineType.ECHARTS),
    ECUSTOMSMAP("ECustomsMap", "\u6d77\u5173\u5730\u56fe", "MapChart", EngineType.ECHARTS),
    MAPONLINEPOLYGON("MapOnlinePolygon", "\u884c\u653f\u533a\u5212\u5730\u56fe", "MapOnLineChart", EngineType.OTHER),
    MAPONLINEPOLYLINE("MapOnlinePolyline", "\u9053\u8def\u5730\u56fe", "MapOnLineChart", EngineType.OTHER),
    MAPONLINEPOINT("MapOnlinePoint", "\u5730\u70b9\u5730\u56fe", "MapOnLineChart", EngineType.OTHER),
    BOXPLOT("BoxPlot", "\u7bb1\u7ebf\u56fe", "BoxPlotChart", EngineType.ECHARTS),
    CUSTOM("CUSTOM", "\u81ea\u5b9a\u4e49\u56fe\u8868", "CustomChart", EngineType.ECHARTS),
    DOUBLEAXIS("DoubleAxis", "\u53cc\u5750\u6807\u8f74\u56fe", "Legacy", EngineType.ANYCHART),
    LINEBOX("LineBox", "\u6298\u7ebf\u76f4\u65b9\u56fe", "Legacy", EngineType.ANYCHART),
    STACK("Stack", "\u5806\u79ef\u56fe", "Legacy", EngineType.ANYCHART),
    PIE("Pie", "\u997c\u56fe", "Legacy", EngineType.ANYCHART),
    MULTILEVELPIE("MultiLevelPie", "\u591a\u7ea7\u997c\u56fe", "Legacy", EngineType.OTHER),
    METER("Meter", "\u4eea\u8868\u76d8\u56fe", "Legacy", EngineType.ANYCHART),
    SCATTER("Scatter", "\u6563\u70b9\u56fe", "Legacy", EngineType.ANYCHART),
    MAP("Map", "\u5730\u56fe", "Legacy", EngineType.OTHER);

    private String value;
    private String title;
    private String group;
    private EngineType engineType;

    private ChartType(String value, String title, String group, EngineType engineType) {
        this.value = value;
        this.title = title;
        this.group = group;
        this.engineType = engineType;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public String group() {
        return this.group;
    }

    public boolean isEcharts() {
        return EngineType.ECHARTS.equals((Object)this.engineType);
    }

    public static ChartType[] filterEchart(ChartType[] types) {
        if (types == null) {
            return null;
        }
        ArrayList<ChartType> result = new ArrayList<ChartType>();
        for (ChartType type : types) {
            if (!type.isEcharts()) continue;
            result.add(type);
        }
        return result.toArray(new ChartType[result.size()]);
    }

    public static ChartType[] getAll() {
        return new ChartType[]{MLINEBOX, ESTACK, WARNING, WATERFALL, DOUBLEBOX, PROGRESS, EPIE, EROSE, EMULTILEVELPIE, MULTIMETER, MEASURE, ZBCARD, MSCATTER, MQUADRANT, DUPONT, COMPLEXRELATION, RECTANGLETREE, FUNNEL, STRUCTTREE, RADAR, RANKING, WORDCLOUD, BUBBLESCLOUD, DOUBLECONTRAST, EWARNINGMAP, EMIGRATEMAP, ETRADEMAP, ENATIONALTRADEMAP, ECITYMAP, MAPONLINEPOLYGON, MAPONLINEPOLYLINE, MAPONLINEPOINT, BOXPLOT, CUSTOM, DOUBLEAXIS, LINEBOX, STACK, PIE, MULTILEVELPIE, METER, SCATTER, MAP};
    }

    public static ChartType[] getNormal() {
        return new ChartType[]{MLINEBOX, ESTACK, WARNING, WATERFALL, DOUBLEBOX, EPIE, MULTIMETER, MEASURE, ZBCARD, MSCATTER, DUPONT, RADAR, MAPONLINEPOLYGON, MAPONLINEPOLYLINE, MAPONLINEPOINT, DOUBLEAXIS, LINEBOX, STACK, PIE, MULTILEVELPIE, METER, SCATTER, MAP, CUSTOM};
    }

    public static boolean allowImage(ChartType type) {
        return type != DUPONT && type != MAPONLINEPOINT && type != MAPONLINEPOLYGON && type != MAPONLINEPOLYLINE && type != ZBCARD && type != BUBBLESCLOUD && type != DOUBLECONTRAST && type != RANKING && type != MEASURE;
    }

    public static boolean allowStatic(ChartType type) {
        return type == DOUBLEAXIS || type == LINEBOX || type == STACK || type == PIE || type == METER || type == SCATTER || type == WARNING;
    }

    public static boolean allowWord(ChartType type) {
        return type == DOUBLEAXIS || type == LINEBOX || type == STACK || type == PIE || type == MLINEBOX || type == EPIE || type == ESTACK;
    }

    public static boolean allowExtScript(ChartType type) {
        return EWARNINGMAP == type || EPIE == type || EROSE == type || RADAR == type || MLINEBOX == type || ESTACK == type || DUPONT == type;
    }

    public static boolean allowFlash(ChartType type) {
        return type == DOUBLEAXIS || type == LINEBOX || type == STACK || type == PIE || type == METER || type == SCATTER || type == WARNING || type == WATERFALL || type == MULTILEVELPIE || type == MAP;
    }

    public static String getGroupTitle(String group) {
        String title = "";
        if ("LineBarChart".equals(group)) {
            title = "\u6298\u7ebf\u76f4\u65b9\u56fe";
        } else if ("PieChart".equals(group)) {
            title = "\u997c\u56fe";
        } else if ("MeterChart".equals(group)) {
            title = "\u4eea\u8868\u76d8\u56fe";
        } else if ("MapChart".equals(group)) {
            title = "\u5730\u56fe";
        } else if ("MapOnLineChart".equals(group)) {
            title = "\u5728\u7ebf\u5730\u56fe";
        } else if ("RankingChart".equals(group)) {
            title = "\u6392\u540d\u56fe";
        } else if ("ScatterChart".equals(group)) {
            title = "\u6563\u70b9\u56fe";
        } else if ("RelationChart".equals(group)) {
            title = "\u5173\u7cfb\u56fe";
        } else if ("RadarChart".equals(group)) {
            title = "\u96f7\u8fbe\u56fe";
        } else if ("BoxPlotChart".equals(group)) {
            title = "\u7bb1\u7ebf\u56fe";
        } else if ("Legacy".equals(group)) {
            title = "\u9057\u7559";
        } else if ("CustomChart".equals(group)) {
            title = "\u81ea\u5b9a\u4e49";
        }
        return title;
    }
}

