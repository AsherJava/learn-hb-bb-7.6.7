/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptor
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nvwa.encryption.common.EncryptionException;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.springframework.util.StringUtils;

public class DeployContext {
    private final DataScheme dataScheme;
    private final boolean check;
    private final boolean preDeploy;
    private List<String> ddlSqls;
    private final Set<RefreshTable> refreshTableSet;
    private final DeployResult deployResult;
    private Map<String, String> tableModelCodes;
    private final Map<String, Boolean> tableExistData;
    private Predicate<String> existData;
    private SymmetricEncryptor symmetricEncryptor;

    public DeployContext(DataScheme dataScheme, boolean check) {
        this(dataScheme, check, false);
    }

    public DeployContext(DataScheme dataScheme, boolean check, boolean preDeploy) {
        this.dataScheme = dataScheme;
        this.check = check;
        this.preDeploy = preDeploy;
        this.refreshTableSet = new HashSet<RefreshTable>();
        this.deployResult = new DeployResult();
        if (this.preDeploy) {
            this.ddlSqls = new ArrayList<String>();
        }
        this.tableExistData = new HashMap<String, Boolean>();
        if (StringUtils.hasText(dataScheme.getEncryptScene())) {
            try {
                this.symmetricEncryptor = new SymmetricEncryptor(dataScheme.getEncryptScene());
            }
            catch (EncryptionException e) {
                throw new SchemeDataException("\u52a0\u5bc6\u573a\u666f\u5f02\u5e38", (Throwable)e);
            }
        }
    }

    public String getDataSchemeKey() {
        return this.dataScheme.getKey();
    }

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public DataSchemeType getDataSchemeType() {
        return this.dataScheme.getType();
    }

    public String getDataSchemeBizCode() {
        return this.dataScheme.getBizCode();
    }

    public boolean isCheck() {
        return this.check;
    }

    public boolean isPreDeploy() {
        return this.preDeploy;
    }

    public Set<RefreshTable> getRefreshTableSet() {
        return this.refreshTableSet;
    }

    public DeployResult getDeployResult() {
        return this.deployResult;
    }

    public List<String> getDdlSqls() {
        return this.ddlSqls;
    }

    public void updateTableModelCodes(Map<String, String> tableModelCodes) {
        if (null == this.tableModelCodes) {
            this.tableModelCodes = new HashMap<String, String>();
        }
        this.tableModelCodes.putAll(tableModelCodes);
    }

    public Map<String, String> getTableModelCodes() {
        return this.tableModelCodes;
    }

    public void setExistData(Predicate<String> existData) {
        this.existData = existData;
    }

    public boolean isExistData(String tableName) {
        Boolean data = this.tableExistData.get(tableName);
        if (null == data) {
            data = this.existData.test(tableName);
            this.tableExistData.put(tableName, data);
        }
        return data;
    }

    public SymmetricEncryptor getSymmetricEncryptor() {
        return this.symmetricEncryptor;
    }
}

