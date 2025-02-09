# Read Me

Verify CDC is enabled for mydb database

```sql
USE mydb;
SELECT name, is_cdc_enabled FROM sys.databases WHERE name = 'mydb';
```

Verify CDC is enabled for tables

```sql
SELECT name, is_tracked_by_cdc FROM sys.tables WHERE is_tracked_by_cdc = 1;
```