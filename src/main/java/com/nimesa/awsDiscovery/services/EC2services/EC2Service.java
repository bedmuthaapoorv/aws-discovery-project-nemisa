package com.nimesa.awsDiscovery.services.EC2services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStatus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.nimesa.awsDiscovery.services.EC2services.ListInstances;
@Service
public class EC2Service {

    private final AmazonEC2 ec2Client;
    private ListInstances listInstances;
    public EC2Service(@Value("${aws.accessKeyId}") String accessKeyId,
                      @Value("${aws.secretKey}") String secretKey,
                      @Value("${aws.region}") String region) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretKey);
        this.ec2Client = AmazonEC2ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        listInstances=new ListInstances();
    }
    
    public List<Instance> listInstances(){
    	return listInstances.listInstances(ec2Client);
    }
    
    public List<String> listInstanceIds() {
    	List<String> instanceIds=new ArrayList<>(); 
    	for(Instance instance:listInstances.listInstances(ec2Client)) {
    		instanceIds.add(instance.getInstanceId());
    	}
    	return instanceIds;
    }
    
    public String getJobResult(String instanceId){
    	DescribeInstanceStatusRequest request = new DescribeInstanceStatusRequest().withInstanceIds(instanceId);
        DescribeInstanceStatusResult response = ec2Client.describeInstanceStatus(request);

        List<InstanceStatus> instanceStatuses = response.getInstanceStatuses();

        if (instanceStatuses.isEmpty()) {
            return "Instance ID not found or instance stopped";
        } else {
            InstanceState instanceState = instanceStatuses.get(0).getInstanceState();
            return instanceState.getName();
        }
    }
}
