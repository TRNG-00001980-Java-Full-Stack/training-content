
### **1. Basic Docker Commands**

- **`docker --version`**
  - **Description:** Displays the version of Docker installed.
  - **Usage:** `docker --version`

- **`docker info`**
  - **Description:** Provides detailed information about Docker’s system-wide configuration.
  - **Usage:** `docker info`

### **2. Docker Images**

- **`docker pull <image>`**
  - **Description:** Downloads an image from a Docker registry (e.g., Docker Hub).
  - **Usage:** `docker pull ubuntu:latest`

- **`docker build -t <image>:<tag> <path>`**
  - **Description:** Builds a Docker image from a Dockerfile located at `<path>`.
  - **Usage:** `docker build -t myapp:1.0 .`

- **`docker images` or `docker image ls`**
  - **Description:** Lists all Docker images on the local system.
  - **Usage:** `docker images` or `docker image ls`

- **`docker rmi <image>`**
  - **Description:** Removes a Docker image from the local system.
  - **Usage:** `docker rmi myapp:1.0`

- **`docker tag <source-image> <target-image>`**
  - **Description:** Tags an image with a new name.
  - **Usage:** `docker tag myapp:1.0 myrepo/myapp:1.0`

### **3. Docker Containers**

- **`docker run <options> <image>`**
  - **Description:** Creates and starts a new container from the specified image.
  - **Usage:** `docker run -d -p 80:80 nginx`

- **`docker ps`**
  - **Description:** Lists all running containers.
  - **Usage:** `docker ps`

- **`docker ps -a`**
  - **Description:** Lists all containers, including stopped ones.
  - **Usage:** `docker ps -a`

- **`docker exec -it <container> <command>`**
  - **Description:** Executes a command inside a running container.
  - **Usage:** `docker exec -it mycontainer /bin/bash`

- **`docker stop <container>`**
  - **Description:** Stops a running container.
  - **Usage:** `docker stop mycontainer`

- **`docker start <container>`**
  - **Description:** Starts a stopped container.
  - **Usage:** `docker start mycontainer`

- **`docker restart <container>`**
  - **Description:** Restarts a running or stopped container.
  - **Usage:** `docker restart mycontainer`

- **`docker rm <container>`**
  - **Description:** Removes a stopped container from the local system.
  - **Usage:** `docker rm mycontainer`

### **4. Docker Networks**

- **`docker network ls`**
  - **Description:** Lists all Docker networks.
  - **Usage:** `docker network ls`

- **`docker network inspect <network>`**
  - **Description:** Provides detailed information about a specific network.
  - **Usage:** `docker network inspect mynetwork`

- **`docker network create <network>`**
  - **Description:** Creates a new Docker network.
  - **Usage:** `docker network create mynetwork`

- **`docker network rm <network>`**
  - **Description:** Removes a Docker network.
  - **Usage:** `docker network rm mynetwork`

### **5. Docker Volumes**

- **`docker volume ls`**
  - **Description:** Lists all Docker volumes.
  - **Usage:** `docker volume ls`

- **`docker volume inspect <volume>`**
  - **Description:** Provides detailed information about a specific volume.
  - **Usage:** `docker volume inspect myvolume`

- **`docker volume create <volume>`**
  - **Description:** Creates a new Docker volume.
  - **Usage:** `docker volume create myvolume`

- **`docker volume rm <volume>`**
  - **Description:** Removes a Docker volume.
  - **Usage:** `docker volume rm myvolume`

### **6. Docker Compose Commands**

- **`docker-compose up`**
  - **Description:** Builds, (re)creates, starts, and attaches to containers for a service.
  - **Usage:** `docker-compose up`

- **`docker-compose down`**
  - **Description:** Stops and removes containers, networks, volumes, and images created by `docker-compose up`.
  - **Usage:** `docker-compose down`

- **`docker-compose build`**
  - **Description:** Builds or rebuilds services defined in the `docker-compose.yml` file.
  - **Usage:** `docker-compose build`

- **`docker-compose logs`**
  - **Description:** Displays logs from services defined in the `docker-compose.yml` file.
  - **Usage:** `docker-compose logs`

### **7. Docker System Commands**

- **`docker system df`**
  - **Description:** Shows disk usage for Docker components.
  - **Usage:** `docker system df`

- **`docker system prune`**
  - **Description:** Removes unused data to free up space. It includes unused containers, networks, images, and optionally, volumes.
  - **Usage:** `docker system prune`

- **`docker system prune -a`**
  - **Description:** Removes all unused images, not just dangling ones.
  - **Usage:** `docker system prune -a`

These commands form the foundation for working with Docker, enabling you to build, run, manage, and clean up containers and images.