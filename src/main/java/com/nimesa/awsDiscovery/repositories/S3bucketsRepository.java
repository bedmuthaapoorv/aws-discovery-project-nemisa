package com.nimesa.awsDiscovery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimesa.awsDiscovery.Entities.Instances;
import com.nimesa.awsDiscovery.Entities.S3Bucket;

public interface S3bucketsRepository  extends JpaRepository<S3Bucket, Long>{
	
}
