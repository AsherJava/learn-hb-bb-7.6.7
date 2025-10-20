/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.desc;

import com.jiuqi.bi.database.desc.CaseSensitity;
import java.util.Set;

public interface IDatabaseDescriptor {
    public static final String STORAGE_MODE_ROW = "row";
    public static final String STORAGE_MODE_COLUMN = "col";

    public boolean supportEmptyString();

    public boolean supportInnerOrderBy();

    public boolean supportLookupField();

    public boolean supportLookupOrderBy();

    public boolean supportOrderNulls();

    public boolean supportWithClause();

    public boolean supportFullJoin();

    public boolean supportFastInsert();

    public boolean supportMerge();

    public boolean supportDropIfExists();

    public boolean supportCreateIfNotExists();

    public boolean supportWindowFunctions();

    public String getStrcatOperator() throws UnsupportedOperationException;

    public String getStrcatFunction();

    public String getNVLName();

    public int defaultPort();

    public Set<String> getKeyWords();

    public boolean isKeyword(String var1);

    public boolean supportView();

    public String storageMode();

    public CaseSensitity getCaseSensitity();

    public String[] getKeywordQuotes();
}

