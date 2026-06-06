package es.uma.tesaw.proyecto_bancosol.controller.rest;

import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.service.ColaboradoresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradoresRestController {

    private final ColaboradoresService colaboradoresService;

    public ColaboradoresRestController(ColaboradoresService colaboradoresService) {
        this.colaboradoresService = colaboradoresService;
    }

    @GetMapping
    public List<ColaboradorDTO> listarColaboradores(@RequestParam(required = false) String busqueda,
                                                    @RequestParam(required = false) String zona) {
        return colaboradoresService.listarColaboradores(busqueda, zona);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorDTO> obtenerColaborador(@PathVariable String id) {
        return colaboradoresService.obtenerColaborador(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ColaboradorDTO> crearColaborador(@RequestBody ColaboradorDTO colaboradorDTO) {
        ColaboradorDTO colaboradorCreado = colaboradoresService.crearColaborador(colaboradorDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(colaboradorCreado.getIdColaborador())
                .toUri();

        return ResponseEntity.created(location).body(colaboradorCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColaboradorDTO> actualizarColaborador(@PathVariable String id,
                                                                @RequestBody ColaboradorDTO colaboradorDTO) {
        return colaboradoresService.actualizarColaborador(id, colaboradorDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarColaborador(@PathVariable String id) {
        if (!colaboradoresService.eliminarColaborador(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarArgumentosInvalidos(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
