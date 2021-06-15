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
import com.tallerOne.main.daos.InstitutionDao;
import com.tallerOne.main.daos.ThresholdDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Institution;
import com.tallerOne.main.model.Threshold;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@Rollback(false)
class JUnitDaosThr {

	@Autowired
	private ThresholdDao thrDao;
	
	@Autowired
	private InstitutionDao insDao;
	
	Threshold thr;
	Institution ins;
	
	public void stageOne() {
		
		thrDao.deleteAll();
		insDao.deleteAll();
		
		ins = new Institution();
		ins.setInstId((long)0);
		insDao.saveIns(ins);
		
		thr = new Threshold();
		thr.setThresName("Wenas");
		thr.setThresValue("TodoBien");
		thr.setThresValuetype("String");
		thr.setInstInstId(new BigDecimal(ins.getInstId()));
		thrDao.saveThr(thr);
		
	}
	
	@Test
	@DisplayName("Guardar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void guardar() {
		stageOne();
		thrDao.saveThr(thr);
		try {
			assertEquals(thr.getThresId(), thrDao.findById(thr.getThresId()).get().getThresId());
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Editar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void editar() {
		stageOne();
		thrDao.saveThr(thr);
		thr.setThresName("Juan");
		thrDao.editThr(thr);
		try {
			assertEquals(thr.getThresName(), thrDao.findById(thr.getThresId()).get().getThresName());
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Borrar exitosamente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void borrar() {
		stageOne();
		thrDao.saveThr(thr);
		int size = thrDao.findAll().size();
		thrDao.deleteThr(thr);
		try {
			assertEquals(size-1, thrDao.findAll().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Encontrar por nombre")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void encontrarPorNombre() {
		stageOne();
		thrDao.saveThr(thr);
		try {
			assertEquals(thr.getThresName(), thrDao.findByName(thr.getThresName()).get(0).getThresName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Encontrar por valor")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void encontrarPorValor() {
		stageOne();
		thrDao.saveThr(thr);
		try {
			assertEquals(thr.getThresValue(), thrDao.findByValue(thr.getThresValue()).get(0).getThresValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Encontrar por tipo")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void encontrarPorTipo() {
		stageOne();
		thrDao.saveThr(thr);
		try {
			assertEquals(thr.getThresValuetype(), thrDao.findByType(thr.getThresValuetype()).get(0).getThresValuetype());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
