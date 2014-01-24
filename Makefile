JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

run: $(classes)
	java Main

all: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<
