#!/bin/bash

# Find the first JAR file in the 'target' subdirectory and assign its path to a variable.
JAR_FILE=$(find target -name "*with-dependencies.jar" | head -n 1)

# Check if a JAR file was found.
if [[ -z "$JAR_FILE" ]]; then
  echo "Error: No JAR file found in the 'target' directory."
  exit 1
fi

# Run the JAR file.
echo "Found JAR: $JAR_FILE"
java -cp "$JAR_FILE:logback-classic-1.4.11.jar:slf4j-api-2.0.9.jar" us.albertzb.tarot.Tarot
