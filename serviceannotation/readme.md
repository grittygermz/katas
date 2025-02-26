Create a program that scans a give Java package for classes annotated with a custom @Service annoation. If a class is annotated with @Service, create an instance of that class and save it in a Map. If the class constructor has parameters. initialize those parameters first (if they are also annotated with @Service in the same package) and pass them to the constructor.
Steps
1. Create the @Service Annotation:
- define a custom annotation called @Service
2. Scan the package:
- Write a method to scan a given package for classes.
- Check if the classes are annotated with @Service.
3. Instantiate Classes:
- if a class is annotated with @Service, create an instance of that class.
- If the constructor has parameters, initialize those parameters first (if they are also annotated with @Service in the same package) and pass them to the constructor
4. Store instances in a Map:
- Store the created instances in a Map<Class<?>, Object>