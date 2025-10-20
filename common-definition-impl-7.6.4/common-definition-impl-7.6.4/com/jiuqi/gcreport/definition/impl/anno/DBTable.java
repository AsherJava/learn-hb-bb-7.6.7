/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.enums.ModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 */
package com.jiuqi.gcreport.definition.impl.anno;

import com.jiuqi.budget.param.hypermodel.domain.enums.ModelType;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.anno.DBValue;
import com.jiuqi.gcreport.definition.impl.anno.intf.IDefaultValueEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DBTable {
    public DBValue[] initDatas() default {};

    public Class<? extends IDefaultValueEnum> initDataEnum() default IDefaultValueEnum.class;

    public DBIndex[] indexs() default {};

    @AliasFor(annotation=Component.class, attribute="value")
    public String name();

    public String title();

    public String[] bizkeyfields() default {};

    public TableModelKind kind() default TableModelKind.DEFAULT;

    public DBTableGroup ownerGroupID() default @DBTableGroup;

    public boolean inStorage() default true;

    public TableModelType tableType() default TableModelType.DEFAULT;

    public boolean isNewBaseData() default false;

    public Class<? extends BaseEntity> stopSuper() default BaseEntity.class;

    public boolean primaryRequired() default true;

    public TemporaryTableTypeEnum tempTableType() default TemporaryTableTypeEnum.PHYSICAL;

    public boolean convertToBudModel() default false;

    public ModelType modelType() default ModelType.NORMAL;

    public String dataSource() default "";

    public String sourceTable() default "";

    public boolean extendFieldDefaultVal() default true;
}

