/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.common.UploadAllFormSumInfo
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.dataentry.bean.QueryUploadStateInfo;
import com.jiuqi.nr.dataentry.paramInfo.ActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;
import com.jiuqi.nr.dataentry.paramInfo.UploadActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.UploadSumInfo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface UploadOverviewConverter {
    public List<UploadAllFormSumInfo> batchQueryState(BatchQueryUpload var1);

    public List<UploadSumInfo> batchUploadState(BatchQueryUpload var1);

    public List<ActionInfo> batchWorkFlow(QueryUploadStateInfo var1);

    public List<UploadActionInfo> batchUploadActions(@Valid @RequestBody BatchQueryUpload var1);

    public void exportUploadState(ExportExcelState var1, HttpServletResponse var2, HttpServletRequest var3);

    public void exportUploadState2(ExportExcelState var1, HttpServletResponse var2, HttpServletRequest var3);
}

