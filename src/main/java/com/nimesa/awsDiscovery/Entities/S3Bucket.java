package com.nimesa.awsDiscovery.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class S3Bucket {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	List<String> objects;
	long threadId;
	public S3Bucket() {
		
	}
	public S3Bucket( List<String> objects, long threadId) {
		super();
		this.objects = objects;
		this.threadId=threadId;
	}
	
}
