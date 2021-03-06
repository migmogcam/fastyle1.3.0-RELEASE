package app.fastyleApplication.fastyle.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
		
		private Integer id;
			
		@NotBlank
		@Length(max = 200)
		private String usuario;
		
		@NotBlank
		@Length(max = 200)
		private String password;

		@NotBlank
		@Length(max = 100)
		private String name;
		
		@NotBlank
		@Length(max = 100)
		private String apellido1;
		
		@NotBlank
		@Length(max = 100)
		 private String apellido2;
		
		@NotBlank
		@Length(max = 100)
		private String provincia;
		
		@NotBlank
		@Length(max = 100)
		 private String ciudad;
		
		@NotBlank
		@Length(max = 100)
		@Email
		private String eMail;
		
		@NotBlank
		@Length(max = 250)
		private String descripcion;
		
		@NotBlank
		@Length(max = 100)
		private String autority;
		
	
}
