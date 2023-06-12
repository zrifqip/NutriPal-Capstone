gcloud builds submit \
  --tag gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-survey-api:v0.1

gcloud run deploy nutripal-survey-api \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/nutripal-survey-api:v0.1 \
  --platform managed \
  --region asia-southeast2 \
  --allow-unauthenticated \
  --max-instances=3