Midas Core - JPMorgan Software Engineering Virtual Internship
Midas Core is a robust, event-driven backend system designed to process high-frequency financial transactions. This project was developed as part of the JPMorgan Chase Software Engineering Virtual Experience.

 Architecture Overview
The system follows a microservices-oriented architecture, utilizing asynchronous message processing and synchronous RESTful communication.

Key Components:
Kafka Consumer: Processes real-time transaction streams from the trader-updates topic.

Transaction Processor: Validates business logic (sender balance checks, user existence) and persists data.

Incentive API Integration: Interacts with an external REST service to calculate and apply transaction bonuses.

H2 Database: Provides persistence for user accounts and transaction history.

REST Controller: Exposes a public API for real-time balance inquiries.

Features & Implementation Details
Task 1 & 2: Event-Driven Processing
Implemented a Kafka Listener using @KafkaListener to consume JSON-serialized transaction objects.

Mapped incoming messages to a standard Transaction POJO.

Task 3: Persistence & Validation
Designed a Many-to-One relationship between TransactionRecord and UserRecord using Hibernate/JPA.

Built a custom query method findByName in the UserRepository to identify specific traders like "Waldorf" and "Wilbur".

Task 4: External Service Integration (The "Black Box")
Integrated a RestTemplate to perform POST requests to an external Incentives API.

Implemented "Bonus Logic": Incentives are added to the recipient's balance but are NOT deducted from the sender.

Task 5: REST API Exposure
Configured the application to run on a non-standard port (33400) to avoid service conflicts.

Exposed a GET /balance endpoint accepting a userId parameter and returning a JSON-serialized balance object.

Technical Stack
Java 17

Spring Boot 3 (Web, Data JPA)

Apache Kafka

H2 In-Memory Database

Maven

📝 Troubleshooting & Lessons Learned
YAML Configuration: Resolved PortInUseException by correctly nesting the server.port property, ensuring Midas Core and the Incentive API could run simultaneously.

Query Method Generation: Overcame "red line" IDE errors by manually defining custom query methods in Spring Data Repositories to handle non-ID based lookups.
