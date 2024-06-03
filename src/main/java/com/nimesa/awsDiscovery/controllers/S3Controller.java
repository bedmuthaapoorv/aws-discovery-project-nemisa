package com.nimesa.awsDiscovery.controllers;

import com.nimesa.awsDiscovery.Entities.S3Bucket;
import com.nimesa.awsDiscovery.repositories.S3bucketsRepository;
import com.nimesa.awsDiscovery.services.Jobservices.AsyncFunctionExecutor;
import com.nimesa.awsDiscovery.services.Jobservices.ThreadInfo;
import com.nimesa.awsDiscovery.services.S3services.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;
    S3bucketsRepository repository;
    @Autowired
    public S3Controller(S3Service s3Service, S3bucketsRepository repository) {
        this.s3Service = s3Service;
        this.repository=repository;
    }

    @GetMapping("/buckets")
    public List<String> listBuckets() {
        return s3Service.listBuckets();
    }
    
    @GetMapping("/getJobResult/{jobId}")
    public boolean getJobResult(@PathVariable("jobId") String jobId) {
        return s3Service.getJobResult(jobId);
    }
    
    @GetMapping("/getS3Objects/{bucketName}")
    public long getS3Objects(@PathVariable("bucketName") String bucketName) {
        try {
        	AsyncFunctionExecutor executor = new AsyncFunctionExecutor(null, s3Service);
            Future<ThreadInfo> function3Future = executor.runFunction3(bucketName);
        	ThreadInfo function3Result = function3Future.get();
        	repository.save(new S3Bucket(function3Result.getResult(), function3Result.getThreadId()));
        	return function3Result.getThreadId();
        }catch(Exception e) {
        	return 0;
        }
    }
    
    @GetMapping("/GetS3BucketObjectCount/{jobId}")
    public long getObjectCount(@PathVariable("jobId") String jobId) {
        return s3Service.getObjectCount(jobId);
    }
    
    @GetMapping("/GetS3BucketObjectlike/{bucketName}/{pattern}")
    public List<String> getObjectCount(
    		@PathVariable("bucketName") String bucketName, 
    		@PathVariable("pattern") String pattern) {
        return s3Service.getS3BucketObjectlike(bucketName, pattern);
    }
    
}
