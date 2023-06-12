const mysql = require('mysql');
//TO DO: Replace it with your current database
const db = mysql.createConnection({
  host: 'public hostname',
  user: 'root',     
  password: 'root',      
  database: 'capstone',
  connectTimeout: 30000, // Set the connection timeout value (in milliseconds)
  acquireTimeout: 30000, // Set the timeout value for acquiring a connection (in milliseconds)
  timeout: 60000
})
 
module.exports = db;