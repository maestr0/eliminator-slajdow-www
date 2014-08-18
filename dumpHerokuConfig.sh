#!/bin/sh
bash -c 'echo "POSTMARK_INBOUND_ADDRESS=`heroku config:get POSTMARK_INBOUND_ADDRESS`" >> .env'
bash -c 'echo "POSTMARK_SMTP_SERVER=`heroku config:get POSTMARK_SMTP_SERVER`" >> .env'
bash -c 'echo "POSTMARK_API_KEY=`heroku config:get POSTMARK_API_KEY`" >> .env'

bash -c 'echo "DB_JDBC=`heroku config:get DB_JDBC`" >> .env'
bash -c 'echo "DB_DRIVER=`heroku config:get DB_DRIVER`" >> .env'
bash -c 'echo "DB_USER=`heroku config:get DB_USER`" >> .env'
bash -c 'echo "DB_PASSWORD=`heroku config:get DB_PASSWORD`" >> .env'
