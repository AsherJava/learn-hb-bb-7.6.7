/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import net.ttddyy.dsproxy.QueryType;

public class QueryUtils {
    public static String removeCommentAndWhiteSpace(String query) {
        if (query == null) {
            return null;
        }
        return query.replace("--.*\n", "").replace("\n", "").replace("/\\*.*\\*/", "").trim();
    }

    public static QueryType getQueryType(String query) {
        QueryType type;
        String trimmedQuery = QueryUtils.removeCommentAndWhiteSpace(query);
        if (trimmedQuery == null || trimmedQuery.length() < 1) {
            return QueryType.OTHER;
        }
        char firstChar = trimmedQuery.charAt(0);
        switch (firstChar) {
            case 'S': 
            case 's': {
                type = QueryType.SELECT;
                break;
            }
            case 'I': 
            case 'i': {
                type = QueryType.INSERT;
                break;
            }
            case 'U': 
            case 'u': {
                type = QueryType.UPDATE;
                break;
            }
            case 'D': 
            case 'd': {
                type = QueryType.DELETE;
                break;
            }
            default: {
                type = QueryType.OTHER;
            }
        }
        return type;
    }
}

