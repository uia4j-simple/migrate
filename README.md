Simple Database Migration Tool
===

## Support Databases

* Oracle 11
* PostgreSQL 9.3
* HANA

## Commands

* create
* compare

## Configuration
### Database
Databases are defined in `database.conf` file. Each `{KEY}` defines properties for one database.
```
db.{KEY}.type=[ORA/PG/HANA]
db.{KEY}.host=10.160.1.52
db.{KEY}.port=39015
db.{KEY}.schema=WIP
db.{KEY}.service=
db.{KEY}.user=WIP
db.{KEY}.password=Hdb12345
```

###  Plan
```
# NAME, PREFIX
table.query=[prefix/name]
table.name=[value/values]

# Compare Plan
table.compare.default=[true/false]
table.compare.strictVarchar=[true/false]
table.compare.strictNumeric=[true/false]
table.compare.strictDateTime=[true/false]
table.compare.checkNullable=[true/false]
table.compare.checkDataSize=[true/false]

# NAME, PREFIX
view.query=[prefix/name]
view.name=[value/values]

# Compare Plan
view.compare.default=[true/false]
view.compare.strictVarchar=[true/false]
view.compare.strictNumeric=[true/false]
view.compare.strictDateTime=[true/false]
view.compare.checkNullable=[true/false]
view.compare.checkDataSize=[true/false]
```

## Usage (from java)

```
> java -jar migrate-0.0.1-SNAPSHOT.jar
    -c <commnad>
    -ds <db>
    -dt <db>
    [-p <file>]
    [--print-failed]
    [--print-missing]
    [-t <table> | --table-prefix <prefix>]  
    [-v <view> | --view-prefix <prefix>]

 -c,--cmd <commnad>           command name to be executed. [compare,create]
 -ds,--db-source <db>         source database
 -dt,--db-target <db>         destination database
 -p,--plan <file>             run a command with plan file
    --print-failed            print failed items only
    --print-missing           print missing items only
 -t,--table <table>           names of tables
    --table-prefix <prefix>   table's name prefix
 -v,--view <view>             names of views
    --view-prefix <prefix>    view's name prefix
```

## Use Windows Batch
* create.bat
* create-plan.bat
* compare.bat
* compare-plan.bat

## Examples

1. Define __DEV__, __TEST__ and __PROD__ database sources. All are __HANA__.
  ```
  # Develop Environment
  db.DEV.type=HANA
  db.DEV.host=10.160.22.66
  db.DEV.port=30011
  db.DEV.schema=WIP
  db.DEV.service=
  db.DEV.user=WIP
  db.DEV.password=12345

  # Test Environment
  db.TEST.type=HANA
  db.TEST.host=10.160.3.69
  db.TEST.port=30012
  db.TEST.schema=WIP
  db.TEST.service=
  db.TEST.user=WIP
  db.TEST.password=12345

  # Production Environment
  db.PROD.type=HANA
  db.PROD.host=10.160.4.78
  db.PROD.port=30013
  db.PROD.schema=WIP
  db.PROD.service=
  db.PROD.user=WIP
  db.PROD.password=12345
  ```

2. Configure compare plan.
  ```
  # NAME, PREFIX
  table.query=
  table.name=

  #
  table.compare.default=
  table.compare.strictVarchar=true
  table.compare.strictNumeric=true
  table.compare.strictDateTime=true
  table.compare.checkNullable=true
  table.compare.checkDataSize=true

  # NAME, PREFIX
  view.query=prefix
  view.name=VIEW_

  #
  view.compare.default=true
  view.compare.strictVarchar=false
  view.compare.strictNumeric=false
  view.compare.strictDateTime=false
  view.compare.checkNullable=false
  view.compare.checkDataSize=false
  ```

### compare
1. Compare __SHOP_ORDER__ table between __DEV__ & __TEST__ environment.
  ```
  > compare.bat -ds DEV -ds TEST -table SHOP_ORDER
  ```
2. Compare tables with __SFC__ prefix and diplay ___missing___ only between __DEV__ & __PROD__ environment.
  ```
  > compare.bat -ds DEV -ds PROD --table-prefix SFC --print-missing
  ```
3. Compare views using __plan/compare.coof__ and display ___failed___ only between __TEST__ & __RPOD__ environment.
  ```
  > compare-plan.bat -ds TEST -ds PROD --print-failed
  ```

### create
1. create __SHOP_ORDER__ table from __DEV__ & __TEST__ environment.
  ```
  > create.bat -ds DEV -ds TEST -t SHOP_ORDER
  ```
2. create __VIEW_SFC__ and __VIEW_SFC_ITEM__ views from __DEV__ & __PROD__ environment.
  ```
  > create.bat -ds DEV -ds PROD -v VIEW_SFC VIEW_SFC_ITEM
  ```
