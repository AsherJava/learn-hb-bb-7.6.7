/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.SurveyProblemHandleVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseSurveyProblemHandleVO {
    private String style;
    private String message;
    private boolean success;
    private Map<String, List<SurveyProblemHandleVO>> tableCodes;

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void addItem(String tableCode, SurveyProblemHandleVO item) {
        List<SurveyProblemHandleVO> items;
        this.success = false;
        this.message = "\u64cd\u4f5c\u5931\u8d25";
        if (this.tableCodes == null) {
            this.tableCodes = new HashMap<String, List<SurveyProblemHandleVO>>();
        }
        if (null == (items = this.tableCodes.get(tableCode))) {
            items = new ArrayList<SurveyProblemHandleVO>();
            this.tableCodes.put(tableCode, items);
        }
        items.add(item);
    }

    public Map<String, List<SurveyProblemHandleVO>> getTableCodes() {
        return this.tableCodes;
    }

    public void setTableCodes(Map<String, List<SurveyProblemHandleVO>> tableCodes) {
        this.tableCodes = tableCodes;
    }
}

