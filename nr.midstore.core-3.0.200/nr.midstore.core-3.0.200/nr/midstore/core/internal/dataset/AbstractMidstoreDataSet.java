/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.io.params.output.ExportEntity
 */
package nr.midstore.core.internal.dataset;

import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.io.params.output.ExportEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore.core.dataset.IMidstoreDataSet;

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
}

