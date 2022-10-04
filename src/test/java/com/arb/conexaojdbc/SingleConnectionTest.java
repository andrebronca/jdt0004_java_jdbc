package com.arb.conexaojdbc;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import com.arb.dao.UserPosDAO;
import com.arb.model.BeanUserFone;
import com.arb.model.Telefone;
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
		user.setNome("andre");
		user.setEmail( user.getNome() + "@java.com");
		UserPosDAO dao = new UserPosDAO();
		boolean sucesso = dao.salvar(user);
		assertTrue(sucesso);
	}
	
	@Test
	public void inserirTelefone() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava user = dao.getByID(2);
		if (user.getId() != null) {
			Telefone fone = new Telefone();
			fone.setNumero("9987");
			fone.setTipo("fixo");
			fone.setProprietario(user.getId());
			dao.salvarTelefone(fone);
		}
	}
	
	@Test
	public void listaTodosUsuariosTelefones() {
		UserPosDAO dao = new UserPosDAO();
		List<BeanUserFone> lista = dao.listaUserFones();
		for(BeanUserFone o : lista) {
			System.out.println(o);
		}
	}
	
	@Test
	public void deleteUserENumeroTelefone() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava user = dao.getByID(2);
		if (user.getId() != null) {
			dao.deletarFone(user.getId());
		}
	}
	
	@Test
	public void listaTelefonesUsuarioPorID() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava user = dao.getByID(2);
		if (user.getId() != null) {
			List<BeanUserFone> lista = dao.listaFonesUserByID(user.getId());
			for(BeanUserFone o : lista) {
				System.out.println(o);
			}
		}
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
		Userposjava user = dao.getByID(4);
		if (user.getId() != null) {
			System.out.println(user);
		}
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
		if (user.getId() != null) {
			dao.deletar(user.getId());
		}
	}

}
