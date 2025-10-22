/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.midstore.core.definition.db;

import org.springframework.dao.DataAccessException;

public class DBMidstoreException
extends DataAccessException {
    private static final long serialVersionUID = 7502385790410148817L;

    public DBMidstoreException() {
        super("\u67e5\u8be2\u53c2\u6570\u5f02\u5e38");
    }

    public DBMidstoreException(String message) {
        super(message);
    }

    public DBMidstoreException(Throwable e) {
        super(e.getMessage(), e);
    }
}

