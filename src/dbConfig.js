const mysql = require('mysql');
//TO DO: Replace it with your current database
const db = mysql.createConnection({
    socketPath: process.env.INSTANCE_UNIX_SOCKET,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    connectTimeout: 30000, // Set the connection timeout value (in milliseconds)
    acquireTimeout: 30000, // Set the timeout value for acquiring a connection (in milliseconds)
    timeout: 60000
  });
  
/*db.connect((err) => {
    if (err) {
      console.error('Error connecting to the database:', err);
      return;
    }
    console.log('Successfully connected to the database');
  });*/
 
module.exports = db;