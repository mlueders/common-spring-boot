package com.bancvue.testsupport

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.exception.DatabaseException
import liquibase.exception.LiquibaseException
import liquibase.exception.LockException
import liquibase.resource.ResourceAccessor

import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException

class LiquibaseTestSupport {
	private Liquibase liquibase;

	public LiquibaseTestSupport(String fileName, ResourceAccessor resourceAccessor, DataSource dataSource) {
		try {
			Connection conn = dataSource.getConnection();
			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(conn));
			liquibase = new Liquibase(fileName, resourceAccessor, database);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (DatabaseException e) {
			throw new RuntimeException(e);
		} catch (LiquibaseException e) {
			throw new RuntimeException(e);
		}
	}

	public void cleanCreate() {
		cleanCreate(null);
	}

	public void cleanCreate(String context) {
		try {
			liquibase.dropAll();
			liquibase.update(context);
		} catch (DatabaseException e) {
			throw new RuntimeException(e);
		} catch (LockException e) {
			throw new RuntimeException(e);
		} catch (LiquibaseException e) {
			throw new RuntimeException(e);
		}

	}

	public void close() {
		try {
			liquibase.dropAll();
			liquibase.getDatabase().getConnection().close();
		} catch (DatabaseException e) {
			throw new RuntimeException(e);
		} catch (LockException e) {
			throw new RuntimeException(e);
		}
	}
}
