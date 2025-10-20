/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.relate.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public final class SSORelateConst {
    public static final String SSO_RELATE_NAME = "SSORelate";
    public static final String SSO_RELATE_TITLE = "\u5355\u70b9\u8054\u67e5";
    private static final List<Map<String, Object>> RELATE_QUERY_BILL_PARAM_LIST = SSORelateConst.getRelateQueryBillParamMap();
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String NEED_FLAG = "needFlag";
    public static final String RELATE_QUERY_TYPE_BILL = "BILL";
    public static final String RELATE_QUERY_TYPE_BILLLIST = "BILLLIST";
    public static final String RELATE_QUERY_TYPE_CUSTOM_QUERY = "CUSTOM_QUERY";
    public static final String RELATE_URL_TYPE = "URL";

    private SSORelateConst() {
    }

    public static List<Map<String, Object>> getRelateQueryBillParamMap() {
        if (!CollectionUtils.isEmpty(RELATE_QUERY_BILL_PARAM_LIST)) {
            return RELATE_QUERY_BILL_PARAM_LIST;
        }
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put(NAME, "billCode");
        map2.put(TITLE, "\u5355\u636e\u7f16\u53f7");
        map2.put(NEED_FLAG, true);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put(NAME, "verifyCode");
        map3.put(TITLE, "\u9a8c\u8bc1\u7801");
        map3.put(NEED_FLAG, false);
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put(NAME, "pageTitle");
        map4.put(TITLE, "\u754c\u9762\u6807\u9898");
        map4.put(NEED_FLAG, false);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        return list;
    }
}

