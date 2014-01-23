JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

run: Main.class DragTyper.class
	java Main

all: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<
