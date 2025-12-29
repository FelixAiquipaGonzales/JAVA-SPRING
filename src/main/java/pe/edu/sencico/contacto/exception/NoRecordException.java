package pe.edu.sencico.contacto.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoRecordException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoRecordException(String message) {
		super(message);
	}
	
}
