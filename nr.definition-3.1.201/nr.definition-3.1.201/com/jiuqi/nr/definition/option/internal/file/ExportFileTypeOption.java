/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.definition.option.internal.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.common.FileTypeOptionEnum;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.ImportExportGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExportFileTypeOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "EXPORT_FILE_TYPE";
    }

    @Override
    public String getTitle() {
        return "\u6570\u636e\u5bfc\u51fa\u6587\u4ef6\u7c7b\u578b";
    }

    @Override
    public String getDefaultValue() {
        try {
            return new ObjectMapper().writeValueAsString((Object)new String[]{FileTypeOptionEnum.EXCEL.getTitle(), FileTypeOptionEnum.JIO.getTitle()});
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_CHECK_BOX;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> list = new ArrayList<OptionItem>(5);
        for (final FileTypeOptionEnum value : FileTypeOptionEnum.values()) {
            list.add(new OptionItem(){

                @Override
                public String getTitle() {
                    return value.getTitle();
                }

                @Override
                public String getValue() {
                    return value.getTitle();
                }
            });
        }
        return list;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public double getOrder() {
        return 51.0;
    }

    @Override
    public String getPageTitle() {
        return new ImportExportGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new ImportExportGroup().getTitle();
    }
}

