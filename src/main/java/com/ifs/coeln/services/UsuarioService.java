package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.AlunoDTO;
import com.ifs.coeln.dto.ServidorDTO;
import com.ifs.coeln.entities.Curso;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Turma;
import com.ifs.coeln.entities.Usuario;
import com.ifs.coeln.repositories.UsuarioRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private HistoricoService hisService;

	@Autowired
	private TurmaService turmaService;

	@Autowired
	private CursoService cursoService;

	public List<AlunoDTO> findAllAlunos() {
		return filterListAlunos(repository.findAll());
	}

	private List<AlunoDTO> filterListAlunos(List<Usuario> list) {
		List<AlunoDTO> dto = new ArrayList<>();
		for (Usuario usuario : list) {
			if (!usuario.getIs_deleted() && !usuario.getIs_servidor()) {
				dto.add(new AlunoDTO(usuario));
			}
		}
		return dto;
	}

	public Usuario findByMatricula(String id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
	}

	public AlunoDTO insertAluno(Usuario obj) {
		try {
			obj.setIs_servidor(false);
			obj.setIs_deleted(false);
			Usuario usuario = new Usuario(obj);
			Turma turma = turmaService.findById(obj.getTurma().getId());
			if (turma.getIs_deleted()) {
				throw new ResourceNotFoundException("Turma", turma.getId());
			}
			Curso curso = cursoService.findById(obj.getCurso().getId());
			if (curso.getIs_deleted()) {
				throw new ResourceNotFoundException("Curso", curso.getId());
			}
			usuario = repository.save(usuario);
			usuario.setTurma(turma);
			usuario.setCurso(curso);
			hisService.insert(new Historico(null, "inserido", usuario.getMatricula(), "Aluno", 1L));
			return new AlunoDTO(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "aluno");
		}
	}

	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Item", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public AlunoDTO updateAluno(String id, Usuario obj) {
		try {
			Usuario entity = repository.getOne(id);
			updateDataAluno(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getMatricula(), "Aluno", 1L));
			}
			return new AlunoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Item", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "item");
		}
	}

	private void updateDataAluno(Usuario entity, Usuario obj) {
		entity.setNome((obj.getNome() == null) ? entity.getNome() : obj.getNome());
		entity.setNome_projeto((obj.getNome_projeto() == null) ? entity.getNome_projeto() : obj.getNome_projeto());
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		if (obj.getCurso() == null) {
		} else {
			entity.setCurso(cursoService.findById(obj.getCurso().getId()));
		}
		if (obj.getTurma() == null) {
		} else {
			entity.setTurma(turmaService.findById(obj.getTurma().getId()));
		}
	}
	////////////////////////

	public List<ServidorDTO> findAllServidores() {
		return filterListServidores(repository.findAll());
	}

	private List<ServidorDTO> filterListServidores(List<Usuario> list) {
		List<ServidorDTO> dto = new ArrayList<>();
		for (Usuario usuario : list) {
			if (usuario.getIs_deleted() == false && usuario.getIs_servidor() == true) {
				dto.add(new ServidorDTO(usuario));
			}
		}
		return dto;
	}

	public ServidorDTO insertServidor(Usuario obj) {
		try {
			obj.setIs_servidor(true);
			obj.setIs_deleted(false);
			Usuario usuario = new Usuario(obj);
			Curso curso = cursoService.findById(obj.getCurso().getId());
			if (curso.getIs_deleted()) {
				throw new ResourceNotFoundException("Curso", curso.getId());
			}
			usuario = repository.save(usuario);
			usuario.setCurso(curso);
			hisService.insert(new Historico(null, "inserido", usuario.getMatricula(), "Servidor", 1L));
			return new ServidorDTO(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "aluno");
		}
	}

	public ServidorDTO updateServidor(String id, Usuario obj) {
		try {
			Usuario entity = repository.getOne(id);
			updateDataServidor(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getMatricula(), "Servidor", 1L));
			}
			return new ServidorDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Item", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "item");
		}
	}

	private void updateDataServidor(Usuario entity, Usuario obj) {
		entity.setNome((obj.getNome() == null) ? entity.getNome() : obj.getNome());
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		if (obj.getCurso() == null) {
		} else {
			entity.setCurso(cursoService.findById(obj.getCurso().getId()));
		}
	}

}
