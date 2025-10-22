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
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.internal.definition.MidstoreSchemeInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreSchemeInfoServiceImpl
implements IMidstoreSchemeInfoService {
    @Autowired
    private IMidstoreDataDao<MidstoreSchemeInfoDO> midstoreDataDao;
    private final Function<MidstoreSchemeInfoDO, MidstoreSchemeInfoDTO> toDto = Convert::mdi2Do;
    private final Function<List<MidstoreSchemeInfoDO>, List<MidstoreSchemeInfoDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public MidstoreSchemeInfoDTO getByKey(String infoKey) {
        MidstoreSchemeInfoDO obj = this.midstoreDataDao.get(infoKey);
        return this.toDto.apply(obj);
    }

    @Override
    public MidstoreSchemeInfoDTO getBySchemeKey(String schemeKey) {
        List<MidstoreSchemeInfoDO> objs = this.midstoreDataDao.getBySchemeKey(schemeKey);
        List<MidstoreSchemeInfoDTO> list = this.list2Dto.apply(objs);
        MidstoreSchemeInfoDTO result = null;
        if (list != null && list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public List<MidstoreSchemeInfoDTO> list(MidstoreSchemeInfoDTO midstoreDataDTO) {
        ArrayList<MidstoreSchemeInfoDTO> list = new ArrayList<MidstoreSchemeInfoDTO>();
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            MidstoreSchemeInfoDO obj = this.midstoreDataDao.get(midstoreDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)midstoreDataDTO.getSchemeKey())) {
            List<MidstoreSchemeInfoDO> objs = this.midstoreDataDao.getBySchemeKey(midstoreDataDTO.getSchemeKey());
            list.addAll((Collection<MidstoreSchemeInfoDTO>)this.list2Dto.apply(objs));
        } else {
            list.addAll((Collection)this.list2Dto.apply(this.midstoreDataDao.getAll()));
        }
        return list;
    }

    @Override
    public void add(MidstoreSchemeInfoDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.insert(midstoreDataDTO);
    }

    @Override
    public void update(MidstoreSchemeInfoDTO midstoreDataDTO) throws MidstoreException {
        this.midstoreDataDao.update(midstoreDataDTO);
    }

    @Override
    public void delete(MidstoreSchemeInfoDTO midstoreDataDTO) throws MidstoreException {
        if (StringUtils.isNotEmpty((String)midstoreDataDTO.getKey())) {
            this.midstoreDataDao.delete(midstoreDataDTO.getKey());
        }
    }

    @Override
    public void batchAdd(List<MidstoreSchemeInfoDTO> midstoreDataDTOs) throws MidstoreException {
        if (midstoreDataDTOs.size() <= 1000) {
            ArrayList<MidstoreSchemeInfoDTO> list2 = new ArrayList<MidstoreSchemeInfoDTO>();
            list2.addAll(midstoreDataDTOs);
            this.midstoreDataDao.batchInsert(list2);
        } else {
            ArrayList<MidstoreSchemeInfoDTO> list2 = new ArrayList<MidstoreSchemeInfoDTO>();
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
    public void batchUpdate(List<MidstoreSchemeInfoDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<MidstoreSchemeInfoDTO> list2 = new ArrayList<MidstoreSchemeInfoDTO>();
        list2.addAll(midstoreDataDTOs);
        this.midstoreDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<MidstoreSchemeInfoDTO> midstoreDataDTOs) throws MidstoreException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (MidstoreSchemeInfoDTO dto : midstoreDataDTOs) {
            list2.add(dto.getKey());
        }
        this.midstoreDataDao.batchDelete(list2);
    }

    @Override
    public void batchDeleteByKeys(List<String> keys) throws MidstoreException {
        this.midstoreDataDao.batchDelete(keys);
    }
}

