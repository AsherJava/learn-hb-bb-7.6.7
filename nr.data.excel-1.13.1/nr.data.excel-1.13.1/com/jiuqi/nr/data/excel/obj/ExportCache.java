/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle
 *  com.jiuqi.nr.datacrud.MultiDimensionalDataSet
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.data.excel.export.ExcelPrintSetup;
import com.jiuqi.nr.data.excel.export.fml.Fml;
import com.jiuqi.nr.data.excel.extend.ISheetNameProvider;
import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.data.excel.obj.ExcelExportEnv;
import com.jiuqi.nr.data.excel.obj.ExpFormFolding;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.obj.FilterSort;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface ExportCache {
    public FormDefine getFormByKey(String var1);

    public Grid2Data getFormStyle(String var1);

    public DataLinkDefine getDataLink(String var1);

    public FormSchemeDefine getFormScheme(String var1);

    public List<String> getFieldKeysInRegion(String var1);

    public FieldDefine queryFieldDefine(String var1);

    public FieldDefine queryMasterFieldDefine(String var1);

    public TableDefine queryTableDefine(String var1);

    public List<DataLinkDefine> getLinksInFormByField(String var1, String var2);

    public RegionSettingDefine getRegionSetting(String var1);

    public List<DataLinkDefine> getAllLinksInRegion(String var1);

    public List<DataRegionDefine> getSortedRegionsByForm(String var1);

    public List<RegionTabSettingDefine> getTabsByRegion(String var1);

    @Nullable
    public MultiDimensionalDataSet getMultiDimensionalDataSet(String var1, SheetInfo var2);

    public List<IEntityRefer> getEntityRefer(String var1);

    public TaskDefine getTaskDefine(String var1);

    public Map<String, List<String>> getEnumDataMap();

    public IEntityRow getEntityData(String var1, String var2);

    public Map<String, List<ConditionalStyle>> link2ConditionalStyle();

    public List<ConditionalStyle> getConditionalStyleByForm(IConditionalStyleController var1, String var2);

    public Map<String, Set<DimensionValueSet>> unconditionalStyleDims();

    public boolean autoFillIsNullTable();

    public ExcelExportEnv getExcelExportEnv(DimensionCombination var1);

    public List<String> getFileGroupKeys();

    @NonNull
    public DataValueFormatter getDataValueFormatter(String var1);

    public IEntityDefine getEntityDefine(String var1);

    public String getZeroShow();

    public String getDataSnapshotId();

    public FormSchemeDefine getCurFormScheme();

    public ExportOps getExportOps();

    public FilterSort getFilterSort(DimensionCombination var1);

    public boolean isGridDataFormatted();

    public ReportFmlExecEnvironment getFmlExecEnv(IRunTimeViewController var1, IDataDefinitionRuntimeController var2, IEntityViewRunTimeController var3);

    public List<ExpFormFolding> getExpFormFoldingOps(String var1);

    public ExcelPrintSetup getExcelPrintSetup(String var1, String var2);

    public boolean isMeasure();

    @Nullable
    public IDataValueBalanceActuator getBalanceActuator(String var1);

    public Integer getMeasureDecimal(String var1);

    public void initCellStyleCache(Grid2Data var1);

    public GridCellStyleData getCellStyle(int var1, int var2);

    @NonNull
    public List<Fml> getParsedFormulasByForm(String var1);

    public boolean expFml();

    @NonNull
    public Map<String, CustomGridCellStyle> getCustomGridCellStyle(DimensionCombination var1, String var2);

    public void initBatchQueryDims(FormSchemeDefine var1, List<FormDefine> var2, DimensionCollection var3);

    public ISheetNameProvider getSheetNameProvider();
}

