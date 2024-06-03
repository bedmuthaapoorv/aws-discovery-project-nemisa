package com.nimesa.awsDiscovery.services.Jobservices;

import java.util.List;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimesa.awsDiscovery.services.EC2services.EC2Service;
import com.nimesa.awsDiscovery.services.S3services.S3Service;

@Service
public class AsyncFunctionExecutor {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final S3Service s3Service;
    private final EC2Service ec2Service;

	public AsyncFunctionExecutor(EC2Service ec2Service, S3Service s3Service) {
		this.s3Service = s3Service;
    	this.ec2Service = ec2Service;
	}


	public Future<ThreadInfo> runFunction1() {
        return executor.submit(() -> {
            // Function 1 logic
            long threadId = Thread.currentThread().getId();
            // System.out.println("Function 1 is running in thread: " + threadId);
            return new ThreadInfo(ec2Service.listInstanceIds(), threadId); // Return result and thread ID
        });
    }

    public Future<ThreadInfo> runFunction2() {
        return executor.submit(() -> {
            // Function 2 logic
            long threadId = Thread.currentThread().getId();
            System.out.println("Function 2 is running in thread: " + threadId);
            return new ThreadInfo(s3Service.listBuckets(), threadId); // Return result and thread ID
        });
    }

    public Future<ThreadInfo> runFunction3(String bucketName) {
        return executor.submit(() -> {
            // Function 2 logic
            long threadId = Thread.currentThread().getId();
            System.out.println("Function 2 is running in thread: " + threadId);
            return new ThreadInfo(s3Service.getS3BucketObjectlike(bucketName, ""), threadId); // Return result and thread ID
        });
    }
    
    public void shutdownExecutor() {
        executor.shutdown();
    }

//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        AsyncFunctionExecutor executor = new AsyncFunctionExecutor();
//
//        Future<ThreadInfo> function1Future = executor.runFunction1();
//        Future<ThreadInfo> function2Future = executor.runFunction2();
//
//        // Obtain thread IDs as soon as they are created
//        ThreadInfo function1Result = function1Future.get();
//        ThreadInfo function2Result = function2Future.get();
//
//        System.out.println("Thread ID for Function 1: " + function1Result.getThreadId());
//        System.out.println("Thread ID for Function 2: " + function2Result.getThreadId());
//
//        executor.shutdownExecutor();
//    }

    // Class to hold result and thread ID
    
}
