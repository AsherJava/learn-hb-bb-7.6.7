/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.function.func.doc;

import com.jiuqi.nr.function.func.doc.FuncParamDoc;
import java.util.List;

public interface IFuncDoc {
    public String name();

    public String sortName();

    public String category();

    public String desc();

    public List<FuncParamDoc> params();

    public String result();

    public String example();

    public boolean deprecated();
}

