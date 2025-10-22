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
public enum TaskFindModeType {
    TASKFIND_DEFAULT(0, "\u6807\u51c6\u6a21\u5f0f(\u6839\u636e\u5355\u673a\u7248\u4efb\u52a1\u6807\u8bc6\u5339\u914d\u62a5\u8868\u65b9\u6848\uff1b\u53bb\u6570\u5b57\u540e\u5339\u914d\u4efb\u52a1)"),
    TASKFIND_CHANGE(1, "\u53ef\u5207\u6362\u6a21\u5f0f\uff08\u53ef\u5207\u6362\u4efb\u52a1\uff0c\u62a5\u8868\u65b9\u6848\uff09"),
    TASKFIND_YEAR(2, "\u6309\u5e74\u5ea6\u6a21\u5f0f\uff08\u53ef\u5207\u6362\u4efb\u52a1\uff0c\u6309\u5e74\u5ea6\u5339\u914d\u62a5\u8868\u65b9\u6848\uff09");

    private final int value;
    private final String title;
    private static final HashMap<Integer, TaskFindModeType> MAP;
    private static final HashMap<String, TaskFindModeType> TITLE_MAP;

    private TaskFindModeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static TaskFindModeType valueOf(int value) {
        return MAP.get(value);
    }

    public static TaskFindModeType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static TaskFindModeType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            TaskFindModeType compareStatusType = MAP.get(value);
            TaskFindModeType byTitle = TITLE_MAP.get(title);
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

    public static TaskFindModeType[] interestType(int kind) {
        if (kind < 0) {
            return new TaskFindModeType[0];
        }
        ArrayList<TaskFindModeType> values = new ArrayList<TaskFindModeType>(TaskFindModeType.values().length);
        for (TaskFindModeType value : TaskFindModeType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new TaskFindModeType[0]);
    }

    static {
        TaskFindModeType[] values = TaskFindModeType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (TaskFindModeType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

