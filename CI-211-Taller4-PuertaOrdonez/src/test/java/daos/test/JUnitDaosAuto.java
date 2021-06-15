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
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.model.Eventstatus;
import com.tallerOne.main.model.Institution;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@Rollback(false)
class JUnitDaosAuto {

	@Autowired
	private AutotransitionDao autoDao;
	
	@Autowired
	private InstitutionDao insDao;
	
	@Autowired
	private EstadoDao estDao;
	
	Autotransition auto;
	Institution ins;
	Eventstatus est;
	
	public void stageOne() {
		
		autoDao.deleteAll();
		insDao.deleteAll();
		estDao.deleteAll();
		
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
		
	}

	@DisplayName("Guardar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void guardar() {
		stageOne();
		autoDao.saveAuto(auto);
		try {
			assertEquals(auto.getAutotranName(), autoDao.findById(auto.getAutotranId()).get().getAutotranName());
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
		autoDao.saveAuto(auto);
		auto.setAutotranName("T-T");
		autoDao.editAuto(auto);
		try {
			assertEquals(auto.getAutotranName(), autoDao.findById(auto.getAutotranId()).get().getAutotranName());
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
		autoDao.saveAuto(auto);
		int size = autoDao.findAll().size();
		autoDao.deleteAuto(auto);
		try {
			assertEquals(size-1, autoDao.findAll().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Encontrar por nombre")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void encontrarPorNombre() {
		stageOne();
		autoDao.saveAuto(auto);
		try {
			assertEquals(auto.getAutotranName(), autoDao.findByName(auto.getAutotranName()).get(0).getAutotranName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Encontrar por insId")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void encontrarPorInsId() {
		stageOne();
		//autoDao.saveAuto(auto);
		try {
			assertEquals(auto.getInstInstId(), autoDao.findByInsId(auto.getInstInstId().longValue()).get(0).getInstInstId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Encontrar por activo")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void encontrarPorActivo() {
		stageOne();
		autoDao.saveAuto(auto);
		try {
			assertEquals(auto.getAutotranIsactive(), autoDao.findByIsActive(auto.getAutotranIsactive()).get(0).getAutotranIsactive());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
