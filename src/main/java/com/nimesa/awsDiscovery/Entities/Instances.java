package com.nimesa.awsDiscovery.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Instances {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	List<String> instances;
	String service;
	long threadId;
	public Instances() {
		
	}
	public Instances(List<String> instances, String service, long threadId){
		this.instances=instances;
		this.service=service;
		this.threadId=threadId;
	}
	public long getThreadId() {
		return threadId;
	}
	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getInstances() {
		return instances;
	}
	public void setInstances(List<String> instances) {
		this.instances = instances;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	@Override
	public String toString() {
		return "Instances [id=" + id + ", instances=" + instances + ", service=" + service + ", threadId=" + threadId
				+ "]";
	}
}
