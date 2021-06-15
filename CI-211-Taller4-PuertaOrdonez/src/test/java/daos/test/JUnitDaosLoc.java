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
import com.tallerOne.main.daos.LocalConditionDao;
import com.tallerOne.main.daos.PreconditionDao;
import com.tallerOne.main.daos.ThresholdDao;
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
class JUnitDaosLoc {

	@Autowired
	private LocalConditionDao locDao;
	
	@Autowired
	private PreconditionDao preDao;
	
	@Autowired
	private ThresholdDao thrDao;
	
	@Autowired
	private AutotransitionDao autoDao;
	
	@Autowired
	private InstitutionDao insDao;
	
	@Autowired
	private EstadoDao estDao;
	
	Localcondition loc;
	Precondition pre;
	Threshold thr;
	Autotransition auto;
	Institution ins;
	Eventstatus est;
	
	public void stageOne() {
		
		locDao.deleteAll();
		preDao.deleteAll();
		thrDao.deleteAll();
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
		
		thr = new Threshold();
		thr.setThresName("Wenas");
		thr.setThresValue("TodoBien");
		thr.setThresValuetype("String");
		thr.setInstInstId(new BigDecimal(ins.getInstId()));
		thrDao.saveThr(thr);
		
		loc = new Localcondition();
		loc.setLoconValuetype("Haha");
		loc.setLoconColumnname("Caca");
		loc.setLoconTablename("Kaka");
		loc.setLoconKeycolumn("Xaxa");
		loc.setLoconOperator("==");
		loc.setLoconQuerystring("Nana");
		loc.setPrecondition(pre);
		loc.setThreshold(thr);
		locDao.saveLoc(loc);
		
	}
	
	@DisplayName("Guardar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void guardar() {
		stageOne();
		locDao.saveLoc(loc);
		try {
			assertEquals(loc.getLoconTablename(), locDao.findById(loc.getLoconId()).get().getLoconTablename());
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
		locDao.saveLoc(loc);
		loc.setLoconTablename("LoCambiéJeJeJE");
		locDao.editLoc(loc);
		try {
			assertEquals(loc.getLoconTablename(), locDao.findById(loc.getLoconId()).get().getLoconTablename());
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
		locDao.saveLoc(loc);
		int size = locDao.findAll().size();
		locDao.deleteLoc(loc);
		try {
			assertEquals(size-1, locDao.findAll().size());
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
		locDao.saveLoc(loc);
		try {
			assertEquals(loc.getLoconTablename(), locDao.findByName(loc.getLoconTablename()).get(0).getLoconTablename());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Encontrar por tipo de retorno")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void encontrarPorTipoRetorno() {
		stageOne();
		locDao.saveLoc(loc);
		try {
			assertEquals(loc.getLoconValuetype(), locDao.findByTypeValue(loc.getLoconValuetype()).get(0).getLoconValuetype());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("Encontrar por Id precondicion")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Test
	void encontrarPorIdPrecondicion() {
		stageOne();
		locDao.saveLoc(loc);
		try {
			//assertEquals(loc.getPrecondition().getPreconId(), locDao.findByIdPrecondition(loc.getPrecondition().getPreconId()).get(0).getPrecondition().getPreconId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
