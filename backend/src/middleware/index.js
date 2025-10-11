// Export all middleware from a single file for easier imports
const { authMiddleware, optionalAuthMiddleware, requireRole, requireGroupAdmin, requireChatParticipant, validateInput } = require('./authMiddleware');
const { errorHandler, asyncHandler, AppError } = require('./errorHandler');

module.exports = {
  authMiddleware,
  optionalAuthMiddleware,
  requireRole,
  requireGroupAdmin,
  requireChatParticipant,
  validateInput,
  errorHandler,
  asyncHandler,
  AppError
};
