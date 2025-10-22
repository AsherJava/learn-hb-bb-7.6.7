/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import java.io.Serializable;
import java.util.Arrays;

public class ENameSet
implements Serializable {
    private static final long serialVersionUID = -1036367796412177443L;
    private int nameCount;
    private String[] names;

    public ENameSet() {
        this.nameCount = 0;
        this.names = new String[2];
    }

    public ENameSet(ENameSet clone) {
        this.nameCount = clone.nameCount;
        this.names = new String[this.nameCount];
        System.arraycopy(clone.names, 0, this.names, 0, this.nameCount);
    }

    public int size() {
        return this.nameCount;
    }

    public String get(int index) {
        return this.names[index];
    }

    public int add(String name) {
        int i;
        int index = this.nameCount;
        for (i = 0; i < this.nameCount; ++i) {
            int flag = name.compareTo(this.names[i]);
            if (flag == 0) {
                return -i;
            }
            if (flag >= 0) continue;
            index = i;
            break;
        }
        if (this.names.length <= this.nameCount) {
            String[] newItems = new String[this.names.length + 4];
            System.arraycopy(this.names, 0, newItems, 0, this.nameCount);
            this.names = newItems;
        }
        for (i = this.nameCount; i > index; --i) {
            this.names[i] = this.names[i - 1];
        }
        this.names[index] = name;
        ++this.nameCount;
        return index;
    }

    public int remove(String name) {
        for (int i = 0; i < this.nameCount; ++i) {
            if (!name.equals(this.names[i])) continue;
            this.removeAt(i);
            return i;
        }
        return -1;
    }

    public void removeAt(int index) {
        for (int j = index + 1; j < this.nameCount; ++j) {
            this.names[j - 1] = this.names[j];
        }
        this.names[this.nameCount - 1] = null;
        --this.nameCount;
    }

    public int indexOf(String name) {
        for (int i = 0; i < this.nameCount; ++i) {
            if (name.compareTo(this.names[i]) != 0) continue;
            return i;
        }
        return -1;
    }

    public boolean contains(String name) {
        for (int i = 0; i < this.nameCount; ++i) {
            if (name.compareTo(this.names[i]) != 0) continue;
            return true;
        }
        return false;
    }

    public void assign(ENameSet source) {
        if (source == null || source == this) {
            return;
        }
        if (this.names.length < source.nameCount) {
            this.names = new String[source.nameCount];
        }
        this.nameCount = source.nameCount;
        System.arraycopy(source.names, 0, this.names, 0, this.nameCount);
    }

    public boolean containsAll(ENameSet another) {
        int start = 0;
        for (int i = 0; i < another.nameCount; ++i) {
            int flag;
            String name = another.names[i];
            while (start < this.nameCount && (flag = name.compareTo(this.names[start])) != 0) {
                if (flag < 0) {
                    return false;
                }
                ++start;
            }
            if (start >= this.nameCount) {
                return false;
            }
            ++start;
        }
        return true;
    }

    public boolean containsAny(ENameSet another) {
        int start = 0;
        block0: for (int i = 0; i < another.nameCount; ++i) {
            String name = another.names[i];
            while (start < this.nameCount) {
                int flag = name.compareTo(this.names[start]);
                if (flag == 0) {
                    return true;
                }
                if (flag < 0) continue block0;
                ++start;
            }
        }
        return false;
    }

    public void addAll(ENameSet another) {
        if (this.nameCount == 0) {
            this.names = new String[another.nameCount];
            this.nameCount = another.nameCount;
            System.arraycopy(another.names, 0, this.names, 0, this.nameCount);
        } else {
            for (int i = 0; i < another.nameCount; ++i) {
                this.add(another.names[i]);
            }
        }
    }

    public void removeAll(ENameSet another) {
        for (int i = 0; i < another.nameCount; ++i) {
            this.remove(another.names[i]);
        }
        if (this.nameCount == 0) {
            this.names = new String[2];
        }
    }

    public void clear() {
        this.nameCount = 0;
        this.names = new String[2];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ENameSet)) {
            return false;
        }
        return this.compareTo((ENameSet)obj) == 0;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        for (String name : this.names) {
            result = 31 * result + (name == null ? 0 : this.names.hashCode());
        }
        return result;
    }

    public int compareTo(ENameSet another) {
        if (another == null) {
            return 1;
        }
        int result = this.nameCount - another.nameCount;
        if (result == 0) {
            for (int i = 0; i < this.nameCount; ++i) {
                result = this.names[i].compareTo(another.names[i]);
                if (result == 0) continue;
                return result;
            }
        }
        return result;
    }

    public String toString() {
        return Arrays.toString(this.names);
    }
}

