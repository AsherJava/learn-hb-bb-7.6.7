/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util;

public class Escaper {
    private final Mapping[] nonOverlapMappings;
    private final Mapping[] specialBackMappings;
    private final Mapping[] normalBackMappings;

    public Escaper(String[][] mappings, int normalSetIndex) {
        this.nonOverlapMappings = new Mapping[mappings.length];
        this.specialBackMappings = new Mapping[normalSetIndex];
        this.normalBackMappings = new Mapping[mappings.length - normalSetIndex];
        for (int i = 0; i < mappings.length; ++i) {
            char[] mappings0 = mappings[i][0].toCharArray();
            char[] mappings1 = mappings[i][1].toCharArray();
            if (i < normalSetIndex) {
                this.specialBackMappings[i] = new Mapping(mappings1, mappings0);
            } else {
                this.normalBackMappings[i - normalSetIndex] = new Mapping(mappings1, mappings0);
            }
            this.nonOverlapMappings[i] = new Mapping(mappings0, mappings1);
        }
    }

    public String escape(String string) {
        return this.applyMappings(this.nonOverlapMappings, string);
    }

    public String unescape(String string) {
        String result = this.applyMappings(this.normalBackMappings, string);
        return this.applyMappings(this.specialBackMappings, result);
    }

    private String applyMappings(Mapping[] mappings, String string) {
        StringBuffer result = new StringBuffer();
        if (string != null && string.length() > 0) {
            char[] stringChars = string.toCharArray();
            for (int stringIndex = 0; stringIndex < stringChars.length; ++stringIndex) {
                boolean mapped = false;
                for (int mappingIndex = 0; mappingIndex < mappings.length; ++mappingIndex) {
                    Mapping mapping = mappings[mappingIndex];
                    boolean matches = true;
                    for (int sourceIndex = 0; sourceIndex < mapping.source.length && stringIndex + sourceIndex < stringChars.length; ++sourceIndex) {
                        if (stringChars[stringIndex + sourceIndex] == mapping.source[sourceIndex]) continue;
                        matches = false;
                        break;
                    }
                    if (!matches) continue;
                    mapped = true;
                    result.append(mapping.dest);
                    stringIndex += mapping.source.length - 1;
                    break;
                }
                if (mapped) continue;
                result.append(stringChars[stringIndex]);
            }
        }
        return result.toString();
    }

    private class Mapping {
        private final char[] source;
        private final char[] dest;

        public Mapping(char[] source, char[] dest) {
            this.source = source;
            this.dest = dest;
        }
    }
}

