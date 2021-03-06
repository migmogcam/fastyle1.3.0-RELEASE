package app.fastyleApplication.fastyle.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.services.EsteticistaService;
import app.fastyleApplication.fastyle.services.ServicioEsteticoService;
import app.fastyleApplication.fastyle.services.UsuarioService;

@Controller
public class ServicioEsteticoController {

	@Autowired
	ServicioEsteticoService service;

	@Autowired
	EsteticistaService serviceEsteticista;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	EsteticistaService esteticistaService;

	@GetMapping("/servicioEsteticoRegistro")
	public String addServicioEstetico(Model model) {
		model.addAttribute("servicioEstetico", new ServicioEstetico());

		return "crearServicioEstetico";
	}

	@PostMapping("/crearServicioEstetico")
	public String addServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "error";
		}
		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "servicioCreado";
	}

	@GetMapping("/servicioEsteticoEdit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		try {
			ServicioEstetico servicioEstetico = service.getServicioEsteticoById(id);
		} catch (Exception e) {
			return "error";
		}
		model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
		return "accionRealizada";
	}

	@PostMapping("/servicioEsteticoUpdate/{id}")
	public String updateServicioEsteticoService(@PathVariable("id") Integer id,
			@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "error";
		}

		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
		return "accionRealizada";
	}

	@GetMapping("/servicioEsteticoDelete/{id}")
	public String deleteServicioEsteticoService(@PathVariable("id") Integer id, Model model) {
		try {
			service.deleteServicioEsteticoById(id);
		} catch (Exception e) {
			return "error";
		}
		model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
		return "accionRealizada";
	}

	//@GetMapping("/listadoServicios")
	@GetMapping("/")
	public String listado(Model model) {
		List<ServicioEstetico> servicios = null;
		try {
			servicios = service.getAllServicioEsteticos();
			model.addAttribute("listaServicios", servicios);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "listadoServicios"; // view
	}

	@GetMapping("/servicioInfo/{id}")
	public String diplayInfo(@PathVariable("id") long id, Model model) {
		List<Esteticista> servicios = null;
		ServicioEstetico servicioEstetico = null;
		try {
			servicioEstetico = service.getServicioEsteticoById((int) id);
			String anonimo = SecurityContextHolder.getContext().getAuthentication().getName();
			if (!anonimo.equals("anonymousUser")) {
				Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
						.getAuthentication().getAuthorities();
				for (GrantedAuthority g : authorities) {
					if (g.getAuthority().equals("ROLE_ESTETICISTA")) {
						String username = SecurityContextHolder.getContext().getAuthentication().getName();
						Usuario u = this.usuarioService.findByUsuario(username);
						Esteticista c = this.esteticistaService.findByUsuario(u);
						if (servicioEstetico.getEsteticista().contains(c)) {
							model.addAttribute("est", "existe");
						} else {
							model.addAttribute("est", "noExiste");
						}

					} else {
						model.addAttribute("est", "noEs");
					}
				}
			} else {
				model.addAttribute("est", "noEs");
			}
			servicios = servicioEstetico.getEsteticista();
			model.addAttribute("listaEsteticistas", servicios);
			model.addAttribute("servicioEstetico", servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "servicioInfo"; // view
	}

	@GetMapping("/ServicioUnir/{idServ}")
	public String ServicioUnir(@PathVariable("idServ") long id, Model model) {
		ServicioEstetico servicioEstetico = null;
		try {
			servicioEstetico = service.getServicioEsteticoById((int) id);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			Esteticista c = this.esteticistaService.findByUsuario(u);
			servicioEstetico.getEsteticista().add(c);
			this.service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "accionRealizada"; // view
	}
	
	
	
	@GetMapping("/ServicioCancelar/{idServ}")
	public String ServicioCancelar(@PathVariable("idServ") long id, Model model) {
		ServicioEstetico servicioEstetico = null;
		try {
			servicioEstetico = service.getServicioEsteticoById((int) id);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			Esteticista c = this.esteticistaService.findByUsuario(u);
			servicioEstetico.getEsteticista().remove(c);
			this.service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "accionRealizada"; // view
	}

}
