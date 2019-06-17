Database Migration Simple Tool
===
## Description
This tool helps developers to create tables and views with same structure rapidly in different databases.

### Support Databases

| Source | Target | Compare | Create |
|---|---|:---:|:---:|
| PostgreSQL | PostgreSQL | v | v |
| PostgreSQL | HANA | v | v |
| HANA | HANA | v | v |
| HANA | PostgreSQL | v | v |
| Oracle | Oracle | v | v |
| Oracle | HANA | v | v |
| Oracle | PostgreSQL | v | v |

## Command
This tool provides two commands:
* compare - check if the structure of the table and view are the same.
* create - create tables and views to destination dataabse.

### Use java.exe
```
> java -jar migrate-0.0.1-SNAPSHOT.jar
    -c <commnad>
    -ds <db>
    -dt <db>
    [-p <file>]
    [--print-failed]
    [--print-missing]
    [-t <tables> | --table-prefix <prefix>]  
    [-v <views> | --view-prefix <prefix>]

 -c,--cmd <commnad>           command name to be executed. [compare,create]
 -ds,--db-source <db>         source database, <db> defined in database.conf
 -dt,--db-target <db>         destination database, <db> defined in database.conf
 -p,--plan <file>             run a command depending on the plan file
    --print-failed            print failed items only
    --print-missing           print missing items only
 -t,--table <tables>          names of table, e.g. -t *, -t TABLE1,TABLE2
    --table-prefix <prefix>   prefix of table name
 -v,--view <views>            names of view, e.g. -v *, -v VIEW1,VIEW2
    --view-prefix <prefix>    prefix of view name
```

### Use Windows Batch
* create.bat
* create-plan.bat
* compare.bat
* compare-plan.bat

## Configuration
### database.conf
Each `<db>` defines properties for one database.
```
db.<db>.type=[ORA/PG/HANA]
db.<db>.host=10.160.1.52
db.<db>.port=39015
db.<db>.schema=WIP
db.<db>.service=
db.<db>.user=WIP
db.<db>.password=Hdb12345
```

###  plan.conf
Plan file is used to define how to compare and create tables between two databases.
Note: properties of __table.name__ and __view name__ are __CASE SENSITIVE__
```
# NAME or PREFIX, Empty will be ignore
table.query=[NAME/PREFIX]
table.name=[value/values]

# Compare output options, if default is ture, other options will be ignore.
table.compare.default=[true/false]
table.compare.strictVarchar=[true/false]
table.compare.strictNumeric=[true/false]
table.compare.strictDateTime=[true/false]
table.compare.checkNullable=[true/false]
table.compare.checkDataSize=[true/false]

# NAME or PREFIX, Empty will be ignore
view.query=[NAME/PREFIX]
view.name=[value/values]

# Compare output options, if default is ture, other options will be ignore.
view.compare.default=[true/false]
view.compare.strictVarchar=[true/false]
view.compare.strictNumeric=[true/false]
view.compare.strictDateTime=[true/false]
view.compare.checkNullable=[true/false]
view.compare.checkDataSize=[true/false]
```
## Example (Widows Batch)
### database.conf
 Define __DEV__, __TEST__ database sources.
  ```
  # DEV (PostgreSQL)
  db.DEV.type=PG
  db.DEV.host=localhost
  db.DEV.port=5432
  db.DEV.schema=public
  db.DEV.service=authdb
  db.DEV.user=auth
  db.DEV.password=auth

  # TEST (HANA)
  db.TEST.type=HANA
  db.TEST.host=10.160.3.69
  db.TEST.port=30012
  db.TEST.schema=WIP
  db.TEST.service=
  db.TEST.user=WIP
  db.TEST.password=12345
  ```

### plan.conf
  ```
  # all tables
  table.query=NAME
  table.name=

  table.compare.default=false
  table.compare.strictVarchar=true
  table.compare.strictNumeric=true
  table.compare.strictDateTime=true
  table.compare.checkNullable=true
  table.compare.checkDataSize=true

  # views with "VIEW_" prefix naming
  view.query=prefix
  view.name=VIEW_

  # View: Output options, if default is ture, other options will be ignore.
  view.compare.default=true
  ```

### compare
1. Compare __SHOP_ORDER__ table between __DEV__ & __TEST__ environment.
  ```
  > compare.bat -ds DEV -ds TEST -t SHOP_ORDER
  ```
2. Compare tables with __SFC__ prefix and diplay ___missing___ only.
  ```
  > compare.bat -ds DEV -ds TEST --table-prefix SFC --print-missing
  ```
3. Compare using __plan.coof__ and display ___failed___ only.
  ```
  > compare.bat -ds TEST -ds DEV --plan plan.conf
  ```

### create
1. create __SHOP_ORDER__ table from __DEV__ to __TEST__ environment.
  ```
  > create.bat -ds DEV -ds TEST -t SHOP_ORDER
  ```
2. create __VIEW_SFC__ and __VIEW_SFC_ITEM__ views from __TEST__ to __DEV__ environment.
  ```
  > create.bat -ds TEST -ds DEV -v VIEW_SFC,VIEW_SFC_ITEM
  ```
3. Create tables and views using __plan.coof__ from __TEST__ to __DEV__ environment.
  ```
  > create.bat -ds TEST -ds DEV --plan plan.conf
  ```
