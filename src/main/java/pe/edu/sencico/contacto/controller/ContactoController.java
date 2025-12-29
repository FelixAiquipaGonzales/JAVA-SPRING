package pe.edu.sencico.contacto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.sencico.contacto.service.ContactoService;
import pe.edu.sencico.contacto.vo.ContactoVO;

@Validated
@RestController
@RequestMapping("/contactos")
public class ContactoController {

	private final ContactoService contactoService;

	public ContactoController(ContactoService contactoService) {
		this.contactoService = contactoService;
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ContactoVO>> getAllContactos(){
		List<ContactoVO> contactos = contactoService.getContactos();
		return ResponseEntity.ok(contactos);
	}
	
	@GetMapping("/getId/{id}")
	public ResponseEntity<ContactoVO> getContactoById(@PathVariable Long id){
		ContactoVO contacto = contactoService.getContacto(id);
		return ResponseEntity.ok(contacto);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ContactoVO> createContacto(@Valid @RequestBody ContactoVO contactoVO){
		ContactoVO createContacto = contactoService.addContacto(contactoVO);
		return new ResponseEntity<>(createContacto,HttpStatus.CREATED);
	}
	
	@PutMapping("/getId/{id}")
	public ResponseEntity<ContactoVO> updateContacto(@PathVariable Long id, @Valid @RequestBody ContactoVO contactoVO){
		ContactoVO updateContacto = contactoService.updateContacto(id, contactoVO);
		return ResponseEntity.ok(updateContacto);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteContacto(@PathVariable Long id){
		contactoService.deleteContacto(id);
		return ResponseEntity.noContent().build();
	}
}
