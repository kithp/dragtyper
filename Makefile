JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

run: Main.class DragTyper.class DragTyperGui.class KeyboardPanel.class ListPopup.class
	java Main

all: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<
