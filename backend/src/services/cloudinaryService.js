const cloudinary = require('cloudinary').v2;
const { promisify } = require('util');

// Configure Cloudinary
cloudinary.config({
  cloud_name: process.env.CLOUDINARY_CLOUD_NAME,
  api_key: process.env.CLOUDINARY_API_KEY,
  api_secret: process.env.CLOUDINARY_API_SECRET
});

/**
 * Upload image to Cloudinary
 */
const uploadImage = async (fileBuffer, options = {}) => {
  try {
    const uploadOptions = {
      folder: 'akshar/images',
      resource_type: 'image',
      transformation: [
        { quality: 'auto' },
        { fetch_format: 'auto' }
      ],
      ...options
    };

    return new Promise((resolve, reject) => {
      cloudinary.uploader.upload_stream(
        uploadOptions,
        (error, result) => {
          if (error) {
            reject(error);
          } else {
            resolve({
              public_id: result.public_id,
              url: result.secure_url,
              width: result.width,
              height: result.height,
              format: result.format,
              size: result.bytes
            });
          }
        }
      ).end(fileBuffer);
    });
  } catch (error) {
    throw new Error(`Image upload failed: ${error.message}`);
  }
};

/**
 * Upload video to Cloudinary
 */
const uploadVideo = async (fileBuffer, options = {}) => {
  try {
    const uploadOptions = {
      folder: 'akshar/videos',
      resource_type: 'video',
      transformation: [
        { quality: 'auto' },
        { fetch_format: 'auto' }
      ],
      ...options
    };

    return new Promise((resolve, reject) => {
      cloudinary.uploader.upload_stream(
        uploadOptions,
        (error, result) => {
          if (error) {
            reject(error);
          } else {
            resolve({
              public_id: result.public_id,
              url: result.secure_url,
              width: result.width,
              height: result.height,
              format: result.format,
              size: result.bytes,
              duration: result.duration,
              thumbnail: result.thumbnail_url
            });
          }
        }
      ).end(fileBuffer);
    });
  } catch (error) {
    throw new Error(`Video upload failed: ${error.message}`);
  }
};

/**
 * Upload audio to Cloudinary
 */
const uploadAudio = async (fileBuffer, options = {}) => {
  try {
    const uploadOptions = {
      folder: 'akshar/audio',
      resource_type: 'video', // Cloudinary treats audio as video
      transformation: [
        { quality: 'auto' },
        { fetch_format: 'auto' }
      ],
      ...options
    };

    return new Promise((resolve, reject) => {
      cloudinary.uploader.upload_stream(
        uploadOptions,
        (error, result) => {
          if (error) {
            reject(error);
          } else {
            resolve({
              public_id: result.public_id,
              url: result.secure_url,
              format: result.format,
              size: result.bytes,
              duration: result.duration
            });
          }
        }
      ).end(fileBuffer);
    });
  } catch (error) {
    throw new Error(`Audio upload failed: ${error.message}`);
  }
};

/**
 * Upload document to Cloudinary
 */
const uploadDocument = async (fileBuffer, options = {}) => {
  try {
    const uploadOptions = {
      folder: 'akshar/documents',
      resource_type: 'raw',
      ...options
    };

    return new Promise((resolve, reject) => {
      cloudinary.uploader.upload_stream(
        uploadOptions,
        (error, result) => {
          if (error) {
            reject(error);
          } else {
            resolve({
              public_id: result.public_id,
              url: result.secure_url,
              format: result.format,
              size: result.bytes
            });
          }
        }
      ).end(fileBuffer);
    });
  } catch (error) {
    throw new Error(`Document upload failed: ${error.message}`);
  }
};

/**
 * Delete file from Cloudinary
 */
const deleteFile = async (publicId, resourceType = 'image') => {
  try {
    const result = await cloudinary.uploader.destroy(publicId, {
      resource_type: resourceType
    });
    
    return result.result === 'ok';
  } catch (error) {
    throw new Error(`File deletion failed: ${error.message}`);
  }
};

/**
 * Generate thumbnail for video
 */
const generateThumbnail = async (videoPublicId, options = {}) => {
  try {
    const thumbnailOptions = {
      resource_type: 'video',
      transformation: [
        { width: 300, height: 300, crop: 'fill', gravity: 'center' },
        { format: 'jpg', quality: 'auto' }
      ],
      ...options
    };

    const url = cloudinary.url(videoPublicId, thumbnailOptions);
    
    return {
      url: url,
      width: 300,
      height: 300
    };
  } catch (error) {
    throw new Error(`Thumbnail generation failed: ${error.message}`);
  }
};

/**
 * Resize image
 */
const resizeImage = async (imagePublicId, options = {}) => {
  try {
    const resizeOptions = {
      transformation: [
        { width: options.width || 800, height: options.height || 600, crop: 'limit' },
        { quality: 'auto', fetch_format: 'auto' }
      ],
      ...options
    };

    const url = cloudinary.url(imagePublicId, resizeOptions);
    
    return {
      url: url,
      width: options.width || 800,
      height: options.height || 600
    };
  } catch (error) {
    throw new Error(`Image resize failed: ${error.message}`);
  }
};

/**
 * Get file info from Cloudinary
 */
const getFileInfo = async (publicId, resourceType = 'image') => {
  try {
    const result = await cloudinary.api.resource(publicId, {
      resource_type: resourceType
    });
    
    return {
      public_id: result.public_id,
      url: result.secure_url,
      width: result.width,
      height: result.height,
      format: result.format,
      size: result.bytes,
      created_at: result.created_at
    };
  } catch (error) {
    throw new Error(`Failed to get file info: ${error.message}`);
  }
};

/**
 * Upload file based on type
 */
const uploadFile = async (fileBuffer, fileType, originalName, options = {}) => {
  const timestamp = Date.now();
  const fileName = `${timestamp}_${originalName.replace(/[^a-zA-Z0-9.-]/g, '_')}`;
  
  const uploadOptions = {
    public_id: fileName,
    ...options
  };

  switch (fileType) {
    case 'image':
      return await uploadImage(fileBuffer, uploadOptions);
    case 'video':
      return await uploadVideo(fileBuffer, uploadOptions);
    case 'audio':
      return await uploadAudio(fileBuffer, uploadOptions);
    case 'document':
      return await uploadDocument(fileBuffer, uploadOptions);
    default:
      throw new Error(`Unsupported file type: ${fileType}`);
  }
};

module.exports = {
  uploadImage,
  uploadVideo,
  uploadAudio,
  uploadDocument,
  uploadFile,
  deleteFile,
  generateThumbnail,
  resizeImage,
  getFileInfo
};
