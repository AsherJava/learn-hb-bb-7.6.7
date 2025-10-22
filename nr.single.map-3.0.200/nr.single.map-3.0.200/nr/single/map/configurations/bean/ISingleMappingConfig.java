/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.util.List;
import java.util.Map;
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

public interface ISingleMappingConfig {
    public String getMappingConfigKey();

    public void setMappingConfigKey(String var1);

    public String getConfigName();

    public void setConfigName(String var1);

    public String getTaskKey();

    public void setTaskKey(String var1);

    public String getSchemeKey();

    public void setSchemeKey(String var1);

    public SingleFileTaskInfo getTaskInfo();

    public void setTaskInfo(SingleFileTaskInfo var1);

    public List<SingleFileFormulaItem> getFormulaInfos();

    public void setFormulaInfos(List<SingleFileFormulaItem> var1);

    public void setTableConfig(TableConfig var1);

    public TableConfig getTableConfig();

    public Map<String, SingleFileEnumInfo> getEnumInfos();

    public void setEnumInfos(Map<String, SingleFileEnumInfo> var1);

    public List<SingleFileTableInfo> getTableInfos();

    public void setTableInfos(List<SingleFileTableInfo> var1);

    public List<SingleFileFieldInfo> getZbFields();

    public void setZbFields(List<SingleFileFieldInfo> var1);

    public List<RuleMap> getMapRule();

    public void setMapRule(List<RuleMap> var1);

    public List<RuleMap> getMapRule(RuleKind var1);

    public UnitMapping getMapping();

    public void setMapping(UnitMapping var1);

    public MappingConfig getConfig();

    public void setConfig(MappingConfig var1);

    public void putEnumInfo(String var1, SingleFileEnumInfo var2);

    public boolean isAutoGenerate();
}

