/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 */
package com.jiuqi.nr.io.api;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.io.params.input.UnitCountQueryParam;
import com.jiuqi.nr.io.params.input.UnitQueryParam;
import com.jiuqi.nr.io.tsd.dto.AnalysisParam;
import com.jiuqi.nr.io.tsd.dto.AnalysisRes;
import com.jiuqi.nr.io.tsd.dto.EParam;
import com.jiuqi.nr.io.tsd.dto.IParam;
import com.jiuqi.nr.io.tsd.dto.IResult;
import java.util.List;
import java.util.Map;

public interface ITaskDataService {
    public void exportTaskData(EParam var1, FileWriter var2, IProgressMonitor var3);

    public Result<AnalysisRes> preAnalysis(AnalysisParam var1, FileFinder var2);

    public List<IEntityDataDTO> getUnitByParam(UnitQueryParam var1);

    public Map<EntityDataType, Integer> countUnitByParam(UnitCountQueryParam var1);

    public IResult importTaskData(IParam var1, FileFinder var2, IProgressMonitor var3);
}

