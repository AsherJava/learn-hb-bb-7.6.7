/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.data.bean;

import java.util.HashMap;
import java.util.Map;

public class MidstorePostCancledResultParam {
    private static final long serialVersionUID = 1L;
    private static final String CANCLED_RESULT_KEY = "midstoreDataPostCanceled";
    public static final String CANCLED_RESULT_TITLE = "\u4e2d\u95f4\u5e93\u6570\u636e\u63d0\u4f9b \u5df2\u53d6\u6d88";
    private static final String SUCCESS_TABLE_COUNT = "tableNum";
    private final Map<String, String> param = new HashMap<String, String>();

    public MidstorePostCancledResultParam(String formNum) {
        this.param.put(SUCCESS_TABLE_COUNT, formNum);
    }

    public String getCode() {
        return CANCLED_RESULT_KEY;
    }

    public Map<String, String> getParam() {
        return this.param;
    }
}

