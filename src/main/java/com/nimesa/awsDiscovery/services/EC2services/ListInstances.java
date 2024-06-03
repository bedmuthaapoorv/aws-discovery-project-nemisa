package com.nimesa.awsDiscovery.services.EC2services;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

public class ListInstances {
	public List<Instance> listInstances(final AmazonEC2 ec2Client) {
        List<Instance> instances = new ArrayList<>();
        boolean done = false;
        DescribeInstancesRequest request = new DescribeInstancesRequest();

        while (!done) {
            DescribeInstancesResult response = ec2Client.describeInstances(request);
            for (Reservation reservation : response.getReservations()) {
                instances.addAll(reservation.getInstances());
            }
            request.setNextToken(response.getNextToken());
            if (response.getNextToken() == null) {
                done = true;
            }
        }

        return instances;
    }
}
