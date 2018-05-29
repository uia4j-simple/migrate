@ECHO OFF

SET /p RUN="Are you sure to execute 'create' command? (y/n)"
IF %RUN% NEQ Y IF %RUN% NEQ y EXIT/B

java -jar migrate-0.0.1-SNAPSHOT.jar -c create %*
