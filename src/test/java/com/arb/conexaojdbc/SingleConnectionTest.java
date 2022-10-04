package com.arb.conexaojdbc;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import com.arb.dao.UserPosDAO;
import com.arb.model.Userposjava;

public class SingleConnectionTest {

	@Test
	public void testConectarNoBancoDados() {
		SingleConnection.getConnection();
		boolean conectou = SingleConnection.getConectado();
		assertTrue(conectou);
	}
	
	@Test
	public void inserindoUsuarioNoBD() {
		Userposjava user = new Userposjava();
		user.setNome("bronca6");
		user.setEmail("bronca6@g1.com");
		UserPosDAO dao = new UserPosDAO();
		boolean sucesso = dao.salvar(user);
		assertTrue(sucesso);
	}
	
	@Test
	public void listarTodosUsers() {
		UserPosDAO dao = new UserPosDAO();
		List<Userposjava> users = dao.getAll();
		for(Userposjava u : users) {
			System.out.println(u);
		}
	}
	
	@Test
	public void obterUsuarioPorID() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava user = dao.getByID(1L);
		System.out.println(user);
	}
	
	@Test
	public void atualizarNome() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava user = dao.getByID(2L);
		System.out.println(user);
		user.setNome(user.getNome() +" alterado");
		dao.autalizarNome(user);
		user = dao.getByID(2L);
		System.out.println(user);
	}
	
	@Test
	public void deletarUsuario() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava user = dao.getByID(3);
		if (user != null) {
			dao.deletar(user.getId());
		}
	}

}
