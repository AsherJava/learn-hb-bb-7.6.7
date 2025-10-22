/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nr.single.para.compare.definition.CompareAttributeDTO;
import nr.single.para.compare.definition.ICompareAttribute;
import nr.single.para.compare.definition.ISingleCompareAttributeService;
import nr.single.para.compare.definition.common.CompareDataType;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareAttributeServiceImpl
implements ISingleCompareAttributeService {
    @Override
    public List<ICompareAttribute> list(CompareAttributeDTO compareAttrDTO) {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        if (compareAttrDTO.getCompareType() == null) {
            list.addAll(this.getFMDMFieldAttrs());
            list.addAll(this.getEnumAttrs());
            list.addAll(this.getEnumDataAttrs());
            list.addAll(this.getFormAttrs());
            list.addAll(this.getFieldAttrs());
            list.addAll(this.getPrintAttrs());
            list.addAll(this.getTaskLinkAttrs());
        }
        return list;
    }

    private List<ICompareAttribute> getFMDMFieldAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "Key", "KEY", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFieldCode", "\u5355\u673a\u7248\u6307\u6807\u6807\u8bc6", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFieldTitle", "\u5355\u673a\u7248\u6307\u6807\u540d\u79f0", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFieldAttr", "\u5c5e\u6027", CompareDataType.DATA_FMDMFIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFieldKey", "\u7f51\u62a5\u6307\u6807Key", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFieldCode", "\u7f51\u62a5\u6307\u6807\u6807\u8bc6", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFieldTitle", "\u7f51\u62a5\u6307\u6807\u540d\u79f0", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "ChangeType", "\u53d8\u5316\u5185\u5bb9", CompareDataType.DATA_FMDMFIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "UpdateType", "\u8986\u76d6\u65b9\u5f0f", CompareDataType.DATA_FMDMFIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "OwnerTableType", "\u6240\u5c5e\u8868\u7c7b\u578b", CompareDataType.DATA_FMDMFIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "OwnerTableKey", "\u6240\u5c5e\u8868Key", CompareDataType.DATA_FMDMFIELD, ColumnModelType.STRING, 40, 0, false));
        return list;
    }

    private List<ICompareAttribute> getEnumAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "Key", "KEY", CompareDataType.DATA_ENUM, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleEnumCode", "\u5355\u673a\u7248\u679a\u4e3e\u6807\u8bc6", CompareDataType.DATA_ENUM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleEnumTitle", "\u5355\u673a\u7248\u679a\u4e3e\u540d\u79f0", CompareDataType.DATA_ENUM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetEnumKey", "\u7f51\u62a5\u679a\u4e3eId", CompareDataType.DATA_ENUM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetEnumCode", "\u7f51\u62a5\u679a\u4e3e\u6807\u8bc6", CompareDataType.DATA_ENUM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetEnumTitle", "\u7f51\u62a5\u679a\u4e3e\u540d\u79f0", CompareDataType.DATA_ENUM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "ChangeType", "\u53d8\u5316\u5185\u5bb9", CompareDataType.DATA_ENUM, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "UpdateType", "\u8986\u76d6\u65b9\u5f0f", CompareDataType.DATA_ENUM, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "DataCompareType", "\u679a\u4e3e\u9879\u5339\u914d\u65b9\u5f0f", CompareDataType.DATA_ENUM, ColumnModelType.INTEGER, 0, 0, false));
        return list;
    }

    private List<ICompareAttribute> getEnumDataAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "Key", "KEY", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleEnumItemCode", "\u5355\u673a\u7248\u679a\u4e3e\u9879\u7f16\u7801", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleEnumItemTitle", "\u5355\u673a\u7248\u679a\u4e3e\u9879\u542b\u4e49", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetEnumItemKey", "\u7f51\u62a5\u679a\u4e3e\u9879Key", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetEnumItemCode", "\u7f51\u62a5\u679a\u4e3e\u9879\u7f16\u7801", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetEnumItemTitle", "\u7f51\u62a5\u679a\u4e3e\u9879\u542b\u4e49", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "ChangeType", "\u53d8\u5316\u5185\u5bb9", CompareDataType.DATA_ENUMITEM, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "UpdateType", "\u8986\u76d6\u65b9\u5f0f", CompareDataType.DATA_ENUMITEM, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "enumCompareId", "enumCompareId", CompareDataType.DATA_ENUMITEM, ColumnModelType.STRING, 40, 0, false));
        return list;
    }

    private List<ICompareAttribute> getFormAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "Key", "KEY", CompareDataType.DATA_FORM, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFormCode", "\u5355\u673a\u7248\u62a5\u8868\u6807\u8bc6", CompareDataType.DATA_FORM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFormTitile", "\u5355\u673a\u7248\u62a5\u8868\u540d\u79f0", CompareDataType.DATA_FORM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFormData", "\u5355\u673a\u7248\u62a5\u8868\u8868\u6837", CompareDataType.DATA_FORM, ColumnModelType.BLOB, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormId", "\u7f51\u62a5\u62a5\u8868Key", CompareDataType.DATA_FORM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormCode", "\u7f51\u62a5\u62a5\u8868\u6807\u8bc6", CompareDataType.DATA_FORM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormTitle", "\u7f51\u62a5\u62a5\u8868\u540d\u79f0", CompareDataType.DATA_FORM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFormData", "\u7f51\u62a5\u62a5\u8868\u8868\u6837", CompareDataType.DATA_FORM, ColumnModelType.BLOB, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "ChangeTypes", "\u53d8\u5316\u5185\u5bb9", CompareDataType.DATA_FORM, ColumnModelType.STRING, 30, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "UpdateType", "\u8986\u76d6\u65b9\u5f0f", CompareDataType.DATA_FORM, ColumnModelType.INTEGER, 0, 0, false));
        return list;
    }

    private List<ICompareAttribute> getFieldAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "Key", "KEY", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFieldCode", "\u5355\u673a\u7248\u6307\u6807\u6807\u8bc6", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFieldTitle", "\u5355\u673a\u7248\u6307\u6807\u540d\u79f0", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleMatchTitle", "\u5355\u673a\u7248\u6307\u6807\u5339\u914d\u540d\u79f0", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleRegionId", "\u5355\u673a\u7248\u6307\u6807\u6240\u5728\u533a\u57df", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SinglePox", "\u5355\u673a\u7248\u6307\u6807x\u5750\u6807", CompareDataType.DATA_FIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SinglePoY", "\u5355\u673a\u7248\u6307\u6807Y\u5750\u6807", CompareDataType.DATA_FIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFieldKey", "\u7f51\u62a5\u6307\u6807Key", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFieldCode", "\u7f51\u62a5\u6307\u6807\u6807\u8bc6", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFieldTitle", "\u7f51\u62a5\u6307\u6807\u540d\u79f0", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetMatchTitle", "\u5355\u673a\u7248\u6307\u6807\u5339\u914d\u540d\u79f0", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetRegionId", "\u5355\u673a\u7248\u6307\u6807\u540d\u79f0", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetPox", "\u5355\u673a\u7248\u6307\u6807x\u5750\u6807", CompareDataType.DATA_FIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetPoY", "\u5355\u673a\u7248\u6307\u6807Y\u5750\u6807", CompareDataType.DATA_FIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "ChangeType", "\u53d8\u5316\u5185\u5bb9", CompareDataType.DATA_FIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "UpdateType", "\u8986\u76d6\u65b9\u5f0f", CompareDataType.DATA_FIELD, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "formCompareId", "enumCompareId", CompareDataType.DATA_FIELD, ColumnModelType.STRING, 40, 0, false));
        return list;
    }

    private List<ICompareAttribute> getPrintAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "Key", "KEY", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SinglePrintScheme", "\u5355\u673a\u7248\u6253\u5370\u65b9\u6848\u540d\u79f0", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFormCode", "\u5355\u673a\u7248\u62a5\u8868\u6807\u8bc6", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleFormTitile", "\u5355\u673a\u7248\u62a5\u8868\u540d\u79f0", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetPrintScheme", "\u7f51\u62a5\u6253\u5370\u65b9\u6848\u540d\u79f0", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetPrintSchemeKey", "\u7f51\u62a5\u6253\u5370\u65b9\u6848Key", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormKey", "\u7f51\u62a5\u62a5\u8868Key", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormCode", "\u7f51\u62a5\u62a5\u8868\u6807\u8bc6", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormTitle", "\u7f51\u62a5\u62a5\u8868\u540d\u79f0", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "ChangeTypes", "\u53d8\u5316\u5185\u5bb9", CompareDataType.DATA_PRINTTITEM, ColumnModelType.STRING, 30, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "UpdateType", "\u8986\u76d6\u65b9\u5f0f", CompareDataType.DATA_PRINTTITEM, ColumnModelType.INTEGER, 0, 0, false));
        return list;
    }

    private List<ICompareAttribute> getTaskLinkAttrs() {
        ArrayList<ICompareAttribute> list = new ArrayList<ICompareAttribute>();
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "id", "KEY", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 40, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleTaskCode", "\u5355\u673a\u7248\u62a5\u8868\u6807\u8bc6", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleTaskTitile", "\u5355\u673a\u7248\u62a5\u8868\u540d\u79f0", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 300, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleTaskYear", "\u5355\u673a\u7248\u4efb\u52a1\u5e74\u5ea6", CompareDataType.DATA_TASKLINK, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "SingleTaskType", "\u5355\u673a\u7248\u4efb\u52a1\u7c7b\u578b", CompareDataType.DATA_TASKLINK, ColumnModelType.INTEGER, 0, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetTaskId", "\u7f51\u62a5\u4efb\u52a1Key", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetFormSchemeKey", "\u7f51\u62a5\u62a5\u8868\u6807\u8bc6", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 50, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetCurrentEpression", "\u5f53\u524d\u8868\u8fbe\u5f0f", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 30, 0, false));
        list.add(this.getNewAttr(UUID.randomUUID().toString(), "NetLinkEpression", "\u5bf9\u5e94\u8868\u8fbe\u5f0f", CompareDataType.DATA_TASKLINK, ColumnModelType.STRING, 0, 0, false));
        return list;
    }

    private ICompareAttribute getNewAttr(String key, String code, String title, CompareDataType compareType, ColumnModelType columnType, int precision, int decimal, boolean nullAble) {
        CompareAttributeDTO attr = new CompareAttributeDTO();
        attr.setKey(key);
        attr.setCode(code);
        attr.setTitle(title);
        attr.setCompareType(compareType);
        attr.setColumnType(columnType);
        attr.setPrecision(precision);
        attr.setDecimal(decimal);
        attr.setNullAble(nullAble);
        return attr;
    }
}

