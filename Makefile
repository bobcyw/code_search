NAME = code-search
VERSION = 1.0.0

.PHONY: doc

doc:
	mvn lombok:delombok
	mvn javadoc:javadoc
	rm -rf target/generated-sources
