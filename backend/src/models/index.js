// Export all models from a single file for easier imports
const User = require('./User');
const Chat = require('./Chat');
const Message = require('./Message');
const Status = require('./Status');
const Call = require('./Call');

module.exports = {
  User,
  Chat,
  Message,
  Status,
  Call
};
