@ECHO OFF

SET /p RUN="Are you sure to execute 'create' command? (y/n)"
IF %RUN% NEQ Y IF %RUN% NEQ y EXIT/B

java -jar db-migration.jar -c create %*
