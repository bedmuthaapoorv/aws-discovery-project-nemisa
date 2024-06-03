package com.nimesa.awsDiscovery.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimesa.awsDiscovery.Entities.Instances;
import com.nimesa.awsDiscovery.repositories.DiscoverServicesRepository;
import com.nimesa.awsDiscovery.services.EC2services.EC2Service;
import com.nimesa.awsDiscovery.services.Jobservices.AsyncFunctionExecutor;
import com.nimesa.awsDiscovery.services.Jobservices.ThreadInfo;
import com.nimesa.awsDiscovery.services.S3services.S3Service;

@RestController
@RequestMapping("/api/general")
public class GeneralController {
	private final EC2Service ec2Service;
	private final S3Service s3Service;
	private final DiscoverServicesRepository repository;
	private Set<Long> activeThreads;
    @Autowired
    public GeneralController(EC2Service ec2Service, S3Service s3Service,DiscoverServicesRepository repository) {
        this.ec2Service = ec2Service;
        this.s3Service = s3Service;
        this.repository = repository;
        activeThreads=new HashSet<>();
    }
    
	@GetMapping("/discoverServices/{services}")
    public Set<Long> discoverServices(@PathVariable String[] services) {
        Set<String> servicesSet=new HashSet<String>();
        for(String service: services) {
        	servicesSet.add(service);
        }
        
        
        
        AsyncFunctionExecutor executor = new AsyncFunctionExecutor(ec2Service, s3Service);

        Future<ThreadInfo> function1Future = executor.runFunction1();
        Future<ThreadInfo> function2Future = executor.runFunction2();

        activeThreads.clear();
        // Obtain thread IDs as soon as they are created
        try {
        	if(servicesSet.contains("EC2")) {
        		ThreadInfo function1Result = function1Future.get();
        		activeThreads.add(function1Result.getThreadId());
        		repository.save(new Instances(function1Result.getResult(), "EC2", function1Result.getThreadId()));
        		
            }
        	if(servicesSet.contains("S3")) {
            	ThreadInfo function2Result = function2Future.get();
            	activeThreads.add(function2Result.getThreadId());
            	repository.save(new Instances(function2Result.getResult(), "S3", function2Result.getThreadId()));            	
            	
        	}

        	executor.shutdownExecutor();
        	return activeThreads;
        }catch(Exception e) {
            executor.shutdownExecutor();
        	return null;
        }

	}
	
	@GetMapping("/getJobResult/{jobId}")
	public String GetJobResult(@PathVariable("jobId") Long jobId) {
		if(activeThreads.contains(jobId)) {
			List<Instances> result=repository.findByThreadId(jobId);
			if(result==null) {
				return "In progress";
			}else {
				return "Success";
			}
		}else {
			return "Failed";
		}
	}
	
	@GetMapping("/getDiscoveryResults/{service}")
	public Set<String> GetJobResult(@PathVariable("service") String service) {
		Set<String> result=new HashSet<>();
		List<Instances> instances=repository.findByService(service);
		for(Instances instance: instances) {
			for(String instanceIds: instance.getInstances()) {
				result.add(instanceIds);
			}
		}
		return result;
	}
}
