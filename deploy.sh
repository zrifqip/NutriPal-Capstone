gcloud builds submit \
  --tag gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-food-ml-api:v0.1

gcloud run deploy nutripal-food-ml-api \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-food-ml-api:v0.1 \
  --platform managed \
  --region asia-southeast2 \
  --allow-unauthenticated \
  --max-instances=1 