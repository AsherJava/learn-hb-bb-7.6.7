/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.TableConfig;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;

public class SingleMappingConfig
implements ISingleMappingConfig,
Serializable {
    public static final String DEFAULT_NAME = "\u6620\u5c04\u65b9\u6848";
    public static final String NAME_SUFFIX = "-\u590d\u5236";
    private static final long serialVersionUID = -6276347229924788651L;
    private String mappingConfigKey;
    private String configName;
    private String schemeKey;
    private String taskKey;
    private SingleFileTaskInfo taskInfo;
    private List<SingleFileFormulaItem> formulaInfos;
    private List<SingleFileTableInfo> tableInfos;
    private List<SingleFileFieldInfo> zbFields;
    private Map<String, SingleFileEnumInfo> enumInfos;
    private List<RuleMap> mapRule;
    private UnitMapping mapping;
    private MappingConfig config;
    private TableConfig tableConfig;
    private boolean autoGenerate;

    public SingleMappingConfig() {
    }

    public SingleMappingConfig(String mappingConfigKey, String configName, String schemeKey, String taskKey, SingleFileTaskInfo taskInfo, List<SingleFileFormulaItem> formulaInfos, List<SingleFileTableInfo> tableInfos, List<SingleFileFieldInfo> zbFields, Map<String, SingleFileEnumInfo> enumInfos, List<RuleMap> mapRule, UnitMapping mapping, MappingConfig config, TableConfig tableConfig) {
        this.mappingConfigKey = mappingConfigKey;
        this.configName = configName;
        this.schemeKey = schemeKey;
        this.taskKey = taskKey;
        this.taskInfo = taskInfo;
        this.formulaInfos = formulaInfos;
        this.tableInfos = tableInfos;
        this.zbFields = zbFields;
        this.enumInfos = enumInfos;
        this.mapRule = mapRule;
        this.mapping = mapping;
        this.config = config;
        this.tableConfig = tableConfig;
    }

    @Override
    public String getMappingConfigKey() {
        return this.mappingConfigKey;
    }

    @Override
    public void setMappingConfigKey(String mappingConfigKey) {
        this.mappingConfigKey = mappingConfigKey;
    }

    @Override
    public String getConfigName() {
        return this.configName;
    }

    @Override
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public SingleFileTaskInfo getTaskInfo() {
        return this.taskInfo;
    }

    @Override
    public void setTaskInfo(SingleFileTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public List<SingleFileFormulaItem> getFormulaInfos() {
        return this.formulaInfos;
    }

    @Override
    public void setFormulaInfos(List<SingleFileFormulaItem> formulaInfos) {
        this.formulaInfos = formulaInfos;
    }

    @Override
    public List<SingleFileTableInfo> getTableInfos() {
        return this.tableInfos;
    }

    @Override
    public void setTableInfos(List<SingleFileTableInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }

    @Override
    public void setZbFields(List<SingleFileFieldInfo> zbFields) {
        this.zbFields = zbFields;
    }

    @Override
    public List<RuleMap> getMapRule() {
        return this.mapRule;
    }

    @Override
    public List<RuleMap> getMapRule(RuleKind kind) {
        return this.mapRule.stream().filter(r -> r.getRule().equals((Object)kind)).collect(Collectors.toList());
    }

    @Override
    public void setMapRule(List<RuleMap> mapRule) {
        this.mapRule = mapRule;
    }

    @Override
    public UnitMapping getMapping() {
        return this.mapping;
    }

    @Override
    public void setMapping(UnitMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public MappingConfig getConfig() {
        return this.config;
    }

    @Override
    public void setConfig(MappingConfig config) {
        this.config = config;
    }

    @Override
    public TableConfig getTableConfig() {
        return this.tableConfig;
    }

    @Override
    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }

    @Override
    public Map<String, SingleFileEnumInfo> getEnumInfos() {
        return this.enumInfos;
    }

    @Override
    public void setEnumInfos(Map<String, SingleFileEnumInfo> enumInfos) {
        this.enumInfos = enumInfos;
    }

    @Override
    public void putEnumInfo(String code, SingleFileEnumInfo enumInfo) {
        this.enumInfos.put(code, enumInfo);
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    @Override
    public boolean isAutoGenerate() {
        return this.autoGenerate;
    }

    @Override
    public List<SingleFileFieldInfo> getZbFields() {
        return this.zbFields;
    }
}

