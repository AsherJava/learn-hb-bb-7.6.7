/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  nr.single.map.data.facade.SingleFileRegionInfo
 */
package nr.single.para.parain.service;

import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.parain.internal.cache.RegionTableList;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IFormFieldDefineImportService {
    public void importRegionFields(TaskImportContext var1, FieldDefs var2, DesignDataRegionDefine var3, DesignFormDefine var4, RepInfo var5, boolean var6, boolean var7, boolean var8, SingleFileRegionInfo var9, RegionTableList var10, List<ZBInfo> var11, CompareDataFormDTO var12, String var13) throws Exception;

    public void updateFormInfoCacheToServer(TaskImportContext var1, boolean var2, boolean var3, boolean var4, boolean var5) throws Exception;

    public DataTableGatherType getTableGatherType(FieldDefs var1, boolean var2);

    public Grid2Data getFormGrid(TaskImportContext var1, DesignFormDefine var2);

    public int getMaxFieldCountInTable();

    public int getMaxTableFieldSize();
}

