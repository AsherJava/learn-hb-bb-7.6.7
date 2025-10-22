/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.single.core.util;

import com.jiuqi.nr.single.core.util.NumberObject;
import org.apache.commons.lang3.StringUtils;

public class FuzzyComparor {
    private static final long BLOCK_DATA_MASK = -256L;
    private static final int USEFLAG = 1;
    private static final int CHAR_DATA_MASK = 0xFFFF00;
    private int length;
    private String compData;
    private int[] charList;
    private long[] blockList;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCompData() {
        return this.compData;
    }

    public void setCompData(String compData) {
        this.compData = compData;
    }

    public int[] getCharList() {
        return this.charList;
    }

    public void setCharList(int[] charList) {
        this.charList = charList;
    }

    public long[] getBlockList() {
        return this.blockList;
    }

    public void setBlockList(long[] blockList) {
        this.blockList = blockList;
    }

    public void initBuffer(String aCompData) {
        char c;
        int i;
        this.compData = aCompData;
        int n = this.length = StringUtils.isNotEmpty((CharSequence)aCompData) ? aCompData.length() : 0;
        if (this.length <= 0) {
            return;
        }
        short[] blockBuffer = new short[this.length * 2 + 6];
        for (i = 0; i < this.length; ++i) {
            c = aCompData.charAt(i);
            short c0 = (short)c;
            short c1 = (short)(c0 / 256);
            short c2 = (short)(c0 % 256);
            blockBuffer[i + 3] = c1;
            blockBuffer[i + 3 + 1] = c2;
        }
        this.charList = new int[this.length];
        this.blockList = new long[this.length];
        for (i = 0; i < this.length; ++i) {
            c = aCompData.charAt(i);
            this.charList[i] = (short)c << 8;
            this.blockList[i] = this.getlong(blockBuffer, i * 2, 8) & 0xFFFFFFFFFFFFFF00L;
        }
        this.quickSortInt32(this.charList, 0, this.length - 1);
        this.quickSortInt64(this.blockList, 0, this.length - 1);
    }

    public float compareTo(String another) {
        int i;
        int len;
        float result = 0.0f;
        int n = len = StringUtils.isNotEmpty((CharSequence)another) ? another.length() : 0;
        if (len <= 0) {
            if (this.length == 0) {
                result = 1.0f;
                return result;
            }
            result = 0.0f;
            return result;
        }
        if (this.length == 0) {
            result = 0.0f;
            return result;
        }
        int charMatch = 0;
        NumberObject numObj = new NumberObject();
        for (i = 0; i < this.length; ++i) {
            this.charList[i] = this.charList[i] & 0xFFFF00;
        }
        for (i = 0; i < len; ++i) {
            int charData = another.charAt(i) << 8;
            if (!this.findLastInt32(this.charList, charData, numObj)) continue;
            Integer index = numObj.getIndex();
            this.charList[index.intValue()] = this.charList[index] | 1;
            ++charMatch;
        }
        result = (float)charMatch / ((float)(this.length + len - charMatch) + 0.0f);
        if (this.getMin(this.length, len) <= 1) {
            return result;
        }
        int blockMatch = 0;
        for (int i2 = 0; i2 < this.length; ++i2) {
            this.blockList[i2] = this.blockList[i2] & 0xFFFFFFFFFFFFFF00L;
        }
        short[] blockBuffer = new short[len * 2 + 6];
        for (int i3 = 0; i3 < len; ++i3) {
            char c = another.charAt(i3);
            short c0 = (short)c;
            short c1 = (short)(c0 / 256);
            short c2 = (short)(c0 % 256);
            blockBuffer[i3 + 3] = c1;
            blockBuffer[i3 + 3 + 1] = c2;
        }
        long block = 0L;
        for (int i4 = 0; i4 < len; ++i4) {
            block = this.getlong(blockBuffer, i4 * 2, 8) & 0xFFFFFFFFFFFFFF00L;
            if (!this.findLastInt64(this.blockList, block, numObj)) continue;
            Integer index = numObj.getIndex();
            this.blockList[index.intValue()] = this.blockList[index] | 1L;
            ++blockMatch;
        }
        int adjust = this.length + len + blockMatch * 2;
        result = (float)(0.5 * (double)result + 0.5 * (double)(2 * blockMatch + adjust) / (double)(this.length + len + adjust));
        return result;
    }

    private long getlong(short[] BlockBuffer, int fromIndex, int len) {
        long result = 0L;
        for (int i = 0; i < len; ++i) {
            result += (long)((double)BlockBuffer[fromIndex + i] * Math.pow(256.0, i));
        }
        return result;
    }

    private void quickSortInt32(int[] int32List, int start, int stop) {
        int i = 0;
        int j = 0;
        int middle = 0;
        int t = 0;
        do {
            i = start;
            j = stop;
            middle = int32List[start + stop >> 1];
            while (true) {
                if (int32List[i] < middle) {
                    ++i;
                    continue;
                }
                while (int32List[j] > middle) {
                    --j;
                }
                if (i <= j) {
                    t = int32List[i];
                    int32List[i] = int32List[j];
                    int32List[j] = t;
                }
                if (++i > --j) break;
            }
            if (start < j) {
                this.quickSortInt32(int32List, start, j);
            }
            start = i;
        } while (i < stop);
    }

    private boolean findLastInt32(int[] int32List, int toSearch, NumberObject indexObj) {
        boolean result = false;
        int l = 0;
        int h = int32List.length - 1;
        int m = 0;
        int c = 0;
        while (l <= h) {
            m = l + h + 1 >> 1;
            c = int32List[m] - toSearch;
            if (c < 0) {
                l = m + 1;
                continue;
            }
            if (c > 0) {
                h = m - 1;
                continue;
            }
            l = m;
            if (h != l) continue;
            result = true;
            break;
        }
        indexObj.setIndex(l);
        return result;
    }

    private void quickSortInt64(long[] int64List, int start, int stop) {
        int i = 0;
        int j = 0;
        long middle = 0L;
        long t = 0L;
        do {
            i = start;
            j = stop;
            middle = int64List[start + stop >> 1];
            while (true) {
                if (int64List[i] < middle) {
                    ++i;
                    continue;
                }
                while (int64List[j] > middle) {
                    --j;
                }
                if (i <= j) {
                    t = int64List[i];
                    int64List[i] = int64List[j];
                    int64List[j] = t;
                }
                if (++i > --j) break;
            }
            if (start < j) {
                this.quickSortInt64(int64List, start, j);
            }
            start = i;
        } while (i < stop);
    }

    private boolean findLastInt64(long[] int64List, long toSearch, NumberObject indexObj) {
        boolean result = false;
        int l = 0;
        int h = int64List.length - 1;
        int m = 0;
        long c = 0L;
        while (l <= h) {
            m = l + h + 1 >> 1;
            c = int64List[m] - toSearch;
            if (c < 0L) {
                l = m + 1;
                continue;
            }
            if (c > 0L) {
                h = m - 1;
                continue;
            }
            l = m;
            if (h != l) continue;
            result = true;
            break;
        }
        indexObj.setIndex(l);
        return result;
    }

    private int getMin(int i1, int i2) {
        int result = i1;
        if (i2 < i1) {
            result = i2;
        }
        return result;
    }
}

