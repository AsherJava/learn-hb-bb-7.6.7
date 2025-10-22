/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import java.util.HashMap;
import java.util.Map;

public class BuildUploadCountResultSet {
    public void getUploadCountResult(UploadSumNew uploadSum, int stateIndex, int countIndex, MemoryDataSet<NvwaQueryColumn> executeQuery) {
        int submitNum = 0;
        int uploadNum = 0;
        int returnNum = 0;
        int rejectNum = 0;
        int confirmNum = 0;
        int size = executeQuery.size();
        String state = "";
        block22: for (int k = 0; k < size; ++k) {
            DataRow dataRow = executeQuery.get(k);
            state = dataRow.getString(stateIndex);
            if (state == null) continue;
            switch (state) {
                case "start": {
                    continue block22;
                }
                case "act_submit": 
                case "cus_submit": {
                    ++submitNum;
                    continue block22;
                }
                case "act_return": 
                case "cus_return": {
                    ++returnNum;
                    continue block22;
                }
                case "act_upload": 
                case "cus_upload": 
                case "act_cancel_confirm": {
                    ++uploadNum;
                    continue block22;
                }
                case "act_confirm": 
                case "cus_confirm": {
                    ++confirmNum;
                    continue block22;
                }
                case "act_reject": 
                case "cus_reject": {
                    ++rejectNum;
                }
            }
        }
        uploadSum.setSubmitedNum(submitNum);
        uploadSum.setReturnedNum(returnNum);
        uploadSum.setUploadedNum(uploadNum);
        uploadSum.setConfirmedNum(confirmNum);
        uploadSum.setRejectedNum(rejectNum);
    }

    public void getFormUploadCountResult(int actionIndex, int nodeIndex, int formIndex, MemoryDataSet<NvwaQueryColumn> executeQuery, Map<String, UploadAllFormSumInfo> formToSum) {
        HashMap formToSubmitNumMap = new HashMap();
        int size = executeQuery.size();
        String action = "";
        String formKey = "";
        block20: for (int j = 0; j < size; ++j) {
            DataRow dataRow = executeQuery.get(j);
            action = dataRow.getString(actionIndex);
            formKey = dataRow.getString(formIndex);
            if (action == null) continue;
            switch (action) {
                case "act_submit": 
                case "cus_submit": {
                    if (!formToSum.containsKey(formKey)) continue block20;
                    int submitedNum = formToSum.get(formKey).getSubmitedNum();
                    UploadAllFormSumInfo uploadAllFormSumInfo = formToSum.get(formKey);
                    uploadAllFormSumInfo.setSubmitedNum(submitedNum + 1);
                    continue block20;
                }
                case "act_return": 
                case "cus_return": {
                    if (!formToSum.containsKey(formKey)) continue block20;
                    int returnedNum = formToSum.get(formKey).getReturnedNum();
                    UploadAllFormSumInfo uploadAllFormSumInfo = formToSum.get(formKey);
                    uploadAllFormSumInfo.setReturnedNum(returnedNum + 1);
                    continue block20;
                }
                case "act_upload": 
                case "cus_upload": 
                case "act_cancel_confirm": {
                    if (!formToSum.containsKey(formKey)) continue block20;
                    int uploadedNum = formToSum.get(formKey).getUploadedNum();
                    UploadAllFormSumInfo uploadAllFormSumInfo = formToSum.get(formKey);
                    uploadAllFormSumInfo.setUploadedNum(uploadedNum + 1);
                    continue block20;
                }
                case "act_confirm": 
                case "cus_confirm": {
                    if (!formToSum.containsKey(formKey)) continue block20;
                    int confirmedNum = formToSum.get(formKey).getConfirmedNum();
                    UploadAllFormSumInfo uploadAllFormSumInfo = formToSum.get(formKey);
                    uploadAllFormSumInfo.setConfirmedNum(confirmedNum + 1);
                    continue block20;
                }
                case "act_reject": 
                case "cus_reject": {
                    if (!formToSum.containsKey(formKey)) continue block20;
                    int rejectedNum = formToSum.get(formKey).getRejectedNum();
                    UploadAllFormSumInfo uploadAllFormSumInfo = formToSum.get(formKey);
                    uploadAllFormSumInfo.setRejectedNum(rejectedNum + 1);
                }
            }
        }
    }
}

