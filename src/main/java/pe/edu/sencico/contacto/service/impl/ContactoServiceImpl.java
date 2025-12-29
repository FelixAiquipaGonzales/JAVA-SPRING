package pe.edu.sencico.contacto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.sencico.contacto.converter.Converter;
import pe.edu.sencico.contacto.entity.Contacto;
import pe.edu.sencico.contacto.exception.NoRecordException;
import pe.edu.sencico.contacto.repository.ContactoRepository;
import pe.edu.sencico.contacto.service.ContactoService;
import pe.edu.sencico.contacto.vo.ContactoVO;

@Service
public class ContactoServiceImpl implements ContactoService{

	private final ContactoRepository contactoRepository;
	
	private final Converter converter;
	
	public ContactoServiceImpl(ContactoRepository contactoRepository, Converter converter) {
		this.contactoRepository = contactoRepository;
		this.converter = converter;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContactoVO> getContactos() {
		return contactoRepository.findAll().stream()
				.map(converter::convertToVo)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ContactoVO getContacto(Long id) {
		return contactoRepository.findById(id)
				.map(converter::convertToVo)
				.orElseThrow(()->new NoRecordException("No Contact found with ID: " + id));
	}

	@Override
	@Transactional
	public ContactoVO addContacto(ContactoVO contactoVO) {
		Contacto contacto = converter.convertToEntity(contactoVO);
		Contacto savedContacto = contactoRepository.save(contacto);
		return converter.convertToVo(savedContacto);
	}

	@Override
	@Transactional
	public ContactoVO updateContacto(Long id, ContactoVO contactoVO) {
		Contacto contacto = contactoRepository.findById(id)
				.orElseThrow(()->new NoRecordException("No Contact found with ID: " + id));
		BeanUtils.copyProperties(contactoVO, contacto);
		Contacto updateContacto = contactoRepository.save(contacto);
		return converter.convertToVo(updateContacto);
	}

	@Override
	@Transactional
	public void deleteContacto(Long id) {
		if(!contactoRepository.existsById(id)) {
			throw new NoRecordException("No Contact found with ID: " + id);
		}
		contactoRepository.deleteById(id);
	}

}
