#!/bin/sh
bash -c 'echo "#!/bin/sh" > setEnv.sh'
bash -c 'echo "export POSTMARK_INBOUND_ADDRESS=`heroku config:get POSTMARK_INBOUND_ADDRESS`" >> setEnv.sh'
bash -c 'echo "export POSTMARK_SMTP_SERVER=`heroku config:get POSTMARK_SMTP_SERVER`" >> setEnv.sh'
bash -c 'echo "export POSTMARK_API_KEY=`heroku config:get POSTMARK_API_KEY`" >> setEnv.sh'

bash -c 'echo "export DB_JDBC=\"`heroku config:get DB_JDBC`?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory\"" >> setEnv.sh'
bash -c 'echo "export DB_DRIVER=`heroku config:get DB_DRIVER`" >> setEnv.sh'
bash -c 'echo "export DB_USER=`heroku config:get DB_USER`" >> setEnv.sh'
bash -c 'echo "export DB_PASSWORD=`heroku config:get DB_PASSWORD`" >> setEnv.sh'
