/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.simpleparameters;

import com.jiuqi.budget.page.PageInfo;
import org.springframework.util.StringUtils;

public class SearchParam
extends PageInfo {
    String searchKey;

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        if (StringUtils.hasLength(searchKey)) {
            StringBuilder builder = new StringBuilder(searchKey.length() + 2);
            if (searchKey.charAt(0) != '%') {
                builder.append('%').append(searchKey);
            } else {
                builder.append(searchKey);
            }
            if (searchKey.charAt(searchKey.length() - 1) != '%') {
                builder.append('%');
            }
            this.searchKey = builder.toString();
        } else {
            this.searchKey = null;
        }
    }
}

