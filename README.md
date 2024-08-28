##File Management Application

## Overview

The File Management Application is an intuitive Android tool for easy file management. It supports creating, opening, saving, and deleting files with a user-friendly interface. 

## Features

- **Document Creation**: Create new documents easily.
- **File Editing**: Open and edit existing files.
- **File Management**: Streamlined operations for efficient file handling.

## Architecture

The application follows the **Model-View-Controller (MVC)** design pattern:

- **Model**: Manages data and file operations.
- **View**: Handles the user interface and interactions.
- **Controller**: Processes user inputs, interacts with the model, and updates the view.

## Components

### User Interface (UI)

- **MainActivity**: The main screen for file operations (Create, Delete, Open, Show Files).
  - `EditText`: For entering file names.
  - `Buttons`: For file operations.
- **FileContentActivity**: For viewing and editing file content.
  - `TextView`: Displays the file name.
  - `EditText`: For editing file content.
  - `Button`: For saving changes.

### Functional Components

- **Create File**: Validates and saves new files, navigating to FileContentActivity.
- **Open File**: Loads and displays files in FileContentActivity.
- **Save File**: Updates files with new content from FileContentActivity.
- **Delete File**: Removes specified files and provides feedback.
- **Show Files**: Displays a list of all files in a dialog box.

### File Storage

- Files are stored in the app’s private external storage directory.
- File names are managed using SharedPreferences.

## Development

- **Development Environment**: Android Studio Koala

View raw, if any issues in accessing the zip directly from the branch
