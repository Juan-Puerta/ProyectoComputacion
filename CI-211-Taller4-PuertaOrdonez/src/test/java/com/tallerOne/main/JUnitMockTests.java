package com.tallerOne.main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.Optional;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import com.tallerOne.main.daos.AutotransitionDao;
import com.tallerOne.main.daos.EstadoDao;
import com.tallerOne.main.daos.InstitutionDao;
import com.tallerOne.main.daos.LocalConditionDao;
import com.tallerOne.main.daos.PreconditionDao;
import com.tallerOne.main.daos.ThresholdDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.model.Institution;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.model.Precondition;
import com.tallerOne.main.model.Threshold;
import com.tallerOne.main.services.AutotransitionServiceImp;
import com.tallerOne.main.services.LocalConditionServiceImp;
import com.tallerOne.main.services.PreconditionServiceImp;
import com.tallerOne.main.services.ThresholdServiceImp;

@RunWith(MockitoJUnitRunner.class)
class JUnitMockTests {

	@Nested
	class PreconditionUnitTest{
		
		@Mock
		private PreconditionDao repository;
		@Mock
		private AutotransitionDao autoRepository;
		@InjectMocks
		private PreconditionServiceImp preConImp;
		
		private Precondition laPrecondicion;
		
		@BeforeEach
		public void setup() {
			MockitoAnnotations.initMocks(this);
			preConImp = new PreconditionServiceImp(repository, autoRepository);
		}
		
		public void stageOne() {
			laPrecondicion = new Precondition();
			laPrecondicion.setAutotransition(new Autotransition());
			laPrecondicion.setPreconId(1);
			laPrecondicion.setPreconLogicaloperand("OR");
		}
		
		public void stageTwo() {
			laPrecondicion = new Precondition();
			laPrecondicion.setAutotransition(new Autotransition());
			laPrecondicion.setPreconId(1);
			laPrecondicion.setPreconLogicaloperand("XYZW");
		}
		
		@Test
		@DisplayName("Guardar una precondición exitosamente")
		void savePreconditionOne() throws ElementDoesntExistException {
			stageOne();
			when(autoRepository.findById(laPrecondicion.getAutotransition().getAutotranId())).thenReturn(Optional.of(laPrecondicion.getAutotransition()));

			try {
				assertEquals(laPrecondicion, preConImp.savePrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con operador logico invalido")
		void savePreconditionTwo() throws ElementDoesntExistException {
			stageTwo();
			when(autoRepository.findById(laPrecondicion.getAutotransition().getAutotranId())).thenReturn(Optional.of(laPrecondicion.getAutotransition()));
			try {
				assertThrows(StringValidationException.class, () -> preConImp.savePrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con autotransicion que no existe")
		void savePreconditionThree() throws ElementDoesntExistException {
			stageOne();
			when(autoRepository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> preConImp.savePrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editPreconditionOne() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(laPrecondicion.getPreconId())).thenReturn(Optional.of(laPrecondicion));
			when(autoRepository.findById(laPrecondicion.getAutotransition().getAutotranId())).thenReturn(Optional.of(laPrecondicion.getAutotransition()));
			try {
				assertEquals(laPrecondicion, preConImp.editPrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con precondición que no esta guardada el el repositorio")
		void editPreconditionTwo() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById((long)0)).thenReturn(Optional.empty());
			//when(repository.findById(laPrecondicion.getPreconId()).isPresent()).thenReturn(false);
			try {
				assertThrows(ElementDoesntExistException.class, () -> preConImp.editPrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con autotransicion que no existe")
		void editPreconditionThree() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(laPrecondicion.getPreconId())).thenReturn(Optional.of(laPrecondicion));
			when(autoRepository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> preConImp.editPrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con operador logico invalido")
		void editPreconditionFour() throws ElementDoesntExistException {
			stageTwo();
			when(repository.findById(laPrecondicion.getPreconId())).thenReturn(Optional.of(laPrecondicion));
			when(autoRepository.findById(laPrecondicion.getAutotransition().getAutotranId())).thenReturn(Optional.of(laPrecondicion.getAutotransition()));
			try {
				assertThrows(StringValidationException.class, () -> preConImp.editPrecondition(laPrecondicion));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	@Nested
	class LocalConditionUnitTest{
		
		@Mock
		private LocalConditionDao repository;
		@Mock
		private PreconditionDao preRepository;
		@Mock
		private ThresholdDao thrRepository;
		@InjectMocks
		private LocalConditionServiceImp loConImp;
		
		private Localcondition laCondition;
		
		@BeforeEach
		public void setup() {
			MockitoAnnotations.initMocks(this);
			loConImp = new LocalConditionServiceImp(repository, preRepository, thrRepository);
		}
		
		public void stageOne() {
			laCondition = new Localcondition();
			laCondition.setThreshold(new Threshold());
			laCondition.setPrecondition(new Precondition());
			laCondition.setLoconId(1);
			laCondition.setLoconOperator("==");
			laCondition.setLoconKeycolumn("A");
			laCondition.setLoconTablename("B");
			laCondition.setLoconQuerystring("C");
		}
		
		public void stageTwo() {
			laCondition = new Localcondition();
			laCondition.setThreshold(new Threshold());
			laCondition.setPrecondition(new Precondition());
			laCondition.setLoconId(1);
			laCondition.setLoconOperator("#");
			laCondition.setLoconKeycolumn("A");
			laCondition.setLoconTablename("B");
			laCondition.setLoconQuerystring("C");
		}
		
		public void stageThree() {
			laCondition = new Localcondition();
			laCondition.setThreshold(new Threshold());
			laCondition.setPrecondition(new Precondition());
			laCondition.setLoconId(1);
			laCondition.setLoconOperator("==");
			laCondition.setLoconKeycolumn("A");
			laCondition.setLoconTablename("T - T");
			laCondition.setLoconQuerystring("C");
		}
		
		@Test
		@DisplayName("Guardar exitosamente")
		void saveLocalConditionOne() throws ElementDoesntExistException {
			stageOne();
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertEquals(laCondition, loConImp.saveLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con precondición no existente")
		void saveLocalConditionTwo() throws ElementDoesntExistException {
			stageOne();
			//when(preRepository.findById(0))
			when(preRepository.findById((long)0)).thenReturn(Optional.empty());
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConImp.saveLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con threshold no existente")
		void saveLocalConditionThree() throws ElementDoesntExistException {
			stageOne();
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConImp.saveLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con nombre de tabla con espacio")
		void saveLocalConditionFour() throws ElementDoesntExistException {
			stageThree();
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(StringValidationException.class, () -> loConImp.saveLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con operador invalido")
		void saveLocalConditionFive() throws ElementDoesntExistException {
			stageTwo();
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(StringValidationException.class, () -> loConImp.saveLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editLocalConditionOne() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(laCondition.getLoconId())).thenReturn(Optional.of(laCondition));
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertEquals(laCondition, loConImp.editLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con localCondition que no existe en el repositorio")
		void editLocalConditionTwo() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById((long)0)).thenReturn(Optional.empty());
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConImp.editLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con precondition que no existe en el repositorio")
		void editLocalConditionThree() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(laCondition.getLoconId())).thenReturn(Optional.of(laCondition));
			when(preRepository.findById((long)0)).thenReturn(Optional.empty());
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConImp.editLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con Threshold que no existe en el repositorio")
		void editLocalConditionFour() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(laCondition.getLoconId())).thenReturn(Optional.of(laCondition));
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> loConImp.editLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con nombre de tabla con espacio")
		void editLocalConditionFive() throws ElementDoesntExistException {
			stageThree();
			when(repository.findById(laCondition.getLoconId())).thenReturn(Optional.of(laCondition));
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(StringValidationException.class, () -> loConImp.editLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con operador invalido")
		void editLocalConditionSix() throws ElementDoesntExistException {
			stageTwo();
			when(repository.findById(laCondition.getLoconId())).thenReturn(Optional.of(laCondition));
			when(preRepository.findById(laCondition.getPrecondition().getPreconId())).thenReturn(Optional.of(laCondition.getPrecondition()));
			when(thrRepository.findById(laCondition.getThreshold().getThresId())).thenReturn(Optional.of(laCondition.getThreshold()));
			try {
				assertThrows(StringValidationException.class, () -> loConImp.editLocalCondition(laCondition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	@Nested
	class ThresholdUnitTest{
		
		@Mock
		private ThresholdDao repository;
		@Mock
		private InstitutionDao insRepository;
		@InjectMocks
		private ThresholdServiceImp thrImp;
		
		private Threshold threshold;
		
		@BeforeEach
		public void setup() {
			MockitoAnnotations.initMocks(this);
			thrImp = new ThresholdServiceImp(repository, insRepository);
		}
		
		public void stageOne() {
			threshold = new Threshold();
			threshold.setInstInstId(new BigDecimal(1));
			threshold.setThresId(2);
			threshold.setThresName("A");
			threshold.setThresValue("B");
			threshold.setThresValuetype("Int");
		}
		
		public void stageTwo() {
			threshold = new Threshold();
			threshold.setInstInstId(new BigDecimal(1));
			threshold.setThresId(2);
			threshold.setThresName("");
			threshold.setThresValue("B");
			threshold.setThresValuetype("Int");
		}
		
		public void stageThree() {
			threshold = new Threshold();
			threshold.setInstInstId(new BigDecimal(1));
			threshold.setThresId(2);
			threshold.setThresName("A");
			threshold.setThresValue("B");
			threshold.setThresValuetype("BigDecimal");
		}
		
		@Test
		@DisplayName("Guardar exitosamente")
		void saveThresholdOne() {
			Institution ins = new Institution();
			stageOne();
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertEquals(threshold, thrImp.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con institucion que no existe")
		void saveThresholdTwo() {
			stageOne();
			when(insRepository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> thrImp.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con nombre vacio")
		void saveThresholdThree() {
			Institution ins = new Institution();
			stageTwo();
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertThrows(StringValidationException.class, () -> thrImp.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar con tipo de valor invalido")
		void saveThresholdFour() {
			Institution ins = new Institution();
			stageThree();
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertThrows(StringValidationException.class, () -> thrImp.saveThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editThresholdOne() throws ElementDoesntExistException {
			stageOne();
			Institution ins = new Institution();
			when(repository.findById(threshold.getThresId())).thenReturn(Optional.of(threshold));
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertEquals(threshold, thrImp.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con threshold que no existe")
		void editThresholdTwo() throws ElementDoesntExistException {
			stageOne();
			Institution ins = new Institution();
			when(repository.findById((long)0)).thenReturn(Optional.empty());
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertThrows(ElementDoesntExistException.class, () -> thrImp.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con institucion que no existe")
		void editThresholdThree() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(threshold.getThresId())).thenReturn(Optional.of(threshold));
			when(insRepository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> thrImp.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con nombre vacio")
		void editThresholdFour() throws ElementDoesntExistException {
			stageTwo();
			Institution ins = new Institution();
			when(repository.findById(threshold.getThresId())).thenReturn(Optional.of(threshold));
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertThrows(StringValidationException.class, () -> thrImp.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con tipo de valor invalido")
		void editThresholdFive() throws ElementDoesntExistException {
			stageThree();
			Institution ins = new Institution();
			when(repository.findById(threshold.getThresId())).thenReturn(Optional.of(threshold));
			when(insRepository.findById(threshold.getInstInstId().longValue())).thenReturn(Optional.of(ins));
			try {
				assertThrows(StringValidationException.class, () -> thrImp.editThreshold(threshold));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

	@Nested
	class AutotransitionUnitTest{
		
		@Mock
		private AutotransitionDao repository;
		@Mock
		private InstitutionDao insRepositoryDAO;
		@Mock
		private EstadoDao estEpiRepositoryDAO;
		@InjectMocks
		private AutotransitionServiceImp autoImp;
		
		private Autotransition autotransition;
		
		//@SuppressWarnings("deprecation")
		@BeforeEach
		public void setup() {
			MockitoAnnotations.initMocks(this);
			autoImp = new AutotransitionServiceImp(repository, insRepositoryDAO, estEpiRepositoryDAO);
		}
		
		public void stageOne() {
			autotransition = new Autotransition();
			autotransition.setAutotranId(1);
			autotransition.setAutotranName("Nada");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("AND");
		}
		
		public void stageTwo() {
			autotransition = new Autotransition();
			autotransition.setAutotranId(1);
			autotransition.setAutotranName("");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("AND");
		}
		
		public void stageThree() {
			autotransition = new Autotransition();
			autotransition.setAutotranId(1);
			autotransition.setAutotranName("Nada");
			autotransition.setAutotranIsactive("EPA");
			autotransition.setAutotranLogicaloperand("AND");
		}
		
		public void stageFour() {
			autotransition = new Autotransition();
			autotransition.setAutotranId(1);
			autotransition.setAutotranName("Nada");
			autotransition.setAutotranIsactive("Y");
			autotransition.setAutotranLogicaloperand("XAXA");
		}
		
		@Test
		@DisplayName("Guardar autotransition exitosamente")
		void saveAutotransitionOne() {
			stageOne();
			try {
				assertEquals(autotransition, autoImp.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar autotransition con nombre invalido")
		void saveAutotransitionTwo() {
			stageTwo();
			try {
				assertThrows(StringValidationException.class, () -> autoImp.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar autotransition con activo invalido")
		void saveAutotransitionThree() {
			stageThree();
			try {
				assertThrows(StringValidationException.class, () -> autoImp.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Guardar autotransition con operador logico invalido")
		void saveAutotransitionFour() {
			stageFour();
			try {
				assertThrows(StringValidationException.class, () -> autoImp.saveAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar exitosamente")
		void editAutotransitionOne() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById(autotransition.getAutotranId())).thenReturn(Optional.of(autotransition));
			try {
				assertEquals(autotransition, autoImp.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con autotransition que no existe en el repositorio")
		void editAutotransitionTwo() throws ElementDoesntExistException {
			stageOne();
			when(repository.findById((long)0)).thenReturn(Optional.empty());
			try {
				assertThrows(ElementDoesntExistException.class, () -> autoImp.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con nombre invalido")
		void editAutotransitionThree() throws ElementDoesntExistException {
			stageTwo();
			when(repository.findById(autotransition.getAutotranId())).thenReturn(Optional.of(autotransition));
			try {
				assertThrows(StringValidationException.class, () -> autoImp.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con activo invalido")
		void editAutotransitionFour() throws ElementDoesntExistException {
			stageThree();
			when(repository.findById(autotransition.getAutotranId())).thenReturn(Optional.of(autotransition));
			try {
				assertThrows(StringValidationException.class, () -> autoImp.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		@Test
		@DisplayName("Editar con operador logico invalido")
		void editAutotransitionFive() throws ElementDoesntExistException {
			stageFour();
			when(repository.findById(autotransition.getAutotranId())).thenReturn(Optional.of(autotransition));
			try {
				assertThrows(StringValidationException.class, () -> autoImp.editAutotransition(autotransition));
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
}
