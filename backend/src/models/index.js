// Export all models from a single file for easier imports
const User = require('./User');
const Chat = require('./Chat');
const Message = require('./Message');
const Status = require('./Status');

module.exports = {
  User,
  Chat,
  Message,
  Status
};
