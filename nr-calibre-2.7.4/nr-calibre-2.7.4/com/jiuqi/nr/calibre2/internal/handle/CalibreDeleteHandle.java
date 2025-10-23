/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.handle;

import com.jiuqi.nr.calibre2.api.ICalibreCheckReference;
import com.jiuqi.nr.calibre2.api.ICallBackCalibreCheck;
import com.jiuqi.nr.calibre2.api.IReferenceCalibre;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class CalibreDeleteHandle {
    @Autowired
    private List<ICallBackCalibreCheck> callBackCalibreCheckList;
    private final Delete delete;

    public CalibreDeleteHandle() {
        this.delete = new SafeDelete();
    }

    public CalibreDeleteHandle(boolean safe) {
        this.delete = safe ? new SafeDelete() : new UnSafeDelete();
    }

    public void run(String calibreDefine, List<String> calibreKey) {
        this.delete.run(calibreDefine, calibreKey);
    }

    final class UnSafeDelete
    extends Delete {
        UnSafeDelete() {
        }

        @Override
        void beforeDelete() {
        }

        @Override
        void run() {
            if (this.tryDelete()) {
                this.afterDelete();
            }
        }
    }

    final class SafeDelete
    extends Delete {
        SafeDelete() {
        }

        @Override
        void beforeDelete() {
            ArrayList allCalibreKeys = new ArrayList();
            for (ICallBackCalibreCheck calibreCheck : CalibreDeleteHandle.this.callBackCalibreCheckList) {
                ICalibreCheckReference check = calibreCheck.check(this.calibreDefine, this.calibreKey);
                List<IReferenceCalibre> references = check.getReferences();
                List enableDeleteKeys = references.stream().map(IReferenceCalibre::calibreDataCode).filter(check::enableDelete).collect(Collectors.toList());
                allCalibreKeys.add(enableDeleteKeys);
            }
            this.calibreKey = allCalibreKeys.stream().reduce((l1, l2) -> {
                l1.retainAll((Collection<?>)l2);
                return l1;
            }).orElse(Collections.emptyList());
        }

        @Override
        void run() {
            this.beforeDelete();
            if (this.tryDelete()) {
                this.afterDelete();
            }
        }
    }

    static abstract class Delete {
        protected String calibreDefine;
        protected List<String> calibreKey;

        Delete() {
        }

        abstract void beforeDelete();

        void run(String calibreDefine, List<String> calibreKey) {
            this.calibreDefine = calibreDefine;
            this.calibreKey = calibreKey;
            this.run();
        }

        abstract void run();

        boolean tryDelete() {
            return false;
        }

        void afterDelete() {
        }
    }
}

