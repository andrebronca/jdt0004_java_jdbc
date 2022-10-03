package com.arb.conexaojdbc;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Test;

public class SingleConnectionTest {

	@Test
	public void testConectarNoBancoDados() {
		SingleConnection.getConnection();
		boolean conectou = SingleConnection.getConectado();
		assertTrue(conectou);
	}

}
