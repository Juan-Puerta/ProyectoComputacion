package daos.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tallerOne.main.MainApplication;
import com.tallerOne.main.daos.AutotransitionDao;
import com.tallerOne.main.daos.EstadoDao;
import com.tallerOne.main.daos.InstitutionDao;
import com.tallerOne.main.daos.PreconditionDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.model.Eventstatus;
import com.tallerOne.main.model.Institution;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.model.Precondition;
import com.tallerOne.main.model.Threshold;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@Rollback(false)
class JUnitDaosPre {

	@Autowired
	private PreconditionDao preDao;
	
	@Autowired
	private AutotransitionDao autoDao;
	
	@Autowired
	private InstitutionDao insDao;
	
	@Autowired
	private EstadoDao estDao;

	Precondition pre;
	Autotransition auto;
	Institution ins;
	Eventstatus est;
	
	
	public void stageOne() {
		
		preDao.deleteAll();
		autoDao.deleteAll();
		estDao.deleteAll();
		insDao.deleteAll();
		
		ins = new Institution();
		ins.setInstId((long)0);
		insDao.saveIns(ins);
		
		est = new Eventstatus();
		estDao.save(est);
		
		auto = new Autotransition();
		auto.setAutotranName("Y-Y");
		auto.setAutotranIsactive("Y");
		auto.setAutotranLogicaloperand("OR");
		auto.setInstInstId(new BigDecimal(ins.getInstId()));
		auto.setEventstatus1(est);
		autoDao.saveAuto(auto);
		
		pre = new Precondition();
		pre.setPreconLogicaloperand("AND");
		pre.setAutotransition(auto);
		preDao.savePre(pre);
		
	}
	
	@DisplayName("Guardar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void guardar() {
		stageOne();
		preDao.savePre(pre);
		try {
			assertEquals(pre.getPreconId(), preDao.findById(pre.getPreconId()).get().getPreconId());
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Editar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void editar() {
		stageOne();
		preDao.savePre(pre);
		pre.setPreconLogicaloperand("OR");
		preDao.editPre(pre);
		try {
			assertEquals(pre.getPreconLogicaloperand(), preDao.findById(pre.getPreconId()).get().getPreconLogicaloperand());
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Borrar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void borrar() {
		stageOne();
		preDao.savePre(pre);
		int size = preDao.findAll().size();
		preDao.deletePre(pre);
		try {
			assertEquals(size-1, preDao.findAll().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
