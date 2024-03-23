package bruno.spring.java.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bruno.spring.java.dataVoV1.PersonVO;
import bruno.spring.java.dataVoV2.PersonVoV2;
import bruno.spring.java.exceptions.ResourceNotFoundException;
import bruno.spring.java.mapper.DozerMapper;
import bruno.spring.java.mapper.mapperCustom.PersonMapper;
import bruno.spring.java.models.Person;
import bruno.spring.java.repositories.PersonRepository;


@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;

	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {

		logger.info("Finding all people!");

		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one person!");
		
		var entity =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {

		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);

		return vo;
	}

	public PersonVoV2 createV2(PersonVoV2 person) {

		logger.info("Creating one person with V2!");
		
		var entity = mapper.convertVoToEntity(person);
		var vo =  mapper.convertEntityToVo(repository.save(entity));

		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);

		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
