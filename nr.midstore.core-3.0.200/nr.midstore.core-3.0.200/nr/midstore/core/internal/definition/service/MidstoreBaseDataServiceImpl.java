/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.midstore.core.internal.definition.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.midstore.core.definition.dao.IMidstoreDataDao;
import nr.midstore.core.definition.db.Convert;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;
import nr.midstore.core.definition.service.IMidstoreBaseDataService;
import nr.midstore.core.internal.definition.MidstoreBaseDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreBaseDataServiceImpl
implements IMidstoreBaseDataService {
    @Autowired
    private IMidstoreDataDao<MidstoreBaseDataDO> midstoreDataDao;
    private final Function<MidstoreBaseDataDO, MidstoreBaseDataDTO> toDto = Convert::mdb2Do;
    private final Function<List<MidstoreBaseDataDO>, List<MidstoreBaseDataDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<MidstoreBaseDataDTO> list(MidstoreBaseDataDTO midstoreDataDTO) {
        ArrayList<MidstoreBaseDataDTO> list = new ArrayList<MidstoreBaseDataDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreBaseDataDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            List<MidstoreBaseDataDO> list2 = this.midstoreDataDao.getBySchemeKey(midstoreDataDTO.getSchemeKey());
            list.addAll((Collection<MidstoreBaseDataDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(MidstoreBaseDataDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreBaseDataDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreBaseDataDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            this.midstoreDataDao.deleteBySchemeKey(midstoreDataDTO.getSchemeKey());
        }
    }

    @Override
    public void batchAdd(List<MidstoreBaseDataDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreBaseDataDTO> list2 = new ArrayList<MidstoreBaseDataDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreBaseDataDTO> list2 = new ArrayList<MidstoreBaseDataDTO>();
            for (int i = 0; i < midstoreDataDTOs.size(); ++i) {
                if (list2.size() >= 600) {
                    this.midstoreDataDao.batchInsert(list2);
                    list2.clear();
                }
                list2.add(midstoreDataDTOs.get(i));
            }
            if (list2.size() > 0) {
                this.midstoreDataDao.batchInsert(list2);
            }
        }
    }

    @Override
    public void batchUpdate(List<MidstoreBaseDataDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreBaseDataDTO> list2 = new ArrayList<MidstoreBaseDataDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreBaseDataDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreBaseDataDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

