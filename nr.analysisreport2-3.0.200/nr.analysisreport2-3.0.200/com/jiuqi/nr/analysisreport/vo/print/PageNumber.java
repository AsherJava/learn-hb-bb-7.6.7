/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.analysisreport.vo.print;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PageNumber {
    private String originNumber;
    private String numberScope;
    private Boolean showDiff;
    private NumberStyle numberStyle;
    private NumberPosition numberPosition;
    private ShowNumber showNumber;

    public String getOriginNumber() {
        return this.originNumber;
    }

    public void setOriginNumber(String originNumber) {
        this.originNumber = originNumber;
    }

    public String getNumberScope() {
        return this.numberScope;
    }

    public void setNumberScope(String numberScope) {
        this.numberScope = numberScope;
    }

    public Boolean getShowDiff() {
        return this.showDiff;
    }

    public void setShowDiff(Boolean showDiff) {
        this.showDiff = showDiff;
    }

    public NumberStyle getNumberStyle() {
        return this.numberStyle;
    }

    public void setNumberStyle(NumberStyle numberStyle) {
        this.numberStyle = numberStyle;
    }

    public NumberPosition getNumberPosition() {
        return this.numberPosition;
    }

    public void setNumberPosition(NumberPosition numberPosition) {
        this.numberPosition = numberPosition;
    }

    public ShowNumber getShowNumber() {
        return this.showNumber;
    }

    public void setShowNumber(ShowNumber showNumber) {
        this.showNumber = showNumber;
    }

    @JsonCreator
    public static PageNumber fromJson(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (PageNumber)objectMapper.readValue(value, (TypeReference)new TypeReference<PageNumber>(){});
    }

    public static class ShowNumber {
        private Boolean total;
        private Boolean odd;
        private Boolean even;

        public Boolean getTotal() {
            return this.total;
        }

        public void setTotal(Boolean total) {
            this.total = total;
        }

        public Boolean getOdd() {
            return this.odd;
        }

        public void setOdd(Boolean odd) {
            this.odd = odd;
        }

        public Boolean getEven() {
            return this.even;
        }

        public void setEven(Boolean even) {
            this.even = even;
        }
    }

    public static class NumberPosition {
        private String total;
        private String odd;
        private String even;

        public String getTotal() {
            return this.total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOdd() {
            return this.odd;
        }

        public void setOdd(String odd) {
            this.odd = odd;
        }

        public String getEven() {
            return this.even;
        }

        public void setEven(String even) {
            this.even = even;
        }
    }

    public static class NumberStyle {
        private String total;
        private String odd;
        private String even;

        public String getTotal() {
            return this.total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOdd() {
            return this.odd;
        }

        public void setOdd(String odd) {
            this.odd = odd;
        }

        public String getEven() {
            return this.even;
        }

        public void setEven(String even) {
            this.even = even;
        }
    }
}

