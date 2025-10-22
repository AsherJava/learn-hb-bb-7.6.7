/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.midstore.core.definition.db;

import org.springframework.dao.DataAccessException;

public class BeanMidstoreException
extends DataAccessException {
    private static final long serialVersionUID = -4743968098369288206L;

    public BeanMidstoreException(String message) {
        super(message);
    }

    public BeanMidstoreException(Throwable e) {
        super(e.getMessage(), e);
    }
}

