## Version Control System

A simple project that replicates the basic
features of GIT, like tracking files, commiting changes, and restoring
your files to a previously commited changes version.

Start by building the project. Run the following command while in the root parent:

```
./gradlew build
```

Create your jar file:

```
./gradlew jar
```

You can now run the jar from the root and pass it the ```--help``` flag:

```
java -jar build/libs/VersionControlSystem-1.0-SNAPSHOT.jar --help
```

You can also temporarily alias the jar execution, so you can more easily play around with the app:

```
alias ggit='java -jar build/libs/VersionControlSystem-1.0-SNAPSHOT.jar'
```

From now on, you can use the **ggit** command like this: 

```ggit config "John Hernandez"```

```ggit add fileOne.txt```

```ggit commit "My First commit"```

```ggit log```