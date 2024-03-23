package bruno.spring.java.mapper.mapperCustom;

import java.util.Date;

import org.springframework.stereotype.Service;

import bruno.spring.java.dataVoV2.PersonVoV2;
import bruno.spring.java.models.Person;

@Service
public class PersonMapper {
    
    public PersonVoV2 convertEntityToVo(Person person){

        PersonVoV2 vo = new PersonVoV2();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setAddress(person.getAddress());
        vo.setGender(person.getGender());
        vo.setBirthDay(new Date());

        return vo;
    }

    public Person convertVoToEntity(PersonVoV2 person){

        Person entity = new Person();
        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return entity;
    }
}
