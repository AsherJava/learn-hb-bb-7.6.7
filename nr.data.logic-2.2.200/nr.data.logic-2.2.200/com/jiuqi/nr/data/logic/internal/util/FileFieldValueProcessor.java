/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachment.input.FileCalculateParam
 *  com.jiuqi.nr.attachment.service.FileCalculateService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.input.FileCalculateParam;
import com.jiuqi.nr.attachment.service.FileCalculateService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileFieldValueProcessor
implements IFieldValueUpdateProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FileFieldValueProcessor.class);
    private final FileCalculateService fileCalculateService = (FileCalculateService)BeanUtil.getBean(FileCalculateService.class);
    private FormSchemeDefine formSchemeDefine;

    public Object processValue(QueryContext context, FieldDefine fieldDefine, Object value) {
        if (value == null) {
            return null;
        }
        IFmlExecEnvironment env = context.getExeContext().getEnv();
        if (!(env instanceof ReportFmlExecEnvironment)) {
            return value;
        }
        if (fieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE || fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
            ReportFmlExecEnvironment reportFmlExecEnvironment = (ReportFmlExecEnvironment)env;
            FormSchemeDefine formScheme = this.getFormSchemeDefine(reportFmlExecEnvironment);
            if (formScheme == null) {
                return value;
            }
            DimensionValueSet currentMasterKey = context.getCurrentMasterKey();
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(currentMasterKey);
            try {
                DimensionCombination combination = dimensionCombinationBuilder.getCombination();
                FileCalculateParam fileCalculateParam = new FileCalculateParam();
                fileCalculateParam.setFieldKey(fieldDefine.getKey());
                fileCalculateParam.setFormSchemeKey(formScheme.getKey());
                fileCalculateParam.setTaskKey(formScheme.getTaskKey());
                fileCalculateParam.setToDims(combination);
                fileCalculateParam.setFromGroupKey(String.valueOf(value));
                return this.fileCalculateService.copyFileGroup(fileCalculateParam);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return value;
    }

    private FormSchemeDefine getFormSchemeDefine(ReportFmlExecEnvironment env) {
        if (this.formSchemeDefine == null) {
            this.formSchemeDefine = env.getFormSchemeDefine();
        }
        return this.formSchemeDefine;
    }
}

