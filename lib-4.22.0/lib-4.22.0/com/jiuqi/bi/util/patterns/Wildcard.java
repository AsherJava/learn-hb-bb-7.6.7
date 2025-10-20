/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Wildcard
implements Iterable<Object> {
    private String pattern;
    private List<Object> items;
    private boolean caseSensitive;
    @Deprecated
    public static final char DEFAULT_WILDCHAR = '%';
    public static final char DEFAULT_MULTI_WILDCHAR = '%';
    public static final char DEFAULT_SINGLE_WILDCHAR = '_';
    public static final char IGNORE_WILDCARD = '\u0000';
    private final Character MULTI_WILDCARD;
    private final Character SINGLE_WILDCARD;

    public Wildcard(String pattern) {
        this(pattern, '%', '\u0000');
    }

    public Wildcard(String pattern, boolean caseSensitive) {
        this(pattern, '%', '\u0000', caseSensitive);
    }

    public Wildcard(String pattern, char multiWildchar) {
        this(pattern, multiWildchar, '\u0000');
    }

    public Wildcard(String pattern, char multiWildchar, boolean caseSensitive) {
        this(pattern, multiWildchar, '\u0000', caseSensitive);
    }

    public Wildcard(String pattern, char multiWildchar, char singleWildchar) {
        this(pattern, multiWildchar, singleWildchar, false);
    }

    public Wildcard(String pattern, char multiWildchar, char singleWildchar, boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        this.MULTI_WILDCARD = Character.valueOf(multiWildchar);
        this.SINGLE_WILDCARD = Character.valueOf(singleWildchar);
        this.items = new ArrayList<Object>();
        this.pattern = pattern;
        if (pattern == null || pattern.length() == 0) {
            return;
        }
        int cur = 0;
        StringBuilder buffer = new StringBuilder(pattern.length());
        while (cur < pattern.length()) {
            char ch = pattern.charAt(cur);
            if (ch == multiWildchar) {
                if (cur < pattern.length() - 1 && pattern.charAt(cur + 1) == multiWildchar) {
                    buffer.append(multiWildchar);
                    cur += 2;
                    continue;
                }
                if (buffer.length() > 0) {
                    this.items.add(this.wrapStr(buffer.toString()));
                    buffer.setLength(0);
                }
                this.items.add(this.MULTI_WILDCARD);
                ++cur;
                continue;
            }
            if (ch == singleWildchar) {
                if (cur < pattern.length() - 1 && pattern.charAt(cur + 1) == singleWildchar) {
                    buffer.append(singleWildchar);
                    cur += 2;
                    continue;
                }
                if (buffer.length() > 0) {
                    this.items.add(this.wrapStr(buffer.toString()));
                    buffer.setLength(0);
                }
                this.items.add(this.SINGLE_WILDCARD);
                ++cur;
                continue;
            }
            buffer.append(ch);
            ++cur;
        }
        if (buffer.length() > 0) {
            this.items.add(this.wrapStr(buffer.toString()));
        }
    }

    private String wrapStr(String str) {
        return this.caseSensitive ? str : str.toUpperCase();
    }

    public boolean match(String s) {
        if (s == null || this.items.size() == 0) {
            return false;
        }
        return this.matchFrom(this.wrapStr(s), 0, 0);
    }

    private boolean matchFrom(String s, int level, int fromIndex) {
        Object curItem;
        Object lastItem = null;
        Object object = curItem = level == 0 ? null : this.items.get(level - 1);
        while (level < this.items.size()) {
            lastItem = curItem;
            curItem = this.items.get(level);
            if (curItem == this.MULTI_WILDCARD) {
                ++level;
                continue;
            }
            if (fromIndex == s.length()) {
                return false;
            }
            if (curItem == this.SINGLE_WILDCARD) {
                ++level;
                do {
                    if (!this.matchFrom(s, level, ++fromIndex)) continue;
                    return true;
                } while (lastItem == this.MULTI_WILDCARD && fromIndex < s.length());
                continue;
            }
            String subStr = (String)this.items.get(level);
            if (lastItem == this.MULTI_WILDCARD) {
                while (fromIndex < s.length()) {
                    int next = s.indexOf(subStr, fromIndex);
                    if (next == -1 || level == 0 && next > 0) {
                        return false;
                    }
                    fromIndex = next + subStr.length();
                    if (!this.matchFrom(s, level + 1, fromIndex)) continue;
                    return true;
                }
                return false;
            }
            if (s.startsWith(subStr, fromIndex)) {
                return this.matchFrom(s, level + 1, fromIndex + subStr.length());
            }
            return false;
        }
        if (fromIndex < s.length()) {
            return this.items.get(this.items.size() - 1) == this.MULTI_WILDCARD;
        }
        return true;
    }

    public String toString() {
        return this.pattern;
    }

    public static boolean match(String s, String pattern) {
        Wildcard wildcard = new Wildcard(pattern);
        return wildcard.match(s);
    }

    public static boolean match(String s, String pattern, char multiWildchar) {
        Wildcard wildcard = new Wildcard(pattern, multiWildchar);
        return wildcard.match(s);
    }

    public static boolean match(String s, String pattern, char multiWildchar, char singleWildchar) {
        Wildcard wildcard = new Wildcard(pattern, multiWildchar, singleWildchar);
        return wildcard.match(s);
    }

    public static int findFirstWildchar(String pattern, String wildchars) {
        if (pattern == null || wildchars == null) {
            return -1;
        }
        int cur = 0;
        while (cur < pattern.length()) {
            char ch = pattern.charAt(cur);
            if (wildchars.indexOf(ch) < 0) {
                ++cur;
                continue;
            }
            if (cur < pattern.length() - 1 && pattern.charAt(cur + 1) == ch) {
                cur += 2;
                continue;
            }
            return cur;
        }
        return -1;
    }

    @Override
    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(this.items).iterator();
    }
}

