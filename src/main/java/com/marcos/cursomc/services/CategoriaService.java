package com.marcos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marcos.cursomc.domain.Categoria;
import com.marcos.cursomc.repositories.CategoriaRepository;
import com.marcos.cursomc.services.exceptions.DataIntegrityException;
import com.marcos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria object) {
		// esse object.setId, se o ID estiver nulo ele identifica que é um update
		object.setId(null);
		return repo.save(object);
	}

	public Categoria update(Categoria object) {
		find(object.getId());
		return repo.save(object);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Não é possível excluir uma categoria que têm produtos");
		}
	}
	
	public List<Categoria> findAll() {
		return repo.findAll();

	}
}
