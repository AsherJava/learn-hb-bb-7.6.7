/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.io.params.output.ExportEntity
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.bean.ImportInfo
 */
package nr.midstore.core.dataset;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore.core.dataset.MidstoreTableData;

public interface IMidstoreDataSet
extends Iterator<ArrayList<Object>> {
    public int getTotalCount();

    public MidstoreTableData getRegionData();

    public boolean isFloatRegion();

    public List<ExportFieldDefine> getFieldDataList();

    public List<FileInfo> getAttamentFiles();

    public List<ExportEntity> getEntitys();

    public DimensionValueSet importDatas(List<Object> var1) throws Exception;

    public ImportInfo commit() throws Exception;

    public List<FieldDefine> getBizFieldDefList();

    public FieldDefine getUnitFieldDefine() throws Exception;

    public Map<String, Set<String>> getUnImport() throws Exception;

    public String getCodeTitle(String var1);

    public int getDbDataCount();

    public void close();
}

