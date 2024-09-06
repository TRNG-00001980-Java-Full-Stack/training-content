### Hoisting

Hoisting is a JavaScript mechanism where variable and function declarations are moved to the top of their containing scope during the compilation phase. This means that variables and functions can be used before they are declared.

#### Variable Hoisting

Variables declared with `var` are hoisted to the top of their function or global scope, but their initialization remains in place.

Example:

```javascript
console.log(a); // undefined
var a = 5;
console.log(a); // 5
```

In the above example, `var a` is hoisted to the top, but `a = 5` is not. Hence, the first `console.log(a)` outputs `undefined`.

Variables declared with `let` and `const` are also hoisted, but they are not initialized. Accessing them before the declaration results in a `ReferenceError`.

Example:

```javascript
console.log(b); // ReferenceError: Cannot access 'b' before initialization
let b = 10;
console.log(b); // 10
```

#### Function Hoisting

Function declarations are hoisted completely, including their definition.

Example:

```javascript
greet(); // Hello!

function greet() {
    console.log('Hello!');
}
```

However, function expressions (using `var`, `let`, or `const`) are not hoisted.

Example:

```javascript
sayHello(); // TypeError: sayHello is not a function

var sayHello = function() {
    console.log('Hello!');
};
```

### Errors

JavaScript provides mechanisms to handle errors gracefully using `try`, `catch`, `finally`, and `throw`.

#### Types of Errors

- **SyntaxError**: Incorrect syntax.
- **ReferenceError**: Accessing an undeclared variable.
- **TypeError**: Using a value in an inappropriate way.
- **RangeError**: A numeric value outside its allowable range.
- **URIError**: Incorrect use of global URI handling functions.

#### Handling Errors

You can use `try...catch` to handle exceptions.

Example:

```javascript
try {
    // Code that may throw an error
    let result = riskyOperation();
} catch (error) {
    // Code to handle the error
    console.error('An error occurred:', error.message);
} finally {
    // Code that runs regardless of the error
    console.log('Execution completed.');
}
```

#### Throwing Errors

You can throw custom errors using the `throw` statement.

Example:

```javascript
function divide(a, b) {
    if (b === 0) {
        throw new Error('Division by zero is not allowed.');
    }
    return a / b;
}

try {
    let result = divide(4, 0);
} catch (error) {
    console.error(error.message); // Division by zero is not allowed.
}
```

### Default Parameters

Default parameters allow you to initialize function parameters with default values if no arguments are provided.

Example:

```javascript
function greet(name = 'Guest') {
    console.log(`Hello, ${name}!`);
}

greet(); // Hello, Guest!
greet('Alice'); // Hello, Alice!
```

In this example, if no argument is passed to `greet`, `name` defaults to `'Guest'`.

### Spread and Rest Operators

#### Spread Operator

The spread operator (`...`) allows an iterable (like an array) to be expanded in places where zero or more arguments (for function calls) or elements (for array literals) are expected.

Example:

```javascript
let arr1 = [1, 2, 3];
let arr2 = [...arr1, 4, 5, 6];
console.log(arr2); // [1, 2, 3, 4, 5, 6]

let obj1 = { a: 1, b: 2 };
let obj2 = { ...obj1, c: 3 };
console.log(obj2); // { a: 1, b: 2, c: 3 }
```

#### Rest Operator

The rest operator (`...`) allows you to represent an indefinite number of arguments as an array.

Example:

```javascript
function sum(...numbers) {
    return numbers.reduce((acc, curr) => acc + curr, 0);
}

console.log(sum(1, 2, 3)); // 6
console.log(sum(4, 5, 6, 7)); // 22
```

In this example, `...numbers` collects all arguments into an array named `numbers`.

### Conclusion

Understanding hoisting, handling errors, using default parameters, and leveraging the spread and rest operators are fundamental skills for effective JavaScript programming. These concepts enable you to write cleaner, more reliable, and flexible code, enhancing your ability to develop robust web applications.