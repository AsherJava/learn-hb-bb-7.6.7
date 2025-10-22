/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.internal.dto.MoveDataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.SearchDataFieldDTO;
import com.jiuqi.nr.datascheme.web.param.DataFieldMovePM;
import java.util.List;

public interface DataFieldDesignService {
    public List<DesignDataField> filterField(FieldSearchQuery var1);

    public int countBy(FieldSearchQuery var1);

    public List<SearchDataFieldDTO> searchFieldByScheme(FieldSearchQuery var1);

    public int position(DesignDataField var1, int var2);

    public List<DesignDataField> queryCandidate(String var1);

    public List<MoveDataFieldDTO> move(DataFieldMovePM var1);
}

