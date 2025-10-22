/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportEntity;
import java.util.List;

public interface ExportService {
    public List<IRegionDataSet> getRegionsDatas(TableContext var1);

    public List<ExportEntity> getEntitys(TableContext var1);
}

