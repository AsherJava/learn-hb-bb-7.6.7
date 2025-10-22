/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.data.access.api.IStateDataPublishService
 *  com.jiuqi.nr.data.access.api.param.PublishParam
 *  com.jiuqi.nr.data.access.api.response.DataPublishReturnInfo
 *  com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.access.api.IStateDataPublishService;
import com.jiuqi.nr.data.access.api.param.PublishParam;
import com.jiuqi.nr.data.access.api.response.DataPublishReturnInfo;
import com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.bean.DataPublishParam;
import com.jiuqi.nr.dataentry.exception.DataEntryException;
import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPublishServiceImpl
implements IDataPublishService {
    private static final Logger logger = LoggerFactory.getLogger(DataPublishServiceImpl.class);
    @Autowired
    private IStateDataPublishService stateDataPublishService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public boolean isEnableDataPublis(String taskKey) {
        return this.stateDataPublishService.isEnableDataPublis(taskKey);
    }

    @Override
    public Boolean isDataPublished(DataPublishParam param) {
        JtableContext jtableContext = param.getContext();
        PublishParam publishParam = new PublishParam();
        publishParam.setFormKeys(param.getFormKeys());
        publishParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        publishParam.setMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey()));
        return this.stateDataPublishService.isDataPublished(publishParam);
    }

    @Override
    public List<DataPublishReturnInfo> dataPublish(DataPublishParam param, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext jtableContext = param.getContext();
        if (jtableContext.getFormSchemeKey() == null) {
            throw new DataEntryException("\u6ca1\u6709\u62a5\u8868\u65b9\u6848");
        }
        if (jtableContext.getDimensionSet() == null) {
            throw new DataEntryException("\u6ca1\u6709\u7ef4\u5ea6");
        }
        if (jtableContext.getFormKey() == null && param.getFormKeys() == null) {
            throw new DataEntryException("\u6ca1\u6709\u62a5\u8868");
        }
        if (param.getFormKeys().isEmpty()) {
            param.setFormKeys(this.runTimeViewController.queryAllFormKeysByFormScheme(jtableContext.getFormSchemeKey()));
        }
        PublishParam publishParam = new PublishParam();
        publishParam.setFormKeys(param.getFormKeys());
        publishParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        publishParam.setPublish(param.isPublish());
        publishParam.setMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey()));
        return this.stateDataPublishService.dataPublish(publishParam, asyncTaskMonitor);
    }

    @Override
    public List<String> getPublishedFormKeys(DataPublishParam param) {
        JtableContext context = param.getContext();
        PublishParam publishParam = new PublishParam();
        publishParam.setFormKeys(param.getFormKeys());
        publishParam.setFormSchemeKey(context.getFormSchemeKey());
        publishParam.setMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)context.getDimensionSet(), (String)context.getFormSchemeKey()));
        return this.stateDataPublishService.getPublishedFormKeys(publishParam);
    }

    @Override
    public List<DataPublishBatchReadWriteResult> getBatchResult(JtableContext context) {
        PublishParam publishParam = new PublishParam();
        String[] formKeys = context.getFormKey().split(";");
        publishParam.setFormKeys(Arrays.asList(formKeys));
        publishParam.setFormSchemeKey(context.getFormSchemeKey());
        publishParam.setMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)context.getDimensionSet(), (String)context.getFormSchemeKey()));
        return this.stateDataPublishService.getBatchResult(publishParam);
    }
}

