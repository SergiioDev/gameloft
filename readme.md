# Documentation

## Project Overview

This application uses:

- Java 23
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL database
- JUnit and Mockito for testing

## Prerequisites

- Java 23 JDK
- Docker and Docker Compose
- Maven

## Setup and Running the Application

### 1. Clone the Repository

```bash
git clone <repository-url>
cd gameloft
```

### 2. Database Setup

The project uses PostgreSQL running in a Docker container, this command will start the container and execute the sql script populating the database:

```bash
docker-compose up -d
```

This will start a PostgreSQL database with the following configuration:
- Host: localhost
- Port: 5432
- Database: gameloft
- Username: gameloft
- Password: asphalt


### 3. Run the Application
You can run the application using your IDE by executing the main application class.

## API Endpoints

The application provides RESTful endpoints for:
- Matching players with campaigns

Execute this curl request to see how the API works

```bash
curl --location 'localhost:8080/get_client_config/97983be2-98b7-11e7-90cf-082e5f28d836'
```

## Architectural Decisions

### Service Structure

This project uses a simple service structure with the `MatcherService` as a central service that works with multiple repositories (PlayerRepository, CampaignRepository) 
rather than having separate services each with their own repository.

This decision was made for the following reasons:

1. **Simplicity**: Since just only one endpoint was needed, a single service simplifies the codebase and makes it easier to understand for the sake of the test.

2. **Focused Responsibility**: The MatcherService has a clear, focused responsibility of handling the matching logic between players and campaigns.

For larger applications a more fine-grained service structure might be appropriate, with each entity having its own dedicated service. However, for the current requirements and scale of this application, the simpler approach is more maintainable and efficient.

### Project Structure

- `models/`: Contains DTOs and entity classes
- `repositories/`: JPA repositories for database access
- `services/`: Business logic implementation
- `controllers/`: REST API endpoints
- `exceptions/`: Custom exception classes
