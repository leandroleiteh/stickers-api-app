package br.com.leandrodev.linguagens.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class LinguagemController {

	@Autowired
	private LinguagemRepository repositorio;

	@GetMapping("/linguagens")
	public List<Linguagem> obterLinguagens() {
		List<Linguagem> linguagens = repositorio.findByOrderByRanking();
		return linguagens;
	}

	@PostMapping("/linguagens")
	public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem) {
		Linguagem linguagemSalva = repositorio.save(linguagem);
		return new ResponseEntity<>(linguagemSalva, HttpStatus.CREATED);
	}

	@GetMapping("/linguagens/{id}")
	public ResponseEntity<Linguagem> obterLinguagemId(@PathVariable String id) {
		return repositorio.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping("/linguagens/{id}")
	public ResponseEntity<Void> deletarLinguagem(@PathVariable String id){
    	if(!repositorio.existsById(id)) {
        	return ResponseEntity.notFound().build();
    	}
    	repositorio.deleteById(id);
    	return ResponseEntity.noContent().build();
    }
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/linguagens/{id}")
	public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem){
		if(!repositorio.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		Linguagem linguagemSalva = repositorio.save(linguagem);
		return linguagemSalva;
		
		
		
	}

}