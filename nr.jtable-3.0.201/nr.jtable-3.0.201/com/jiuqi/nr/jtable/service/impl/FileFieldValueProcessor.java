/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.jtable.params.base.IsNewAttachmentInfo;
import com.jiuqi.nr.jtable.service.IJtableFileService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileFieldValueProcessor
implements IFieldValueUpdateProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FileFieldValueProcessor.class);
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IJtableFileService jtableFileService;

    public FileFieldValueProcessor(IDataDefinitionRuntimeController dataDefinitionRuntimeController, IJtableFileService jtableFileService) {
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.jtableFileService = jtableFileService;
    }

    public Object processValue(QueryContext context, FieldDefine fieldDefine, Object value) {
        NpContext npContext = NpContextHolder.getContext();
        String ctxInfoKey = "IS_NEW_ATTACHMENT";
        Object val = npContext.getDefaultExtension().get(ctxInfoKey);
        if (null != val && val instanceof IsNewAttachmentInfo && !((IsNewAttachmentInfo)val).isNewAttachment()) {
            try {
                if (fieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE || fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
                    String newGroupKey = UUID.randomUUID().toString();
                    this.jtableFileService.copyFileGroup(fieldDefine, value.toString(), newGroupKey);
                    return newGroupKey;
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return value;
    }
}

