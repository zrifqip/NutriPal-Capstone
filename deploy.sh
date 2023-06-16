gcloud builds submit \
  --tag gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-survey-api:v0.3

gcloud run deploy nutripal-survey-api \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-survey-api:v0.3 \
  --platform managed \
  --region asia-southeast2 \
  --allow-unauthenticated \
  --max-instances=1 \
  --add-cloudsql-instances INSTANCE_CONNECTION_NAME \
  --set-env-vars INSTANCE_UNIX_SOCKET="..." \
  --set-env-vars INSTANCE_CONNECTION_NAME="..." \
  --set-env-vars DB_NAME="..." \
  --set-env-vars DB_USER="..." \
  --set-env-vars DB_PASS="..." \
  --set-env-vars SECRET_KEY="..."
