/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.Violation;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DataSchemeValidator {
    public void checkDataScheme(DesignDataScheme var1) throws SchemeDataException;

    public void checkDataScheme(DesignDataScheme var1, String ... var2) throws SchemeDataException;

    public void levelCheckDataScheme(String var1) throws SchemeDataException;

    public void levelCheckDataScheme(DesignDataScheme var1) throws SchemeDataException;

    public <E extends DesignDataScheme> void checkDataScheme(Collection<E> var1) throws SchemeDataException;

    public List<Violation> validator(DesignDataScheme var1);

    public Map<String, List<Violation>> validator(Collection<DesignDataScheme> var1);

    public boolean isSubLevel(DesignDataScheme var1);

    public void checkSubLevelModify(DesignDataScheme var1, List<DesignDataDimension> var2);
}

