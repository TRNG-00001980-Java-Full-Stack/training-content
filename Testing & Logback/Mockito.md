**Mockito**
- Mockito is a popular mocking framework for Java that allows you to create mock objects for unit testing. It helps in isolating the behavior of the unit under test by simulating the interactions with dependencies.

**Key Features of Mockito**

- Creating Mocks: Generate mock objects that can simulate the behavior of real objects.
- Stubbing: Define how mock objects should respond to method calls.
- Verifying Interactions: Check if certain methods were called or not called on mocks.
- Spy Objects: Create partial mocks where some methods are real and others are stubbed.
- Argument Matchers: Specify conditions for method arguments when stubbing or verifying interactions.


**Basic Annotations**
- @Mock: Used to create and inject mock objects.
- @InjectMocks: Creates an instance of the class under test and injects mocks into it.


**Stubs**
- Stubbing in Mockito refers to specifying the behavior of a mock object when certain methods are called. This allows you to define how a mock should behave without needing to interact with actual implementations.