/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.util;

import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.intern.AbstractFormState;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class FormUploadStateTool
extends AbstractFormState {
    private static FormUploadStateTool tool;

    public static FormUploadStateTool getInstance() {
        if (null == tool) {
            tool = new FormUploadStateTool();
        }
        return tool;
    }

    public ReadWriteAccessDesc writeable(Object paramsVO, String orgId, String formId) {
        DimensionParamsVO newParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, newParamsVO);
        newParamsVO.setOrgId(orgId);
        return this.writeable(newParamsVO, formId);
    }

    public List<ReadWriteAccessDesc> writeable(Object paramsVO, String orgId, List<String> formIds) {
        DimensionParamsVO newParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, newParamsVO);
        newParamsVO.setOrgId(orgId);
        return this.writeable(newParamsVO, formIds);
    }

    public ReadWriteAccessDesc writeable(DimensionParamsVO queryParamsVO, String formId) {
        ReadWriteAccessDesc writeable = UploadStateTool.getInstance().writeable(queryParamsVO);
        if (Boolean.FALSE.equals(writeable.getAble())) {
            return writeable;
        }
        boolean isFormLocked = this.isFormLocked(queryParamsVO, queryParamsVO.getOrgId(), formId);
        if (isFormLocked) {
            return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u62a5\u8868\u5df2\u9501\u5b9a\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
        }
        UploadState uploadState = this.queryUploadState(queryParamsVO, queryParamsVO.getOrgId(), formId);
        return UploadStateTool.getInstance().writeable(uploadState);
    }

    public List<ReadWriteAccessDesc> writeable(DimensionParamsVO queryParamsVO, List<String> formIds) {
        ReadWriteAccessDesc orgWriteable = UploadStateTool.getInstance().writeable(queryParamsVO);
        if (Boolean.FALSE.equals(orgWriteable.getAble())) {
            ArrayList<ReadWriteAccessDesc> writeableList = new ArrayList<ReadWriteAccessDesc>();
            formIds.forEach(item -> writeableList.add(orgWriteable));
            return writeableList;
        }
        List<UploadState> uploadStateList = this.queryUploadState(queryParamsVO, queryParamsVO.getOrgId(), formIds);
        List<ReadWriteAccessDesc> writeableList = uploadStateList.stream().map(uploadState -> UploadStateTool.getInstance().writeable((UploadState)uploadState)).collect(Collectors.toList());
        for (int i = 0; i < writeableList.size(); ++i) {
            ReadWriteAccessDesc writeAccessDesc = writeableList.get(i);
            if (!writeAccessDesc.getAble().booleanValue()) continue;
            boolean isFormLocked = this.isFormLocked(queryParamsVO, queryParamsVO.getOrgId(), formIds.get(i));
            writeAccessDesc.setAble(Boolean.valueOf(!isFormLocked));
        }
        return writeableList;
    }

    public List<ReadWriteAccessDesc> writeableExcludeFormLocked(DimensionParamsVO queryParamsVO, List<String> formIds) {
        ReadWriteAccessDesc orgWriteable = UploadStateTool.getInstance().writeable(queryParamsVO);
        if (Boolean.FALSE.equals(orgWriteable.getAble())) {
            ArrayList<ReadWriteAccessDesc> writeableList = new ArrayList<ReadWriteAccessDesc>();
            formIds.forEach(item -> writeableList.add(orgWriteable));
            return writeableList;
        }
        List<UploadState> uploadStateList = this.queryUploadState(queryParamsVO, queryParamsVO.getOrgId(), formIds);
        List<ReadWriteAccessDesc> writeableList = uploadStateList.stream().map(uploadState -> UploadStateTool.getInstance().writeable((UploadState)uploadState)).collect(Collectors.toList());
        return writeableList;
    }

    public ReadWriteAccessDesc writeableByOnlyFormUploadState(DimensionParamsVO queryParamsVO, String formId) {
        UploadState uploadState = this.queryUploadState(queryParamsVO, queryParamsVO.getOrgId(), formId);
        ReadWriteAccessDesc writeAccessDesc = UploadStateTool.getInstance().writeable(uploadState);
        return writeAccessDesc;
    }

    public ReadWriteAccessDesc writeableByOnlyFormLockState(DimensionParamsVO queryParamsVO, String formId) {
        boolean isFormLocked = this.isFormLocked(queryParamsVO, queryParamsVO.getOrgId(), formId);
        ReadWriteAccessDesc writeAccessDesc = new ReadWriteAccessDesc(Boolean.valueOf(!isFormLocked), "\u62a5\u8868\u5df2\u9501\u5b9a\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
        return writeAccessDesc;
    }

    @Override
    public UploadState queryUploadState(DimensionParamsVO param, String orgId, String formId) {
        return super.queryUploadState(param, orgId, formId);
    }

    public boolean isUploaded(DimensionParamsVO param, String orgId, String formId) {
        UploadState uploadState = super.queryUploadState(param, orgId, formId);
        return UploadState.UPLOADED.equals((Object)uploadState);
    }

    @Override
    public boolean isFormLocked(DimensionParamsVO param, String orgId, String formId) {
        return super.isFormLocked(param, orgId, formId);
    }

    @Override
    public boolean isFormLocked(DataEntryContext dataEntryContext, String formId) {
        return super.isFormLocked(dataEntryContext, formId);
    }

    @Override
    public String filterLockedForm(Collection<FormDefine> formDefines, String taskId, String schemeId, Map<String, DimensionValue> dimensionSetMap) {
        return super.filterLockedForm(formDefines, taskId, schemeId, dimensionSetMap);
    }
}

