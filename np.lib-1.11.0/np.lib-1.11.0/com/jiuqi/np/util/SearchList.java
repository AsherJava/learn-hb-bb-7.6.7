/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.IntValue
 */
package com.jiuqi.np.util;

import com.jiuqi.bi.util.IntValue;
import com.jiuqi.np.util.SearchListDuplicationException;
import java.util.ArrayList;

public class SearchList {
    protected boolean[] descfields;
    protected int lastFound = -1;
    protected ArrayList keys = new ArrayList(128);
    protected ArrayList values = new ArrayList(128);

    public static final int lengthCompare(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt) {
        int i = paramArrayOfObject1.length;
        if (i > paramArrayOfObject2.length) {
            i = paramArrayOfObject2.length;
        }
        if (i > paramInt) {
            i = paramInt;
        }
        for (int j = 0; j < i; ++j) {
            int k = SearchList.compare(paramArrayOfObject1[j], paramArrayOfObject2[j]);
            if (k == 0) continue;
            return k;
        }
        if (i >= paramInt) {
            return 0;
        }
        return paramArrayOfObject1.length - paramArrayOfObject2.length;
    }

    public static final int compare(Object paramObject1, Object paramObject2) {
        if (paramObject1 instanceof Object[] && paramObject2 instanceof Object[]) {
            Object[] arrayOfObject1 = (Object[])paramObject1;
            int i = arrayOfObject1.length;
            Object[] arrayOfObject2 = (Object[])paramObject2;
            if (i > arrayOfObject2.length) {
                i = arrayOfObject2.length;
            }
            for (int j = 0; j < i; ++j) {
                int k = SearchList.compare(arrayOfObject1[j], arrayOfObject2[j]);
                if (k == 0) continue;
                return k;
            }
            return arrayOfObject1.length - arrayOfObject2.length;
        }
        if (paramObject1 == null || paramObject2 == null) {
            return (paramObject1 == null ? 0 : 1) - (paramObject2 == null ? 0 : 1);
        }
        if (paramObject1.equals(paramObject2)) {
            return 0;
        }
        try {
            if (paramObject1 instanceof Comparable) {
                return ((Comparable)paramObject1).compareTo(paramObject2);
            }
            if (paramObject2 instanceof Comparable) {
                return ((Comparable)paramObject2).compareTo(paramObject1);
            }
            return ((Boolean)paramObject1 != false ? 1 : 0) - ((Boolean)paramObject2 != false ? 1 : 0);
        }
        catch (ClassCastException classCastException) {
            return 0;
        }
    }

    public static final boolean matchKey(Object paramObject1, Object paramObject2) {
        if (paramObject1 == null) {
            return true;
        }
        if (paramObject2 == null) {
            return false;
        }
        if (paramObject1.equals(paramObject2)) {
            return true;
        }
        if (paramObject1 instanceof Object[] && paramObject2 instanceof Object[]) {
            int i = ((Object[])paramObject1).length;
            if (i != ((Object[])paramObject2).length) {
                return false;
            }
            for (int j = 0; j < i; ++j) {
                if (((Object[])paramObject1)[j] == null || ((Object[])paramObject1)[j].equals(((Object[])paramObject2)[j])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public void setDescFields(boolean[] paramArrayOfBoolean) {
        this.descfields = paramArrayOfBoolean;
    }

    public void setCapacity(int paramInt) {
        this.keys.ensureCapacity(paramInt);
        this.values.ensureCapacity(paramInt);
    }

    public final int compareKey(Object paramObject1, Object paramObject2) {
        if (this.descfields == null) {
            return SearchList.compare(paramObject1, paramObject2);
        }
        if (paramObject1 instanceof Object[] && paramObject2 instanceof Object[]) {
            Object[] arrayOfObject1 = (Object[])paramObject1;
            int j = arrayOfObject1.length;
            Object[] arrayOfObject2 = (Object[])paramObject2;
            if (j < arrayOfObject2.length) {
                j = arrayOfObject2.length;
            }
            for (int k = 0; k < j; ++k) {
                int m = SearchList.compare(arrayOfObject1[k], arrayOfObject2[k]);
                if (m == 0) continue;
                if (this.descfields[0]) {
                    m = -m;
                }
                return m;
            }
            return arrayOfObject2.length > j ? -1 : (arrayOfObject1.length > j ? 1 : 0);
        }
        int i = SearchList.compare(paramObject1, paramObject2);
        if (this.descfields[0]) {
            i = -i;
        }
        return i;
    }

    public void clear() {
        this.keys.clear();
        this.values.clear();
        this.lastFound = -1;
    }

    public final int size() {
        return this.keys.size();
    }

    public final Object getKey(int paramInt) {
        return this.keys.get(paramInt);
    }

    public final Object getValue(int paramInt) {
        return this.values.get(paramInt);
    }

    public final void setValue(int paramInt, Object paramObject) {
        this.values.set(paramInt, paramObject);
    }

    public final int search(Object paramObject) {
        if (this.lastFound >= 0 && this.compareKey(paramObject, this.keys.get(this.lastFound)) == 0) {
            return this.lastFound;
        }
        int i = 0;
        int j = this.keys.size() - 1;
        int m = -1;
        while (i <= j) {
            int k = i + j >> 1;
            m = this.compareKey(paramObject, this.keys.get(k));
            if (m > 0) {
                i = k + 1;
                continue;
            }
            if (m < 0) {
                j = k - 1;
                continue;
            }
            i = k;
        }
        return m == 0 ? i : -i - 1;
    }

    public final int searchfirst(Object paramObject) {
        int i = 0;
        int j = this.keys.size() - 1;
        int m = -1;
        while (i <= j) {
            int k = i + j >> 1;
            m = this.compareKey(paramObject, this.keys.get(k));
            if (m > 0) {
                i = k + 1;
                continue;
            }
            if (m < 0) {
                j = k - 1;
                continue;
            }
            j = k;
            if (i != k) continue;
            break;
        }
        return m == 0 ? i : -i - 1;
    }

    public final int searchlast(Object paramObject) {
        int i = 0;
        int j = this.keys.size() - 1;
        int m = -1;
        while (i <= j) {
            int k = i + j + 1 >> 1;
            m = this.compareKey(paramObject, this.keys.get(k));
            if (m > 0) {
                i = k + 1;
                continue;
            }
            if (m < 0) {
                j = k - 1;
                continue;
            }
            i = k;
            if (j != k) continue;
            break;
        }
        return m == 0 ? i : -i - 1;
    }

    public final int partlySearch(Object[] paramArrayOfObject, IntValue paramIntValue) {
        int k;
        int j;
        int i = 0;
        int m = 0;
        int n = j = this.keys.size() - 1;
        int i1 = -1;
        while (i <= j) {
            k = i + j >> 1;
            i1 = SearchList.lengthCompare(paramArrayOfObject, (Object[])this.keys.get(k), paramArrayOfObject.length);
            if (i1 > 0) {
                i = k + 1;
                continue;
            }
            if (i1 < 0) {
                n = j = k - 1;
                continue;
            }
            m = k;
            j = k;
            if (i != k) continue;
        }
        if (i1 != 0) {
            paramIntValue.value = 0;
            return -i - 1;
        }
        j = n;
        n = i;
        i = m;
        while (i <= j) {
            k = i + j + 1 >> 1;
            i1 = SearchList.lengthCompare(paramArrayOfObject, (Object[])this.keys.get(k), paramArrayOfObject.length);
            if (i1 > 0) {
                i = k + 1;
                continue;
            }
            if (i1 < 0) {
                j = k - 1;
                continue;
            }
            i = k;
            if (j != k) continue;
        }
        paramIntValue.value = i - n + 1;
        return n;
    }

    public final boolean haskey(Object paramObject) {
        return this.search(paramObject) >= 0;
    }

    public final int indexOf(Object paramObject) {
        int i = this.search(paramObject);
        return i < 0 ? -1 : i;
    }

    public final Object findkey(Object paramObject) {
        int i = this.search(paramObject);
        if (i < 0) {
            return null;
        }
        this.lastFound = i;
        return this.values.get(i);
    }

    public final int findfirstkey(Object paramObject) {
        int i = this.searchfirst(paramObject);
        if (i < 0) {
            return -1;
        }
        return i;
    }

    public final int findlastkey(Object paramObject) {
        int i = this.searchlast(paramObject);
        if (i < 0) {
            return -1;
        }
        return i;
    }

    public void add(Object paramObject1, Object paramObject2) throws SearchListDuplicationException {
        int i = this.search(paramObject1);
        if (i >= 0) {
            if (this.values.get(i) == paramObject2) {
                return;
            }
            throw new SearchListDuplicationException("\u952e\u503c\u91cd\u590d\uff1a" + paramObject1 == null ? null : paramObject1.toString());
        }
        i = -i - 1;
        this.keys.add(i, paramObject1);
        this.values.add(i, paramObject2);
        this.lastFound = i;
    }

    public int addNew(Object paramObject1, Object paramObject2) {
        int i = this.search(paramObject1);
        if (i < 0) {
            i = -i - 1;
            this.keys.add(i, paramObject1);
            this.values.add(i, paramObject2);
            this.lastFound = i;
        }
        return i;
    }

    public void remove(int paramInt) {
        if (paramInt < this.lastFound) {
            --this.lastFound;
        }
        if (paramInt == this.lastFound) {
            this.lastFound = -1;
        }
        this.keys.remove(paramInt);
        this.values.remove(paramInt);
    }

    public boolean remove(Object paramObject) {
        int i = this.search(paramObject);
        if (i >= 0) {
            this.keys.remove(i);
            this.values.remove(i);
            if (i < this.lastFound) {
                --this.lastFound;
            }
            if (i == this.lastFound) {
                this.lastFound = -1;
            }
        }
        return i >= 0;
    }

    public final int addOrReplace(Object paramObject1, Object paramObject2) {
        int i = this.search(paramObject1);
        if (i < 0) {
            i = -i - 1;
            this.keys.add(i, paramObject1);
            this.values.add(i, paramObject2);
            this.lastFound = i;
        } else {
            this.values.set(i, paramObject2);
        }
        return i;
    }

    public final int addfirst(Object paramObject1, Object paramObject2) {
        int i = this.searchfirst(paramObject1);
        if (i < 0) {
            i = -i - 1;
        }
        this.keys.add(i, paramObject1);
        this.values.add(i, paramObject2);
        this.lastFound = i;
        return i;
    }

    public final boolean removefirst(Object paramObject) {
        int i = this.searchfirst(paramObject);
        if (i >= 0) {
            this.keys.remove(i);
            this.values.remove(i);
            if (i < this.lastFound) {
                --this.lastFound;
            }
            if (i == this.lastFound) {
                this.lastFound = -1;
            }
        }
        return i >= 0;
    }

    public final int addlast(Object paramObject1, Object paramObject2) {
        int i = this.searchlast(paramObject1);
        i = i >= 0 ? ++i : -i - 1;
        this.keys.add(i, paramObject1);
        this.values.add(i, paramObject2);
        this.lastFound = i;
        return i;
    }

    public final boolean removelast(Object paramObject) {
        int i = this.searchlast(paramObject);
        if (i >= 0) {
            this.keys.remove(i);
            this.values.remove(i);
            if (i < this.lastFound) {
                --this.lastFound;
            }
            if (i == this.lastFound) {
                this.lastFound = -1;
            }
        }
        return i >= 0;
    }

    public final int indexOfValue(Object paramObject) {
        return this.values.indexOf(paramObject);
    }

    public static void main(String[] paramArrayOfString) {
        String[] arrayOfString = new String[2];
        Object[] arrayOfObject = new Object[2];
        arrayOfString[0] = "123";
        arrayOfObject[0] = "123";
        arrayOfString[1] = "456";
        arrayOfObject[1] = "456";
        System.out.print(SearchList.compare(arrayOfString, arrayOfObject));
    }
}

