package com.airbnb.config;

import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class AWSS3Config {

        @Value("${access-key}")
        private String accessKey;

        @Value("${secret-key}")
        private String secretKey;

        @Value("${region}")
        private String region;

        @Bean
        public AmazonS3 s3Client() {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
            return AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.fromName(region))
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
        }
}
/*Config file acts as a medium to log in to aws & to login to aws we require access key, secret key,and region
and only then we can log in to aws & in order to programmatically login we require these above methods
 */
