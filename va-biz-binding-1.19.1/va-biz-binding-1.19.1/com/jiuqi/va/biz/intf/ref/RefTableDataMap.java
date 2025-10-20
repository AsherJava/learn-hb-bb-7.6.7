/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.ref;

import com.jiuqi.va.biz.intf.ref.RefDataFilter;
import com.jiuqi.va.biz.utils.BaseDataUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface RefTableDataMap {
    public static final Map<String, Object> ERROR_VALUE = Collections.unmodifiableMap(new HashMap());
    public static final Map<String, Object> EMPTY_VALUE = Collections.unmodifiableMap(new HashMap());
    public static final Map<String, Map<String, Object>> ERROR_MAP = Collections.unmodifiableMap(new HashMap());
    public static final Map<String, Map<String, Object>> EMPTY_MAP = Collections.unmodifiableMap(new HashMap());

    public Map<String, Map<String, Object>> list();

    public Map<String, Object> find(String var1);

    default public Map<String, Object> findByName(String name) {
        return null;
    }

    default public Object findRefFieldValue(String id, String fieldName) {
        Map<String, Object> values = this.find(id);
        return values == null ? null : values.get(fieldName);
    }

    public Map<String, Object> toViewValue(String var1, Map<String, Object> var2);

    default public Map<String, Object> match(String text) {
        ArrayList matchList;
        String title;
        int index;
        if (text.contains("/")) {
            String[] strs = text.split("/");
            text = strs[strs.length - 1];
        }
        if ((index = text.indexOf(" ")) == -1) {
            Map<String, Object> value = this.find(text);
            if (value != null) {
                return value;
            }
            title = text;
        } else {
            String code = text.substring(0, index);
            Map<String, Object> value = this.find(code);
            if (value != null) {
                return value;
            }
            title = text.substring(index + 1);
        }
        Map<String, Object> result = this.findByName(title);
        if (result == null || result.size() == 0) {
            result = this.findByName(text);
        }
        if (result != null && result.size() > 0) {
            if (Objects.equals(result.get("stopflag"), 1)) {
                return null;
            }
            return result;
        }
        final String titleFieldName = this.getTitleFieldName();
        List<Map<String, Object>> list = this.filter(new RefDataFilter(matchList = new ArrayList()){
            final /* synthetic */ List val$matchList;
            {
                this.val$matchList = list;
            }

            @Override
            public boolean filter(Map<String, Object> refDataMap) {
                String s = (String)refDataMap.get(titleFieldName);
                if (s == null) {
                    return false;
                }
                if (Objects.equals(refDataMap.get("stopflag"), 1)) {
                    return false;
                }
                boolean match = s.contains(title);
                if (match) {
                    this.val$matchList.add(refDataMap);
                    if (s.length() == title.length()) {
                        return true;
                    }
                }
                return false;
            }
        });
        return list.size() == 1 ? list.get(0) : (matchList.size() == 1 ? (Map)matchList.get(0) : null);
    }

    public String getTitleFieldName();

    default public List<Map<String, Object>> filter(Map<String, Object> filterMap) {
        return BaseDataUtils.findRefObject(filterMap, this.list());
    }

    default public List<Map<String, Object>> filter(String expression) {
        throw new UnsupportedOperationException();
    }

    default public List<Map<String, Object>> filter(RefDataFilter filterCondition) {
        return BaseDataUtils.findRefObject(filterCondition, this.list());
    }
}

