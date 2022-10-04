package com.arb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arb.conexaojdbc.SingleConnection;
import com.arb.model.BeanUserFone;
import com.arb.model.Telefone;
import com.arb.model.Userposjava;

public class UserPosDAO {
	private Connection conn;
	private final String TABELA = "userposjava";

	public UserPosDAO() {
		conn = SingleConnection.getConnection();
	}

	public boolean salvar(Userposjava user) {
		boolean res = false;
		try {
			String sql = "insert into " + TABELA + " (nome, email) values (?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getEmail());
			ps.execute();
			conn.commit();
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	//gambiarra, deveria ter um dao específico
	public void salvarTelefone(Telefone fone) {
		String sql = "insert into telefoneuser(numero,tipo,usuariopessoa) values(?,?,?)"; 
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fone.getNumero());
			ps.setString(2, fone.getTipo());
			ps.setLong(3, fone.getProprietario());
			ps.execute();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Userposjava> getAll() {
		List<Userposjava> users = new ArrayList<Userposjava>();
		String sql = "select * from " + TABELA;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Userposjava user = new Userposjava();
				user.setId(rs.getLong("id"));
				user.setNome(rs.getString("nome"));
				user.setEmail(rs.getString("email"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public Userposjava getByID(long id) {
		Userposjava user = new Userposjava();
		String sql = "select * from " + TABELA + " where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setId(rs.getLong("id"));
				user.setNome(rs.getString("nome"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public void autalizarNome(Userposjava user) {
		String sql = "update " + TABELA + " set nome = ? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNome());
			ps.setLong(2, user.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deletar(Long id) {
		String sql = "delete from userposjava where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//delete em cascata
	public void deletarFone(Long idUser) {
		String sqlTelefone = "delete from telefoneuser where usuariopessoa = ?";
		String sqlUser = "delete from "+ TABELA +" where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlTelefone);
			ps.setLong(1, idUser);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(sqlUser);
			ps.setLong(1, idUser);
			ps.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List<BeanUserFone> listaUserFones(){
		List<BeanUserFone> lista = new ArrayList<BeanUserFone>();
		String sql = "select u.id, u.nome, u.email, t.numero "
				+ "from userposjava u "
				+ "inner join telefoneuser t on u.id = t.usuariopessoa "
				+ "order by u.id ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BeanUserFone bUserFone = new BeanUserFone();
				bUserFone.setId(rs.getLong("id"));
				bUserFone.setNome(rs.getString("nome"));
				bUserFone.setEmail(rs.getString("email"));
				bUserFone.setNumero(rs.getString("numero"));
				lista.add(bUserFone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}
	
	public List<BeanUserFone> listaFonesUserByID(Long id){
		List<BeanUserFone> lista = new ArrayList<BeanUserFone>();
		String sql = "select u.id, u.nome, u.email, t.numero "
				+ "from userposjava u "
				+ "inner join telefoneuser t on u.id = t.usuariopessoa "
				+ "where u.id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BeanUserFone bUserFone = new BeanUserFone();
				bUserFone.setId(rs.getLong("id"));
				bUserFone.setNome(rs.getString("nome"));
				bUserFone.setEmail(rs.getString("email"));
				bUserFone.setNumero(rs.getString("numero"));
				lista.add(bUserFone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
