/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.Violation;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FieldValidator {
    public void checkField(DesignDataField var1) throws SchemeDataException;

    public <E extends DesignDataField> void checkField(Collection<E> var1) throws SchemeDataException;

    public void levelCheckField(DesignDataField var1) throws SchemeDataException;

    public void levelCheckField(String var1) throws SchemeDataException;

    public List<Violation> validator(DesignDataField var1);

    public Map<String, List<Violation>> validator(Collection<DesignDataField> var1);
}

