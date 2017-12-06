package com.zorro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
@EnableJpaRepositories(basePackages="com.zorro.backend.persistence.repositories")
@EntityScan(basePackages="com.zorro.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("classpath:/application-common.properties")
public class ApplicationConfig {
	
	@Value("${aws.s3.profile}")
	private String awsProfileName;
	
	@Value("${aws.access.key.id}")
	private String awsKeyId;
	
	@Value("${aws.secret.access.key}")
	private String awsKey;
	
	@Value("${aws.s3.root.bucket.name}")
	private String awss3BucketName;
	   
	
	@Bean
	public AmazonS3Client s3Client(){
		
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKeyId, awsKey);
		AmazonS3Client s3Client = new AmazonS3Client(awsCredentials);
		Region region = Region.getRegion(Regions.AP_SOUTH_1);
		s3Client.setRegion(region);
		return s3Client;
	}
	

}
