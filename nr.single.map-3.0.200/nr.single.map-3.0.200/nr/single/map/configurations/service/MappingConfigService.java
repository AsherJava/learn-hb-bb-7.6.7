/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package nr.single.map.configurations.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.bean.EntityVO;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.vo.EntitySaveResult;
import nr.single.map.configurations.vo.EnumItemMappingVO;
import nr.single.map.configurations.vo.EnumMappingVO;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;

public interface MappingConfigService {
    public List<SingleConfigInfo> getAllMappingInTask(String var1);

    public ISingleMappingConfig getConfigByKey(String var1);

    public List<SingleConfigInfo> getAllMappingInReport(String var1);

    public List<SingleConfigInfo> getAllMapping();

    public List<SingleConfigInfo> getConfigInSingleTask(String var1);

    public SingleConfigInfo getMappingByKey(String var1);

    public List<RuleMap> getRuleMapByConfig(String var1);

    public Map<String, Object> getMappingConfig(String var1);

    public UnitMapping getEntityConfig(String var1);

    public List<SingleFileFieldInfo> getZbConfig(String var1);

    public List<SingleFileFieldInfo> getZbConfigByForm(String var1, String var2);

    public List<SingleFileFormulaItem> getFormulaConfig(String var1);

    public List<SingleFileFormulaItem> getFormulaConfig(String var1, String var2);

    public List<SingleFileFormulaItem> getFormulaConfig(String var1, String var2, String var3);

    public String getEntityIdByTask(String var1, String var2);

    public SingleConfigInfo copyMappingConfig(String var1);

    public void deleteMappingConfig(String var1);

    public EntitySaveResult saveEntityConfig(String var1, UnitMapping var2, List<RuleMap> var3);

    public void saveEntityUnitCustomMapping(String var1, List<UnitCustomMapping> var2);

    public void saveZbConfig(String var1, String var2, List<SingleFileFieldInfoImpl> var3);

    public void saveFormulaConfig(String var1, String var2, List<SingleFileFormulaItemImpl> var3);

    public void saveMappingConfig(String var1, MappingConfig var2);

    public void updateMappingName(String var1, String var2);

    public String buildConfigName(List<SingleConfigInfo> var1, String var2, boolean var3);

    public void cleanEntityConfig(String var1);

    public void cleanRuleConfig(String var1);

    public void cleanZbConfig(String var1, String var2);

    public void cleanPeriodConfig(String var1);

    public void cleanFormula(String var1, String var2, String var3);

    public void changeOrder(String var1, boolean var2);

    public List<EntityVO> getSeletedEntities(String var1) throws Exception;

    public EnumMappingVO getEnumMapping(String var1);

    public EnumItemMappingVO getEnumItemMapping(String var1, String var2);

    public void saveEnumMapping(EnumMappingVO var1);

    public void saveEnumItemMapping(EnumItemMappingVO var1);

    public EnumItemMappingVO quickMatch(String var1, String var2, String var3);

    public List<IEntityRow> listEntityRows(String var1, DimensionValueSet var2, String var3) throws Exception;

    public List<IEntityRow> listEntityRows(String var1) throws Exception;

    public String queryPeriod(String var1);

    public List<EntityVO> queryEntityAttribute(String var1);
}

