package dongyang.spmis.service;

import dongyang.spmis.mapper.CmmnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmmnServiceImpl implements CmmnService {

	@Autowired
	private CmmnMapper cmmnMapper;

}
