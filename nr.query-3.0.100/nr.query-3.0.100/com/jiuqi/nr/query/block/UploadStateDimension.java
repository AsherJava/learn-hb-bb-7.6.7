/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.bpm.de.dataflow.bean.UploadStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.query.block;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bpm.de.dataflow.bean.UploadStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.query.block.QuerySelectItem;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UploadStateDimension {
    public String task;
    public String scheme;
    public String tableName;
    public String fieldName = "PREVEVENT";

    public void setTask(String task) {
        this.task = task;
    }

    public String getTask() {
        return this.task;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    private List<UploadStateBean> getWorkFlowStatus(UploadState state, String formSchemeKey) {
        IDataentryFlowService dataentryFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        List uploadStateBeans = dataentryFlowService.queryAllactionCode(state, formSchemeKey);
        return uploadStateBeans;
    }

    public String getFilterFormula(List<QuerySelectItem> items, String formSchemeKey) {
        if (StringUtils.isEmpty((String)this.tableName) || StringUtils.isEmpty((String)this.fieldName)) {
            return null;
        }
        String value = "";
        value = null;
        for (QuerySelectItem item : items) {
            List<UploadStateBean> uploadStateBeans = this.getWorkFlowStatus(UploadState.valueOf((String)item.getCode()), formSchemeKey);
            if (uploadStateBeans.size() == 0) continue;
            if (uploadStateBeans.size() == 1) {
                value = "'" + uploadStateBeans.get(0).getActionCode() + "'";
                continue;
            }
            for (UploadStateBean state : uploadStateBeans) {
                if (value == null) {
                    value = "'" + state.getActionCode() + "'";
                    continue;
                }
                value = value + ",'" + state.getActionCode() + "'";
            }
        }
        if (value == null) {
            return null;
        }
        value = " in {" + value + "}";
        String filter = this.tableName + "[" + this.fieldName + "]" + value;
        return filter;
    }
}

