package pe.edu.sencico.contacto.converter;

import pe.edu.sencico.contacto.entity.Contacto;
import pe.edu.sencico.contacto.vo.ContactoVO;

public interface Converter {

	ContactoVO convertToVo(Contacto contacto);
    
    Contacto convertToEntity(ContactoVO contactoVO);
}
