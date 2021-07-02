# Database
MariaDB Docker Compose

This Docker-Compose file contains a MariaDB server and PHPMyAdmin dashboard for convenient access.

## Helper script
This folder contains a helper script `helper`. It contains some useful commands to make life easier for
inexperienced Docker users.

### Commands
The helper script contains the following commands:

- `./helper start` - Starts Docker compose in foreground (`docker-compose up`)
- `./helper bg` - Starts Docker compose in background (`docker-compose up -d`)
- `./helper stop` - Stops Docker compose (`docker-compose down`)
- `./helper restart` - Restarts both services
- `./helper dump` - Creates a MySQL dump of the `tournament` database and saves it as `dump.sql`.
- `./helper script [file]` - Executes an SQL script from the `scripts` folder in the database console.
- `./helper console` - Brings up the MariaDB console.
- `./helper init` - Initializes the tables from the `init.sql` script.

## Without helper script
### Start MariaDB
```bash
# With console logging
docker-compose up

# In background
docker-compose up -d
```

### Stop MariaDB
```bash
docker-compose down
```

## Setup
When started, a new folder called `db` is created in the directory, where the data can be persisted.

The MariaDB database is running on **port 3307**. Default username and password are `root`. Access PHPMyAdmin via [localhost:8000](http://localhost:8000).

It is recommended to import the `init.sql` script into the database when you first set up the database. You can do
this by running `./helper init`. If you don't want to or can't use the helper script use your local machine's
MySQL client or PHPMyAdmin to upload the script.
