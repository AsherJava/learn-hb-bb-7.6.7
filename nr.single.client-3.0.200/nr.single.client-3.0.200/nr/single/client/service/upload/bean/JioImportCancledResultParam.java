/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.service.upload.bean;

import java.util.HashMap;
import java.util.Map;

public class JioImportCancledResultParam {
    private static final long serialVersionUID = 1L;
    private static final String CANCLED_RESULT_KEY = "jioDataImportCanceled";
    public static final String CANCLED_RESULT_TITLE = "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88";
    private static final String SUCCESS_FORM_COUNT = "formNum";
    private final Map<String, String> param = new HashMap<String, String>();

    public JioImportCancledResultParam(String formNum) {
        this.param.put(SUCCESS_FORM_COUNT, formNum);
    }

    public String getCode() {
        return CANCLED_RESULT_KEY;
    }

    public Map<String, String> getParam() {
        return this.param;
    }
}

