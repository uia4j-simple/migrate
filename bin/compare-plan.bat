@echo off
echo ========= plan\compare.conf =========
type plan\compare.conf
echo ====================================

java -jar db-migration.jar -c compare -p plan\compare.conf %*
