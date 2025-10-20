/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.operator.jcajce;

import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.bouncycastle.operator.IDigestCalculatorProvider;

public interface IJcaDigestCalculatorProviderBuilder {
    public IDigestCalculatorProvider build() throws AbstractOperatorCreationException;
}

