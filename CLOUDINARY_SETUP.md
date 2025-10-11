# Cloudinary Setup Guide

## ☁️ **Free File Storage Setup**

### Step 1: Create Account
1. Go to: https://cloudinary.com/
2. Click "Sign Up For Free"
3. Fill in details and verify email

### Step 2: Get Credentials
1. Login to Cloudinary Dashboard
2. Go to "Dashboard" section
3. Copy these values:

```
Cloud Name: your_cloud_name
API Key: 123456789012345
API Secret: your_secret_key_here
```

### Step 3: Update .env File
```env
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=123456789012345
CLOUDINARY_API_SECRET=your_secret_key_here
```

## ✅ **What Cloudinary Provides:**
- **Free Plan**: 25GB storage, 25GB bandwidth/month
- **Image Optimization**: Auto-resize, format conversion
- **Video Processing**: Thumbnails, compression
- **CDN**: Global fast delivery
- **Transformations**: Crop, resize, effects

## 📁 **File Types Supported:**
- Images: JPG, PNG, GIF, WebP
- Videos: MP4, MOV, AVI
- Audio: MP3, WAV, OGG
- Documents: PDF, DOC, DOCX

## 🔧 **Features Used in Akshar:**
- Avatar uploads
- Message media files
- Image thumbnails
- Video compression
- Document storage

## 📱 **How it Works in Akshar:**
1. User uploads file from mobile app
2. File sent to backend API
3. Backend uploads to Cloudinary
4. Cloudinary returns optimized URL
5. URL stored in database
6. Other users can download/view file

## 🚀 **Quick Test:**
After setup, test file upload:
```bash
POST /api/messages/:chatId/media
# Upload an image and see Cloudinary URL in response
```
