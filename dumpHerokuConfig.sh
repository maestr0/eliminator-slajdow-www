#!/bin/sh
bash -c 'echo "POSTMARK_INBOUND_ADDRESS=`heroku config:get POSTMARK_INBOUND_ADDRESS`" → .env'
bash -c 'echo "POSTMARK_SMTP_SERVER=`heroku config:get POSTMARK_SMTP_SERVER`" → .env'
bash -c 'echo "POSTMARK_API_KEY=`heroku config:get POSTMARK_API_KEY`" → .env'
