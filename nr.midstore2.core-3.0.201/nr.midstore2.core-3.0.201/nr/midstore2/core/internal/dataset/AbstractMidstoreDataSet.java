/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.io.params.output.ExportEntity
 *  com.jiuqi.nr.io.sb.bean.ImportInfo
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.io.service.MzOrgCodeRepairService
 */
package nr.midstore2.core.internal.dataset;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.service.MzOrgCodeRepairService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.dataset.IMidstoreRegionDataSetReader;

public abstract class AbstractMidstoreDataSet
implements IMidstoreDataSet {
    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public List<FileInfo> getAttamentFiles() {
        return null;
    }

    @Override
    public List<ExportEntity> getEntitys() {
        return null;
    }

    @Override
    public Map<String, Set<String>> getUnImport() throws Exception {
        return new HashMap<String, Set<String>>();
    }

    @Override
    public String getCodeTitle(String code) {
        return null;
    }

    @Override
    public int getDbDataCount() {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ArrayList<Object> next() {
        return null;
    }

    @Override
    public void close() {
    }

    @Override
    public DimensionValueSet importDatas(List<Object> row) throws Exception {
        return null;
    }

    @Override
    public ImportInfo commit() throws Exception {
        return null;
    }

    @Override
    public void queryToDataRowReader(IMidstoreRegionDataSetReader reader) {
    }

    public MultistageUnitReplace getMultistageUnitReplace() {
        return null;
    }

    public MzOrgCodeRepairService getMzOrgCodeRepairService() {
        return null;
    }

    public String dataTransfer(FieldDefine fieldDefine, String data) {
        return null;
    }

    public String dwValueRepair(IDataRow dataRow, String data) {
        return null;
    }
}

