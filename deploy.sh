gcloud builds submit \
  --tag gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-survey-api:v0.2

gcloud run deploy nutripal-survey-api \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-survey-api:v0.2 \
  --platform managed \
  --region asia-southeast2 \
  --allow-unauthenticated \
  --max-instances=3 \
  --add-cloudsql-instances INSTANCE_CONNECTION_NAME \
  --set-env-vars INSTANCE_UNIX_SOCKET="/cloudsql/...:nutripal" \
  --set-env-vars INSTANCE_CONNECTION_NAME="...:nutripal" \
  --set-env-vars DB_NAME="capstone" \
  --set-env-vars DB_USER="root" \
  --set-env-vars DB_PASS="root"