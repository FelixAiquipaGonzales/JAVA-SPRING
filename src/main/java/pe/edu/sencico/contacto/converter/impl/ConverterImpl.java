package pe.edu.sencico.contacto.converter.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import pe.edu.sencico.contacto.converter.Converter;
import pe.edu.sencico.contacto.entity.Contacto;
import pe.edu.sencico.contacto.vo.ContactoVO;

@Component
public class ConverterImpl implements Converter{

	@Override
	public ContactoVO convertToVo(Contacto contacto) {
		ContactoVO contactoVO = new ContactoVO();
		BeanUtils.copyProperties(contacto, contactoVO);
		//aqui se pone el DNI
		contactoVO.setDni(contacto.getRuc().substring(2,contacto.getRuc().length()-1));
		return contactoVO;
	}

	@Override
	public Contacto convertToEntity(ContactoVO contactoVO) {
		Contacto contacto = new Contacto();
		BeanUtils.copyProperties(contactoVO, contacto);
		return contacto;
	}

}
