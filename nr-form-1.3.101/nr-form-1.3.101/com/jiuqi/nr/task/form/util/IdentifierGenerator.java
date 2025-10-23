/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.util;

import java.util.function.Function;
import org.springframework.util.Assert;

public abstract class IdentifierGenerator<E> {
    abstract IdParts getIdParts(E var1);

    abstract boolean exist(IdParts var1, E var2);

    abstract E nextIdentifier(IdParts var1, E var2, Function<IdParts, String> var3);

    abstract Function<IdParts, String> formatSupplier();

    public E generateUniqueId(E origin) {
        Assert.notNull(origin, "origin cannot be null");
        IdParts idParts = this.getIdParts(origin);
        boolean same = this.exist(idParts, origin);
        while (same) {
            origin = this.nextIdentifier(idParts, origin, this.formatSupplier());
            same = this.exist(idParts, origin);
        }
        return origin;
    }

    public void reset() {
    }

    public static class IdParts {
        private final String base;
        private int suffixNumber;

        IdParts(String base, int suffixNumber) {
            this.base = base;
            this.suffixNumber = suffixNumber;
        }

        public String getBase() {
            return this.base;
        }

        public int getSuffixNumber() {
            return this.suffixNumber;
        }

        public void setSuffixNumber(int suffixNumber) {
            this.suffixNumber = suffixNumber;
        }
    }
}

