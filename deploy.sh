gcloud builds submit \
  --tag gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-food-ml-api:v0.1

gcloud run deploy nutripal-food-ml-api \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-food-ml-api:v0.1 \
  --platform managed \
  --region asia-southeast2 \
  --allow-unauthenticated \
  --max-instances=1 \
  --add-cloudsql-instances INSTANCE_CONNECTION_NAME \
  --set-env-vars INSTANCE_UNIX_SOCKET="/cloudsql/...:nutripal" \
  --set-env-vars INSTANCE_CONNECTION_NAME="...:nutripal" \
  --set-env-vars DB_NAME="capstone" \
  --set-env-vars DB_USER="root" \
  --set-env-vars DB_PASS="root"