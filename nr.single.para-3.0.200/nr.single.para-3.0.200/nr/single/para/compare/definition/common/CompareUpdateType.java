/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package nr.single.para.compare.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum CompareUpdateType {
    UPDATE_DEFAULT(1, "\u9ed8\u8ba4"),
    UPDATE_NEW(2, "\u65b0\u589e"),
    UPDATE_IGNORE(3, "\u5ffd\u7565"),
    UPDATE_OVER(4, "\u8986\u76d6"),
    UPDATE_UNOVER(5, "\u4e0d\u8986\u76d6"),
    UPDATE_APPOINT(6, "\u6307\u5b9a"),
    UPDATE_RECODE(7, "\u91cd\u65b0\u7f16\u7801"),
    UPDATE_KEEP(8, "\u4fdd\u6301"),
    UPDATA_USENET(9, "\u4ee5\u7f51\u62a5\u4e3a\u51c6"),
    UPDATA_USESINGLE(10, "\u4ee5\u5355\u673a\u7248\u4e3a\u51c6");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareUpdateType> MAP;
    private static final HashMap<String, CompareUpdateType> TITLE_MAP;

    private CompareUpdateType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareUpdateType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareUpdateType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareUpdateType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareUpdateType compareStatusType = MAP.get(value);
            CompareUpdateType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals((Object)compareStatusType) ? compareStatusType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    public static CompareUpdateType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareUpdateType[0];
        }
        ArrayList<CompareUpdateType> values = new ArrayList<CompareUpdateType>(CompareUpdateType.values().length);
        for (CompareUpdateType value : CompareUpdateType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareUpdateType[0]);
    }

    static {
        CompareUpdateType[] values = CompareUpdateType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareUpdateType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

