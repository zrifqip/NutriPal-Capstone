const mysql = require('mysql');
//TO DO: Replace it with your current database
const db = mysql.createConnection({
  host: '34.128.110.173',
  user: 'root',     
  password: 'root',      
  database: 'capstone',
  connectTimeout: 30000, // Set the connection timeout value (in milliseconds)
  acquireTimeout: 30000, // Set the timeout value for acquiring a connection (in milliseconds)
  timeout: 60000
})
 
/*db.connect(function(err) {
  if (err) {
      console.error('Error connecting to MySQL database:', err);
      return;
    }
console.log('Database is connected successfully !');
}); */
module.exports = db;