#!/usr/bin/env bash
bash -c 'echo "#!/bin/sh" > setEnv.sh'
bash -c 'echo "export POSTMARK_INBOUND_ADDRESS=`heroku config:get POSTMARK_INBOUND_ADDRESS --app eliminator-slajdow`" >> setEnv.sh'
bash -c 'echo "export POSTMARK_SMTP_SERVER=`heroku config:get POSTMARK_SMTP_SERVER --app eliminator-slajdow`" >> setEnv.sh'
bash -c 'echo "export POSTMARK_API_KEY=`heroku config:get POSTMARK_API_KEY --app eliminator-slajdow`" >> setEnv.sh'

bash -c 'echo "export DB_JDBC=\"`heroku config:get DB_JDBC  --app eliminator-slajdow`?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory\"" >> setEnv.sh'
bash -c 'echo "export DB_DRIVER=`heroku config:get DB_DRIVER --app eliminator-slajdow`" >> setEnv.sh'
bash -c 'echo "export DB_USER=`heroku config:get DB_USER --app eliminator-slajdow`" >> setEnv.sh'
bash -c 'echo "export DB_PASSWORD=`heroku config:get DB_PASSWORD --app eliminator-slajdow`" >> setEnv.sh'
