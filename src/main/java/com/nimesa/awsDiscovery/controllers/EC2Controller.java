package com.nimesa.awsDiscovery.controllers;

import com.amazonaws.services.ec2.model.Instance;
import com.nimesa.awsDiscovery.services.EC2services.EC2Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ec2")
public class EC2Controller {

    private final EC2Service ec2Service;

    @Autowired
    public EC2Controller(EC2Service ec2Service) {
        this.ec2Service = ec2Service;
    }

    @GetMapping("/instanceIds")
    public List<String> listInstanceIds() {
        return ec2Service.listInstanceIds();
    }
    
    @GetMapping("/instances")
    public List<Instance> listInstances() {
        return ec2Service.listInstances();
    }
    
    @GetMapping("/getJobResult/{jobId}")
    public String getJobResult(@PathVariable("jobId") String jobId) {
        return ec2Service.getJobResult(jobId);
    }
    
}
