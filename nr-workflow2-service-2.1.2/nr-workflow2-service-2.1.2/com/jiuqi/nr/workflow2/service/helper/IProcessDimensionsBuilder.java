/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import java.util.Collection;

public interface IProcessDimensionsBuilder {
    public IBusinessKey buildBusinessKey(ProcessOneRunPara var1);

    public IBusinessKey buildBusinessKey(ProcessOneExecutePara var1);

    public IBusinessKeyCollection buildBusinessKeyCollection(ProcessBatchRunPara var1);

    public IBusinessKeyCollection buildBusinessKeyCollection(ProcessBatchExecutePara var1);

    public IBusinessKeyCollection buildUnitDimensionCollection(ProcessBatchRunPara var1);

    public DimensionCollection buildDimensionCollection(ProcessBatchRunPara var1);

    public IDimensionObjectMapping processDimToFormDefinesMap(FormSchemeDefine var1, DimensionCollection var2, Collection<String> var3);

    public IDimensionObjectMapping processDimToFormConditionMap(FormSchemeDefine var1, DimensionCollection var2, Collection<String> var3);

    public IDimensionObjectMapping processDimToGroupDefinesMap(FormSchemeDefine var1, DimensionCollection var2, Collection<String> var3);

    public IDimensionObjectMapping processDimToGroupConditionMap(FormSchemeDefine var1, DimensionCollection var2, Collection<String> var3);
}

