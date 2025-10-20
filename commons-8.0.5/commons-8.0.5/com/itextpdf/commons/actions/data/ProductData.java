/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.data;

import java.util.Objects;

public final class ProductData {
    private final String publicProductName;
    private final String productName;
    private final String version;
    private final String minimalCompatibleLicenseKeyVersion;
    private final int sinceCopyrightYear;
    private final int toCopyrightYear;

    public ProductData(String publicProductName, String productName, String version, int sinceCopyrightYear, int toCopyrightYear) {
        this(publicProductName, productName, version, null, sinceCopyrightYear, toCopyrightYear);
    }

    public ProductData(String publicProductName, String productName, String version, String minimalCompatibleLicenseKeyVersion, int sinceCopyrightYear, int toCopyrightYear) {
        this.publicProductName = publicProductName;
        this.productName = productName;
        this.version = version;
        this.minimalCompatibleLicenseKeyVersion = minimalCompatibleLicenseKeyVersion;
        this.sinceCopyrightYear = sinceCopyrightYear;
        this.toCopyrightYear = toCopyrightYear;
    }

    public String getPublicProductName() {
        return this.publicProductName;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getVersion() {
        return this.version;
    }

    public int getSinceCopyrightYear() {
        return this.sinceCopyrightYear;
    }

    public int getToCopyrightYear() {
        return this.toCopyrightYear;
    }

    public String getMinCompatibleLicensingModuleVersion() {
        return this.minimalCompatibleLicenseKeyVersion;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ProductData other = (ProductData)o;
        return Objects.equals(this.publicProductName, other.publicProductName) && Objects.equals(this.productName, other.productName) && Objects.equals(this.version, other.version) && this.sinceCopyrightYear == other.sinceCopyrightYear && this.toCopyrightYear == other.toCopyrightYear;
    }

    public int hashCode() {
        int result = this.publicProductName != null ? this.publicProductName.hashCode() : 0;
        result += 31 * result + (this.productName != null ? this.productName.hashCode() : 0);
        result += 31 * result + (this.version != null ? this.version.hashCode() : 0);
        result += 31 * result + this.sinceCopyrightYear;
        result += 31 * result + this.toCopyrightYear;
        return result;
    }
}

