/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package nr.single.para.upload.domain;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.ZBInfoDO;

public class ZBInfoDTO
extends ZBInfoDO {
    public ZBInfoDTO() {
    }

    public ZBInfoDTO(String title, String value) {
        super(title, value);
    }

    public static List<ZBInfoDTO> getInfoByAttribute(IEntityAttribute attribute) {
        ArrayList<ZBInfoDTO> infos = new ArrayList<ZBInfoDTO>();
        infos.add(new ZBInfoDTO("\u4e3b\u952e", attribute.getID()));
        infos.add(new ZBInfoDTO("\u6807\u8bc6", attribute.getCode()));
        infos.add(new ZBInfoDTO("\u6807\u9898", attribute.getTitle()));
        infos.add(new ZBInfoDTO("\u8868\u6a21\u578b\u6807\u8bc6", attribute.getTableID()));
        infos.add(new ZBInfoDTO("\u7269\u7406\u4ee3\u7801", attribute.getName()));
        infos.add(new ZBInfoDTO("\u63cf\u8ff0", attribute.getDesc()));
        infos.add(new ZBInfoDTO("\u5b57\u6bb5\u5206\u7ec4", attribute.getCatagory()));
        infos.add(new ZBInfoDTO("\u7c7b\u578b", attribute.getColumnType().name()));
        infos.add(new ZBInfoDTO("\u7cbe\u5ea6", String.valueOf(attribute.getPrecision())));
        infos.add(new ZBInfoDTO("\u5c0f\u6570\u4f4d", String.valueOf(attribute.getDecimal())));
        infos.add(new ZBInfoDTO("\u5141\u8bb8\u4e3a\u7a7a", attribute.isNullAble() ? "\u662f" : "\u5426"));
        infos.add(new ZBInfoDTO("\u9ed8\u8ba4\u503c", attribute.getDefaultValue()));
        infos.add(new ZBInfoDTO("\u5173\u8054\u8868", attribute.getReferTableID()));
        infos.add(new ZBInfoDTO("\u5173\u8054\u5b57\u6bb5", attribute.getReferColumnID()));
        infos.add(new ZBInfoDTO("\u5173\u8054\u8fc7\u6ee4\u6761\u4ef6", attribute.getFilter()));
        infos.add(new ZBInfoDTO("\u662f\u5426\u591a\u503c\u9009\u62e9\u6a21\u5f0f", attribute.isMultival() ? "\u662f" : "\u5426"));
        infos.add(new ZBInfoDTO("\u805a\u5408\u65b9\u5f0f", attribute.getAggrType().name()));
        infos.add(new ZBInfoDTO("\u5e94\u7528\u7c7b\u578b", attribute.getApplyType().name()));
        infos.add(new ZBInfoDTO("\u663e\u793a\u683c\u5f0f\u5b57\u7b26\u4e32", attribute.getShowFormat()));
        infos.add(new ZBInfoDTO("\u5355\u4f4d\u91cf\u7eb2", attribute.getMeasureUnit()));
        infos.add(new ZBInfoDTO("\u662f\u5426\u4e3a\u7cfb\u7edf\u5b57\u6bb5", attribute.getKind().name()));
        infos.add(new ZBInfoDTO("\u5b57\u6bb5\u987a\u5e8f", String.valueOf(attribute.getOrder())));
        infos.add(new ZBInfoDTO("\u662f\u5426\u652f\u6301\u591a\u8bed\u8a00", attribute.isSupportI18n() ? "\u662f" : "\u5426"));
        return infos;
    }

    public static List<ZBInfoDTO> getInfoByDataField(DataField dataField) {
        ArrayList<ZBInfoDTO> infos = new ArrayList<ZBInfoDTO>();
        infos.add(new ZBInfoDTO("\u4e3b\u952e", dataField.getKey()));
        infos.add(new ZBInfoDTO("\u6807\u8bc6", dataField.getCode()));
        infos.add(new ZBInfoDTO("\u540d\u79f0", dataField.getTitle()));
        infos.add(new ZBInfoDTO("\u63cf\u8ff0", dataField.getDesc()));
        infos.add(new ZBInfoDTO("\u66f4\u65b0\u65f6\u95f4", dataField.getUpdateTime().toString()));
        infos.add(new ZBInfoDTO("\u522b\u540d", dataField.getAlias()));
        infos.add(new ZBInfoDTO("\u662f\u5426\u5141\u8bb8\u4e3a\u7a7a", dataField.isNullable() ? "\u662f" : "\u5426"));
        infos.add(new ZBInfoDTO("\u6570\u636e\u65b9\u6848key", dataField.getDataSchemeKey()));
        infos.add(new ZBInfoDTO("\u6240\u5c5e\u6570\u636e\u8868", dataField.getDataTableKey()));
        infos.add(new ZBInfoDTO("\u6307\u6807\u7c7b\u522b", dataField.getDataFieldKind().name()));
        infos.add(new ZBInfoDTO("\u7248\u672c", dataField.getVersion()));
        infos.add(new ZBInfoDTO("\u7ea7\u522b", dataField.getLevel()));
        infos.add(new ZBInfoDTO("\u9ed8\u8ba4\u503c", dataField.getDefaultValue()));
        infos.add(new ZBInfoDTO("\u957f\u5ea6", String.valueOf(dataField.getPrecision())));
        infos.add(new ZBInfoDTO("\u6307\u6807\u7cbe\u5ea6", String.valueOf(dataField.getDecimal())));
        infos.add(new ZBInfoDTO("\u6307\u6807\u5e94\u7528\u7c7b\u578b", dataField.getDataFieldApplyType().name()));
        infos.add(new ZBInfoDTO("\u6307\u6807\u7c7b\u578b", dataField.getDataFieldType().name()));
        infos.add(new ZBInfoDTO("\u5173\u8054\u5b9e\u4f53\u4e3b\u952e", dataField.getRefDataEntityKey()));
        infos.add(new ZBInfoDTO("\u5173\u8054\u4e1a\u52a1\u4e3b\u952e", dataField.getRefDataFieldKey()));
        infos.add(new ZBInfoDTO("\u5bc6\u7ea7", String.valueOf(dataField.getSecretLevel())));
        infos.add(new ZBInfoDTO("\u8ba1\u91cf\u5355\u4f4d", dataField.getMeasureUnit()));
        infos.add(new ZBInfoDTO("\u6c47\u603b\u7c7b\u578b", dataField.getDataFieldGatherType().name()));
        if (dataField.getAllowMultipleSelect() != null) {
            infos.add(new ZBInfoDTO("\u662f\u5426\u5141\u8bb8\u591a\u9009", dataField.getAllowMultipleSelect() != false ? "\u662f" : "\u5426"));
        }
        if (dataField.getAllowUndefinedCode() != null) {
            infos.add(new ZBInfoDTO("\u662f\u5426\u5141\u8bb8\u672a\u5b9a\u4e49\u7f16\u7801", dataField.getAllowUndefinedCode() != false ? "\u662f" : "\u5426"));
        }
        return infos;
    }
}

