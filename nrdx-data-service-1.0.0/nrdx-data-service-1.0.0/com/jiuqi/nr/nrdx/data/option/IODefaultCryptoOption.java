/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.AffectedMode
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.core.RelationTaskOptionItem
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 *  com.jiuqi.nvwa.encryption.crypto.domain.pojo.SymmetricAlgorithmInfo
 *  com.jiuqi.nvwa.encryption.crypto.service.CryptoService
 */
package com.jiuqi.nr.nrdx.data.option;

import com.jiuqi.nr.definition.option.core.AffectedMode;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import com.jiuqi.nr.nrdx.data.option.IOCryptoGroup;
import com.jiuqi.nvwa.encryption.crypto.domain.pojo.SymmetricAlgorithmInfo;
import com.jiuqi.nvwa.encryption.crypto.service.CryptoService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IODefaultCryptoOption
implements TaskOptionDefine {
    @Autowired
    private CryptoService cryptoService;
    public static final String KEY = "IO_CRYPTO_DEFAULT_ALGORITHM";
    public static final String TITLE = "\u52a0\u5bc6\u7b97\u6cd5";

    public String getKey() {
        return KEY;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getDefaultValue() {
        return null;
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_DROP_DOWN;
    }

    public List<OptionItem> getOptionItems() {
        List allSymmetricAlgorithmInfos = this.cryptoService.getAllSymmetricAlgorithmInfos();
        if (allSymmetricAlgorithmInfos == null || allSymmetricAlgorithmInfos.isEmpty()) {
            return Collections.emptyList();
        }
        return allSymmetricAlgorithmInfos.stream().map(e -> new OptionItem((SymmetricAlgorithmInfo)e){
            final /* synthetic */ SymmetricAlgorithmInfo val$e;
            {
                this.val$e = symmetricAlgorithmInfo;
            }

            public String getTitle() {
                return this.val$e.getAlgorithmName();
            }

            public String getValue() {
                return this.val$e.getId();
            }
        }).collect(Collectors.toList());
    }

    public String getRegex() {
        return null;
    }

    public double getOrder() {
        return 200.0;
    }

    public String getPageTitle() {
        return new IOCryptoGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return "NRDX\u5bfc\u51fa\u52a0\u5bc6";
    }

    public boolean canEmpty() {
        return false;
    }

    public RelationTaskOptionItem getRelationTaskOptionItem() {
        RelationTaskOptionItem relationTaskOptionItem = new RelationTaskOptionItem();
        relationTaskOptionItem.setFromKey("IO_CRYPTO_SWITCH");
        relationTaskOptionItem.setAffectedMode(AffectedMode.VISIBLE);
        relationTaskOptionItem.setFromValues(Collections.singletonList("1"));
        return relationTaskOptionItem;
    }
}

