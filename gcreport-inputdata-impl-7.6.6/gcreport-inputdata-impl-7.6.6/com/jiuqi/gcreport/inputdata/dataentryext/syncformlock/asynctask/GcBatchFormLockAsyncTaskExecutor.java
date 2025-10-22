/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.api.IStateFormLockService
 *  com.jiuqi.nr.data.access.api.param.LockParam
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.asynctask;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.params.GcBatchFormLockParam;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBatchFormLockAsyncTaskExecutor
implements NpAsyncTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GcBatchFormLockAsyncTaskExecutor.class);
    @Autowired
    IStateFormLockService formLockService;
    @Autowired
    DimCollectionBuildUtil dimCollectionBuildUtil;

    public void execute(Object args, AsyncTaskMonitor monitor) {
        try {
            if (Objects.isNull(args)) {
                return;
            }
            if (args instanceof String) {
                GcBatchFormLockParam gcBatchFormLockParam = (GcBatchFormLockParam)JsonUtils.readValue((String)((String)args), GcBatchFormLockParam.class);
                LockParam lockParam = this.getFormLockParam(gcBatchFormLockParam);
                this.formLockService.batchLockForm(lockParam, monitor);
            }
        }
        catch (Exception e) {
            logger.error("\u9501\u5b9a\u89e3\u9501\u540c\u6b65\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.ASYNCTASK_GC_BATCH_FORM_LOCK.getName();
    }

    private LockParam getFormLockParam(GcBatchFormLockParam gcBatchFormLockParam) {
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(gcBatchFormLockParam.getFormKeys().stream().collect(Collectors.toList()));
        lockParam.setTaskKey(gcBatchFormLockParam.getTaskKey());
        lockParam.setFormSchemeKey(gcBatchFormLockParam.getFormSchemeKey());
        lockParam.setLock(gcBatchFormLockParam.isLock());
        lockParam.setMasterKeys(this.getDimensionCollection(gcBatchFormLockParam.getDimensionValueMaps(), gcBatchFormLockParam.getDimensionValueMap(), gcBatchFormLockParam.getFormSchemeKey()));
        lockParam.setIgnoreAuth(true);
        lockParam.setForceUnLock(gcBatchFormLockParam.isForceUnLock());
        return lockParam;
    }

    private DimensionCollection getDimensionCollection(List<Map<String, DimensionValue>> dimensionValueMaps, Map<String, DimensionValue> dimensionValueMap, String formSchemeKey) {
        HashSet<String> mdCodes = new HashSet<String>();
        DimensionValueSet sourceLockDim = DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap);
        for (Map<String, DimensionValue> dimension : dimensionValueMaps) {
            String mdCode = dimension.get("MD_ORG").getValue();
            if (mdCode == null) continue;
            mdCodes.add(mdCode.toString());
        }
        if (mdCodes.size() > 0) {
            sourceLockDim.setValue("MD_ORG", (Object)CollectionUtils.toString((Object[])mdCodes.toArray(), (String)";"));
        }
        return this.dimCollectionBuildUtil.buildDimensionCollection(sourceLockDim, formSchemeKey);
    }
}

