package pe.edu.sencico.contacto.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoVO {

	private Long id;
	
	@NotBlank
	@Size(max = 30)
	private String apellidoPat;
	
	@NotBlank
	@Size(max = 30)
	private String apellidoMat;
	
	@NotBlank
	@Size(max = 40)
	private String nombre;
	
	@NotBlank
	@Size(max = 1)
	@Pattern(regexp="[MF]")
	private String sexo;
	
	@NotBlank
	@Size(max = 11)
	private String ruc;
	
	@NotBlank
	@Size(max = 50)
	private String email;
	
	@Size(max = 18)
	private String telefono;
	
	@Size(max = 8)
	private String dni;
}
