package pe.edu.sencico.contacto.service;

import java.util.List;

import pe.edu.sencico.contacto.vo.ContactoVO;

public interface ContactoService {

	List<ContactoVO> getContactos();
	
	ContactoVO getContacto(Long id);
	
	ContactoVO addContacto(ContactoVO contactoVO);
	
	ContactoVO updateContacto(Long id, ContactoVO contactoVO);
	
	void deleteContacto(Long id);
}
