/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.producer.AbstractFormattedPlaceholderPopulator;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

class UsedProductsPlaceholderPopulator
extends AbstractFormattedPlaceholderPopulator {
    private static final char PRODUCT_NAME = 'P';
    private static final char VERSION = 'V';
    private static final char USAGE_TYPE = 'T';
    private static final String PRODUCTS_SEPARATOR = ", ";

    @Override
    public String populate(List<ConfirmedEventWrapper> events, String parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid usage of placeholder \"{0}\": format is required", "usedProducts"));
        }
        LinkedHashSet<ProductRepresentation> usedProducts = new LinkedHashSet<ProductRepresentation>();
        for (ConfirmedEventWrapper confirmedEventWrapper : events) {
            usedProducts.add(new ProductRepresentation(confirmedEventWrapper));
        }
        LinkedHashSet<String> usedProductsRepresentations = new LinkedHashSet<String>();
        for (ProductRepresentation representation : usedProducts) {
            usedProductsRepresentations.add(this.formatProduct(representation, parameter));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringRepresentation : usedProductsRepresentations) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(PRODUCTS_SEPARATOR);
            }
            stringBuilder.append(stringRepresentation);
        }
        return stringBuilder.toString();
    }

    private String formatProduct(ProductRepresentation product, String format) {
        StringBuilder builder = new StringBuilder();
        char[] formatArray = format.toCharArray();
        for (int i = 0; i < formatArray.length; ++i) {
            if (formatArray[i] == '\'') {
                i = this.attachQuotedString(i, builder, formatArray);
                continue;
            }
            if (this.isLetter(formatArray[i])) {
                builder.append(this.formatLetter(formatArray[i], product));
                continue;
            }
            builder.append(formatArray[i]);
        }
        return builder.toString();
    }

    private String formatLetter(char letter, ProductRepresentation product) {
        if (letter == 'P') {
            return product.getProductName();
        }
        if (letter == 'V') {
            return product.getVersion();
        }
        if (letter == 'T') {
            return product.getProductUsageType();
        }
        throw new IllegalArgumentException(MessageFormatUtil.format("Pattern contains unexpected character {0}", Character.valueOf(letter)));
    }

    private static class ProductRepresentation {
        private static final Map<String, String> PRODUCT_USAGE_TYPE_TO_HUMAN_READABLE_FORM;
        private final String productName;
        private final String productUsageType;
        private final String version;

        public ProductRepresentation(ConfirmedEventWrapper event) {
            this.productName = event.getEvent().getProductData().getPublicProductName();
            this.productUsageType = PRODUCT_USAGE_TYPE_TO_HUMAN_READABLE_FORM.containsKey(event.getProductUsageType()) ? PRODUCT_USAGE_TYPE_TO_HUMAN_READABLE_FORM.get(event.getProductUsageType()) : event.getProductUsageType();
            this.version = event.getEvent().getProductData().getVersion();
        }

        public String getProductName() {
            return this.productName;
        }

        public String getProductUsageType() {
            return this.productUsageType;
        }

        public String getVersion() {
            return this.version;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ProductRepresentation that = (ProductRepresentation)o;
            if (this.getProductName() == null ? that.getProductName() != null : !this.getProductName().equals(that.getProductName())) {
                return false;
            }
            if (this.getProductUsageType() == null ? that.getProductUsageType() != null : !this.getProductUsageType().equals(that.getProductUsageType())) {
                return false;
            }
            return this.getVersion() == null ? that.getVersion() == null : this.getVersion().equals(that.getVersion());
        }

        public int hashCode() {
            int result = this.getProductName() == null ? 0 : this.getProductName().hashCode();
            result = 31 * result + (this.getProductUsageType() == null ? 0 : this.getProductUsageType().hashCode());
            result = 31 * result + (this.getVersion() == null ? 0 : this.getVersion().hashCode());
            return result;
        }

        static {
            HashMap<String, String> productUsageTypeMapping = new HashMap<String, String>();
            productUsageTypeMapping.put("nonproduction", "non-production");
            PRODUCT_USAGE_TYPE_TO_HUMAN_READABLE_FORM = Collections.unmodifiableMap(productUsageTypeMapping);
        }
    }
}

