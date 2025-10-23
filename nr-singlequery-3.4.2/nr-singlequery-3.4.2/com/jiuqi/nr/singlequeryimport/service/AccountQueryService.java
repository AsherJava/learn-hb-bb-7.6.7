/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.service;

import com.jiuqi.nr.singlequeryimport.bean.ModalColWarningInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public interface AccountQueryService {
    public QueryResult search(List<String> var1, String var2, Map<String, Integer> var3, String var4, QueryConfigInfo var5, JSONObject var6, List<Map<String, String>> var7, Map<String, List<Integer>> var8, List<ModalColWarningInfo> var9, List<ModalColWarningInfo> var10) throws Exception;
}

