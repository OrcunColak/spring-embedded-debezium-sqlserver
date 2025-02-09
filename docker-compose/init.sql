-- Enable CDC at the database level
EXEC sys.sp_cdc_enable_db;

-- Create a sample table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.users') AND type in (N'U'))
BEGIN
    CREATE TABLE dbo.users (
        id INT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(255),
        email NVARCHAR(255)
    );
END

-- Enable CDC on the table
EXEC sys.sp_cdc_enable_table
    @source_schema = 'dbo',
    @source_name = 'users',
    @role_name = NULL;
