/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkTypeUtil {
    private static final Logger logger = LoggerFactory.getLogger(LinkTypeUtil.class);

    public static LinkType getType(FieldType fieldType) {
        switch (fieldType) {
            case FIELD_TYPE_FLOAT: {
                return LinkType.LINK_TYPE_FLOAT;
            }
            case FIELD_TYPE_STRING: {
                return LinkType.LINK_TYPE_STRING;
            }
            case FIELD_TYPE_INTEGER: {
                return LinkType.LINK_TYPE_INTEGER;
            }
            case FIELD_TYPE_LOGIC: {
                return LinkType.LINK_TYPE_BOOLEAN;
            }
            case FIELD_TYPE_DATE: {
                return LinkType.LINK_TYPE_DATE;
            }
            case FIELD_TYPE_DATE_TIME: {
                return LinkType.LINK_TYPE_DATETIME;
            }
            case FIELD_TYPE_TIME: {
                return LinkType.LINK_TYPE_TIME;
            }
            case FIELD_TYPE_UUID: {
                return LinkType.LINK_TYPE_UUID;
            }
            case FIELD_TYPE_DECIMAL: {
                return LinkType.LINK_TYPE_DECIMAL;
            }
            case FIELD_TYPE_TIME_STAMP: {
                return LinkType.LINK_TYPE_ERROR;
            }
            case FIELD_TYPE_TEXT: {
                return LinkType.LINK_TYPE_TEXT;
            }
            case FIELD_TYPE_PICTURE: {
                return LinkType.LINK_TYPE_PICTURE;
            }
            case FIELD_TYPE_BINARY: {
                return LinkType.LINK_TYPE_ERROR;
            }
            case FIELD_TYPE_FILE: {
                return LinkType.LINK_TYPE_FILE;
            }
            case FIELD_TYPE_ENUM: {
                return LinkType.LINK_TYPE\uff3fENUM;
            }
            case FIELD_TYPE_LATITUDE_LONGITUDE: {
                return LinkType.LINK_TYPE_ERROR;
            }
            case FIELD_TYPE_QRCODE: {
                return LinkType.LINK_TYPE_ERROR;
            }
            case FIELD_TYPE_OBJECT_ARRAY: {
                return LinkType.LINK_TYPE_ERROR;
            }
            case FIELD_TYPE_ERROR: {
                return LinkType.LINK_TYPE_ERROR;
            }
        }
        return LinkType.LINK_TYPE_ERROR;
    }

    public static LinkType getType(ColumnModelType columnModelType) {
        switch (columnModelType) {
            case BOOLEAN: {
                return LinkType.LINK_TYPE_BOOLEAN;
            }
            case DOUBLE: {
                return LinkType.LINK_TYPE_FLOAT;
            }
            case STRING: {
                return LinkType.LINK_TYPE_STRING;
            }
            case INTEGER: {
                return LinkType.LINK_TYPE_INTEGER;
            }
            case BIGDECIMAL: {
                return LinkType.LINK_TYPE_DECIMAL;
            }
            case DATETIME: {
                return LinkType.LINK_TYPE_DATETIME;
            }
            case CLOB: {
                return LinkType.LINK_TYPE_TEXT;
            }
            case BLOB: {
                return LinkType.LINK_TYPE_ERROR;
            }
            case ATTACHMENT: {
                return LinkType.LINK_TYPE_FILE;
            }
            case UUID: {
                return LinkType.LINK_TYPE_UUID;
            }
        }
        return LinkType.LINK_TYPE_ERROR;
    }
}

