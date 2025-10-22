/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.definition.exception;

import org.springframework.dao.DataAccessException;

public class BeanParaException
extends DataAccessException {
    private static final long serialVersionUID = -4743968098369288206L;

    public BeanParaException(String message) {
        super(message);
    }

    public BeanParaException(Throwable e) {
        super(e.getMessage(), e);
    }
}

