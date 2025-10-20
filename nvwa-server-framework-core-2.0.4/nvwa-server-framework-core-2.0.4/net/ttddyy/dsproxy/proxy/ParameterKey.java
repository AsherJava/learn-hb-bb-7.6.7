/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

public class ParameterKey
implements Comparable<ParameterKey> {
    private int index;
    private String name;
    private ParameterKeyType type;

    public ParameterKey(int index) {
        this.index = index;
        this.type = ParameterKeyType.BY_INDEX;
    }

    public ParameterKey(String name) {
        this.name = name;
        this.type = ParameterKeyType.BY_NAME;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public ParameterKeyType getType() {
        return this.type;
    }

    public boolean isByIndex() {
        return this.type == ParameterKeyType.BY_INDEX;
    }

    public boolean isByName() {
        return this.type == ParameterKeyType.BY_NAME;
    }

    public String getKeyAsString() {
        return this.type == ParameterKeyType.BY_INDEX ? Integer.toString(this.index) : this.name;
    }

    @Override
    public int compareTo(ParameterKey other) {
        boolean bothAreIntString;
        if (this.isByIndex()) {
            if (other.isByIndex()) {
                return this.index == other.index ? 0 : (this.index < other.index ? -1 : 1);
            }
            if (this.isIntString(other.name)) {
                return this.compareInt(this.index, Integer.parseInt(other.name));
            }
            return -1;
        }
        if (other.isByIndex()) {
            if (this.isIntString(this.name)) {
                return this.compareInt(Integer.parseInt(this.name), other.index);
            }
            return 1;
        }
        boolean thisIsIntString = this.isIntString(this.name);
        boolean otherIsIntString = this.isIntString(other.name);
        boolean bl = bothAreIntString = thisIsIntString && otherIsIntString;
        if (bothAreIntString) {
            return this.compareInt(Integer.parseInt(this.name), Integer.parseInt(other.name));
        }
        if (thisIsIntString || otherIsIntString) {
            return thisIsIntString ? -1 : 1;
        }
        return this.name.compareTo(other.name);
    }

    private boolean isIntString(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private int compareInt(int left, int right) {
        return left < right ? -1 : (left == right ? 0 : 1);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ParameterKey that = (ParameterKey)o;
        if (this.index != that.index) {
            return false;
        }
        if (this.name != null ? !this.name.equals(that.name) : that.name != null) {
            return false;
        }
        return this.type == that.type;
    }

    public int hashCode() {
        int result = this.index;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "ParameterKey[type=" + (Object)((Object)this.type) + ", index=" + this.index + ", name='" + this.name + '\'' + ']';
    }

    public static enum ParameterKeyType {
        BY_INDEX,
        BY_NAME;

    }
}

