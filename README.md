PwCrypt
---
PwCypt is an open source password manager which plans to have peer-to-peer synchronisation and optional backup to Google Drive. It utilises military grade AES-256bit encryption, with PBKDF2WithHmacSHA256 as its key derivation algorithm. 

Desktop, Android and iOS builds are available, but only Desktop and Android will be thoroughly tested. PwCrypt is built using JavaFX, with Gluon Mobile and Afterburner.fx.

This project was primarily created for its learning outcomes and to make use of the libraries I had been creating. It makes use of my other repositories:
  - EncryptionUtils: Facade for handling everything encryption related
  - DatabaseUtils: Encrypted credentials are stored in an SQLite database

#### Version Timeline with Upcoming Features:

    v0.1 - [COMPLETE] - Login/Register
    v0.2 - Show/Add/Edit/Delete credentials
    v0.3 - Password generator
    v0.4 - Interface with PwCrypt-Peer-Tracker REST Micro-service
    v0.5 - Sync between devices
    v0.6 - Google Drive sync