### Introduction to AWS EC2

Amazon Elastic Compute Cloud (EC2) is a web service provided by AWS that allows users to run virtual machines (called instances) in the cloud. EC2 provides scalable computing power, enabling you to easily deploy, manage, and scale applications without worrying about physical hardware.

### Key EC2 Concepts

1. **Instances**: Virtual servers in the cloud that run your applications.
2. **AMI (Amazon Machine Image)**: A template used to create EC2 instances. It includes the OS, application server, and applications.
3. **Instance Types**: Define the hardware of the host machine (CPU, memory, storage, etc.). Examples include t2.micro, m5.large, etc.
4. **Security Groups**: Act as a virtual firewall, controlling inbound and outbound traffic to instances.
5. **Key Pairs**: Used for SSH access to EC2 instances.
6. **Elastic IP**: Static IP addresses that can be associated with instances.
7. **Auto Scaling**: Automatically adjusts the number of instances based on demand.
8. **Elastic Load Balancer (ELB)**: Distributes incoming traffic across multiple instances.

### EC2 Tutorial: Launching an EC2 Instance

#### Prerequisites
1. **AWS Account**: You need to sign up for an AWS account.
2. **AWS Management Console**: This tutorial assumes you are using the AWS Console, but you can also use the AWS CLI.

### Step-by-Step Guide to Launch an EC2 Instance

#### Step 1: Log into AWS Management Console

- Go to [AWS Management Console](https://aws.amazon.com/console/).
- Log in to your account.

#### Step 2: Open the EC2 Dashboard

- In the AWS Management Console, search for **EC2** and click **EC2** under the "Compute" section.

#### Step 3: Launch a New EC2 Instance

1. **Click "Launch Instances"**.
2. **Choose an Amazon Machine Image (AMI)**:
   - Select an AMI. For this example, we’ll use **Amazon Linux 2 AMI** (free-tier eligible).
3. **Choose an Instance Type**:
   - Select **t2.micro** (free-tier eligible).
   - Click **Next: Configure Instance Details**.

#### Step 4: Configure Instance Details

1. **Number of Instances**: Keep this as 1.
2. **Network**: Choose the default VPC.
3. **Auto-assign Public IP**: Ensure this is enabled if you need SSH access over the internet.
4. Leave other settings as default and click **Next: Add Storage**.

#### Step 5: Add Storage

1. **Size (GiB)**: The default is 8 GiB, which is enough for basic use cases.
2. **Volume Type**: Keep the default as **General Purpose SSD**.
3. Click **Next: Add Tags**.

#### Step 6: Add Tags (Optional)

- You can add tags to identify your instance, e.g., "Name: MyEC2Instance".
- Click **Next: Configure Security Group**.

#### Step 7: Configure Security Group

1. **Create a new security group** or select an existing one.
2. **Add Rule**: Add an **SSH** rule to allow access to port **22**.
   - Set the source as **My IP** to restrict SSH access to your IP address.
3. You may also add **HTTP** or **HTTPS** rules if you are running a web server.
4. Click **Review and Launch**.

#### Step 8: Review and Launch

1. Review your instance details.
2. **Click "Launch"**.
3. **Create a Key Pair**:
   - Select **Create a new key pair** and name it (e.g., "MyKeyPair").
   - Download the key pair (.pem file) and store it in a safe location (you'll need it for SSH access).
4. Click **Launch Instances**.

#### Step 9: Connect to Your EC2 Instance

1. After your instance launches, go to the **EC2 Dashboard**.
2. Click **Instances** in the left-hand menu and select your instance.
3. Copy the **Public IP** of your instance.
4. Open a terminal (on Linux/macOS) or use an SSH client like PuTTY on Windows.

For Linux/macOS, use the following SSH command:
```bash
chmod 400 MyKeyPair.pem
ssh -i "MyKeyPair.pem" ec2-user@your-public-ip
```

For PuTTY on Windows:
- Convert the .pem file to a .ppk file using PuTTYgen.
- Open PuTTY, enter the **Public IP** as the host, and provide the path to the .ppk file for authentication.

#### Step 10: Install Software on EC2 (Optional)

Once connected, you can install software on your instance. For example, to install **Nginx**:

```bash
sudo yum update -y
sudo amazon-linux-extras install nginx1
sudo service nginx start
```

Access the instance’s public IP in a browser (e.g., http://your-public-ip) to see the Nginx welcome page.

#### Step 11: Terminate the Instance (Optional)

When you're done, terminate the instance to avoid incurring charges:
1. Go to the **Instances** section in the EC2 dashboard.
2. Select your instance.
3. Click **Actions** > **Instance State** > **Terminate**.

---
## Introduction to AWS Auto Scaling

### What is AWS Auto Scaling?

AWS Auto Scaling enables automatic resource management for scalable applications. It can:
- Scale EC2 instances in and out based on demand.
- Ensure that you have the right capacity to handle incoming traffic.
- Scale automatically according to defined conditions (e.g., CPU utilization).

AWS Auto Scaling can also manage other resources like DynamoDB tables, ECS services, and Aurora databases.

---

##  Auto Scaling Components

### Auto Scaling Group (ASG):

An **Auto Scaling Group** is a collection of EC2 instances managed together for scaling purposes. It defines:
- The **minimum**, **maximum**, and **desired** number of instances.
- The **launch template/launch configuration** for creating instances.
- **Availability Zones** where instances should be deployed.

### Launch Template or Launch Configuration:

- **Launch Template**: A reusable template for configuring EC2 instance details, including AMI, instance type, security groups, and more.
- **Launch Configuration**: Similar to Launch Templates, but more limited. AWS recommends using Launch Templates due to their flexibility.

### Scaling Policies:

These define the conditions under which Auto Scaling should add or remove instances (e.g., when CPU utilization exceeds 70%, scale out by one instance).
##  Creating an Auto Scaling Group

Here is a step-by-step guide to creating an Auto Scaling group for EC2 instances:

### Step 1: Create a Launch Template
1. Go to the **EC2 Dashboard** in the AWS Console.
2. In the left pane, choose **Launch Templates** and click **Create launch template**.
3. Fill in the required fields:
   - **Template Name**: A unique name for your template.
   - **AMI**: Choose an Amazon Machine Image (AMI) for your instances.
   - **Instance Type**: Choose the instance type (e.g., `t2.micro`).
   - **Key Pair**: Select or create a key pair to access your instances.
   - **Security Group**: Choose a security group that allows access to your instances.
4. Click **Create launch template**.

### Step 2: Create an Auto Scaling Group (ASG)
1. In the **EC2 Dashboard**, select **Auto Scaling Groups** from the left panel.
2. Click **Create Auto Scaling group**.
3. Configure the following:
   - **Auto Scaling Group Name**: Provide a unique name.
   - **Launch Template**: Choose the launch template created in the previous step.
4. Define the **VPC and Subnets** where instances should be launched (ensure multiple subnets across different Availability Zones).
5. Set the **Desired Capacity**, **Minimum Capacity**, and **Maximum Capacity** for the group.

### Step 3: Attach a Load Balancer (Optional)
- You can attach an **Elastic Load Balancer (ELB)** to distribute incoming traffic evenly among instances.
- Select an existing Load Balancer or create a new one.

### Step 4: Configure Scaling Policies
1. Under **Scaling Policies**, select one of the following options:
   - **Target tracking scaling**: For automatic scaling to maintain a target metric (e.g., average CPU utilization).
   - **Step scaling**: Scale based on CloudWatch alarm thresholds in increments.
   - **Simple scaling**: Scale based on a single alarm threshold.
2. Configure the conditions that will trigger scaling (e.g., CPU utilization > 70%).

### Step 5: Review and Create
- Review the configuration and click **Create Auto Scaling group**.

---

##  Scaling Policies and Configurations

### Types of Scaling Policies:

- **Target Tracking Scaling**: Automatically scales the group to maintain a target metric, such as keeping the average CPU utilization at 50%.
  - Example configuration:
    - **Metric**: Average CPU Utilization.
    - **Target**: 50%.
  
- **Step Scaling**: Increases or decreases the number of instances in predefined steps, based on CloudWatch alarms.
  - Example configuration:
    - If CPU usage is between 70-85%, add 1 instance.
    - If CPU usage is above 85%, add 2 instances.
  
- **Simple Scaling**: Adds or removes instances based on a specific CloudWatch alarm.

### Cooldown Period:
- A **cooldown period** is a pause after a scaling activity. It prevents Auto Scaling from launching or terminating additional instances before the previous scaling activity is completed.

---


### Additional EC2 Features

1. **Elastic IPs**: Assign static IPs to your EC2 instances, which remain the same even if the instance is restarted.
   - To allocate and associate an Elastic IP, go to **Elastic IPs** under **Network & Security** and allocate a new Elastic IP. Then associate it with your instance.
  
2. **Auto Scaling**: Automatically increase or decrease the number of EC2 instances in response to demand.
   - Set up Auto Scaling by defining a launch template, an auto-scaling group, and scaling policies.

3. **Elastic Load Balancer (ELB)**: Distribute incoming traffic across multiple EC2 instances.
   - You can create an Application Load Balancer under the **Load Balancing** section.

4. **Spot Instances**: Save up to 90% on compute costs by bidding for unused EC2 instances.

5. **Monitoring and Alerts**: Use **Amazon CloudWatch** to monitor your EC2 instances and set alarms for CPU usage, disk activity, etc.

---

### Conclusion

In this tutorial, you learned how to launch an EC2 instance, connect to it, and install software like Nginx. EC2 is a powerful service that enables you to run scalable, flexible compute resources in the cloud. You can extend this tutorial by setting up Auto Scaling, load balancers, or deploying complex applications such as microservices.
