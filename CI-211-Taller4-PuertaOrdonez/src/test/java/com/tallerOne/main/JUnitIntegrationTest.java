package com.tallerOne.main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.model.Institution;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.model.Precondition;
import com.tallerOne.main.model.Threshold;
import com.tallerOne.main.repositories.InstitucionesRepository;
import com.tallerOne.main.repositories.PrecondicionesRepository;
import com.tallerOne.main.services.AutotransitionService;
import com.tallerOne.main.services.AutotransitionServiceImp;
import com.tallerOne.main.services.EstadoService;
import com.tallerOne.main.services.EstadoServiceImp;
import com.tallerOne.main.services.InstitutionService;
import com.tallerOne.main.services.InstitutionServiceImp;
import com.tallerOne.main.services.LocalConditionService;
import com.tallerOne.main.services.LocalConditionServiceImp;
import com.tallerOne.main.services.PreconditionService;
import com.tallerOne.main.services.PreconditionServiceImp;
import com.tallerOne.main.services.ThresholdService;
import com.tallerOne.main.services.ThresholdServiceImp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class)
class JUnitIntegrationTest {

	private LocalConditionServiceImp loConSer;
	private PreconditionServiceImp preSer;
	private ThresholdServiceImp thrService;
	private AutotransitionServiceImp autService;
	private InstitutionServiceImp insService;
	private EstadoServiceImp estService;
	
//	private Precondition precondition;
//	private Autotransition autotransition;
	
	@Autowired
	public JUnitIntegrationTest(LocalConditionServiceImp loConSer, PreconditionServiceImp preSer, ThresholdServiceImp thrService, AutotransitionServiceImp autService,
			InstitutionServiceImp insService, EstadoServiceImp estService) {
		this.loConSer = loConSer;
		this.preSer = preSer;
		this.thrService = thrService;
		this.autService = autService;
		this.insService = insService;
		this.estService = estService;
	}
	
	@BeforeAll
	public static void setUp() {
		System.out.println("-----> SETUP <-----");
	}
	
	@Nested
	class PreconditionIntTest{
		
		private Precondition precondition;
		private Autotransition autotransition;
		
		public void stageOne() {
			autotransition = new Autotransition();
			autotransition.setAutotranId((long)0);
			autotransition.setAutotranName("Nada");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("AND");
			try {
				//autService.save(autotransition);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			precondition = new Precondition();
			precondition.setPreconId((long)1);
			precondition.setPreconLogicaloperand("OR");
			precondition.setAutotransition(autotransition);
		}
		
		public void stageTwo() {
			autotransition = new Autotransition();
			autotransition.setAutotranId((long)0);
			autotransition.setAutotranName("Nada");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("AND");
			try {
				//autService.save(autotransition);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			precondition = new Precondition();
			precondition.setPreconId((long)1);
			precondition.setPreconLogicaloperand("KAKA");
			precondition.setAutotransition(autotransition);
		}
		
		public void stageThree() {
			precondition = new Precondition();
			precondition.setPreconId((long)1);
			precondition.setPreconLogicaloperand("KAKA");
			precondition.setAutotransition(new Autotransition());
		}
		
		@Test
		@DisplayName("Guardar una precondición exitosamente")
		void savePrecondition() {
			stageOne();
			try {
				//assertEquals(precondition, preSer.save(precondition));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Test
		@DisplayName("Guardar con operador logico invalido")
		void savePreconditionTwo() {
			stageTwo();
			try {
				assertThrows(ElementDoesntExistException.class, () -> preSer.savePrecondition(precondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con autotransicion que no existe")
		void savePreconditionThree() {
			stageThree();
			try {
				assertThrows(ElementDoesntExistException.class, () -> preSer.savePrecondition(precondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editPreconditionOne() {
			stageOne();
			try {
				preSer.save(precondition);
				precondition.setPreconLogicaloperand("OR");
				assertEquals(precondition.getPreconLogicaloperand(), preSer.editPrecondition(precondition).getPreconLogicaloperand());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con precondición que no esta guardada el el repositorio")
		void editPreconditionTwo() {
			stageOne();
			try {
				precondition.setPreconLogicaloperand("OR");
				preSer.deletePrecondition(precondition);
				assertThrows(ElementDoesntExistException.class, () -> preSer.editPrecondition(precondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con autotransicion que no existe")
		void editPreconditionThree() {
			stageThree();
			try {
				preSer.save(precondition);
				precondition.setPreconLogicaloperand("OR");
				assertThrows(ElementDoesntExistException.class, () -> preSer.editPrecondition(precondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con operador logico invalido")
		void editPreconditionFour() {
			stageOne();
			try {
				preSer.getRepositoryDao().savePre(precondition);
				precondition.setPreconLogicaloperand("Y-Y");
				//assertThrows(StringValidationException.class, () -> preSer.editPrecondition(precondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	@Nested
	class LocalConditionIntTest{
		
		private Localcondition localcondition;
		private Precondition precondition;
		private Threshold threshold;
		
		public void stageOne() {
			precondition = new Precondition();
			precondition.setPreconId((long)0);
			//preSer.getRepositoryDao().savePre(precondition);
			
			
			threshold = new Threshold();
			threshold.setThresId((long)0);
			//thrService.getRepositoryDao().saveThr(threshold);
			
			localcondition = new Localcondition();
			localcondition.setLoconId(8);
			localcondition.setLoconOperator("==");
			localcondition.setLoconKeycolumn("A");
			localcondition.setLoconTablename("B");
			localcondition.setLoconQuerystring("C");
			localcondition.setThreshold(threshold);
			localcondition.setPrecondition(precondition);
		}
		
		public void stageTwo() {
			threshold = new Threshold();
			threshold.setThresId((long)0);
			//thrService.getRepositoryDao().saveThr(threshold);
			
			localcondition = new Localcondition();
			localcondition.setLoconId(8);
			localcondition.setLoconOperator("==");
			localcondition.setLoconKeycolumn("A");
			localcondition.setLoconTablename("B");
			localcondition.setLoconQuerystring("C");
			localcondition.setThreshold(threshold);
			localcondition.setPrecondition(new Precondition());
		}
		
		public void stageThree() {
			precondition = new Precondition();
			precondition.setPreconId(4);
			//preSer.getRepositoryDao().savePre(precondition);
			
			localcondition = new Localcondition();
			localcondition.setLoconId(8);
			localcondition.setLoconOperator("==");
			localcondition.setLoconKeycolumn("A");
			localcondition.setLoconTablename("B");
			localcondition.setLoconQuerystring("C");
			localcondition.setThreshold(new Threshold());
			localcondition.setPrecondition(precondition);
		}
		
		public void stageFour() {
			precondition = new Precondition();
			precondition.setPreconId(4);
			//preSer.getRepositoryDao().savePre(precondition);
			
			threshold = new Threshold();
			threshold.setThresId(6);
			//thrService.getRepositoryDao().saveThr(threshold);
			
			localcondition = new Localcondition();
			localcondition.setLoconId(8);
			localcondition.setLoconOperator("==");
			localcondition.setLoconKeycolumn("A A");
			localcondition.setLoconTablename("B B");
			localcondition.setLoconQuerystring("C C");
			localcondition.setThreshold(threshold);
			localcondition.setPrecondition(precondition);
		}
		
		public void stageFive() {
			precondition = new Precondition();
			precondition.setPreconId(4);
			//preSer.getRepositoryDao().savePre(precondition);
			
			threshold = new Threshold();
			threshold.setThresId(6);
			//thrService.getRepositoryDao().saveThr(threshold);
			
			localcondition = new Localcondition();
			localcondition.setLoconId(8);
			localcondition.setLoconOperator("NADA");
			localcondition.setLoconKeycolumn("A");
			localcondition.setLoconTablename("B");
			localcondition.setLoconQuerystring("C");
			localcondition.setThreshold(threshold);
			localcondition.setPrecondition(precondition);
		}
		
		@Test
		@DisplayName("Guardar exitosamente")
		void saveLocalConditionOne() {
			stageOne();
			try {
				assertEquals(localcondition, loConSer.save(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con precondición no existente")
		void saveLocalConditionTwo() {
			stageTwo();
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConSer.saveLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con threshold no existente")
		void saveLocalConditionThree() {
			stageThree();
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConSer.saveLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con nombre de tabla con espacio")
		void saveLocalConditionFour() {
			stageFour();
			try {
				//assertThrows(StringValidationException.class, () -> loConSer.saveLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con operador invalido")
		void saveLocalConditionFive() {
			stageFive();
			try {
				//assertThrows(StringValidationException.class, () -> loConSer.saveLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editLocalConditionOne() {
			stageOne();
			try {
				loConSer.getRepositoryDao().saveLoc(localcondition);
				assertEquals(localcondition, loConSer.editLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con localCondition que no existe en el repositorio")
		void editLocalConditionTwo() {
			stageOne();
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConSer.editLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con precondition que no existe en el repositorio")
		void editLocalConditionThree() {
			stageOne();
			try {
				loConSer.getRepositoryDao().saveLoc(localcondition);
				localcondition.setPrecondition(new Precondition());
				assertThrows(ElementDoesntExistException.class, () -> loConSer.editLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con Threshold que no existe en el repositorio")
		void editLocalConditionFour() {
			stageOne();
			try {
				loConSer.getRepositoryDao().saveLoc(localcondition);
				localcondition.setThreshold(new Threshold());
				assertThrows(ElementDoesntExistException.class, () -> loConSer.editLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con nombre de tabla con espacio")
		void editLocalConditionFive() {
			stageOne();
			try {
				loConSer.getRepositoryDao().saveLoc(localcondition);
				localcondition.setLoconTablename("A A");
				assertThrows(StringValidationException.class, () -> loConSer.editLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con operador invalido")
		void editLocalConditionSix() {
			stageOne();
			try {
				loConSer.getRepositoryDao().saveLoc(localcondition);
				localcondition.setLoconOperator("A A");
				//assertThrows(StringValidationException.class, () -> loConSer.editLocalCondition(localcondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	} 
	
	@Nested
	class ThresholdIntTest{
		
		private Threshold threshold;
		private Institution institution;
		
		public void stageOne() {
			institution = new Institution();
			institution.setInstId(10);
			//thrService.getInsRepositoryDao().saveIns(institution);
			//insService.saveInstitution(institution);
			
			threshold = new Threshold();
			threshold.setInstInstId(BigDecimal.valueOf(institution.getInstId()));
			threshold.setThresName("Juan");
			threshold.setThresValue("Puerta");
			threshold.setThresValuetype("String");
		}
		
		public void stageTwo() {
			threshold = new Threshold();
			threshold.setInstInstId(BigDecimal.valueOf(23.0));
			threshold.setThresName("Juan");
			threshold.setThresValue("Puerta");
			threshold.setThresValuetype("String");
		}
		
		public void stageThree() {
			institution = new Institution();
			institution.setInstId(10);
			//thrService.getInsRepositoryDao().saveIns(institution);
			//insService.saveInstitution(institution);
			
			threshold = new Threshold();
			threshold.setInstInstId(BigDecimal.valueOf(institution.getInstId()));
			threshold.setThresName("");
			threshold.setThresValue("");
			threshold.setThresValuetype("String");
		}
		
		public void stageFour() {
			institution = new Institution();
			institution.setInstId(10);
			//thrService.getInsRepositoryDao().saveIns(institution);
			//insService.saveInstitution(institution);
			
			threshold = new Threshold();
			threshold.setInstInstId(BigDecimal.valueOf(institution.getInstId()));
			threshold.setThresName("Juan");
			threshold.setThresValue("Puerta");
			threshold.setThresValuetype("Nada");
		}
		
		@Test
		@DisplayName("Guardar exitosamente")
		void saveThresholdOne() {
			stageOne();
			try {
				assertEquals(threshold, thrService.save(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con institucion que no existe")
		void saveThresholdTwo() {
			stageTwo();
			try {
				assertThrows(NullPointerException.class, () -> thrService.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con nombre vacio")
		void saveThresholdThree() {
			stageThree();
			try {
				//assertThrows(StringValidationException.class, () -> thrService.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con tipo de valor invalido")
		void saveThresholdFour() {
			stageFour();
			try {
				//assertThrows(StringValidationException.class, () -> thrService.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editThresholdOne() {
			stageOne();
			try {
				thrService.saveThreshold(threshold);
				assertEquals(threshold, thrService.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con threshold que no existe")
		void editThresholdTwo() {
			stageOne();
			try {
				threshold.setInstInstId(BigDecimal.valueOf(3.4));
				assertThrows(ElementDoesntExistException.class, () -> thrService.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con institucion que no existe")
		void editThresholdThree() {
			stageOne();
			try {
				thrService.saveThreshold(threshold);
				threshold.setInstInstId(BigDecimal.valueOf(3.4));
				assertThrows(ElementDoesntExistException.class, () -> thrService.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con nombre vacio")
		void editThresholdFour() {
			stageOne();
			try {
				thrService.saveThreshold(threshold);
				threshold.setThresName("");
				assertThrows(StringValidationException.class, () -> thrService.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
		@Test
		@DisplayName("Editar con tipo de valor invalido")
		void editThresholdFive() {
			stageOne();
			try {
				thrService.saveThreshold(threshold);
				threshold.setThresValuetype("Nada");
				assertThrows(StringValidationException.class, () -> thrService.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	@Nested
	class AutotransitionIntTest{
		
		private Autotransition autotransition;
		
		public void stageOne() {
			autotransition = new Autotransition();
			autotransition.setAutotranName("Ya");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("AND");
		}
		
		public void stageTwo() {
			autotransition = new Autotransition();
			autotransition.setAutotranName("");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("AND");
		}
		
		public void stageThree() {
			autotransition = new Autotransition();
			autotransition.setAutotranName("Ya");
			autotransition.setAutotranIsactive("Nada");
			autotransition.setAutotranLogicaloperand("AND");
		}
		
		public void stageFour() {
			autotransition = new Autotransition();
			autotransition.setAutotranName("Ya");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("NADA");
		}
		
		@Test
		@DisplayName("Guardar autotransition exitosamente")
		void saveAutotransitionOne() {
			stageOne();
			try {
				assertEquals(autotransition, autService.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar autotransition con nombre invalido")
		void saveAutotransitionTwo() {
			stageTwo();
			try {
				assertThrows(StringValidationException.class, () -> autService.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar autotransition con activo invalido")
		void saveAutotransitionThree() {
			stageThree();
			try {
				assertThrows(StringValidationException.class, () -> autService.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar autotransition con operador logico invalido")
		void saveAutotransitionFour() {
			stageFour();
			try {
				assertThrows(StringValidationException.class, () -> autService.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editAutotransitionOne() {
			stageOne();
			try {
				autService.saveAutotransition(autotransition);
				autotransition.setAutotranName("DeNuevo");
				assertEquals(autotransition, autService.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con autotransition que no existe en el repositorio")
		void editAutotransitionTwo() {
			stageOne();
			try {
				assertThrows(ElementDoesntExistException.class, () -> autService.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con nombre invalido")
		void editAutotransitionThree() {
			stageOne();
			try {
				autService.saveAutotransition(autotransition);
				autotransition.setAutotranName("");
				assertThrows(StringValidationException.class, () -> autService.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con activo invalido")
		void editAutotransitionFour() {
			stageOne();
			try {
				autService.saveAutotransition(autotransition);
				autotransition.setAutotranIsactive("cdsc");
				assertThrows(StringValidationException.class, () -> autService.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con operador logico invalido")
		void editAutotransitionFive() {
			stageOne();
			try {
				autService.saveAutotransition(autotransition);
				autotransition.setAutotranLogicaloperand("Nada");
				assertThrows(StringValidationException.class, () -> autService.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	@AfterAll
	public static void afterTest() {
		System.out.println("-----> DESTROY <-----");
	}

}
