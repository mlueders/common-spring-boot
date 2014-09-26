/*
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.boot.testsupport

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
