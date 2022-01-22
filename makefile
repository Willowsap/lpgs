HAMCREST_JAR = /usr/share/java/hamcrest/core-1.1.jar
JUNIT5_JAR = junit-platform-console-standalone-1.2.0.jar 
JUNIT5_RUNNER = org.junit.platform.console.ConsoleLauncher
JC = javac
JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	game/LicensePlateGameSolver.java \
	tests/Driver.java

TESTS = \
	tests/LicensePlateGameSolverTest.java

default: classes compile

classes: $(CLASSES:.java=.class)

compile: classes 
	javac -cp .:$(JUNIT5_JAR) $(TESTS)

run:
	java tests/Driver

clean:
	rm -f *.class

test: $(JUNIT5_JAR)
	java -cp .:$(JUNIT5_JAR) $(JUNIT5_RUNNER) --scan-class-path 

