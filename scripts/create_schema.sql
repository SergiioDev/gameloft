SELECT 'CREATE DATABASE gameloft'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'gameloft')\gexec

\c gameloft

