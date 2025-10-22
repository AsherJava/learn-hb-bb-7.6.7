/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 */
package nr.single.para.parain.service;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.parain.internal.cache.RegionTableCache;
import nr.single.para.parain.internal.cache.RegionTableList;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IFormRegionDefineImportService {
    public void importFormRegions(TaskImportContext var1, RepInfo var2, DesignFormDefine var3, boolean var4, boolean var5, SingleFileTableInfo var6, DesignDataGroup var7, CompareDataFormDTO var8) throws Exception;

    public void updateFormInfoCacheToServer(TaskImportContext var1, boolean var2, boolean var3, boolean var4, boolean var5) throws Exception;

    public RegionTableCache getRegonTableCache(TaskImportContext var1, FieldDefs var2, DesignDataRegionDefine var3, DesignFormDefine var4, RepInfo var5, boolean var6, boolean var7, boolean var8, SingleFileRegionInfo var9, RegionTableList var10, String var11, boolean var12) throws Exception;

    public RegionTableCache getRegonTableCache(TaskImportContext var1, FieldDefs var2, DesignDataRegionDefine var3, DesignFormDefine var4, RepInfo var5, boolean var6, boolean var7, boolean var8, SingleFileRegionInfo var9, RegionTableList var10, String var11, boolean var12, CompareTableType var13) throws Exception;
}

