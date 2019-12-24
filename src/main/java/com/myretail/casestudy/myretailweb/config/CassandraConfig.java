package com.myretail.casestudy.myretailweb.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.client.RestTemplate;

import com.datastax.driver.core.Session;

@Configuration
@EnableCassandraRepositories(basePackages = "com.myretail.casestudy.myretailweb.dao")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${cassandra.host}")
	String host;
	
	@Value("${cassandra.keyspace}")
	String keyspace;
	
	@Value("${cassandra.port}")
	String port;
    
    @Override
    public String getContactPoints() {
        return host;
    }
    
    @Override
    public int getPort() {
    	return Integer.parseInt(port);
    }

    @Override
    public String getKeyspaceName() {
        return keyspace;
    }
    
	public CassandraClusterFactoryBean cluster() {
    	CassandraClusterFactoryBean bean = super.cluster();
        bean.setJmxReportingEnabled(false);
    	return bean;
    }
	
	@Bean(name="CassandraSession")
	public Session getSession() {
		return cluster().getObject().connect();
	}
       
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(keyspace).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        return Arrays.asList(specification);
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
    
    @Bean
    public RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
}
