## AWS Discovery Assignment Documentation:

## application.properties:

**Please ensure to replace the Application.properties file with the one provided here as it includes Nimesa AWS credentials, I did not push it to a public repository rather providing it here**


```
spring.application.name=awsDiscovery
aws_access_key_id = add-access-key-id
aws_secret_access_key = add-secret-access-key
aws.accessKeyId=add-access-key-id
aws.secretKey=add-secret-access-key
aws.region=ap-south-1
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```



## API documentation: \
 \
Endpoint:** <code>/api/general/discoverServices/{services}</code></strong>

This API asynchronously discovers EC2 instances in the Mumbai Region in one thread and S3 buckets in another thread and persist the result in DB

The services provided must be comma separated, for example:

**[http://localhost:8080/api/general/discoverServices/EC2,S3](http://localhost:8080/api/general/discoverServices/EC2,S3)**

Output: Job Ids regarding each service
![Screenshot 2024-05-30 124729](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/b20c8e99-ebc2-44c3-a541-7316f8c8f39c)

Endpoint:** <code>/api/general/getJobResult/{jobId}</code></strong>

This method should return the Job Status for the given ID, Job status can be Success or In Progress or Failed.

In case of invalid Job Id, the API will return Failed.
![Screenshot 2024-05-30 130403](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/aea73187-be99-4fea-9f0b-57836f0783b7)

Endpoint:** <code>/api/general/getDiscoveryResults/{service}</code></strong>

This method should return the EC2 instances if the service is “EC2” and should return list of buckets if the service is “S3”


![Screenshot 2024-05-30 130552](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/f0c736c2-acde-4c3d-8550-f38c91e22336)

Endpoint:** <code>/api/s3/getS3Objects/{bucketName}</code></strong>

Discover all the File Names in the S3 bucket and persist in the DB. 

Return the JobID as it may take time for the job the complete

![Screenshot 2024-05-30 131047](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/63b613fa-41d0-43d0-96a4-99315136a108)

Endpoint:** <code>/api/s3/GetS3BucketObjectCount/{jobId}</code></strong>

Returns list of objects inside a bucket regarding a respective gets3Objects request jobId

![Screenshot 2024-05-30 131404](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/737f9599-3e51-4fb7-8fc3-9d67f5b00109)

Endpoint:** <code>/api/s3/GetS3BucketObjectlike/{bucketName}/{pattern}</code></strong>

Returns List of file names matching the pattern inside the bucket name
![Screenshot 2024-05-30 131524](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/089cd439-07d1-4d09-8649-0d576bb57fed)

## Database documentation:

The assignment uses h2 database, after you run the project visit [http://localhost:8080/h2-console](http://localhost:8080/h2-console) 

Username: sa

There is no password 

Click on connect to access the database

There are 2 tables that are programmatically created as soon as the backend server starts:



1. INSTANCES: Includes the result of discover services api call

![Screenshot 2024-05-30 132734](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/e06266db-543f-4d3d-9f5c-0cfb91c28aee)


2. S3BUCKET: includes the result of get S3 bucket objects API call

![Screenshot 2024-05-30 132820](https://github.com/bedmuthaapoorv/aws-discovery-project-nemisa/assets/40523798/5ea2eef5-adc6-42fc-a580-5caa539e9e37)

It should be noted the Thread Id and Job Id are interchangeably used in the documentation.
