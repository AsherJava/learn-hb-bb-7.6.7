/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.CommonUtil
 *  com.jiuqi.budget.common.utils.MapSizeUtil
 */
package com.jiuqi.budget.components;

import com.jiuqi.budget.common.utils.CommonUtil;
import com.jiuqi.budget.common.utils.MapSizeUtil;
import com.jiuqi.budget.components.SysDim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class LinkId
implements Iterable<Map.Entry<String, String>> {
    private static final String NONE_STR = "NONE";
    private static final LinkId NONE = new LinkId("NONE");
    private Map<String, String> dimValMap;
    private String id;

    public LinkId(Map<String, String> dimValMap) {
        this.dimValMap = dimValMap;
    }

    public LinkId(String id) {
        this.id = id;
    }

    public LinkId() {
        this(NONE_STR);
    }

    public String getDimVal(String dimCode) {
        this.checkDimValMap();
        return this.dimValMap.get(dimCode);
    }

    public void setDimVal(String dimCode, String dimVal) {
        this.checkDimValMap();
        this.dimValMap.put(dimCode, dimVal);
        this.id = null;
    }

    private void checkDimValMap() {
        if (this.dimValMap != null) {
            return;
        }
        if (NONE_STR.equals(this.id)) {
            this.dimValMap = new HashMap<String, String>();
        } else {
            ArrayList strings = CommonUtil.splitStr((String)this.id, (char)',');
            this.dimValMap = new HashMap<String, String>(MapSizeUtil.getHashMapInitCapacities((int)strings.size()));
            StringBuilder dataTime = new StringBuilder();
            for (String dimInfo : strings) {
                int index = dimInfo.indexOf(61);
                if (index == -1) {
                    dataTime.append(',').append(dimInfo);
                    continue;
                }
                this.dimValMap.put(dimInfo.substring(0, index), dimInfo.substring(index + 1));
            }
            if (dataTime.length() > 0) {
                this.dimValMap.put(SysDim.DATATIME.alias(), dataTime.substring(1));
            }
        }
    }

    public Map<String, String> toMap() {
        this.checkDimValMap();
        return this.dimValMap;
    }

    public String toString() {
        if (!StringUtils.hasLength(this.id)) {
            if (this.dimValMap == null || this.dimValMap.isEmpty()) {
                this.id = NONE_STR;
            } else {
                StringBuilder idBuilder = new StringBuilder(150);
                this.dimValMap.keySet().stream().sorted().forEach(key -> {
                    String dimVal = this.dimValMap.get(key);
                    Assert.notNull((Object)dimVal, "\u7ef4\u503c\u4e0d\u53ef\u4e3a\u7a7a\uff01");
                    if (SysDim.isDataTime(key)) {
                        if (dimVal.length() == 9) {
                            idBuilder.append(',').append((String)key).append('=').append(dimVal);
                        } else {
                            idBuilder.append(',').append(dimVal);
                        }
                        return;
                    }
                    idBuilder.append(',').append((String)key).append('=').append(dimVal);
                });
                this.id = idBuilder.substring(1);
            }
        }
        return this.id;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof LinkId) {
            return this.toString().equals(obj.toString());
        }
        return false;
    }

    public static LinkId none() {
        return NONE;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        this.checkDimValMap();
        return this.dimValMap.entrySet().iterator();
    }

    public boolean isNone() {
        return NONE_STR.equals(this.toString());
    }
}

