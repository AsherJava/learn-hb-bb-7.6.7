/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.mappingscheme.impl.enums;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum IsolationStrategy {
    SHARE("SHARE", "\u5171\u4eab", CollectionUtils.newArrayList()),
    UNITCODE("UNITCODE", "\u6309\u7ec4\u7ec7\u673a\u6784\u9694\u79bb", CollectionUtils.newArrayList((Object[])new String[]{"DC_UNITCODE"})),
    BOOKCODE("BOOKCODE", "\u6309\u8d26\u7c3f\u9694\u79bb", CollectionUtils.newArrayList((Object[])new String[]{"ODS_BOOKCODE"})),
    YEAR("YEAR", "\u6309\u5e74\u5ea6\u9694\u79bb", CollectionUtils.newArrayList((Object[])new String[]{"ODS_ACCTYEAR"})),
    SHARE_ISOLATION("SHARE_ISOLATION", "\u6309\u7ec4\u7ec7\u673a\u6784\u5171\u4eab+\u9694\u79bb", CollectionUtils.newArrayList((Object[])new String[]{"DC_UNITCODE"})),
    ISOLATION_SHARE("ISOLATION_SHARE", "\u6309\u7ec4\u7ec7\u673a\u6784\u9694\u79bb+\u5411\u4e0b\u7ea7\u5171\u4eab", CollectionUtils.newArrayList((Object[])new String[]{"DC_UNITCODE"})),
    ORG_ASSISTCODE("ORG_ASSISTCODE", "\u7ec4\u7ec7\u673a\u6784\u6309\u7167ASSISTCODE\u533a\u5206", CollectionUtils.newArrayList());

    private final String code;
    private final String name;
    private final List<String> field;

    private IsolationStrategy(String code, String name, List<String> field) {
        this.code = code;
        this.name = name;
        this.field = field;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    @Deprecated
    public String getField() {
        return String.join((CharSequence)",", this.field);
    }

    public List<String> getFieldList() {
        return this.field;
    }

    public static List<String> getIsolationStrategyList() {
        return Arrays.stream(IsolationStrategy.values()).map(IsolationStrategy::getCode).collect(Collectors.toList());
    }

    public static List<String> getIsolationFieldByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            code = IsolationStrategy.SHARE.code;
        }
        return IsolationStrategy.valueOf(code).getFieldList();
    }
}

