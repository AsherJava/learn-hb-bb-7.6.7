/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.file.FileInfo
 */
package com.jiuqi.nr.io.dataset;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.input.ImportErrorData;
import com.jiuqi.nr.io.params.input.ImportResult;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRegionDataSet
extends Iterator<ArrayList<Object>> {
    public int getTotalCount();

    public RegionData getRegionData();

    public boolean isFloatRegion();

    public List<ExportFieldDefine> getFieldDataList();

    public List<FileInfo> getAttamentFiles();

    public List<ExportEntity> getEntitys();

    public DimensionValueSet importDatas(List<Object> var1) throws Exception;

    public ImportInfo commit() throws Exception;

    public List<FieldDefine> getBizFieldDefList();

    public FieldDefine getUnitFieldDefine() throws Exception;

    public Map<String, Set<String>> getUnImport() throws Exception;

    public List<ImportErrorData> getImportErrorInfos();

    public List<ImportErrorData> getImportAmendInfos();

    public ImportResult getImportResult();

    public String getCodeTitle(String var1);

    public int getDbDataCount();

    public void close();

    public void queryToDataRowReader(IRegionDataSetReader var1);
}

