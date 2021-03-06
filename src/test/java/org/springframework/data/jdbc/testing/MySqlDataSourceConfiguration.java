/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jdbc.testing;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * {@link DataSource} setup for MySQL.
 * 
 * @author Jens Schauder
 * @author Oliver Gierke
 */
@Configuration
@Profile("mysql")
class MySqlDataSourceConfiguration extends DataSourceConfiguration {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jdbc.testing.DataSourceConfiguration#createDataSource()
	 */
	protected DataSource createDataSource() {

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql:///test?user=root");

		return dataSource;
	}

	@PostConstruct
	public void initDatabase() {

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql:///?user=root");

		ClassPathResource createScript = new ClassPathResource("create-mysql.sql");
		DatabasePopulator databasePopulator = new ResourceDatabasePopulator(createScript);

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDatabasePopulator(databasePopulator);
		initializer.setDataSource(dataSource);
		initializer.afterPropertiesSet();
	}
}
