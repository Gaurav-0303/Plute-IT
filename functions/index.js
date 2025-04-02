/**
 * Firebase Cloud Functions for PluteIT notifications.
 */
const {
  onValueCreated,
  onValueUpdated,
  onValueDeleted,
} = require("firebase-functions/v2/database");
const admin = require("firebase-admin");

admin.initializeApp();

/**
   * Sends a push notification to subscribed users.
   * @param {Object} payload - The notification payload.
   * @return {Promise} A promise indicating the send status.
   */
function sendNotification(payload) {
  const message = {
    notification: {
      title: payload.title,
      body: payload.body,
      image: payload.image || "",
    },
    data: payload.data,
    topic: "pluteit-updates",
  };

  return admin
      .messaging()
      .send(message)
      .then((response) => {
        console.log("Notification sent successfully:", response);
        return null;
      })
      .catch((error) => {
        console.error("Error sending notification:", error);
      });
}

/**
   * Listens for new category additions and sends a notification.
   */
exports.onCategoryAdded = onValueCreated(
    "/categories/{categoryId}",
    (event) => {
      const category = event.data.val();
      const payload = {
        title: "New Category Added",
        body: `Category "${category.title}" has been added!`,
        image: category.image || "",
        type: "category_added",
        data: {
          categoryId: event.params.categoryId,
          title: category.title,
        },
      };
      return sendNotification(payload);
    },
);

/**
   * Listens for new item additions and sends a notification.
   */
exports.onItemAdded = onValueCreated(
    "/items/{itemId}",
    (event) => {
      const item = event.data.val();
      const payload = {
        title: "New Item Added",
        body: `Item "${item.name}" has been added!`,
        image: item.logo || "",
        type: "item_added",
        data: {
          itemId: event.params.itemId,
          name: item.name,
          categoryUid: item.categoryUid,
        },
      };
      return sendNotification(payload);
    },
);

/**
   * Listens for item updates and sends a notification.
   */
exports.onItemUpdated = onValueUpdated(
    "/items/{itemId}",
    (event) => {
      const before = event.data.before.val();
      const after = event.data.after.val();

      if (JSON.stringify(before) === JSON.stringify(after)) {
        return null;
      }

      const payload = {
        title: "Item Updated",
        body: `Item "${after.name}" has been updated!`,
        image: after.logo || "",
        type: "item_updated",
        data: {
          itemId: event.params.itemId,
          name: after.name,
          categoryUid: after.categoryUid,
        },
      };
      return sendNotification(payload);
    },
);

/**
   * Listens for item deletions and sends a notification.
   */
exports.onItemDeleted = onValueDeleted(
    "/items/{itemId}",
    (event) => {
      const item = event.data.val();
      const payload = {
        title: "Item Deleted",
        body: `Item "${item.name}" has been removed!`,
        image: item.logo || "",
        type: "item_deleted",
        data: {
          itemId: event.params.itemId,
          name: item.name,
        },
      };
      return sendNotification(payload);
    },
);
