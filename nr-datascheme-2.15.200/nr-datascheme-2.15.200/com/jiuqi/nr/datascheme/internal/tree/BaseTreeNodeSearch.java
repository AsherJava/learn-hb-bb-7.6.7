/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.TreeSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public abstract class BaseTreeNodeSearch<NODE extends ISchemeNode> {
    @Autowired
    protected IDataSchemeAuthService dataSchemeAuthService;

    public List<NODE> search(TreeSearchQuery searchQuery) {
        boolean tField;
        boolean mi;
        String keyword = searchQuery.getKeyword();
        if (keyword == null) {
            return Collections.emptyList();
        }
        if ((keyword = keyword.trim()).isEmpty()) {
            return Collections.emptyList();
        }
        int searchType = searchQuery.getSearchType();
        if (searchType <= 0) {
            return Collections.emptyList();
        }
        String schemeKey = searchQuery.getScheme();
        List<String> schemes = searchQuery.getSchemes();
        if (CollectionUtils.isEmpty(schemes) && schemeKey != null) {
            schemes = Collections.singletonList(schemeKey);
        }
        ArrayList<NODE> filter = new ArrayList<NODE>();
        if ((NodeType.SCHEME_GROUP.getValue() & searchType) != 0) {
            filter.addAll(this.searchDataSchemeGroup(keyword));
        }
        if ((NodeType.SCHEME.getValue() & searchType) != 0) {
            filter.addAll(this.searchDataScheme(keyword));
        }
        if ((NodeType.GROUP.getValue() & searchType) != 0) {
            filter.addAll(this.searchDataTableGroup(schemes, keyword));
        }
        boolean mt = (NodeType.MUL_DIM_TABLE.getValue() & searchType) != 0;
        boolean dt = (NodeType.DETAIL_TABLE.getValue() & searchType) != 0;
        boolean at = (NodeType.ACCOUNT_TABLE.getValue() & searchType) != 0;
        boolean t = (NodeType.TABLE.getValue() & searchType) != 0;
        boolean bl = mi = (NodeType.MD_INFO.getValue() & searchType) != 0;
        if (mt || dt || t || at || mi) {
            int searchType0 = 0;
            if (mt) {
                searchType0 |= DataTableType.MULTI_DIM.getValue();
            }
            if (dt) {
                searchType0 = searchType0 | DataTableType.DETAIL.getValue() | DataTableType.SUB_TABLE.getValue();
            }
            if (t) {
                searchType0 |= DataTableType.TABLE.getValue();
            }
            if (t) {
                searchType0 |= DataTableType.ACCOUNT.getValue();
            }
            if (mi) {
                searchType0 |= DataTableType.MD_INFO.getValue();
            }
            filter.addAll(this.searchDataTable(schemes, keyword, searchType0));
        }
        boolean zb = (NodeType.FIELD_ZB.getValue() & searchType) != 0;
        boolean field = (NodeType.FIELD.getValue() & searchType) != 0;
        boolean bl2 = tField = (NodeType.TABLE_DIM.getValue() & searchType) != 0;
        if (zb || field || tField) {
            int kind = 0;
            if (tField) {
                kind |= DataFieldKind.TABLE_FIELD_DIM.getValue();
            }
            if (zb) {
                kind |= DataFieldKind.FIELD_ZB.getValue();
            }
            if (field) {
                kind |= DataFieldKind.FIELD.getValue();
            }
            filter.addAll(this.searchDataField(schemes, keyword, kind));
        }
        return filter;
    }

    protected abstract List<NODE> searchDataField(List<String> var1, String var2, int var3);

    protected abstract List<NODE> searchDataTable(List<String> var1, String var2, int var3);

    protected abstract List<NODE> searchDataTableGroup(List<String> var1, String var2);

    protected abstract List<NODE> searchDataSchemeGroup(String var1);

    protected abstract List<NODE> searchDataScheme(String var1);

    protected Map<String, Boolean> canReadSchemes(Set<String> dataSchemeKeys) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        if (!CollectionUtils.isEmpty(dataSchemeKeys)) {
            for (String dataSchemeKey : dataSchemeKeys) {
                result.put(dataSchemeKey, this.dataSchemeAuthService.canReadScheme(dataSchemeKey));
            }
        }
        return result;
    }
}

