package com.example.android.worldnewsapp.Utils;

public class WasteCodes {

//     /*final RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//
//        Call<NewsResponse> call = apiService.getTopHeadlines(country,category,API_KEY);
//        call.enqueue(new Callback<NewsResponse>() {
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                int statusCode = response.code();
//                List<News> news= response.body().getArticles();
//                recyclerView.setAdapter(new NewsAdapter(news, R.layout.list_item_news, getApplicationContext()));
//            }
//
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
//
//    public void createNotificationChannel() {
//
//        // Create a notification manager object.
//        mNotifyManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        // Notification channels are only available in OREO and higher.
//        // So, add a check on SDK version.
//        if (android.os.Build.VERSION.SDK_INT >=
//                android.os.Build.VERSION_CODES.O) {
//
//            // Create the NotificationChannel with all the parameters.
//            NotificationChannel notificationChannel = new NotificationChannel
//                    (PRIMARY_CHANNEL_ID,
//                            getString(R.string.notification_channel_name),
//                            NotificationManager.IMPORTANCE_HIGH);
//
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setDescription
//                    (getString(R.string.notification_channel_description));
//
//            mNotifyManager.createNotificationChannel(notificationChannel);
//        }
//    }
//
//    /**
//     * Helper method that builds the notification.
//     *
//     * @return NotificationCompat.Builder: notification build with all the
//     * parameters.
//     */
//    private NotificationCompat.Builder getNotificationBuilder() {
//
//        // Set up the pending intent that is delivered when the notification
//        // is clicked.
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent notificationPendingIntent = PendingIntent.getActivity
//                (this, NOTIFICATION_ID, notificationIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Build the notification with all of the parameters.
//        NotificationCompat.Builder notifyBuilder = new NotificationCompat
//                .Builder(this, PRIMARY_CHANNEL_ID)
//                .setContentTitle( getString(R.string.notification_title))
//                .setContentText(getString(R.string.notification_text))
//                .setSmallIcon(R.mipmap.news_primary_50px)
//                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setDefaults(NotificationCompat.DEFAULT_ALL);
//        return notifyBuilder;
//    }
//
//    /**
//     * OnClick method for the "Update Me!" button. Updates the existing
//     * notification to show a picture.
//     */
//    public void updateNotification() {
//
//        // Load the drawable resource into the a bitmap image.
//        Bitmap androidImage = BitmapFactory
//                .decodeResource(getResources(), R.mipmap.news_primary_50px);
//
//        // Build the notification with all of the parameters using helper
//        // method.
//        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
//
//        // Update the notification style to BigPictureStyle.
//        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//                .bigPicture(androidImage)
//                .setBigContentTitle(getString(R.string.notification_updated)));
//
//        // Deliver the notification.
//        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
//
//        // Disable the update button, leaving only the cancel button enabled.
//        setNotificationButtonState(false, false, true);
//    }
//
//    /**
//     * OnClick method for the "Cancel Me!" button. Cancels the notification.
//     */
//    /*public void cancelNotification() {
//        // Cancel the notification.
//        mNotifyManager.cancel(NOTIFICATION_ID);
//
//        // Reset the buttons.
//        setNotificationButtonState(true, false, false);
//    }
//
//    /**
//     * Helper method to enable/disable the buttons.
//     *
//     * @param isNotifyEnabled, boolean: true if notify button enabled
//     * @param isUpdateEnabled, boolean: true if update button enabled
//     * @param isCancelEnabled, boolean: true if cancel button enabled
//     */
//    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean
//            isUpdateEnabled, Boolean isCancelEnabled) {
//        /*button_notify.setEnabled(isNotifyEnabled);
//        button_update.setEnabled(isUpdateEnabled);
//        button_cancel.setEnabled(isCancelEnabled);*/
//    }
//
//
//
//    /**
//     * OnClick method for the "Notify Me!" button.
//     * Creates and delivers a simple notification.
//     */
//    public void sendNotification() {
//
//        // Sets up the pending intent to update the notification.
//        // Corresponds to a press of the Update Me! button.
//        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
//        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
//                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
//
//        // Build the notification with all of the parameters using helper
//        // method.
//        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
//
//        // Add the action button using the pending intent.
//        notifyBuilder.addAction(R.mipmap.news_primary_50px,
//                getString(R.string.notification_updated), updatePendingIntent);
//
//        // Deliver the notification.
//        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
//
//        // Enable the update and cancel buttons but disables the "Notify
//        // Me!" button.
//        setNotificationButtonState(false, true, true);
//    }*/

}
