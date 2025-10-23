/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.dataextract;

import com.jiuqi.nvwa.dataextract.IDataExtractRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExtractRequestManager {
    private static final DataExtractRequestManager instance = new DataExtractRequestManager();
    private Map<String, IDataExtractRequest> requestMap = new HashMap<String, IDataExtractRequest>();

    public static final DataExtractRequestManager getInstance() {
        return instance;
    }

    public void regRequest(IDataExtractRequest request) {
        if (request == null || request.getType() == null) {
            throw new NullPointerException();
        }
        this.requestMap.put(request.getType().toUpperCase(), request);
    }

    public IDataExtractRequest findRequest(String type) {
        if (type == null) {
            throw new NullPointerException();
        }
        return this.requestMap.get(type.toUpperCase());
    }

    public List<IDataExtractRequest> getAllRequests() {
        return new ArrayList<IDataExtractRequest>(this.requestMap.values());
    }
}

