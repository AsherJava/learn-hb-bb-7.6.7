/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.nr.data.logic.facade.param.input.GetTableContext;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.spi.ICheckResultTableProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SplitCheckTableHelper {
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    @Autowired(required=false)
    private ICheckResultTableProvider checkResultTableProvider;
    @Autowired
    private FormulaParseUtil formulaParseUtil;

    public String getSplitCKRTableName(FormSchemeDefine formSchemeDefine) {
        String tableName = CheckTableNameUtil.getCKRTableName(formSchemeDefine.getFormSchemeCode());
        return this.transSplitTableName(formSchemeDefine, tableName);
    }

    public String getCKRTableName(FormSchemeDefine formSchemeDefine, String batchId) {
        String tableName = this.getSplitCKRTableName(formSchemeDefine);
        if (null != this.checkResultTableProvider) {
            GetTableContext getTableContext = new GetTableContext();
            getTableContext.setOriginalTableName(tableName);
            getTableContext.setBatchId(batchId);
            getTableContext.setFormSchemeKey(formSchemeDefine.getKey());
            String ckrTableName = this.checkResultTableProvider.getCKRTableName(getTableContext);
            if (StringUtils.isNotEmpty((String)ckrTableName)) {
                return ckrTableName;
            }
        }
        return tableName;
    }

    public String getSplitAllCKRTableName(FormSchemeDefine formSchemeDefine) {
        String tableName = CheckTableNameUtil.getAllCKRTableName(formSchemeDefine.getFormSchemeCode());
        return this.transSplitTableName(formSchemeDefine, tableName);
    }

    public String getSplitCKDTableName(FormSchemeDefine formSchemeDefine) {
        String tableName = CheckTableNameUtil.getCKDTableName(formSchemeDefine.getFormSchemeCode());
        return this.transSplitTableName(formSchemeDefine, tableName);
    }

    public String getSplitCKSTableName(FormSchemeDefine formSchemeDefine) {
        String tableName = CheckTableNameUtil.getCKSTableName(formSchemeDefine.getFormSchemeCode());
        return this.transSplitTableName(formSchemeDefine, tableName);
    }

    public String getSplitCKSSubTableName(FormSchemeDefine formSchemeDefine) {
        String tableName = CheckTableNameUtil.getCKSSubTableName(formSchemeDefine.getFormSchemeCode());
        return this.transSplitTableName(formSchemeDefine, tableName);
    }

    private String transSplitTableName(FormSchemeDefine formSchemeDefine, String tableName) {
        if (null != this.splitTableHelper) {
            ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(formSchemeDefine.getKey());
            tableName = this.splitTableHelper.getCurrentSplitTableName(executorContext, tableName);
        }
        return tableName;
    }

    @NonNull
    public Map<String, String> getSplitKeyValue(FormSchemeDefine formSchemeDefine, String splitTableName) {
        Map splitKeyValue = null;
        if (null != this.splitTableHelper) {
            ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(formSchemeDefine.getKey());
            splitKeyValue = this.splitTableHelper.getSumSchemeKey(executorContext, splitTableName);
        }
        return splitKeyValue == null ? Collections.emptyMap() : splitKeyValue;
    }
}

