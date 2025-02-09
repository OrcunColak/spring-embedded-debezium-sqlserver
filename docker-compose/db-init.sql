-- Use master database to ensure the necessary changes are made
USE master;
GO

-- Check if the database 'mydb' exists. If not, create it
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'mydb')
BEGIN
    CREATE DATABASE mydb;
END
GO

-- Switch to the 'mydb' database
USE mydb;
GO

-- Enable CDC for the 'mydb' database if not already enabled
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'cdc_admin')
BEGIN
    EXEC sys.sp_cdc_enable_db;
END
GO

-- Create the 'users' table if it doesn't exist
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.users') AND type = 'U')
BEGIN
    CREATE TABLE dbo.users (
        id INT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(255),
        email NVARCHAR(255)
    );
END
GO

-- Enable CDC on the 'users' table if not already enabled
IF NOT EXISTS (SELECT * FROM cdc.change_tables WHERE source_object_id = OBJECT_ID('dbo.users'))
BEGIN
    EXEC sys.sp_cdc_enable_table
        @source_schema = 'dbo',
        @source_name = 'users',
        @role_name = NULL;
END
GO

-- Create the 'orders' table if it doesn't exist
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders') AND type = 'U')
BEGIN
    CREATE TABLE dbo.orders (
        id INT IDENTITY(1,1) PRIMARY KEY,
        user_id INT,
        amount DECIMAL(10,2),
        status NVARCHAR(50),
        created_at DATETIME DEFAULT GETDATE(),
        FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
    );
END
GO

-- Enable CDC on the 'orders' table if not already enabled
IF NOT EXISTS (SELECT * FROM cdc.change_tables WHERE source_object_id = OBJECT_ID('dbo.orders'))
BEGIN
    EXEC sys.sp_cdc_enable_table
        @source_schema = 'dbo',
        @source_name = 'orders',
        @role_name = NULL;
END
GO
