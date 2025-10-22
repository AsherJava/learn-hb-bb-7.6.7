/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package nr.single.para.parain.util;

import com.jiuqi.np.common.exception.JQException;

public interface IParaServeCodeService {
    public String getServeCode() throws JQException;

    public String getCurServeCode();

    public boolean isSameServeCode(String var1) throws JQException;
}

