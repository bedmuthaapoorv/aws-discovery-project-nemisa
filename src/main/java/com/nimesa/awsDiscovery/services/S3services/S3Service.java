package com.nimesa.awsDiscovery.services.S3services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    public S3Service(@Value("${aws.accessKeyId}") String accessKeyId,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public List<String> listBuckets() {
    	List<String> bucketIds=new ArrayList<>();
    	for(Bucket bucket: s3Client.listBuckets()) {
        	bucketIds.add(bucket.getName());
        }
        return bucketIds;
    }

	public boolean getJobResult(String jobId) {
		try {
			return s3Client.doesBucketExistV2(jobId);
		}catch(Exception e) {
			return false;
		}
	}
	
	public long getObjectCount(String bucketName) {
        try {
		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result;
        long totalObjects = 0;

        do {
            result = s3Client.listObjectsV2(request);
            totalObjects += result.getKeyCount();
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        return totalObjects;
        }catch(Exception e) {
        	return 0;
        }
    }

	public List<String> getS3BucketObjectlike(String bucketName, String pattern) {
		List<String> matchedFiles = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result;

        do {
            result = s3Client.listObjectsV2(request);
            result.getObjectSummaries().forEach(summary -> {
                String key = summary.getKey();
                if (key.contains(pattern)) {
                    matchedFiles.add(key);
                }
            });
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        return matchedFiles;
		 
	}
}
