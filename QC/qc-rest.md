# QC Questions on REST
 - What is REST?
   - REpresentational State Tranfer - An architectural style for web services described by Roy Fielding in order to write modern web services. There are 6 constraints, and following these should result in powerful, useful, long-lived applications.

 - What are the REST Constraints?
   - Unified Interface - No matter how it is accessed(consumed), the same interface is presented to the user.
   - Statelessness - The RESTful server does not maintain state, therefore every request sent to the server must include all necessary info to process it.
   - Caching - Storing info in memory where it can be accessed rapidly for repeated requests.
   - Client-server architecture - Two parts to a full application that are independent of eachother, the client is the user-facing part, and the server is the remote part that interfaces with persistence.
   - Layered - The application is conceptually split into multiple layers that abstract parts of the application away from other parts, for instance the API layer doesn't need to directly invoke methods on classes in the persistence layer.
   - Code On-Demand - This one is optional, and basdically means we can supply executable code to the client.
 - Marshalling and Unmarshalling
   - Translating data between different wys of structuring or representing that data.

 - What is HTTP?
   - Hyper Text Transfer Protocol - The client/server protocol of the world wide web. Note: a protocol is a set of rules defining unabiguous communication.

 - What are the HTTP verbs/methods?
   - GET, POST, PUT, PATCH, HEAD, OPTIONS, CONNECT, TRACE, DELETE
   - Important ones to know: GET and PUT/POST/PATCH

 - What are the differences between PUT, POST, and PATCH requests?
   - POST - traditionally creating a new resource
   - PUT - traditionally updates the entire resource
   - PATCH - traditionally updating only part of a resource

 - What are some differences between GET and POST requests?
   - GET is for requesting representations from the server while POST (as well as PATCH and PUT) are used to supply representations to the server

 - What does it mean for an operation to be safe? What HTTP methods are safe?
   - The operation is read-only, the state of the resources is unchanged.

 - What does it mean for an operation to be idempotent? What HTTP methods are idempotent?
   - An idempotent operation will always have the same result regardless of how many times it is invoked, and regardless of the state of the resource before.

 - When would your API respond with a 201 status code?
   - When a POST request has been processed and a new resource was created.

 - When would your API respond with a 204 status code?
   - Indicating the request is successful, and that the client does not require navigation to another page.

 - When would your API respond with a 401 status code?
   - Unauthorized - request has not been completed becuase it lacks valid authentication.

 - When would your API respond with a 403 status code?
   - Forbidden - the server refuses to authorize the request.

 - When would your API respond with a 404 status code?
   - Resource not found.

 - When would your API respond with a 405 status code?
   - Method not allowed - for instance trying a POST when the server doesn't accept POSTs on a resource.

 - When would your API respond with a 409 status code?
   - Conflict - when the request could not be processed. For instance when the resource is not in a valid state, or when the request would invalidate the state of the resource.

 - What is a resource?
   - The target of an HTTP request, some data that can be consumed or manipulated.

 - What is your understanding of what are RESTful web services are?
   - Web services that adhere to the RESTful constraints, are clean, robust, useful, uniform, and long-lasting thanks to those constraints.

 - Name the protocol which is used by RESTful web services.
   - HTTP.

 - Explain the term ‘Addressing’ with respect to RESTful web service.
   - This just means that resources can be found at some location. (Think URIs and URLs)

 - List the advantages and disadvantages of ‘Statelessness’
   - PROS: 
     - Security - less chance sensitive info can be extracted from server memory.
     - Server-side interruptions will not necessarily impact clients.
     - Scalability - you can implement horizontal scaling easily
     - Adheres to RESTful constraints
   - CONS: 
     - Each request carrys more info and is more complex to process
     - Complexity

 - What is Caching?
   - Storing frequently accessed information for rapid repeated lookup.
   
 - What is a Payload?
   - The sought after data transmitted in a request/response body. Typically JSON or XML.
