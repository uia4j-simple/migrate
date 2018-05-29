@echo off
echo ========= plan\compare.conf =========
type plan\compare.conf
echo ====================================

java -jar migrate-0.0.1-SNAPSHOT.jar -c compare -p plan\compare.conf %*
